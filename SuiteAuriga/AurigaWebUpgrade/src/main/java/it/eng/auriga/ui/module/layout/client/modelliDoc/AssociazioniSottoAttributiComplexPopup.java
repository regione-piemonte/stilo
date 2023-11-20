/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AssociazioniSottoAttributiComplexPopup extends ModalWindow {
		
	protected AssociazioniSottoAttributiComplexPopup _window;
	
	protected Record record;
	
	protected String nomeTabella;
	protected String idEntitaAssociata;
	protected String nomeAttributoComplex;
	
	protected DynamicForm mDynamicForm;
	protected AssociazioniAttributiCustomItem associazioniSottoAttributiComplexItem;
	
	protected Canvas portletLayout;
	
	protected DetailToolStripButton okButton;
	
	public AssociazioniSottoAttributiComplexPopup(String title, final String nomeTabella, final String idEntitaAssociata, final Record record){
		
		super("sottoAttributiComplex", true);
		
		_window = this;

		this.record = record;		
		
		this.nomeTabella = nomeTabella;		
		this.idEntitaAssociata = idEntitaAssociata;		
		this.nomeAttributoComplex = record != null ? record.getAttribute("nomeAttributoCustom") : null;		
				
		setTitle(title);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);						
		
		mDynamicForm = new DynamicForm();													
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(8);
		mDynamicForm.setColWidths(120,1,1,1,1,1,"*","*");		
		mDynamicForm.setCellPadding(5);		
		mDynamicForm.setWrapItemTitles(false);		
		
		associazioniSottoAttributiComplexItem = new AssociazioniAttributiCustomItem() {
			
			@Override
			public String getNomeTabella() {
				return nomeTabella;
			};	
			
			@Override
			public String getIdEntitaAssociata() {
				return idEntitaAssociata;
			};	
			
			@Override
			public String getNomeAttributoComplex() {
				return nomeAttributoComplex;
			}	
			
			@Override
			public boolean isAssociazioneSottoAttributo() {
				return true;
			}
			
		};		
		associazioniSottoAttributiComplexItem.setName("listaAssociazioniSottoAttributiComplex");
		associazioniSottoAttributiComplexItem.setShowTitle(false);
		associazioniSottoAttributiComplexItem.setNotReplicable(true);
		associazioniSottoAttributiComplexItem.setCanEdit(true);
		
		mDynamicForm.setFields(new FormItem[]{associazioniSottoAttributiComplexItem});		
		
		okButton = new DetailToolStripButton("Ok", "ok.png");
		okButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				onOkButtonClick(mDynamicForm.getValuesAsRecord().getAttributeAsRecordList("listaAssociazioniSottoAttributiComplex"));	
				_window.markForDestroy();
			}   
		}); 

		// Creo la TOOLSTRIP e aggiungo i bottoni	        
		ToolStrip detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		//detailToolStrip.addButton(backButton);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(okButton);	
		 		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout mainLayout = new VLayout();  
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		mainLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(mDynamicForm);
		
		mainLayout.addMember(layout);		
		mainLayout.addMember(detailToolStrip);
		
		setBody(mainLayout);
				
		setIcon("buttons/key.png");
	        		
		mDynamicForm.editRecord(record);				
		_window.show();
	}
	
	public void onOkButtonClick(RecordList listaSottoAttributiComplex) {
		
	}
	
}
