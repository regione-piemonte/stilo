/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum ProvenienzaRequestEnum {
	
	REQ_ORIGIN_LOTTO ("LOTTO_DOC"),
	REQ_ORIGIN_ELENCO("ELENCO_DOC");
	
	private final String provRequest;
	
	ProvenienzaRequestEnum(String provRequest){
		this.provRequest = provRequest;
	}

	public String getProvenienzaRequest() {
		return provRequest;
	}
	
	public static final ProvenienzaRequestEnum fromString(String provRequest) {
		for (ProvenienzaRequestEnum provRequestEnum : values()) {
			if (provRequestEnum.getProvenienzaRequest().equals(provRequest))
				return provRequestEnum;
		}
		return null;
	}
	
}
