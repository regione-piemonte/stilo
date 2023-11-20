/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

/**
 * 
 * @author denis.bragato
 *
 */
public class FSConfig implements Serializable {

	private static final long serialVersionUID = 913642043506544218L;

	private String host;
	private String dominio;
	private String user;
	private String password;
	private String directoryLocale;
	private String subDirLocale;
	private String directoryRemota;
	private String directoryRemotaBackup;

	public FSConfig() {
	}
	
	public FSConfig(String host, String user, String password, String directoryRemota, String directoryLocale, String directoryRemotaBackup) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.directoryLocale = directoryLocale;
		this.directoryRemota = directoryRemota;
		this.directoryRemotaBackup = directoryRemotaBackup;
	}

	public FSConfig(String host, String user, String password, String directoryRemota, String directoryLocale, String subDirLocale, String directoryRemotaBackup) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.directoryLocale = directoryLocale;
		this.subDirLocale = subDirLocale;
		this.directoryRemota = directoryRemota;
		this.directoryRemotaBackup = directoryRemotaBackup;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDirectoryLocale() {
		return directoryLocale;
	}
	public void setDirectoryLocale(String directoryLocale) {
		this.directoryLocale = directoryLocale;
	}

	public String getSubDirLocale() {
		return subDirLocale;
	}
	public void setSubDirLocale(String subDirLocale) {
		this.subDirLocale = subDirLocale;
	}
	
	public String getDirectoryRemota() {
		return directoryRemota;
	}
	public void setDirectoryRemota(String directoryRemota) {
		this.directoryRemota = directoryRemota;
	}

	public String getDirectoryRemotaBackup() {
		return directoryRemotaBackup;
	}
	public void setDirectoryRemotaBackup(String directoryRemotaBackup) {
		this.directoryRemotaBackup = directoryRemotaBackup;
	}
	
	@Override
	public String toString() {
		return String.format(
				"FSConfig [host=%s, dominio=%s, user=%s, password=%s, directoryLocale=%s, subDirLocale=%s, directoryRemota=%s, directoryRemotaBackup=%s]",
				host, dominio, user, password, directoryLocale, subDirLocale, directoryRemota, directoryRemotaBackup);
	}
	
}
