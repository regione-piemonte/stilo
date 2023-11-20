/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.converters.TRegProtVsEmailToTRegProtVsEmailBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TCommentiEmail;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRegProtVsEmail;
import it.eng.aurigamailbusiness.database.mail.TRubricaEmail;
import it.eng.aurigamailbusiness.database.mail.TUtentiModPec;
import it.eng.aurigamailbusiness.database.mail.TValDizionario;
import it.eng.aurigamailbusiness.database.utility.DecodificaUtility;
import it.eng.aurigamailbusiness.database.utility.bean.search.EmailResultBean;
import it.eng.aurigamailbusiness.database.utility.send.TipoDestinatario;
import it.eng.core.business.converter.IBeanPopulate;
import it.eng.core.business.converter.UtilPopulate;

public class TEmailMgoToEmailResultBean implements IBeanPopulate<TEmailMgo, EmailResultBean> {

	// Rappresentano i fruitori relativi all'utente. Viene settato quando viene istanziato il beanPopulate
	private List<TAnagFruitoriCaselle> lListFruitori;
	private Session session;

	@Override
	public void populate(TEmailMgo src, EmailResultBean dest) throws Exception {
		// I campi foreignKey vengono caricati con i relativi Id
		TEmailMgo lEmailMgo = (TEmailMgo) session.get(TEmailMgo.class, src.getIdEmail());

		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}

		if (lEmailMgo != null) {
			dest.setTUtentiModPecByIdUtenteLockId(
					lEmailMgo.getTUtentiModPecByIdUtenteLock() != null ? lEmailMgo.getTUtentiModPecByIdUtenteLock().getIdUtente() : null);
			dest.setTAnagFruitoriCaselleId(lEmailMgo.getTAnagFruitoriCaselle() != null ? lEmailMgo.getTAnagFruitoriCaselle().getIdFruitoreCasella() : null);
			dest.setTValDizionarioByIdRecDizOperLockId(
					lEmailMgo.getTValDizionarioByIdRecDizOperLock() != null ? lEmailMgo.getTValDizionarioByIdRecDizOperLock().getIdRecDiz() : null);
			dest.setTValDizionarioByIdRecDizContrassegnoId(
					lEmailMgo.getTValDizionarioByIdRecDizContrassegno() != null ? lEmailMgo.getTValDizionarioByIdRecDizContrassegno().getIdRecDiz() : null);
			dest.setTUtentiModPecByIdUtenteAssegnId(
					lEmailMgo.getTUtentiModPecByIdUtenteAssegn() != null ? lEmailMgo.getTUtentiModPecByIdUtenteAssegn().getIdUtente() : null);
		}
		// Procedo con le decodifiche
		if (StringUtils.isNotBlank(dest.getTUtentiModPecByIdUtenteLockId())) {
			TUtentiModPec lUtentiModPecByIdUtente = (TUtentiModPec) session.get(TUtentiModPec.class, dest.getTUtentiModPecByIdUtenteLockId());
			dest.setTUtentiModPecByIdUtenteLockDecodifica(DecodificaUtility.getDecodificaUtente(lUtentiModPecByIdUtente));
		}
		if (StringUtils.isNotBlank(dest.getTAnagFruitoriCaselleId())) {
			TAnagFruitoriCaselle lAnagFruitoriCaselle = (TAnagFruitoriCaselle) session.get(TAnagFruitoriCaselle.class, dest.getTAnagFruitoriCaselleId());
			dest.setTAnagFruitoriCaselleDecodifica(DecodificaUtility.getDecodificaUo(lAnagFruitoriCaselle));
		}
		if (StringUtils.isNotBlank(dest.getTValDizionarioByIdRecDizOperLockId())) {
			TValDizionario lValDizionarioByIdRecDizOperLock = (TValDizionario) session.get(TValDizionario.class, dest.getTValDizionarioByIdRecDizOperLockId());
			dest.setTValDizionarioByIdRecDizOperLockDecodifica(lValDizionarioByIdRecDizOperLock.getValore());
		}
		if (StringUtils.isNotBlank(dest.getTValDizionarioByIdRecDizContrassegnoId())) {
			TValDizionario lValDizionarioByIdRecDizContrassegno = (TValDizionario) session.get(TValDizionario.class,
					dest.getTValDizionarioByIdRecDizContrassegnoId());
			dest.setTValDizionarioByIdRecDizContrassegnoDecodifica(lValDizionarioByIdRecDizContrassegno.getValore());
		}
		if (StringUtils.isNotBlank(dest.getTUtentiModPecByIdUtenteAssegnId())) {
			TUtentiModPec lUtentiModPecByIdUtenteAssegn = (TUtentiModPec) session.get(TUtentiModPec.class, dest.getTUtentiModPecByIdUtenteAssegnId());
			dest.setTUtentiModPecByIdUtenteAssegnDecodifica(DecodificaUtility.getDecodificaUtente(lUtentiModPecByIdUtenteAssegn));
		}

		// Setto gli estremi di registrazione
		dest.setRegProtAssociati(getRegProtAssociati(src.getIdEmail()));

		// Setto presenza commenti
		dest.setFlgPresenzaCommenti(getFlgPresenzaCommenti(src.getIdEmail()));

		List<String> lListP = new ArrayList<String>();
		List<String> lListCC = new ArrayList<String>();
		List<String> lListCCN = new ArrayList<String>();
		// se valorizzato, se no la decodifica del campo ID_VOCE_RUBRICA_DEST fatta con T_RUBRICA_EMAIL.ACCOUNT_EMAIL
		for (TDestinatariEmailMgo lTDestinatariEmailMgo : src.getTDestinatariEmailMgos()) {
			String lStringAccount = getAccount(lTDestinatariEmailMgo);
			if (lTDestinatariEmailMgo.getFlgTipoDestinatario().equals(TipoDestinatario.TO.getValue())) {
				lListP.add(lStringAccount);
			} else if (lTDestinatariEmailMgo.getFlgTipoDestinatario().equals(TipoDestinatario.CC.getValue())) {
				lListCC.add(lStringAccount);
			} else if (lTDestinatariEmailMgo.getFlgTipoDestinatario().equals(TipoDestinatario.BCC.getValue())) {
				lListCCN.add(lStringAccount);
			}
		}
		dest.setDestinatari(lListP);
		dest.setDestinatariCC(lListCC);
		dest.setDestinatariCCN(lListCCN);
	}

	private String getAccount(TDestinatariEmailMgo lTDestinatariEmailMgo) {
		if (StringUtils.isNotEmpty(lTDestinatariEmailMgo.getAccountDestinatario())) {
			return lTDestinatariEmailMgo.getAccountDestinatario();
		} else {
			TRubricaEmail lTRubricaEmail = (TRubricaEmail) session.get(TRubricaEmail.class, lTDestinatariEmailMgo.getTRubricaEmail().getIdVoceRubricaEmail());
			return lTRubricaEmail.getAccountEmail();
		}
	}

	/**
	 * Recupera la presenza di commenti
	 * 
	 * @param idEmail
	 * @return
	 */
	private boolean getFlgPresenzaCommenti(String idEmail) {
		// Vado su commenti
		Criteria lCriteria = session.createCriteria(TCommentiEmail.class);
		// Prendo quelli relativi alla mail in esame
		lCriteria.createAlias("TEmailMgo", "TEmailMgo");
		lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", idEmail));
		// Se ho dei fruitori
		if (lListFruitori != null && lListFruitori.size() > 0) {
			// Verifico che ci siano commenti pubblici o dei miei fruitori
			lCriteria.createAlias("TAnagFruitoriCaselle", "TAnagFruitoriCaselle");
			lCriteria.add(Restrictions.or(Restrictions.eq("flgPubblico", true), Restrictions.in("TAnagFruitoriCaselle", lListFruitori)));
		} else {
			// Solo commenti pubblici
			lCriteria.add(Restrictions.eq("flgPubblico", true));
		}
		// Seleziono l'id
		lCriteria.setProjection(Projections.count("id"));
		Long lLong = (Long) lCriteria.uniqueResult();
		// Se ce ne sono true
		if (lLong != null && lLong > 0)
			return true;
		// altrimenti false
		else
			return false;

	}

	// private List<String> getEstremiProtocolloAssociati(String idEmail) {
	// Criteria lCriteria = session.createCriteria(TRegProtVsEmail.class);
	// lCriteria.createAlias("TEmailMgo", "TEmailMgo");
	// lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", idEmail));
	// List<TRegProtVsEmail> lListResult = lCriteria.list();
	// List<String> protocolli = new ArrayList<String>();
	// for (TRegProtVsEmail lTRegProtVsEmail : lListResult){
	// StringBuffer lStringBuffer = new StringBuffer();
	// if (StringUtils.isNotEmpty(lTRegProtVsEmail.getSiglaRegistro())){
	// lStringBuffer.append(lTRegProtVsEmail.getSiglaRegistro() + " ");
	// }
	// lStringBuffer.append(lTRegProtVsEmail.getNumReg() + "/" + lTRegProtVsEmail.getAnnoReg());
	// if (lTRegProtVsEmail.getTsReg()!=null){
	// lStringBuffer.append(" del " + DecodificaUtility.getDate(lTRegProtVsEmail.getTsReg()));
	// }
	// protocolli.add(lStringBuffer.toString());
	// }
	// return protocolli;
	// }

	private List<TRegProtVsEmailBean> getRegProtAssociati(String idEmail) throws Exception {
		Criteria lCriteria = session.createCriteria(TRegProtVsEmail.class);
		lCriteria.createAlias("TEmailMgo", "TEmailMgo");
		lCriteria.add(Restrictions.eq("TEmailMgo.idEmail", idEmail));
		return (List<TRegProtVsEmailBean>) UtilPopulate.populateList(lCriteria.list(), TRegProtVsEmailBean.class, new TRegProtVsEmailToTRegProtVsEmailBean());
	}

	@Override
	public void populateForUpdate(TEmailMgo src, EmailResultBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

	public void setlListFruitori(List<TAnagFruitoriCaselle> lListFruitori) {
		this.lListFruitori = lListFruitori;
	}

	public List<TAnagFruitoriCaselle> getlListFruitori() {
		return lListFruitori;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

}