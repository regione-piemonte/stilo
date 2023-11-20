/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AutoCompletamentoIndirizziBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VerificaNumeroCivicoBean;
import it.eng.utility.client.toponomastica.ClientConfig;
import it.eng.utility.client.toponomastica.ClientSpringToponomastica;
import it.eng.utility.client.toponomastica.NumeroCivicoRequest;
import it.eng.utility.client.toponomastica.NumeroCivicoResponse;
import it.eng.utility.client.toponomastica.StradaRequest;
import it.eng.utility.client.toponomastica.StradaResponse;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "AurigaAutoCompletamentoIndirizziDatasource")
public class AurigaAutoCompletamentoIndirizziDatasource extends AbstractFetchDataSource<AutoCompletamentoIndirizziBean> {
	
	@Override
	public PaginatorBean<AutoCompletamentoIndirizziBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		PaginatorBean<AutoCompletamentoIndirizziBean> paginatorBean = new PaginatorBean<AutoCompletamentoIndirizziBean>();
		List<AutoCompletamentoIndirizziBean> data = new ArrayList<AutoCompletamentoIndirizziBean>();

		String indirizzo = getExtraparams().get("indirizzo") != null ? getExtraparams().get("indirizzo") : "";
		
		// Leggo le configurazioni del servizio
		String clientToponomasticaConfig = ParametriDBUtil.getParametroDB(getSession(), "XML_CONF_WS_TOPONOMATICA");
		if (StringUtils.isNotBlank(clientToponomasticaConfig) && StringUtils.isNotBlank(indirizzo) && indirizzo.length() > 3) {
			XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
			ClientConfig clientConfig = lXmlUtility.unbindXml(clientToponomasticaConfig, ClientConfig.class);
			final StradaRequest input = new StradaRequest();
			input.setNomeVia(indirizzo);
			input.setEndpoint(clientConfig.getEndpoint());
			input.setMetodo(clientConfig.getMetodoRicercaStrada());
			input.setToken(clientConfig.getToken());
		
			StradaResponse response = ClientSpringToponomastica.getClient().getStrada(input);
							
			if (response != null && StringUtils.isNotBlank(response.getResponse())) {
				String xmlLista =response.getResponse();
				
				Document doc = Jsoup.parse(xmlLista);
				Elements strade = doc.select("STRADA");
				
				for (Element strada : strade) {
					AutoCompletamentoIndirizziBean lAurigaAutoCompletamentoBean = new AutoCompletamentoIndirizziBean();
					String codiceStrada = strada.select("CODICE_STRADA").get(0).text();
					String nomeVia = strada.select("NOME_VIA").get(0).text();	
					lAurigaAutoCompletamentoBean.setCodiceStrada(codiceStrada);
					lAurigaAutoCompletamentoBean.setNomeVia(nomeVia);
					data.add(lAurigaAutoCompletamentoBean);
				}
			}
		} else if (StringUtils.isBlank(clientToponomasticaConfig)) {
			addMessage("Nessuna configurazione trovata per il client toponomastica", "", MessageType.ERROR);
		} 

		paginatorBean.setData(data);
		paginatorBean.setStartRow(0);
		paginatorBean.setEndRow(data.size());
		paginatorBean.setTotalRows(data.size());

		return paginatorBean;
	}
	
	public VerificaNumeroCivicoBean verificaNumeroCivico(VerificaNumeroCivicoBean bean) throws Exception {
		
		bean.setEsitoVerifica(false);
		// Leggo le configurazioni del servizio
		String clientToponomasticaConfig = ParametriDBUtil.getParametroDB(getSession(), "XML_CONF_WS_TOPONOMATICA");
		if (StringUtils.isNotBlank(clientToponomasticaConfig)) {
			XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
			ClientConfig clientConfig = lXmlUtility.unbindXml(clientToponomasticaConfig, ClientConfig.class);
			final NumeroCivicoRequest input = new NumeroCivicoRequest();
			input.setCodiceStrada(bean.getCodiceStrada());
			input.setNumeroCivico(bean.getNumeroCivico());
			input.setLetteraCivico(bean.getLetteraCivico());
			input.setColoreCivico(bean.getColoreCivico());
			input.setEndpoint(clientConfig.getEndpoint());
			input.setMetodo(clientConfig.getMetodoEsistenzaNumCivico());
			input.setToken(clientConfig.getToken());
		
			NumeroCivicoResponse response = ClientSpringToponomastica.getClient().getEsistenzaNumeroCivico(input);
							
			if (response != null) {
				bean.setEsitoVerifica(response.isNumeroCivicoEsiste());
			}
			
		} else {
			addMessage("Nessuna configurazione trovata per il client toponomastica", "", MessageType.ERROR);
		}
		
		return bean;
	}
}
