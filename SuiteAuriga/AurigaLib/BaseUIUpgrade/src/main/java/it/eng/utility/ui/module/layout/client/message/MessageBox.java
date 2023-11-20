/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.WaitingMessageCallback;
/**
 * Classe message box per la gestione dei messaggi di errore
 * @author michele
 *
 */
public class MessageBox extends HStack {
	
	private boolean showMessageBoxStack;
	
	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	private List<String> infos = new ArrayList<String>();
	
	private VStack stack;
//	private HStack messageBoxStack;
	
	private Label errorslabel;
	private Label warningslabel;
	private Label infoslabel;

	public MessageBox() {
		this(true);
	}
	
	public MessageBox(boolean showMessageBoxStack) {
		
		this.showMessageBoxStack = showMessageBoxStack;
		
		setSize("120", "25");
		setRight(0);
		
		//Creo una 
		stack = new VStack();
		stack.setPosition(Positioning.ABSOLUTE);		
		stack.setWidth(500);
		stack.setAutoHeight();
		stack.setMinHeight(20);
		stack.setTop(2);
		stack.setLeft((Page.getWidth()/2)-250);
		stack.show();
				
		if(showMessageBoxStack) {
			errorslabel = new Label("0");
	 		if (UserInterfaceFactory.isAttivaAccessibilita()){
				errorslabel.setTabIndex(-1);
				errorslabel.setCanFocus(false);	
	 		}
			errorslabel.setStyleName(it.eng.utility.Styles.messageBoxLabel);
			errorslabel.setIcon("message/error.png");
			errorslabel.setWidth(40);
			errorslabel.setHoverWidth(450);  
			errorslabel.setHoverStyle(it.eng.utility.Styles.errorhover);
			errorslabel.setPrompt("<div style=\"margin:5px;\"><img src=\"images/message/error.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;<i>Non ci sono messaggi di errore</i></div>");
//			errorslabel.setCursor(Cursor.HAND);
			addMember(errorslabel); //da togliere nel caso si utilizzi messageBoxStack
			
			warningslabel = new Label("0");
	 		if (UserInterfaceFactory.isAttivaAccessibilita()){
				warningslabel.setTabIndex(-1);
				warningslabel.setCanFocus(false);
	 		}
			warningslabel.setStyleName(it.eng.utility.Styles.messageBoxLabel);
			warningslabel.setIcon("message/warning.png");
			warningslabel.setWidth(40);
			warningslabel.setHoverWidth(450);  
			warningslabel.setHoverStyle(it.eng.utility.Styles.warninghover);  		
			warningslabel.setPrompt("<div style=\"margin:5px;\"><img src=\"images/message/warning.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;<i>Non ci sono messaggi di warning</i></div>");
//			warningslabel.setCursor(Cursor.HAND);
			addMember(warningslabel); //da togliere nel caso si utilizzi messageBoxStack
			
			infoslabel = new Label("0");
	 		if (UserInterfaceFactory.isAttivaAccessibilita()){
				infoslabel.setTabIndex(-1);
				infoslabel.setCanFocus(false);
	 		}
			infoslabel.setStyleName(it.eng.utility.Styles.messageBoxLabel);
			infoslabel.setIcon("message/information.png");
			infoslabel.setWidth(40);
			infoslabel.setHoverWidth(450);  
			infoslabel.setHoverStyle(it.eng.utility.Styles.infohover);
			infoslabel.setPrompt("<div style=\"margin:5px;\"><img src=\"images/message/information.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;<i>Non ci sono messaggi informativi</i></div>");
//			infoslabel.setCursor(Cursor.HAND);
			addMember(infoslabel); //da togliere nel caso si utilizzi messageBoxStack
				
//			messageBoxStack = new HStack();
//			messageBoxStack.setPosition(Positioning.ABSOLUTE);
//			messageBoxStack.bringToFront();
//			messageBoxStack.setWidth(100);
//			messageBoxStack.setHeight(25);
//			messageBoxStack.setTop(Page.getHeight()-25);
//			messageBoxStack.setLeft(Page.getWidth()-120);		
//			messageBoxStack.addMember(errorslabel);
//			messageBoxStack.addMember(warningslabel);
//			messageBoxStack.addMember(infoslabel);
//			messageBoxStack.show();
		}
	}
	
	public void addMessages(List<MessageBean> messages){
		addMessages(messages, null, null, null, null);
	}
	
	public void addMessages(List<MessageBean> messages, DSCallback callback, DSResponse dsResponse, Object data, DSRequest dsRequest){
		for (int i = 0; i < messages.size(); i++) {
			MessageBean message = messages.get(i);			
			switch (message.getType()) {
				case ERROR:
					Layout.hideWaitPopup();
					errors.add(0, getDatePrefix() + message.getMessage());
					if(errors.size()>10){
						errors.remove(10);						
					}
					break;				
				case WARNING:
				case WARNING_NO_POPUP:						
					warnings.add(0, getDatePrefix() + message.getMessage());
					if(warnings.size()>10){
						warnings.remove(10);						
					}
					break;					
				case INFO:
					infos.add(0, getDatePrefix() + message.getMessage());
					if(infos.size()>10){
						infos.remove(10);						
					}
					break;					
				default:
					break;
			}			
			// Se ho più messaggi in blocco la callback deve essere eseguita solo dall'ultimo messaggio
			if (i == messages.size() - 1) {
				viewMessages(message, callback, dsResponse, data, dsRequest);
			} else {
			viewMessages(message);
		}				
		}				
		//Aggiorno la lista dei messaggi nel prompt
		if(showMessageBoxStack) {
			refreshInfoPrompt();
			refreshWarningPrompt();
			refreshErrorPrompt();
//			messageBoxStack.setTop(Page.getHeight()-25);
//			messageBoxStack.setLeft(Page.getWidth()-120);	
//			messageBoxStack.bringToFront();
		}
	}

	public void addWaitingMessage(MessageBean message, final int waitingTime, final WaitingMessageCallback callback) {
		switch (message.getType()) {
			case ERROR:
				Layout.hideWaitPopup();
				errors.add(0, getDatePrefix() + message.getMessage());
				if(errors.size()>10){
					errors.remove(10);						
				}
				break;		
			case WARNING:
			case WARNING_NO_POPUP:					
				warnings.add(0, getDatePrefix() + message.getMessage());
				if(warnings.size()>10){
					warnings.remove(10);						
				}
				break;				
			case INFO:
				infos.add(0, getDatePrefix() + message.getMessage());
				if(infos.size()>10){
					infos.remove(10);						
				}
				break;				
			default:
				break;
		}
		viewMessages(message, waitingTime, callback);					
		//Aggiorno la lista dei messaggi nel prompt
		if(showMessageBoxStack) {
			refreshInfoPrompt();
			refreshWarningPrompt();
			refreshErrorPrompt();
//			messageBoxStack.setTop(Page.getHeight()-25);
//			messageBoxStack.setLeft(Page.getWidth()-120);	
//			messageBoxStack.bringToFront();			
		}
	}
	
	private String getDatePrefix() {
		Date now = new Date();
		DateTimeFormat dtf = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
		return dtf.format(now) + " - ";	
	}
	
//	private void viewMessages(final Label messagelabel){
//		viewMessages(messagelabel, null, null);
//	}
	
//	protected void viewMessages(MessageBean message, DSCallback callback, DSResponse dsResponse, Object data, DSRequest dsRequest){
//		viewMessages(message, null, callback, dsResponse, data, dsRequest);
//	}
	
//	private void viewMessages(final Label messagelabel, final Integer waitingTime, final WaitingMessageCallback callback){
//		
//		messagelabel.show();
//		
//		stack.addMember(messagelabel);		
//		stack.setLeft((Page.getWidth()/2)-250);
//		stack.bringToFront();
//		
//		messagelabel.bringToFront();
//		messagelabel.setWidth100();		
//		messagelabel.markForRedraw();
//		final Label messagelabelFinal = messagelabel;
//		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand(){
//				public boolean execute() {
//					messagelabel.animateRect(Page.getScreenWidth(), 10 , null, null, new AnimationCallback() {
//					public void execute(boolean earlyFinish) {
//						//Distruggo il messaggio
//						messagelabelFinal.destroy();
//						stack.sendToBack();
//						if(callback != null) {
//							callback.execute();
//						}
//					}
//				}, (waitingTime != null ? waitingTime : 1000));  			
//					return false;
//				}
//		}, 5000);		 
//	}
	
	protected void viewMessages(MessageBean message){
		viewMessages(message, null, null, null, null, null, null);
	}
	
	protected void viewMessages(MessageBean message, final Integer waitingTime, final WaitingMessageCallback callback){
		viewMessages(message, waitingTime, callback, null, null, null, null);
	}
	
	protected void viewMessages(MessageBean message, DSCallback callback, DSResponse dsResponse, Object data, DSRequest dsRequest){
		viewMessages(message, null, null, callback, dsResponse, data, dsRequest);
	}
	
	protected void viewMessages(MessageBean message, final Integer waitingTime, final WaitingMessageCallback waitingMessageCallback, final DSCallback dsCallback, final DSResponse dsResponse, final Object data, final DSRequest dsRequest){
		
		final Label messagelabel = new Label(message.getMessage());
		messagelabel.setAlign(Alignment.CENTER);   
		messagelabel.setValign(VerticalAlignment.CENTER);				
		messagelabel.setMinHeight(28);
		messagelabel.setHeight100();
		messagelabel.setWidth100();		
		switch (message.getType()) {
			case ERROR:
				messagelabel.setBaseStyle(it.eng.utility.Styles.errorhover);
				messagelabel.setContents("&nbsp;&nbsp;<img src=\"images/message/error.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;" + messagelabel.getContents());				
				break;			
			case WARNING:
			case WARNING_NO_POPUP:				
				messagelabel.setBaseStyle(it.eng.utility.Styles.warninghover);
				messagelabel.setContents("&nbsp;&nbsp;<img src=\"images/message/warning.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;" + messagelabel.getContents());				
				break;				
			case INFO:
				messagelabel.setBaseStyle(it.eng.utility.Styles.infohover);
				messagelabel.setContents("&nbsp;&nbsp;<img src=\"images/message/information.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;" + messagelabel.getContents());				
				break;
			default:
				break;
		}		
		messagelabel.show();
		messagelabel.setCanFocus(true);
		messagelabel.focus();
		
		stack.addMember(messagelabel);		
		stack.setLeft((Page.getWidth()/2)-250);
		stack.bringToFront();
		
		messagelabel.bringToFront();
		messagelabel.setWidth100();		
		messagelabel.markForRedraw();
		
		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand(){
				public boolean execute() {
					messagelabel.animateRect(Page.getScreenWidth(), 10 , null, null, new AnimationCallback() {
					public void execute(boolean earlyFinish) {
						//Distruggo il messaggio
						messagelabel.destroy();
						stack.sendToBack();
						if(waitingMessageCallback != null) {
							waitingMessageCallback.execute();
						}
						if(dsCallback != null) {
							dsCallback.execute(dsResponse, data, dsRequest);
						}
					}
				}, (waitingTime != null ? waitingTime : 1000));  			
					return false;
				}
		}, 5000);		 
	}
	
	private void refreshInfoPrompt(){
		if(infos.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<div style=\"margin:5px;\"><img src=\"images/message/information.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;<i>Ultimi messaggi informativi:</i></div>");
			buffer.append("<ul>");
			for(String info:infos){
				buffer.append("<li>"+info+"</li>");			
			}
			buffer.append("</ul>");
			infoslabel.setPrompt(buffer.toString());
			infoslabel.setMargin(2);
			infoslabel.setHoverWrap(true);
			infoslabel.setHoverWidth(450);
			infoslabel.setContents(infos.size()+"");
		} 
	}
	
	private void refreshWarningPrompt(){
		if(warnings.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<div style=\"margin:5px;\"><img src=\"images/message/warning.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;<i>Ultimi messaggi di warning:</i></div>");
			buffer.append("<ul>");
			for(String warning:warnings){
				buffer.append("<li>"+warning+"</li>");			
			}
			buffer.append("</ul>");
			warningslabel.setPrompt(buffer.toString());
			warningslabel.setMargin(2);
			warningslabel.setHoverWrap(true);
			warningslabel.setHoverWidth(450);
			warningslabel.setContents(warnings.size()+"");
		}
	}
	
	private void refreshErrorPrompt(){
		if(errors.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<div style=\"margin:5px;\"><img src=\"images/message/error.png\" height=\"20\" width=\"20\" align=\"absmiddle\" />&nbsp;&nbsp;<i>Ultimi messaggi di errore:</i></div>");
			buffer.append("<ul>");	
			for(String error:errors){
				buffer.append("<li>"+error+"</li>");
			}
			buffer.append("</ul>");
			errorslabel.setPrompt(buffer.toString());
			errorslabel.setMargin(2);
			errorslabel.setHoverWrap(true);
			errorslabel.setHoverWidth(450);
			errorslabel.setContents(errors.size()+"");
			errorslabel.markForRedraw();
		}
	}
	
	public void redrawMessageBoxStack() {
		if(showMessageBoxStack) {
//			messageBoxStack.hide();
//			messageBoxStack.setTop(Page.getHeight()-25);
//			messageBoxStack.setLeft(Page.getWidth()-120);	
//			messageBoxStack.show();
		}
	}
	
	public void destroyMessageBoxLabels() {
		errorslabel.destroy();
		warningslabel.destroy();
		infoslabel.destroy();
	}
	
	public List<String> getErrors() {
		return errors;
	}

	
	public List<String> getWarnings() {
		return warnings;
	}

	
	public List<String> getInfos() {
		return infos;
	}

	
	public Label getErrorslabel() {
		return errorslabel;
	}

	
	public Label getWarningslabel() {
		return warningslabel;
	}

	
	public Label getInfoslabel() {
		return infoslabel;
	}

}
