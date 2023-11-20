/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

public class SFTPWrapPool extends GenericObjectPool<SFTPWrap> {

	private Logger logger = Logger.getLogger(SFTPWrapPool.class);

	/**
	 * Constructor.
	 * 
	 * It uses the default configuration for pool provided by apache-commons-pool2.
	 * 
	 * @param factory
	 */
	public SFTPWrapPool(PooledObjectFactory<SFTPWrap> factory) {
		super(factory);
	}

	/**
	 * 
	 * 
	 * @param factory
	 * @param config
	 */
	public SFTPWrapPool(PooledObjectFactory<SFTPWrap> factory, GenericObjectPoolConfig config) {
		super(factory, config);
	}

	@Override
	public SFTPWrap borrowObject() throws Exception {
		debug(poolRealTimeInfo());
		return super.borrowObject();
	}

	@Override
	public void returnObject(SFTPWrap obj) {
		debug(poolRealTimeInfo());
		super.returnObject(obj);
	}

	@Override
	public void invalidateObject(SFTPWrap obj) throws Exception {
		debug(poolRealTimeInfo());
		super.invalidateObject(obj);
	}

	private void debug(String message) {
		logger.debug(message);
	}

	public String poolRealTimeInfo() {
		StringBuffer ret = new StringBuffer();
		ret.append("BorrowedCount:").append(this.getBorrowedCount()).append(";");
		ret.append("CreatedCount:").append(this.getCreatedCount()).append(";");
		ret.append("NumActive:").append(this.getNumActive()).append(";");
		ret.append("NumIdle:").append(this.getNumIdle()).append(";");
		ret.append("ReturnedCount:").append(this.getReturnedCount()).append(";");
		return ret.toString();
	}
}
