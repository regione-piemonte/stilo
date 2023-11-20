/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.bean.CacheLevelBean;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;

/**
 * Menu dei preferiti
 * 
 * @author michele
 *
 */
public class NavigatorLayout extends HLayout {

	private final CustomAdvancedTreeLayout layout;
	private NavigatorButton currentLevel;
	private Boolean flgMostraContenuti;
	private HashMap<String, CacheLevelBean> cacheLevels;

	public NavigatorLayout(CustomAdvancedTreeLayout pLayout) {
		layout = pLayout;
		cacheLevels = new HashMap<String, CacheLevelBean>();
		setAlign(Alignment.LEFT);
		setOverflow(Overflow.HIDDEN);
	}

	public void setFlgMostraContenuti(Boolean flgMostraContenuti) {
		this.flgMostraContenuti = flgMostraContenuti;
	}

	public Boolean getFlgMostraContenuti() {
		return flgMostraContenuti;
	}

	public void setPercorso(final RecordList percorso) {
		if(getMembers() != null) {
			for(Canvas member : getMembers()) {
				member.markForDestroy();
			}
		}
		if (percorso != null) {
			Label label = new Label("&nbsp;<font style=\"font-weight: bold\">" + I18NUtil.getMessages().navigatorPercorsoItem_title() + "</font>&nbsp;:&nbsp;&nbsp;&nbsp;");
			label.setStyleName(it.eng.utility.Styles.formTitle);
			label.setAutoFit(true);
			String prompt = "";
			for (int i = 0; i < percorso.getLength(); i++) {
				if (i != 0) {
					prompt += "&nbsp;/&nbsp;";
				}
				prompt += percorso.get(i).getAttributeAsString("nome");
			}
			label.setPrompt(prompt);
			setMembers(label);
			HashMap<String, CacheLevelBean> newCacheLevels = new HashMap<String, CacheLevelBean>();
			for (int i = 0; i < percorso.getLength(); i++) {
				boolean isCurrentLevel = (i == (percorso.getLength() - 1));
				String nome = percorso.get(i).getAttributeAsString("nome") != null ? percorso.get(i).getAttributeAsString("nome") : "";
				String title = ((nome.length() > 30) ? nome.substring(0, 30) + "..." : nome);
				final NavigatorButton button = new NavigatorButton(title, this);
				final TreeNodeBean node = new TreeNodeBean();
				node.setIdNode(percorso.get(i).getAttributeAsString("idNode"));
				node.setNome(nome);
				node.setIdFolder(percorso.get(i).getAttributeAsString("idFolder"));
				node.setIdLibreria(percorso.get(i).getAttributeAsString("idLibreria"));
				node.setAltriAttributi(percorso.get(i).getAttributeAsMap("altriAttributi"));
				button.setNode(node);
				button.addShowContextMenuHandler(new ShowContextMenuHandler() {

					@Override
					public void onShowContextMenu(ShowContextMenuEvent event) {

						event.cancel();
						Menu contextMenu = new NavigationContextMenu(layout, node, NavigationContextMenuFrom.NAVIGATOR_LEVEL);
						contextMenu.showContextMenu();
					}
				});
				// button.setContextMenu(new NavigationContextMenu(layout, node, NavigationContextMenuFrom.NAVIGATOR_LEVEL));
				button.setShowDisabled(false);
				button.setWrap(false);
				button.setPrompt(nome);
				button.setHoverWidth(200);
				button.setShowHover(true);
				button.setCanHover(true);
				addMember(button);
				if (cacheLevels.containsKey(node.getIdNode())) {
					newCacheLevels.put(node.getIdNode(), cacheLevels.get(node.getIdNode()));
				}
				if (isCurrentLevel) {
					currentLevel = button;
					// button.setDisabled(true);
				} else {
					Label separator = new Label("&nbsp;/&nbsp;");
					separator.setAutoFit(true);
					addMember(separator);
					if (i != 0 && i < percorso.getLength() - 3) {
						button.hide();
						separator.hide();
					}
				}
				if (i == 0 && percorso.getLength() > 4) {
					Label puntini = new Label("&nbsp;&nbsp;...&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;");
					puntini.setAutoFit(true);
					addMember(puntini);
				}
			}
			cacheLevels = newCacheLevels;
			layout.redrawMultiselectButtons();
		}
	}

	public RecordList getPercorso() {
		RecordList percorso = new RecordList();
		for (int i = 0; i < getMembers().length; i++) {
			Canvas member = getMember(i);
			if (member instanceof NavigatorButton) {
				TreeNodeBean node = ((NavigatorButton) member).getNode();
				Record livello = new Record();
				livello.setAttribute("idNode", node.getIdNode());
				livello.setAttribute("nome", node.getNome());
				livello.setAttribute("idFolder", node.getIdFolder());
				livello.setAttribute("altriAttributi", node.getAltriAttributi());
				percorso.add(livello);
			}
		}
		return percorso;
	}

	public RecordList getPercorso(String idNode) {
		RecordList percorso = new RecordList();
		int index = getPercorso().findIndex("idNode", idNode);
		if (index == -1) {
			return null;
		} else {
			for (int i = 0; i <= index; i++) {
				percorso.add(getPercorso().get(i));
			}
		}
		return percorso;
	}

	public void saveCurrentCacheLevel() {
		CacheLevelBean cacheLevel = new CacheLevelBean();
		cacheLevel.setFlgMostraContenuti(layout.getFlgMostraContenuti());
		// if(!cacheLevel.getFlgMostraContenuti()) {
		final JSONEncoder encoder = new JSONEncoder();
		encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
		// String criteriaStr = layout.searchCriteria != null ? JSON.encode(layout.searchCriteria.getJsObj(), encoder) : "";
		// System.out.println("CACHE LEVEL " + getCurrentNode().getIdNode() + ": " + criteriaStr);
		// cacheLevel.setCriteria(layout.searchCriteria);
		if (layout.getFilter().isVisible()) {
			cacheLevel.setCriteria(layout.getFilter().getCriteria(true));
		} else {
			cacheLevel.setCriteria(null);
		}
		// } else {
		// cacheLevel.setCriteria(null);
		// }
		if (layout.showPaginazioneItems()/* && layout.isPaginazioneAttiva()*/) {
			cacheLevel.setNroPagina(layout.getNroPagina());
		} else {
			cacheLevel.setNroPagina(null);
		}
		cacheLevels.put(getCurrentNode().getIdNode(), cacheLevel);
	}

	public CacheLevelBean getCurrentCacheLevel() {
		return cacheLevels.get(getCurrentNode().getIdNode());
	}

	public TreeNodeBean getCurrentNode() {
		return currentLevel.getNode();
	}

	public CustomAdvancedTreeLayout getLayout() {
		return layout;
	}

	public HashMap<String, CacheLevelBean> getCacheLevels() {
		return cacheLevels;
	}

	public void setCacheLevels(HashMap<String, CacheLevelBean> cacheLevels) {
		this.cacheLevels = cacheLevels;
	}

}