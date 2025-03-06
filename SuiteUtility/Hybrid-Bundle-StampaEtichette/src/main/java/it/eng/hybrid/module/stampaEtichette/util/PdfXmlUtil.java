package it.eng.hybrid.module.stampaEtichette.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.stampaEtichette.bean.LabelBean;
import it.eng.hybrid.module.stampaEtichette.bean.LabelBean.LabelDetailBean;
import it.eng.hybrid.module.stampaEtichette.bean.LabelBean.LabelDetailBean.Barcode;
import it.eng.hybrid.module.stampaEtichette.bean.LabelBean.LabelDetailBean.BarcodeProperty;
import it.eng.hybrid.module.stampaEtichette.bean.LabelBean.LabelDetailBean.BarcodeProperty.HumanReadable;
import it.eng.hybrid.module.stampaEtichette.bean.LabelBean.LabelDetailBean.BarcodeProperty.QuietZone;
import it.eng.hybrid.module.stampaEtichette.bean.LabelBean.LabelDetailBean.Riga;
import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfRigaBean;
import it.eng.hybrid.module.stampaEtichette.bean.PrintBean;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;

public class PdfXmlUtil {

	public final static Logger logger = Logger.getLogger(PdfXmlUtil.class);
	
	private static PdfPropertiesBean pdfProperties;
	private static ParameterBean parameter;

	public static String preparaXml(int numCopia, PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean) throws JAXBException {
		pdfProperties = pdfPropertiesBean;
		logger.info("pdfProperties " + pdfProperties);
		parameter = pParameterBean;
		TestoBarcodeBean lTestoBarcodeBean = RigaBarcodeUtil.getTestoBarcodeBean(numCopia, parameter);
		List<Riga> lRighe = new ArrayList<Riga>();
		LabelBean lLabelBean = new LabelBean();
		LabelDetailBean lLabelDetailBean = new LabelDetailBean();
		Barcode lBarcode = new Barcode();
		lBarcode.setMarginBottom(pdfProperties.getBarcodeMarginBottom());
		lBarcode.setValue(lTestoBarcodeBean.getBarcode());
		lBarcode.setTextAlign(pdfProperties.getBarCodeAlign());
		lBarcode.setOrientation(pdfProperties.getBarCodeOrientation());
		lBarcode.setValue(lTestoBarcodeBean.getBarcode());
		lLabelDetailBean.setBarCode(lBarcode);
		BarcodeProperty lBarcodeProperty = new BarcodeProperty();
		lBarcodeProperty.setHeight(pdfProperties.getBarCodeHeight());
		lBarcodeProperty.setModuleWidth(pdfProperties.getBarcodeModuleWidth());
		lBarcodeProperty.setWideFactor(pdfProperties.getBarCodeWideFactor());
		QuietZone lQuietZone = new QuietZone();
		lQuietZone.setEnabled(Boolean.valueOf(pdfProperties.getBarCodeQuietZoneEnabled()));
		lQuietZone.setValue(pdfProperties.getBarCodeQuietZoneValue());
		lBarcodeProperty.setQuietZone(lQuietZone);
		HumanReadable lHumanReadable = new HumanReadable();
		lHumanReadable.setPlacement(pdfProperties.getBarCodeHumanReadablePlacement());
		lBarcodeProperty.setHumanReadable(lHumanReadable);
		lLabelDetailBean.setBarcodeProperty(lBarcodeProperty);
		int i = 1;
		logger.debug("La scritta da scrivere è " + lTestoBarcodeBean.getTesto());
		logger.debug("Il barcode è: " + lTestoBarcodeBean.getBarcode());
		for (String lStrSplit : lTestoBarcodeBean.getTesto().split("\\|\\*\\|")){
			Riga lRiga = new Riga();
			logger.debug("La Riga " + i +" da scrivere � " + lStrSplit);
			lRiga.setValue(lStrSplit);
			PdfRigaBean lPdfRigaBean = RigaBarcodeUtil.getPdfRigaBean(i, pdfProperties);
			lRiga.setTextAlign(lPdfRigaBean.getTextAlign());
			lRiga.setFontSize(lPdfRigaBean.getFontSize());
			lRiga.setMarginBottom(lPdfRigaBean.getMarginBottom());
			lRiga.setFontWeight(lPdfRigaBean.getFontWeight());
			lRiga.setMarginTop(lPdfRigaBean.getMarginTop());
			lRighe.add(lRiga);
			i++;
		}
		lLabelDetailBean.setRiga(lRighe);
		lLabelBean.setLabel(Arrays.asList(new LabelDetailBean[]{lLabelDetailBean}));
		lLabelBean.setFontFamily(pdfProperties.getFontFamily());
		PrintBean lPrintBean = new PrintBean();
		lPrintBean.setMargin(pdfProperties.getMargin());
		lPrintBean.setPageHeight(pdfProperties.getPageHeight());
		lPrintBean.setPageWidth(pdfProperties.getPageWidth());
		//lPrintBean.setOrientation(pdfPropertiesBean.getOrientation());
		lPrintBean.setLabels(lLabelBean);
		String xml = SingletonJaxbMarshaller.transformToXml(lPrintBean);
		logger.debug("XML di preparaXml: " + xml);
		return xml;
	}

	
}
