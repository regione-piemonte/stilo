/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

public class ImportExcelBean {

		private String uriExcel;
		private String mimetype;
		private HashMap<String ,String> mappaObbligatorieta;
		
		public String getUriExcel() {
			return uriExcel;
		}
		public String getMimetype() {
			return mimetype;
		}
		public HashMap<String, String> getMappaObbligatorieta() {
			return mappaObbligatorieta;
		}
		public void setUriExcel(String uriExcel) {
			this.uriExcel = uriExcel;
		}
		public void setMimetype(String mimetype) {
			this.mimetype = mimetype;
		}
		public void setMappaObbligatorieta(HashMap<String, String> mappaObbligatorieta) {
			this.mappaObbligatorieta = mappaObbligatorieta;
		}
}
