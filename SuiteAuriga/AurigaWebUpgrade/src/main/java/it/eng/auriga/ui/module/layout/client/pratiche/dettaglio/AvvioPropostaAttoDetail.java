/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.AvvioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetailAtti;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class AvvioPropostaAttoDetail extends ProtocollazioneDetailAtti implements PropostaAttoInterface {

	protected AvvioPropostaAttoDetail instance;

	protected Record recordAvvio;
	protected Record recordEvento;

	protected String idTipoProc;
	protected String nome;
	protected String activityName;

	protected AvvioPraticaLayout avvioPraticaLayout;

	public AvvioPropostaAttoDetail(String nomeEntita, String idTipoProc, Record lRecordAvvio, Record lRecordEvento,
			AvvioPraticaLayout avvioPraticaLayout) {

		super(nomeEntita, getAttributiAddDocTabsAvvio(), getTipoDocumento(lRecordAvvio));

		instance = this;

		this.idTipoProc = idTipoProc;

		this.recordAvvio = lRecordAvvio;
		this.recordEvento = lRecordEvento;

		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;

		this.avvioPraticaLayout = avvioPraticaLayout;

	}

	@Override
	public boolean isAvvioPropostaAtto() {
		return true;
	}	
	
	@Override
	public String getIdProcessTypeTask() {
		return idTipoProc;
	}
	
	public boolean isGenerazioneAutomaticaFilePrimario() {
		return isPropostaAtto2Milano();
	}
	
	@Override
	public boolean showFilePrimarioForm() {
		if (isGenerazioneAutomaticaFilePrimario() || !showUploadFilePrimario()) {
			return false;
		}
		return true;
	}

	public static LinkedHashMap<String, String> getAttributiAddDocTabsAvvio() {
		if (!isPropostaAtto2Milano()) {
			LinkedHashMap<String, String> attributAddDocTabs = new LinkedHashMap<String, String>();
			attributAddDocTabs.put("SCELTE_ITER", "Opzioni iter");
			return attributAddDocTabs;
		}
		return null;
	}

	public static String getTipoDocumento(Record lRecordAvvio) {
		return lRecordAvvio != null ? lRecordAvvio.getAttribute("idTipoDocProposta") : null;
	}

	@Override
	public Record getRecordToSave(String motivoVarDatiReg) {
		final String idTipoDocProposta = recordAvvio.getAttribute("idTipoDocProposta");
		final String siglaProposta = recordAvvio.getAttribute("siglaProposta");
		Record lRecordToSave = super.getRecordToSave(motivoVarDatiReg);
		lRecordToSave.setAttribute("tipoDocumento", idTipoDocProposta);
		lRecordToSave.setAttribute("siglaProtocollo", siglaProposta);
		return lRecordToSave;
	}
	
	@Override
	public Record getRecordEvento() {
		return null;
	}

	@Override
	public void loadDati() {

	}

	@Override
	public void salvaDatiProvvisorio() {

	}

	@Override
	public void salvaDatiDefinitivo() {
		if (validate()) {
			Record lRecordToSave = getRecordToSave(null);
			final String uoProtocollante = lRecordToSave.getAttribute("uoProtocollante");
			final String oggetto = lRecordToSave.getAttribute("oggetto");
			final String idFolder = getIdFolderForAvvio(lRecordToSave);
			final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lProtocolloDataSource.addParam("isPropostaAtto", "true");
			Layout.showWaitPopup("Avvio iter atti in corso...");
			lProtocolloDataSource.addData(lRecordToSave, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordSaved = response.getData()[0];
						final String idUd = lRecordSaved.getAttribute("idUd");
						final String rowidDoc = lRecordSaved.getAttribute("rowidDoc");
						final String idDocPrimario = lRecordSaved.getAttribute("idDocPrimario");
						if (rowidDoc != null && !"".equals(rowidDoc)) {
							salvaAttributiDinamiciDoc(false, rowidDoc, activityName, null, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									avvioPraticaLayout.avvioIterAtti(idUd, idFolder, idDocPrimario, uoProtocollante, oggetto);
								}
								
								@Override
								public void manageError() {
									avvioPraticaLayout.manageOnErrorAvvioIterAtti(idUd);
								}
							});
						} else {
							lProtocolloDataSource.getData(lRecordSaved, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										final String rowidDoc = response.getData()[0].getAttribute("rowidDoc");
										salvaAttributiDinamiciDoc(false, rowidDoc, activityName, null, new ServiceCallback<Record>() {

											@Override
											public void execute(Record object) {
												avvioPraticaLayout.avvioIterAtti(idUd, idFolder, idDocPrimario, uoProtocollante, oggetto);
											}
											
											@Override
											public void manageError() {
												avvioPraticaLayout.manageOnErrorAvvioIterAtti(idUd);
											}
										});
									} else {
										avvioPraticaLayout.manageOnErrorAvvioIterAtti(idUd);
									}
								}
							});
						}
					} else {
						avvioPraticaLayout.manageOnErrorAvvioIterAtti(null);
					}
				}
			});
		} else {
			avvioPraticaLayout.manageOnErrorAvvioIterAtti(null);
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}

	public String getIdFolderForAvvio(Record lRecord) {
		String idFolder = null;
		RecordList listaClassFasc = lRecord.getAttributeAsRecordList("listaClassFasc");
		if (listaClassFasc != null && listaClassFasc.getLength() > 0) {
			Record lFirstRecord = listaClassFasc.get(0);
			if (lFirstRecord.getAttribute("idFolderFascicolo") != null && !"".equals(lFirstRecord.getAttribute("idFolderFascicolo"))) {
				idFolder = lFirstRecord.getAttribute("idFolderFascicolo");
			}
		}
		if (idFolder == null) {
			RecordList listaFolderCustom = lRecord.getAttributeAsRecordList("listaFolderCustom");
			if (listaFolderCustom != null && listaFolderCustom.getLength() > 0) {
				Record lFirstRecord = listaFolderCustom.get(0);
				if (lFirstRecord.getAttribute("id") != null && !"".equals(lFirstRecord.getAttribute("id"))) {
					idFolder = lFirstRecord.getAttribute("id");
				}
			}
		}
		return idFolder;
	}

	public void aggiornaFilePrimario(String idUd, String idDocPrimario, String uriFilePrimario, String nomeFilePrimario, final ServiceCallback<Record> callback) {
		Record lRecordToSave = new Record();
		lRecordToSave.setAttribute("idUd", idUd);
		lRecordToSave.setAttribute("idDocPrimario", idDocPrimario);
		lRecordToSave.setAttribute("uriFilePrimario", uriFilePrimario);
		lRecordToSave.setAttribute("nomeFilePrimario", nomeFilePrimario);
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lProtocolloDataSource.addParam("isPropostaAtto", "true");
		lProtocolloDataSource.executecustom("aggiornaFilePrimario", lRecordToSave, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordSaved = response.getData()[0];
					if (callback != null) {
						callback.execute(lRecordSaved);
					}
				}
			}
		});
	}

	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return null;
	}

	@Override
	public String getNomeTastoSalvaDefinitivo() {
		return isPropostaAtto2Milano() ? "Avanti" : "Avvia iter";
	}
	
	@Override
	public String getNomeTastoSalvaDefinitivo_2() {
		return null;
	}


	@Override
	public void back() {
		avvioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
	}

	@Override
	public boolean hasDocumento() {
		return false;
	}

	@Override
	public boolean showDetailToolStrip() {
		return false;
	}

}
