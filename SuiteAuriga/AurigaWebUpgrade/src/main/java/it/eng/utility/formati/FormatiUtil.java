/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.formati.bean.FormatiServiceBean;
import it.eng.client.FormatiService;
import it.eng.core.business.TPagingList;
import it.eng.utility.ui.user.AurigaUserUtil;

public class FormatiUtil {
	
	private static Logger mLogger = Logger.getLogger(FormatiUtil.class);
	
	static Map<String, Boolean> mapMimetypeConvertibili;
	
	public static boolean isConvertibile(HttpSession session, String mimetype) throws Exception {
		
		AurigaLoginBean loginBean = session != null ? AurigaUserUtil.getLoginInfo(session) : null;
		
		init(loginBean);
		
		if(mapMimetypeConvertibili == null) {
			throw new Exception("Non sono stati caricati i formati convertibili in fase di inizializzazione");
		} else if (mapMimetypeConvertibili.containsKey(mimetype)){
			return mapMimetypeConvertibili.get(mimetype);
		} else return false;
	}

	public static void init(AurigaLoginBean loginBean) throws Exception {
		
		if(mapMimetypeConvertibili == null && loginBean != null) {
			mapMimetypeConvertibili = new HashMap<String, Boolean>();
			FormatiService lFormatiService = new FormatiService();
			TPagingList<FormatiServiceBean> lListResult = lFormatiService.loadall(Locale.ITALY, loginBean);
			for (FormatiServiceBean lFormatiServiceBean : lListResult.getData()){
				String[] lStringMimetypes = lFormatiServiceBean.getMimetype().split(";");
				for (String mimetype : lStringMimetypes){
					mapMimetypeConvertibili.put(mimetype, lFormatiServiceBean.isConvertibile());
				}
			}			 		
		}
	}
	
}
