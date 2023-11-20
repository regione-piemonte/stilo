/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.Layout;

public class SezioneContenuti extends VLayout {

	private ImgButton header;
	private VLayout headerLayout;
	private VLayout layout;
	private VLayout sectionLayout;

	private String sectionTitle;
	private boolean canCollapse;
	private boolean showOpen;
	private Canvas[] contents;
	
	public SezioneContenuti(String pTitle, final boolean pCanCollapse, boolean pShowOpen, final Canvas... pContents) {
		
		contents = pContents;
		
		sectionTitle = pTitle;
		canCollapse = pCanCollapse;
		showOpen = pShowOpen;
		
		setOverflow(Overflow.VISIBLE);		
		
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
		
		if(canCollapse) {
			if(showOpen) {
				header.setIcon("opener_opened.png");
			} else {
				header.setIcon("opener_closed.png");
			}	
		}

		setTitle(sectionTitle);
		
		setHeight(10);
		setWidth100();
		
		if(showTitleInSection()) {
			
			layout = new VLayout();
			layout.setPadding(11);			
			layout.setRedrawOnResize(true);

			setContents(contents);
			
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
			
			setContents(contents);
			
			addMember(layout);
		
		}
				
		if(canCollapse && !showOpen) {
			hideLayout();		
		} else {
			showLayout();
		}		
		
	}
	
	public void showLayout() {
		layout.show();
		headerLayout.bringToFront();
	}

	public void hideLayout() {
		layout.hide();
		headerLayout.bringToFront();
	}
	
	public void buildHeader() {
		
		header = new ImgButton();
		header.setCanFocus(false);
		header.setTabIndex(-1);
    	header.addClickHandler(new ClickHandler() {				
			@Override
			public void onClick(ClickEvent event) {
				if(canCollapse) {
					((ImgButton)event.getSource()).bringToFront();
					if(layout.isVisible()) {
						close();
					} else {
						open();
					}				
				}
			}			
		});		
    	
    	if(canCollapse) {
			header.setIconHeight(22);
			header.setIconWidth(22);			
			header.setShowDownIcon(false);
			header.setShowRollOverIcon(false);			
			header.setIconOrientation("left");
			header.setIconSpacing(0);			
			header.setCursor(Cursor.HAND);
		} else {
			header.setCursor(Cursor.ARROW);
		}
		header.setSrc("blank.png");		
		header.setHeight(8);	
		header.setShowDown(false);
		header.setShowRollOver(false);
		header.setAutoFit(true);
		header.setLeft(8);
		header.setShowTitle(true);
		header.setAlign(Alignment.LEFT);
		header.bringToFront();
	}
	
	public void setContents(Canvas... cantents) {
		layout.setMembers(contents);	
		for(Canvas canvas : contents) {			
			canvas.bringToFront();
		}
		layout.markForRedraw();
	}
	
	@Override
	public void setTitle(String title) {
		if(!canCollapse) { 
			title = "&nbsp;&nbsp;" + title;
		}
		header.setTitle("<span class='"+ getHeaderTitleStyle()+"'>"+title+"&nbsp;&nbsp;</span>");
	}
	
	public String getHeaderTitleStyle() {
		return it.eng.utility.Styles.headerDetailSectionTitle;
	}
	
	public void open(){
		header.setIcon("opener_opened.png");
		showLayout();
	}
	
	public void close(){
		header.setIcon("opener_closed.png");
		hideLayout();
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
		if(contents != null) {
			for(Canvas content : contents) {
				content.destroy();
			}
		}
		super.onDestroy();
	}
	
}