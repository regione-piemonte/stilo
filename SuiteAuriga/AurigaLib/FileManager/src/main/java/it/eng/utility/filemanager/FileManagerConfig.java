/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FileManagerConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private FileManagerTypeEnum tipoServizio;
	private String proxyHost;
	private int proxyPort;
	private String proxyUser;
	private String proxyPassword;
	private String host;
	private int port;
	private String user;
	private String password;
	private int numTentativiCollegamento = 1;
	private int timeoutConnessione = 0;

	public FileManagerConfig() {
	}

	public FileManagerTypeEnum getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(FileManagerTypeEnum tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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

	public int getNumTentativiCollegamento() {
		return numTentativiCollegamento;
	}

	public void setNumTentativiCollegamento(int numTentativiCollegamento) {
		this.numTentativiCollegamento = numTentativiCollegamento;
	}

	public int getTimeoutConnessione() {
		return timeoutConnessione;
	}

	public void setTimeoutConnessione(int timeoutConnessione) {
		this.timeoutConnessione = timeoutConnessione;
	}

	@Override
	public String toString() {
		return String.format(
				"FileManagerConfig [tipoServizio=%s, proxyHost=%s, proxyPort=%s, proxyUser=%s, proxyPassword=%s, host=%s, port=%s, user=%s, password=%s, numTentativiCollegamento=%s, timeoutConnessione=%s]",
				tipoServizio, proxyHost, proxyPort, proxyUser, proxyPassword, host, port, user, password, numTentativiCollegamento, timeoutConnessione);
	}

}
