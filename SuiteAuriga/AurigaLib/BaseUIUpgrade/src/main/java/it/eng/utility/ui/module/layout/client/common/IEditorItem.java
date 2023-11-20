/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface IEditorItem {
	
	public abstract String getValue();
	
	public abstract boolean isCKEditor();
	
	public abstract void manageOnDestroy();
	
}
