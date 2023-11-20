/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovaemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.XmlFilterPostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailTrovaemail;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "PostaInUscitaRegistrazioneDataSource")
public class PostaInUscitaRegistrazioneDataSource extends AbstractFetchDataSource<PostaElettronicaBean> {

	@Override
	public String getNomeEntita() {
		return "postainuscitaregistrazione";
	}	
	
	@Override
	public PaginatorBean<PostaElettronicaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();
		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		Integer overflowLimit = null;
		
		String colOrderBy = null;
		String flgDescOrderBy = null;
		
		//  1: idEmail
		//  3: flgIo 
		//  6: categoria
		//  8: tsInvioClient
		//  9: tsInvio
		// 15: statoConsolidamento
		// 21: accountMittente
		// 22: oggetto
		// 24: destinatari
		// 35: flgStatoProt
		// 46: listEstremiRegProtAssociati
		// 47: listIdUdProtAssociati
		// 61: flgEmailCertificata
		// 62: flgInteroperabile
		// 68: azioneDaFare
		// 69: codAzioneDaFare
		// 70: dettaglioAzioneDaFare
		// 71: id
		// 73: progrOrdinamento
		// 76: statoInvio
		// 77: statoAccettazione
		// 78: statoConsegna
		String colsToReturn = "1,3,6,8,15,21,22,24,35,46,47,61,62,68,69,70,71,73,76,77,78";
		
		// leggo il pramatro di input
		String idUd = "";
		if (getExtraparams().get("idUd") != null)
			idUd = StringUtils.isNotBlank(getExtraparams().get("idUd")) ? getExtraparams().get("idUd") : null;
			
		// Inizializzo l'INPUT
		DmpkIntMgoEmailTrovaemailBean input = new DmpkIntMgoEmailTrovaemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgsenzapaginazionein(1);
		input.setBachsizeio(overflowLimit);
		input.setOverflowlimitin(overflowLimit);
		input.setColtoreturnin(colsToReturn);		

		/*
		// Ordino per : 73: progrOrdinamento (desc)
		Map<String,String> extraparams = new HashMap<String,String>();
		extraparams.put("orderBy", "-progrOrdinamento");
		setExtraparams(extraparams);
		
		HashSet<String> setNumColonneOrdinabili = new HashSet<String>(Arrays.asList("73"));
		String[] colAndFlgDescOrderBy = getColAndFlgDescOrderBy(PostaElettronicaBean.class, setNumColonneOrdinabili);
		colOrderBy = colAndFlgDescOrderBy[0];
		flgDescOrderBy = colAndFlgDescOrderBy[1];
		input.setColorderbyio(colOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		
		*/
		
		XmlFilterPostaElettronicaBean xmlPostaElettronicaBean = new XmlFilterPostaElettronicaBean();
		xmlPostaElettronicaBean.setIdUdTrasmessa(idUd);		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String filter = lXmlUtilitySerializer.bindXml(xmlPostaElettronicaBean);
		input.setFiltriio(filter);
					
		// Eseguo la store
		DmpkIntMgoEmailTrovaemail lservice = new DmpkIntMgoEmailTrovaemail();
		StoreResultBean<DmpkIntMgoEmailTrovaemailBean> output =  lservice.execute(getLocale(), loginBean, input);
		
		// Leggo l'esito
		boolean overflow = false;
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
			}
		}
				
		// leggo i risultati
		List<PostaElettronicaBean> data = new ArrayList<PostaElettronicaBean>();	
		if (output.getResultBean().getResultout() != null) {
			String xml = output.getResultBean().getResultout();
			data = XmlListaUtility.recuperaLista(xml, PostaElettronicaBean.class, new PostaElettronicaBeanDeserializationHelper(createRemapConditions()));
		}
				
		// Restiruisco i risultati
		PaginatorBean<PostaElettronicaBean> lPaginatorBean = new PaginatorBean<PostaElettronicaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}	
	
	protected Boolean manageOverflow(String errorMessageOut) {

		boolean overflow = false;

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {

			// Se entro qua vuol dire che sono in overflow (ho troppi risultati
			// dalla store)

			GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");

			// Controllo se devo visualizzare il messaggio di warning
			Boolean showOverflow = config.getShowAlertMaxRecord();
			if (showOverflow) {
				addMessage(errorMessageOut, "", MessageType.WARNING);
			}
			// Sono in overflow, quindi restituisco true anche se non ho visualizzato
			// il messaggio di warning. In questo modo su BaseUI posso gestire la visualizzazione del +
			// sulla label col numero di risultati trovati
			overflow = true;
		}
		return overflow;
	}
	
	private Map<String, String> createRemapConditions() {
		return new HashMap<String, String>();
	}
}
