/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface IAuthenticationBean extends Serializable{
	
	public Map<String, String> describe();
	
	public void populateFromDescription( Map<String, String> description);
	
	public Set<String> listProperties();
	
	public String getApplicationName();



}
