/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateIns_raccomandateBean;
import it.eng.auriga.database.store.dmpk_raccomandate.bean.DmpkRaccomandateRic_raccomandateBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
//import it.eng.auriga.function.bean.FindUoAppartenenzaResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.client.DmpkRaccomandateIns_raccomandate;
import it.eng.client.DmpkRaccomandateRic_raccomandate;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.RecuperoFile;
//import it.eng.document.function.bean.CreaDatiPostalizzazioneOutBean;
//import it.eng.document.function.bean.DatiPostalizzazioneBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.postel.AddressType;
import it.eng.postel.AddressTypologyType;
import it.eng.postel.ContactType;
import it.eng.postel.DocumentsType;
import it.eng.postel.Parts;
import it.eng.postel.PartsType;
import it.eng.postel.ReceiverType;
import it.eng.postel.ReceiversType;
import it.eng.postel.SenderType;
import it.eng.postel.TypologyType;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.TimbraUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;

@Datasource(id = "PostelDataSource") 
public class PostelDataSource extends AbstractServiceDataSource<ProtocollazioneBean, ProtocollazioneBean> {

	private static final Logger logPostelDS = Logger.getLogger(ProtocolloDataSource.class);

	public ProtocollazioneBean call(ProtocollazioneBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());

		String nomeDestinatario="";
		String cognomeDestinatario="";
		String denominazione="";
		String identificativo;		
		String mittente_line_1;
		String mittente_line_3;
		String mittente_line_4;
		String cap;
		String tipoToponimo;
		String toponimo;
		String civico;
		String indirizzo;
		String comune;
		String nomeStato;
		String capLocProv;
		String provincia;
		
		ArrayList<String> idsDestinatariOk = new ArrayList<>();
		
		//Elementi dell'xml request
		ReceiversType lReceiversType = new ReceiversType();		
		SenderType lSenderType = new SenderType();
		DocumentsType lDocumentsType = new DocumentsType();
		Parts lParts = new Parts();
		
		
		InputStream postelProp = PostelDataSource.class.getClassLoader().getResourceAsStream("postel.properties");
		Properties lProperties = new Properties();
		
		lProperties.load(postelProp);
		

		try {


			/****** DOCUMENTI ******/

			ArrayList<File> fileAllegati = new ArrayList<>();
			ArrayList<String> nomiFileAllegati = new ArrayList<>();
			logPostelDS.info("Inizio fase creazione del pacchetto Postel");		

			InfoFileUtility lInfoFileUtility = new InfoFileUtility();

			//Converto i file allegati in caso non siano pdf e li aggiungo alla lista dei Doc da mergiare
			//(per il primario esiste un parametro da DB che ne abilita la conversione)
			logPostelDS.debug("Comincio la conversione dei file allegati in caso non siano pdf");

			for (AllegatoProtocolloBean allegato: pInBean.getListaAllegati()) {

				if (allegato.getUriFileAllegato() != null && !"".equalsIgnoreCase(allegato.getUriFileAllegato())){

					if (allegato.getInfoFile().getMimetype().equals("application/pdf")) {
						allegato.setNomeFileAllegato(Integer.toString(pInBean.getIdUd().intValue())+"_"+allegato.getNomeFileAllegato());
						File fAl = StorageImplementation.getStorage().getRealFile(allegato.getUriFileAllegato());
						logPostelDS.info("riga 159 path file allegato: " + fAl.getAbsolutePath());
						fileAllegati.add(fAl);
						lDocumentsType.getDocument().add(allegato.getNomeFileAllegato());
						nomiFileAllegati.add(allegato.getNomeFileAllegato());
					}else {
						if(allegato.getInfoFile().isConvertibile()) {
							allegato.setNomeFileAllegato(pInBean.getIdUd().toString()+"_"+allegato.getNomeFileAllegato());
							File fAl = null;
							String uriFile = null;
							RecuperoFile lRecuperoFile = new RecuperoFile();
							FileExtractedIn lFileExtractedIn = new FileExtractedIn();
							lFileExtractedIn.setUri(allegato.getUriFileAllegato());
							FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
							fAl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
							uriFile = fAl.toURI().toString();

							int sogliaConversioneMB = ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_CONVERSIONE") != null ? Integer.parseInt(ParametriDBUtil.getParametroDB(getSession(), "DIMENSIONE_MAX_FILE_CONVERSIONE")) : 0;
							long dimensioneFileMB = fAl.length() / 1000000;

							if (sogliaConversioneMB == 0 || sogliaConversioneMB >= dimensioneFileMB) {
								try {
									String nomeFile = allegato.getNomeFileAllegato();
									String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(uriFile, nomeFile));
									File fileConvertito = StorageImplementation.getStorage().extractFile(uriPdf);
									fileAllegati.add(fileConvertito);
									lDocumentsType.getDocument().add(nomeFile);
									nomiFileAllegati.add(nomeFile);
								}catch (Exception e) {
									logPostelDS.error("Errore durante la conversione del file" + e.getMessage(), e);
									throw new Exception("Errore durante la conversione del file");
								}
							}else {
								logPostelDS.error("Errore durante la conversione del file: Il file è troppo grande per poter essere convertito");
								throw new Exception("Errore durante la conversione del file: Il file è troppo grande per poter essere convertito");
							}
						}else {
							logPostelDS.error("Errore durante la conversione del file: Il file non è convertibile");
							throw new Exception("Errore durante la conversione del file: Il file non è convertibile");
						}
					}
				}else {
					logPostelDS.error("Errore durante il recupero del file");
					throw new Exception("Errore durante il recupero del file");
				}
			}

			lDocumentsType.setDocumentaryUnit(pInBean.getIdUd().intValue());


			/****** MITTENTE ******/
			
			logPostelDS.info("Creazione sezione Mittente per xml Postel");

			//limite lunghezza identificativo
			int limIdentificativo = 44;
			mittente_line_1 = pInBean.getGruppoProtocollantePostelMittente().toUpperCase(getLocale()).trim();
			if (mittente_line_1.length()>limIdentificativo) {
				mittente_line_1 = (mittente_line_1).substring(0, limIdentificativo);
			}

			mittente_line_3 = pInBean.getTipoToponimoMittentePostel().toUpperCase(getLocale()) + " " +
					pInBean.getToponimoMittentePostel().toUpperCase(getLocale()) + " " +
					pInBean.getCivicoMittentePostel().toUpperCase(getLocale());
			mittente_line_4 = pInBean.getCapMittentePostel().toUpperCase(getLocale()) + " " +
					pInBean.getComuneMittentePostel().toUpperCase(getLocale()) + " " +
					pInBean.getProvinciaMittentePostel().toUpperCase(getLocale());

			AddressType lAddressType = new AddressType();
			lAddressType.setDug(pInBean.getTipoToponimoMittentePostel().toUpperCase(getLocale()));
			lAddressType.setToponym(pInBean.getToponimoMittentePostel().toUpperCase(getLocale()));
			lAddressType.setCivicNumber(pInBean.getCivicoMittentePostel().toUpperCase(getLocale()));

			ContactType lContactTypeForSender = new ContactType();
			lContactTypeForSender.setAddressTypology(AddressTypologyType.NORMALE);
			lContactTypeForSender.setAddress(lAddressType);
			lContactTypeForSender.setState("ITALIA");
			lContactTypeForSender.setPostalCode(pInBean.getCapMittentePostel().toUpperCase(getLocale()));
			lContactTypeForSender.setZipCode(pInBean.getCapMittentePostel().toUpperCase(getLocale()));
			lContactTypeForSender.setTown(pInBean.getComuneMittentePostel().toUpperCase(getLocale()));
			lContactTypeForSender.setProvince(pInBean.getProvinciaMittentePostel().toUpperCase(getLocale()));
			lContactTypeForSender.setBusinessName(mittente_line_1);

			lSenderType.setContact(lContactTypeForSender);


			/****** DESTINATARI ******/
			
			logPostelDS.debug("Creazione sezione Destinatari per xml Postel");

			List<DestinatarioProtBean> listaDestinatari = pInBean.getListaDestinatari();

			for(DestinatarioProtBean destinatario : listaDestinatari) {


				String tipoDestinatario = destinatario.getTipoDestinatario();
				boolean isDestinatarioInterno = tipoDestinatario != null && ("UP".equals(tipoDestinatario) || "UOI".equals(tipoDestinatario) || "LD".equals(tipoDestinatario) || "PREF".equals(tipoDestinatario));

				if (!isDestinatarioInterno) {

					//identificativo
					if (destinatario.getTipoDestinatario().equalsIgnoreCase("PF")) {
						nomeDestinatario = destinatario.getNomeDestinatario().toUpperCase(getLocale()).trim();
						cognomeDestinatario = destinatario.getCognomeDestinatario().toUpperCase(getLocale()).trim();
						identificativo = cognomeDestinatario+" "+nomeDestinatario; 
						if (identificativo.length()>limIdentificativo)
							identificativo = identificativo.substring(0, limIdentificativo);
					}else { 
						denominazione = destinatario.getDenominazioneDestinatario().toUpperCase(getLocale()).trim();
						identificativo = denominazione;
						if (identificativo.length()>limIdentificativo)
							identificativo = identificativo.substring(0, limIdentificativo);
					}

					//cap, provincia
					cap = destinatario.getMezzoTrasmissioneDestinatario().getCap();
					
					if (destinatario.getProvincia() != null && !"".equalsIgnoreCase(destinatario.getProvincia())) {
						provincia = destinatario.getProvincia();
					}else {
						provincia = destinatario.getMezzoTrasmissioneDestinatario().getDescrizioneIndirizzo().substring(destinatario.getMezzoTrasmissioneDestinatario().getDescrizioneIndirizzo().lastIndexOf("(") + 1, destinatario.getMezzoTrasmissioneDestinatario().getDescrizioneIndirizzo().lastIndexOf(")")) .toUpperCase(getLocale());
					}

					//tipoToponimo
					tipoToponimo = destinatario.getMezzoTrasmissioneDestinatario().getTipoToponimo();
					toponimo = destinatario.getMezzoTrasmissioneDestinatario().getIndirizzo().toUpperCase(getLocale());
					civico = destinatario.getMezzoTrasmissioneDestinatario().getCivico();
					indirizzo = tipoToponimo +" "+ toponimo +" "+ civico;

					//comune
					comune = destinatario.getMezzoTrasmissioneDestinatario().getComune().toUpperCase(getLocale());
					capLocProv = cap +" "+ comune +" "+ provincia;

					//nomeStato
					nomeStato = destinatario.getMezzoTrasmissioneDestinatario().getStato().toUpperCase(getLocale());


					/** DESTINATARIO PER XML **/

					ReceiverType lReceiverType = new ReceiverType();

					if(destinatario.getIdSoggetto() != null) 
						lReceiverType.setId(destinatario.getIdSoggetto());

					AddressType lAddressTypeForReceiver = new AddressType();
					lAddressTypeForReceiver.setDug(tipoToponimo);
					lAddressTypeForReceiver.setToponym(toponimo);
					lAddressTypeForReceiver.setCivicNumber(civico);


					ContactType lContactTypeForReceiver = new ContactType();
					lContactTypeForReceiver.setAddressTypology(AddressTypologyType.NORMALE);
					lContactTypeForReceiver.setAddress(lAddressTypeForReceiver);
					lContactTypeForReceiver.setState("ITALIA");
					lContactTypeForReceiver.setPostalCode(cap);
					lContactTypeForReceiver.setZipCode(cap);
					lContactTypeForReceiver.setTown(comune);
					lContactTypeForReceiver.setProvince(provincia);

					if (destinatario.getTipoDestinatario().equalsIgnoreCase("PF")) {
						lContactTypeForReceiver.setName(nomeDestinatario);
						lContactTypeForReceiver.setSurname(cognomeDestinatario);
					}else {
						lContactTypeForReceiver.setBusinessName(identificativo);
					}

					lReceiverType.setContact(lContactTypeForReceiver);					
					lReceiversType.getReceiver().add(lReceiverType);
					
					
					idsDestinatariOk.add(destinatario.getIdSoggetto());

				}
			}
			
			
			
			if(idsDestinatariOk == null || idsDestinatariOk.isEmpty() || idsDestinatariOk.size() < 1) {
				throw new Exception("Assicurarsi che il destinatario sia stato memorizzato nella rubrica e che sia presente l'ID del destinatario");
			}

			/****** CREO XML ******/
			
			logPostelDS.debug("Genero xml per Postel");

			lParts.setDocuments(lDocumentsType);
			lParts.setSender(lSenderType);
			lParts.setReceivers(lReceiversType);
			if(pInBean.getModalitaInvio() != null && "raccomandata".equalsIgnoreCase(pInBean.getModalitaInvio())) {
				logPostelDS.debug("Modalita' invio di tipo Raccomandata");
				lParts.setTypology(TypologyType.ROL);
			}else if(pInBean.getModalitaInvio() != null && "posta prioritaria".equalsIgnoreCase(pInBean.getModalitaInvio())) {
				logPostelDS.debug("Modalita' invio di tipo Posta prioritaria");
				lParts.setTypology(TypologyType.LOL);
			}else {
				throw new Exception("Non è stata specificata la modalita' di invio");
			}
			String tempDir=System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
			File xmlFile = null;
			if(pInBean.getModalitaInvio() != null && "raccomandata".equalsIgnoreCase(pInBean.getModalitaInvio())) {
				xmlFile = new File(tempDir + TypologyType.ROL.value()+ "." +Integer.toString(pInBean.getIdUd().intValue())+".xml");
			}
			if(pInBean.getModalitaInvio() != null && "posta prioritaria".equalsIgnoreCase(pInBean.getModalitaInvio())) {
				xmlFile = new File(tempDir + TypologyType.LOL.value()+ "." +Integer.toString(pInBean.getIdUd().intValue())+".xml");
			}
			
			logPostelDS.debug("path directory temp: " + tempDir);

			JAXBContext context = JAXBContext.newInstance(Parts.class);
			Marshaller mar= context.createMarshaller();
//			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			mar.marshal(lParts, xmlFile);
			
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

			String zipFileName = lProperties.getProperty("zipPath")+Integer.toString(pInBean.getIdUd().intValue())+"_"+timeStamp+".zip";
			
			logPostelDS.debug("Path file zip Postel: " + zipFileName);
			
			File zipFile = new File(zipFileName);
			zipFile.setReadable(true);
			zipFile.setWritable(true);
			
			
			/** ZIP **/
			
			logPostelDS.debug("Creazione Zip Postel");

			try {

				// create byte buffer
				byte[] buffer = new byte[1024];

				FileOutputStream fos = new FileOutputStream(zipFile);

				ZipOutputStream zos = new ZipOutputStream(fos);
				
				
				logPostelDS.debug("Inserisco l'xml all'interno dello zip");
				
				/** XML **/
				FileInputStream fisXml = new FileInputStream(xmlFile);
				zos.putNextEntry(new ZipEntry(xmlFile.getName()));
				int length;

				while ((length = fisXml.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				} 
				zos.closeEntry();
				
				
				logPostelDS.debug("Inserisco gli allegati all'interno dello zip");

				/** ALLEGATI **/
				for (int i=0; i < fileAllegati.size(); i++) {
					
					File allegato = fileAllegati.get(i);
					
					logPostelDS.debug("allegato percorso: " + allegato.getAbsolutePath());

					FileInputStream fis = new FileInputStream(allegato);
					
					// begin writing a new ZIP entry, positions the stream to the start of the entry data
					zos.putNextEntry(new ZipEntry(nomiFileAllegati.get(i)));

					while ((length = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, length);
					}

					zos.closeEntry();

					// close the InputStream
					fisXml.close();
					fis.close();

				}
		
				// close the ZipOutputStream
				zos.close();
				
				/** Cancello file creati **/
				
				logPostelDS.debug("Dopo aver generato lo zip elimino file creati");
				
				xmlFile.delete();
				for (int i=0; i < fileAllegati.size(); i++) {
					fileAllegati.get(i).delete();
				}

			}catch (IOException ioe) {
				logPostelDS.error("Errore nella creazione del file Zip: " + ioe.getMessage());
				throw new Exception("Errore nella creazione del file Zip: "+ ioe.getMessage());
			}
			
			
			/**** Inserimento dati nel DB ****/
			
			try {
				
				/*** Controllo se già esiste nel DB un record con lo stesso idUd nel qual caso non devo fare la insert ma creo comunque il pacchetto 
				 	 starà poi al modulo di integrazione PIIntegrationPOM andare a fare l'update sul record per modificarne lo stato ***/
				
				boolean isToInsert = true;
				String tipologia = "";
				if(pInBean.getModalitaInvio() != null && "raccomandata".equalsIgnoreCase(pInBean.getModalitaInvio()))
					tipologia = "1";
				if(pInBean.getModalitaInvio() != null && "posta prioritaria".equalsIgnoreCase(pInBean.getModalitaInvio()))
					tipologia = "2";
				String criterio = "ID_UD" + "|*|5|*|" + Integer.toString(pInBean.getIdUd().intValue()) + "|£|" + "TIPO" + "|*|5|*|" + tipologia;
				logPostelDS.debug("Criterio per la ricerca della raccomandata[" + criterio +"]");
				
				DmpkRaccomandateRic_raccomandateBean lDmpkRaccomandateRic_raccomandateBean = new DmpkRaccomandateRic_raccomandateBean();
				lDmpkRaccomandateRic_raccomandateBean.setV_ric(criterio);
				
				DmpkRaccomandateRic_raccomandate lDmpkRaccomandateRicRaccomandate = new DmpkRaccomandateRic_raccomandate();
				StoreResultBean<DmpkRaccomandateRic_raccomandateBean> output = lDmpkRaccomandateRicRaccomandate.execute(getLocale(), lSchemaBean, lDmpkRaccomandateRic_raccomandateBean);
				
				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					if (output.isInError()) {
						logPostelDS.error("Ricerca della raccomandata con id_ud ["+ Integer.toString(pInBean.getIdUd().intValue()) +"], errore nell'output:" + output.getDefaultMessage());
						throw new StoreException(output.getDefaultMessage());
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
				
				if (output.getResultBean() != null && output.getResultBean().getResultout() != null) {
					logPostelDS.debug("Controllo che il record sia da inserire");
					StringReader sr = new StringReader(output.getResultBean().getResultout());
					Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
					if (lista != null && lista.getRiga().size() != 0) { 
						isToInsert = false;
					}
				}else {
					logPostelDS.error("Ricerca della raccomandata con id_ud ["+ Integer.toString(pInBean.getIdUd().intValue()) +"], errore nell'output, il resultBean o resultOut è null");
					throw new StoreException(output.getDefaultMessage());
				}
				
				if(isToInsert) {

					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

					String idDestinatari = "";
					int contatore = 0;

					for (String idDestinatarioOk : idsDestinatariOk) {
						logPostelDS.debug("idDestinatarioOk_"+contatore+"["+idDestinatarioOk+"]");
						if (contatore != 0) {
							idDestinatari = idDestinatari.concat("|$|");
						}
						idDestinatari = idDestinatari.concat(idDestinatarioOk);
						contatore++;
					}

					logPostelDS.debug("Inserisco la raccomandata appena creata nel DB");
					//Inserisco la raccomandata appena creata nel DB
					DmpkRaccomandateIns_raccomandateBean lInsRaccomandateObjectBean = new DmpkRaccomandateIns_raccomandateBean();
					lInsRaccomandateObjectBean.setV_anno_protocollo(pInBean.getAnnoProtocollo() != null ? new BigDecimal(pInBean.getAnnoProtocollo()) : null);
					lInsRaccomandateObjectBean.setV_data_protocollo(format.format(pInBean.getDataProtocollo()));
					lInsRaccomandateObjectBean.setV_numero_protocollo(pInBean.getNroProtocollo());
					lInsRaccomandateObjectBean.setV_destinatari(idDestinatari);
					lInsRaccomandateObjectBean.setV_dati_mittente(pInBean.getGruppoProtocollantePostelMittente());
					lInsRaccomandateObjectBean.setV_id_ud(pInBean.getIdUd());
					if(pInBean.getModalitaInvio() != null && "raccomandata".equalsIgnoreCase(pInBean.getModalitaInvio()))
						lInsRaccomandateObjectBean.setV_tipo("1");
					if(pInBean.getModalitaInvio() != null && "posta prioritaria".equalsIgnoreCase(pInBean.getModalitaInvio()))
						lInsRaccomandateObjectBean.setV_tipo("2");
					lInsRaccomandateObjectBean.setV_data_invio(format.format(new Date()));

					DmpkRaccomandateIns_raccomandate lDmpkRaccomandateInsRaccomandate = new DmpkRaccomandateIns_raccomandate();
					StoreResultBean<DmpkRaccomandateIns_raccomandateBean> outputIns = lDmpkRaccomandateInsRaccomandate.execute(getLocale(), lSchemaBean, lInsRaccomandateObjectBean);

				}
			}catch(Exception e){
				logPostelDS.error("Errore nell'inserimento del record nel DB" + e);
				throw new Exception("Errore durante l'inserimento del record nel database" + e);
			}
			
			
			return pInBean;

		}catch(Exception e){
			logPostelDS.error("Errore nella creazione del pacchetto: " + e);
			throw new Exception("Errore nella creazione del pacchetto: " + e);
		}
	}


	public ProtocollazioneBean generaTimbrati(ProtocollazioneBean pInBean) throws Exception{
	

		
		logPostelDS.debug("genero i file timbrati");
		
		File fileDaTimbrare = null;
		TimbraUtil lTimbraUtil = new TimbraUtil();
		
		List<AllegatoProtocolloBean> allegatiDaTimbrare = new ArrayList<>();
		List<AllegatoProtocolloBean> allegatiTimbrati = new ArrayList<>();
	

		if (pInBean.getListaAllegati()!=null) {
			// Seleziono gli allegati da timbrare
			for(AllegatoProtocolloBean allegato: pInBean.getListaAllegati()) {
//				if(allegato.getInfoFile().isFirmato()) {
					allegatiDaTimbrare.add(allegato);
//				}
			}
		}

		if(pInBean.getUriFilePrimario()!=null /**&& pInBean.getInfoFile().isFirmato()**/) {

			String nomeFileDaTimbrare = pInBean.getNomeFilePrimario();
			
			OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
			lOpzioniTimbroBean.setIdUd(String.valueOf(pInBean.getIdUd().intValue()));
			lOpzioniTimbroBean.setIdDoc(String.valueOf(pInBean.getIdDocPrimario().intValue()));
			lOpzioniTimbroBean.setNomeFile(pInBean.getNomeFilePrimario());
			lOpzioniTimbroBean.setUri(pInBean.getUriFilePrimario());
			lOpzioniTimbroBean.setRemote(pInBean.getRemoteUriFilePrimario());
			lOpzioniTimbroBean.setMimetype(pInBean.getInfoFile().getMimetype());
			lOpzioniTimbroBean.setSkipScelteOpzioniCopertina("true");
			
			DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
			DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
			input.setIdudio(pInBean.getIdUd());
			input.setIddocin(lOpzioniTimbroBean.getIdDoc() != null && !"".equals(lOpzioniTimbroBean.getIdDoc()) ? new BigDecimal(lOpzioniTimbroBean.getIdDoc()) : null);
			input.setFinalitain("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");

			StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
			
			if (result.isInError()) {
				throw new StoreException(result);
			}
			lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
			lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());
			
			OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
			
			lOpzioniTimbroBean.setPosizioneIntestazione(lOpzioniTimbroAttachEmail.getPosizioneIntestazione() != null
					? lOpzioniTimbroAttachEmail.getPosizioneIntestazione().value()
					: null);
			lOpzioniTimbroBean.setPosizioneTestoInChiaro(lOpzioniTimbroAttachEmail.getPosizioneTestoInChiaro() != null
					? lOpzioniTimbroAttachEmail.getPosizioneTestoInChiaro().value()
					: null);
			
			String posTimbroConfig = "";
			String rotaTimbroConfig = "";
			String tipoPaginaConfig = "";
			OpzioniCopertinaTimbroBean lOpzioniCopertinaTimbroBean = (OpzioniCopertinaTimbroBean) SpringAppContext
					.getContext().getBean("OpzioniCopertinaTimbroBean");
			if (lOpzioniCopertinaTimbroBean != null) {
				posTimbroConfig = lOpzioniCopertinaTimbroBean.getPosizioneTimbro() != null
						? lOpzioniCopertinaTimbroBean.getPosizioneTimbro().value()
						: null;
				rotaTimbroConfig = lOpzioniCopertinaTimbroBean.getRotazioneTimbro() != null
						? lOpzioniCopertinaTimbroBean.getRotazioneTimbro().value()
						: null;
				/**TODO: Manca il tipoPagina nel Bean caricato dal config, capire se è voluto o è stata una dimenticanza**/
			}		

			String posTimbroPref = lOpzioniTimbroBean.getPosizioneTimbroPref() != null
					&& !"".equalsIgnoreCase(lOpzioniTimbroBean.getPosizioneTimbroPref()) ? lOpzioniTimbroBean.getPosizioneTimbroPref() : null;
			String rotaTimbroPref = lOpzioniTimbroBean.getRotazioneTimbroPref() != null
					&& !"".equalsIgnoreCase(lOpzioniTimbroBean.getRotazioneTimbroPref()) ? lOpzioniTimbroBean.getRotazioneTimbroPref() : null;
			String tipoPaginaPref = lOpzioniTimbroBean.getTipoPagina() != null
							&& !"".equalsIgnoreCase(lOpzioniTimbroBean.getTipoPagina()) ? lOpzioniTimbroBean.getTipoPagina() : null;

			lOpzioniTimbroBean.setPosizioneTimbroPref(posTimbroPref != null ? posTimbroPref : posTimbroConfig);
			lOpzioniTimbroBean.setRotazioneTimbroPref(rotaTimbroPref != null ? rotaTimbroPref : rotaTimbroConfig);
			lOpzioniTimbroBean.setTipoPagina(tipoPaginaPref != null ? tipoPaginaPref : null);
			
			
			// COMINCIO LA FASE DI TIMBRATURA
			RecuperoFile lRecuperoFile = new RecuperoFile();
			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(pInBean.getUriFilePrimario());
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);

			// In caso non sia pdf ma sia convertibile faccio la conversione
			if(!pInBean.getInfoFile().getMimetype().equals("application/pdf") && pInBean.getInfoFile().isConvertibile()) {
				try {
					String uriFilePdf = StorageImplementation.getStorage().storeStream(
							lTimbraUtil.converti(out.getExtracted(), pInBean.getNomeFilePrimario()));
					fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
					nomeFileDaTimbrare = pInBean.getNomeFilePrimario() + ".pdf";
				} catch (Exception e) {
					logPostelDS.error("Errore durante la conversione del file primario");
					throw new Exception("Errore durante la conversione del file primario");
				}

			}else if (!pInBean.getInfoFile().getMimetype().equals("application/pdf") && !pInBean.getInfoFile().isConvertibile()){
				logPostelDS.error("Il file primario non è convertibile in pdf");
				throw new Exception("Il file primario non è convertibile in pdf");
			}else {
				fileDaTimbrare = out.getExtracted();
			}
			InputStream timbrato = null;

			// Provo a timbrarlo
			try {
				boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "TIMBRATURA_ABILITA_PDFA");
				TimbraUtility lTimbraUtility = new TimbraUtility();
				OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmailFinale = lTimbraUtility.populatePreference(lOpzioniTimbroBean);
				timbrato = lTimbraUtil.timbra(fileDaTimbrare, nomeFileDaTimbrare, lOpzioniTimbroAttachEmailFinale, generaPdfA);
			} catch (Exception e) {
				logPostelDS.error("Errore durante la timbratura del file primario");
				throw new Exception("Errore durante la timbratura del file primario");
			}

			// Genero il timbrato
			String lStrUri = StorageImplementation.getStorage().storeStream(timbrato);			
			String fileName = FilenameUtils.getBaseName(pInBean.getNomeFilePrimario()) + "_timb.pdf";
			pInBean.setNomeFilePrimario(fileName);
			pInBean.setUriFilePrimario(lStrUri);
		}
		
		// Timbro gli allegati firmati
		if(!allegatiDaTimbrare.isEmpty()) {
			
			for(AllegatoProtocolloBean allegato : allegatiDaTimbrare) {
				
				pInBean.getListaAllegati().remove(allegato);
				String nomeFileDaTimbrare = allegato.getNomeFileAllegato();
				
				OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
				lOpzioniTimbroBean.setIdUd(String.valueOf(pInBean.getIdUd().intValue()));
				lOpzioniTimbroBean.setIdDoc(String.valueOf(allegato.getIdDocAllegato().intValue()));
				lOpzioniTimbroBean.setNomeFile(allegato.getNomeFileAllegato());
				lOpzioniTimbroBean.setUri(allegato.getUriFileAllegato());
				lOpzioniTimbroBean.setRemote(allegato.getRemoteUri());
				lOpzioniTimbroBean.setMimetype(allegato.getInfoFile().getMimetype());
				lOpzioniTimbroBean.setSkipScelteOpzioniCopertina("true");

				DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
				DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
				input.setIdudio(pInBean.getIdUd());
				input.setIddocin(allegato.getIdDocAllegato() != null && !"".equals(allegato.getIdDocAllegato()) ? allegato.getIdDocAllegato() : null);
				input.setFinalitain("CONFORMITA_ORIG_DIGITALE_SUPP_DIG");

				StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
				
				if (result.isInError()) {
					throw new StoreException(result);
				}
				lOpzioniTimbroBean.setTesto(result.getResultBean().getContenutobarcodeout());
				lOpzioniTimbroBean.setTestoIntestazione(result.getResultBean().getTestoinchiaroout());
				
				OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmail = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAttachEmail");
				
				lOpzioniTimbroBean.setPosizioneIntestazione(lOpzioniTimbroAttachEmail.getPosizioneIntestazione() != null
						? lOpzioniTimbroAttachEmail.getPosizioneIntestazione().value()
						: null);
				lOpzioniTimbroBean.setPosizioneTestoInChiaro(lOpzioniTimbroAttachEmail.getPosizioneTestoInChiaro() != null
						? lOpzioniTimbroAttachEmail.getPosizioneTestoInChiaro().value()
						: null);
				

				lOpzioniTimbroBean.setPosizioneTimbroPref("bassoSn");
				lOpzioniTimbroBean.setRotazioneTimbroPref("verticale");
				lOpzioniTimbroBean.setTipoPagina("prima");
				
				
				//COMINCIO LA FASE DI TIMBRATURA
				RecuperoFile lRecuperoFile = new RecuperoFile();
				AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(allegato.getUriFileAllegato());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);

				// In caso non sia pdf ma sia convertibile faccio la conversione
				if(!allegato.getInfoFile().getMimetype().equals("application/pdf") && allegato.getInfoFile().isConvertibile()) {
					try {
						String uriFilePdf = StorageImplementation.getStorage().storeStream(
								lTimbraUtil.converti(out.getExtracted(), allegato.getNomeFileAllegato()));
						fileDaTimbrare = StorageImplementation.getStorage().extractFile(uriFilePdf);
						nomeFileDaTimbrare = allegato.getNomeFileAllegato() + ".pdf";
					} catch (Exception e) {
						logPostelDS.error("Errore durante la conversione degli allegati firmati");
						throw new Exception("Errore durante la conversione degli allegati firmati");
					}

				}else if (!allegato.getInfoFile().getMimetype().equals("application/pdf") && !allegato.getInfoFile().isConvertibile()){
					logPostelDS.error("Il file allegato non è convertibile in pdf");
					throw new Exception("Il file allegato non è convertibile in pdf");
				}else {
					fileDaTimbrare = out.getExtracted();
				}

				InputStream timbrato = null;

				// Provo a timbrarlo
				try {
					boolean generaPdfA = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "TIMBRATURA_ABILITA_PDFA");
					TimbraUtility lTimbraUtility = new TimbraUtility();
					OpzioniTimbroAttachEmail lOpzioniTimbroAttachEmailFinale = lTimbraUtility.populatePreference(lOpzioniTimbroBean);
					timbrato = lTimbraUtil.timbra(fileDaTimbrare, nomeFileDaTimbrare, lOpzioniTimbroAttachEmailFinale, generaPdfA);
				} catch (Exception e) {
					logPostelDS.error("Errore durante la timbratura dei file allegati");
					throw new Exception("Errore durante la timbratura dei file allegati");
				}

				// Genero il timbrato
				String lStrUri = StorageImplementation.getStorage().storeStream(timbrato);			
				String fileName = FilenameUtils.getBaseName(allegato.getNomeFileAllegato()) + "_timb.pdf";
				allegato.setNomeFileAllegato(fileName);
				allegato.setUriFileAllegato(lStrUri);
				allegatiTimbrati.add(allegato);
			}
		}
		
		
		// Aggiungo gli allegati timbrati alla lista di allegati del bean che ritorna al client
		if(!allegatiTimbrati.isEmpty()) {
			for (AllegatoProtocolloBean allegato : allegatiTimbrati) {
				pInBean.getListaAllegati().add(allegato);
			}
		}
		return pInBean;
	}
}

