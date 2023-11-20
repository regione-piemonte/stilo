/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class MappeUoProtocollanteBean {
	
	Map<String,String> uoRegistrazioneValueMap;
	Map<String,UoProtocollanteBean> mappaUoRegistrazione;
	Map<String,UoProtocollanteBean> mappaDestProtEntrataDefault;
	
	public Map<String, String> getUoRegistrazioneValueMap() {
		return uoRegistrazioneValueMap;
	}
	public void setUoRegistrazioneValueMap(Map<String, String> uoRegistrazioneValueMap) {
		this.uoRegistrazioneValueMap = uoRegistrazioneValueMap;
	}
	public Map<String, UoProtocollanteBean> getMappaUoRegistrazione() {
		return mappaUoRegistrazione;
	}
	public void setMappaUoRegistrazione(Map<String, UoProtocollanteBean> mappaUoRegistrazione) {
		this.mappaUoRegistrazione = mappaUoRegistrazione;
	}
	public Map<String, UoProtocollanteBean> getMappaDestProtEntrataDefault() {
		return mappaDestProtEntrataDefault;
	}
	public void setMappaDestProtEntrataDefault(Map<String, UoProtocollanteBean> mappaDestProtEntrataDefault) {
		this.mappaDestProtEntrataDefault = mappaDestProtEntrataDefault;
	}
	
}
