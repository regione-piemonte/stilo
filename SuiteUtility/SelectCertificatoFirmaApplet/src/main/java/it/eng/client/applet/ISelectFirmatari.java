/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.nilo.plaf.nimrod.NimRODLookAndFeel;

public interface ISelectFirmatari {

	public void selectedTab(int index);
	public NimRODLookAndFeel getLookFeel();
	public String getBaseurl();
	public String getCookie();
	
	
}
