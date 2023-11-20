/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLoaddettemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaAllegato;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.XmlDatiPostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailLoaddettemail;
import it.eng.utility.formati.FormatiUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "AurigaAbilitazioniEmailDataSource")
public class AurigaAbilitazioniEmailDataSource extends AbstractServiceDataSource<PostaElettronicaBean, DettaglioPostaElettronicaBean> {

	private static Logger mLogger = Logger.getLogger(AurigaAbilitazioniEmailDataSource.class);

	@Override
	public DettaglioPostaElettronicaBean call(PostaElettronicaBean pInBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkIntMgoEmailLoaddettemailBean input = new DmpkIntMgoEmailLoaddettemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdemailin(pInBean.getIdEmail());
		input.setCinodoscrivaniain(getExtraparams().get("idNode"));
		input.setFlgsoloabilitazioniin(new Integer("1"));

		DmpkIntMgoEmailLoaddettemail loaddettemailBean = new DmpkIntMgoEmailLoaddettemail();
		StoreResultBean<DmpkIntMgoEmailLoaddettemailBean> output = loaddettemailBean.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		DettaglioPostaElettronicaBean result = new DettaglioPostaElettronicaBean();

		if (StringUtils.isNotBlank(output.getResultBean().getXmldatiemailout())) {
			XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
			XmlDatiPostaElettronicaBean lXmlDatiPostaElettronicaBean = lXmlUtility.unbindXml(output.getResultBean().getXmldatiemailout(),
					XmlDatiPostaElettronicaBean.class);

			if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_MAIL_REPLY")) {
				result.setAbilitaRispondi(lXmlDatiPostaElettronicaBean.getAbilitaRispondi());
				result.setAbilitaRispondiATutti(lXmlDatiPostaElettronicaBean.getAbilitaRispondiATutti());
			} else {
				result.setAbilitaRispondi("false");
				result.setAbilitaRispondiATutti("false");
			}

			List<DettaglioPostaElettronicaAllegato> lListAllegati = new ArrayList<DettaglioPostaElettronicaAllegato>();
			if(lXmlDatiPostaElettronicaBean.getListaAllegati() != null  && !lXmlDatiPostaElettronicaBean.getListaAllegati().isEmpty()) {
				for (DettaglioPostaElettronicaAllegato attachItem : lXmlDatiPostaElettronicaBean.getListaAllegati()) {
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					lMimeTypeFirmaBean.setFirmato(attachItem.getFlgFirmato() != null && attachItem.getFlgFirmato().equalsIgnoreCase("1") ? true : false);
					lMimeTypeFirmaBean.setFirmaValida(attachItem.getFlgFirmaValida() != null && attachItem.getFlgFirmaValida().equalsIgnoreCase("1") ? true : false);					
					lMimeTypeFirmaBean.setCorrectFileName(attachItem.getNomeFile());
					lMimeTypeFirmaBean.setMimetype(attachItem.getMimetype());
					lMimeTypeFirmaBean.setConvertibile(FormatiUtil.isConvertibile(getSession(), attachItem.getMimetype()));
					if (lMimeTypeFirmaBean.isFirmato()) {
						lMimeTypeFirmaBean
						.setTipoFirma(attachItem.getNomeFile().toUpperCase().endsWith("P7M") || attachItem.getNomeFile().toUpperCase().endsWith("TSD")
								? "CAdES_BES" : "PDF");						
					}
					lMimeTypeFirmaBean.setAlgoritmo(attachItem.getAlgoritmo() != null && !"".equals(attachItem.getAlgoritmo()) ? attachItem.getAlgoritmo() : null);
					lMimeTypeFirmaBean.setEncoding(attachItem.getEncoding() != null && !"".equals(attachItem.getEncoding()) ? attachItem.getEncoding() : null);
					attachItem.setInfoFile(lMimeTypeFirmaBean);
					lListAllegati.add(attachItem);
				}
			}
			result.setListaAllegati(lListAllegati);
			result.setUri(lXmlDatiPostaElettronicaBean.getUri());
			result.setAbilitaInoltraEmail(lXmlDatiPostaElettronicaBean.getAbilitaInoltraEmail());
			result.setAbilitaInoltraContenuti(lXmlDatiPostaElettronicaBean.getAbilitaInoltraContenuti());
			result.setAbilitaAssegna(lXmlDatiPostaElettronicaBean.getAbilitaAssegna());
			result.setAbilitaAssociaProtocollo(lXmlDatiPostaElettronicaBean.getAbilitaAssociaProtocollo());
			result.setAbilitaArchivia(lXmlDatiPostaElettronicaBean.getAbilitaArchivia());
			result.setAbilitaProtocolla(lXmlDatiPostaElettronicaBean.getAbilitaProtocolla());
			result.setAbilitaRepertoria(lXmlDatiPostaElettronicaBean.getAbilitaRepertoria());
			result.setAbilitaScaricaEmail(lXmlDatiPostaElettronicaBean.getAbilitaScaricaEmail());
			result.setAbilitaScaricaEmailSenzaBustaTrasporto(lXmlDatiPostaElettronicaBean.getAbilitaScaricaEmailSenzaBustaTrasporto());
			result.setAbilitaInoltraEmailSbustata(lXmlDatiPostaElettronicaBean.getAbilitaInoltraEmailSbustata());
			result.setAbilitaNotifInteropAggiornamento(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropAggiornamento());
			result.setAbilitaNotifInteropAnnullamento(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropAnnullamento());
			result.setAbilitaNotifInteropConferma(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropConferma());
			result.setAbilitaNotifInteropEccezione(lXmlDatiPostaElettronicaBean.getAbilitaNotifInteropEccezione());
			result.setAbilitaAzioneDaFare(lXmlDatiPostaElettronicaBean.getAbilitaAzioneDaFare());
			result.setAbilitaRiapri(lXmlDatiPostaElettronicaBean.getAbilitaRiapri());
			result.setAbilitaPresaInCarico(lXmlDatiPostaElettronicaBean.getAbilitaPresaInCarico());
			result.setAbilitaMessaInCarico(lXmlDatiPostaElettronicaBean.getAbilitaMessaInCarico());
			result.setAbilitaRilascio(lXmlDatiPostaElettronicaBean.getAbilitaRilascio());
			result.setAbilitaInvia(lXmlDatiPostaElettronicaBean.getAbilitaInvia());
			result.setAbilitaInvioCopia(lXmlDatiPostaElettronicaBean.getAbilitaInvioCopia());
			result.setAbilitaAssociaAInvio(lXmlDatiPostaElettronicaBean.getAbilitaAssociaAInvio());
			result.setAbilitaAnnullamentoInvio(lXmlDatiPostaElettronicaBean.getAbilitaAnnullamentoInvio());
			result.setAbilitaRegIstanzaAutotutela(lXmlDatiPostaElettronicaBean.getAbilitaRegIstanzaAutotutela());
			result.setAbilitaRegIstanzaCED(lXmlDatiPostaElettronicaBean.getAbilitaRegIstanzaCED());
			result.setAbilitaProtocollaAccessoAttiSUE(lXmlDatiPostaElettronicaBean.getAbilitaProtocollaAccessoAttiSUE());
			result.setAbilitaStampaFile(lXmlDatiPostaElettronicaBean.getAbilitaStampaFile());
			
			/**
			 *  Salvataggio dati per gestione chiusura mail risposte o inoltare
			 */
			result.setIdEmailInoltrate(lXmlDatiPostaElettronicaBean.getIdEmailInoltrate());
			result.setEmailPredecessoreIdEmail(lXmlDatiPostaElettronicaBean.getEmailPredecessoreIdEmail());
			result.setFlgMailPredecessoreStatoLavAperta(lXmlDatiPostaElettronicaBean.getFlgMailPredecessoreStatoLavAperta());
			result.setFlgMailPredecessoreConAzioneDaFare(lXmlDatiPostaElettronicaBean.getFlgMailPredecessoreConAzioneDaFare());
			result.setRuoloVsPredecessore(lXmlDatiPostaElettronicaBean.getRuoloVsPredecessore());
			result.setNroMailPredecessore(lXmlDatiPostaElettronicaBean.getNroMailPredecessore());
			result.setMailPredecessoriUnicaAzioneDaFare(lXmlDatiPostaElettronicaBean.getMailPredecessoriUnicaAzioneDaFare());
		}
		result.setIdEmail(pInBean.getIdEmail());
		result.setId(pInBean.getId());

		return result;
	}

}
