/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioExplodetreenodeBean;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioGetlivpathexclassifBean;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioNavigatitolariotreeBean;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioTestshowfoldercontentallowedBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.ui.module.layout.server.titolario.datasource.bean.TitolarioTreeNodeBean;
import it.eng.client.DmpkTitolarioExplodetreenode;
import it.eng.client.DmpkTitolarioGetlivpathexclassif;
import it.eng.client.DmpkTitolarioNavigatitolariotree;
import it.eng.client.DmpkTitolarioTestshowfoldercontentallowed;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.Navigator;
import it.eng.utility.ui.module.layout.shared.bean.NavigatorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

@Datasource(id = "TitolarioTreeDatasource")
public class TitolarioTreeDatasource extends AbstractTreeDataSource<TitolarioTreeNodeBean> {

	@Override
	public PaginatorBean<TitolarioTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		Boolean isFirstLoad = StringUtils.isNotBlank(getExtraparams().get("isFirstLoad")) ? new Boolean(getExtraparams().get("isFirstLoad")) : false;
		String idRootNode = StringUtils.isNotBlank(getExtraparams().get("idRootNode")) ? getExtraparams().get("idRootNode") : null;
		String idNodeToOpen = StringUtils.isNotBlank(getExtraparams().get("idNodeToOpen")) ? getExtraparams().get("idNodeToOpen") : null;
		BigDecimal idPianoClassif = StringUtils.isNotBlank(getExtraparams().get("idPianoClassif")) ? new BigDecimal(getExtraparams().get("idPianoClassif"))
				: null;
		Integer flgSoloAttive = StringUtils.isNotBlank(getExtraparams().get("flgSoloAttive")) ? new Integer(getExtraparams().get("flgSoloAttive")) : null;
		String tsRif = StringUtils.isNotBlank(getExtraparams().get("tsRif")) ? getExtraparams().get("tsRif") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;

		String idNodeToExplImpl = null;
		Integer flgExplodedNode = null;

		List<TitolarioTreeNodeBean> data = new ArrayList<TitolarioTreeNodeBean>();

		if (isFirstLoad) {

			DmpkTitolarioNavigatitolariotreeBean input = new DmpkTitolarioNavigatitolariotreeBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdrootnodeio(idRootNode);
			input.setIdnodetoexplimplin(idNodeToExplImpl);
			input.setFlgexplodenodein(flgExplodedNode);
			input.setIdpianoclassifin(idPianoClassif);
			input.setFlgsoloattivein(flgSoloAttive);
			input.setTsrifin(tsRif);
			input.setFinalitain(finalita);

			DmpkTitolarioNavigatitolariotree navigatitolariotree = new DmpkTitolarioNavigatitolariotree();
			StoreResultBean<DmpkTitolarioNavigatitolariotreeBean> output = navigatitolariotree.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
					input);

			boolean flgMostraContenuti = false;

			idRootNode = output.getResultBean().getIdrootnodeio();

			if (output.getResultBean().getTreexmlout() != null) {
				HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
				StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						BigDecimal nroLivelloProgressivo = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1 dell'xml
						BigDecimal nroProgr = null;
						if (nroProgrXLivello.containsKey(nroLivelloProgressivo)) {
							nroProgr = nroProgrXLivello.get(nroLivelloProgressivo).add(BigDecimal.ONE);
						} else {
							nroProgr = BigDecimal.ONE;
						}
						nroProgrXLivello.put(nroLivelloProgressivo, nroProgr);
						TitolarioTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
						data.add(node);
					}
				}
			}

			List<NavigatorBean> percorso = new ArrayList<NavigatorBean>();
			if (output.getResultBean().getPercorsorootnodexmlout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getPercorsorootnodexmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						boolean isCurrentLevel = (i == (lista.getRiga().size() - 1));
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						NavigatorBean livello = new NavigatorBean();
						livello.setIdNode(v.get(0)); // colonna 1 dell'xml
						livello.setNome(v.get(1)); // colonna 2 dell'xml
						livello.setIdFolder(v.get(2)); // colonna 3 dell'xml
						if (isCurrentLevel) {
							flgMostraContenuti = testShowFolderContentAllowed(livello.getIdFolder(), idPianoClassif, flgSoloAttive, tsRif, finalita);
						}
						percorso.add(livello);
					}
				}
			}

			Navigator navigator = new Navigator();
			navigator.setPercorso(percorso);
			navigator.setFlgMostraContenuti(flgMostraContenuti);
			HashMap<String, String> altriParametri = new HashMap<String, String>();
			if (idPianoClassif != null) {
				altriParametri.put("idPianoClassif", String.valueOf(idPianoClassif));
			}
			navigator.setAltriParametri(altriParametri);

			getSession().setAttribute("TITOLARIO_NAVIGATOR", navigator);

		} else {

			BigDecimal nroProgrRootNode = StringUtils.isNotBlank(getExtraparams().get("nroProgrRootNode")) ? new BigDecimal(getExtraparams().get(
					"nroProgrRootNode")) : null;

			DmpkTitolarioExplodetreenodeBean input = new DmpkTitolarioExplodetreenodeBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdnodetoexplodein(idRootNode);
			input.setIdpianoclassifin(idPianoClassif);
			input.setFlgsoloattivein(flgSoloAttive);
			input.setTsrifin(tsRif);
			input.setFinalitain(finalita);

			DmpkTitolarioExplodetreenode explodetreenode = new DmpkTitolarioExplodetreenode();
			StoreResultBean<DmpkTitolarioExplodetreenodeBean> output = explodetreenode.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

			if (output.getResultBean().getTreexmlout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						BigDecimal nroLivelloProgressivo = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1 dell'xml
						BigDecimal nroProgr = null;
						if (nroProgrXLivello.containsKey(nroLivelloProgressivo)) {
							nroProgr = nroProgrXLivello.get(nroLivelloProgressivo).add(BigDecimal.ONE);
						} else if (i == 0) {
							nroProgr = nroProgrRootNode;
						} else {
							nroProgr = BigDecimal.ONE;
						}
						nroProgrXLivello.put(nroLivelloProgressivo, nroProgr);
						TitolarioTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
						if (i == 0 && "2".equals(node.getFlgEsplodiNodo())) {
							boolean openRootNode = StringUtils.isNotBlank(getExtraparams().get("openRootNode")) ? Boolean.valueOf(getExtraparams().get(
									"openRootNode")) : false;
							if (!openRootNode) {
								node.setFlgEsplodiNodo("1");
							}
						}
						data.add(node);
					}
				}
			}
		}

		if (idNodeToOpen != null && StringUtils.isNotBlank(idNodeToOpen)) {
			StringSplitterServer st = new StringSplitterServer(idNodeToOpen, "/");
			String idNode = "";
			while (st.hasMoreTokens()) {
				String idFolder = st.nextToken();
				idNode += "/" + idFolder;
				explodeTreeNode(idNode, data);
			}
		}

		PaginatorBean<TitolarioTreeNodeBean> lPaginatorBean = new PaginatorBean<TitolarioTreeNodeBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	private void explodeTreeNode(String idNodeToExplode, List<TitolarioTreeNodeBean> data) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		BigDecimal idPianoClassif = StringUtils.isNotBlank(getExtraparams().get("idPianoClassif")) ? new BigDecimal(getExtraparams().get("idPianoClassif"))
				: null;
		Integer flgSoloAttive = StringUtils.isNotBlank(getExtraparams().get("flgSoloAttive")) ? new Integer(getExtraparams().get("flgSoloAttive")) : null;
		String tsRif = StringUtils.isNotBlank(getExtraparams().get("tsRif")) ? getExtraparams().get("tsRif") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;

		BigDecimal nroProgrRootNode = StringUtils.isNotBlank(getExtraparams().get("nroProgrRootNode")) ? new BigDecimal(getExtraparams()
				.get("nroProgrRootNode")) : null;

		DmpkTitolarioExplodetreenodeBean input = new DmpkTitolarioExplodetreenodeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdnodetoexplodein(idNodeToExplode);
		input.setIdpianoclassifin(idPianoClassif);
		input.setFlgsoloattivein(flgSoloAttive);
		input.setTsrifin(tsRif);
		input.setFinalitain(finalita);

		DmpkTitolarioExplodetreenode explodetreenode = new DmpkTitolarioExplodetreenode();
		StoreResultBean<DmpkTitolarioExplodetreenodeBean> output = explodetreenode.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

		if (output.getResultBean().getTreexmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					BigDecimal nroLivelloProgressivo = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1 dell'xml
					BigDecimal nroProgr = null;
					if (nroProgrXLivello.containsKey(nroLivelloProgressivo)) {
						nroProgr = nroProgrXLivello.get(nroLivelloProgressivo).add(BigDecimal.ONE);
					} else if (i == 0) {
						nroProgr = nroProgrRootNode;
					} else {
						nroProgr = BigDecimal.ONE;
					}
					nroProgrXLivello.put(nroLivelloProgressivo, nroProgr);
					TitolarioTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
					if (i == 0 && "2".equals(node.getFlgEsplodiNodo())) {
						boolean openRootNode = StringUtils.isNotBlank(getExtraparams().get("openRootNode")) ? Boolean.valueOf(getExtraparams().get(
								"openRootNode")) : false;
						if (!openRootNode) {
							node.setFlgEsplodiNodo("1");
						}
					}
					if (!node.getIdNode().equals(idNodeToExplode)) {
						data.add(node);
					}
				}
			}
		}

	}

	public TitolarioTreeNodeBean buildBeanFromValoriRiga(Vector<String> v, BigDecimal nroProgr) {
		TitolarioTreeNodeBean node = new TitolarioTreeNodeBean();
		node.setNroLivello(v.get(10) != null ? new BigDecimal(v.get(10)) : null); // colonna 11 dell'xml
		node.setNroProgr(nroProgr);
		node.setIdNode(v.get(1)); // colonna 2 dell'xml
		node.setIndice(v.get(6)); // colonna 7 dell'xml
		node.setNome(node.getIndice() != null && !"".equals(node.getIndice()) ? node.getIndice() + " - " + v.get(2) : v.get(2)); // concatenazione indice e
																																	// colonna 3 dell'xml
		node.setTipo(v.get(3)); // colonna 4 dell'xml
		node.setDettagli(v.get(4)); // colonna 5 dell'xml
		node.setFlgEsplodiNodo(v.get(5)); // colonna 6 dell'xml
		if (StringUtils.isBlank(node.getIdNode()) || "/".equals(node.getIdNode())) {
			node.setIdFolder(null);
		} else {
			String idFolder = node.getIdNode().substring(node.getIdNode().lastIndexOf("/") + 1);
			node.setIdFolder(idFolder);
		}
		node.setFlgSelXFinalita(v.get(7) != null && "1".equals(v.get(7))); // colonna 8 dell'xml
		node.setFlgAttiva(v.get(14)); // colonna 15 dell'xml flag attiva/chiusa
		String parentId = node.getIdNode().substring(0, node.getIdNode().lastIndexOf("/"));
		if (parentId == null || "".equals(parentId)) {
			if (!"/".equals(node.getIdNode())) {
				parentId = "/";
			}
		}
		node.setParentId(parentId);
		return node;
	}

	public Navigator getPercorsoIniziale() throws Exception {
		return (Navigator) getSession().getAttribute("TITOLARIO_NAVIGATOR");
	}

	public Navigator calcolaPercorso(TitolarioTreeNodeBean bean) throws Exception {
		if (StringUtils.isBlank(bean.getIdNode()) || "/".equals(bean.getIdNode())) {
			bean.setIdFolder(null);
		} else {
			bean.setIdFolder(bean.getIdNode().substring(bean.getIdNode().lastIndexOf("/") + 1));
		}
		return calcolaPercorsoFromList(bean);
	}

	public Navigator calcolaPercorsoFromList(TitolarioTreeNodeBean bean) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		BigDecimal idPianoClassif = StringUtils.isNotBlank(getExtraparams().get("idPianoClassif")) ? new BigDecimal(getExtraparams().get("idPianoClassif"))
				: null;
		Integer flgSoloAttive = StringUtils.isNotBlank(getExtraparams().get("flgSoloAttive")) ? new Integer(getExtraparams().get("flgSoloAttive")) : null;
		String tsRif = StringUtils.isNotBlank(getExtraparams().get("tsRif")) ? getExtraparams().get("tsRif") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;

		BigDecimal idClassificazione = bean != null && StringUtils.isNotBlank(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null;

		DmpkTitolarioGetlivpathexclassifBean input = new DmpkTitolarioGetlivpathexclassifBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclassificazionein(idClassificazione);
		input.setIdpianoclassifin(idPianoClassif);
		input.setFlgsoloattivein(flgSoloAttive);
		input.setTsrifin(tsRif);
		input.setFinalitain(finalita);

		DmpkTitolarioGetlivpathexclassif getlivpathexclassif = new DmpkTitolarioGetlivpathexclassif();
		StoreResultBean<DmpkTitolarioGetlivpathexclassifBean> output = getlivpathexclassif.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
				input);

		List<NavigatorBean> percorso = new ArrayList<NavigatorBean>();
		if (output.getResultBean().getPercorsoxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getPercorsoxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					NavigatorBean livello = new NavigatorBean();
					livello.setIdFolder(v.get(0)); // colonna 1 dell'xml
					if (i == 0) {
						livello.setIdNode("/");
					} else {
						String idNodePrec = percorso.get(i - 1).getIdNode();
						if (!idNodePrec.endsWith("/")) {
							idNodePrec += "/";
						}
						livello.setIdNode(idNodePrec + livello.getIdFolder());
					}
					livello.setNome(v.get(1)); // colonna 2 dell'xml
					livello.setFlgMostraContenuti(v.get(2) != null && "1".equals(v.get(2))); // colonna 3 dell'xml
					percorso.add(livello);
				}
			}
		}

		Navigator navigator = new Navigator();
		navigator.setPercorso(percorso);
		navigator.setFlgMostraContenuti(output.getResultBean().getFlgshowcontentallowedout() != null
				&& output.getResultBean().getFlgshowcontentallowedout().intValue() == 1);

		return navigator;
	}

	private boolean testShowFolderContentAllowed(String idFolder, BigDecimal idPianoClassif, Integer flgSoloAttive, String tsRif, String finalita)
			throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		DmpkTitolarioTestshowfoldercontentallowedBean input = new DmpkTitolarioTestshowfoldercontentallowedBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclassificazionein(idFolder != null && !"".equals(idFolder) ? new BigDecimal(idFolder) : null);
		input.setIdpianoclassifin(idPianoClassif);
		input.setFlgsoloattivein(flgSoloAttive);
		input.setTsrifin(tsRif);
		input.setFinalitain(finalita);

		DmpkTitolarioTestshowfoldercontentallowed testshowfoldercontentallowed = new DmpkTitolarioTestshowfoldercontentallowed();
		StoreResultBean<DmpkTitolarioTestshowfoldercontentallowedBean> output = testshowfoldercontentallowed.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), input);

		return (output.getResultBean().getFlgshowcontentallowedout() == 1);
	}

}
