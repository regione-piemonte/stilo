/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolder;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolderId;
import it.eng.core.business.converter.IBeanPopulate;

public class TRelEmailFolderBeanToTRelEmailFolder implements IBeanPopulate<TRelEmailFolderBean, TRelEmailFolder> {

	@Override
	public void populate(TRelEmailFolderBean src, TRelEmailFolder dest) throws Exception {
		if (src.getIdEmail() != null && src.getIdFolderCasella() != null) {
			TRelEmailFolderId chiave = new TRelEmailFolderId();
			chiave.setIdEmail(src.getIdEmail());
			chiave.setIdFolderCasella(src.getIdFolderCasella());
			dest.setId(chiave);
		}
	}

	@Override
	public void populateForUpdate(TRelEmailFolderBean src, TRelEmailFolder dest) throws Exception {
		if (src.hasPropertyBeenModified("idEmail")) {
			if (src.getIdEmail() != null) {
				TRelEmailFolderId chiave = new TRelEmailFolderId();
				chiave.setIdEmail(src.getIdEmail());
				dest.setId(chiave);
			} else {
				dest.setId(null);
			}
		}
		if (src.hasPropertyBeenModified("idFolderCasella")) {
			if (src.getIdFolderCasella() != null) {
				TRelEmailFolderId chiave = new TRelEmailFolderId();
				chiave.setIdFolderCasella(src.getIdFolderCasella());
				dest.setId(chiave);
			} else {
				dest.setId(null);
			}
		}
	}

}