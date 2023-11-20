/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

public class AttachmentReplicableItem extends ReplicableItem {

	FileUploadItemWithFirmaAndMimeType uploadButton;
	ReplicableCanvas actual;

	// Indica se deve essere utilizzata la visualizzazione avanzata (grafica aggiornata e utilizzo della scrollbar in caso di un numero alto di allegati)
	// L'opzione è configurabile per mantenere la retrocompatibilità del componente dopo l'introduzione della grafica avanzata
	protected boolean usaGraficaAvantata = false;
	// Altezza massima in pixel che può raggiungere la lista file, senza che sia mostrato lo scrolling
	protected int maxAltezzaListaFile = 250;
	
	private boolean aggiuntaFile = false;

	public AttachmentReplicableItem() {
		this(false, 250);
	}

	public AttachmentReplicableItem(boolean usaGraficaAvantata, int maxAltezzaListaFile) {
		this.usaGraficaAvantata = usaGraficaAvantata;
		this.maxAltezzaListaFile = maxAltezzaListaFile;
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		return new AttachmentCanvas(this);
	}

	@Override
	protected void disegna(Object value) {
		super.disegna(value);
	}

	/**
	 * Aggiunge al VLayout un HLayout con un bottone di remove ed una istanza del {@link ReplicableCanvas}
	 */
	@Override
	public ReplicableCanvas onClickNewButton() {
		ReplicableCanvas lReplicableCanvas = super.onClickNewButton();
		return lReplicableCanvas;
	}

	@Override
	protected VLayout creaVLayout() {
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight(20);
		if (usaGraficaAvantata) {
			// Se ho abilitato la scrollbar la setto nell'overflow del layout
			lVLayout.setOverflow(Overflow.AUTO);
			// Visualizzazione cornice
			lVLayout.setStyleName(it.eng.utility.Styles.textItemReadonly);
		}
		return lVLayout;
	}

	@Override
	public void storeValue(Object arg0) {
		super.storeValue(arg0);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				resizeElencoReplicableItem();
			}
		});
	}

	@Override
	public void storeValue(Record arg0) {
		super.storeValue(arg0);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				resizeElencoReplicableItem();
			}
		});
	}

	@Override
	public void storeValue(RecordList values) {
		super.storeValue(values);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				resizeElencoReplicableItem();
			}
		});
	}

	private void resizeElencoReplicableItem() {
		// Controllo se ho abilitato lo scrolling della lista
		if (usaGraficaAvantata) {
			// Calcolo l'altezza del layout
			int heigth = getAltezzaComponente();
			if (heigth > maxAltezzaListaFile) {
				// Se il layout eccede le dimensioni massime lo blocco, a quel punto interviene lo scrolling
				getVLayout().setHeight(maxAltezzaListaFile);
			} else {
				// Altrimenti lo ridimensiono
				// Setto una altezza fittizzia piccola, in modo che intervenga lo scrolling
				getVLayout().setHeight(5);
				// Leggo l'altezza che avrebbe il layout senza scrolling
				// E' capitato che all'inserimento della prima riga l'altezza letta con getScrollHeight fosse erroneamente 0 (forse perchè il layout non era
				// ancora aggiornato), nel caso si verificasse ciò per ovviare uso un'altezza minima (26 è l'altezza di una riga)
				int heightWithoutScrolling = Math.max(26, getVLayout().getScrollHeight());
				// Aggiungo lo spessore di eventuali bordi (considero l'altezza del bordo superiore ed inferiore)
				int edgeSize = ((getVLayout().getShowEdges() != null) && (Boolean.valueOf(getVLayout().getShowEdges()))) ? getVLayout().getEdgeSize() * 2 : 0;
				getVLayout().setHeight(heightWithoutScrolling + edgeSize + 2);
			}
		}
	}

	@Override
	public void redraw() {
		super.redraw();
		resizeElencoReplicableItem();
	}

	private int getAltezzaComponente() {
		int altezzaTot = 0;
		// Calcolo l'altezza del layout che contiene la lista.
		if (showNewButton) {
			// Se mostro il punsante per aggiungere un nuovo elemento, devo considerare anche l'altezza di tale bottone
			// Ottengo il numero di elementi
			int numElem = getVLayout().getMembers().length;
			// Il pulsante di add è alla fine
			int altezzaAddButton = getVLayout().getMember(numElem - 1).getScrollHeight();
			altezzaTot += altezzaAddButton;
		}
		// Aggiungo l'altezza di tutte le sizioni ripetibili (il pulsante di add non viene considerato)
		altezzaTot += getTotalHeight();
		return altezzaTot;
	}

	@Override
	public int getTotalHeight() {
		int totalHeight = 0;
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
							totalHeight += lReplicableCanvas.getCanvasHeight();
						}
					}
				} else {
					totalHeight += lVLayoutMember.getVisibleHeight();
				}
			}
		}
		return totalHeight;
	}

	private Record detailRecord;

	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

	public void downloadFile(ServiceCallback<Record> lDsCallback) {

	}

	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = new HLayout();
		addButtonsLayout.setMembersMargin(3);
		DynamicForm addButtonsForm = new DynamicForm();
		uploadButton = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				actual = onClickNewButton();
				actual.getForm()[0].setValue("fileNameAttach", displayFileName);
				actual.getForm()[0].setValue("uriAttach", uri);
				((AttachmentCanvas) actual).changedEventAfterUpload(displayFileName, uri);
				uploadButton.redrawPrettyFileUploadInput();
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				actual.getForm()[0].setValue("infoFileAttach", info);
			}
		});

		uploadButton.setCanFocus(false);
		uploadButton.setTabIndex(-1);
		addButtonsForm.setItems(uploadButton);
		addButtonsForm.setMargin(0);
		addButtonsLayout.addMember(addButtonsForm);
		return addButtonsLayout;
	}

	public void setFileAsAllegatoFromWindow(Record record) {

		AttachmentCanvas canvas = (AttachmentCanvas) onClickNewButton();
		Record recordToSave = new Record();
		recordToSave.setAttribute("uriAttach", record.getAttributeAsString("uriAttach"));
		recordToSave.setAttribute("fileNameAttach", record.getAttributeAsString("fileNameAttach"));
		recordToSave.setAttribute("infoFileAttach", record.getAttributeAsRecord("infoFile"));
		canvas.editRecord(recordToSave);

		if ((canvas.getForm() != null) && (canvas.getForm().length >= 1) && (canvas.getForm()[0] != null)) {
			canvas.getForm()[0].setValue("uriAttach", record.getAttributeAsString("uriAttach"));
			canvas.getForm()[0].setValue("fileNameAttach", record.getAttributeAsString("fileNameAttach"));
			canvas.getForm()[0].setValue("infoFileAttach", record.getAttributeAsRecord("infoFile"));
			canvas.changedEventAfterUpload(record.getAttributeAsString("fileNameAttach"), record.getAttributeAsString("uriAttach"));
			if (this.getForm() != null) {
				this.getForm().redraw();
			}
		}
		Layout.addMessage(new MessageBean("File spostato con successo", "", MessageType.INFO));

	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setAggiuntaFile(false);
		super.setCanEdit(canEdit);
	}

	public void setAggiuntaFileMode() {
		setCanEdit(true);
		setAggiuntaFile(true);
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ImgButton || lHLayoutMember instanceof DynamicForm) {
							if (i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo mostro
								if (getShowNewButton()) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							} else if (lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void updateMode() {
		if(isAggiuntaFile()) {
			setAggiuntaFileMode();
		} 		
	}

	public boolean showStampaFileButton() {
		return false;
	}
	
	public void setAggiuntaFile(boolean aggiuntaFile) {
		this.aggiuntaFile = aggiuntaFile;
	}

	public boolean isAggiuntaFile() {
		return aggiuntaFile;
	}

}
