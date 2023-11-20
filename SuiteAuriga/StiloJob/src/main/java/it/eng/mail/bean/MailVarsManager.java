/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public interface MailVarsManager {
	public Map<TemplateVarsEnum, Object> creaListaEmailVars();
}
