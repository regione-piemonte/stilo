/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.EscapeHtmlClient;
import it.eng.utility.ui.module.core.client.SJCLClient;
import it.eng.utility.ui.module.core.client.ScriptCleanerClient;

public interface ClientFactory {
	
	SJCLClient getSJCLClient();
	ScriptCleanerClient getScriptCleanerClient();
	EscapeHtmlClient getEcapeHtmlClient();

}
