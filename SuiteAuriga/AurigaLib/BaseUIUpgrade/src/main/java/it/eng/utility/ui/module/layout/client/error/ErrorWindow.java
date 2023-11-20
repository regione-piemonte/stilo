/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.IconClickEvent;
import com.smartgwt.client.widgets.events.IconClickHandler;

import it.eng.utility.ui.module.layout.shared.util.ExceptionUtil;

public class ErrorWindow extends Dialog {

	public ErrorWindow errorWindow;

	private StackTraceDetail stackTraceDetail;
	private Label errorwindowlabel;

	public ErrorWindow(Record record) {

		buildWindow();
		errorwindowlabel.setContents(record.getAttribute("errorMessage"));
		stackTraceDetail = new StackTraceDetail();
		String htmlStackTrace = record.getAttribute("htmlStackTrace");
		stackTraceDetail.getErrorWindowStackTrace().setContents(htmlStackTrace != null ? htmlStackTrace.replaceAll("\\n", "<br/>") : null);
	}

	public ErrorWindow(Throwable pThrowable) {
		
		buildWindow();
		errorwindowlabel.setContents(pThrowable.getMessage() != null && !"".equals(pThrowable.getMessage()) ? pThrowable.getMessage() : "Errore generico");
		stackTraceDetail = new StackTraceDetail();
		String htmlStackTrace = new ExceptionUtil().getStackTrace(pThrowable);
		stackTraceDetail.getErrorWindowStackTrace().setContents(htmlStackTrace != null ? htmlStackTrace.replaceAll("\\n", "<br/>") : null);
	}

	private void buildWindow() {
		
		errorWindow = this;
		
		setTitle("Errore");
		setAutoCenter(true);
		setWidth(400);
		setHeight(110);
		setAutoDraw(true);
		setAutoSize(true);
		setShowStatusBar(false);
		setShowToolbar(false);
		setFooterHeight(0);
		setDefaultLayoutAlign(Alignment.CENTER);
		setAlign(Alignment.CENTER);		
		
		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				manageOnCloseWindow();
			}
		});

		errorwindowlabel = new Label();
		errorwindowlabel.setHeight(40);
		errorwindowlabel.setWidth("*");
		errorwindowlabel.setWrap(false);
		errorwindowlabel.setContents("");
		errorwindowlabel.setIconSpacing(10);
		errorwindowlabel.setLayoutAlign(Alignment.CENTER);
		errorwindowlabel.setIcon("[SKIN]Dialog/warn.png");
		errorwindowlabel.setIconSize(32);
		errorwindowlabel.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				// Federico Cacco 04.05.2016
				// Mostro la finestra solamente se il contenuto è una stringa non vuota
				String contents = stackTraceDetail.getErrorWindowStackTrace() != null ? stackTraceDetail.getErrorWindowStackTrace().getContents() : null;
				if (contents != null && !"".equalsIgnoreCase(contents.replace("&nbsp;", "").trim())) {
					stackTraceDetail.show();
				}
			}
		});

		Button okButton = new Button();
		okButton.setTitle("OK");
		okButton.setLayoutAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageOnCloseWindow();

			}
		});
		
		addItem(errorwindowlabel);
		addItem(okButton);
	}

	public void manageOnCloseWindow() {
		errorWindow.markForDestroy();
		stackTraceDetail.markForDestroy();
	}

}
