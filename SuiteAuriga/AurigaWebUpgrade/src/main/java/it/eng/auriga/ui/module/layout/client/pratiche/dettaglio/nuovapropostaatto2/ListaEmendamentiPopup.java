/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class ListaEmendamentiPopup extends ModalWindow {
	
	protected ListaEmendamentiPopup _window;
	protected NuovaPropostaAtto2CompletaDetail _detail;
	protected boolean _listaEmendamentiBloccoRiordinoAut;
	protected String listaIdUdEmendamentiOrdineOriginale;
	
	protected ValuesManager vm;
	protected DynamicForm listaEmendamentiForm;
		
	protected ListaEmendamentiItem listaEmendamentiItem;
	protected TabSet _tabSet;
	
	public ListaEmendamentiPopup(String title, String nomePopup, TabSet tabSet, NuovaPropostaAtto2CompletaDetail detail, boolean isSubList, boolean listaEmendamentiBloccoRiordinoAut) {

		super("lista_emendamenti", true);

		_window = this;
		_tabSet = tabSet;
		_detail = detail;
		_listaEmendamentiBloccoRiordinoAut = listaEmendamentiBloccoRiordinoAut;
		
		vm = new ValuesManager();
	
		setTitle(title != null && !"".equals(title)? title : "Lista emendamenti");

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		listaEmendamentiForm = new DynamicForm();
		listaEmendamentiForm.setValuesManager(vm);
		listaEmendamentiForm.setKeepInParentRect(true);
		listaEmendamentiForm.setWidth100();
		listaEmendamentiForm.setHeight100();
		listaEmendamentiForm.setNumCols(7);
		listaEmendamentiForm.setColWidths(10, 10, 10, 10, 10, "*", "*");
		listaEmendamentiForm.setCellPadding(2);
		listaEmendamentiForm.setWrapItemTitles(false);
		
		listaEmendamentiItem = new ListaEmendamentiItem("listaEmendamenti", _window, _tabSet, detail, isSubList, false, listaEmendamentiBloccoRiordinoAut);
		
		listaEmendamentiForm.setFields(listaEmendamentiItem);	
		
		Button approvaButton = new Button("Approva");
		approvaButton.setIcon("buttons/save.png");
		approvaButton.setIconSize(16);
		approvaButton.setAutoFit(false);
		approvaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
				String idUd = _detail.getIdUd();
				List<ListGridRecord> recordSelezionati = listaEmendamentiItem.getRecordSelezionati();
				Record recordToPass = new Record();
				recordToPass.setAttribute("idUd", idUd);
				recordToPass.setAttribute("listaEmendamenti", recordSelezionati);
				final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				
				lNuovaPropostaAtto2CompletaDataSource.executecustom("approvaEmendamenti", recordToPass, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Layout.hideWaitPopup();
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							_detail.recuperaListaEmendamenti(new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									RecordList listaEmendamenti = object.getAttributeAsRecordList("listaEmendamenti");
									boolean listaEmendamentiBloccoRiordinoAut = object.getAttributeAsBoolean("listaEmendamentiBloccoRiordinoAut").booleanValue();
									_detail.listaEmendamentiItem.setValue(listaEmendamenti);
									_detail.listaEmendamentiBloccoRiordinoAutItem.setValue(listaEmendamentiBloccoRiordinoAut);
									_listaEmendamentiBloccoRiordinoAut = listaEmendamentiBloccoRiordinoAut;
									Record recordToPass = new Record();
									recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);
									_window.initContent(recordToPass);
									listaEmendamentiItem.setBloccoRiordinoAut(listaEmendamentiBloccoRiordinoAut);
								}

							});
						}
					}
				});
			}
		});
		
		Button confermaButton = new Button("Salva");
		confermaButton.setIcon("buttons/save.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (_listaEmendamentiBloccoRiordinoAut) {
					manageOnClickConferma();
				} else {
					// Verifico se l'ordinamento è cambiato
					RecordList listaEmendamenti = listaEmendamentiForm.getValueAsRecordList("listaEmendamenti");
					String listaIdUdEmendamentiOrdineAttuale = getListaIdUdEmendamenti(listaEmendamenti);
					boolean ordineInvariato = listaIdUdEmendamentiOrdineAttuale.equalsIgnoreCase(listaIdUdEmendamentiOrdineOriginale);
					String messaggio = "";
					if (ordineInvariato) {
						messaggio = "Salvando verrà disattivato il riordino automatico degli emendamenti sulla base della posizione. Confermi di voler procedere?";
					} else {
						messaggio = "Salvando l’ordinamento manuale verrà disattivato il riordino automatico degli emendamenti sulla base della posizione. Confermi di voler procedere?";
					}
					SC.ask(messaggio, new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								manageOnClickConferma();
							}
							
						}
					});
				}
			}

			private void manageOnClickConferma() {
				Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
				// Verifico se ho già caricato gli emendamenti
				String idUd = _detail.getIdUd();
				RecordList listaEmendamenti = listaEmendamentiItem.getValueAsRecordList();
				Record recordToPass = new Record();
				recordToPass.setAttribute("idUd", idUd);
				recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);

				final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				
				lNuovaPropostaAtto2CompletaDataSource.executecustom("salvaListaEmendamenti", recordToPass, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Layout.hideWaitPopup();
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							_detail.recuperaListaEmendamenti(new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									RecordList listaEmendamenti = object.getAttributeAsRecordList("listaEmendamenti");
									boolean listaEmendamentiBloccoRiordinoAut = object.getAttributeAsBoolean("listaEmendamentiBloccoRiordinoAut").booleanValue();
									_detail.listaEmendamentiItem.setValue(listaEmendamenti);
									_detail.listaEmendamentiBloccoRiordinoAutItem.setValue(listaEmendamentiBloccoRiordinoAut);
									_listaEmendamentiBloccoRiordinoAut = listaEmendamentiBloccoRiordinoAut;
									Record recordToPass = new Record();
									recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);
									_window.initContent(recordToPass);
									listaEmendamentiItem.setBloccoRiordinoAut(listaEmendamentiBloccoRiordinoAut);
								}

							});
						}
					}
				});
			}
		});

		Button annullaButton = new Button("Chiudi");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				_window.markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		// _buttons.addMember(approvaButton);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
	
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);

		layout.addMember(listaEmendamentiForm);		

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);

		setIcon("archivio/assegna.png");
	}
	
	public void initContent(Record listaEmendamenti) {
		listaEmendamentiItem.clearMappe();
		listaEmendamentiForm.setValue("listaEmendamenti", listaEmendamenti.getAttributeAsRecordArray("listaEmendamenti"));
		listaEmendamentiItem.sort("nroEmendamento");
		listaIdUdEmendamentiOrdineOriginale = getListaIdUdEmendamenti(listaEmendamenti.getAttributeAsRecordList("listaEmendamenti"));
	}
	
	// Restituisce la lista degli id emendamenti mantenedo l'ordine
	private static String getListaIdUdEmendamenti(RecordList listaEmendamenti) {
		String result = "";
		if (listaEmendamenti != null) {
			for (int i = 0; i < listaEmendamenti.getLength(); i++) {
				String idUdEmendamento =  listaEmendamenti.get(i).getAttribute("idUd");
				if (idUdEmendamento != null && !"".equalsIgnoreCase(idUdEmendamento)){
					result += idUdEmendamento + ";";
					if (listaEmendamenti.get(i).getAttributeAsRecordList("listaSubEmendamenti") != null) {
						RecordList listaSubEmendamenti = listaEmendamenti.get(i).getAttributeAsRecordList("listaSubEmendamenti");
						for (int j = 0; j < listaSubEmendamenti.getLength(); j++) {
							String idUdSubEmendamento = listaSubEmendamenti.get(j).getAttribute("idUd");
							if (idUdSubEmendamento != null && !"".equalsIgnoreCase(idUdSubEmendamento)){
								result +=  idUdEmendamento + "-" + idUdSubEmendamento + ";";
							}
						}
					}
				}
			}
		}
		
		return result;
		
	}
		
		public abstract void onClickOkButton(Record record, DSCallback callback);

}