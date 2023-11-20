/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDPage;
//import org.imgscalr.Scalr;
//import org.imgscalr.Scalr.Method;
//import org.imgscalr.Scalr.Mode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;

public class PdfBarcodePageExtractor {

	private Logger logger = LogManager.getLogger(PdfBarcodePageExtractor.class);

	private PDPage pdPage;
	private BufferedImage image;
	private int maximumBlankPixelDelimiterCount;
	
	private List<BarcodeResult> resultList;
	
	private Hashtable<DecodeHintType, Object> decodeHintTypes;
	
	private Code128Reader code128Reader;
	private Code39Reader code39Reader;
	private PDF417Reader pdf417Reader;
	private QRCodeReader qrCodeReader;
	private DataMatrixReader dataMatrixReader;
	
	private List<String> patternBarcodeFirma = null;
	private List<String> patternBarcodeDataMatrix = null;
	private List<String> patternBarcodeQRCode = null;
	private List<String> patternBarcodeQRCodeAggiuntivi = null;
	
	private Double rapportoLarghezzaPaginaLarghezzaAreaFirma;
	private Double rapportoLarghezzaBarcodeLarghezzaPagina;
	private Double rapportoAltezzaPaginaAltezzaAreaFirma;
	
	private Float luminosita;
	private Float contrasto;
	
	/*flg per stampare i ritagli di immagini trovati durante l'esecuzione dell algoritmo e valuta*/
	private static boolean flgStampaImageDebug = false;
	
	public PdfBarcodePageExtractor(BufferedImage image, int maximumBlankPixelDelimiterCount) {

		this.image = image;
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
		
		resultList = new ArrayList<BarcodeResult>();
		
		decodeHintTypes = new Hashtable<DecodeHintType, Object>();
		decodeHintTypes.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

	}
	
	/**
	 * Estrae i codice in base ai parametri con cui è stata creata la classe
	 * 
	 * @return
	 * @throws IOException
	 */
	protected List<BarcodeResult> extractBarCodes(Integer currentPageNumber) throws IOException {

		// barCodeArea era null oppure nell'area specificata non è stato trovato
		// nullo, provo un'analisi dell'intera immagine
		List<BarcodeResult> results = extractBarcodeArrayByAreas(image, maximumBlankPixelDelimiterCount, currentPageNumber);
		
		List<BarcodeResult> newResults = new ArrayList<BarcodeResult>();
		for(BarcodeResult r : results){
			if((r!=null && r.isFlagBarcodeFirma()) || !newResults.contains(r)){
				newResults.add(r);
			}
		}
		
		System.out.println("Completata scansione della pagina "+ (currentPageNumber + 1));

		return newResults;
	}
	
	/**
	 * 
	 * @param image
	 * @param maximumBlankPixelDelimiterCount
	 * @return 
	 * @throws IOException
	 */
	protected List<BarcodeResult> extractBarcodeArrayByAreas(BufferedImage image, int maximumBlankPixelDelimiterCount, Integer currentPageNumber)
			throws IOException {
		 
		 BarcodeResult[] results = null;
		 results = extractBarcodeArray(image, currentPageNumber, null);
		 if(results!=null && results.length>0) {
			 addResults(results);
		 }
		
		boolean trovato = false;
		 if( /*(results==null || results.length==0) && */image!=null ){
			List<Rectangle> areaList = getAreaList(image, maximumBlankPixelDelimiterCount);
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

				
				BufferedImage croppedImage = image.getSubimage(rectangle.x, rectangle.y, rectangle.width,rectangle.height);
				
				stampaImageDebug(true, croppedImage, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
				
				results = extractBarcodeArray(croppedImage, currentPageNumber, rectangle);
				if(results!=null && results.length>0) {
					addResults(results);
					trovato = true;
				}

					if(results==null || results.length==0){
						//System.out.println("provo a ruotare 90 gradi a sinistra");
						results = extractBarcodeArray(rotate90ToLeftImage(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
						if(results!=null && results.length>0){
							//System.out.println("2 " + results.length);
							addResults(results);
							trovato = true;
						}
					}

					if(results==null || results.length==0){
						//System.out.println("provo a ruotare 90 gradi a destra");
						results = extractBarcodeArray(rotate90ToRightImage(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
						if(results!=null && results.length>0){
							//System.out.println("3 " + results.length);
							addResults(results);
							trovato = true;
						}
					}
					if(results==null || results.length==0){
						//System.out.println("provo a ruotare 180 gradi ");
						results = extractBarcodeArray(rotate180Image(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
						if(results!=null && results.length>0){
							addResults(results);
							trovato = true;
						}
					}
					if(results==null || results.length==0){
						//System.out.println("provo a ruotare 45 gradi ");
						results = extractBarcodeArray(rotate45(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
						if(results!=null && results.length>0){
							addResults(results);
							trovato = true;
						}
					}
			}
//
			if(!trovato && (results==null || results.length==0)){
				//System.out.println("provo a suddivididere l'immagine in b/n");
				
			BufferedImage blackAndWhiteImage = getThresholdImage(image);
			stampaImageDebug(true, blackAndWhiteImage, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
			
			if(isImageMoreBlack(blackAndWhiteImage)) {
//				System.out.println("L'immagine prodotta è troppo scura");
//				RescaleOp rescaleOp = new RescaleOp(1.6f, 55, null);
				RescaleOp rescaleOp = new RescaleOp(luminosita, contrasto, null);
				rescaleOp.filter(image, image); 
//				System.out.println("Schiarisco l'immagine");
				
				stampaImageDebug(true, image, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
				
				blackAndWhiteImage = getThresholdImage(image);
				stampaImageDebug(true, blackAndWhiteImage, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
			}
			
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

				BufferedImage croppedImage = image.getSubimage(rectangle.x, rectangle.y, rectangle.width,
						rectangle.height);
				
				stampaImageDebug(true, croppedImage, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
				
				results = extractBarcodeArray(croppedImage, currentPageNumber, rectangle);
				if(results!=null && results.length>0) {
					addResults(results);
				}
				
				if(results==null || results.length==0){
					results = extractBarcodeArray(rotate90ToLeftImage(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
					if(results!=null && results.length>0){
						addResults(results);
					}
				}
				
				if(results==null || results.length==0){
					results = extractBarcodeArray(rotate90ToRightImage(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
					if(results!=null && results.length>0){
						addResults(results);
					}
				}
				
				if(results==null || results.length==0){
					results = extractBarcodeArray(rotate180Image(croppedImage, BufferedImage.TYPE_INT_ARGB), currentPageNumber, rectangle);
					if(results!=null && results.length>0){
						addResults(results);
					}
				}
			}
		}
		} else {
			logger.info("Image null");
		}
		
		return resultList;
	}
	
	private void stampaImageDebug(boolean stampa, BufferedImage image, String pathDirectory) {
		if (flgStampaImageDebug && stampa) {
			try {
				File file = null;
				if (StringUtils.isNotBlank(pathDirectory)) {
					file = new File(pathDirectory + "image_" + System.currentTimeMillis() + ".png");
				} else {
					file = File.createTempFile("image", ".png");
				}
				ImageIO.write(image, "png", file);
			} catch (Exception e) {
				logger.error("Errore durante la scrittura dell'immagine per il debug: " + e.getMessage());
			}
		}
	}

	protected BarcodeResult[] extractBarcodeArray(BufferedImage bufferedImage, Integer currentPageNumber, Rectangle rectangle) {
		BarcodeResult[] results = null;


		try {
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			//Reader reader = new MultiFormatReader();
			//Result result = reader.decode(bitmap);

			Map<DecodeHintType,Object> hints = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
			//hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
			//hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);


			//code39Reader = new Code39Reader();
			//MultiFormatReader reader1 = new MultiFormatReader();
			//Result result = reader1.decode(bitmap, hints);
//			Map<DecodeHintType,Object> pureHints = new EnumMap<DecodeHintType,Object>(hints);
//			pureHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
//			pureHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

			List<Result> barcodeResults = new ArrayList<Result>();
			if(pdf417Reader!=null ){
				try {
					Result result = pdf417Reader.decode(bitmap, hints);
					barcodeResults.add(result);
				} catch (Exception e) {
					//logger.error("Nessun codice individuato 417 nella pagina " + currentPageNumber, e);
				}
			}
			if(/*result== null &&*/ code128Reader!=null ){
				try {
					Result result = code128Reader.decode(bitmap, hints);
					barcodeResults.add(result);
				} catch (Exception e) {
					//logger.error("Nessun codice 128 individuato nella pagina " + currentPageNumber/*, e*/);
				}
			}
			if(/*result== null && */code39Reader!=null ){
				try {
					Result result = code39Reader.decode(bitmap, hints);
					barcodeResults.add(result);
				} catch (Exception e) {
					//logger.error("Nessun codice 39 individuato nella pagina " + currentPageNumber/*, e*/);
				}
			}
			if(/*result== null && */qrCodeReader!=null ){
				Result result = null;
				try {
					result = qrCodeReader.decode(bitmap, hints);
					barcodeResults.add(result);
				} catch (Exception e) {
					//logger.error("Nessun codice 39 individuato nella pagina " + currentPageNumber/*, e*/);
				}
			}
			if(/*result== null &&*/ dataMatrixReader!=null ){
				try {
					Result result = dataMatrixReader.decode(bitmap, hints);
					barcodeResults.add(result);
					//DataMatrixReader reader = new DataMatrixReader();
					//result = reader.decode(bitmap, hints);
					//System.out.println("DataMatrixReader "+result );
					//System.out.println("result " + result);
					
				} catch (Exception e) {
					//logger.error("Nessun codice 39 individuato nella pagina " + currentPageNumber/*, e*/);
				}
			}
			
			
			
			if( barcodeResults!=null && barcodeResults.size()>0){
				results = new BarcodeResult[barcodeResults.size()];
				for(int i=0; i< barcodeResults.size(); i++){
					BarcodeResult bean = new BarcodeResult();
				
					Result result = barcodeResults.get(i);
					bean.setBarcode( result.getText());
					bean.setTipoBarcode( result.getBarcodeFormat().name());
					bean.setNumPage( currentPageNumber+1 );
					
					if( (patternBarcodeDataMatrix==null ||  patternBarcodeDataMatrix.size()==0) 
						&& (patternBarcodeQRCode==null ||  patternBarcodeQRCode.size()==0)) {
						bean.setFlagBarcodeValido(true);
					} else {
						boolean isBarcodeValido = false;
						if( patternBarcodeDataMatrix!=null && patternBarcodeDataMatrix.size()>0 && result.getBarcodeFormat().equals(BarcodeFormat.DATA_MATRIX)){
							for(String patternDaVerificare : patternBarcodeDataMatrix ){
								isBarcodeValido = isBarcodeValido || verificaBarcode(result.getText(), patternDaVerificare);
							}
						}
						if( patternBarcodeQRCode!=null && patternBarcodeQRCode.size()>0 && result.getBarcodeFormat().equals(BarcodeFormat.QR_CODE )){
							for(String patternDaVerificare : patternBarcodeQRCode ){
								isBarcodeValido = isBarcodeValido || verificaBarcode(result.getText(), patternDaVerificare);
							}
						}
						bean.setFlagBarcodeValido(isBarcodeValido);
					}
					
					boolean isQrCodeAggiuntivo = false;
					if( bean.getTipoBarcode()!=null && bean.getTipoBarcode().equals(BarcodeFormat.QR_CODE.name())){
						if(patternBarcodeQRCodeAggiuntivi!=null && patternBarcodeQRCodeAggiuntivi.size()>0){
							for(String patternDaVerificare : patternBarcodeQRCodeAggiuntivi ){
								isQrCodeAggiuntivo = isQrCodeAggiuntivo || verificaBarcode(result.getText(), patternDaVerificare);
							}
						}
					}
					bean.setFlagQrcodeAggiuntivo(isQrCodeAggiuntivo);
					
					boolean isBarcodeFirma = false;
					for(String patternDaVerificare : patternBarcodeFirma ){
						isBarcodeFirma = isBarcodeFirma || verificaBarcode(result.getText(), patternDaVerificare);
					}
					bean.setFlagBarcodeFirma(isBarcodeFirma);
					if( isBarcodeFirma) {
						BufferedImage areaFirma = getAreaFirma(rectangle.x, rectangle.y, rectangle.getWidth(), rectangle.getHeight() );
						//bean.setImmagineBarcode( areaFirma );
						
						BufferedImage blackAndWhiteImage = getThresholdImage(areaFirma);
						
						stampaImageDebug(true, blackAndWhiteImage, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
						
						List<Rectangle> areaList = getAllAreaByColor(blackAndWhiteImage, null, 0, 0, 0, 5, false);
						if( areaList!=null && areaList.size()>0){
							bean.setFlagFirmaPresente(true);
						}
					}
					results[i] = bean;
				}
			}else {
				//logger.debug("Nessun risultato trovato");
			}
			return results;
		} catch (Exception e) {
			logger.error("Nessun codice individuato nella pagina " + currentPageNumber, e);
			
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
	
	protected BufferedImage rotate45(BufferedImage inputImage, int imageType){
		AffineTransform transform = new AffineTransform();
	    transform.rotate(45, inputImage.getWidth()/2, inputImage.getHeight()/2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    return op.filter(inputImage, null);
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
	
	protected BufferedImage getThresholdImage(BufferedImage image) throws IOException {
		float saturationMin = 0.10f;
		float brightnessMin = 0.80f;
		BufferedImage thresholdImage = copyImage(image, BufferedImage.TYPE_INT_ARGB);
		
		computeBlackAndWhite(image, thresholdImage, saturationMin, brightnessMin);
				
		return thresholdImage;
	}
	
	protected BufferedImage getThresholdImage2(BufferedImage image) throws IOException {
        
	       int h=image.getHeight();
	       int w=image.getWidth();
	       
	       BufferedImage bufferedImage = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
	       if (image == null) {
	             System.out.println("No image loaded");
	        } 
	       else {
	           for(int i=0;i<w;i++)
	           {
	               for(int j=0;j<h;j++)
	               {
	           
	               
	                    //Get RGB Value
	                    int val = image.getRGB(i, j);
	                    //Convert to three separate channels
	                    int r = (0x00ff0000 & val) >> 16;
	                    int g = (0x0000ff00 & val) >> 8;
	                    int b = (0x000000ff & val);
	                    int m=(r+g+b);
	                    //(255+255+255)/2 =283 middle of dark and light
	                    if(m>=383)
	                    {
	                        // for light color it set white
	                        bufferedImage.setRGB(i, j, Color.WHITE.getRGB()); 
	                    }
	                    else{  
	                        // for dark color it will set black
	                        bufferedImage.setRGB(i, j, 0);
	                    }
	                }  
	            }
	        }
	       
	       
	       return bufferedImage;
	    }
	
	

	
	protected final BufferedImage copyImage(BufferedImage bi, int type) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), type);
		Graphics2D g = result.createGraphics();
		g.drawRenderedImage(bi, null);
		g.dispose();
		return result;
	}

	protected void computeBlackAndWhite(BufferedImage in, BufferedImage out, float saturationMin, float brightnessMin)
			throws IOException {
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
	
	public List<Rectangle> getAreaList(BufferedImage image, int maximumBlankPixelDelimiterCount) throws IOException {
		List<Rectangle> areaList = getAllAreaByColor(image, null, 0, 0, 0, maximumBlankPixelDelimiterCount, false);

		return areaList;
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
	 *            define the same area until number of blank pixel is not
	 *            greater than maximumBlankPixelDelimiterCount
	 * @return
	 * @throws IOException
	 */
	protected List<Rectangle> getAllAreaByColor(BufferedImage in, BufferedImage out, int redColor, int greenColor,
			int blueColor, int maximumBlankPixelDelimiterCount, boolean debug) throws IOException {

		int w = in.getWidth();
		int h = in.getHeight();
		//logger.debug("w " + w + " h " + h );
		int pixel;
		List<Rectangle> areaList = new ArrayList<Rectangle>();

		Graphics2D gc = null;

		if (out != null) {
			gc = out.createGraphics();
			gc.setColor(new Color(1f, 0f, 0f));
		}

		int maximumBlankPixelDelimiterCountDouble = maximumBlankPixelDelimiterCount * 2;
		//System.out.println(":::::::::::::: " + redColor + " " + greenColor + " " + blueColor );
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				pixel = in.getRGB(x, y);
				int alpha = ((pixel >> 24) & 0xFF);
				int red = ((pixel >> 16) & 0xFF);
				int green = ((pixel >> 8) & 0xFF);
				int blue = (pixel & 0xFF);

				if( debug)
					System.out.println(":::::::::::::: " + red + " " + green + " " + blue );
				
				if (red == redColor && green == greenColor && blue == blueColor) {
					Rectangle rect = new Rectangle(x - maximumBlankPixelDelimiterCount,
							y - maximumBlankPixelDelimiterCount, maximumBlankPixelDelimiterCountDouble,
							maximumBlankPixelDelimiterCountDouble);
					
					
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
						//System.out.println("int::"+intersection);
						if (intersection.width > 0 && intersection.height > 0) {
							//System.out.println("2222" + rect);
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
									if (debug) {
										System.out.println(rectangle + " intersect " + rec2);
									}
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
	
	
	protected BufferedImage getAreaFirma(int x, int y, double width, double height) throws IOException {

		BufferedImage croppedImage = null;
		try{
			
			rapportoLarghezzaPaginaLarghezzaAreaFirma = rapportoLarghezzaPaginaLarghezzaAreaFirma!= null && rapportoLarghezzaPaginaLarghezzaAreaFirma !=0 ?
					rapportoLarghezzaPaginaLarghezzaAreaFirma : 29;
			rapportoLarghezzaBarcodeLarghezzaPagina = rapportoLarghezzaBarcodeLarghezzaPagina!= null && rapportoLarghezzaBarcodeLarghezzaPagina !=0 ?
					rapportoLarghezzaBarcodeLarghezzaPagina : 7.5;
			rapportoAltezzaPaginaAltezzaAreaFirma = rapportoAltezzaPaginaAltezzaAreaFirma!= null && rapportoAltezzaPaginaAltezzaAreaFirma !=0 ?
					rapportoAltezzaPaginaAltezzaAreaFirma : 3.1;
			
			/**/
			/*N.B
			 *  Non è esattamente l'area di firma ma un po piu centrale in quanto le coordinate del rettangolo individuate comprendono un margine dinamico
			 *  l'algoritmo non mi passa esattamente le coordinate del barcode (coordinate Rectangle)
			 *  e quindi per sicurezza restringo l'individuazione alla parte piu centrale
			 * */
			float widhtImagePercent = (float) image.getWidth()/100;
			
			int wAreaFirma = (int) (widhtImagePercent * rapportoLarghezzaPaginaLarghezzaAreaFirma); 
			int xAreaFirma = (int) (x + (widhtImagePercent  * rapportoLarghezzaBarcodeLarghezzaPagina)); 			
			int hAreaFirma = (int) (widhtImagePercent * rapportoAltezzaPaginaAltezzaAreaFirma); 
			int yAreaFirma = (int) ((y + (height/2)) - (hAreaFirma/2)); 
			
			croppedImage = image.getSubimage(xAreaFirma, yAreaFirma, wAreaFirma, hAreaFirma);
			
			stampaImageDebug(false, croppedImage, "C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\Nuova cartella\\");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return croppedImage;
	}
	
	protected void addResults(BarcodeResult[] results) {

		if (results == null) {
			return;
		}
		
		for (BarcodeResult result : results) {
			resultList.add(result);			
		}
	}
	
	private boolean isImageMoreBlack(BufferedImage image) throws IOException {
	    int color = image.getRGB(0, 0);
	    
	    /*In alcune immagini nere che venivano prodotte metteva alcuni pixel bianchi random, impercettibili anche all occhio umano, quindi 
	     * se i pixel bianchi sono piu dell 2% per me non è un immagine tutta nera (si potrebbe/dovrebbe configurare)*/
	    int percentualePixelDiversi = 10;
	    
	    int numPixelTotali = image.getHeight()*image.getWidth();
	    
	    int limitePixelDiversiAmmessi = (numPixelTotali*percentualePixelDiversi)/100;

	    int numWhitePixel = 0;
	    
	    for (int r = 0; r < image.getHeight(); r += 1) {
	        for (int c = 0; c < image.getWidth(); c += 1) {
	            if (image.getRGB(c, r) != color) {
	            	numWhitePixel++;
	            	if(numWhitePixel>=limitePixelDiversiAmmessi) {
	            		return false;
	            	}
	            }
	        }
	    }
	    
	    Color colorObject = new Color(color);
	    
	    if(colorObject.getRed()==0 && colorObject.getBlue()==0 && colorObject.getGreen()==0) {
	    	return true;
	    }else {
	    	return false;
	    }    		    		    	
	}
	
	private boolean verificaBarcode(String barcodeStr, String pattern)
	{
		if ((barcodeStr != null) && (barcodeStr.length() > 0))
		{
			boolean esitoPattern = riconosciCodice(barcodeStr, pattern);
			logger.debug("Esito verifica pattern per il codice " + barcodeStr + ": " + esitoPattern);
			return esitoPattern;
		}
		return false;
	}
	
	private boolean riconosciCodice(String codice, String pattern) {
		boolean isValid = false;
		Pattern p = null;
		Matcher m = null;
		if (codice == null) {
			return false;
		}
		boolean patternValid = false;
		p = Pattern.compile(pattern, 2);
		if (p != null) {
			m = p.matcher(codice);
		}
		if (m != null) {
			patternValid = m.find();
		}
		isValid = (isValid) || (patternValid);
		
		return isValid;
	}

	public Hashtable<DecodeHintType, Object> getDecodeHintTypes() {
		return decodeHintTypes;
	}

	public void setDecodeHintTypes(Hashtable<DecodeHintType, Object> decodeHintTypes) {
		this.decodeHintTypes = decodeHintTypes;
	}

	public Code128Reader getCode128Reader() {
		return code128Reader;
	}

	public void setCode128Reader(Code128Reader code128Reader) {
		this.code128Reader = code128Reader;
	}

	public Code39Reader getCode39Reader() {
		return code39Reader;
	}

	public void setCode39Reader(Code39Reader code39Reader) {
		this.code39Reader = code39Reader;
	}

	public PDF417Reader getPdf417Reader() {
		return pdf417Reader;
	}

	public void setPdf417Reader(PDF417Reader pdf417Reader) {
		this.pdf417Reader = pdf417Reader;
	}

	public QRCodeReader getQrCodeReader() {
		return qrCodeReader;
	}

	public void setQrCodeReader(QRCodeReader qrCodeReader) {
		this.qrCodeReader = qrCodeReader;
	}

	public DataMatrixReader getDataMatrixReader() {
		return dataMatrixReader;
	}

	public void setDataMatrixReader(DataMatrixReader dataMatrixReader) {
		this.dataMatrixReader = dataMatrixReader;
	}
	public List<String> getPatternBarcodeFirma() {
		return patternBarcodeFirma;
	}
	
	public List<String> getPatternBarcodeDataMatrix() {
		return patternBarcodeDataMatrix;
	}
	public void setPatternBarcodeDataMatrix(List<String> patternBarcodeDataMatrix) {
		this.patternBarcodeDataMatrix = patternBarcodeDataMatrix;
	}

	public List<String> getPatternBarcodeQRCode() {
		return patternBarcodeQRCode;
	}
	public void setPatternBarcodeQRCode(List<String> patternBarcodeQRCode) {
		this.patternBarcodeQRCode = patternBarcodeQRCode;
	}
	
	public List<String> getPatternBarcodeQRCodeAggiuntivi() {
		return patternBarcodeQRCodeAggiuntivi;
	}

	public void setPatternBarcodeQRCodeAggiuntivi(List<String> patternBarcodeQRCodeAggiuntivi) {
		this.patternBarcodeQRCodeAggiuntivi = patternBarcodeQRCodeAggiuntivi;
	}

	public void setPatternBarcodeFirma(List<String> patternBarcodeFirma) {
		this.patternBarcodeFirma = patternBarcodeFirma;
	}

	public double getRapportoLarghezzaPaginaLarghezzaAreaFirma() {
		return rapportoLarghezzaPaginaLarghezzaAreaFirma;
	}

	public double getRapportoLarghezzaBarcodeLarghezzaPagina() {
		return rapportoLarghezzaBarcodeLarghezzaPagina;
	}

	public double getRapportoAltezzaPaginaAltezzaAreaFirma() {
		return rapportoAltezzaPaginaAltezzaAreaFirma;
	}

	public void setRapportoLarghezzaPaginaLarghezzaAreaFirma(double rapportoLarghezzaPaginaLarghezzaAreaFirma) {
		this.rapportoLarghezzaPaginaLarghezzaAreaFirma = rapportoLarghezzaPaginaLarghezzaAreaFirma;
	}

	public void setRapportoLarghezzaBarcodeLarghezzaPagina(double rapportoLarghezzaBarcodeLarghezzaPagina) {
		this.rapportoLarghezzaBarcodeLarghezzaPagina = rapportoLarghezzaBarcodeLarghezzaPagina;
	}

	public void setRapportoAltezzaPaginaAltezzaAreaFirma(double rapportoAltezzaPaginaAltezzaAreaFirma) {
		this.rapportoAltezzaPaginaAltezzaAreaFirma = rapportoAltezzaPaginaAltezzaAreaFirma;
	}

	public Float getLuminosita() {
		return luminosita;
	}

	public Float getContrasto() {
		return contrasto;
	}

	public void setLuminosita(Float luminosita) {
		this.luminosita = luminosita;
	}

	public void setContrasto(Float contrasto) {
		this.contrasto = contrasto;
	}

	public static void main(String[] args) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("C:/Users/TESAURO/Downloads/scansioni/0/test_bw1631873928569.png"));
		    
		    LuminanceSource source = new BufferedImageLuminanceSource(img);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			//Reader reader = new MultiFormatReader();
			//Result result = reader.decode(bitmap);

			Map<DecodeHintType,Object> hints = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
			
			DataMatrixReader reader = new DataMatrixReader();
			Result result = reader.decode(bitmap, hints);
			System.out.println(result);
		} catch (IOException e) {
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChecksumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
