/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;

public class HybridWindow {

	protected String appletJarName;

	public HybridWindow(String title, String appletJarName) {
		this.appletJarName = appletJarName;
	}

	public static String getSignerAppletMultiJarVersion() {
		String versione = AurigaLayout.getParametroDB("SIGNER_APPLET_MULTI_VER");
		if (versione != null && !"".equals(versione.trim())) {
			return "_" + versione;
		}
		return "";
	}

}
