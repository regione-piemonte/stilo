/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum FruitoreEnum {
	
	ATTITRASP("ATTITRASP");
	
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
