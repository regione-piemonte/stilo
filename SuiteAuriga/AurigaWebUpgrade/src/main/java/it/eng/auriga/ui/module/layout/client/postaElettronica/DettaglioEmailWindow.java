/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class DettaglioEmailWindow extends ModalWindow {

	private DettaglioEmailWindow _window;

	private String idEmail;
	private String idUd;

	/**
	 * Layout & Detail della maschera da cui provengo
	 */
	private CustomLayout _layoutPostaElettronica;

	private CustomDetail _detailPostaElettronica;

	private ToolStrip detailToolStrip;
	private ToolStrip detailToolStripRbuild;
	private VLayout vLayout=null;

	/**
	 * Detail della maschera corrente
	 */
	private CustomDetail portletLayout;

	public DettaglioEmailWindow(String idEmail, String tipoRel) {
		this(idEmail, tipoRel, null, null, null);
	}

	public DettaglioEmailWindow(String idEmail, String tipoRel, CustomLayout layoutPostaElettronica) {
		this(idEmail, tipoRel, null, null, null);
		_layoutPostaElettronica = layoutPostaElettronica;
	}

	public DettaglioEmailWindow(String idEmail, String tipoRel, String classifica, Record listRecord, Record abilitazioni, CustomLayout layoutPostaElettronica) {
		this(idEmail, tipoRel, classifica, listRecord, abilitazioni);
		_layoutPostaElettronica = layoutPostaElettronica;
	}

	public DettaglioEmailWindow(String idEmail, String tipoRel, CustomDetail detailPostaElettronica) {
		this(idEmail, tipoRel, null, null, null);
		_detailPostaElettronica = detailPostaElettronica;
	}

	public DettaglioEmailWindow(String idEmail, String tipoRel, String classifica, Record listRecord, Record abilitazioni, CustomDetail detailPostaElettronica) {
		this(idEmail, tipoRel, classifica, listRecord, abilitazioni);
		_detailPostaElettronica = detailPostaElettronica;
	}

	public DettaglioEmailWindow(String idEmail, String tipoRel, String classifica, Record listRecord, Record abilitazioni) {

		super("dettagliopostaelettronica", true);

		setTitle("Dettaglio e-mail");

		_window = this;
		this.idEmail = idEmail;
		setCanFocus(AurigaLayout.getIsAttivaAccessibilita());

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		Record lRecord = new Record();
		lRecord.setAttribute("idEmail", idEmail);

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			portletLayout = new PostaElettronicaDetail("dettagliopostaelettronica", tipoRel, classifica, listRecord, abilitazioni);

			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						portletLayout.editRecord(record);
						_window.setTitle("Dettaglio e-mail");
						if (!AurigaLayout.getIsAttivaAccessibilita()) {			
						_window.show();
					}
					}
					
					//Nascondo il popup (di caricamento dettaglio della mail in corso) aperto in PostaElettronicaWindow
					Layout.hideWaitPopup();
				}
			}, new DSRequest());
		} else {
			portletLayout = new DettaglioPostaElettronica("dettagliopostaelettronica", tipoRel, classifica, listRecord, null);
			((DettaglioPostaElettronica) portletLayout).setWindow(this);

			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						portletLayout.editRecord(record);
						if (!AurigaLayout.getIsAttivaAccessibilita()) {			
						_window.show();
					}
					}
					
					//Nascondo il popup (di caricamento dettaglio della mail in corso) aperto in PostaElettronicaWindow
					Layout.hideWaitPopup();
				}
			}, new DSRequest());
			if (AurigaLayout.getIsAttivaAccessibilita()) {
				vLayout = (VLayout)portletLayout.getMember("main_layout");
				detailToolStrip = (ToolStrip)vLayout.getMember("bottoni");
			} 
		}
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			detailToolStrip.markForDestroy();
			detailToolStrip.markForRedraw();
			addBackButton();
			
			portletLayout.setHeight(450);
			Canvas[] members = detailToolStrip.getMembers();
			for(Canvas singleButton : members) {
				singleButton.markForRedraw();
				detailToolStripRbuild.addMember(singleButton);
			}
			
			Label label = new Label();
			label.setContents("<h5 style=\"margin-left: 9px\">Azioni</h5>");
			label.setWidth100();
			label.setHeight(30);
			
			if (vLayout == null) {
				portletLayout.addMember(label);
				portletLayout.addMember(detailToolStripRbuild);			
			} else {
				vLayout.addMember(label);
				vLayout.addMember(detailToolStripRbuild);
				portletLayout.addMember(vLayout);
			} 
		}
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		if (AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			setShowHeaderIcon(false);
		} else {
			setIcon("mail/mail-reply2.png");
		}

		if (AurigaLayout.getIsAttivaAccessibilita()) {
			_window.show();		
		} 
	}
	
	private void addBackButton() {
//		detailToolStrip = new ToolStrip();
//		detailToolStrip.setWidth100();
//		detailToolStrip.setHeight(30);
//		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStripRbuild = new ToolStrip();
		detailToolStripRbuild.setName("bottoni");
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageOnCloseClick();
			}
		});
		
		detailToolStripRbuild.addButton(closeModalWindow,0);
		detailToolStripRbuild.addFill(); // push all buttons to the right
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	@Override
	public void manageOnCloseClick() {
		super.manageOnCloseClick();
		boolean controllo = false;
		if (portletLayout instanceof DettaglioPostaElettronica)
			controllo = ((DettaglioPostaElettronica) portletLayout).ricaricaPagina;
		else if (portletLayout instanceof PostaElettronicaDetail)
			controllo = ((PostaElettronicaDetail) portletLayout).verificaProtocollazione;
		if (_layoutPostaElettronica != null && controllo) {
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idEmail", idEmail);
			_layoutPostaElettronica.reloadListAndSetCurrentRecord(lRecordToLoad);
		}
		if (_detailPostaElettronica != null && controllo) {
			if (_detailPostaElettronica instanceof DettaglioPostaElettronica) {
				((DettaglioPostaElettronica) _detailPostaElettronica).reloadDetail();
			}
		}
	}
	
	
	public CustomLayout get_layoutPostaElettronica() {
		return _layoutPostaElettronica;
	}

	public void set_layoutPostaElettronica(CustomLayout _layoutPostaElettronica) {
		this._layoutPostaElettronica = _layoutPostaElettronica;
	}
	
	@Override
	public void show() {

		super.show();
		Layout.hideWaitPopup();
		if (UserInterfaceFactory.isAttivaAccessibilita()){
			focus();
		}
	}
}
