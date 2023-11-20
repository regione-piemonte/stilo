/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.IstruttoreProcItem;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.layout.client.common.DetailSection;

public class TaskDettFascicoloDetail extends TaskDettFascicoloGenDetail implements TaskFlussoInterface {
	
	protected TaskDettFascicoloDetail instance;
	
	protected DetailSection detailSectionCompetenza;
	protected DynamicForm competenzaForm;
	protected AssegnazioneItem ufficioCompetenteItem;
	protected IstruttoreProcItem istruttoreProcItem;

	protected DetailSection detailSectionDocumentiIniziali;
	protected DynamicForm documentiInizialiForm;
	protected AllegatiItem documentiInizialiItem;
		
	public TaskDettFascicoloDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idFolder, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, lRecordEvento, dettaglioPraticaLayout);
		
		instance = this;
		
	}
	
	@Override
	public VLayout getLayoutDatiPrincipali() {
		
		VLayout layoutDatiDocumento = new VLayout(5);

		createDetailSectionDatiIdentificativi();
		layoutDatiDocumento.addMember(detailSectionDatiIdentificativi);

		createDetailSectionCompetenza();
		layoutDatiDocumento.addMember(detailSectionCompetenza);

		createDetailSectionDocumentiIniziali();
		layoutDatiDocumento.addMember(detailSectionDocumentiIniziali);

		createDetailSectionDocumentiIstruttoria();
		layoutDatiDocumento.addMember(detailSectionDocumentiIstruttoria);

		return layoutDatiDocumento;	
	}
	
	public void createDetailSectionCompetenza() {
		
		competenzaForm = new DynamicForm();
		competenzaForm.setValuesManager(vm);
		competenzaForm.setWidth("100%");
		competenzaForm.setHeight("5");
		competenzaForm.setPadding(5);
		competenzaForm.setNumCols(3);
		competenzaForm.setColWidths("1", "*", "*");
		competenzaForm.setTabSet(tabSet);
		competenzaForm.setTabID("HEADER");

		ufficioCompetenteItem = new AssegnazioneItem();
		ufficioCompetenteItem.setName("listaAssegnazioni");
		ufficioCompetenteItem.setTitle("Ufficio");
		ufficioCompetenteItem.setFlgUdFolder("F");
		ufficioCompetenteItem.setTipoAssegnatari("UO");
		ufficioCompetenteItem.setNotReplicable(true);
		ufficioCompetenteItem.setFlgSenzaLD(true);

		istruttoreProcItem = new IstruttoreProcItem(null, null);
		istruttoreProcItem.setName("listaIstruttoriProc");
		istruttoreProcItem.setTitle("Assegnatario");
		istruttoreProcItem.setNotReplicable(true);
		
		competenzaForm.setFields(ufficioCompetenteItem, istruttoreProcItem);
	
		detailSectionCompetenza = new DetailSection("Competenza", true, true, false, competenzaForm);
	}
	
	public void createDetailSectionDocumentiIniziali() {
		
		documentiInizialiForm = new DynamicForm();
		documentiInizialiForm.setValuesManager(vm);
		documentiInizialiForm.setWidth("100%");
		documentiInizialiForm.setPadding(5);
		documentiInizialiForm.setTabSet(tabSet);
		documentiInizialiForm.setTabID("HEADER");

		documentiInizialiItem = new AllegatiItem() {

			@Override
			public boolean showNumeroAllegato() {
				return false;
			}

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showNomeFileAllegato() {
				return false;
			}

			@Override
			public String getTitleDescrizioneFileAllegato() {
				return "Oggetto";
			}

			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return new Integer("500");
			}

			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return true;
			}

			@Override
			public boolean showVisualizzaFileUdButton() {
				return true;
			}
			
			@Override
			public boolean isDocumentiInizialiIstanza() {
				return true;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}
			
		};
		documentiInizialiItem.setShowFlgParteDispositivo(false);
		documentiInizialiItem.setName("listaDocumentiIniziali");
		documentiInizialiItem.setShowTitle(false);

		documentiInizialiForm.setFields(documentiInizialiItem);
		
		detailSectionDocumentiIniziali = new DetailSection("Istanze", true, true, false, documentiInizialiForm);		
	}
	
	@Override
	public String getDetailSectionDocumentiIstruttoriaTitle() {
		return "Documenti istruttoria";
	}	
	
	@Override
	public boolean isDocIstruttoriaCedAutotutela() {
		RecordList listaDocumentiIniziali = (documentiInizialiForm) != null ? documentiInizialiForm.getValueAsRecordList("listaDocumentiIniziali") : null;
		if (listaDocumentiIniziali != null && listaDocumentiIniziali.getLength() > 0) {
			return ("CED".equalsIgnoreCase(listaDocumentiIniziali.get(0).getAttribute("descTipoFileAllegato")) || "AUTOTUTELA".equalsIgnoreCase(listaDocumentiIniziali.get(0).getAttribute("descTipoFileAllegato")));
		}
		return false;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		setCanEdit(false, documentiInizialiForm);
	}
	
	@Override
	public void editRecord(Record record) {		
		super.editRecord(record);
		RecordList listaDocumentiIniziali = record.getAttributeAsRecordList("listaDocumentiIniziali");
		if (listaDocumentiIniziali == null || listaDocumentiIniziali.getLength() == 0) {
			detailSectionDocumentiIniziali.setVisible(false);
		} else {
			detailSectionDocumentiIniziali.setVisible(true);
		}
	}
	
}