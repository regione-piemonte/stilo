/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.FrontendButton;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.grid.ColumnTree;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.NodeSelectedEvent;
import com.smartgwt.client.widgets.grid.events.NodeSelectedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public abstract class ListaNuoviOst extends VLayout {
	
	protected ListGrid grid; 

	protected String title;
	protected String keyFieldName;
	protected String displayFieldName;
	protected GWTRestDataSource datasource;
	
	private String key;
	private String display;
	
	public ListaNuoviOst(String title, final String keyFieldName, final String displayFieldName, GWTRestDataSource datasource) {	
		
		this.title = title;
		this.keyFieldName = keyFieldName;
		this.displayFieldName = displayFieldName;
		this.datasource = datasource;
		
		setWidth100();
		setBorder("1px solid #5c7c9d");	
		setRedrawOnResize(true);
		setPadding(5);
		
		grid = new ListGrid();  
		grid.setWidth100();
		grid.setHeight(1);
		grid.setAutoFetchData(true);
		grid.setDataSource(datasource);
		grid.setShowHeader(true);
		grid.setAlternateRecordStyles(false);
		grid.setLeaveScrollbarGap(false);		
		grid.setKeepInParentRect(true);
		grid.setLoadingMessage(null);
		grid.setDataFetchMode(FetchMode.BASIC);		
		grid.setBodyOverflow(Overflow.VISIBLE);
		grid.setOverflow(Overflow.VISIBLE);
		grid.setShowEmptyMessage(false);
		grid.setSelectionType(SelectionStyle.SINGLE);
		grid.setShowRollOver(false);		
		grid.setCanReorderFields(false);
		grid.setCanResizeFields(false);
		grid.setCanReorderRecords(false);
		grid.setCanHover(true);		
		grid.setCanGroupBy(false);	
		grid.setCanSort(false);
		grid.setShowHeaderContextMenu(false);
		grid.setNoDoubleClicks(true); 
		grid.setVirtualScrolling(true);      
		grid.setCanEdit(false);		
		grid.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				key = event.getRecord().getAttribute(keyFieldName);	
				display = event.getRecord().getAttribute(displayFieldName);
			}
		});		
		
		ListGridField keyField = new ListGridField(keyFieldName);
		keyField.setHidden(true);
		keyField.setCanHide(false);
		
		ListGridField displayField = new ListGridField(displayFieldName, title);
		
		grid.setFields(keyField, displayField);
		
		FrontendButton buttonAggiungi = new FrontendButton("Aggiungi");		
		buttonAggiungi.setIcon("pratiche/task/buttons/aggiungi.png");		
		buttonAggiungi.setIconSize(30);
		buttonAggiungi.setAlign(Alignment.CENTER);
		buttonAggiungi.setWidth(140);
		buttonAggiungi.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(key != null && !"".equals(key)) {
					manageOnClickAggiungi(key, display);					
				} else {
					Layout.addMessage(new MessageBean("Nessun elemento selezionato", "", MessageType.ERROR));
				}
			}
		});
		
		setMembersMargin(5);
		setMembers(grid, buttonAggiungi);						
	}
	
	public abstract void manageOnClickAggiungi(String key, String display);
	
};

