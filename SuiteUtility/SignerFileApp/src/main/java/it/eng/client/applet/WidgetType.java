package it.eng.client.applet;
/**
 * tipo di widget possibili per la gestione da pannello di conf
 * @author Russo
 *
 */
public enum WidgetType {
	TEXT,COMBO,PASSWORD,CHECKBOX;
	
	 public static WidgetType fromValue(String v) {
	        for (WidgetType c: WidgetType.values()) {
	            if (c.name().equalsIgnoreCase(v)) {
	                return c;
	            }
	        }
	         return null;
	    }
}
