/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class NuovoAttiRichiestiItem extends ReplicableItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		NuovaAttiRichiestiCanvas lAttiRichiestiCanvas = new NuovaAttiRichiestiCanvas();
		return lAttiRichiestiCanvas;
	}
	
	public boolean isElencoAttiRichiestiInCanvasEditable(){
		return true;
	}
	
	public boolean isEstremiAttiRichiestiInCanvasEditable(){
		return true;
	}
	
	public boolean isStatoAttiInCanvasToShow(){
		return true;
	}
	
	public boolean isStatoAttiInCanvasEditable(){
		return true;
	}
	
	public boolean isNoteCittadellaInCanvasToEnable(){
		return true;
	}
	
	public boolean isNoteUffRichiedenteInCanvasToEnable(){
		return true;
	}
	
	public boolean isTaskArchivioInCanvas(){
		return true;
	}

	
	public Record getIndirizzoImpostato() {
		return null;
	}
	
	public String getIdNodoRicerca() {
		return "/";
	}
	
	@Override
	protected ImgButton[] createAddButtons() {

		ImgButton[] addButtons = new ImgButton[2];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("[SKIN]actions/add.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();   	
			}   
		});
		
		addButtons[1] = new ImgButton();   
		addButtons[1].setSrc("lookup/archiviomulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt("Cerca in archivio pratiche");
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				// Leggo il nodo di partenza dal dettaglio
				String idNodoRicerca = getIdNodoRicerca();
				AttiRichiestiMultiLookupArchivio lookupMultiplaArchivio = new AttiRichiestiMultiLookupArchivio(null, idNodoRicerca);				
				lookupMultiplaArchivio.show(); 	
			}   
		});
		
		addButtons[2] = new ImgButton();   
		addButtons[2].setSrc("lookup/indirizzomulti.png");   
		addButtons[2].setShowDown(false);   
		addButtons[2].setShowRollOver(false);      
		addButtons[2].setSize(16); 
		addButtons[2].setPrompt("Cerca fascicoli per indirizzo");
		addButtons[2].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				// Leggo il nodo di partenza dal dettaglio
				Record filterValue = new Record();
				Record indirizzoImpostato = getIndirizzoImpostato();
				String viaImpostata = null;
				if (indirizzoImpostato != null && indirizzoImpostato.getAttributeAsString("indirizzo") != null) {
					viaImpostata = indirizzoImpostato.getAttributeAsString("indirizzo");
				}
				filterValue.setAttribute("flgUdFolder", "FS");
				filterValue.setAttribute("indirizzo", viaImpostata);
				AttiRichiestiMultiLookupArchivio lookupMultiplaArchivio = new AttiRichiestiMultiLookupArchivio(filterValue, null);				
				lookupMultiplaArchivio.show(); 	
			}   
		});
		
		return addButtons;		
	}
	
	public String getFinalitaAttiRichiestiLookupArchivio() {
		return "SEL_X_ACCESSO_ATTI";
	}
	
	public class AttiRichiestiMultiLookupArchivio extends LookupArchivioPopup {

		private List<NuovaAttiRichiestiCanvas> multiLookupList = new ArrayList<NuovaAttiRichiestiCanvas>(); 
		
		public AttiRichiestiMultiLookupArchivio(Record filterValues, String idRootNode) {
			super(filterValues, idRootNode, false, true);
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaAttiRichiestiLookupArchivio();
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona da archivio";
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			NuovaAttiRichiestiCanvas lastCanvas = (NuovaAttiRichiestiCanvas) getLastCanvas();
			if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordArchivio(record, false);
				multiLookupList.add(lastCanvas);
			} else {
				NuovaAttiRichiestiCanvas nuovaAttiRichiestiCanvas = (NuovaAttiRichiestiCanvas) onClickNewButton();
				nuovaAttiRichiestiCanvas.setFormValuesFromRecordArchivio(record, false);
				multiLookupList.add(nuovaAttiRichiestiCanvas);
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			for(NuovaAttiRichiestiCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idFolder").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

	}
	
}
