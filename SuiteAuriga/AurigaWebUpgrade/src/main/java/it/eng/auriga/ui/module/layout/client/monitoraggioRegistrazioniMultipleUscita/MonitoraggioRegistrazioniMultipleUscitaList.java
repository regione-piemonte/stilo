/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.monitoraggioRegistrazioniMultipleUscita;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class MonitoraggioRegistrazioniMultipleUscitaList extends CustomList {
	
	private ListGridField nroRichiesta;
	private ListGridField tsInvioRichiesta;
	private ListGridField userRichiesta;
	private ListGridField tipoRichiesta;
	private ListGridField statoRichiesta;
	private ListGridField nroRegistrazioniRichieste;
	private ListGridField nroRegistrazioniEffettuate;
	private ListGridField nroRegistrazioniDaTrasmettereViaMail;
	private ListGridField nroRegistrazioniTrasmesseViaMail;
	
	protected ControlListGridField downloadDestinatariRegistrazioniMancantiButtonField;
	protected ControlListGridField visualizzaRegistrazioniButtonField;

	public MonitoraggioRegistrazioniMultipleUscitaList(String nomeEntita) {
		
		super(nomeEntita);
		
		nroRichiesta = new ListGridField("nroRichiesta", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_nroRichiesta_title());
		nroRichiesta.setWrap(false);
		
		tsInvioRichiesta = new ListGridField("tsInvioRichiesta", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_tsInvioRichiesta_title());
		tsInvioRichiesta.setWrap(false);
		tsInvioRichiesta.setType(ListGridFieldType.DATE);
		tsInvioRichiesta.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		userRichiesta = new ListGridField("userRichiesta", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_userRichiesta_title());
		userRichiesta.setWrap(false);
		
		tipoRichiesta = new ListGridField("tipoRichiesta", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_tipoRichiesta_title());
		tipoRichiesta.setWrap(false);
		
		statoRichiesta = new ListGridField("statoRichiesta", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_statoRichiesta_title());
		statoRichiesta.setWrap(false);
		
		nroRegistrazioniRichieste = new ListGridField("nroRegistrazioniRichieste", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_nroRegistrazioniRichieste_title());
		nroRegistrazioniRichieste.setWrap(false);
		nroRegistrazioniRichieste.setType(ListGridFieldType.INTEGER);
		
		nroRegistrazioniEffettuate = new ListGridField("nroRegistrazioniEffettuate", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_nroRegistrazioniEffettuate_title());
		nroRegistrazioniEffettuate.setWrap(false);
		nroRegistrazioniEffettuate.setType(ListGridFieldType.INTEGER);
		
		nroRegistrazioniDaTrasmettereViaMail = new ListGridField("nroRegistrazioniDaTrasmettereViaMail", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_nroRegistrazioniDaTrasmettereViaMail_title());
		nroRegistrazioniDaTrasmettereViaMail.setWrap(false);
		nroRegistrazioniDaTrasmettereViaMail.setType(ListGridFieldType.INTEGER);
		
		nroRegistrazioniTrasmesseViaMail = new ListGridField("nroRegistrazioniTrasmesseViaMail", I18NUtil.getMessages().monitoraggio_registrazioni_multiple_uscita_list_nroRegistrazioniTrasmesseViaMail_title());
		nroRegistrazioniTrasmesseViaMail.setWrap(false);
		nroRegistrazioniTrasmesseViaMail.setType(ListGridFieldType.INTEGER);
		
		setFields(new ListGridField[] {
				nroRichiesta,
				tsInvioRichiesta,
				userRichiesta,
				tipoRichiesta,
				statoRichiesta,
				nroRegistrazioniRichieste,
				nroRegistrazioniEffettuate,
				nroRegistrazioniDaTrasmettereViaMail,
				nroRegistrazioniTrasmesseViaMail
			});
	}

}
