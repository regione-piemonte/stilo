/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.Session;

import it.eng.aurigamailbusiness.bean.PttEmailBean;
import it.eng.aurigamailbusiness.database.egr.PttEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class PttEmailToPttEmailBean implements IBeanPopulate<PttEmail, PttEmailBean> {

	Session session;

	public PttEmailToPttEmailBean(Session session) {
		this.session = session;
	}

	public PttEmailToPttEmailBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populate(PttEmail src, PttEmailBean dest) throws Exception {
		if (src.getMessageId() != null) {
			dest.setMessageId(src.getMessageId());
		}
	}

	@Override
	public void populateForUpdate(PttEmail src, PttEmailBean dest) throws Exception {
		if (src.getMessageId() != null) {
			dest.setMessageId(src.getMessageId());
		}
	}

}