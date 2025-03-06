package it.eng.core.business.export;

import java.util.EventListener;

public interface ExportListener   extends EventListener{
	public void manageEvent(ExportEvent  event) throws Exception;
	 
}
