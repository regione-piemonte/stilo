/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BarcodeQRCode;

import net.sf.jooreports.templates.image.RenderedImageSource;

public class BarcodeUtility {

	private static final Logger logger = Logger.getLogger(BarcodeUtility.class);

	public static RenderedImageSource getImageProvider(String testo, ImpostazioniBarcodeBean impostazioniBarcodeBean)
			throws BadElementException, UnsupportedEncodingException {
		java.awt.Image barCode;
		if ("QRCODE".equalsIgnoreCase(impostazioniBarcodeBean.getBarcodeEncoding())) {
			barCode = getQRcode(testo, impostazioniBarcodeBean);
		} else if ("PDF417".equalsIgnoreCase(impostazioniBarcodeBean.getBarcodeEncoding())) {
			barCode = getCodePDF417(testo, impostazioniBarcodeBean);
		} else {
			barCode = getBarcode(testo, impostazioniBarcodeBean);
		}
		// construct the buffered image
		BufferedImage bImage = new BufferedImage(barCode.getWidth(null), barCode.getHeight(null), BufferedImage.TYPE_INT_RGB);
		// obtain it's graphics
		Graphics2D bImageGraphics = bImage.createGraphics();
		// draw the Image (image) into the BufferedImage (bImage)
		bImageGraphics.drawImage(barCode, null, null);
		// cast it to rendered image
		RenderedImage rImage = (RenderedImage) bImage;
		return new RenderedImageSource(bImage);
	}

	private static java.awt.Image getBarcode(String testo, ImpostazioniBarcodeBean impostazioniBarcodeBean) throws BadElementException {
		logger.debug("Aggiungo il barcode");
		Barcode barcode = new Barcode128();

		barcode.setCode(testo);
		barcode.setCodeType(Barcode128.CODE128);

		// Controllo barCodeHeight
		logger.debug("BarCodeHeight vale " + impostazioniBarcodeBean.getBarCodeHeight());
		if ((impostazioniBarcodeBean.getBarCodeHeight() != null) && (!"".equalsIgnoreCase(impostazioniBarcodeBean.getBarCodeHeight()))) {
			String barCodeHeightString = impostazioniBarcodeBean.getBarCodeHeight();
			barcode.setBarHeight(Utilities.millimetersToPoints(convertLengthMeasurementToMm(barCodeHeightString)));
		} else {
			barcode.setBarHeight(Utilities.millimetersToPoints(10));
		}

		// Controllo barCodeHumanReadablePlacement
		logger.debug("BarCodeHumanReadablePlacement vale " + impostazioniBarcodeBean.getBarCodeHumanReadablePlacement());
		if (impostazioniBarcodeBean.getBarCodeHumanReadablePlacement() == null
				|| "none".equalsIgnoreCase(impostazioniBarcodeBean.getBarCodeHumanReadablePlacement())) {
			barcode.setFont(null);
		} else {
			barcode.setBaseline(10);
		}

		// Controllo barcodeModuleWidth
		float barcodeModuleWidth = 0f;
		logger.debug("BarcodeModuleWidth vale " + impostazioniBarcodeBean.getBarcodeModuleWidth());
		if (impostazioniBarcodeBean.getBarcodeModuleWidth() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getBarcodeModuleWidth())) {
			barcodeModuleWidth = Utilities.millimetersToPoints(convertLengthMeasurementToMm(impostazioniBarcodeBean.getBarcodeModuleWidth()));
			barcode.setX(barcodeModuleWidth);
		} else {
			barcodeModuleWidth = Utilities.millimetersToPoints(convertLengthMeasurementToMm("0.2"));
			barcode.setX(barcodeModuleWidth);
		}

		// Controllo barCodeWideFactor
		logger.debug("BarCodeWideFactor vale " + impostazioniBarcodeBean.getBarCodeWideFactor());
		if (impostazioniBarcodeBean.getBarCodeWideFactor() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getBarCodeWideFactor())) {
			barcode.setN(Float.parseFloat(removeUnitOfMeasure(impostazioniBarcodeBean.getBarCodeWideFactor(), "mw")));
		}

		// Ho settato il barcode. Lo converto in immagine

		java.awt.Image img = barcode.createAwtImage(java.awt.Color.BLACK, java.awt.Color.WHITE);

		logger.debug("Genero l'immagine per il timbro con codifica BarcodeCode128  " + img);

		return img;
	}

	private static java.awt.Image getQRcode(String testo, ImpostazioniBarcodeBean impostazioniBarcodeBean)
			throws BadElementException, UnsupportedEncodingException {
		logger.debug("Aggiungo il barcode");

		// Controllo QRCodeHeight
		logger.debug("QRCodeHeight vale " + impostazioniBarcodeBean.getQrCodeHeight());
		int height = 30;
		if (impostazioniBarcodeBean.getQrCodeHeight() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getQrCodeHeight())) {
			height = Integer.valueOf(impostazioniBarcodeBean.getQrCodeHeight());
		}

		// Controllo qrCodeWidth
		logger.debug("QRCodeWidth vale " + impostazioniBarcodeBean.getQrCodeWidth());
		int width = 30;
		if (impostazioniBarcodeBean.getQrCodeWidth() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getQrCodeWidth())) {
			width = Integer.valueOf(impostazioniBarcodeBean.getQrCodeWidth());
		}

		BarcodeQRCode barcodeQRCode = new BarcodeQRCode(testo, width, height, null);

		// Ho settato il barcode. Lo converto in immagine
		java.awt.Image img = barcodeQRCode.createAwtImage(java.awt.Color.BLACK, java.awt.Color.WHITE);
		barcodeQRCode.createAwtImage(Color.BLACK, Color.WHITE);

		return img;
	}

	private static java.awt.Image getCodePDF417(String testo, ImpostazioniBarcodeBean impostazioniBarcodeBean) throws BadElementException {
		logger.debug("Aggiungo il barcode");
		BarcodePDF417 barcodePDF417 = new BarcodePDF417();
		barcodePDF417.setText(testo);
		barcodePDF417.setOptions(BarcodePDF417.PDF417_USE_ASPECT_RATIO);
		barcodePDF417.setOptions(BarcodePDF417.PDF417_FIXED_COLUMNS);
		barcodePDF417.setOptions(BarcodePDF417.PDF417_FIXED_COLUMNS);

		barcodePDF417.setCodeColumns(0);
		barcodePDF417.setCodeColumns(0);

		// Controllo pdf147YHeight
		logger.debug("Pdf147YHeight vale " + impostazioniBarcodeBean.getPdf147YHeight());
		barcodePDF417.setYHeight(3);
		if (impostazioniBarcodeBean.getPdf147YHeight() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getPdf147YHeight())) {
			barcodePDF417.setYHeight(Float.valueOf(impostazioniBarcodeBean.getPdf147YHeight()));
		}

		// Controllo pdf147AspectRatio
		logger.debug("Pdf147AspectRatio vale " + impostazioniBarcodeBean.getPdf147AspectRatio());
		barcodePDF417.setAspectRatio(1);
		if (impostazioniBarcodeBean.getPdf147AspectRatio() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getPdf147AspectRatio())) {
			barcodePDF417.setAspectRatio(Float.valueOf(impostazioniBarcodeBean.getPdf147AspectRatio()));
		}

		// Controllo pdf147ScalePercentX
		logger.debug("pdf147ScalePercentX vale " + impostazioniBarcodeBean.getPdf147ScalePercentX());
		int pdf147ScalePercentX = 100;
		if (impostazioniBarcodeBean.getPdf147ScalePercentX() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getPdf147ScalePercentX())) {
			pdf147ScalePercentX = Integer.valueOf(impostazioniBarcodeBean.getPdf147ScalePercentX());
		}

		// Controllo pdf147ScalePercentY
		logger.debug("pdf147ScalePercentY vale " + impostazioniBarcodeBean.getPdf147ScalePercentY());
		int pdf147ScalePercentY = 100;
		if (impostazioniBarcodeBean.getPdf147ScalePercentY() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getPdf147ScalePercentY())) {
			pdf147ScalePercentY = Integer.valueOf(impostazioniBarcodeBean.getPdf147ScalePercentY());
		}

		// Controllo pdf147AspectRatio
		logger.debug("Pdf147AspectRatio vale " + impostazioniBarcodeBean.getPdf147AspectRatio());
		barcodePDF417.setAspectRatio(1);
		if (impostazioniBarcodeBean.getPdf147AspectRatio() != null && !"".equalsIgnoreCase(impostazioniBarcodeBean.getPdf147AspectRatio())) {
			barcodePDF417.setAspectRatio(Float.valueOf(impostazioniBarcodeBean.getPdf147AspectRatio()));
		}

		// Ho settato il barcode. Lo converto in immagine
		java.awt.Image img = barcodePDF417.createAwtImage(java.awt.Color.BLACK, java.awt.Color.WHITE);

		return img;
	}

	/**
	 * Converte una dimensione in millimetri
	 * 
	 * @param strMisura
	 *            La dimensione (deve terminare con l'unità di misura)
	 * @return La dimensione convertita in millimentri
	 */
	private static float convertLengthMeasurementToMm(String strMisura) {
		logger.debug("Converto " + strMisura + " in millimetri");
		int fattConv = 1;
		String strMisuraSenzaUnita = strMisura;
		if (strMisura.toLowerCase().indexOf("mm") != -1) {
			// La misura è in millimetri
			fattConv = 1;
			strMisuraSenzaUnita = strMisura.substring(0, strMisura.toLowerCase().indexOf("mm"));
		} else if (strMisura.toLowerCase().indexOf("cm") != -1) {
			// La misura è in centimentri
			fattConv = 10;
			strMisuraSenzaUnita = strMisura.substring(0, strMisura.toLowerCase().indexOf("cm"));
		}
		Float misuraSenzaUnita = Float.parseFloat(strMisuraSenzaUnita);
		logger.debug("Misura da convertire: " + strMisuraSenzaUnita + ", Fattore di conversione: " + fattConv + ", Misura convertita: "
				+ misuraSenzaUnita * fattConv);
		return misuraSenzaUnita * fattConv;
	}

	/**
	 * Rimuove l'unità di misura dalla dimensione
	 * 
	 * @param inputValue
	 *            La dimensione con l'unità di misura
	 * @param unitOfMeasure
	 *            L'unità di misura da rimuovere
	 * @return La dimensione senza unità di misura
	 */
	private static String removeUnitOfMeasure(String inputValue, String unitOfMeasure) {
		String result = inputValue;
		if (inputValue.toLowerCase().indexOf(unitOfMeasure) != -1) {
			result = inputValue.substring(0, inputValue.toLowerCase().indexOf(unitOfMeasure));
		}
		logger.debug("Valore in input: " + inputValue + ", Unità da rimuovere: " + unitOfMeasure + ", Output: " + result);
		return result;
	}

}
