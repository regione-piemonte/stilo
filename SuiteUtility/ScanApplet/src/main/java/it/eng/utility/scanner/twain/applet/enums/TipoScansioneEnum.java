/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum TipoScansioneEnum {
	
		COLOR("0"), BLACKWHITE("1");
		private String tipoScansioneCode;

		private TipoScansioneEnum(String s) {
			tipoScansioneCode = s;
		}

		public String getTipoScansioneCode() {
			return tipoScansioneCode;
		}

	}


