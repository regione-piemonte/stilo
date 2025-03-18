/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Oggetto che contiene al proprio interno il nome di una property di una Entity. (campo propertyName)
 * Nel caso in cui questa property corrisponda ad una FK, contiene anche il nome della PK della chiave esterna (campo propertyPKName)
 * @author upescato
 *
 */

public class PropValue {
		private String propertyName;			//Nome della property
		private String propertyPKName;			//Nome della primary key della property (!=null se la property in questione è una FK)
		
		public String getPropertyName() {
			return propertyName;
		}
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
		public String getPropertyPKName() {
			return propertyPKName;
		}
		public void setPropertyPKName(String propertyPKName) {
			this.propertyPKName = propertyPKName;
		}
	}
