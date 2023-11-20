/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SimpleBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AggiornaInfoFileBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.CalcolaFirmaBean;
import it.eng.auriga.ui.module.layout.server.task.bean.InfoFirmaGraficaBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtils;
import it.eng.utility.digest.DigestCtrl;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.AppletMultiplaBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "CalcolaFirmaDatasource")
public class CalcolaFirmaDatasource extends AbstractServiceDataSource<SimpleBean, MimeTypeFirmaBean> {
	
	private static final Logger log = Logger.getLogger(CalcolaFirmaDatasource.class);

	@Override
	public MimeTypeFirmaBean call(SimpleBean pInBean) throws Exception {
		throw new UnsupportedOperationException("Operazione non supportata");
	}
	
	public AggiornaInfoFileBean aggiornaInfoFileFirmati(AggiornaInfoFileBean bean) {
		HashMap<String, CalcolaFirmaBean> nuovaMappaFileFirmati = new HashMap<String, CalcolaFirmaBean>();;
		HashMap<String, CalcolaFirmaBean> mappaFileFirmati = bean.getFileFirmati();
		List<CalcolaFirmaBean> listaFileVerPreFirma = bean.getFileVerPreFirma();
		
		Set<String> idFileSet = mappaFileFirmati.keySet();
		if (idFileSet != null) {
			for (String idFile : idFileSet) {
				CalcolaFirmaBean fileVerPreFirma = null;
				for (CalcolaFirmaBean file : listaFileVerPreFirma) {
					String idFileVerPreFirma = file.getIdFile();
					if (idFileVerPreFirma != null && idFileVerPreFirma.equalsIgnoreCase(idFile)){
						fileVerPreFirma = file;
					}
				}
				
				MimeTypeFirmaBean infoFilePreFirma = null;
				if (fileVerPreFirma != null && fileVerPreFirma.getInfoFile() != null) {
					infoFilePreFirma = fileVerPreFirma.getInfoFile();
				}
				CalcolaFirmaBean fileFirmato = mappaFileFirmati.get(idFile);
				MimeTypeFirmaBean infoFileFirmato = fileFirmato.getInfoFile();
				boolean presentiFirmeExtraAuriga = infoFilePreFirma != null && infoFilePreFirma.getInfoFirmaMarca() != null ? infoFilePreFirma.getInfoFirmaMarca().isFirmeExtraAuriga() : false;
				if (infoFileFirmato != null && infoFileFirmato.getFirmatari() != null){
					int numeroFirmatariBustaEsterna = 0;
					Firmatari firmatarioPiuRecente =  null;
					for (Firmatari bustaFileFirmato : infoFileFirmato.getBuste()) {
						if (!presentiFirmeExtraAuriga){
							bustaFileFirmato.setFirmaExtraAuriga(false);
						}
						if ("1".equalsIgnoreCase(bustaFileFirmato.getFiglioDi())) {
							numeroFirmatariBustaEsterna ++;
						}
						if (firmatarioPiuRecente == null || (bustaFileFirmato.getDataFirma() != null && firmatarioPiuRecente.getDataFirma() != null && bustaFileFirmato.getDataFirma().before(firmatarioPiuRecente.getDataFirma()))){
							firmatarioPiuRecente = bustaFileFirmato;
						}
					}
					// Se sono in firma verticale la busta l'ha fatta Auriga, altrimenti lascio il valore di bustaCrittograficaFattaDaAuriga inalterato
					if (numeroFirmatariBustaEsterna == 1 && infoFileFirmato.getInfoFirmaMarca() != null) {
						infoFileFirmato.getInfoFirmaMarca().setBustaCrittograficaFattaDaAuriga(true);
					} else {
						if (infoFilePreFirma != null && infoFilePreFirma.getInfoFirmaMarca() != null) {
							infoFileFirmato.getInfoFirmaMarca().setBustaCrittograficaFattaDaAuriga(infoFilePreFirma.getInfoFirmaMarca().isBustaCrittograficaFattaDaAuriga());
						}
					}
					// Il firmatario più recente ha sicuramente firmato su Auriga
					firmatarioPiuRecente.setFirmaExtraAuriga(false);
					// Verifico se sto firmando il file principale di un atto
					if (fileVerPreFirma != null && fileVerPreFirma.getIsFilePrincipaleAtto() != null && fileVerPreFirma.getIsFilePrincipaleAtto() && infoFileFirmato.getInfoFirmaMarca() != null) {
						infoFileFirmato.getInfoFirmaMarca().setBustaCrittograficaInComplPassoIter(true);
					}
				}
				// Setto le impostazioni della marca temporale
				// Se dopo una firma il file è marcato temporalmente allora la marca è stata fatta da Auriga, in quanto se firmo un file già marcato
				// viene fatta sempre una firma CAdES verticale, e quindi la marcca preesistente finiribbe su una busta crittografica interna
				if (infoFileFirmato != null && infoFileFirmato.getInfoFirmaMarca() != null && infoFileFirmato.getInfoFirmaMarca().getDataOraMarcaTemporale() != null) {
					infoFileFirmato.getInfoFirmaMarca().setMarcaTemporaleAppostaDaAuriga(true);
				}
				// In test uso DISABILITA_CONTROLLO_CERTIFICATO_SCADUTO_FIRMA_CLIENT, in modo da poter avere firme valide anche con dispositivi di firma cliant scaduti
				if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DISABILITA_CONTROLLO_CERTIFICATO_SCADUTO_FIRMA_CLIENT")) {
					infoFileFirmato.setFirmaValida(true);
					if (infoFileFirmato.getInfoFirmaMarca() != null) {
						infoFileFirmato.getInfoFirmaMarca().setFirmeNonValideBustaCrittografica(false);
					}
				}
				
				if (bean.getAggiornamentoFirmeParziale() != null && !bean.getAggiornamentoFirmeParziale() && fileVerPreFirma != null && fileVerPreFirma.getListaInformazioniFirmaGrafica() != null) {
					// Ordino temporalmente la lista dei firmatari per inserire le eventuali informazioni contenute in listaInformazioniFirmaGrafica
					// In listaInformazioniFirmaGrafica ho le informazioni in ordine dalla firma più vecchia a quella più recente
					ArrayList<Firmatari> listaFirmeOrdinate = new ArrayList<Firmatari>();
					Firmatari[] firmatari = infoFileFirmato.getBuste();
					if (firmatari != null) {
						for (Firmatari firmatarioDaInserire : firmatari) {
							if (listaFirmeOrdinate.isEmpty()) {
								listaFirmeOrdinate.add(firmatarioDaInserire);
							} else {
								for (int pos = 0; true; pos++) {
									if (pos == listaFirmeOrdinate.size()) {
										listaFirmeOrdinate.add(firmatarioDaInserire);
										break;
									} else {
										Date dataFirmnatarioDaInserire = firmatarioDaInserire.getDataFirma();
										Date dataFirmaPosizione = listaFirmeOrdinate.get(pos).getDataFirma();
										if (dataFirmnatarioDaInserire != null && dataFirmaPosizione != null && dataFirmnatarioDaInserire.before(dataFirmaPosizione)) {
											listaFirmeOrdinate.add(pos, firmatarioDaInserire);
											break;
										}
									}
								}
							}
						}
					}
					// Inserisco le informazioni di listaInformazioniFirmaGrafica
					int posFirmatarioDaAggiornare = 0;
					for (int i = 0; i < fileVerPreFirma.getListaInformazioniFirmaGrafica().size(); i++) {
						InfoFirmaGraficaBean lInfoFirmaGrafica = fileVerPreFirma.getListaInformazioniFirmaGrafica().get(i);
						if (posFirmatarioDaAggiornare < listaFirmeOrdinate.size()) {
							Firmatari firmatarioDaAggiornare = listaFirmeOrdinate.get(posFirmatarioDaAggiornare);							
							firmatarioDaAggiornare.setTitolareFirma(lInfoFirmaGrafica.getTitolareFirma());
							firmatarioDaAggiornare.setCodiceActivityFirma(lInfoFirmaGrafica.getCodiceActivityFirma());
							firmatarioDaAggiornare.setIdUtenteLavoroFirma(lInfoFirmaGrafica.getIdUtenteLavoroFirma());
							firmatarioDaAggiornare.setIdUtenteLoggatoFirma(lInfoFirmaGrafica.getIdUtenteLoggatoFirma());
						}
						posFirmatarioDaAggiornare ++;
					}
				}
								
				fileFirmato.setInfoFile(infoFileFirmato);
				nuovaMappaFileFirmati.put(fileFirmato.getIdFile(), fileFirmato);
			}
		}
		bean.setFileFirmati(nuovaMappaFileFirmati);
		return bean;
	}
	
	public static FirmaHsmBean aggiornaInfoFileFirmatiFileGeneratiDaModello(FirmaHsmBean result) {
		List<FileDaFirmare> listaFileFirmati = result.getFileFirmati();
		if (listaFileFirmati != null && !listaFileFirmati.isEmpty()) {
			// Ho firmato un file generato da modello, quindi la busta è fatta da auriga e ci sarà un unico firmatario non extra auriga
			for (FileDaFirmare fileFirmato : listaFileFirmati) {
				MimeTypeFirmaBean infoFileFirmato = fileFirmato.getInfoFile();
				// Il file avrà solo un firmatario, che non sarà extra auriga
				if (infoFileFirmato.getBuste() != null && infoFileFirmato.getBuste() != null) {
					for (Firmatari bustaFileFirmato : infoFileFirmato.getBuste()) {
						bustaFileFirmato.setFirmaExtraAuriga(false);
					}
				}
				// Il file firmato avrà busta crittografica fatta da auriga
				if (fileFirmato != null && fileFirmato.getInfoFile() != null && fileFirmato.getInfoFile().getInfoFirmaMarca() != null) {
					InfoFirmaMarca lInfoFirmaMarca = fileFirmato.getInfoFile().getInfoFirmaMarca();
					lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(true);
				}
			}
		}
		return result;
	}
	
	public AppletMultiplaBean calcolaInfoFileFirmaMultiplaHashDaDispositivo(AppletMultiplaBean bean) throws Exception {
		List<FileDaFirmareBean> files = null;
		if(bean.getFiles() != null) {
			files = new ArrayList<FileDaFirmareBean>();
			for(int i = 0; i < bean.getFiles().size(); i++) {
				FileDaFirmareBean lFileBean = bean.getFiles().get(i);
				if(lFileBean.getFirmaEseguita() != null && lFileBean.getFirmaEseguita()) {
					// Aggiorno infoFile senza chiamare fileop se ho l'infoFile && non è firma xades && ho la appa degli attributi di firma && il file non è stato marcato
					MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
					if (lFileBean.getInfoFile() != null && StringUtils.isNotBlank(lFileBean.getTipoFirmaDaEffettuare()) && lFileBean.getTipoFirmaDaEffettuare().toLowerCase().indexOf("xades") == -1 && StringUtils.isNotBlank(lFileBean.getJsonMappaAttributiFirma()) && StringUtils.isNotBlank(lFileBean.getMarcaDaApporre()) && "false".equalsIgnoreCase(lFileBean.getMarcaDaApporre())) {
						try {
							// Ho ricevuto le informazioni di firma dal modulo di firma, aggiorno infoFile senza invocare fileOp
							String jsonMappaAttributiFirma = lFileBean.getJsonMappaAttributiFirma();
							HashMap<String, String> mappaAttributiFirma = new Gson().fromJson(jsonMappaAttributiFirma, HashMap.class);
							lMimeTypeFirmaBean = FirmaUtils.aggiungiInfoFirmaInInfoFile(lFileBean.getUri(), lFileBean.getInfoFile(), mappaAttributiFirma, lFileBean.getTipoFirmaDaEffettuare());
						} catch (Exception e) {
							log.error("Errore nell'aggiornamento delle informazioni di firma" + e.getMessage(), e);
							File lFile = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
							lMimeTypeFirmaBean = retrieveInfo(lFile, lFileBean.getNomeFile(), bean.getProvenienza());
						}						
					} else {
						File lFile = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
						lMimeTypeFirmaBean = retrieveInfo(lFile, lFileBean.getNomeFile(), bean.getProvenienza());
					}
					lFileBean.setInfoFile(lMimeTypeFirmaBean);
				} 
				files.add(lFileBean);
			}
		}
		bean.setFiles(files);
		return bean;
	}

	private MimeTypeFirmaBean retrieveInfo(File pInputStream, String pStrDisplayFileName, String provenienza) throws FileNotFoundException{

		if (provenienza.equals("ScanApplet.jar")){
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setCorrectFileName(pStrDisplayFileName);
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			lMimeTypeFirmaBean.setDaScansione(true);
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
			DigestCtrl lDigestCtrl = new DigestCtrl();
			lDigestCtrl.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lDigestCtrl.setEncoding(lDocumentConfiguration.getEncoding());
			lMimeTypeFirmaBean.setImpronta(lDigestCtrl.execute(new FileInputStream(pInputStream)));
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setCorrectFileName(pStrDisplayFileName);
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			lMimeTypeFirmaBean.setDaScansione(true);
			lMimeTypeFirmaBean.setBytes(pInputStream.length());
			return lMimeTypeFirmaBean;
		} else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setDaScansione(false);
			try {
				return lInfoFileUtility.getInfoFromFile(pInputStream.toURI().toString(), pStrDisplayFileName, false, null);
			} catch (Exception e) {
				return lMimeTypeFirmaBean;
			}
		}
	}
}
