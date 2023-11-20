/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskProtocolloFileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "TaskDettUdGenDataSource")
public class TaskDettUdGenDataSource extends AbstractServiceDataSource<ProtocollazioneBean, TaskFileDaFirmareBean>{

	private static Logger mLogger = Logger.getLogger(TaskDettUdGenDataSource.class);

	public TaskFileDaFirmareBean getFileDaFirmare(ProtocollazioneBean pProtocollazioneBean) throws StorageException, Exception{
		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		lTaskFileDaFirmareBean.setFiles(getListaFileDaFirmare(pProtocollazioneBean, fileDaTimbrare));
		return lTaskFileDaFirmareBean;		
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaFirmare(ProtocollazioneBean pProtocollazioneBean, boolean fileDaTimbrare) throws StorageException, Exception{
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Aggiungo il primario
		if (StringUtils.isNotBlank(pProtocollazioneBean.getUriFilePrimario())){
			aggiungiPrimario(listaFileDaFirmare, pProtocollazioneBean, fileDaTimbrare);
		}
		if (pProtocollazioneBean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(pProtocollazioneBean.getFilePrimarioOmissis().getUriFile())){
			aggiungiPrimarioOmissis(listaFileDaFirmare, pProtocollazioneBean, fileDaTimbrare);
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pProtocollazioneBean.getListaAllegati()!=null && pProtocollazioneBean.getListaAllegati().size()>0){
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pProtocollazioneBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo()!=null && lAllegatoProtocolloBean.getFlgParteDispositivo()){
					String idUd = pProtocollazioneBean.getIdUd() != null ? String.valueOf(pProtocollazioneBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())){
						aggiungiAllegato(listaFileDaFirmare, lAllegatoProtocolloBean, fileDaTimbrare);
					}
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						aggiungiAllegatoOmissis(listaFileDaFirmare, lAllegatoProtocolloBean, fileDaTimbrare);
					}
				}
			}
		}
		return listaFileDaFirmare;
	}	
	
	private void aggiungiPrimario(ArrayList<FileDaFirmareBean> listaFileDaFirmare, ProtocollazioneBean pProtocollazioneBean, boolean fileDaTimbrare) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		FileDaFirmareBean lFileDaFirmareBean  = FilePrimarioUtility.generateFilePrimario(pProtocollazioneBean, pProtocollazioneBean.getIdProcess(), loginBean, getLocale(), getSession());
		String idUd = pProtocollazioneBean.getIdUd() != null ? String.valueOf(pProtocollazioneBean.getIdUd()) : null;
		if(listaFileDaFirmare == null) {
			listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		}
		if (fileDaTimbrare && StringUtils.isNotBlank(idUd)) {			
			listaFileDaFirmare.add(timbraFile(lFileDaFirmareBean, idUd));
		} else {
			listaFileDaFirmare.add(lFileDaFirmareBean);
		}		
	}
	
	private void aggiungiPrimarioOmissis(ArrayList<FileDaFirmareBean> listaFileDaFirmare, ProtocollazioneBean pProtocollazioneBean, boolean fileDaTimbrare) throws Exception {
		if(pProtocollazioneBean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(pProtocollazioneBean.getFilePrimarioOmissis().getUriFile())) {
			FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
			lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis" + pProtocollazioneBean.getFilePrimarioOmissis().getUriFile());
			lFileDaFirmareBeanOmissis.setInfoFile(pProtocollazioneBean.getFilePrimarioOmissis().getInfoFile());
			lFileDaFirmareBeanOmissis.setNomeFile(pProtocollazioneBean.getFilePrimarioOmissis().getNomeFile());
			lFileDaFirmareBeanOmissis.setUri(pProtocollazioneBean.getFilePrimarioOmissis().getUriFile());	
			lFileDaFirmareBeanOmissis.setIsFilePrincipaleAtto(true);
			String idUd = pProtocollazioneBean.getIdUd() != null ? String.valueOf(pProtocollazioneBean.getIdUd()) : null;
			if(listaFileDaFirmare == null) {
				listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
			}
			if (fileDaTimbrare && StringUtils.isNotBlank(idUd)) {			
				listaFileDaFirmare.add(timbraFile(lFileDaFirmareBeanOmissis, idUd));
			} else {
				listaFileDaFirmare.add(lFileDaFirmareBeanOmissis);
			}
		}
	}

	private void aggiungiAllegato(ArrayList<FileDaFirmareBean> listaFileDaFirmare, AllegatoProtocolloBean lAllegatoProtocolloBean, boolean fileDaTimbrare) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
			FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
			lFileDaFirmareBean.setIdFile("allegato" + lAllegatoProtocolloBean.getUriFileAllegato());
			lFileDaFirmareBean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
			lFileDaFirmareBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
			lFileDaFirmareBean.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
			lFileDaFirmareBean.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirma());
			if(listaFileDaFirmare == null) {
				listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
			}
			if (fileDaTimbrare && StringUtils.isNotBlank(lAllegatoProtocolloBean.getIdUdAppartenenza())) {			
				listaFileDaFirmare.add(timbraFile(lFileDaFirmareBean, lAllegatoProtocolloBean.getIdUdAppartenenza()));
			} else {
				listaFileDaFirmare.add(lFileDaFirmareBean);
			}		
		}
	}
	
	private void aggiungiAllegatoOmissis(ArrayList<FileDaFirmareBean> listaFileDaFirmare, AllegatoProtocolloBean lAllegatoProtocolloBean, boolean fileDaTimbrare) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
			FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
			lFileDaFirmareBeanOmissis.setIdFile("allegatoOmissis" + lAllegatoProtocolloBean.getUriFileOmissis());
			lFileDaFirmareBeanOmissis.setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
			lFileDaFirmareBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
			lFileDaFirmareBeanOmissis.setUri(lAllegatoProtocolloBean.getUriFileOmissis());
			lFileDaFirmareBeanOmissis.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis());
			if(listaFileDaFirmare == null) {
				listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
			}
			if (fileDaTimbrare && StringUtils.isNotBlank(lAllegatoProtocolloBean.getIdUdAppartenenza())) {			
				listaFileDaFirmare.add(timbraFile(lFileDaFirmareBeanOmissis, lAllegatoProtocolloBean.getIdUdAppartenenza()));
			} else {
				listaFileDaFirmare.add(lFileDaFirmareBeanOmissis);
			}		
		}
	}
	
	public ProtocollazioneBean aggiornaFileFirmati(TaskProtocolloFileFirmatiBean pTaskProtocolloFileFirmatiBean) throws Exception{
		ProtocollazioneBean lProtocollazioneBean = pTaskProtocolloFileFirmatiBean.getProtocolloOriginale();
		boolean firmaNonValida = false;
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles()){
			String idFile = lFileDaFirmareBean.getIdFile();
			if(lFileDaFirmareBean.getInfoFile().isFirmato() && !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
				if (idFile.startsWith("primarioOmissis")) {
					mLogger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
							+ lFileDaFirmareBean.getUri());
				} else if (idFile.startsWith("primario")) {
					mLogger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
							+ lFileDaFirmareBean.getUri());
				} else if (idFile.startsWith("allegatoOmissis")) {
					mLogger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
							+ lFileDaFirmareBean.getUri());
				} else if (idFile.startsWith("allegato")) {
					mLogger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
							+ lFileDaFirmareBean.getUri());
				}
				firmaNonValida = true;
			}
			if (idFile.startsWith("primarioOmissis")) {
				aggiornaPrimarioOmissisFirmato(lProtocollazioneBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("primario")) {
				aggiornaPrimarioFirmato(lProtocollazioneBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("allegatoOmissis")) {
				aggiornaAllegatoOmissisFirmato(lProtocollazioneBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("allegato")) {
				aggiornaAllegatoFirmato(lProtocollazioneBean, lFileDaFirmareBean);
			} 				
		}
		if(firmaNonValida) {
			throw new StoreException("La firma di uno o più file risulta essere non valida");
		}
		return lProtocollazioneBean;
	}
	
	public ProtocollazioneBean aggiornaFile(TaskProtocolloFileFirmatiBean pTaskProtocolloFileFirmatiBean) throws Exception{
		ProtocollazioneBean lProtocollazioneBean = pTaskProtocolloFileFirmatiBean.getProtocolloOriginale();
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskProtocolloFileFirmatiBean.getFileFirmati().getFiles()){	
			String idFile = lFileDaFirmareBean.getIdFile();
			if (idFile.startsWith("primarioOmissis")) {
				aggiornaPrimarioOmissis(lProtocollazioneBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("primario")) {
				aggiornaPrimario(lProtocollazioneBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("allegatoOmissis")) {
				aggiornaAllegatoOmissis(lProtocollazioneBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("allegato")) {
				aggiornaAllegato(lProtocollazioneBean, lFileDaFirmareBean);
			} 
		}		
		return lProtocollazioneBean;
	}
	
	public ProtocolloDataSource getProtocolloDataSource() {	
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource();
		lProtocolloDataSource.setSession(getSession());
		Map<String, String> extraparams = new LinkedHashMap<String, String>();
		extraparams.put("isTaskDettUdGen", "true");
		lProtocolloDataSource.setExtraparams(extraparams);	
		return lProtocolloDataSource;
	}
	
	private void aggiornaPrimarioFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		if(lProtocollazioneBean.getIsDocPrimarioChanged() != null && lProtocollazioneBean.getIsDocPrimarioChanged()) {
			// Prima salvo la versione pre firma se è stata modificata
			getProtocolloDataSource().aggiornaFilePrimario(lProtocollazioneBean);
		}
		aggiornaPrimario(lProtocollazioneBean, lFileDaFirmareBean);
	}
	
	private void aggiornaPrimarioOmissisFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		if(lProtocollazioneBean.getFilePrimarioOmissis() != null && lProtocollazioneBean.getFilePrimarioOmissis().getIsChanged() != null && lProtocollazioneBean.getFilePrimarioOmissis().getIsChanged()) {
			// Prima salvo la versione pre firma se è stata modificata
			getProtocolloDataSource().aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		aggiornaPrimarioOmissis(lProtocollazioneBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaAllegatoFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource();
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				if(lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegato(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegato(lProtocollazioneBean, lFileDaFirmareBean);
	}
	
	private void aggiornaAllegatoOmissisFirmato(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource();
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				if(lAllegatoProtocolloBean.getIsChangedOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegatoOmissis(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegatoOmissis(lProtocollazioneBean, lFileDaFirmareBeanOmissis);
	}

	private void aggiornaPrimario(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBean) {
		lProtocollazioneBean.setUriFilePrimario(lFileDaFirmareBean.getUri());
		lProtocollazioneBean.setNomeFilePrimario(lFileDaFirmareBean.getNomeFile());
		lProtocollazioneBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
		lProtocollazioneBean.setIsDocPrimarioChanged(true);
	}

	private void aggiornaPrimarioOmissis(ProtocollazioneBean lProtocollazioneBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) {
		if(lProtocollazioneBean.getFilePrimarioOmissis() == null) {
			lProtocollazioneBean.setFilePrimarioOmissis(new DocumentBean());
		}
		lProtocollazioneBean.getFilePrimarioOmissis().setUriFile(lFileDaFirmareBeanOmissis.getUri());
		lProtocollazioneBean.getFilePrimarioOmissis().setNomeFile(lFileDaFirmareBeanOmissis.getNomeFile());
		lProtocollazioneBean.getFilePrimarioOmissis().setInfoFile(lFileDaFirmareBeanOmissis.getInfoFile());
		lProtocollazioneBean.getFilePrimarioOmissis().setIsChanged(true);
	}
	
	private void aggiornaAllegato(ProtocollazioneBean lProtocollazioneBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()){
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)){
				lAllegatoProtocolloBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
				lAllegatoProtocolloBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
				lAllegatoProtocolloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
				lAllegatoProtocolloBean.setIsChanged(true);
				break;
			}
		}
	}
	
	private void aggiornaAllegatoOmissis(ProtocollazioneBean lProtocollazioneBean,	FileDaFirmareBean lFileDaFirmareBeanOmissis) {
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lProtocollazioneBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				lAllegatoProtocolloBean.setUriFileOmissis(lFileDaFirmareBeanOmissis.getUri());
				lAllegatoProtocolloBean.setNomeFileOmissis(lFileDaFirmareBeanOmissis.getNomeFile());
				lAllegatoProtocolloBean.setInfoFileOmissis(lFileDaFirmareBeanOmissis.getInfoFile());
				lAllegatoProtocolloBean.setIsChangedOmissis(true);
				break;
			}
		}
	}
	
	public FileDaFirmareBean unioneFile(ProtocollazioneBean pProtocollazioneBean) throws StorageException, Exception{
		try {
			mLogger.debug("UNIONE DEI FILE");
			List<FileDaFirmareBean> lListFileDaUnireBean = getListaFileDaFirmare(pProtocollazioneBean, false);
			if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {
				List<InputStream> lListInputStream = new ArrayList<InputStream>(lListFileDaUnireBean.size());
				for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean) {
					mLogger.debug("File " + lFileDaUnireBean.getNomeFile() + ": " + lFileDaUnireBean.getUri());
					if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
						mLogger.debug("Il file è firmato in CAdES");
						FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaUnireBean);
						if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
							mLogger.debug("Il file sbustato è un pdf, quindi lo aggiungo");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoBean.getUri()));
						} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
							mLogger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
							mLogger.debug("mimetype: " + lFileDaUnireBean.getInfoFile().getMimetype());							
							try {
								FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
								lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoConvertitoBean.getUri()));	
							} catch (Exception e) {
								String errorMessage = "Errore durante la conversione del file sbustato";
								if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
									errorMessage = "Errore durante la conversione del file sbustato " + lFileSbustatoBean.getNomeFile();
								}
								mLogger.error(errorMessage + " : " + e.getMessage(), e);
								throw new StoreException(errorMessage);
							}
						} else {
							String errorMessage = "Il file sbustato non è un pdf e non è convertibile.";
							if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
								errorMessage = "Il file sbustato " + lFileSbustatoBean.getNomeFile() + " non è un pdf e non è convertibile.";
							}
							mLogger.error(errorMessage);
							throw new StoreException(errorMessage);
						}
					} else if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
						if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().equalsIgnoreCase("PADES")) {
							mLogger.debug("Il file è firmato in PAdES quindi devo prendere la versione precedente la firma");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriVerPreFirma()));
						} else {
							mLogger.debug("Il file è un pdf, quindi lo aggiungo");
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUri()));
						}
					} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
						mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
						try {
							FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaUnireBean);
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileConvertitoBean.getUri()));	
						} catch (Exception e) {
							String errorMessage = "Errore durante la conversione del file";
							if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
								errorMessage = "Errore durante la conversione del file " + lFileDaUnireBean.getNomeFile();
							}
							mLogger.error(errorMessage + " : " + e.getMessage(), e);
							throw new StoreException(errorMessage);
						}
					} else {
						String errorMessage = "Il file non è un pdf e non è convertibile.";
						if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
							errorMessage = "Il file " + lFileDaUnireBean.getNomeFile() + " non è un pdf e non è convertibile.";
						}
						mLogger.error(errorMessage);
						throw new StoreException(errorMessage);
					}					
				}
				File lFileUnione = unioneFilePdf(lListInputStream);
				String nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : "UnioneFile.pdf";
				String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
				FileDaFirmareBean lFileUnioneBean = new FileDaFirmareBean();
				lFileUnioneBean.setUri(uriFileUnione);
				lFileUnioneBean.setNomeFile(nomeFileUnione);		
				lFileUnioneBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione, false, null));
				String idUd = pProtocollazioneBean.getIdUd() != null ? String.valueOf(pProtocollazioneBean.getIdUd()) : null;
				boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;
				if (fileDaTimbrare && StringUtils.isNotBlank(idUd)) {			
					return timbraFile(lFileUnioneBean, idUd);
				} else {
					return lFileUnioneBean;
				}						
			} else {
				String errorMessage = "E' obbligatorio inserire almeno un file per procedere";
				mLogger.error(errorMessage);
				throw new StoreException(errorMessage);
			}			
		}  catch(StoreException e) {
			mLogger.error("Si è verificato un errore durante l'unione dei file. " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante l'unione dei file. " + e.getMessage());
		} catch (Exception e1) {
			mLogger.error("Si è verificato un errore durante l'unione dei file. " + e1.getMessage(), e1);
			throw new StoreException("Si è verificato un errore durante l'unione dei file.");
		}	
	}
	
	public File unioneFilePdf(List<InputStream> lListInputStream) throws Exception {
		Document document = new Document();
		// Istanzio una copia nell'output
		File lFile = File.createTempFile("pdf", ".pdf");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(lFile));
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (InputStream lInputStream : lListInputStream) {
			// Leggo il file
			reader = new PdfReader(lInputStream);
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
		}
		// Chiudo il documento
		document.close();
		return lFile;
	}

	private FileDaFirmareBean timbraFile(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		mLogger.debug("TIMBRO FILE");		
		mLogger.debug("File " + lFileDaTimbrareBean.getNomeFile() + ": " + lFileDaTimbrareBean.getUri());
		if (StringUtils.isNotBlank(idUd)) {			
			mLogger.debug("idUd: " + idUd);
			if (lFileDaTimbrareBean.getInfoFile().isFirmato() && lFileDaTimbrareBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
				mLogger.debug("Il file è firmato in CAdES");
				FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaTimbrareBean);
				if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
					mLogger.debug("Il file sbustato è un pdf, quindi lo timbro");
					return timbraFilePdf(lFileSbustatoBean, idUd);						
				} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
					mLogger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
					mLogger.debug("mimetype: " + lFileDaTimbrareBean.getInfoFile().getMimetype());							
					try {
						FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
						return timbraFilePdf(lFileSbustatoConvertitoBean, idUd);	
					} catch (Exception e) {
						mLogger.debug("Errore durante la conversione del file sbustato: " + e.getMessage(), e);
					}
				} else {
					mLogger.debug("Il file sbustato non è un pdf e non è convertibile, quindi non lo timbro");
				}
			} else if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
				mLogger.debug("Il file è un pdf, quindi lo timbro");
				return timbraFilePdf(lFileDaTimbrareBean, idUd);		
			} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
				mLogger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
				try {
					FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaTimbrareBean);
					return timbraFilePdf(lFileConvertitoBean, idUd);	
				} catch (Exception e) {
					mLogger.debug("Errore durante la conversione del file: " + e.getMessage(), e);
				}
			} else {
				mLogger.debug("Il file non è un pdf e non è convertibile, quindi non lo timbro");
			}							
		} else {
			mLogger.debug("idUd non valorizzata, quindi non lo timbro");
		}
		return lFileDaTimbrareBean;
	}
	
	private FileDaFirmareBean convertiFile(FileDaFirmareBean lFileDaConvertireBean) throws Exception{
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(lFileDaConvertireBean.getUri());
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));				
		String nomeFile = lFileDaConvertireBean.getInfoFile().getCorrectFileName() != null ? lFileDaConvertireBean.getInfoFile().getCorrectFileName() : "";
		String nomeFilePdf = FilenameUtils.getBaseName(nomeFile) + ".pdf";
		String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(lFile.toURI().toString(), nomeFile));
		lFileDaConvertireBean.setNomeFile(nomeFilePdf);
		lFileDaConvertireBean.setUri(uriPdf);
		lFileDaConvertireBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaConvertireBean;			
	}
	
	private FileDaFirmareBean sbustaFile(FileDaFirmareBean lFileDaSbustareBean) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(lFileDaSbustareBean.getUri());				
		String nomeFile = lFileDaSbustareBean.getInfoFile().getCorrectFileName() != null ? lFileDaSbustareBean.getInfoFile().getCorrectFileName() : "";		
		String nomeFileSbustato = (nomeFile != null && nomeFile.toLowerCase().endsWith(".p7m")) ? nomeFile.substring(0, nomeFile.length() - 4) : nomeFile;		
		String uriSbustato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(lFile.toURI().toString(), nomeFile));		
		lFileDaSbustareBean.setNomeFile(nomeFileSbustato);
		lFileDaSbustareBean.setUri(uriSbustato);
		lFileDaSbustareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaSbustareBean;				
	}
	
	private FileDaFirmareBean timbraFilePdf(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype("application/pdf");
		lOpzioniTimbroBean.setUri(lFileDaTimbrareBean.getUri());
		lOpzioniTimbroBean.setNomeFile(lFileDaTimbrareBean.getNomeFile());
		lOpzioniTimbroBean.setIdUd(idUd);
		lOpzioniTimbroBean.setRemote(true);
		TimbraUtility timbraUtility = new TimbraUtility();
		lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());
		
		// Setto i parametri del timbro utilizzando dal config.xml il bean OpzioniTimbroAutoDocRegBean
		try{
			OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
			if(lOpzTimbroAutoDocRegBean != null){
				lOpzioniTimbroBean.setPosizioneTimbro(lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn");
				lOpzioniTimbroBean.setRotazioneTimbro(lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale");
				if (lOpzTimbroAutoDocRegBean.getPaginaTimbro() != null) {
					if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null) {
						lOpzioniTimbroBean.setTipoPagina(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value());
					} else if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine() != null) {
						lOpzioniTimbroBean.setTipoPagina("intervallo");
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa()));
						}
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaA()));
						}
					}
				}
				lOpzioniTimbroBean.setTimbroSingolo(lOpzTimbroAutoDocRegBean.isTimbroSingolo());
				lOpzioniTimbroBean.setMoreLines(lOpzTimbroAutoDocRegBean.isRigheMultiple());
			}
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			mLogger.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}

		// Timbro il file
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBean.setUri(lTimbraResultBean.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}		
		File lFileTimbrato = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBean.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);
		lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);				
		return lFileDaTimbrareBean;
	}
	
	@Override
	public TaskFileDaFirmareBean call(ProtocollazioneBean pInBean)
			throws Exception {
		return null;
	}

}
