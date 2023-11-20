/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.GwtEvent;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class ChangeCanEditEvent extends GwtEvent<ChangeCanEditHandler> {
	
	private final FormItem item;
	 
	 public ChangeCanEditEvent(FormItem item) {
		this.item = item;
	 }

	public static Type<ChangeCanEditHandler> TYPE = new Type<ChangeCanEditHandler>();

	/**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<ChangeCanEditHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<ChangeCanEditHandler>();
        }
        return TYPE;
    }

    
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeCanEditHandler> getAssociatedType() {
		
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeCanEditHandler handler) {
		
		handler.onChangeCanEdit(this);
	}


	public FormItem getItem() {
		return item;
	}
	
}
