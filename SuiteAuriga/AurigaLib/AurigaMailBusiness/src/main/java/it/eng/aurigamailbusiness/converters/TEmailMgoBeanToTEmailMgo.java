/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.MailboxAccount;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TUtentiModPec;
import it.eng.core.business.converter.IBeanPopulate;

public class TEmailMgoBeanToTEmailMgo implements IBeanPopulate<TEmailMgoBean, TEmailMgo> {

	@Override
	public void populate(TEmailMgoBean src, TEmailMgo dest) throws Exception {
		if (src.getIdCasella() != null) {
			MailboxAccount account = new MailboxAccount();
			account.setIdAccount(src.getIdCasella());
			dest.setMailboxAccount(account);
		}
		if (src.getIdUtenteLock() != null) {
			TUtentiModPec utente = new TUtentiModPec();
			utente.setIdUtente(src.getIdUtenteLock());
			dest.setTUtentiModPecByIdUtenteLock(utente);
		}
		if (src.getIdUtenteAssegn() != null) {
			TUtentiModPec utente = new TUtentiModPec();
			utente.setIdUtente(src.getIdUtenteAssegn());
			dest.setTUtentiModPecByIdUtenteAssegn(utente);
		}
		if (src.getIdUoAssegn() != null) {
			TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
			fruitore.setIdFruitoreCasella(src.getIdUoAssegn());
			dest.setTAnagFruitoriCaselle(fruitore);
		}

	}

	@Override
	public void populateForUpdate(TEmailMgoBean src, TEmailMgo dest) throws Exception {
		if (src.hasPropertyBeenModified("idCasella")) {
			if (src.getIdCasella() != null) {
				MailboxAccount account = new MailboxAccount();
				account.setIdAccount(src.getIdCasella());
				dest.setMailboxAccount(account);
			} else {
				dest.setMailboxAccount(null);
			}
		}
		if (src.hasPropertyBeenModified("idUtenteLock")) {
			if (src.getIdUtenteLock() != null) {
				TUtentiModPec utente = new TUtentiModPec();
				utente.setIdUtente(src.getIdUtenteLock());
				dest.setTUtentiModPecByIdUtenteLock(utente);
			} else {
				dest.setTUtentiModPecByIdUtenteLock(null);
			}
		}
		if (src.hasPropertyBeenModified("idUtenteAssegn")) {
			if (src.getIdUtenteAssegn() != null) {
				TUtentiModPec utente = new TUtentiModPec();
				utente.setIdUtente(src.getIdUtenteAssegn());
				dest.setTUtentiModPecByIdUtenteAssegn(utente);
			} else {
				dest.setTUtentiModPecByIdUtenteAssegn(null);
			}
		}
		if (src.hasPropertyBeenModified("idUoAssegn")) {
			if (src.getIdUoAssegn() != null) {
				TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
				fruitore.setIdFruitoreCasella(src.getIdUoAssegn());
				dest.setTAnagFruitoriCaselle(fruitore);
			} else {
				dest.setTAnagFruitoriCaselle(null);
			}
		}
	}

}