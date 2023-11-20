/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.GwtEvent;
import com.smartgwt.client.data.DataSourceField;

public class ChangeDependsFromEvent extends GwtEvent<ChangeDependsFromHandler> {
	
	private final ConfigurableFilter filter;
	private final DataSourceField field;
	 
	 public ChangeDependsFromEvent(ConfigurableFilter filter, DataSourceField field) {
		this.filter = filter;
	  	this.field = field;
	 }

	public static Type<ChangeDependsFromHandler> TYPE = new Type<ChangeDependsFromHandler>();

	/**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<ChangeDependsFromHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<ChangeDependsFromHandler>();
        }
        return TYPE;
    }

    
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeDependsFromHandler> getAssociatedType() {
		
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeDependsFromHandler handler) {
		
		handler.onChangeDependsFrom(this);
	}


	public ConfigurableFilter getFilter() {
		return filter;
	}

	public DataSourceField getField() {
		return field;
	}
	
}
