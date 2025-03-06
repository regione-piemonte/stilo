package it.eng.generator.url;

import it.eng.applet.PieChartApplet;
import it.eng.parameter.ReportParameterBean;

public class UrlBuilder {

	public static String buildUrl(){
		ReportParameterBean lReportParameterBean = PieChartApplet.getParameters();
		String url = lReportParameterBean.getPercentageServlet() + "?idUtente=" + lReportParameterBean.getIdUtente() +
		 "&idSchema=" + lReportParameterBean.getIdSchema() + "&idDominio=" + lReportParameterBean.getIdDominio() +
		 "&richiesta=" + lReportParameterBean.getRichiesta() +"&level=" + lReportParameterBean.getLevel() +
		 "&da=" + lReportParameterBean.getDa() + "&a=" + lReportParameterBean.getA() + "&tipoReport=" + lReportParameterBean.getTipoReport()+
		 "&tipoDiRegistrazione=" + lReportParameterBean.getTipoRegistrazione();
		System.out.println(url);
		return url;
	}
}
