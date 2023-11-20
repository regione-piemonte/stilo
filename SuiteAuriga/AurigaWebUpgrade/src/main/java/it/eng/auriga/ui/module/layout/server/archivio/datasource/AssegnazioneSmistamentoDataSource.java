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

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationInvioBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaemailnotassinvioccBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AssegnazioneSmistamentoBean;
import it.eng.auriga.ui.module.layout.server.common.TipoIdBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkCollaborationInvio;
import it.eng.client.DmpkIntMgoEmailPreparaemailnotassinviocc;
import it.eng.client.GestioneInviiNotifiche;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.Flag;
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

@Datasource(id = "AssegnazioneSmistamentoDataSource")
public class AssegnazioneSmistamentoDataSource extends AbstractDataSource<AssegnazioneSmistamentoBean, AssegnazioneSmistamentoBean> {

	private static final Logger log = Logger.getLogger(AssegnazioneSmistamentoDataSource.class);

	@Override
	public AssegnazioneSmistamentoBean add(AssegnazioneSmistamentoBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		HashMap<String, String> errorMessages = null;

		for (ArchivioBean udFolder : bean.getListaRecord()) {

			if (StringUtils.isNotBlank(bean.getIdInvio())) {

				ModificaInvioNotificaBean input = new ModificaInvioNotificaBean();
				input.setFlgInvioNotifica("I");
				input.setIdInvioNotifica(bean.getIdInvio());
				input.setFlgUdFolder(bean.getFlgUdFolder());
				input.setIdUdFolder(udFolder.getIdUdFolder());
				input.setMotivoAnnullamento(null);
				input.setXmlDestinatari(getXmlAssegnatari(bean));
				input.setMotivoInvio(bean.getMotivoInvio());
				input.setMessaggioInvio(bean.getMessaggioInvio());
				input.setLivelloPriorita(bean.getLivelloPriorita());
				input.setFlgInviaFascicolo(bean.getFlgInviaFascicolo());
				input.setFlgInviaDocCollegati(bean.getFlgInviaDocCollegati());
				input.setFlgMantieniCopiaUd(bean.getFlgMantieniCopiaUd());
				//TODO da passare flgPresaInCarico e giorniTrascorsi

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
				DmpkCollaborationInvioBean input = new DmpkCollaborationInvioBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

				input.setFlgtypeobjtosendin(bean.getFlgUdFolder());
				if (udFolder.getIdUdFolder() != null)
					input.setIdobjtosendin(new BigDecimal(udFolder.getIdUdFolder()));

				input.setFlgcallbyguiin(new Integer(0));
				input.setRecipientsxmlin(getXmlAssegnatari(bean));
				input.setCodmotivoinvioin(bean.getMotivoInvio());
				input.setMessaggioinvioin(bean.getMessaggioInvio());
				input.setLivelloprioritain(bean.getLivelloPriorita());

				if ("U".equals(bean.getFlgUdFolder())) {
					if (bean.getFlgInviaFascicolo() != null) {
						input.setFlginviofascicoloin(bean.getFlgInviaFascicolo() ? new Integer(1) : new Integer(0));
					}
					if (bean.getFlgInviaDocCollegati() != null) {
						input.setFlginvioudcollegatein(bean.getFlgInviaDocCollegati() ? new Integer(1) : new Integer(0));
					}
					if (bean.getFlgMantieniCopiaUd() != null) {
						input.setFlgmantienicopiaudin(bean.getFlgMantieniCopiaUd() ? new Integer(1) : new Integer(0));
					}
					if (bean.getFlgForceInvio() != null) {
						input.setFlgforceinvioin(bean.getFlgForceInvio() ? new Integer(1) : new Integer(0));
					}					
					input.setSenderidin(null);
					input.setSendertypein(null);
				}
				//TODO da passare flgPresaInCarico e giorniTrascorsi

				DmpkCollaborationInvio dmpkCollaborationInvio = new DmpkCollaborationInvio();
				StoreResultBean<DmpkCollaborationInvioBean> output = dmpkCollaborationInvio.execute(getLocale(), loginBean, input);

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

	public String getXmlAssegnatari(AssegnazioneSmistamentoBean bean) throws Exception {
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(getListaAssegnatari(bean));
	}

	private List<AssegnatariBean> getListaAssegnatari(AssegnazioneSmistamentoBean bean) throws Exception {
		List<AssegnatariBean> listaAssegnatari = new ArrayList<AssegnatariBean>();
		if (bean.getListaAssegnazioni() != null) {
			for (AssegnazioneBean assegnazioneBean : bean.getListaAssegnazioni()) {				
				AssegnatariBean lAssegnatariBean = new AssegnatariBean();
				lAssegnatariBean.setTipo(getTipoAssegnatario(assegnazioneBean));				
				if(lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {					
					lAssegnatariBean.setIdSettato(assegnazioneBean.getGruppo());					
				} else {
					lAssegnatariBean.setIdSettato(assegnazioneBean.getIdUo());		
					if(assegnazioneBean.getCodRapidoChanged() != null && assegnazioneBean.getCodRapidoChanged()){
						lAssegnatariBean.setCodRapido(assegnazioneBean.getCodRapido());						
					}
				}				
				if (bean.getMotivoInvio() != null ) {							
					if ("PAF".equals(bean.getMotivoInvio())) {
						if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
							throw new StoreException("L'assegnazione per firma/approvazione deve essere fatta a persona/e e non ad una U.O.");
						}	
					}
					if ("PAV".equals(bean.getMotivoInvio())) {
						if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.UNITA_ORGANIZZATIVA)) {
							throw new StoreException("L'assegnazione per apposizione visto elettronico deve essere fatta a persona/e e non ad una U.O.");
						}	
					}												
				}
				lAssegnatariBean.setPermessiAccesso("FC");
				//TODO ASSEGNAZIONE SAVE OK
				if (bean.getFlgPresaInCarico() != null && bean.getFlgPresaInCarico()) {
					lAssegnatariBean.setFeedback(Flag.SETTED);
				}
				if (bean.getFlgMancataPresaInCarico() != null && bean.getFlgMancataPresaInCarico()) {
					lAssegnatariBean.setNumeroGiorniFeedback(bean.getGiorniTrascorsi());
				}
				if(StringUtils.isNotBlank(lAssegnatariBean.getIdSettato()) ||
						StringUtils.isNotBlank(lAssegnatariBean.getCodRapido())) {
					listaAssegnatari.add(lAssegnatariBean);
				}				
			}
		}
		return listaAssegnatari;
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

	private HashMap<String, String> invioNotificheAssegnazioneInvioCC(AssegnazioneSmistamentoBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<AssegnatariBean> listaAssegnatari = getListaAssegnatari(bean);
		HashMap<String, String> errorMessages = new HashMap<String, String>();

		if (bean.getFlgUdFolder() != null && !"".equals(bean.getFlgUdFolder())) {
			/**
			 * Assegnazione per conoscenza di un FOLDER, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_FLD
			 */
			if (bean.getFlgUdFolder().equalsIgnoreCase("F")) {
				if (attivaNotEmailAssegnFLD(bean)) {
					if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
						sendNotificaAssegnazione(bean, lAurigaLoginBean, token, idUserLavoro, lXmlUtilitySerializer, listaAssegnatari);
					} else {
						log.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
								+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_FLD");
						//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
					}
				}
			}
			/**
			 * Assegnazione per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
			 */
			else if (bean.getFlgUdFolder().equalsIgnoreCase("U")) {
				if (attivaNotEmailAssegnUD(bean)) {
					if (listaAssegnatari != null && listaAssegnatari.size() > 0) {
						sendNotificaAssegnazione(bean, lAurigaLoginBean, token, idUserLavoro, lXmlUtilitySerializer, listaAssegnatari);
					} else {
						log.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
								+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_UD");
						//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
					}
				}
			}
		} else {
			log.warn("Warning, flgUdFolder non valorizzato");
		}

		return errorMessages;
	}

	private void sendNotificaAssegnazione(AssegnazioneSmistamentoBean bean, AurigaLoginBean lAurigaLoginBean, String token, String idUserLavoro,
			XmlUtilitySerializer lXmlUtilitySerializer, List<AssegnatariBean> listaAssegnatari) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, Exception {
		DmpkIntMgoEmailPreparaemailnotassinvioccBean lInputBean = new DmpkIntMgoEmailPreparaemailnotassinvioccBean();
		lInputBean.setCodidconnectiontokenin(token);
		lInputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		lInputBean.setAssinvioccin("assegnazione_competenza");

		List<TipoIdBean> listaUdFolder = new ArrayList<TipoIdBean>();
		for (ArchivioBean record : bean.getListaRecord()) {
			TipoIdBean udFolder = new TipoIdBean();
			udFolder.setTipo(record.getFlgUdFolder());
			udFolder.setId(record.getIdUdFolder());
			listaUdFolder.add(udFolder);
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

		lInputBean.setCodmotivoinvioin(bean.getMotivoInvio());
		lInputBean.setMotivoinvioin(null);
		lInputBean.setMessaggioinvioin(bean.getMessaggioInvio());
		lInputBean.setLivelloprioritain(bean.getLivelloPriorita());

		DmpkIntMgoEmailPreparaemailnotassinviocc preparaemailnotassinviocc = new DmpkIntMgoEmailPreparaemailnotassinviocc();
		StoreResultBean<DmpkIntMgoEmailPreparaemailnotassinvioccBean> lStoreResultBean = preparaemailnotassinviocc.execute(getLocale(), lAurigaLoginBean,
				lInputBean);

		if (lStoreResultBean.isInError()) {
			String message = "Fallito invio notifica e-mail dell'assegnazione";
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				log.error("Fallito invio notifica e-mail dell'assegnazionem per il seguente motivo: " + lStoreResultBean.getDefaultMessage());
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
						log.warn("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails);
						addMessage("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
					}
				} catch (Exception e) {
					log.error("Fallito invio notifica e-mail dell'assegnazione:" + e.getMessage(), e);
					addMessage("Fallito invio notifica e-mail dell'assegnazione: " + e.getMessage(), "", MessageType.WARNING);
				}
			} else {
				log.warn("Non è stata inviata alcuna notifica e-mail dell'assegnazione");
			}
		}
	}
	
	@Override
	public AssegnazioneSmistamentoBean get(AssegnazioneSmistamentoBean bean) throws Exception {
		return null;
	}

	@Override
	public AssegnazioneSmistamentoBean remove(AssegnazioneSmistamentoBean bean) throws Exception {
		return null;
	}

	@Override
	public AssegnazioneSmistamentoBean update(AssegnazioneSmistamentoBean bean, AssegnazioneSmistamentoBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public PaginatorBean<AssegnazioneSmistamentoBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AssegnazioneSmistamentoBean bean) throws Exception {
		return null;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnUD(AssegnazioneSmistamentoBean bean) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			if(bean.getFlgMandaNotificaMail() != null && bean.getFlgMandaNotificaMail()) {
				attivo = true;
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_FLD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnFLD(AssegnazioneSmistamentoBean bean) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_FLD"))) {
			if(bean.getFlgMandaNotificaMail() != null && bean.getFlgMandaNotificaMail()) {
				attivo = true;
			}
		}
		return attivo;
	}

}