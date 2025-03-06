package it.eng.areas.hybrid.gui.fx;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class FxManager extends Application {
	private static final Logger logger = Logger.getLogger(FxManager.class);

	public static Stage primaryStage;
	
	@Override
	public void init() throws Exception {
		super.init();
/* Scommentare se il JNLP avvia una Applicazione JFX
 		
		List<String> parameters = getParameters().getRaw();
		if (parameters.size() > 0) {
			String serverUrl = parameters.get(0);
			OSGiManager.getInstance().getClientModuleManager().setSharedProperty(IClientModuleContainer.PARAMETER_SERVERURL, serverUrl);
			if (parameters.size() > 1) {
				String serverSession = parameters.get(1);
				OSGiManager.getInstance().getClientModuleManager().setSharedProperty(IClientModuleContainer.PARAMETER_SERVERSESSION, serverSession);
			}
		}
*/		
	}
	
	@Override
	public void start(Stage primaryStage) {
		logger.debug("FxManager avviato");
		FxManager.primaryStage = primaryStage;
		Platform.setImplicitExit(false);
		
	}
	

}
