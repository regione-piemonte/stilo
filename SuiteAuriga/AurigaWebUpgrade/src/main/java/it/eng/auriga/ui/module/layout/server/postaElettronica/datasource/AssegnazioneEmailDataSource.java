/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAssegnaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaemailnotassinvioccBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.TipoIdBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.AssegnazioneEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkIntMgoEmailAssegnaemail;
import it.eng.client.DmpkIntMgoEmailPreparaemailnotassinviocc;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AssegnazioneEmailDataSource")
public class AssegnazioneEmailDataSource extends AbstractDataSource<AssegnazioneEmailBean, AssegnazioneEmailBean> {

	private static Logger mLogger = Logger.getLogger(AssegnazioneEmailDataSource.class);

	@Override
	public AssegnazioneEmailBean add(AssegnazioneEmailBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		Boolean isMassivo = getExtraparams() != null && getExtraparams().get("isMassivo") != null
				&& getExtraparams().get("isMassivo").equals("1") ? true : false;

		HashMap<String, String> errorMessages = null;
		Set<String> listaEmailInvioCC = new HashSet<String>();
		
		for (PostaElettronicaBean email : bean.getListaRecord()) {

			if (email.getStatoLavorazione() != null && email.getStatoLavorazione().equalsIgnoreCase("lavorazione conclusa")) {
				if (errorMessages == null) {
					errorMessages = new HashMap<String, String>();
					errorMessages.put(email.getIdEmail(), "E-mail già archiviata: operazione non consentita");
				}
			} else {
				DmpkIntMgoEmailAssegnaemailBean input = new DmpkIntMgoEmailAssegnaemailBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdemailin(email.getIdEmail());
				input.setAssegnatarixmlin(getXmlAssegnatari(bean));

				DmpkIntMgoEmailAssegnaemail dmpkIntMgoEmailAssegnaemail = new DmpkIntMgoEmailAssegnaemail();
				StoreResultBean<DmpkIntMgoEmailAssegnaemailBean> output = dmpkIntMgoEmailAssegnaemail.execute(getLocale(), loginBean, input);

				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					if (output.isInError()) {
						mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
					if (errorMessages == null) {
						errorMessages = new HashMap<String, String>();
						errorMessages.put(email.getIdEmail(), output.getDefaultMessage());
					}
				} else {
					if(isMassivo != null && isMassivo ) {
						listaEmailInvioCC.add(email.getIdEmail());
					}
				}
			}
		}

		if (errorMessages != null) {
			if(isMassivo != null && isMassivo) {
				invioNotificheAssegnazione(bean, listaEmailInvioCC, isMassivo);
			}
			bean.setErrorMessages(errorMessages);
		} else {
			HashMap<String, String> errMsg = invioNotificheAssegnazione(bean, listaEmailInvioCC, isMassivo);
			if (errMsg != null && errMsg.size() > 0) {
				bean.setErrorMessages(errMsg);
			}
		}

		return bean;
	}
	
	private HashMap<String, String> invioNotificheAssegnazione(AssegnazioneEmailBean bean, 
			Set<String> listaEmailInvioCC, Boolean isMassivo) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<AssegnatariBean> listaAssegnatari = getListaAssegnatari(bean);
		HashMap<String, String> errorMessages = new HashMap<String, String>();
			
		/**
		 * Assegnazione per conoscenza di una EMAIL, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_EMAIL
		 */
		
		 if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_EMAIL") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_EMAIL"))) {
			if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
				sendNotificaAssegnazione(bean, lAurigaLoginBean, token, idUserLavoro, lXmlUtilitySerializer, 
						listaAssegnatari, listaEmailInvioCC, isMassivo);
			} else {
				mLogger.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
						+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_UD");
			}
		}
			
		return errorMessages;
	}
	
	public String getXmlAssegnatari(AssegnazioneEmailBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaAssegnatari(bean));
	}

	private List<AssegnatariBean> getListaAssegnatari(AssegnazioneEmailBean bean) throws Exception {
		List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
		if (bean.getListaAssegnazioni() != null) {
			for (AssegnazioneBean assegnazioneBean : bean.getListaAssegnazioni()) {
				AssegnatariBean lAssegnatariBean = new AssegnatariBean();
				lAssegnatariBean.setTipo(getTipoAssegnatario(assegnazioneBean));
				if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {								
					lAssegnatariBean.setIdSettato(assegnazioneBean.getGruppo());										
				} else {					
					lAssegnatariBean.setIdSettato(assegnazioneBean.getIdUo());					
				}	
				lAssegnatariBean.setMessaggioInvio(bean.getMessaggio());
				if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
					listaAssegnatari.add(lAssegnatariBean);
				}
			}
		}
		return listaAssegnatari;
	}

	private TipoAssegnatario getTipoAssegnatario(DestInvioBean lDestInvioBean) {
		TipoAssegnatario[] tipiAssegnatari = TipoAssegnatario.values();
		for (TipoAssegnatario tipoAssegnatario : tipiAssegnatari) {
			if (tipoAssegnatario.toString().equals(lDestInvioBean.getTypeNodo())) {
				return tipoAssegnatario;
			}
		}
		return null;
	}
	
	private void sendNotificaAssegnazione(AssegnazioneEmailBean bean, AurigaLoginBean lAurigaLoginBean, String token, String idUserLavoro,
			XmlUtilitySerializer lXmlUtilitySerializer, List<AssegnatariBean> listaAssegnatari,
			Set<String> listaEmailInvioCC, Boolean isMassivo)
					throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		DmpkIntMgoEmailPreparaemailnotassinvioccBean lInputBean = new DmpkIntMgoEmailPreparaemailnotassinvioccBean();
		lInputBean.setCodidconnectiontokenin(token);
		lInputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		lInputBean.setAssinvioccin("assegnazione_competenza");

		List<TipoIdBean> listaUdFolder = new ArrayList<TipoIdBean>();
		for (PostaElettronicaBean record : bean.getListaRecord()) {
			/**
			 * Se nell'assegnazione multipla alcune mail NON sono state assegnate si mettono nell'xml SOLO quelle assegnate.
			 */
			if(isMassivo != null && isMassivo) {
				if(canAddEmailUdFolderXml(listaEmailInvioCC, record.getIdEmail())) {
					TipoIdBean udFolder = new TipoIdBean();
					udFolder.setTipo("E");
					udFolder.setId(record.getIdEmail());
					listaUdFolder.add(udFolder);
				}
			} else {
				TipoIdBean udFolder = new TipoIdBean();
				udFolder.setTipo("E");
				udFolder.setId(record.getIdEmail());
				listaUdFolder.add(udFolder);
			}
		}
		lInputBean.setUdfolderxmlin(lXmlUtilitySerializer.bindXmlList(listaUdFolder));

		List<TipoIdBean> listaDestAssInvioCC = new ArrayList<TipoIdBean>();
		for (int i = 0; i < listaAssegnatari.size(); i++) {
			TipoIdBean destAssInvioCC = new TipoIdBean();
			destAssInvioCC.setTipo(listaAssegnatari.get(i).getTipo().toString());
			destAssInvioCC.setId(listaAssegnatari.get(i).getIdSettato());
			listaDestAssInvioCC.add(destAssInvioCC);
		}
		lInputBean.setDestassinvioccxmlin(lXmlUtilitySerializer.bindXmlList(listaDestAssInvioCC));

		lInputBean.setCodmotivoinvioin(null);
		lInputBean.setMotivoinvioin(null);
		lInputBean.setMessaggioinvioin(bean.getMessaggio());
		lInputBean.setLivelloprioritain(null);

		DmpkIntMgoEmailPreparaemailnotassinviocc preparaemailnotassinviocc = new DmpkIntMgoEmailPreparaemailnotassinviocc();
		StoreResultBean<DmpkIntMgoEmailPreparaemailnotassinvioccBean> lStoreResultBean = preparaemailnotassinviocc.execute(getLocale(), lAurigaLoginBean,
				lInputBean);

		if (lStoreResultBean.isInError()) {
			String message = "Fallito invio notifica e-mail dell'assegnazione";
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				mLogger.error("Fallito invio notifica e-mail dell'assegnazionem per il seguente motivo: " + lStoreResultBean.getDefaultMessage());
				message += " per il seguente motivo: " + lStoreResultBean.getDefaultMessage();
			}
			addMessage(message, "", MessageType.WARNING);
		} else {
			if (lStoreResultBean.getResultBean().getFlgemailtosendout() != null && lStoreResultBean.getResultBean().getFlgemailtosendout().intValue() == 1) {
				SenderBean sender = new SenderBean();
				sender.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
				sender.setAccount(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				sender.setAddressFrom(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				List<String> addressTo = new ArrayList<String>();
				StringReader sr = new StringReader(lStoreResultBean.getResultBean().getIndirizzidestemailout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						addressTo.add(v.get(0));
					}
				}
				sender.setAddressTo(addressTo);
				sender.setSubject(lStoreResultBean.getResultBean().getOggemailout());
				sender.setBody(lStoreResultBean.getResultBean().getBodyemailout());
				sender.setIsHtml(true);
				sender.setIsPec(false);
				try {
					EmailSentReferenceBean lEmailSentReferenceBean = AurigaMailService.getMailSenderService().send(getLocale(), sender);
					String notSentMails = null;
					for (SenderBean lSenderBean : lEmailSentReferenceBean.getSentMails()) {
						if (!lSenderBean.getIsSent()) {
							if (notSentMails == null) {
								notSentMails = lSenderBean.getAddressTo().get(0);
							} else {
								notSentMails += ", " + lSenderBean.getAddressTo().get(0);
							}
						}
					}
					if (notSentMails != null) {
						mLogger.warn("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails);
						addMessage("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
					}
				} catch (Exception e) {
					mLogger.error("Fallito invio notifica e-mail dell'assegnazione:" + e.getMessage(), e);
					addMessage("Fallito invio notifica e-mail dell'assegnazione: " + e.getMessage(), "", MessageType.WARNING);
				}
			} else {
				mLogger.warn("Non è stata inviata alcuna notifica e-mail dell'assegnazione");
			}
		}
	}
	
	private Boolean canAddEmailUdFolderXml(Set<String> listaEmailInvioCC, String idEmail) {
		Boolean verify = false;
		if(listaEmailInvioCC != null && !listaEmailInvioCC.isEmpty()) {
			if(listaEmailInvioCC.contains(idEmail)) {
				verify = true;
			}
		}
		return verify;
	}

	@Override
	public AssegnazioneEmailBean get(AssegnazioneEmailBean bean) throws Exception {
		return null;
	}

	@Override
	public AssegnazioneEmailBean remove(AssegnazioneEmailBean bean) throws Exception {
		return null;
	}

	@Override
	public AssegnazioneEmailBean update(AssegnazioneEmailBean bean, AssegnazioneEmailBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public PaginatorBean<AssegnazioneEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AssegnazioneEmailBean bean) throws Exception {
		return null;
	}

}