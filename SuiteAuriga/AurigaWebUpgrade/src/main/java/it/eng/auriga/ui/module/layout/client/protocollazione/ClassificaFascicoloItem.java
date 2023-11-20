/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.titolario.LookupTitolarioPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ClassificaFascicoloItem extends ReplicableItem{
	
	private boolean fascicoloObbligatorio = false;	
	private boolean fascicolazioneMassiva = false;	
	
	private boolean aggiunta = false;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ClassificaFascicoloCanvas lClassificaFascioloCanvas = new ClassificaFascicoloCanvas(this);
		return lClassificaFascioloCanvas;
	}

	@Override
	protected ImgButton[] createAddButtons() {
		ImgButton[] addButtons = new ImgButton[3];
		
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
		addButtons[1].setSrc("lookup/titolariomulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupTitolarioButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				ClassificaFascioloMultiLookupTitolario lookupTitolarioPopup = new ClassificaFascioloMultiLookupTitolario(null);				
				lookupTitolarioPopup.show(); 	
			}   
		});
		
		addButtons[2] = new ImgButton();   
		addButtons[2].setSrc("lookup/archiviomulti.png");   
		addButtons[2].setShowDown(false);   
		addButtons[2].setShowRollOver(false);      
		addButtons[2].setSize(16); 
		addButtons[2].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupArchivioButton_prompt());
		addButtons[2].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				ClassificaFascicoloMultiLookupArchivio lookupArchivioPopup = new ClassificaFascicoloMultiLookupArchivio(null);				
				lookupArchivioPopup.show();
			}   
		});
		
		return addButtons;		
	}	

	public class ClassificaFascioloMultiLookupTitolario extends LookupTitolarioPopup {

		private List<ClassificaFascicoloCanvas> multiLookupList = new ArrayList<ClassificaFascicoloCanvas>(); 
		
		public ClassificaFascioloMultiLookupTitolario(Record record) {
			super(record, false);			
		}

		public String getFinalita() {
			return "ASSEGNAZIONE";
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			ClassificaFascicoloCanvas lastCanvas = (ClassificaFascicoloCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordTitolario(record);
				multiLookupList.add(lastCanvas);
			} else {
				ClassificaFascicoloCanvas canvas = (ClassificaFascicoloCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordTitolario(record);
				multiLookupList.add(canvas);
			}						
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(ClassificaFascicoloCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("classifiche").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
	
	}
	
	public void setAggiunta(boolean aggiunta) {
		this.aggiunta = aggiunta;
	}

	public boolean isAggiunta() {
		return aggiunta;
	}
	
	public class ClassificaFascicoloMultiLookupArchivio extends LookupArchivioPopup {

		private List<ClassificaFascicoloCanvas> multiLookupList = new ArrayList<ClassificaFascicoloCanvas>(); 
		
		public ClassificaFascicoloMultiLookupArchivio(Record record) {
			super(record, null, false);			
		}

		@Override
		public String getFinalita() {			
			return "FASCICOLA_UD";
		}
		
		@Override
		public void manageLookupBack(Record record) {
								
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			ClassificaFascicoloCanvas lastCanvas = (ClassificaFascicoloCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordArchivio(record);
				multiLookupList.add(lastCanvas);
			} else {
				ClassificaFascicoloCanvas canvas = (ClassificaFascicoloCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordArchivio(record);
				multiLookupList.add(canvas);
			}	
		}	
		
		@Override
		public void manageMultiLookupUndo(Record record) {	
			for(ClassificaFascicoloCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idFolderFascicolo").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
	}	
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setAggiunta(false);
		super.setCanEdit(canEdit);
	}
	
	public void setAggiuntaMode() {
		setCanEdit(true);
		setAggiunta(true);
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);									
				if(lVLayoutMember instanceof HLayout) {
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof ImgButton) {
							if(i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo mostro
								if(getShowNewButton()) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							} else if(lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton)lHLayoutMember).setAlwaysDisabled(true);																	
							}																			
						} else if(lHLayoutMember instanceof ReplicableCanvas) {
							ClassificaFascicoloCanvas lClassificaFascicoloCanvas = (ClassificaFascicoloCanvas)lHLayoutMember;								
							lClassificaFascicoloCanvas.setCanEdit(false);
						}
					}	
				}			
			}
		}		
	}
	
	@Override
	public void updateMode() {
		if(isAggiunta()) {
			setAggiuntaMode();
		} 		
	}
	
	public boolean isFascicoloObbligatorio() {
		return fascicoloObbligatorio;
	}

	public void setFascicoloObbligatorio(boolean fascicoloObbligatorio) {
		this.fascicoloObbligatorio = fascicoloObbligatorio;
	}
	
	public boolean isFascicolazioneMassiva() {
		return fascicolazioneMassiva;
	}

	public void setFascicolazioneMassiva(boolean fascicolazioneMassiva) {
		this.fascicolazioneMassiva = fascicolazioneMassiva;
	}
		
}
