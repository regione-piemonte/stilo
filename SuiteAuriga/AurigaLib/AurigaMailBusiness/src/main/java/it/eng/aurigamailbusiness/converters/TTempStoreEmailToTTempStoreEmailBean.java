/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TTempStoreEmailBean;
import it.eng.aurigamailbusiness.database.mail.TTempStoreEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TTempStoreEmailToTTempStoreEmailBean implements IBeanPopulate<TTempStoreEmail,TTempStoreEmailBean> {

	@Override
	public void populate(TTempStoreEmail src, TTempStoreEmailBean dest) throws Exception {
		dest.setIdEmail(src.getIdEmail());
		dest.setMessageId(src.getMessageId());
		dest.setIdCasella(src.getIdCasella());
		dest.setTsInvio(src.getTsInvio());
		dest.setAccountMittente(src.getAccountMittente());
		dest.setOggetto(src.getOggetto());
		dest.setCorpo(src.getCorpo());
		dest.setNomiAllegati(src.getNomiAllegati());
	}

	@Override
	public void populateForUpdate(TTempStoreEmail src, TTempStoreEmailBean dest) throws Exception {
		// TODO Auto-generated method stub

	}

}