/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.log4j.Logger;

public class SFTPWrapFactory extends BasePooledObjectFactory<SFTPWrap> {

	private String sftpHost;
	private String sftpUsername;
	private String sftpPassword;
	private int sftpPort = 22;
	private int timeout = 360000;
	private Logger logger = Logger.getLogger(SFTPWrapFactory.class);

	public SFTPWrapFactory(String sftpUsername, String sftpPassword, String sftpHost, int sftpPort, int timeout) {
		this.sftpUsername = sftpUsername;
		this.sftpPassword = sftpPassword;
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;
		this.timeout = timeout;

	}

	@Override
	public SFTPWrap create() throws Exception {
		debug("Created SFTPWrap");
		return new SFTPWrap(this.sftpUsername, this.sftpPassword, this.sftpHost, this.sftpPort, this.timeout);
	}

	@Override
	public PooledObject<SFTPWrap> wrap(SFTPWrap obj) {
		debug("Wrap SFTPWrap");
		try {
			obj.init();
		} catch (Exception e) {
			error("Wrap Error [" + e.getMessage() + "]");
		}
		return new DefaultPooledObject<SFTPWrap>(obj);
	}

	@Override
	public void destroyObject(PooledObject<SFTPWrap> p) throws Exception {
		super.destroyObject(p);
		debug("Destroy SFTPWrap");
		p.getObject().close();
	}

	@Override
	public boolean validateObject(PooledObject<SFTPWrap> p) {
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
	public void activateObject(PooledObject<SFTPWrap> p) throws Exception {
		super.activateObject(p);
		debug("Activate SFTPWrap");
	}

	@Override
	public void passivateObject(PooledObject<SFTPWrap> p) throws Exception {
		super.passivateObject(p);
		debug("Passivate SFTPWrap");
	}

	@Override
	public PooledObject<SFTPWrap> makeObject() throws Exception {
		debug("Make SFTPWrap");
		return super.makeObject();
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public String getSftpUsername() {
		return sftpUsername;
	}

	public void setSftpUsername(String sftpUsername) {
		this.sftpUsername = sftpUsername;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
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
