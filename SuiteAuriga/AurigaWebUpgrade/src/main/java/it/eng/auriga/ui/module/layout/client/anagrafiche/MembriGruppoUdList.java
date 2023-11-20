/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author DANCRIST
 *
 */

public class MembriGruppoUdList extends CustomList {
	
	private ListGridField idRubrica;
	private ListGridField tipoSoggetto;
	private ListGridField denominazione;
	private ListGridField codRapido;
	private ListGridField codFiscalePIVA;
	private ListGridField indirizzo;	
	private ListGridField presaInCarico;	
	private ListGridField messAltPresaInCarico;
	
	
	public MembriGruppoUdList(String nomeEntita, String flgAssCondDest) {

		super(nomeEntita, false);	
		
		idRubrica = new ListGridField("idRubrica", "Id.");
		idRubrica.setHidden(true);
		idRubrica.setCanHide(false);

		messAltPresaInCarico = new ListGridField("messAltPresaInCarico");
		messAltPresaInCarico.setHidden(true);
		messAltPresaInCarico.setCanHide(false);

		
		tipoSoggetto = new ListGridField("tipoSoggetto", "Tipo"); // come in lista soggetti
		tipoSoggetto.setAttribute("custom", true);
		tipoSoggetto.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (value != null) {
					if ("#APA".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_APA_value();
					if ("#IAMM".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_IAMM_value();
					if ("UO;UOI".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_UOUOI_value();
					if ("UP".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_UP_value();
					if ("#AF".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_AF_value();
					if ("#AG".equals(value))
						return I18NUtil.getMessages().soggetti_tipo_AG_value();
				}
				return null;
			}
		});
		

		codRapido = new ListGridField("codRapido", "Cod. rapido");
		
		denominazione = new ListGridField("denominazione", "Denominazione");
		denominazione.setAttribute("custom", true);
		denominazione.setCellAlign(Alignment.LEFT);

		codFiscalePIVA = new ListGridField("codFiscalePIVA", "Cod. fiscale/P.IVA");
		
		indirizzo = new ListGridField("indirizzo", "Indirizzo"); // solo se destinatari
		if(flgAssCondDest != null && !"D".equals(flgAssCondDest)) {
			indirizzo.setHidden(true);
			indirizzo.setCanHide(false);
		}
		
		presaInCarico = new ListGridField("presaInCarico", "Presa in carico");
		presaInCarico.setType(ListGridFieldType.ICON);
		presaInCarico.setWidth(30);
		presaInCarico.setIconWidth(16);
		presaInCarico.setIconHeight(16);
		Map<String, String> flgTipoIcons = new HashMap<String, String>();		
		flgTipoIcons.put("1", "archivio/flgPresaInCarico/fatta.png");
		flgTipoIcons.put("0", "archivio/flgPresaInCarico/da_fare.png");
		presaInCarico.setValueIcons(flgTipoIcons);
		presaInCarico.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if("1".equals(record.getAttribute("presaInCarico"))) {
					//return I18NUtil.getMessages().archivio_list_flgPresaInCaricoField_0_value();
					return record.getAttribute("messAltPresaInCarico");
				} else if ("0".equals(record.getAttribute("presaInCarico"))) {
					return I18NUtil.getMessages().archivio_list_flgPresaInCaricoField_1_value();
				}				
				return null;
			}
		});
		
		if(flgAssCondDest != null && "A".equals(flgAssCondDest)) {
			setFields(
					idRubrica, 
					tipoSoggetto,
					codRapido,
					denominazione, 
					codFiscalePIVA,
					indirizzo,
					presaInCarico,
					messAltPresaInCarico
			);
		} else {
			setFields(
					idRubrica, 
					tipoSoggetto,
					codRapido,
					denominazione, 
					codFiscalePIVA,
					indirizzo
			);
		}
			
		
		
	}
	
	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {

		return true;
	}

	@Override
	protected boolean showDetailButtonField() {

		return false;
	}

	@Override
	protected boolean showModifyButtonField() {

		return false;
	}

	@Override
	protected boolean showDeleteButtonField() {

		return false;
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}