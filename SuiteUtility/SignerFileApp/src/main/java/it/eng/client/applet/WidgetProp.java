package it.eng.client.applet;

/**
 * proprietà lette dalla configurazione per ogni widget
 * @author Russo
 *
 */
public enum WidgetProp {
	TYPE("type"),
	VISIBLE("visible"),
	ALLOW_EDIT("allowEdit"),
	VALUE_PROP("valueProp"),
	OPTIONSPROP("optionsProp"),
	ENCRYPT("encrypt");
	private final String value;

	WidgetProp(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WidgetProp fromValue(String v) {
        for (WidgetProp c: WidgetProp.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
