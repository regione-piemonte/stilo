/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioDatiContabiliSIBWindow extends ModalWindow {
	
	protected DettaglioDatiContabiliSIBWindow window;
	protected DettaglioDatiContabiliSIBDetail detail;	
	protected ToolStrip detailToolStrip;
	
	public DettaglioDatiContabiliSIBWindow(ListaDatiContabiliSIBItem gridItem, String nomeEntita, Record record) {
		
		super(nomeEntita, false);
		
		if(record != null) {
			setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));
		}
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new DettaglioDatiContabiliSIBDetail(nomeEntita, gridItem);		
		detail.setHeight100();
		detail.setWidth100();
		
		setBody(detail);
		
		detail.editRecord(record);
		
		detail.setCanEdit(false);
		
		setIcon("blank.png");		
	}
	
	public String getTipoEstremiRecord(Record record) {
		
		String estremi = "";
		if(record.getAttribute("tipoDettaglio") != null && !"".equals(record.getAttribute("tipoDettaglio"))) {
			estremi += InvioDatiSpesaDetail.buildTipoDettaglioValueMap().get(record.getAttribute("tipoDettaglio")) + " ";
		}
		if(record.getAttribute("tipoDettaglio") != null && "SCP".equals(record.getAttribute("tipoDettaglio"))) {
			if(record.getAttribute("annoCrono") != null && !"".equals(record.getAttribute("annoCrono"))) {
				estremi += record.getAttribute("annoCrono") + " ";
			}	
			if(record.getAttribute("numeroCrono") != null && !"".equals(record.getAttribute("numeroCrono"))) {
				estremi += "N. " + record.getAttribute("numeroCrono") + " ";
			}
			if(record.getAttribute("subCrono") != null && !"".equals(record.getAttribute("subCrono"))) {
				if(Integer.parseInt(record.getAttribute("subCrono")) > 0) {
					estremi += "Sub. " + record.getAttribute("subCrono") + " ";
				}
			}
		} else {
			if(record.getAttribute("annoCompetenza") != null && !"".equals(record.getAttribute("annoCompetenza"))) {
				estremi += record.getAttribute("annoCompetenza") + " ";
			}	
			if(record.getAttribute("numeroDettaglio") != null && !"".equals(record.getAttribute("numeroDettaglio"))) {
				estremi += "N. " + record.getAttribute("numeroDettaglio") + " ";
			}
			if(record.getAttribute("subNumero") != null && !"".equals(record.getAttribute("subNumero"))) {
				if(Integer.parseInt(record.getAttribute("subNumero")) > 0) {
					estremi += "Sub. " + record.getAttribute("subNumero") + " ";
				}
			}
		}
		if(record.getAttribute("oggetto") != null && !"".equals(record.getAttribute("oggetto"))) {			
			if (record.getAttribute("oggetto").length() > 30){
				estremi += " - " + record.getAttribute("oggetto").substring(0, 30) + "...";
			} else {
				estremi += " - " + record.getAttribute("oggetto");		
			}
		}			
		return estremi;
	}
	
}
