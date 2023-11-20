/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Enum che definisce una lista dei servizi esposti che necessitano dell'ordinamento degli attachment lato server all'interno del request MIME
 * @author Mattia Donami
 *
 */
public enum OrderAttachServicesEnum {
	
	SERVICE_WSADDUD("WSAddUd"), SERVICE_WSUPDUD("WSUpdUd");
	

	private final String service;
	
	OrderAttachServicesEnum(String service){
		this.service = service;
	}

	public String getService() {
		return service;
	}
	
	public static OrderAttachServicesEnum fromValue(String service) {
		for (OrderAttachServicesEnum serviceEnum : OrderAttachServicesEnum.values()) {
			if (serviceEnum.service.equalsIgnoreCase(service)) {
				return serviceEnum;
			}
		}
		return null;
	}
}
