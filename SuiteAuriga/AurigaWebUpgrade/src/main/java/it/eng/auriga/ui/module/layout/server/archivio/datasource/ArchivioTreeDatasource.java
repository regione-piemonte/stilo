/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExplodetreenodeBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetidnodoclassificaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetlivpathexfolderBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetlivpathexnodoBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreNavigarepositorytreeBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTestshowfoldercontentallowedBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioTreeNodeBean;
import it.eng.client.DmpkCoreExplodetreenode;
import it.eng.client.DmpkCoreGetidnodoclassifica;
import it.eng.client.DmpkCoreGetlivpathexfolder;
import it.eng.client.DmpkCoreGetlivpathexnodo;
import it.eng.client.DmpkCoreNavigarepositorytree;
import it.eng.client.DmpkCoreTestshowfoldercontentallowed;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractTreeDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.Navigator;
import it.eng.utility.ui.module.layout.shared.bean.NavigatorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "ArchivioTreeDatasource")
public class ArchivioTreeDatasource extends AbstractTreeDataSource<ArchivioTreeNodeBean> {

	private static final Logger log = Logger.getLogger(ArchivioTreeDatasource.class);

	@Override
	public PaginatorBean<ArchivioTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		Boolean isFirstLoad = StringUtils.isNotBlank(getExtraparams().get("isFirstLoad")) ? new Boolean(getExtraparams().get("isFirstLoad")) : false;
		String idRootNode = StringUtils.isNotBlank(getExtraparams().get("idRootNode")) ? getExtraparams().get("idRootNode") : null;
		String idNodeToOpen = StringUtils.isNotBlank(getExtraparams().get("idNodeToOpen")) ? getExtraparams().get("idNodeToOpen") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;

		String idNodeToExplImpl = null;
		Integer flgExplodedNode = null;

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		List<ArchivioTreeNodeBean> data = new ArrayList<ArchivioTreeNodeBean>();

		if (isFirstLoad) {

			DmpkCoreNavigarepositorytreeBean input = new DmpkCoreNavigarepositorytreeBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgescludiudin(1);
			input.setIdrootnodeio(idRootNode);
			input.setIdnodetoexplimplin(idNodeToExplImpl);
			input.setFlgexplodenodein(flgExplodedNode);
			input.setFinalitain(finalita);
			// input.setNonsoloinarchiviocorrentein(null);

			DmpkCoreNavigarepositorytree navigarepositorytree = new DmpkCoreNavigarepositorytree();
			StoreResultBean<DmpkCoreNavigarepositorytreeBean> output = navigarepositorytree.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
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
						BigDecimal nroLivello = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1 dell'xml
						BigDecimal nroProgr = null;
						if (nroProgrXLivello.containsKey(nroLivello)) {
							nroProgr = nroProgrXLivello.get(nroLivello).add(BigDecimal.ONE);
						} else {
							nroProgr = BigDecimal.ONE;
						}
						nroProgrXLivello.put(nroLivello, nroProgr);
						ArchivioTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
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
						Map<String, String> altriAttributi = new HashMap<String, String>();
						altriAttributi.put("showNewFolderButton", v.get(4)); // colonna 5 dell'xml
						altriAttributi.put("showNewUdButton", v.get(5)); // colonna 6 dell'xml
						altriAttributi.put("showNewFolderCustomButton", v.get(6)); // colonna 7 dell'xml
						livello.setAltriAttributi(altriAttributi);
						if (isCurrentLevel) {
							flgMostraContenuti = testShowFolderContentAllowed(livello.getIdFolder());
						}
						percorso.add(livello);
					}
				}
			}

			Navigator navigator = new Navigator();
			navigator.setPercorso(percorso);
			navigator.setFlgMostraContenuti(flgMostraContenuti);

			getSession().setAttribute("ARCHIVIO_NAVIGATOR", navigator);

		} else {

			BigDecimal nroProgrRootNode = StringUtils.isNotBlank(getExtraparams().get("nroProgrRootNode")) ? new BigDecimal(getExtraparams().get(
					"nroProgrRootNode")) : null;

			DmpkCoreExplodetreenodeBean input = new DmpkCoreExplodetreenodeBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgescludiudin(1);
			input.setIdnodetoexplodein(idRootNode);
			input.setFinalitain(finalita);
			// input.setNonsoloinarchiviocorrentein(null);

			DmpkCoreExplodetreenode explodetreenode = new DmpkCoreExplodetreenode();
			StoreResultBean<DmpkCoreExplodetreenodeBean> output = explodetreenode.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

			if (output.getResultBean().getTreexmlout() != null) {
				StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						BigDecimal nroLivello = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1 dell'xml
						BigDecimal nroProgr = null;
						if (nroProgrXLivello.containsKey(nroLivello)) {
							nroProgr = nroProgrXLivello.get(nroLivello).add(BigDecimal.ONE);
						} else if (i == 0) {
							nroProgr = nroProgrRootNode;
						} else {
							nroProgr = BigDecimal.ONE;
						}
						nroProgrXLivello.put(nroLivello, nroProgr);
						ArchivioTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
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

		PaginatorBean<ArchivioTreeNodeBean> lPaginatorBean = new PaginatorBean<ArchivioTreeNodeBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	private void explodeTreeNode(String idNodeToExplode, List<ArchivioTreeNodeBean> data) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		String idRootNode = StringUtils.isNotBlank(getExtraparams().get("idRootNode")) ? getExtraparams().get("idRootNode") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;

		BigDecimal nroProgrRootNode = StringUtils.isNotBlank(getExtraparams().get("nroProgrRootNode")) ? new BigDecimal(getExtraparams()
				.get("nroProgrRootNode")) : null;

		DmpkCoreExplodetreenodeBean input = new DmpkCoreExplodetreenodeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgescludiudin(1);
		input.setIdnodetoexplodein(idRootNode);
		input.setFinalitain(finalita);
		// input.setNonsoloinarchiviocorrentein(null);

		DmpkCoreExplodetreenode explodetreenode = new DmpkCoreExplodetreenode();
		StoreResultBean<DmpkCoreExplodetreenodeBean> output = explodetreenode.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

		if (output.getResultBean().getTreexmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getTreexmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				HashMap<BigDecimal, BigDecimal> nroProgrXLivello = new HashMap<BigDecimal, BigDecimal>();
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					BigDecimal nroLivello = v.get(0) != null ? new BigDecimal(v.get(0)) : null; // colonna 1 dell'xml
					BigDecimal nroProgr = null;
					if (nroProgrXLivello.containsKey(nroLivello)) {
						nroProgr = nroProgrXLivello.get(nroLivello).add(BigDecimal.ONE);
					} else if (i == 0) {
						nroProgr = nroProgrRootNode;
					} else {
						nroProgr = BigDecimal.ONE;
					}
					nroProgrXLivello.put(nroLivello, nroProgr);
					ArchivioTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
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

	public ArchivioTreeNodeBean buildBeanFromValoriRiga(Vector<String> v, BigDecimal nroProgr) {
		ArchivioTreeNodeBean node = new ArchivioTreeNodeBean();
		node.setNroLivello(v.get(0) != null ? new BigDecimal(v.get(0)) : null);
		node.setNroProgr(nroProgr);
		node.setIdNode(v.get(1)); // colonna 2 dell'xml
		node.setNome(v.get(2)); // colonna 3 dell'xml
		node.setTipo(v.get(3)); // colonna 4 dell'xml
		node.setDettagli(v.get(4)); // colonna 5 dell'xml
		node.setFlgEsplodiNodo(v.get(5)); // colonna 6 dell'xml
		node.setIdFolder(v.get(6)); // colonna 7 dell'xml
		node.setFlgSelXFinalita(v.get(7) != null && "1".equals(v.get(7))); // colonna 8 dell'xml
		node.setFlgPreferiti(v.get(8) != null ? "1".equals(v.get(8)) : null); // colonna 9 dell'xml
		node.setLivelloRiservatezza(v.get(17)); // colonna 18 dell'xml
		node.setPercorsoNome(v.get(18)); // colonna 19 dell'xml
		node.setNroSecondario(v.get(19)); // colonna 20 dell'xml
		node.setEstremiDocCapofila(v.get(20)); // colonna 21 dell'xml
		String info = v.get(16); // colonna 17 dell'xml
		if (info != null && !"".equals(info)) {
			StringSplitterServer st = new StringSplitterServer(info, "**");
			int n = 0;
			while (st.hasMoreTokens()) {
				if (n == 0) {
					node.setNomeFascicolo(st.nextToken().toString());
				} else if (n == 1) {
					node.setAnnoFascicolo(new Integer(st.nextToken().toString()));
				} else if (n == 2) {
					node.setIdClassifica(st.nextToken().toString());
				} else if (n == 3) {
					node.setIndiceClassifica(st.nextToken().toString());
				} else if (n == 4) {
					node.setNroFascicolo(st.nextToken().toString());
				} else if (n == 5) {
					node.setNroSottofascicolo(st.nextToken().toString());
				} else if (n == 6) {
					node.setNroInserto(st.nextToken().toString());
				}
				n++;
			}
		}
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
		return (Navigator) getSession().getAttribute("ARCHIVIO_NAVIGATOR");
	}

	public Navigator calcolaPercorso(ArchivioTreeNodeBean bean) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();
		String idNode = bean != null ? bean.getIdNode() : null;

		DmpkCoreGetlivpathexnodoBean input = new DmpkCoreGetlivpathexnodoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdnodoin(idNode);
		input.setFlgfolderudin(null);

		DmpkCoreGetlivpathexnodo getlivpathexnodo = new DmpkCoreGetlivpathexnodo();
		StoreResultBean<DmpkCoreGetlivpathexnodoBean> output = getlivpathexnodo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

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
					Map<String, String> altriAttributi = new HashMap<String, String>();
					altriAttributi.put("showNewFolderButton", v.get(3)); // colonna 4 dell'xml
					altriAttributi.put("showNewUdButton", v.get(4)); // colonna 5 dell'xml
					altriAttributi.put("showNewFolderCustomButton", v.get(5)); // colonna 6 dell'xml
					livello.setAltriAttributi(altriAttributi);
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

	public ArchivioTreeNodeBean getIdNodeByIdFolder(ArchivioTreeNodeBean bean) throws Exception {
		if (bean != null && bean.getIdFolder() != null && !"".equals(bean.getIdFolder())) {
			StringSplitterServer st = new StringSplitterServer(bean.getIdFolder(), "|*|");
			String idNode = null;
			while (st.hasMoreTokens()) {
				String idFolder = st.nextToken();
				ArchivioTreeNodeBean folderBean = new ArchivioTreeNodeBean();
				folderBean.setIdFolder(idFolder);
				Navigator navigator = calcolaPercorsoFromList(folderBean);
				NavigatorBean currentNode = navigator.getPercorso().get(navigator.getPercorso().size() - 1);
				if (idNode == null) {
					idNode = currentNode.getIdNode();
				} else {
					idNode += "|*|" + currentNode.getIdNode();
				}
			}
			bean.setIdNode(idNode);
		}
		return bean;
	}

	public Navigator calcolaPercorsoFromList(ArchivioTreeNodeBean bean) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();
		// String idNode = bean != null ? bean.getIdNode() : null;
		String idFolder = bean != null ? bean.getIdFolder() : null;

		DmpkCoreGetlivpathexfolderBean input = new DmpkCoreGetlivpathexfolderBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		// input.setIdnodopercorsoricercain(idNode);
		input.setIdnodopercorsoricercain("/");
		input.setIdfolderin(idFolder != null && !"".equals(idFolder) ? new BigDecimal(idFolder) : null);
		input.setFlgfolderudin(null);

		DmpkCoreGetlivpathexfolder getlivpathexfolder = new DmpkCoreGetlivpathexfolder();
		StoreResultBean<DmpkCoreGetlivpathexfolderBean> output = getlivpathexfolder.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

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
					Map<String, String> altriAttributi = new HashMap<String, String>();
					altriAttributi.put("showNewFolderButton", v.get(3)); // colonna 4 dell'xml
					altriAttributi.put("showNewUdButton", v.get(4)); // colonna 5 dell'xml
					altriAttributi.put("showNewFolderCustomButton", v.get(5)); // colonna 6 dell'xml
					livello.setAltriAttributi(altriAttributi);
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

	private boolean testShowFolderContentAllowed(String idFolder) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		DmpkCoreTestshowfoldercontentallowedBean input = new DmpkCoreTestshowfoldercontentallowedBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdfolderin(idFolder != null && !"".equals(idFolder) ? new BigDecimal(idFolder) : null);
		input.setFlgfolderudin(null);

		DmpkCoreTestshowfoldercontentallowed testshowfoldercontentallowed = new DmpkCoreTestshowfoldercontentallowed();
		StoreResultBean<DmpkCoreTestshowfoldercontentallowedBean> output = testshowfoldercontentallowed.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), input);

		return (output.getResultBean().getFlgshowcontentallowedout() == 1);
	}

	public ArchivioTreeNodeBean getIdNodoClassifica(ArchivioTreeNodeBean bean) throws Exception {

		BigDecimal idClassifica = (bean != null && bean.getIdClassifica() != null) ? new BigDecimal(bean.getIdClassifica()) : null;
		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		DmpkCoreGetidnodoclassificaBean input = new DmpkCoreGetidnodoclassificaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdclassificazionein(idClassifica);

		DmpkCoreGetidnodoclassifica getidnodoclassifica = new DmpkCoreGetidnodoclassifica();
		StoreResultBean<DmpkCoreGetidnodoclassificaBean> output = getidnodoclassifica.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

		if (output.getResultBean().getIdnodoout() != null) {
			bean.setIdNode(output.getResultBean().getIdnodoout());
			return bean;
		}

		return null;
	}

}
