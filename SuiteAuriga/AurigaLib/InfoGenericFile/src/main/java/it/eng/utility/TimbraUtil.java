/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.fileOperation.clientws.AbstractResponseOperationType;
import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.FileOperationResponse;
import it.eng.fileOperation.clientws.ImageFile;
import it.eng.fileOperation.clientws.InputConversionType;
import it.eng.fileOperation.clientws.InputCopiaConformeType;
import it.eng.fileOperation.clientws.InputFileOperationType;
import it.eng.fileOperation.clientws.InputTimbroType;
import it.eng.fileOperation.clientws.MessageType;
import it.eng.fileOperation.clientws.Operations;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro.Pagine;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.ResponseCopiaConformeType;
import it.eng.fileOperation.clientws.ResponsePdfConvResultType;
import it.eng.fileOperation.clientws.ResponseTimbroType;
import it.eng.fileOperation.clientws.TestoTimbro;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.services.fileop.FileOpUtility;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;

public class TimbraUtil extends FileOpUtility {

	private static Logger log = Logger.getLogger(TimbraUtil.class);
	
	/**
	 * Riceve un pdf in input stream e lo restituisce timbrato secondo 
	 * le specifiche contenute nell {@link OpzioniTimbroAttachEmail} 
	 * ricevuto in ingresso
	 * @param lInputStream
	 * @param lOpzioniTimbroAttachEmail
	 * @return
	 * @throws Exception 
	 */
	public InputStream timbra(File file, String displayFileName, OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail, boolean generaPdfA) throws Exception{
	
		FileOperationResponse lFileOperationResponse = null;

		try {
			
			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(file, displayFileName);
			
			Operations operations = new Operations();
			
			InputTimbroType lInputTimbroType = new InputTimbroType();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lInputTimbroType, lOpzioniTimbroAttachEmail);
			operations.getOperation().add(lInputTimbroType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);
			
		} catch (Exception e) {
			log.error(String.format("Errore in timbra() del file %s: %s", displayFileName, e.getMessage()), e);
			throw e;
		}		
		
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}		
		
		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");			
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();			
			for (AbstractResponseOperationType lAbstractResponseOperationType  : opResults){						
				if (lAbstractResponseOperationType instanceof ResponseTimbroType){					
					ResponseTimbroType lResponseTimbroType = (ResponseTimbroType) lAbstractResponseOperationType;										
					if (lResponseTimbroType.getErrorsMessage() != null && lResponseTimbroType.getErrorsMessage().getErrMessage() != null && lResponseTimbroType.getErrorsMessage().getErrMessage().size() > 0) {												
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						StringBuffer error = new StringBuffer(); 
						boolean first = true;						
						for (MessageType message : errors) {
							log.error(message.getDescription());
							if (first) {
								first = false; 
							} else {
								error.append(";");
							}
							error.append(message.getDescription());							
						}	
						throw new Exception(error.toString());
					} else {	
						// FIXME  Vedo se devo fare una conversione pdfa
						if (generaPdfA) {
							File fileTimbratoPdfa = File.createTempFile("timbrato", ".pdf");
							InputStream inputStreamTimbrato = lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();
							InfoFileUtility lFileUtility = new InfoFileUtility();
							lFileUtility.convertiPdfToPdfA(inputStreamTimbrato, new FileOutputStream(fileTimbratoPdfa));
							return new FileInputStream(fileTimbratoPdfa);
						} else {
							return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();	
						}
					}				
				}
			}
		}	
		
		return null;			
	}	
	
	/**
	 * Riceve un pdf in input stream e lo restituisce timbrato secondo le specifiche
	 * contenute nell {@link OpzioniTimbroAttachEmail} ricevuto in ingresso
	 * 
	 * @param lInputStream
	 * @param lOpzioniTimbroAttachEmail
	 * @return
	 * @throws Exception
	 */
	public static InputStream timbra(File file, String displayFileName, OpzioniCopertinaTimbroBean lOpzioniTimbro, boolean generaPdfA) throws Exception {

		FileOperationResponse lFileOperationResponse = null;

		try {

			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(file, displayFileName);

			Operations operations = new Operations();

			InputTimbroType lInputTimbroType = new InputTimbroType();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lInputTimbroType, lOpzioniTimbro);
			operations.getOperation().add(lInputTimbroType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);

		} catch (Exception e) {
			log.error(String.format("Errore in timbra() del file %s: %s", displayFileName, e.getMessage()), e);
			throw e;
		}

		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}

		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse()
					.getFileOperationResults().getFileOperationResult();
			for (AbstractResponseOperationType lAbstractResponseOperationType : opResults) {
				if (lAbstractResponseOperationType instanceof ResponseTimbroType) {
					ResponseTimbroType lResponseTimbroType = (ResponseTimbroType) lAbstractResponseOperationType;
					if (lResponseTimbroType.getErrorsMessage() != null
							&& lResponseTimbroType.getErrorsMessage().getErrMessage() != null
							&& lResponseTimbroType.getErrorsMessage().getErrMessage().size() > 0) {
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						StringBuffer error = new StringBuffer();
						boolean first = true;
						for (MessageType message : errors) {
							log.error(message.getDescription());
							if (first) {
								first = false;
							} else {
								error.append(";");
							}
							error.append(message.getDescription());
						}
						throw new Exception(error.toString());
					} else {
						// FIXME Vedo se devo fare una conversione pdfa
						if (generaPdfA) {
							File fileTimbratoPdfa = File.createTempFile("timbrato", ".pdf");
							InputStream inputStreamTimbrato = lFileOperationResponse.getFileoperationResponse()
									.getFileResult().getInputStream();
							InfoFileUtility lFileUtility = new InfoFileUtility();
							lFileUtility.convertiPdfToPdfA(inputStreamTimbrato, new FileOutputStream(fileTimbratoPdfa));
							return new FileInputStream(fileTimbratoPdfa);
						} else {
							return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Riceve un input stream di un file che non è un pdf e restituisce un pdf
	 * @param lInputStream
	 * @return
	 * @throws Exception 
	 */
	public static InputStream converti(File file, String displayFileName) throws Exception{
		
		FileOperationResponse lFileOperationResponse = null;

		try {

			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(file, displayFileName);
			
			Operations operations = new Operations();
			
			InputConversionType lInputConversionType = new InputConversionType();
			lInputConversionType.setPdfA(false);
			operations.getOperation().add(lInputConversionType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);
			
		} catch (Exception e) {
			log.error(String.format("Errore in timbra() del file %s: %s", displayFileName, e.getMessage()), e);
			throw e;
		}		
		
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}		
		
		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");			
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();			
			for (AbstractResponseOperationType lAbstractResponseOperationType  : opResults){						
				if (lAbstractResponseOperationType instanceof ResponsePdfConvResultType){					
					ResponsePdfConvResultType lResponsePdfConvResultType = (ResponsePdfConvResultType) lAbstractResponseOperationType;										
					if (lResponsePdfConvResultType.getErrorsMessage() != null && lResponsePdfConvResultType.getErrorsMessage().getErrMessage() != null && lResponsePdfConvResultType.getErrorsMessage().getErrMessage().size() > 0) {												
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						StringBuffer error = new StringBuffer(); 
						boolean first = true;						
						for (MessageType message : errors) {
							log.error(message.getDescription());
							if (first) {
								first = false; 
							} else {
								error.append(";");
							}
							error.append(message.getDescription());							
						}	
						throw new Exception(error.toString());
					} else {						
						return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();						
					}				
				}
			}
		}	
		
		return null;			
	}
	
	/**
	 * 
	 * @param opzioniTimbro opzioni Timbro a cui aggiungere il testo e il contenuto del barcode
	 * @param TestoInChiaro Testo del timbro
	 * @param ContenutoBarcode contenuto del barcode
	 */
	public static void impostaTestoOpzioniTimbro(OpzioniCopertinaTimbroBean opzioniTimbro, String testoInChiaro, String contenutoBarcode) {
		TestoTimbro testoTimbro = new TestoTimbro();
		testoTimbro.setTesto(testoInChiaro);
		opzioniTimbro.setIntestazioneTimbro(testoTimbro);

		TestoTimbro testoContenutoTimbro = new TestoTimbro();
		testoContenutoTimbro.setTesto(contenutoBarcode);
		opzioniTimbro.setTestoTimbro(testoContenutoTimbro);
	}

	/**
	 * 
	 * @param bean contiene diverse opzioni per il timbro
	 * @return Bean contenente le varie impostazioni da passare al metodo timbra.
	 */
	public static OpzioniTimbroAttachEmail populatePreference(OpzioniTimbroBean bean) {

		OpzioniTimbroAttachEmail result = new OpzioniTimbroAttachEmail();
		OpzioniTimbroAttachEmail lOpzioniTimbroConfig = (OpzioniTimbroAttachEmail) SpringAppContext.getContext()
				.getBean("OpzioniTimbroAttachEmail");

		/**
		 * Se skipScelteOpzioniCopertina = true allora recupero i valori dalla
		 * preference se ci sono, altrimenti dal config.xml
		 */
		if (bean.getSkipScelteOpzioniCopertina() != null && "true".equals(bean.getSkipScelteOpzioniCopertina())) {

			result.setPosizioneTimbro(bean.getPosizioneTimbroPref() != null && !"".equals(bean.getPosizioneTimbroPref())
					? PosizioneTimbroNellaPagina.fromValue(bean.getPosizioneTimbroPref())
					: lOpzioniTimbroConfig.getPosizioneTimbro());

			result.setRotazioneTimbro(bean.getRotazioneTimbroPref() != null && !"".equals(bean.getRotazioneTimbroPref())
					? TipoRotazione.fromValue(bean.getRotazioneTimbroPref())
					: lOpzioniTimbroConfig.getRotazioneTimbro());

			if (bean.getTipoPagina() == null
					|| (bean.getTipoPagina() != null && "intervallo".equals(bean.getTipoPagina()))) {
				Pagine lPaginaTimbroPagine = new Pagine();
				lPaginaTimbroPagine.setPaginaDa(bean.getPaginaDa() != null ? Integer.valueOf(bean.getPaginaDa()) : 1);
				lPaginaTimbroPagine.setPaginaA(bean.getPaginaA() != null ? Integer.valueOf(bean.getPaginaA()) : 1);
				PaginaTimbro lPaginaTimbro = new PaginaTimbro();
				lPaginaTimbro.setPagine(lPaginaTimbroPagine);
				result.setPaginaTimbro(lPaginaTimbro);
			} else {
				PaginaTimbro lPaginaTimbro = new PaginaTimbro();
				lPaginaTimbro.setTipoPagina(
						bean.getTipoPagina() != null ? TipoPagina.fromValue(bean.getTipoPagina()) : null);
				result.setPaginaTimbro(lPaginaTimbro);
			}

			TestoTimbro lTestoTimbro = new TestoTimbro();
			lTestoTimbro.setTesto(bean.getTesto());
			result.setTestoTimbro(lTestoTimbro);

			TestoTimbro lTestoTimbroIntestazione = new TestoTimbro();
			lTestoTimbroIntestazione.setTesto(bean.getTestoIntestazione());
			result.setIntestazioneTimbro(lTestoTimbroIntestazione);

			result.setPosizioneIntestazione(lOpzioniTimbroConfig.getPosizioneIntestazione());

			result.setTimeout(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getTimeout() != null
					? lOpzioniTimbroConfig.getTimeout()
					: 60000);
			result.setCodifica(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getCodifica() != null
					? lOpzioniTimbroConfig.getCodifica()
					: CodificaTimbro.BARCODE_PDF_417);
			result.setTimbroSingolo(false);
			result.setRigheMultiple(false);

			result.setMappaParametri(lOpzioniTimbroConfig.getMappaParametri());

		} else {

			result.setPosizioneTimbro(PosizioneTimbroNellaPagina.fromValue(bean.getPosizioneTimbro()));
			result.setRotazioneTimbro(TipoRotazione.fromValue(bean.getRotazioneTimbro()));

			PaginaTimbro lPaginaTimbro = new PaginaTimbro();
			if (bean.getTipoPagina() == null
					|| (bean.getTipoPagina() != null && "intervallo".equals(bean.getTipoPagina()))) {
				Pagine lPaginaTimbroPagine = new Pagine();
				lPaginaTimbroPagine.setPaginaDa(bean.getPaginaDa() != null ? Integer.valueOf(bean.getPaginaDa()) : 1);
				lPaginaTimbroPagine.setPaginaA(bean.getPaginaA() != null ? Integer.valueOf(bean.getPaginaA()) : 1);
				lPaginaTimbro.setPagine(lPaginaTimbroPagine);
				result.setPaginaTimbro(lPaginaTimbro);
			} else {
				lPaginaTimbro.setTipoPagina(
						bean.getTipoPagina() != null ? TipoPagina.fromValue(bean.getTipoPagina()) : null);
				result.setPaginaTimbro(lPaginaTimbro);
			}

			TestoTimbro lTestoTimbro = new TestoTimbro();
			lTestoTimbro.setTesto(bean.getTesto());
			result.setTestoTimbro(lTestoTimbro);

			TestoTimbro lTestoTimbroIntestazione = new TestoTimbro();
			lTestoTimbroIntestazione.setTesto(bean.getTestoIntestazione());
			result.setIntestazioneTimbro(lTestoTimbroIntestazione);

			result.setPosizioneIntestazione(lOpzioniTimbroConfig.getPosizioneIntestazione());

			result.setTimeout(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getTimeout() != null
					? lOpzioniTimbroConfig.getTimeout()
					: 60000);
			result.setCodifica(lOpzioniTimbroConfig != null && lOpzioniTimbroConfig.getCodifica() != null
					? lOpzioniTimbroConfig.getCodifica()
					: CodificaTimbro.BARCODE_PDF_417);
			result.setTimbroSingolo(false);
			result.setRigheMultiple(false);
		}

		return result;
	}

	public InputStream timbraConformitaCustom(File file, String nomeFile,
			OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail, File imgLogo, String percentualeLarghezzaTesto, String testoAggiuntivo, boolean generaPdfA) throws Exception {
		FileOperationResponse lFileOperationResponse = null;

		try {
			
			InputFileOperationType lInputFileOperationType = buildInputFileOperationType(file, nomeFile);
			
			Operations operations = new Operations();
			
			InputCopiaConformeType lInputCopiaConformeType = new InputCopiaConformeType();
//			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lInputCopiaConformeType, lOpzioniTimbroAttachEmail);
			
			ImageFile imageFile = new ImageFile();
			imageFile.setFileStream(new DataHandler(new FileDataSource(imgLogo)));
			
			lInputCopiaConformeType.setRotazione(lOpzioniTimbroAttachEmail.getRotazioneTimbro());
			lInputCopiaConformeType.setPosizione(lOpzioniTimbroAttachEmail.getPosizioneTimbro());
			lInputCopiaConformeType.setPagina(lOpzioniTimbroAttachEmail.getPaginaTimbro());
			lInputCopiaConformeType.setIntestazione(lOpzioniTimbroAttachEmail.getIntestazioneTimbro());
			lInputCopiaConformeType.setImmagine(imageFile);
			lInputCopiaConformeType.setAmpiezzaRiquadro(Integer.valueOf(percentualeLarghezzaTesto));
			if(StringUtils.isNotBlank(testoAggiuntivo)) {
				TestoTimbro testoAggiuntivoTimbro = new TestoTimbro();
				testoAggiuntivoTimbro.setTesto(testoAggiuntivo);
				lInputCopiaConformeType.setTestoAggiuntivo(testoAggiuntivoTimbro);
			}
			
			
			operations.getOperation().add(lInputCopiaConformeType);

			lFileOperationResponse = callFileOperationNoOut(lInputFileOperationType, operations);
			
		} catch (Exception e) {
			log.error(String.format("Errore in timbraConformitaCustom() del file %s: %s", nomeFile, e.getMessage()), e);
			throw e;
		}		
		
		if (lFileOperationResponse == null) {
			// C'è stato un timeout
			throw new IOException("Raggiunto il timeout");
		}		
		
		if (lFileOperationResponse.getFileoperationResponse() != null) {
			log.debug("Risposta dal servizio di FileOperation");			
			List<AbstractResponseOperationType> opResults = lFileOperationResponse.getFileoperationResponse().getFileOperationResults().getFileOperationResult();			
			for (AbstractResponseOperationType lAbstractResponseOperationType  : opResults){						
				if (lAbstractResponseOperationType instanceof ResponseCopiaConformeType){					
					ResponseCopiaConformeType lResponseTimbroType = (ResponseCopiaConformeType) lAbstractResponseOperationType;										
					if (lResponseTimbroType.getErrorsMessage() != null && lResponseTimbroType.getErrorsMessage().getErrMessage() != null && lResponseTimbroType.getErrorsMessage().getErrMessage().size() > 0) {												
						List<MessageType> errors = lAbstractResponseOperationType.getErrorsMessage().getErrMessage();
						StringBuffer error = new StringBuffer(); 
						boolean first = true;						
						for (MessageType message : errors) {
							log.error(message.getDescription());
							if (first) {
								first = false; 
							} else {
								error.append(";");
							}
							error.append(message.getDescription());							
						}	
						throw new Exception(error.toString());
					} else {	
						// FIXME  Vedo se devo fare una conversione pdfa
						if (generaPdfA) {
							File fileTimbratoPdfa = File.createTempFile("timbrato", ".pdf");
							InputStream inputStreamTimbrato = lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();
							InfoFileUtility lFileUtility = new InfoFileUtility();
							lFileUtility.convertiPdfToPdfA(inputStreamTimbrato, new FileOutputStream(fileTimbratoPdfa));
							return new FileInputStream(fileTimbratoPdfa);
						} else {
							return lFileOperationResponse.getFileoperationResponse().getFileResult().getInputStream();	
						}
					}				
				}
			}
		}	
		
		return null;			
	
	}
	
}
