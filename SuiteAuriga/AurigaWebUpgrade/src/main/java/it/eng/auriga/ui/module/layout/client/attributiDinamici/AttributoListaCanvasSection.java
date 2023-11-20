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

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas.ReplicableCanvasForm;

public class AttributoListaCanvasSection extends VLayout {

	private ImgButton header;
	private VLayout headerLayout;
	private VLayout layout;
	private VLayout sectionLayout;

	private VLayout canvasLayout;
	private ReplicableCanvasForm[] forms;

	public boolean closed;

	public int limite;

	public AttributoListaCanvasSection(VLayout canvasLayout, final int limite, final ReplicableCanvasForm... forms) {

		this.canvasLayout = canvasLayout;
		this.limite = limite;
		this.forms = forms;
		
		buildHeader();
		
		headerLayout = new VLayout();
		headerLayout.setWidth(5);
		headerLayout.setHeight(5);
		headerLayout.setPadding(0);
		headerLayout.setMargin(0);
		headerLayout.setOverflow(Overflow.VISIBLE);
		headerLayout.setStyleName(it.eng.utility.Styles.headerDetailSection);
		headerLayout.setMembers(header);

		if(!showTitleInSection()) {
			addChild(headerLayout);
		}
		
		setOpenerClosedIcon();
		
		if(showTitleInSection()) {
			
			layout = new VLayout();
			layout.setPadding(5);
			for (DynamicForm form : forms) {
				form.setPadding(0);
			}
			layout.setRedrawOnResize(true);

			setForms(forms);
			
			sectionLayout = new VLayout();		
			sectionLayout.setMargin(4);
			sectionLayout.setStyleName(it.eng.utility.Styles.detailSection);
		
			sectionLayout.setMembers(headerLayout, layout);
		
			addMember(sectionLayout);
			
		} else {

			layout = new VLayout();
			layout.setPadding(11);
			layout.setMargin(4);
			layout.setIsGroup(true);
			layout.setStyleName(it.eng.utility.Styles.detailSection);
			layout.setRedrawOnResize(true);
	
			setForms(forms);
	
			addMember(layout);
		
		}

		layout.show();

		open();

	}
	
	public void buildHeader() {
		
		header = new ImgButton();
		header.setCanFocus(false);
		header.setTabIndex(-1);
		header.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				((ImgButton) event.getSource()).bringToFront();
				boolean isVisible = forms[limite].isVisible();
				if (isVisible) {
					close();
				} else {
					open();
				}
			}
		});
		
		header.setIconHeight(22);
		header.setIconWidth(22);
		header.setShowDownIcon(false);
		header.setShowRollOverIcon(false);
		header.setIconOrientation("left");
		header.setIconSpacing(0);
		header.setCursor(Cursor.HAND);

		header.setSrc("blank.png");
		header.setHeight(8);
		header.setShowDown(false);
		header.setShowRollOver(false);
		header.setAutoFit(true);
		header.setLeft(8);
		header.setTitle("");
		header.setShowTitle(true);
		header.setAlign(Alignment.LEFT);
		header.bringToFront();
	}

	public void setForms(DynamicForm... forms) {
		layout.setMembers(forms);
		for (DynamicForm form : forms) {
			form.bringToFront();
		}
		layout.markForRedraw();
	}

	public DynamicForm[] getForms() {
		return forms;
	}

	public void clearValue() {
		for (DynamicForm form : forms) {
			form.clearValues();
			for (FormItem item : form.getFields()) {
				item.clearValue();
			}
		}
	}

	public void open() {
		setOpenerOpenedIcon();
		for (int i = 0; i < forms.length; i++) {
			ReplicableCanvasForm form = forms[i];
			if (i > (limite - 1)) {
				form.show();
			}
		}
		canvasLayout.setMembers(this);
		canvasLayout.redraw();
		headerLayout.bringToFront();
	}

	public void close() {
		setOpenerClosedIcon();
		for (int i = 0; i < forms.length; i++) {
			ReplicableCanvasForm form = forms[i];
			if (i > (limite - 1)) {
				form.hide();
			}
		}
		canvasLayout.setMembers(this);
		canvasLayout.redraw();
		headerLayout.bringToFront();
	}

	public void setOpenerOpenedIcon() {
		closed = false;
		header.setIcon("opener_opened.png");
		header.setCursor(Cursor.HAND);
	}

	public void setOpenerClosedIcon() {
		closed = true;
		header.setIcon("opener_closed.png");
		header.setCursor(Cursor.HAND);
	}

	public VLayout getLayout() {
		return layout;
	}

	public void setLayout(VLayout layout) {
		this.layout = layout;
	}

	public boolean showTitleInSection() {
		return Layout.isAttivoRestyling();
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
		if(canvasLayout != null) {
			canvasLayout.destroy();
		}
		if(forms != null) {
			for(DynamicForm form : forms) {
				form.destroy();
			}
		}
		super.onDestroy();
	}
	
}
