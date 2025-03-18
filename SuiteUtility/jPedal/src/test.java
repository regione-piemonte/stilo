/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.net.MalformedURLException;
import java.net.URL;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File lFile = new File("C:\\fileop.wsdl");
			URL u = lFile.toURI().toURL();
			URL url = new URL("file://C:\\fileop.wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
