/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.auriga.ui.module.layout.client.report.PieChartAppletWindow;
import it.eng.auriga.ui.module.layout.client.report.PieChartHybridWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;

public class PieChartUtility {

	public interface PieChartCallback {

		void execute(String nomeFileFirmato, String uriFileFirmato, String record);
	}

	public static void pieChart(Date lDateDa, Date lDateA, String tipoReport, String tipoRegistrazione, PieChartCallback callback) {
		String modalitaPieChart = AurigaLayout.getParametroDB("MODALITA_REPORT");
		if (modalitaPieChart == null || "".equalsIgnoreCase(modalitaPieChart) || "APPLET".equalsIgnoreCase(modalitaPieChart)
				|| BrowserUtility.detectIfIEBrowser()) {
			PieChartAppletWindow lPieChartWindow = new PieChartAppletWindow(lDateDa, lDateA, tipoReport, tipoRegistrazione, callback);
			lPieChartWindow.show();
		} else {
			new PieChartHybridWindow(lDateDa, lDateA, tipoReport, tipoRegistrazione, callback);
			// Non serve fare lo show come nelle applet
		}
	}

}
