/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;

public class AnnoItem extends ExtendedTextItem {
	
	public AnnoItem(){	
		super();
		setAttribute();
	}

	public AnnoItem(String name, String title) {
		super(name, title);
		setAttribute();
    }  
	
	protected void setAttribute() {

		setHeight(20);
		setWidth(70);

		this.setLength(4);
		this.setKeyPressFilter("[0-9]");
		this.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String valueStr = null;
				if (value != null)
					valueStr = String.valueOf(value);

				return valueStr == null || "".equals(valueStr) || (valueStr.length() == 4);
			}
		});
		this.setTextAlign(Alignment.RIGHT);	
	}
	
	@Deprecated
	@Override
	// Utilizzare addChangedBlurHandler
	public HandlerRegistration addChangedHandler(ChangedHandler handler) {
		return super.addChangedHandler(handler);
	}
	
}