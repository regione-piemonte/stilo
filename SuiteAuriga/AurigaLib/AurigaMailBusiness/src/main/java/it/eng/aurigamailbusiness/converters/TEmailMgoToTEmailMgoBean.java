/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;

import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TUtentiModPecBean;
import it.eng.aurigamailbusiness.database.dao.DaoTAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTUtentiModPec;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.core.business.converter.IBeanPopulate;

public class TEmailMgoToTEmailMgoBean implements IBeanPopulate<TEmailMgo, TEmailMgoBean> {

	Session session;

	public TEmailMgoToTEmailMgoBean(Session session) {
		this.session = session;
	}

	public TEmailMgoToTEmailMgoBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populate(TEmailMgo src, TEmailMgoBean dest) throws Exception {
		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}
		if (src.getTUtentiModPecByIdUtenteLock() != null) {
			dest.setIdUtenteLock(src.getTUtentiModPecByIdUtenteLock().getIdUtente());
		}
		if (src.getTAnagFruitoriCaselle() != null) {
			String idFruitore = src.getTAnagFruitoriCaselle().getIdFruitoreCasella();
			if (idFruitore != null) {
				DaoTAnagFruitoriCaselle daoFruitori = (DaoTAnagFruitoriCaselle) DaoFactory.getDao(DaoTAnagFruitoriCaselle.class);
				TAnagFruitoriCaselleBean fruitoreCasella = null;
				if (session == null) {
					fruitoreCasella = daoFruitori.get(idFruitore);
				} else {
					fruitoreCasella = daoFruitori.getInSession(idFruitore, session);
				}
				dest.setIdUoAssegn(idFruitore);
				dest.setDescrizioneUoAssegn(fruitoreCasella.getDenominazione());
			}
		}
		if (src.getTUtentiModPecByIdUtenteAssegn() != null) {
			String idUtente = src.getTUtentiModPecByIdUtenteAssegn().getIdUtente();
			if (idUtente != null) {
				DaoTUtentiModPec daoUtenti = (DaoTUtentiModPec) DaoFactory.getDao(DaoTUtentiModPec.class);
				TUtentiModPecBean utente = null;
				if (session == null) {
					utente = daoUtenti.get(idUtente);
				} else {
					utente = daoUtenti.getInSession(idUtente, session);
				}
				dest.setIdUtenteAssegn(idUtente);
				dest.setDescrizioneUtenteAssegn(utente.getNome() + " " + utente.getCognome());
			}
		}
	}

	@Override
	public void populateForUpdate(TEmailMgo src, TEmailMgoBean dest) throws Exception {
		if (src.getMailboxAccount() != null) {
			dest.setIdCasella(src.getMailboxAccount().getIdAccount());
		}
		if (src.getTUtentiModPecByIdUtenteLock() != null) {
			dest.setIdUtenteLock(src.getTUtentiModPecByIdUtenteLock().getIdUtente());
		}
		if (src.getTAnagFruitoriCaselle() != null) {
			String idFruitore = src.getTAnagFruitoriCaselle().getIdFruitoreCasella();
			if (idFruitore != null) {
				DaoTAnagFruitoriCaselle daoFruitori = (DaoTAnagFruitoriCaselle) DaoFactory.getDao(DaoTAnagFruitoriCaselle.class);
				TAnagFruitoriCaselleBean fruitoreCasella = null;
				if (session == null) {
					fruitoreCasella = daoFruitori.get(idFruitore);
				} else {
					fruitoreCasella = daoFruitori.getInSession(idFruitore, session);
				}
				dest.setIdUoAssegn(idFruitore);
				dest.setDescrizioneUoAssegn(fruitoreCasella.getDenominazione());
			}
		}
		if (src.getTUtentiModPecByIdUtenteAssegn() != null) {
			String idUtente = src.getTUtentiModPecByIdUtenteAssegn().getIdUtente();
			if (idUtente != null) {
				DaoTUtentiModPec daoUtenti = (DaoTUtentiModPec) DaoFactory.getDao(DaoTUtentiModPec.class);
				TUtentiModPecBean utente = null;
				if (session == null) {
					utente = daoUtenti.get(idUtente);
				} else {
					utente = daoUtenti.getInSession(idUtente, session);
				}
				dest.setIdUtenteAssegn(idUtente);
				dest.setDescrizioneUtenteAssegn(utente.getNome() + " " + utente.getCognome());
			}
		}
	}

}