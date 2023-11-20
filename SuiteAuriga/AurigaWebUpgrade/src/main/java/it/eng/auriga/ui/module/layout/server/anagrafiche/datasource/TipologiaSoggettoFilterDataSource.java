/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.OptionFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "TipologiaSoggettoFilterDataSource")
public class TipologiaSoggettoFilterDataSource extends OptionFetchDataSource<SimpleKeyValueBean> {

	@Override
	public PaginatorBean<SimpleKeyValueBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<SimpleKeyValueBean> lListResult = new ArrayList<SimpleKeyValueBean>();

		// #APA=Altra PA
		// #IAMM=MDG
		// UO;UOI=U.O./ufficio interno
		// UP=Unità di personale
		// #AF=Altra persona fisica
		// #AG=Altra persona giuridica

		SimpleKeyValueBean itemAltraPA = new SimpleKeyValueBean();
		itemAltraPA.setKey("#APA");
		itemAltraPA.setValue("Altra PA");
		lListResult.add(itemAltraPA);

		SimpleKeyValueBean itemIAMM = new SimpleKeyValueBean();
		itemIAMM.setKey("#IAMM");
		String labelMDG = ParametriDBUtil.getParametroDB(getSession(), "LABEL_AOOI_IN_MITT_DEST");
		itemIAMM.setValue(labelMDG != null && !"".equals(labelMDG) ? labelMDG : "MDG");
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "SHOW_AOOI_IN_MITT_DEST")) {
			lListResult.add(itemIAMM);
		}

		SimpleKeyValueBean itemUOUOI = new SimpleKeyValueBean();
		itemUOUOI.setKey("UO;UOI");
		itemUOUOI.setValue("U.O./ufficio interno");
		lListResult.add(itemUOUOI);

		SimpleKeyValueBean itemUP = new SimpleKeyValueBean();
		itemUP.setKey("UP");
		itemUP.setValue("Unità di personale");
		lListResult.add(itemUP);

		SimpleKeyValueBean itemAF = new SimpleKeyValueBean();
		itemAF.setKey("#AF");
		itemAF.setValue("Altra persona fisica");
		lListResult.add(itemAF);

		SimpleKeyValueBean itemAG = new SimpleKeyValueBean();
		itemAG.setKey("#AG");
		itemAG.setValue("Altra persona giuridica");
		lListResult.add(itemAG);

		PaginatorBean<SimpleKeyValueBean> lPaginatorBean = new PaginatorBean<SimpleKeyValueBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setEndRow(lListResult.size());
		lPaginatorBean.setTotalRows(lListResult.size());

		return lPaginatorBean;
	}

}
