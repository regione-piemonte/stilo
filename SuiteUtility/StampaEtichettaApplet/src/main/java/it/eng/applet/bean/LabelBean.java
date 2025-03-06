package it.eng.applet.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

public class LabelBean {

	private List<LabelDetailBean> label;
	private String fontFamily;

	public List<LabelDetailBean> getLabel() {
		return label;
	}
	public void setLabel(List<LabelDetailBean> labels) {
		this.label = labels;
	}
	@XmlAttribute(name = "font-family")
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public static class LabelDetailBean {

		private List<Riga> riga;
		private Barcode barCode;
		private BarcodeProperty barcodeProperty;
		
		public List<Riga> getRiga() {
			return riga;
		}
		public void setRiga(List<Riga> riga) {
			this.riga = riga;
		}
		public Barcode getBarCode() {
			return barCode;
		}
		public void setBarCode(Barcode barCode) {
			this.barCode = barCode;
		}
		public BarcodeProperty getBarcodeProperty() {
			return barcodeProperty;
		}
		public void setBarcodeProperty(BarcodeProperty barcodeProperty) {
			this.barcodeProperty = barcodeProperty;
		}
		
		public static class Barcode {
			
			private String textAlign;
			private String marginBottom;
			private String value;
			private String orientation;
			
			@XmlAttribute(name = "text-align")
			public String getTextAlign() {
				return textAlign;
			}
			public void setTextAlign(String textAlign) {
				this.textAlign = textAlign;
			}
			@XmlAttribute(name = "margin-bottom")
			public String getMarginBottom() {
				return marginBottom;
			}
			public void setMarginBottom(String marginBottom) {
				this.marginBottom = marginBottom;
			}
			public void setValue(String value) {
				this.value = value;
			}
			@XmlValue
			public String getValue() {
				return value;
			}
			@XmlAttribute
			public String getOrientation() {
				return orientation;
			}
			public void setOrientation(String orientation) {
				this.orientation = orientation;
			}
		}
		
		public static class BarcodeProperty {

			private String height;
			private String moduleWidth;
			private String wideFactor;
			private QuietZone quietZone;
			private HumanReadable humanReadable;
			
			public String getHeight() {
				return height;
			}
			public void setHeight(String height) {
				this.height = height;
			}
			@XmlElement(name = "module-width")
			public String getModuleWidth() {
				return moduleWidth;
			}
			public void setModuleWidth(String moduleWidth) {
				this.moduleWidth = moduleWidth;
			}
			@XmlElement(name = "wide-factor")
			public String getWideFactor() {
				return wideFactor;
			}
			public void setWideFactor(String wideFactor) {
				this.wideFactor = wideFactor;
			}
			public void setQuietZone(QuietZone quietZone) {
				this.quietZone = quietZone;
			}
			@XmlElement(name = "quiet-zone")
			public QuietZone getQuietZone() {
				return quietZone;
			}
			@XmlElement(name = "human-readable")
			public HumanReadable getHumanReadable() {
				return humanReadable;
			}
			public void setHumanReadable(HumanReadable humanReadable) {
				this.humanReadable = humanReadable;
			}

			public static class HumanReadable {
			
				private String placement;
				
				public String getPlacement() {
					return placement;
				}
				public void setPlacement(String placement) {
					this.placement = placement;
				}
			}

			public static class QuietZone {
				
				private Boolean enabled;
				private String value;
				@XmlAttribute(required = true)
				public Boolean getEnabled() {
					return enabled;
				}
				public void setEnabled(Boolean enabled) {
					this.enabled = enabled;
				}
				@XmlValue
				public String getValue() {
					return value;
				}
				public void setValue(String value) {
					this.value = value;
				}
			}
		}

		public static class Riga {

			private String value;
			private String fontSize;
			private String textAlign;
			private String marginBottom;
			private String marginTop;
			private String fontWeight;
			
			@XmlValue
			public String getValue() {
				return value;
			}
			public void setValue(String value) {
				this.value = value;
			}
			@XmlAttribute(name = "font-size")
			public String getFontSize() {
				return fontSize;
			}
			public void setFontSize(String fontSize) {
				this.fontSize = fontSize;
			}
			@XmlAttribute(name = "text-align")
			public String getTextAlign() {
				return textAlign;
			}
			public void setTextAlign(String textAlign) {
				this.textAlign = textAlign;
			}
			@XmlAttribute(name = "margin-bottom")
			public String getMarginBottom() {
				return marginBottom;
			}
			public void setMarginBottom(String marginBottom) {
				this.marginBottom = marginBottom;
			}
			@XmlAttribute(name = "font-weight")
			public String getFontWeight() {
				return fontWeight;
			}
			public void setFontWeight(String fontWeight) {
				this.fontWeight = fontWeight;
			}
			@XmlAttribute(name = "margin-top")
			public String getMarginTop() {
				return marginTop;
			}
			public void setMarginTop(String marginTop) {
				this.marginTop = marginTop;
			}
		}
	}


}
