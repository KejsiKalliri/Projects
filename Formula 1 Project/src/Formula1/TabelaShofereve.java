package Formula1;
import java.io.*;
import java.util.*;


public class TabelaShofereve extends ShoferiFormula1{
		
		public void Afisho() throws IOException,NullPointerException{
			int[] piket=new int[100];
		    String[] emrat=new String[100];
			try {
				File file = new File("C:\\Users\\User\\Desktop\\Shoferi.txt"); 
				BufferedReader br = new BufferedReader(new FileReader(file));         //bejme leximin e filet me te gjithe shoferet e rregjistruar 

				
				String line;
				int i=0;
				while((line=br.readLine())!=null) {        //leximi behet rresht per rresht 
//					System.out.println(line);
					StringTokenizer st=new StringTokenizer(line);
					String emri=st.nextToken();            //e ndajm rreshtin ne stringa per te mar vtm emrin 
					piket[i]=super.llogaritPiket(emri);     //duke thirrur metoden llogarit piket bej llogaritjen e pikeve per emrin e mare dhe e vendos ne vektorin piket 
					emrat[i]=emri;                  //emrin e mare e vendos ne vektorin emri 
					i++;
				}
				
				int temp;
				String tempEmri;
				for(int a=0;a<i-1;a++) {
					for(int j=a+1;j<i;j++) {            //beje renditjen e vektorit 
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
							if(piket[a]==piket[j]) {            //mare parasysh rastin kur piket dalin te barabarte, ne kete raste llogarisim kush ka zene me shpesh vendin e pare 
								
								int counter1=0;               //variabel qe mban heret qe ka zene vendin e pare emri i pare 
								int counter2=0;                 //variabel qe mban heret qe ka zene vendin e pare emri i dyte 
								String line2;
								while((line2=br1.readLine())!=null) {      //leximi i filet behet rresht per rresht 
								StringTokenizer st2=new StringTokenizer(line2);
								String emri2=st2.nextToken();               //kapim emrin e shoferit duke e ndare rreshtin me stringa 
								if(emrat[a].equals(emri2)) {          //krahasojm nqs emri ,me indexin te njejt me indeksin qe na dolen piket e barabarta, eshte i njejt me emrin e mare nga leximi i filet rresht per rresht 
									String ekipi=st2.nextToken();             //marim emrin e ekipit duke e ndare me stringa 
									String pozicioni=st2.nextToken();        //marim pozicionin si string 
									int poz=Integer.parseInt(pozicioni);
									if(poz==1) {             //krahasojm nqs ky pozicion esht 1 apo jo 
										counter1++;           //nqs po rrisim counterin e emrit te pare 
									}
								}
								if(emrat[j].equals(emri2)) {
									String ekipi=st2.nextToken();         //i njeti arsyetim si me larte
									String pozicioni=st2.nextToken();
									int poz=Integer.parseInt(pozicioni);
									if(poz==1) {
										counter2++;
									}
								}
								}
								if(counter2<counter1) {       //krahasojme 2 vlerat e counterit 
									temp=piket[a];
									tempEmri=emrat[a];
									piket[a]=piket[j];        //bej renditjen 
									emrat[a]=emrat[j];
									piket[j]=temp;
									emrat[j]=tempEmri;
								}
							}
							br1.close();
						}
					}
				}
					File file1 = new File("C:\\Users\\User\\Desktop\\Shoferi.txt"); //lexoj filen me gjithe shoferet 
					BufferedReader br1 = new BufferedReader(new FileReader(file1));
					String line2;
					while((line2=br1.readLine())!=null){          //beje leximin rresht per rresht 
					StringTokenizer st=new StringTokenizer(line2);
					String emri=st.nextToken();                //mar emrin nga secili rresht 
					for(int k=0;k<i;k++) {
						if(emrat[k].equals(emri)) {       //lexoj vektorin emrat dhe njekohesisht bej krahasimin nqs esht i barabart me nje nga emrat ne file ne menyre qe te mar ekipin e ketij emri 
							String ekipi=st.nextToken();
							System.out.println("Emri i shoferit: "+emrat[k]+"\nEkipi: "+ekipi+"\npiket: "+piket[k]);
							super.VendiPareDyteTret(emrat[k]);    
							super.NrGarave(emrat[k]);            
						}
					}
					}
				br1.close();
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
		}