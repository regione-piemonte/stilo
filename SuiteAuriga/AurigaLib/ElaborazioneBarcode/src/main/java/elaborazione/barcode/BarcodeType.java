/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum BarcodeType {

	CODE39("CODE39"),
	CODE128("CODE128"),
	PDF417("PDF417"),
	QRCODE("QRCODE"),
	DATAMATRIX("DATAMATRIX");
	
	private String type;
	
	private BarcodeType(String type) {
        this.type = type;
    }

	public String getType() {
		return type;
	}

}
