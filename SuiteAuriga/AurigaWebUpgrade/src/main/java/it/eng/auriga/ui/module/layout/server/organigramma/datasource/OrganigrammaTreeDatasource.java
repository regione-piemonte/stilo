/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityAdddeldestpreferitoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityExplodetreenodeBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGetlivpathexuoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityNavigastrutturaorgtreeBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTestshowfoldercontentallowedBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.OrganigrammaDestinatariPreferitiBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.OrganigrammaTreeNodeBean;
import it.eng.client.DmpkDefSecurityAdddeldestpreferito;
import it.eng.client.DmpkDefSecurityExplodetreenode;
import it.eng.client.DmpkDefSecurityGetlivpathexuo;
import it.eng.client.DmpkDefSecurityNavigastrutturaorgtree;
import it.eng.client.DmpkDefSecurityTestshowfoldercontentallowed;
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

@Datasource(id = "OrganigrammaTreeDatasource")
public class OrganigrammaTreeDatasource extends AbstractTreeDataSource<OrganigrammaTreeNodeBean> {
	
	private static final Logger log = Logger.getLogger(OrganigrammaTreeDatasource.class);

	@Override
	public PaginatorBean<OrganigrammaTreeNodeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		Boolean isFirstLoad = StringUtils.isNotBlank(getExtraparams().get("isFirstLoad")) ? new Boolean(getExtraparams().get("isFirstLoad")) : false;
		String idRootNode = StringUtils.isNotBlank(getExtraparams().get("idRootNode")) ? getExtraparams().get("idRootNode") : null;
		Integer flgIncludiUtenti = StringUtils.isNotBlank(getExtraparams().get("flgIncludiUtenti")) ? new Integer(getExtraparams().get("flgIncludiUtenti"))
				: null;
		Integer flgNoDatiRootNode = StringUtils.isNotBlank(getExtraparams().get("flgNoDatiRootNode")) ? new Integer(getExtraparams().get("flgNoDatiRootNode"))
				: null;
		String tipoRelUtentiConUo = StringUtils.isNotBlank(getExtraparams().get("tipoRelUtentiConUo")) ? getExtraparams().get("tipoRelUtentiConUo") : null;
		String idNodeToOpen = StringUtils.isNotBlank(getExtraparams().get("idNodeToOpen")) ? getExtraparams().get("idNodeToOpen") : null;
		BigDecimal idOrganigramma = StringUtils.isNotBlank(getExtraparams().get("idOrganigramma")) ? new BigDecimal(getExtraparams().get("idOrganigramma"))
				: null;
		Integer flgSoloAttive = StringUtils.isNotBlank(getExtraparams().get("flgSoloAttive")) ? new Integer(getExtraparams().get("flgSoloAttive")) : null;
		String tsRif = StringUtils.isNotBlank(getExtraparams().get("tsRif")) ? getExtraparams().get("tsRif") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;
		String idUd = StringUtils.isNotBlank(getExtraparams().get("idUd")) ? getExtraparams().get("idUd") : null;
		String idEmail = StringUtils.isNotBlank(getExtraparams().get("idEmail")) ? getExtraparams().get("idEmail") : null;

		String idNodeToExplImpl = null;
		Integer flgExplodedNode = null;

		List<OrganigrammaTreeNodeBean> data = new ArrayList<OrganigrammaTreeNodeBean>();

		if (isFirstLoad) {

			DmpkDefSecurityNavigastrutturaorgtreeBean input = new DmpkDefSecurityNavigastrutturaorgtreeBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdrootnodeio(idRootNode);
			input.setIdnodetoexplimplin(idNodeToExplImpl);
			input.setFlgexplodenodein(flgExplodedNode);
			input.setFlgincludiutentiin(flgIncludiUtenti);
			input.setFlgnodatirootnodein(flgNoDatiRootNode);
			input.setTiporelutenticonuoin(tipoRelUtentiConUo);
			input.setIdorganigrammaio(idOrganigramma);
			input.setFlgsoloattivein(flgSoloAttive);
			input.setTsrifin(tsRif);
			input.setFinalitain(finalita);
			if (StringUtils.isNotBlank(idEmail)) {
				input.setTyobjxfinalitain("E");
				input.setIdobjxfinalitain(idEmail);
			} else if (StringUtils.isNotBlank(idUd)) {
				input.setTyobjxfinalitain("U");
				input.setIdobjxfinalitain(idUd);
			}

			DmpkDefSecurityNavigastrutturaorgtree navigarepositorytree = new DmpkDefSecurityNavigastrutturaorgtree();
			StoreResultBean<DmpkDefSecurityNavigastrutturaorgtreeBean> output = navigarepositorytree.execute(getLocale(),
					AurigaUserUtil.getLoginInfo(getSession()), input);

			boolean flgMostraContenuti = false;

			idRootNode = output.getResultBean().getIdrootnodeio();
			idOrganigramma = output.getResultBean().getIdorganigrammaio();

			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				throw new StoreException(output);
			}

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
						OrganigrammaTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
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
							flgMostraContenuti = testShowFolderContentAllowed(livello.getIdFolder(), flgIncludiUtenti, tipoRelUtentiConUo, idOrganigramma,
									flgSoloAttive, tsRif, finalita, idUd);
						}
						String codRapidoUo = v.get(4); // colonna 5 dell'xml
						String nroLivello = "0";
						if (StringUtils.isNotBlank(codRapidoUo)) {
							int pos = codRapidoUo.lastIndexOf(".");
							if (pos == -1) {
								nroLivello = "1";
							} else {
								StringSplitterServer st = new StringSplitterServer(codRapidoUo, ".");
								nroLivello = "" + st.countTokens();
							}
						}
						HashMap<String, String> altriAttributi = new HashMap<String, String>();
						altriAttributi.put("nroLivello", nroLivello);
						altriAttributi.put("codRapidoUo", codRapidoUo);
						livello.setAltriAttributi(altriAttributi);
						percorso.add(livello);
					}
				}
			}

			Navigator navigator = new Navigator();
			navigator.setPercorso(percorso);
			navigator.setFlgMostraContenuti(flgMostraContenuti);
			HashMap<String, String> altriParametri = new HashMap<String, String>();
			if (idOrganigramma != null) {
				altriParametri.put("idOrganigramma", String.valueOf(idOrganigramma));
			}
			navigator.setAltriParametri(altriParametri);

			getSession().setAttribute("ORGANIGRAMMA_NAVIGATOR", navigator);

		} else {

			BigDecimal nroProgrRootNode = StringUtils.isNotBlank(getExtraparams().get("nroProgrRootNode")) ? new BigDecimal(getExtraparams().get(
					"nroProgrRootNode")) : null;

			DmpkDefSecurityExplodetreenodeBean input = new DmpkDefSecurityExplodetreenodeBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIdnodetoexplodein(idRootNode);
			input.setFlgincludiutentiin(flgIncludiUtenti);
			input.setTiporelutenticonuoin(tipoRelUtentiConUo);
			input.setIdorganigrammain(idOrganigramma);
			input.setFlgsoloattivein(flgSoloAttive);
			input.setTsrifin(tsRif);
			input.setFinalitain(finalita);
			if (StringUtils.isNotBlank(idUd)) {
				input.setTyobjxfinalitain("U");
				input.setIdobjxfinalitain(idUd);
			}
			
			if (StringUtils.isNotBlank(idEmail)) {
				input.setTyobjxfinalitain("E");
				input.setIdobjxfinalitain(idEmail);
			}

			DmpkDefSecurityExplodetreenode explodetreenode = new DmpkDefSecurityExplodetreenode();
			StoreResultBean<DmpkDefSecurityExplodetreenodeBean> output = explodetreenode.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

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
						OrganigrammaTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
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

		PaginatorBean<OrganigrammaTreeNodeBean> lPaginatorBean = new PaginatorBean<OrganigrammaTreeNodeBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	private void explodeTreeNode(String idNodeToExplode, List<OrganigrammaTreeNodeBean> data) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		Integer flgIncludiUtenti = StringUtils.isNotBlank(getExtraparams().get("flgIncludiUtenti")) ? new Integer(getExtraparams().get("flgIncludiUtenti"))
				: null;
		String tipoRelUtentiConUo = StringUtils.isNotBlank(getExtraparams().get("tipoRelUtentiConUo")) ? getExtraparams().get("tipoRelUtentiConUo") : null;
		BigDecimal idOrganigramma = StringUtils.isNotBlank(getExtraparams().get("idOrganigramma")) ? new BigDecimal(getExtraparams().get("idOrganigramma"))
				: null;
		Integer flgSoloAttive = StringUtils.isNotBlank(getExtraparams().get("flgSoloAttive")) ? new Integer(getExtraparams().get("flgSoloAttive")) : null;
		String tsRif = StringUtils.isNotBlank(getExtraparams().get("tsRif")) ? getExtraparams().get("tsRif") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;
		String idUd = StringUtils.isNotBlank(getExtraparams().get("idUd")) ? getExtraparams().get("idUd") : null;

		BigDecimal nroProgrRootNode = StringUtils.isNotBlank(getExtraparams().get("nroProgrRootNode")) ? new BigDecimal(getExtraparams()
				.get("nroProgrRootNode")) : null;

		DmpkDefSecurityExplodetreenodeBean input = new DmpkDefSecurityExplodetreenodeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdnodetoexplodein(idNodeToExplode);
		input.setFlgincludiutentiin(flgIncludiUtenti);
		input.setTiporelutenticonuoin(tipoRelUtentiConUo);
		input.setIdorganigrammain(idOrganigramma);
		input.setFlgsoloattivein(flgSoloAttive);
		input.setTsrifin(tsRif);
		input.setFinalitain(finalita);
		if (StringUtils.isNotBlank(idUd)) {
			input.setTyobjxfinalitain("U");
			input.setIdobjxfinalitain(idUd);
		}

		DmpkDefSecurityExplodetreenode explodetreenode = new DmpkDefSecurityExplodetreenode();
		StoreResultBean<DmpkDefSecurityExplodetreenodeBean> output = explodetreenode.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

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
					OrganigrammaTreeNodeBean node = buildBeanFromValoriRiga(v, nroProgr);
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

	public OrganigrammaTreeNodeBean buildBeanFromValoriRiga(Vector<String> v, BigDecimal nroProgr) {
		OrganigrammaTreeNodeBean node = new OrganigrammaTreeNodeBean();
		node.setNroLivello(v.get(18) != null ? new BigDecimal(v.get(18)) : null); // colonna 19 dell'xml
		node.setNroProgr(nroProgr);
		node.setIdNode(v.get(1)); // colonna 2 dell'xml
		node.setNome(v.get(2)); // colonna 3 dell'xml
		node.setTipo(v.get(3)); // colonna 4 dell'xml
		node.setCodRapidoUo(v.get(13)); // colonna 14 dell'xml
		if (node.getTipo().startsWith("UO")) {
			node.setDescrUoSvUt(v.get(14)); // colonna 15 dell'xml
		} else if (node.getTipo().startsWith("UT") || node.getTipo().startsWith("SV")) {
			node.setDescrUoSvUt(v.get(15)); // colonna 16 dell'xml
			node.setCodRapidoSvUt(v.get(24)); // colonna 25 dell'xml
			node.setCodFiscale(v.get(25)); // colonna 26 dell'xml
			if (node.getTipo().substring(node.getTipo().indexOf("_") + 1) != null)
				node.setTipoRelUtenteUo(node.getTipo().substring(node.getTipo().indexOf("_") + 1));
		}
		node.setIdRubrica(v.get(26)); // colonna 27 dell'xml
		node.setDettagli(v.get(4)); // colonna 5 dell'xml
		node.setFlgEsplodiNodo(v.get(5)); // colonna 6 dell'xml
		if (StringUtils.isBlank(node.getIdNode()) || "/".equals(node.getIdNode())) {
			node.setIdFolder(null);
		} else {
			String idFolder = node.getIdNode().substring(node.getIdNode().lastIndexOf("/") + 1);
			node.setIdFolder(idFolder);
		}
		node.setIdUo(v.get(8)); // colonna 9 xml
		node.setIdUser(v.get(9)); // colonna 10 xml
		node.setIdUoSvUt(v.get(6)); // colonna 7 dell'xml
		node.setFlgSelXFinalita(v.get(7) != null && "1".equals(v.get(7))); // colonna 8 dell'xml
		String parentId = node.getIdNode().substring(0, node.getIdNode().lastIndexOf("/"));
		if (parentId == null || "".equals(parentId)) {
			if (!"/".equals(node.getIdNode())) {
				parentId = "/";
			}
		}
		node.setDataInizioValidita(v.get(19)); // colonna 20 dell'xml;
		node.setParentId(parentId);
		node.setFlgPuntoProtocollo(v.get(29) != null && "1".equals(v.get(29))); // colonna 30 dell'xml
		node.setUsername(v.get(30));// USERNAME: colonna 31 dell'xml 
		node.setFlgDestinatarioNeiPreferiti(v.get(31) != null && "1".equals(v.get(31)));  // col.32 : la UO/scrivania e' nei preferiti (1 = si, 0 = no)
		node.setFlgSelXAssegnazione(v.get(32)); // colonna 33 dell'xml
		node.setFlgProtocollista(v.get(33) != null && "1".equals(v.get(33))); // colonna 34 dell'xml
		node.setFlgPuntoRaccoltaEmail(v.get(34) != null && "1".equals(v.get(34))); //colonna 35 dell'xml
		node.setAbilitaUoProtEntrata(v.get(36) != null && "1".equals(v.get(36))); //colonna 37 dell'xml
		node.setAbilitaUoProtUscita(v.get(37)  != null && "1".equals(v.get(37))); //colonna 38 dell'xml
		
		return node;
	}

	public Navigator getPercorsoIniziale() throws Exception {
		return (Navigator) getSession().getAttribute("ORGANIGRAMMA_NAVIGATOR");
	}

	public Navigator calcolaPercorso(OrganigrammaTreeNodeBean bean) throws Exception {
		if (StringUtils.isBlank(bean.getIdNode()) || "/".equals(bean.getIdNode())) {
			bean.setIdFolder(null);
		} else {
			bean.setIdFolder(bean.getIdNode().substring(bean.getIdNode().lastIndexOf("/") + 1));
		}
		return calcolaPercorsoFromList(bean);
	}

//	public OrganigrammaOutBean spostaUtenti(OrganigrammaCopyPasteBean bean) throws Exception {
//
//		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
//
//		OrganigrammaTreeNodeBean copyNode = bean.getCopyNode();
//		OrganigrammaTreeNodeBean pasteNode = bean.getPasteNode();
//
//		OrganigrammaInBean copy = new OrganigrammaInBean();
//		copy.setDataInizioValidita(copyNode.getDataInizioValidita());
//		copy.setIdUO(copyNode.getIdUo());
//		copy.setIdUser(copyNode.getIdUser());
//		copy.setTipoRelazioneUtente(copyNode.getTipoRelUtenteUo());
//
//		OrganigrammaInBean paste = new OrganigrammaInBean();
//		paste.setDataInizioValidita(pasteNode.getDataInizioValidita());
//		paste.setIdUO(pasteNode.getIdUo());
//		paste.setIdUser(pasteNode.getIdUser());
//		paste.setTipoRelazioneUtente(pasteNode.getTipoRelUtenteUo());
//
//		OrganigrammaOutBean outBean = new OrganigrammaOutBean();
//		GestioneOrganigramma gestioneOrganigramma = new GestioneOrganigramma();
//		outBean = gestioneOrganigramma.spostautenti(getLocale(), lAurigaLoginBean, copy, paste);
//
//		return outBean;
//
//	}

	public Navigator calcolaPercorsoFromList(OrganigrammaTreeNodeBean bean) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		Integer flgIncludiUtenti = StringUtils.isNotBlank(getExtraparams().get("flgIncludiUtenti")) ? new Integer(getExtraparams().get("flgIncludiUtenti"))
				: null;
		String tipoRelUtentiConUo = StringUtils.isNotBlank(getExtraparams().get("tipoRelUtentiConUo")) ? getExtraparams().get("tipoRelUtentiConUo") : null;
		BigDecimal idOrganigramma = StringUtils.isNotBlank(getExtraparams().get("idOrganigramma")) ? new BigDecimal(getExtraparams().get("idOrganigramma"))
				: null;
		Integer flgSoloAttive = StringUtils.isNotBlank(getExtraparams().get("flgSoloAttive")) ? new Integer(getExtraparams().get("flgSoloAttive")) : null;
		String tsRif = StringUtils.isNotBlank(getExtraparams().get("tsRif")) ? getExtraparams().get("tsRif") : null;
		String finalita = StringUtils.isNotBlank(getExtraparams().get("finalita")) ? getExtraparams().get("finalita") : null;
		String idUd = StringUtils.isNotBlank(getExtraparams().get("idUd")) ? getExtraparams().get("idUd") : null;

		BigDecimal idUo = bean != null && StringUtils.isNotBlank(bean.getIdFolder()) ? new BigDecimal(bean.getIdFolder()) : null;

		DmpkDefSecurityGetlivpathexuoBean input = new DmpkDefSecurityGetlivpathexuoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIduoin(idUo);
		input.setFlgincludiutentiin(flgIncludiUtenti);
		input.setTiporelutenticonuoin(tipoRelUtentiConUo);
		input.setIdorganigrammain(idOrganigramma);
		input.setFlgsoloattivein(flgSoloAttive);
		input.setTsrifin(tsRif);
		input.setFinalitain(finalita);

		DmpkDefSecurityGetlivpathexuo getlivpathexuo = new DmpkDefSecurityGetlivpathexuo();
		StoreResultBean<DmpkDefSecurityGetlivpathexuoBean> output = getlivpathexuo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);

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
					String codRapidoUo = v.get(4); // colonna 5 dell'xml
					String nroLivello = "0";
					if (StringUtils.isNotBlank(codRapidoUo)) {
						int pos = codRapidoUo.lastIndexOf(".");
						if (pos == -1) {
							nroLivello = "1";
						} else {
							StringSplitterServer st = new StringSplitterServer(codRapidoUo, ".");
							nroLivello = "" + st.countTokens();
						}
					}
					HashMap<String, String> altriAttributi = new HashMap<String, String>();
					altriAttributi.put("nroLivello", nroLivello);
					altriAttributi.put("codRapidoUo", codRapidoUo);
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

	private boolean testShowFolderContentAllowed(String idFolder, Integer flgIncludiUtenti, String tipoRelUtentiConUo, BigDecimal idOrganigramma,
			Integer flgSoloAttive, String tsRif, String finalita, String idUd) throws Exception {

		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();

		DmpkDefSecurityTestshowfoldercontentallowedBean input = new DmpkDefSecurityTestshowfoldercontentallowedBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIduoin(idFolder != null && !"".equals(idFolder) ? new BigDecimal(idFolder) : null);
		input.setFlgincludiutentiin(flgIncludiUtenti);
		input.setTiporelutenticonuoin(tipoRelUtentiConUo);
		input.setIdorganigrammain(idOrganigramma);
		input.setFlgsoloattivein(flgSoloAttive);
		input.setTsrifin(tsRif);
		input.setFinalitain(finalita);

		DmpkDefSecurityTestshowfoldercontentallowed testshowfoldercontentallowed = new DmpkDefSecurityTestshowfoldercontentallowed();
		StoreResultBean<DmpkDefSecurityTestshowfoldercontentallowedBean> output = testshowfoldercontentallowed.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), input);

		return (output.getResultBean().getFlgshowcontentallowedout() == 1);
	}
	
	public OrganigrammaTreeNodeBean getIdNodeByIdFolder(OrganigrammaTreeNodeBean bean) throws Exception {
		if (bean != null && bean.getIdFolder() != null && !"".equals(bean.getIdFolder())) {
			StringSplitterServer st = new StringSplitterServer(bean.getIdFolder(), "|*|");
			String idNode = null;
			while (st.hasMoreTokens()) {
				String idFolder = st.nextToken();
				if(idFolder.equals("/")) {
					if (idNode == null) {
						idNode = "/";
					} else {
						idNode += "|*|/";
					}	
				} else {
					OrganigrammaTreeNodeBean folderBean = new OrganigrammaTreeNodeBean();
					folderBean.setIdFolder(idFolder);
					Navigator navigator = calcolaPercorsoFromList(folderBean);
					NavigatorBean currentNode = navigator.getPercorso().get(navigator.getPercorso().size() - 1);
					if (idNode == null) {
						idNode = currentNode.getIdNode();
					} else {
						idNode += "|*|" + currentNode.getIdNode();
					}
				}
			}
			bean.setIdNode(idNode);
		}
		return bean;
	}
	
	public OrganigrammaDestinatariPreferitiBean aggiungiTogliDestinatariPreferiti(OrganigrammaDestinatariPreferitiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkDefSecurityAdddeldestpreferitoBean input = new DmpkDefSecurityAdddeldestpreferitoBean();

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		String flgTipo = "";
		if (bean.getTipo()!= null ){
			if (bean.getTipo().startsWith("SV")) {
				flgTipo = "SV";
			}
			else if (bean.getTipo().startsWith("UO")) {
				flgTipo = "UO";
			} 
			else if (bean.getTipo().startsWith("UT")) {
				flgTipo = "UT";
			}
		}
		input.setFlgtipoin(flgTipo);
		input.setIddestprefin((bean.getIdDestPref() != null ? new BigDecimal(bean.getIdDestPref()) : null));
		input.setFlgadddelin(bean.getFlgAddDel());
		
		DmpkDefSecurityAdddeldestpreferito service = new DmpkDefSecurityAdddeldestpreferito();
		StoreResultBean<DmpkDefSecurityAdddeldestpreferitoBean> output = service.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}
	
}