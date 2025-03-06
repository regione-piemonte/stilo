package it.eng.client.applet;
/**
 * bean che contiene le info di configurazione di ogni widget di configurazione
 * @author Russo
 *
 */
public class WidgetInfo {
	
	private WidgetType type;
	private boolean allowEdit;
	private boolean visible;
	private String propValue;//prop di conf da cui prendere il valore del widget
	private String optionsProp;
	private boolean encrypt;
	 
	public String getOptionsProp() {
		return optionsProp;
	}
	public void setOptionsProp(String optionsProp) {
		this.optionsProp = optionsProp;
	}
	public WidgetType getType() {
		return type;
	}
	public void setType(WidgetType type) {
		this.type = type;
	}
	public boolean isAllowEdit() {
		return allowEdit;
	}
	public void setAllowEdit(boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	public boolean isEncrypt() {
		return encrypt;
	}
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
	
}
