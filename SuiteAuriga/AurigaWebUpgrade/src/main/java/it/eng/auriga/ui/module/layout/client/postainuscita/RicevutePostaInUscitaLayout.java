/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;

public class RicevutePostaInUscitaLayout extends CustomLayout {	
			
	public RicevutePostaInUscitaLayout(GWTRestDataSource pGWTRestDataSource) {
		super("ricevutepostainuscita", 
				pGWTRestDataSource,  
				null, 
				new RicevutePostaInUscitaList("ricevutepostainuscita") ,
				switchDetailPostaElettronica()
		);

		multiselectButton.hide();	
		newButton.hide();		
		refreshListButton.hide();
		topListToolStripSeparator.hide();
		
		this.setLookup(false);
	}
	
	private static CustomDetail switchDetailPostaElettronica(){
		
		CustomDetail portletLayout = null;
		
		if(!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			portletLayout = new PostaElettronicaDetail("ricevutepostainuscita");	
		}else if(AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")){
			portletLayout = new DettaglioPostaElettronica("ricevutepostainuscita");
		}else{
			portletLayout = new CustomDetail("ricevutepostainuscita");
		}
		return portletLayout;
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().ricevutepostainuscita_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().ricevutepostainuscita_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().ricevutepostainuscita_detail_view_title(getTipoEstremiRecord(record));		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();		
		deleteButton.hide();
		editButton.hide();
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		
		final Record newRecord = new Record();

		// TODO ......
		return newRecord;
	}
	
}
