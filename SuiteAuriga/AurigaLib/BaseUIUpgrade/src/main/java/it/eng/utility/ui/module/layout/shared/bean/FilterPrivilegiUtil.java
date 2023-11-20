/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;



public abstract interface FilterPrivilegiUtil {
	
	public boolean isRequired(String nomeFiltro, String[] privilegi);
	public Map<String, List<String>> getMap();
	public void setContainer(FilterPrivilegiContainer pFilterPrivilegiContainer);
	public FilterPrivilegiContainer getContainer();
}
