/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.core.shared.annotation.JSONBean;

/**
 * Bean di gestione del navigatore
 * @author michele
 *
 */
@JSONBean
public class NavigatorBean {

	private String idNode;
	private String nome;
	private String idFolder;
	private Boolean flgMostraContenuti;
	private Map<String, String> altriAttributi;
	
	public NavigatorBean() {
	
	}

	public String getIdNode() {
		return idNode;
	}
	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public Boolean getFlgMostraContenuti() {
		return flgMostraContenuti;
	}
	public void setFlgMostraContenuti(Boolean flgMostraContenuti) {
		this.flgMostraContenuti = flgMostraContenuti;
	}

	public Map<String, String> getAltriAttributi() {
		return altriAttributi;
	}

	public void setAltriAttributi(Map<String, String> altriAttributi) {
		this.altriAttributi = altriAttributi;
	}

}