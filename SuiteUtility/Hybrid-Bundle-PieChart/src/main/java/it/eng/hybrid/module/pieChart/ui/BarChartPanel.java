package it.eng.hybrid.module.pieChart.ui;

import it.eng.hybrid.module.pieChart.dataset.BarChartProperty;
import it.eng.hybrid.module.pieChart.generator.CustomBarToolTipGenerator;
import it.eng.hybrid.module.pieChart.url.CustomBarUrlGenerator;
import it.eng.hybrid.module.pieChart.url.UrlGeneratorInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleEdge;

public class BarChartPanel extends CustomChartPanel {

	private static final long serialVersionUID = -7534282410609573321L;
	private CustomBarUrlGenerator urlGenerator;
	
	public BarChartPanel(BarChartProperty pBarChartProperty, final PieChartApplication pieChartAppl,final LeftClickAction pLeftClickAction, 
			CustomBarUrlGenerator pCustomBarUrlGenerator, boolean generateUrl,
			PieMenuItem... pPieMenuItem){
		super(ChartFactory.createBarChart(pBarChartProperty.getTitle(), pBarChartProperty.getAssexLabel(), pBarChartProperty.getAsseyLabel(),
				pBarChartProperty.getDataset(),  PlotOrientation.VERTICAL,          // data
				true,              // no legend
				true,               // tooltips
				generateUrl               // no URL generation
		), pieChartAppl,  generateUrl,   pLeftClickAction, pCustomBarUrlGenerator,
		pPieMenuItem);
		pieChartAppl.setAsseX(pBarChartProperty.getAssexLabel());
		pieChartAppl.setAsseY(pBarChartProperty.getAsseyLabel());
		
	}

	@Override
	public void setUrlLabelGenerator(boolean generateUrl,
			UrlGeneratorInterface pUrlGenerator) {
		CategoryPlot categoryplot = (CategoryPlot) getChart().getPlot();
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		barrenderer.setMaximumBarWidth(.20);
		final GradientPaint gp2 = new GradientPaint(
				0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, Color.lightGray
		);
		barrenderer.setSeriesPaint(0, gp2);
		urlGenerator = (CustomBarUrlGenerator)pUrlGenerator;
		if (generateUrl){
			barrenderer.setBaseItemURLGenerator(urlGenerator);
		}	
		barrenderer.setBaseToolTipGenerator(new CustomBarToolTipGenerator(urlGenerator.getLevel()));

	}
}
