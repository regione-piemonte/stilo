/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.bean.AurigaStoppableSearchBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.AurigaStopSessionService;

public class StopSearchUtility  {

	private static Logger log = Logger.getLogger(StopSearchUtility.class);

	public static StopSearchBean stopSearch(StopSearchBean bean, Locale locale, AurigaLoginBean loginBean)throws Exception {

		if(bean != null && StringUtils.isNotBlank(bean.getDatasource()) && StringUtils.isNotBlank(bean.getUuid())) {
			AurigaStoppableSearchBean lAurigaStoppableSearchBean = new AurigaStoppableSearchBean();
			lAurigaStoppableSearchBean.setUuid(bean.getUuid());
			AurigaStopSessionService lAurigaStopSessionService = new AurigaStopSessionService();
			lAurigaStopSessionService.stopsession(locale, loginBean, lAurigaStoppableSearchBean);			
		}
		return bean;
	}

}
