/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import it.eng.auriga.repository2.jaxws.webservices.common.AurigaWebServiceConfigurer;
import it.eng.auriga.repository2.jaxws.webservices.common.JAXWSAbstractAurigaService;
import it.eng.document.storage.DocumentStorage;
import it.eng.spring.utility.SpringAppContext;

public class WSFileUtils {

	public File saveInputStreamToStorageTmp(BigDecimal idDominio, InputStream inputStreamIn) throws Exception {

		File fileOut = null;
		try {
			// Salvo inputStream in un file temp
			// File fileTemp = saveInputStreamToFile(inputStreamIn);

			// Salvo il file nello storage
			String uriVer = DocumentStorage.storeInput(inputStreamIn, idDominio, null); // store(fileTemp, idDominio);

			// Cancello il file temp
			// fileTemp.delete();

			// Estraggo il file
			fileOut = (DocumentStorage.extract(uriVer, idDominio));
		}

		catch (Exception e1) {
			throw new Exception(e1.getMessage());
		}

		return fileOut;
	}
	
	public String saveInputStreamToStorageTmpAndGetUri(BigDecimal idDominio, InputStream inputStreamIn) throws Exception {

		String uriVer = null;
		try {
			uriVer = DocumentStorage.storeInput(inputStreamIn, idDominio, null); // store(fileTemp, idDominio);
		} catch (Exception e1) {
			throw new Exception(e1.getMessage());
		}
		return uriVer;
	}

	public File saveInputStreamToFile(InputStream fileIn) throws Exception {
		FileOutputStream fost = null;

		try {
			// Leggo la dir temporanea
			AurigaWebServiceConfigurer awsc = (AurigaWebServiceConfigurer) SpringAppContext.getContext()
					.getBean(JAXWSAbstractAurigaService._SPRING_BEAN_WSCONFIGUER);
			String tmpWorkDirectory = awsc.getTempPath();
			File tmpWorkDirectoryFile = new File(tmpWorkDirectory);
			if (!tmpWorkDirectoryFile.exists()) {
				tmpWorkDirectoryFile.mkdirs();
			}

			byte bufferAU[] = new byte[1024];
			int count = 0;

			// Istanzio il file di output
			File fileOut = new File(tmpWorkDirectory + "attach_" + (new Date()).getTime() + "_" + (int) (Math.random() * Integer.MAX_VALUE));

			// istanzio un output stream
			fost = new FileOutputStream(fileOut);

			// replico il file
			while ((count = fileIn.read(bufferAU)) > 0) {
				fost.write(bufferAU, 0, count);
			}

			return fileOut;

		} catch (Exception e1) {
			throw new Exception(e1.getMessage());
		} finally {
			// Chiudo il file di input
			try {
				fileIn.close();
			} catch (Exception e2) {
			}
			// Chiudo lo stream di output
			try {
				fost.close();
			} catch (Exception e2) {
			}
		}
	}

	public File extractFile(BigDecimal idDominio, String uri) throws Exception {

		File fileOut;
		try {
			fileOut = DocumentStorage.extract(uri, idDominio);
		} catch (Exception e1) {
			throw new Exception(e1.getMessage());
		}

		return fileOut;
	}
	
	public File extract(BigDecimal idDominio, String uri) throws Exception {

		File fileOut;
		try {
			fileOut = DocumentStorage.extract(uri, idDominio);
		} catch (Exception e1) {
			throw new Exception(e1.getMessage());
		}

		return fileOut;
	}

}
