/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public abstract class RequiredIfFunction {

	public String utente;
	public String[] privilegi;
	
	public abstract boolean isRequired(Map<String, Object> requiredIfMap);
}
