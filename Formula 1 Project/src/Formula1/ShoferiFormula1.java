package Formula1;
import java.io.*;
import java.util.*;

import javax.swing.JLabel;

public class ShoferiFormula1 {
private String emriShoferit;
private String ekipi;


public ShoferiFormula1() {
         
}
public ShoferiFormula1(String emriShoferi,String ekipi) {
	this.emriShoferit=emriShoferit;
	this.ekipi=ekipi;
}
public void setEmriShoferit(String emriShoferit) {
	this.emriShoferit=emriShoferit;
}
public String getEmriShoferit() {
	return this.emriShoferit;
}

public void setEkipi(String ekipi) {
	this.ekipi=ekipi;
}
public String getEkipi() {
	return this.ekipi;
}

public int llogaritPiket(String emri) throws IOException{
	int sh=0;
	int total=0;
	try {
	Scanner reader=new Scanner(System.in);
	File file=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");
	Scanner sc=new Scanner(file);                          //lexojm filen me garat qe jane kryer ku jane te rregj te gjith konkurentet me ekipet dhe pozicionet perkatese
	this.setEmriShoferit(emri);               
	boolean ndodhet=false;                                //variabel qe na tregon nqs nje emer qe ne vendosim gjendet ne file ose jo nqs nuk gjendet afishohet mszh qe ska garuar asnjeher
	while(sc.hasNextLine()) {
		String line=sc.nextLine();                         //lexojm filen rresht per rresht 
		StringTokenizer st=new StringTokenizer(line);       //e ndajm rreshtin ne stringa 
		while(st.hasMoreTokens()) {
			String s1=this.getEmriShoferit();
			String s2=st.nextToken();
			if((s1.toLowerCase()).equals(s2.toLowerCase())) { 
				sh=0;                                     //krahasojm cdo string nqs eshte i barabart me emrin e vendosur nga ne
				ndodhet=true;
				String ekipi=st.nextToken();
				String pozicioni=st.nextToken();                  //ne momentin qe gjendet emri i vendosur nga ne ne liste pozicionin e kthejm ne integer 
				int poz=Integer.parseInt(pozicioni);
				switch(poz) {
				case(1):                    //llogaris piket ne varesi te pozicionit 
					sh+=25;
				break;
				case(2):
					sh+=18;
				break;
				case(3):
					sh+=15;
				break;
				case(4):
					sh+=12;
				break;
				case(5):
					sh+=10;
				break;
				case(6):
					sh+=8;
				break;
				case(7):
					sh+=6;
				break;
				case(8):
					sh+=4;
				break;
				case(9):
					sh+=2;
				break;
				case(10):
					sh+=1;
				break;
				}
				total+=sh;        //mbledh gjithe piket e seciles gare 
			}
		}
	}
	sc.close();
//	if(!ndodhet) {
//		System.out.println("Ky shofer nuk ka mare pjese ne asnje gare per kete sezon!");
//	}else {
//		return total;
//	}
	}
	catch(IOException ex) {
		System.out.println(ex.getMessage());
	}
	return total;
}

public void NrGarave(String emri) throws IOException{
	try {
	int counterNdodhet=0;                                   //variabel per te mbajtur nr e hereve qe haset emri i lojtarit
    Scanner reader=new Scanner(System.in);
    this.setEmriShoferit(emri);
	File file=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");
	Scanner sc=new Scanner(file);             //bejme leximin e filet me gjithe garat e perfunduara 
	while(sc.hasNextLine()) {
		String line=sc.nextLine();               
		StringTokenizer st=new StringTokenizer(line);
		while(st.hasMoreElements()) {                    //krahasojm cdo string te filet me emrin e lojtarit 
			String s1=this.getEmriShoferit();
			String s2=st.nextToken();
			if((s1.toLowerCase()).equals(s2.toLowerCase())) {
				counterNdodhet++;
			}
		}
	}
	sc.close();
	System.out.println("Numri i garave qe ka kryer: "+counterNdodhet+"\n");
	}
	catch(IOException ex) {
		System.out.println(ex.getMessage());
	}
}

public void VendiPareDyteTret(String emri) throws IOException{
	try {
	File file=new File("C:\\Users\\User\\Desktop\\Garat e perfunduara.txt");
	Scanner sc=new Scanner(file);            //lexojm filen me gjithe garat e perfunduara 
	Scanner reader=new Scanner(System.in);
	this.setEmriShoferit(emri);
	boolean ndodhet=false;                  //per te treguar nqs ky emer ndodhet ne listen e garave ose jo 
	int nrVendiPare=0,nrVendiDyte=0,nrVendiTrete=0;              //3 countera per te mbajtur vl se sa here ka qen ne vendine 1 2 dhe 3 
	while(sc.hasNextLine()) {
		String line=sc.nextLine();               //lexojm rresht per rresht filen 
		StringTokenizer st=new StringTokenizer(line);
		while(st.hasMoreTokens()) {
			String s1=this.getEmriShoferit();
			String s2=st.nextToken();                    //e ndajm rreshtin ne stringa ne menyr qe secilen string ta krahasojm me emrin e vendosur 
			if((s1.toLowerCase()).equals(s2.toLowerCase())) {       //krahasojm cdo string nqs eshte i barabart me emrin e vendosur nga ne
				ndodhet=true;
				String ekipi=st.nextToken();
				String pozicioni=st.nextToken();                  //ne momentin qe gjendet emri i vendosur nga ne ne liste pozicionin e kthejm ne integer 
				int poz=Integer.parseInt(pozicioni);
				switch(poz) {
				case(1):
					nrVendiPare++;
				break;
				case(2):
					nrVendiDyte++;
				break;
				case(3):
					nrVendiTrete++;
				break;
				}
	         }
	       }
	     }
	sc.close();
	if(ndodhet) {
		System.out.println("Vendi I: "+nrVendiPare+"\nVendi II "+nrVendiDyte+"\nVendi III: "+nrVendiTrete);
	}else {
		System.out.println("Ky shofer nuk ka garuar asnjehere per kete sezon!");
	}
	   }
	catch(IOException ex) {
		System.out.println(ex.getMessage());
	}
}
}