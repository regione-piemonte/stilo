/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;

public abstract class SaveModelloAction {

	private GWTRestDataSource modelliDS;
	private SelectItem modelliSelectItem;

	private JSONEncoder encoder;

	public SaveModelloAction(GWTRestDataSource modelliDS, SelectItem modelliSelectItem) {
		this.modelliDS = modelliDS;
		this.modelliSelectItem = modelliSelectItem;

		this.encoder = new JSONEncoder();
		this.encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
	}

	public void execute(Record record) {
		
		final String nome = record != null ? record.getAttribute("nome") : null;
		final boolean flgPubblico = record != null && record.getAttributeAsBoolean("flgPubblico") != null && record.getAttributeAsBoolean("flgPubblico");
		final boolean flgUo = record != null && record.getAttributeAsBoolean("flgUo") != null && record.getAttributeAsBoolean("flgUo");
		final String idUo = record != null ? record.getAttribute("idUoAssociata") : null;
		final boolean flgVisibSottoUo = record != null && record.getAttributeAsBoolean("flgVisibileDaSottoUo") != null
				&& record.getAttributeAsBoolean("flgVisibileDaSottoUo");
		final boolean flgGestSottoUo = record != null && record.getAttributeAsBoolean("flgModificabileDaSottoUo") != null
				&& record.getAttributeAsBoolean("flgModificabileDaSottoUo");
		if (nome != null && !nome.equals("")) {
			AdvancedCriteria criteriaModelli = new AdvancedCriteria();
			criteriaModelli.addCriteria("prefName", OperatorId.EQUALS, nome);
			criteriaModelli.addCriteria("flgIncludiPubbl", flgPubblico ? "1" : "0");
			if (flgUo) {
				if (idUo != null && !"".equals(idUo)) {
					if (idUo.startsWith("UO")) {
						criteriaModelli.addCriteria("idUo", idUo.substring(2));
					} else {
						criteriaModelli.addCriteria("idUo", idUo);
					}
				}
			}
			modelliDS.fetchData(criteriaModelli, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record[] data = response.getData();
						if (data.length != 0) {
							final Record record = data[0];
							if (!flgPubblico && flgUo && idUo != null && !"".equals(idUo)) {
								record.setAttribute("flgVisibSottoUo", flgVisibSottoUo ? true : null);
								record.setAttribute("flgGestSottoUo", flgGestSottoUo ? true : null);
							}
							Map values = getMapValuesForUpdate();
							String valuesStr = JSON.encode(new Record(values).getJsObj(), encoder);
							record.setAttribute("value", valuesStr);
							
							modelliDS.addParam("idDominio", getPrefKeySuffixSpecXDominio());
							modelliDS.updateData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										final Record record = response.getData()[0];
										modelliSelectItem.fetchData(new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												modelliSelectItem.setValue(record.getAttribute("key"));
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().saveModelloAction_salvataggioCorretto_message(), "", MessageType.INFO));	
											}
										});										
									}
								}
							});
						} else {
							final Record record = new Record();
							record.setAttribute("prefName", nome);
							if (flgPubblico) {
								String idDominio = AurigaLayout.getIdDominio();
								record.setAttribute("userid", "PUBLIC." + idDominio);
							} else if (flgUo && idUo != null && !"".equals(idUo)) {
								if (idUo.startsWith("UO")) {
									record.setAttribute("userid", "UO." + idUo.substring(2));
									record.setAttribute("idUo", idUo.substring(2));
								} else {
									record.setAttribute("userid", "UO." + idUo);
									record.setAttribute("idUo", idUo);
								}
								record.setAttribute("flgVisibSottoUo", flgVisibSottoUo ? true : null);
								record.setAttribute("flgGestSottoUo", flgGestSottoUo ? true : null);
							}
							Map values = getMapValuesForAdd();
							String valuesStr = JSON.encode(new Record(values).getJsObj(), encoder);
							record.setAttribute("value", valuesStr);
							
							modelliDS.addParam("idDominio", getPrefKeySuffixSpecXDominio());
							modelliDS.addData(record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										final Record record = response.getData()[0];
										modelliSelectItem.fetchData(new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												modelliSelectItem.setValue(record.getAttribute("key"));												
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().saveModelloAction_salvataggioCorretto_message(), "", MessageType.INFO));	
											}
										});												
									}
								}
							});
						}
					}
				}
			});
		}
	}
	
	public static String getPrefKeySuffixSpecXDominio() {
		String idDominio = getIdDominio();
		return idDominio != null && !"".equals(idDominio) ? "." + idDominio : ""; 
	}

	public static String getIdDominio() {
		if(getUtenteLoggato() != null) {
			if (getUtenteLoggato().getDominio() != null && getUtenteLoggato().getDominio().split(":").length == 2) {
				return getUtenteLoggato().getDominio().split(":")[1];
			} else {
				return getUtenteLoggato().getDominio();
			}
		}
		return null;
	}
	
	public static LoginBean getUtenteLoggato() {
		return Layout.getUtenteLoggato();
	}

	public abstract Map getMapValuesForAdd();

	public abstract Map getMapValuesForUpdate();

}