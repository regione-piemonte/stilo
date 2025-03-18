/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.EventListener;

public interface ExportListener   extends EventListener{
	public void manageEvent(ExportEvent  event) throws Exception;
	 
}
