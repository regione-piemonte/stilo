/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSSignedData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2CheckuservscertificatofirmaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.shared.bean.WSEndPointArubaHsmBean;
import it.eng.client.DmpkCore2Checkuservscertificatofirma;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;
import it.eng.hsm.TsrGenerator;
import it.eng.hsm.pades.FirmaPadesApposizioneFileOpUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.sign.FileElaborate;
import it.eng.utility.ui.sign.SignerHashUtil;

@Controller
@RequestMapping("/UploadMultiHashSignerApplet")
public class UploadMultiHashSignerApplet {

	private static Logger logger = Logger.getLogger(UploadMultiHashSignerApplet.class);

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> post(@RequestParam("idDoc") String idDoc, @RequestParam("signedBean") String signedBean,
			@RequestParam("firmatario") String firmatario, @RequestParam(value = "pathFileTemp", required = false) String pathFileTemp,
			@RequestParam("versioneDoc") String versioneDoc, @RequestParam(value = "firmaPresente", required = false) String firmaPresente,
			@RequestParam(value = "firmaValida", required = false) String firmaValida,
			@RequestParam(value = "modalitaFirma", required = false) String modalitaFirma, 
			@RequestParam(value = "subjectX500Principal", required = false) String subjectX500Principal, final HttpSession session, HttpServletRequest servletrequest,
			HttpServletResponse servletresponse) throws Exception {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
		try {
			Cookie ck[] = servletrequest.getCookies();
			String jSessionIdFromCookie = "";
			if (ck != null){
				for(int i = 0; i < ck.length; i++){
					logger.debug("Trovato cookie: " + ck[i].getName() + " " + ck[i].getValue());
					if ((StringUtils.isNotBlank(ck[i].getName())) && ("JSESSIONID".equalsIgnoreCase(ck[i].getName()))) {
						jSessionIdFromCookie = ck[i].getValue(); 
					}
				}  
			} else {
				logger.debug("Nessuno cookies presente");
			}
			// Controllo che la sessione sia valida, però lo faccio solo se ho ricevuto l'id sessione del chiamante tramite cookie
			if (StringUtils.isNotBlank(jSessionIdFromCookie)) {
				String currentJSessionId = session.getId();
				if ((StringUtils.isNotBlank(currentJSessionId)) && !currentJSessionId.equalsIgnoreCase(jSessionIdFromCookie)) {
					// La sessione dell'utente che ha avviato l'upload e la sessione corrente non coincidono, sollevo l'eccezione
					logger.error("Errore: le sessione utente e la sessione della servlet non coincidono. JSESSIONID da cookie: " + jSessionIdFromCookie + ", JSESSIOID corrente: " + currentJSessionId);
					return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				} else {
					// Le sessioni coincidono, verifico se è valida
					LoginBean loginBean = session.getAttribute("LOGIN_INFO") != null ? (LoginBean) session.getAttribute("LOGIN_INFO") : null;
					if (loginBean == null || StringUtils.isBlank(loginBean.getUserid())) {
						// La sessione è scaduta
						logger.error("Errore: la sessione utente è scaduta. JSESSIONID da cookie: " + jSessionIdFromCookie + ", JSESSIOID corrente: " + currentJSessionId);
						return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			}
			
			SignerObjectBean bean = (SignerObjectBean) SerializationUtils.deserialize(Base64.decodeBase64(signedBean));
			
			logger.debug("Recupero il file originale");
			String[] infoFile = idDoc.split("#");
			String idFile = infoFile[0];
			String uriFile = URLDecoder.decode(infoFile[1], "UTF-8");
			String fileNameOriginale = infoFile[2];
			
			// Controllo la coerenza tra utente loggato e certificato di firma
			boolean controlloCoerenza = verificaCoerenzaUtenteLoggatoVsCertificatoFirma(idFile, subjectX500Principal, firmatario, servletrequest.getSession().getServletContext());
			if (!controlloCoerenza) {
				// La verifica di coerenza ha dato negativo
				return new ResponseEntity<String>("VERIFICA_CONFORMITA_CERTIFICATO_FIRMA_ERROR", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
			File lFileFirmato = null;
			boolean isCades = true;
			if (bean.getFileType() != null && bean.getFileType() == SignerType.PDF) {
				FileElaborate fileElaborate = (FileElaborate) servletrequest.getSession().getServletContext().getAttribute("fileElaborate" + idFile);
				// Verifico se la firma è stata eseguita mediante la firma grafica di file op
				if (firmaEseguitaConFirmaGraficaFileop(servletrequest.getSession().getServletContext(), idFile)) {
					String nomeCampoFirma = (String) servletrequest.getSession().getServletContext().getAttribute("nomeCampoFirma" + idFile);	
					servletrequest.getSession().getServletContext().removeAttribute("nomeCampoFirma" + idFile);
					String hashFirmata = com.itextpdf.text.pdf.codec.Base64.encodeBytes(bean.getSignerInfo());
					byte[] bytesFileFirmato = FirmaPadesApposizioneFileOpUtil.completaFirma(fileElaborate.getUnsigned().toURI().toString(), fileNameOriginale, nomeCampoFirma, hashFirmata);
					String urifileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(bytesFileFirmato));
					lFileFirmato = StorageImplementation.getStorage().getRealFile(urifileFirmato);
					fileElaborate.setSigned(lFileFirmato);
				} else {
					lFileFirmato = File.createTempFile("sign", "");
					File lFileDaFirmare = StorageImplementation.getStorage().extractFile(uriFile);
					servletrequest.getSession().getServletContext().removeAttribute("fileElaborate" + idFile);
					fileElaborate.setUnsigned(lFileDaFirmare);
					fileElaborate.setSigned(lFileFirmato);
					SignerHashUtil.attachPdf(bean.getSignerInfo(), fileElaborate);
					isCades = false;
				}
			} else {
				lFileFirmato = File.createTempFile("sign", "");
				servletrequest.getSession().getServletContext().removeAttribute("fileElaborate" + idFile);
				if (!fileNameOriginale.toLowerCase().endsWith(".p7m")) {
					fileNameOriginale = fileNameOriginale + ".p7m";
				}
				InputStream lInputStream = StorageImplementation.getStorage().extract(uriFile);
				FileOutputStream outputStream = FileUtils.openOutputStream(lFileFirmato);
				byte[] originalData = IOUtils.toByteArray(lInputStream);
				// caso firma parallela
				if (modalitaFirma != null && modalitaFirma.equalsIgnoreCase("congiunta")) {
					// il file era firmato in CAdES e con una firma CAdES valida -> parallela
					if ((firmaPresente != null && firmaPresente.equalsIgnoreCase("true")) && (firmaValida != null && firmaValida.equalsIgnoreCase("true"))) {
						CMSSignedData data = SignerHashUtil.createSignedData(bean, originalData);
						SignerHashUtil.addSignerInfo(originalData, data, outputStream);
					}
					// il file non era firmato in CAdES, o aveva una firma CAdES non valida -> verticale
					else {
						CMSSignedData data = SignerHashUtil.signerP7M(bean, originalData);
						ByteArrayInputStream bis = new ByteArrayInputStream(data.getEncoded());
						SignerHashUtil.attachP7M(originalData, bis, outputStream);
					}
				}
				// caso firma verticale
				else {
					CMSSignedData data = SignerHashUtil.signerP7M(bean, originalData);
					ByteArrayInputStream bis = new ByteArrayInputStream(data.getEncoded());
					SignerHashUtil.attachP7M(originalData, bis, outputStream);
				}
				outputStream.flush();
				outputStream.close();
				lInputStream.close();
			}
			
			// Inizio apposizione marca
			logger.debug("Path lFileFirmato prima della marca: " + lFileFirmato.getPath());
			String uriFileFirmato = null;
			String tipoMarca = null;
			try {
				WSEndPointArubaHsmBean wsEndPointArubaHsmBean = (WSEndPointArubaHsmBean) SpringAppContext.getContext().getBean("WSEndPointArubaHsm");
				if (wsEndPointArubaHsmBean != null && StringUtils.isNotBlank(wsEndPointArubaHsmBean.getTipoMarcaForzataPerApplet())) {
					if ("true".equalsIgnoreCase(wsEndPointArubaHsmBean.getTipoMarcaForzataPerApplet())){
						tipoMarca = "HSM";
					} else {
						tipoMarca = wsEndPointArubaHsmBean.getTipoMarcaForzataPerApplet();
					}
				}
			} catch(Exception e) {
				tipoMarca = null;
				logger.error("Errore nella lettura di WSEndPointArubaHsm: " + e.getMessage(), e);
			}
			
			boolean isFileDaMarcare = false;
			if(tipoMarca != null && (tipoMarca.equalsIgnoreCase("HSM") || tipoMarca.equalsIgnoreCase("SERVER"))){
				isFileDaMarcare = true;
			}
			
			if (isFileDaMarcare || bean.marca) {
				boolean useAuth = false;
				WSEndPointArubaHsmBean wsEndPointArubaHsmBean = (WSEndPointArubaHsmBean) SpringAppContext.getContext().getBean("WSEndPointArubaHsm");
				if(wsEndPointArubaHsmBean!=null && wsEndPointArubaHsmBean.getMarcaServiceUid()!=null && 
						!wsEndPointArubaHsmBean.getMarcaServiceUid().equalsIgnoreCase("")){
					useAuth = true;
				}
				TsrGenerator tsr = new TsrGenerator(wsEndPointArubaHsmBean.getMarcaServiceUrl(), wsEndPointArubaHsmBean.getMarcaServiceUid(),
						wsEndPointArubaHsmBean.getMarcaServicePwd(), useAuth);
				// ProxyUtil.initProxyConnection();
				
				byte[] byteFileMarcato = null;
				if( isCades ){
					byteFileMarcato = tsr.addMarca(lFileFirmato);
				} else {
					byteFileMarcato = tsr.addMarcaPdf(lFileFirmato);
				}
				uriFileFirmato = StorageImplementation.getStorage().storeStream(new ByteArrayInputStream(byteFileMarcato));
			} else {
				uriFileFirmato = StorageImplementation.getStorage().store(lFileFirmato, new String[] {});
			}
			logger.debug("uriFileFirmato dopo marca: " + uriFileFirmato);
			String fileFirmato = StringEscapeUtils.escapeJava(java.net.URLEncoder.encode(fileNameOriginale, "UTF-8") + "#" + uriFileFirmato + "#" + idFile + "#" + (isFileDaMarcare || bean.marca));
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(fileFirmato);
			return new ResponseEntity<String>(lStringBuffer.toString(), responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			return new ResponseEntity<String>("OPERATION_ERROR", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private static boolean verificaCoerenzaUtenteLoggatoVsCertificatoFirma(String idFile, String subjectX500Principal, String firmatario, ServletContext servletContext) throws StoreException {
		// Faccio il controllo se ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG sia true
		String attivaCtrlUserVsCertifFirmaDig = (String) servletContext.getAttribute("ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG_FOR_APPLET" + idFile);
		String idUser = (String) servletContext.getAttribute("SIGNATURE_ID_USER_FOR_APPLET" + idFile);
		String userSchema = (String) servletContext.getAttribute("SIGNATURE_USER_SCHEMA_FOR_APPLET" + idFile);
		// Rimuovo gli attributi non più necessari
		servletContext.removeAttribute("ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG_FOR_APPLET" + idFile);
		servletContext.removeAttribute("SIGNATURE_ID_USER_FOR_APPLET" + idFile);
		servletContext.removeAttribute("SIGNATURE_USER_SCHEMA_FOR_APPLET" + idFile);
		if (Boolean.parseBoolean(attivaCtrlUserVsCertifFirmaDig)) {
			// Verifico la coerenza tra utente loggato e certificato di firma utilizzato
			boolean resultOk = false;
			try {
				DmpkCore2Checkuservscertificatofirma lDmpkCore2Checkuservscertificatofirma = new DmpkCore2Checkuservscertificatofirma();
				DmpkCore2CheckuservscertificatofirmaBean lDmpkCore2CheckuservscertificatofirmaBean = new DmpkCore2CheckuservscertificatofirmaBean();
				// Estraggo i dati da mandare alla store di verifica
				lDmpkCore2CheckuservscertificatofirmaBean.setIduserin(new BigDecimal(idUser));
				lDmpkCore2CheckuservscertificatofirmaBean.setDaticertificatofirmain(StringUtils.isNotBlank(subjectX500Principal) ? subjectX500Principal : firmatario);
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(userSchema);
				Locale locale = new Locale("it", "IT");
				StoreResultBean<DmpkCore2CheckuservscertificatofirmaBean> res = lDmpkCore2Checkuservscertificatofirma.execute(locale, lSchemaBean, lDmpkCore2CheckuservscertificatofirmaBean);
				if (res.isInError()) {
					logger.error("Il certificato di firma non corrisponde al nominativo dell'utente loggato");
					logger.error("Utente loggato: " + idUser);
					logger.error("Certificato di firma: " + subjectX500Principal);
				} else {
					resultOk = true;
				}
			} catch (Exception e) {
				logger.error("Errore nella verifica della corrispondenza tra nominativo utente e certificato di firma");
				logger.error("Utente loggato: " + idUser);
				logger.error("Certificato di firma: " + subjectX500Principal);
			}
			return resultOk;
		} else {
			// Non eseguo nessun controllo
			return true;
		}
	}
	
	private static boolean firmaEseguitaConFirmaGraficaFileop(ServletContext servletContext, String idFile) {
		String apposizioneGraficaFirmaTramiteFileop = (String) servletContext.getAttribute("apposizioneGraficaFirmaTramiteFileop" + idFile);
		servletContext.removeAttribute("apposizioneGraficaFirmaTramiteFileop" + idFile);
		if (StringUtils.isNotBlank(apposizioneGraficaFirmaTramiteFileop) && "true".equalsIgnoreCase(apposizioneGraficaFirmaTramiteFileop)) {
			return true;
		}
		return false;
	}
}
