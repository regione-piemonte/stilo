/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.DateUtil;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PieChartUtility.PieChartCallback;
import it.eng.auriga.ui.module.layout.client.applet.HybridEng;
import it.eng.auriga.ui.module.layout.client.protocollazione.HybridWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

public class PieChartHybridWindow extends HybridWindow {

	protected final HybridEng instanceApplet;
	protected HybridWindow instanceWindow;

	private PieChartCallback returnCallback;

	public PieChartHybridWindow(Date lDateDa, Date lDateA, String tipoReport, String tipoRegistrazione, PieChartCallback returnCallback) {
		super("Statistiche protocollo", "PieChartApplet.jar");
		instanceWindow = this;
		Map<String, String> lMapParams = new HashMap<String, String>();
		String contextPath = GWT.getHostPageBaseURL();
		LoginBean lLoginBean = Layout.getUtenteLoggato();
		lMapParams.put("percentageServlet", contextPath + "springdispatcher/percentageServlet");
		lMapParams.put("pdfBuilderPercentageServlet", contextPath + "springdispatcher/pdfBuilderPercentageServlet");
		lMapParams.put("xlsBuilderPercentageServlet", contextPath + "springdispatcher/xlsBuilderPercentageServlet");
		lMapParams.put("idSchema", lLoginBean.getSchema());
		lMapParams.put("idUtente", lLoginBean.getIdUtente());
		lMapParams.put("idDominio",AurigaLayout.getIdDominio());
		lMapParams.put("tipoReport", tipoReport);
		lMapParams.put("tipoDiRegistrazione", tipoRegistrazione);
		lMapParams.put("richiesta", "-1");

		lMapParams.put("da", DateUtil.formatAsShortDate(lDateDa));
		lMapParams.put("a", DateUtil.formatAsShortDate(lDateA));
		lMapParams.put("level", "0");

		HybridEng lAppletEng = new HybridEng("StatisticheProtocolliApplet", "StampaEtichettaApplet.jnlp", lMapParams, 800, 400, true, true, this,
				new String[] { "PieChartApplet.jar" }, "it.eng.applet.PieChartApplet") {

			@Override
			public void uploadFromServlet(String file) {

			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {

			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return null;
			}

			@Override
			protected void startHybrid(JavaScriptObject jsParams) {
				callHybrid(jsParams);
			}
		};
		instanceApplet = lAppletEng;
		// addItem(lAppletEng);
	}

	public static native void callHybrid(JavaScriptObject lMapParams) /*-{
		$wnd.doPieChart(lMapParams);
	}-*/;

}
