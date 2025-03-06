package it.eng.core.service.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface IAuthenticationBean extends Serializable{
	
	public Map<String, String> describe();
	
	public void populateFromDescription( Map<String, String> description);
	
	public Set<String> listProperties();
	
	public String getApplicationName();



}
