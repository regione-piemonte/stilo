/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ControlloRepertoriazioneBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "ControlloRepertoriazioneDataSource")
public class ControlloRepertoriazioneDataSource extends AbstractServiceDataSource<LoginBean, ControlloRepertoriazioneBean>{

	@Override
	public ControlloRepertoriazioneBean call(LoginBean pInBean) throws Exception {
		
		//calcolo il numero di giorni tra la data corrente ed il primo giorno dell'anno
		DateTime currentDate = new DateTime();
		DateTime firstDayOfTheYear = new DateTime(currentDate.getYear(), 1, 1, 0, 0, 0, 0);
		
		int giorniDiff = Days.daysBetween(firstDayOfTheYear, currentDate).getDays();
		
		String ggEntroXRprAnnoPassato = ParametriDBUtil.getParametroDB(getSession(), "GG_ENTRO_X_RPR_ANNO_PASSATO");
		
		ControlloRepertoriazioneBean lControlloRepertoriazioneBean = new ControlloRepertoriazioneBean();
		lControlloRepertoriazioneBean.setShowCheckAnnoPassato(StringUtils.isNotBlank(ggEntroXRprAnnoPassato) && giorniDiff <= Integer.parseInt(ggEntroXRprAnnoPassato));
				 
		return lControlloRepertoriazioneBean;
	}



}
