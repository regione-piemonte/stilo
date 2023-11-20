/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2CheckuservscertificatofirmaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.TipoFirmaHsm;
import it.eng.auriga.ui.module.layout.shared.bean.WSEndPointArubaHsmBean;
import it.eng.client.DmpkCore2Checkuservscertificatofirma;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.hsm.cades.FirmaCadesUtil;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.marca.MarcaResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.config.ArubaConfig;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.MedasConfig;
import it.eng.hsm.client.config.PkBoxConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;
import it.eng.hsm.pades.FirmaPadesApposizioneFileOpUtil;
import it.eng.hsm.pades.FirmaPadesUtil;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
//import it.eng.tz.avtmb.integration.client.StampsignClientService;
//import it.eng.tz.avtmb.integration.client.StampsignClientServiceHelper;
//import it.eng.tz.avtmb.integration.client.StampsignClientServiceImpl;
//import it.eng.tz.avtmb.integration.client.dto.SignType;
//import it.eng.tz.avtmb.integration.client.dto.StampSignAuthRequest;
//import it.eng.tz.avtmb.integration.client.dto.StampSignAuthResponse;
//import it.eng.tz.avtmb.integration.client.dto.StampSignRequest;
//import it.eng.tz.avtmb.integration.client.dto.StampSignResponse;
import it.eng.utility.FirmaUtils;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;


/**
 * 
 * @author Federico Cacco
 * 
 *         Classe di utility per firma HSM, ricavata dalla vecchia classe it.eng.arubaHsm.ArubaHsmClient
 *
 */
public class HsmBaseUtility {

	private static final Logger log = Logger.getLogger(HsmBaseUtility.class);

    //private static StampsignClientServiceHelper helper;
    //private static StampsignClientService stampsignClientService;
    private static Properties properties;
	
	public static CertResponseBean richiediListaCertificati(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("richiediListaCertificati inizio " + formatter.format(dataCorrente));

		// Creo il client per il retrieve dei certificati
		Hsm hsmClient = HsmClientFactory.getHsmClient(session, bean);
		
		// Ottengo la lista dei certificati
		CertResponseBean certResponseBean = hsmClient.getUserCertificateList();
		MessageBean message = certResponseBean.getMessageBean();
		if ((message != null) && ((message.getStatus() != null) && (message.getStatus().equals(ResponseStatus.OK)))) {
			dataCorrente = new Date();
			log.debug("remoteOtpGenerator fine " + formatter.format(dataCorrente));
			return certResponseBean;
		}else{
			String errorMessage = "Errore nella richiesta dei certificati disponibili";
			if ((message != null) && (message.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(message.getDescription())) {
				errorMessage += ". " + message.getDescription();
			} else {
				errorMessage += ". " + "Verificare i dati inseriti";
			}
			throw new HsmClientSignatureException(errorMessage);
		}
	}
	
	public static OtpResponseBean richiediCodiceOTP(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("remoteOtpGenerator inizio " + formatter.format(dataCorrente));

		// Creo il client per il retrieve dei certificati		
		Hsm hsmClient = HsmClientFactory.getHsmClient(session, bean);
	
		// Invio la richiesta del codice OTP
		OtpResponseBean otpResponseBean = hsmClient.richiediOTP();
		MessageBean message = otpResponseBean.getMessage();
		if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
			log.error("Errore: - " + message.getCode() + " " + message.getDescription());
			throw new HsmClientSignatureException(message.getDescription());
		}
		dataCorrente = new Date();
		log.debug("remoteOtpGenerator fine " + formatter.format(dataCorrente));
		return otpResponseBean;
	}

	public static FirmaHsmBean firmaMultiplaHash(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("firmaMultipla inizio " + formatter.format(dataCorrente));

		if(bean.getUseExternalWS()) {
//		TC 26/05/2020 Gestione chiamata ai servizi di Area vasta Bari che a sua volta effettua la firma ARUBA
			StorageService storageService = StorageImplementation.getStorage();
			if(properties == null) {
				try {
					loadProperties();				}
				catch (Exception e) {
					throw new HsmClientSignatureException("Firma non eseguita. Verificare le configurazioni per la firma");
				}				
			}
			List<FileDaFirmare> fileFirmati = new ArrayList<FileDaFirmare>();
			try {
				String sessionId = null;
				boolean openSession = false;
				boolean closeSession = false;

//				helper = new StampsignClientServiceHelper(properties);
//				stampsignClientService = new StampsignClientServiceImpl(properties);
				
//				StampSignAuthRequest authRequest = helper.generateStampSignAuthRequest(properties.getProperty("area.vasta.stampsign.aoo"));
//		        StampSignAuthResponse authResponse = stampsignClientService.stampSignAuth(authRequest);
//		        if (authResponse.getErrore() != null){
//		        	throw new HsmClientSignatureException(" " + authResponse.getErrore().getMessaggio());
//		        }
//		        String accessToken = authResponse.getToken().getAccessToken().toString();
		        int i=0;
		        String otpCode = (bean.getCodiceOtp() == null) ? "" : bean.getCodiceOtp();
		        
				for (FileDaFirmare fileDaFirmareDaLista : bean.getListaFileDaFirmare()) {
					i++;
					if(bean.getListaFileDaFirmare().size() > 1) {
						openSession = (i == 1) ? true : false;
						closeSession = (bean.getListaFileDaFirmare().size() == i) ? true : false;
						if(sessionId != null) {
							otpCode = "";
						}
					}				
					String uriFile = fileDaFirmareDaLista.getUri();						
					File fileDaFirmare = storageService.getRealFile(uriFile);
					dataCorrente = new Date();
					log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
	
					byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
					dataCorrente = new Date();
					log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
	
					byte[] bytesFileFirmato = null;
//			        UUID uuidRequest = UUID.randomUUID();
//			        String stampsignSigner = properties.getProperty("area.vasta.stampsign.stampsignSigner");
//			        StampSignRequest signRequest = helper.fillSignRequest(bytesFileDaFirmare, uuidRequest, stampsignSigner, otpCode, SignType.fromString("PADES"));
//			        StampSignRequest signRequest = helper.fillSignRequest(bytesFileDaFirmare, uuidRequest, stampsignSigner, otpCode,
//			        			SignType.fromString(bean.getTipofirma().toUpperCase()), 
//			        			sessionId, openSession, closeSession);
			        
//			        StampSignResponse response = stampsignClientService.invokeStampSign(accessToken,signRequest);
//			        if (response.getErrore() != null){
//			            throw new HsmClientSignatureException(" " + response.getErrore().getMessaggio());
//			        }
//			        if(sessionId == null && response.getRisultato().getSessionId() != null) {
//			        	sessionId = response.getRisultato().getSessionId().toString();
//			        }
//			        bytesFileFirmato = response.getRisultato().getDownloadFileContent();
//			        String signedPath = uriFileTemp.replace(".pdf", "-SIGNED.pdf").replace("C:", "D:");
//			        try (
//			        		FileOutputStream fos = new FileOutputStream(signedPath)) {
//			        	fos.write(bytesFileFirmato);
//			        }

			        dataCorrente = new Date();
					log.debug("Ho recuperato i bytes del file firmato - " + formatter.format(dataCorrente));

					if (bytesFileFirmato != null) {
						dataCorrente = new Date();
						log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
						fileFirmati.add(creaFileFirmato(null, fileDaFirmareDaLista, bytesFileFirmato, null, false, fileDaFirmareDaLista.getTipoFirmaHsm().getDescrizione(), null));
						dataCorrente = new Date();
						log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
					}
				}
	
				bean.setFileFirmati(fileFirmati);	
			} 
			catch (HsmClientSignatureException e) {
				log.debug("Errore nella firma remota", e);
				throw e;
			}
			catch (Exception e) {
				log.debug("Errore nella firma remota", e);
				throw new HsmClientSignatureException("Firma non eseguita. Verificare i dati inseriti");
			}
		} else {
			// Verifico se le eventuali firme Pades devono essere visualizzate
			boolean visualizzaFirmaPades = ParametriDBUtil.getParametroDBAsBoolean(session, "SHOW_GRAPHIC_SIGNATURE_IN_PADES");
	
			String certId = bean.getCertId();
			
			CertResponseBean certResponseBean = null;
			Hsm hsmClient = null;
			// Creo il client per il retrieve dei certificati
			hsmClient = HsmClientFactory.getHsmClient(session, bean);
			if (certId != null && !"".equalsIgnoreCase(certId)){
				// Ho già selezionato il certificato con cui effettuare la firma, lo devo solamente recuperare			
				certResponseBean = hsmClient.getUserCertificateById();
			}else{
				// Ottengo la lista dei certificati
				certResponseBean = hsmClient.getUserCertificateList();
			}
			MessageBean messageBean = certResponseBean.getMessageBean();
	
			if ((messageBean != null) && (messageBean.getStatus() != null) && (messageBean.getStatus().equals(ResponseStatus.OK))){
				List<CertBean> certBeanList = certResponseBean.getCertList();
				if (certBeanList != null && certBeanList.size() > 0) {
					// Estraggo il certificato di firma
					CertBean certBean = certBeanList.get(0);
					bean.setCertificatoDiFirma(certBean);
					String certificateSerialNumber = certBean.getSerialNumber();
					bean.setCertSerialNumber(certificateSerialNumber);
					try {
						log.debug("Recupero il certificato con id " + certBean.getCertId() + " - " + formatter.format(dataCorrente));
						X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(certBean.getCertValue()));
		
						// Verifico coerenza utente loggato vs certificato di firma
						if (!verificaCoerenzaUtenteLoggatoVsCertificatoFirma(bean, cert, session)) {
							log.error("Il certificato di firma non corrisponde al nominativo dell'utente loggato: firma non consentita");
							log.error("Utente loggato: " + (AurigaUserUtil.getLoginInfo(session) != null ? AurigaUserUtil.getLoginInfo(session).getIdUser() : "LoginInfo null"));
							log.error("Certificato di firma: " + cert.getSubjectX500Principal().toString());
							throw new HsmClientSignatureException("Il certificato di firma non corrisponde al tuo nominativo: firma non consentita");
						};
						
						// Creo il client per la firma HSM
						hsmClient = HsmClientFactory.getHsmClient(session, bean);
						
						// Verifico se devo aprire una sessione di firma e in caso la apro
						apriSessioneFirmaSeRichiesto(session, hsmClient);
		
						dataCorrente = new Date();
		
						StorageService storageService = StorageImplementation.getStorage();
		
						List<FileDaFirmare> fileFirmati = new ArrayList<FileDaFirmare>();
						
						if (!hsmClient.getHsmConfig().getHsmType().equals(HsmType.PKBOX)) {
		
							for (FileDaFirmare fileDaFirmareBean : bean.getListaFileDaFirmare()) {
								String uriFile = fileDaFirmareBean.getUri();
								File fileDaFirmare = storageService.getRealFile(uriFile);
			
								dataCorrente = new Date();
								log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
								byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
								dataCorrente = new Date();
								log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
								byte[] bytesFileFirmato = null;
			
								if (fileDaFirmareBean.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_PADES)) {
									bytesFileFirmato = FirmaPadesUtil.firmaFileHash(bytesFileDaFirmare, cert, hsmClient, visualizzaFirmaPades, bean.getRettangoloFirmaPades());
								} else if (fileDaFirmareBean.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_PADES_FILEOP) && fileDaFirmareBean.getListaInformazioniFirmaGrafica() != null) {
									bytesFileFirmato = FirmaPadesApposizioneFileOpUtil.firmaFileHash(fileDaFirmareBean, fileDaFirmare, cert, null, hsmClient);																							
								} else if (fileDaFirmareBean.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_CONGIUNTA)) {
									bytesFileFirmato = FirmaCadesUtil.firmaFileP7mCongiuntaHash(bytesFileDaFirmare, cert, hsmClient);
								} else if (fileDaFirmareBean.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_VERTICALE)) {
									bytesFileFirmato = FirmaCadesUtil.firmaFileP7mVerticaleHash(bytesFileDaFirmare, cert, hsmClient);
								}
								dataCorrente = new Date();
								log.debug("Ho recuperato i bytes del file firmato - " + formatter.format(dataCorrente));
			
								if (bytesFileFirmato != null) {
									dataCorrente = new Date();
									log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
									FileDaFirmare lFileDaFirmare = creaFileFirmato(hsmClient, fileDaFirmareBean, bytesFileFirmato, bean.getFileDaMarcare(), bean.isSkipControlloFirmaBusta(), fileDaFirmareBean.getTipoFirmaHsm().getDescrizione(), certBean);
									fileFirmati.add(lFileDaFirmare);
									dataCorrente = new Date();
									log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
								}
							}
			
							bean.setFileFirmati(fileFirmati);
							
						} else {
				
							Map<Integer, Integer> mappaPosFilePades = new HashMap<Integer, Integer>();
							Map<Integer, Integer> mappaPosFileCadesVerticale = new HashMap<Integer, Integer>();
							Map<Integer, Integer> mappaPosFileCadesCongiunta = new HashMap<Integer, Integer>();
							
							int posFilePades = 0;
							int posFileCadesVerticale = 0;
							int posFileCadesCongiunta = 0;
							
							List<byte[]> fileDaFirmarePades = new ArrayList<>();
							List<byte[]> fileDaFirmareCadesCongiunta = new ArrayList<>();
							List<byte[]> fileDaFirmareCadesVerticale = new ArrayList<>();
							
							List<byte[]> fileFirmatiPades = new ArrayList<>();
							List<byte[]> fileFirmatiCadesCongiunta = new ArrayList<>();
							List<byte[]> fileFirmatiCadesVerticale = new ArrayList<>();
							
							for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
								FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
								
								String uriFile = fileDaFirmareDaLista.getUri();
			
								File fileDaFirmare = storageService.getRealFile(uriFile);
			
								dataCorrente = new Date();
								log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
								byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
								dataCorrente = new Date();
								log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
								
								if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_PADES)) {
									log.debug("file " + pos + " aggiunto a fileDaFirmarePades");
									fileDaFirmarePades.add(bytesFileDaFirmare);
									mappaPosFilePades.put(pos, posFilePades);
									posFilePades ++;
								} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_CONGIUNTA)) {
									log.debug("file " + pos + " aggiunto a fileDaFirmareCadesCongiunta");
									fileDaFirmareCadesCongiunta.add(bytesFileDaFirmare);
									mappaPosFileCadesCongiunta.put(pos, posFileCadesCongiunta);
									posFileCadesCongiunta ++;
								} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_VERTICALE)) {
									log.debug("file " + pos + " aggiunto a fileDaFirmareCadesVerticale");
									fileDaFirmareCadesVerticale.add(bytesFileDaFirmare);
									mappaPosFileCadesVerticale.put(pos, posFileCadesVerticale);
									posFileCadesVerticale ++;
								}
							}
			
							if (!fileDaFirmarePades.isEmpty()) {
								log.debug("faccio le firme Pades");
								fileFirmatiPades = FirmaPadesUtil.firmaFileHashPkBox(fileDaFirmarePades, cert, hsmClient, visualizzaFirmaPades, bean.getRettangoloFirmaPades());	
							} 
							if (!fileDaFirmareCadesCongiunta.isEmpty()) {
								log.debug("faccio le firme Cades congiunte");
								fileFirmatiCadesCongiunta = FirmaCadesUtil.firmaFileP7mCongiuntaHashPkBox(fileDaFirmareCadesCongiunta, cert, hsmClient);
							} 
							if (!fileDaFirmareCadesVerticale.isEmpty()) {
								log.debug("faccio le firme Cades verticali");
								fileFirmatiCadesVerticale = FirmaCadesUtil.firmaFileP7mVerticaleHashPkBox(fileDaFirmareCadesVerticale, cert, hsmClient);
							}
							dataCorrente = new Date();
							log.debug("Ho terminato la firma dei file, costruisco l'outpunt - " + formatter.format(dataCorrente));
							
							for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
								FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
								byte[] bytesFileFirmato = null;
								if (mappaPosFilePades.containsKey(pos)) {
									log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiPades alla posizione " + mappaPosFilePades.get(pos));
									bytesFileFirmato = fileFirmatiPades.get(mappaPosFilePades.get(pos));
								} else if (mappaPosFileCadesCongiunta.containsKey(pos)) {
									log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiCadesCongiunta alla posizione " + mappaPosFileCadesCongiunta.get(pos));
									bytesFileFirmato = fileFirmatiCadesCongiunta.get(mappaPosFileCadesCongiunta.get(pos));
								} else if (mappaPosFileCadesVerticale.containsKey(pos)) {
									log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiCadesVerticale alla posizione " + mappaPosFileCadesVerticale.get(pos));
									bytesFileFirmato = fileFirmatiCadesVerticale.get(mappaPosFileCadesVerticale.get(pos));
								}
		
								if (bytesFileFirmato != null) {
									dataCorrente = new Date();
									log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
									fileFirmati.add(creaFileFirmato(hsmClient, fileDaFirmareDaLista, bytesFileFirmato, bean.getFileDaMarcare(), bean.isSkipControlloFirmaBusta(), fileDaFirmareDaLista.getTipoFirmaHsm().getDescrizione(), certBean));
									dataCorrente = new Date();
									log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
								} else {
									throw new HsmClientSignatureException("Non sono stati trovati bytesFileFirmato per la posizione " + pos);
								}
							}
						}
			
						bean.setFileFirmati(fileFirmati);
	
						// Verifico se devo chiudere la sessione di firma eventualemte aperta
						chiudiSessioneFirmaSeRichiesto(session, hsmClient);
		
					} catch (CertificateException e) {
						log.error("Errore nella firma multipla hash", e);
					}
				} else {
					log.error("Non è stato trovato nessun certificato valido per la firma");
					throw new HsmClientSignatureException("Non è stato trovato nessun certificato valido per la firma");
				}
			} else {
				String errorMessage = "Firma non eseguita";
				if ((messageBean != null) && (messageBean.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(messageBean.getDescription())) {
					errorMessage += ". " + messageBean.getDescription();
				} else {
					errorMessage += ". " + "Verificare i dati inseriti";
				}
				throw new HsmClientSignatureException(errorMessage);
			}

		}

		dataCorrente = new Date();
		log.debug("firmaMultipla fine " + formatter.format(dataCorrente));
		return bean;
	}
	
	public static FirmaHsmBean firmaMultiplaFile(FirmaHsmBean bean, HttpSession session) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataCorrente = null;
		dataCorrente = new Date();
		log.debug("firmaMultiplaFile inizio " + formatter.format(dataCorrente));
		
		// Controllo se ci sono tipi misti di firma, non è detto che il client hsm supporti tale operazione con uno stesso otp
		// Nel caso di forme remote senza otp o di sessioni di firma non ci sarebbe il problema, quindi questa cosa va parametrizzata
		boolean presentiFirmePades = false;
		boolean presentiFirmeCadesCongiunte = false;
		boolean presentiFirmeCadesVerticali = false;
		
		for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
			FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
			if (fileDaFirmareDaLista.getTipoFirmaHsm() == TipoFirmaHsm.HASH_PADES) {
				presentiFirmePades = true;	
			}	
			if (fileDaFirmareDaLista.getTipoFirmaHsm() == TipoFirmaHsm.HASH_CADES_CONGIUNTA) {
				presentiFirmeCadesCongiunte = true;
			}
			if (fileDaFirmareDaLista.getTipoFirmaHsm() == TipoFirmaHsm.HASH_CADES_VERTICALE) {
				presentiFirmeCadesVerticali = true;
			}
		}
		
		TipoFirmaHsm tipoFirmaDaSettare;
		if (presentiFirmeCadesVerticali) {
			tipoFirmaDaSettare = TipoFirmaHsm.HASH_CADES_VERTICALE;
		} else if (presentiFirmeCadesCongiunte) {
			tipoFirmaDaSettare = TipoFirmaHsm.HASH_CADES_CONGIUNTA;
		} else {
			tipoFirmaDaSettare = TipoFirmaHsm.HASH_PADES;
		}
		
		for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
			FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
			fileDaFirmareDaLista.setTipoFirmaHsm(tipoFirmaDaSettare);
		}
		
		Hsm hsmClient = null;
		// Creo il client per la firma HSM
		hsmClient = HsmClientFactory.getHsmClient(session, bean);
		
		// Verifico se devo aprire una sessione di firma e in caso la apro
		apriSessioneFirmaSeRichiesto(session, hsmClient);
	
		StorageService storageService = StorageImplementation.getStorage();

		List<FileDaFirmare> fileFirmati = new ArrayList<FileDaFirmare>();
		
		Map<Integer, Integer> mappaPosFilePades = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mappaPosFileCadesVerticale = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mappaPosFileCadesCongiunta = new HashMap<Integer, Integer>();
		
		int posFilePades = 0;
		int posFileCadesVerticale = 0;
		int posFileCadesCongiunta = 0;
		
		List<byte[]> fileDaFirmarePades = new ArrayList<>();
		List<byte[]> fileDaFirmareCadesCongiunta = new ArrayList<>();
		List<byte[]> fileDaFirmareCadesVerticale = new ArrayList<>();
		
		List<byte[]> fileFirmatiPades = new ArrayList<>();
		List<byte[]> fileFirmatiCadesCongiunta = new ArrayList<>();
		List<byte[]> fileFirmatiCadesVerticale = new ArrayList<>();
					
		for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
			FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
			
			String uriFile = fileDaFirmareDaLista.getUri();

			File fileDaFirmare = storageService.getRealFile(uriFile);

			dataCorrente = new Date();
			log.debug("Recupero i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));

			byte[] bytesFileDaFirmare = getFileBytes(fileDaFirmare);
			dataCorrente = new Date();
			log.debug("Ho recuperato i bytes del file " + fileDaFirmare + " - " + formatter.format(dataCorrente));
			
			if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_PADES)) {
				log.debug("file " + pos + " aggiunto a fileDaFirmarePades");
				fileDaFirmarePades.add(bytesFileDaFirmare);
				mappaPosFilePades.put(pos, posFilePades);
				posFilePades ++;
			} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_CONGIUNTA)) {
				log.debug("file " + pos + " aggiunto a fileDaFirmareCadesCongiunta");
				fileDaFirmareCadesCongiunta.add(bytesFileDaFirmare);
				mappaPosFileCadesCongiunta.put(pos, posFileCadesCongiunta);
				posFileCadesCongiunta ++;
			} else if (fileDaFirmareDaLista.getTipoFirmaHsm().equals(TipoFirmaHsm.HASH_CADES_VERTICALE)) {
				log.debug("file " + pos + " aggiunto a fileDaFirmareCadesVerticale");
				fileDaFirmareCadesVerticale.add(bytesFileDaFirmare);
				mappaPosFileCadesVerticale.put(pos, posFileCadesVerticale);
				posFileCadesVerticale ++;
			}
		}
	
		if (!fileDaFirmarePades.isEmpty()) {
			log.debug("faccio le firme Pades");
			fileFirmatiPades = FirmaPadesUtil.firmaFileFile(fileDaFirmarePades, hsmClient);
		}
		if (!fileDaFirmareCadesCongiunta.isEmpty()) {
			log.debug("faccio le firme Cades congiunte");
			fileFirmatiCadesCongiunta = FirmaCadesUtil.firmaFileP7mCongiuntaFile(fileDaFirmareCadesCongiunta, hsmClient);
		} 
		if (!fileDaFirmareCadesVerticale.isEmpty()) {
			log.debug("faccio le firme Cades verticali");
			fileFirmatiCadesVerticale = FirmaCadesUtil.firmaFileP7mVerticaleFile(fileDaFirmareCadesVerticale, hsmClient);
		}
		
		dataCorrente = new Date();
		log.debug("Ho terminato la firma dei file, costruisco l'outpunt - " + formatter.format(dataCorrente));
		for (int pos = 0; pos < bean.getListaFileDaFirmare().size(); pos++) {
			FileDaFirmare fileDaFirmareDaLista = bean.getListaFileDaFirmare().get(pos);
			byte[] bytesFileFirmato = null;
			if (mappaPosFilePades.containsKey(pos)) {
				log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiPades alla posizione " + mappaPosFilePades.get(pos));
				bytesFileFirmato = fileFirmatiPades.get(mappaPosFilePades.get(pos));
			} else if (mappaPosFileCadesCongiunta.containsKey(pos)) {
				log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiCadesCongiunta alla posizione " + mappaPosFileCadesCongiunta.get(pos));
				bytesFileFirmato = fileFirmatiCadesCongiunta.get(mappaPosFileCadesCongiunta.get(pos));
			} else if (mappaPosFileCadesVerticale.containsKey(pos)) {
				log.debug("Il file in posizione " + pos + " lo recupero da fileFirmatiCadesVerticale alla posizione " + mappaPosFileCadesVerticale.get(pos));
				bytesFileFirmato = fileFirmatiCadesVerticale.get(mappaPosFileCadesVerticale.get(pos));
			}
			if (bytesFileFirmato != null) {
				dataCorrente = new Date();
				log.debug("Creo il file firmato - " + formatter.format(dataCorrente));
				fileFirmati.add(creaFileFirmato(hsmClient, fileDaFirmareDaLista, bytesFileFirmato, bean.getFileDaMarcare(), bean.isSkipControlloFirmaBusta(), fileDaFirmareDaLista.getTipoFirmaHsm().getDescrizione(), null));
				dataCorrente = new Date();
				log.debug("Ho creato il file firmato - " + formatter.format(dataCorrente));
			} else {
				throw new HsmClientSignatureException("Non sono stati trovati bytesFileFirmato per la posizione " + pos);
			}
		}
		
		bean.setFileFirmati(fileFirmati);
		
		// Verifico se devo chiudere la sessione di firma eventualemte aperta
		chiudiSessioneFirmaSeRichiesto(session, hsmClient);
		
		dataCorrente = new Date();
		log.debug("firmaMultiplaFile fine " + formatter.format(dataCorrente));
		return bean;
	}

	private static void apriSessioneFirmaSeRichiesto(HttpSession session, Hsm hsmClient) throws HsmClientConfigException, HsmClientSignatureException {
		if (hsmClient.getHsmConfig().getClientConfig().isRequireSignatureInSession()) {
			// Se richiesto dal provider, devo eseguire tutte le firme nella stessa sessione. Ne apro una e salvo il suo id
			try {
				SessionResponseBean sessioneResponseBean = hsmClient.apriSessioneFirmaMultipla();
				MessageBean message = sessioneResponseBean.getMessage();
				if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
					log.error("Errore nell'apertura della sessione di firma: " + message.getCode() + " - " + message.getDescription()  + " " + getHsmUserDescription(hsmClient, session));
					String errorMessage = "Errore nell'apertura della sessione di firma";
					if ((message != null) && (message.getDescription() != null) && !"errore sconosciuto".equalsIgnoreCase(message.getDescription())) {
						errorMessage += ". " + message.getDescription();
					} else {
						errorMessage += ". " + "Verificare i dati inseriti";
					}
					throw new HsmClientSignatureException(errorMessage);
				}
				hsmClient.getHsmConfig().getClientConfig().setIdSession(sessioneResponseBean.getSessionId());

			} catch (UnsupportedOperationException e) {
				log.debug("Il client non supporta l'apertura della sessione. L'operazione verrà ignorata", e);
			}
		}
	}
	
	private static void chiudiSessioneFirmaSeRichiesto(HttpSession session, Hsm hsmClient) throws HsmClientConfigException {
		if (hsmClient.getHsmConfig().getClientConfig().isRequireSignatureInSession()) {
			// Se avevo aperto una sessione di firma, la devo chiudere
			try {
				MessageBean message = hsmClient.chiudiSessioneFirmaMultipla(hsmClient.getHsmConfig().getClientConfig().getIdSession());
				if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
					log.error("Errore nella chiusura della sessione di firma: " + message.getCode() + " - " + message.getDescription() + " " + getHsmUserDescription(hsmClient, session));
				}
			} catch (UnsupportedOperationException e) {
				log.debug("Il client non supporta la chiusura della sessione. L'operazione verrà ignorata", e);
			}
		}
	}
		
	private static void loadProperties() throws Exception{
		InputStream stream = HsmBaseUtility.class.getClassLoader().getResourceAsStream("application.properties");
		if(stream != null) {
			try {
				if(properties==null) {
					properties = new Properties();
				}
				properties.load(stream);
			}
			catch(IOException e) {
				log.error("loadProperties errore durante il caricamento di application.properties");
				throw new Exception("loadProperties errore durante il caricamento di application.properties", e);
			}
			catch(Exception e) {
				log.error("loadProperties errore durante il caricamento di application.properties");
				throw new Exception("loadProperties errore durante il caricamento di application.properties", e);
			}
		}
		return;
	}
	
	protected static FileDaFirmare creaFileFirmato(Hsm hsmClient, FileDaFirmare lFileDaFirmare, byte[] bytesFileFirmato, String tipoMarca, boolean skipControlloFirmaBusta, String tipofirma, CertBean certBean) throws Exception {

		String nomeFileFirmato = "";

		nomeFileFirmato = lFileDaFirmare.getNomeFile();

		if (tipofirma.toLowerCase().indexOf("cades") != -1) {
			// Sono in firma Cades, devo eventualmente cambiare il nome del file
			if (!nomeFileFirmato.toLowerCase().endsWith(".p7m")) {
				nomeFileFirmato = lFileDaFirmare.getNomeFile() + ".p7m";
			}
		}

		FileDaFirmare fileFirmatoBean = new FirmaHsmBean().new FileDaFirmare();
		String uriFileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(bytesFileFirmato));
		
		// MARCA
		boolean isFileDaMarcare = false;
		if(tipoMarca!=null && (tipoMarca.equalsIgnoreCase("HSM") || tipoMarca.equalsIgnoreCase("SERVER"))){
			isFileDaMarcare = true;
		}
		if (isFileDaMarcare) {

			byte[] byteFileMarcato = null;
			// Hsm hsmClient = HsmClientFactory.getHsmClient(hsmType);
			File fileFirmato = StorageImplementation.getStorage().extractFile(uriFileFirmato);
						
			if(tipoMarca.equalsIgnoreCase("HSM")){
				MarcaResponseBean marcaResponseBean = hsmClient.aggiungiMarca(fileFirmato);
	
				if ((marcaResponseBean != null) && (marcaResponseBean.getMessage() != null)) {
					log.debug("Response Status: " + marcaResponseBean.getMessage().getStatus());
					log.debug("Response Return Code: " + marcaResponseBean.getMessage().getCode());
					if (marcaResponseBean.getMessage().getDescription() != null) {
						log.debug("Response Description: " + marcaResponseBean.getMessage().getDescription());
					}
					if (marcaResponseBean.getMessage().getStatus().equals(ResponseStatus.OK)) {
						byteFileMarcato = marcaResponseBean.getFileMarcatoResponseBean().getFileMarcato();
					}
				}
			} else if(tipoMarca.equalsIgnoreCase("SERVER")){
				WSEndPointArubaHsmBean wsEndPointArubaHsmBean = (WSEndPointArubaHsmBean) SpringAppContext.getContext().getBean("WSEndPointArubaHsm");
				
				boolean useAuth = false;
				if(wsEndPointArubaHsmBean!=null && wsEndPointArubaHsmBean.getMarcaServiceUid()!=null && 
						!wsEndPointArubaHsmBean.getMarcaServiceUid().equalsIgnoreCase("")){
					useAuth = true;
				}
				
				TsrGenerator tsr = new TsrGenerator(wsEndPointArubaHsmBean.getMarcaServiceUrl(), wsEndPointArubaHsmBean.getMarcaServiceUid(), wsEndPointArubaHsmBean.getMarcaServicePwd(), useAuth); 
				if (tipofirma.toLowerCase().indexOf("cades") != -1) {
					byteFileMarcato = tsr.addMarca(fileFirmato);
				} else {
					byteFileMarcato = tsr.addMarcaPdf(fileFirmato);
				} 
			}
			uriFileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(byteFileMarcato));
			// Fine marca
		}

		fileFirmatoBean.setNomeFile(nomeFileFirmato);
		fileFirmatoBean.setUri(uriFileFirmato);
		fileFirmatoBean.setIdFile(lFileDaFirmare.getIdFile());

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriFileFirmato));

		MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();

		if (!skipControlloFirmaBusta) {
			if (certBean != null && !isFileDaMarcare) {
				try {
					infoFile = FirmaUtils.aggiungiInfoFirmaInInfoFile(fileFirmatoBean.getUri(), lFileDaFirmare.getInfoFile(), certBean, tipofirma);
				} catch (Exception e) {
					log.error("Errore nell'aggiornamento delle informazioni di firma" + e.getMessage(), e);
					infoFile = lInfoFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
				}
				fileFirmatoBean.setInfoFile(infoFile);
			} else {
				infoFile = lInfoFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
				fileFirmatoBean.setInfoFile(infoFile);
			}
		}

		return fileFirmatoBean;

	}

	protected static byte[] getFileBytes(File file) {
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			log.error("Errore in getFileBytes", e);
		}
		return bFile;
	}

	protected static SignOption getSignOption() {
		return null;
	}
	
	public static boolean verificaCoerenzaUtenteLoggatoVsCertificatoFirma(FirmaHsmBean bean, X509Certificate cert, HttpSession session) throws StoreException {
		// Faccio il controllo se ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG sia true, che isSkipControlloCoerenzaCertificatoFirma sia false e di non essere in delega
		if (ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG") && !bean.isSkipControlloCoerenzaCertificatoFirma() && StringUtils.isBlank(bean.getUsernameDelegante())) {
			// Verifico la coerenza tra utente loggato e certificato di firma utilizzato
			boolean resultOk = false;
			try {
				DmpkCore2Checkuservscertificatofirma lDmpkCore2Checkuservscertificatofirma = new DmpkCore2Checkuservscertificatofirma();
				DmpkCore2CheckuservscertificatofirmaBean lDmpkCore2CheckuservscertificatofirmaBean = new DmpkCore2CheckuservscertificatofirmaBean();
				// Estraggo i dati da mandare alla store di verifica
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
				String certInfo = cert.getSubjectX500Principal().toString();
				lDmpkCore2CheckuservscertificatofirmaBean.setIduserin(loginBean.getIdUser());
				lDmpkCore2CheckuservscertificatofirmaBean.setDaticertificatofirmain(certInfo);
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(loginBean.getSchema());
				Locale locale = new Locale("it", "IT");
				StoreResultBean<DmpkCore2CheckuservscertificatofirmaBean> res = lDmpkCore2Checkuservscertificatofirma.execute(locale, lSchemaBean, lDmpkCore2CheckuservscertificatofirmaBean);
				if (!res.isInError() ) {
					resultOk = true;
				}
			} catch (Exception e) {
				log.error("Errore nella verifica della corrispondenza tra nominativo utente e certificato di firma", e);
				throw new StoreException("Errore nella verifica della corrispondenza tra nominativo utente e certificato di firma");
			}
			return resultOk;
		} else {
			// Non eseguo nessun controllo
			return true;
		}
	}
	
//	public static boolean firmaRemotaConPassaggioFile(Hsm hsmClient) {
//		String value = hsmClient.getHsmConfig().getClientConfig().getFirmaRemotaConPassaggioFile();
//		return Boolean.parseBoolean(value);
//	}
	
	public static String getHsmUserDescription(Hsm bean, HttpSession session) {
		ClientConfig clientConfig = (bean != null && bean.getHsmConfig() != null && bean.getHsmConfig().getClientConfig() != null) ? bean.getHsmConfig().getClientConfig() : null;
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(session);
		String userDescription = "(UtenteLoggato: " + loginBean.getDenominazione() + ", UtenteDelega: " + loginBean.getDelegaDenominazione();
		if (clientConfig != null && clientConfig instanceof ArubaConfig) {
			ArubaConfig arubaClientConfig = (ArubaConfig)clientConfig;
			String username = arubaClientConfig.getUser();
			String password = StringUtils.isNotBlank(arubaClientConfig.getPassword()) ?arubaClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
			String usernameDelegato = arubaClientConfig.getDelegatedUser();
			String passwordDelegato = StringUtils.isNotBlank(arubaClientConfig.getDelegatedPassword()) ? arubaClientConfig.getDelegatedPassword().replaceAll(".", "*") : "<vuota>";
			String dominioDelegato = arubaClientConfig.getDelegatedDomain();
			String isInDelega = arubaClientConfig.isUseDelegate() + "";
			userDescription += ", HsmUsername: " + username + ", HsmPassword: " + password + ", HsmUsernameDelegato: " + usernameDelegato + ", HsmPasswordDelegato: " + passwordDelegato + ", HsmDominio: " + dominioDelegato + ", HsmInDelega: " + isInDelega;
		}else if (clientConfig != null && clientConfig instanceof MedasConfig) {
			MedasConfig medasClientConfig = (MedasConfig)clientConfig;
			String username = medasClientConfig.getUser();
			String codiceFiscale = medasClientConfig.getCodiceFiscale();
			String password = StringUtils.isNotBlank(medasClientConfig.getPassword()) ? medasClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
			userDescription += ", HsmUsername: " + username + ", HsmCodiceFiscale: " + codiceFiscale + ", HsmPassword: " + password;
		}else if (clientConfig != null && clientConfig instanceof PkBoxConfig) {
			PkBoxConfig pkBoxClientConfig = (PkBoxConfig)clientConfig;
			String user = pkBoxClientConfig.getUser();
			String password = StringUtils.isNotBlank(pkBoxClientConfig.getPassword()) ? pkBoxClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
			String alias = pkBoxClientConfig.getAlias();
			String pin = StringUtils.isNotBlank(pkBoxClientConfig.getPin()) ? pkBoxClientConfig.getPin().replaceAll(".", "*") : "<vuota>";
			userDescription += ", HsmUser: " + user + ", HsmPassword: " + password + ", HsmAlias: " + alias + ", HsmPin: " + pin;
		}else if (clientConfig != null && clientConfig instanceof InfoCertConfig) {
			InfoCertConfig infocertClientConfig = (InfoCertConfig)clientConfig;
			String alias = infocertClientConfig.getAlias();
			userDescription += ", Hsmalias: " + alias;
		}
		userDescription += ")";
		return userDescription;
	}

}
