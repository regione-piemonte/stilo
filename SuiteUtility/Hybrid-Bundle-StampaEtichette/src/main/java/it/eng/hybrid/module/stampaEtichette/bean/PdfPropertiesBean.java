package it.eng.hybrid.module.stampaEtichette.bean;

import java.util.List;

public class PdfPropertiesBean {

	// Impostazioni impaginazione
	private String pageHeight;
	private String pageWidth;
	private String margin;
	private String fontFamily;
	private String labelWithoutCodeVertical;
	private String labelWithCodeAndTextVertical;
	// Impostazioni barcode generale
	private String barCodeAlign;
	// Impostazioni barcode 128
	private String barcodeModuleWidth;
	private String barCodeWideFactor;
	private String barCodeQuietZoneValue;
	private String barCodeHumanReadablePlacement;
	private String barcodeMarginBottom;
	private String barCodeQuietZoneEnabled;
	private String barCodeOrientation;
	private String barCodeHeight;
	// Impostazioni barcode PDF147
	private String pdf147YHeight;
	private String pdf147AspectRatio;
	private String pdf147ScalePercentX;
	private String pdf147ScalePercentY;
	// Impostazioni barcode datamatrix
	private String datamatrixHeight;
	private String datamatrixWidth;
	private String datamatrixWhitespace;
	// Impostazioni barcode QRCode
	private String qrCodeHeight;
	private String qrCodeWidth;
	private String qrCodeVertical;
	// Impostazioni barcode QRCode faldone
	private String qrCodeFaldoneHeight;
	private String qrCodeFaldoneWidth;
	private String qrCodeFaldoneVertical;
	// Impostazioni stampa
	private String metodoStampaEtichette;
	private String metodoStampaFile;
	private String printableResolution;
	private String renderImageType;
	private String antialias;
	private String alphaInterpolation;
	private String colorRender;
	private String dither;
	private String fractionalMetrics;
	private String interpolation;
	private String render;
	private String stroke;
	private String textAntialias;

	private List<PdfRigaBean> righe;
	private List<PdfRigaBean> secondLabelRighe;
	private List<PdfRigaBean> repertorioLabelRighe;
	private List<PdfRigaBean> faldoneLabelRighe;

	public String getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(String pageHeight) {
		this.pageHeight = pageHeight;
	}

	public String getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getLabelWithoutCodeVertical() {
		return labelWithoutCodeVertical;
	}

	public void setLabelWithoutCodeVertical(String labelWithoutCodeVertical) {
		this.labelWithoutCodeVertical = labelWithoutCodeVertical;
	}
	
	public String getLabelWithCodeAndTextVertical() {
		return labelWithCodeAndTextVertical;
	}

	public void setLabelWithCodeAndTextVertical(String labelWithCodeAndTextVertical) {
		this.labelWithCodeAndTextVertical = labelWithCodeAndTextVertical;
	}

	public String getBarCodeAlign() {
		return barCodeAlign;
	}

	public void setBarCodeAlign(String barCodeAlign) {
		this.barCodeAlign = barCodeAlign;
	}

	public String getBarcodeModuleWidth() {
		return barcodeModuleWidth;
	}

	public void setBarcodeModuleWidth(String barcodeModuleWidth) {
		this.barcodeModuleWidth = barcodeModuleWidth;
	}

	public String getBarCodeWideFactor() {
		return barCodeWideFactor;
	}

	public void setBarCodeWideFactor(String barCodeWideFactor) {
		this.barCodeWideFactor = barCodeWideFactor;
	}

	public String getBarCodeQuietZoneValue() {
		return barCodeQuietZoneValue;
	}

	public void setBarCodeQuietZoneValue(String barCodeQuietZoneValue) {
		this.barCodeQuietZoneValue = barCodeQuietZoneValue;
	}

	public String getBarCodeHumanReadablePlacement() {
		return barCodeHumanReadablePlacement;
	}

	public void setBarCodeHumanReadablePlacement(String barCodeHumanReadablePlacement) {
		this.barCodeHumanReadablePlacement = barCodeHumanReadablePlacement;
	}

	public String getBarcodeMarginBottom() {
		return barcodeMarginBottom;
	}

	public void setBarcodeMarginBottom(String barcodeMarginBottom) {
		this.barcodeMarginBottom = barcodeMarginBottom;
	}

	public String getBarCodeQuietZoneEnabled() {
		return barCodeQuietZoneEnabled;
	}

	public void setBarCodeQuietZoneEnabled(String barCodeQuietZoneEnabled) {
		this.barCodeQuietZoneEnabled = barCodeQuietZoneEnabled;
	}

	public String getBarCodeOrientation() {
		return barCodeOrientation;
	}

	public void setBarCodeOrientation(String barCodeOrientation) {
		this.barCodeOrientation = barCodeOrientation;
	}

	public String getBarCodeHeight() {
		return barCodeHeight;
	}

	public void setBarCodeHeight(String barCodeHeight) {
		this.barCodeHeight = barCodeHeight;
	}

	public String getPdf147YHeight() {
		return pdf147YHeight;
	}

	public void setPdf147YHeight(String pdf147yHeight) {
		pdf147YHeight = pdf147yHeight;
	}

	public String getPdf147AspectRatio() {
		return pdf147AspectRatio;
	}

	public void setPdf147AspectRatio(String pdf147AspectRatio) {
		this.pdf147AspectRatio = pdf147AspectRatio;
	}

	public String getPdf147ScalePercentX() {
		return pdf147ScalePercentX;
	}

	public void setPdf147ScalePercentX(String pdf147ScalePercentX) {
		this.pdf147ScalePercentX = pdf147ScalePercentX;
	}

	public String getPdf147ScalePercentY() {
		return pdf147ScalePercentY;
	}

	public void setPdf147ScalePercentY(String pdf147ScalePercentY) {
		this.pdf147ScalePercentY = pdf147ScalePercentY;
	}

	public String getDatamatrixHeight() {
		return datamatrixHeight;
	}

	public void setDatamatrixHeight(String datamatrixHeight) {
		this.datamatrixHeight = datamatrixHeight;
	}

	public String getDatamatrixWidth() {
		return datamatrixWidth;
	}

	public void setDatamatrixWidth(String datamatrixWidth) {
		this.datamatrixWidth = datamatrixWidth;
	}

	public String getDatamatrixWhitespace() {
		return datamatrixWhitespace;
	}

	public void setDatamatrixWhitespace(String datamatrixWhitespace) {
		this.datamatrixWhitespace = datamatrixWhitespace;
	}

	public String getQrCodeHeight() {
		return qrCodeHeight;
	}

	public void setQrCodeHeight(String qrCodeHeight) {
		this.qrCodeHeight = qrCodeHeight;
	}

	public String getQrCodeWidth() {
		return qrCodeWidth;
	}

	public void setQrCodeWidth(String qrCodeWidth) {
		this.qrCodeWidth = qrCodeWidth;
	}

	public String getQrCodeVertical() {
		return qrCodeVertical;
	}

	public void setQrCodeVertical(String qrCodeVertical) {
		this.qrCodeVertical = qrCodeVertical;
	}
	
	public String getQrCodeFaldoneHeight() {
		return qrCodeFaldoneHeight;
	}
	
	public void setQrCodeFaldoneHeight(String qrCodeFaldoneHeight) {
		this.qrCodeFaldoneHeight = qrCodeFaldoneHeight;
	}

	public String getQrCodeFaldoneWidth() {
		return qrCodeFaldoneWidth;
	}
	
	public void setQrCodeFaldoneWidth(String qrCodeFaldoneWidth) {
		this.qrCodeFaldoneWidth = qrCodeFaldoneWidth;
	}
	
	public String getQrCodeFaldoneVertical() {
		return qrCodeFaldoneVertical;
	}
	
	public void setQrCodeFaldoneVertical(String qrCodeFaldoneVertical) {
		this.qrCodeFaldoneVertical = qrCodeFaldoneVertical;
	}

	public String getMetodoStampaEtichette() {
		return metodoStampaEtichette;
	}

	public void setMetodoStampaEtichette(String metodoStampaEtichette) {
		this.metodoStampaEtichette = metodoStampaEtichette;
	}

	public String getMetodoStampaFile() {
		return metodoStampaFile;
	}

	public void setMetodoStampaFile(String metodoStampaFile) {
		this.metodoStampaFile = metodoStampaFile;
	}

	public String getPrintableResolution() {
		return printableResolution;
	}

	public void setPrintableResolution(String printableResolution) {
		this.printableResolution = printableResolution;
	}

	public String getRenderImageType() {
		return renderImageType;
	}

	public void setRenderImageType(String renderImageType) {
		this.renderImageType = renderImageType;
	}

	public String getAntialias() {
		return antialias;
	}

	public void setAntialias(String antialias) {
		this.antialias = antialias;
	}

	public String getAlphaInterpolation() {
		return alphaInterpolation;
	}

	public void setAlphaInterpolation(String alphaInterpolation) {
		this.alphaInterpolation = alphaInterpolation;
	}

	public String getColorRender() {
		return colorRender;
	}

	public void setColorRender(String colorRender) {
		this.colorRender = colorRender;
	}

	public String getDither() {
		return dither;
	}

	public void setDither(String dither) {
		this.dither = dither;
	}

	public String getFractionalMetrics() {
		return fractionalMetrics;
	}

	public void setFractionalMetrics(String fractionalMetrics) {
		this.fractionalMetrics = fractionalMetrics;
	}

	public String getInterpolation() {
		return interpolation;
	}

	public void setInterpolation(String interpolation) {
		this.interpolation = interpolation;
	}

	public String getRender() {
		return render;
	}

	public void setRender(String render) {
		this.render = render;
	}

	public String getStroke() {
		return stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public String getTextAntialias() {
		return textAntialias;
	}

	public void setTextAntialias(String textAntialias) {
		this.textAntialias = textAntialias;
	}

	public List<PdfRigaBean> getRighe() {
		return righe;
	}

	public void setRighe(List<PdfRigaBean> righe) {
		this.righe = righe;
	}

	public List<PdfRigaBean> getSecondLabelRighe() {
		return secondLabelRighe;
	}

	public void setSecondLabelRighe(List<PdfRigaBean> secondLabelRighe) {
		this.secondLabelRighe = secondLabelRighe;
	}

	public List<PdfRigaBean> getRepertorioLabelRighe() {
		return repertorioLabelRighe;
	}
	
	public void setRepertorioLabelRighe(List<PdfRigaBean> repertorioLabelRighe) {
		this.repertorioLabelRighe = repertorioLabelRighe;
	}
	
	public List<PdfRigaBean> getFaldoneLabelRighe() {
		return faldoneLabelRighe;
	}

	public void setFaldoneLabelRighe(List<PdfRigaBean> faldoneLabelRighe) {
		this.faldoneLabelRighe = faldoneLabelRighe;
	}

	@Override
	public String toString() {
		return "PdfPropertiesBean [pageHeight=" + pageHeight + ", pageWidth=" + pageWidth + ", margin=" + margin + ", fontFamily=" + fontFamily
				+ ", labelWithoutCodeVertical=" + labelWithoutCodeVertical + ", labelWithCodeAndTextVertical=" + labelWithCodeAndTextVertical + ", barCodeAlign="
				+ barCodeAlign + ", barcodeModuleWidth=" + barcodeModuleWidth + ", barCodeWideFactor=" + barCodeWideFactor + ", barCodeQuietZoneValue="
				+ barCodeQuietZoneValue + ", barCodeHumanReadablePlacement=" + barCodeHumanReadablePlacement + ", barcodeMarginBottom=" + barcodeMarginBottom
				+ ", barCodeQuietZoneEnabled=" + barCodeQuietZoneEnabled + ", barCodeOrientation=" + barCodeOrientation + ", barCodeHeight=" + barCodeHeight
				+ ", pdf147YHeight=" + pdf147YHeight + ", pdf147AspectRatio=" + pdf147AspectRatio + ", pdf147ScalePercentX=" + pdf147ScalePercentX
				+ ", pdf147ScalePercentY=" + pdf147ScalePercentY + ", datamatrixHeight=" + datamatrixHeight + ", datamatrixWidth=" + datamatrixWidth
				+ ", datamatrixWhitespace=" + datamatrixWhitespace + ", qrCodeHeight=" + qrCodeHeight + ", qrCodeWidth=" + qrCodeWidth + ", qrCodeVertical="
				+ qrCodeVertical + ", qrCodeFaldoneHeight=" + qrCodeFaldoneHeight + ", qrCodeFaldoneWidth=" + qrCodeFaldoneWidth + ", qrCodeFaldoneVertical="
				+ qrCodeFaldoneVertical + ", metodoStampaEtichette=" + metodoStampaEtichette + ", metodoStampaFile=" + metodoStampaFile
				+ ", printableResolution=" + printableResolution + ", renderImageType=" + renderImageType + ", antialias=" + antialias + ", alphaInterpolation="
				+ alphaInterpolation + ", colorRender=" + colorRender + ", dither=" + dither + ", fractionalMetrics=" + fractionalMetrics + ", interpolation="
				+ interpolation + ", render=" + render + ", stroke=" + stroke + ", textAntialias=" + textAntialias + ", righe=" + righe + ", secondLabelRighe="
				+ secondLabelRighe + ", repertorioLabelRighe=" + repertorioLabelRighe + ", faldoneLabelRighe=" + faldoneLabelRighe + "]";
	}

}
