/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.io.IOUtils;
import it.eng.aurigamailbusiness.bean.AnonymousSenderBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.InfoNotificheBean;
import it.eng.aurigamailbusiness.bean.InfoNotificheBean.EsitoInvio;
import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.VerificaInvioMailNotificaBean;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccount;
import it.eng.aurigamailbusiness.database.dao.MailSenderService;
import it.eng.aurigamailbusiness.database.mail.TAnagNotifEmail;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;

/**
 * classe per la gestione delle notifiche via email
 * 
 * @author jravagnan
 * 
 */
@Service(name = "NotificheManager")
public class NotificheManager {

	private static Logger log = LogManager.getLogger(NotificheManager.class);

	private static final String DELIMITER = ";";

	private static final String DOLLARO = "$";

	/**
	 * gestione delle notifiche da inviare
	 * 
	 * @param beanIn
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "inviaNotifiche")
	public ResultBean<InfoNotificheBean> inviaNotifiche(VerificaInvioMailNotificaBean beanIn) throws Exception {
		ResultBean<InfoNotificheBean> resultBean = new ResultBean<InfoNotificheBean>();
		Session lSession = null;
		InfoNotificheBean info = null;
		try {
			// Apro la sessione
			lSession = HibernateUtil.begin();
			Criteria criteriNotifiche = lSession.createCriteria(TAnagNotifEmail.class);
			criteriNotifiche.add(Restrictions.eq("idSpAoo", new BigDecimal(beanIn.getIdSpAoo())));
			criteriNotifiche.add(Restrictions.eq("tipoNotifica", beanIn.getTipoNotifica().getValue()));
			Map<String, String> restrizioni = beanIn.getRestrizioni();
			if (restrizioni != null) {
				Set<String> chiaviRestrizioni = restrizioni.keySet();
				for (String chiave : chiaviRestrizioni) {
					criteriNotifiche.add(Restrictions.eq(chiave, restrizioni.get(chiave)));
				}
			}
			List<TAnagNotifEmail> anagraficheRisultato = criteriNotifiche.list();
			if (anagraficheRisultato == null || anagraficheRisultato.size() == 0) {
				log.info("nessuna anagrafica di notifica; " + beanIn.getTipoNotifica().getValue() + "per l'idSpAoo: " + beanIn.getIdSpAoo().toString()
						+ " con le restrizioni inserite");
			} else {
				for (TAnagNotifEmail anagraficaNotifica : anagraficheRisultato) {
					if (anagraficaNotifica.getIdAccountMitt() != null) {
						info = inviaEmailNonAnonima(anagraficaNotifica, beanIn.getValori(), beanIn.getAttachments());
					} else {
						info = inviaEmailAnonima(anagraficaNotifica, beanIn.getValori(), beanIn.getAttachments());
					}
				}
			}
			resultBean.setResultBean(info);
			return resultBean;
		} catch (Exception exception) {
			log.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception e) {
				log.error("Errore: " + e.getMessage(), e);
			}
		}
	}

	private InfoNotificheBean inviaEmailNonAnonima(TAnagNotifEmail anagNotifica, Map<String, String> valori, List<File> attachments) {
		InfoNotificheBean infoBean = new InfoNotificheBean();
		try {
			MailboxAccountBean bean = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class)).get(anagNotifica.getIdAccountMitt());
			String account = bean.getAccount();
			SenderBean sb = new SenderBean();
			sb.setAccount(account);
			List<String> destinatariTo = new ArrayList<String>();
			List<String> destinatariCc = new ArrayList<String>();
			String destTo = anagNotifica.getDestNotifica();
			String destCC = anagNotifica.getDestCcNotifica();
			if (destTo != null) {
				StringTokenizer stTo = new StringTokenizer(destTo, DELIMITER);
				while (stTo.hasMoreTokens()) {
					destinatariTo.add(stTo.nextToken());
				}
			}
			if (destCC != null) {
				StringTokenizer stCc = new StringTokenizer(destCC, DELIMITER);
				while (stCc.hasMoreTokens()) {
					destinatariCc.add(stCc.nextToken());
				}
			}
			sb.setAddressTo(destinatariTo);
			sb.setAddressCc(destinatariCc);
			sb.setAddressFrom(anagNotifica.getAccountMittente());
			sb.setBody(rimpiazzaPlaceHolder(anagNotifica.getBody(), valori));
			sb.setSubject(rimpiazzaPlaceHolder(anagNotifica.getSubject(), valori));
			List<SenderAttachmentsBean> attachs = new ArrayList<SenderAttachmentsBean>();
			if (attachments != null) {
				for (File file : attachments) {
					SenderAttachmentsBean attach = new SenderAttachmentsBean();
					attach.setFilename(file.getName());
					//attach.setFile(file);					
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
						attach.setContent(IOUtils.toByteArray(fis));
						attachs.add(attach);
					} finally {
						if (fis != null) {
							try {
								fis.close();
							} catch (Exception e) {
								log.warn("Errore nella chiusura dello stream", e);
							}
						}
					}
					attachs.add(attach);
				}
			}
			sb.setAttachments(attachs);
			EmailSentReferenceBean referenceBean = new MailSenderService().sendAndSaveMailFileInStorage(sb, false);
			if (referenceBean.getSentMails() == null || referenceBean.getSentMails().size() == 0) {
				infoBean.setEsito(EsitoInvio.ERROR_INVIO);
			} else {
				log.error("Inviata la mail non anonima");
				infoBean.setEsito(EsitoInvio.OK);
			}
		} catch (Exception e) {
			log.error("Non è stato possibile inviare la mail non anonima", e);
			infoBean.setEsito(EsitoInvio.GENERIC_PROBLEM);
		}
		return infoBean;
	}

	private InfoNotificheBean inviaEmailAnonima(TAnagNotifEmail anagNotifica, Map<String, String> valori, List<File> attachments) {
		InfoNotificheBean infoBean = new InfoNotificheBean();
		try {
			AnonymousSenderBean asb = new AnonymousSenderBean();
			asb.setSmtpEndpointAccountMitt(anagNotifica.getSmtpEndpointAccountMitt());
			asb.setSmtpPortAccountMitt(anagNotifica.getSmtpPortAccountMitt());
			List<String> destinatariTo = new ArrayList<String>();
			List<String> destinatariCc = new ArrayList<String>();
			String destTo = anagNotifica.getDestNotifica();
			String destCC = anagNotifica.getDestCcNotifica();
			if (destTo != null) {
				StringTokenizer stTo = new StringTokenizer(destTo, DELIMITER);
				while (stTo.hasMoreTokens()) {
					destinatariTo.add(stTo.nextToken());
				}
			}
			if (destCC != null) {
				StringTokenizer stCc = new StringTokenizer(destCC, DELIMITER);
				while (stCc.hasMoreTokens()) {
					destinatariCc.add(stCc.nextToken());
				}
			}
			asb.setAddressTo(destinatariTo);
			asb.setAddressCc(destinatariCc);
			asb.setAddressFrom(anagNotifica.getAccountMittente());
			asb.setBody(rimpiazzaPlaceHolder(anagNotifica.getBody(), valori));
			asb.setSubject(rimpiazzaPlaceHolder(anagNotifica.getSubject(), valori));
			List<SenderAttachmentsBean> attachs = new ArrayList<SenderAttachmentsBean>();
			if (attachments != null) {
				for (File file : attachments) {
					SenderAttachmentsBean attach = new SenderAttachmentsBean();
					attach.setFilename(file.getName());
					attach.setFile(file);				
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
						attach.setContent(IOUtils.toByteArray(fis));
						attachs.add(attach);
					} finally {
						if (fis != null) {
							try {
								fis.close();
							} catch (Exception e) {
								log.warn("Errore nella chiusura dello stream", e);
							}
						}
					}					
					attachs.add(attach);
				}
			}
			asb.setAttachments(attachs);
			EmailSentReferenceBean referenceBean = new MailSenderService().sendAnonymous(asb);
			if (referenceBean.getSentMails() == null || referenceBean.getSentMails().size() == 0) {
				infoBean.setEsito(EsitoInvio.ERROR_INVIO);
			} else {
				infoBean.setEsito(EsitoInvio.OK);
			}
		} catch (Exception e) {
			log.error("Non è stato possibile inviare la mail", e);
			infoBean.setEsito(EsitoInvio.GENERIC_PROBLEM);
		}
		return infoBean;
	}

	private String rimpiazzaPlaceHolder(String originale, Map<String, String> valori) {
		Set<String> placeholders = valori.keySet();
		for (String ph : placeholders) {
			String strToSearch = DOLLARO + ph + DOLLARO;
			if (originale.contains(strToSearch)) {
				originale = originale.replace(strToSearch, valori.get(ph));
			}
		}
		return originale;
	}

}