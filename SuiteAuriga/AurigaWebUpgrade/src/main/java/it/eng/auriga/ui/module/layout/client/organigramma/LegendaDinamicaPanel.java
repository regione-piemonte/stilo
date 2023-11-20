/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

import com.google.gwt.user.client.ui.InlineHTML;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class LegendaDinamicaPanel extends VLayout {

	private VLayout pannelloLegenda;
	private int numSimboliPerRiga = 4;
	private DynamicForm griglia;

	public LegendaDinamicaPanel() {
		init();
	}

	public LegendaDinamicaPanel(int numSimboliPerRiga) {
		this.numSimboliPerRiga = numSimboliPerRiga;
		init();
	}

	private void init() {
		// setBorderWidth(2);
		this.setAutoWidth();
		this.setLayoutAlign(Alignment.CENTER);

		pannelloLegenda = new VLayout();
		pannelloLegenda.addStyleName("pannelloLegendaDinamica");
		pannelloLegenda.setAutoWidth();
		pannelloLegenda.setEdgeSize(2);
		pannelloLegenda.setShowEdges(true);
		pannelloLegenda.setAutoHeight();
		pannelloLegenda.setHeight(2);
		pannelloLegenda.setOverflow(Overflow.VISIBLE);

		griglia = new DynamicForm();
		griglia = new DynamicForm();
		griglia.setWidth("100%");
		griglia.setHeight("5");
		griglia.setPadding(5);
		griglia.setWrapItemTitles(false);
		griglia.setNumCols(numSimboliPerRiga * 2);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			pannelloLegenda.setCanFocus(false);
			pannelloLegenda.setTabIndex(-1);
			griglia.setCanFocus(false);
			griglia.setTabIndex(-1);					
		} 
		// griglia.setNumCols(10);

		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LegendaOrganigrammaDinamicoDataSource");
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				if (data != null && data.getLength() > 0) {
					riempi(data);
				}
			}
		});
	}

	private void riempi(RecordList data) {
		if (data.getLength() > 0) {
			FormItem[] elementi = new FormItem[data.getLength()];
			for (int i = 0; i < data.getLength(); i++) {

				// Calcolo la posizione del simbolo
				// Ogni simbolo è un pannello, a sua volta composta da 2 elementi
				int numCol = (i % numSimboliPerRiga);
				String nomeIcona = data.get(i).getAttribute("nomeIcona");
				String descrizione = data.get(i).getAttribute("descrizione") + "&nbsp;&nbsp;";
				if (numCol != (numSimboliPerRiga - 1)) {
					descrizione += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}

				ElementoLegenda elem = new ElementoLegenda(nomeIcona, descrizione);
				elementi[i] = elem;
			}
			griglia.setItems(elementi);
		}

		String etichetta = I18NUtil.getMessages().pannelloLeggendaDinamica_title();
		Label legendaLabel = new Label("<b><u><i><font size='2' color='black' face='Arial'>" + etichetta + "</font></i></u></b>");
		legendaLabel.setAutoHeight();
		legendaLabel.setLayoutAlign(Alignment.CENTER);
		legendaLabel.setAutoWidth();
		pannelloLegenda.addMember(getVerticalSpacerItem("10px"));
		pannelloLegenda.addMember(legendaLabel);
		pannelloLegenda.addMember(getVerticalSpacerItem("1px"));
		pannelloLegenda.addMember(griglia);
		addMember(getVerticalSpacerItem("15px"));
		addMember(pannelloLegenda);
		addMember(getVerticalSpacerItem("15px"));
	}

	private InlineHTML getVerticalSpacerItem(String verticalSpace) {
		InlineHTML space = new InlineHTML();
		space.setHeight(verticalSpace);
		return space;
	}

	class ElementoLegenda extends StaticTextItem {

		public ElementoLegenda(String nomeIcona, String descrizione) {
			super();
			setVAlign(VerticalAlignment.BOTTOM);
			setTitleVAlign(VerticalAlignment.BOTTOM);
			setValue("<font color='black' face='Arial'>" + descrizione + "</font>");
			setPrompt(descrizione);
			setWrap(false);
			String imagesrc = "images/organigramma/tipo/UO_" + nomeIcona + ".png";
			setTitle("<img src='" + imagesrc + "' align='bottom' height='16' width='16'");
			setName(descrizione != null ? descrizione.replace(" ", "") : null);
		}
	}
}