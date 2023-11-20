/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FocusEvent;
import com.smartgwt.client.widgets.form.fields.events.FocusHandler;

/**
 * TextItem with {@link #addChangedBlurHandler(ChangedHandler)} event
 * 
 * @author Mihai Ile
 * 
 */
public class ExtendedNumericItem extends NumericItem {
	
	private boolean firstChangedBlurHandler = true;
	protected Object initialFocusValue;
	protected boolean initialFocusValueSet;
	protected ChangedEvent lastChangedEvent;
	protected ChangeEvent lastChangeEvent;
	private List<ChangedHandler> changedBlurHandlers;
	private HandlerRegistration addFocusHandler;
	private HandlerRegistration addChangeHandler;
	private HandlerRegistration addChangedHandler;
	private HandlerRegistration addBlurHandler;
	private Boolean validateOnChangeBlur = false;
	
	public ExtendedNumericItem() {
		super();
	}
	
	public ExtendedNumericItem(String name) {
		super(name);	            
    }

    public ExtendedNumericItem(String name, String title) {
    	super(name, title);
    }
    
    public ExtendedNumericItem(boolean hasFloat) {
    	super(hasFloat);
	}
	
	public ExtendedNumericItem(String name, boolean hasFloat) {
		super(name, hasFloat);	            
    }

    public ExtendedNumericItem(String name, String title, boolean hasFloat) {
    	super(name, title, hasFloat);
    }
    
    public ExtendedNumericItem(boolean hasFloat, Integer nroDecimali) {
    	super(hasFloat, nroDecimali);
	}
	
	public ExtendedNumericItem(String name, boolean hasFloat, Integer nroDecimali) {
		super(name, hasFloat, nroDecimali);       
    }

    public ExtendedNumericItem(String name, String title, boolean hasFloat, Integer nroDecimali) {
    	super(name, title, hasFloat, nroDecimali);
    }
	
	/**
	 * Registration used to free resources whenever possible
	 * 
	 * @author Mihai Ile
	 * 
	 */
	private class HandlerRegistrationExtended implements HandlerRegistration {

		private final ChangedHandler handler;
		private final ExtendedNumericItem extendedTextItem;

		public HandlerRegistrationExtended(ExtendedNumericItem extendedTextItem, ChangedHandler handler) {
			this.extendedTextItem = extendedTextItem;
			this.handler = handler;
		}

		public void removeHandler() {
			extendedTextItem.removeChangedBlurHandler(handler);
		}
	}
	
	private void removeChangedBlurHandler(ChangedHandler handler) {
		changedBlurHandlers.remove(handler);
		if (changedBlurHandlers.isEmpty()) {
			// reset the addChangedBlurHandler
			addFocusHandler.removeHandler();
			addChangeHandler.removeHandler();
			addChangedHandler.removeHandler();
			addBlurHandler.removeHandler();
			changedBlurHandlers = null;
			firstChangedBlurHandler = true;
		}
	}
	
	private void initChangedBlur() {
		
		// initialize the list of listeners
		changedBlurHandlers = new ArrayList<ChangedHandler>();
		
		// we need a focus handler to notify we need to reset the initial value 
		FocusHandler focusHandler = new FocusHandler() {
			public void onFocus(FocusEvent event) {
				initialFocusValueSet = false;
			}
		};
		addFocusHandler = super.addFocusHandler(focusHandler);
		
		// we need a change handler to set the initial value in needed
		ChangeHandler changeHandler = new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String value = (String) (event.getValue() != null ? event.getValue() : "");
				String oldValue = (String) (event.getOldValue() != null ? event.getOldValue() : "");
				if(value.equals(oldValue)) {
					event.cancel();
				} else {
					lastChangeEvent = event;
					if (!initialFocusValueSet) {
						initialFocusValueSet = true;
						initialFocusValue = event.getOldValue();
					}
				}
			}
		};
		addChangeHandler = super.addChangeHandler(changeHandler);

		// we need the changed handler to keep track of the last change made
		ChangedHandler changedHandler = new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				lastChangedEvent = event;
			}
		};
		addChangedHandler = super.addChangedHandler(changedHandler);

		// we need a blur handler to notify all listeners if a change occurred 
		BlurHandler blurHandler = new BlurHandler() {
			public void onBlur(BlurEvent event) {
				if (lastChangedEvent != null) {
					// something changed, check if value is different from the old one
					Object finalValue = lastChangedEvent.getValue();
					if (initialFocusValue != null && finalValue != null) {
						// check using equals
						if (!finalValue.equals(initialFocusValue)) {
							if (shouldNotifyListenersOfChange()) {
								notifyListenersOfChange();
							}
						}
					} else {
						// one of them is null, do the normal == check
						if (initialFocusValue != finalValue) {
							if (shouldNotifyListenersOfChange()) {
								notifyListenersOfChange();
							}
						}
					}
					lastChangedEvent = null;
				}
			}
		};
		addBlurHandler = super.addBlurHandler(blurHandler);
	}

	private boolean shouldNotifyListenersOfChange() {
		boolean res = true; // by default should notify
		if(getForm() != null) {
			getForm().clearErrors(true);
		}
		if (validateOnChangeBlur) {
			setValidateOnChange(true);
			res = handleChange(lastChangedEvent.getValue(), initialFocusValue);
			setValidateOnChange(false);			
		}
		return res;
	}

	/**
	 * Notify all listeners about a change of the item's value
	 * @param event 
	 */
	private void notifyListenersOfChange() {
		
		// execute on copy because some listener could ask to remove itself from the original
		// changedBlurHandlers list using HandlerRegistration.removeHandler() while we are still
		// looping it.
		ArrayList<ChangedHandler> copy = new ArrayList<ChangedHandler>();
		for (ChangedHandler changedHandler : changedBlurHandlers) {
			copy.add(changedHandler);
		}
		// loop through the copy instead of the original list
		for (ChangedHandler handler : copy) {
			handler.onChanged(lastChangedEvent);
		}
	}

	/**
	 * Add a changed handler fired when the component loses focus (similar to
	 * "onchage" from JavaScript).
	 * <p>
	 * Called when the component loses focus and the FormItem's value has been
	 * changed as the result of user interaction during the period that it had
	 * focus.
	 * 
	 * @param handler
	 *            the changed handler
	 */
	public HandlerRegistration addChangedBlurHandler(ChangedHandler handler) {
		if (firstChangedBlurHandler) {
			// initialize changedBlurHandler support
			firstChangedBlurHandler = false;
			initChangedBlur();
		}
		changedBlurHandlers.add(handler);
		
		return new HandlerRegistrationExtended(this, handler);
	}
	
    /**
	 * If true, form items will be validated when each item's "changeBlur" handler
	 * is fired as well as when the entire form is submitted or validated.
	 * <p>
	 * 
	 * @param validateOnChangeBlur
	 *            validateOnChangeBlur Default value is false
	 */
	public void setValidateOnChangeBlur(Boolean validateOnChangeBlur) {
		this.validateOnChangeBlur = validateOnChangeBlur;
	}
	
    private native boolean handleChange(Object newValue, Object oldValue) /*-{
		var self = this.@com.smartgwt.client.core.DataClass::getJsObj()();
		return self.handleChange(newValue, oldValue);
	}-*/;
	
}