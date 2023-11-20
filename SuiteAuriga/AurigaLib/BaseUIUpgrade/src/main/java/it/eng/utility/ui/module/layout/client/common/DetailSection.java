/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

public class DetailSection extends VLayout {

	private ImgButton header;
	private VLayout headerLayout;
	private VLayout layout;
	private VLayout sectionLayout;
	private DynamicForm[] mForms;

	private String sectionTitle;
	private boolean canCollapse;
	private boolean showOpen;
	private boolean isRequired;
	private String backgroundColor;

	protected boolean closed;
	protected boolean disabled;

	private int viewReplicableItemHeight = 0;

	public static int _DEFAULT_VIEW_REPLICABLE_ITEM_HEIGHT = 130;

	// *******************************************************************************

	public DetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired, final DynamicForm... forms) {
		this(pTitle, null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
	}

	public DetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
			final DynamicForm... forms) {
		this(pTitle, pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
	}

	public DetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
			final String pBackgroundColor, final DynamicForm... forms) {

		mForms = forms;

		this.sectionTitle = pTitle;
		this.canCollapse = pCanCollapse;
	 	this.showOpen = pShowOpen;
		this.isRequired = pIsRequired;
		this.backgroundColor = pBackgroundColor;

		buildHeader();
		
		headerLayout = new VLayout();
		headerLayout.setWidth(5);
		headerLayout.setHeight(5);
		headerLayout.setPadding(0);
		headerLayout.setMargin(0);
		headerLayout.setOverflow(Overflow.VISIBLE);
		if(backgroundColor != null) {
			headerLayout.setBackgroundColor(backgroundColor);
		}
		headerLayout.setStyleName(it.eng.utility.Styles.headerDetailSection);
		headerLayout.setMembers(header);
		
		if(!showTitleInSection()) {
			addChild(headerLayout);
		}
		
		if (canCollapse()) {
			if (showOpen()) {
				if (showFirstCanvasWhenEmptyAfterOpen()) {
					showFirstCanvas();
				}
				setOpenerOpenedIcon();
			} else {
				setOpenerClosedIcon();
			}			
		} else {
			setOpenerBlanckIcon();
		}

		setTitle(sectionTitle);

		if (pHeight != null) {
			setHeight(pHeight);
		}
		
		if(showTitleInSection()) {
			
			layout = new VLayout();
			layout.setPadding(5);
			for (DynamicForm form : forms) {
				form.setPadding(0);
				
				// per evitare l'effetto blink degli scroll che compaiono e scompaiono
				form.setHeight("99%");
				form.setWidth("99%");
				form.setOverflow(Overflow.VISIBLE);
//				form.setCellBorder(1);
			}
			layout.setRedrawOnResize(true);

			setForms(forms);
			
			sectionLayout = new VLayout();		
			sectionLayout.setMargin(4);
			if(backgroundColor != null) {
				sectionLayout.setBackgroundColor(backgroundColor);
			}
			sectionLayout.setStyleName(it.eng.utility.Styles.detailSection);
		
			sectionLayout.setMembers(headerLayout, layout);
		
			addMember(sectionLayout);
		
		} else {		
			
			layout = new VLayout();
			layout.setPadding(11);
			layout.setMargin(4);
			layout.setIsGroup(true);
			if(backgroundColor != null) {
				layout.setBackgroundColor(backgroundColor);
			}
			layout.setStyleName(it.eng.utility.Styles.detailSection);
			layout.setRedrawOnResize(true);

			setForms(forms);
			
			addMember(layout);
			
		}

		if (canCollapse() && !showOpen()) {
			hideLayout();
		} else {
			showLayout();
		}
	}
	
	public void showLayout() {
		layout.show();
		headerLayout.bringToFront();
		fixEmptyAndNotEdititableSectionApparence();
	}

	public void hideLayout() {
		layout.hide();
		headerLayout.bringToFront();
	}
	
	public void buildHeader() {
		
		header = new ImgButton();
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			header.setCanFocus(true);
	//		header.setTabIndex(-1);	
 		} else {
		header.setCanFocus(false);
		header.setTabIndex(-1);
		}
		header.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (canCollapse()) {
					((ImgButton) event.getSource()).bringToFront();
					if (layout.isVisible()) {
						close();
					} else {
						open(new ServiceCallback<String>() {

							@Override
							public void execute(String openErrorMessage) {
								if (openErrorMessage != null && !"".equals(openErrorMessage)) {
									Layout.addMessage(new MessageBean(openErrorMessage, "", MessageType.ERROR));
								}
							}							
						});						
					}
				}
			}
		});

		if (canCollapse()) {
			if (isRequired() && FrontendUtil.showAsteriskIconInRequiredFormItemTitle()) {
				header.setIconHeight(22);
				header.setIconWidth(32);
			} else {
				header.setIconHeight(22);
				header.setIconWidth(22);
			}
			header.setShowDownIcon(false);
			header.setShowRollOverIcon(false);
			header.setIconOrientation("left");
			header.setIconSpacing(0);
		} else {
			header.setCursor(Cursor.ARROW);
			header.setIconHeight(22);
			header.setIconWidth(1);
		}
		if(backgroundColor != null) {
			header.setBackgroundColor(backgroundColor);
		}
		header.setIcon("blank.png");
		header.setSrc("blank.png");
		header.setShowDown(false);
		header.setShowRollOver(false);
		header.setAutoFit(true);
		header.setLeft(8);	
		header.setShowTitle(true);
		header.setAlign(Alignment.LEFT);
		header.bringToFront();		
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		if (getDisabled()) {
			close();
		} else {
			if (!isOpen()) {
				setOpenerClosedIcon();
			} else {
				setOpenerOpenedIcon();
			}
		}
		redrawDetailSectionHeaderTitle();
	}
	
	@Override
	public boolean getDisabled() {
		return disabled;
	}

	public void setForms(DynamicForm... forms) {
		layout.setMembers(forms);
		for (DynamicForm form : forms) {
			form.setDetailSection(this);
			for (FormItem item : form.getFields()) {
				if (item instanceof ReplicableItem) {
					ReplicableItem replicableItem = ((ReplicableItem) item);
					manageOnChangeCanEdit(replicableItem);
					replicableItem.addChangeCanEditHandler(new ChangeCanEditHandler() {

						@Override
						public void onChangeCanEdit(ChangeCanEditEvent event) {
							manageOnChangeCanEdit(event.getItem());
						}
					});
				}
			}
			form.bringToFront();
		}
		layout.markForRedraw();
	}

	public DynamicForm[] getForms() {
		return mForms;
	}

	public void manageOnChangeCanEdit(FormItem item) {
		ReplicableItem replicableItem = (ReplicableItem) item;
		int totalHeight = replicableItem.getTotalHeight();
		if (replicableItem.getEditing() || totalHeight < getViewReplicableItemHeight()) {
			if (layout.getMembers().length > 0 && layout.getMembers()[0] instanceof VLayout) {
				layout.setCanDragResize(false);
				VLayout formsLayout = (VLayout) layout.getMembers()[0];
				DynamicForm form = (DynamicForm) formsLayout.getMembers()[0];
				formsLayout.markForDestroy();
				layout.setMembers(form);
				layout.setAutoHeight();
				layout.markForRedraw();
			}
		} else {
			if (layout.getMembers().length > 0 && layout.getMembers()[0] instanceof DynamicForm) {
				layout.setHeight(getViewReplicableItemHeight());
				layout.setMinHeight(getViewReplicableItemHeight());
				layout.setCanDragResize(true);
				layout.setResizeFrom("B");
				DynamicForm form = (DynamicForm) layout.getMembers()[0];
				final VLayout formsLayout = new VLayout();
				formsLayout.setOverflow(Overflow.AUTO);
				formsLayout.setHeight100();
				formsLayout.setMembers(form);
				layout.setMembers(formsLayout);
				layout.markForRedraw();
			}
		}
		fixEmptyAndNotEdititableSectionApparence();
	}

	public void setViewReplicableItemHeight(int viewReplicableItemHeight) {
		this.viewReplicableItemHeight = viewReplicableItemHeight;
	}

	public int getViewReplicableItemHeight() {
		return viewReplicableItemHeight > 0 ? viewReplicableItemHeight : _DEFAULT_VIEW_REPLICABLE_ITEM_HEIGHT;
	}

	public void clearValue() {
		for (DynamicForm lDynamicForm : mForms) {
			lDynamicForm.clearValues();
			for (FormItem lFormItem : lDynamicForm.getFields()) {
				lFormItem.clearValue();
			}
		}
	}

	@Override
	public void setTitle(String title) {
		setAttribute("realTitle", title, true);
		if (!canCollapse()) {
			title = "&nbsp;&nbsp;" + title;
		}
		if (isRequired() && !FrontendUtil.showAsteriskIconInRequiredFormItemTitle()) {
			title += "&nbsp;*";
		}
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			if (!isOpen() && hasValue()) {
				header.setTitle("<span class=\"" + getHeaderTitleStyle() + "\"" + (getDisabled() ? " style=\"color:grey\"" : "") + "><h6>" + title + "&nbsp;...&nbsp;&nbsp;</h6></span>");
			} else {
				header.setTitle("<span class=\"" + getHeaderTitleStyle() + "\"" + (getDisabled() ? " style=\"color:grey\"" : "") + "><h6>" + title + "&nbsp;&nbsp;</h6></span>");
			}	
 		} else {
			if (!isOpen() && hasValue()) {
				header.setTitle("<span class=\"" + getHeaderTitleStyle() + "\"" + (getDisabled() ? " style=\"color:grey\"" : "") + ">" + title + "&nbsp;...&nbsp;&nbsp;</span>");
			} else {
				header.setTitle("<span class=\"" + getHeaderTitleStyle() + "\"" + (getDisabled() ? " style=\"color:grey\"" : "") + ">" + title + "&nbsp;&nbsp;</span>");
			}
		}
	}

	@Override
	public String getTitle() {
		return getAttribute("realTitle");
	}
	
	public String getHeaderTitleStyle() {
		return it.eng.utility.Styles.headerDetailSectionTitle;
	}

	public void redrawDetailSectionHeaderTitle() {
		setTitle(getTitle());
		if (!canCollapse() || getDisabled()) {
			header.setCursor(Cursor.ARROW);
		} else {
			header.setCursor(Cursor.HAND);
		}
	}
	
	@Override
	public void redraw() {
		super.redraw();
		fixEmptyAndNotEdititableSectionApparence();
	}

	public void open() {
		open(null);
	}
	
	public void showFirstCanvas() {
		try {
			for (DynamicForm mDynamicForm : mForms) {
				for (FormItem item : mDynamicForm.getFields()) {
					if (item instanceof ReplicableItem && (((ReplicableItem) item).getEditing() != null && ((ReplicableItem) item).getEditing())
							&& ((ReplicableItem) item).getTotalMembers() == 0) {
						ReplicableCanvas lReplicableCanvas = ((ReplicableItem) item).onClickNewButton();						
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void open(ServiceCallback<String> errorCallback) {
		if (isOpenable()) {
			if (showFirstCanvasWhenEmptyAfterOpen()) {
				showFirstCanvas();
			}
			setOpenerOpenedIcon();
			redrawDetailSectionHeaderTitle();
			showLayout();
		} else {
			if (errorCallback != null) {
				String openErrorMessage = getOpenErrorMessage();
				errorCallback.execute(openErrorMessage);
			}
		}
	}
	
	public void removeEmptyCanvas() {
		try {
			for (DynamicForm mDynamicForm : mForms) {
				for (FormItem item : mDynamicForm.getFields()) {
					if (item instanceof ReplicableItem) {
						ReplicableItem lReplicableItem = (ReplicableItem) item;
						if(lReplicableItem.getEditing() != null && lReplicableItem.getEditing()/* && !lReplicableItem.isObbligatorio()*/) {	
							lReplicableItem.removeEmptyCanvas();
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void close() {
		if (showFirstCanvasWhenEmptyAfterOpen()) {
			removeEmptyCanvas();
		}
		setOpenerClosedIcon();
		redrawDetailSectionHeaderTitle();
		hideLayout();
	}

	public boolean hasValue() {
		for (DynamicForm mDynamicForm : mForms) {
			mDynamicForm.markForRedraw();
			if (mDynamicForm.hasValue()) {
				return true;
			}
		}
		return false;
	}

	public void openIfhasValue() {
		if (hasValue()) {
			open();
		} else {
			close();
		}
	}

	public boolean hasErrors() {
		for (DynamicForm mDynamicForm : mForms) {
			mDynamicForm.markForRedraw();
			if (mDynamicForm.hasErrors()) {
				return true;
			}
		}
		return false;
	}
	
	public void openIfhasErrors() {
		if (hasErrors()) {
			open();
		}
	}

	public void setOpenerOpenedIcon() {
		closed = false;
		if (isRequired() && FrontendUtil.showAsteriskIconInRequiredFormItemTitle()) {
			header.setIcon(getDisabled() ? "opener_opened_required_Disabled.png" : "opener_opened_required.png");			
		} else {
			header.setIcon(getDisabled() ? "opener_opened_Disabled.png" : "opener_opened.png");			
		}
	}

	public void setOpenerClosedIcon() {
		closed = true;
		if (isRequired() && FrontendUtil.showAsteriskIconInRequiredFormItemTitle()) {
			header.setIcon(getDisabled() ? "opener_closed_required_Disabled.png" : "opener_closed_required.png");			
		} else {
			header.setIcon(getDisabled() ? "opener_closed_Disabled.png" : "opener_closed.png");
		}
	}
	
	public void setOpenerBlanckIcon() {
		header.setIcon("blank.png");			
	}

	public VLayout getLayout() {
		return layout;
	}

	public void setLayout(VLayout layout) {
		this.layout = layout;
	}

	public boolean showOpen() {
		return showOpen;
	}
	
	public boolean canCollapse() {
		return canCollapse;
	}
	
	public boolean isRequired() {
		return isRequired;
	}
	
	public void setRequired(boolean isRequired) {
		
		this.isRequired = isRequired;	
		
		//distruggo il precedente header o rimane in memoria
		if(header != null) {
			header.markForDestroy();
		}
		
		buildHeader();
		headerLayout.setMembers(header);	
		
		if (!isOpen()) {
			setOpenerClosedIcon();						
		} else {						
			setOpenerOpenedIcon();
		}
		
		redrawDetailSectionHeaderTitle();
	}
	
	public boolean isOpen() {
		return !closed;
	}

	public boolean validate() {
		boolean valid = true;
		for (DynamicForm form : mForms) {
			if(form.isEditing()) {
				if(form.getFields().length == 1 && (form.getFields()[0] instanceof ReplicableItem)) {
					ReplicableItem lReplicableItem = (ReplicableItem) form.getFields()[0];
					valid = lReplicableItem.validate() && valid;				
				} else {
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item != null && item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							valid = lReplicableItem.validate() && valid;
						} else if (item != null && item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							valid = lIDocumentItem.validate() && valid;
						}					
					}
				}
			}
		}
		return valid;		
	}
	
	public boolean valuesAreValid() {
		boolean valid = true;
		for (DynamicForm form : mForms) {
			if(form.isEditing()) {
				if(form.getFields().length == 1 && (form.getFields()[0] instanceof ReplicableItem)) {
					ReplicableItem lReplicableItem = (ReplicableItem) form.getFields()[0];
					valid = lReplicableItem.valuesAreValid() && valid;				
				} else {
					valid = form.valuesAreValid(false) && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							valid = lReplicableItem.valuesAreValid() && valid;
						} else if (item != null && item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							valid = lIDocumentItem.valuesAreValid() && valid;
						}						
					}
				}
			}
		}
		return valid;		
	}
	
	public boolean isOpenable() {
		return true;
	}

	public String getOpenErrorMessage() {
		return null;
	}

	public boolean showFirstCanvasWhenEmptyAfterOpen() {
		return false;
	}
	
	public boolean showTitleInSection() {
		return Layout.isAttivoRestyling();
	}
	
	// Una sezione è vuota se è composta solamente da sezioni ripetibili, ed esse sono tutte vuote
	protected boolean isSectionEmptyAndNotEdititable() {
		boolean formEmptyAndNotEdititable = true;
		// Ciclo su tutti i form
		for (DynamicForm form : mForms) {
			// Verifico se l'item è editabile
			if(!form.isEditing()) {
				for (FormItem item : form.getFields()) {
					// Verifico se l'item è un ReplicableItem vuoto non editabile
					if (!(item instanceof ReplicableItem && ((ReplicableItem) item).getAllCanvas().length == 0)) {
						formEmptyAndNotEdititable = false;
					} 
				}
			} else {
				formEmptyAndNotEdititable = false;
			}
		}
		return formEmptyAndNotEdititable;
	}
	
	// Metodo per fare in modo che le sezioni vuote e non editabili non occupino spazio
	protected void fixEmptyAndNotEdititableSectionApparence() {
		if (showTitleInSection()) {
			boolean formEmptyAndNotEdititable = isSectionEmptyAndNotEdititable();
			if (formEmptyAndNotEdititable) {
				layout.setHeight(0);
				layout.setOverflow(Overflow.HIDDEN);
			}else {
				layout.setHeight100();
				layout.setOverflow(Overflow.VISIBLE);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		if(header != null) {
			header.destroy();		
		}
		if(headerLayout != null) {
			headerLayout.destroy();		
		}
		if(layout != null) {
			layout.destroy();	
		}
		if(sectionLayout != null) {
			sectionLayout.destroy();		
		}
		if(mForms != null) {
			for(DynamicForm form : mForms) {
				form.destroy();
			}
		}
		super.onDestroy();
	}
	
}
