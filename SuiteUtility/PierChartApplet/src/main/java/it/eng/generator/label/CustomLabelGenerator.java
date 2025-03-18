/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.applet.dataset.bean.PieDataBean;

import java.text.AttributedString;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

public class CustomLabelGenerator implements PieSectionLabelGenerator {

	@Override
	public AttributedString generateAttributedSectionLabel(PieDataset arg0,
			Comparable arg1) {
		PieDataBean lPieDataBean = (PieDataBean)arg1;
		return new AttributedString(lPieDataBean.getLabel());
	}

	@Override
	public String generateSectionLabel(PieDataset arg0, Comparable arg1) {
		PieDataBean lPieDataBean = (PieDataBean)arg1;
		return lPieDataBean.getLabel() + " (" + lPieDataBean.getPercArrotondata() + "%)";
	}

}
