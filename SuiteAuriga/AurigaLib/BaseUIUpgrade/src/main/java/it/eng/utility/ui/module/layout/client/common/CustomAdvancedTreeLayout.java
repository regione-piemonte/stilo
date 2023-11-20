/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.bean.CacheLevelBean;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;

public class CustomAdvancedTreeLayout extends CustomLayout implements TreeSectionVisibility{
	
	public final int CUSTOM_ADVANCED_TREE_SETION_DEFAULT_WIDTH = 250;

	protected GWTRestDataSource treedatasource;

	protected final CustomAdvancedTree tree;

	protected Boolean flgSoloFolder;
	protected String idRootNode;

	protected VLayout navigationLayout;

	protected final ToolStrip navigationToolStrip;
	// protected final TextItem percorsoItem;
	protected final NavigatorLayout navigator;
	protected final ToolStripButton vaiAlLivSupButton;
//	protected final ToolStripButton cercaPercorsoButton;

	protected AdvancedCriteria initialCriteria;
	protected AdvancedCriteria defaultCriteria;
	protected Boolean flgMostraContenuti;
	protected Map<String, String> altriParametri;

	protected String idNodeToOpen;
	
	public CustomAdvancedTreeLayout(String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode) {
		this(nomeEntita, nomeEntita, pTreeDatasource, pDatasource, tree, filter, list, detail, finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, null,
				null);
	}

	public CustomAdvancedTreeLayout(String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, Boolean showOnlyDetail) {
		this(nomeEntita, nomeEntita, pTreeDatasource, pDatasource, tree, filter, list, detail, finalita, flgSelezioneSingola, flgSoloFolder, idRootNode,
				showOnlyDetail, null);
	}

	public CustomAdvancedTreeLayout(String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, AdvancedCriteria initialCriteria) {
		this(nomeEntita, nomeEntita, pTreeDatasource, pDatasource, tree, filter, list, detail, finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, null,
				initialCriteria);
	}

	public CustomAdvancedTreeLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode) {
		this(nomePortlet, nomeEntita, pTreeDatasource, pDatasource, tree, filter, list, detail, finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, null,
				null);
	}

	public CustomAdvancedTreeLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, Boolean showOnlyDetail) {
		this(nomePortlet, nomeEntita, pTreeDatasource, pDatasource, tree, filter, list, detail, finalita, flgSelezioneSingola, flgSoloFolder, idRootNode,
				showOnlyDetail, null);
	}

	public CustomAdvancedTreeLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, AdvancedCriteria initialCriteria) {
		this(nomePortlet, nomeEntita, pTreeDatasource, pDatasource, tree, filter, list, detail, finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, null,
				initialCriteria);
	}

	public CustomAdvancedTreeLayout(String nomePortlet, String nomeEntita, final GWTRestDataSource pTreeDatasource, final GWTRestDataSource pDatasource,
			final CustomAdvancedTree tree, final ConfigurableFilter filter, final CustomList list, final CustomDetail detail, String finalita,
			Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, Boolean showOnlyDetail, AdvancedCriteria initialCriteria) {

		super(nomePortlet, nomeEntita, pDatasource, filter, list, detail, finalita, flgSelezioneSingola, showOnlyDetail);

		this.tree = tree;
		this.tree.setLayout(this);

		this.treedatasource = pTreeDatasource;
		this.treedatasource.addParam("finalita", finalita);
		this.treedatasource.setForceToShowPrompt(true);
		
		this.tree.setDataSource(treedatasource);

		this.flgSoloFolder = flgSoloFolder;
		this.idRootNode = idRootNode;
		this.initialCriteria = initialCriteria;

		navigator = new NavigatorLayout(this);

		vaiAlLivSupButton = new ToolStripButton();
		vaiAlLivSupButton.setIcon("buttons/folderUp.png");
		vaiAlLivSupButton.setIconSize(16);
		vaiAlLivSupButton.setAutoFit(true);
		vaiAlLivSupButton.setPrompt(I18NUtil.getMessages().navigatorVaiAlLivSupItem_prompt());
		vaiAlLivSupButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				vaiAlLivelloSuperiore();
			}
		});

//		 cercaPercorsoButton = new ToolStripButton();
//		 cercaPercorsoButton.setIcon("buttons/search.png");
//		 cercaPercorsoButton.setIconSize(16);
//		 cercaPercorsoButton.setAutoFit(true);
//		 cercaPercorsoButton.setPrompt("Cerca");
//		 cercaPercorsoButton.addClickHandler(new ClickHandler() {
//		 public void onClick(ClickEvent event) {
//			cerca();
//		 }
//		 });

		navigationToolStrip = new ToolStrip();
		navigationToolStrip.setPaddingAsLayoutMargin(false);
		navigationToolStrip.setWidth100();
		navigationToolStrip.setHeight(30);

		navigationToolStrip.addMember(navigator);
		navigationToolStrip.addButton(vaiAlLivSupButton);
//		navigationToolStrip.addButton(cercaPercorsoButton);

		leftLayout.addMember(tree);
		leftLayout.setWidth(getTreeSectionDefaultWidth());
		leftLayout.setShowResizeBar(true);
		leftLayout.setOverflow(Overflow.AUTO);
		
		mainLayout.setMembers(leftLayout, rightLayout);		
		
		navigationLayout = new VLayout();
		navigationLayout.setStyleName(it.eng.utility.Styles.navigationLayout);		
		navigationLayout.setWidth100();
		navigationLayout.setHeight(30);
//		navigationLayout.setShowResizeBar(true);
		
		navigationLayout.setMembers(navigationToolStrip);

		sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
		sectionStack.setAnimateSections(false);
		sectionStack.setOverflow(Overflow.AUTO);

		searchSection = new SectionStackSection();
		searchSection.setShowHeader(false);
		searchSection.setExpanded(true);
		searchSection.setItems(navigationLayout, mainLayout);

		detailSection = new SectionStackSection();
		detailSection.setShowHeader(false);
		detailSection.setExpanded(false);
		detailSection.setItems(detailLayout);

		sectionStack.setSections(searchSection, detailSection);

		setMembers(sectionStack);

		setMultiselect(getDefaultMultiselect());
		setDetailAuto(getDefaultDetailAuto());
		hideDetail();

		if (idRootNode != null && !"".equals(idRootNode)) {
			tree.firstLoadTree(idRootNode);
		} else {
			loadTreeFromDefaultIdRootNode();
		}

		if (configLista.getOrdinamentoDefault() != null) {
			for (String key : configLista.getOrdinamentoDefault().keySet()) {
				if ("ascending".equals(configLista.getOrdinamentoDefault().get(key))) {
					list.addSort(new SortSpecifier(key, SortDirection.ASCENDING));
				} else if ("descending".equals(configLista.getOrdinamentoDefault().get(key))) {
					list.addSort(new SortSpecifier(key, SortDirection.DESCENDING));
				}
			}
		}

		if (idRootNode != null && !"".equals(idRootNode)) {
			caricaPreference(idRootNode);
		} else {
			caricaPreference();
		}
		
		caricaPreferenceTreeVisibility();
	}
	
	public List<MenuItem> getCustomNavigationContextMenuItems(TreeNodeBean node, final NavigationContextMenuFrom from, final Integer treeRecordIndex) {
		return new ArrayList<MenuItem>();
	}
	
	// se il metodo torna true all'apertura della maschera non viene caricata la ricerca preferita di DEFAULT ma solo il layout del filtro,
	// eccetto quando si apre la maschera con impostato un idRootNode di partenza
	public boolean skipRicercaPreferitaIniziale() {
		return false;
	}

	
	@Override
	public boolean hasDefaultValueRicercaPreferita() {
		if(skipRicercaPreferitaIniziale()) {
			return false; // faccio in modo che quando salvo la ricerca preferita non mi suggerisca in automatico "DEFAULT"
		}
		return true;
	}
		
	@Override
	protected void caricaPreference() {
		if(!skipRicercaPreferitaIniziale()) {	
			caricaPreference(null);
		} else {
			if (UserInterfaceFactory.getListaDefPrefsService() != null) {
				getListaDefPrefs(new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record object) {
						if (object.getAttribute("layoutLista") != null) {
							layoutListaSelectItem.setValue("DEFAULT");
							list.setViewState(object.getAttribute("layoutLista"));
						} else if (object.getAttribute("layoutListaDefault") != null) {
							layoutListaSelectItem.setValue((String) null);
							list.setViewState(object.getAttribute("layoutListaDefault"));
						} else {
							layoutListaSelectItem.setValue((String) null);
						}
						if (object.getAttribute("layoutFiltro") != null) {
							ricercaPreferitaSelectItem.setValue((String) null);
							layoutFiltroSelectItem.setValue("DEFAULT");
							//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?
							setMaxRecordVisualizzabili("");
							setNroRecordXPagina("");
							setCriteriaAndFirstSearch(new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltro"))), object.getAttributeAsBoolean("autosearch"));
						} else if (object.getAttribute("layoutFiltroDefault") != null) {
							ricercaPreferitaSelectItem.setValue((String) null);
							layoutFiltroSelectItem.setValue((String) null);
							//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?
							setMaxRecordVisualizzabili("");
							setNroRecordXPagina("");
							setCriteriaAndFirstSearch(new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltroDefault"))), object.getAttributeAsBoolean("autosearch"));
						} else {
							ricercaPreferitaSelectItem.setValue((String) null);
							layoutFiltroSelectItem.setValue((String) null);
							//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?
							setMaxRecordVisualizzabili("");
							setNroRecordXPagina("");
							setDefaultCriteriaAndFirstSearch(object.getAttributeAsBoolean("autosearch"));
						}
					}
				});
			} else {
				caricaPreferenceLista(new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						caricaLayoutFiltro();
					}
				});
			}
		}
	}

	public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, boolean autoSearch) {
		if(!skipRicercaPreferitaIniziale()) {
			defaultCriteria = criteria;
		}
		super.setCriteriaAndFirstSearch(criteria, autoSearch);
	}
	
	public void getListaDefPrefs(String idNode, final ServiceCallback<Record> callback) {
		Record lRecord = getRecordListaDefPrefs();
		if (idNode != null && !"".equals(idNode)) {
			lRecord.setAttribute("idNode", idNode);
		}
		getListaDefPrefs(lRecord, callback);
	}
	
	protected void caricaPreference(final String idNode) {
		if (UserInterfaceFactory.getListaDefPrefsService() != null) {
			getListaDefPrefs(idNode, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (object.getAttribute("layoutLista") != null) {
						layoutListaSelectItem.setValue("DEFAULT");
						list.setViewState(object.getAttribute("layoutLista"));
					} else if (object.getAttribute("layoutListaDefault") != null) {
						layoutListaSelectItem.setValue((String) null);
						list.setViewState(object.getAttribute("layoutListaDefault"));
					} else {
						layoutListaSelectItem.setValue((String) null);
					}
					if (object.getAttribute("ricercaPreferita") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(object.getAttribute("ricercaPreferita")));
						ricercaPreferitaSelectItem.setValue("DEFAULT");
						layoutFiltroSelectItem.setValue((String) null);
//						initialCriteria =  new AdvancedCriteria(JSON.decode(object.getAttribute("ricercaPreferita")));				
//						setCriteriaAndFirstSearch(initialCriteria, object.getAttributeAsBoolean("autosearch"));
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabiliFromCriteria(criteria);					
						setNroRecordXPaginaFromCriteria(criteria);				
						setCriteriaAndFirstSearch(checkRetrocompatibilitaFiltri(criteria), object.getAttributeAsBoolean("autosearch"));
					} else if (object.getAttribute("layoutFiltro") != null) {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue("DEFAULT");
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");										
						setCriteriaAndFirstSearch(new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltro"))),
								object.getAttributeAsBoolean("autosearch"));
					} else if (object.getAttribute("ricercaPreferitaDefault") != null) {
						AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(object.getAttribute("ricercaPreferitaDefault")));
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
//						initialCriteria =  new AdvancedCriteria(JSON.decode(object.getAttribute("ricercaPreferitaDefault")));
//						setCriteriaAndFirstSearch(initialCriteria, object.getAttributeAsBoolean("autosearch"));
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabiliFromCriteria(criteria);					
						setNroRecordXPaginaFromCriteria(criteria);				
						setCriteriaAndFirstSearch(checkRetrocompatibilitaFiltri(criteria), object.getAttributeAsBoolean("autosearch"));
					} else if (object.getAttribute("layoutFiltroDefault") != null) {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
						setMaxRecordVisualizzabili("");					
						setNroRecordXPagina("");										
						setCriteriaAndFirstSearch(new AdvancedCriteria(JSON.decode(object.getAttribute("layoutFiltroDefault"))),
								object.getAttributeAsBoolean("autosearch"));
					} else {
						ricercaPreferitaSelectItem.setValue((String) null);
						layoutFiltroSelectItem.setValue((String) null);
						setDefaultCriteriaAndFirstSearch(object.getAttributeAsBoolean("autosearch"));
					}
				}
			});
		} else {
			caricaPreferenceLista(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (isForcedToAutoSearch()) {
						caricaPreferenceRicerca(idNode, true);
					} else {
						AdvancedCriteria criteriaAutoSearch = new AdvancedCriteria();
						criteriaAutoSearch.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
						autoSearchDS.fetchData(criteriaAutoSearch, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									caricaPreferenceRicerca(idNode, new Boolean(record.getAttribute("value")));
								} else {
									autoSearchDefaultDS.fetchData(null, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											Record[] data = response.getData();
											if (data.length != 0) {
												Record record = data[0];
												caricaPreferenceRicerca(idNode, new Boolean(record.getAttribute("value")));
											} else {
												caricaPreferenceRicerca(idNode, false);
											}
										}
									});
								}
							}
						});
					}
				}
			});
		}
	}
	
	protected void caricaPreferenceTreeVisibility() {
		final GWTRestDataSource showTreeDS = UserInterfaceFactory.getPreferenceDataSource();
		showTreeDS.addParam("prefKey", getPrefKeyPrefixForPortlet() + "showTree");
		AdvancedCriteria criteria = new AdvancedCriteria();
		criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		showTreeDS.fetchData(criteria, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					Record record = data[0];
					String preferenceValue = record.getAttribute("value");
					boolean showTree = true;
					int treeSectionWidth = getTreeSectionDefaultWidth();
					if (preferenceValue == null || preferenceValue.indexOf(":") == -1) {
						showTree = new Boolean(record.getAttribute("value"));
					}else {
						showTree = new Boolean(record.getAttribute("value").split(":")[0]);
						treeSectionWidth = new Integer(record.getAttribute("value").split(":")[1]);
					}
					setTreeSectionWidth(treeSectionWidth);
					if(showTree) {
						showTree();
					} else {
						hideTree();
					}
				} else {
					showTree();
				}     
			}         
		});	
	}
	
	protected void caricaPreferenceRicerca(String idNode, final boolean autoSearch) {
		GWTRestDataSource ricercaPreferitaIdNodeDS = UserInterfaceFactory.getPreferenceDataSource();
		ricercaPreferitaIdNodeDS.addParam("userId", "DEFAULT");
		ricercaPreferitaIdNodeDS.addParam("prefKey", getPrefKeyPrefix() + idNode + ".filter");
		ricercaPreferitaIdNodeDS.addParam("prefName", "DEFAULT");
		ricercaPreferitaIdNodeDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length > 0 && data[0].getAttribute("value") != null) {
					AdvancedCriteria criteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
					ricercaPreferitaSelectItem.setValue((String) null);
					layoutFiltroSelectItem.setValue((String) null);
//					initialCriteria = new AdvancedCriteria(JSON.decode(data[0].getAttribute("value")));
//					setCriteriaAndFirstSearch(initialCriteria, autoSearch);
					//TODO mettere maxRecordVisualizzabili e nroRecordXPagina in layoutLista o preference a parte?					
					setMaxRecordVisualizzabiliFromCriteria(criteria);					
					setNroRecordXPaginaFromCriteria(criteria);				
					setCriteriaAndFirstSearch(criteria, autoSearch);
				} else {
					caricaPreferenceRicerca(autoSearch);
				}
			}
		});
	}
	

	public void loadTreeFromDefaultIdRootNode() {

		final GWTRestDataSource impostaComeRadiceDS = UserInterfaceFactory.getPreferenceDataSource();
		impostaComeRadiceDS.addParam("prefKey", getPrefKeyPrefix() + "idRootNode");

		final GWTRestDataSource impostaComeRadiceDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
		impostaComeRadiceDefaultDS.addParam("userId", "DEFAULT");
		impostaComeRadiceDefaultDS.addParam("prefKey", getPrefKeyPrefix() + "idRootNode");
		impostaComeRadiceDefaultDS.addParam("prefName", "DEFAULT");

		AdvancedCriteria criteriaLayoutFiltro = new AdvancedCriteria();
		criteriaLayoutFiltro.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		impostaComeRadiceDS.fetchData(criteriaLayoutFiltro, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (data.length != 0) {
					String idRootNode = data[0].getAttribute("value");
					tree.firstLoadTree(idRootNode);
				} else {
					impostaComeRadiceDefaultDS.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								String idRootNode = data[0].getAttribute("value");
								tree.firstLoadTree(idRootNode);
							} else {
								tree.firstLoadTree("/");
							}
						}
					});
				}
			}
		});
	}

	public void setPercorsoIniziale() {
		tree.getDataSource().performCustomOperation("getPercorsoIniziale", new Record(), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					// quando cambio il percorso in cui sto ricercando devo resettare il numero di pagina
					if(nroPaginaItem != null) {
						nroPaginaItem.setValue("");
						nroPaginaItem.hide();
					}
					navigator.setPercorso(record.getAttributeAsRecordList("percorso"));
					navigator.setFlgMostraContenuti(record.getAttributeAsBoolean("flgMostraContenuti"));
					flgMostraContenuti = navigator.getFlgMostraContenuti();
					setAltriParametriPercorsoIniziale(record.getAttributeAsMap("altriParametri"));
					if (showOnlyDetail == null || !showOnlyDetail) {
						cercaModeIniziale();
					}
				}
			}
		}, new DSRequest());
	}

	public void setAltriParametriPercorsoIniziale(Map<String, String> altriParametri) {
		this.altriParametri = altriParametri;
	}

	public void aggiornaPercorso(String idNode, final DSCallback callback) {
		Record record = new Record();
		record.setAttribute("idNode", idNode);
		tree.getDataSource().performCustomOperation("calcolaPercorso", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					// quando cambio il percorso in cui sto ricercando devo resettare il numero di pagina
					if(nroPaginaItem != null) {
						nroPaginaItem.setValue("");
						nroPaginaItem.hide();
					}
					navigator.setPercorso(record.getAttributeAsRecordList("percorso"));
					navigator.setFlgMostraContenuti(record.getAttributeAsBoolean("flgMostraContenuti"));
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	public void aggiornaPercorsoFromList(String idFolder, final DSCallback callback) {
		Record record = new Record();
		record.setAttribute("idFolder", idFolder);
		record.setAttribute("idNode", navigator.getCurrentNode().getIdNode());
		tree.getDataSource().performCustomOperation("calcolaPercorsoFromList", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					// quando cambio il percorso in cui sto ricercando devo resettare il numero di pagina
					if(nroPaginaItem != null) {
						nroPaginaItem.setValue("");
						nroPaginaItem.hide();
					}
					navigator.setPercorso(record.getAttributeAsRecordList("percorso"));
					navigator.setFlgMostraContenuti(record.getAttributeAsBoolean("flgMostraContenuti"));
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if(!"finalita".equals(crit.getFieldName()) && 
				   !"maxRecordVisualizzabili".equals(crit.getFieldName()) && 
				   !"nroRecordXPagina".equals(crit.getFieldName()) && 
				   !"nroPagina".equals(crit.getFieldName())) {
					criterionList.add(crit);
				}
			}
		}

		String idFolder = navigator.getCurrentNode().getIdFolder();
		if (idFolder != null && !"".equals(idFolder)) {
			criterionList.add(new Criterion("idFolder", OperatorId.EQUALS, idFolder));
		}

		if (finalita != null && !"".equals(finalita)) {
			criterionList.add(new Criterion("finalita", OperatorId.EQUALS, finalita));
		}

		if (flgSoloFolder != null) {
			criterionList.add(new Criterion("flgSoloFolder", OperatorId.EQUALS, flgSoloFolder));
		}

		if(showMaxRecordVisualizzabiliItem() && maxRecordVisualizzabiliItem != null && maxRecordVisualizzabiliItem.getValueAsString() != null && !maxRecordVisualizzabiliItem.getValueAsString().equalsIgnoreCase("")) {
			int maxRecordVisualizzabili = Integer.valueOf((String) maxRecordVisualizzabiliItem.getValue());
			if (maxRecordVisualizzabili > 0){
				criterionList.add(new Criterion("maxRecordVisualizzabili", OperatorId.EQUALS, maxRecordVisualizzabiliItem.getValueAsString()));	
			} else {
				maxRecordVisualizzabiliItem.setValue("");
			}
		}
		
		if(showPaginazioneItems() && nroRecordXPaginaItem != null && nroRecordXPaginaItem.getValueAsString() != null && !nroRecordXPaginaItem.getValueAsString().equalsIgnoreCase("")){
			int nroRecordXPagina = Integer.valueOf((String) nroRecordXPaginaItem.getValue());
			if (nroRecordXPagina > 0){
				criterionList.add(new Criterion("nroRecordXPagina", OperatorId.EQUALS, nroRecordXPaginaItem.getValueAsString()));	
			} else {
				nroRecordXPaginaItem.setValue("");
			}
		}
		
		if(showPaginazioneItems() && nroPaginaItem != null && nroPaginaItem.getValueAsString() != null && !nroPaginaItem.getValueAsString().equalsIgnoreCase("")){
			int nroPagina = Integer.valueOf((String) nroPaginaItem.getValue());
			if (nroPagina > 0){
				criterionList.add(new Criterion("nroPagina", OperatorId.EQUALS, nroPaginaItem.getValueAsString()));	
			} else {
				nroPaginaItem.setValue("");
			}
		}
		
		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return new AdvancedCriteria(OperatorId.AND, criterias);
	}

	public void ricaricaAlberoAPartireDa(String idRootNode) {
		tree.reloadTreeFrom(idRootNode);
	}

	public void ricaricaSottoAlberoAPartireDa(String idNode, boolean forceToOpenNode) {
		tree.reloadSubTreeFrom(idNode, forceToOpenNode);
	}

	public void vaiAlLivelloSuperiore() {
		RecordList percorso = navigator.getPercorso();
		if (percorso.getLength() > 1) {
			Record livelloSup = percorso.get(percorso.getLength() - 2);
			esplora(livelloSup.getAttributeAsString("idNode"));
		}
	}

	public void doSearch() {
		// se il filtro è visibile sono in modalità di ricerca con filtri nel nodo (ricorsiva oppure no)
		if (filter.isVisible()) {
			flgMostraContenuti = false;
			AdvancedCriteria lAdvancedCriteria = filter.getCriteria(true);
			validateFilter(lAdvancedCriteria);
		}
		// altrimenti sto esplorando i contenuti del nodo (solo il primo livello)
		else {
			flgMostraContenuti = true;
			doSearch(null);
		}
	}

	@Override
	public void doSearch(AdvancedCriteria criteria) {
		// ogni volta che effettuo una ricerca mi salvo il livello corrente in cache (filtri e modalità di ricerca)
		salvaLivelloCorrente();
		super.doSearch(criteria);
	}

	public void esplora(String idNode) {
		if (idNode != null) {
			// if (!idNode.equals(navigator.getCurrentNode().getIdNode())) {
			// salvaLivelloCorrente();
			// }
			CacheLevelBean cacheLevel = navigator.getCacheLevels().get(idNode);
			if (cacheLevel != null) {			
				navigator.setPercorso(navigator.getPercorso(idNode));
				esploraMode(cacheLevel);
			} else {
				aggiornaPercorso(idNode, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						esploraMode(null);
					}
				});
			}
		}
	}

	public void esploraFromList(String idFolder) {
		if (idFolder != null) {
			// if (!idFolder.equals(navigator.getCurrentNode().getIdFolder())) {
			// salvaLivelloCorrente();
			// }
			Record record = navigator.getPercorso().find("idFolder", idFolder);
			CacheLevelBean cacheLevel = record != null ? navigator.getCacheLevels().get(record.getAttributeAsString("idNode")) : null;
			if (cacheLevel != null) {							
				navigator.setPercorso(navigator.getPercorso(record.getAttributeAsString("idNode")));
				esploraMode(cacheLevel);
			} else {
				aggiornaPercorsoFromList(idFolder, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						esploraMode(null);
					}
				});
			}
		}
	}

	public void cerca(String idNode) {
		if (idNode != null) {
			// if (!idNode.equals(navigator.getCurrentNode().getIdNode())) {
			// salvaLivelloCorrente();
			// }
			aggiornaPercorso(idNode, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					cercaMode();
				}
			});
		}
	}

	public void cercaFromList(String idFolder) {
		if (idFolder != null) {
			// if (!idFolder.equals(navigator.getCurrentNode().getIdFolder())) {
			// salvaLivelloCorrente();
			// }
			aggiornaPercorsoFromList(idFolder, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					cercaMode();
				}
			});
		}
	}

	public void esploraMode() {
		esploraMode(null);
	}

	public void esploraMode(CacheLevelBean cacheLevel) {
		flgMostraContenuti = cacheLevel != null ? cacheLevel.getFlgMostraContenuti() : navigator.getFlgMostraContenuti();
		AdvancedCriteria filterCriteria = cacheLevel != null ? cacheLevel.getCriteria() : null;
		setNroPagina(cacheLevel != null ? cacheLevel.getNroPagina() :null);
		if (flgMostraContenuti && filterCriteria == null) {
			filterLayout.setVisible(false);
			caricaLayoutFiltro(true);
		} else {
			cercaMode(filterCriteria);
		}
	}

	public void cercaMode() {
		cercaMode(null);
	}
	
	public boolean skipAutoSearchInizialeWithIdRootNode() {
		return false;
	}
	
	public void cercaModeIniziale() {
		if(initialCriteria != null) {
			cercaMode(initialCriteria);
		} else if (defaultCriteria != null && !skipRicercaPreferitaIniziale()) {
			if (idRootNode != null && !"".equals(idRootNode)) {
				cercaMode(defaultCriteria, true, !skipAutoSearchInizialeWithIdRootNode());
			} else { 
				cercaMode(defaultCriteria, false, false);
			}
		} else {
			esploraMode();
		}		
	}

	public void cercaMode(AdvancedCriteria filterCriteria) {
		cercaMode(filterCriteria, true, true);
	}
	
	public void cercaMode(AdvancedCriteria filterCriteria, boolean resetFilterPrefs, boolean autoSearch) {
		flgMostraContenuti = false;
		filterLayout.setVisible(true);
		list.setData(new ListGridRecord[0]);
		refreshNroRecord();
		filtroAttivoImg.hide();
		setMultiselect(getDefaultMultiselect());
		list.setEmptyMessage(I18NUtil.getMessages().list_noSearchMessage());
		if (filterCriteria != null) {
			filter.setCriteria(filterCriteria);
			if(resetFilterPrefs) {
				ricercaPreferitaSelectItem.setValue((String) null);
				layoutFiltroSelectItem.setValue((String) null);
			}
			if(autoSearch) {
				doSearch();
			}
		} else {
			isFetched = false;
			searchCriteria = null;
			caricaLayoutFiltro();
		}
	}

	/*
	@Override
	public void reloadListAndSetCurrentRecord(Record record) {
		 if (getIdNodeToOpen() != null && !"".equals(getIdNodeToOpen())) {
			 if (getIdNodeToOpen().equals(navigator.getCurrentNode().getIdNode())) {
				 super.reloadListAndSetCurrentRecord(record);
				 ((CustomAdvancedTree) getTree()).setIdNodeToOpen(getIdNodeToOpen());
				 ((CustomAdvancedTree) getTree()).firstLoadTree(((CustomAdvancedTree) getTree()).getIdRootNode());
			 } else {
				 ((CustomAdvancedTree) getTree()).setIdNodeToOpen(getIdNodeToOpen());
				 ((CustomAdvancedTree) getTree()).firstLoadTreeAndEsploraNodeToOpen(((CustomAdvancedTree) getTree()).getIdRootNode());
			 }
		 } else {
			 super.reloadListAndSetCurrentRecord(record);
			 ((CustomAdvancedTree) getTree()).setIdNodeToOpen(navigator.getCurrentNode().getIdNode());
			 ((CustomAdvancedTree) getTree()).firstLoadTree(((CustomAdvancedTree) getTree()).getIdRootNode());
		 }
	}

	@Override
	public void hideDetailAfterSave() {
		 if (getIdNodeToOpen() != null && !"".equals(getIdNodeToOpen())) {
		 super.hideDetail(true);
		 } else {
		 super.hideDetailAfterSave();
		 }
	}
	
	@Override
	public void hideDetail(boolean reloadList) {
		 super.hideDetail(reloadList);
		 setIdNodeToOpen(null);
	}
	*/
	
	@Override
	public void hideDetailAfterSave() {
		if (getIdNodeToOpen() != null && !"".equals(getIdNodeToOpen())) {
			((CustomAdvancedTree) getTree()).reloadSubTreeFrom(getIdNodeToOpen());
			setIdNodeToOpen(null);
		}
		super.hideDetailAfterSave();
	}

	public void salvaLivelloCorrente() {
		navigator.saveCurrentCacheLevel();
	}

	public CustomAdvancedTree getTree() {
		return tree;
	}

	public NavigatorLayout getNavigator() {
		return navigator;
	}

	public String getIdFolderCorrente() {
		String idFolderCorrente;
		try {
			idFolderCorrente = getNavigator().getCurrentNode().getIdFolder();
		} catch (Exception e) {
			idFolderCorrente = null;
		}
		return idFolderCorrente;
	}

	public Boolean getFlgMostraContenuti() {
		return flgMostraContenuti;
	}

	public String getFinalita() {
		return finalita;
	}

	public Boolean getFlgSoloFolder() {
		return flgSoloFolder;
	}

	public String getIdRootNode() {
		return idRootNode;
	}

	public Map<String, String> getAltriParametri() {
		return altriParametri;
	}

	public String getIdNodeToOpen() {
		return idNodeToOpen;
	}

	public void setIdNodeToOpen(String idNodeToOpen) {
		this.idNodeToOpen = idNodeToOpen;
	}

	public void setIdNodeToOpenByIdFolder(String idFolder) {
		setIdNodeToOpenByIdFolder(idFolder, null);
	}
	
	public void setIdNodeToOpenByIdFolder(String idFolder, final GenericCallback callback) {
		try {
			if (idFolder != null && !"".equals(idFolder)) {
				Record record = new Record();
				record.setAttribute("idFolder", idFolder);
				tree.getDataSource().performCustomOperation("calcolaPercorsoFromList", record, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record data = response.getData()[0];
							RecordList percorso = data.getAttributeAsRecordList("percorso");
							Record currentNode = percorso.get(percorso.getLength() - 1);
							String idNode = currentNode.getAttributeAsString("idNode");
							setIdNodeToOpen(idNode);
							if(callback != null) callback.execute();
						}
					}
				}, new DSRequest());
			} else {
				if(getIdNodeToOpen() == null) {
					setIdNodeToOpen("/");
				}
				if(callback != null) callback.execute();
			}
		} catch(Exception e) {
			setIdNodeToOpen(null);
			if(callback != null) callback.execute();
		}
	}
	
	@Override
	public void showTree() {
		leftLayout.show();
	}
	
	@Override
	public void hideTree() {
		leftLayout.hide();
	}
	
	@Override
	public boolean isTreeVisible() {
		return leftLayout.isVisible();
	}

	@Override
	public int getTreeSectionDefaultWidth() {
		return CUSTOM_ADVANCED_TREE_SETION_DEFAULT_WIDTH;
	}
	
	@Override
	public int getTreeSectionWidth() {
		return leftLayout.getWidth();
	}

	@Override
	public void setTreeSectionWidth(int width) {
		leftLayout.setWidth(width);		
	}
	
	@Override
	protected void onDestroy() {		
		if(treedatasource != null) {
			treedatasource.destroy();
		}		
		super.onDestroy();
	}
	
}
