/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

/**
 * 
 * @author matzanin
 *
 */

public class VerificaRaffinamentoCampioniLayout extends CustomLayout {
	
	private MultiToolStripButton nuovoCampionamentoMultiButton;
	
	public VerificaRaffinamentoCampioniLayout() {
		
		super("verificaRaffinamentoCampioni", 
			  new GWTRestDataSource("VerificaRaffinamentoCampioniDataSource", "codiceAttoStruttura", FieldType.TEXT), 
			  new VerificaRaffinamentoCampioniFilter("verificaRaffinamentoCampioni"),
			  new VerificaRaffinamentoCampioniList("verificaRaffinamentoCampioni"), 
			  new CustomDetail("verificaRaffinamentoCampioni"), 
			  null,
			  null, 
			  null);
		
		setMultiselect(true);
		newButton.hide();			
	}
	
	@Override
	public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
		filter.setCriteria(((VerificaRaffinamentoCampioniFilter)filter).getDefaultCriteria());
		firstSearch(autosearch, true);
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		if (nuovoCampionamentoMultiButton == null) {
			nuovoCampionamentoMultiButton = new MultiToolStripButton("menu/campioniAtti.png", this, "Nuovo campionamento", false) {

				@Override
				public void doSomething() {
					
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					
					AdvancedCriteria criteria = filter.getCriteria(true);
					Date dataInizioPeriodoVerifica = null;
					Date dataFinePeriodoVerifica = null;
					String tipoRaggruppamento = null;
					if (criteria != null && criteria.getCriteria() != null){
						for (Criterion lCriterion : criteria.getCriteria()) {	
							if("periodoVerifica".equals(lCriterion.getFieldName())) {     
								dataInizioPeriodoVerifica = lCriterion.getAttributeAsDate("start");
								dataFinePeriodoVerifica = lCriterion.getAttributeAsDate("end");
							} 
							else if("tipoRaggruppamento".equals(lCriterion.getFieldName())) {   
								tipoRaggruppamento = lCriterion.getValueAsString();
							} 
						}
					}	
					
					final Record nuovoCampionamentoRecord = new Record();
					nuovoCampionamentoRecord.setAttribute("listaRecord", listaRecord);
					nuovoCampionamentoRecord.setAttribute("dataInizioPeriodoVerifica", dataInizioPeriodoVerifica);
					nuovoCampionamentoRecord.setAttribute("dataFinePeriodoVerifica", dataFinePeriodoVerifica);
					nuovoCampionamentoRecord.setAttribute("tipoRaggruppamento", tipoRaggruppamento);
					
					NuovoCampionamentoDaVerificaPopup lNuovoCampionamentoDaVerificaPopup = new NuovoCampionamentoDaVerificaPopup(new ServiceCallback<String>() {
						
						@Override
						public void execute(String percentualeCampionamento) {
							if(percentualeCampionamento != null && !"".equals(percentualeCampionamento)) {
								nuovoCampionamentoRecord.setAttribute("percentualeCampionamento", percentualeCampionamento);
								new GWTRestDataSource("VerificaRaffinamentoCampioniDataSource").executecustom("nuovoCampionamento", nuovoCampionamentoRecord, new DSCallback() {
									
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean("Nuovo campionamento effettuato con successo", "", MessageType.INFO));
											doSearch();
										}
									}
								});							
							}
						}
					});
					lNuovoCampionamentoDaVerificaPopup.show();					
				}
			};
		}
		
		return new MultiToolStripButton[] { nuovoCampionamentoMultiButton };
	}
	
}
