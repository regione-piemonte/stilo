/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class SubProfiliDetail extends CustomDetail {

	protected TabSet tabSet;
	
	// TAB Dati principali
	protected Tab tabDatiPrincipali;
	protected VLayout layoutTabDatiPrincipali;
	protected DynamicForm formDatiPrincipali;
	
	// TAB Funzionalità del sistema
	protected Tab tabFunzionalitaSistema;
	protected VLayout layoutTabFunzionalitaSistema;
	protected DynamicForm formTabFunzionalitaSistema;
	protected ListaFunzionalitaSistemaItem listaFunzionalitaSistemaItem;
	
	// TAB Tipo di processo/procedimento
	protected Tab tabTipoProcessoProcedimento;
	protected VLayout layoutTabTipoProcessoProcedimento;
	protected DynamicForm formTabTipoProcessoProcedimento;
	protected ListaTipoProcessoProcedimentoItem listaTipoProcessoProcedimentoItem;
	
	// TAB Tipo di documento
	protected Tab tabTipoDocumento;
	protected VLayout layoutTabTipoDocumento;
	protected DynamicForm formTabTipoDocumento;
	protected ListaTipoDocumentoItem listaTipoDocumentoItem;
	
	// TAB Tipo di Folder
	protected Tab tabTipoFolder;
	protected VLayout layoutTabTipoFolder;
	protected DynamicForm formTabTipoFolder;
	protected ListaTipoFolderItem listaTipoFolderItem;
	
	// TAB Classificazione
	protected Tab tabClassificazione;
	protected VLayout layoutTabClassificazione;
	protected DynamicForm formTabClassificazione;
	protected ListaClassificazioneItem listaClassificazioneItem;
	
	// TAB Tipo di registrazione (protocollo o repertorio o altro); 
	protected Tab tabTipoRegistrazione;
	protected VLayout layoutTabTipoRegistrazione;
	protected DynamicForm formTabTipoRegistrazione;	
	protected ListaTipoRegistrazioneItem listaTipoRegistrazioneItem;
	
	private TextAreaItem noteGruppoPrivItem;
	private TextItem nomeGruppoPrivItem;
	private HiddenItem idGruppoPrivItem;
	
	public SubProfiliDetail(String nomeEntita) {
		
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		createTabSet();

		setMembers(tabSet);
	}

	protected void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		//************************************************************
		// Tab Dati principali
		//************************************************************
		tabDatiPrincipali = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabDatiPrincipali_title() + "</b>");
		tabDatiPrincipali.setAttribute("tabID", "HEADER");
		tabDatiPrincipali.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabDatiPrincipali_title());
		layoutTabDatiPrincipali = createLayoutTab(getLayoutTabDatiPrincipali(), lVLayoutSpacer);
		tabDatiPrincipali.setPane(layoutTabDatiPrincipali);
		tabSet.addTab(tabDatiPrincipali);
		
		//************************************************************
		// Tab Funzionalità del sistema
		//************************************************************
		tabFunzionalitaSistema = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabFunzionalitaSistema_title() + "</b>");
		tabFunzionalitaSistema.setAttribute("tabID", "HEADER");
		tabFunzionalitaSistema.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabFunzionalitaSistema_title());
		layoutTabFunzionalitaSistema = createLayoutTab(getLayoutTabFunzionalitaSistema(), lVLayoutSpacer);
		tabFunzionalitaSistema.setPane(layoutTabFunzionalitaSistema);
		tabSet.addTab(tabFunzionalitaSistema);
		
		//************************************************************
		// Tab Tipo di processo/procedimento
		//************************************************************
		tabTipoProcessoProcedimento = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabTipoProcessoProcedimento_title() + "</b>");
		tabTipoProcessoProcedimento.setAttribute("tabID", "HEADER");
		tabTipoProcessoProcedimento.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabTipoProcessoProcedimento_title());
		layoutTabTipoProcessoProcedimento = createLayoutTab(getLayoutTabTipoProcessoProcedimento(), lVLayoutSpacer);
		tabTipoProcessoProcedimento.setPane(layoutTabTipoProcessoProcedimento);
		tabSet.addTab(tabTipoProcessoProcedimento);
		
		//************************************************************
		// Tab Tipo di documento
		//************************************************************
		tabTipoDocumento = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabTipoDocumento_title() + "</b>");
		tabTipoDocumento.setAttribute("tabID", "HEADER");
		tabTipoDocumento.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabTipoDocumento_title());
		layoutTabTipoDocumento = createLayoutTab(getLayoutTabTipoDocumento(), lVLayoutSpacer);
		tabTipoDocumento.setPane(layoutTabTipoDocumento);
		tabSet.addTab(tabTipoDocumento);
		
		//************************************************************
		// Tab Tipo di folder
		//************************************************************
		tabTipoFolder = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabTipoFolder_title() + "</b>");
		tabTipoFolder.setAttribute("tabID", "HEADER");
		tabTipoFolder.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabTipoFolder_title());
		layoutTabTipoFolder = createLayoutTab(getLayoutTabTipoFolder(), lVLayoutSpacer);
		tabTipoFolder.setPane(layoutTabTipoFolder);
		tabSet.addTab(tabTipoFolder);
		
		//************************************************************
		// Tab Classificazione
		//************************************************************
		tabClassificazione = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabClassificazione_title() + "</b>");
		tabClassificazione.setAttribute("tabID", "HEADER");
		tabClassificazione.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabClassificazione_title());
		layoutTabClassificazione = createLayoutTab(getLayoutTabClassificazione(), lVLayoutSpacer);
		tabClassificazione.setPane(layoutTabClassificazione);
		tabSet.addTab(tabClassificazione);

		//************************************************************
		// Tab Tipo di registrazione
		//************************************************************
		tabTipoRegistrazione = new Tab("<b>" + I18NUtil.getMessages().sub_profili_detail_tabTipoRegistrazione_title() + "</b>");
		tabTipoRegistrazione.setAttribute("tabID", "HEADER");
		tabTipoRegistrazione.setPrompt(I18NUtil.getMessages().sub_profili_detail_tabTipoRegistrazione_title());
		layoutTabTipoRegistrazione = createLayoutTab(getLayoutTabTipoRegistrazione(), lVLayoutSpacer);
		tabTipoRegistrazione.setPane(layoutTabTipoRegistrazione);
		tabSet.addTab(tabTipoRegistrazione);

	}
	
	public VLayout getLayoutTabTipoRegistrazione() {
		   
	    formTabTipoRegistrazione = new DynamicForm();
	    formTabTipoRegistrazione.setValuesManager(vm);
	    formTabTipoRegistrazione.setWidth("100%");
	    formTabTipoRegistrazione.setHeight("100%");
	    formTabTipoRegistrazione.setPadding(5);
	    formTabTipoRegistrazione.setWrapItemTitles(false);
	    formTabTipoRegistrazione.setNumCols(12);
	    formTabTipoRegistrazione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
	    formTabTipoRegistrazione.setValuesManager(vm);
		
	    listaTipoRegistrazioneItem = new ListaTipoRegistrazioneItem("listaTipoRegistrazione") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaTipoRegistrazioneItem.setStartRow(true);
		listaTipoRegistrazioneItem.setShowTitle(false);
		listaTipoRegistrazioneItem.setHeight(245);
		formTabTipoRegistrazione.setItems(listaTipoRegistrazioneItem);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formTabTipoRegistrazione);
				
		return lVLayout;
    }
	
	public VLayout getLayoutTabClassificazione() {
		   
	    formTabClassificazione = new DynamicForm();
	    formTabClassificazione.setValuesManager(vm);
	    formTabClassificazione.setWidth("100%");
	    formTabClassificazione.setHeight("100%");
	    formTabClassificazione.setPadding(5);
	    formTabClassificazione.setWrapItemTitles(false);
	    formTabClassificazione.setNumCols(12);
	    formTabClassificazione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
	    formTabClassificazione.setValuesManager(vm);
		
	    listaClassificazioneItem = new ListaClassificazioneItem("listaClassificazione") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaClassificazioneItem.setStartRow(true);
		listaClassificazioneItem.setShowTitle(false);
		listaClassificazioneItem.setHeight(245);
		formTabClassificazione.setItems(listaClassificazioneItem);
	    
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formTabClassificazione);
				
		return lVLayout;
    }
	
	public VLayout getLayoutTabTipoFolder() {
		   
	    formTabTipoFolder = new DynamicForm();
	    formTabTipoFolder.setValuesManager(vm);
	    formTabTipoFolder.setWidth("100%");
	    formTabTipoFolder.setHeight("100%");
	    formTabTipoFolder.setPadding(5);
	    formTabTipoFolder.setWrapItemTitles(false);
	    formTabTipoFolder.setNumCols(12);
	    formTabTipoFolder.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
	    formTabTipoFolder.setValuesManager(vm);
	    
	    listaTipoFolderItem = new ListaTipoFolderItem("listaTipoFolder") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaTipoFolderItem.setStartRow(true);
		listaTipoFolderItem.setShowTitle(false);
		listaTipoFolderItem.setHeight(245);
		formTabTipoFolder.setItems(listaTipoFolderItem);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formTabTipoFolder);
				
		return lVLayout;
    }
	
	public VLayout getLayoutTabTipoDocumento() {
		   
	    formTabTipoDocumento = new DynamicForm();
	    formTabTipoDocumento.setValuesManager(vm);
	    formTabTipoDocumento.setWidth("100%");
	    formTabTipoDocumento.setHeight("100%");
	    formTabTipoDocumento.setPadding(5);
	    formTabTipoDocumento.setWrapItemTitles(false);
	    formTabTipoDocumento.setNumCols(12);
	    formTabTipoDocumento.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
	    formTabTipoDocumento.setValuesManager(vm);
		
	    listaTipoDocumentoItem = new ListaTipoDocumentoItem("listaTipoDocumento") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaTipoDocumentoItem.setStartRow(true);
		listaTipoDocumentoItem.setShowTitle(false);
		listaTipoDocumentoItem.setHeight(245);
		formTabTipoDocumento.setItems(listaTipoDocumentoItem);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formTabTipoDocumento);
				
		return lVLayout;
    }
	
	public VLayout getLayoutTabTipoProcessoProcedimento() {
		   
	    formTabTipoProcessoProcedimento = new DynamicForm();
	    formTabTipoProcessoProcedimento.setValuesManager(vm);
	    formTabTipoProcessoProcedimento.setWidth("100%");
	    formTabTipoProcessoProcedimento.setHeight("100%");
	    formTabTipoProcessoProcedimento.setPadding(5);
	    formTabTipoProcessoProcedimento.setWrapItemTitles(false);
	    formTabTipoProcessoProcedimento.setNumCols(12);
	    formTabTipoProcessoProcedimento.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
	    formTabTipoProcessoProcedimento.setValuesManager(vm);
		
	    listaTipoProcessoProcedimentoItem = new ListaTipoProcessoProcedimentoItem("listaTipoProcessoProcedimento") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaTipoProcessoProcedimentoItem.setStartRow(true);
		listaTipoProcessoProcedimentoItem.setShowTitle(false);
		listaTipoProcessoProcedimentoItem.setHeight(245);
		formTabTipoProcessoProcedimento.setItems(listaTipoProcessoProcedimentoItem);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formTabTipoProcessoProcedimento);
				
		return lVLayout;
   }
   
   public VLayout getLayoutTabFunzionalitaSistema() {
	   
	    formTabFunzionalitaSistema = new DynamicForm();
	    formTabFunzionalitaSistema.setValuesManager(vm);
	    formTabFunzionalitaSistema.setWidth("100%");
	    formTabFunzionalitaSistema.setHeight("100%");
	    formTabFunzionalitaSistema.setPadding(5);
	    formTabFunzionalitaSistema.setWrapItemTitles(false);
	    formTabFunzionalitaSistema.setNumCols(12);
	    formTabFunzionalitaSistema.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
	    formTabFunzionalitaSistema.setValuesManager(vm);
		
	    listaFunzionalitaSistemaItem = new ListaFunzionalitaSistemaItem("listaFunzionalitaSistema") {
			
			@Override
			public boolean isShowEditButtons() {
				return true;
			}
			
			@Override
			public boolean isShowNewButton() {
				return false;
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return false;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return true;
			}
		};
		
		listaFunzionalitaSistemaItem.setStartRow(true);
		listaFunzionalitaSistemaItem.setShowTitle(false);
		listaFunzionalitaSistemaItem.setHeight(245);
		formTabFunzionalitaSistema.setItems(listaFunzionalitaSistemaItem);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formTabFunzionalitaSistema);
				
		return lVLayout;
   }
   
   public VLayout getLayoutTabDatiPrincipali() {
		
	    formDatiPrincipali = new DynamicForm();		
		formDatiPrincipali.setValuesManager(vm);
		formDatiPrincipali.setWidth("100%");  
		formDatiPrincipali.setHeight("5");  
		formDatiPrincipali.setPadding(5);
		formDatiPrincipali.setWrapItemTitles(false);
		formDatiPrincipali.setNumCols(2);	
		
		// Hidden
		idGruppoPrivItem = new HiddenItem("idGruppoPriv");
		
		// Nome
		nomeGruppoPrivItem = new TextItem("nomeGruppoPriv", I18NUtil.getMessages().sub_profili_detail_nomeGruppoPrivItem_title());
		nomeGruppoPrivItem.setRequired(true);
		nomeGruppoPrivItem.setWidth(650);
		nomeGruppoPrivItem.setStartRow(true);
		
		// Note
		noteGruppoPrivItem = new TextAreaItem("noteGruppoPriv", I18NUtil.getMessages().sub_profili_detail_noteGruppoPrivItem_title());
		noteGruppoPrivItem.setStartRow(true);
		noteGruppoPrivItem.setLength(4000);
		noteGruppoPrivItem.setHeight(60);
		noteGruppoPrivItem.setWidth(650);
		
		formDatiPrincipali.setItems(
				                    idGruppoPrivItem,
				                    nomeGruppoPrivItem,
				                    noteGruppoPrivItem
				     );

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);			

		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);		

		lVLayout.addMember(formDatiPrincipali);
		
		return lVLayout;
	}
 
   protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
   
   @Override
	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		setCanEdit(canEdit, formDatiPrincipali);
		setCanEdit(canEdit, formTabFunzionalitaSistema);
		setCanEdit(canEdit, formTabTipoProcessoProcedimento);
		setCanEdit(canEdit, formTabTipoDocumento);
		setCanEdit(canEdit, formTabTipoFolder);
		setCanEdit(canEdit, formTabClassificazione);
		setCanEdit(canEdit, formTabTipoRegistrazione);
		
		listaFunzionalitaSistemaItem.setMultiselect(canEdit);
		listaTipoProcessoProcedimentoItem.setMultiselect(canEdit);
		listaTipoDocumentoItem.setMultiselect(canEdit);
		listaTipoFolderItem.setMultiselect(canEdit);
		listaClassificazioneItem.setMultiselect(canEdit);
		listaTipoRegistrazioneItem.setMultiselect(canEdit);
	}
}