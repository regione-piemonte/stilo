package it.eng.utility.scanner.twain.applet.enums;

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


