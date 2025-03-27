/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.job.trasparenza.bean;

public enum FruitoreEnum {
	
	ATTITRASP("ATTITRASP"),
	ASLVC_DD("ASLVC_DD"),
	ASLVC_DG("ASLVC_DG");
	
	private final String description;
	
	private FruitoreEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return String.format("%s", description);
	}
	
}
