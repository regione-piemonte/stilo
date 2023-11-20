/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.GwtEvent;
import com.smartgwt.client.data.DataSourceField;

public class ChangeValueMapEvent extends GwtEvent<ChangeValueMapHandler> {
	
	private final ConfigurableFilter filter;
	private final DataSourceField field;
	private boolean canFilter;
	 
	 public ChangeValueMapEvent(ConfigurableFilter filter, DataSourceField field, boolean canFilter) {		
		this.filter = filter;
	  	this.field = field;
	  	this.canFilter = canFilter;
	 }

	public static Type<ChangeValueMapHandler> TYPE = new Type<ChangeValueMapHandler>();

	/**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<ChangeValueMapHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<ChangeValueMapHandler>();
        }
        return TYPE;
    }

    
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeValueMapHandler> getAssociatedType() {
		
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeValueMapHandler handler) {
		
		handler.onChangeValueMap(this);
	}


	public ConfigurableFilter getFilter() {
		return filter;
	}

	public DataSourceField getField() {
		return field;
	}
	
	public boolean isCanFilter() {
		return canFilter;
	}
	
}
