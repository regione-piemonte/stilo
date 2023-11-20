/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TAssegnEmailBean;
import it.eng.aurigamailbusiness.database.mail.TAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.mail.TAssegnEmail;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TUtentiModPec;
import it.eng.core.business.converter.IBeanPopulate;

public class TAssegnEmailBeanToTAssegnEmail implements IBeanPopulate<TAssegnEmailBean, TAssegnEmail> {

	@Override
	public void populate(TAssegnEmailBean src, TAssegnEmail dest) throws Exception {
		if (src.getIdEmail() != null) {
			TEmailMgo tEmailMgo = new TEmailMgo();
			tEmailMgo.setIdEmail(src.getIdEmail());
			dest.setTEmailMgo(tEmailMgo);
		}
		if (src.getIdUtenteDest() != null) {
			TUtentiModPec utente = new TUtentiModPec();
			utente.setIdUtente(src.getIdUtenteDest());
			dest.setTUtentiModPecByIdUtenteDest(utente);
		}
		if (src.getIdUtenteMitt() != null) {
			TUtentiModPec utente = new TUtentiModPec();
			utente.setIdUtente(src.getIdUtenteMitt());
			dest.setTUtentiModPecByIdUtenteMitt(utente);
		}
		if (src.getIdFruitoreDest() != null) {
			TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
			fruitore.setIdFruitoreCasella(src.getIdFruitoreDest());
			dest.setTAnagFruitoriCaselle(fruitore);
		}

	}

	@Override
	public void populateForUpdate(TAssegnEmailBean src, TAssegnEmail dest) throws Exception {
		if (src.hasPropertyBeenModified("idEmail")) {
			if (src.getIdEmail() != null) {
				TEmailMgo tEmailMgo = new TEmailMgo();
				tEmailMgo.setIdEmail(src.getIdEmail());
				dest.setTEmailMgo(tEmailMgo);
			} else {
				dest.setTEmailMgo(null);
			}
		}
		if (src.hasPropertyBeenModified("idUtenteDest")) {
			if (src.getIdUtenteDest() != null) {
				TUtentiModPec utente = new TUtentiModPec();
				utente.setIdUtente(src.getIdUtenteDest());
				dest.setTUtentiModPecByIdUtenteDest(utente);
			} else {
				dest.setTUtentiModPecByIdUtenteDest(null);
			}
		}
		if (src.hasPropertyBeenModified("idUtenteMitt")) {
			if (src.getIdUtenteMitt() != null) {
				TUtentiModPec utente = new TUtentiModPec();
				utente.setIdUtente(src.getIdUtenteMitt());
				dest.setTUtentiModPecByIdUtenteMitt(utente);
			} else {
				dest.setTUtentiModPecByIdUtenteMitt(null);
			}
		}
		if (src.hasPropertyBeenModified("idFruitoreDest")) {
			if (src.getIdFruitoreDest() != null) {
				TAnagFruitoriCaselle fruitore = new TAnagFruitoriCaselle();
				fruitore.setIdFruitoreCasella(src.getIdFruitoreDest());
				dest.setTAnagFruitoriCaselle(fruitore);
			} else {
				dest.setTAnagFruitoriCaselle(null);
			}
		}
	}

}