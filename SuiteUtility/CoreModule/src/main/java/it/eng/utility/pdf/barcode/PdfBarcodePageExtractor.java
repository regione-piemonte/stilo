package it.eng.utility.pdf.barcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDPage;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.multi.GenericMultipleBarcodeReader;

/**
 * 
 * Permette di estrarre i codici a barre presenti in un'immagine
 *
 */
public class PdfBarcodePageExtractor {

	protected Logger logger = Logger.getLogger(PdfBarcodePageExtractor.class);

	protected PDPage pdPage;
	protected BufferedImage image;
	protected int maximumBlankPixelDelimiterCount;
	protected List<Result> resultList;
	protected DataMatrixReader dataMatrixReader;
	protected Hashtable<DecodeHintType, Object> decodeHintTypes;
	protected GenericMultipleBarcodeReader reader;

	private Rectangle barCodeArea;

	/**
	 * 
	 * @param image
	 *            immagine da analizzare per l'estrazione del qrcode
	 * @param pageNumber
	 * @param maximumBlankPixelDelimiterCount
	 */
	public PdfBarcodePageExtractor(BufferedImage image, int maximumBlankPixelDelimiterCount) {
		this(image, maximumBlankPixelDelimiterCount, null);
	}

	public PdfBarcodePageExtractor(BufferedImage image, int maximumBlankPixelDelimiterCount, Rectangle barCodeArea) {

		this.image = image;
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
		resultList = new ArrayList<Result>();

		/*
		 * build default bar code reader
		 */
		dataMatrixReader = new DataMatrixReader();
		reader = new GenericMultipleBarcodeReader(dataMatrixReader);

		decodeHintTypes = new Hashtable<DecodeHintType, Object>();
		decodeHintTypes.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

		this.barCodeArea = barCodeArea;
	}

	/**
	 * Estrae i codice in base ai parametri con cui è stata creata la classe
	 * 
	 * @return
	 * @throws IOException
	 */
	protected List<Result> extractBarCodes() throws IOException {

		// costa un botto, decommentare solo in caso di debug
		// ImageIO.write(image, "png", outputfile);

		if (barCodeArea != null) {
			extractBarcodeArrayByAreas(image, barCodeArea);
		}

		// barCodeArea era null oppure nell'area specificata non è stato trovato
		// nullo, provo un'analisi dell'intera immagine
		if (resultList == null || resultList.isEmpty()) {
			extractBarcodeArrayByAreas(image, maximumBlankPixelDelimiterCount);
		}

		logger.info("Completata scansione della pagina ");

		return resultList;
	}

	/**
	 * get area list by color
	 * 
	 * 
	 * @param in
	 * @param out
	 *            if not null draw rectangle for debug purpose
	 * @param redColor
	 * @param greenColor
	 * @param blueColor
	 * @param maximumBlankPixelDelimiterCount
	 *            define the same area until number of blank pixel is not greater than maximumBlankPixelDelimiterCount
	 * @return
	 * @throws IOException
	 */
	protected List<Rectangle> getAllAreaByColor(BufferedImage in, BufferedImage out, int redColor, int greenColor, int blueColor,
			int maximumBlankPixelDelimiterCount, boolean debug) throws IOException {

		int w = in.getWidth();
		int h = in.getHeight();
		int pixel;
		List<Rectangle> areaList = new ArrayList<Rectangle>();

		Graphics2D gc = null;

		if (out != null) {
			gc = out.createGraphics();
			gc.setColor(new Color(1f, 0f, 0f));
		}

		int maximumBlankPixelDelimiterCountDouble = maximumBlankPixelDelimiterCount * 2;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				pixel = in.getRGB(x, y);
				int alpha = ((pixel >> 24) & 0xFF);
				int red = ((pixel >> 16) & 0xFF);
				int green = ((pixel >> 8) & 0xFF);
				int blue = (pixel & 0xFF);

				if (red == redColor && green == greenColor && blue == blueColor) {
					Rectangle rect = new Rectangle(x - maximumBlankPixelDelimiterCount, y - maximumBlankPixelDelimiterCount,
							maximumBlankPixelDelimiterCountDouble, maximumBlankPixelDelimiterCountDouble);

					boolean isInArea = false;
					for (Rectangle rectangle : areaList) {
						if (rectangle.contains(x, y)) {
							rectangle.add(rect);
							isInArea = true;
							break;
						}
					}

					if (isInArea) {
						continue;
					}

					/*
					 * get pixel colors
					 */
					pixel = pixel & 0x00000000;
					pixel = pixel | (alpha << 24);
					pixel = pixel | (0 << 16);
					pixel = pixel | (255 << 8);
					pixel = pixel | (0);

					isInArea = false;
					for (Rectangle rectangle : areaList) {
						Rectangle intersection = rectangle.intersection(rect);
						if (intersection.width > 0 && intersection.height > 0) {
							isInArea = true;
							rectangle.add(rect);

							break;
						}
					}

					if (!isInArea) {
						areaList.add(rect);
					}

					while (isInArea) {
						Rectangle rectToRemove = null;
						isInArea = false;
						for (Rectangle rectangle : areaList) {
							for (Rectangle rec2 : areaList) {
								if (rec2 == rectangle)
									continue;

								Rectangle intersection = rectangle.intersection(rec2);
								if (intersection.width > 0 && intersection.height > 0) {
									isInArea = true;
									rectangle.add(rec2);
									rectToRemove = rec2;
									break;
								}

								if (isInArea) {
									break;
								}
							}
						}

						if (rectToRemove != null) {
							areaList.remove(rectToRemove);
						}
					}

					if (out != null) {
						out.setRGB(x, y, pixel);
						gc.draw(rect);
					}
				}
			}
		}
		return areaList;
	}

	public List<Rectangle> getAreaList(BufferedImage image, int maximumBlankPixelDelimiterCount) throws IOException {
		List<Rectangle> areaList = getAllAreaByColor(image, null, 0, 0, 0, maximumBlankPixelDelimiterCount, false);

		return areaList;
	}

	protected void extractBarcodeArrayByAreas(BufferedImage image, Rectangle rectangle) {

		found: {

			if (rectangle.x < 0) {
				rectangle.x = 0;
			}

			if (rectangle.y < 0) {
				rectangle.y = 0;
			}

			if (rectangle.y + rectangle.height > image.getHeight()) {
				rectangle.height = image.getHeight() - rectangle.y;
			}

			if (rectangle.x + rectangle.width > image.getWidth()) {
				rectangle.width = image.getWidth() - rectangle.x;
			}

			BufferedImage croppedImage = image.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

			/*
			 * zxing library can not deals with DataMatrix in all orientations, so we have to rotate the image and ask zxing four times to find DataMatrix
			 */
			addResults(extractBarcodeArray(croppedImage));

			if (resultList.isEmpty()) {
				addResults(extractBarcodeArray(rotate90ToLeftImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
			} else {
				break found;
			}

			if (resultList.isEmpty()) {
				addResults(extractBarcodeArray(rotate90ToRightImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
			} else {
				break found;
			}

			if (resultList.isEmpty()) {
				addResults(extractBarcodeArray(rotate180Image(croppedImage, BufferedImage.TYPE_INT_ARGB)));
			} else {
				break found;
			}

			if (!resultList.isEmpty()) {
				break found;
			}

		}
	}

	/**
	 * 
	 * @param image
	 * @param maximumBlankPixelDelimiterCount
	 * @throws IOException
	 */
	protected void extractBarcodeArrayByAreas(BufferedImage image, int maximumBlankPixelDelimiterCount) throws IOException {

		// costa un botto, deccomentare solo in caso di necessità di debug
		// File outputfile = new File(destinationDir + "test_bw" +
		// System.currentTimeMillis() + ".png");
		// ImageIO.write(blackAndWhiteImage, "png", outputfile);

		List<Rectangle> areaList = getAreaList(image, maximumBlankPixelDelimiterCount);

		found: {

			for (Rectangle rectangle : areaList) {

				/*
				 * verify bounds before crop image
				 */
				if (rectangle.x < 0) {
					rectangle.x = 0;
				}

				if (rectangle.y < 0) {
					rectangle.y = 0;
				}

				if (rectangle.y + rectangle.height > image.getHeight()) {
					rectangle.height = image.getHeight() - rectangle.y;
				}

				if (rectangle.x + rectangle.width > image.getWidth()) {
					rectangle.width = image.getWidth() - rectangle.x;
				}

				BufferedImage croppedImage = image.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

				/*
				 * zxing library can not deals with DataMatrix in all orientations, so we have to rotate the image and ask zxing four times to find DataMatrix
				 */
				addResults(extractBarcodeArray(croppedImage));

				if (resultList.isEmpty()) {
					addResults(extractBarcodeArray(rotate90ToLeftImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
				} else {
					break found;
				}

				if (resultList.isEmpty()) {
					addResults(extractBarcodeArray(rotate90ToRightImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
				} else {
					break found;
				}

				if (resultList.isEmpty()) {
					addResults(extractBarcodeArray(rotate180Image(croppedImage, BufferedImage.TYPE_INT_ARGB)));
				} else {
					break found;
				}

				if (!resultList.isEmpty()) {
					break found;
				}
			}

			// provo ad estrarre utilizzando una versione in bn dell'immagine
			BufferedImage blackAndWhiteImage = getThresholdImage(image);
			List<Rectangle> areaListBW = getAreaList(blackAndWhiteImage, maximumBlankPixelDelimiterCount);
			for (Rectangle rectangle : areaListBW) {

				/*
				 * verify bounds before crop image
				 */
				if (rectangle.x < 0) {
					rectangle.x = 0;
				}

				if (rectangle.y < 0) {
					rectangle.y = 0;
				}

				if (rectangle.y + rectangle.height > image.getHeight()) {
					rectangle.height = image.getHeight() - rectangle.y;
				}

				if (rectangle.x + rectangle.width > image.getWidth()) {
					rectangle.width = image.getWidth() - rectangle.x;
				}

				/*
				 * crop image to extract barcodes
				 */

				BufferedImage croppedImage = image.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

				/*
				 * zxing library can not deals with DataMatrix in all orientations, so we have to rotate the image and ask zxing four times to find DataMatrix
				 */
				addResults(extractBarcodeArray(croppedImage));

				if (resultList.isEmpty()) {
					addResults(extractBarcodeArray(rotate90ToLeftImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
				} else {
					break found;
				}

				if (resultList.isEmpty()) {
					addResults(extractBarcodeArray(rotate90ToRightImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
				} else {
					break found;
				}

				if (resultList.isEmpty()) {
					addResults(extractBarcodeArray(rotate180Image(croppedImage, BufferedImage.TYPE_INT_ARGB)));
				}
			}
		}
	}

	protected void addResults(Result[] results) {

		if (results == null) {
			return;
		}

		for (Result result : results) {
			resultList.add(result);
		}
	}

	protected Result[] extractBarcodeArray(BufferedImage bufferedImage, GenericMultipleBarcodeReader reader) {
		Result[] results = null;

		try {
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			results = reader.decodeMultiple(bitmap, decodeHintTypes);
		} catch (NotFoundException e) {

			logger.error(e);
		}

		return results;
	}

	protected Result[] extractBarcodeArray(BufferedImage bufferedImage) {
		Result[] results = null;

		try {
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Reader reader = new MultiFormatReader();
			Result result = reader.decode(bitmap);
			results = new Result[1];
			results[0] = result;
			return results;
		} catch (Exception e) {
			logger.error(e);
		}

		return results;
	}

	/**
	 * @see http://forum.codecall.net/topic/69182-java-image-rotation/
	 */
	protected BufferedImage rotate180Image(BufferedImage inputImage, int imageType) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(width, height, imageType);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(width - x - 1, height - y - 1, inputImage.getRGB(x, y));
			}
		}

		return returnImage;
	}

	/**
	 * @see http://forum.codecall.net/topic/69182-java-image-rotation/
	 */
	protected BufferedImage rotate90ToRightImage(BufferedImage inputImage, int imageType) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(height, width, imageType);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(height - y - 1, x, inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}

	/**
	 * @see http://forum.codecall.net/topic/69182-java-image-rotation/
	 */
	protected BufferedImage rotate90ToLeftImage(BufferedImage inputImage, int imageType) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(height, width, imageType);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(y, width - x - 1, inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}

	protected BufferedImage getBlackAndWhiteImage(BufferedImage image) throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage imageBW = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g = imageBW.createGraphics();
		g.drawRenderedImage(image, null);
		g.dispose();

		return imageBW;
	}

	protected BufferedImage getThresholdImage(BufferedImage image) throws IOException {
		float saturationMin = 0.10f;
		float brightnessMin = 0.80f;
		BufferedImage thresholdImage = copyImage(image, BufferedImage.TYPE_INT_ARGB);

		computeBlackAndWhite(image, thresholdImage, saturationMin, brightnessMin);

		return thresholdImage;
	}

	protected void computeBlackAndWhite(BufferedImage in, BufferedImage out, float saturationMin, float brightnessMin) throws IOException {
		int w = in.getWidth();
		int h = in.getHeight();
		int pixel;
		float[] hsb = new float[3];

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				pixel = in.getRGB(x, y);

				int alpha = ((pixel >> 24) & 0xFF);
				int red = ((pixel >> 16) & 0xFF);
				int green = ((pixel >> 8) & 0xFF);
				int blue = (pixel & 0xFF);

				Color.RGBtoHSB(red, green, blue, hsb);

				if (hsb[2] < brightnessMin || (hsb[2] >= brightnessMin && hsb[1] >= saturationMin)
				// (red + green + blue > iThreshold *3 )
				) {
					red = 0;
					green = 0;
					blue = 0;
				} else {
					red = 255;
					green = 255;
					blue = 255;
				}
				pixel = pixel & 0x00000000;
				pixel = pixel | (alpha << 24);
				pixel = pixel | (red << 16);
				pixel = pixel | (green << 8);
				pixel = pixel | (blue);

				out.setRGB(x, y, pixel);
			}
		}
	}

	protected final BufferedImage copyImage(BufferedImage bi, int type) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), type);
		Graphics2D g = result.createGraphics();
		g.drawRenderedImage(bi, null);
		g.dispose();
		return result;
	}

	public PDPage getPdPage() {
		return pdPage;
	}

	public int getMaximumBlankPixelDelimiterCount() {
		return maximumBlankPixelDelimiterCount;
	}

	public void setMaximumBlankPixelDelimiterCount(int maximumBlankPixelDelimiterCount) {
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
	}
}
