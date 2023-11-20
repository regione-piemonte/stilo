/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;

/**
 * Popup che disegna una progress bar e la aggiorna ogni 50 ms
 * andando a verificare sulla session lo stato di avanzamento dell'upload
 * e se ha eventualmente finito.
 * 
 * L'interrogazione della session avviene mediante l'apposita datasource
 * @author Rametta
 *
 */
public class ProgressBarWindow extends Window {

	final ProgressBarWindow _window;

	private VLayout bar = new VLayout();

	private Label hBar1Label = new Label(); 
	private int hBar1Value = 0;  
	private Progressbar hBar1 = new Progressbar();  
	private Label preparazioneUploadLabel = new Label();
	private Timer timer;
	private boolean end;

	public boolean cancelUpload = false;

	/**
	 * Crea la ProgressBarWindow
	 */
	public ProgressBarWindow() {
		_window = this;
		setShowTitle(true);	
		setTitle(I18NUtil.getMessages().progressBarWindow_title());
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(110);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		hBar1.setHeight(10);  		
		hBar1.setLength(383); //larghezza della barra di progressione
		hBar1.setVertical(false);  
		hBar1.setAlign(Alignment.CENTER);
		bar.setAlign(Alignment.CENTER);
		bar.setWidth100();
		bar.setHeight(50);
		bar.setLayoutAlign(Alignment.CENTER);
		bar.setLayoutTopMargin(15);
		preparazioneUploadLabel.setIcon("file/" + "wait.gif");
		bar.addMember(preparazioneUploadLabel);
		bar.addMember(hBar1);
		bar.addMember(hBar1Label);
		bar.setVisible(true);
		bar.setPadding(5);
		addItem(bar);
		creaTimer("");
		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				cancelUpload = true;
				destroyMyself(true);
				GWTRestDataSource.printMessage(new MessageBean(I18NUtil.getMessages().progressBarWindow_interrupt_message(), 
						I18NUtil.getMessages().progressBarWindow_interrupt_message(), MessageType.WARNING));
			}
		});
	}

	@Override
	public void destroy() {
		hBar1Value = 100;
		hBar1.setPercentDone(hBar1Value);  
		hBar1Label.setContents(hBar1Value+"%");
		new Timer() {

			@Override
			public void run() {
				_window.destroyMyself(false);
			}  

		}.schedule(500);
	}
	
	public void destroyAfterError() {
		if(timer != null) {
			timer.cancel();
		}
		end = true;
		finalDestroy();
	}
	
	private void finalDestroy(){
		super.destroy();
	}

	private void destroyMyself(boolean isUploadCancellato){
		final ProgressBarWindow instance = this;
		Record lRecord = new Record();
		lRecord.setAttribute("finish", true);
		if(isUploadCancellato) {
			lRecord.setAttribute("uploadCancellato", true);
		}
		OneCallGWTRestService<Record, Record> lOneCallUploadStatusDataSource = new OneCallGWTRestService<Record, Record>("UploadStatusDataSource");
		lOneCallUploadStatusDataSource.setShowPrompt(false);
		lOneCallUploadStatusDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				if(timer != null) {
					timer.cancel();
				}
				end = true;
				instance.finalDestroy();	
			}		
		});		
	}

	protected void creaTimer(String value) {
		
		bar.setVisible(true);
		hBar1Value = 0;    
		hBar1.setPercentDone(hBar1Value);  

		hBar1Label.setHeight(16);  
		hBar1Label.setContents(hBar1Value + "%");  
		
		preparazioneUploadLabel.setHeight(16);  
		preparazioneUploadLabel.setContents("Verifica in corso. Attendere….");

		timer = new Timer() {  
			public void run() {  
				if (!end){
					Record lRecord = new Record();
					OneCallGWTRestService<Record, Record> lOneCallUploadStatusDataSource = new OneCallGWTRestService<Record, Record>("UploadStatusDataSource");
					lOneCallUploadStatusDataSource.setShowPrompt(false);
					lOneCallUploadStatusDataSource.call(lRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							if (!end){
								hBar1Value = object.getAttributeAsInt("caricato");
//								System.out.println("Carico il valore " + hBar1Value);
//								if (hBar1Value > 0) {
//									preparazioneUploadLabel.setVisible(false);
//								}
								if (hBar1Value >= 95) {  
									hBar1Value = 95; 
								}
								if (object.getAttributeAsBoolean("finito")){
									hBar1Value = 100;
								} 
								hBar1.setPercentDone(hBar1Value);  
								hBar1Label.setContents(hBar1Value+"%");
								if(hBar1Value!=100 && !end)  
									schedule(50);
								else timer.cancel();
							}
						}			
					});
				} 
			}
		};
		timer.schedule(50);
	}
}
