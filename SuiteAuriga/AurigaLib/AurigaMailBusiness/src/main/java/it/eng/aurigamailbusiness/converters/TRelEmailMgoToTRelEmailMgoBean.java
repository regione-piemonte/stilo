/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.database.mail.TRelEmailMgo;
import it.eng.core.business.converter.IBeanPopulate;

public class TRelEmailMgoToTRelEmailMgoBean implements IBeanPopulate<TRelEmailMgo, TRelEmailMgoBean> {

	@Override
	public void populate(TRelEmailMgo src, TRelEmailMgoBean dest) throws Exception {
		if (src.getTDestinatariEmailMgo() != null) {
			dest.setIdDestinatario2(src.getTDestinatariEmailMgo().getIdDestinatarioEmail());
		}
		if (src.getTEmailMgoByIdEmail1() != null) {
			dest.setIdEmail1(src.getTEmailMgoByIdEmail1().getIdEmail());
		}
		if (src.getTEmailMgoByIdEmail2() != null) {
			dest.setIdEmail2(src.getTEmailMgoByIdEmail2().getIdEmail());
		}
		if (src.getTValDizionarioByIdRecDizRuolo1Vs2() != null) {
			dest.setIdRecDizRuolo1Vs2(src.getTValDizionarioByIdRecDizRuolo1Vs2().getIdRecDiz());
		}
		if (src.getTValDizionarioByIdRecDizCategRel() != null) {
			dest.setIdRecDizCategRel(src.getTValDizionarioByIdRecDizCategRel().getIdRecDiz());
		}
	}

	@Override
	public void populateForUpdate(TRelEmailMgo src, TRelEmailMgoBean dest) throws Exception {
		if (src.getTDestinatariEmailMgo() != null) {
			dest.setIdDestinatario2(src.getTDestinatariEmailMgo().getIdDestinatarioEmail());
		}
		if (src.getTEmailMgoByIdEmail1() != null) {
			dest.setIdEmail1(src.getTEmailMgoByIdEmail1().getIdEmail());
		}
		if (src.getTEmailMgoByIdEmail2() != null) {
			dest.setIdEmail2(src.getTEmailMgoByIdEmail2().getIdEmail());
		}
		if (src.getTValDizionarioByIdRecDizRuolo1Vs2() != null) {
			dest.setIdRecDizRuolo1Vs2(src.getTValDizionarioByIdRecDizRuolo1Vs2().getIdRecDiz());
		}
		if (src.getTValDizionarioByIdRecDizCategRel() != null) {
			dest.setIdRecDizCategRel(src.getTValDizionarioByIdRecDizCategRel().getIdRecDiz());
		}
	}

}