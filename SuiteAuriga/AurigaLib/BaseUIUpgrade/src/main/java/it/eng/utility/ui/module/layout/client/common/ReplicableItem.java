/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas.ReplicableCanvasForm;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem.RemoveButton;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

/**
 * CanvasItem che contiene al proprio interno un VLayout con un bottone di add, la cui pressione permette di aggiungere infinite volte il
 * {@link ReplicableCanvas} definito al suo interno.
 * 
 * Essendo un formItem, la classe gestisce sempre una RecordList come value
 * 
 * @author Rametta
 *
 */

public abstract class ReplicableItem extends CanvasItem implements HasChangeCanEditHandlers {

	protected ReplicableItem instance;
	protected RecordList oldDataValue;
	protected Boolean showNewButton = true;
	protected Boolean showRemoveButton = true;
	protected Boolean notReplicable = false;
	protected Boolean editing = true;
	protected Boolean ordinabile = false;
	protected Integer maxLength = null;
	protected Boolean showDuplicaRigaButton = false;

	/**
	 * Serve per istanziare la classe tramite GWT
	 * 
	 * @param jsObj
	 */
	public ReplicableItem(JavaScriptObject jsObj) {
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste. return ReplicableItem;
	 * 
	 * @param jsObj
	 * @return
	 */
	public ReplicableItem buildObject(JavaScriptObject jsObj) {
		ReplicableItem lItem = getOrCreateRef(jsObj);
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * 
	 * @param jsObj
	 * @return
	 */
	public static ReplicableItem getOrCreateRef(JavaScriptObject jsObj) {
		if (jsObj == null)
			return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if (obj != null) {
			obj.setJsObj(jsObj);
			return (ReplicableItem) obj;
		} else {
			return null;
		}
	}

	/**
	 * Crea un ReplicableItem. In fase di init, lo disegna e ne setta lo showHandler per gestire il setValue
	 * 
	 */
	public ReplicableItem() {
		instance = this;
		setCanFocus(true);
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {

			public void onInit(FormItem item) {
				// Inizializza il componente
				buildObject(item.getJsObj()).disegna(item.getValue());
				setCanFocus(true);
				// Setto lo showValue (gestisce il setValue)
				addShowValueHandler(new ShowValueHandler() {

					@Override
					public void onShowValue(ShowValueEvent event) {
						// Recupero il valore - è sempre un RecordList
						RecordList lRecordList = event.getDataValueAsRecordList();
						// Disegna e gestisce il valore da caricare
						if(oldDataValue != null && lRecordList != null && oldDataValue.equals(lRecordList)) {
							// non faccio nulla perchè sono uguali (altrimenti mi mette le x per cancellare le righe la prima volta che apro il dettaglio in modalità view)
						} else {
							drawAndSetValue(lRecordList);
						}
					}
				});
			}
		});
	}
	
	@Override
	public void setCanvas(Canvas canvas) {		
		super.setCanvas(canvas);
	}
	
	public boolean hasValue() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			if(lReplicableCanvas.hasValue(getCanvasDefaultRecord())) {
				return true;
			}
		}
		return false;
	}
	
	public FormItem getItemByName(String fieldName) {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);			
			FormItem item = lReplicableCanvas.getItemByName(fieldName);
			if(item != null) {
				return item;
			}
		}
		return null;
	}
	
	public void clearErrors() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {						
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			if(lReplicableCanvas.getValuesManager() != null) {
				lReplicableCanvas.getValuesManager().clearErrors(true);
			}
			for (DynamicForm form : lReplicableCanvas.getForm()) {
				form.clearErrors(true);
				for (FormItem item : form.getFields()) {
					if(item != null && (item instanceof ReplicableItem)) {
						ReplicableItem lReplicableItem = (ReplicableItem) item;
						lReplicableItem.clearErrors();
					}
				}
			}
		}
	}

	@Override
	public Boolean validate() {
		clearErrors();
		if(skipValidation()) {
			return true; 
		}
		DetailSection detailSection = getForm() != null ? getForm().getDetailSection() : null;		
		if (detailSection != null && detailSection.showFirstCanvasWhenEmptyAfterOpen()) {
			if(getEditing() != null && getEditing()/* && !isObbligatorio()*/) {	
				removeEmptyCanvas();
			}
		}
		boolean hasValue = hasValue();
		boolean skipEmptyCanvasValidation = false;
		if(!isObbligatorio() && !hasValue) {
			// il ReplicableItem non è obbligatorio e contiene solo righe vuote, quindi elimino le righe) e salto la validazione  	
		 	return true;			
		} else if(hasValue) {
			skipEmptyCanvasValidation = true;
		}
		boolean valid = true;
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {						
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			if(!skipEmptyCanvasValidation || lReplicableCanvas.hasValue(getCanvasDefaultRecord())) {
				for (DynamicForm form : lReplicableCanvas.getForm()) {
					valid = form.validate() && valid;
				}
				valid = lReplicableCanvas.validate() && valid;
			}			
		}
		return valid;
	}
	
	public Boolean valuesAreValid() {
		// Tipicamente restituisce i valuesAreValid di tutti i form interni
		if(skipValidation()) {
			return true; 
		}
		DetailSection detailSection = getForm() != null ? getForm().getDetailSection() : null;		
		if (getEditing() != null && getEditing() && !isObbligatorio() && detailSection != null && detailSection.showFirstCanvasWhenEmptyAfterOpen()) {
			if(!hasValue()) {
				// il ReplicableItem non è obbligatorio e contiene solo righe vuote, quindi salto la validazione 	
				return true;
			}
		}
		boolean valid = true;
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			for (DynamicForm form : lReplicableCanvas.getForm()) {
				valid = form.valuesAreValid(false) && valid;
			}
			valid = lReplicableCanvas.valuesAreValid() && valid;
		}
		return valid;
	}
	
	public void removeEmptyCanvas() {
		final RecordList lRecordList = new RecordList();
		List<Boolean> lListEditing = new ArrayList<Boolean>();
		ReplicableCanvas[] allCanvas = getAllCanvas();
		if(allCanvas != null && allCanvas.length > 0) {
			for (ReplicableCanvas lReplicableCanvas : allCanvas) {
				if(lReplicableCanvas.hasValue(getCanvasDefaultRecord())) {
					lRecordList.add(lReplicableCanvas.getFormValuesAsRecord());
					lListEditing.add(lReplicableCanvas.getEditing());
				}
			}
			if (lRecordList.getLength() == 0) {
				if (isObbligatorio() || getNotReplicable() /* || hasDefaultValue()*/) {
					lRecordList.add(allCanvas[0].getFormValuesAsRecord());
					lListEditing.add(allCanvas[0].getEditing());				
				}
			}
			if(allCanvas.length > lRecordList.getLength()) {
				drawAndSetValue(lRecordList, lListEditing);
				updateMode();
			}
		}
	}
	
	public void updateMode() {
		
	}

	public Map getMapErrors() {
		Map errors = null;
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			if (lReplicableCanvas.getErrors() != null && lReplicableCanvas.getErrors().size() > 0) {
				if (errors == null) {
					errors = new HashMap();
				}
				errors.putAll(lReplicableCanvas.getErrors());
			}
		}
		return errors;
	}

	public void resetCanvasChanged() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			lReplicableCanvas.setChanged(false);
		}
	}

	public boolean hasDefaultValue() {
		return false;
	}

	public Record getDefaultRecord() {
		return new Record();
	}

	/**
	 * Disegna un VLayout con all'interno un bottone per l'add. Viene settato il VLayout come canvas per l'item. Aggancio al click del bottone la gestione
	 * dell'add.
	 * 
	 * @param value
	 */
	protected void disegna(Object value) {
		VLayout lVLayout = creaVLayout();
		count = 0;
		if (isObbligatorio() || getNotReplicable() || hasDefaultValue()) {
			// Recupero il canvas da replicare
			final ReplicableCanvas lReplicableCanvas = getCanvasToReply();
			lReplicableCanvas.bringToFront();
			lReplicableCanvas.setItem(this);
			lReplicableCanvas.setEditing(editing);
			lReplicableCanvas.setCanEdit(true);
			lReplicableCanvas.setCanFocus(true);
			// incremento il contatore
			count = 1;
			lReplicableCanvas.setCounter(count);
			// Creo l'HLayout che contiene delete e il mio canvas
			final HLayout lHLayout = new HLayout();
			// lHLayout.setAlign(VerticalAlignment.CENTER);
			// lHLayout.setLayoutAlign(VerticalAlignment.CENTER);
			lHLayout.setDefaultLayoutAlign(VerticalAlignment.CENTER);
			// Creo il bottone di remove
			RemoveButton removeButton = null;
			if (getNotReplicable() && getShowRemoveButton()) {
				removeButton = new RemoveButton();
				removeButton.setSrc("blank.png");
				removeButton.setShowDown(false);
				removeButton.setShowRollOver(false);
				removeButton.setMargin(2);
				removeButton.setSize(20);
				removeButton.setCursor(Cursor.ARROW);
			} else if (getNotReplicable() && !getShowRemoveButton()) {
				removeButton = new RemoveButton();
				removeButton.setShowDown(false);
				removeButton.setShowRollOver(false);
				removeButton.setMargin(0);
				removeButton.setSize(0);
			}else{
				removeButton = createRemoveButton(lVLayout, lHLayout);
			}
			removeButton.setDisabled(true);
			// Aggiungo il bottone
			lHLayout.addMember(removeButton);
			if (ordinabile) {
				ImgButton upButton = createUpButton(lVLayout, lHLayout);
				upButton.setDisabled(true);
				lHLayout.addMember(upButton);
				lReplicableCanvas.setUpButton(upButton);

				ImgButton downButton = createDownButton(lVLayout, lHLayout);
				downButton.setDisabled(true);
				lHLayout.addMember(downButton);
				lReplicableCanvas.setDownButton(downButton);
			}
			if (showDuplicaRigaButton) {
				ImgButton duplicaRigaButton = createDuplicaRigaButton(lVLayout, lHLayout);
				lHLayout.addMember(duplicaRigaButton);
				lReplicableCanvas.setDuplicaRigaButton(duplicaRigaButton);
			}
			// Aggiungo il replicableCanvas
			lReplicableCanvas.setVLayout(lVLayout);
			lReplicableCanvas.setHLayout(lHLayout);
			lReplicableCanvas.setRemoveButton(removeButton);
			// gestisto il change handler per il canvas
			setUpChangeHandler(lReplicableCanvas);
			lHLayout.addMember(lReplicableCanvas);
			// Aggiungo al Vlayout l'hlayout appena creato
			lVLayout.addMember(lHLayout);
			// Creo un recordList
			RecordList lRecordList = new RecordList();
			if (getCanvasDefaultRecord() != null) {
				lReplicableCanvas.editRecord(getCanvasDefaultRecord());
			} 
			/*
			else {
				lReplicableCanvas.editRecord(new Record()); //TODO potrebbe togliere dei valori settati nel canvas con setValue invece che con setDefaultValue
			}
			*/
			Record lRecord = lReplicableCanvas.getFormValuesAsRecord();
			lRecordList.add(lRecord);
			// Memorizzo il valore nel canvas
			storeValue(lRecordList);
		}
		if (showNewButton) {
			// Creo il bottone
			HLayout addButtonsLayout = createAddButtonsLayout();
			lVLayout.addMember(addButtonsLayout);
		}
		// Setto il vLayout come Canvas
		setCanvas(lVLayout);
		// Setto lo shouldSaveValue per gestire lo store dei valori
		setShouldSaveValue(true);
	}

	/**
	 * Crea il layout nella quale vengono contenute le canvas delle varie sezioni ripeture. Questo metodo può essere sovrascritto per aggiungere degli ulteriori
	 * attributi a tale layout (come ad esempio l'overflow o altro)
	 * 
	 * @return Il layout nella quale verranno contenute le canvas delle varie sezioni ripetibili
	 */
	protected VLayout creaVLayout() {
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight(20);
		lVLayout.setAlign(VerticalAlignment.CENTER);
		return lVLayout;
	}

	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = new HLayout();
		addButtonsLayout.setMembersMargin(3);
		for (ImgButton button : createAddButtons()) {
	 		if (UserInterfaceFactory.isAttivaAccessibilita()){
	//			button.setTabIndex(null);
				button.setCanFocus(true);
	 		} else {
				button.setCanFocus(false);
				button.setTabIndex(-1);
			}
			addButtonsLayout.addMember(button);
		}
		return addButtonsLayout;
	}

	protected VLayout getVLayout() {
		return (VLayout) getCanvas();
	}

	public int getTotalMembers() {
		VLayout lVLayout = getVLayout();
		if (lVLayout != null) {
			return showNewButton ? (lVLayout.getMembers().length - 1) : lVLayout.getMembers().length;
		}
		return 0;
	}

	protected ReplicableCanvas getReplicableCanvas(HLayout lHLayout) {
		int index = 1;
		if (ordinabile) {
			index += 2;
		}
		if (showDuplicaRigaButton) {
			index += 1;
		}
		return (ReplicableCanvas) lHLayout.getMember(index);
	}

	public ReplicableCanvas getFirstCanvas() {
		VLayout lVLayout = getVLayout();
		if (lVLayout != null && getTotalMembers() > 0) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(0);
			return getReplicableCanvas(lHLayout);
		}
		return null;
	}

	public ReplicableCanvas getLastCanvas() {
		VLayout lVLayout = getVLayout();
		if (lVLayout != null && getTotalMembers() > 0) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(getTotalMembers() - 1);
			return getReplicableCanvas(lHLayout);
		}
		return null;
	}

	public ReplicableCanvas[] getAllCanvas() {
		VLayout lVLayout = getVLayout();
		if (lVLayout != null && getTotalMembers() > 0) {
			ReplicableCanvas[] allCanvas = new ReplicableCanvas[getTotalMembers()];
			for (int i = 0; i < getTotalMembers(); i++) {
				HLayout lHLayout = (HLayout) lVLayout.getMember(i);
				allCanvas[i] = getReplicableCanvas(lHLayout);
			}
			return allCanvas;
		}
		;
		return new ReplicableCanvas[0];
	}

	public RecordList getValueAsRecordList() {
		RecordList value = new RecordList();
		for (ReplicableCanvas canvas : getAllCanvas()) {
			value.add(canvas.getFormValuesAsRecord());
		}
		return value;
	}

	protected HLayout getAddButtonsLayout() {
		VLayout lVLayout = getVLayout();
		if (showNewButton && lVLayout != null && lVLayout.getMembers().length > 0) {
			return (HLayout) lVLayout.getMember(lVLayout.getMembers().length - 1);
		}
		return null;
	}

	public void hideShowNewButton() {
		VLayout lVLayout = getVLayout();
		if (!showNewButton)
			return;
		else {
			if (lVLayout != null && lVLayout.getMembers().length > 0) {
				HLayout lHLayout = (HLayout) lVLayout.getMember(lVLayout.getMembers().length - 1);
				lHLayout.hide();
			}
		}

	}

	/**
	 * Creo il bottone per l'add e gestisco l'evento di click
	 * 
	 * @return
	 */
	protected ImgButton[] createAddButtons() {
		ImgButton[] addButtons = new ImgButton[1];
		addButtons[0] = new ImgButton();
		addButtons[0].setSrc("[SKIN]actions/add.png");
		addButtons[0].setShowDown(false);
		addButtons[0].setShowRollOver(false);
		addButtons[0].setSize(16);
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//onClickNewButton();
				onClickHandlerNewButton();
			}
		});
		return addButtons;
	}
	
	
	

	/**
	 * Funzione che mi restituisce il {@link ReplicableCanvas} da replicare
	 * 
	 * @return
	 */
	public abstract ReplicableCanvas getCanvasToReply();

	// Contatore interno dei valori
	protected int count = 0;

	/**
	 * Funzione che riceve in ingresso il valore da settare, ovvero una {@link RecordList} e per ogni {@link Record} aggiunge un {@link ReplicableCanvas}
	 * popolato secondo il {@link Record} che sta analizzando.
	 * 
	 * @param lRecordList
	 */
	
	public void drawAndSetValue(RecordList lRecordList) {
		drawAndSetValue(lRecordList, null);
	}
	
	public void drawAndSetValue(RecordList lRecordList, List<Boolean> lListEditing) {
		// Resetto il contatore
		count = 0;
		oldDataValue = lRecordList;
		// Recupero il VLayout
		final VLayout lVLayout = getVLayout();
		// Calcolo il numero totale degli oggetti interni
		if (getTotalMembers() > 0) {
			// Ciclo gli oggetti
			for (int i = (getTotalMembers() - 1); i >= 0; i--) {
				// li rimuovo tutti tranne il bottone di add in ultima posizione
				removeVLayoutMemberAtIndex(lVLayout, i);						
			}
		}
		// Refresh del layout
		lVLayout.markForRedraw();
		// Recupero l'istanza del canvas cui è legato il Layout, ovvero il nostro ReplicableItem
		ReplicableItem lReplicableItem = (ReplicableItem) lVLayout.getCanvasItem();
		// Scorro la lista da settare come valore
		boolean toResetCanvasChanged = false;
		if (lRecordList == null || lRecordList.getLength() == 0) {
			if (isObbligatorio() || getNotReplicable() /*|| hasDefaultValue()*/) {
				lRecordList = new RecordList();
				if (hasDefaultValue()) {
					lRecordList.add(getDefaultRecord());
					if (lListEditing != null) {
						lListEditing.add(null);
					}
				} else {
					lRecordList.add(new Record());
					if (lListEditing != null) {
						lListEditing.add(null);
					}
				}
				toResetCanvasChanged = true;
			}
		}
		if (lRecordList != null) {
			for (int k = 0; k < lRecordList.getLength(); k++) {
				// incremento il contatore
				count++;
				// Recupero il record
				Record lRecord = lRecordList.get(k);
				if(lRecord.getAttributeAsBoolean("isHiddenCanvas")) {
					continue;
				}
				// Recupero il canvas da replicare
				final ReplicableCanvas lReplicableCanvas = lReplicableItem.getCanvasToReply();
				lReplicableCanvas.bringToFront();
				lReplicableCanvas.setItem(this);
				if(lListEditing != null) {
					if(lListEditing.get(k) != null) {
						lReplicableCanvas.setEditing(lListEditing.get(k));
						if (lListEditing.get(k) != null) {
							lReplicableCanvas.setCanEdit(lListEditing.get(k));
						}
					}
				} else {
					lReplicableCanvas.setEditing(editing);
					if (editing != null) {
						lReplicableCanvas.setCanEdit(editing);
					}
				}
				lReplicableCanvas.setCanFocus(true);
				lReplicableCanvas.setCounter(count);
				// Creo l'HLayout che contiene delete e il mio canvas
				final HLayout lHLayout = new HLayout();
				// lHLayout.setAlign(VerticalAlignment.CENTER);
				// lHLayout.setLayoutAlign(VerticalAlignment.CENTER);
				lHLayout.setDefaultLayoutAlign(VerticalAlignment.CENTER);
				// Creo il bottone di remove
				RemoveButton removeButton = null;
				if (getNotReplicable() && getShowRemoveButton()) {
					removeButton = new RemoveButton();
					removeButton.setSrc("blank.png");
					removeButton.setShowDown(false);
					removeButton.setShowRollOver(false);
					removeButton.setMargin(2);
					removeButton.setSize(20);
					removeButton.setCursor(Cursor.ARROW);
				} else if (getNotReplicable() && !getShowRemoveButton()) {
					removeButton = new RemoveButton();
					removeButton.setShowDown(false);
					removeButton.setShowRollOver(false);
					removeButton.setMargin(0);
					removeButton.setSize(0);
				} else {
					removeButton = createRemoveButton(lVLayout, lHLayout);
				}
				// Aggiungo il bottone
				lHLayout.addMember(removeButton);
				if (ordinabile) {
					ImgButton upButton = createUpButton(lVLayout, lHLayout);
					lHLayout.addMember(upButton);
					lReplicableCanvas.setUpButton(upButton);

					ImgButton downButton = createDownButton(lVLayout, lHLayout);
					lHLayout.addMember(downButton);
					lReplicableCanvas.setDownButton(downButton);
				}
				if (showDuplicaRigaButton) {
					ImgButton duplicaRigaButton = createDuplicaRigaButton(lVLayout, lHLayout);
					lHLayout.addMember(duplicaRigaButton);
					lReplicableCanvas.setDuplicaRigaButton(duplicaRigaButton);
				}
				// Aggiungo il replicableCanvas
				lReplicableCanvas.setVLayout(lVLayout);
				lReplicableCanvas.setHLayout(lHLayout);
				lReplicableCanvas.setRemoveButton(removeButton);
				// gestisto il change handler per il canvas
				setUpChangeHandler(lReplicableCanvas);
				lHLayout.addMember(lReplicableCanvas);
				// Aggiungo al Vlayout l'hlayout appena creato
				if (showNewButton) {
					lVLayout.addMember(lHLayout, (lVLayout.getMembers().length - 1));
				} else {
					lVLayout.addMember(lHLayout);
				}
				lReplicableCanvas.editRecord(lRecord);
				if (toResetCanvasChanged) {
					resetCanvasChanged();
				}
			}
		}
		// Memorizzo la recordList
		storeValue(lRecordList);
	}

	/**
	 * Creo il removeButton e ne gestisco il click
	 * 
	 * @param lVLayout
	 * @param lHLayout
	 * @return
	 */
	protected RemoveButton createRemoveButton(final VLayout lVLayout, final HLayout lHLayout) {
		// Creo il bottone di remove
		RemoveButton removeButton = new RemoveButton();
		removeButton.setShowDown(false);
		removeButton.setShowRollOver(false);
		removeButton.setMargin(2);
		removeButton.setSize(20);
		removeButton.setSrc("buttons/remove.png");
		removeButton.setPrompt(I18NUtil.getMessages().removeButton_prompt());
		removeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				setUpClickRemoveHandler(lVLayout, lHLayout);
			}
		});
		return removeButton;
	}

	protected ImgButton createUpButton(final VLayout lVLayout, final HLayout lHLayout) {
		// Creo il bottone di remove
		ImgButton upButton = new ImgButton();
		upButton.setShowDown(false);
		upButton.setShowRollOver(false);
		upButton.setMargin(2);
		upButton.setSize(20);
		upButton.setSrc("buttons/up.png");
		upButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageClickUp(lVLayout, lHLayout);
			}
		});
		return upButton;
	}

	protected ImgButton createDownButton(final VLayout lVLayout, final HLayout lHLayout) {
		// Creo il bottone di remove
		ImgButton downButton = new ImgButton();
		downButton.setShowDown(false);
		downButton.setShowRollOver(false);
		downButton.setMargin(2);
		downButton.setSize(20);
		downButton.setSrc("buttons/down.png");
		downButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageClickDown(lVLayout, lHLayout);
			}
		});
		return downButton;
	}

	protected ImgButton createDuplicaRigaButton(final VLayout lVLayout, final HLayout lHLayout) {
		// Creo il bottone di remove
		ImgButton duplicaRigaButton = new ImgButton();
		duplicaRigaButton.setShowDown(false);
		duplicaRigaButton.setShowRollOver(false);
		duplicaRigaButton.setMargin(2);
		duplicaRigaButton.setSize(20);
		duplicaRigaButton.setSrc("buttons/duplicaRiga.png");
		duplicaRigaButton.setPrompt("Duplica riga");
		duplicaRigaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageClickDuplicaRiga(lVLayout, lHLayout);
			}
		});
		return duplicaRigaButton;
	}

	/**
	 * Per ogni form del {@link ReplicableCanvas} gestisco il {@link ChangedEvent} di ogni field
	 * 
	 * @param lReplicableCanvas
	 */
	protected void setUpChangeHandler(final ReplicableCanvas lReplicableCanvas) {
		for (DynamicForm form : lReplicableCanvas.getForm()) {
			for (FormItem lFormItem : form.getFields()) {
				lFormItem.addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						manageChanged(event, lReplicableCanvas);
					}
				});
			}
		}
	}

	
	
	public void onClickHandlerNewButton() {
		onClickNewButton(); 
	}
	
	
	/**
	 * Aggiunge al VLayout un HLAyout con un bottone di remove ed una istanza del {@link ReplicableCanvas}
	 */
	public ReplicableCanvas onClickNewButton() {
		// Recupero il layout
		final VLayout lVLayout = getVLayout();
		// Recupero il ReplicableItem
		ReplicableItem lReplicableItem = (ReplicableItem) lVLayout.getCanvasItem();
		// Recupero il ReplicableCanvas
		final ReplicableCanvas lReplicableCanvas = lReplicableItem.getCanvasToReply();
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			lReplicableItem.setCanFocus(false);
			lReplicableItem.setTabIndex(-1);	
			lReplicableCanvas.setCanFocus(false);
			lReplicableCanvas.setTabIndex(-1);	
 		}
		lReplicableCanvas.bringToFront();
		lReplicableCanvas.setItem(this);
		lReplicableCanvas.setEditing(editing);
		lReplicableCanvas.setCanEdit(true);
		lReplicableCanvas.setCanFocus(true);
		// incremento il contatore
		count++;
		// Lo memorizzo
		lReplicableCanvas.setCounter(count);
		// Creo un HLayout
		final HLayout lHLayout = new HLayout();
		// lHLayout.setAlign(VerticalAlignment.CENTER);
		// lHLayout.setLayoutAlign(VerticalAlignment.CENTER);
		lHLayout.setDefaultLayoutAlign(VerticalAlignment.CENTER);
		lHLayout.setHeight100();
		RemoveButton removeButton = createRemoveButton(lVLayout, lHLayout);
		// Aggiungo il removeButton
		lHLayout.addMember(removeButton);
		if (ordinabile) {
			ImgButton upButton = createUpButton(lVLayout, lHLayout);
			lHLayout.addMember(upButton);
			lReplicableCanvas.setUpButton(upButton);

			ImgButton downButton = createDownButton(lVLayout, lHLayout);
			lHLayout.addMember(downButton);
			lReplicableCanvas.setDownButton(downButton);
		}
		if (showDuplicaRigaButton) {
			ImgButton duplicaRigaButton = createDuplicaRigaButton(lVLayout, lHLayout);
			lHLayout.addMember(duplicaRigaButton);
			lReplicableCanvas.setDuplicaRigaButton(duplicaRigaButton);
		}
		// Aggiungo il replicableCanvas
		lReplicableCanvas.setVLayout(lVLayout);
		lReplicableCanvas.setHLayout(lHLayout);
		lReplicableCanvas.setRemoveButton(removeButton);
		// gestisto il change handler per il canvas
		setUpChangeHandler(lReplicableCanvas);
		lHLayout.addMember(lReplicableCanvas);
		// Aggiungo l'hlayout al layout
		if (showNewButton) {
			lVLayout.addMember(lHLayout, (lVLayout.getMembers().length - 1));
		} else {
			lVLayout.addMember(lHLayout);
		}
		lVLayout.markForRedraw();
		CanvasItem lCanvasItem = lVLayout.getCanvasItem();
		// Creo un recordList
		RecordList lRecordList;
		// Se non è memorizzato
		if (lCanvasItem.getValue() == null) {
			// Lo creo nuovo
			lRecordList = new RecordList();
		} else {
			// Altrimenti lo recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		// Aggiungo un record vuoto
		if (getCanvasDefaultRecord() != null) {
			lReplicableCanvas.editRecord(getCanvasDefaultRecord());
		}
		/*
		else {
			lReplicableCanvas.editRecord(new Record()); //TODO potrebbe togliere dei valori settati nel canvas con setValue invece che con setDefaultValue
		}
		*/
		lRecordList.add(lReplicableCanvas.getFormValuesAsRecord());
		// Memorizzo il valore nel canvas
		storeValue(lRecordList);
		return lReplicableCanvas;
	}

	public int getPosition(HLayout lHLayout) {
		int number = -1;
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < lVLayout.getMembers().length; i++) {
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
				number = i;
				break;
			}
		}
		return number;
	}

	/**
	 * Fa l'update della proprietà name ricevuta in ingresso, con il valore value ricevuto in ingresso, per il canvas alla posizione position
	 * 
	 * @param name
	 *            Nome della prop
	 * @param value
	 *            Valore della prop
	 * @param position
	 *            Posizione del canvas
	 */
	public void updateInternalValue(String name, Object value, HLayout lHLayout) {
		// Memorizzo la posizione
		int number = -1;
		// Recupero il layout
		VLayout lVLayout = getVLayout();
		if (lHLayout == null)
			number = 0;
		else {
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
					number = i;
				}
			}
		}
		// Recupero il relativo CanvasItem
		CanvasItem lCanvasItem = lVLayout.getCanvasItem();
		// Creo una recordList vuota
		RecordList lRecordList;
		// Se non è memorizzata nel canvas
		if (lCanvasItem.getValue() == null) {
			// la creo vuota
			lRecordList = new RecordList();
		} else {
			// Altrimenti la recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		// Recupero il relativo record
		Record lRecordModificato = lRecordList.get(number);
		// Se è nullo lo creo
		if (lRecordModificato == null) {
			lRecordModificato = new Record();
		}
		// SC.echo(lRecordModificato.getJsObj());
		// Setto la specifica proprietà con il valroe in ingresso
		lRecordModificato.setAttribute(name, value);
		// Se era già presente il record in lista (per forza)
		if (number != -1 && lRecordList.getLength() > number) {
			// faccio l'update sulla lista
			lRecordList.set(number, lRecordModificato);
			// System.out.println("updateInternalValue(\"" + name + "\",\"" + value + "\") alla riga " + number);
			// Memorizzo il valore
			lCanvasItem.storeValue(lRecordList);
		}
		// else {
		// // Altrimenti aggiungo in fondo
		// lRecordList.add(lRecordModificato);
		// // System.out.println("updateInternalValue(\"" + name + "\",\"" + value + "\") in fondo");
		// }
	}

	/**
	 * Fa l'update del Record ricevuto in ingresso, per il canvas alla posizione position
	 * 
	 * @param name
	 *            Nome della prop
	 * @param value
	 *            Valore della prop
	 * @param position
	 *            Posizione del canvas
	 */
	public void updateInternalRecord(Record pRecord, HLayout lHLayout) {
		// Memorizzo la posizione
		int number = -1;
		// Recupero il layout
		VLayout lVLayout = getVLayout();
		if (lVLayout != null) {
			if (lHLayout == null)
				number = 0;
			else {
				for (int i = 0; i < lVLayout.getMembers().length; i++) {
					if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
						number = i;
					}
				}
			}
			// Recupero il relativo CanvasItem
			CanvasItem lCanvasItem = lVLayout.getCanvasItem();
			// Creo una recordList vuota
			RecordList lRecordList;
			// Se non è memorizzata nel canvas
			if (lCanvasItem.getValue() == null) {
				// la creo vuota
				lRecordList = new RecordList();
			} else {
				// Altrimenti la recupero
				lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
			}
			// Recupero il relativo record
			Record lRecordModificato = lRecordList.get(number);
			// Se è nullo lo creo
			if (lRecordModificato == null) {
				lRecordModificato = new Record();
			}
			// Setto la specifica proprietà con il valroe in ingresso
			lRecordModificato = pRecord;
			// Se era già presente il record in lista (per forza)
			if (number != -1 && lRecordList.getLength() > number) {
				// faccio l'update sulla lista
				lRecordList.set(number, lRecordModificato);
				// System.out.println("updateInternalRecord(\"" + lRecordModificato.toMap().toString() + "\") alla riga " + number);
				// Memorizzo il valore
				lCanvasItem.storeValue(lRecordList);
			}
			// else {
			// // Altrimenti aggiungo in fondo
			// lRecordList.add(lRecordModificato);
			// // System.out.println("updateInternalRecord(\"" + lRecordModificato.toMap().toString() + "\") in fondo ");
			// }
		}
	}

	/**
	 * Gestisco il change relativo a ogni field di ogni form del {@link ReplicableCanvas}. Qui recupero la posizione del canvas e aggiorno il relativo record
	 * 
	 * @param event
	 * @param lReplicableCanvas
	 */
	public synchronized void manageChanged(final ChangedEvent event, final ReplicableCanvas lReplicableCanvas) {
		FormItem item = event.getItem() != null ? event.getItem() : (FormItem) event.getSource();
		if(item != null) {
			Object value = event.getItem() != null ? event.getValue() : ((FormItem) event.getSource()).getValue();
			// Aggiorno il relativo record
			// final int[] selectionRange = (item instanceof TextItem) ? ((TextItem) item).getSelectionRange() : null;
			updateInternalValue(item.getName(), value, lReplicableCanvas.getHLayout());
			// if (item instanceof TextItem) {
			// if (selectionRange != null) {
			// Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			//
			// @Override
			// public void execute() {
			// ((TextItem) item).setSelectionRange(selectionRange[0], selectionRange[1]);
			// }
			// });
			// }
			// }
		}
	}

	protected void manageClickUp(final VLayout lVLayout, final HLayout lHLayout) {
		int index = -1;
		// Individuo il relativo HLayout
		for (int i = 0; i < lVLayout.getMembers().length; i++) {
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
				index = i;
			}
		}
		// Lo sposto
		Canvas lVLayoutMember = lVLayout.getMember(index);
		lVLayout.removeMember(lVLayoutMember);
		lVLayout.addMember(lVLayoutMember, index - 1);
		getReplicableCanvas((HLayout) lVLayout.getMember(index - 1)).setCounter(index);
		getReplicableCanvas((HLayout) lVLayout.getMember(index)).setCounter(index + 1);
		lVLayout.markForRedraw();
		// Recupero il VLayout aggiornato
		VLayout lVLayoutReal = (VLayout) getCanvas();
		// Recupero il relativo CanvasItem
		CanvasItem lCanvasItem = lVLayoutReal.getCanvasItem();
		// Se non il valore non era memorizzato
		RecordList lRecordList;
		if (lCanvasItem.getValue() == null) {
			// Lo creo
			lRecordList = new RecordList();
		} else {
			// Altrimenti lo recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		Record lRecord = lRecordList.get(index);
		lRecordList.removeAt(index);
		lRecordList.addAt(lRecord, index - 1);
		// Lo memorizzo
		storeValue(lRecordList);
	}

	protected void manageClickDown(final VLayout lVLayout, final HLayout lHLayout) {
		int index = -1;
		// Individuo il relativo HLayout
		for (int i = 0; i < lVLayout.getMembers().length; i++) {
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
				index = i;
			}
		}
		// Lo sposto
		Canvas lVLayoutMember = lVLayout.getMember(index);
		lVLayout.removeMember(lVLayoutMember);
		lVLayout.addMember(lVLayoutMember, index + 1);
		getReplicableCanvas((HLayout) lVLayout.getMember(index)).setCounter(index + 1);
		getReplicableCanvas((HLayout) lVLayout.getMember(index + 1)).setCounter(index + 2);
		lVLayout.markForRedraw();
		// Recupero il VLayout aggiornato
		VLayout lVLayoutReal = (VLayout) getCanvas();
		// Recupero il relativo CanvasItem
		CanvasItem lCanvasItem = lVLayoutReal.getCanvasItem();
		// Se non il valore non era memorizzato
		RecordList lRecordList;
		if (lCanvasItem.getValue() == null) {
			// Lo creo
			lRecordList = new RecordList();
		} else {
			// Altrimenti lo recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		Record lRecord = lRecordList.get(index);
		lRecordList.removeAt(index);
		lRecordList.addAt(lRecord, index + 1);
		// Lo memorizzo
		storeValue(lRecordList);
	}

	protected void manageClickDuplicaRiga(final VLayout lVLayout, final HLayout lHLayout) {
		int index = -1;
		// Individuo il relativo HLayout
		for (int i = 0; i < lVLayout.getMembers().length; i++) {
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
				index = i;
			}
		}
		if (index >= 0) {
			// Recupero il VLayout aggiornato
			VLayout lVLayoutReal = (VLayout) getCanvas();
			// Recupero il relativo CanvasItem
			CanvasItem lCanvasItem = lVLayoutReal.getCanvasItem();
			// Recupero i valori
			RecordList lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
			Map values = lRecordList.get(index).toMap();
			String[] fieldsToSkipInDuplicaRiga = getFieldsToSkipInDuplicaRiga();
			if (fieldsToSkipInDuplicaRiga != null) {
				for (String fieldName : fieldsToSkipInDuplicaRiga) {
					values.remove(fieldName);
				}
			}
			ReplicableCanvas canvas = onClickNewButton();
			for (ReplicableCanvasForm form : canvas.getForm()) {
				form.setValues(values);
			}
		}
	}

	protected String[] getFieldsToSkipInDuplicaRiga() {
		return null;
	}

	public void setUpClickRemoveHandler(final VLayout lVLayout, final HLayout lHLayout) {
		setUpClickRemove(lVLayout, lHLayout);
	}
	
	/**
	 * Gestisto il click sul remove. Rimuovo l'hlayout che contiene il bottone cliccato. Rimuovo il relativo record dalla lista
	 * 
	 * @param lVLayout
	 * @param lHLayout
	 */
	public void setUpClickRemove(final VLayout lVLayout, final HLayout lHLayout) {
		int index = -1;
		// Individuo il relativo HLayout
		for (int i = 0; i < lVLayout.getMembers().length; i++) {
			if (lVLayout.getMember(i).getID().equals(lHLayout.getID())) {
				index = i;
			}
		}
		// Lo rimuovo				
		removeVLayoutMemberAtIndex(lVLayout, index);		
		lVLayout.markForRedraw();
		// Recupero il VLayout aggiornato
		VLayout lVLayoutReal = (VLayout) getCanvas();
		// Recupero il relativo CanvasItem
		CanvasItem lCanvasItem = lVLayoutReal.getCanvasItem();
		// Se non il valore non era memorizzato
		RecordList lRecordList;
		if (lCanvasItem.getValue() == null) {
			// Lo creo
			lRecordList = new RecordList();
		} else {
			// Altrimenti lo recupero
			lRecordList = new RecordList((JavaScriptObject) lCanvasItem.getValue());
		}
		// Rimuovo dalla lista il relativo record
		// Record lRecord = new Record();
		// lRecord.setAttribute("dirty", true);
		// lRecordList.set(index, lRecord );
		lRecordList.removeAt(index);
		count--;
		// Recupero il ReplicableItem
		ReplicableItem lReplicableItem = (ReplicableItem) lVLayout.getCanvasItem();
		if (lVLayout != null && getTotalMembers() > 0) {
			for (int i = 0; i < getTotalMembers(); i++) {
				ReplicableCanvas lReplicableCanvas = getReplicableCanvas((HLayout) lVLayout.getMember(i));
				lReplicableCanvas.setItem(lReplicableItem);
				lReplicableCanvas.setCounter(i + 1);
			}
		}
		// Lo memorizzo
		storeValue(lRecordList);
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditing(canEdit);
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
							lReplicableCanvas.setCanEdit(canEdit);
						} else {
							if (canEdit) {
								if(lHLayoutMember.getParentElement() != null) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							} else {
								lHLayoutMember.hide();
							}
						}
					}
				}
			}
		}
		if(getForm() != null && getForm().getDetailSection() != null && getForm().getDetailSection().showFirstCanvasWhenEmptyAfterOpen() && getForm().getDetailSection().isOpen()) {
			try {
				if (getEditing() != null && getEditing() && getTotalMembers() == 0) {
					onClickNewButton();
				} else if(getTotalMembers() == 1 && !hasValue()){
					removeEmptyCanvas();
				}
			} catch (Exception e) {
			}
		}
		this.fireEvent(new ChangeCanEditEvent(instance));
	}
	
	public void setCanEdit(boolean flgInibitaAggiuntaRighe, boolean flgInibitaCancellazioneRighe, boolean canEdit) {
		if(flgInibitaAggiuntaRighe && flgInibitaCancellazioneRighe) {
			setCanEdit(false);
			if (getCanvas() != null) {
				final VLayout lVLayout = (VLayout) getCanvas();
				for (int i = 0; i < lVLayout.getMembers().length; i++) {
					Canvas lVLayoutMember = lVLayout.getMember(i);
					if (lVLayoutMember instanceof HLayout) {
						for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
							if (lHLayoutMember instanceof ReplicableCanvas) {
								ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
								lReplicableCanvas.setCanEdit(canEdit);		
							}
						}
					}
				}
			}
		} else {
			setCanEdit(canEdit);
			if(flgInibitaAggiuntaRighe || flgInibitaCancellazioneRighe) {
				if (getCanvas() != null) {
					final VLayout lVLayout = (VLayout) getCanvas();
					for (int i = 0; i < lVLayout.getMembers().length; i++) {
						Canvas lVLayoutMember = lVLayout.getMember(i);
						if (lVLayoutMember instanceof HLayout) {
							for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
								if (lHLayoutMember instanceof ImgButton) {
									if (i == (lVLayout.getMembers().length - 1)) {
										// se è il bottone di add lo nascondo
										if(flgInibitaAggiuntaRighe) {
											lHLayoutMember.hide();
										}
									} else if (lHLayoutMember instanceof RemoveButton) {
										// se è un bottone di remove lo disabilito
										if(flgInibitaCancellazioneRighe) {
											((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void refreshTabIndex() {
		if(it.eng.utility.ui.module.layout.client.Layout.isAttivoRefreshTabIndex()) {
			if (getCanvas() != null) {
				final VLayout lVLayout = getVLayout();
				for (int i = 0; i < lVLayout.getMembers().length; i++) {
					Canvas lVLayoutMember = lVLayout.getMember(i);
					if (lVLayoutMember instanceof HLayout) {
						for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
							if (lHLayoutMember instanceof ReplicableCanvas) {
								ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
								int counter = lReplicableCanvas.getCounter();
								if (getTabIndex() != null) {
									int tabIndex = getTabIndex() + ((counter - 1) * 100) + 1;
									for (DynamicForm form : lReplicableCanvas.getForm()) {
										form.setCanFocus(false);
										form.setTabIndex(-1);
										for (FormItem item : form.getFields()) {
											if (item.getCanFocus() && !(item instanceof HiddenItem)) {
												item.setTabIndex(0); // item.setTabIndex(tabIndex);
												item.setGlobalTabIndex(0); // item.setGlobalTabIndex(tabIndex);
												CustomDetail.showItemTabIndex(item);
												tabIndex += 1;
											} else
												item.setTabIndex(-1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void redraw() {
		HLayout addButtonsLayout = getAddButtonsLayout();
		if (addButtonsLayout != null) {
			if (maxLength != null && maxLength.intValue() > 0 && getTotalMembers() >= maxLength.intValue()) {
				addButtonsLayout.hide();
			} else {
				if (editing) {
					if(addButtonsLayout.getParentElement() != null) {
						addButtonsLayout.show();
						if (UserInterfaceFactory.isAttivaAccessibilita()){
							changeAriaHidden();
						}
					} else {
						addButtonsLayout.hide();
					}
				} else {
					addButtonsLayout.hide();
				}
			}
		}
		if (getCanvas() != null) {
			final VLayout lVLayout = getVLayout();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
							lReplicableCanvas.markForRedraw();
						}
					}
				}
			}
		}
	}
	
	public native void changeAriaHidden ()/*-{
		var imgButtonElements = $doc.getElementsByClassName('imgButton');
		var multipleUploadButtonElements = $doc.getElementsByClassName('normal');
		var divImgButtonElements = Array.prototype.filter.call(imgButtonElements, function(testElement){
		  return testElement.nodeName === 'DIV';
		});
		var divMultipleUploadButtonElements = Array.prototype.filter.call(multipleUploadButtonElements, function(testElement){
		  return testElement.nodeName === 'DIV';
		});
		if (divImgButtonElements !== null && divImgButtonElements !== undefined) {
			for (var i = 0; i < divImgButtonElements.length; i++) {
			  var aria_hidden = divImgButtonElements[i].getAttribute('aria-hidden');
			  if (aria_hidden) {
			  	divImgButtonElements[i].setAttribute('aria-hidden', 'false');
			  }
			}
		}
		if (divMultipleUploadButtonElements !== null && divMultipleUploadButtonElements !== undefined) {
			for (var i = 0; i < divMultipleUploadButtonElements.length; i++) {
			  var aria_hidden = divMultipleUploadButtonElements[i].getAttribute('aria-hidden');
			  if (aria_hidden) {
			  	divMultipleUploadButtonElements[i].setAttribute('aria-hidden', 'false');
			  }
			}
		}
	}-*/;

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
				}
			}
		}
		return totalHeight;
	}

	public boolean isObbligatorio() {
		return getAttributeAsBoolean("obbligatorio") != null ? getAttributeAsBoolean("obbligatorio") : false;
	}
	
	public void setObbligatorio(boolean obbligatorio) {
		setAttribute("obbligatorio", obbligatorio);
	}
	
	public void setObbligatorioAfterRedraw(boolean obbligatorio) {
		// ATTENZIONE: da utilizzare quando l'obbligatorietà del replicableItem dipende dal valore di qualche altro campo a maschera 
		// questo metodo verrà utilizzato all'interno della showIfCondition del replicableItem, chiamata in seguito al redraw del form scatenato dall'evento di changed del campo da cui dipende l'obbligatorietà del replicableItem
		if(obbligatorio != isObbligatorio()) {
			// se l'obbligatorietà è cambiata rispetto a prima
			setAttribute("obbligatorio", obbligatorio);
			storeValueAfterChangedObbligatorio(getForm() != null ? getForm().getValueAsRecordList(getName()) : new RecordList());
		}
	}	

	public Boolean getNotReplicable() {
		return notReplicable;
	}

	public void setNotReplicable(Boolean notReplicable) {
		this.notReplicable = notReplicable;
		this.showNewButton = !notReplicable;
	}

	public Boolean getShowNewButton() {
		return showNewButton;
	}

	public void setShowNewButton(Boolean showNewButton) {
		this.showNewButton = showNewButton;
	}
	
	public Boolean getShowRemoveButton() {
		return showRemoveButton;
	}

	public void setShowRemoveButton(Boolean showRemoveButton) {
		this.showRemoveButton = showRemoveButton;
	}

	public Boolean getEditing() {
		return editing;
	}

	public void setEditing(Boolean editing) {
		this.editing = editing;
	}

	public Boolean getOrdinabile() {
		return ordinabile;
	}

	public void setOrdinabile(Boolean ordinabile) {
		this.ordinabile = ordinabile;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Boolean getShowDuplicaRigaButton() {
		return showDuplicaRigaButton;
	}

	public void setShowDuplicaRigaButton(Boolean showDuplicaRigaButton) {
		this.showDuplicaRigaButton = showDuplicaRigaButton;
	}
	
	public void removeVLayoutMemberAtIndex(VLayout lVLayout, int index) {
		HLayout lHLayout = (HLayout) lVLayout.getMember(index);
		lVLayout.removeMember(lHLayout);
		ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
		lReplicableCanvas.markForDestroy();
		lHLayout.markForDestroy();
	}
	
	public void storeValueAfterChangedObbligatorio(RecordList lRecordList) {		
		storeValue(lRecordList);		
		if (isObbligatorio() || getNotReplicable() /*|| hasDefaultValue()*/) {
			try {
				if (getEditing() != null && getEditing() && getTotalMembers() == 0) {
					onClickNewButton();
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void storeValue(final RecordList values) {
		VLayout lVLayout = getVLayout();
		if (lVLayout != null) {
			if (isObbligatorio() && getTotalMembers() == 1) {
				getFirstCanvas().getRemoveButton().setDisabled(true);
			} else if (getTotalMembers() > 0) {
				if (!getFirstCanvas().getRemoveButton().isAlwaysDisabled()) {
					getFirstCanvas().getRemoveButton().setDisabled(false);
				}
			}
			HLayout addButtonsLayout = getAddButtonsLayout();
			if (addButtonsLayout != null) {
				if (maxLength != null && maxLength.intValue() > 0 && getTotalMembers() >= maxLength.intValue()) {
					addButtonsLayout.hide();
				} else {
					if (editing) {
						if(addButtonsLayout.getParentElement() != null) {
							addButtonsLayout.show();
						} else {
							addButtonsLayout.hide();
						}
					} else {
						addButtonsLayout.hide();
					}
				}
			}
			if (ordinabile) {
				if (lVLayout != null && getTotalMembers() > 0) {
					for (int i = 0; i < getTotalMembers(); i++) {
						HLayout lHLayout = (HLayout) lVLayout.getMember(i);
						ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
						lReplicableCanvas.getUpButton().setDisabled(false);
						lReplicableCanvas.getDownButton().setDisabled(false);
						// se è il primo disabilito upButton
						if (i == 0) {
							lReplicableCanvas.getUpButton().setDisabled(true);
						}
						// se è l'ultimo disabilito downButton
						if (i == (getTotalMembers() - 1)) {
							lReplicableCanvas.getDownButton().setDisabled(true);
						}
					}
				}
			}
		}
		super.storeValue(values);
		this.fireEvent(new ChangedEvent(instance.getJsObj()) {

			@Override
			public FormItem getItem() {
				return instance;
			}

			@Override
			public Object getValue() {
				return values;
			}
		});
		refreshTabIndex();
	}

	public class RemoveButton extends ImgButton {

		private boolean alwaysDisabled;

		public RemoveButton() {
	 		if (UserInterfaceFactory.isAttivaAccessibilita()){
				setTabIndex(null);
				setCanFocus(true);
	 		} else {
				setCanFocus(false);
				setTabIndex(-1);
			}
		}

		public boolean isAlwaysDisabled() {
			return alwaysDisabled;
		}

		public void setAlwaysDisabled(boolean alwaysDisabled) {
			this.alwaysDisabled = alwaysDisabled;
			setDisabled(true);
		}
	}

	@Override
	public HandlerRegistration addChangeCanEditHandler(ChangeCanEditHandler handler) {
		return doAddHandler(handler, ChangeCanEditEvent.getType());

	}

	public Record getCanvasDefaultRecord() {
		return null;
	}
	
	// Indica se il ReplicableItem non è visibile all'interno del form oppure se il form è HIDDEN (volutamente nascosto, non 
	// perchè la sezione a cui appartiene è chiusa o perchè sono su un altro tab), in modo da ignorare tutte le validazioni. 
	// Per dubbi o chiarimenti chiedere a Mattia Zanin o a Federico Cacco.
	public boolean skipValidation() {
		return getForm() == null || !getForm().isFormVisible() || (getForm().isFormVisible() && !getVisible()) || !getForm().isEditing();
	}
	
	public void manageOnDestroy() {
		for (ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			lReplicableCanvas.markForDestroy();
		}
	}

}
