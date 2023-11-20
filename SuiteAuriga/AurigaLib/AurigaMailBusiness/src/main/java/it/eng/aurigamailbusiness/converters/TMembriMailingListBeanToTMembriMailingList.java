/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.TMembriMailingListBean;
import it.eng.aurigamailbusiness.database.mail.TMembriMailingList;
import it.eng.aurigamailbusiness.database.mail.TMembriMailingListId;
import it.eng.core.business.converter.IBeanPopulate;

public class TMembriMailingListBeanToTMembriMailingList implements IBeanPopulate<TMembriMailingListBean, TMembriMailingList> {

	@Override
	public void populate(TMembriMailingListBean src, TMembriMailingList dest) throws Exception {
		String idVoceRubricaMailingList = src.getIdVoceRubricaMailingList();
		String idVoceRubricaMembro = src.getIdVoceRubricaMembro();
		TMembriMailingListId lTMembriMailingListId = new TMembriMailingListId(idVoceRubricaMailingList, idVoceRubricaMembro);
		dest.setId(lTMembriMailingListId);

	}

	@Override
	public void populateForUpdate(TMembriMailingListBean src, TMembriMailingList dest) throws Exception {
		String idVoceRubricaMailingList = src.getIdVoceRubricaMailingList();
		String idVoceRubricaMembro = src.getIdVoceRubricaMembro();
		TMembriMailingListId lTMembriMailingListId = new TMembriMailingListId(idVoceRubricaMailingList, idVoceRubricaMembro);
		dest.setId(lTMembriMailingListId);
	}

}