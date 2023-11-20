/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRelEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TValDizionario;
import it.eng.core.business.converter.IBeanPopulate;

public class TRelEmailMgoBeanToTRelEmailMgo implements IBeanPopulate<TRelEmailMgoBean, TRelEmailMgo> {

	@Override
	public void populate(TRelEmailMgoBean src, TRelEmailMgo dest) throws Exception {
		if (src.getIdEmail1() != null) {
			TEmailMgo email1 = new TEmailMgo();
			email1.setIdEmail(src.getIdEmail1());
			dest.setTEmailMgoByIdEmail1(email1);
		}
		if (src.getIdEmail2() != null) {
			TEmailMgo email2 = new TEmailMgo();
			email2.setIdEmail(src.getIdEmail2());
			dest.setTEmailMgoByIdEmail2(email2);
		}
		if (src.getIdDestinatario2() != null) {
			TDestinatariEmailMgo destina = new TDestinatariEmailMgo();
			destina.setIdDestinatarioEmail(src.getIdDestinatario2());
			dest.setTDestinatariEmailMgo(destina);
		}
		if (src.getIdRecDizRuolo1Vs2() != null) {
			TValDizionario dizionario = new TValDizionario();
			dizionario.setIdRecDiz(src.getIdRecDizRuolo1Vs2());
			dest.setTValDizionarioByIdRecDizRuolo1Vs2(dizionario);
		}
		if (src.getIdRecDizCategRel() != null) {
			TValDizionario dizionario = new TValDizionario();
			dizionario.setIdRecDiz(src.getIdRecDizCategRel());
			dest.setTValDizionarioByIdRecDizCategRel(dizionario);
		}
	}

	@Override
	public void populateForUpdate(TRelEmailMgoBean src, TRelEmailMgo dest) throws Exception {
		if (src.hasPropertyBeenModified("idEmail1")) {
			TEmailMgo email1 = new TEmailMgo();
			email1.setIdEmail(src.getIdEmail1());
			dest.setTEmailMgoByIdEmail1(email1);
		}
		if (src.hasPropertyBeenModified("idEmail2")) {
			TEmailMgo email2 = new TEmailMgo();
			email2.setIdEmail(src.getIdEmail2());
			dest.setTEmailMgoByIdEmail2(email2);
		}
		if (src.hasPropertyBeenModified("idDestinatario2")) {
			TDestinatariEmailMgo destina = new TDestinatariEmailMgo();
			destina.setIdDestinatarioEmail(src.getIdDestinatario2());
			dest.setTDestinatariEmailMgo(destina);
		}
		if (src.hasPropertyBeenModified("idRecDizRuolo1Vs2")) {
			TValDizionario dizionario = new TValDizionario();
			dizionario.setIdRecDiz(src.getIdRecDizRuolo1Vs2());
			dest.setTValDizionarioByIdRecDizRuolo1Vs2(dizionario);
		}
		if (src.hasPropertyBeenModified("idRecDizCategRel")) {
			TValDizionario dizionario = new TValDizionario();
			dizionario.setIdRecDiz(src.getIdRecDizCategRel());
			dest.setTValDizionarioByIdRecDizCategRel(dizionario);
		}
	}
}