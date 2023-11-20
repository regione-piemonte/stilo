/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SFTPStorageConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_PORT = 22;
	public static final int DEFAULT_MAX_NUM_RETRIEVE = 5;
	public static final int DEFAULT_TIMEOUT = 60000;

	private String host;
	private String username;
	private String password;
	private int port = -100;
	private int maxNumRetrieve = -100;
	private String workingDir;
	private String tempRepositoryBasePath;
	private int timeout = -100;

	private int nroMaxFiles = -100;

	private int maxIdle;
	private int maxTotal;
	private int minIdle;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTempRepositoryBasePath() {
		return tempRepositoryBasePath;
	}

	public void setTempRepositoryBasePath(String tempRepositoryBasePath) {
		this.tempRepositoryBasePath = tempRepositoryBasePath;
	}

	public int getPort() {
		if (this.port == -100) {
			this.port = DEFAULT_PORT;
		}
		return port;
	}

	public void setPort(int portHost) {
		this.port = portHost;
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getMaxNumRetrieve() {
		return maxNumRetrieve;
	}

	public void setMaxNumRetrieve(int maxNumRetrieve) {
		this.maxNumRetrieve = maxNumRetrieve;
	}

	public int getNroMaxFiles() {
		return nroMaxFiles;
	}

	public void setNroMaxFiles(int nroMaxFiles) {
		this.nroMaxFiles = nroMaxFiles;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		if (this.timeout == -100) {
			this.timeout = DEFAULT_TIMEOUT;
		}
		this.timeout = timeout;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

}
