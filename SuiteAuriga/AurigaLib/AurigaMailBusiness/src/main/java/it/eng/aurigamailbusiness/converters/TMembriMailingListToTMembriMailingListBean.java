/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TMembriMailingListBean;
import it.eng.aurigamailbusiness.database.mail.TMembriMailingList;
import it.eng.core.business.converter.IBeanPopulate;

public class TMembriMailingListToTMembriMailingListBean implements IBeanPopulate<TMembriMailingList, TMembriMailingListBean> {

	@Override
	public void populate(TMembriMailingList src, TMembriMailingListBean dest) throws Exception {
		if (src.getTRubricaEmailByIdVoceRubricaMembro() != null) {
			dest.setIdVoceRubricaMembro(src.getTRubricaEmailByIdVoceRubricaMembro().getIdVoceRubricaEmail());
		}
		if (src.getTRubricaEmailByIdVoceRubricaMailingList() != null) {
			dest.setIdVoceRubricaMailingList(src.getTRubricaEmailByIdVoceRubricaMailingList().getIdVoceRubricaEmail());
		}
	}

	@Override
	public void populateForUpdate(TMembriMailingList src, TMembriMailingListBean dest) throws Exception {
		if (src.getTRubricaEmailByIdVoceRubricaMembro() != null) {
			dest.setIdVoceRubricaMembro(src.getTRubricaEmailByIdVoceRubricaMembro().getIdVoceRubricaEmail());
		}
		if (src.getTRubricaEmailByIdVoceRubricaMailingList() != null) {
			dest.setIdVoceRubricaMailingList(src.getTRubricaEmailByIdVoceRubricaMailingList().getIdVoceRubricaEmail());
		}
	}

}