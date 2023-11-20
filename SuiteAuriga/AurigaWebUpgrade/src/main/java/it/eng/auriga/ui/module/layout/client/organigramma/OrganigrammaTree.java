/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.classifiche.ClassificheWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.registriNumerazione.RegistriNumerazioneWindow;
import it.eng.auriga.ui.module.layout.client.tipoFascicoliAggr.TipologieFascicoliWindows;
import it.eng.auriga.ui.module.layout.client.tipologieDocumentali.TipologieDocumentaliWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTree;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;

public class OrganigrammaTree extends CustomAdvancedTree {

	public OrganigrammaTree(String nomeEntita) {

		super(nomeEntita);

		TreeGridField idFolder = new TreeGridField("idFolder");
		idFolder.setHidden(true);

		TreeGridField idUoSvUt = new TreeGridField("idUoSvUt");
		idUoSvUt.setHidden(true);

		TreeGridField nroLivello = new TreeGridField("nroLivello");
		nroLivello.setType(ListGridFieldType.INTEGER);
		nroLivello.setHidden(true);

		TreeGridField tipo = new TreeGridField("tipo");
		tipo.setHidden(true);

		TreeGridField codRapidoUo = new TreeGridField("codRapidoUo");
		codRapidoUo.setHidden(true);

		TreeGridField nroProgr = new TreeGridField("nroProgr");
		nroProgr.setType(ListGridFieldType.INTEGER);
		nroProgr.setHidden(true);

		TreeGridField nome = new TreeGridField("nome");
		nome.setShowHover(true);
		nome.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "<b>" + record.getAttributeAsString("nome") + "</b><br/>" + record.getAttributeAsString("dettagli");
			}
		});
		
		TreeGridField flgSelXAssegnazione = new TreeGridField("flgSelXAssegnazione");
		flgSelXAssegnazione.setHidden(true);

//		TreeGridField flgPuntoProtocollo = new TreeGridField("flgPuntoProtocollo");
//		flgPuntoProtocollo.setWidth(30);
//		flgPuntoProtocollo.setCellFormatter(new CellFormatter() {
//
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
//					return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}
//				return null;
//			}
//		});
//		flgPuntoProtocollo.setHoverCustomizer(new HoverCustomizer() {
//
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
//					return "Punto di Protocollo";
//				}
//				return null;
//			}
//		});
		
		
		
		
//		TreeGridField tipoRelUtenteUo = new TreeGridField("tipoRelUtenteUo");
//		tipoRelUtenteUo.setWidth(30);
//		tipoRelUtenteUo.setCellFormatter(new CellFormatter() {
//
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if (record.getAttribute("tipoRelUtenteUo") != null && "A".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					return "<div align=\"center\"><img src=\"images/organigramma/tipoRelazioneUtenteUO/A.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}else if (record.getAttribute("tipoRelUtenteUo") != null && "D".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					return "<div align=\"center\"><img src=\"images/organigramma/tipoRelazioneUtenteUO/D.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}else if (record.getAttribute("tipoRelUtenteUo") != null && "L".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					return "<div align=\"center\"><img src=\"images/organigramma/tipoRelazioneUtenteUO/L.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}
//				return null;
//			}
//		});
//		tipoRelUtenteUo.setHoverCustomizer(new HoverCustomizer() {
//
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if (record.getAttribute("tipoRelUtenteUo") != null && "A".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					return I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_A_prompt();
//				}else if (record.getAttribute("tipoRelUtenteUo") != null && "D".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					return I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_D_prompt(); 
//				}else if (record.getAttribute("tipoRelUtenteUo") != null && "L".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					return I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_L_prompt();
//				}
//				return null;
//			}
//		});
		
		
		TreeGridField iconeRiga = new TreeGridField("iconeRiga");
		iconeRiga.setWidth(60);
		iconeRiga.setCellFormatter(new CellFormatter() {

//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				String format = "";
//				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT") && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
//					format += "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"Punto di Protocollo\" /></div>";
//				}
//				if (record.getAttribute("tipoRelUtenteUo") != null && "A".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					format += "<div align=\"center\"><img src=\"images/organigramma/tipoRelazioneUtenteUO/A.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}else if (record.getAttribute("tipoRelUtenteUo") != null && "D".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					format += "<div align=\"center\"><img src=\"images/organigramma/tipoRelazioneUtenteUO/D.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}else if (record.getAttribute("tipoRelUtenteUo") != null && "L".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
//					format += "<div align=\"center\"><img src=\"images/organigramma/tipoRelazioneUtenteUO/L.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}
//				if (record.getAttribute("flgProtocollista") != null && record.getAttributeAsBoolean("flgProtocollista")) {
//					format += "<div align=\"center\"><img src=\"images/organigramma/protocollista.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
//				}
//				if ("".equalsIgnoreCase(format)) {
//					return null;
//				}
//				return format;
//			}
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String format = "";
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT") && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
					format += "<img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" />";
				}
				if (record.getAttribute("flgPuntoRaccoltaEmail") != null && record.getAttributeAsBoolean("flgPuntoRaccoltaEmail")) {
					format += "<img src=\"images/organigramma/puntoRaccoltaEmail.png\" height=\"16\" width=\"16\" alt=\"\" />";
				}
				if (record.getAttribute("tipoRelUtenteUo") != null && "A".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
					format += "<img src=\"images/organigramma/tipoRelazioneUtenteUO/A.png\" height=\"16\" width=\"16\" alt=\"\" />";
				}else if (record.getAttribute("tipoRelUtenteUo") != null && "D".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
					format += "<img src=\"images/organigramma/tipoRelazioneUtenteUO/D.png\" height=\"16\" width=\"16\" alt=\"\" />";
				}else if (record.getAttribute("tipoRelUtenteUo") != null && "L".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
					format += "<img src=\"images/organigramma/tipoRelazioneUtenteUO/L.png\" height=\"16\" width=\"16\" alt=\"\" />";
				}
				if (record.getAttribute("flgProtocollista") != null && record.getAttributeAsBoolean("flgProtocollista")) {
					format += "<img src=\"images/organigramma/protocollista.png\" height=\"16\" width=\"16\" alt=\"\" />";
				}
				if ("".equalsIgnoreCase(format)) {
					return null;
				}
				return "<div style=\"margin-left:10px;\" align=\"left\">" + format + "</div>";
			}
		});
		iconeRiga.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String hover = "";
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT") && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
					hover += "Punto di Protocollo";
				}
				if (record.getAttribute("flgPuntoRaccoltaEmail") != null && record.getAttributeAsBoolean("flgPuntoRaccoltaEmail")) {
					hover += "".equalsIgnoreCase(hover) ? I18NUtil.getMessages().organigramma_uo_detail_flgPuntoRaccoltaEmailItem_hover() : "<BR>" + I18NUtil.getMessages().organigramma_uo_detail_flgPuntoRaccoltaEmailItem_hover();
				}
				if (record.getAttribute("tipoRelUtenteUo") != null && "A".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
					hover += "".equalsIgnoreCase(hover) ? I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_A_prompt() : "<BR>" + I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_A_prompt();
				}else if (record.getAttribute("tipoRelUtenteUo") != null && "D".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
					hover += "".equalsIgnoreCase(hover) ? I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_D_prompt() : "<BR>" + I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_D_prompt(); 
				}else if (record.getAttribute("tipoRelUtenteUo") != null && "L".equalsIgnoreCase(record.getAttributeAsString("tipoRelUtenteUo"))) {
					hover += "".equalsIgnoreCase(hover) ? I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_L_prompt() : "<BR>" + I18NUtil.getMessages().organigramma_list_tipoRelUtenteUoField_L_prompt();
				}
				if (record.getAttribute("flgProtocollista") != null && record.getAttributeAsBoolean("flgProtocollista")) {
					hover += "".equalsIgnoreCase(hover) ? "Protocollista" : "<BR>" + "Protocollista";
				}
				if ("".equalsIgnoreCase(hover)) {
					return null;
				}
				return hover;
			}
		});
		
		TreeGridField abilitaUoProtEntrata = new TreeGridField("abilitaUoProtEntrata");
		abilitaUoProtEntrata.setHidden(true);

		TreeGridField abilitaUoProtUscita = new TreeGridField("abilitaUoProtUscita");
		abilitaUoProtUscita.setHidden(true);

		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			setFields(idFolder, idUoSvUt, tipo, codRapidoUo, nroLivello, nroProgr, nome, flgSelXAssegnazione, iconeRiga, abilitaUoProtEntrata, abilitaUoProtUscita);
		} else {
			setFields(idFolder, idUoSvUt, tipo, codRapidoUo, nroLivello, nroProgr, flgSelXAssegnazione, nome, iconeRiga, abilitaUoProtEntrata, abilitaUoProtUscita);
		}

	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	@Override
	public boolean isExplorableTreeNode(Record record) {
		String tipo = record.getAttributeAsString("tipo");
		return (tipo != null && !tipo.startsWith("SV") && !tipo.startsWith("UT"));
	}

	public void manageCustomTreeNode(TreeNode node) {
		String tipo = node.getAttributeAsString("tipo");
		int pos = tipo.indexOf("_");
		if (!tipo.equals("SV_A") && !tipo.startsWith("UO") && pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		Boolean flgEsplodibile = node.getAttributeAsBoolean("flgEsplodibile");
		String suffix = !flgEsplodibile ? "_NonEsplodibile" : "";
		node.setIcon(nomeEntita + "/tipo/" + tipo + suffix + ".png");
		node.setShowOpenIcon(false);
		node.setShowDropIcon(false);
	}

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			if (record.getAttribute("tipo") != null && record.getAttribute("tipo").startsWith("SV_")) {
				Menu scrivaniaContextMenu = createSvAltreOpMenu(record);
				scrivaniaContextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));
				scrivaniaContextMenu.showContextMenu();
			} else {
				TreeNodeBean node = new TreeNodeBean();
				node.setIdNode(record.getAttributeAsString("idNode"));
				node.setIdFolder(record.getAttributeAsString("idFolder"));
				node.setIdLibreria(record.getAttributeAsString("idLibreria"));
				final Menu navigationContextMenu = new NavigationContextMenu(layout, node, NavigationContextMenuFrom.TREE_NODE, getRecordIndex(record));
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						navigationContextMenu.showContextMenu();
						navigationContextMenu.hide();
						showNodeContextMenu(getRecord(getRecordIndex(record)), navigationContextMenu);
					}
				});
			}
		}
	}

	public void showNodeContextMenu(final Record record, Menu navigationContextMenu) {
		final Menu contextMenu = new Menu();
		final Menu altreOpMenu = createAltreOpMenu(record);
		for (int i = 0; i < altreOpMenu.getItems().length; i++) {
			contextMenu.addItem(altreOpMenu.getItems()[i], i);
		}
		if (navigationContextMenu != null) {
			for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
				contextMenu.addItem(navigationContextMenu.getItems()[i], i);
			}
			if (contextMenu.getItems().length > navigationContextMenu.getItems().length) {
				MenuItem separatorMenuItem = new MenuItem();
				separatorMenuItem.setIsSeparator(true);
				contextMenu.addItem(separatorMenuItem, navigationContextMenu.getItems().length);
			}
		}
		contextMenu.showContextMenu();

	}

	public Menu createAltreOpMenu(final Record record) {

		Menu altreOpMenu = new Menu();

		final String idUoSvUt = record.getAttribute("idUoSvUt");
		final String nroLivello = record.getAttribute("nroLivello");
		final String tipo = record.getAttribute("tipo");

		if (tipo != null) {
			
			if (tipo.startsWith("UO")) {
				
				// Visualizza U.O.
				MenuItem visualizzaMenuItem = getVisualizzaMenuItem(record);
				altreOpMenu.addItem(visualizzaMenuItem);

				if (OrganigrammaLayout.isAbilToMod()) {
					// Modifica U.O.
					MenuItem modificaMenuItem = getModificaMenuItem(record);
					altreOpMenu.addItem(modificaMenuItem);
				}

				// Aggancia utente a U.O.
				if(OrganigrammaLayout.isAbilToIns()){
					MenuItem agganciaUtenteUOMenuItem = getAgganciaUtenteUOMenuItem(record);
					altreOpMenu.addItem(agganciaUtenteUOMenuItem);
				}

				// Classifiche abilitate
				if (OrganigrammaLayout.isAbilToMod()) {
					MenuItem classificheAbilitate = getClassificheAbilitateMenuItem(record);
					altreOpMenu.addItem(classificheAbilitate);
				}
				
				// Tipologie documentali pubblicabili
				if (OrganigrammaLayout.isAbilTipologieDocPubblicabili()) {
					MenuItem tipologieDocAbilitatePubblicabili = getTipologieDocAbilitatePubblicabiliMenuItem(record);
					altreOpMenu.addItem(tipologieDocAbilitatePubblicabili);
				}
				
				// Tipologie documentali abilitate
				if (OrganigrammaLayout.isAbilToMod()) {
					MenuItem tipologieDocAbilitate = getTipologieDocAbilitateMenuItem(record);
					altreOpMenu.addItem(tipologieDocAbilitate);
				}

				// Tipologie fascicoli e UA abilitate
				if (OrganigrammaLayout.isAbilToMod()) {
					MenuItem tipologieFascicoliEUAAbilitate = getFascicoliEUAAbilitateMenuItem(record);
					altreOpMenu.addItem(tipologieFascicoliEUAAbilitate);
				}
				
				if (OrganigrammaLayout.isAbilToMod()) {
					// Incolla utilizzata per le operazioni { Copia & Incolla - Taglia UO - Taglia SV }
					MenuItem incollaMenuItem = new MenuItem("Incolla", "incolla.png");
					incollaMenuItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							if (((OrganigrammaLayout) layout).getCopyNode() != null) {
								((OrganigrammaLayout) layout).copyAndPaste("SV",record,false);
							} else if (((OrganigrammaLayout) layout).getCutNode() != null) {
								((OrganigrammaLayout) layout).paste("UO",record,false);
							} else if (((OrganigrammaLayout) layout).getCutNodePostazione() != null) {
								((OrganigrammaLayout) layout).paste("SV",record,false);
							}	
						}
					});
					if ((((OrganigrammaLayout) layout).getCopyNode() != null && !((OrganigrammaLayout) layout).getCopyNode().getAttribute("idUoSvUt").equals(idUoSvUt))
							|| (((OrganigrammaLayout) layout).getCutNode() != null && !((OrganigrammaLayout) layout).getCutNode().getAttribute("idUoSvUt").equals(idUoSvUt))
							|| (((OrganigrammaLayout) layout).getCutNodePostazione() != null  && !((OrganigrammaLayout) layout).getCutNodePostazione().getAttribute("idUoSvUt").equals(idUoSvUt))) {
						incollaMenuItem.setEnabled(true);
					} else {
						incollaMenuItem.setEnabled(false);
					}
					altreOpMenu.addItem(incollaMenuItem);
				}
			
				// Taglia UO
				MenuItem tagliaUOMenuItem = getTagliaUOMenuItem(record);
				if (Layout.isPrivilegioAttivo("SIC/SO;M")) {
					tagliaUOMenuItem.setEnabled(true);
				} else {
					tagliaUOMenuItem.setEnabled(false);
				}
				altreOpMenu.addItem(tagliaUOMenuItem);
				
				//Elimina UO
				MenuItem eliminaUOMenuItem = getEliminaUOMenuItem(record);
				if (Layout.isPrivilegioAttivo("SIC/SO;FC")) {
					eliminaUOMenuItem.setEnabled(true);
				}else{
					eliminaUOMenuItem.setEnabled(false);
				}
				altreOpMenu.addItem(eliminaUOMenuItem);
			
				// Se la UO/scrivania NON e' nei preferiti (=0) mostro la voce "Aggiungi ai destinatari preferiti"
				if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && !record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
					MenuItem aggiungiDestinatariPreferitiItem = getAggiungiDestinatariPreferitiItem(record);
					altreOpMenu.addItem(aggiungiDestinatariPreferitiItem);
				}
			
				// Se la UO/scrivania E'nei preferiti (=1) mostro la voce "Togli dai destinatari preferiti"
				if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
					MenuItem togliDestinatariPreferitiItem = getTogliDestinatariPreferitiItem(record);
					altreOpMenu.addItem(togliDestinatariPreferitiItem);
				}
				
				
				// Tipi Registri Numerazione abilitati
				if (OrganigrammaLayout.isAbilToMod()) {
					MenuItem tipiRegistriNumrazioneAbilitati = getTipiRegistriNumerazioneAbilitatiMenuItem(record);
					altreOpMenu.addItem(tipiRegistriNumrazioneAbilitati);					
				}
				
				
			} else if (tipo.startsWith("SV")) {
				// Se la UO/scrivania NON e' nei preferiti (=0) mostro la voce "Aggiungi ai destinatari preferiti"
				if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && !record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
					MenuItem aggiungiDestinatariPreferitiItem = getAggiungiDestinatariPreferitiItem(record);
					altreOpMenu.addItem(aggiungiDestinatariPreferitiItem);
				}
			
				// Se la UO/scrivania E'nei preferiti (=1) mostro la voce "Togli dai destinatari preferiti"
				if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
					MenuItem togliDestinatariPreferitiItem = getTogliDestinatariPreferitiItem(record);
					altreOpMenu.addItem(togliDestinatariPreferitiItem);
				}
			} else if (tipo.startsWith("AOO")) {
				
				if (Layout.isPrivilegioAttivo("SIC/SO;M")) {
					MenuItem incollaMenuItem = new MenuItem("Incolla", "incolla.png");
					incollaMenuItem.addClickHandler(new ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							if (((OrganigrammaLayout) layout).getCutNode() != null) {
								((OrganigrammaLayout) layout).paste("UO",record,false);
							}
						}
					});
					if (((OrganigrammaLayout) layout).getCutNode() != null && !((OrganigrammaLayout) layout).getCutNode().getAttribute("idUoSvUt").equals(idUoSvUt)){
						incollaMenuItem.setEnabled(true);
					}else{
						incollaMenuItem.setEnabled(false);
					}
					altreOpMenu.addItem(incollaMenuItem);
				}
			}
		}

		if (((OrganigrammaLayout) getLayout()).isAbilToInsInLivello(nroLivello != null ? new BigDecimal(nroLivello) : new BigDecimal(0))) {
			// Nuova U.O.
			MenuItem aggiungiUO = getAggiungiUOMenuItem(record);
			altreOpMenu.addItem(aggiungiUO);
		}

		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}

	public Menu createSvAltreOpMenu(final Record record) {

		Menu svAltreOpMenu = new Menu();

		// Visualizza postazione
		MenuItem visualizzaMenuItem = getVisualizzaMenuItem(record);
		svAltreOpMenu.addItem(visualizzaMenuItem);

		if (OrganigrammaLayout.isAbilToMod()) {
			// Modifica postazione
			MenuItem modificaMenuItem = getModificaMenuItem(record);
			svAltreOpMenu.addItem(modificaMenuItem);

			// Sostituzione utente
			MenuItem sostituzioneMenuItem = getSostituzioneScrivaniaUtenteMenuItem(record);
			svAltreOpMenu.addItem(sostituzioneMenuItem);
			
			//Taglia postazione
			MenuItem tagliaPostazioneMenuItem = getTagliaPostazioneMenuItem(record);
			svAltreOpMenu.addItem(tagliaPostazioneMenuItem);
			
			// Copia
			MenuItem copiaMenuItem = getCopyMenuItem(record);
			svAltreOpMenu.addItem(copiaMenuItem);
		}

		// Se la UO/scrivania NON e' nei preferiti (=0) mostro la voce "Aggiungi ai destinatari preferiti"
		if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && !record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
			MenuItem aggiungiDestinatariPreferitiItem = getAggiungiDestinatariPreferitiItem(record);
			svAltreOpMenu.addItem(aggiungiDestinatariPreferitiItem);
		}
	
		// Se la UO/scrivania E'nei preferiti (=1) mostro la voce "Togli dai destinatari preferiti"
		if (record.getAttribute("flgDestinatarioNeiPreferiti") != null && record.getAttributeAsBoolean("flgDestinatarioNeiPreferiti")) {
			MenuItem togliDestinatariPreferitiItem = getTogliDestinatariPreferitiItem(record);
			svAltreOpMenu.addItem(togliDestinatariPreferitiItem);
		}
		
		svAltreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return svAltreOpMenu;
	}

	private MenuItem getClassificheAbilitateMenuItem(final Record record) {
		MenuItem classificheAbilitateMenuItem = new MenuItem("Classifiche abilitate", "lookup/titolario.png");
		classificheAbilitateMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				Record classificheRecord = new Record();				
				classificheRecord.setAttribute("flgTpDestAbil", "UO");
				classificheRecord.setAttribute("flgClassificheAttiva", "1");
				classificheRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				classificheRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				classificheRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));
				
				ClassificheWindow classifiche = new ClassificheWindow("Classifiche abilitate alla U.0.", classificheRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				classifiche.show();
			}
		});
		return classificheAbilitateMenuItem;
	}

	
	
	private MenuItem getTipiRegistriNumerazioneAbilitatiMenuItem(final Record record) {
		MenuItem tipiRegistriNumerazioneAbilitatiMenuItem = new MenuItem("Registri abilitati", "menu/registri_numerazione.png");
	
		tipiRegistriNumerazioneAbilitatiMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				Record registriRecord = new Record();
				registriRecord.setAttribute("callFrom", "organigramma");
				registriRecord.setAttribute("flgStatoAbilitazioneIn", "1");
				registriRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
				registriRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				registriRecord.setAttribute("opzioniAbil", "A");
				registriRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				registriRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));

				RegistriNumerazioneWindow registriNumerazione = new RegistriNumerazioneWindow("Registri Numerazione abilitati alla U.0.", registriRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				registriNumerazione.show();
			}
		});
		
		return tipiRegistriNumerazioneAbilitatiMenuItem;
	}
	
	private MenuItem getTipologieDocAbilitatePubblicabiliMenuItem(final Record record) {
		MenuItem tipologieDocAbilitatePubblicabiliMenuItem = new MenuItem("Tipologie documentali pubblicabili", "menu/tipologieDocumentaliPubblicabili.png");
		tipologieDocAbilitatePubblicabiliMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				String titoloWindow = "Tipologie documentali pubblicabili dagli operatori della UO";
				Record tipiDocRecord = new Record();
				tipiDocRecord.setAttribute("viewTipologiaDaUO", "true");
				tipiDocRecord.setAttribute("flgStatoAbilitazioneIn", "1");
				tipiDocRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
				tipiDocRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				tipiDocRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				tipiDocRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));
				tipiDocRecord.setAttribute("icona", record.getAttributeAsString("icona"));					
				tipiDocRecord.setAttribute("opzioniAbil", "P");
				
				TipologieDocumentaliWindow tipiDoc = new TipologieDocumentaliWindow(titoloWindow, tipiDocRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				tipiDoc.show();
			}
		});
		return tipologieDocAbilitatePubblicabiliMenuItem;

	}
	
	private MenuItem getTipologieDocAbilitateMenuItem(final Record record) {
		MenuItem tipologieDocAbilitateMenuItem = new MenuItem("Tipologie documentali abilitate", "menu/tipologieDocumentali.png");
		tipologieDocAbilitateMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				Record tipiDocRecord = new Record();
				
				tipiDocRecord.setAttribute("viewTipologiaDaUO", "true");
				tipiDocRecord.setAttribute("flgStatoAbilitazioneIn", "1");
				tipiDocRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
				tipiDocRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				tipiDocRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				tipiDocRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));

				TipologieDocumentaliWindow tipiDoc = new TipologieDocumentaliWindow("Tipologie documentali abilitate alla U.0.", tipiDocRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				tipiDoc.show();
			}
		});
		return tipologieDocAbilitateMenuItem;

	}

	private MenuItem getFascicoliEUAAbilitateMenuItem(final Record record) {		
		MenuItem fascicoliEUAAbilitateMenuItem = new MenuItem("Tipologie fascicoli e UA abilitate", "menu/tipo_fascicoli_aggr.png");
		fascicoliEUAAbilitateMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				Record tipiFolderRecord = new Record();
				
				tipiFolderRecord.setAttribute("viewTipologiaDaUO", "true");
				tipiFolderRecord.setAttribute("flgStatoAbilitazioneIn", "1");				
				tipiFolderRecord.setAttribute("flgTipologiaDestAbilIn", "UO");
				tipiFolderRecord.setAttribute("idUo", record.getAttributeAsString("idUoSvUt"));
				tipiFolderRecord.setAttribute("codRapidoUo", record.getAttributeAsString("codRapidoUo"));
				tipiFolderRecord.setAttribute("denominazioneEstesa", record.getAttributeAsString("descrUoSvUt"));
				
				TipologieFascicoliWindows tipiFolder = new TipologieFascicoliWindows("Tipologie fascicoli e UA abilitate  alla U.O", tipiFolderRecord) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				tipiFolder.show();
			}
		});
		return fascicoliEUAAbilitateMenuItem;

	}

	private MenuItem getSostituzioneScrivaniaUtenteMenuItem(final Record record) {		
		MenuItem sostituzioneMenuItem = new MenuItem("Sostituisci utente", "organigramma/sostituisciUtente.png");
		sostituzioneMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((OrganigrammaList) getLayout().getList()).manageLoadDetailPerSostituzioneUtente(record, -1, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						getLayout().editMode();
						((OrganigrammaLayout) layout).setIdNodeToOpen(record.getAttribute("parentId"));
					}
				});
			}
		});
		return sostituzioneMenuItem;
	}

	private MenuItem getAgganciaUtenteUOMenuItem(final Record record) {		
		MenuItem agganciaUtenteUOMenuItem = new MenuItem(I18NUtil.getMessages().organigramma_list_agganciaUtente(), "organigramma/agganciaUtenteUo.png");
		agganciaUtenteUOMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getLayout().changeDetail(new GWTRestDataSource("PostazioneDatasource", "ciRelUserUo", FieldType.TEXT),
						new PostazioneDetail("organigramma", false));
				Map<String, Object> mappa = new HashMap<String, Object>();
				mappa.put("idUo", record.getAttributeAsString("idUoSvUt"));
				mappa.put("abilitaUoProtEntrata", record.getAttributeAsString("abilitaUoProtEntrata"));
				mappa.put("abilitaUoProtUscita",  record.getAttributeAsString("abilitaUoProtUscita"));				
				getLayout().getDetail().editNewRecord(mappa);
				getLayout().getDetail().getValuesManager().clearErrors(true);
				getLayout().newMode();
				((OrganigrammaLayout) layout).setIdNodeToOpen(record.getAttribute("idNode"));
			}
		});
		return agganciaUtenteUOMenuItem;
	}

	private MenuItem getAggiungiUOMenuItem(final Record record) {	
		MenuItem aggiungiUO = new MenuItem("Nuova U.O.", "buttons/new.png");
		aggiungiUO.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getLayout().changeDetail(new GWTRestDataSource("OrganigrammaDatasource", "idUoSvUt", FieldType.TEXT), new OrganigrammaDetail("organigramma"));
				Map<String, String> mappa = new HashMap<String, String>();
				try {
					mappa.put("nroLivello", "" + (Integer.parseInt(record.getAttribute("nroLivello")) + 1));
				} catch (Exception e) {
					mappa.put("nroLivello", "1");
				}
				mappa.put("livelloCorrente", record.getAttribute("codRapidoUo"));
				getLayout().getDetail().editNewRecord(mappa);
				getLayout().getDetail().getValuesManager().clearErrors(true);
				getLayout().newMode();
				((OrganigrammaLayout) layout).setIdNodeToOpen(record.getAttribute("idNode"));
			}
		});
		return aggiungiUO;
	}

	private MenuItem getCopyMenuItem(final Record record) {		
		MenuItem copiaMenuItem = new MenuItem("Copia", "copia.png");
		copiaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((OrganigrammaLayout) layout).setCopyNode(record);
				((OrganigrammaLayout) layout).setCutNodePostazione(null);
				((OrganigrammaLayout) layout).setCutNode(null);
			}
		});
		return copiaMenuItem;
	}
	
	private MenuItem getTagliaUOMenuItem(final Record record) {		
		MenuItem tagliaUOMenuItem = new MenuItem("Taglia", "buttons/cut.png");
		tagliaUOMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record cutRecord = new Record(record.toMap());				
				if(cutRecord.getAttribute("parentId") != null) {
					cutRecord.setAttribute("idUoSup", cutRecord.getAttribute("parentId").substring(cutRecord.getAttribute("parentId").lastIndexOf("/") + 1));
				}
				((OrganigrammaLayout) layout).setCutNode(cutRecord);
				((OrganigrammaLayout) layout).setCutNodePostazione(null);
				((OrganigrammaLayout) layout).setCopyNode(null);
			}
		});
		return tagliaUOMenuItem;
	}
	
	private MenuItem getTagliaPostazioneMenuItem(final Record record) {		
		MenuItem tagliaPostazioneMenuItem = new MenuItem("Taglia", "buttons/cut.png");
		tagliaPostazioneMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Record cutRecordPostazione = new Record(record.toMap());				
				if(cutRecordPostazione.getAttribute("parentId") != null) {
					cutRecordPostazione.setAttribute("idUoSup",
				    cutRecordPostazione.getAttribute("parentId").substring(cutRecordPostazione.getAttribute("parentId").lastIndexOf("/") + 1));
				}
				((OrganigrammaLayout) layout).setCutNodePostazione(cutRecordPostazione);
				((OrganigrammaLayout) layout).setCutNode(null);
				((OrganigrammaLayout) layout).setCopyNode(null);
			}
		});
		return tagliaPostazioneMenuItem;
	}
	
	private MenuItem getEliminaUOMenuItem(final Record currentRecord) {		
		MenuItem eliminaUOMenuItem = new MenuItem("Elimina UO", "buttons/delete.png");
		eliminaUOMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((OrganigrammaLayout) layout).delete(currentRecord,false);
			}
		});
		return eliminaUOMenuItem;
	}

	private MenuItem getVisualizzaMenuItem(final Record record) {		
		MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getLayout().getList().manageLoadDetail(record, -1, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						getLayout().viewMode();
						((OrganigrammaLayout) layout).setIdNodeToOpen(record.getAttribute("parentId"));
					}
				});
			}
		});
		return visualizzaMenuItem;
	}

	private MenuItem getModificaMenuItem(final Record record) {		
		MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
		modificaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getLayout().getList().manageLoadDetail(record, -1, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						getLayout().editMode();
						((OrganigrammaLayout) layout).setIdNodeToOpen(record.getAttribute("parentId"));
					}
				});
			}
		});
		return modificaMenuItem;
	}

	@Override
	protected boolean canToFirstLoadTree() {
		return ((OrganigrammaLayout) getLayout()).getIdOrganigramma() != null && !"".equals(((OrganigrammaLayout) getLayout()).getIdOrganigramma());
	}
	
	private MenuItem getAggiungiDestinatariPreferitiItem(final Record record) {
		
		MenuItem aggiungiDestinatariPreferitiMenuItem = new MenuItem("Aggiungi ai destinatari preferiti", "organigramma/aggiungi_destinatari_preferiti.png");
		aggiungiDestinatariPreferitiMenuItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {				
				((OrganigrammaLayout) layout).aggiungiTogliDestinatariPreferiti(record,"A", "TREE");
			}
		});
		return aggiungiDestinatariPreferitiMenuItem;

	}
	
	private MenuItem getTogliDestinatariPreferitiItem(final Record record) {
		
		MenuItem aggiungiDestinatariPreferitiMenuItem = new MenuItem("Togli dai destinatari preferiti", "organigramma/togli_destinatari_preferiti.png");
		aggiungiDestinatariPreferitiMenuItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				((OrganigrammaLayout) layout).aggiungiTogliDestinatariPreferiti(record, "D", "TREE");
			}
		});
		return aggiungiDestinatariPreferitiMenuItem;

	}
}