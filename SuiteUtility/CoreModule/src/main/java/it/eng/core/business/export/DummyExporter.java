package it.eng.core.business.export;

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
