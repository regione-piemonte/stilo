/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.log4j.Logger;

public class FTPWrapFactory extends BasePooledObjectFactory<FTPWrap> {

	private String ftpHost;
	private String ftpUsername;
	private String ftpPassword;
	private String protocol;
	private int ftpPort = 22;
	private int timeout = 360000;

	private boolean overwrite;
	private boolean passiveMode;
	private boolean isDebugEnabled;
	private String workingDir;

	private Logger logger = Logger.getLogger(FTPWrapFactory.class);

	public FTPWrapFactory(String ftpUsername, String ftpPassword, String ftpHost, int ftpPort, int timeout, String protocol, boolean overwrite,
			boolean passiveMode, String workingDir, boolean isDebugEnabled) {
		this.ftpUsername = ftpUsername;
		this.ftpPassword = ftpPassword;
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.timeout = timeout;
		this.protocol = protocol;
		this.overwrite = overwrite;
		this.passiveMode = passiveMode;
		this.workingDir = workingDir;
		this.isDebugEnabled = isDebugEnabled;
	}

	@Override
	public FTPWrap create() throws Exception {
		debug("Created SFTPWrap");
		return new FTPWrap(this.ftpUsername, this.ftpPassword, this.ftpHost, this.ftpPort, this.timeout, this.protocol, this.overwrite, this.passiveMode,
				this.workingDir, this.isDebugEnabled);
	}

	@Override
	public PooledObject<FTPWrap> wrap(FTPWrap obj) {
		debug("Wrap SFTPWrap");
		try {
			obj.init();
		} catch (Exception e) {
			error("Wrap Error [" + e.getMessage() + "]");
		}
		return new DefaultPooledObject<FTPWrap>(obj);
	}

	@Override
	public void destroyObject(PooledObject<FTPWrap> p) throws Exception {
		super.destroyObject(p);
		debug("Destroy SFTPWrap");
		p.getObject().close();
	}

	@Override
	public boolean validateObject(PooledObject<FTPWrap> p) {
		boolean ret = false;

		try {
			ret = p.getObject().check();
			debug("Validate SFTPWrap [" + ret + "]");
		} catch (Exception e) {
			error("Validation error [" + e.getMessage() + "]");
		}

		return super.validateObject(p);
	}

	@Override
	public void activateObject(PooledObject<FTPWrap> p) throws Exception {
		super.activateObject(p);
		debug("Activate SFTPWrap");
	}

	@Override
	public void passivateObject(PooledObject<FTPWrap> p) throws Exception {
		super.passivateObject(p);
		debug("Passivate SFTPWrap");
	}

	@Override
	public PooledObject<FTPWrap> makeObject() throws Exception {
		debug("Make SFTPWrap");
		return super.makeObject();
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private void debug(String message) {
		// System.out.println(message);
		logger.debug(message);
	}

	private void error(String message) {
		// System.out.println(message);
		logger.error(message);
	}

}
