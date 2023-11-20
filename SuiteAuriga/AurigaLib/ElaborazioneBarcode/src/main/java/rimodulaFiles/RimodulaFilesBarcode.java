/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import elaborazione.barcode.BarcodeResult;
import elaborazione.barcode.BarcodeType;
import elaborazione.barcode.PdfBarcodeExtractor;
import it.eng.aurigamailbusiness.bean.CoppiaAttachBeanFile;
import it.eng.aurigamailbusiness.bean.InfoFileBarcodeBean;
import it.eng.aurigamailbusiness.bean.InfoFileRimodulatoBean;
import utility.UtilityFileBarcode;

public class RimodulaFilesBarcode {
	
	private static final Logger log = LogManager.getLogger(RimodulaFilesBarcode.class);
	
	private static List<String> listaTempFileToDelete = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		File destFile = new File("C:/Users/DBE4131/Downloads/test/");
		//File destFile = new File("C:\\Users\\antpeluso\\Desktop\\EsempiBarcodeA2A\\testJavaA2A\\");

		String barcodeType1 = BarcodeType.QRCODE.getType();
		String barcodeType2 = BarcodeType.DATAMATRIX.getType();

		Integer maximumBlankPixelDelimiterCount = 16;
		Float luminosita = (float) 1.4;
		Float contrasto = (float) 55;
		Float limiteSuperficiePagina = (float) 50000000;
		Integer limiteNumPagine = 200;

		String patternBarcodeFirma1 = "A2A.firma";
		List<String> patternBarcodeFirma = new ArrayList<String>();
		patternBarcodeFirma.add(patternBarcodeFirma1);

		String patternBarcodeDataMatrix1 = "[0-9]{2}-(E|ELE|G|GAS)-.+";
		List<String> patternBarcodeDataMatrix = new ArrayList<String>();
		if (patternBarcodeDataMatrix1 != null)
			patternBarcodeDataMatrix.add(patternBarcodeDataMatrix1);

		String patternBarcodeQRCode1 = "^02.IVA";
		List<String> patternBarcodeQRCode = new ArrayList<String>();
		if (patternBarcodeQRCode1 != null)
			patternBarcodeQRCode.add(patternBarcodeQRCode1);
		
		String patternBarcodeQRCode2 = "^01.ACCISE";
		if (patternBarcodeQRCode1 != null)
			patternBarcodeQRCode.add(patternBarcodeQRCode2);

		String patternBarcodeQRCode3 = "^A-[0-9]{10}";
		List<String> patternBarcodeQRCodeAggiuntivi = new ArrayList<String>();
		if (patternBarcodeQRCode3 != null)
			patternBarcodeQRCodeAggiuntivi.add(patternBarcodeQRCode2);

		Double rapportoLarghezzaPaginaLarghezzaAreaFirma = (double) 29;
		Double rapportoLarghezzaBarcodeLarghezzaPagina = 7.5;
		Double rapportoAltezzaPaginaAltezzaAreaFirma = 3.1;

		// boolean elaboraSoloPrimaPagina = false;
		List<String> barcodeTypes = new ArrayList<String>();
		barcodeTypes.add(barcodeType1);
		barcodeTypes.add(barcodeType2);
 
		System.out.println("dest " + destFile);
		List<CoppiaAttachBeanFile> listaFile = new ArrayList<CoppiaAttachBeanFile>();
		for (File file : destFile.listFiles()) {
			System.out.println("FILE " + file);
			CoppiaAttachBeanFile bean = new CoppiaAttachBeanFile();
			bean.setFileAllegatoPdf(file);

			listaFile.add(bean);
			
			PdfReader reader = new PdfReader(file.getPath());
			reader.unethicalreading = true;
			
			int nPages=reader.getNumberOfPages();
			
			for(int pageNumber=0;pageNumber<nPages; pageNumber++){
				System.out.println("Analisi pagina " + pageNumber);
				//System.out.println("risultato analisi: " + dimensioniPaginaAccettate(file, pageNumber, limiteSuperficiePagina));
			}
			
			System.out.println("\n\n");
		}

		/*List<InfoFileRimodulatoBean> listaFilesRimodulati = rimodulaBarcodeFiles(listaFile,
				rapportoLarghezzaPaginaLarghezzaAreaFirma, rapportoLarghezzaBarcodeLarghezzaPagina,
				rapportoAltezzaPaginaAltezzaAreaFirma, patternBarcodeFirma, patternBarcodeDataMatrix,
				patternBarcodeQRCode, patternBarcodeQRCodeAggiuntivi, maximumBlankPixelDelimiterCount, luminosita, 
				contrasto, limiteSuperficiePagina, limiteNumPagine);
*/
		/*for (InfoFileRimodulatoBean file : listaFilesRimodulati) {
			System.out.println(file.getFileRimodulato().getPath());
		}*/

	}
	
	public static List<InfoFileRimodulatoBean> rimodulaBarcodeFiles(List<CoppiaAttachBeanFile> listaCoppiaAttachBeanFileOriginali,
			Double rapportoLarghezzaPaginaLarghezzaAreaFirma,
			Double rapportoLarghezzaBarcodeLarghezzaPagina,
			Double rapportoAltezzaPaginaAltezzaAreaFirma, List<String> patternBarcodeFirma,
			List<String> patternBarcodeDataMatrix, List<String> patternBarcodeQRCode, List<String> patternBarcodeQRCodeAggiuntivi, 
			Integer maximumBlankPixelDelimiterCount,
			Float luminosita,
			Float contrasto,
			Float limiteSuperficiePagina,
			Integer limiteNumPagine) throws Exception {
		
		Map<String, FileRimodulatoBarcode> mappaBarcode = new HashMap<>();

		try {
			log.debug("Individuo i barcode sulle pagine");
			popolaMappaPagineBarcode(listaCoppiaAttachBeanFileOriginali, mappaBarcode, 
					rapportoLarghezzaPaginaLarghezzaAreaFirma,
					rapportoLarghezzaBarcodeLarghezzaPagina,
					rapportoAltezzaPaginaAltezzaAreaFirma, patternBarcodeFirma, patternBarcodeDataMatrix, 
					patternBarcodeQRCode, patternBarcodeQRCodeAggiuntivi, maximumBlankPixelDelimiterCount, luminosita, contrasto, limiteSuperficiePagina, limiteNumPagine);
			
			log.debug("Inizio processo di split file per barcode");
			//System.out.println(mappaBarcode);
			List<InfoFileRimodulatoBean> filesRimodulati = creaFilesRimodulati(mappaBarcode);
			log.debug("Individuati " + filesRimodulati.size() + " files rimodulati");
			
			return filesRimodulati;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}finally {
			deleteTempFile();
		}
	}


	/**
	 * 
	 */
	private static void deleteTempFile() {
		if(listaTempFileToDelete!=null && listaTempFileToDelete.size()>0) {
			for(String pathTempFile : listaTempFileToDelete) {
				try {
					File file = new File(pathTempFile);
					file.delete();
				} catch (Exception e) {
					
				}
			}
		}
	}

	private static List<InfoFileRimodulatoBean> creaFilesRimodulati(Map<String, FileRimodulatoBarcode> mappaBarcode) throws Exception {
		Set<String> barcodes = mappaBarcode.keySet();
		List<InfoFileRimodulatoBean> listaFileRimodulati = new ArrayList<>();
		int incrementaleNomeFile = 0;
		
		for(String barcode : barcodes) {
			
			File destTempFile = File.createTempFile("tempFile", ".pdf");
			FileOutputStream os = new FileOutputStream(destTempFile);

			Document document = new Document();
			PdfWriter writer = null;

			try {
				writer = PdfWriter.getInstance(document, os);
				
				writer.setTagged();
				writer.setLanguage("it");
				writer.setLinearPageMode();
				writer.createXmpMetadata();

				document.open();

				PdfContentByte cb = writer.getDirectContent();
				
				List<PagineDaUnireBarcode> listaPagine = mappaBarcode.get(barcode).getListaPagineDaUnire();
				
				int numFirmeAttese = 0;
				int numFirmeCompilate = 0;
				
				for (PagineDaUnireBarcode paginaDaUnire : listaPagine) {
					PdfReader reader = new PdfReader(paginaDaUnire.getPathFile());
					reader.unethicalreading = true;
					int numPagina = paginaDaUnire.getNumPagina();
					
					numFirmeAttese = numFirmeAttese + paginaDaUnire.getNumFirmeAttese();
					numFirmeCompilate = numFirmeCompilate + paginaDaUnire.getNumFirmeCompilate();

					addPageToDocument(document, writer, cb, reader, numPagina, false);

				}

				os.flush();
				
				if (document != null) {
					document.close();
				}
				if (os != null) {
					os.close();
				}
				
				InfoFileRimodulatoBean lInfoFileRimodulatoBean = new InfoFileRimodulatoBean();
				lInfoFileRimodulatoBean.setNomeFile("File_" + incrementaleNomeFile++ + ".pdf");
				lInfoFileRimodulatoBean.setImprontaFile(UtilityFileBarcode.calcolaImpronta(destTempFile, null, null));
				if(mappaBarcode.get(barcode).getTipoBarcode()!=null && "DATA_MATRIX".equals(mappaBarcode.get(barcode).getTipoBarcode())) {
					lInfoFileRimodulatoBean.setDatamatrix(barcode);					
				}else if (mappaBarcode.get(barcode).getTipoBarcode()!=null && "QR_CODE".equals(mappaBarcode.get(barcode).getTipoBarcode())){
					lInfoFileRimodulatoBean.setQrCode(barcode);
				}
				lInfoFileRimodulatoBean.setFlgFirmatoDigitalmente("0");
				lInfoFileRimodulatoBean.setNumFirmeAttese(String.valueOf(numFirmeAttese));
				lInfoFileRimodulatoBean.setNumFirmeCompilate(String.valueOf(numFirmeCompilate));
				lInfoFileRimodulatoBean.setDimensioneFile(String.valueOf(destTempFile.length()));
				lInfoFileRimodulatoBean.setFileRimodulato(destTempFile);
				lInfoFileRimodulatoBean.setNomeFileOriginale( mappaBarcode.get(barcode).getNomeFileOriginale() );
				listaFileRimodulati.add(lInfoFileRimodulatoBean);
				
			} catch (Exception e) {
				log.error("Errore durante la creazione dei file per barcode: " + e.getMessage(), e);
				throw new Exception("Errore durante la creazione dei file per barcode: " + e.getMessage(), e);
			} 	
		}
		
		return listaFileRimodulati;
	}

	/**
	 * @param listaCoppiaAttachBeanFileSbustati
	 * @param mappaBarcode
	 * @param maximumBlankPixelDelimiterCount2 
	 * @param margineAreaFirma2 
	 * @param heightAreaFirma2 
	 * @param widthAreaFirma2 
	 * @param patternFirma 
	 * @throws Exception 
	 */
	private static void popolaMappaPagineBarcode(List<CoppiaAttachBeanFile> listaCoppiaAttachBeanFileSbustati, Map<String, FileRimodulatoBarcode> mappaBarcode,  
			Double rapportoLarghezzaPaginaLarghezzaAreaFirma,
			Double rapportoLarghezzaBarcodeLarghezzaPagina,
			Double rapportoAltezzaPaginaAltezzaAreaFirma,
			List<String> patternBarcodeFirma, List<String> patternBarcodeDataMatrix, List<String> patternBarcodeQRCode, List<String> patternBarcodeQRCodeAggiuntivi,
			Integer maximumBlankPixelDelimiterCount,
			Float luminosita,
			Float contrasto,
			Float limiteSuperficiePagina,
			Integer limiteNumPagine) throws Exception {
		
		for(CoppiaAttachBeanFile coppiaAttachBeanFile : listaCoppiaAttachBeanFileSbustati) {
			if(coppiaAttachBeanFile.getFileAllegatoPdf()!=null) {
			
				File file = coppiaAttachBeanFile.getFileAllegatoPdf();
				log.debug("Elaborazione file " + file);
				String nomeFileOriginale = coppiaAttachBeanFile.getAttachMgo().getDisplayFilename();
				PdfReader reader = null;
				String lastBarCode = "";
				String barcode = null;
				String tipoBarcode = null;
				
				Map<String, FileRimodulatoBarcode> mappaPagineSlegate = new HashMap<String, FileRimodulatoBarcode>();
				
				List<String> qrCodeRilevati = new ArrayList<>();
				List<String> datamatrixRilevati = new ArrayList<>();
				String erroreScansioneIndividuati = "";
				
				try {
					reader = new PdfReader(file.getPath());
					reader.unethicalreading = true;
					
					if(checkLimitePaginePdf(reader.getNumberOfPages(), limiteNumPagine)) {
						reader.close();
						log.debug("Superato il numero massimo di pagine da elaborare, ");
						continue;
					}
					
					for (int i = 1; i <= reader.getNumberOfPages(); i++) {
						int numFirmeAttese = 0;
						int numFirmeCompilate = 0;
						
						/*Inizio a settare il barcode come non valido o nullo e se lo trovo sulla pagina lo sovrascrivo*/
						if(lastBarCode.contains("#BARCODE_NON_VALIDO_")) {
							barcode = lastBarCode;
						}else {
							barcode = "#BARCODE_NON_VALIDO_" + file.getName() + "_PAGINA_" + i;	
						}
						
						PdfBarcodeExtractor extractor = new PdfBarcodeExtractor(patternBarcodeFirma, patternBarcodeDataMatrix, patternBarcodeQRCode,  
								patternBarcodeQRCodeAggiuntivi, rapportoLarghezzaPaginaLarghezzaAreaFirma,  
								rapportoLarghezzaBarcodeLarghezzaPagina,  rapportoAltezzaPaginaAltezzaAreaFirma, maximumBlankPixelDelimiterCount, luminosita, contrasto);
						
						List<BarcodeResult> result = null;
						
						//se le dimensioni della pagina rientrano in quelle accettate scansiono la pagina per trovare il barcode, altrimenti lascio il result a null 
						//considerando la pagina come se non avesse barcode
						Rectangle psize = reader.getPageSize(i);
						if(dimensioniPaginaAccettate(psize, limiteSuperficiePagina)) {
							try {
								result = extractor.scanPage(convertPageToImage(file, i), i);
							} catch (IOException e) {
								log.error("Errore nella scansione della pagina " + i + " - La pagina verra' ignorata nel processo di rilevamento barcode");
							}
						} else {
							log.debug("Pagina troppo grande, non verra' elaborata");
						}
						
						if(result!=null && result.size()>0) {
							int numBarcodeNoFirmaValidi = 0;
							for(BarcodeResult resultBarcode : result) {
								//System.out.println(resultBarcode.getBarcode()+ " " + resultBarcode.getTipoBarcode());
								if(resultBarcode.getErroreScansione()!=null && !"".equalsIgnoreCase(resultBarcode.getErroreScansione())) {
									erroreScansioneIndividuati = erroreScansioneIndividuati + "Errore barcode alla pagina " + i + ": " + resultBarcode.getErroreScansione() + "\n\n";
								}else {
									/*Sezione barcode non di firma*/
									if(!resultBarcode.isFlagBarcodeFirma()) {
										if("DATA_MATRIX".equalsIgnoreCase(resultBarcode.getTipoBarcode())) {
											if(!datamatrixRilevati.contains(resultBarcode.getBarcode())) {
												datamatrixRilevati.add(resultBarcode.getBarcode());
											}
										}
										else if("QR_CODE".equalsIgnoreCase(resultBarcode.getTipoBarcode())) {
											if(!qrCodeRilevati.contains(resultBarcode.getBarcode())) {
												qrCodeRilevati.add(resultBarcode.getBarcode());
											}
											if( resultBarcode.isFlagQrcodeAggiuntivo() ){
												if(!qrCodeRilevati.contains(resultBarcode.getBarcode())) {
													qrCodeRilevati.add(resultBarcode.getBarcode());
												}
											}
										}
										
										/*Se il barcode è valido lo conteggio*/
										if(resultBarcode.isFlagBarcodeValido()) {
											numBarcodeNoFirmaValidi++;
											barcode = resultBarcode.getBarcode();
											tipoBarcode = resultBarcode.getTipoBarcode();																						
										}
//										/*Se non è valido per me è un barcode nullo e lo inserisco in una pagina a parte*/
//										else {
//											barcode = "#BARCODE_NON_VALIDO_"  + i;
//										}										
										
									/*Sezione barcode di firma*/
									}else {
										numFirmeAttese++;
										if(resultBarcode.isFlagFirmaPresente()) {
											numFirmeCompilate++;
										}
									}
								}															
							}
							//System.out.println("qrRilevati " + qrCodeRilevati);
							
							/*Se il num di barcode senza firma validi sono piu di 1 sulla stessa pagina lo considero non valido*/
							if(numBarcodeNoFirmaValidi>1) {
								if(lastBarCode.contains("#BARCODE_NON_VALIDO_")) {
									barcode = lastBarCode;
								}else {
									barcode = "#BARCODE_NON_VALIDO_" + file.getName() + "_PAGINA_" + i;	
								}
							}						
						}
						
						PagineDaUnireBarcode paginaDaUnire = new PagineDaUnireBarcode();
						paginaDaUnire.setNumPagina(i);
						paginaDaUnire.setPathFile(file.getPath());
						paginaDaUnire.setNumFirmeAttese(numFirmeAttese);
						paginaDaUnire.setNumFirmeCompilate(numFirmeCompilate);
						
						if(!barcode.contains("#BARCODE_NON_VALIDO_")) {							
							if(barcodeGiaPresente(barcode, mappaBarcode)) {
								aggiungiPaginaABarcode(barcode, mappaBarcode, paginaDaUnire);
							}else {
								aggiungiBarcode(barcode, tipoBarcode, mappaBarcode, paginaDaUnire, nomeFileOriginale);
							}
						
						/*Barcode non validi li aggiungo alla mappa delle pagine slegate*/
						}else {
							if(lastBarCode!=null && !"".equals(lastBarCode) && lastBarCode.equals(barcode)) {
								aggiungiPaginaABarcode(barcode, mappaPagineSlegate, paginaDaUnire);
							}else {
								aggiungiBarcode(barcode, "", mappaPagineSlegate, paginaDaUnire, nomeFileOriginale);
							}
						}
						
						lastBarCode = barcode;
						
					}
					
					InfoFileBarcodeBean infoFileBarcodeBean = new InfoFileBarcodeBean();
					//infoFileBarcodeBean.setNomeFileOriginale(nomeFileOriginale);
					infoFileBarcodeBean.setErroreScansione(erroreScansioneIndividuati);
					
					String qrCodeRilevatiString = "";
					for(String qrCode : qrCodeRilevati) {
						qrCodeRilevatiString = qrCodeRilevatiString + qrCode + "|*|";
					}
					infoFileBarcodeBean.setQrCodeRilevati(qrCodeRilevatiString);
					//System.out.println("qrCodeRilevatiString " + qrCodeRilevatiString);
					
					String dataMatrixRilevatiString = "";
					for(String dataMatrix : datamatrixRilevati) {
						dataMatrixRilevatiString = dataMatrixRilevatiString + dataMatrix + "|*|";
					}
					infoFileBarcodeBean.setDatamatrixRilevati(dataMatrixRilevatiString);
					
					coppiaAttachBeanFile.setInfoFileBarcodeBean(infoFileBarcodeBean);
					
					/*Se alla fine dell'elaborazione del file il numero di pagine slegate sono esattamente tutte le 
					 * pagine del file, non inserisco il file nella mappa dei barcode individuati e quindi non verrà rimodulato.
					 * Se invece le pagine non sono esattamente le stesse significa che all'interno del file sono stati individuati dei barcode e quindi rimodulo un file con sole
					 * le pagine slegate da barcode*/					
					if(!verificaNumPagineSlegateUgualePagTotali(mappaPagineSlegate, reader)) {
						mappaBarcode.putAll(mappaPagineSlegate);
					}					
					
				}catch(Exception ex) {
					log.error("Errore durante l' individuazione dei barcode sulle pagine: " + ex.getMessage(), ex);
					throw new Exception("Errore durante l' individuazione dei barcode sulle pagine" + ex.getMessage(), ex);
				}finally {
					if(reader!=null) {
						reader.close();
					}
				}
			}
		}
	}

	/**
	 * Questo metodo controlla se il numero di agine del pdf supera il limite consentito, oltre il quale si 
	 * hanno elaborazioni troppo lunghe che possono portare problemi
	 **/
	private static boolean checkLimitePaginePdf(int numberOfPages, Integer limiteNumPagine) {	
		if(numberOfPages>limiteNumPagine) {
			return true;
		}		
		return false;
	}
	
	
	/**
	 * Questo metodo controlla se la superficie della pagina (altezza * larghezza) rientra nei limiti consentiti.
	 * Perche alcune pagine di alcuni file erano troppo grandi (formato A3) e nella trasformazione della pagina in immagine andava in OutOfMemory
	 **/
	private static boolean dimensioniPaginaAccettate(Rectangle psize, float limiteSuperficiePagina) {
		try {
			
			float superficiePagina = psize.getWidth() * psize.getHeight();
			
			int superficiePaginaInt=(int)(Math.round(superficiePagina));
			
			if(superficiePaginaInt > limiteSuperficiePagina) {
				return false;
			}
			
			return true;
		
		} catch (Exception e) {
			log.error("Errore durante il controllo della superficie della pagina " + e.getMessage(), e);
			return false;	
		} 
	}

	@SuppressWarnings("unlikely-arg-type")
	private static boolean verificaNumPagineSlegateUgualePagTotali(Map<String, FileRimodulatoBarcode> mappaPagineSlegate,
			PdfReader reader) {
		if(mappaPagineSlegate!=null && mappaPagineSlegate.size()==1) {
			Set<String> keySet = mappaPagineSlegate.keySet();
			FileRimodulatoBarcode fileRimodulato = mappaPagineSlegate.get(keySet.iterator().next());
			int numPagineSlegate = fileRimodulato.getListaPagineDaUnire().size();
			if(numPagineSlegate == reader.getNumberOfPages()) {
				return true;
			}
		}
		return false;
	}


	private static void aggiungiBarcode(String barcode, String tipoBarcode, Map<String, FileRimodulatoBarcode> mappaBarcode, PagineDaUnireBarcode paginaDaUnire, String nomeFileOriginale) {
		FileRimodulatoBarcode fileRimodulato = new FileRimodulatoBarcode();
		fileRimodulato.getListaPagineDaUnire().add(paginaDaUnire);
		fileRimodulato.setBarcode(barcode);
		fileRimodulato.setTipoBarcode(tipoBarcode);
		fileRimodulato.setNomeFileOriginale(nomeFileOriginale);
		
		mappaBarcode.put(barcode, fileRimodulato);		
		
		
	}

	private static void aggiungiPaginaABarcode(String barcode, Map<String, FileRimodulatoBarcode> mappaBarcode, PagineDaUnireBarcode paginaDaUnire) {
		Set<String> barcodes = mappaBarcode.keySet();
		
		for(String barcodePresente : barcodes) {
			if(barcode.equals(barcodePresente)) {
				mappaBarcode.get(barcode).getListaPagineDaUnire().add(paginaDaUnire);
			}
		}
		
	}

	private static boolean barcodeGiaPresente(String barcode, Map<String, FileRimodulatoBarcode> mappaBarcode) {
		Set<String> barcodes = mappaBarcode.keySet();
		
		if(barcodes!=null && barcodes.size()>0 && barcodes.contains(barcode)) {
			return true;
		}else {
			return false;
		}		
	}
	
	private static void addPageToDocument(Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		// Verifico la rotazione della pagina corrente
		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4) {
				document.setPageSize(PageSize.A4.rotate());
			} else {
				document.setPageSize(psize);
			}
		} else {
			if (forceA4) {
				document.setPageSize(PageSize.A4);
			} else {
				document.setPageSize(psize);
			}
		}

		// Creo una nuova pagina nel document in cui copiare la pagina corrente
		document.newPage();
		// Raddrizzo l'immagine a seconda della rotazione
		switch (psize.getRotation()) {
		case 0:
			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			break;
		case 90:
			cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
			break;
		case 180:
			cb.addTemplate(page, -1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
			break;
		case 270:
			cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
			break;
		default:
			break;
		}
	}
	
	public static BufferedImage convertPageToImage(File filePdf, int pageNumber) throws InvalidPasswordException, IOException
			/*throws Exception*/ {

		PDDocument document = PDDocument.load(filePdf);		   
		PDFRenderer pdfRenderer = new PDFRenderer(document);		

		int defaultDPI = 185;
		BufferedImage bImage = pdfRenderer.renderImageWithDPI(pageNumber - 1, defaultDPI, ImageType.RGB);

		document.close();
		return bImage;
	}
}

