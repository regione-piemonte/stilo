/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.HashMap;
import java.util.Map;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;




public class ScadenzeConfigurateList extends CustomList {

	private ListGridField descrizioneScadenze;
	private ListGridField flgTipo;
	private ListGridField durataPeriodo;
	private ListGridField validoDal;
	private ListGridField validoFinoAl;

	public ScadenzeConfigurateList(String nomeEntita) {
		super(nomeEntita);

		descrizioneScadenze = new ListGridField("descrizioneScadenze", I18NUtil.getMessages().scadenze_configurate_descrizione());

		flgTipo = new ListGridField("tipo", I18NUtil.getMessages().scadenze_configurate_Tipo());		
		flgTipo.setType(ListGridFieldType.ICON);
		flgTipo.setWidth(30);
		flgTipo.setIconWidth(16);
		flgTipo.setIconHeight(16);
		Map<String, String> flgTipoIcons = new HashMap<String, String>();		
		flgTipoIcons.put("S", "buttons/puntuale.png");
		flgTipoIcons.put("P", "buttons/durativo.png");
		flgTipo.setValueIcons(flgTipoIcons);
		flgTipo.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if("P".equals(record.getAttribute("tipo"))) {
					return "relativa a durata";
				}else if("S".equals(record.getAttribute("tipo"))) {
					return "scadenza puntuale";
				}				
				return null;
			}
		});

		durataPeriodo = new ListGridField("durataPeriodo", I18NUtil.getMessages().scadenze_configurate_Durata());

		validoDal = new ListGridField("validoDal", I18NUtil.getMessages().scadenze_configurate_Valida_dal());
		validoDal.setType(ListGridFieldType.DATE);
		validoDal.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		validoFinoAl = new ListGridField("validoFinoAl", I18NUtil.getMessages().scadenze_configurate_Valida_fino_al());
		validoFinoAl.setType(ListGridFieldType.DATE);
		validoFinoAl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		setFields(descrizioneScadenze,
				flgTipo,
				durataPeriodo,
				validoDal,
				validoFinoAl
		);
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}

	@Override
	protected Canvas createFieldCanvas(String fieldName, ListGridRecord record) {

		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);

			recordCanvas.addMember(detailButton);			
			recordCanvas.addMember(modifyButton);		

			lCanvasReturn = recordCanvas;
		}
		
		return lCanvasReturn;
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);	
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		}, new DSRequest());	
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};
	 
    @Override
	protected boolean showDetailButtonField() {
		
		return true;
	}
   
    @Override
	protected boolean showModifyButtonField() {
		
		return true;
	}
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}