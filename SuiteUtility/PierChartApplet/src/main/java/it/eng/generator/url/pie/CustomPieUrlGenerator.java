package it.eng.generator.url.pie;

import it.eng.applet.PieChartApplet;
import it.eng.applet.dataset.bean.PieDataBean;
import it.eng.generator.url.UrlGeneratorInterface;
import it.eng.parameter.ReportParameterBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.data.general.PieDataset;

/**
 * Ha il compito di generare l'url con cui andrò a richiamare
 * i valori del grafico di dettaglio.
 * 
 * In questo caso prende la proprietà percentageServletDetail della {@link ReportParameterBean}
 * @author Rametta
 *
 */
public class CustomPieUrlGenerator implements PieURLGenerator, UrlGeneratorInterface {
	
	private String level;
	
	public CustomPieUrlGenerator(String level){
		this.level = level;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public String generateURL(PieDataset arg0, Comparable arg1, int arg2) {
		ReportParameterBean lReportParameterBean = PieChartApplet.getParameters();
		String url;
		PieDataBean lPieDataBean = (PieDataBean)arg1;
		try {
			url = lReportParameterBean.getPercentageServlet() + "?idUtente=" + lReportParameterBean.getIdUtente() +
			"&idSchema=" + lReportParameterBean.getIdSchema() + "&idDominio=" + lReportParameterBean.getIdDominio() +
			"&level=" + level +
			"&richiesta=" + URLEncoder.encode(lPieDataBean.getIdSoggetto(), "UTF-8") +
			 "&da=" + lReportParameterBean.getDa() + "&a=" + lReportParameterBean.getA() + "&tipoReport=" + lReportParameterBean.getTipoReport()+
			 "&tipoDiRegistrazione=" + lReportParameterBean.getTipoRegistrazione();
			System.out.println(url);
			return url;
		} catch (UnsupportedEncodingException e1) {
			return null;
		}
	}
}
