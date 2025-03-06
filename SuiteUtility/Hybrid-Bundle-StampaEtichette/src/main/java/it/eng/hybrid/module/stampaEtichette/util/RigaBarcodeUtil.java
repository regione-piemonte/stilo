package it.eng.hybrid.module.stampaEtichette.util;


import java.util.List;

import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfRigaBean;
import it.eng.hybrid.module.stampaEtichette.bean.StampaEtichettaPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;
import it.eng.hybrid.module.stampaEtichette.bean.ZebraRigaBean;

public class RigaBarcodeUtil {

	public static PdfRigaBean getPdfRigaBean(int i, PdfPropertiesBean pdfProperties) {
		List<PdfRigaBean> lList = pdfProperties.getRighe();
		for (PdfRigaBean lPdfRigaBean : lList){
			if (lPdfRigaBean.getNumeroRiga()==i){
				return lPdfRigaBean;
			}
		}
		return null;
	}
	
	public static PdfRigaBean getPdfSecondLabelRigaBean(int i, PdfPropertiesBean pdfProperties) {
		List<PdfRigaBean> lList = pdfProperties.getSecondLabelRighe();
		for (PdfRigaBean lPdfRigaBean : lList){
			if (lPdfRigaBean.getNumeroRiga()==i){
				return lPdfRigaBean;
			}
		}
		return null;
	}

	public static TestoBarcodeBean getTestoBarcodeBean(int numCopia, ParameterBean parameter) {
		List<TestoBarcodeBean> lList = parameter.getTesto();
		for (TestoBarcodeBean lTestoBarcodeBean : lList){
			if (lTestoBarcodeBean.getCounter()==numCopia+1){
				return lTestoBarcodeBean;
			}
		}
		return null;
	}
	
	public static PdfRigaBean getPdfRepertorioLabelRigaBean(int i, PdfPropertiesBean pdfProperties) {
		List<PdfRigaBean> lList = pdfProperties.getRepertorioLabelRighe();
		for (PdfRigaBean lPdfRigaBean : lList){
			if (lPdfRigaBean.getNumeroRiga()==i){
				return lPdfRigaBean;
			}
		}
		return null;
	}
	
	public static PdfRigaBean getPdfFaldoneLabelRigaBean(int i, PdfPropertiesBean pdfProperties) {
		List<PdfRigaBean> lList = pdfProperties.getFaldoneLabelRighe();
		for (PdfRigaBean lPdfRigaBean : lList){
			if (lPdfRigaBean.getNumeroRiga()==i){
				return lPdfRigaBean;
			}
		}
		return null;
	}
	
	public static ZebraRigaBean getZebraRigaBean(int i, StampaEtichettaPropertiesBean pStampaEtichettaPropertiesBean) {
		List<ZebraRigaBean> lList = pStampaEtichettaPropertiesBean.getRighe();
		for (ZebraRigaBean lZebraRigaBean : lList){
			if (lZebraRigaBean.getNumeroRiga()==i){
				return lZebraRigaBean;
			}
		}
		return null;
	}
}
