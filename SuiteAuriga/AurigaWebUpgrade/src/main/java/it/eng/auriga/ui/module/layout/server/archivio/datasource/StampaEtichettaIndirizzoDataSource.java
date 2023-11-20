/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGetetichetteconinddestBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ListaIdStampaEtichettaIndirizzoBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ListaStampaEtichettaIndirizzoBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperazioneMassivaArchivioBean;
import it.eng.auriga.ui.module.layout.shared.bean.StampaEtichettaConIndirizzoConfig;
import it.eng.client.DmpkRegistrazionedocGetetichetteconinddest;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Datasource(id = "StampaEtichettaIndirizzoDataSource")
public class StampaEtichettaIndirizzoDataSource extends AbstractServiceDataSource<ExportBean, ExportBean>{	
	
	private int numFascicoli = 0;

	private float puntiXcm = 28.346456693f;
	
	public ListaStampaEtichettaIndirizzoBean getListaEtichette(OperazioneMassivaArchivioBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		DmpkRegistrazionedocGetetichetteconinddestBean input = new DmpkRegistrazionedocGetetichetteconinddestBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		String xmlInput = createXMLInput(bean);
		input.setListaidudin(xmlInput);
		
		String mezziTrasmissione = bean.getMezziTrasmissione();
		if (mezziTrasmissione != null && !mezziTrasmissione.equals(""))
			mezziTrasmissione = mezziTrasmissione.replace(",", ";");
		
		input.setCodmezzotrasmin(mezziTrasmissione);
		
		DmpkRegistrazionedocGetetichetteconinddest etichetteConIndirizzo = new DmpkRegistrazionedocGetetichetteconinddest();
		StoreResultBean<DmpkRegistrazionedocGetetichetteconinddestBean> output = etichetteConIndirizzo.execute(getLocale(), loginBean, input);
		
		ListaStampaEtichettaIndirizzoBean listaStampaEtichetteBean = new ListaStampaEtichettaIndirizzoBean();
		
		if(output.getDefaultMessage() != null) {
			if(errorMessages == null) 
				errorMessages = new HashMap<String, String>();
			
			errorMessages.put("error", output.getDefaultMessage());
			listaStampaEtichetteBean.setErrorMessages(errorMessages);
		}
		
		if(output.getResultBean().getListaetichetteout() != null && output.getResultBean().getListaetichetteout().length() > 0) {
			StringReader sr = new StringReader(output.getResultBean().getListaetichetteout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			
			ArrayList<String> values = new ArrayList<String>();
			
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));		
				values.add(v.get(0));
			}
//			for (int i = 0; i < 20; i++) {
//				values.add("STRING AFSDFAS DASFDSF SDFSDFDS FSD FSDF SDFEWFGGJGFYUTBDYNDYDNYT");
//			}
			
			listaStampaEtichetteBean.setEtichette(values);
		}
		
		listaStampaEtichetteBean.setNumFascicoli(numFascicoli);
		
		return listaStampaEtichetteBean;
	}
	
	
	protected String createXMLInput(OperazioneMassivaArchivioBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String xmlIdDocumenti = null;
		
		List<ListaIdStampaEtichettaIndirizzoBean> lList = new ArrayList<ListaIdStampaEtichettaIndirizzoBean>();
		
		ListaIdStampaEtichettaIndirizzoBean listaIdStampaEtichettaIndirizzoBean = null;
		
		for (ArchivioBean archivioBean : bean.getListaRecord()){
			if(!archivioBean.getFlgUdFolder().equals("F")) {
				listaIdStampaEtichettaIndirizzoBean = new ListaIdStampaEtichettaIndirizzoBean();
				listaIdStampaEtichettaIndirizzoBean.setIdDocumento(archivioBean.getIdUdFolder());
				lList.add(listaIdStampaEtichettaIndirizzoBean);
			}
			else 
				numFascicoli += 1;
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlIdDocumenti = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlIdDocumenti;
	}
	
	public ExportBean call(ExportBean exportBean) throws Exception{
		
		File file = File.createTempFile("export", "");		
		Font font_10= new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		
		StampaEtichettaConIndirizzoConfig config = (StampaEtichettaConIndirizzoConfig)SpringAppContext.getContext().getBean("StampaEtichettaConIndirizzoConfig");
		
		float altezzaCm    = config.getAltezzaEtichetta();
		float larghezzaCm  = config.getLarghezzaEtichetta();
		
		//Converto i cm in punti
		float altezzaCmToPt   = (altezzaCm * puntiXcm);		
		float larghezzaCmToPt = (72f*larghezzaCm)/2.54f;
		
		
		//Numero massimo di colone in A4. 21 è la dimensione di un A4
		int numColonne = (int)Math.round(((72f*21)/2.54f)/larghezzaCmToPt);
		
		//Fisso la larghezza delle colonne tutte uguali
		float[] configPercentageWidth = new float[numColonne];

		for(int i = 0; i < numColonne; i++){
			configPercentageWidth[i] = 1;
		}		
		
		//Serve per dare la percentuale alla tabella in base alle colonne
//		float percentualeTmp = numColonne * 4.142855f;
		
		PdfPTable table = null;
		table = new PdfPTable(numColonne);
		
//		table.setWidths(configPercentageWidth);
//		table.setWidthPercentage(percentualeTmp * larghezzaCm);
		table.setWidthPercentage(100);
		
		//Defaultcell è usata per le celle vuote
		table.getDefaultCell().setBorder(0);
		table.getDefaultCell().setBorderWidth(0f);
		
		
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
	        cell.setCellEvent(new DottedCell());
	        cell.setBorder(PdfPCell.NO_BORDER);
	        cell.setBorderWidth(0f);
	        
			cell.setPadding(12);
	        // NO_BORDER
	        
			//Fisso l'altezza
			cell.setFixedHeight(altezzaCmToPt);
			cell.setNoWrap(false);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			
			
			
			table.addCell(cell);					
		}					
		
		table.completeRow();
		
		Document document = null;
		document = new Document(PageSize.A4);
		document.setMargins(0, 0, 0, 0);
		document.newPage();

		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.addAuthor("Automatic Export");
		document.add(table);
		document.close();
		
		StorageService storageService = StorageImplementation.getStorage();		
		exportBean.setTempFileOut(storageService.store(file));			
		
		return exportBean;		
	
	}
	
	class DottedCell implements PdfPCellEvent {
        @Override
        public void cellLayout(PdfPCell cell, Rectangle position,
            PdfContentByte[] canvases) {
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            
            canvas.setColorStroke(BaseColor.WHITE);    // Serve per nascondere la linea
            //canvas.setLineDash(3f, 3f);
            
            canvas.rectangle(position.getLeft(), position.getBottom(), position.getWidth(), position.getHeight());
            canvas.stroke();
        }
    }

}
