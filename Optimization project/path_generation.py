import pulp
import networkx as nx
import random
import time
import heapq
from collections import defaultdict
from typing import List, Tuple, Dict, Set
import numpy as np


class PathBasedMaxFlow:
    def __init__(self, G: nx.DiGraph, source: int, sink: int):
        self.G = G
        self.source = source
        self.sink = sink
        self.edge_capacities = {(u, v): data.get('capacity', 0) for u, v, data in G.edges(data=True)}

        # Data structures for efficiency
        self.edge_to_paths = defaultdict(list)

        self.paths = []
        self.path_edges = []
        self.path_to_idx = {}  # For quick lookup

        # Master problem
        self.prob = None
        self.x_vars = {}
        self.constraints = {}

        # Cache for pricing
        self._successors = {node: list(self.G.successors(node)) for node in self.G.nodes()}
        self._predecessors = {node: list(self.G.predecessors(node)) for node in self.G.nodes()}

    def find_initial_paths(self) -> List[List[int]]:

        paths = []

        try:
            p1 = nx.shortest_path(self.G, self.source, self.sink)
            paths.append(p1)
        except:
            pass

        try:
            p2 = self._widest_path()
            if p2 and tuple(p2) != tuple(p1):
                paths.append(p2)
        except:
            pass

        for _ in range(1):
            path = self._random_path()
            if path and tuple(path) not in [tuple(p) for p in paths]:
                paths.append(path)

        return paths if paths else [self._find_any_path()]

    def _widest_path(self) -> List[int]:

        max_cap = {v: 0 for v in self.G.nodes()}
        max_cap[self.source] = float('inf')
        parent = {}
        pq = [(-float('inf'), self.source)]

        while pq:
            curr_cap, u = heapq.heappop(pq)
            curr_cap = -curr_cap

            if u == self.sink:
                break

            if curr_cap < max_cap[u]:
                continue

            for v in self._successors[u]:
                cap = self.edge_capacities.get((u, v), 0)
                path_cap = min(curr_cap, cap)

                if path_cap > max_cap[v]:
                    max_cap[v] = path_cap
                    parent[v] = u
                    heapq.heappush(pq, (-path_cap, v))

        # Reconstruct path
        if self.sink not in parent:
            raise nx.NetworkXNoPath

        path = [self.sink]
        while path[-1] != self.source:
            path.append(parent[path[-1]])
        path.reverse()

        return path    # O(ElogV)

    def _random_path(self) -> List[int]:
        max_depth = 100
        visited = set()
        stack = [(self.source, [self.source])]

        while stack:
            node, path = stack.pop()
            if len(path) > max_depth:
                continue

            if node == self.sink:
                return path

            if node in visited:
                continue
            visited.add(node)

            successors = self._successors[node]
            if successors:
                random.shuffle(successors)
                for next_node in successors:
                    if next_node not in path:
                        stack.append((next_node, path + [next_node]))

        return None

    def _find_any_path(self) -> List[int]:
        try:
            return next(nx.all_simple_paths(self.G, self.source, self.sink))
        except StopIteration:
            raise ValueError("No path from source to sink")

    def add_path(self, path: List[int]) -> int:
        path_tuple = tuple(path)
        if path_tuple in self.path_to_idx:
            return self.path_to_idx[path_tuple]

        path_idx = len(self.paths)
        self.paths.append(path)
        self.path_to_idx[path_tuple] = path_idx

        edges = set()
        for i in range(len(path) - 1):
            u, v = path[i], path[i + 1]
            edges.add((u, v))
            self.edge_to_paths[(u, v)].append(path_idx)

        self.path_edges.append(edges)
        return path_idx

    def build_master_problem(self, initial_paths: List[List[int]]):

        self.prob = pulp.LpProblem("PathBasedMaxFlow", pulp.LpMaximize)

        for path in initial_paths:
            path_idx = self.add_path(path)
            var_name = f"x_{path_idx}"
            self.x_vars[path_idx] = pulp.LpVariable(var_name, lowBound=0)

        self.prob += pulp.lpSum(self.x_vars.values())

        for (u, v), cap in self.edge_capacities.items():
            constr_name = f"cap_{u}_{v}"

            vars_in_edge = []
            for path_idx, edges in enumerate(self.path_edges):
                if (u, v) in edges and path_idx in self.x_vars:
                    vars_in_edge.append(self.x_vars[path_idx])

            constr = pulp.LpConstraint(
                e=pulp.lpSum(vars_in_edge),
                sense=pulp.LpConstraintLE,
                rhs=cap,
                name=constr_name
            )
            self.constraints[(u, v)] = constr
            self.prob.addConstraint(constr)

    def solve_master(self) -> Tuple[float, Dict[Tuple[int, int], float]]:

        try:
            # solver = pulp.PULP_CBC_CMD(msg=False, timeLimit=30)
            solver = pulp.PULP_CBC_CMD(msg=False)
            self.prob.solve(solver)
        except:
            self.prob.solve(pulp.PULP_CBC_CMD(msg=False))

        # Get dual values of the edge capacity constraints
        duals = {}
        for (u, v) in self.edge_capacities.keys():
            constr_name = f"cap_{u}_{v}"
            if constr_name in self.prob.constraints:
                constr = self.prob.constraints[constr_name]

                if hasattr(constr, 'pi') and constr.pi is not None:
                    duals[(u, v)] = constr.pi
                elif hasattr(constr, 'shadowPrice'):
                    duals[(u, v)] = constr.shadowPrice
                else:
                    duals[(u, v)] = 0.0
            else:
                duals[(u, v)] = 0.0

        objective = pulp.value(self.prob.objective)
        if objective is None:
            objective = 0.0

        return objective, duals

    def pricing_problem(self, duals: Dict[Tuple[int, int], float]) -> Tuple[List[int], float]:

        has_negative_weights = any(w < 0 for w in duals.values())

        if not has_negative_weights:
            return self._pricing_dijkstra(duals)
        else:
            return self._pricing_bellman_ford(duals)

    def _pricing_dijkstra(self, duals):

        dist = {node: float('inf') for node in self.G.nodes()}
        dist[self.source] = 0
        parent = {node: None for node in self.G.nodes()}
        heap = [(0, self.source)]

        while heap:
            current_dist, u = heapq.heappop(heap)

            if current_dist > dist[u]:
                continue

            if u == self.sink:
                break

            for v in self._successors[u]:
                weight = duals.get((u, v), 0.0)
                new_dist = dist[u] + weight

                if new_dist < dist[v]:
                    dist[v] = new_dist
                    parent[v] = u
                    heapq.heappush(heap, (new_dist, v))

        if parent[self.sink] is None:
            return None, float('-inf')

        path = []
        node = self.sink
        while node is not None:
            path.append(node)
            node = parent[node]
        path.reverse()

        # Calculate reduced cost
        total_dual = sum(duals.get((u, v), 0.0) for u, v in zip(path[:-1], path[1:]))
        rc = 1.0 - total_dual

        return path, rc

    # def _pricing_bellman_ford(self, duals):
    #     """Use Bellman-Ford for negative weights."""
    #     dist = {node: float('inf') for node in self.G.nodes()}
    #     dist[self.source] = 0
    #     parent = {node: None for node in self.G.nodes()}
    #
    #     # Bellman-Ford: relax edges |V| - 1 times
    #     for _ in range(len(self.G.nodes()) - 1):
    #         updated = False
    #         for u, v in self.G.edges():
    #             weight = duals.get((u, v), 0.0)
    #             if dist[u] + weight < dist[v]:   
    #                 dist[v] = dist[u] + weight
    #                 parent[v] = u
    #                 updated = True
    #         if not updated:
    #             break
    #
    #     if parent[self.sink] is None:
    #         return None, float('-inf')
    #
    #     # Reconstruct path
    #     path = []
    #     node = self.sink
    #     while node is not None:
    #         path.append(node)
    #         node = parent[node]
    #     path.reverse()
    #
    #     # Calculate reduced cost
    #     total_dual = sum(duals.get((u, v), 0.0) for u, v in zip(path[:-1], path[1:]))
    #     rc = 1.0 - total_dual
    #
    #     return path, rc

    def add_new_column(self, path: List[int]):

        path_idx = self.add_path(path)

        if path_idx in self.x_vars:
            return

        # Create new variable
        var_name = f"x_{path_idx}"
        new_var = pulp.LpVariable(var_name, lowBound=0)
        self.x_vars[path_idx] = new_var

        # Update objective
        self.prob.setObjective(self.prob.objective + new_var)

        # Update constraints for ALL edges in this path
        for i in range(len(path) - 1):
            u, v = path[i], path[i + 1]
            constr_name = f"cap_{u}_{v}"

            if constr_name in self.prob.constraints:
                self.prob.constraints[constr_name] += new_var
            else:

                cap = self.edge_capacities.get((u, v), 0)
                constr = pulp.LpConstraint(
                    e=new_var,
                    sense=pulp.LpConstraintLE,
                    rhs=cap,
                    name=constr_name
                )
                self.constraints[(u, v)] = constr
                self.prob.addConstraint(constr)

    def solve(self, max_iterations=100, tolerance=1e-6):
        """Solve the problem with column generation"""
        start_time = time.time()

        # Initialize with some paths
        print("Finding initial paths...")
        initial_paths = self.find_initial_paths()
        print(f"Found {len(initial_paths)} initial paths")

        # Build master problem once
        print("Building master problem...")
        self.build_master_problem(initial_paths)

        iteration = 0
        improving = True

        while improving:
            iteration += 1

            # Solve restricted master
            print(f"\nIteration {iteration}: Solving master with {len(self.paths)} paths...")  # in every iteration is solved master problem
            obj, duals = self.solve_master()               # we get the solution and the duals
            print(f"Current objective: {obj:.2f}")

            # Solve pricing problem
            print("Solving pricing problem...")
            new_path, reduced_cost = self.pricing_problem(duals)   # the new path and its reduce cost is generated

            if new_path is None:
                print("No path found in pricing")
                improving = False
                continue

            print(f"Reduced cost: {reduced_cost:.6f}")

            # Check if we should stop
            if reduced_cost <= tolerance:
                print(f"Optimal reached (reduced cost <= {tolerance})")
                improving = False
            else:
                # Check if path is new
                path_tuple = tuple(new_path)
                path_exists = path_tuple in self.path_to_idx

                if not path_exists:
                    print(f"Adding new path (length {len(new_path)})")
                    self.add_new_column(new_path)
                else:
                    print(f"Path already exists, stopping to avoid cycling")
                    improving = False

        # optimal solution is achieved (or there doesn't exist any path from source to sink)

        # Final solve
        obj, _ = self.solve_master()   #we compute now the value of the objective function after all generated paths (from pricing problem) have been added
        end_time = time.time()

        # Get final solution
        final_flows = {}
        for path_idx, var in self.x_vars.items():
            if path_idx < len(self.paths):
                flow = var.value() if var.value() is not None else 0
                if flow > tolerance:  # only paths with value higher than the tolerance are stored => this is the real solution
                    final_flows[tuple(self.paths[path_idx])] = flow

        return {
            'objective': obj,
            'flows': final_flows,
            'paths': len(self.paths),
            'iterations': iteration,
            'time': end_time - start_time
        }


def solve_max_flow_conventional(G, source, sink):

    # Initialize LP
    lp = pulp.LpProblem("MaxFlow_Conventional", pulp.LpMaximize)

    # Flow variables
    flow_vars = {}
    for u, v in G.edges():
        flow_vars[(u, v)] = pulp.LpVariable(f"f_{u}_{v}", lowBound=0, upBound=G[u][v]['capacity'])

    # Objective: maximize flow out of source
    lp += pulp.lpSum(flow_vars[(source, v)] for v in G.successors(source)), "TotalFlow"

    # Flow conservation constraints for intermediate nodes
    for node in G.nodes():
        if node not in [source, sink]:
            lp += (pulp.lpSum(flow_vars[(u, node)] for u in G.predecessors(node)) == pulp.lpSum(flow_vars[(node, v)] for v in G.successors(node))), f"FlowConservation_{node}"

    # Solve LP
    start_time = time.time()
    lp.solve()
    end_time = time.time()

    # Extract results
    status = pulp.LpStatus[lp.status]
    max_flow = pulp.value(lp.objective)
    solve_time = end_time - start_time

    # Flow values per edge
    flow_values = {(u, v): flow_vars[(u, v)].value() for u, v in G.edges()}

    result = {
        'status': status,
        'max_flow': max_flow,
        'solve_time': solve_time,
        'flow_vars': flow_values
    }

    return result



if __name__ == "__main__":

    def create_test_graph(nodes=10000, edges_per_node=5):
        G = nx.DiGraph()

        G.add_nodes_from(range(nodes))

        for i in range(nodes):

            for _ in range(edges_per_node):
                j = random.randint(0, nodes - 1)
                if i != j and not G.has_edge(i, j):
                    cap = random.randint(1, 100)  # random capacities
                    G.add_edge(i, j, capacity=cap)

        source = 0
        sink = nodes - 1

        for i in range(min(100, nodes)):
            if i != sink:
                G.add_edge(i, sink, capacity=random.randint(10, 50))
            if i != source:
                G.add_edge(source, i, capacity=random.randint(10, 50))

        return G, source, sink


    print("Creating test graph...")
    G, source, sink = create_test_graph(10000, 5)

    print(f"Graph with {G.number_of_nodes()} nodes and {G.number_of_edges()} edges")

    solver = PathBasedMaxFlow(G, source, sink)

    print("\nStarting column generation...")
    # result = solver.solve(max_iterations=50)
    result = solver.solve()

    # test_pricing_problem_simple()

    print(f"\nResults of path generation:")
    print(f"Objective value: {result['objective']:.2f}")
    print(f"Number of paths generated: {result['paths']}")
    print(f"Iterations: {result['iterations']}")
    print(f"Time: {result['time']:.2f} seconds")

    print("\nTop flows:")
    sorted_flows = sorted(result['flows'].items(), key=lambda x: x[1], reverse=True)[:5]
    for path, flow in sorted_flows:
        print(f"  Path {path[:5]}... (length {len(path)}): {flow:.2f}")

    # Conventional LP
    result = solve_max_flow_conventional(G, source, sink)
    print(f"\nResults of conventional LP:")
    print("Status:", result['status'])
    print("Maximum Flow:", result['max_flow'])
    print("Solve time:", result['solve_time'])
    # for edge, f in list(result['flow_vars'].items())[:10]:
    #     print(edge, "->", f)