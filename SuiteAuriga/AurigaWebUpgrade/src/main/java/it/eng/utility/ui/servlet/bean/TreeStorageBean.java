/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class TreeStorageBean {

	private String idStorage;
	private String tipoStorage;
	private String flgDisattivo;
	private TreeStorageConfigBean config;
	public String getIdStorage() {
		return idStorage;
	}
	public void setIdStorage(String idStorage) {
		this.idStorage = idStorage;
	}
	public String getTipoStorage() {
		return tipoStorage;
	}
	public void setTipoStorage(String tipoStorage) {
		this.tipoStorage = tipoStorage;
	}
	public String getFlgDisattivo() {
		return flgDisattivo;
	}
	public void setFlgDisattivo(String flgDisattivo) {
		this.flgDisattivo = flgDisattivo;
	}
	public TreeStorageConfigBean getConfig() {
		return config;
	}
	public void setConfig(TreeStorageConfigBean config) {
		this.config = config;
	}
}
