/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.listaFunzionalita.ListaFunzionalitaWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DettaglioProfiloDetail extends CustomDetail{
	
	protected DynamicForm form;
	protected DynamicForm formAbilSpeciali;

	protected HiddenItem idProfilo;
	protected TextItem nomeProfilo;
	protected CheckboxItem livMaxRiservatezza;

	protected SelectItem accessoDocIndipACL;
	protected SelectItem accessoDocIndipUserAbil;
	protected SelectItem accessoWorkspaceIndipACL;
	protected SelectItem accessoFolderIndipACL;
	protected SelectItem accessoFolderIndipUserAbil;
	protected CheckboxItem flgVisibTuttiRiservati;

	protected ImgButtonItem buttonListaFunzioni;	

	public DettaglioProfiloDetail(String nomeEntita) {
		super(nomeEntita);
		
		/*
		 * FORM DEFAULT
		 */

		form = new DynamicForm();		
		form.setValuesManager(vm);
		form.setWidth("100%");  
		form.setHeight("5");  
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(7);	
		
		idProfilo = new HiddenItem("idProfilo");
		
		nomeProfilo = new TextItem("nomeProfilo", "Nome profilo");
		nomeProfilo.setWidth(150);
		nomeProfilo.setStartRow(true);

		// (se LivMaxRiservatezza = 1 nell’xml è spuntato, se 0 no)
		livMaxRiservatezza = new CheckboxItem("livMaxRiservatezza", "Abilitazione ai riservati");
		livMaxRiservatezza.setVisible(true);
		livMaxRiservatezza.setValue(true);
		livMaxRiservatezza.setColSpan(1);
		livMaxRiservatezza.setWidth("*");		
		livMaxRiservatezza.setStartRow(false);
		
		buttonListaFunzioni = new ImgButtonItem("buttonListaFunzioni", "buttons/altriDati.png", "Abilitazioni a funzioni");
		buttonListaFunzioni.setTitle("Abilitazioni a funzioni");
		buttonListaFunzioni.setShowTitle(true);
		buttonListaFunzioni.setAlwaysEnabled(true);
		buttonListaFunzioni.setColSpan(1);
		buttonListaFunzioni.setStartRow(true);		
		buttonListaFunzioni.addIconClickHandler(new IconClickHandler() {			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				visualizzaListaFunzioni((String)idProfilo.getValue(), (String)nomeProfilo.getValue());
			}
		});
		buttonListaFunzioni.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return idProfilo.getValue() != null && !"".equals((String)idProfilo.getValue());
			}
		});
	
		form.setItems(
				idProfilo,
				nomeProfilo,
				livMaxRiservatezza,
				buttonListaFunzioni
		);
		
		/*
		 *  FORM ABILITAZIONI SPECIALI
		 */

		formAbilSpeciali = new DynamicForm();
		formAbilSpeciali.setValuesManager(vm);
		formAbilSpeciali.setWidth("100%");  
		formAbilSpeciali.setHeight("5");  
		formAbilSpeciali.setPadding(5);
		formAbilSpeciali.setWrapItemTitles(false);
		formAbilSpeciali.setNumCols(12);

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("V", "sola visualizzazione");
		valueMap.put("FC", "visualizzazione e modifica");		
		
		//  Accesso ai documenti indipendentemente dai loro permessi
		accessoDocIndipACL = new SelectItem("accessoDocIndipACL", "Accesso ai documenti indipendentemente dai loro permessi");
		accessoDocIndipACL.setValueMap(valueMap);
		accessoDocIndipACL.setAllowEmptyValue(true);
		accessoDocIndipACL.setColSpan(1);
		accessoDocIndipACL.setStartRow(true);
		
		// Accesso ai documenti indipendentemente dalle loro caratteristiche		
		accessoDocIndipUserAbil = new SelectItem("accessoDocIndipUserAbil", "Accesso ai documenti indipendentemente dalle loro caratteristiche");
		accessoDocIndipUserAbil.setValueMap(valueMap);
		accessoDocIndipUserAbil.setAllowEmptyValue(true);
		accessoDocIndipUserAbil.setColSpan(1);
		accessoDocIndipUserAbil.setStartRow(true);
		
		// Accesso ai workspace indipendentemente dai loro permessi		
		accessoWorkspaceIndipACL = new SelectItem("accessoWorkspaceIndipACL", "Accesso ai workspace indipendentemente dai loro permessi");
		accessoWorkspaceIndipACL.setValueMap(valueMap);
		accessoWorkspaceIndipACL.setAllowEmptyValue(true);
		accessoWorkspaceIndipACL.setColSpan(1);
		accessoWorkspaceIndipACL.setStartRow(true);
		
		//  Accesso a fascicoli e cartelle indipendentemente dai loro permessi
		accessoFolderIndipACL = new SelectItem("accessoFolderIndipACL", "Accesso a fascicoli e cartelle indipendentemente dai loro permessi");
		accessoFolderIndipACL.setValueMap(valueMap);
		accessoFolderIndipACL.setAllowEmptyValue(true);
		accessoFolderIndipACL.setColSpan(1);
		accessoFolderIndipACL.setStartRow(true);
		
		//  Accesso a fascicoli e cartelle indipendentemente dalle loro caratteristiche
		accessoFolderIndipUserAbil = new SelectItem("accessoFolderIndipUserAbil", "Accesso a fascicoli e cartelle indipendentemente dalle loro caratteristiche");
		accessoFolderIndipUserAbil.setValueMap(valueMap);
		accessoFolderIndipUserAbil.setAllowEmptyValue(true);
		accessoFolderIndipUserAbil.setColSpan(1);
		accessoFolderIndipUserAbil.setStartRow(true);
		
		//  Accesso a tutti i documenti e fascicoli/cartelle riservati
		flgVisibTuttiRiservati = new CheckboxItem("flgVisibTuttiRiservati", "Accesso a tutti i documenti e fascicoli/cartelle riservati");
		flgVisibTuttiRiservati.setValue(false);
		flgVisibTuttiRiservati.setColSpan(1);
		flgVisibTuttiRiservati.setWidth("*");
		flgVisibTuttiRiservati.setStartRow(true);
						
		formAbilSpeciali.setItems(
				accessoDocIndipACL,
				accessoDocIndipUserAbil,
				accessoWorkspaceIndipACL,
				accessoFolderIndipACL,
				accessoFolderIndipUserAbil,
				flgVisibTuttiRiservati
		);
		
		DetailSection detailSectionAbilSpeciali = new DetailSection("Abilitazioni speciali", true, true, false, formAbilSpeciali);
		
		/*
		 * LAYOUT
		 */
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);			
		
		VLayout lVLayoutSpacer = new VLayout();  
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);		
		
		lVLayout.addMember(form);			
		lVLayout.addMember(detailSectionAbilSpeciali);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);			
		
	}
	
	public void setIdProfilo(String value) {
		idProfilo.setValue(value);
	}
	
	public String getIdProfilo() {
		return (String) idProfilo.getValue();
	}	
	
	public void visualizzaListaFunzioni(String idProfilo, String nomeProfilo) {
		ListaFunzionalitaWindow funzionalitaPopup = new ListaFunzionalitaWindow("Abilitazioni a funzioni per il profilo", idProfilo, nomeProfilo, true, Layout.isPrivilegioAttivo("SIC/PR;M"), "PR");		
		funzionalitaPopup.show();
	}	
		
	@Override  
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);		
	}	
}
