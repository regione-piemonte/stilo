/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesProcesshistorytreeBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.AttiInLavorazioneTreeNodeBean;
import it.eng.client.DmpkProcessesProcesshistorytree;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "AttiInLavorazioneTreeDatasource")
public class AttiInLavorazioneTreeDatasource extends AbstractTreeDataSource<AttiInLavorazioneTreeNodeBean> {

	@Override
	public PaginatorBean<AttiInLavorazioneTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		BigDecimal idProcessIn = StringUtils.isNotBlank(getExtraparams().get("idProcessIn")) ? new BigDecimal(getExtraparams().get("idProcessIn")) : null;
		String codTipoEventoIn = StringUtils.isNotBlank(getExtraparams().get("codTipoEventoIn")) ? getExtraparams().get("codTipoEventoIn") : null;
		String desEventoIn = StringUtils.isNotBlank(getExtraparams().get("desEventoIn")) ? getExtraparams().get("desEventoIn") : null;
		String desUserIn = StringUtils.isNotBlank(getExtraparams().get("desUserIn")) ? getExtraparams().get("desUserIn") : null;
		String desEsitoIn = StringUtils.isNotBlank(getExtraparams().get("desEsitoIn")) ? getExtraparams().get("desEsitoIn") : null;
		String dettagliEventoIn = StringUtils.isNotBlank(getExtraparams().get("dettagliEventoIn")) ? getExtraparams().get("dettagliEventoIn") : null;
		String idSelectedNodeIO = StringUtils.isNotBlank(getExtraparams().get("idSelectedNodeIO")) ? getExtraparams().get("idSelectedNodeIO") : null;
		Integer flgExplSelNodeIn = StringUtils.isNotBlank(getExtraparams().get("flgExplSelNodeIn")) ? new Integer(getExtraparams().get("flgExplSelNodeIn"))
				: null;
		String flgNextPrevNodeIn = StringUtils.isNotBlank(getExtraparams().get("flgNextPrevNodeIn")) ? getExtraparams().get("flgNextPrevNodeIn") : null;

		List<AttiInLavorazioneTreeNodeBean> data = new ArrayList<AttiInLavorazioneTreeNodeBean>();

		DmpkProcessesProcesshistorytreeBean input = new DmpkProcessesProcesshistorytreeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdprocessin(idProcessIn);
		input.setCodtipoeventoin(codTipoEventoIn);
		input.setDeseventoin(desEventoIn);
		input.setDesuserin(desUserIn);
		input.setDesesitoin(desEsitoIn);
		input.setDettaglieventoin(dettagliEventoIn);
		input.setIdselectednodeio(idSelectedNodeIO);
		input.setFlgexplselnodein(flgExplSelNodeIn);
		input.setFlgnextprevnodein(flgNextPrevNodeIn);

		DmpkProcessesProcesshistorytree processhistorytree = new DmpkProcessesProcesshistorytree();
		StoreResultBean<DmpkProcessesProcesshistorytreeBean> output = processhistorytree.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException(output);
		}

		if (output.getResultBean().getTreexmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					AttiInLavorazioneTreeNodeBean node = new AttiInLavorazioneTreeNodeBean();
					node.setIdNode(v.get(0)); // colonna 1 dell'xml
					node.setNroLivello(v.get(1) != null ? new BigDecimal(v.get(1)) : null); // colonna 2 dell'xml
					node.setTipo(v.get(2)); // colonna 3 dell'xml
					node.setNome(v.get(3)); // colonna 4 dell'xml
					node.setDescrizione(v.get(3)); // colonna 4 dell'xml
					node.setDettagli(v.get(4)); // colonna 5 dell'xml
					node.setFlgEsplodiNodo(v.get(5)); // colonna 6 dell'xml
					node.setMatchaFiltro(v.get(6)); // colonna 7 dell'xml
					node.setSelezionabile(v.get(7)); // colonna 8 dell'xml
					node.setUrlAttivita(v.get(8)); // colonna 9 dell'xml
					node.setParentId(v.get(9) != null ? v.get(9).toString() : null); // colonna 10 dell'xml
					if (node.getDettagli() != null && !"".equals(node.getDettagli())) {
						if (node.getDettagli().contains("Messaggio associato:")) {
							String[] values = node.getDettagli().split("Messaggio associato:");
							node.setMessaggio(values[1]);
							String msgHtml = node.getMessaggio().replace("<br>", "\n").replace("</br>", "\n").replace("<br/>", "\n");
							node.setMessaggio(msgHtml);
						}
					}
					data.add(node);
				}
			}
		}

		PaginatorBean<AttiInLavorazioneTreeNodeBean> lPaginatorBean = new PaginatorBean<AttiInLavorazioneTreeNodeBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
}
