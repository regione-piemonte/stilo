/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyExporter extends AbstractExportListener {

	private static final Logger logger = LoggerFactory.getLogger(DummyExporter.class);

	@Override
	public void onInit(ExportEvent ee) throws Exception {
		logger.debug("onInit.....");

	}

	@Override
	public void onData(ExportEvent ee) throws Exception {
		logger.debug("onData.....");

	}

	@Override
	public void onRunning(ExportEvent ee) throws Exception {
		logger.debug("onRunning.....");

	}

	@Override
	public void onFinish(ExportEvent ee) throws Exception {
		logger.debug("onFinish.....");

	}

}
