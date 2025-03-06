package it.eng.hybrid.module.pieChart.url;

import it.eng.hybrid.module.pieChart.parameters.ReportParameterBean;
import it.eng.hybrid.module.pieChart.ui.PieChartApplication;

public class UrlBuilder {

	public static String buildUrl(){
		ReportParameterBean lReportParameterBean = PieChartApplication.getParameters();
		String url = lReportParameterBean.getPercentageServlet() + "?idUtente=" + lReportParameterBean.getIdUtente() +
		 "&idSchema=" + lReportParameterBean.getIdSchema() + "&idDominio=" + lReportParameterBean.getIdDominio() +
		 "&richiesta=" + lReportParameterBean.getRichiesta() +"&level=" + lReportParameterBean.getLevel() +
		 "&da=" + lReportParameterBean.getDa() + "&a=" + lReportParameterBean.getA() + "&tipoReport=" + lReportParameterBean.getTipoReport()+
		 "&tipoDiRegistrazione=" + lReportParameterBean.getTipoRegistrazione();
		System.out.println(url);
		return url;
	}
}
