package org.jpedal.examples.viewer.utils;

import it.eng.fileOperation.clientws.CodificaTimbro;
import it.eng.fileOperation.clientws.PaginaTimbro;
import it.eng.fileOperation.clientws.PosizioneRispettoAlTimbro;
import it.eng.fileOperation.clientws.PosizioneTimbroNellaPagina;
import it.eng.fileOperation.clientws.TipoPagina;
import it.eng.fileOperation.clientws.TipoRotazione;
import it.eng.fileOperation.clientws.PaginaTimbro.Pagine;

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
import org.jpedal.utils.LogWriter;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeDatamatrix;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class TimbroUtils {

	public int MAX_LENGHT_TIMBRO_PDF417;
	public int FONT_SIZE;
	public float MARGINE_SUP;
	public float MARGINE_INF;
	public float MARGINE_DX;
	public float MARGINE_SN;
	public float MARGINE_TIMBRO_INTESTAZIONE;
	public String FONT_NAME;
	
	public TimbroUtils() {
		super();
	}

	public TimbroUtils(int mAX_LENGHT_TIMBRO_PDF417, int fONT_SIZE,
			float mARGINE_SUP, float mARGINE_INF, float mARGINE_DX,
			float mARGINE_SN, float mARGINE_TIMBRO_INTESTAZIONE, 
			String fontName) {
		super();
		MAX_LENGHT_TIMBRO_PDF417 = mAX_LENGHT_TIMBRO_PDF417;
		FONT_SIZE = fONT_SIZE;
		MARGINE_SUP = mARGINE_SUP;
		MARGINE_INF = mARGINE_INF;
		MARGINE_DX = mARGINE_DX;
		MARGINE_SN = mARGINE_SN;
		MARGINE_TIMBRO_INTESTAZIONE = mARGINE_TIMBRO_INTESTAZIONE;
		FONT_NAME = fontName;
	}

	public File timbraPdf( File file, CodificaTimbro codifica, String testo, 
			PaginaTimbro paginaTimbro,
			PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro,
			PosizioneRispettoAlTimbro posizioneTestoInChiaro, 
			boolean timbroSingolo,
			String testoIntestazione, PosizioneRispettoAlTimbro posizioneIntestazione, 
			String outputFileLoc, String fileOutputName,
			boolean moreLines) //{
		throws FileNotFoundException, DocumentException, UnsupportedEncodingException, Exception {

		PdfReader pdfReader = null;
		PdfStamper pdfStamper = null;
		ByteArrayOutputStream baos = null;
		try {
			pdfReader = new PdfReader( new FileInputStream( file ) );
			baos = new ByteArrayOutputStream();
			pdfStamper = new PdfStamper( pdfReader, baos );
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			LogWriter.writeLog("Errore " + e.getMessage() );
			throw new Exception("Errore nella lettura del file. Il file potrebbe essere protetto.");
		} catch (IOException e) {
			//e.printStackTrace();
			LogWriter.writeLog("Errore " + e.getMessage() );
			throw new Exception("Errore nella lettura del file. Il file potrebbe essere protetto.");
		}
		
		//TODO: verificare questo dato
		//http://www.pdf-tools.com/pdf/validate-pdfa-online.aspx
		//pdfStamper.getWriter().setPDFXConformance(PdfWriter.PDFA1B);

		int totalPages = pdfReader.getNumberOfPages();
		LogWriter.writeLog("Numero totale di pagine " + totalPages);
		//ricavo le pagine da cui apporre il timbro
		TipoPagina tipoPagina = null;
		if( paginaTimbro!=null &&  paginaTimbro.getPagine()!=null ){
			// sono state specificate le pagine su cui apporre il timbro (lista pagine, intervallo da-a)
			if( paginaTimbro.getPagine().getPagina().size()>0 ){
				List<Integer> numeroPagine = paginaTimbro.getPagine().getPagina();
				//log.info("numeroPagine " + numeroPagine);
				for(Integer paginaDaTimbrare: numeroPagine ){
					//verifico se la pagina esiste nel pdf
					if( paginaDaTimbrare<=totalPages ){
						timbraPagina( paginaDaTimbrare, pdfStamper, codifica, testo, posizioneTimbro, rotazioneTimbro, timbroSingolo, testoIntestazione, posizioneIntestazione, posizioneTestoInChiaro, moreLines );
					}
				}
			} else if( paginaTimbro.getPagine().getPaginaDa()>0 && paginaTimbro.getPagine().getPaginaA()>0 ) {
				Integer paginaDa = paginaTimbro.getPagine().getPaginaDa();
				//log.info("paginaDa " + paginaDa);
				Integer paginaA = paginaTimbro.getPagine().getPaginaA();
				//log.info("paginaA " + paginaA);
				if( paginaA>0 && paginaDa>0 ){
					for(int i=paginaDa; i<=paginaA;i++){
						timbraPagina(i, pdfStamper, codifica, testo, posizioneTimbro, rotazioneTimbro, timbroSingolo, testoIntestazione, posizioneIntestazione, posizioneTestoInChiaro, moreLines );
					}
				}
			} 
		} else if( paginaTimbro!=null &&  paginaTimbro.getTipoPagina()!=null ){
			//è stato specificato il tipo di pagina (prima, ultima, tutte)
			tipoPagina = paginaTimbro.getTipoPagina();
			//log.info("tipoPagina " + tipoPagina );
			if( tipoPagina!=null && tipoPagina.equals( TipoPagina.PRIMA ) ){
				timbraPagina(1, pdfStamper, codifica, testo, posizioneTimbro, rotazioneTimbro, timbroSingolo, testoIntestazione, posizioneIntestazione, posizioneTestoInChiaro, moreLines );
			}
			if( tipoPagina!=null && tipoPagina.equals( TipoPagina.ULTIMA ) ){
				timbraPagina(totalPages, pdfStamper, codifica, testo, posizioneTimbro, rotazioneTimbro, timbroSingolo, testoIntestazione, posizioneIntestazione, posizioneTestoInChiaro, moreLines );
			}
			if( tipoPagina!=null && tipoPagina.equals( TipoPagina.TUTTE ) ){
				for(int i=1; i<=totalPages; i++)
					timbraPagina(i, pdfStamper, codifica, testo, posizioneTimbro, rotazioneTimbro, timbroSingolo, testoIntestazione, posizioneIntestazione, posizioneTestoInChiaro, moreLines );
			}
		} else {

			return null;
		}

		pdfStamper.getWriter().createXmpMetadata();
		pdfStamper.close();
		pdfReader.close();

		File directoryOutput = new File( outputFileLoc );
		if( !directoryOutput.exists() )
			directoryOutput.mkdir();
		File fileTimbrato = new File( outputFileLoc + "/" + fileOutputName );
		LogWriter.writeLog("Genero il file timbrato " + fileTimbrato.getPath(), false);
		FileOutputStream fileOutputStream = new FileOutputStream( fileTimbrato );
		IOUtils.write(baos.toByteArray(), fileOutputStream);
		baos.close();
		fileOutputStream.flush();
		fileOutputStream.close();
		return fileTimbrato;
	}
	
	private void timbraPagina(Integer paginaDaTimbrare, PdfStamper pdfStamper, CodificaTimbro codifica, String testo, 
			PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro, 
			boolean timbroSingolo, String testoIntestazione, PosizioneRispettoAlTimbro posizioneIntestazione,
			PosizioneRispettoAlTimbro posizioneTestoInChiaro, boolean moreLines)// { 
					throws DocumentException, IOException, Exception{
		
		LogWriter.writeLog("Timbro la pagina " + paginaDaTimbrare );
		//pdfStamper.setRotateContents( false );
		PdfContentByte over = pdfStamper.getOverContent( paginaDaTimbrare );
		
		BaseFont bf = BaseFont.createFont( FONT_NAME, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		over.setFontAndSize(bf, FONT_SIZE);
		
		float dimensioneSingoloCarattere = bf.getWidthPoint(testo.charAt(0), FONT_SIZE);
		float dimensioneTestoIntestazione = 0;
		if( testoIntestazione!=null) {
			dimensioneTestoIntestazione = bf.getWidthPoint(testoIntestazione, FONT_SIZE);
		}
		float dimensioneTestoInChiaro = bf.getWidthPoint(testo, FONT_SIZE);
		
		List<Image> imgs = new ArrayList<Image>();
		List<Float> posXImg = new ArrayList<Float>();
		List<Float> posYImg = new ArrayList<Float>();
		List<Float> posXIntestazione = new ArrayList<Float>();
		List<Float> posYIntestazione = new ArrayList<Float>();
		List<Float> posXTestoInChiaro = new ArrayList<Float>();
		List<Float> posYTestoInChiaro = new ArrayList<Float>();
		List<String> listaTestoIntestazione = new ArrayList<String>();
		List<String> listaTestoInChiaro = new ArrayList<String>();
		
		int alignText = 0;
		float orientationText = 0;
		
		LogWriter.writeLog("Genero il timbro dal testo " + testo );
		LogWriter.writeLog("codifica " + codifica );
		
		if( codifica.equals( CodificaTimbro.BARCODE_PDF_417) ){
			if( testo.length() < MAX_LENGHT_TIMBRO_PDF417) {
				imgs.add( addBarcodePdf417(testo ) );
			} else if( !timbroSingolo ){
				int numeroTimbri = testo.length()/ MAX_LENGHT_TIMBRO_PDF417;
				if( testo.length() % MAX_LENGHT_TIMBRO_PDF417 >0)
					numeroTimbri++;
				LogWriter.writeLog("numeroTimbri da applicare " + numeroTimbri);
				for(int i=0;i<numeroTimbri;i++){
					int indiceDa =  i * MAX_LENGHT_TIMBRO_PDF417;
 					int indiceA = indiceDa + MAX_LENGHT_TIMBRO_PDF417 - 1;
					if( indiceA>testo.length())
						indiceA = testo.length();
					String sottoTesto = testo.substring(indiceDa, indiceA);
					LogWriter.writeLog( "genero il timbro dal testo " + sottoTesto + ", di lunghezza " + sottoTesto.length() );
					imgs.add( addBarcodePdf417( sottoTesto ));
				}
			} else {
				throw new Exception("Testo troppo grande per un timbro singolo");
			}
		} else if( codifica.equals( CodificaTimbro.BARCODE_DATAMATRIX) ){
			if( testo.length() < MAX_LENGHT_TIMBRO_PDF417) {
				imgs.add( addBarcodeDatamatrix(testo) );
			} else if( !timbroSingolo ){
				int numeroTimbri = testo.length()/ MAX_LENGHT_TIMBRO_PDF417;
				if( testo.length() % MAX_LENGHT_TIMBRO_PDF417 >0)
					numeroTimbri++;
				LogWriter.writeLog("numeroTimbri da applicare " + numeroTimbri);
				for(int i=0;i<numeroTimbri;i++){
					int indiceDa =  i * MAX_LENGHT_TIMBRO_PDF417;
 					int indiceA = indiceDa + MAX_LENGHT_TIMBRO_PDF417 - 1;
					if( indiceA>testo.length())
						indiceA = testo.length();
					String sottoTesto = testo.substring(indiceDa, indiceA);
					LogWriter.writeLog( "genero il timbro dal testo " + sottoTesto + ", di lunghezza " + sottoTesto.length() );
					imgs.add( addBarcodeDatamatrix( sottoTesto ));
				}
			} else {
				throw new Exception("Testo troppo grande per un timbro singolo");
			}
		} else if( codifica.equals( CodificaTimbro.BARCODE_QR_CODE) ){
			if( testo.length() < MAX_LENGHT_TIMBRO_PDF417) {
				imgs.add( addBarcodeQRCode(testo) );
			} else if( !timbroSingolo ){
				int numeroTimbri = testo.length()/ MAX_LENGHT_TIMBRO_PDF417;
				if( testo.length() % MAX_LENGHT_TIMBRO_PDF417 >0)
					numeroTimbri++;
				LogWriter.writeLog("numeroTimbri da applicare " + numeroTimbri);
				for(int i=0;i<numeroTimbri;i++){
					int indiceDa =  i * MAX_LENGHT_TIMBRO_PDF417;
 					int indiceA = indiceDa + MAX_LENGHT_TIMBRO_PDF417 - 1;
					if( indiceA>testo.length())
						indiceA = testo.length();
					String sottoTesto = testo.substring(indiceDa, indiceA);
					LogWriter.writeLog( "genero il timbro dal testo " + sottoTesto + ", di lunghezza " + sottoTesto.length() );
					imgs.add( addBarcodeQRCode( sottoTesto ));
				}
			} else {
				throw new Exception("Testo troppo grande per un timbro singolo");
			}
		}
		
		float heightImgTotale = 0;
		float widthImgTotale = 0;
		
		for(int i=0; i<imgs.size();i++){
			Image img = imgs.get(i);
			
			//alto destra
			if( posizioneTimbro!=null && posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX )){
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE) ){
					if( pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==0 || pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==180 ){
						posXImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare)  .getWidth() - MARGINE_DX - img.getScaledHeight() );
						posYImg.add(pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getHeight() - MARGINE_SUP - widthImgTotale - img.getScaledWidth());
					} else {
						posXImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare)  .getHeight() - MARGINE_DX - img.getScaledHeight() );
						posYImg.add(pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getWidth() - MARGINE_SUP - widthImgTotale - img.getScaledWidth());
					}
				} else {
					if( pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==0 || pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==180 ){
						posXImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - widthImgTotale - img.getScaledWidth() );
						posYImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - img.getScaledHeight() );
					} else {
						posXImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_DX - widthImgTotale - img.getScaledWidth() );
						posYImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_SUP - img.getScaledHeight() );
					}
				}
				widthImgTotale += img.getWidth();
				heightImgTotale += img.getHeight();
			} 
			//alto sinistra
			else if( posizioneTimbro!=null && posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN )){
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE) ){
					if( pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==0 || pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==180 ){
						posXImg.add( MARGINE_SN );
						posYImg.add(pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getHeight() - MARGINE_SUP - widthImgTotale - img.getScaledWidth());
					} else {
						posXImg.add( MARGINE_SN );
						posYImg.add(pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getWidth() - MARGINE_SUP - widthImgTotale - img.getScaledWidth());
					}
				} else {
					if( pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==0 || pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==180 ){
						posXImg.add(MARGINE_SN + widthImgTotale );
						posYImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - img.getScaledHeight());
					} else {
						posXImg.add(MARGINE_SN + widthImgTotale );
						posYImg.add(pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_SUP - img.getScaledHeight());
					}
				}
				widthImgTotale += img.getWidth();
				heightImgTotale += img.getHeight();
			} 
			//basso destra
			else if( posizioneTimbro!=null && posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX )){
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE) ){
					if( pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==0 || pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==180 ){
						posXImg.add(pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getWidth() - img.getScaledHeight() - MARGINE_DX);
						posYImg.add(MARGINE_INF + widthImgTotale );
					} else {
						posXImg.add(pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getHeight() - img.getScaledHeight() - MARGINE_DX);
						posYImg.add(MARGINE_INF + widthImgTotale );
					}
				} else {
					if( pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==0 || pdfStamper.getReader().getPageRotation( paginaDaTimbrare )==180 ){
						posXImg.add( pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getWidth() - MARGINE_DX - widthImgTotale - img.getScaledWidth());
						posYImg.add( MARGINE_INF );
					} else {
						posXImg.add( pdfStamper.getReader().getPageSize( paginaDaTimbrare ).getHeight() - MARGINE_DX - widthImgTotale - img.getScaledWidth());
						posYImg.add( MARGINE_INF );
					}
				}
				widthImgTotale += img.getWidth();
				heightImgTotale += img.getHeight();
			} 
			//basso sinistra
			else if( posizioneTimbro!=null && posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN)){
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE) ){
					posXImg.add(MARGINE_SN );
					posYImg.add(MARGINE_INF + widthImgTotale);
				} else {
					posXImg.add(MARGINE_SN +  widthImgTotale );
					posYImg.add(MARGINE_INF);
				}
				widthImgTotale += img.getWidth();
				heightImgTotale += img.getHeight();
			}
		}
		LogWriter.writeLog("Coordinate X dei timbri " + posXImg);
		LogWriter.writeLog("Coordinate Y dei timbri " + posYImg);
		
		float widthPagina = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_SN - MARGINE_DX;
		float heighthPagina = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - MARGINE_INF;
		if( rotazioneTimbro.equals( TipoRotazione.ORIZZONTALE ) ){
			if( widthImgTotale>widthPagina){
				LogWriter.writeLog("I timbri non possono essere contenuti nella pagina. Non posso timbrare");
				throw new Exception("I timbri non possono essere contenuti nella pagina.");
			}
		} else if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
			if( widthImgTotale>heighthPagina){
				LogWriter.writeLog("I timbri non possono essere contenuti nella pagina. Non posso timbrare");
				throw new Exception("I timbri non possono essere contenuti nella pagina.");
			}
		} 
		
		for(int i=0; i<imgs.size();i++){
			Image img = imgs.get(i);
			if( rotazioneTimbro.equals( TipoRotazione.VERTICALE) ) {
				if( posizioneTimbro!=null && 
						(posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN))){
					img.setRotation((float)Math.PI / 2);
				} else if( posizioneTimbro!=null && (posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX))){
					img.setRotation(3*(float)Math.PI / 2);
				}
			}
				
			img.setAbsolutePosition( posXImg.get(i), posYImg.get(i) );
			over.addImage( img );
		} 
		//LogWriter.writeLog("img " + imgs);
				
		if( posizioneIntestazione!=null && testoIntestazione!=null ){
				
			if( posizioneIntestazione.equals( PosizioneRispettoAlTimbro.INLINEA ) ) {
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
					float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - widthImgTotale - MARGINE_SUP - MARGINE_INF - MARGINE_TIMBRO_INTESTAZIONE;
					int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
					int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
					orientationText = -90;
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN) ){
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE));
								posYIntestazione.add( posYImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) );
							posYIntestazione.add( posYImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX) ){
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + imgs.get(0).getHeight() - ((i)*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE);
								posYIntestazione.add( posYImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE);
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) + imgs.get(0).getHeight() - MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(imgs.size()-1)  - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN) ){
						orientationText = 90;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + ((i)*MARGINE_TIMBRO_INTESTAZIONE) + MARGINE_TIMBRO_INTESTAZIONE);
								posYIntestazione.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) + MARGINE_TIMBRO_INTESTAZIONE);
							posYIntestazione.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					}  else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX) ){
						orientationText = 90;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + imgs.get(0).getHeight() - ((numeroRighe-i)*MARGINE_TIMBRO_INTESTAZIONE) );
								posYIntestazione.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) + imgs.get(0).getHeight() - MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					} 
				} else {
					float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - widthImgTotale - MARGINE_DX - MARGINE_SN - MARGINE_TIMBRO_INTESTAZIONE ;
					int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
					int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN )){
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE);
								posYIntestazione.add( posYImg.get(0) + imgs.get(imgs.size()-1).getScaledHeight() - (i*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(posXImg.size()-1) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(posYImg.size()-1) + imgs.get(imgs.size()-1).getScaledHeight() - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX )){
						alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE);
								posYIntestazione.add( posYImg.get(0) + imgs.get(imgs.size()-1).getScaledHeight() - (i*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(posXImg.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(posYImg.size()-1) + imgs.get(imgs.size()-1).getScaledHeight() - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX )){
						alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE);
								posYIntestazione.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE)  );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(posXImg.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(posYImg.size()-1) );
						}
					} if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN )){
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE);
								posYIntestazione.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE)  );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(posXImg.size()-1) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(posYImg.size()-1) );
						}
					}
				}
			}
			if( posizioneIntestazione.equals( PosizioneRispettoAlTimbro.SOTTO ) ) {
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
					float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - MARGINE_INF;
					int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
					int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN)){
						alignText = PdfContentByte.ALIGN_RIGHT;
						orientationText = +90;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + imgs.get( 0).getScaledWidth() + ((i)*MARGINE_TIMBRO_INTESTAZIONE) + MARGINE_TIMBRO_INTESTAZIONE );
								posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) + imgs.get(0).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX)){
						orientationText = -90;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) - ((i)*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN)){
						orientationText = +90;
						//alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) + imgs.get( 0).getScaledWidth() + ((i)*MARGINE_TIMBRO_INTESTAZIONE) + MARGINE_TIMBRO_INTESTAZIONE );
								posYIntestazione.add( posYImg.get(0) );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) + imgs.get( 0).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(0) );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX)){
						orientationText = -90;
						alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posXIntestazione.add( posXImg.get(0) - ((i)*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								posYIntestazione.add( posYImg.get(0) );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione );
							posXIntestazione.add( posXImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posYIntestazione.add( posYImg.get(0)  ) ;
						}
					}
				} else {
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posYIntestazione.add( posYImg.get(0) - (i*10) - MARGINE_TIMBRO_INTESTAZIONE );
								posXIntestazione.add( posXImg.get(0) );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posYIntestazione.add( posYImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posXIntestazione.add( posXImg.get(0) );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							alignText = PdfContentByte.ALIGN_RIGHT;
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posYIntestazione.add( posYImg.get(0) - (i*10) - MARGINE_TIMBRO_INTESTAZIONE );
								posXIntestazione.add( posXImg.get(0) + imgs.get(0).getScaledWidth());
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							alignText = PdfContentByte.ALIGN_RIGHT;
							posYIntestazione.add( posYImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posXIntestazione.add( posXImg.get(0) + imgs.get(0).getScaledWidth());
						}
					}
				}
			}
			if( posizioneIntestazione.equals( PosizioneRispettoAlTimbro.SOPRA ) ) {
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN)){
						listaTestoIntestazione.add(testoIntestazione );
						posXIntestazione.add( posXImg.get(posXImg.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
						posYIntestazione.add( posYImg.get(0) );
					} else {
						listaTestoIntestazione.add(testoIntestazione );
						posXIntestazione.add( posXImg.get(0) + imgs.get(0).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
						posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight() ) ;
					}
				} else {
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posYIntestazione.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
								posXIntestazione.add( posXImg.get(0) );
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(0) );
							posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testoIntestazione.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoIntestazione) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							alignText = PdfContentByte.ALIGN_RIGHT;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testoIntestazione.length())
									endIndex = testoIntestazione.length();
								listaTestoIntestazione.add(testoIntestazione.substring(beginIndex, endIndex));
								posYIntestazione.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
								posXIntestazione.add( posXImg.get(0) + imgs.get(0).getScaledWidth());
								if( endIndex == testoIntestazione.length())
									break;
							}
						} else {
							listaTestoIntestazione.add(testoIntestazione);
							posXIntestazione.add( posXImg.get(0) + imgs.get(0).getScaledWidth() );
							alignText = PdfContentByte.ALIGN_RIGHT;
							posYIntestazione.add( posYImg.get(0) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					}  
				}
			}
			LogWriter.writeLog("Coordinate X Intestazione " + posXIntestazione);
			LogWriter.writeLog("Coordinate Y Intestazione " + posYIntestazione);
			LogWriter.writeLog("Testi Intestazione " + listaTestoIntestazione);
			
			for(int i=0; i<listaTestoIntestazione.size();i++){
				String s = listaTestoIntestazione.get(i);
				over.beginText();
				over.showTextAligned( alignText , s, posXIntestazione.get(i), posYIntestazione.get(i), orientationText );
				over.endText();
			}
		}
		
		if( posizioneTestoInChiaro!=null ){
			
			if( posizioneTestoInChiaro.equals( PosizioneRispettoAlTimbro.INLINEA ) ) {
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
					float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - widthImgTotale - MARGINE_SUP - MARGINE_INF - MARGINE_TIMBRO_INTESTAZIONE;
					int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
					int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
					orientationText = -90;
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN) ){
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE));
								posYTestoInChiaro.add( posYImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo );
							posXTestoInChiaro.add( posXImg.get(0) );
							posYTestoInChiaro.add( posYImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX) ){
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getHeight() - ((i)*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE);
								posYTestoInChiaro.add( posYImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE);
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo );
							posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getHeight() - MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(imgs.size()-1)  - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN) ){
						orientationText = 90;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + ((i)*MARGINE_TIMBRO_INTESTAZIONE) + MARGINE_TIMBRO_INTESTAZIONE);
								posYTestoInChiaro.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo );
							posXTestoInChiaro.add( posXImg.get(0) + MARGINE_TIMBRO_INTESTAZIONE);
							posYTestoInChiaro.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					}  else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX) ){
						orientationText = 90;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getHeight() - ((numeroRighe-i)*MARGINE_TIMBRO_INTESTAZIONE) );
								posYTestoInChiaro.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add( testo );
							posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getHeight() - MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getWidth() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					} 
				} else {
					float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - widthImgTotale - MARGINE_DX - MARGINE_SN - MARGINE_TIMBRO_INTESTAZIONE ;
					int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
					int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN )){
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE);
								posYTestoInChiaro.add( posYImg.get(0) + imgs.get(imgs.size()-1).getScaledHeight() - (i*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(posXImg.size()-1) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(posYImg.size()-1) + imgs.get(imgs.size()-1).getScaledHeight() - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX )){
						alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE);
								posYTestoInChiaro.add( posYImg.get(0) + imgs.get(imgs.size()-1).getScaledHeight() - (i*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(posXImg.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(posYImg.size()-1) + imgs.get(imgs.size()-1).getScaledHeight() - MARGINE_TIMBRO_INTESTAZIONE );
						}
					} if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX )){
						alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(imgs.size()-1) - MARGINE_TIMBRO_INTESTAZIONE);
								posYTestoInChiaro.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE)  );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(posXImg.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(posYImg.size()-1) );
						}
					} if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN )){
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(imgs.size()-1) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE);
								posYTestoInChiaro.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE)  );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(posXImg.size()-1) + imgs.get(imgs.size()-1).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(posYImg.size()-1) );
						}
					}
				}
			}
			if( posizioneTestoInChiaro.equals( PosizioneRispettoAlTimbro.SOTTO ) ) {
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
					float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getHeight() - MARGINE_SUP - MARGINE_INF;
					int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
					int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN)){
						alignText = PdfContentByte.ALIGN_RIGHT;
						orientationText = +90;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get( 0).getScaledWidth() + ((i)*MARGINE_TIMBRO_INTESTAZIONE) + MARGINE_TIMBRO_INTESTAZIONE );
								posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX)){
						orientationText = -90;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) - ((i)*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight());
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN)){
						orientationText = +90;
						//alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get( 0).getScaledWidth() + ((i)*MARGINE_TIMBRO_INTESTAZIONE) + MARGINE_TIMBRO_INTESTAZIONE );
								posYTestoInChiaro.add( posYImg.get(0) );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo );
							posXTestoInChiaro.add( posXImg.get(0) + imgs.get( 0).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(0) );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX)){
						orientationText = -90;
						alignText = PdfContentByte.ALIGN_RIGHT;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posXTestoInChiaro.add( posXImg.get(0) - ((i)*MARGINE_TIMBRO_INTESTAZIONE) - MARGINE_TIMBRO_INTESTAZIONE );
								posYTestoInChiaro.add( posYImg.get(0) );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo );
							posXTestoInChiaro.add( posXImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posYTestoInChiaro.add( posYImg.get(0)  ) ;
						}
					}
				} else {
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posYTestoInChiaro.add( posYImg.get(0) - (i*10) - MARGINE_TIMBRO_INTESTAZIONE );
								posXTestoInChiaro.add( posXImg.get(0) );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posYTestoInChiaro.add( posYImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posXTestoInChiaro.add( posXImg.get(0) );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							alignText = PdfContentByte.ALIGN_RIGHT;
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posYTestoInChiaro.add( posYImg.get(0) - (i*10) - MARGINE_TIMBRO_INTESTAZIONE );
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getScaledWidth());
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							alignText = PdfContentByte.ALIGN_RIGHT;
							posYTestoInChiaro.add( posYImg.get(0) - MARGINE_TIMBRO_INTESTAZIONE );
							posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getScaledWidth());
						}
					}
				}
			}
			if( posizioneTestoInChiaro.equals( PosizioneRispettoAlTimbro.SOPRA ) ) {
				if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN)){
						listaTestoInChiaro.add(testo );
						posXTestoInChiaro.add( posXImg.get(posXImg.size()-1) - MARGINE_TIMBRO_INTESTAZIONE );
						posYTestoInChiaro.add( posYImg.get(0) );
					} else {
						listaTestoInChiaro.add(testo );
						posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getScaledWidth() + MARGINE_TIMBRO_INTESTAZIONE );
						posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight() ) ;
					}
				} else {
					if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posYTestoInChiaro.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
								posXTestoInChiaro.add( posXImg.get(0) );
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(0) );
							posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					} else if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX )){
						float spazioDisponibile = pdfStamper.getReader().getPageSize(paginaDaTimbrare).getWidth() - MARGINE_DX - MARGINE_SN;
						int caratteriPerRiga = (int)(spazioDisponibile/dimensioneSingoloCarattere);
						int numeroRighe = (int) (testo.length()/caratteriPerRiga) +1;
						if( moreLines && spazioDisponibile<dimensioneTestoInChiaro) {
							LogWriter.writeLog("Il testo verrà diviso su più righe per mancanza di spazio");
							int beginIndex = 0;
							int endIndex = 0;
							alignText = PdfContentByte.ALIGN_RIGHT;
							for(int i=0;i<numeroRighe;i++){
								beginIndex =(i)*caratteriPerRiga;
								endIndex = (i+1)*caratteriPerRiga;
								if( endIndex>=testo.length())
									endIndex = testo.length();
								listaTestoInChiaro.add(testo.substring(beginIndex, endIndex));
								posYTestoInChiaro.add( posYImg.get(0) + ((numeroRighe-i-1)*MARGINE_TIMBRO_INTESTAZIONE) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
								posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getScaledWidth());
								if( endIndex == testo.length())
									break;
							}
						} else {
							listaTestoInChiaro.add(testo);
							posXTestoInChiaro.add( posXImg.get(0) + imgs.get(0).getScaledWidth() );
							alignText = PdfContentByte.ALIGN_RIGHT;
							posYTestoInChiaro.add( posYImg.get(0) + imgs.get(0).getScaledHeight() + MARGINE_TIMBRO_INTESTAZIONE );
						}
					}  
				}
			}
			
			LogWriter.writeLog("Coordinate X Testo In Chiaro " + posXTestoInChiaro);
			LogWriter.writeLog("Coordinate Y Testo In Chiaro " + posYTestoInChiaro);
			LogWriter.writeLog("Testi In Chiaro " + listaTestoInChiaro);
			
			for(int i=0; i<listaTestoInChiaro.size();i++){
				String s = listaTestoInChiaro.get(i);
				over.beginText();
				over.showTextAligned( alignText , s, posXTestoInChiaro.get(i), posYTestoInChiaro.get(i), orientationText );
				over.endText();
			}
		}

	}

	private static Image addBarcodePdf417(String testo) throws BadElementException{
		BarcodePDF417 pdf417 = new BarcodePDF417();
		pdf417.setText(testo);
		//pdf417.setAspectRatio(.25f);
		Image img = pdf417.getImage();
//		img.scalePercent(50, 50 * pdf417.getYHeight());
		
		LogWriter.writeLog("genero l'immagine per il timbro con codifica BarcodeDatamatrix  " + img );
		return img;
	}

	private static Image addBarcodeDatamatrix(String testo) throws UnsupportedEncodingException, BadElementException {
		BarcodeDatamatrix datamatrix = new BarcodeDatamatrix();
		datamatrix.generate(testo);
		Image img = datamatrix.createImage();
		//img.scalePercent(50, 50);
		LogWriter.writeLog("genero l'immagine per il timbro con codifica BarcodeDatamatrix  " + img );
		return img;
	}

	private Image addBarcodeQRCode(String testo) throws BadElementException  {
		BarcodeQRCode qrcode = new BarcodeQRCode(testo, 1, 1, null);
		Image img = qrcode.getImage();
		LogWriter.writeLog("genero l'immagine per il timbro con codifica BarcodeDatamatrix  " + img );
		return img;
	}
	
	public static boolean validaPosizioneTesto(PosizioneTimbroNellaPagina posizioneTimbro, TipoRotazione rotazioneTimbro, PosizioneRispettoAlTimbro posizioneTesto){
		if( posizioneTesto.equals( PosizioneRispettoAlTimbro.INLINEA ) )
			return true;
		else if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOPRA ) ) {
			if( rotazioneTimbro.equals( TipoRotazione.ORIZZONTALE ) ){
				if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_DX ) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.BASSO_SN ))
					return true;
				else 
					return false;
			} else 
				return false;
		} else if( posizioneTesto.equals( PosizioneRispettoAlTimbro.SOTTO ) ){
			if( rotazioneTimbro.equals( TipoRotazione.VERTICALE ) ){
				return true;
			} else {
				if( posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_DX ) || posizioneTimbro.equals( PosizioneTimbroNellaPagina.ALTO_SN ))
					return true;
				else 
					return false;
			}
		}
		return false;
	}
	
	public int getFONT_SIZE() {
		return FONT_SIZE;
	}

	public void setFONT_SIZE(int fONT_SIZE) {
		FONT_SIZE = fONT_SIZE;
	}

	public int getMAX_LENGHT_TIMBRO_PDF417() {
		return MAX_LENGHT_TIMBRO_PDF417;
	}

	public void setMAX_LENGHT_TIMBRO_PDF417(int mAX_LENGHT_TIMBRO_PDF417) {
		MAX_LENGHT_TIMBRO_PDF417 = mAX_LENGHT_TIMBRO_PDF417;
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

	public float getMARGINE_TIMBRO_INTESTAZIONE() {
		return MARGINE_TIMBRO_INTESTAZIONE;
	}

	public void setMARGINE_TIMBRO_INTESTAZIONE(float mARGINE_TIMBRO_INTESTAZIONE) {
		MARGINE_TIMBRO_INTESTAZIONE = mARGINE_TIMBRO_INTESTAZIONE;
	}

	public static void main(String[] args) {
		File f = new File("/sviluppo/eclipse-indigo/workspace/jPedal/samplefiles/Carta identità  Andrea Campello.PDF");
		TimbroUtils timbroUtils = new TimbroUtils( 1850,8,20,20,20,20, 10,BaseFont.COURIER );
		PaginaTimbro paginaTimbro = new PaginaTimbro();
		Pagine pagine = new Pagine();
		pagine.getPagina().add( 1 );
		paginaTimbro.setPagine(pagine);
		
		try {
			File fileTimbrato = timbroUtils.timbraPdf( f, 
					CodificaTimbro.BARCODE_PDF_417, "ciao", paginaTimbro, 
					PosizioneTimbroNellaPagina.BASSO_DX, TipoRotazione.ORIZZONTALE, 
					PosizioneRispettoAlTimbro.SOTTO, 
					true, "Firmato digitalmente da", PosizioneRispettoAlTimbro.SOTTO, 
					f.getParentFile() + "/output/", "test.pdf", true);
			System.out.println(fileTimbrato);
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

	}

}
