/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Window;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PieChartUtility.PieChartCallback;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.protocollazione.AppletWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

public class PieChartAppletWindow extends AppletWindow {

	protected final AppletEng instanceApplet;
	protected Window instanceWindow;

	public PieChartAppletWindow(Date lDateDa, Date lDateA, String tipoReport, String tipoRegistrazione, PieChartCallback returnCallback) {
		super("Statistiche protocollo", "PieChartApplet" + getStatisticheAppletJarVersion() + ".jar");
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

		AppletEng lAppletEng = new AppletEng("StatisticheProtocolliApplet", "StampaEtichettaApplet.jnlp", lMapParams, 800, 400, true, true, this,
				new String[] {appletJarName}, "it.eng.applet.PieChartApplet") {

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
			public void defaultCloseClick(Window pWindow) {
				pWindow.markForDestroy();
			}
		};
		instanceApplet = lAppletEng;
		addItem(lAppletEng);
	}

}
