/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.DatiRegistrazioniOut;
import it.eng.auriga.ui.module.layout.shared.bean.StampaRegProtConfig;

import it.eng.document.NumeroColonna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FileUtil;
import it.eng.utility.XmlUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/* Il TipoReport puo' essere :
      -- PROTOCOLLO se sono registrazioni di protocollo generale.
	  -- REPERTORIO_ATTI se sono sono registrazioni di repertori atti.
	  -- REPERTORIO se sono sono registrazioni di qualsiasi altro repertorio.
	  -- PROPOSTE_ATTO se sono sono registrazioni di proposte atti.																	
 */

public class StampaRegProt {
	
	private static StampaRegProt instance;
	
	private static Logger mLogger = Logger.getLogger(StampaRegProt.class);
	
	public static StampaRegProt getInstance(){
		if (instance == null){
			instance = new StampaRegProt();
		}
		return instance;
	}
	
	public void stampaRegProt(File file, List<DatiRegistrazioniOut> datiRegistrazioniList, String header, String footer) throws Exception {
		stampaRegProt(file, datiRegistrazioniList, header, footer, 0, "PROTOCOLLO");
	}

	public void stampaRegProt(File file, List<DatiRegistrazioniOut> datiRegistrazioniList, String header, String footer, int startPageNumber) throws Exception {
		stampaRegProt(file, datiRegistrazioniList, header, footer, startPageNumber, "PROTOCOLLO");
	}

	
	public void stampaRegProt(File file, List<DatiRegistrazioniOut> datiRegistrazioniList, String header, String footer, int startPageNumber, String tipoReport) throws Exception {
		
		PdfPTable table = createTableDatiRegistrazioni(datiRegistrazioniList,tipoReport);
		
		Document document = new Document(PageSize.A3.rotate(), 10, 10, 25, 25);		
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFXNONE);
		HeaderFooter event = new HeaderFooter(header, footer, startPageNumber);
		writer.setPageEvent(event);
		 
		document.open();
								
	    document.add(table);		   
        
	    writer.createXmpMetadata();
		document.close();						
	}
	
	public void stampaRegProtMassive(File file, List<File> fileDatiRegList, String header, String footer, String tipoReport) throws Exception {		
		
		Document document = new Document(PageSize.A3.rotate(), 10, 10, 25, 25);		
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setPDFXConformance(PdfWriter.PDFXNONE);
		HeaderFooter event = new HeaderFooter(header, footer, 0);
		writer.setPageEvent(event);
		 
		document.open();
		
		boolean skipFirstHeader = false;
		if(fileDatiRegList != null && fileDatiRegList.size() > 0) {
			for(int i = 0; i < fileDatiRegList.size(); i++) {
				String datiRegistrazioniXml = FileUtils.readFileToString(fileDatiRegList.get(i));
				
				List<DatiRegistrazioniOut> datiRegistrazioniList = new ArrayList<DatiRegistrazioniOut>();   	        
				StringReader sr = new StringReader(datiRegistrazioniXml);
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if(lista != null) {
					for (int r = 0; r < lista.getRiga().size(); r++) {
							Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(r));																	
							DatiRegistrazioniOut datiReg = new DatiRegistrazioniOut();	        								
							datiReg.setDataOraReg(v.get(0)); 				//col.  1  - Data e ora prot.
							datiReg.setNroReg(v.get(1)); 					//col.  2  - N° prot.
							datiReg.setTipoReg(v.get(2)); 					//col.  3  - Tipo
							datiReg.setUtenteUoReg(v.get(3)); 				//col.  4  - Effettuato da						
							datiReg.setEstremiRegEmergenza(v.get(4)); 		//col.  5  - Prot. emergenza
							datiReg.setMittenti(v.get(5)); 					//col.  6  - Mittente/i
							datiReg.setDestinatari(v.get(6)); 				//col.  7  - Destinatario/i
							datiReg.setOggetto(v.get(7)); 					//col.  8  - Oggetto
							datiReg.setClassificheFascicoli(v.get(8)); 		//col.  9  - Fascicolo/i  
							datiReg.setFileAssociati(v.get(9)); 			//col.  10 - Impronte dei file associati
							datiReg.setAssegnatari(v.get(10)); 				//col.  11 - Assegnatario/i della registrazione
							datiReg.setFlgAnnullata(v.get(11)); 			//col.  12 - Prot. annullato
							datiReg.setDataOraAnn(v.get(12)); 				//col.  13 - Data e ora di annullamento della registrazione
							datiReg.setEstremiAttoAnnReg(v.get(13)); 		//col.  14 - Prot. ricevuto
							datiReg.setFlgRegConDatiAnn(v.get(14)); 		//col.  15 - Dati annullati
							datiReg.setEstremiProtRicevuto(v.get(15)); 		//col.  16 - Prot. ricevuto	
							datiReg.setNrAllegati(v.get(16)); 				//col.  17 - Nr.allegati 
							datiReg.setUfficioProponente(v.get(17));        //col.  18: Ufficio proponente (se registro di proposta atto/repertorio atti)
							datiReg.setStatoProposta(v.get(18));            //col.  19: Stato della proposta di atto: in iter, archiviata, approvata, pubblicata (se registro di proposta atto)
							datiReg.setEstremiRepertorio(v.get(19));        //col.  20: Estremi di repertorio corrispondenti al n.ro di proposta (se registro di proposta atto)
							datiReg.setEstremiProposta(v.get(20));          //col.  21: Estremi di proposta corrispondenti al n.ro di repertorio (se registro di repertorio atti)
							datiReg.setEstremiProtocolloGenerale(v.get(21));//col.  22: Estremi di protocollo generale corrispondenti (se registro diverso dal Prot. Gen.)
							datiReg.setPeriodoPubblicazione(v.get(22));     //col.  23: Periodo/i di pubblicazione (se registro di proposta atto/repertorio atti)
							datiReg.setDataAdozione(v.get(23));             //col.  24: Data di adozione (la data dell¿ultima firma) (se registro di proposta atto/repertorio atti)

					       	datiRegistrazioniList.add(datiReg);
				    }		
				}
				document.add(createTableDatiRegistrazioni(datiRegistrazioniList, skipFirstHeader, tipoReport));		        	        	        
		        skipFirstHeader = writer.getVerticalPosition(false) < document.top();	        	
			}
		} else {
			document.add(new Paragraph(" "));
		}
	    writer.createXmpMetadata();
		document.close();						
	}
	
	public PdfPTable createTableDatiRegistrazioni(List<DatiRegistrazioniOut> datiRegistrazioniList) throws Exception {
		return createTableDatiRegistrazioni(datiRegistrazioniList, false, "PROTOCOLLO");
	}

	public PdfPTable createTableDatiRegistrazioni(List<DatiRegistrazioniOut> datiRegistrazioniList,String tipoReport) throws Exception {
		return createTableDatiRegistrazioni(datiRegistrazioniList, false, tipoReport);
	}
	
	public PdfPTable createTableDatiRegistrazioni(List<DatiRegistrazioniOut> datiRegistrazioniList, boolean skipFirstHeader, String tipoReport) throws Exception {

		StampaRegProtConfig config = (StampaRegProtConfig)SpringAppContext.getContext().getBean("StampaRegProtConfig");
		
		if (tipoReport == null )
			throw new Exception("Il tipo di report e' sconosciuto."); 
		
		if (tipoReport.equalsIgnoreCase("PROTOCOLLO")){
			config = (StampaRegProtConfig)SpringAppContext.getContext().getBean("StampaRegProtConfig");
		}
		else if(tipoReport.equalsIgnoreCase("REPERTORIO_ATTI")){
			config = (StampaRegProtConfig)SpringAppContext.getContext().getBean("StampaRegRepertoriAttiConfig");
		}
        else if(tipoReport.equalsIgnoreCase("REPERTORIO")){
        	config = (StampaRegProtConfig)SpringAppContext.getContext().getBean("StampaRegRepertoriConfig");
		}
        else if(tipoReport.equalsIgnoreCase("PROPOSTE_ATTO")){
        	config = (StampaRegProtConfig)SpringAppContext.getContext().getBean("StampaRegProposteAttiConfig");			
		}
        else{
        	throw new Exception("Il tipo di report e' sconosciuto."); 
        }
		
//		int[] columnWidth = new int[config.getColsToPrint().length];
		float[] configPercentageWidth = new float[config.getColsToPrint().length];
		
		PdfPTable table = new PdfPTable(config.getColsToPrint().length);
		table.setWidthPercentage(100);			
		
		//Setto le intestazioni
		for(int c = 0;  c < config.getColsToPrint().length; c++){
			PdfPCell cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(config.getColsToPrint()[c].getIntestazione()), new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
			cell.setBackgroundColor(new BaseColor(0xC0, 0xC0, 0xC0));
			table.addCell(cell);			
//			columnWidth[c] = config.getColsToPrint()[c].getIntestazione() != null ? config.getColsToPrint()[c].getIntestazione().length() : 0;
			configPercentageWidth[c] = config.getColsToPrint()[c].getPercentageWidth();
		}		
		table.setHeaderRows(1);		
		table.setSkipFirstHeader(skipFirstHeader);		
		
		if(datiRegistrazioniList != null && datiRegistrazioniList.size() > 0) {
			//Scrivo le righe
			for (DatiRegistrazioniOut datiReg : datiRegistrazioniList) {			
				 for (int c = 0; c < config.getColsToPrint().length; c++) {				    	 
					String value = null;
					for (Field lField : DatiRegistrazioniOut.class.getDeclaredFields()){
						NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
						if (lNumeroColonna!=null){
							int numCol = new Integer(lNumeroColonna.numero()).intValue();
							if(numCol == config.getColsToPrint()[c].getNroColonna().intValue()) {
								value = BeanUtilsBean2.getInstance().getProperty(datiReg, lField.getName());
							}						
						}
					}		    	 				
					if(value == null){
						value = "";
					}
//					int length = value.length();
//					int previusLength = columnWidth[c];
//					if(previusLength<=length){
//						columnWidth[c] = new Integer(length).intValue();
//					}							
					if(config.getColsToPrint()[c].getNroColonna().intValue() == 10){						
						PdfPCell lPdfPCell = new PdfPCell();
						if(StringUtils.isNotBlank(value)) {
							BarcodePDF417 barcode = new BarcodePDF417();
							barcode.setText(value);
							Image img = barcode.getImage();										
							lPdfPCell.addElement(img);
						}
						lPdfPCell.setPadding(10);					
						table.addCell(lPdfPCell);
//						columnWidth[c] = 120;
					} else	{										
						table.addCell(new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(value), new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL))));
					}						
				}				
			}
		}
		
//		//Calcolo le larghezze
//		int maxLength = 0;
//		for(int i = 0; i < columnWidth.length; i++){
//			maxLength += columnWidth[i];
//		}
//		float[] percentageColumnWidth = new float[columnWidth.length]; 
//		for(int i = 0; i < columnWidth.length; i++){
//			percentageColumnWidth[i] = (new Float(((double)columnWidth[i]/(double)maxLength)*100)).floatValue();
//		}
//		table.setTotalWidth(percentageColumnWidth);	
		
		//Calcolo le larghezze
		table.setTotalWidth(configPercentageWidth);
		
		return table;
	}

	static class HeaderFooter extends PdfPageEventHelper {

		private int pageNumber;
		private String header;
		private String footer;
		
		public HeaderFooter(String pHeader, String pFooter, int pStartPageNumber){
			pageNumber = pStartPageNumber > 0 ? pStartPageNumber : 0;
			header = pHeader;
			footer = pFooter;
		}		
		
		@Override
		public void onEndPage (PdfWriter writer, Document document) {
            pageNumber++;
            Font font = new Font(FontFamily.COURIER, 8, Font.NORMAL);
           	ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase(header, font),
                    10, document.top() + 10, 0);
        	ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase(String.format("Pag. %d", pageNumber), font),
                    document.left() + document.right() - 10, document.bottom() - 15, 0);        	                       
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, new Phrase(footer, font),
                    10, document.bottom() - 15, 0);                      
        }
                
    }

	public static void concatFiles(List<File> fileList, File fileOutput) throws Exception {
		if (fileList.size() > 0) {
			InputStream isReader = null;
			FileOutputStream osReader = null;
			try {
				isReader = new FileInputStream(fileList.get(0));
				PdfReader reader = new PdfReader(isReader);
				Document document = new Document(reader.getPageSizeWithRotation(1));
				osReader = new FileOutputStream(fileOutput) ;
				PdfCopy cp = new PdfCopy(document,osReader);
				document.open();
				for (File file : fileList ) {
					InputStream isReaderItem = new FileInputStream(file);
		            PdfReader prItem = new PdfReader(isReaderItem);
		            for (int k = 1; k <= prItem.getNumberOfPages(); ++k) {
		                cp.addPage(cp.getImportedPage(prItem, k));
		            }
		            cp.freeReader(prItem);
		            if(isReaderItem != null) {
		            	try {
		            		isReaderItem.close(); 
						} catch (Exception e) {}
		            }
				}
			    cp.close();
			    document.close();
			} finally {
				if(isReader != null) {
					try {
						isReader.close(); 
					} catch (Exception e) {}
				}
				if(osReader != null) {
					try {
						osReader.close(); 
					} catch (Exception e) {}
				}
			}
		} else{             
		    throw new Exception("La lista dei pdf da concatenare è vuota");        
		}		
	}
	
	public static void testStampa () {		
		String datiRegistrazioniXml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><Lista><Riga><Colonna Nro=\"1\">25/11/2013 04:59:53</Colonna><Colonna Nro=\"2\">0001277</Colonna><Colonna Nro=\"3\">U</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"7\">Corte d`Appello di CAGLIARI; Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"8\">prova file con nome lungo</Colonna><Colonna Nro=\"10\">SHA-256 base64 2KsJKvvA4g7jmv8JyTNN5ZkZQsCwz8OOWv8fg2SKI2w=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 10:16:48</Colonna><Colonna Nro=\"2\">0001278</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test di vale</Colonna><Colonna Nro=\"10\">SHA-256 base64 zc/KiUzh/hlCQ8H8fivfkKHA2DOZEqFlSB4Mwgrwwbo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5728 del 15/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 10:34:12</Colonna><Colonna Nro=\"2\">0001279</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">4a prova -solo file primario</Colonna><Colonna Nro=\"10\">SHA-256 base64 j+kac+HZh78jFDQJ9JN1jJIgjQyMlkMsCEBXLLhAPCc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5734 del 16/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 11:37:00</Colonna><Colonna Nro=\"2\">0001280</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">paolo sas</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test scanner</Colonna><Colonna Nro=\"10\">SHA-256 base64 m23Bqv8S/x/Ls6j2bG5/mJYMQYBMOyqVf+/3FgoeWvM=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 11:50:02</Colonna><Colonna Nro=\"2\">0001281</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">paolo sas</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test allegato scan</Colonna><Colonna Nro=\"10\">SHA-256 base64 AcxyzQxGcytDdhx6I15YBLwiWplVSorW+MDxQqZrePo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:15:46</Colonna><Colonna Nro=\"2\">0001282</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">stefanel</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 OOlScLWq7lRV3t+90KPx6ic0VnRjO7P//85AQSBO6gA=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:16:23</Colonna><Colonna Nro=\"2\">0001283</Colonna><Colonna Nro=\"3\">U</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"7\">Procura della Repubblica presso il Tribunale di MODENA</Colonna><Colonna Nro=\"8\">test vale</Colonna><Colonna Nro=\"10\">SHA-256 base64 jFm/HlifEqSdvjHu6i9RfJSk9WBvt3PmLwM5rgTT7n8=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:17:23</Colonna><Colonna Nro=\"2\">0001284</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:17:35</Colonna><Colonna Nro=\"2\">0001285</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test per Jacopo -1a</Colonna><Colonna Nro=\"10\">SHA-256 base64 zc/KiUzh/hlCQ8H8fivfkKHA2DOZEqFlSB4Mwgrwwbo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5737 del 25/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:22:05</Colonna><Colonna Nro=\"2\">0001286</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 3BNmVDkpA2/Z1+44ZDLzVOEdlGFmco/mBFax3e1DR1A=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:45:49</Colonna><Colonna Nro=\"2\">0001287</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 xvtcNOfvuw6aoIOUm+28eLihHAEBvLvU1hOIMl2NVo8=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:49:09</Colonna><Colonna Nro=\"2\">0001288</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 14:02:16</Colonna><Colonna Nro=\"2\">0001289</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 cWkLpbfIIwDbeVlYpbD42Z26+hoHkb8pXvOw3ByMPtg=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:10:23</Colonna><Colonna Nro=\"2\">0001290</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 V6bV8iVqFV50hEkBA+SM+3PUVawE49eHRZGqPE1OYew=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:15:22</Colonna><Colonna Nro=\"2\">0001291</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 xAKywmR4BCBqk2kvwO9qdWIFTU59K3pSG5QkKarFvSk=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:35:18</Colonna><Colonna Nro=\"2\">0001292</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 coRwu8BFTGRoN0FDlUm385TlVCRfu/X1Z4PpIwrO8fc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:58:41</Colonna><Colonna Nro=\"2\">0001293</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test 4 per jacaopo</Colonna><Colonna Nro=\"10\">SHA-256 base64 j+kac+HZh78jFDQJ9JN1jJIgjQyMlkMsCEBXLLhAPCc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5740 del 25/11/2013</Colonna></Riga></Lista>";
		try {
			List<DatiRegistrazioniOut> datiRegistrazioniList = new ArrayList<DatiRegistrazioniOut>();   	        
			StringReader sr = new StringReader(datiRegistrazioniXml);
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
					DatiRegistrazioniOut datiReg = new DatiRegistrazioniOut();	        								
					datiReg.setDataOraReg(v.get(0)); //col. 1
					datiReg.setNroReg(v.get(1)); //col.  2   
					datiReg.setTipoReg(v.get(2)); //col.  3
					datiReg.setUtenteUoReg(v.get(3)); //col.  4   						
					datiReg.setEstremiRegEmergenza(v.get(4)); //col.  5   
					datiReg.setMittenti(v.get(5)); //col.  6
					datiReg.setDestinatari(v.get(6)); //col.  7   
					datiReg.setOggetto(v.get(7)); //col.  8
					datiReg.setClassificheFascicoli(v.get(8)); //col.  9   
					datiReg.setFileAssociati(v.get(9)); //col.  10
					datiReg.setAssegnatari(v.get(10)); //col.  11 
					datiReg.setFlgAnnullata(v.get(11)); //col.  12
					datiReg.setDataOraAnn(v.get(12)); //col. 13
					datiReg.setEstremiAttoAnnReg(v.get(13)); //col.  14
					datiReg.setFlgRegConDatiAnn(v.get(14)); //col.  15
					datiReg.setEstremiProtRicevuto(v.get(15)); //col.  16	
					datiReg.setNrAllegati(v.get(16)); 				//col.  17 - Nr.allegati 
					datiReg.setUfficioProponente(v.get(17));        //col.  18: Ufficio proponente (se registro di proposta atto/repertorio atti)
					datiReg.setStatoProposta(v.get(18));            //col.  19: Stato della proposta di atto: in iter, archiviata, approvata, pubblicata (se registro di proposta atto)
					datiReg.setEstremiRepertorio(v.get(19));        //col.  20: Estremi di repertorio corrispondenti al n.ro di proposta (se registro di proposta atto)
					datiReg.setEstremiProposta(v.get(20));          //col.  21: Estremi di proposta corrispondenti al n.ro di repertorio (se registro di repertorio atti)
					datiReg.setEstremiProtocolloGenerale(v.get(21));//col.  22: Estremi di protocollo generale corrispondenti (se registro diverso dal Prot. Gen.)
					datiReg.setPeriodoPubblicazione(v.get(22));     //col.  23: Periodo/i di pubblicazione (se registro di proposta atto/repertorio atti)
					datiReg.setDataAdozione(v.get(23));             //col.  24: Data di adozione (la data dell¿ultima firma) (se registro di proposta atto/repertorio atti)

			       	datiRegistrazioniList.add(datiReg);
		    	}				
				String header = "Test stampa registro di protocollo in append";
				String footer = "By Mattia Zanin";				
				StampaRegProt.getInstance().stampaRegProt(new File("D:/tmp/TestStampaRegProtOut.pdf"), datiRegistrazioniList, header, footer);							
			}
		} catch (Throwable e) {
			mLogger.warn(e);
		}				
	}
	
	public static void testStampaMassiva (int num) {		
		try {			
			List<File> fileDatiRegList = new ArrayList<File>();   	        
			for (int i = 0; i < num; i++) {
				String datiRegistrazioniXml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><Lista><Riga><Colonna Nro=\"1\">25/11/2013 04:59:53</Colonna><Colonna Nro=\"2\">0001277</Colonna><Colonna Nro=\"3\">U</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"7\">Corte d`Appello di CAGLIARI; Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"8\">prova file con nome lungo</Colonna><Colonna Nro=\"10\">SHA-256 base64 2KsJKvvA4g7jmv8JyTNN5ZkZQsCwz8OOWv8fg2SKI2w=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 10:16:48</Colonna><Colonna Nro=\"2\">0001278</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test di vale</Colonna><Colonna Nro=\"10\">SHA-256 base64 zc/KiUzh/hlCQ8H8fivfkKHA2DOZEqFlSB4Mwgrwwbo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5728 del 15/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 10:34:12</Colonna><Colonna Nro=\"2\">0001279</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">4a prova -solo file primario</Colonna><Colonna Nro=\"10\">SHA-256 base64 j+kac+HZh78jFDQJ9JN1jJIgjQyMlkMsCEBXLLhAPCc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5734 del 16/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 11:37:00</Colonna><Colonna Nro=\"2\">0001280</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">paolo sas</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test scanner</Colonna><Colonna Nro=\"10\">SHA-256 base64 m23Bqv8S/x/Ls6j2bG5/mJYMQYBMOyqVf+/3FgoeWvM=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 11:50:02</Colonna><Colonna Nro=\"2\">0001281</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">paolo sas</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test allegato scan</Colonna><Colonna Nro=\"10\">SHA-256 base64 AcxyzQxGcytDdhx6I15YBLwiWplVSorW+MDxQqZrePo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:15:46</Colonna><Colonna Nro=\"2\">0001282</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">stefanel</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 OOlScLWq7lRV3t+90KPx6ic0VnRjO7P//85AQSBO6gA=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:16:23</Colonna><Colonna Nro=\"2\">0001283</Colonna><Colonna Nro=\"3\">U</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"7\">Procura della Repubblica presso il Tribunale di MODENA</Colonna><Colonna Nro=\"8\">test vale</Colonna><Colonna Nro=\"10\">SHA-256 base64 jFm/HlifEqSdvjHu6i9RfJSk9WBvt3PmLwM5rgTT7n8=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:17:23</Colonna><Colonna Nro=\"2\">0001284</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:17:35</Colonna><Colonna Nro=\"2\">0001285</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test per Jacopo -1a</Colonna><Colonna Nro=\"10\">SHA-256 base64 zc/KiUzh/hlCQ8H8fivfkKHA2DOZEqFlSB4Mwgrwwbo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5737 del 25/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:22:05</Colonna><Colonna Nro=\"2\">0001286</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 3BNmVDkpA2/Z1+44ZDLzVOEdlGFmco/mBFax3e1DR1A=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:45:49</Colonna><Colonna Nro=\"2\">0001287</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 xvtcNOfvuw6aoIOUm+28eLihHAEBvLvU1hOIMl2NVo8=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:49:09</Colonna><Colonna Nro=\"2\">0001288</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 14:02:16</Colonna><Colonna Nro=\"2\">0001289</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 cWkLpbfIIwDbeVlYpbD42Z26+hoHkb8pXvOw3ByMPtg=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:10:23</Colonna><Colonna Nro=\"2\">0001290</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 V6bV8iVqFV50hEkBA+SM+3PUVawE49eHRZGqPE1OYew=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:15:22</Colonna><Colonna Nro=\"2\">0001291</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 xAKywmR4BCBqk2kvwO9qdWIFTU59K3pSG5QkKarFvSk=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:35:18</Colonna><Colonna Nro=\"2\">0001292</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 coRwu8BFTGRoN0FDlUm385TlVCRfu/X1Z4PpIwrO8fc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:58:41</Colonna><Colonna Nro=\"2\">0001293</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test 4 per jacaopo</Colonna><Colonna Nro=\"10\">SHA-256 base64 j+kac+HZh78jFDQJ9JN1jJIgjQyMlkMsCEBXLLhAPCc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5740 del 25/11/2013</Colonna></Riga></Lista>";
				File file = new File("D:/tmp/DatiReg" + i + ".pdf");
				FileUtils.writeStringToFile(file, datiRegistrazioniXml);
				fileDatiRegList.add(file);
			}
			String header = "Test stampa registro di protocollo in append";
			String footer = "By Mattia Zanin";
			StampaRegProt.getInstance().stampaRegProtMassive(new File("D:/tmp/TestStampaRegProtOut.pdf"), fileDatiRegList, header, footer,"PROTOCOLLO");
			for(int i = 0; i < num; i++) {
				File file = new File("D:/tmp/DatiReg" + i + ".pdf");
				FileUtil.deleteFile(file);
			}		
		} catch (Throwable e) {
			mLogger.warn(e);
		}				
	}
	
	public static void testConcatStampe () {		
		String datiRegistrazioniXml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><Lista><Riga><Colonna Nro=\"1\">25/11/2013 04:59:53</Colonna><Colonna Nro=\"2\">0001277</Colonna><Colonna Nro=\"3\">U</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"7\">Corte d`Appello di CAGLIARI; Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"8\">prova file con nome lungo</Colonna><Colonna Nro=\"10\">SHA-256 base64 2KsJKvvA4g7jmv8JyTNN5ZkZQsCwz8OOWv8fg2SKI2w=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 10:16:48</Colonna><Colonna Nro=\"2\">0001278</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test di vale</Colonna><Colonna Nro=\"10\">SHA-256 base64 zc/KiUzh/hlCQ8H8fivfkKHA2DOZEqFlSB4Mwgrwwbo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5728 del 15/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 10:34:12</Colonna><Colonna Nro=\"2\">0001279</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">4a prova -solo file primario</Colonna><Colonna Nro=\"10\">SHA-256 base64 j+kac+HZh78jFDQJ9JN1jJIgjQyMlkMsCEBXLLhAPCc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5734 del 16/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 11:37:00</Colonna><Colonna Nro=\"2\">0001280</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">paolo sas</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test scanner</Colonna><Colonna Nro=\"10\">SHA-256 base64 m23Bqv8S/x/Ls6j2bG5/mJYMQYBMOyqVf+/3FgoeWvM=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 11:50:02</Colonna><Colonna Nro=\"2\">0001281</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">paolo sas</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test allegato scan</Colonna><Colonna Nro=\"10\">SHA-256 base64 AcxyzQxGcytDdhx6I15YBLwiWplVSorW+MDxQqZrePo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:15:46</Colonna><Colonna Nro=\"2\">0001282</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">stefanel</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 OOlScLWq7lRV3t+90KPx6ic0VnRjO7P//85AQSBO6gA=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:16:23</Colonna><Colonna Nro=\"2\">0001283</Colonna><Colonna Nro=\"3\">U</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"7\">Procura della Repubblica presso il Tribunale di MODENA</Colonna><Colonna Nro=\"8\">test vale</Colonna><Colonna Nro=\"10\">SHA-256 base64 jFm/HlifEqSdvjHu6i9RfJSk9WBvt3PmLwM5rgTT7n8=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:17:23</Colonna><Colonna Nro=\"2\">0001284</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:17:35</Colonna><Colonna Nro=\"2\">0001285</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test per Jacopo -1a</Colonna><Colonna Nro=\"10\">SHA-256 base64 zc/KiUzh/hlCQ8H8fivfkKHA2DOZEqFlSB4Mwgrwwbo=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5737 del 25/11/2013</Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:22:05</Colonna><Colonna Nro=\"2\">0001286</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 3BNmVDkpA2/Z1+44ZDLzVOEdlGFmco/mBFax3e1DR1A=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:45:49</Colonna><Colonna Nro=\"2\">0001287</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 xvtcNOfvuw6aoIOUm+28eLihHAEBvLvU1hOIMl2NVo8=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 13:49:09</Colonna><Colonna Nro=\"2\">0001288</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">No</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 14:02:16</Colonna><Colonna Nro=\"2\">0001289</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 cWkLpbfIIwDbeVlYpbD42Z26+hoHkb8pXvOw3ByMPtg=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:10:23</Colonna><Colonna Nro=\"2\">0001290</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 V6bV8iVqFV50hEkBA+SM+3PUVawE49eHRZGqPE1OYew=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:15:22</Colonna><Colonna Nro=\"2\">0001291</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 xAKywmR4BCBqk2kvwO9qdWIFTU59K3pSG5QkKarFvSk=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:35:18</Colonna><Colonna Nro=\"2\">0001292</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">test</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test</Colonna><Colonna Nro=\"10\">SHA-256 base64 coRwu8BFTGRoN0FDlUm385TlVCRfu/X1Z4PpIwrO8fc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\"></Colonna></Riga><Riga><Colonna Nro=\"1\">25/11/2013 15:58:41</Colonna><Colonna Nro=\"2\">0001293</Colonna><Colonna Nro=\"3\">E</Colonna><Colonna Nro=\"4\">Martinucci Valentina&gt; per 2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"6\">Procura di Milano</Colonna><Colonna Nro=\"7\">Tribunale di BOLZANO</Colonna><Colonna Nro=\"8\">test 4 per jacaopo</Colonna><Colonna Nro=\"10\">SHA-256 base64 j+kac+HZh78jFDQJ9JN1jJIgjQyMlkMsCEBXLLhAPCc=</Colonna><Colonna Nro=\"11\">2.1 - Protocollo (PROTOCOLLO)</Colonna><Colonna Nro=\"12\">No</Colonna><Colonna Nro=\"15\">Sì</Colonna><Colonna Nro=\"16\">M_DG - 01514602100 N. 5740 del 25/11/2013</Colonna></Riga></Lista>";
		try {
			List<DatiRegistrazioniOut> datiRegistrazioniList = new ArrayList<DatiRegistrazioniOut>();   	        
			StringReader sr = new StringReader(datiRegistrazioniXml);
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
					DatiRegistrazioniOut datiReg = new DatiRegistrazioniOut();	        								
					datiReg.setDataOraReg(v.get(0)); //col. 1
					datiReg.setNroReg(v.get(1)); //col.  2   
					datiReg.setTipoReg(v.get(2)); //col.  3
					datiReg.setUtenteUoReg(v.get(3)); //col.  4   						
					datiReg.setEstremiRegEmergenza(v.get(4)); //col.  5   
					datiReg.setMittenti(v.get(5)); //col.  6
					datiReg.setDestinatari(v.get(6)); //col.  7   
					datiReg.setOggetto(v.get(7)); //col.  8
					datiReg.setClassificheFascicoli(v.get(8)); //col.  9   
					datiReg.setFileAssociati(v.get(9)); //col.  10
					datiReg.setAssegnatari(v.get(10)); //col.  11 
					datiReg.setFlgAnnullata(v.get(11)); //col.  12
					datiReg.setDataOraAnn(v.get(12)); //col. 13
					datiReg.setEstremiAttoAnnReg(v.get(13)); //col.  14
					datiReg.setFlgRegConDatiAnn(v.get(14)); //col.  15
					datiReg.setEstremiProtRicevuto(v.get(15)); //col.  16	
					datiReg.setNrAllegati(v.get(16)); 				//col.  17 - Nr.allegati 
					datiReg.setUfficioProponente(v.get(17));        //col.  18: Ufficio proponente (se registro di proposta atto/repertorio atti)
					datiReg.setStatoProposta(v.get(18));            //col.  19: Stato della proposta di atto: in iter, archiviata, approvata, pubblicata (se registro di proposta atto)
					datiReg.setEstremiRepertorio(v.get(19));        //col.  20: Estremi di repertorio corrispondenti al n.ro di proposta (se registro di proposta atto)
					datiReg.setEstremiProposta(v.get(20));          //col.  21: Estremi di proposta corrispondenti al n.ro di repertorio (se registro di repertorio atti)
					datiReg.setEstremiProtocolloGenerale(v.get(21));//col.  22: Estremi di protocollo generale corrispondenti (se registro diverso dal Prot. Gen.)
					datiReg.setPeriodoPubblicazione(v.get(22));     //col.  23: Periodo/i di pubblicazione (se registro di proposta atto/repertorio atti)
					datiReg.setDataAdozione(v.get(23));             //col.  24: Data di adozione (la data dell¿ultima firma) (se registro di proposta atto/repertorio atti)

			       	datiRegistrazioniList.add(datiReg);
		    	}				
				String header = "Test stampa registro di protocollo in append";
				String footer = "By Mattia Zanin";
				List<File> fileList =  new ArrayList<File>();
				int pageNumber = 0;
				for(int i = 0; i < 100; i++) {
					File file = new File("D:/tmp/TestStampaRegProt" + i + ".pdf");
					StampaRegProt.getInstance().stampaRegProt(file, datiRegistrazioniList, header, footer, pageNumber);
					PdfReader reader = new PdfReader(FileUtils.openInputStream(file));				
					pageNumber += reader.getNumberOfPages();
					reader.close();
					fileList.add(file);
				}
				concatFiles(fileList, new File("D:/tmp/TestStampaRegProtOut.pdf"));	
				for(int i = 0; i < 1000; i++) {
					File file = new File("D:/tmp/TestStampaRegProt" + i + ".pdf");
					FileUtil.deleteFile(file);
				}				
			}
		} catch (Throwable e) {
			mLogger.warn(e);
		}				
	}
	
	public static void main (String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");				
		SpringAppContext.setContext(context);		
		testStampa();
//		testStampaMassiva(1);
//		testConcatStampe();
	}

}