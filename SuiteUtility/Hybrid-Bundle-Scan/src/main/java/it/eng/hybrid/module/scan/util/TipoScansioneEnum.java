package it.eng.hybrid.module.scan.util;

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


