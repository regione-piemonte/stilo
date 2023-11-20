/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTrovavisibledocversionBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VisualizzaVersioniFileBean;
import it.eng.client.DmpkCoreTrovavisibledocversion;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@Datasource(id = "VisualizzaVersioniFileDataSource")
public class VisualizzaVersioniFileDataSource extends AbstractFetchDataSource<VisualizzaVersioniFileBean> {

	private static final Logger log = Logger.getLogger(VisualizzaVersioniFileDataSource.class);

	@Override
	public PaginatorBean<VisualizzaVersioniFileBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String idDocIn = getExtraparams().get("idDocIn") != null ? getExtraparams().get("idDocIn") : "";
		
		List<VisualizzaVersioniFileBean> data = new ArrayList<VisualizzaVersioniFileBean>();

		DmpkCoreTrovavisibledocversionBean input = new DmpkCoreTrovavisibledocversionBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIddocin(new BigDecimal(idDocIn));
		input.setFlgsenzapaginazionein(1);
		input.setColorderbyio("1");

		DmpkCoreTrovavisibledocversion dmpkCoreTrovavisibledocversionBean = new DmpkCoreTrovavisibledocversion();
		StoreResultBean<DmpkCoreTrovavisibledocversionBean> output = dmpkCoreTrovavisibledocversionBean.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (output.getResultBean().getVersionlistxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getVersionlistxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					VisualizzaVersioniFileBean bean = new VisualizzaVersioniFileBean();

					bean.setNr(v.get(0) != null ? new Integer(v.get(0)) : new Integer(0));// N° (colonna 1; è una numero e va ordinato come tale)
					bean.setNome(v.get(1));// Nome (colonna 2).
					bean.setUriFile(v.get(2));// Uri del file
					bean.setImpronta(v.get(3));// 4: Impronta del file
					bean.setIconaAcquisitaDaScanner(v.get(10) != null && "1".equals(v.get(10)) ? "1" : "0");// Icona “Acquisita da scanner” (se colonna 11 = 1)
					bean.setCreataDa(v.get(17));// Creata da (colonna 18)
					bean.setCreataIl(v.get(18) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(18)) : null);// Creata il (colonna 19, data e ora)
					bean.setUltimoAggEffDa(v.get(19));// Ultimo agg. effettuato da (colonna 20)
					bean.setDtUltimoAggEff(v.get(20) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(20)) : null);// Ultimo agg. effettuato il
																															// (colonna 21, data e ora)
					bean.setAlgoritmo(v.get(22));// 23: Algoritmo di calcolo dell'impronta(col 4): SHA-1, SHA-256, MD5 ecc
					bean.setEncoding(v.get(23));// 24: Encoding
					bean.setDimensione(v.get(24) != null ? new Integer(v.get(24).replaceAll("\\.", "")) : new Integer(0));// Dimensione del file in KBytes
					bean.setMimetype(v.get(25));// il mimetype colonna 26
					bean.setIconaFirmata(v.get(26) != null && "1".equals(v.get(26)) ? "1" : "0");// Icona con la coccarda rossa “Firmata” (se colonna 27 =1).
					bean.setFlgConvertibilePdf(v.get(27) != null && "1".equals(v.get(27)) ? "1" : "0");// ovvero se il file è pdf o convertibile in pdf;

					data.add(bean);
				}
			}
		}

		PaginatorBean<VisualizzaVersioniFileBean> lPaginatorBean = new PaginatorBean<VisualizzaVersioniFileBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());

		return lPaginatorBean;
	}
}
