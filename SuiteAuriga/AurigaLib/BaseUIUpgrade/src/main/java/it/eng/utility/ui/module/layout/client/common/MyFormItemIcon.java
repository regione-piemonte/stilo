/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class MyFormItemIcon extends FormItemIcon{
	
	private FormItem owner;

	public void setOwner(FormItem owner) {
		this.owner = owner;
	}

	public FormItem getOwner() {
		return owner;
	}
	
	
}
