/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.annotation.JSONBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean di gestione del menu
 * @author michele
 *
 */
@JSONBean
public class MenuBean {

	private String nomeEntita;
	private String title;
	private String prompt;
	private String icon;
	private String portalDesktopIcon;
	private Boolean preferiti = false;
	private Boolean isPopup = false;
	private List<MenuBean> submenu;
	private List<String> privilegi;
	
	public MenuBean() {
	
	}	
	
	public MenuBean preferiti(Boolean flag){
		this.preferiti = flag;
		return this;
	}
	
	public MenuBean(String nomeEntita) {
		super();
		this.nomeEntita = nomeEntita;
	}

	public MenuBean addSubMenu(MenuBean menu){
		if(submenu==null){
			submenu = new ArrayList<MenuBean>();
		}
		submenu.add(menu);
		return this;
	}
		
	public void setNomeEntita(String nomeEntita) {
		this.nomeEntita = nomeEntita;
	}
	
	public String getNomeEntita() {
		return nomeEntita;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPrompt() {
		return prompt;
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPortalDesktopIcon() {
		return portalDesktopIcon;
	}

	public void setPortalDesktopIcon(String portalDesktopIcon) {
		this.portalDesktopIcon = portalDesktopIcon;
	}

	public Boolean getPreferiti() {
		return preferiti;
	}	
	
	public void setPreferiti(Boolean preferiti) {
		this.preferiti = preferiti;
	}	
	
	public Boolean getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(Boolean isPopup) {
		this.isPopup = isPopup;
	}

	public List<MenuBean> getSubmenu() {
		return submenu;
	}
	
	public void setSubmenu(List<MenuBean> submenu) {
		this.submenu = submenu;
	}
	
	public List<String> getPrivilegi() {
		return privilegi;
	}

	public void setPrivilegi(List<String> privilegi) {
		this.privilegi = privilegi;
	}

}