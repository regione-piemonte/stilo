package it.eng.applet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdobeUtil {

	private static String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * Mi dice se il sistema operativo è windows
	 * @return
	 */
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	/**
	 * Verifica se tra i processi in esecuzione è presente Adobe con l'exe passato
	 * come parametro
	 * @param runningExe il nome dell'immagine exe
	 * @return
	 */
	public static boolean isRunning(String runningExe) {
		boolean result = false;
		try {
			if (isWindows()) {
				System.out.println("SO = Windows...");
				String line = "";
				Process p = Runtime.getRuntime().exec(
						"tasklist /FI \"IMAGENAME eq "+runningExe+"\" /FO list");
				int i = 0;
				BufferedReader in = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				while ((line = in.readLine()) != null) {
					System.out.println((i++) + ": " + line);
					String[] KeyValue = line.split("\\:");
					if (KeyValue[0].trim().equalsIgnoreCase("PID")) {
						result = true;
					}
				}
				in.close();
			}
		}  catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Interrompe il processo adobe
	 * @param runningExe
	 */
	public static void cleanAdobe(String runningExe) {
		try {
			if (isWindows()) {
				System.out.println("SO = Windows...");
				String line = "";
				Process p = Runtime.getRuntime().exec(
						"tasklist /FI \"IMAGENAME eq "+runningExe+"\" /FO CSV");
				int i = 0;
				BufferedReader in = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				while ((line = in.readLine()) != null) {
					System.out.println((i++) + ": " + line);
					String[] KeyValue = line.split("\\,");
					if (KeyValue[0].trim().equalsIgnoreCase("\""+runningExe+"\"")) {
						Runtime.getRuntime().exec(
								"taskkill /F /PID " + KeyValue[1].trim().replaceAll("\"", ""));
					}
				}
				in.close();
			}
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
}
