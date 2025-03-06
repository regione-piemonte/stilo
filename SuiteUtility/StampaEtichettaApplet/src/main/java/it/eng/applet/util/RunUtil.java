package it.eng.applet.util;

import it.eng.applet.gobbler.StreamGobbler;

public class RunUtil {
	/**
	 * esegue la riga di comando passata tramite il parametro. viene usato il
	 * metodo Runtime.exec per compatibilità con le vecchie versioni del
	 * framework
	 * 
	 * @param commandline
	 *            linea di comando da eseguire
	 * @return
	 */
	public static boolean runCommandString(String commandline) throws Exception {
		
			boolean retVal = false;

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commandline);

			// cattura lo stream di errore del processo invocato e lo stampa sulla console
			StreamGobbler errorGobbler = new StreamGobbler(
					process.getErrorStream(), "ERROR");

			// cattura lo stream di output del processo invocato e lo stampa sulla console
			StreamGobbler outputGobbler = new StreamGobbler(process
					.getInputStream(), "OUTPUT");

			// avvia la cattura
			errorGobbler.start();
			outputGobbler.start();

			// attende il completamento del processo e legge il valore di ritorno per eventuali errori
			int exitVal = process.waitFor();
			System.out.println("ExitValue: " + exitVal);

			retVal = true;

			return (retVal);
		}
}
