/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationNotificaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaemailnotassinvioccBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CondivisioneBean;
import it.eng.auriga.ui.module.layout.server.common.TipoIdBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkCollaborationNotifica;
import it.eng.client.DmpkIntMgoEmailPreparaemailnotassinviocc;
import it.eng.client.GestioneInviiNotifiche;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.ModificaInvioNotificaBean;
import it.eng.document.function.bean.ModificaInvioNotificaOutBean;
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

@Datasource(id = "CondivisioneDataSource")
public class CondivisioneDataSource extends AbstractDataSource<CondivisioneBean, CondivisioneBean> {

	private static final Logger log = Logger.getLogger(CondivisioneDataSource.class);

	@Override
	public CondivisioneBean add(CondivisioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = null;

		for (ArchivioBean udFolder : bean.getListaRecord()) {

			if (StringUtils.isNotBlank(bean.getIdNotifica())) {

				ModificaInvioNotificaBean input = new ModificaInvioNotificaBean();
				input.setFlgInvioNotifica("N");
				input.setIdInvioNotifica(bean.getIdNotifica());
				input.setFlgUdFolder(bean.getFlgUdFolder());
				input.setIdUdFolder(udFolder.getIdUdFolder());
				input.setMotivoAnnullamento(null);
				input.setXmlDestinatari(getXmlInvioDestCC(bean));
				input.setMotivoInvio(bean.getMotivoInvio());
				input.setMessaggioInvio(bean.getMessaggioInvio());
				input.setLivelloPriorita(bean.getLivelloPriorita());
				input.setFlgInviaFascicolo(bean.getFlgInviaFascicolo());
				input.setFlgInviaDocCollegati(bean.getFlgInviaDocCollegati());
				
				GestioneInviiNotifiche gestioneInviiNotifiche = new GestioneInviiNotifiche();
				ModificaInvioNotificaOutBean output = new ModificaInvioNotificaOutBean();

				try {
					output = gestioneInviiNotifiche.modificainvionotifica(getLocale(), loginBean, input);
				} catch (Exception e) {
					throw new StoreException("Errore : " + e.getMessage());
				}

				if (output.getDefaultMessage() != null) {
					if (errorMessages == null)
						errorMessages = new HashMap<String, String>();
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			} else {
				DmpkCollaborationNotificaBean input = new DmpkCollaborationNotificaBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

				input.setFlgtypeobjtonotifin(bean.getFlgUdFolder());
				input.setIdobjtonotifin(new BigDecimal(udFolder.getIdUdFolder()));

				input.setFlgcallbyguiin(new Integer(0));
				input.setRecipientsxmlin(getXmlInvioDestCC(bean));
				input.setCodmotivonotifin(bean.getMotivoInvio());
				input.setMessaggionotifin(bean.getMessaggioInvio());
				input.setLivelloprioritain(bean.getLivelloPriorita());
				if (bean.getFlgPresaInCarico() != null && bean.getFlgPresaInCarico()) {
					input.setRichconfermapresavisin(new Integer(1));
				}
				if (bean.getFlgMancataPresaInCarico() != null && bean.getFlgMancataPresaInCarico()) {
					input.setNotnopresavisentroggin(bean.getGiorniTrascorsi() != null ? bean.getGiorniTrascorsi() : null);
				}	

				if ("U".equals(bean.getFlgUdFolder())) {
					
					if (bean.getFlgInviaFascicolo() != null) {
						input.setFlginviofascicoloin(bean.getFlgInviaFascicolo() ? 1 : 0);
					}
					if (bean.getFlgInviaDocCollegati() != null) {
						input.setFlginvioudcollegatein(bean.getFlgInviaDocCollegati() ? 1 : 0);
					}
					
					input.setSenderidin(null);
					input.setSendertypein(null);
				}

				DmpkCollaborationNotifica dmpkCollaborationNotifica = new DmpkCollaborationNotifica();
				StoreResultBean<DmpkCollaborationNotificaBean> output = dmpkCollaborationNotifica.execute(getLocale(), loginBean, input);

				if (output.getDefaultMessage() != null) {
					if (errorMessages == null)
						errorMessages = new HashMap<String, String>();
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			}

		}

		if (errorMessages != null) {
			bean.setErrorMessages(errorMessages);
		} else {
			HashMap<String, String> errMsg = invioNotificheAssegnazioneInvioCC(bean);
			if (errMsg != null && errMsg.size() > 0) {
				bean.setErrorMessages(errMsg);
			}
		}

		return bean;
	}

	public String getXmlInvioDestCC(CondivisioneBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaInvioDestCC(bean));
	}

	private List<AssegnatariBean> getListaInvioDestCC(CondivisioneBean bean) {
		List<AssegnatariBean> listaInvioDestCC = new ArrayList<AssegnatariBean>();
		if (bean.getListaDestInvioCC() != null && bean.getListaDestInvioCC().size() > 0) {
			for (DestInvioCCBean destInvioCCBean : bean.getListaDestInvioCC()) {				
				AssegnatariBean lAssegnatariBean = new AssegnatariBean();
				lAssegnatariBean.setTipo(getTipoAssegnatario(destInvioCCBean));
				if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {								
					lAssegnatariBean.setIdSettato(destInvioCCBean.getGruppo());										
				} else {					
					lAssegnatariBean.setIdSettato(destInvioCCBean.getIdUo());	
					if(destInvioCCBean.getCodRapidoChanged() != null && destInvioCCBean.getCodRapidoChanged()){
						lAssegnatariBean.setCodRapido(destInvioCCBean.getCodRapido());
					}
				}	
				lAssegnatariBean.setPermessiAccesso("V");					
				//TODO CONDIVISIONE SAVE OK
				if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato()) ||
						StringUtils.isNotBlank(lAssegnatariBean.getCodRapido())) {
					listaInvioDestCC.add(lAssegnatariBean);
				}	
			}
		}
		return listaInvioDestCC;
	}

	private TipoAssegnatario getTipoAssegnatario(DestInvioBean lDestInvioBean) {
		if(StringUtils.isNotBlank(lDestInvioBean.getTypeNodo())) {
			TipoAssegnatario[] tipiAssegnatari = TipoAssegnatario.values();
			for (TipoAssegnatario tipoAssegnatario : tipiAssegnatari) {
				if (tipoAssegnatario.toString().equals(lDestInvioBean.getTypeNodo())) {
					return tipoAssegnatario;
				}
			}
		} else if(lDestInvioBean.getCodRapidoChanged() != null && lDestInvioBean.getCodRapidoChanged() && StringUtils.isNotBlank(lDestInvioBean.getCodRapido())) {
			return TipoAssegnatario.UNITA_ORGANIZZATIVA;			
		}
		return null;
	}

	private HashMap<String, String> invioNotificheAssegnazioneInvioCC(CondivisioneBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = new HashMap<String, String>();

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<AssegnatariBean> listaInvioDestCC = getListaInvioDestCC(bean);

		if (bean.getFlgUdFolder() != null && !"".equals(bean.getFlgUdFolder())) {
			/**
			 * Invio per conoscenza di un FOLDER, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_INVIO_CC_FLD
			 */
			if (bean.getFlgUdFolder().equalsIgnoreCase("F")) {
				if (attivaNotEmailInvioCCFLD(bean)) {
					if (listaInvioDestCC != null && listaInvioDestCC.size() > 0) {
						sendNotificaPerConoscenza(bean, lAurigaLoginBean, token, idUserLavoro, lXmlUtilitySerializer, listaInvioDestCC);
					} else {
						log.warn("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + "lista destinatari listaInvioDestCC: "
								+ listaInvioDestCC.size() + ", verificare parametro ATTIVA_NOT_EMAIL_INVIO_CC_FLD");
						//addMessage("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza", "", MessageType.WARNING);
					}
				}
			}
			/**
			 * Invio per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
			 */
			else if (bean.getFlgUdFolder().equalsIgnoreCase("U")) {
				if (attivaNotEmailInvioCCUD(bean)) {
					if (listaInvioDestCC != null && listaInvioDestCC.size() > 0) {
						sendNotificaPerConoscenza(bean, lAurigaLoginBean, token, idUserLavoro, lXmlUtilitySerializer, listaInvioDestCC);
					} else {
						log.warn("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + "lista destinatari listaInvioDestCC: "
								+ listaInvioDestCC.size() + ", verificare parametro ATTIVA_NOT_EMAIL_INVIO_CC_UD");
						//addMessage("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza", "", MessageType.WARNING);
					}
				}				
			}
		} else {
			log.warn("Warning, flgUdFolder non valorizzato");
		}

		return errorMessages;
	}

	private void sendNotificaPerConoscenza(CondivisioneBean bean, AurigaLoginBean lAurigaLoginBean, String token, String idUserLavoro,
			XmlUtilitySerializer lXmlUtilitySerializer, List<AssegnatariBean> listaInvioDestCC) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, Exception {

		DmpkIntMgoEmailPreparaemailnotassinvioccBean lInputBean = new DmpkIntMgoEmailPreparaemailnotassinvioccBean();
		lInputBean.setCodidconnectiontokenin(token);
		lInputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		lInputBean.setAssinvioccin("invio_conoscenza");

		List<TipoIdBean> listaUdFolder = new ArrayList<TipoIdBean>();
		for (ArchivioBean record : bean.getListaRecord()) {
			TipoIdBean udFolder = new TipoIdBean();
			udFolder.setTipo(record.getFlgUdFolder());
			udFolder.setId(record.getIdUdFolder());
			listaUdFolder.add(udFolder);
		}
		lInputBean.setUdfolderxmlin(lXmlUtilitySerializer.bindXmlList(listaUdFolder));

		List<TipoIdBean> listaDestAssInvioCC = new ArrayList<TipoIdBean>();
		for (int i = 0; i < listaInvioDestCC.size(); i++) {
			TipoIdBean destAssInvioCC = new TipoIdBean();
			destAssInvioCC.setTipo(listaInvioDestCC.get(i).getTipo().toString());
			destAssInvioCC.setId(listaInvioDestCC.get(i).getIdSettato());
			listaDestAssInvioCC.add(destAssInvioCC);
		}
		lInputBean.setDestassinvioccxmlin(lXmlUtilitySerializer.bindXmlList(listaDestAssInvioCC));

		lInputBean.setCodmotivoinvioin(bean.getMotivoInvio());
		lInputBean.setMotivoinvioin(null);
		lInputBean.setMessaggioinvioin(bean.getMessaggioInvio());
		lInputBean.setLivelloprioritain(bean.getLivelloPriorita());

		DmpkIntMgoEmailPreparaemailnotassinviocc preparaemailnotassinviocc = new DmpkIntMgoEmailPreparaemailnotassinviocc();
		StoreResultBean<DmpkIntMgoEmailPreparaemailnotassinvioccBean> lStoreResultBean = preparaemailnotassinviocc.execute(getLocale(), lAurigaLoginBean,
				lInputBean);

		if (lStoreResultBean.isInError()) {
			String message = "Fallito invio notifica e-mail dell'invio per conoscenza";
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				log.error("Fallito invio notifica e-mail dell'invio per conoscenza per il seguente motivo:" + lStoreResultBean.getDefaultMessage());
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
						log.warn("Warning, Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails);
						addMessage("Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
					}
				} catch (Exception e) {
					log.error("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + e.getMessage(), e);
					addMessage("Fallito invio notifica e-mail dell'invio per conoscenza: " + e.getMessage(), "", MessageType.WARNING);
				}
			} else {
				log.warn("Warning, Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza,getFlgemailtosendout: "
						+ lStoreResultBean.getResultBean().getFlgemailtosendout());
			}
		}
	}
	
	@Override
	public CondivisioneBean get(CondivisioneBean bean) throws Exception {		
		return null;
	}

	@Override
	public CondivisioneBean remove(CondivisioneBean bean) throws Exception {		
		return null;
	}

	@Override
	public CondivisioneBean update(CondivisioneBean bean, CondivisioneBean oldvalue) throws Exception {		
		return bean;
	}

	@Override
	public PaginatorBean<CondivisioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(CondivisioneBean bean) throws Exception {		
		return null;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCUD(CondivisioneBean bean) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			if(bean.getFlgMandaNotificaMail() != null && bean.getFlgMandaNotificaMail()) {
				attivo = true;
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_FLD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCFLD(CondivisioneBean bean) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_FLD"))) {
			if(bean.getFlgMandaNotificaMail() != null && bean.getFlgMandaNotificaMail()) {
				attivo = true;
			}
		}
		return attivo;
	}
	
}