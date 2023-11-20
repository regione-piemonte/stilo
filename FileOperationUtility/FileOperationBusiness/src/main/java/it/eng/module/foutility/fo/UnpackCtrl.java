/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.SbustamentoBean;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.InputUnpackType;
import it.eng.module.foutility.beans.generated.ResponseUnpackType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.InputFileUtil;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.CAdESSigner;
import it.eng.utility.cryptosigner.data.P7MSigner;
import it.eng.utility.cryptosigner.data.PdfSigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.XMLSigner;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.exception.NoSignerException;
/**
 * operazione di sbustamento 
 * @author Russo
 *
 */
public class UnpackCtrl extends AbstractFileController {
	
	public static final  Logger log = LogManager.getLogger( UnpackCtrl.class );
	
	public String operationType;
	// chiave identificativa della operazione
	public static final String UnpackCtrlCode=UnpackCtrl.class.getName();
	
	//file estratto dalle buste (File)
	public static final String EXTRACTED_FILE = "extractedFile";
	
	// indica se il file è firmato (boolean)
	public static final String IS_ROOT_SIGNED = "isRootSigned";
	public static final String ENVELOPE_FORMAT = "envelopeFormat";
	public static final String ENVELOPE_FORMAT_ESTERNA = "envelopeFormatEsterna";
	public static final String EXTRACTED_FILE_NAME = "extractedFileName";
	
	//file temporaneo estratto dalla busta
	//private File extracttempfile;
	//private AbstractSigner signer = null;
	//private String nomeFileSbustato = "";
	//private SignerType formatoBusta;
	//private SignerType formatoBustaEsterna;
	//private Boolean fileSigned = null;
	
	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output, String requestKey) {
		ResponseUnpackType response= new ResponseUnpackType();
		boolean ret=false;
		File extractedFile = null;
		try {
			 
			File documentFile=input.getInputFile();
			log.debug(requestKey + " - Metodo execute di UnpackCtrl sul file " + documentFile +" - input " + input + " output "+ output);
			
			//signer = null;
			//fileSigned = null;
			//nomeFileSbustato = "";
			
			String fileName = InputFileUtil.getTempFileName(input);
			log.debug(requestKey + " - fileName " + fileName );
			
			boolean bustaEsterna = true;
			SbustamentoBean sbustamentoBean = extractDocumentFile( documentFile,input.getTemporaryDir(), fileName, bustaEsterna, requestKey, null );
			extractedFile = sbustamentoBean.getExtracttempfile();
			log.info(requestKey + " - File estratto " + extractedFile );
			
			output.addProperty(EXTRACTED_FILE, extractedFile);
			if (!extractedFile.exists()){
				log.info(requestKey + " - Non sono riuscito a scrivere");
			}
			if (extractedFile != documentFile){
				log.debug(requestKey + " - Il file di input era firmato");
				output.addProperty(IS_ROOT_SIGNED, true);
			}else{
				output.addProperty(IS_ROOT_SIGNED, false);
				log.debug(requestKey + " - Il file di input non era firmato");
			}
			
			SignerType formatoBusta = sbustamentoBean.getFormatoBusta();
			SignerType formatoBustaEsterna = sbustamentoBean.getFormatoBustaEsterna();
			String nomeFileSbustato = sbustamentoBean.getNomeFileSbustato();
			
			output.addProperty(ENVELOPE_FORMAT, formatoBusta);
			output.addProperty(ENVELOPE_FORMAT_ESTERNA, formatoBustaEsterna);
			output.addProperty(UnpackCtrl.EXTRACTED_FILE_NAME, nomeFileSbustato );
			
			Boolean abilitaNomeSbustato = false;
			boolean isRichiestoSbustamento = false;
			if( customInput instanceof InputUnpackType ){
				InputUnpackType inputUnpackType = (InputUnpackType)customInput;
				abilitaNomeSbustato = inputUnpackType.isAbilitaNomeSbustato();
				log.debug("abilitaNomeSbustato " + abilitaNomeSbustato );
				if( abilitaNomeSbustato!=null && abilitaNomeSbustato && nomeFileSbustato!=null ){
					response.setNomeFileSbustato( nomeFileSbustato );
				}
				isRichiestoSbustamento = true;
			}
			
			ret=true;
			//if(isRichiestoSbustamento)
				output.setFileResult(extractedFile);
			response.setVerificationStatus(VerificationStatusType.OK);
		} catch (IOException e) {
			log.fatal("fatal estracting..",e);
			OutputOperations.addError(response, FileOpMessage.UNPACK_OP_ERROR, VerificationStatusType.KO, e.getMessage() );
		} catch (Exception e) {
			log.fatal("fatal estracting..",e);
			OutputOperations.addError(response, FileOpMessage.UNPACK_OP_ERROR, VerificationStatusType.KO, e.getMessage() );
		}
		output.addResult(UnpackCtrlCode, response);
		
		return ret;
	}
	
	public void reset(){
		//signer = null;
		//fileSigned = null;
		//nomeFileSbustato = "";
	}
	
	/**
	 * estrae ricorsivamente il file dalle buste innestate
	 * @param documentFile
	 * @return
	 * @throws IOException
	 */ 
	public SbustamentoBean extractDocumentFile(File documentFile,File tempDir, String fileName, boolean bustaEsterna, String requestKey, File extracttempfile) throws IOException {
		//signer = null;
		SbustamentoBean sbustamentoBean = new SbustamentoBean();
		AbstractSigner signer = null;
		log.debug(requestKey + " -  Metodo di estrazione file " + documentFile);
		//while(fileSigned==null || fileSigned==true){
			//if (signer==null && fileSigned==null ) {
				log.debug(requestKey + " -  Verifico se il file " + documentFile + " deve essere sbustato. fileName " + fileName);
				try {
					if(!StringUtils.isBlank( fileName ) && fileName.contains(".")){
						String estensioneFile = FilenameUtils.getExtension( fileName );
						if( estensioneFile!=null && !StringUtils.isBlank( fileName ) )
							signer = SignerUtil.newInstance().getSignerManager(documentFile, estensioneFile);
						else
							signer = SignerUtil.newInstance().getSignerManager( documentFile );
					}
					else
						signer = SignerUtil.newInstance().getSignerManager( documentFile );
					//fileSigned = true;
					sbustamentoBean.setFileSigned(true);
					sbustamentoBean.setSigner(signer);
					log.debug(requestKey + " -  Ho individuato il Signer  " + signer);
				} catch (NoSignerException e) {
					// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o 
					//è firmato in maniera ignota al cryptosigner
					log.debug(requestKey + " - Il file " + documentFile + " non e' firmato");
					//fileSigned = false;
					sbustamentoBean.setFileSigned(false);
					sbustamentoBean.setExtracttempfile(documentFile);
					signer=null;
				}
			//}
		//}
		SignerType formatoBusta = null;
		SignerType formatoBustaEsterna = null;
		String nomeFileSbustato = "";
		if (signer!=null ) {
			if( signer.getFormat()!=null ){
				formatoBusta = signer.getFormat();
				sbustamentoBean.setFormatoBusta(formatoBusta);
				if( bustaEsterna )
					formatoBustaEsterna = signer.getFormat();
				sbustamentoBean.setFormatoBustaEsterna(formatoBustaEsterna);
				log.debug(requestKey + " -  Il file e' firmato, Formato busta " + formatoBusta );
				log.debug(requestKey + " -  Il file e' firmato, Formato busta esterna " + formatoBustaEsterna );
			}
			
			log.debug(requestKey + " -  signer: "  +  " " +signer);
			boolean esito = signer.isSignedType(documentFile);
			log.debug(requestKey + " - Esito verifica tipo firma: "  + esito +  " " +signer);
			log.debug(requestKey + " - Sbusto il file " + documentFile);
			InputStream stream = signer.getContentAsInputStream(documentFile);
			if (stream!=null && esito) {
				if (extracttempfile!=null) {
					//cancello il precedente
					extracttempfile.delete();
				}
				
				String suffissoFile = ".file";
				if(!StringUtils.isBlank( fileName ) ){
					String ext = FilenameUtils.getExtension(fileName);
					if (ext!=null && (ext.equalsIgnoreCase("p7m") ) ) {
						//nomeFilePassato = nomeFilePassato.substring(0, nomeFilePassato.indexOf(estensione) - 1);
						String fileName1 = FilenameUtils.getBaseName(fileName);
						//estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
						suffissoFile = "." + FilenameUtils.getExtension(fileName1) ;
					}
					else {
						suffissoFile = "." + ext;
					}
				}
				
				extracttempfile = File.createTempFile("FileEstratto-", suffissoFile, tempDir);
				log.info(requestKey + " - File estratto " + extracttempfile );
				FileOutputStream fos = new FileOutputStream( extracttempfile );
				IOUtils.copyLarge(stream, fos);
				fos.close();
				stream.close();
				sbustamentoBean.setExtracttempfile(extracttempfile);
				/*if( signer!=null){
				try{
					signer.closeFileStream();
					} catch(Exception e){
						log.error("",e);
					}
				}*/
				
				//if(!StringUtils.isBlank( fileName ) ){
					nomeFileSbustato = rename( extracttempfile, fileName, formatoBusta, requestKey, sbustamentoBean);
					sbustamentoBean.setNomeFileSbustato(nomeFileSbustato);
					log.debug(requestKey + " - nomeFileSbustato " + nomeFileSbustato );
				//}
				
//				if(signer.getFormat().equals(SignerType.PAdES)){
//					return extracttempfile;
//				}
				
				return extractDocumentFile(extracttempfile,tempDir, nomeFileSbustato, false, requestKey, extracttempfile);
			} else {
				return sbustamentoBean;
			}
		} else {
			nomeFileSbustato = fileName;
			sbustamentoBean.setNomeFileSbustato(nomeFileSbustato);
			return sbustamentoBean;
		}
	}
	
	public String rename( File file, String fileName ,SignerType tipoBusta, String requestKey, SbustamentoBean sbustamentoBean){
		log.debug( requestKey + " - Metodo rename, fileName: " + fileName + " - tipoBusta: "+ tipoBusta);
		AbstractSigner signer = null;
		File extracttempfile = sbustamentoBean.getExtracttempfile();
		if( tipoBusta == null)
			return fileName;

		if( tipoBusta.equals( SignerType.PAdES )){
			log.debug(requestKey + " - Verifico se il file " + file + " e' firmato PADES. fileName " + fileName);
			signer =  new PdfSigner();
			boolean isPdf = signer.isSignedType(file);
			log.info(requestKey + " -  isSignedPdf? " + isPdf);
			if( isPdf ){
				log.debug(requestKey + " - Ho individuato il Signer  " + signer);
				//fileSigned = true;
				sbustamentoBean.setFileSigned(true);
			}else {
				log.debug(requestKey + " - Il file " + extracttempfile + " non e' firmato");
				signer =null;
				//fileSigned = false;
				sbustamentoBean.setFileSigned(false);
			}
			if( fileName!=null && fileName.contains( ".p7m") ){
				String nuovoFilename = fileName.substring(0, fileName.indexOf(".p7m"));
				return nuovoFilename;
			} else 
				return fileName;
		}
		if( tipoBusta.equals( SignerType.XAdES ) || tipoBusta.equals( SignerType.XAdES_BES ) || tipoBusta.equals( SignerType.XAdES_C )
				|| tipoBusta.equals( SignerType.XAdES_T ) || tipoBusta.equals( SignerType.XAdES_X ) || tipoBusta.equals( SignerType.XAdES_XL )){
			log.debug(requestKey + " - Verifico se il file " + file + " è firmato XADES. fileName " + fileName);
			signer =  new XMLSigner();
			boolean isXml = signer.isSignedType(file);
			log.info(requestKey + " -  isSignedXml? " + isXml);
			if( isXml ){
				log.debug(requestKey + " - Ho individuato il Signer  " + signer);
				//fileSigned = true;
				sbustamentoBean.setFileSigned(true);
			}else {
				log.debug(requestKey + " - Il file " + extracttempfile + " non e' firmato");
				signer =null;
				//fileSigned = false;
				sbustamentoBean.setFileSigned(false);
			}
			if( fileName!=null && fileName.contains( ".p7m") ){
				String nuovoFilename = fileName.substring(0, fileName.indexOf(".p7m"));
				return nuovoFilename;
			} else 
				return fileName;
		} else if( tipoBusta.equals( SignerType.CAdES_BES ) || tipoBusta.equals( SignerType.CAdES_C ) ||
				tipoBusta.equals( SignerType.CAdES_T ) || tipoBusta.equals( SignerType.CAdES_X_Long )){
			if( fileName!=null && fileName.contains( ".p7m") ){
				log.debug(requestKey + " - Verifico se il file " + file + " deve essere sbustato. fileName " + fileName);
				try {
					if(!StringUtils.isBlank( fileName ) && fileName.contains(".")){
						String estensioneFile = FilenameUtils.getExtension( fileName );
						if( estensioneFile!=null && !StringUtils.isBlank( fileName ) )
							signer = SignerUtil.newInstance().getSignerManager(file, estensioneFile);
						else
							signer = SignerUtil.newInstance().getSignerManager(file);
					}
					else
						signer = SignerUtil.newInstance().getSignerManager(file);
					log.debug(requestKey + " - Ho individuato il Signer  " + signer);
					//fileSigned = true;
					sbustamentoBean.setFileSigned(true);
				} catch (NoSignerException e) {
					// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o 
					//è firmato in maniera ignota al cryptosigner
					log.debug(requestKey + " - Il file " + extracttempfile + " non e' firmato");
					signer =null;
					//fileSigned = false;
					sbustamentoBean.setFileSigned(false);
				}
				
				if( signer!=null && signer instanceof CAdESSigner ){
					return fileName;
				}
				
				String nuovoFilename = fileName.substring(0, fileName.indexOf(".p7m"));
				return nuovoFilename;
			} else {
				try {
					signer = SignerUtil.newInstance().getSignerManager(file);
					log.debug(requestKey + " - Ho individuato il Signer  " + signer);
					//fileSigned = true;
					sbustamentoBean.setFileSigned(true);
				} catch (NoSignerException e) {
					// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o 
					//è firmato in maniera ignota al cryptosigner
					log.debug(requestKey + " - Il file " + extracttempfile + " non e' firmato");
					signer =null;
					//fileSigned = false;
					sbustamentoBean.setFileSigned(false);
				}
				return fileName;
			}
		} else if( tipoBusta.equals( SignerType.P7M ) ){
			if( fileName!=null && fileName.contains( ".p7m") ){
				log.debug(requestKey + " - Verifico se il file " + file + " deve essere sbustato. fileName " + fileName);
				try {
					if(!StringUtils.isBlank( fileName ) && fileName.contains(".")){
						String estensioneFile = FilenameUtils.getExtension( fileName );
						if( estensioneFile!=null && !StringUtils.isBlank( fileName ) )
							signer = SignerUtil.newInstance().getSignerManager(file, estensioneFile);
						else
							signer = SignerUtil.newInstance().getSignerManager(file);
					}
					else
						signer = SignerUtil.newInstance().getSignerManager(file);
					log.debug(requestKey + " - Ho individuato il Signer  " + signer);
					//fileSigned = true;
					sbustamentoBean.setFileSigned(true);
				} catch (NoSignerException e) {
					// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o 
					//è firmato in maniera ignota al cryptosigner
					log.debug(requestKey + " - Il file " + extracttempfile + " non e' firmato");
					signer =null;
					//fileSigned = false;
					sbustamentoBean.setFileSigned(false);
				}
				
				if( signer!=null && signer instanceof P7MSigner ){
					return fileName;
				}
				
				String nuovoFilename = fileName.substring(0, fileName.indexOf(".p7m"));
				return nuovoFilename;
			} else {
				try {
					signer = SignerUtil.newInstance().getSignerManager(file);
					log.debug(requestKey + " - Ho individuato il Signer  " + signer);
					//fileSigned = true;
					sbustamentoBean.setFileSigned(true);
				} catch (NoSignerException e) {
					// Se arriva qua non e' stato trovato alcun signer per cui o il file non è firmato o 
					//è firmato in maniera ignota al cryptosigner
					log.debug(requestKey + " - Il file " + extracttempfile + " non e' firmato");
					signer =null;
					//fileSigned = false;
					sbustamentoBean.setFileSigned(false);
				}
				return fileName;
			}
		} else if( tipoBusta.equals( SignerType.TSD ) ){
			
			if( fileName!=null && fileName.contains( ".tsd") ){
				String nuovoFilename = fileName.substring(0, fileName.indexOf(".tsd"));
				return nuovoFilename;
			}
			return fileName;
		}
		return fileName;
	}
	
	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/*public SignerType getFormatoBusta() {
		return formatoBusta;
	}
	
	public String getNomeFileSbustato() {
		return nomeFileSbustato;
	}*/
	
	/*public void setSigner(AbstractSigner signer) {
		this.signer = signer;
	}*/

	/*public void setFileSigned(Boolean fileSigned) {
		this.fileSigned = fileSigned;
	}*/

	public static void main(String[] args) {
		File fileFrom = new File("C:/Users/Anna Tesauro/Desktop/8016012000000055.pdf");
		File fileTo = new File("C:/Users/Anna Tesauro/Desktop/8016012000000055_codificato.pdf");
		UnpackCtrl ctr = new UnpackCtrl();
//		try {
//			ctr.extractDocumentFile(file, new File("C:/Users/Anna Tesauro/Desktop/output"),"");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		boolean esitoCancellazione = file.delete();
//		System.out.println("esito " + esitoCancellazione);
		try {
			//ctr.cambiaFileEncoding(fileFrom, fileTo, null, "ISO-8859-1");
			//ctr.cambiaFileEncoding(fileFrom, fileTo, null, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
