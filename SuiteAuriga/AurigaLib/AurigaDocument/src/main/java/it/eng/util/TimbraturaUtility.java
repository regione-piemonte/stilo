/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoadfilepertimbroconbustaBean;
import it.eng.auriga.database.store.dmpk_repository_gui.store.Loadfilepertimbroconbusta;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.FilePerBustaConTimbroBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.filePerBustaTimbro.InfoFilePerBustaTimbro;
import it.eng.document.function.StoreException;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.util.bean.ModelliDocXmlBean;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.xml.XmlListaUtility;

public class TimbraturaUtility {
	
	private static final Logger log = Logger.getLogger(TimbraturaUtility.class);
	
	public static File creaBustaPdf(AurigaLoginBean loginBean, String idUd, String idDoc, String finalita, File fileBusta, String nomeFileBusta, boolean flgPerPubblicazione) throws Exception {
		
		List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta = recuperaFilePerBustaTimbro(idUd, idDoc, loginBean, fileBusta, nomeFileBusta, flgPerPubblicazione);			
		
		File bustaPdfFile = creaTimbraturaBustaPdf(loginBean, idUd, idDoc, finalita, listaFileDaAggiungereAllaBusta);
		
		return bustaPdfFile;
	}
	
	private static List<InfoFilePerBustaTimbro> recuperaFilePerBustaTimbro(String idUd, String idDoc, AurigaLoginBean loginBean, File fileBusta, String nomeFileBusta, boolean flgPerPubblicazione) throws Exception {
		try {
			List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta = new ArrayList<InfoFilePerBustaTimbro>();

				FilePerBustaConTimbroBean result = callStoreDmpkEepositoryGuiLoadFilePerTimbroConBusta(idUd, idDoc, loginBean);
							
				if(result.isFlgVersionePubblicabile() && flgPerPubblicazione) {
					if(result.getListaFilePerBustaTimbro()!=null && result.getListaFilePerBustaTimbro().size()>0) {						
						for(InfoFilePerBustaTimbro filePerBustaTimbro : result.getListaFilePerBustaTimbro()) {								
							if(filePerBustaTimbro.getVersione().contains("P")){
								listaFileDaAggiungereAllaBusta.add(filePerBustaTimbro);
							}								
						}
					}else {    /*Se la store non mi ritorna file devo aggiungere solo quello sulla quale ho selezionato l'operazione di timbratura*/
						InfoFilePerBustaTimbro fileTimbrato = new InfoFilePerBustaTimbro();
						fileTimbrato.setFile(fileBusta);
						fileTimbrato.setNomeFile(nomeFileBusta);
						
						listaFileDaAggiungereAllaBusta.add(fileTimbrato);
					}
				}else {
					if(result.getListaFilePerBustaTimbro()!=null && result.getListaFilePerBustaTimbro().size()>0) {						
						for(InfoFilePerBustaTimbro filePerBustaTimbro : result.getListaFilePerBustaTimbro()) {								
							if(filePerBustaTimbro.getVersione().contains("I")){
								listaFileDaAggiungereAllaBusta.add(filePerBustaTimbro);
							}								
						}
					}else {    /*Se la store non mi ritorna file devo aggiungere solo quello sulla quale ho selezionato l'operazione di timbratura*/
						InfoFilePerBustaTimbro fileTimbrato = new InfoFilePerBustaTimbro();
						fileTimbrato.setFile(fileBusta);
						fileTimbrato.setNomeFile(nomeFileBusta);
						
						listaFileDaAggiungereAllaBusta.add(fileTimbrato);
					}
				}			
			
			
			for(InfoFilePerBustaTimbro infoFileBustaTimbro : listaFileDaAggiungereAllaBusta) {
				String uriFile = infoFileBustaTimbro.getUriFile();
				File fileBustaTimbro = DocumentStorage.extract(uriFile, null);
				infoFileBustaTimbro.setFile(fileBustaTimbro);
			}
			
			return listaFileDaAggiungereAllaBusta;
		} catch (Exception e) {
			throw new Exception("Errore durante il recupero dei file da allegare alla busta pdf: " + e.getMessage(), e);
		}
	}
	
	private static File creaTimbraturaBustaPdf(AurigaLoginBean loginBean, String idUd, String idDoc, String finalita, List<InfoFilePerBustaTimbro> listaFileDaAggiungereAllaBusta) throws Exception {
		try {
			
			File pdfModelloTimbratura = creaModelloTimbraturaBusta(loginBean, idDoc, idUd, finalita);			
			
			File bustaTimbrataFile = addAttacchToPdf(pdfModelloTimbratura, listaFileDaAggiungereAllaBusta);
			
			return bustaTimbrataFile;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}			
	}
	
	private static File creaModelloTimbraturaBusta(AurigaLoginBean loginBean, String idDoc, String idUd, String finalita) throws Exception {
		
		try {
			ModelliDocXmlBean modelloDocBean = ModelliUtil.recuperaModello(loginBean, "BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE");
					
			String sezioneCacheModello = ModelliUtil.getSezioneCacheModello(loginBean, idUd, idDoc, finalita, "BUSTA_ACCOMPAGNAMENTO_CON_TIMBRO_REGISTRAZIONE");
			
			List<String> listaValoriTimbroModello = new ArrayList<>();
			listaValoriTimbroModello.add("segnaturaRegInTimbro");
			listaValoriTimbroModello.add("improntaDoc");
			
			File modelloTimbratura = ModelliUtil.generaModelloPdf(modelloDocBean, sezioneCacheModello, listaValoriTimbroModello, null, true);

			return modelloTimbratura;
		} catch (Exception e) {
			throw new Exception("Errore durante la creazione del modello per la busta pdf: " + e.getMessage(), e);		
		}				

	}
	
	public static File addAttacchToPdf (File pdfModelloTimbratura, List<InfoFilePerBustaTimbro> listaFileDaAggiungereBustaTimbro) throws Exception {
		try {
			File resultFile = File.createTempFile("tmp", ".pdf");
			
			PdfReader reader = new PdfReader(new FileInputStream(pdfModelloTimbratura));
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(resultFile));
			
			int i = 1;
			for(InfoFilePerBustaTimbro fileDaAggiungereBusta : listaFileDaAggiungereBustaTimbro) {	    	
				File fileAllegato = fileDaAggiungereBusta.getFile();
				String nomeFileAllegato = fileDaAggiungereBusta.getNomeFile();
				
				byte [] byteAllegato = FileUtils.readFileToByteArray(fileAllegato);
				PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(
			            stamper.getWriter(), null, nomeFileAllegato, byteAllegato);
			    stamper.addFileAttachment(StringUtils.isNotBlank(fileDaAggiungereBusta.getDescrizione()) ? fileDaAggiungereBusta.getDescrizione() : "File" + i, fs);
			    i++;
			}
			
			if(stamper!=null) {
				stamper.close();
			}
			if(reader!=null) {
				reader.close();
			}
			
			return resultFile;
		} catch (Exception e) {
			throw new Exception("Errore durante la creazione della busta di timbratura per il file firmato: " + e.getMessage(), e);	
		}
	}
	
	private static FilePerBustaConTimbroBean callStoreDmpkEepositoryGuiLoadFilePerTimbroConBusta(String idUd, String idDoc, AurigaLoginBean loginBean) throws Exception {		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRepositoryGuiLoadfilepertimbroconbustaBean input = new DmpkRepositoryGuiLoadfilepertimbroconbustaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdudin(idUd != null && !"".equals(idUd) ? new BigDecimal(idUd) : null);
		input.setIddocin(idDoc != null && !"".equals(idDoc) ? new BigDecimal(idDoc) : null);

		Loadfilepertimbroconbusta store = new Loadfilepertimbroconbusta();
		StoreResultBean<DmpkRepositoryGuiLoadfilepertimbroconbustaBean> output = store.execute(loginBean,input);

		// Leggo l'esito
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException("Errore durante recupero informazioni per la busta pdf del timbro: " + output.getDefaultMessage());			
		} 
		
		FilePerBustaConTimbroBean result = new FilePerBustaConTimbroBean();
				
		// Restituisco i dati
		result.setFlgVersionePubblicabile(output.getResultBean().getFlgpubblconomissisout()==1 ? true : false);                                 
        
		if (output.getResultBean().getListafileout() != null && !output.getResultBean().getListafileout().equalsIgnoreCase("")){
			List<InfoFilePerBustaTimbro> listaFileDaAggiungereBusta    = new ArrayList<InfoFilePerBustaTimbro>();
			listaFileDaAggiungereBusta = XmlListaUtility.recuperaLista(output.getResultBean().getListafileout(), InfoFilePerBustaTimbro.class);
			result.setListaFilePerBustaTimbro(listaFileDaAggiungereBusta);
		}		
				
		return result;
	}
	
	private static Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}

}
