package it.eng.utility.oomanager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.odftoolkit.odfdom.OdfElement;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.doc.form.OdfForm;
import org.odftoolkit.odfdom.dom.element.draw.DrawControlElement;
import org.odftoolkit.odfdom.dom.element.form.FormCheckboxElement;
import org.odftoolkit.odfdom.dom.element.form.FormListboxElement;
import org.odftoolkit.odfdom.dom.element.form.FormOptionElement;
import org.odftoolkit.odfdom.dom.element.form.FormRadioElement;
import org.odftoolkit.odfdom.dom.element.form.FormTextElement;
import org.odftoolkit.odfdom.dom.element.form.FormTextareaElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.utility.oomanager.exception.OpenOfficeException;
import it.eng.utility.oomanager.xml.NewSezioneCache;
import it.eng.utility.oomanager.xml.NewSezioneCache.Variabile.Lista.Riga;
import it.eng.utility.oomanager.xml.NewSezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.utility.oomanager.xslt.OdtToHtml;


public class DocumentOperation {
	
	private static final String PREFIX_TABLE = "TAB_";
	private static final String PREFIX_SEZ_RIPETUTA = "SR_";
	
	public static void mergeDocumentsInPdf(List<File> inputs, File output) throws Exception {		
		FileOutputStream fos = null;
		PdfCopyFields copy = null;		
		try {			
			fos = new FileOutputStream(output);
			copy = new PdfCopyFields(fos);
			//Apro il documento
			copy.open();			
			//Inserisco i documenti pdf in ordine
			for(int i=0; i<inputs.size(); i++){												
				//Creo un PDFReader
				FileInputStream fis = null;
				File tempFile = null;
				try {					
					tempFile = File.createTempFile(FilenameUtils.getBaseName(inputs.get(i).getName()), ".pdf");
					try {
						OpenOfficeConverter.newInstance().convert(inputs.get(i), tempFile);
						fis = new FileInputStream(tempFile);
					} catch(Exception e) {
						fis = new FileInputStream(inputs.get(i));	
					}									
					PdfReader reader = new PdfReader(fis);
					copy.addDocument(reader);					
				} finally{					
					try { fis.close(); } catch (Exception e) {}
					try { tempFile.delete(); } catch (Exception e) {}
				}				
			}			
		} finally{
			try { copy.close(); } catch (Exception e) {}
			try { fos.close(); } catch (Exception e) {}
		}				
	}
	
	public static InputStream convertOdtToPdf(File odt) throws Exception{		
		File tempFile = null;			
		try{
			tempFile = File.createTempFile(FilenameUtils.getBaseName(odt.getName()), ".pdf");
			//DocumentFormat format = new DocumentFormat("PDF", "application/pdf", "pdf");
			//DocumentFormatRegistry registry = documentConverter.getFormatRegistry();
			//DocumentFormat format = registry.getFormatByExtension("pdf");
			//format.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
			OpenOfficeConverter.newInstance().convert(odt, tempFile/*, format*/);
			return FileUtils.openInputStream(tempFile);
		} catch (Throwable e) {
			throw new OpenOfficeException("Errore durante la conversione in pdf del modello odt", e);
		} finally {
			try{ tempFile.delete(); } catch(Exception e) {}
		}
	}		
	
	public static String convertOdtToHtml(File odt, String filterBasePath) throws Exception{				
		File input = null;
		File output = null;		
		try {        	
        	input = File.createTempFile(FilenameUtils.getBaseName(odt.getName()), ".odt");					        	
        	FileUtils.copyFile(odt, input);		
			//Recupero le immagini
			OdfPackage pack = null;
			OdfDocument odfTextDocument = null;			
	        try {	        	
	        	pack = OdfPackage.loadPackage(input);  
				odfTextDocument = OdfTextDocument.loadDocument(pack);				
				List<OdfDrawImage> immagini = OdfDrawImage.getImages(odfTextDocument);											
				
				//List<ImageBean> images = new ArrayList<ImageBean>(); 
				
				for(int i=0;i<immagini.size();i++){
					String fileName = StringUtils.remove(immagini.get(i).getImageUri().getPath(),"Pictures/");
					
					//Salvo e setto l'URI dell'immagine
					byte[] data = pack.getBytes(immagini.get(i).getImageUri().getPath());	
					
					String mimetype = null;
					try {
						mimetype = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));										
					} catch(Exception e) {}
					/*
					if(StringUtils.isBlank(mimetype)) {
						try {
							mimetype = Magic.getMagicMatch(data).getMimeType(); 
						} catch(Exception e) {}							
					}
					if(StringUtils.isBlank(mimetype)) {
						try {
							FileNameMap fileNameMap = URLConnection.getFileNameMap();
							mimetype = fileNameMap.getContentTypeFor(fileName);				    							
						} catch(Exception e) {}
					}					
					if(StringUtils.isBlank(mimetype)) {
						try {
							ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
						    Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
						    if (readers.hasNext()) {
						       mimetype = readers.next().getFormatName();
						    }						    						
						} catch(Exception e) {}
					}
					*/
					mimetype = StringUtils.isBlank(mimetype) ? "image/*" : mimetype;
					
					immagini.get(i).setImagePath("data:" + mimetype + ";base64," + Base64.encodeBase64String(data));
					
					/*
					URI uri = addImage(data, fileName, contextUrl, contextRealPath);
					immagini.get(i).setImagePath(uri.getPath());				 		
					
					ImageBean tmp = new ImageBean();
					tmp.setUri(uri);
					tmp.setData(data);
					images.add(tmp);							
					
					//Recupero il nome dell'immagine
					NamedNodeMap map = immagini.get(i).getParentNode().getAttributes();
					for(int y=0;y<map.getLength();y++){
						if(map.item(y).getLocalName().equals("name")){
							tmp.setName(map.item(y).getNodeValue());
							break;
						}
					}
					*/								
				}
				odfTextDocument.save(input);
	        }
	        finally{
	        	if(odfTextDocument!=null){
	        		odfTextDocument.close();
	        	}        	
	        	if(pack!=null){
	        		pack.close();
	        	}        	
	        }			
	        output = File.createTempFile(FilenameUtils.getBaseName(odt.getName()), ".html");
			OdtToHtml.convertOdtToHtml(input, output, filterBasePath);			
			String html = FileUtils.readFileToString(output);			
			return html;
		} finally { 
			try{ input.delete(); } catch(Exception e) {}
			try{ output.delete(); } catch(Exception e) {}			
		}					
	}
	
	private static URI addImage(byte[] data, String fileName, String contextUrl, String contextRealPath) throws Exception{		
		File file = new File(contextRealPath+"/images/"+fileName);
		FileUtils.writeByteArrayToFile(file, data);						
		return new URI(contextUrl+"/images/"+fileName);				
	}
	
	public static File setValueToOdt(Map<String, Object> valData, File modelloOdt) throws Exception{		
		// vi sono pi� elementi di tipo form:form, ma uno solo contiene tutti gli elementi di tipo OdfElement
		// questa variabile � il riferimento a questo elemento sotto il quale devo aggiungere gli elementi di tipo OdfElement relativi alle tabelle
		Node formFormParentElement = null;			
		// struttura per accedere rapidamente agli OdfElement per id
		HashMap<String, OdfElement> odfElementIdx = new HashMap<String, OdfElement>();
		// struttura per accedere rapidamente agli OdfElement per name
		HashMap<String, OdfElement> odfElementNamex = new HashMap<String, OdfElement>();		
		// prefisso del nome da attribuire ai controlli aggiunti per le tabelle
		String addedControl = "addedControl";		
		// suffisso del file temporaneo in cui viene popolato modelloOdt con i dati di valData
		String suffixBuildFile = "_build";		
		//contatore per attribuire un nome univoco agli elementi che vengono aggiunti
		int addedControlCounter = 0;	
		File tempBuildFile = modelloOdt;				
		//Setto i valori
		OdfPackage anOdfPackeage = null;
		OdfDocument odfTextDocument = null;
		try{
			anOdfPackeage = OdfPackage.loadPackage(tempBuildFile);  
			odfTextDocument = OdfTextDocument.loadDocument(anOdfPackeage);						
			//prepara una tablla di associazione tra il nome dei controlli e il loro id
			NodeList formList = odfTextDocument.getContentDom().getElementsByTagName("form:form");												
			//questo ciclo di valorizzazione provvede a popolare i dati non compresi nelle tabelle
			//e a raccogliere nomi e id degli elementi da popolare nelle tabelle
			for(int i = 0; i < formList.getLength(); i++){
				//Recupero i valori del form
				OdfForm forms = (OdfForm)formList.item(i);
				for(int y = 0; y < forms.getLength(); y++){							
					//Valore di testo
					Node node = forms.item(y);
					if(node instanceof FormListboxElement){
						formFormParentElement = node.getParentNode();
						//Recupero il valore selezionato
						FormListboxElement odfElement = (FormListboxElement)node;
						String name = odfElement.getFormNameAttribute();
						//System.out.println(odfElement.getFormIdAttribute());
						if ((odfElement.getFormIdAttribute() != null) && (odfElement.getFormNameAttribute() != null)) {
							odfElementIdx.put(odfElement.getFormIdAttribute(), odfElement);
							odfElementNamex.put(odfElement.getFormNameAttribute(), odfElement);
							//System.out.println("id:" + odfElement.getFormIdAttribute());
							//System.out.println("name:" + odfElement.getFormNameAttribute());
							if (valData.get(odfElement.getFormNameAttribute()) != null) {
								boolean trovato = false;
								NodeList options = odfElement.getElementsByTagName("form:option");
								for (int j = 0; j < options.getLength(); j++) {
									FormOptionElement optElement = (FormOptionElement)options.item(j); 
									if (optElement.getFormLabelAttribute().equals(valData.get(odfElement.getFormNameAttribute()))) {
										trovato = true;
										optElement.setFormCurrentSelectedAttribute(Boolean.TRUE);
									} else{
										optElement.setFormCurrentSelectedAttribute(Boolean.FALSE);
									}
								}								
								if(!trovato) {
									FormOptionElement optElement = ((FormListboxElement)node).newFormOptionElement();
									optElement.setFormLabelAttribute((String)valData.get(odfElement.getFormNameAttribute()));
									optElement.setFormCurrentSelectedAttribute(Boolean.TRUE);																
								}
							}
						}				
					}else if(node instanceof FormTextElement){
						formFormParentElement = node.getParentNode();
						FormTextElement odfElement = (FormTextElement)node;
						//System.out.println(odfElement.getFormIdAttribute());
						if ((odfElement.getFormIdAttribute() != null) && (odfElement.getFormNameAttribute() != null)) {						
							odfElementIdx.put(odfElement.getFormIdAttribute(), odfElement);
							odfElementNamex.put(odfElement.getFormNameAttribute(), odfElement);
							//System.out.println("id:" +  odfElement.getFormIdAttribute());
							//System.out.println("name:" + odfElement.getFormNameAttribute());
							if (valData.get(odfElement.getFormNameAttribute()) != null)
								odfElement.setFormCurrentValueAttribute((valData.get(odfElement.getFormNameAttribute()).toString()));
						}
					}else if(node instanceof FormTextareaElement){
						formFormParentElement = node.getParentNode();
						FormTextareaElement odfElement = (FormTextareaElement)node;
						if ((odfElement.getFormIdAttribute() != null) && (odfElement.getFormNameAttribute() != null)) {
							odfElementIdx.put(odfElement.getFormIdAttribute(), odfElement);
							odfElementNamex.put(odfElement.getFormNameAttribute(), odfElement);
							//System.out.println("id:" +  odfElement.getFormIdAttribute());
							//System.out.println("name:" + odfElement.getFormNameAttribute());
							if (valData.get(odfElement.getFormNameAttribute()) != null)
								odfElement.setFormCurrentValueAttribute((valData.get(odfElement.getFormNameAttribute()).toString()));
						}
					}else if(node instanceof FormCheckboxElement){
						formFormParentElement = node.getParentNode();
						FormCheckboxElement odfElement = (FormCheckboxElement)node;
						//System.out.println(odfElement.getFormIdAttribute());
						if ((odfElement.getFormIdAttribute() != null) && (odfElement.getFormNameAttribute() != null)) {							
							odfElementIdx.put(odfElement.getFormIdAttribute(), odfElement);
							odfElementNamex.put(odfElement.getFormNameAttribute(), odfElement);
							//System.out.println("id:" +  odfElement.getFormIdAttribute());
							//System.out.println("name:" + odfElement.getFormNameAttribute());
							if ((valData.get(odfElement.getFormNameAttribute()) != null) && (valData.get(odfElement.getFormNameAttribute())).toString().equals("1"))
								odfElement.setFormCurrentStateAttribute("checked");
							else
								odfElement.setFormCurrentStateAttribute("unchecked");
						}
					}else if(node instanceof FormRadioElement){
						formFormParentElement = node.getParentNode();
						FormRadioElement odfElement = (FormRadioElement)node;
						if ((odfElement.getFormIdAttribute() != null) && (odfElement.getFormNameAttribute() != null)) {
							odfElementIdx.put(odfElement.getFormIdAttribute(), odfElement);
							odfElementNamex.put(odfElement.getFormNameAttribute(), odfElement);
							//System.out.println("id:" +  odfElement.getFormIdAttribute());
							//System.out.println("name:" + odfElement.getFormNameAttribute());
							//se form:value corrisponde al valore registrato per il radio button
							if (valData.get(odfElement.getFormNameAttribute()) != null) {
								if (odfElement.getFormValueAttribute().equals((valData.get(odfElement.getFormNameAttribute())).toString()))
									odfElement.setFormCurrentSelectedAttribute(Boolean.TRUE);
								else
									odfElement.setFormCurrentSelectedAttribute(Boolean.FALSE);
							}
						}
					} 
				}
			}
			NodeList tables = odfTextDocument.getContentDom().getElementsByTagName("table:table");		
			NodeList formTexts = odfTextDocument.getContentDom().getElementsByTagName("form:text");			
			//� il parent degli elementi form:text a cui dovr� aggiungere quelli della lista
			Node formParent = null;
			if ((formTexts != null) && (formTexts.item(0) != null))
				formParent = ((FormTextElement)formTexts.item(0)).getParentNode();		
			int tablesSize = tables.getLength();
			for(int i = 0;i < tablesSize;i++){
				TableTableElement table = (TableTableElement)tables.item(i);				
				//System.out.println(table.getTableNameAttribute());				
				String tableName = table.getTableNameAttribute();
				if (tableName.startsWith(PREFIX_SEZ_RIPETUTA) || tableName.startsWith(PREFIX_TABLE)) {					
					ArrayList<HashMap<String, String>> tabellaValori = new ArrayList<HashMap<String, String>>();	
					NewSezioneCache.Variabile.Lista aLista = (NewSezioneCache.Variabile.Lista)valData.get(tableName);
					if (aLista != null) {
						List<Riga> rigaLista = aLista.getRiga();
						for (Riga aRiga : rigaLista) {
							List<Colonna> colonnaLista = aRiga.getColonna();
							HashMap<String, String> colonnaValori = new HashMap<String, String>();
							for (Colonna aColonna : colonnaLista) {
								colonnaValori.put(aColonna.getName(), aColonna.getContent());
							}
							tabellaValori.add(colonnaValori);
						}		
						for (int numRow = 0 ; numRow < tabellaValori.size() ; numRow++) {					
							//valorizzo la prima riga
							if (numRow == 0) {
								Set<String> elencoName = ((HashMap<String, String>)tabellaValori.get(0)).keySet();
								Iterator elencoNameIter = elencoName.iterator();
								while (elencoNameIter.hasNext()) {
									String name = (String) elencoNameIter.next();									
									Node odfElement = odfElementNamex.get(name);									
									if(odfElement instanceof FormListboxElement){
										FormListboxElement anElement = (FormListboxElement)odfElement;
										String formName = anElement.getFormNameAttribute();
										String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());		
										if (valore != null) {
											boolean trovato = false;
											NodeList options = anElement.getElementsByTagName("form:option");
											for (int j = 0; j < options.getLength(); j++) {
												FormOptionElement optElement = (FormOptionElement)options.item(j); 
												if (optElement.getFormLabelAttribute().equals(valore)) {
													trovato = true;
													optElement.setFormCurrentSelectedAttribute(Boolean.TRUE);
												} else{
													optElement.setFormCurrentSelectedAttribute(Boolean.FALSE);
												}
											}																		
											if(!trovato) {
												FormOptionElement optElement = ((FormListboxElement)odfElement).newFormOptionElement();
												optElement.setFormLabelAttribute(valore);
												optElement.setFormCurrentSelectedAttribute(Boolean.TRUE);																
											}
																						
										}
									}else if(odfElement instanceof FormTextElement){
										FormTextElement anElement = (FormTextElement)odfElement;
										if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {	
											String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
											anElement.setFormCurrentValueAttribute(valore);
										}
									}else if(odfElement instanceof FormTextareaElement){
										FormTextareaElement anElement = (FormTextareaElement)odfElement;
										if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {
											String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
											anElement.setFormCurrentValueAttribute(valore);
										}
									}else if(odfElement instanceof FormCheckboxElement){
										FormCheckboxElement anElement = (FormCheckboxElement)odfElement;
										if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {							
											String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
											if (valore.equals("1"))
												anElement.setFormCurrentStateAttribute("checked");	
											else
												anElement.setFormCurrentStateAttribute("unchecked");	
										}
									}else if(odfElement instanceof FormRadioElement){
										FormRadioElement anElement = (FormRadioElement)odfElement;
										if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {
											//se form:value corrisponde al valore registrato per il radio button
											String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
											if (anElement.getFormValueAttribute().equals(valore))
												anElement.setFormCurrentSelectedAttribute(Boolean.TRUE);
											else
												anElement.setFormCurrentSelectedAttribute(Boolean.FALSE);
										}
									} 
								}
							}
							//se ci sono pi� righe valorizzate devo aggiungere gli elementi corrispondenti
							if (numRow > 0) {
								NodeList rowList = table.getElementsByTagName("table:table-row");
								int rowListSize = rowList.getLength();
								//for (int rlNum = 0; rlNum < rowListSize; rlNum++) {
									TableTableRowElement existentRow = (TableTableRowElement) rowList.item(0);
									if(tableName.startsWith(PREFIX_TABLE)){
										existentRow = (TableTableRowElement) rowList.item(1);
									}									
									TableTableRowElement clonedRow = (TableTableRowElement)existentRow.cloneNode(true);
									NodeList drawControls = clonedRow.getElementsByTagName("draw:control");
									//se non ci sono draw:control sono sulla riga del titolo -> continue
									if (drawControls.getLength() == 0)
										continue;									
									TableTableRowElement newRow = table.newTableTableRowElement();									
									for (int dcNum = 0; dcNum < drawControls.getLength(); dcNum++) {
										DrawControlElement dcElement = (DrawControlElement)drawControls.item(dcNum);
										String oldName = dcElement.getDrawControlAttribute();
										addedControlCounter++;
										String name = addedControl + addedControlCounter;
										dcElement.setDrawControlAttribute(name);
										//aggiungo un form:text clonandolo dal corrispondente 
										Node odfElementToClone = odfElementIdx.get(oldName);
										Node odfElement = odfElementToClone.cloneNode(true);																				
										if(odfElement instanceof FormListboxElement){
											FormListboxElement anElement = (FormListboxElement)odfElement;
											//String formName = anElement.getFormNameAttribute();
											anElement.setFormIdAttribute(name);
											anElement.setXmlIdAttribute(name);
											String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
											if (valore != null) {
												boolean trovato = false;
												NodeList options = anElement.getElementsByTagName("form:option");
												for (int j = 0; j < options.getLength(); j++) {
													FormOptionElement optElement = (FormOptionElement)options.item(j); 
													if (optElement.getFormLabelAttribute().equals(valore)) {
														trovato = true;
														optElement.setFormCurrentSelectedAttribute(Boolean.TRUE);
													} else{
														optElement.setFormCurrentSelectedAttribute(Boolean.FALSE);
													}
												}																		
												if(!trovato) {
													FormOptionElement optElement = ((FormListboxElement)odfElement).newFormOptionElement();
													optElement.setFormLabelAttribute(valore);
													optElement.setFormCurrentSelectedAttribute(Boolean.TRUE);																
												}																							
											}		
										}else if(odfElement instanceof FormTextElement){
											FormTextElement anElement = (FormTextElement)odfElement;
											if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {
												anElement.setFormIdAttribute(name);
												anElement.setXmlIdAttribute(name);		
												String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
												anElement.setFormCurrentValueAttribute(valore);
											}
										}else if(odfElement instanceof FormTextareaElement){
											FormTextareaElement anElement = (FormTextareaElement)odfElement;
											if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {
												anElement.setFormIdAttribute(name);
												anElement.setXmlIdAttribute(name);												
												String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
												anElement.setFormCurrentValueAttribute(valore);
											}
										}else if(odfElement instanceof FormCheckboxElement){
											FormCheckboxElement anElement = (FormCheckboxElement)odfElement;
											if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {							
												anElement.setFormIdAttribute(name);
												anElement.setXmlIdAttribute(name);
												String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
												if (valore.equals("1"))
													anElement.setFormCurrentStateAttribute("checked");
												else
													anElement.setFormCurrentStateAttribute("unchecked");		
											}
										}else if(odfElement instanceof FormRadioElement){
											FormRadioElement anElement = (FormRadioElement)odfElement;
											if ((anElement.getFormIdAttribute() != null) && (anElement.getFormNameAttribute() != null)) {
												anElement.setFormIdAttribute(name);
												anElement.setXmlIdAttribute(name);
												//se form:value corrisponde al valore registrato per il radio button
												String valore = tabellaValori.get(numRow).get(anElement.getFormNameAttribute());
												if (anElement.getFormValueAttribute().equals(valore))
													anElement.setFormCurrentSelectedAttribute(Boolean.TRUE);
												else
													anElement.setFormCurrentSelectedAttribute(Boolean.FALSE);		
											}
										} 
										formFormParentElement.appendChild(odfElement);
									}							
									table.replaceChild(clonedRow, newRow);
								//}
							}
						}
					}
				}
			}
			odfTextDocument.save(tempBuildFile);			
		}catch(Exception e){
			throw e;
		}finally{
			if(odfTextDocument!=null){
				odfTextDocument.close();
			}
			if(anOdfPackeage!=null){
				anOdfPackeage.close();
			}
		}
		return tempBuildFile;
	}

}
