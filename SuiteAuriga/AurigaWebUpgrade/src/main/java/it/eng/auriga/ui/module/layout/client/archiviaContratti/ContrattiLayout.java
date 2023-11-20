/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;

public class ContrattiLayout extends CustomLayout{
	
	protected DetailToolStripButton invioInConservazioneButton;
	

	public ContrattiLayout(String nomeEntita){
		this(nomeEntita, null, null);
	}

	public ContrattiLayout(String nomeEntita, String finalita, Boolean showOnlyDetail){
		super(nomeEntita,
				new GWTRestDataSource("ArchivioContrattiDataSource", "idUd", FieldType.TEXT),
				new ConfigurableFilter(nomeEntita),
				new ContrattiList(nomeEntita),
				new ContrattiDetail(nomeEntita),
				finalita,
				showOnlyDetail
				);
		
		multiselectButton.hide();
		if (!isAbilToIns()) {
			newButton.hide();
		}
		
	}
	
	public static boolean isAbilToIns() {
		return true;
	}
	
	public static boolean isAbilToMod() {
		return true;
	}
	
	public static boolean isAbilToDel() {
		return false;
	}
	
	
	public static boolean isRecordAbilToMod(boolean flgDiSistema , boolean invioInConservazione) {
		
		return !flgDiSistema && invioInConservazione && isAbilToMod();
	}
	
	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		
		return flgValido && !flgDiSistema && isAbilToDel();
	}
	
	@Override
	public String getTipoEstremiRecord(Record record) {
		
		return record.getAttribute("nroContratto") + " del " + (DateUtil.format((Date)record.getAttributeAsDate("dataStipulaContratto")));
	}
	
	@Override
	public String getNewDetailTitle() {
		return "Nuovo contratto";
	}
	
	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio contratto " + getTipoEstremiRecord(record);
	}
	
	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica contratto " + getTipoEstremiRecord(record);
	}
	
	@Override
	public void newMode() {
		super.newMode();

		saveButton.setTitle("Archivia contratto");
		
		invioInConservazioneButton.hide();
	}
	
	@Override
	public void viewMode() {
		
		super.viewMode();	
		saveButton.setTitle("Salva");
		deleteButton.hide();
		altreOpButton.hide();
		
		Record record = new Record(detail.getValuesManager().getValues());
		
		if(record.getAttributeAsBoolean("modificaDati")) {
			editButton.show();
		} else {
			editButton.hide();
		}
		
		if(record.getAttributeAsBoolean("invioInConservazione")) {
			invioInConservazioneButton.show();
		} else {
			invioInConservazioneButton.hide();
		}
		
		showDetail();
		
	}	
	
	public void onSaveButtonClick() {
		
		((ContrattiDetail)detail).archiviaContratto();
	}
	
	@Override
	public List<ToolStripButton> getDetailButtons() {
		
		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();
		
		invioInConservazioneButton = new DetailToolStripButton("Invio in conservazione", "anagrafiche/soggetti/flgEmailPecPeo/PEC.png");
		invioInConservazioneButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {

				if (detail.validate())
				
					SC.ask("Procedendo il contratto non sarà più modificabile e verrà avviato il processo di conservazione. Confermi l’operazione ?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {	
								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioContrattiDataSource");		
								
								Record record = new Record(detail.getValuesManager().getValues());
								
								lGwtRestDataSource.executecustom("invioInConservazione", record, new DSCallback() {								
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										    			
										if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
											
											final Record savedRecord = response.getData()[0];
											
											try {
												list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {							
													@Override
													public void execute(DSResponse response, Object rawData, DSRequest request) {
														viewMode();		
														Layout.hideWaitPopup();
														Layout.addMessage(new MessageBean("Acquisita con successo la richiesta di avvio del processo di conservazione", "", MessageType.INFO));									
													}
												});	
											} catch(Exception e) {
												Layout.hideWaitPopup();
											}
											
										} 																															
									}
								});			
								
							}
							
						}
					});
				
			}
		});

		detailButtons.add(saveButton);
		detailButtons.add(editButton);
		detailButtons.add(reloadDetailButton);
		detailButtons.add(undoButton);
//		detailButtons.add(deleteButton);
		detailButtons.add(invioInConservazioneButton);
		return detailButtons;
	}

}
