/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.MaximizeClickEvent;
import com.smartgwt.client.widgets.events.MaximizeClickHandler;
import com.smartgwt.client.widgets.events.MouseDownEvent;
import com.smartgwt.client.widgets.events.MouseDownHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTreeLayout;
import it.eng.utility.ui.module.layout.client.common.TreeSectionVisibility;

public class Portlet extends Window {

	protected String nomeEntita;
	public String tipoRecord;
	protected Canvas body;

	protected String icon;
	protected Boolean offset;
	private Boolean homepage = false;

	protected final Menu settingsMenu;

	protected final MenuItem setHomepageMenuItem;
	protected final MenuItem saveDimPosMenuItem;
	protected final MenuItem separatorMenuItem;
	protected final MenuItem autoSearchMenuItem;

	protected final HeaderControl settingsHeaderControl;
	// protected final HeaderControl helpHeaderControl;

	protected boolean canMaximizeRestore = true;

	public Portlet(String nomeEntita) {
		this(nomeEntita, false, true);
	}

	public Portlet(final String nomeEntita, boolean isJustWindow) {

		this(nomeEntita, isJustWindow, true);
	}

	public Portlet(final String nomeEntita, boolean isJustWindow, boolean showPreference) {

		this.nomeEntita = nomeEntita;

		setOffset(false);
		setShowShadow(false);
		// disable predefined component animation
		setAnimateMinimize(false);
		// Window is draggable with "outline" appearance by default.
		// "target" is the solid appearance.
		setDragAppearance(DragAppearance.OUTLINE);
		setCanDrop(true);
		setDragOpacity(30);
		setShowMinimizeButton(true);
		setShowMaximizeButton(true);
		setShowCloseButton(true);
		setShowStatusBar(true);
		setShowResizer(true);
		setShowHeaderIcon(true);
		setUseOpacityFilter(true);
		setHeaderStyle(it.eng.utility.Styles.windowHeader);
		setShowHeaderBackground(false);

		if (!isJustWindow) {
			addMouseDownHandler(new MouseDownHandler() {

				@Override
				public void onMouseDown(MouseDownEvent event) {

					if (getAttribute("minimizeClick") != null && getAttributeAsBoolean("minimizeClick")) {
						setAttribute("minimizeClick", false, true);
					} else {
						Portlet portlet = (Portlet) event.getSource();
						Layout.selectPortlet(portlet.getNomeEntita());
					}
				}
			});
		}

		setHomepageMenuItem = new MenuItem(I18NUtil.getMessages().setHomepageMenuItem_title(), "menu/homepage.png");

		if (showPreference) {
			final GWTRestDataSource homepageDS = UserInterfaceFactory.getPreferenceDataSource();
			homepageDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "homepage");
			setHomepageMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {

					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					if (!homepage) {
						homepageDS.fetchData(criteria, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									record.setAttribute("value", getNomeEntita());
									homepageDS.updateData(record);
								} else {
									Record record = new Record();
									record.setAttribute("prefName", "DEFAULT");
									record.setAttribute("value", getNomeEntita());
									homepageDS.addData(record);
								}
								homepage = true;
								Layout.redrawOpenedPortletsHomepageMenuItem(nomeEntita, homepage);
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSetHomepage_message(), "", MessageType.INFO));
							}
						});
					} else {
						homepageDS.fetchData(criteria, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									record.setAttribute("value", (String) null);
									homepageDS.updateData(record);
								}
								homepage = false;
								Layout.redrawOpenedPortletsHomepageMenuItem(nomeEntita, homepage);
								Layout.addMessage(new MessageBean("Deselezionata da home-page", "", MessageType.INFO));
							}
						});
					}
				}
			});

			AdvancedCriteria homepageCriteria = new AdvancedCriteria();
			homepageCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");

			homepageDS.fetchData(homepageCriteria, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record[] data = response.getData();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
						if (nomeEntita.equals(data[0].getAttributeAsString("value"))) {
							homepage = true;
						}
					}
					Layout.redrawOpenedPortletsHomepageMenuItem(nomeEntita, homepage);
				}
			});
		}

		saveDimPosMenuItem = new MenuItem(I18NUtil.getMessages().saveDimPosMenuItem_title(), "menu/dimpos.png");

		if (showPreference) {
			final GWTRestDataSource layoutPortletDS = UserInterfaceFactory.getPreferenceDataSource();
			layoutPortletDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + nomeEntita + ".layout.window");
			saveDimPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					layoutPortletDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							Record portletDimPos = new Record();
							if (getMaximized() != null && getMaximized()) {
								portletDimPos.setAttribute("maximized", true);								
							} else {
								portletDimPos.setAttribute("maximized", false);
								int pageLeft = getPageLeft();
								int pageTop = getPageTop() - 32;
								portletDimPos.setAttribute("pageLeft", pageLeft >= 0 ? pageLeft : 0);
								portletDimPos.setAttribute("pageTop", pageTop >= 0 ? pageTop : 0);
								portletDimPos.setAttribute("width", getWidth());
								portletDimPos.setAttribute("height", getHeight());								
							}								
							JSONEncoder encoder = new JSONEncoder();
							encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
							if (data.length != 0) {
								Record record = data[0];
								record.setAttribute("value", JSON.encode(portletDimPos.getJsObj(), encoder));
								layoutPortletDS.updateData(record);
							} else {
								Record record = new Record();
								record.setAttribute("prefName", "DEFAULT");
								record.setAttribute("value", JSON.encode(portletDimPos.getJsObj(), encoder));
								layoutPortletDS.addData(record);
							}							
							if (body != null && (body instanceof TreeSectionVisibility)) {
								final GWTRestDataSource showTreeDS = UserInterfaceFactory.getPreferenceDataSource();
								showTreeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + ((TreeSectionVisibility)body).getPrefKeyPrefixForPortlet() + "showTree");
								AdvancedCriteria criteria = new AdvancedCriteria();
								criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
								showTreeDS.fetchData(criteria, new DSCallback() {
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										Record[] data = response.getData();
										boolean treeVisible = ((TreeSectionVisibility)body).isTreeVisible() ? true : false;
										int treeWidth = ((TreeSectionVisibility)body).getTreeSectionWidth();
										if (data.length != 0) {
											Record record = data[0];
											record.setAttribute("value", treeVisible + ":" + treeWidth);
											showTreeDS.updateData(record);
										} else {
											Record record = new Record();
											record.setAttribute("prefName", "DEFAULT");
											record.setAttribute("value", treeVisible + ":" + treeWidth);
											showTreeDS.addData(record);
										}	
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSaveDimPos_message(), "", MessageType.INFO));
									}									
								});							
							} else {							
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSaveDimPos_message(), "", MessageType.INFO));
							}
						}
					});
				}
			});
		}

		separatorMenuItem = new MenuItem();
		separatorMenuItem.setIsSeparator(true);
		autoSearchMenuItem = new MenuItem(I18NUtil.getMessages().autoSearchMenuItem_title());

		if (showPreference) {

			final GWTRestDataSource autoSearchDS = UserInterfaceFactory.getPreferenceDataSource();
			autoSearchDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + nomeEntita + ".autosearch");

			final GWTRestDataSource autoSearchDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
			autoSearchDefaultDS.addParam("userId", "DEFAULT");
			autoSearchDefaultDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + nomeEntita + ".autosearch");
			autoSearchDefaultDS.addParam("prefName", "DEFAULT");

			autoSearchMenuItem.setCheckIfCondition(new MenuItemIfFunction() {

				@Override
				public boolean execute(Canvas target, Menu menu, MenuItem item) {

					return autoSearchMenuItem.getAttributeAsBoolean("value");
				}
			});

			autoSearchMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {

					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					autoSearchDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();

							final boolean checked = autoSearchMenuItem.getChecked();
							autoSearchMenuItem.setAttribute("value", !checked);
							Record record = null;
							if (data.length != 0) {
								record = data[0];
								record.setAttribute("value", !checked);
								autoSearchDS.updateData(record);
							} else {
								record = new Record();
								record.setAttribute("value", !checked);
								autoSearchDS.addData(record);
							}
							Layout.addMessage(new MessageBean(checked ? I18NUtil.getMessages().afterDisattivaAutoSearch_message() : I18NUtil.getMessages()
									.afterAttivaAutoSearch_message(), "", MessageType.INFO));
						}
					});
				}
			});

			AdvancedCriteria criteriaAutoSearch = new AdvancedCriteria();
			criteriaAutoSearch.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");

			autoSearchDS.fetchData(criteriaAutoSearch, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					Record[] data = response.getData();
					if (data.length != 0) {
						Record record = data[0];
						autoSearchMenuItem.setAttribute("value", new Boolean(record.getAttribute("value")));
					} else {
						autoSearchDefaultDS.fetchData(null, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								if (data.length != 0) {
									Record record = data[0];
									autoSearchMenuItem.setAttribute("value", new Boolean(record.getAttribute("value")));
								} else {
									autoSearchMenuItem.setAttribute("value", false);
								}
							}
						});
					}
				}
			});
		}

		settingsMenu = new Menu();
		settingsMenu.setOverflow(Overflow.VISIBLE);
		settingsMenu.setShowIcons(true);
		settingsMenu.setSelectionType(SelectionStyle.SINGLE);
		settingsMenu.setCanDragRecordsOut(false);
		settingsMenu.setWidth("*");
		settingsMenu.setHeight("*");

		// if(showPreference)
		{
			settingsMenu.addItem(setHomepageMenuItem);
			settingsMenu.addItem(saveDimPosMenuItem);
			settingsMenu.addItem(separatorMenuItem);
			settingsMenu.addItem(autoSearchMenuItem);
		}

		HeaderControl minimizeHeaderControl = new HeaderControl(HeaderControl.MINIMIZE, new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				setMinimizedPortlet(true);
				List<Portlet> otherPortlets = Layout.getOtherOpenedPortlet(getNomeEntita());
				if (otherPortlets != null && otherPortlets.size() > 0) {
					for (int i = (otherPortlets.size() - 1); i >= 0; i--) {
						if (!otherPortlets.get(i).isMinimizedPortlet()) {
							Layout.selectPortlet(otherPortlets.get(i).getNomeEntita());
							break;
						}
					}
				}
			}
		});
		minimizeHeaderControl.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {

				setAttribute("minimizeClick", true, true);
			}
		});
		minimizeHeaderControl.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {

				setAttribute("minimizeClick", false, true);
			}
		});
		minimizeHeaderControl.setCursor(Cursor.HAND);

		settingsHeaderControl = new HeaderControl(HeaderControl.SETTINGS, new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				settingsMenu.showContextMenu();
			}
		});
		settingsHeaderControl.setCursor(Cursor.HAND);

		// helpHeaderControl = new HeaderControl(HeaderControl.HELP,
		// new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// SC.say("TO DO...");
		// }
		// });
		// helpHeaderControl.setCursor(Cursor.HAND);

		addMaximizeClickHandler(new MaximizeClickHandler() {

			@Override
			public void onMaximizeClick(MaximizeClickEvent event) {

				if (!canMaximizeRestore) {
					event.cancel();
					Layout.addMessage(new MessageBean("Operazione non consentita!", "", MessageType.ERROR));
				}
			}
		});

		addRestoreClickHandler(new RestoreClickHandler() {

			@Override
			public void onRestoreClick(RestoreClickEvent event) {

				if (!canMaximizeRestore) {
					event.cancel();
					Layout.addMessage(new MessageBean("Operazione non consentita!", "", MessageType.ERROR));
				}
			}
		});

		if (showPreference) {
			setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, minimizeHeaderControl, HeaderControls.MAXIMIZE_BUTTON,
					settingsHeaderControl,
					// helpHeaderControl,
					HeaderControls.CLOSE_BUTTON);
		} else {
			setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, minimizeHeaderControl, HeaderControls.MAXIMIZE_BUTTON,
					HeaderControls.CLOSE_BUTTON);
		}

		// show either a shadow, or translucency, when dragging a portlet
		// (could do both at the same time, but these are not visually
		// compatible effects)
		// setShowDragShadow(true);
		setDragOpacity(30);

		// these settings enable the portlet to autosize its height only to fit
		// its contents
		// (since width is determined from the containing layout, not the
		// portlet contents)
		// setVPolicy(LayoutPolicy.NONE);
		// setOverflow(Overflow.VISIBLE);
		setHeight(300);

		// headercontrol settings = new headercontrol(headercontrol.settings);
		// headercontrol help = new headercontrol(headercontrol.help);
		// setheaderControls(HeaderControl.MINIMIZE, HeaderControl.MAXIMIZE,
		// HeaderControl.SETTINGS, HeaderControl.HELP, HeaderControl.CLOSE);
	}

	@Override
	public void show() {

		super.show();
		Layout.hideWaitPopup();
	}

	public void setCanMaximizeRestore(boolean canMaximizeRestore) {
		this.canMaximizeRestore = canMaximizeRestore;
	}

	public boolean getCanMaximizeRestore() {
		return canMaximizeRestore;
	}

	public void redrawHomepageMenuItem(boolean isHomepage) {
		if (isHomepage) {
			setHomepageMenuItem.setTitle("Deseleziona da home-page");
			setHomepageMenuItem.setIcon("blank.png");
		} else {
			setHomepageMenuItem.setTitle(I18NUtil.getMessages().setHomepageMenuItem_title());
			setHomepageMenuItem.setIcon("menu/homepage.png");
		}
		settingsMenu.markForRedraw();
	}

	@Override
	public void manageOnCloseClick() {
		if (body instanceof CustomLayout && ((((CustomLayout) body).getMode() != null) && ((CustomLayout) body).getFullScreenDetail())) {
			if (Layout.isReloadDisabled()) {
				((CustomLayout) body).hideDetail(false);
			} else if (((CustomLayout) body).getDetail().isSaved()) {
				((CustomLayout) body).getDetail().setSaved(false);
				((CustomLayout) body).hideDetailAfterSave();
			} else {
				((CustomLayout) body).hideDetail();
			}					
		} else {			
			if (body instanceof CustomLayout) {
				((CustomLayout) body).closeViewer();
			} else if (body instanceof CustomDetail) {
				((CustomDetail) body).closeViewer();
			}
			closePortlet();
		}
	}
	
	@Override
	public void markForDestroy() {
		if (body != null) {
			if (body instanceof CustomDetail) {
				((CustomDetail) body).markForDestroy();
			} else if(body instanceof CustomLayout) {
				if(((CustomLayout) body).getDetail() != null) {					
					((CustomLayout) body).getDetail().markForDestroy();
				}
			}
		}
		super.markForDestroy();
	}
	
	public void closePortlet() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				if (getIsModal()) {
					markForDestroy();
				} else {
					Layout.removePortlet(getNomeEntita());
				}
			}
		});		
	}

	public void setBody(Canvas pBody) {

		if (body != null) {
			super.removeItem(body);
			if (body instanceof CustomDetail) {
				((CustomDetail) body).markForDestroy();
			} else if(body instanceof CustomLayout) {
				if(((CustomLayout) body).getDetail() != null) {					
					((CustomLayout) body).getDetail().markForDestroy();
				}
			}
			body.markForDestroy();
		}
		body = pBody;
		super.addItem(body);
		if (body instanceof CustomSimpleTreeLayout || body instanceof CustomAdvancedTreeLayout || body instanceof CustomDetail
				|| ((body instanceof CustomLayout) && !((CustomLayout) body).getShowAvvioRicercaAutomatica())) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
		if ((body instanceof CustomLayout) && !((CustomLayout) body).getCanSetAsHomepage()) {
			settingsMenu.removeItem(setHomepageMenuItem);
		}
		if ((body instanceof CustomLayout)) {
			((CustomLayout) body).getList().setWindow(this);
		} else if ((body instanceof CustomList)) {
			((CustomList) body).setWindow(this);
		}
		if (body instanceof CustomLayout) {
			((CustomLayout) body).setPortlet(this);
		}
	}

	public Canvas getBody() {
		return body;
	}

	public void setMinimizedPortlet(boolean minimized) {
		setAttribute("minimizedPortlet", minimized, true);
	}

	public boolean isMinimizedPortlet() {
		return getAttribute("minimizedPortlet") != null ? getAttributeAsBoolean("minimizedPortlet") : false;
	}

	public void seleziona() {

		for (Portlet portlet : Layout.getOtherOpenedPortlet(getNomeEntita())) {
			String title = "<span class=\"" + it.eng.utility.Styles.titlePortlet + "\" style=\"color:lightgrey;font-weight:bold\">" + portlet.getTitle() + "</span>";
			portlet.setAttribute("title", title, true);
		}
		String title = "<span span class=\"" + it.eng.utility.Styles.titlePortlet + "\" style=\"color:white;font-weight:bold\">" + getTitle() + "</span>";
		setAttribute("title", title, true);
	}

	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}

	public String getNomeEntita() {
		return nomeEntita;
	}

	public void setIcon(String icon) {
		this.setHeaderIcon(icon);
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setOffset(Boolean offset) {
		this.offset = offset;
	}

	public Boolean isOffset() {
		return offset;
	}
	
	@Override
	protected void onDestroy() {
		if(settingsMenu != null) {
			settingsMenu.destroy();
		}
		super.onDestroy();
	}

}