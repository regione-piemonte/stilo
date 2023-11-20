/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocStampadestinatarispedBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ListaIdStampaDistintaSpedizioneBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ListaStampaDistintaSpedizioneBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.MezziTrasmissioneXmlBean;
import it.eng.auriga.ui.module.layout.server.stampaDistintaSpedizione.StampaDistintaSpedizione;
import it.eng.auriga.ui.module.layout.server.stampaDistintaSpedizione.bean.DatiDistintaSpedizioneOut;
import it.eng.auriga.ui.module.layout.shared.bean.StampaEtichettaConIndirizzoConfig;
import it.eng.client.DmpkRegistrazionedocStampadestinatarisped;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Datasource(id = "StampaDistintaSpedizioneDataSource")
public class StampaDistintaSpedizioneDataSource extends AbstractServiceDataSource<ExportBean, ExportBean>{	
	
	private int numFascicoli = 0;

	public ListaStampaDistintaSpedizioneBean getListaDestinatari(ListaStampaDistintaSpedizioneBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		DmpkRegistrazionedocStampadestinatarispedBean input = new DmpkRegistrazionedocStampadestinatarispedBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		// Lista dei documenti selezionati
		String xmlInput = createXMLInput(bean);
		input.setListaidudin(xmlInput);
		
		// Lista dei mezzi di trasmissione impostati nel filtro
		String xmlMezziTrasmissione = createXMLMezziTrasmissione(bean);
		input.setListacodmezziin(xmlMezziTrasmissione);
		
		DmpkRegistrazionedocStampadestinatarisped service = new DmpkRegistrazionedocStampadestinatarisped();
		StoreResultBean<DmpkRegistrazionedocStampadestinatarispedBean> output = service.execute(getLocale(), loginBean, input);
		
		ListaStampaDistintaSpedizioneBean listaStampaDistintaSpedizioneBean = new ListaStampaDistintaSpedizioneBean();
		
		if(output.getDefaultMessage() != null) {
			if(errorMessages == null) 
				errorMessages = new HashMap<String, String>();
			
			errorMessages.put("error", output.getDefaultMessage());
			listaStampaDistintaSpedizioneBean.setErrorMessages(errorMessages);
		}
		
		if(output.getResultBean().getResultout() != null && output.getResultBean().getResultout().length() > 0) {
			try {
				// Leggo i dati della lista
				List<DatiDistintaSpedizioneOut> datiRegistrazioniList = new ArrayList<DatiDistintaSpedizioneOut>();   	        
				StringReader sr = new StringReader(output.getResultBean().getResultout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				
				listaStampaDistintaSpedizioneBean.setListaDistintaSpedizioneVuota(true);
				if((lista != null) && (lista.getRiga().size() > 0)){
					listaStampaDistintaSpedizioneBean.setListaDistintaSpedizioneVuota(false);
					for (int i = 0; i < lista.getRiga().size(); i++) 
					{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
						DatiDistintaSpedizioneOut datiReg = new DatiDistintaSpedizioneOut();	        								
						datiReg.setNroReg(v.get(0)); 										    //col.  1 : Nro di protocollo/registrazione
						datiReg.setSiglaReg(v.get(1)); 											//col.  2 : Sigla del registro di registrazione   
						datiReg.setDataOraReg(v.get(2));                                        //col.  3 : Data di registrazione
						datiReg.setDestinatario(v.get(3));                                      //col.  4 : Denominazione / cognome e nome del destinatario					
						datiReg.setIndirizzoSpedizione(v.get(4));                               //col.  5 : Indirizzo di spedizione
						datiReg.setDescMezzoTrasmissione(v.get(5));                             //col.  6 : Mezzo di trasmissione
				       	datiRegistrazioniList.add(datiReg);
			    	}
				
					// Leggo l'intestazione
					String header1 = output.getResultBean().getHeader1out();
					String header2 = output.getResultBean().getHeader2out();
					String header3 = output.getResultBean().getHeader3out();
					
					// Leggo il footer 
					String footer = "";
					
					// Creo il file PDF
					File fileOut = File.createTempFile("stampa_distinta_spedizione", ".pdf");					
					StampaDistintaSpedizione.getInstance().stampaDistintaSpedizione(fileOut, datiRegistrazioniList, header1, header2, header3, footer);
					StorageService storageService = StorageImplementation.getStorage();	
					bean.setUri(storageService.store(fileOut));		
								
					/*
					DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
					RebuildedFile lRebuildedFile = new RebuildedFile();
					
					//lRebuildedFile.setIdDocumento(new BigDecimal(datiUdStampa.getIdDoc()));
					
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(bean.getUri()));				
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					InfoFileUtility lFileUtility = new InfoFileUtility();
					lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
					lMimeTypeFirmaBean.setFirmato(false);
					lMimeTypeFirmaBean.setFirmaValida(false);
					lMimeTypeFirmaBean.setConvertibile(false);
					lMimeTypeFirmaBean.setDaScansione(false);							
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.PRIMARIO);
					GenericFile lGenericFile = new GenericFile();				
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					
					//lGenericFile.setDisplayFilename((datiUdStampa.getDisplayFilename()));
					lGenericFile.setDisplayFilename(("stampa_distinta_spedizione"));
					
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().getValue());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().getValue());				
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);				
					lRebuildedFile.setInfo(lFileInfoBean);
					VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
					lVersionaDocumentoInBean.setRebuildedFile(lRebuildedFile);
					
					
					GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
					VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);	
					if (lVersionaDocumentoOutBean.getDefaultMessage() != null){
						throw new StoreException(lVersionaDocumentoOutBean);
					}
					*/
					listaStampaDistintaSpedizioneBean.setUri(bean.getUri());
				}
				
			} catch (Exception e) {
				
				throw new Exception("Stampa distinta di spedizione fallita", e);
			}
		}
		
		listaStampaDistintaSpedizioneBean.setNumFascicoli(numFascicoli);
		
		return listaStampaDistintaSpedizioneBean;
	}
	
	
   protected String createXMLMezziTrasmissione(ListaStampaDistintaSpedizioneBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String xmlMezziTrasmissione = null;
		
		List<MezziTrasmissioneXmlBean> lList = new ArrayList<MezziTrasmissioneXmlBean>();
		
		if(bean.getListMezziTrasmissione()!=null && bean.getListMezziTrasmissione().size()>0){
			for (int i = 0; i < bean.getListMezziTrasmissione().size(); i++){
			    MezziTrasmissioneXmlBean lMezziTrasmissioneXmlBean = new MezziTrasmissioneXmlBean();
			    lMezziTrasmissioneXmlBean.setIdMezzoTrasmissione(bean.getListMezziTrasmissione().get(i));
			    lMezziTrasmissioneXmlBean.setDesMezzoTrasmissione("");
				lList.add(lMezziTrasmissioneXmlBean);
			}			
		}
			
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlMezziTrasmissione = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlMezziTrasmissione;
	}

	protected String createXMLInput(ListaStampaDistintaSpedizioneBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String xmlIdDocumenti = null;
		
		List<ListaIdStampaDistintaSpedizioneBean> lList = new ArrayList<ListaIdStampaDistintaSpedizioneBean>();
		
		ListaIdStampaDistintaSpedizioneBean listaIdStampaDistintaSpedizioneBean = null;
		
		for (ArchivioBean archivioBean : bean.getListaRecord()){
			if(!archivioBean.getFlgUdFolder().equals("F")) {
				listaIdStampaDistintaSpedizioneBean = new ListaIdStampaDistintaSpedizioneBean();
				listaIdStampaDistintaSpedizioneBean.setIdDocumento(archivioBean.getIdUdFolder());
				lList.add(listaIdStampaDistintaSpedizioneBean);
			}
			else 
				numFascicoli += 1;
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlIdDocumenti = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlIdDocumenti;
	}
	
	public ExportBean call(ExportBean exportBean) throws Exception {
		
		OutputStream os = null;
		try {
		
			File file = File.createTempFile("export", "");		
			
			StampaEtichettaConIndirizzoConfig config = (StampaEtichettaConIndirizzoConfig)SpringAppContext.getContext().getBean("StampaEtichettaConIndirizzoConfig");
			
			int numCol = config.getNroColonna();
			Font font_10  = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
			
			PdfPTable table = null;
			
			if(numCol > exportBean.getFields().length)
				table = new PdfPTable(exportBean.getFields().length);
			else 
				table = new PdfPTable(numCol);
			
			table.setWidthPercentage(100);	
					
			//Scrivo le righe
			for (int r = 0; r < exportBean.getFields().length; r++) {
				String value = (String) exportBean.getFields()[r];
				if(value==null){
					value = "";
				}
				else
					value = value.replaceAll("<br/>", "\n");
	
				Paragraph paragraph = new Paragraph(StringEscapeUtils.unescapeHtml(value),font_10);
				PdfPCell cell = new PdfPCell(paragraph);
				cell.setBorder(0);
				cell.setPadding(30);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);					
			}					
			
			// aggiungo celle vuote nel caso il numero di colonne non 
			// sia multiplo dei campi. Non si completa la rige e quindi  non si vedono
			for(int i = 0; i<=exportBean.getFields().length - table.getNumberOfColumns(); i++ ){				
				PdfPCell emptyCell = new PdfPCell();
				emptyCell.setBorder(0);
				table.addCell(emptyCell);
			}
			
			Document document = null;
			document = new Document(PageSize.A4,10,10,10,10);
			document.newPage();
			
			os = new FileOutputStream(file);
			PdfWriter.getInstance(document, os);
			document.open();
			document.addAuthor("Automatic Export");
			document.add(table);
			document.close();
			
			StorageService storageService = StorageImplementation.getStorage();		
			exportBean.setTempFileOut(storageService.store(file));			
			
			return exportBean;			
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (Exception e) {}
			}
		}
	}	
}
