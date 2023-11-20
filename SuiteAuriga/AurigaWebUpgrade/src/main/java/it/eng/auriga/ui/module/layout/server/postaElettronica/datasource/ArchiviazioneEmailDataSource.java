/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailArchiviaemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ArchiviazioneEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ArchiviazionelXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailArchiviaemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ArchiviazioneEmailDataSource")
public class ArchiviazioneEmailDataSource extends AbstractDataSource<ArchiviazioneEmailBean, ArchiviazioneEmailBean> {

	private static Logger mLogger = Logger.getLogger(ArchiviazioneEmailDataSource.class);

	@Override
	public ArchiviazioneEmailBean add(ArchiviazioneEmailBean bean) throws Exception {

		HashMap<String, String> errorMessages = null;
		AurigaLoginBean aurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<ArchiviazionelXmlBean> listaArchiviazioneEmail = new ArrayList<ArchiviazionelXmlBean>();
		ArchiviazionelXmlBean archiviazioneXmlBean = null;

		DmpkIntMgoEmailArchiviaemailBean input = new DmpkIntMgoEmailArchiviaemailBean();

		for (PostaElettronicaBean email : bean.getListaRecord()) {
			archiviazioneXmlBean = new ArchiviazionelXmlBean();
			archiviazioneXmlBean.setIdEmail(email.getIdEmail());

			listaArchiviazioneEmail.add(archiviazioneXmlBean);
		}

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlInput = lXmlUtilitySerializer.bindXmlList(listaArchiviazioneEmail);
		input.setListaidemailin(xmlInput);
		input.setMotiviin(bean.getMotivazione());
		input.setFlggestazionedafarein(bean.getOperazioneRichiesta());

		DmpkIntMgoEmailArchiviaemail archiviaMail = new DmpkIntMgoEmailArchiviaemail();

		StoreResultBean<DmpkIntMgoEmailArchiviaemailBean> output = archiviaMail.execute(getLocale(), aurigaLoginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);

			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(0), v.get(4));
				}
			}
		}
		bean.setErrorMessages(errorMessages);

		return bean;
	}

	@Override
	public ArchiviazioneEmailBean get(ArchiviazioneEmailBean bean) throws Exception {
		return null;
	}

	@Override
	public ArchiviazioneEmailBean remove(ArchiviazioneEmailBean bean) throws Exception {
		return null;
	}

	@Override
	public ArchiviazioneEmailBean update(ArchiviazioneEmailBean bean, ArchiviazioneEmailBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public PaginatorBean<ArchiviazioneEmailBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ArchiviazioneEmailBean bean) throws Exception {
		return null;
	}

}
