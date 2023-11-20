/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public abstract class StoreBean {

	public enum StoreType { STORE, FUNCTION}
	protected StoreType type;

	public abstract String getErrmsgout();
	public abstract Integer getErrcodeout();
	public abstract String getErrcontextout();
	public abstract String getStoreName();
	public StoreType getType() {
		return type;
	}
}
