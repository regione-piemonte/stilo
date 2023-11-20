/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import java.util.Map;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author ottavio passalacqua
 *
 */
public class ApplicazioniEsterneDetail extends CustomDetail {

    // DynamicForm
	private DynamicForm formApplicazioniEsterne;
	
	// HiddenItem
	private HiddenItem idApplEsternaItem;
	
	// TextItem
	private TextItem codApplicazioneItem;
	private TextItem codIstanzaItem;
	private TextItem nomeItem;
	
	// CheckboxItem
	private CheckboxItem flgUsaCredenzialiDiverseItem;
	
	public ApplicazioniEsterneDetail(String nomeEntita) {
		super(nomeEntita);
		
	    formApplicazioniEsterne = new DynamicForm();
		formApplicazioniEsterne.setValuesManager(vm);
		formApplicazioniEsterne.setHeight("5");
		formApplicazioniEsterne.setPadding(5);
		formApplicazioniEsterne.setWrapItemTitles(false);
		formApplicazioniEsterne.setNumCols(4);
		formApplicazioniEsterne.setColWidths("1", "1", "1", "*");
		
		idApplEsternaItem = new HiddenItem("idApplEsterna");
		
		codApplicazioneItem = new TextItem("codApplicazione", I18NUtil.getMessages().applicazioni_esterne_codApplicazione_detail());    
		codApplicazioneItem.setLength(30);
		codApplicazioneItem.setRequired(true);
		codApplicazioneItem.setStartRow(true);
		
		codIstanzaItem      = new TextItem("codIstanza",      I18NUtil.getMessages().applicazioni_esterne_codIstanza_detail());
		codIstanzaItem.setLength(30);
		
		nomeItem = new TextItem("nome", I18NUtil.getMessages().applicazioni_esterne_nome_detail());
		nomeItem.setColSpan(3);
		nomeItem.setLength(1000);
		nomeItem.setWidth(575);
		nomeItem.setRequired(true);
		nomeItem.setStartRow(true);
		
		flgUsaCredenzialiDiverseItem = new CheckboxItem("flgUsaCredenzialiDiverse", I18NUtil.getMessages().applicazioni_esterne_flgUsaCredenzialiDiverse_detail());
		flgUsaCredenzialiDiverseItem.setTitleOrientation(TitleOrientation.LEFT);
		flgUsaCredenzialiDiverseItem.setShowTitle(true);
		flgUsaCredenzialiDiverseItem.setLabelAsTitle(true);
		flgUsaCredenzialiDiverseItem.setShowLabel(false);
		flgUsaCredenzialiDiverseItem.setDefaultValue(false);
		flgUsaCredenzialiDiverseItem.setStartRow(true);
		
		
	    formApplicazioniEsterne.setItems(idApplEsternaItem,
	    		                         codApplicazioneItem,
										 codIstanzaItem,
		                                 nomeItem, 
		                                 flgUsaCredenzialiDiverseItem
										 );
	
	
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		lVLayout.addMember(formApplicazioniEsterne);
	
		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
	}
}