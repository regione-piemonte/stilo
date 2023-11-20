/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.tipologieDocumentali.TipologieDocumentaliLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author cristiano
 *
 */

public class TipoFascicoliAggregatiList extends CustomList {

	private ListGridField idFolderType;
	private ListGridField nome;
	private ListGridField descrizione;
	private ListGridField idFolderTypeGen;
	private ListGridField nomeFolderTypeGen;
	private ListGridField flgDaScansionare;
	private ListGridField periodoConserv;
	private ListGridField annotazioni;
	private ListGridField descApplFolderTypes;
	private ListGridField flgRichAbilXVisualizz;
	private ListGridField flgRichAbilXGest;
	private ListGridField flgRichAbilXAssegn;
	private ListGridField tsUltimoAgg;
	private ListGridField utenteUltAgg;
	private boolean tipoFascicoliAbilitate;

	public TipoFascicoliAggregatiList(String nomeEntita,String tipoFascicoliAbilitate) {
		super(nomeEntita);
		
		this.tipoFascicoliAbilitate = tipoFascicoliAbilitate != null && "1".equals(tipoFascicoliAbilitate) ? true : false;

		idFolderType = new ListGridField("idFolderType", I18NUtil.getMessages().tipofascicoliaggr_list_id());

		nome = new ListGridField("nome", I18NUtil.getMessages().tipofascicoliaggr_list_nome());

		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().tipofascicoliaggr_list_descrizione());

		idFolderTypeGen = new ListGridField("idFolderTypeGen", I18NUtil.getMessages().tipofascicoliaggr_list_id_gen());
		idFolderTypeGen.setCanHide(true);
		idFolderTypeGen.setHidden(true);

		nomeFolderTypeGen = new ListGridField("nomeFolderTypeGen", I18NUtil.getMessages().tipofascicoliaggr_list_nome_gen());

		flgDaScansionare = buildFlagIconField("flgDaScansionare", I18NUtil.getMessages().tipofascicoliaggr_list_flgScansionare(), I18NUtil.getMessages()
				.tipofascicoliaggr_list_flgScansionare(), "true", "ok.png");

		periodoConserv = new ListGridField("periodoConserv", I18NUtil.getMessages().tipofascicoliaggr_list_periodo_conserv());

		annotazioni = new ListGridField("annotazioni", I18NUtil.getMessages().tipofascicoliaggr_list_annotazioni());

		descApplFolderTypes = new ListGridField("descApplFolderTypes", I18NUtil.getMessages().tipofascicoliaggr_list_desc_appl());

		flgRichAbilXVisualizz = buildFlagIconField("flgRichAbilXVisualizz", I18NUtil.getMessages().tipofascicoliaggr_list_flgAbVis(), I18NUtil.getMessages()
				.tipofascicoliaggr_list_flgAbVis(), "true", "ok.png");

		flgRichAbilXGest = buildFlagIconField("flgRichAbilXGest", I18NUtil.getMessages().tipofascicoliaggr_list_flgAbLav(), I18NUtil.getMessages()
				.tipofascicoliaggr_list_flgAbLav(), "true", "ok.png");

		flgRichAbilXAssegn = buildFlagIconField("flgRichAbilXAssegn", I18NUtil.getMessages().tipofascicoliaggr_list_flgAbAss(), I18NUtil.getMessages()
				.tipofascicoliaggr_list_flgAbAss(), "true", "ok.png");

		tsUltimoAgg = new ListGridField("tsUltimoAgg", I18NUtil.getMessages().tipofascicoliaggr_list_dtUltAgg());
		tsUltimoAgg.setType(ListGridFieldType.DATE);
		tsUltimoAgg.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		utenteUltAgg = new ListGridField("utenteUltAgg", I18NUtil.getMessages().tipofascicoliaggr_list_uteUltAgg());

		setFields(idFolderType, nome, descrizione, idFolderTypeGen, nomeFolderTypeGen, flgDaScansionare, periodoConserv, annotazioni, descApplFolderTypes,
				tsUltimoAgg, utenteUltAgg, flgRichAbilXVisualizz, flgRichAbilXGest, flgRichAbilXAssegn);

	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return TipologieDocumentaliLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return TipologieDocumentaliLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		return TipoFascicoliAggregatiLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		return TipoFascicoliAggregatiLayout.isRecordAbilToDel(flgDiSistema);
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	public ListGridField buildFlagIconField(final String name, final String title, final String hover, final String trueValue, final String icon) {
		
		ListGridField flagIconField = new ListGridField(name, title);
		flagIconField.setAlign(Alignment.CENTER);
		flagIconField.setAttribute("custom", true);
		flagIconField.setShowHover(true);
		flagIconField.setType(ListGridFieldType.ICON);
		flagIconField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value != null && value.toString().equals(trueValue)) {
					return buildIconHtml(icon);
				}
				return null;
			}
		});
		flagIconField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String realValue = record.getAttribute(name);
				if (realValue != null && realValue.equals(trueValue)) {
					return hover;
				}
				return null;
			}
		});
		return flagIconField;
	}
	
	public boolean isListaTipoFascicoliAbilitate() 
	{
		return tipoFascicoliAbilitate;
	}

}
