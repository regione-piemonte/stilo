/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import it.eng.module.foutility.beans.generated.PaginaTimbro;
import it.eng.module.foutility.beans.generated.PosizioneTimbroNellaPagina;
import it.eng.module.foutility.beans.generated.TipoPagina;
import it.eng.module.foutility.beans.generated.TipoRotazione;
import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.multiLayer.PdfMultiLayerUtil;

/**
 * Espone metodi di utilità per la generazione di qrcode e la loro aggiunta a documenti pdf.<br/>
 * Per l'estrazione dei qrcode e dei relativi dati, fare riferimento a
 * 
 * @see it.eng.utility.pdf.barcode.PdfBarcodeMetadataExtractor
 *
 */
public class CopiaConformeUtils {

	public static final Logger log = LogManager.getLogger(CopiaConformeUtils.class);

	public int FONT_SIZE;
	public float MARGINE_SUP;
	public float MARGINE_INF;
	public float MARGINE_DX;
	public float MARGINE_SN;
	public float SPAZIO_RIGHE;
	public float IMAGE_WIDTH;
	public String FONT_NAME;

	public String getFONT_NAME() {
		return FONT_NAME;
	}

	public void setFONT_NAME(String fONT_NAME) {
		FONT_NAME = fONT_NAME;
	}

	public CopiaConformeUtils() {
		super();
	}

	public CopiaConformeUtils(int fONT_SIZE, float mARGINE_SUP, float mARGINE_INF, float mARGINE_DX, 
			float mARGINE_SN,
			float sPAZIO_RIGHE, String fontName) {
		super();
		FONT_SIZE = fONT_SIZE;
		MARGINE_SUP = mARGINE_SUP;
		MARGINE_INF = mARGINE_INF;
		MARGINE_DX = mARGINE_DX;
		MARGINE_SN = mARGINE_SN;
		SPAZIO_RIGHE = sPAZIO_RIGHE;
		FONT_NAME = fontName;
	}

	public File aggiungiTimbro(File file, PaginaTimbro paginaTimbro, PosizioneTimbroNellaPagina posizioneTimbro,
			TipoRotazione rotazioneTimbro, String testoIntestazione, String testoAggiuntivo,
			File fileImmagine, Float ampiezzaRiquadro,
			String outputFileLoc, String fileOutputName)
			throws FileNotFoundException, DocumentException, UnsupportedEncodingException, Exception {

		PdfReader pdfReader;

		FileInputStream fis = new FileInputStream(file);
		pdfReader = new PdfReader( fis );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfStamper pdfStamper = null;
		try{
			pdfStamper = new PdfStamper(pdfReader, baos);
		} catch (Exception e){
			throw new Exception(FileOpMessage.TIMBRO_OP_PDF_PASSWORD);
		}
		// TODO: verificare questo dato
		// http://www.pdf-tools.com/pdf/validate-pdfa-online.aspx
		// pdfStamper.getWriter().setPDFXConformance(PdfWriter.PDFA1B);

		int totalPages = pdfReader.getNumberOfPages();
		log.debug("Numero totale pagine documento: " + totalPages);
		// ricavo le pagine da cui apporre il timbro
		TipoPagina tipoPagina = null;
		if (paginaTimbro != null && paginaTimbro.getPagine() != null) {
			// sono state specificate le pagine su cui apporre il timbro (lista
			// pagine, intervallo da-a)
			if (paginaTimbro.getPagine().getPagina().size() > 0) {
				List<Integer> numeroPagine = paginaTimbro.getPagine().getPagina();
				// log.info("numeroPagine " + numeroPagine);
				for (Integer paginaDaTimbrare : numeroPagine) {
					// verifico se la pagina esiste nel pdf
					if (paginaDaTimbrare <= totalPages) {
						aggiungiTimbro(paginaDaTimbrare, pdfStamper, posizioneTimbro, rotazioneTimbro, testoIntestazione, testoAggiuntivo, fileImmagine, ampiezzaRiquadro);
					}
				}
			} else if (paginaTimbro.getPagine().getPaginaDa() > 0 && paginaTimbro.getPagine().getPaginaA() > 0) {
				Integer paginaDa = paginaTimbro.getPagine().getPaginaDa();
				// log.info("paginaDa " + paginaDa);
				Integer paginaA = paginaTimbro.getPagine().getPaginaA();
				// log.info("paginaA " + paginaA);
				if (paginaA > 0 && paginaDa > 0) {
					for (int i = paginaDa; i <= paginaA; i++) {
						aggiungiTimbro(i, pdfStamper, posizioneTimbro, rotazioneTimbro, testoIntestazione, testoAggiuntivo, fileImmagine, ampiezzaRiquadro);
					}
				}
			}
		} else if (paginaTimbro != null && paginaTimbro.getTipoPagina() != null) {
			// è stato specificato il tipo di pagina (prima, ultima, tutte)
			tipoPagina = paginaTimbro.getTipoPagina();
			// log.info("tipoPagina " + tipoPagina );
			if (tipoPagina != null && tipoPagina.equals(TipoPagina.PRIMA)) {
				aggiungiTimbro(1, pdfStamper, posizioneTimbro, rotazioneTimbro, testoIntestazione, testoAggiuntivo, fileImmagine, ampiezzaRiquadro);
			}
			if (tipoPagina != null && tipoPagina.equals(TipoPagina.ULTIMA)) {
				aggiungiTimbro(totalPages, pdfStamper,  posizioneTimbro, rotazioneTimbro,  testoIntestazione, testoAggiuntivo, fileImmagine, ampiezzaRiquadro);
			}
			if (tipoPagina != null && tipoPagina.equals(TipoPagina.TUTTE)) {
				for (int i = 1; i <= totalPages; i++)
					aggiungiTimbro(i, pdfStamper, posizioneTimbro, rotazioneTimbro, testoIntestazione, testoAggiuntivo, fileImmagine, ampiezzaRiquadro);
			}
		} else {

			return null;
		}

		pdfStamper.getWriter().createXmpMetadata();

		pdfStamper.close();
		pdfReader.close();
		if( fis!=null )
			fis.close();

		// File fileTimbrato = new File( "provaTimbrata.pdf");
		File fileTimbrato = new File(outputFileLoc + "/" + fileOutputName);
		FileOutputStream fileOutputStream = new FileOutputStream(fileTimbrato);
		IOUtils.write(baos.toByteArray(), fileOutputStream);
		baos.close();
		fileOutputStream.close();
		return fileTimbrato;
	}

	private void aggiungiTimbro(Integer paginaDaTimbrare, PdfStamper pdfStamper, 
			PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro,  String testoIntestazione, String testoAggiuntivo, 
			File fileImmagine, Float ampiezzaRiquadro)
			throws DocumentException, IOException, Exception {

		log.debug("Timbro la pagina " + paginaDaTimbrare);
		PdfContentByte over = pdfStamper.getOverContent(paginaDaTimbrare);

		log.debug("FONT_NAME::: " + FONT_NAME );
		BaseFont bf = BaseFont.createFont(FONT_NAME, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		over.setFontAndSize(bf, FONT_SIZE);

		float dimensioneSingoloCarattere = 1;
		if( testoIntestazione!=null ){
			dimensioneSingoloCarattere = bf.getWidthPoint(testoIntestazione.charAt(0), FONT_SIZE);
			log.debug("dimensioneSingoloCarattere::::  "  + dimensioneSingoloCarattere);
			if( dimensioneSingoloCarattere==0.0f){
				dimensioneSingoloCarattere = bf.getWidthPoint('a', 8);
			}
		} else {
			dimensioneSingoloCarattere = bf.getWidthPoint('a', 8);
		}
		
		float dimensioneTestoIntestazione = 0;
		if (testoIntestazione != null) {
			dimensioneTestoIntestazione = bf.getWidthPoint(testoIntestazione, FONT_SIZE);
			log.debug("dimensioneTestoIntestazione::: " + dimensioneTestoIntestazione);
		}
		float dimensioneTestoInChiaro = bf.getWidthPoint(testoIntestazione, FONT_SIZE);
		log.debug("dimensioneTestoInChiaro::: " + dimensioneTestoInChiaro);

		List<Float> posXIntestazione = new ArrayList<Float>();
		List<Float> posYIntestazione = new ArrayList<Float>();
		List<String> listaTestoIntestazione = new ArrayList<String>();
		
		int alignText = 0;
		float orientationText = 0;

		float widthPagina = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_SN - MARGINE_DX;
		float heighthPagina = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - MARGINE_INF;

		
		
		boolean isTestoAggiuntivo=false;
		boolean isImmagineLogo = false;
		Image img = null;
		if( fileImmagine!=null){
			log.debug("Aggiungo l'immagine");
			img = Image.getInstance(fileImmagine.getAbsolutePath());
			img.scaleAbsolute( IMAGE_WIDTH, img.getHeight() * IMAGE_WIDTH / img.getWidth());
			
			log.debug("w " + img.getWidth() + " h " + img.getHeight());
			log.debug("w " + img.getScaledWidth() + " h " + img.getScaledHeight());
			isImmagineLogo = true;
		}
		
		if ( testoAggiuntivo!=null ) {
			isTestoAggiuntivo = true;
		}
		
		float margineAggiuntivo = 0;
		if (rotazioneTimbro.equals(TipoRotazione.VERTICALE)) {
			
			if(ampiezzaRiquadro!=null){
				ampiezzaRiquadro = heighthPagina * ampiezzaRiquadro /100;
			}
			log.debug("ampiezzaRiquadro " + ampiezzaRiquadro);
			
			float spazioDisponibile = 0;
			if (pdfStamper.getReader().getPageRotation(paginaDaTimbrare) == 0 || pdfStamper.getReader().getPageRotation(paginaDaTimbrare) == 180) {
				spazioDisponibile = ampiezzaRiquadro;
			} else {
				spazioDisponibile = ampiezzaRiquadro * pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() / 100;
			}
			log.debug("spazioDisponibile:  " + spazioDisponibile);
			
			int caratteriPerRiga = (int) (spazioDisponibile / dimensioneSingoloCarattere);
			log.debug("caratteriPerRiga:: " + caratteriPerRiga);
			int numeroRighe = (int) (testoIntestazione.length() / caratteriPerRiga) + 1;
			log.debug("numeroRighe::: " + numeroRighe);
			orientationText = -90;
			
			if( isTestoAggiuntivo ) {
				margineAggiuntivo = 2 * SPAZIO_RIGHE;
			}
			if( isImmagineLogo ) {
				margineAggiuntivo = margineAggiuntivo /*+ img.getScaledHeight()*/;
			}
			
			if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN)) {
				orientationText = 90;
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add((MARGINE_SN + (i) * SPAZIO_RIGHE )  );
						posYIntestazione.add(  pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - ampiezzaRiquadro);
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add( MARGINE_SN );
					posYIntestazione.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - ampiezzaRiquadro);
				}
			} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_DX)) {
				orientationText = -90;
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - (( i) * SPAZIO_RIGHE) );
						posYIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP );
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX  );
					posYIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - ampiezzaRiquadro);
				}
			} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN)) {
				orientationText = 90;
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add( MARGINE_SN +( i) * SPAZIO_RIGHE);
						posYIntestazione.add( MARGINE_INF);
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add( MARGINE_SN  );
					posYIntestazione.add( MARGINE_INF  );
				}
			} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_DX)) {
				orientationText = -90;
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - (i * SPAZIO_RIGHE)  );
						posYIntestazione.add( MARGINE_INF + ampiezzaRiquadro );
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX  );
					posYIntestazione.add( MARGINE_INF + ampiezzaRiquadro );
				}
			}
		} else {
			
			if(ampiezzaRiquadro!=null){
				ampiezzaRiquadro = widthPagina * ampiezzaRiquadro /100;
			}
			log.debug("ampiezzaRiquadro " + ampiezzaRiquadro);
			
			float spazioDisponibile = 0;
			if (pdfStamper.getReader().getPageRotation(paginaDaTimbrare) == 0 || pdfStamper.getReader().getPageRotation(paginaDaTimbrare) == 180) {
				//spazioDisponibile = ampiezzaRiquadro * pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() / 100;
				spazioDisponibile = ampiezzaRiquadro;
			} else {
				spazioDisponibile = ampiezzaRiquadro * pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() / 100;
			}
			int caratteriPerRiga = (int) (spazioDisponibile / dimensioneSingoloCarattere);
			int numeroRighe = (int) (testoIntestazione.length() / caratteriPerRiga) + 1;
			
			if( isTestoAggiuntivo ) {
				margineAggiuntivo = 2 * SPAZIO_RIGHE;
			}
			if( isImmagineLogo ) {
				margineAggiuntivo = margineAggiuntivo + img.getScaledHeight();
			}

			if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN)) {
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add(MARGINE_SN );
						posYIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - (i * SPAZIO_RIGHE));
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add( MARGINE_SN );
					posYIntestazione.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP);
				}
			} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_DX)) {
				//alignText = PdfContentByte.ALIGN_RIGHT;
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - ampiezzaRiquadro);
						posYIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - (i * SPAZIO_RIGHE));
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - ampiezzaRiquadro );
					posYIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP);
				}
			}
			if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_DX)) {
				//alignText = PdfContentByte.ALIGN_RIGHT;
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add( pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - ampiezzaRiquadro );
						posYIntestazione.add(MARGINE_INF + (((numeroRighe)* SPAZIO_RIGHE)-(i*SPAZIO_RIGHE))+ margineAggiuntivo);
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - ampiezzaRiquadro );
					posYIntestazione.add( MARGINE_INF + margineAggiuntivo );
				}
			}
			if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN)) {
				if ( spazioDisponibile < dimensioneTestoIntestazione) {
					log.debug("Il testo verrà diviso su più righe per mancanza di spazio");
					int beginIndex = 0;
					int endIndex = 0;
					for (int i = 0; i < numeroRighe; i++) {
						beginIndex = (i) * caratteriPerRiga;
						endIndex = (i + 1) * caratteriPerRiga;
						if (endIndex >= testoIntestazione.length())
							endIndex = testoIntestazione.length();
						listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
						posXIntestazione.add( MARGINE_SN);
						posYIntestazione.add( MARGINE_INF + (((numeroRighe)* SPAZIO_RIGHE)-(i*SPAZIO_RIGHE)) + margineAggiuntivo);
						if (endIndex == testoIntestazione.length())
							break;
					}
				} else {
					listaTestoIntestazione.add(testoIntestazione);
					posXIntestazione.add( MARGINE_SN );
					posYIntestazione.add( MARGINE_INF + margineAggiuntivo);
				}
			}
		}
				
		log.debug("Coordinate X Intestazione " + posXIntestazione);
		log.debug("Coordinate Y Intestazione " + posYIntestazione);
		log.debug("Testi Intestazione " + listaTestoIntestazione);
		if(listaTestoIntestazione!=null  ){
			log.debug("listaTestoIntestazione " + listaTestoIntestazione.size() );
		}

		float posXImg = 0;
		float posYImg = 0;
		float posXTestoAggiuntivo = 0;
		float posYTestoAggiuntivo = 0;
		
		for (int i = 0; i < listaTestoIntestazione.size(); i++) {
			String s = listaTestoIntestazione.get(i);
			over.beginText();
			over.showTextAligned(alignText, s, posXIntestazione.get(i), posYIntestazione.get(i), orientationText);
			over.endText();
			
			
			
			if( rotazioneTimbro!=null && rotazioneTimbro.equals(TipoRotazione.ORIZZONTALE) && (i==(listaTestoIntestazione.size()-1))){
				posXTestoAggiuntivo = posXIntestazione.get(i);
				posYTestoAggiuntivo = posYIntestazione.get(i) - 2*SPAZIO_RIGHE;
				
				posYImg = posYIntestazione.get(i);
				posXImg = posXIntestazione.get(i);
				
			} else if( rotazioneTimbro!=null && rotazioneTimbro.equals(TipoRotazione.VERTICALE) && (i==(listaTestoIntestazione.size()-1))){
				if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN) || posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN)) {
					posXTestoAggiuntivo = posXIntestazione.get(i) + 2*SPAZIO_RIGHE;
					posYTestoAggiuntivo = posYIntestazione.get(i) ;		
				} else {
					posXTestoAggiuntivo = posXIntestazione.get(i) - 2*SPAZIO_RIGHE;
					posYTestoAggiuntivo = posYIntestazione.get(i) ;		
				}
			}
		}
		
		if ( testoAggiuntivo!=null ) {
			if( rotazioneTimbro!=null && rotazioneTimbro.equals(TipoRotazione.ORIZZONTALE) ){
				
			} else if( rotazioneTimbro!=null && rotazioneTimbro.equals(TipoRotazione.VERTICALE) ){
				if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN) || posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN)) {
					orientationText = 90;
				} else {
					orientationText = -90;
				}
			}
			log.debug("Aggiungo il testo aggiuntivo posXTestoAggiuntivo " + posXTestoAggiuntivo + " posYTestoAggiuntivo "+ posYTestoAggiuntivo);
			over.beginText();
			over.showTextAligned(alignText, testoAggiuntivo, posXTestoAggiuntivo, posYTestoAggiuntivo, orientationText);
			over.endText();
		} else {
			posXTestoAggiuntivo = 0;
			posYTestoAggiuntivo = 0;
		}
		
		if( fileImmagine!=null){
			if (rotazioneTimbro.equals(TipoRotazione.VERTICALE)) {
				if (posizioneTimbro != null
						&& (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN) || posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN))) {
					img.setRotation((float) Math.PI / 2);
				} else if (posizioneTimbro != null
						&& (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_DX) || posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_DX))) {
					img.setRotation(3 * (float) Math.PI / 2);
				}
				
				if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_DX)) {
					posYImg = MARGINE_INF /*+  img.getScaledHeight() */;
					posXImg = posXTestoAggiuntivo - SPAZIO_RIGHE - img.getScaledWidth();
				} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN)) {
					posYImg = MARGINE_INF + ampiezzaRiquadro - img.getScaledHeight() ;
					posXImg = posXTestoAggiuntivo  ;
				} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_DX)) {
					posYImg = posYTestoAggiuntivo - ampiezzaRiquadro;
					posXImg = posXTestoAggiuntivo - img.getScaledWidth();
				} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN)) {
					posYImg = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - img.getScaledHeight();
					posXImg = posXTestoAggiuntivo + SPAZIO_RIGHE;
				}
				
			} else {
				if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_DX)) {
					posYImg = MARGINE_INF + SPAZIO_RIGHE ;
					posXImg = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - img.getScaledWidth();
				} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.BASSO_SN)) {
					posYImg = MARGINE_INF + SPAZIO_RIGHE ;
					posXImg = MARGINE_SN + ampiezzaRiquadro - img.getScaledWidth();
				} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_DX)) {
					posYImg = posYTestoAggiuntivo - SPAZIO_RIGHE - img.getScaledHeight();
					posXImg = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - img.getScaledWidth();
				} else if (posizioneTimbro.equals(PosizioneTimbroNellaPagina.ALTO_SN)) {
					posYImg = posYTestoAggiuntivo - SPAZIO_RIGHE - img.getScaledHeight();
					posXImg = MARGINE_SN + ampiezzaRiquadro - img.getScaledWidth();
				}
			}
	
			log.debug("Aggiungo l'immagine posXImg " + posXImg + " posYImg " + posYImg);
			img.setAbsolutePosition(posXImg,  posYImg);
			over.addImage(img);
		}
	}

	

	public int getFONT_SIZE() {
		return FONT_SIZE;
	}

	public void setFONT_SIZE(int fONT_SIZE) {
		FONT_SIZE = fONT_SIZE;
	}



	public float getMARGINE_SUP() {
		return MARGINE_SUP;
	}

	public void setMARGINE_SUP(float mARGINE_SUP) {
		MARGINE_SUP = mARGINE_SUP;
	}

	public float getMARGINE_INF() {
		return MARGINE_INF;
	}

	public void setMARGINE_INF(float mARGINE_INF) {
		MARGINE_INF = mARGINE_INF;
	}

	public float getMARGINE_DX() {
		return MARGINE_DX;
	}

	public void setMARGINE_DX(float mARGINE_DX) {
		MARGINE_DX = mARGINE_DX;
	}

	public float getMARGINE_SN() {
		return MARGINE_SN;
	}

	public void setMARGINE_SN(float mARGINE_SN) {
		MARGINE_SN = mARGINE_SN;
	}

	public float getSPAZIO_RIGHE() {
		return SPAZIO_RIGHE;
	}

	public void setSPAZIO_RIGHE(float sPAZIO_RIGHE) {
		SPAZIO_RIGHE = sPAZIO_RIGHE;
	}
	
	public float getIMAGE_WIDTH() {
		return IMAGE_WIDTH;
	}

	public void setIMAGE_WIDTH(float iMAGE_WIDTH) {
		IMAGE_WIDTH = iMAGE_WIDTH;
	}

	public static void main(String[] args) {
		CopiaConformeUtils util = new CopiaConformeUtils();
		util.setFONT_NAME("Courier");
		util.setFONT_SIZE(8);
		util.setMARGINE_INF(20.0f);
		util.setMARGINE_SUP(20.0f);
		util.setMARGINE_SN(20.0f);
		util.setMARGINE_DX(20.0f);
		//util.setMARGINE_TIMBRO_INTESTAZIONE(10.0f);
		
		PaginaTimbro paginaTimbro = new PaginaTimbro();
		paginaTimbro.setTipoPagina(TipoPagina.PRIMA);
		String path="C:/Users/TESAURO/Downloads/bond_300mln_2024b_15.03.2022_unicredit_signed_perde_firma_a_destra.pdf";
		File inputFile = new File(path);
		try {
			PdfBean pdfBean = PdfMultiLayerUtil.checkPdfMultiLayer(inputFile);
			if( pdfBean.getMultiLayer()!=null && pdfBean.getMultiLayer()  )
				inputFile = PdfMultiLayerUtil.manageMultiLayerPdf(inputFile, "application/pdf");
			
			util.aggiungiTimbro(inputFile,  paginaTimbro, PosizioneTimbroNellaPagina.ALTO_DX, 
					TipoRotazione.ORIZZONTALE,
					  "abcd", "",
					 null, 25f,
					"C:/Users/TESAURO/Downloads/output/", "fileTimbrato.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*File file = new File("C:/Users/Anna Tresauro/Downloads/Autodichiarazione per assenza.pdf");
		PdfReader pdfReader;
		try{
			InputStream is = new FileInputStream(file);
			pdfReader = new PdfReader(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper pdfStamper = null;
			pdfStamper = new PdfStamper(pdfReader, baos);
			
			int totalPages = pdfReader.getNumberOfPages();
			System.out.println("Numero totale pagine documento: " + totalPages);
			
			PdfContentByte over = pdfStamper.getOverContent(1);

			System.out.println("FONT_NAME::: " + "Courier" );
			BaseFont bf = BaseFont.createFont("Courier", BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			over.setFontAndSize(bf, 8);

			String testo = "\naspmalo.ASPMALO-PROTOCOLLO.PG.2020.0000707.E";
			String testoIntestazione = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			char carattere1 = testo.charAt(0);
			System.out.println(carattere1);
			float dimensioneSingoloCarattere = bf.getWidthPoint(carattere1, 8);
			System.out.println("dimensioneSingoloCarattere::::  "  + dimensioneSingoloCarattere);
			if( dimensioneSingoloCarattere==0.0f){
				dimensioneSingoloCarattere = bf.getWidthPoint('a', 8);
			}
			//dimensioneSingoloCarattere = 0.0f;
			System.out.println("dimensioneSingoloCarattere::::  "  + dimensioneSingoloCarattere);
			
			float spazioDisponibile = 689.0f;
			int caratteriPerRiga = (int) (spazioDisponibile / dimensioneSingoloCarattere);
			System.out.println("caratteriPerRiga:: " + caratteriPerRiga);
			int numeroRighe = (int) (testoIntestazione.length() / caratteriPerRiga) + 1;
			System.out.println("numeroRighe::: " + numeroRighe);
		} catch (Exception e){
			e.printStackTrace();
		}*/

	}

}
