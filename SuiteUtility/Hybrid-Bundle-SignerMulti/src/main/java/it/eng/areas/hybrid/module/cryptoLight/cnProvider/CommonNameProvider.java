package it.eng.areas.hybrid.module.cryptoLight.cnProvider;

import java.util.List;

public interface CommonNameProvider {
	
	public List<String> sendCommonName(String commonName);
	public void saveOutputParameter() throws Exception;
	
}
