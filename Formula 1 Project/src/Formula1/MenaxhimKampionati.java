package Formula1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public interface MenaxhimKampionati {
	public void krijimiShoferitTeRi() throws IOException,NoSuchElementException;
	public void fshiShofer() throws IOException,FileNotFoundException,NullPointerException;
	public void ndryshoShofer() throws IOException,NoSuchElementException;
	public void ShtimGare() throws IOException;
}
