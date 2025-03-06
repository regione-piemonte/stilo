package it.eng.hybrid.module.stampaEtichette.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

/**
 * Classe bean che contiene i parametri di formato dell'etichetta
 * i valori di default sono quelli testati nel laboratorio di Bologna
 * con un'etichetta di circa 10 cm di larghezza.
 * @author Francesco Fioravanti
 *
 */
public class StreamGobbler extends Thread {
	
	public final static Logger logger = Logger.getLogger( StreamGobbler.class );
	
	InputStream is;
	String type;
	OutputStream os;

	public StreamGobbler(InputStream is, String type) {
		this(is, type, null);
	}

	public StreamGobbler(InputStream is, String type, OutputStream redirect) {
		this.is = is;
		this.type = type;
		this.os = redirect;
	}

	public void run() {
		try {
			PrintWriter pw = null;
			if (os != null)
				pw = new PrintWriter(os);

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (pw != null)
					pw.println(line);
				logger.info(type + ">" + line);
			}
			if (pw != null)
				pw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
