/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class UoProtocollistiPopup extends Window {
	
	private UoProtocollistiPopup window;
	
	static Map<String,Object> map = new HashMap<String,Object>();
	
	private DynamicForm form;
	protected SelectItem selectUo;
	
	public UoProtocollistiPopup(final ServiceCallback<Record> callback){
		
		window = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(100);
		setWidth(450);	
		setKeepInParentRect(true);		
		setTitle("Lista Uo Collegate");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
						   
		final GWTRestDataSource uoDS = new GWTRestDataSource("LoadComboUoProtocollistiDataSource");
		selectUo = new SelectItem("selectUo", "Seleziona UO");
		selectUo.setOptionDataSource(uoDS);
		selectUo.setAutoFetchData(true);
		selectUo.setDisplayField("value");
		selectUo.setValueField("key");			
		selectUo.setWidth(300);
		selectUo.setWrapTitle(false);
		selectUo.setAllowEmptyValue(false);
		selectUo.setRedrawOnChange(true);
		selectUo.setClearable(true);
		selectUo.setStartRow(true);
		selectUo.setRequired(true);
		
		form.setFields(selectUo);
		
		addItem(form);	
		
		Button okButton = new Button("OK");   
		okButton.setIcon("ok.png");
		okButton.setIconSize(16); 
		okButton.setAutoFit(false);	
		okButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (selectUo.validate()) {
					window.markForDestroy();
					if(callback != null) {
						Record lRecord= new Record();
						lRecord = selectUo.getSelectedRecord();
						callback.execute(lRecord);
					}
				}
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(okButton);
		_buttons.addMember(annullaButton);	
		_buttons.setBackgroundColor(CustomDetail.backgroundColor);
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		draw();
	}
		
}