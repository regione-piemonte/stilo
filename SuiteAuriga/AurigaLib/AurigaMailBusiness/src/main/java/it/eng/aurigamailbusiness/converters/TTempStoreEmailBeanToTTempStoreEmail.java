/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TTempStoreEmailBean;
import it.eng.aurigamailbusiness.database.mail.TTempStoreEmail;
import it.eng.core.business.converter.IBeanPopulate;

public class TTempStoreEmailBeanToTTempStoreEmail implements IBeanPopulate<TTempStoreEmailBean,TTempStoreEmail> {

	@Override
	public void populate(TTempStoreEmailBean src, TTempStoreEmail dest) throws Exception {
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
	public void populateForUpdate(TTempStoreEmailBean src, TTempStoreEmail dest) throws Exception {
		// TODO Auto-generated method stub

	}

}