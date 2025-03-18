/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.common.bean.HashFileBean;

import java.io.File;
import java.util.List;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;

public interface ISmartCard {

	public void selectedTab(int index);
	public NimRODLookAndFeel getLookFeel();
	public HashFileBean getBean();
	public List<HashFileBean> getHashfilebean();
	public String getBaseurl();
	public String getCookie();
	
	
}
