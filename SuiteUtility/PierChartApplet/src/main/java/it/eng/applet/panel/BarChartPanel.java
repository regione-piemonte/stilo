package it.eng.applet.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

import it.eng.applet.PieChartApplet;
import it.eng.applet.action.LeftClickAction;
import it.eng.applet.dataset.BarChartProperty;
import it.eng.applet.menu.PieMenuItem;
import it.eng.generator.tooltip.bar.CustomBarToolTipGenerator;
import it.eng.generator.url.UrlGeneratorInterface;
import it.eng.generator.url.bar.CustomBarUrlGenerator;
import it.eng.generator.url.pie.CustomPieUrlGenerator;

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
	
	public BarChartPanel(BarChartProperty pBarChartProperty, final PieChartApplet pieChartApplet,final LeftClickAction pLeftClickAction, 
			CustomBarUrlGenerator pCustomBarUrlGenerator, boolean generateUrl,
			PieMenuItem... pPieMenuItem){
		super(ChartFactory.createBarChart(pBarChartProperty.getTitle(), pBarChartProperty.getAssexLabel(), pBarChartProperty.getAsseyLabel(),
				pBarChartProperty.getDataset(),  PlotOrientation.VERTICAL,          // data
				true,              // no legend
				true,               // tooltips
				generateUrl               // no URL generation
		), pieChartApplet,  generateUrl,   pLeftClickAction, pCustomBarUrlGenerator,
		pPieMenuItem);
		pieChartApplet.setAsseX(pBarChartProperty.getAssexLabel());
		pieChartApplet.setAsseY(pBarChartProperty.getAsseyLabel());
		
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
