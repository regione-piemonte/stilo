/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;


public class PdfBarcodeExtractor {

	private static final Logger log = LogManager.getLogger(PdfBarcodeExtractor.class);

	//16
	protected int maximumBlankPixelDelimiterCount;
	
	private QRCodeReader qrCodeReader = null;
	private DataMatrixReader dataMatrixReader = null;
	private Code128Reader code128Reader = null;
	private Code39Reader code39Reader = null;
	private PDF417Reader pdf417Reader = null;
	
	private List<String> patternBarcodeFirma = null;
	private List<String> patternBarcodeDataMatrix = null;
	private List<String> patternBarcodeQRCode = null;
	private List<String> patternBarcodeQRCodeAggiuntivi = null;
	
	private Double rapportoLarghezzaPaginaLarghezzaAreaFirma;
	private Double rapportoLarghezzaBarcodeLarghezzaPagina ;
	private Double rapportoAltezzaPaginaAltezzaAreaFirma;
	
	private Float luminosita;
	private Float contrasto;
	
	public PdfBarcodeExtractor( List<String> patternBarcodeFirma, List<String> patternBarcodeDataMatrix, 
			List<String> patternBarcodeQRCode, List<String> patternBarcodeQRCodeAggiuntivi,
			Double rapportoLarghezzaPaginaLarghezzaAreaFirma,
			Double rapportoLarghezzaBarcodeLarghezzaPagina,
			Double rapportoAltezzaPaginaAltezzaAreaFirma,
			Integer maximumBlankPixelDelimiterCount,
			Float luminosita,
			Float contrasto){
		
		qrCodeReader = new QRCodeReader();
		dataMatrixReader = new DataMatrixReader();
		
		this.patternBarcodeFirma = patternBarcodeFirma;
		this.patternBarcodeDataMatrix = patternBarcodeDataMatrix;
		this.patternBarcodeQRCode = patternBarcodeQRCode;
		this.patternBarcodeQRCodeAggiuntivi = patternBarcodeQRCodeAggiuntivi;
		this.rapportoLarghezzaPaginaLarghezzaAreaFirma = rapportoLarghezzaPaginaLarghezzaAreaFirma;
		this.rapportoLarghezzaBarcodeLarghezzaPagina = rapportoLarghezzaBarcodeLarghezzaPagina;
		this.rapportoAltezzaPaginaAltezzaAreaFirma = rapportoAltezzaPaginaAltezzaAreaFirma;
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
		this.luminosita = luminosita;
		this.contrasto = contrasto;		
	}
	
	public PdfBarcodeExtractor(List<String> barcodeTypes, 
			List<String> patternBarcodeFirma, List<String> patternBarcodeDataMatrix, 
			List<String> patternBarcodeQRCode, List<String> patternBarcodeQRCodeAggiuntivi,
			Double rapportoLarghezzaPaginaLarghezzaAreaFirma,
			Double rapportoLarghezzaBarcodeLarghezzaPagina,
			Double rapportoAltezzaPaginaAltezzaAreaFirma,
			Integer maximumBlankPixelDelimiterCount,
			Float luminosita,
			Float contrasto)
	{
		if ((barcodeTypes != null) && (barcodeTypes.contains((BarcodeType.QRCODE.getType())))) {
			qrCodeReader = new QRCodeReader();
		}
		if ((barcodeTypes != null) && (barcodeTypes.contains((BarcodeType.CODE128.getType())))) {
			code128Reader = new Code128Reader();
		}
		if ((barcodeTypes != null) && (barcodeTypes.contains((BarcodeType.CODE39.getType())))) {
			code39Reader = new Code39Reader();
		}
		if ((barcodeTypes != null) && (barcodeTypes.contains((BarcodeType.PDF417.getType())))) {
			pdf417Reader = new PDF417Reader();
		}
		if ((barcodeTypes != null) && (barcodeTypes.contains((BarcodeType.DATAMATRIX.getType())))) {
			dataMatrixReader = new DataMatrixReader();
		}
		this.patternBarcodeFirma = patternBarcodeFirma;
		this.patternBarcodeDataMatrix = patternBarcodeDataMatrix;
		this.patternBarcodeQRCode = patternBarcodeQRCode;
		this.patternBarcodeQRCodeAggiuntivi = patternBarcodeQRCodeAggiuntivi;
		this.rapportoLarghezzaPaginaLarghezzaAreaFirma = rapportoLarghezzaPaginaLarghezzaAreaFirma;
		this.rapportoLarghezzaBarcodeLarghezzaPagina = rapportoLarghezzaBarcodeLarghezzaPagina;
		this.rapportoAltezzaPaginaAltezzaAreaFirma = rapportoAltezzaPaginaAltezzaAreaFirma;
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
		this.luminosita = luminosita;
		this.contrasto = contrasto;
	}

	public List<BarcodeResult> scanPage( BufferedImage bim, int numPage)
	{
		PdfBarcodePageExtractor pageScanner = new PdfBarcodePageExtractor(bim, maximumBlankPixelDelimiterCount);
		
		pageScanner.setCode128Reader(code128Reader);

		pageScanner.setCode39Reader(code39Reader);

		pageScanner.setPdf417Reader(pdf417Reader);
		pageScanner.setQrCodeReader(qrCodeReader);
		pageScanner.setDataMatrixReader(dataMatrixReader);
		
		pageScanner.setPatternBarcodeFirma(patternBarcodeFirma);
		pageScanner.setPatternBarcodeDataMatrix(patternBarcodeDataMatrix);
		pageScanner.setPatternBarcodeQRCode(patternBarcodeQRCode);
		pageScanner.setPatternBarcodeQRCodeAggiuntivi(patternBarcodeQRCodeAggiuntivi);
		
		pageScanner.setRapportoAltezzaPaginaAltezzaAreaFirma(rapportoAltezzaPaginaAltezzaAreaFirma);
		pageScanner.setRapportoLarghezzaBarcodeLarghezzaPagina(rapportoLarghezzaBarcodeLarghezzaPagina);
		pageScanner.setRapportoLarghezzaPaginaLarghezzaAreaFirma(rapportoLarghezzaPaginaLarghezzaAreaFirma);	
		
		pageScanner.setLuminosita(luminosita);
		pageScanner.setContrasto(contrasto);
		
		List<BarcodeResult> results = null;
		try {
			results = pageScanner.extractBarCodes(numPage);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return results;
	}
}
