/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.pdf.codec.Base64;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.InputCompletaFirmaPadesType;
import it.eng.module.foutility.beans.generated.ResponsePreparaFirmaPadesType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.signature.FirmaPadesUtil;


public class CompletaFirmaPadesCtrl extends AbstractFileController {

	public static final  Logger log = LogManager.getLogger( CompletaFirmaPadesCtrl.class );

	public String operationType;
	
	// chiave identificativa della operazione
	public static final String CompletaFirmaPadesCtrlCode=CompletaFirmaPadesCtrl.class.getName();
	
	public String outputFileName;
		
	@Override
	public boolean execute( InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey ) {
		ResponsePreparaFirmaPadesType response = new ResponsePreparaFirmaPadesType();
		boolean ret=false;
		log.debug(requestKey + " Metodo execute di CompletaFirmaPadesCtrl - input " + input + " output "+ output);
		
		if(customInput instanceof InputCompletaFirmaPadesType){
			
			File inputFile = input.getInputFile();
			log.debug("Uso il file di input per la funzione di completamento firma");
			
			log.info(requestKey + " - Elaborazione file " + inputFile);
								
			InputCompletaFirmaPadesType icfp=((InputCompletaFirmaPadesType) customInput);
			if( icfp!=null && inputFile!=null ){
				
				String nomeCampoFirma = icfp.getNomeCampoFirma();
				log.info("nomeCampoFirma: " + nomeCampoFirma);
				
				String encodedSignedHash = icfp.getEncodedSignedHash();
				//log.info("encodedSignedHash: " + encodedSignedHash);
				
				byte[] signedHash = Base64.decode( encodedSignedHash );
				try {
					String outputFileNameCompleto = inputFile.getName().replace(".", outputFileName+".");
					File outputFile = new File( inputFile.getParent() + "/" + outputFileNameCompleto );
					
					FirmaPadesUtil.completaFile(inputFile, outputFile, nomeCampoFirma, signedHash) ;
					log.debug(requestKey + " - File firmato " + outputFile );
					ret = true;
					output.setFileResult( outputFile );
					response.setVerificationStatus(VerificationStatusType.OK);
					/*} else {
						response.setVerificationStatus(VerificationStatusType.KO);
					}*/
				} catch (Exception e) {
					log.error("errore ..",e);
					if( e.getMessage().equalsIgnoreCase( FileOpMessage.TIMBRO_OP_PDF_PASSWORD )){
						output.addError(response, FileOpMessage.TIMBRO_OP_PDF_PASSWORD,VerificationStatusType.KO);
					} else {
						output.addError(response, FileOpMessage.TIMBRO_OP_ERROR,VerificationStatusType.KO, e.getMessage());
					}
				}
			}
			else {
				log.error("errore, formato file non pdf");
				output.addError(response, FileOpMessage.TIMBRO_OP_FORMAT_NOTPDF,VerificationStatusType.KO);
			}
		}

		output.addResult( CompletaFirmaPadesCtrlCode, response );
		return ret;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
