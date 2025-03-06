package it.eng.hybrid.module.stampaEtichette.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "page")
public class PrintBean {

	private LabelBean labels;
	private String pageHeight;
	private String pageWidth;
	private String margin;
	private String orientation;

	public LabelBean getLabels() {
		return labels;
	}

	public void setLabels(LabelBean labels) {
		this.labels = labels;
	}
	@XmlAttribute(name = "page-height")
	public String getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(String pageHeight) {
		this.pageHeight = pageHeight;
	}
	@XmlAttribute(name = "page-width")
	public String getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}
	@XmlAttribute
	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}
	
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	@XmlAttribute(name = "reference-orientation")
	public String getOrientation() {
		return orientation;
	}
	
	
}
