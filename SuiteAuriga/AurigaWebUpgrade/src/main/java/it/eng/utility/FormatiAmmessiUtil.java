/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_formati_el_ammessi.bean.DmpkFormatiElAmmessiTestmimetypeforammBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.client.DmpkFormatiElAmmessiTestmimetypeforamm;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class FormatiAmmessiUtil {

	public boolean isFormatiAmmessi(HttpSession session, MimeTypeFirmaBean infoFile) throws Exception {
		
		if(infoFile == null || StringUtils.isBlank(infoFile.getMimetype())) {
			return false;
		}
		
		String mimetypes = StringUtils.isNotBlank(infoFile.getMimetypesContenuto()) ? infoFile.getMimetypesContenuto() : infoFile.getMimetype(); 
				
		//se devo controllare più formati metto i mimetype separati da ;
		DmpkFormatiElAmmessiTestmimetypeforamm lDmpkFormatiElAmmessiTestmimetypeforamm = new DmpkFormatiElAmmessiTestmimetypeforamm();
		DmpkFormatiElAmmessiTestmimetypeforammBean lDmpkFormatiElAmmessiTestmimetypeforammBean = new DmpkFormatiElAmmessiTestmimetypeforammBean();
		lDmpkFormatiElAmmessiTestmimetypeforammBean.setMimetypein(mimetypes);

		StoreResultBean<DmpkFormatiElAmmessiTestmimetypeforammBean> storeResultBean = 
				lDmpkFormatiElAmmessiTestmimetypeforamm.execute(UserUtil.getLocale(session), AurigaUserUtil.getLoginInfo(session), lDmpkFormatiElAmmessiTestmimetypeforammBean);

		if (storeResultBean.isInError()){
			throw new StoreException(storeResultBean);
		}

		return storeResultBean.getResultBean().getFlgfmtammessoout() != null && storeResultBean.getResultBean().getFlgfmtammessoout().intValue() == 1;
	}
	
}
