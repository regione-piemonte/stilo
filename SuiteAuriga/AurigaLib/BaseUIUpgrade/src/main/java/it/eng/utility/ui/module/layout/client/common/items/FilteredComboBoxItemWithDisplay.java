/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;

public class FilteredComboBoxItemWithDisplay extends FilteredComboBoxItem {

	public FilteredComboBoxItemWithDisplay(String name, SelectGWTRestDataSource datasource) {
		super();
	    setName(name);  
	    setOptionDataSource(datasource);
	    setDisplayField("displayValue");
    }

    public FilteredComboBoxItemWithDisplay(String name, String title, SelectGWTRestDataSource datasource) {
    	super();
        setName(name);
		setTitle(title);
		setOptionDataSource(datasource);
		setDisplayField("displayValue");
    }
}
