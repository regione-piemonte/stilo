/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.CertificatoHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.TipoFirmaHsm;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.CalcolaFirmaDatasource;
import it.eng.hsm.HsmBaseUtility;
import it.eng.hsm.HsmClientFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "FirmaHsmDataSource")
public class FirmaHsmDataSource extends AbstractDataSource<FirmaHsmBean, FirmaHsmBean> {

	private static final Logger log = Logger.getLogger(FirmaHsmDataSource.class);

	@Override
	public FirmaHsmBean get(FirmaHsmBean bean) throws Exception {
		return null;
	}

	@Override
	public FirmaHsmBean add(FirmaHsmBean bean) throws Exception {
		return null;
	}

	@Override
	public FirmaHsmBean remove(FirmaHsmBean bean) throws Exception {
		return null;
	}

	@Override
	public FirmaHsmBean update(FirmaHsmBean bean, FirmaHsmBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<FirmaHsmBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(FirmaHsmBean bean) throws Exception {
		return null;
	}
	
	public FirmaHsmBean richiediCodiceOtp(FirmaHsmBean bean) {
		// Ricavo il tipo hsm con cui effettuare la firma HSM
		String tipoHsm = getTipoHsm(bean.getProviderHsmFromPreference());
		bean.setHsmType(tipoHsm);

		// Richiedo il codice OTP
		OtpResponseBean otpResponseBean = new OtpResponseBean();
		try {
			otpResponseBean = HsmBaseUtility.richiediCodiceOTP(bean, getSession());
			bean.setEsitoOk(true);
		} catch (Exception e) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String userDescription = "(UtenteLoggato: " + loginBean.getDenominazione() + ", Delega: " + loginBean.getDelegaDenominazione() + ")";
			try {
				userDescription = HsmBaseUtility.getHsmUserDescription(HsmClientFactory.getHsmClient(getSession(), bean), getSession());
			} catch (Exception e1) {
			}
			log.error("Errore nella richiesta del codice OTP" + " " + userDescription, e);
			bean.setEsitoOk(false);
			String messaggioErrore;
			if (StringUtils.isNotBlank(e.getMessage())) {
				messaggioErrore = e.getMessage();
			} else {
				messaggioErrore = "Richiesta codice OTP non riuscita";
			}
			bean.setErrorMessage(messaggioErrore);
		}
		return bean;
	}
	
	public FirmaHsmBean richiediListaCertificati(FirmaHsmBean bean) {
		// Ricavo il tipo hsm con cui effettuare la firma HSM
		String tipoHsm = getTipoHsm(bean.getProviderHsmFromPreference());
		bean.setHsmType(tipoHsm);

		// Richiedo la lista dei certificati
		CertResponseBean certResponseBean = new CertResponseBean();
		try {
			certResponseBean = HsmBaseUtility.richiediListaCertificati(bean, getSession());
			List<CertificatoHsmBean> listaCertificatiHsm = new ArrayList<CertificatoHsmBean>();
			if ((certResponseBean.getCertList() != null) || (certResponseBean.getCertList().size() > 0)){
				for (CertBean certBean : certResponseBean.getCertList()) {
					CertificatoHsmBean certificatoHsmBean = new CertificatoHsmBean();
					certificatoHsmBean.setDescrizione(certBean.getDescrizione());
					certificatoHsmBean.setPotereDiFirma(certBean.getSignaturePower());
					certificatoHsmBean.setSerialNumber(certBean.getSerialNumber());
					certificatoHsmBean.setCertId(certBean.getCertId());
					certificatoHsmBean.setOtps(StringUtils.join(certBean.getOtps(), ";"));
					listaCertificatiHsm.add(certificatoHsmBean);
				}
				bean.setListaCertificati(listaCertificatiHsm);
				bean.setEsitoOk(true);
			}else{
				bean.setEsitoOk(false);
				bean.setErrorMessage("Non è stato trovato nessun certificato associato all'utente");
			}
				
		} catch (Exception e) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String userDescription = "(UtenteLoggato: " + loginBean.getDenominazione() + ", Delega: " + loginBean.getDelegaDenominazione() + ")";
			try {
				userDescription = HsmBaseUtility.getHsmUserDescription(HsmClientFactory.getHsmClient(getSession(), bean), getSession());
			} catch (Exception e1) {
			}
			log.error("Errore nella richiesta della lista dei certificati" + " " + userDescription, e);
			bean.setEsitoOk(false);
			String messaggioErrore;
			if (StringUtils.isNotBlank(e.getMessage())) {
				messaggioErrore = e.getMessage();
			} else {
				messaggioErrore = "Richiesta certificati utente non riuscita";
			}
			bean.setErrorMessage(messaggioErrore);
		}
		return bean;
	}

	public FirmaHsmBean firmaHsmMultiplaFileGeneratodaModello(FirmaHsmBean bean) throws Exception {
		FirmaHsmBean result = firmaHsmMultipla(bean);
		// Dato che non provengo da FirmaUtility non passo per aggiornaInfoFileFirmati di CalcolaFirmaDatasource
		// Devo quindi aggiornare a mano le informazioni su firmaExtraAuriga e bustaCrittograficaFattaDaAuriga
		result = CalcolaFirmaDatasource.aggiornaInfoFileFirmatiFileGeneratiDaModello(result);
		return result;
	}

	public FirmaHsmBean firmaHsmMultipla(FirmaHsmBean bean) throws Exception {
		// Ricavo il tipo hsm con cui effettuare la firma HSM
		String tipoHsm = getTipoHsm(bean.getProviderHsmFromPreference());
		bean.setHsmType(tipoHsm);
		
		boolean firmaAvanzamentoIterAtti = Boolean.parseBoolean(getExtraparams().get("FIRMA_AVANZAMENTO_ITER_ATTI"));
		boolean isFirmaSuImpronta = settaTipoFirmaDaEffettuareSuFileDaFirmare(bean, firmaAvanzamentoIterAtti);

		boolean skipControlloFirmaBusta = Boolean.parseBoolean(getExtraparams().get("skipControlloFirmaBusta"));
		bean.setSkipControlloFirmaBusta(skipControlloFirmaBusta);
		try {
			if (isFirmaSuImpronta) {
				bean = HsmBaseUtility.firmaMultiplaHash(bean, getSession());
			} else {
				bean = HsmBaseUtility.firmaMultiplaFile(bean, getSession());
			}
			bean.setEsitoOk(true);
		} catch (Exception e) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String userDescription = "(UtenteLoggato: " + loginBean.getDenominazione() + ", Delega: " + loginBean.getDelegaDenominazione() + ")";
			try {
				userDescription = HsmBaseUtility.getHsmUserDescription(HsmClientFactory.getHsmClient(getSession(), bean), getSession());
			} catch (Exception e1) {
			}
			log.error("Errore nella firma multipla hash" + " " + userDescription, e);
			bean.setEsitoOk(false);
			String messaggioErrore;
			if (StringUtils.isNotBlank(e.getMessage())) {
				messaggioErrore = "Firma remota non riuscita: " + e.getMessage();
			} else {
				messaggioErrore = "Firma remota non riuscita";
			}
			bean.setErrorMessage(messaggioErrore);
		}
		return bean;
	}

	private boolean settaTipoFirmaDaEffettuareSuFileDaFirmare(FirmaHsmBean bean, boolean firmaAvanzamentoIterAtti) throws Exception {
		// Varibile che vale true se si tratta di una firma su impronta
		boolean isFirmaSuImpronta = true;
		// Estraggo il valore di forzatura eventuale dovuto al fatto che si arriva dagli atti
		String hsmTipoFirma = getExtraparams().get("HSM_TIPO_FIRMA_FORZATO");
		// Estraggo il parametro per la gestione alllegati firmati parte integrante negli atti
		boolean abilGestFirmaAllegatiFirmatiPIAtto = Boolean.parseBoolean(getExtraparams().get("GESTIONE_FIRMA_ALLEGATI_FIRMATI_PI_ATTO"));

		if (StringUtils.isBlank(hsmTipoFirma)) {
			// Allora devo prelevare il valore relativo dal DB perchè non è stato forzato
			// Estraggo il tipo di firma di default
			hsmTipoFirma = ParametriDBUtil.getParametroDB(getSession(), "HSM_TIPO_FIRMA");
		}

		boolean firmaCongiunta = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "FIRMA_CONGIUNTA");

		// Dirotto la firma analizzando il tipo di firma impostata, l'estensione del file e se il file è prefirmato e la presenza di una marcatura temporale nella busta crittografica
		// Per comodità setto tutti i tipi con firma HASH, poi se si tratta di firma su file cambio il tipo alla fine 
		List<FileDaFirmare> listaFileDaFirmare = bean.getListaFileDaFirmare();
		for (FileDaFirmare fileDaFirmare : listaFileDaFirmare) {
			// Verifico se devo apporre la firma tramite file op
			if (hsmTipoFirma != null && hsmTipoFirma.equalsIgnoreCase("PAdES") && fileDaFirmare.getListaInformazioniFirmaGrafica() != null && fileDaFirmare.getListaInformazioniFirmaGrafica().size() > 0) {
				log.debug("Si tratta di una firma che va apposta tramite fileop");
				fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_PADES_FILEOP);
			} else {
				// Se sono in iter atti a sto firmando il primario non devo fare nessuna verifica sulle validità delle firme esistenti, 
				// controllo solo se la firma impostata è coerente con il tipo di firma da fare
				boolean firmaPrimarioInAvanzamentoIterAtti = firmaAvanzamentoIterAtti && (fileDaFirmare.getIsFilePrincipaleAtto() != null && fileDaFirmare.getIsFilePrincipaleAtto());
				// Se la busta crittografica non è creata da Auriga vado in verticale, a meno che non sia stata marcata da auriga
				if (!firmaPrimarioInAvanzamentoIterAtti && fileDaFirmare.getInfoFile() != null && fileDaFirmare.getInfoFile().isFirmato() && fileDaFirmare.getInfoFile().getInfoFirmaMarca() != null && !fileDaFirmare.getInfoFile().getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga() && !fileDaFirmare.getInfoFile().getInfoFirmaMarca().isMarcaTemporaleAppostaDaAuriga()){
					log.debug("La busta crittografica non è creata da Auriga, uso la CAdES verticale");
					fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);	
				// Verifico la presenza di una marca temporale esterna ad auriga
				} else if (!firmaPrimarioInAvanzamentoIterAtti && fileDaFirmare.getInfoFile() != null && fileDaFirmare.getInfoFile().getInfoFirmaMarca() != null && fileDaFirmare.getInfoFile().getInfoFirmaMarca().getDataOraMarcaTemporale() != null && !fileDaFirmare.getInfoFile().getInfoFirmaMarca().isMarcaTemporaleAppostaDaAuriga()){
					log.debug("La busta crittografica è marcata temporalmente, uso la CAdES verticale");
					fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
				// Se il pdf è protetto vado in cades verrticale
				} else if (!firmaPrimarioInAvanzamentoIterAtti && fileDaFirmare.getInfoFile() != null && fileDaFirmare.getInfoFile().isPdfProtetto()) {
					log.debug("Si tratta di un pdf protetto, uso la CAdES verticale");
					fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
				} else {
					// Verifico se la firma della busta crittografica è valida
					boolean firmaValida = fileDaFirmare.getInfoFile().isFirmaValida();
					if (fileDaFirmare.getInfoFile() != null && fileDaFirmare.getInfoFile().getInfoFirmaMarca() != null) {
						firmaValida = !fileDaFirmare.getInfoFile().getInfoFirmaMarca().isFirmeNonValideBustaCrittografica();
					}
					if (firmaPrimarioInAvanzamentoIterAtti) {
						// Se sono in avanzamento iter atti e sto firmando il primario non mi devo preoccupare della validità delle firme, le consiidero tutte valide
						firmaValida = true;
					}
					if ((hsmTipoFirma != null) && (hsmTipoFirma.equalsIgnoreCase("PAdES"))) {
						bean.setTipofirma("PAdES");
						log.debug("La firma impostata è PAdES");
						// La firma di default è quella Pades. Verifico l'estensione del file da firmare
						if (fileDaFirmare.getNomeFile().toLowerCase().endsWith(".pdf")) {
							log.debug("Il file da firmare è un pdf");
							if ((fileDaFirmare.getInfoFile() != null) && ((!fileDaFirmare.getInfoFile().isFirmato()) || (fileDaFirmare.getInfoFile().isFirmato()) && (firmaValida))) {
								// Il file da firmare è pdf, non firmato o con firma preesistente valida. Posso fare la Pades
								log.debug("Il file da firmare è non firmato o con firma valida, uso la PAdES");
								fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_PADES);
							} else {
								// Il file ha almeno una firma non valida. devo usare la firma Cades verticale
								// bean.setTipofirma("CAdES");
								log.debug("Il file da firmare contiene una firma non valida, uso la CAdES verticale");
								fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
							}
						} else {
							// Il file da firmare non è pdf, non posso fare la pades e quindi faccio la cedes
							log.debug("Il file da firmare non è un pdf, devo usare la CAdES");
							if (firmaCongiunta) {
								// Sono in firma congiunta Cades. I file che non hanno firma valida o che non sono firmati
								// devono essere firmati in modo verticale
								log.debug("L'utilizzo della firma congiunta è impostato a true");
								if ((fileDaFirmare.getInfoFile() != null) && (fileDaFirmare.getInfoFile().isFirmato()) && (firmaValida)) {
									log.debug("Il file è firmato e valido, uso la CAdES congiunta");
									fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_CONGIUNTA);
								} else {
									log.debug("Il file non è firmato oppure firmato ma non valido, uso la CAdES verticale");
									fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
								}
							} else {
								// Di default utilizzo la firma verticale. Va bene in ogni caso
								log.debug("L'utilizzo della firma congiunta è impostato a false, uso la CAdES verticale");
								fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
							}
						}
					} else {
						// La firma di default è quella Cades. Devo vedere se fare quella congiunta o no
						log.debug("La firma impostata è CAdES");
						bean.setTipofirma("CAdES");
						if (firmaCongiunta) {
							// Sono in firma congiunta Cades. I file che non hanno firma valida o che non sono firmati
							// devono essere firmati in modo verticale
							log.debug("L'utilizzo della firma congiunta è impostato a true");
							if ((fileDaFirmare.getInfoFile() != null) && (fileDaFirmare.getInfoFile().isFirmato()) && (firmaValida) && (fileDaFirmare.getInfoFile().getTipoFirma().startsWith("CAdES"))) {
								log.debug("Il file è firmato e valido, uso la CAdES congiunta");
								fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_CONGIUNTA);
							} else {
								log.debug("Il file non è firmato oppure firmato ma non valido oppure firmato ma non in CAdES, uso la CAdES verticale");
								fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
							}
						} else {
							// Di default utilizzo la firma verticale. Va bene in ogni caso
							log.debug("L'utilizzo della firma congiunta è impostato a false, uso la CAdES verticale");
							fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
						}
					}
				}
			}
		}
		// Verifico se il client hsm lavora su impronte o su file
		Hsm hsmClient = HsmClientFactory.getHsmClient(getSession(), bean);
		if (Boolean.valueOf(hsmClient.getHsmConfig().getClientConfig().getFirmaRemotaConPassaggioFile())) {
			// Si tratta di una firma su file, cambio tutti i tipi
			isFirmaSuImpronta = false;
			for (FileDaFirmare fileDaFirmare : listaFileDaFirmare) {
				if (TipoFirmaHsm.HASH_PADES.equals(fileDaFirmare.getTipoFirmaHsm())) {
					fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.FILE_PADES);
				} else if (TipoFirmaHsm.HASH_CADES_CONGIUNTA.equals(fileDaFirmare.getTipoFirmaHsm())) {
					fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_CONGIUNTA);
				}else if (TipoFirmaHsm.HASH_CADES_VERTICALE.equals(fileDaFirmare.getTipoFirmaHsm())) {
					fileDaFirmare.setTipoFirmaHsm(TipoFirmaHsm.HASH_CADES_VERTICALE);
				}
			}
		}
		return isFirmaSuImpronta;
	}
	
	private String getTipoHsm(String providerHsm) {
		String xmlParametriHsm;
		if (StringUtils.isNotBlank(providerHsm)) {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(getSession(), "HSM_PARAMETERS_" + providerHsm);
		} else {
			xmlParametriHsm = ParametriDBUtil.getParametroDB(getSession(), "HSM_PARAMETERS");
		}
		
		XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
		try {
			ClientConfig clientConfig = lXmlUtility.unbindXml(xmlParametriHsm, ClientConfig.class);
			
			if (StringUtils.isNotBlank(clientConfig.getHsmType())) {
				return clientConfig.getHsmType();
			} else {
				return ParametriDBUtil.getParametroDB(getSession(), "TIPO_HSM");
			}
		} catch(Exception e) {
			log.error("Errore nel recupero di ClientConfig per determinare il providerHsm. Uso quello nel parametro DB TIPO_HSM", e);
			return ParametriDBUtil.getParametroDB(getSession(), "TIPO_HSM");
		}
	}

}
