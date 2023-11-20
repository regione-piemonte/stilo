/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class CustomSimpleTreeLayout extends CustomLayout implements TreeSectionVisibility{
	
	public final int CUSTOM_SIMPLE_TREE_SETION_DEFAULT_WIDTH = 275;
	
	protected GWTRestDataSource treedatasource;

	protected final CustomSimpleTree tree;
	
	protected VLayout navigationLayout;

	protected ToolStrip navigationToolStrip; 	
	protected Label label; 	
	protected Label searchNodeLabel; 	
	
	public CustomSimpleTreeLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource, final CustomSimpleTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail) {
		
		super(nomePortlet, nomeEntita, pDatasource, filter, list, detail);		
		
		this.tree = tree;			
		this.tree.setLayout(this);  		
		
		this.treedatasource = pTreeDatasource;
		this.treedatasource.setForceToShowPrompt(true);
		
		this.tree.setDataSource(treedatasource);		
		
		navigationToolStrip = new ToolStrip();
		navigationToolStrip.setPaddingAsLayoutMargin(false);
		navigationToolStrip.setWidth100();
		navigationToolStrip.setHeight(20);
		
		label = new Label();
		label.setStyleName(it.eng.utility.Styles.formTitle);
		label.setAutoFit(true);	
		label.setWrap(false);
		
		searchNodeLabel = new Label();		
		searchNodeLabel.setHeight(28);
        searchNodeLabel.setAutoFit(true);
		searchNodeLabel.setWrap(false);
		
		navigationToolStrip.setMembers(label, searchNodeLabel);
				
		leftLayout.addMember(tree);
		leftLayout.setWidth(getTreeSectionDefaultWidth());
		leftLayout.setShowResizeBar(true);
		leftLayout.setOverflow(Overflow.AUTO);
				
		mainLayout.setStyleName(it.eng.utility.Styles.detailLayout);
		mainLayout.setMembers(leftLayout, rightLayout);
		
		navigationLayout = new VLayout();
		navigationLayout.setStyleName(it.eng.utility.Styles.navigationLayout);		
		navigationLayout.setWidth100();
		navigationLayout.setHeight(30);
//		navigationLayout.setShowResizeBar(true);
		
		navigationLayout.setMembers(navigationToolStrip);
		
		sectionStack = new SectionStack();
    	sectionStack.setWidth100();
    	sectionStack.setHeight100();
        sectionStack.setVisibilityMode(VisibilityMode.MUTEX);  
        sectionStack.setAnimateSections(false);         
        sectionStack.setOverflow(Overflow.AUTO);  
        
	    searchSection = new SectionStackSection();  
	    searchSection.setShowHeader(false);  
	    searchSection.setExpanded(true);  
	    searchSection.setItems(navigationLayout, mainLayout);  
	  
	  	detailSection = new SectionStackSection();  
    	detailSection.setShowHeader(false);
	    detailSection.setExpanded(false);  
	    detailSection.setItems(detailLayout); 
	        
	    sectionStack.setSections(searchSection, detailSection);
			
		setMembers(sectionStack);		    
		
		setMultiselect(getDefaultMultiselect());
		setDetailAuto(getDefaultDetailAuto());
		setMultiLookup(false);
		setLookup(false);
		hideDetail();		
		
		rightLayout.hide();
		
		caricaPreferenceTreeVisibility();
		
	}	
	
	public void setSearchNodeLabelContents(String contents) {
		if(contents != null) {			
			label.setContents("&nbsp;<font style=\"font-weight: bold\">" + I18NUtil.getMessages().navigatorPercorsoItem_title() + "</font>&nbsp;:&nbsp;&nbsp;&nbsp;");
			searchNodeLabel.setContents(contents);
		}
	}
	
	public void changeLayout(String nomeEntita, final GWTRestDataSource datasource, final ConfigurableFilter pFilter, final CustomList pList, final CustomDetail pDetail) {
		
		this.nomeEntita = nomeEntita;
		
		if (this.filter != null) {			
			this.filter.markForDestroy();
		}
		
		if (this.list != null) {			
			this.list.markForDestroy();
		}
		
		if (this.detail != null) {			
			this.detail.markForDestroy();
		}
		
		this.filter = pFilter;
		this.list = pList;
		this.detail = pDetail;	
		
		configLista = Layout.getListConfig(list != null ? list.getNomeEntita() : this.nomeEntita);
		fullScreenDetail = configLista.getFullScreenDetail();

		this.filter.setLayout(this);
		this.list.setLayout(this);
		this.detail.setLayout(this);
		
		this.datasource = datasource;
		this.list.setDataSource(datasource);
		this.detail.setDataSource(datasource);
		
		listFields = list.getAllFields();		
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			// Questa operazione viene fatto per aggiornare il tabindex INIZIO
			Canvas[] members = this.filterButtons.getMembers();
			
			HStack filtriBottoni;
			filtriBottoni = new HStack(3);
			filtriBottoni.setPadding(5);
			
			for (Canvas singleMember : members)
			{
				filtriBottoni.addMember(singleMember);
			}
			this.filterButtons = filtriBottoni;		
 		}
		
//		filterLayout = new VLayout();        
		filterLayout.setMembers(filterToolStrip, filter, filterButtons);  	
		
		if(filter.isConfigured) {
			filterLayout.setVisible(true);
			filterLayout.setShowResizeBar(true);
		} else {
			filterLayout.setVisible(false);
		}
		
		bodyListLayout.setMembers(list);	
//		listLayout.setMembers(topListToolStrip, bodyListLayout, bottomListToolStrip);
		
		detailLayout.setMembers(detail, detailToolStrip);		
		
//		searchLayout.setMembers(filterLayout, listLayout);		
//		leftLayout.setMembers(tree);		
//		rightLayout.setMembers(searchLayout, multiLookupLayout);
//		mainLayout.setMembers(leftLayout, rightLayout);
				
		filterLayout.markForRedraw();
		bodyListLayout.markForRedraw();
//		listLayout.markForRedraw();
		detailLayout.markForRedraw();	
//		searchLayout.markForRedraw();
//		leftLayout.markForRedraw();	
//		rightLayout.markForRedraw();
//		mainLayout.markForRedraw();
		
//		this.markForRedraw();	
		
		if(maxRecordVisualizzabiliItem != null) {
			maxRecordVisualizzabiliItem.setValue("");
			if(showMaxRecordVisualizzabiliItem()) {			
				maxRecordVisualizzabiliItem.show();
			} else {
				maxRecordVisualizzabiliItem.hide();
			}
		}
		
		if(nroRecordXPaginaItem != null) {
			nroRecordXPaginaItem.setValue("");
			if(showPaginazioneItems()) {
				nroRecordXPaginaItem.show();
			} else {
				nroRecordXPaginaItem.hide();
			}
		}
		
		if(hasMultiselectButtons()) {
			multiselectButton.show();
		} else {
			multiselectButton.hide();			
		}		

		if(nroPaginaItem != null) {
			nroPaginaItem.setValue("");
			nroPaginaItem.hide();
		}
		if(nroPaginaFirstButton != null) {
			nroPaginaFirstButton.hide();
		}
		if(nroPaginaPrevButton != null) {
			nroPaginaPrevButton.hide();
		}
		if(nroPaginaNextButton != null) {
			nroPaginaNextButton.hide();
		}
		if(nroPaginaLastButton != null) {
			nroPaginaLastButton.hide();
		}
		if(nroPaginaToolStripSeparator != null) {
			nroPaginaToolStripSeparator.hide();
		}

	}
	
	public boolean hasMultiselectButtons() {
		int n = 0;
		for(MultiToolStripButton button : getMultiselectButtons()) {
			if(((MultiToolStripButton) button).toShow()) {
				n++;
			}			
		}
		return (n > 0);
	}
	
	@Override
	public String getPrefKeyPrefix() {
		
		if(tree != null && tree.getCurrentRecord() != null) {
			return getPrefKeyPrefix(tree.getCurrentRecord());
		}		
		return super.getPrefKeyPrefix();		
	}
		
	public String getPrefKeyPrefix(Record record) {
		
		String idNode = record != null ? record.getAttributeAsString("idNode") : null;
		return super.getPrefKeyPrefix() + (idNode != null && !"".equals(idNode) ? idNode + "." : "");					
	}
	
	public VLayout getRightLayout() {
		return rightLayout;
	}
	
	protected void caricaPreferenceTreeVisibility() {
		final GWTRestDataSource showTreeDS = UserInterfaceFactory.getPreferenceDataSource();
		showTreeDS.addParam("prefKey", getPrefKeyPrefixForPortlet() + "showTree");
		AdvancedCriteria criteria = new AdvancedCriteria();
		criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		showTreeDS.fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					Record record = data[0];
					String preferenceValue = record.getAttribute("value");
					boolean showTree = true;
					int treeSectionWidth = getTreeSectionDefaultWidth();
					if (preferenceValue == null || preferenceValue.indexOf(":") == -1) {
						showTree = new Boolean(record.getAttribute("value"));
					}else {
						showTree = new Boolean(record.getAttribute("value").split(":")[0]);
						treeSectionWidth = new Integer(record.getAttribute("value").split(":")[1]);
					}
					setTreeSectionWidth(treeSectionWidth);
					if(showTree) {
						showTree();
					} else {
						hideTree();
					}
				} else {
					showTree();
				}     
			}         
		});	
	}
	
	@Override
	public void showTree() {
		leftLayout.show();
	}
	
	@Override
	public void hideTree() {
		leftLayout.hide();
	}
	
	@Override
	public boolean isTreeVisible() {
		return leftLayout.isVisible();
	}

	@Override
	public int getTreeSectionDefaultWidth() {
		return CUSTOM_SIMPLE_TREE_SETION_DEFAULT_WIDTH;
	}
	
	@Override
	public int getTreeSectionWidth() {
		return leftLayout.getWidth();
	}

	@Override
	public void setTreeSectionWidth(int width) {
		leftLayout.setWidth(width);		
	}
	
	@Override
	protected void onDestroy() {		
		if(treedatasource != null) {
			treedatasource.destroy();
		}		
		super.onDestroy();
	}
		
}
