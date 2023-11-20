/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

public class SchemaSelector {

	private ArrayList<SchemaBean> schemi = new ArrayList<SchemaBean>();
	private Boolean showSelection = true;
	private SchemaBean defaultSchema;

	public void setSchemi(ArrayList<SchemaBean> schemi) {
		this.schemi = schemi;
	}

	public ArrayList<SchemaBean> getSchemi() {
		return schemi;
	}

	public void addSchema(SchemaBean lSchemaBean) {
		schemi.add(lSchemaBean);
	}

	public void setShowSelection(Boolean showSelection) {
		this.showSelection = showSelection;
	}

	public Boolean getShowSelection() {
		return showSelection;
	}

	public SchemaBean getDefaultSchema() {
		return defaultSchema;
	}

	public void setDefaultSchema(SchemaBean defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

}
