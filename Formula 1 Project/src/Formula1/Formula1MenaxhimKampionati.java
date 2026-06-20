package Formula1;
import java.util.*;
import java.nio.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Formula1MenaxhimKampionati implements MenaxhimKampionati {
private int nrShofereve=3;
private int increment=1;
private int decrement=1;


public void setNrShofereve(int nrShofereve) {
	this.nrShofereve=nrShofereve;
}
public int getNrShofereve() {
	return nrShofereve;
}
//increment
public void setIncrement(int increment) {
	this.increment=increment;
}
public void shtoNrShofereve(int nr) {
	this.nrShofereve+=nr;
}
public void incrementNrShofereve() {
	shtoNrShofereve(increment);
}
//decrement
public void setDecrement(int decrement) {
	this.decrement=decrement;
}
public void hiqNrShofereve(int nr) {
	this.nrShofereve-=nr;
}
public void decrementNrShofereve() {
	hiqNrShofereve(decrement);
}

public static void main(String[] args) throws IOException{
	try {
		boolean userExited=false;
	Formula1MenaxhimKampionati obj1=new Formula1MenaxhimKampionati();
	Statistika obj2=new Statistika();
	TabelaShofereve obj3=new TabelaShofereve();

	Scanner reader=new Scanner(System.in);
	while(!userExited) {
	System.out.println("Zgjidh nje numer nga 1-6 per te kryer nje nga veprimet:");
     menu();
	int nr=reader.nextInt();

	switch(nr) {
	case(1):
	    obj1.krijimiShoferitTeRi();
	break;
	case(2):
		obj1.fshiShofer();
	break;
	case(3):
		obj1.ndryshoShofer();
	break;
	case(4):
		obj2.statistika();
	break;
	case(5):
		obj3.Afisho();
	break;
	case(6):
		obj1.ShtimGare();
	}	
	}
	}
	catch(IOException ex) {
		System.out.println(ex.getMessage());
	}
}


public static void menu() {
	System.out.println("\n1.Krijo nje shofer te ri. \n2.Fshij nje shofer bashke me skuadren e tij. \n3.Ndrysho shoferin per nje skuader ekzistuese. \n4.Shfaq statistikat e nje shoferi. \n5.Shfaq tabelen e shofereve. \n6.Shto nje gare te perfunduar. ");
}

public void krijimiShoferitTeRi() throws IOException,NoSuchElementException{      //metoda e pare qe ben shtimin e nje shoferi te ri ne liste
	boolean ndodhet=false;
	Scanner reader=new Scanner(System.in);
	ShoferiFormula1 shoferRi=new ShoferiFormula1();
 System.out.println("Vendos emrin e shoferit: ");
    String emri=reader.next();                        //shkruan te dhenat e shoferit te ri
    System.out.println("Vendos ekipin: ");  
    String ekipi=reader.next();                       //shkruan te dhenat e shoferit te ri          
    shoferRi.setEmriShoferit(emri);
    shoferRi.setEkipi(ekipi);
    try {
    	File file=new File("C:\\Users\\User\\Desktop\\Shoferi.txt");
    	Scanner sc=new Scanner(file);                       //bej leximin e file-it te shofereve
    	while(sc.hasNextLine()) {
    		String line=sc.nextLine();
    		StringTokenizer st=new StringTokenizer(line);
    		while(st.hasMoreElements()) {
    			String s1=shoferRi.getEkipi();                       //e ruaj ne variabel ekipin qe do shtoj  me vete qe te kushti ta kthej direkt me shkronja te vgla te jete me i shkurter kodi 
    			String s2=st.nextToken();                            //e ruaj si variabel me vete secilen string te rreshtit ku jemi
    			if((s1.toLowerCase()).equals(s2.toLowerCase())) {     //bejme krahasimin e emrit te ekipit te vene nga ne me cdo string qe ndodhet ne file duke qene se emri i ekipit eshte unik per secilin 
    				ndodhet=true;                                   //perdorim variablen booleane ndodhet per te treguar nqs ndodhet ky ekip qe ne po rregjistrojm 
    				break;                                          //ne momentin qe ndodhet behet true dalim nga cikli 
    			}
    		}
    	}
    	sc.close();
    }catch(IOException ex){
    	System.out.println(ex.getMessage());
    }
    catch(NoSuchElementException ex) {
    	System.out.println(ex.getMessage());
    }
    if(ndodhet) {
    	System.out.println("Kjo skuader ndodhet. Shoferi duhet te ndodhet ne nje skuader unike!");
    }
    else {
    try {
    	FileWriter fw=new FileWriter("C:\\Users\\User\\Desktop\\Shoferi.txt",true);
    	BufferedWriter bw=new BufferedWriter(fw);
    	PrintWriter pw=new PrintWriter(bw); 
    	pw.println(shoferRi.getEmriShoferit()+" "+shoferRi.getEkipi());               //shtojm emrin ne file(e bashkangjisim ne fund te filet)
    	pw.close();
    	bw.close();
    	fw.close();
    }
    catch(IOException ex) {
    	System.out.println(ex.getMessage());
    }
    System.out.println("Shoferi i ri u shtua!");
    incrementNrShofereve();
    }
}


public void fshiShofer() throws IOException,FileNotFoundException,NullPointerException {  //metoda e dyte qe ben fshirjen e nje shoferi bashk me ekipin 
	try {
	Scanner reader=new Scanner(System.in);
	System.out.println("Vendos emrin e shoferit qe do te fshish: ");
	String emri=reader.next();
	System.out.println("Vendos emrin e ekipit qe do te fshish: ");      //lexojm te emrin e shoferi dhe ekipin qe do heqim nga lista
	String ekipi=reader.next();	
		
		File file=new File("C:\\Users\\User\\Desktop\\Shoferi.txt");
		FileReader fr=new FileReader(file);
		File newfile=new File("C:\\Users\\User\\Desktop\\Shoferet.txt");      //krijojm nje file shoferet per te ruajtur te gjith shoferet pervec ati qe do heqim
		FileWriter fw=new FileWriter(newfile);
		
		String line=emri+" "+ekipi;               //kjo string permban emrin dhe ekipin e shoferit qe do heqim, kete string do e perdorim per ta krahasuar me secilin rresht te filet me gjithe shoferet
		
		BufferedReader br=new BufferedReader(fr);
		BufferedWriter bw=new BufferedWriter(fw);
		String line2=br.readLine();               //lexojm filen me gjith shoferet rresht per rresht 
		while(line2!=null) {
			boolean ndodhet=false;                 //perdorim nje boolean per te treguar se kur gjejm shoferin qe do hiqet 
			if((line2.trim()).equals(line)) {       //bejme krahasimin me cdo rresht te filet ku ndodhen gjithe shoferet 
				ndodhet=true;    
				System.out.println("Shoferi u fshi!");
			}
			if(!ndodhet) {
			    bw.write(line2);                //shkruajm ne filen e ri te gjithe shoferet qe nuk jane njesoj me te dhenat e futura nga perdoruesi
				bw.write("\n");
			}
			    line2=br.readLine();
			
		}	
	    br.close();
	    fr.close();
	    bw.close();
	    fw.close();
	    boolean fshijFile= Files.deleteIfExists(Paths.get("C:\\Users\\User\\Desktop\\Shoferi.txt"));  //fshijm filen qe mban gjithe shoferet
//	    if (fshijFile) {
//	        System.out.println("File u fshi");
//	      } 
//	      else {
//	        System.out.println("File nuk u fshi");
//	      }
	    boolean ndrysho_emrin=newfile.renameTo(file);                   //riemertojm filen e update-uar
//	    if(ndrysho_emrin) {                                
////	    	System.out.println("emri u ndryshua");
//	    }
	}
	catch(IOException ex){
		System.out.println(ex.getMessage());
	}
	catch(NullPointerException ex) {
		System.out.println(ex.getMessage());
	}
	
	System.out.println("Nese mesazhi konfirmues nuk shfaqet ju lutem fusni sakte te dhenat!");
	decrementNrShofereve();
}

public void ndryshoShofer() throws IOException,NoSuchElementException {    //metoda e trete qe ndryshon shoferin 
	try {
	Scanner reader=new Scanner(System.in);
	System.out.println("Vendos emrin e shoferit qe do te ndryshosh: ");
	String emri=reader.next();
	System.out.println("Vendos emrin e ekipit qe do i ndryshohet shoferi: ");
	String ekipi=reader.next();                                                        //vendosim te dhenat e shoferit qe do ndryshohet 
	System.out.println("Vendos emrin e shoferit te ri: ");           
	String shoferiRi=reader.next();                                       //vendosim emrin e shoferit te ri 
	
	
	File file=new File("C:\\Users\\User\\Desktop\\Shoferi.txt");
	File newfile=new File("C:\\Users\\User\\Desktop\\Shoferet.txt");
	String line=emri+" "+ekipi;                                   //rreshti me te dehnat e shoferit qe do i ndryshohet emri 
	BufferedReader br=new BufferedReader(new FileReader(file));            //lexojm filen me gjithe shoferet 
	BufferedWriter bw=new BufferedWriter(new FileWriter(newfile));         //Hapim nje buffer per te shrkuar ne nje file te ri te gjithe emrat me emrin e shoferit te ri 

	
	String line1=br.readLine();
	while(line1!=null) {
		boolean ugjet=false;

		if((line1).equals(line)) {
			ugjet=true;
			bw.write(shoferiRi+" "+ekipi);          //rreshti qe eshte i njete me rreshtin e shoferit qe do ndryshohet shkruhet te file i ri me emrin e shoferit te ndryshuar  
			bw.write("\n");
		}
		if(!ugjet) {
			bw.write(line1);
			bw.write("\n");
		}
		line1=br.readLine();
	}
	br.close();
	bw.close();
	boolean fshijFile= Files.deleteIfExists(Paths.get("C:\\Users\\User\\Desktop\\Shoferi.txt"));   //fshijm filen me gjithe shoferet ku emri i shoferit nuk eshte ndryshuar
	boolean ndrysho_emrin=newfile.renameTo(file);            //riemertojme filen e ri
	}
	catch(IOException ex) {
		System.out.println(ex.getMessage());
	}
	catch(NoSuchElementException ex) {
		System.out.println(ex.getMessage());
	}
	System.out.println("Shoferi u ndryshua!");
}

public void ShtimGare() throws IOException,InputMismatchException {        //metoda e katert per shtimin e nje gare 
	
	try {
		Scanner in = new Scanner(System.in);
		FileWriter file = new FileWriter("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt",true);
		BufferedWriter bw = new BufferedWriter(file);
		PrintWriter pw = new PrintWriter(bw);
		
		File file2=new File("C:\\Users\\User\\Desktop\\Shoferi.txt");
		
		int data,muaji,viti;
		
		System.out.println("Data :");
		data=in.nextInt();
		System.out.println("Muaji :");
		muaji=in.nextInt();
		System.out.println("Viti :");
		viti=in.nextInt();
		
		pw.print("\nData e perfundimit te gares:" + data + "/" + muaji + "/" + viti);
		
		
		String[] emri = new String[nrShofereve];
		String[] ekipi = new String[nrShofereve];
		int[] poz = new int[nrShofereve];
		
		Scanner[] sc=new Scanner[nrShofereve];
		for(int i=0;i<nrShofereve;i++) {          //e lexojme file-in aq here sa kemi numrin e shofereve sepse na duhet te bejme kontrrollin e cdo emri qe fusim nese eshte i njejte ose jo me emrat qe kemi tek file-i Shoferi
			sc[i]=new Scanner(file2);
		}
		
		boolean ndodhet=false;
		
//		System.out.println();

		for(int i=0;i<nrShofereve;i++) {
			ndodhet=false;
			System.out.println("Emri, Ekipi, Pozicioni (" + (i+1) + ")");
			emri[i]=in.next();
			while(sc[i].hasNextLine()) {
				String line=sc[i].nextLine();
				Scanner fjala=new Scanner(line);
				while(fjala.hasNext()) {
					String emri1=fjala.next();
					if(emri[i].equals(emri1)) {
						ndodhet=true;
					}
				}
			}
			
			if(ndodhet) {
			ekipi[i]=in.next();
			poz[i]=in.nextInt();
			
			if(poz[i]<1 || poz[i]>nrShofereve) {
				System.out.println("Rivendos pozicionin :"); //Ne garen e perfunduar bejn pjes vtm garuesit me pozicione 1-nr shofereve 
				poz[i]=in.nextInt();
			}
			if(i>0) {                                      //veme kte kushte sepse pozicioni ne indeksin i nuk kontrrollohet sepse eshte i pari
				for(int j=0;j<i;j++) {                    //per te kontrrolluar qe kjo renditje eshte vendosur vtm 1 here 
			if(poz[i]==poz[j]) {
				System.out.println("Rivendos pozicionin :"); //vtm njeri mund te jete ne nje pozicion (psh jo dy garues ne vend te 3)
				poz[i]=in.nextInt();
			}
				}
		  }
			pw.print("\n" + emri[i] +" "+ekipi[i] + " " + poz[i]);
		} else {
			System.out.println("Ky shofere nuk eshte i rregjistruar te lista e shofereve!\nRivendosni perseri te dhenat e sakta!");
			System.out.println("Emri, Ekipi, Pozicioni (" + (i+1) + ")");
			emri[i]=in.next();
			ekipi[i]=in.next();
			poz[i]=in.nextInt();
			pw.print("\n" + emri[i] +" "+ekipi[i] + " " + poz[i]);
		}
		}
		
		
		System.out.println("Gara u rregjistrua !");
		
		bw.close();
		pw.close();
		
	}
	catch(IOException e) {
		e.getStackTrace();
	}
	catch(InputMismatchException e) {
		System.out.println(e.getMessage());
	}
}
}
