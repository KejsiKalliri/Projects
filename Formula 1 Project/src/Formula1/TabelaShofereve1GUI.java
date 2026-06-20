package Formula1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTable;

public class TabelaShofereve1GUI {
private JFrame frame;
private JPanel panel1;
private JButton button;
private JButton button2;
private JButton button3;
private JButton button4;
private JButton button5;
private JButton button6;
private JButton button7;
private JTextField textfield;
private JLabel label2;
private JTextArea area;
private JTable table;

public TabelaShofereve1GUI() {
	frame=new JFrame("Formula 1");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(500,500);
	frame.setVisible(true);
	frame.setLocation(300,300);
	
	frame.setLayout(new BorderLayout());
	
	panel1=new JPanel();
	panel1.setLayout(new GridLayout(4,3));
	
	button=new JButton("Gjenero gare");
	
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				gjenero();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	});
	
	
	button2=new JButton("Gjenero pozicionin fillestar");
	
	button2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				gjenero_poz_fillestar();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	});
	
	
	button3=new JButton("Shfaq garat e perfunduara");
	
	button3.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				shfaq_garat();
			} catch (FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}
		}
	});
	
	
	textfield=new JTextField(20);
	label2=new JLabel("Vendos nje emer garuesi:");
	button4=new JButton("Shfaq detajet e garave");
	
	
	button4.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				teDhenat_eShoferit();
			} catch (FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}
		}
	});
	
	
	button5=new JButton("Shfaq statistikat ne rendin zbrites");
	
	button5.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
			   Afisho();
			} catch (NullPointerException | IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	});
	
	
	button6=new JButton("Shfaq statistikat ne rendin rrites");
	
	button6.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			shfaq_statistikat();
		}
	});
	
	
	button7=new JButton("Shfaq statistikat sipas vendit te pare ");
	
	button7.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			vendet_Epara();
		}
	});
	
    panel1.add(button5);
    panel1.add(button6);
    panel1.add(button7);
    panel1.add(button);
    panel1.add(button2);
    panel1.add(button3);
    panel1.add(label2);
    panel1.add(textfield);
    panel1.add(button4);
    frame.add(panel1,BorderLayout.CENTER);
    frame.pack();
	
}


public void Afisho() throws IOException,NullPointerException{
	int[] piket=new int[100];
    String[] emrat=new String[100];
    int counter=0;
    JFrame frame=new JFrame("Statistikat ne rendin zbrites");
    frame.setLayout(new GridLayout(4,3));
	try {
		File file = new File("C:\\Users\\User\\Desktop\\Shoferi.txt"); 
		Scanner sc5=new Scanner(file);                     //bejme leximin e filet me te gjithe shoferet e rregjistruar 

		ShoferiFormula1 obj=new ShoferiFormula1();

		int i=0;

		while(sc5.hasNextLine()) {
			String line=sc5.nextLine();                       //leximi behet rresht per rresht
			Scanner sc2=new Scanner(line);
				String emri=sc2.next();                         //e ndajm rreshtin ne stringa per te mar vtm emrin
				piket[i]=obj.llogaritPiket(emri);               //duke thirrur metoden llogarit piket bej llogaritjen e pikeve per emrin e mare dhe e vendos ne vektorin piket
				emrat[i]=emri;                                  //emrin e mare e vendos ne vektorin emri 
				i++;
			}

		
		int temp;
		String tempEmri;
		for(int a=0;a<i-1;a++) {
			for(int j=a+1;j<i;j++) {                       //beje renditjen e vektorit 
				if(piket[a]<piket[j]) {
					temp=piket[a];
					tempEmri=emrat[a];
					piket[a]=piket[j];
					emrat[a]=emrat[j];
					piket[j]=temp;
					emrat[j]=tempEmri;
				}else {
					File file1 = new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt"); //bejme leximin e filet garat e perfunduara 
					BufferedReader br1 = new BufferedReader(new FileReader(file1));
					if(piket[a]==piket[j]) {                                         //mare parasysh rastin kur piket dalin te barabarte, ne kete raste llogarisim kush ka zene me shpesh vendin e pare 
						
						int counter1=0;                                        //variabel qe mban heret qe ka zene vendin e pare emri i pare 
						int counter2=0;                                         //variabel qe mban heret qe ka zene vendin e pare emri i dyte 
						String line2;
						while((line2=br1.readLine())!=null) {                     //leximi i filet behet rresht per rresht 
						StringTokenizer st2=new StringTokenizer(line2);
						String emri2=st2.nextToken();                         //kapim emrin e shoferit duke e ndare rreshtin me stringa
						
//                        System.out.println(emri2);
						if(emrat[a].equals(emri2)) {                      //krahasojm nqs emri ,me indexin te njejt me indeksin qe na dolen piket e barabarta, eshte i njejt me emrin e mare nga leximi i filet rresht per rresht 
							String ekipi=st2.nextToken();                         //marim emrin e ekipit duke e ndare me stringa 
							String pozicioni=st2.nextToken();                    //marim pozicionin si string 
							int poz=Integer.parseInt(pozicioni);
							if(poz==1) {                                         //krahasojm nqs ky pozicion esht 1 apo jo 
								counter1++;                                     //nqs po rrisim counterin e emrit te pare 
							}
						}
						if(emrat[j].equals(emri2)) {
							String ekipi=st2.nextToken();                         //i njeti arsyetim si me larte
							String pozicioni=st2.nextToken();
							int poz=Integer.parseInt(pozicioni);
							if(poz==1) {
								counter2++;
							}
						}
						}
						if(counter2<counter1) {                                  //krahasojme 2 vlerat e counterit 
							temp=piket[a];
							tempEmri=emrat[a];
							piket[a]=piket[j];                                     //bej renditjen 
							emrat[a]=emrat[j];
							piket[j]=temp;
							emrat[j]=tempEmri;
						}
					}
				}
			}
		}
		sc5.close();
		    
		    boolean ndodhet=false;
		    int counter1=0,counter2=0,counter3=0;
		
			File file1 = new File("C:\\Users\\User\\Desktop\\Shoferi.txt");  //lexoj filen me gjithe shoferet 
			Scanner[] vektor=new Scanner[i]; 
			for(int j=0;j<i;j++) {                                      //krijoj nje vektor me objekte te kls Scanner sepse duhet ta lexojme filen aq here sa kemi garues
				vektor[j]=new Scanner(file1);
			}

			String[] ekipet=new String[i];                            //krijoj nje vektor per te mbajtur gjithe ekipet 
			int[] numerues=new int[i];                                //krijoj nje vektor per te mbajtur numrat e shofereve se sa here kane garuar 
			int[] numerues1=new int[i];                                //krijoj nje vektor qe mban se sa here ka zene vendin e 1 secili garues 
			int[] numerues2=new int[i];                               //krijoj nje vektor qe mban se sa here ka zene vendin e 2 secili garues 
			int[] numerues3=new int[i];                               //krijoj nje vektor qe mban se sa here ka zene vendin e 3 secili garues 
			
			for(int k=0;k<i;k++) {                                     //perdorim ciklin per te lexuar filen disa here 
				
			while(vektor[k].hasNextLine()){   
				String line2=vektor[k].nextLine();                    //beje leximin rresht per rresht filen me gjithe shoferet e rregjistruar 
			    StringTokenizer st=new StringTokenizer(line2);
			    String emri=st.nextToken();                               //mar emrin nga secili rresht 
				counter=0;   
				File file2=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");
				Scanner sc=new Scanner(file2);                       //bejme leximin e filet me gjithe garat e perfunduara 
				
				if(emrat[k].equals(emri)) {                          //lexoj vektorin emrat dhe njekohesisht bej krahasimin nqs esht i barabart me nje nga emrat ne file ne menyre qe te mar ekipin e ketij emri 
					counter1=0;
					counter2=0;
					counter3=0;
					String ekipi=st.nextToken();
					ekipet[k]=ekipi;
					
					
					
					while(sc.hasNextLine()) {
						String line3=sc.nextLine();               
						StringTokenizer st2=new StringTokenizer(line3);
					while(st2.hasMoreElements()) {                              //krahasojm cdo string te filet me emrin e lojtarit 
						String s2=st2.nextToken();
						if((emrat[k].toLowerCase()).equals(s2.toLowerCase())) {
							counter++;                                          //aq here sa gjendet emri i garuesit aq here i bie ka luajtur 
							String ekipi1=st2.nextToken();
							String pozicioni=st2.nextToken();                  //ne momentin qe gjendet emri ne liste pozicionin e kthejm ne integer 
							int poz=Integer.parseInt(pozicioni);
							switch(poz) {
							case(1):
								counter1++;
							break;
							case(2):
								counter2++;
							break;
							case(3):
								counter3++;
							break;
							}
						}
						numerues[k]=counter;              //vendos numerimet ne counterat perkates 
						numerues1[k]=counter1;
						numerues2[k]=counter2;
						numerues3[k]=counter3;
				    }
			    }
					
				}
				sc.close();
			}
			
			}
			
			String[] kolona= {"Emri","Ekipi","Piket","Nr.garave","Vendi I","Vendi II","Vendi III"};
			
			
			String[] kolon=new String[] {"Kolon"};
			Object[][] titulli=new Object[][] {{"Emri:                 Ekipi:            Piket:                  Nr.garave:          Vendi I:          Vendi II:           Vendi III:"}};
			table=new JTable(titulli,kolon);              //krijoj rreshtin  e pare te tabeles 
 
			frame.add(table);                             //vendos tabelen ne frame-in e krijuar 
			
			for(int m=0;m<i;m++) {

       		Object[][] teDhenat=new Object[][]{{emrat[m],ekipet[m],piket[m],numerues[m],numerues1[m],numerues2[m],numerues3[m]}};    //vendos gjithe te dhenat ne tabele 
			table=new JTable(teDhenat,kolona); 
			
 
	
			frame.setVisible(true);
			frame.add(table);
			frame.pack();
			}

			for(int k=0;k<i;k++) {
				vektor[k].close();
			}

			sc5.close();
	}
		catch(FileNotFoundException e) {
			e.getStackTrace();
		}
		catch(IOException e1) {
			e1.getStackTrace();
		}
	catch(NullPointerException ex) {
		System.out.println(ex.getMessage());
	}
	}


public void shfaq_statistikat() {
	int[] piket=new int[100];
    String[] emrat=new String[100];
    int counter=0;
    JFrame frame=new JFrame("Statistikat ne rendin rrites");
    frame.setLayout(new GridLayout(4,3));
	try {
		File file = new File("C:\\Users\\User\\Desktop\\Shoferi.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(file));             //bejme leximin e filet me te gjithe shoferet e rregjistruar 

		ShoferiFormula1 obj=new ShoferiFormula1();
		String line;
		int i=0;
		while((line=br.readLine())!=null) {                   //leximi behet rresht per rresht 
			StringTokenizer st=new StringTokenizer(line);
			String emri=st.nextToken();                       //e ndajm rreshtin ne stringa per te mar vtm emrin  
			piket[i]=obj.llogaritPiket(emri);              //duke thirrur metoden llogarit piket bej llogaritjen e pikeve per emrin e mare dhe e vendos ne vektorin piket 
			emrat[i]=emri;                                   //emrin e mare e vendos ne vektorin emri 
			i++;
		}
		int temp;
		String tempEmri;
		for(int a=0;a<i-1;a++) {
			for(int j=a+1;j<i;j++) {                       //beje renditjen e vektorit 
				if(piket[a]>piket[j]) {
					temp=piket[a];
					tempEmri=emrat[a];
					piket[a]=piket[j];
					emrat[a]=emrat[j];
					piket[j]=temp;
					emrat[j]=tempEmri;
				}else {
					File file1 = new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt"); //bejme leximin e filet garat e perfunduara 
					BufferedReader br1 = new BufferedReader(new FileReader(file1));
					if(piket[a]==piket[j]) {                                         //mare parasysh rastin kur piket dalin te barabarte, ne kete raste llogarisim kush ka zene me shpesh vendin e pare 
						
						int counter1=0;                                        //variabel qe mban heret qe ka zene vendin e pare emri i pare 
						int counter2=0;                                         //variabel qe mban heret qe ka zene vendin e pare emri i dyte 
						String line2;
						while((line2=br1.readLine())!=null) {                     //leximi i filet behet rresht per rresht 
						StringTokenizer st2=new StringTokenizer(line2);
						String emri2=st2.nextToken();                              //kapim emrin e shoferit duke e ndare rreshtin me stringa 
						if(emrat[a].equals(emri2)) {                      //krahasojm nqs emri ,me indexin te njejt me indeksin qe na dolen piket e barabarta, eshte i njejt me emrin e mare nga leximi i filet rresht per rresht 
							String ekipi=st2.nextToken();                         //marim emrin e ekipit duke e ndare me stringa 
							String pozicioni=st2.nextToken();                    //marim pozicionin si string 
							int poz=Integer.parseInt(pozicioni);
							if(poz==1) {                                         //krahasojm nqs ky pozicion esht 1 apo jo 
								counter1++;                                     //nqs po rrisim counterin e emrit te pare 
							}
						}
						if(emrat[j].equals(emri2)) {
							String ekipi=st2.nextToken();                         //i njeti arsyetim si me larte
							String pozicioni=st2.nextToken();
							int poz=Integer.parseInt(pozicioni);
							if(poz==1) {
								counter2++;
							}
						}
						}
						if(counter2>counter1) {                                  //krahasojme 2 vlerat e counterit 
							temp=piket[a];
							tempEmri=emrat[a];
							piket[a]=piket[j];                                     //bej renditjen 
							emrat[a]=emrat[j];
							piket[j]=temp;
							emrat[j]=tempEmri;
						}
					}
					br1.close();
				}
			}
		}
		    
		    boolean ndodhet=false;
		    int counter1=0,counter2=0,counter3=0;
		
			File file1 = new File("C:\\Users\\User\\Desktop\\Shoferi.txt");  //lexoj filen me gjithe shoferet 
			Scanner[] vektor=new Scanner[i]; 
			for(int j=0;j<i;j++) {                                   //krijoj nje vektor me objekte te kls Scanner sepse na duhet ta lexojme filen aq here sa kemi garues
				vektor[j]=new Scanner(file1);
			}
			
			
			String[] ekipet=new String[i];                            //krijoj nje vektor per te mbajtur gjithe ekipet 
			int[] numerues=new int[i];                                //krijoj nje vektor per te mbajtur numrat e shofereve se sa here kane garuar 
			int[] numerues1=new int[i];                                //krijoj nje vektor qe mban se sa here ka zene vendin e 1 secili garues 
			int[] numerues2=new int[i];                               //krijoj nje vektor qe mban se sa here ka zene vendin e 2 secili garues 
			int[] numerues3=new int[i];
			
			for(int k=0;k<i;k++) {                  //perdorim ciklin per te lexuar filen disa here 
				
			while(vektor[k].hasNextLine()){   
				String line2=vektor[k].nextLine();              //beje leximin rresht per rresht filen me gjithe shoferet e rregjistruar 
			    StringTokenizer st=new StringTokenizer(line2);
			    String emri=st.nextToken();                               //mar emrin nga secili rresht 
				counter=0;         
				File file2=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");
				Scanner sc=new Scanner(file2);                       //bejme leximin e filet me gjithe garat e perfunduara 
				
				if(emrat[k].equals(emri)) {                          //lexoj vektorin emrat dhe njekohesisht bej krahasimin nqs esht i barabart me nje nga emrat ne file ne menyre qe te mar ekipin e ketij emri 
					counter1=0;
					counter2=0;
					counter3=0;
					String ekipi=st.nextToken();
					ekipet[k]=ekipi;

					
					while(sc.hasNextLine()) {
						String line3=sc.nextLine();               
						StringTokenizer st2=new StringTokenizer(line3);
					while(st2.hasMoreElements()) {                              //krahasojm cdo string te filet me emrin e lojtarit 
						String s2=st2.nextToken();
						if((emrat[k].toLowerCase()).equals(s2.toLowerCase())) {
							counter++;                                          //aq here sa gjendet emri i garuesit aq here i bie ka luajtur 
							String ekipi1=st2.nextToken();
							String pozicioni=st2.nextToken();                  //ne momentin qe gjendet emri ne liste pozicionin e kthejm ne integer 
							int poz=Integer.parseInt(pozicioni);
							switch(poz) {
							case(1):
								counter1++;
							break;
							case(2):
								counter2++;
							break;
							case(3):
								counter3++;
							break;
							}
						}
						numerues[k]=counter;              //vendos numerimet ne vektoret perkates 
						numerues1[k]=counter1;
						numerues2[k]=counter2;
						numerues3[k]=counter3;
				    }
			    }
					
				}
				sc.close();
				
				
			}
			}
			
            String[] kolona= {"Emri","Ekipi","Piket","Nr.garave","Vendi I","Vendi II","Vendi III"};
			
			
			String[] kolon=new String[] {"Kolon"};
			Object[][] titulli=new Object[][] {{"Emri:                 Ekipi:            Piket:                  Nr.garave:          Vendi I:          Vendi II:           Vendi III:"}};
			table=new JTable(titulli,kolon);              //krijoj rreshtin  e pare te tabeles 
			frame.add(table);

			
			for(int m=0;m<i;m++) {
			
       		Object[][] teDhenat=new Object[][]{{emrat[m],ekipet[m],piket[m],numerues[m],numerues1[m],numerues2[m],numerues3[m]}};    //vendos gjithe te dhenat ne tabele 
			table=new JTable(teDhenat,kolona);  
			
			frame.add(table);
			frame.pack();
            frame.setVisible(true);
			}
			
			for(int k=0;k<i;k++) {
				vektor[k].close();
			}
		br.close();
	}
		catch(FileNotFoundException e) {
			e.getStackTrace();
		}
		catch(IOException e1) {
			e1.getStackTrace();
		}
	catch(NullPointerException ex) {
		System.out.println(ex.getMessage());
	}
 }


public void vendet_Epara() {
	JFrame frame=new JFrame("Statistikat sipas vendit te pare");
	 frame.setLayout(new GridLayout(4,3));
	int[] piket=new int[100];
    String[] emrat=new String[100];
    int counter_vendiPare=0;
	try {
		File file = new File("C:\\Users\\User\\Desktop\\Shoferi.txt"); 
		BufferedReader br = new BufferedReader(new FileReader(file));             //bejme leximin e filet me te gjithe shoferet e rregjistruar 

		
		File file0 = new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt"); //bejme leximin e filet garat e perfunduara 
		
		ShoferiFormula1 obj=new ShoferiFormula1();
		String line;
		int i=0;
		while((line=br.readLine())!=null) {                        //leximi behet rresht per rresht 
			StringTokenizer st=new StringTokenizer(line);
			String emri=st.nextToken();                            //e ndajm rreshtin ne stringa per te mar vtm emrin  
			piket[i]=obj.llogaritPiket(emri);                    //duke thirrur metoden llogarit piket bej llogaritjen e pikeve per emrin e mare dhe e vendos ne vektorin piket 
			emrat[i]=emri;                                        //emrin e mare e vendos ne vektorin emri 
			i++;
		}
		
		
		String[] ekipet=new String[i];                            //krijoj nje vektor per te mbajtur gjithe ekipet 
		int[] numerues=new int[i];                                //krijoj nje vektor per te mbajtur numrat e shofereve se sa here kane garuar 
//		int[] numerues1=new int[i];                                //krijoj nje vektor qe mban se sa here ka zene vendin e 1 secili garues 
		int[] numerues2=new int[i];                               //krijoj nje vektor qe mban se sa here ka zene vendin e 2 secili garues 
		int[] numerues3=new int[i];
		
		int temp;
		String tempEmri;
		int[] counterat=new int[i];
		
		Scanner[] vektor1=new Scanner[i]; 
		for(int j=0;j<i;j++) {                                    //krijoj nje vektor me objekte te kls Scanner sepse na duhet ta lexojme filen aq here sa kemi garues
			vektor1[j]=new Scanner(file0);
		}
				
				
				for(int k=0;k<i;k++) {                           //perdorim ciklin per te lexuar filen disa here 
					
					counter_vendiPare=0;
					while(vektor1[k].hasNextLine()){   
						String line2=vektor1[k].nextLine();              //beje leximin rresht per rresht filen me gjithe shoferet e rregjistruar 

						Scanner st2=new Scanner(line2);
						while(st2.hasNext()) {
					    String emri2=st2.next();   
						if(emrat[k].equals(emri2)) {                      //krahasojm nqs emri ,me indexin k, eshte i njejt me emrin e mare nga leximi i filet rresht per rresht 
							String ekipi=st2.next();                         //marim emrin e ekipit duke e ndare me stringa 
							int pozicioni=st2.nextInt();                    //marim pozicionin si int 

							if(pozicioni==1) {                                         //krahasojm nqs ky pozicion esht 1 apo jo 
								counter_vendiPare++;                                     //nqs po rrisim counterin 
	
							}
						}
					}
						
				}
					counterat[k]=counter_vendiPare;                      //gjithe vlerat e counterit qe mban vendin e pare i vendosim ne nje vektor 
			}
				
				int tempCount;
				for(int k=0;k<i-1;k++) {
					for(int j=k+1;j<i;j++) {
						if(counterat[k]<counterat[j]) {
							tempCount=counterat[k];
						    temp=piket[k];
						    tempEmri=emrat[k];
						    counterat[k]=counterat[j];
						    piket[k]=piket[j];                                     //bej renditjen 
						    emrat[k]=emrat[j];
						    counterat[j]=tempCount;
						    piket[j]=temp;
						    emrat[j]=tempEmri;
						}
					}
				}
		
				br.close();
				for(int k=0;k<i;k++) {
					vektor1[k].close();
				}
			
          
		    int counter=0;
		    boolean ndodhet=false;
		    int counter1=0,counter2=0,counter3=0;
		
			File file1 = new File("C:\\Users\\User\\Desktop\\Shoferi.txt");  //lexoj filen me gjithe shoferet 
			Scanner[] vektor=new Scanner[i]; 
			for(int j=0;j<i;j++) {                                        //krijoj nje vektor me objekte te kls Scanner sepse na duhet ta lexojme filen aq here sa kemi garues
				vektor[j]=new Scanner(file1);
			}
			
			for(int k=0;k<i;k++) {                                            //perdorim ciklin per te lexuar filen disa here 
				
			while(vektor[k].hasNextLine()){   
				String line2=vektor[k].nextLine();                               //beje leximin rresht per rresht filen me gjithe shoferet e rregjistruar 
			    StringTokenizer st=new StringTokenizer(line2);
			    String emri=st.nextToken();                                    //mar emrin nga secili rresht 
				counter=0;         
				File file2=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");
				Scanner sc=new Scanner(file2);                                    //bejme leximin e filet me gjithe garat e perfunduara 
				
				if(emrat[k].equals(emri)) {                                   //lexoj vektorin emrat dhe njekohesisht bej krahasimin nqs esht i barabart me nje nga emrat ne file ne menyre qe te mar ekipin e ketij emri
					counter2=0;
					counter3=0;
					String ekipi=st.nextToken();
					ekipet[k]=ekipi;

					
					while(sc.hasNextLine()) {
						String line3=sc.nextLine();               
						StringTokenizer st2=new StringTokenizer(line3);
					while(st2.hasMoreElements()) {                                   //krahasojm cdo string te filet me emrin e lojtarit 
						String s2=st2.nextToken();
						if((emrat[k].toLowerCase()).equals(s2.toLowerCase())) {
							counter++;                                                  //aq here sa gjendet emri i garuesit aq here i bie ka luajtur 
							ndodhet=true;
							String ekipi1=st2.nextToken();
							String pozicioni=st2.nextToken();                        //ne momentin qe gjendet emri ne liste pozicionin e kthejm ne integer 
							int poz=Integer.parseInt(pozicioni);
							switch(poz) {
							case(2):
								counter2++;
							break;
							case(3):
								counter3++;
							break;
							}
						}
						numerues[k]=counter;              //vendos numerimet ne vektoret perkates 
//						numerues1[k]=counter1;
						numerues2[k]=counter2;
						numerues3[k]=counter3;
				    }
			    }
	
				}
				sc.close();
			}
			}

			   String[] kolona= {"Emri","Ekipi","Piket","Nr.garave","Vendi I","Vendi II","Vendi III"};
				
				
				String[] kolon=new String[] {"Kolon"};
				Object[][] titulli=new Object[][] {{"Emri:                 Ekipi:            Piket:                  Nr.garave:          Vendi I:          Vendi II:           Vendi III:"}};
				table=new JTable(titulli,kolon);              //krijoj rreshtin  e pare te tabeles 
				frame.add(table);
				
				for(int m=0;m<i;m++) {

	       		Object[][] teDhenat=new Object[][]{{emrat[m],ekipet[m],piket[m],numerues[m],counterat[m],numerues2[m],numerues3[m]}};    //vendos gjithe te dhenat ne tabele 
				table=new JTable(teDhenat,kolona); 
				
				table.setBounds(350, 350, 400, 400);
				frame.add(table);
				frame.setVisible(true);
				frame.pack();
				}
			
			for(int k=0;k<i;k++) {
				vektor[k].close();
			}
		br.close();
	}
		catch(FileNotFoundException e) {
			e.getStackTrace();
		}
		catch(IOException e1) {
			e1.getStackTrace();
		}
	catch(NullPointerException ex) {
		System.out.println(ex.getMessage());
	}
}

public void gjenero() throws IOException {
	try {
	Random random=new Random();
	Formula1MenaxhimKampionati obj=new Formula1MenaxhimKampionati();
	int nrShofereve=obj.getNrShofereve();
//	System.out.println(nrShofereve);
	int num;
	boolean ndodhet=false;
	int[] shofere=new int[100];
	int numData=random.nextInt(30)+1;        //gjeneroj nje date random 

	int numMuaji=random.nextInt(12)+1;       //gjeneroj nje muaje random

	ArrayList<Integer> list=new ArrayList<>();
	for(int i=0;i<nrShofereve;i++) {
		list.add(i+1);
	}
	Collections.shuffle(list);
	
	File file=new File("C:\\Users\\User\\Desktop\\Shoferi.txt");    
	Scanner sc=new Scanner(file);                                             //bej leximin e filet ku ndodhen gjithe shoferet e rregjistruar 
	FileWriter fw=new FileWriter("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt",true);
	BufferedWriter bw=new BufferedWriter(fw);
	PrintWriter pw=new PrintWriter(bw);                                      //perdorim PrintWriter per te shkruar ne filen ku jane gjithe garat e perfunduara 
	pw.print("\nData e perfundimit te gares:"+numData+"/"+numMuaji+"/"+"2023");
//	pw.print("\n");
	int i=0;
	while(sc.hasNextLine()) {
		String line=sc.nextLine();                                      //bejme leximin e filet rresht per rresht 
		Scanner data=new Scanner(line);                                  //bejme leximin e rreshtit fjale per fjale 
		while(data.hasNext()) {
			String emri=data.next();                                    //duke lexuar rreshtin fjale per fjal marim emrin dhe ekipin 
			String ekipi=data.next();			
			pw.print("\n"+emri +" "+ekipi+" "+list.get(i));
//			System.out.println(emri+" "+ekipi+" "+list.get(i));
//			pw.print("\n");
			
			i++;
		}
	}
	pw.close();
	bw.close();
	fw.close();
	sc.close();
	}
	catch(IOException e) {
		System.out.println(e.getMessage());
	}
}

public void gjenero_poz_fillestar() throws IOException {

	JFrame frame=new JFrame();
	frame.setLayout(new GridLayout(4,3));
	try {
	Random random=new Random();                //krijoj nje obj te kls random  
	int num;                                     //variabel per te mbajtur nr random te gjeneruar

	int[] poz_fillestar=new int[3];             //krijoj nje vektor me permas sa nr i lojatareve qe garojne ne formula 1 (kemi pranuar 3)
	int numData=random.nextInt(30)+1;            //variabel qe mban nje date te gjeneruar 

	int numMuaji=random.nextInt(12)+1;                //variabel qe mban nje muaji te gjeneruar 

    ArrayList<Integer> lista=new ArrayList<>();         //krijojm nje list per te vendosur gjithe nr me rradhe nga 1 deri te nr max i lojtareve  
    for(int i=0;i<3;i++) {
    	lista.add(i+1);
    }
	Collections.shuffle(lista);                         //perdorim metoden shuffle per ti perzier 
	System.out.println("Lista: "+lista);
	for(int i=0;i<3;i++) {
		poz_fillestar[i]=lista.get(i);                    //cdo vlere te listes e hedhim ne vektorin qe mban pozicionet fillestare random 
	}

	
	String[] emrat=new String[lista.size()];
	String[] ekipet=new String[lista.size()];
	
	
	File file=new File("C:\\Users\\User\\Desktop\\Shoferi.txt");
	Scanner sc=new Scanner(file);                                                    //bejme leximin e filet ku ndodhen te gjithe shoferet e rregjistruar per te garuar 
	FileWriter fw=new FileWriter("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt",true);
	BufferedWriter bw=new BufferedWriter(fw);      
	PrintWriter pw=new PrintWriter(bw);                                          //perdorim PrintWriter per te shkruar ne filen ku kemi rregjistruar gjithe garat e perfunduara 
	pw.print("Data e perfundimit te gares:"+numData+"/"+numMuaji+"/"+"2023");        //shkruajme daten dhe muajin e gjeneruar 
	pw.print("\n");
	int i=0;                                                   //variabel per te mbajtur indeksin e vektorit vendet
	int k=0;                                                  //varibel per te mbajtur indeksin e listes ku jane vendet random duke nisur nga 2 
	int shanse=0;                                              //variabel per te mbajtur shanset e mundshme per te fituar garen ne varesi te pozicionit fillestar te gjeneruar per secilin 
	int[] vendet=new int[3];                                  //krijojme vektorin vendet per te mbajtur vendet random 
	ArrayList<Integer> list2=new ArrayList<>();                 //krijom list2 per te vendosur gjithe vlerat e tjera pervec 1 sepse ai qe ka poz fillestar 1 automatikisht ka vendin e 1
	for(int j=1;j<3;j++) {
		list2.add(j+1);
	}
	Collections.shuffle(list2);
    int n=0; 
    String[] kolonat=new String[] {"emri","ekipi","poz.fillestar","shanset"};
    
	
	
	String[] kolon=new String[] {"Kolon"};
	Object[][] titulli=new Object[][] {{"Emri:                 Ekipi:            Poz.fillestar:     Shanset:"}};
	table=new JTable(titulli,kolon);              //krijoj rreshtin  e pare te tabeles 
	frame.add(table);
    
	while(sc.hasNextLine()) {
		String line=sc.nextLine();                       //lexojme rresht per rresht filen ku ndodhen gjithe shoferet e rregjistruar 
		Scanner data=new Scanner(line);                  //lexojme rreshtin fjale per fjale 
		while(data.hasNext()) {
			String emri=data.next();                    //Duke ditur strukturen e filet marim emrin dhe ekipin 
			emrat[i]=emri;
			String ekipi=data.next();
		    ekipet[i]=ekipi;
			if(poz_fillestar[i]==1) {                 //bejme kontrrollin nqs pozicioni fillestar i njerit prej lojtareve eshte 1 atehere e rregjistrojm ne filen garat e perfunduara si fitues
				vendet[i]=1;
			}else {                                     //ne te kundert i vendosim nje vend random nga lista qe mbante numrat e tj pervec 1 
				vendet[i]=list2.get(k);
				k++;
			}
						
			pw.print(emri +" "+ekipi+" "+vendet[i]);     //shtojme perfundimin e gares ne file
			pw.print("\n");                      
			
			 
			
			switch(poz_fillestar[i]) {                  //percaktojme shanset e fitimit te gares per secilin garues ne varesi te poz fillestar
			case(1):
				shanse=40;
				break;
			case(2):
				shanse=30;
			break;
			case(3):
				shanse=10;
			break;
			case(4):
				shanse=10;
			break;
			case(5):
				shanse=2;
			break;
			case(6):
				shanse=2;
			break;
			case(7):
				shanse=2;
			break;
			case(8):
				shanse=2;
			break;
			case(9):
				shanse=2;
			break;
			}
			
			Object[][] teDhenat=new Object[][] {{emrat[i],ekipet[i],poz_fillestar[i],shanse+"%"}};
			JTable table=new JTable(teDhenat,kolonat);
			i++;
			frame.add(table);
			frame.setVisible(true);
			frame.pack();
			
		}
	}
	  
	
	pw.close();
	bw.close();
	fw.close();
	sc.close();
	
	}
	catch(IOException e) {
		System.out.println(e.getMessage());
	}
}


public void shfaq_garat() throws FileNotFoundException,NumberFormatException {
	JFrame frame=new JFrame("Garat e perfunduara te renditura");
	frame.setLayout(new GridLayout(1,10));
	try {
	File file=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");       //beje leximin e filet ku jane rregjistruar gjithe garat e perfunduara 
	Scanner sc=new Scanner(file);
	
	 ArrayList<Integer> datat=new ArrayList<>();           //krijoj dy ArrayList nje per te mbajtur gjithe datat dhe nje per te mbajtur gjithe muajt, keto i ruajme ne lista dhe jo ne vektor sepse marim rastin qe nuk e dim se sa gara jane kryer
	    ArrayList<Integer> muajt=new ArrayList<>();
	
	while(sc.hasNextLine()) {                            //bejme leximin e filet rresht per rresht 
		String line=sc.nextLine();
		StringTokenizer st=new StringTokenizer(line);      //me ane te metodes StringTokenizer bejme leximin e rreshtit string per string 
		while(st.hasMoreTokens()) {
		String fjala=st.nextToken();               

		if(fjala.equals("Data")) {                             //per nje fjale te shkeputur nga rreshti bejme krahasimin nqs eshte kjo fjale Data apo jo 
			StringTokenizer st2=new StringTokenizer(line,":");    //nese po atehere bejme ndarjen e po ketij rreshti sipas karakterit ":" (duke ditur strukturen e filet)
		    String pjesa1=st2.nextToken();                      //kjo menyr ndarjeje rreshti ku ndodhet data e gares ndahet ne dy pjese ku pjesa e dyte ka daten muajin dhe vitin 
		    String pjesa2=st2.nextToken();         

		    StringTokenizer st3=new StringTokenizer(pjesa2,"/");  //kete pjese te dyte e ndajme perseri sa here ndodhet karakteri "/" ne menyr qe te marim daten dhe muajin 
		    String data=st3.nextToken();                       //e dim se stringa e pare do jete data dhe e dyta muaji 
		    

		    String muaji=st3.nextToken();

		    int data_int=Integer.parseInt(data);        //i kthejme ne integer te dyja dhe i shtojme ne listat perkatese 

		    datat.add(data_int);

		    int muaji_int=Integer.parseInt(muaji);
		    muajt.add(muaji_int);
		}
		}
	}
	
	
	int[] data_array=new int[datat.size()];     //krijojme dy vektor me madhesine e listave, nje vektor per daten dhe nje per muajin, i krijojme keta vektor ne menyre qe te bejme renditjen e datave qe jane kryer garat
	int[] muajt_array=new int[muajt.size()];
	
	for(int i=0;i<muajt.size();i++) {
		data_array[i]=datat.get(i);         //gjithe elementet e listave i kalojme te vektoret 
		muajt_array[i]=muajt.get(i);
	}
		
	int tempdata;
	int tempmuaji;
	
	for(int i=0;i<muajt_array.length-1;i++) {
		for(int j=i+1;j<muajt_array.length;j++) {        //bejme renditjen e vektorit duke u nisur nga muaji 
			if(muajt_array[i]>muajt_array[j]) {
				tempmuaji=muajt_array[j];
				tempdata=data_array[j];
				muajt_array[j]=muajt_array[i];
				data_array[j]=data_array[i];
				muajt_array[i]=tempmuaji;
				data_array[i]=tempdata;
			}else if(muajt_array[i]==muajt_array[j]) {      //ne rast se muajt jane te njete krahasojme datat
				if(data_array[i]>data_array[j]) {
					tempmuaji=muajt_array[j];
					tempdata=data_array[j];
					muajt_array[j]=muajt_array[i];
					data_array[j]=data_array[i];
					muajt_array[i]=tempmuaji;
					data_array[i]=tempdata;
				}
			}
		}
	}
	
	for(int i=0;i<muajt_array.length;i++) {
		muajt.set(i, muajt_array[i]);                    //te listat e krijuara me pare mbivendosim elementet e renditur te secilit vektor 
		datat.set(i, data_array[i]);
	}
	

	Scanner[] vektor=new Scanner[data_array.length];    //krijojme nje vektor me objkete te kls Scanner me madhesi sa gjatesia e njerit prej vektoreve te krijuar, kete e bejme sepse do na duhet te lexojme filen aq here sa gara jane kryer 

	for(int a=0;a<muajt_array.length;a++) {
		String koha="gares:"+data_array[a]+"/"+muajt_array[a]+"/2023";      //krijojme nje string per secilin rast kur ndryshon data 

		
		String text1="Date: "+data_array[a]+"/"+muajt_array[a]+"/2023";      //krijoj nje string tj qe te mbaj gjithashtu daten 
		area=new JTextArea(text1,5,10);                                     //kete string e vendos te textarea 
		
		frame.add(area);
		frame.setVisible(true);
	    frame.pack();
		
		
		vektor[a]=new Scanner(file);              //bejme leximin e filet nga e para sa here futemi ne cikel 
		 while(vektor[a].hasNextLine()) {          //leximi rresht per rresht 
			 String line2=vektor[a].nextLine();
			 StringTokenizer st2=new StringTokenizer(line2);
			 while(st2.hasMoreTokens()) {         //cdo rresht i lexuar string per string 
					String fjala=st2.nextToken();
					if(fjala.equals(koha)) {      //bejme kontrrollin nqs nje string i nje rreshti esht i njejte me daten e rradhes sipas ciklit dhe ne rendin rrites 
						
						System.out.println("\nData e perfundimit te gares:"+data_array[a]+"/"+muajt_array[a]+"/2023\n");
						
						while(vektor[a].hasNextLine()) {            //lexojme pjesen e mbetur te filet rresht per rresht 
							String fjalia=vektor[a].nextLine();
							if(!fjalia.startsWith("Data")) {         //bejme knotrrollin nqs ky rresht nuk nis me stringen Data
								System.out.println(fjalia+" ");      //nese plotsohet kushti shkruajme kete fjali ne dritaren tone 

								
								area.append("\n"+fjalia+" ");
								
								Scanner sc4=new Scanner(fjalia);
								while(sc4.hasNext()) {
									String emri=sc4.next();
									String ekipi=sc4.next();
									int poz=sc4.nextInt();
								}
								
							}else {                 //ne te kundert nese nuk plotesohet, pra nise me stringen Data atehere dalim nga cikli dhe nuk i lexojme me rreshtat me poshte 
								break;
							}
						}
					}
			 }
		 }
	}
	sc.close();
	for(int i=0;i<data_array.length;i++) {
		vektor[i].close();
	}
	}

      catch(FileNotFoundException e) {
	       System.out.println(e.getMessage());
      }
      
      catch(NumberFormatException ex) {
    	  System.out.println(ex.getMessage());
      }
}
	


public void teDhenat_eShoferit() throws FileNotFoundException {
	JFrame frame=new JFrame("Detajet e garave");
	frame.setLayout(new GridLayout(1,100));
	try {
	File file=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");    //bejme leximin e filet ku ndodhen gjithe garat 
	Scanner sc=new Scanner(file);
	String emri=textfield.getText();       //marim tekstin e vendosur ne hapsiren e tekstit

	
	while(sc.hasNextLine()) {               //lexojme filen rresht per rresht 
		String line=sc.nextLine();
		
		if(line.startsWith("Data")) {        //nqs fillon me data atehere kete rresht e ndajme sipas karakterit ":"
			StringTokenizer st=new StringTokenizer(line,":");
			String pjesa1=st.nextToken();
			String data=st.nextToken();       //pjesa e dyte e kesaj fjalie i bie te jete data kur eshte luajtur gara 
			while(sc.hasNextLine()) {            //vazhdojme te lexojme akoma rreshtat e tjere per te gjetur lojtarin qe kemi shkruajtur 
				String line2=sc.nextLine();
				if(line2.startsWith(emri)) {       //bejme kontrrollin nqs njeri nga rreshtat ne vazhdim fillon me emrin e lojtarit qe kemi vendosur ne textfield  
					
					String text1="Date: "+data+"\n "+line2;           //krijoj nje string tj qe mban daten dhe rreshtin qe fillon me emrin e vendosur 
					area=new JTextArea(text1,5,10);                   //e vendos kete string ne hapesiren e tekstit 
					
				    frame.add(area);                          // kete panel e vendosim ne frame 
				    frame.setVisible(true);
				    frame.pack();
					
				}
				if(line2.startsWith("Data")) {         //nqs rreshti nuk fillon me emrin e lojtarit po me fjalen Data
					StringTokenizer st1=new StringTokenizer(line2,":");   //atehere e ndajme kete fjali sipas karakterit ":" per te mare daten
					String pj1=st1.nextToken();
					String Data=st1.nextToken();
					
					String text1="Date: "+Data;         //krijoj nje string qe mban daten 
					area=new JTextArea(text1,5,10);      //e vendos kete ne hapesiren e tekstit 
					
				                          // kete panel e vendosim ne frame 
					   frame.add(area);                          // kete panel e vendosim ne frame 
					
					break;                           //dhe dalim nga cikli 
				}
			}
		}
		if(line.startsWith(emri)) {                //kur rikthehemi perseri te cikli i pare jemi direkt te rreshti mbas rreshtit qe mban daten prndj shtojme dhe kete kontrrollin tj pra nqs nis me emrin e lojtarit 
			
			String text1="\n "+line;              //krijoj nje string qe mban rreshtin qe nis me emrin e lojtarit 
			area.append(text1);
			
		}
	}
	sc.close();
	}
	catch(FileNotFoundException e) {
		System.out.println(e.getMessage());
	}
	
}

}

