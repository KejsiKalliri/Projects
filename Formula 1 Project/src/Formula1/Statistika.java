package Formula1;
import java.util.*;
import java.io.*;

public class Statistika extends ShoferiFormula1{
public void statistika() throws IOException{
	try {
	  Scanner reader=new Scanner(System.in);
	  System.out.println("Vendos emrin e shoferit: ");
	  String emri=reader.next();
	  super.NrGarave(emri);
//	  super.llogaritPiket(emri);
	  System.out.println("Piket e grumbulluara: "+super.llogaritPiket(emri));
	  super.VendiPareDyteTret(emri);
	}
	catch(IOException ex) {
		System.out.println(ex.getMessage());
	}
}
}
