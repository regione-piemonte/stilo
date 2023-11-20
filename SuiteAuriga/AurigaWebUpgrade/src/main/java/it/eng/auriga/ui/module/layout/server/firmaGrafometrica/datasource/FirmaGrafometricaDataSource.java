/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.firmaGrafometrica.datasource.bean.FirmaGrafometricaBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;

@Datasource(id = "FirmaGrafometricaDataSource")
public class FirmaGrafometricaDataSource extends AbstractServiceDataSource<FileDaFirmareBean, FirmaGrafometricaBean>{	
	
	private static Logger mLogger = Logger.getLogger(FirmaGrafometricaDataSource.class);
		
	public FirmaGrafometricaBean call(FileDaFirmareBean input) throws Exception{
		
		FirmaGrafometricaBean output = new FirmaGrafometricaBean();
		
		// recupero il file dallo storage e ne ricavo lo stream
		
		StorageService storageService = StorageImplementation.getStorage();
		// attenzione che il metodo getRealFile restituisce direttamente il file e non una copia, attenzione quindi ad eventuali modifiche al file
		File fileDaFirmare = storageService.getRealFile(input.getUri());
		byte[] bytesFileDaFirmare = getFileBytes( fileDaFirmare );
		output.setBytesFileDaFirmare(bytesFileDaFirmare);
		
		// base 64 del file
		output.setBase64EncodeFile(Base64.encodeBase64String(bytesFileDaFirmare));

		// nome del file da firmare
		output.setNomeFileDaFirmare(input.getNomeFile());
		
		// creo label associata al file da firmare
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");
		String label = "Raccolta dati anagrafici del " + dateFormat.format(new Date());
		
		output.setBase64EncodeLabel(Base64.encodeBase64String(label.getBytes()));
		
		return output;
    }
	
	protected static byte[] getFileBytes( File file  ){
		byte[] bFile = new byte[(int) file.length()];

		try {
			//convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		}catch(Exception e){
			mLogger.warn(e);
		}
		return bFile;
	}

}
