package it.eng.core.business.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExportListener implements ExportListener {

	private static final Logger logger = LoggerFactory.getLogger(AbstractExportListener.class);

	public void manageEvent(ExportEvent event) throws Exception {
		ExportEventType type = event.getType();

		switch (type) {
		case START:
			onInit(event);
			break;
		case RUNNING:
			onRunning(event);
			break;
		case DATA:
			onData(event);
			break;
		case FINISH:
			onFinish(event);
			break;
		default:
			logger.warn("Evento non gestito" + event);
			break;
		}
	}

	public abstract void onInit(ExportEvent ee) throws Exception;

	public abstract void onData(ExportEvent ee) throws Exception;

	public abstract void onRunning(ExportEvent ee) throws Exception;

	public abstract void onFinish(ExportEvent ee) throws Exception;

}
