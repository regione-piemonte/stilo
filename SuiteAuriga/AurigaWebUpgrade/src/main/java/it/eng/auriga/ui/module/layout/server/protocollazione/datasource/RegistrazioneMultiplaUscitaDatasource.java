/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatariRegistrazioneMultiplaUscitaXmlBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatariXFileXlsRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.EsitoValidazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileXlsDestinatariRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ImportaDestinatariFromXlsRegMultiplaUscitaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MezzoTrasmissioneDestinatarioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.RegistrazioneMultiplaUscitaBean;
import it.eng.client.GestioneDocumenti;
import it.eng.document.function.bean.CreaDocWithFileBean;
import it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.utility.FileUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "RegistrazioneMultiplaUscitaDatasource")
public class RegistrazioneMultiplaUscitaDatasource extends AbstractFetchDataSource<RegistrazioneMultiplaUscitaBean>{
	
	private static final Logger logger = Logger.getLogger(RegistrazioneMultiplaUscitaDatasource.class);
	
	public static final String _TIPO_REG_PG = "Prot. generale";
	public static final String _TIPO_REG_R = "Repertorio";
	
	public static final String _FLG_SI = "SI";
	public static final String _FLG_NO = "NO";

	@Override
	public PaginatorBean<RegistrazioneMultiplaUscitaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}
	
	@Override
	public RegistrazioneMultiplaUscitaBean add(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		List<CreaDocWithFileBean> listaDocRegMultiplaUscita = new ArrayList<CreaDocWithFileBean>();
		
		if(bean.getListaDestinatariDiversiXReg() != null) {
			for (DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean : bean.getListaDestinatariDiversiXReg()) {
				
				// Copio i dati a maschera nel bean di salvataggio
				ProtocollazioneBean lProtocollazioneBean = createProtocollazioneBeanFromRegistrazioneMultiplaUscitaBean(bean, lDestinatariDiversiXRegBean);
				CreaDocWithFileBean lCreaDocWithFileBean = getProtocolloDataSource(bean).buildCreaDocWithFileBean(lProtocollazioneBean);
				if(lProtocollazioneBean.getErroriFile() != null && !lProtocollazioneBean.getErroriFile().isEmpty()) {
					bean.setErroriFile(lProtocollazioneBean.getErroriFile());
					return bean;
				}
				listaDocRegMultiplaUscita.add(lCreaDocWithFileBean);
				
			}
		}
		
		CreaDocumentiRegMultiplaUscitaBean lCreaDocumentiRegMultiplaUscitaBean = new CreaDocumentiRegMultiplaUscitaBean();
		lCreaDocumentiRegMultiplaUscitaBean.setListaDocRegMultiplaUscita(listaDocRegMultiplaUscita);
		
		lCreaDocumentiRegMultiplaUscitaBean = new GestioneDocumenti().creadocumentiregistrazionemultiplauscita(getLocale(), lAurigaLoginBean, lCreaDocumentiRegMultiplaUscitaBean); //TODO come salvo lCreaDocumentoInBean, lFilePrimarioBean, lAllegatiBean in DB per il job di registrazione multipla in uscita?
		
		addMessage("Registrazione massiva in uscita effettuata con successo con id " + lCreaDocumentiRegMultiplaUscitaBean.getIdJob(), "", MessageType.INFO);

		return bean;
	}
	
	private ProtocollazioneBean createProtocollazioneBeanFromRegistrazioneMultiplaUscitaBean(RegistrazioneMultiplaUscitaBean pRegistrazioneMultiplaUscitaBean, DestinatariRegistrazioneMultiplaUscitaXmlBean pDestinatariDiversiXRegBean) throws Exception {

		if(pRegistrazioneMultiplaUscitaBean != null) {			
			
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
			
			BeanUtilsBean2.getInstance().copyProperties(lProtocollazioneBean, pRegistrazioneMultiplaUscitaBean);
			
			lProtocollazioneBean.setFlgTipoProv("U");
			
			lProtocollazioneBean.setOggetto(generaOggettoWithPlaceholder(lProtocollazioneBean.getOggetto(), null, pDestinatariDiversiXRegBean));
			
			// Aggiungo i valori dei tab dinamici, tutti con il suffisso _Doc				
			lProtocollazioneBean.setValori(new HashMap<String, Object>());		
			if (pRegistrazioneMultiplaUscitaBean.getValori() != null) {
				for (String attrName : pRegistrazioneMultiplaUscitaBean.getValori().keySet()) {
					lProtocollazioneBean.getValori().put(attrName + "_Doc", pRegistrazioneMultiplaUscitaBean.getValori().get(attrName));
				}
			}						
			lProtocollazioneBean.setTipiValori(new HashMap<String, String>());
			if (pRegistrazioneMultiplaUscitaBean.getTipiValori() != null) {
				for (String attrName : pRegistrazioneMultiplaUscitaBean.getTipiValori().keySet()) {
					if(!attrName.contains(".")) {
						lProtocollazioneBean.getTipiValori().put(attrName + "_Doc", pRegistrazioneMultiplaUscitaBean.getTipiValori().get(attrName));
					} else {
						// se contiene il punto è l'attributo relativo alla colonna di un attributo lista
						lProtocollazioneBean.getTipiValori().put(attrName.substring(0, attrName.indexOf(".")) + "_Doc" + attrName.substring(attrName.indexOf(".")), pRegistrazioneMultiplaUscitaBean.getTipiValori().get(attrName));
					}
				}
			}
			
			// Numerazioni da dare
			if(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla() != null) {
				List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();				
				if(_TIPO_REG_PG.equals(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla())) {					
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setCategoria("PG");
					lTipoNumerazioneBean.setSigla(null);		
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getUoProtocollante()) ? pRegistrazioneMultiplaUscitaBean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);		
				} else if(_TIPO_REG_R.equals(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla())) {
					lProtocollazioneBean.setIsRepertorio(true);
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setCategoria("R");
					lTipoNumerazioneBean.setSigla(pRegistrazioneMultiplaUscitaBean.getRepertorio());
					int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					lTipoNumerazioneBean.setAnno(String.valueOf(annoCorrente));					
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getUoProtocollante()) ? pRegistrazioneMultiplaUscitaBean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);					
				}
				lProtocollazioneBean.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
			}
			
			// Destinatari
			List<DestinatarioProtBean> listaDestinatari = new ArrayList<DestinatarioProtBean>();
			if(pDestinatariDiversiXRegBean != null) {
//				DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
//				lDestinatarioProtBean.setTipoDestinatario("XLS");
//				lDestinatarioProtBean.setIdFoglioExcelDestinatari(pRegistrazioneMultiplaUscitaBean.getIdFoglioXlsDestinatariDiversiXReg());
//				lDestinatarioProtBean.setDisplayFileNameExcel(pRegistrazioneMultiplaUscitaBean.getNomeFileXlsDestinatariDiversiXReg());
//				listaDestinatari.add(lDestinatarioProtBean);
				listaDestinatari.add(createDestinatarioProtBeanFromDestinatariDiversiXRegBean(pDestinatariDiversiXRegBean));
			}
			if(pRegistrazioneMultiplaUscitaBean.getListaDestinatari() != null) {
				listaDestinatari.addAll(pRegistrazioneMultiplaUscitaBean.getListaDestinatari());
			}
			lProtocollazioneBean.setListaDestinatari(listaDestinatari);
			
			// File primario
			if(pRegistrazioneMultiplaUscitaBean.getFlgFilePrincipaleUgualeXTutteReg() != null && _FLG_NO.equalsIgnoreCase(pRegistrazioneMultiplaUscitaBean.getFlgFilePrincipaleUgualeXTutteReg())) {
				if(pDestinatariDiversiXRegBean != null && StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getPercorsoFilePrimari())) {					
					File filePrimario = recuperaFilePrimarioDestinatario(pRegistrazioneMultiplaUscitaBean.getPercorsoFilePrimari(), pDestinatariDiversiXRegBean.getNomeFilePrimario());
					if(filePrimario != null) {
						lProtocollazioneBean.setFilePrimario(filePrimario);
						lProtocollazioneBean.setPercorsoFilePrimari(pRegistrazioneMultiplaUscitaBean.getPercorsoFilePrimari());
						lProtocollazioneBean.setNomeFilePrimario(pDestinatariDiversiXRegBean.getNomeFilePrimario());
//						lProtocollazioneBean.setUriFilePrimario(StorageImplementation.getStorage().storeStream(new FileInputStream(filePrimario))); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
//						lProtocollazioneBean.setRemoteUriFilePrimario(false);
//						MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(filePrimario.toURI().toString(), filePrimario.getName(), false, null); //TODO non posso fare una chiamata per ogni file, se sono migliaia diventa lentissimo
//						lProtocollazioneBean.setInfoFile(lMimeTypeFirmaBean);
					}
				}
			}
			
			// File allegati
			List<AllegatoProtocolloBean> listaAllegati = new ArrayList<AllegatoProtocolloBean>();
			if(pDestinatariDiversiXRegBean != null && StringUtils.isNotBlank(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati())) {
				StringSplitterServer st = new StringSplitterServer(pDestinatariDiversiXRegBean.getNomiFileAllegati(), ";");
				while(st.hasMoreTokens()) {
					String nomeFileAllegato = st.nextToken().trim();
					File fileAllegato = recuperaFileAllegatoDestinatario(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati(), pDestinatariDiversiXRegBean.getPercorsoRelFileAllegati(), nomeFileAllegato);					
					if(fileAllegato != null) {
						AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
						lAllegatoProtocolloBean.setFileAllegato(fileAllegato);
						lAllegatoProtocolloBean.setPercorsoRelFileAllegati(pDestinatariDiversiXRegBean.getPercorsoRelFileAllegati());
						lAllegatoProtocolloBean.setNomeFileAllegato(nomeFileAllegato);
//						lAllegatoProtocolloBean.setUriFileAllegato(StorageImplementation.getStorage().storeStream(new FileInputStream(fileAllegato))); //TODO devo usare uno storage temporaneo ad hoc? magari creando una cartella con l'id relativo al job... oppure lascio il file nella cartella temporanea indicata e lo salvo nello storage del job di registrazione? quando cancello il file temporaneo dalla cartella temporanea visto che potrebbe essere condiviso per più registrazioni?
//						lAllegatoProtocolloBean.setRemoteUri(false);
//						MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileAllegato.toURI().toString(), fileAllegato.getName(), false, null); //TODO non posso fare una chiamata per ogni file, se sono migliaia diventa lentissimo
//						lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
						listaAllegati.add(lAllegatoProtocolloBean);						
					}
	    		}
			}
			if(pRegistrazioneMultiplaUscitaBean.getListaAllegati() != null) {
				listaAllegati.addAll(pRegistrazioneMultiplaUscitaBean.getListaAllegati());
			}
			lProtocollazioneBean.setListaAllegati(listaAllegati);
			lProtocollazioneBean.setPercorsoFileAllegati(pRegistrazioneMultiplaUscitaBean.getPercorsoFileAllegati());
			
			return lProtocollazioneBean;
		}
		
		return null;
	}
	
	private DestinatarioProtBean createDestinatarioProtBeanFromDestinatariDiversiXRegBean(DestinatariRegistrazioneMultiplaUscitaXmlBean pDestinatariDiversiXRegBean) throws Exception {
		
		if(pDestinatariDiversiXRegBean != null) {
						
			DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
			
			if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getTipo())) {
				if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.PF.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("PF");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.PG.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("PG");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.PA.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("PA");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.UOI.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("UOI");
				} else if(pDestinatariDiversiXRegBean.getTipo().equalsIgnoreCase(TipoDestinatario.UP.getValue())) {
					lDestinatarioProtBean.setTipoDestinatario("UP");
				}
			}
				
			lDestinatarioProtBean.setCodRapidoDestinatario(pDestinatariDiversiXRegBean.getCodRapido());
			lDestinatarioProtBean.setDenominazioneDestinatario(pDestinatariDiversiXRegBean.getDenominazioneCognome());
			lDestinatarioProtBean.setCognomeDestinatario(pDestinatariDiversiXRegBean.getDenominazioneCognome());
			lDestinatarioProtBean.setNomeDestinatario(pDestinatariDiversiXRegBean.getNome());
			lDestinatarioProtBean.setCodfiscaleDestinatario(pDestinatariDiversiXRegBean.getCodiceFiscale());
//			lDestinatarioProtBean.setPivaDestinatario(pDestinatariDiversiXRegBean.getPiva());
			
			//TODO Per il mapping serve sapere quali sono i valori che si possono inserire nel campo "Mezzo trasmissione" dell'excel dei destinatari
			MezzoTrasmissioneDestinatarioBean lMezzoTrasmissioneDestinatarioBean = new MezzoTrasmissioneDestinatarioBean();
			if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getMezzoTrasmissione())) {
				if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.R.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("R");					
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.NM.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("NM");
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.PEC.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("PEC");
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.PEO.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("PEO");
				} else if(pDestinatariDiversiXRegBean.getMezzoTrasmissione().equalsIgnoreCase(MezzoTrasmissione.EMAIL.getValue())) {
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario("EMAIL");
				}
			}
			/*
			 * DestinatariDiversiXRegBean => DestinatarioProtBean => DestinatariBean
			 * - Toponimo => TipoToponimo => TipoToponimo col. 49
			 * - Indirizzo => Indirizzo e Toponimo => ToponimoIndirizzo col. 25
			 * - NumCivico => Civico => Civico col. 27
			 * - AppendiceCivico => Appendici o Interno ? => Appendici col. 47 o Interno col. 28 ?
			 * - ComuneCittaEstera => NomeComune e Citta => NomeComuneCitta col. 33 (e cod. istat Comune col. 32 ?)
			 * - Cap => Cap => Cap col. 31
			 * - StatoEstero => NomeStato => NomeStato col. 35 (e cod. istat Stato col. 34 ?)
			 * - Localita => Zona o Frazione ? => Zona col. 45 o Frazione col. 26 ?
			 * - IndirizzoRubrica => ? => ?		
			 */
			if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INDIRIZZO_DEST_ESTESI")) {
				// dati indirizzo
				if (StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getStatoEstero())) {		
//					lDestinatarioProtBean.setStato();
					lDestinatarioProtBean.setNomeStato(pDestinatariDiversiXRegBean.getStatoEstero());					
					lDestinatarioProtBean.setIndirizzo(pDestinatariDiversiXRegBean.getIndirizzo());
					lDestinatarioProtBean.setCitta(pDestinatariDiversiXRegBean.getComuneCittaEstera());					
				} else {
					lDestinatarioProtBean.setStato(ProtocolloDataSource._COD_ISTAT_ITALIA);
					lDestinatarioProtBean.setNomeStato(ProtocolloDataSource._NOME_STATO_ITALIA);
					lDestinatarioProtBean.setTipoToponimo(pDestinatariDiversiXRegBean.getToponimo());
					lDestinatarioProtBean.setToponimo(pDestinatariDiversiXRegBean.getIndirizzo());
//					lDestinatarioProtBean.setComune();
					lDestinatarioProtBean.setNomeComune(pDestinatariDiversiXRegBean.getComuneCittaEstera());
//					lDestinatarioProtBean.setFrazione();
					lDestinatarioProtBean.setCap(pDestinatariDiversiXRegBean.getCap());
				}
				lDestinatarioProtBean.setCivico(pDestinatariDiversiXRegBean.getNumCivico());
//				lDestinatarioProtBean.setInterno();
//				lDestinatarioProtBean.setZona();
//				lDestinatarioProtBean.setComplementoIndirizzo();
//				lDestinatarioProtBean.setAppendici();
			} else {						
				lMezzoTrasmissioneDestinatarioBean.setTipoToponimo(pDestinatariDiversiXRegBean.getToponimo());
//				lMezzoTrasmissioneDestinatarioBean.setCiToponimo();
				lMezzoTrasmissioneDestinatarioBean.setIndirizzo(pDestinatariDiversiXRegBean.getIndirizzo());
//				lMezzoTrasmissioneDestinatarioBean.setIndirizzoDestinatario();
//				lMezzoTrasmissioneDestinatarioBean.setFrazione();
				lMezzoTrasmissioneDestinatarioBean.setCivico(pDestinatariDiversiXRegBean.getNumCivico());
//				lMezzoTrasmissioneDestinatarioBean.setInterno();
//				lMezzoTrasmissioneDestinatarioBean.setScala();
//				lMezzoTrasmissioneDestinatarioBean.setPiano();
				lMezzoTrasmissioneDestinatarioBean.setCap(pDestinatariDiversiXRegBean.getCap());
//				lMezzoTrasmissioneDestinatarioBean.setCodIstatComune();
				lMezzoTrasmissioneDestinatarioBean.setComune(pDestinatariDiversiXRegBean.getComuneCittaEstera());
				if (StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getStatoEstero())) {															
//					lMezzoTrasmissioneDestinatarioBean.setCodIstatStato();
					lMezzoTrasmissioneDestinatarioBean.setStato(pDestinatariDiversiXRegBean.getStatoEstero());
				} else {
					lMezzoTrasmissioneDestinatarioBean.setCodIstatStato(ProtocolloDataSource._COD_ISTAT_ITALIA);
					lMezzoTrasmissioneDestinatarioBean.setStato(ProtocolloDataSource._NOME_STATO_ITALIA);
				}				
//				lMezzoTrasmissioneDestinatarioBean.setZona();
//				lMezzoTrasmissioneDestinatarioBean.setComplementoIndirizzo();
//				lMezzoTrasmissioneDestinatarioBean.setAppendici();
			}
			lDestinatarioProtBean.setMezzoTrasmissioneDestinatario(lMezzoTrasmissioneDestinatarioBean);
			
			if(StringUtils.isNotBlank(pDestinatariDiversiXRegBean.getEffettuaAssegnazioneCc())) {
				if(pDestinatariDiversiXRegBean.getEffettuaAssegnazioneCc().equalsIgnoreCase(EffettuaAssegnazioneCc.ASS.getValue())) {
					lDestinatarioProtBean.setFlgAssegnaAlDestinatario(true);					
				} else if(pDestinatariDiversiXRegBean.getEffettuaAssegnazioneCc().equalsIgnoreCase(EffettuaAssegnazioneCc.CC.getValue())) {
					lDestinatarioProtBean.setFlgPC(true);
				} 
			}			
					
			return lDestinatarioProtBean;
		}
		
		return null;
	}
	
	public ProtocolloDataSource getProtocolloDataSource(final RegistrazioneMultiplaUscitaBean pRegistrazioneMultiplaUscitaBean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				if(pRegistrazioneMultiplaUscitaBean != null) {
					salvaAttributiCustomRegistrazioneMultiplaUscita(pRegistrazioneMultiplaUscitaBean, pSezioneCacheAttributiDinamici);
				}
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		Map<String, String> extraparams = getExtraparams();
		if(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla() != null && _TIPO_REG_R.equalsIgnoreCase(pRegistrazioneMultiplaUscitaBean.getTipoRegistrazioneMultipla())) {
			extraparams.put("isRepertorio", "true");
		}
		lProtocolloDataSource.setExtraparams(extraparams);					
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2CompletaDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}
	
	private void salvaAttributiCustomRegistrazioneMultiplaUscita(RegistrazioneMultiplaUscitaBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		salvaAttributiCustomRegistrazioneMultiplaUscita(bean, sezioneCacheAttributiDinamici, false);
	}
	
	private void salvaAttributiCustomRegistrazioneMultiplaUscita(RegistrazioneMultiplaUscitaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean flgGenModello) throws Exception {
		
	}
	
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {		
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}
	
	public EsitoValidazioneBean validazioneFile(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		// verifica di presenza dei file primari e allegati indicati nel file xls dei destinatari
		EsitoValidazioneBean lEsitoValidazioneFilePrimari = validazioneFilePrimari(bean);
		EsitoValidazioneBean lEsitoValidazioneFileAllegati = validazioneFileAllegati(bean);
		
		EsitoValidazioneBean lEsitoValidazioneBean = new EsitoValidazioneBean();
		lEsitoValidazioneBean.setEsitoValidazione(lEsitoValidazioneFilePrimari.getEsitoValidazione() && lEsitoValidazioneFileAllegati.getEsitoValidazione());
		lEsitoValidazioneBean.setErrorMessages(new HashMap<String, String>());
		lEsitoValidazioneBean.getErrorMessages().putAll(lEsitoValidazioneFilePrimari.getErrorMessages());
		for(String key : lEsitoValidazioneFileAllegati.getErrorMessages().keySet()) {
			String value = lEsitoValidazioneFileAllegati.getErrorMessages().get(key);
			if(lEsitoValidazioneBean.getErrorMessages().containsKey(key)) {
				lEsitoValidazioneBean.getErrorMessages().put(key, lEsitoValidazioneBean.getErrorMessages().get(key) + "\n" + value);
			} else {
				lEsitoValidazioneBean.getErrorMessages().put(key, value);
				
			}
		}
		
		return lEsitoValidazioneBean;
	}
	
	public EsitoValidazioneBean validazioneFilePrimari(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		// Verifica di presenza dei file primari indicati nel file xls dei destinatari
		EsitoValidazioneBean lEsitoValidazioneBean = new EsitoValidazioneBean();
		lEsitoValidazioneBean.setEsitoValidazione(true);
		lEsitoValidazioneBean.setErrorMessages(new HashMap<String, String>());
		if(bean.getListaDestinatariDiversiXReg() != null && bean.getListaDestinatariDiversiXReg().size() > 0) {
			if(bean.getFlgFilePrincipaleUgualeXTutteReg() != null && _FLG_NO.equalsIgnoreCase(bean.getFlgFilePrincipaleUgualeXTutteReg()) && StringUtils.isNotBlank(bean.getPercorsoFilePrimari())) {
				for(int i = 0; i < bean.getListaDestinatariDiversiXReg().size(); i++) {
					DestinatariRegistrazioneMultiplaUscitaXmlBean dest = bean.getListaDestinatariDiversiXReg().get(i);
					if(StringUtils.isNotBlank(dest.getNomeFilePrimario())) {	
						File filePrimario = recuperaFilePrimarioDestinatario(bean.getPercorsoFilePrimari(), dest.getNomeFilePrimario());
						if(filePrimario == null) {
							lEsitoValidazioneBean.setEsitoValidazione(false);
							lEsitoValidazioneBean.getErrorMessages().put("" + (i+1), "In riga " + (i+1) + " il file primario " + dest.getNomeFilePrimario() + " non è presente nel percorso indicato.");							
						}
					} else {
						lEsitoValidazioneBean.setEsitoValidazione(false);
						lEsitoValidazioneBean.getErrorMessages().put("" + (i+1), "In riga " + (i+1) + " non è indicato il file primario.");							
					}
				}				
			} else {
				lEsitoValidazioneBean.setEsitoValidazione(false);
				lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato il percorso da cui recuperare i file primari.");		
			}	
		} else {
			lEsitoValidazioneBean.setEsitoValidazione(false);
			lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato nessun destinatario nel file xls.");	
		}
		
		return lEsitoValidazioneBean;
	}
	
	private File recuperaFilePrimarioDestinatario(String percorsoFilePrimari, String nomeFilePrimario) {
		
		if(StringUtils.isNotBlank(percorsoFilePrimari)) {
			File folderFilePrimari = new File(percorsoFilePrimari);
			if(folderFilePrimari.exists() && folderFilePrimari.isDirectory()) {
				if(StringUtils.isNotBlank(nomeFilePrimario)) {
					File filePrimario = new File(folderFilePrimari.getAbsolutePath() + File.separator + nomeFilePrimario);
					if(filePrimario.exists() && filePrimario.isFile()) {
						return filePrimario;				
					}
				}
			}
		}
		return null;
	}

	public EsitoValidazioneBean validazioneFileAllegati(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		// Verifica di presenza dei file allegati indicati nel file xls dei destinatari
		EsitoValidazioneBean lEsitoValidazioneBean = new EsitoValidazioneBean();
		lEsitoValidazioneBean.setEsitoValidazione(true);
		lEsitoValidazioneBean.setErrorMessages(new HashMap<String, String>());
		if(bean.getListaDestinatariDiversiXReg() != null && bean.getListaDestinatariDiversiXReg().size() > 0) {			
			// Recupero solo i destinatari che hanno allegati
			List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestDiversiXRegConAllegati = new ArrayList<DestinatariRegistrazioneMultiplaUscitaXmlBean>();
			for(int i = 0; i < bean.getListaDestinatariDiversiXReg().size(); i++) {
				DestinatariRegistrazioneMultiplaUscitaXmlBean dest = bean.getListaDestinatariDiversiXReg().get(i);
				if(StringUtils.isNotBlank(dest.getNomiFileAllegati())) {
					listaDestDiversiXRegConAllegati.add(dest);
				} 
			}
			if(listaDestDiversiXRegConAllegati != null && listaDestDiversiXRegConAllegati.size() > 0) {		
				if(StringUtils.isNotBlank(bean.getPercorsoFileAllegati())) {
					for(int i = 0; i < listaDestDiversiXRegConAllegati.size(); i++) {
						DestinatariRegistrazioneMultiplaUscitaXmlBean dest = listaDestDiversiXRegConAllegati.get(i);
						StringSplitterServer st = new StringSplitterServer(dest.getNomiFileAllegati(), ";");
						while(st.hasMoreTokens()) {
							String nomeFileAllegato = st.nextToken().trim();
							File fileAllegato = recuperaFileAllegatoDestinatario(bean.getPercorsoFileAllegati(), dest.getPercorsoRelFileAllegati(), nomeFileAllegato);
							if(fileAllegato == null) {
								lEsitoValidazioneBean.setEsitoValidazione(false);
								lEsitoValidazioneBean.getErrorMessages().put("" + (i+1), "In riga " + (i+1) + " il file allegato " + nomeFileAllegato + " non è presente nel percorso indicato.");							
							}					
			    		}
					}
				} else {
					lEsitoValidazioneBean.setEsitoValidazione(false);
					lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato il percorso da cui recuperare i file allegati.");				
				}
			}
		} else {
			lEsitoValidazioneBean.setEsitoValidazione(false);
			lEsitoValidazioneBean.getErrorMessages().put("", "Non è indicato nessun destinatario nel file xls.");	
		}
		return lEsitoValidazioneBean;
	}
		
	private File recuperaFileAllegatoDestinatario(String percorsoFileAllegati, String percorsoRelFileAllegatiDest, String nomeFileAllegato) {
		if(StringUtils.isNotBlank(percorsoFileAllegati)) {
			File folderFileAllegati = new File(percorsoFileAllegati);
			if(folderFileAllegati.exists() && folderFileAllegati.isDirectory()) {
				if(StringUtils.isNotBlank(percorsoRelFileAllegatiDest)) {
					folderFileAllegati = new File(folderFileAllegati.getAbsolutePath() + File.separator + percorsoRelFileAllegatiDest);
					if(!folderFileAllegati.exists() || !folderFileAllegati.isDirectory()) {
						return null;
					}
				} 
				if(StringUtils.isNotBlank(nomeFileAllegato)) {
					File fileAllegato = new File(folderFileAllegati.getAbsolutePath() + File.separator + nomeFileAllegato);
					if(fileAllegato.exists() && fileAllegato.isFile()) {
						return fileAllegato;				
					}
				}
			}	
		}
		return null;
	}
	
	public FileDaFirmareBean generaAnteprimaOggetti(RegistrazioneMultiplaUscitaBean bean) throws Exception {
		
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		File tempFile = null;
				
		try {

			tempFile = File.createTempFile("anteprimaOggetti", "");
			
			Document document = null;
			PdfWriter writer = null;
			
			try {

				Font font_20_bold = new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD);
//				Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		
				document = new Document(PageSize.A4);
				writer = PdfWriter.getInstance(document, new FileOutputStream(tempFile));
		
				document.setMargins(20, 20, 20, 20);
		
				document.newPage();
				document.open();
				
				Paragraph title = new Paragraph("Anteprima oggetti\n\n", font_20_bold);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);
				
				if(StringUtils.isNotBlank(bean.getOggetto()) && bean.getListaDestinatariDiversiXReg() != null && bean.getListaDestinatariDiversiXReg().size() > 0) {
					com.itextpdf.text.List list = new com.itextpdf.text.List();
					List<String> listaPlaceholder = getListaPlaceholderOggetto(bean.getOggetto());			
					for (DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean : bean.getListaDestinatariDiversiXReg()) {
						list.add(generaOggettoWithPlaceholder(bean.getOggetto(), listaPlaceholder, lDestinatariDiversiXRegBean)); 
					}
					document.add(list);						
				}
											
			}  catch(Exception e) {
				throw e;
			} finally {
				try { document.close(); } catch(Exception e) {}
				try { writer.close(); } catch(Exception e) {}
			}
						
			StorageService storageService = StorageImplementation.getStorage();
			lFileDaFirmareBean.setUri(storageService.store(tempFile));
			lFileDaFirmareBean.setNomeFile("Anteprima oggetti.pdf");
	
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setCorrectFileName("Anteprima oggetti.pdf");
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setFirmaValida(false);
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			lMimeTypeFirmaBean.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(tempFile));
			lFileDaFirmareBean.setInfoFile(lMimeTypeFirmaBean);
		
		} catch(Exception e) {
			throw new StoreException("Si è verificato un errore durante la generazione dell'anteprima oggetti");
		} finally {
			try { FileUtil.deleteFile(tempFile); } catch(Exception e) {}
			
		}

		return lFileDaFirmareBean;
	}
	
	private String generaOggettoWithPlaceholder(String oggetto, List<String> listaPlaceholder, DestinatariRegistrazioneMultiplaUscitaXmlBean lDestinatariDiversiXRegBean) {
		if(StringUtils.isNotBlank(oggetto)) {
			if(listaPlaceholder == null) {
				listaPlaceholder = getListaPlaceholderOggetto(oggetto);			
			}
			Map<String, String> mappaIntestazioniColonneValore = lDestinatariDiversiXRegBean.getMappaIntestazioniColonneValore();
			String oggettoWithPlaceholder = "" + oggetto;
			for (int i = 0; i < listaPlaceholder.size(); i++) {
				String valorePlaceholderXls = mappaIntestazioniColonneValore.get(listaPlaceholder.get(i));
				oggettoWithPlaceholder = oggettoWithPlaceholder.replace("$" + listaPlaceholder.get(i) + "$", valorePlaceholderXls);
			}
			return oggettoWithPlaceholder;
		}
		return oggetto;
	}
	
	private List<String> getListaPlaceholderOggetto(String oggetto) {
		List<String> listaPlaceholder = new LinkedList<String> ();
		if(StringUtils.isNotBlank(oggetto)) {
			String app = "" + oggetto;
			while (app.contains("$")) {
				String placeholder = "";
				app = app.substring(app.indexOf("$")+1);
				if (app.contains("$")) {
					placeholder = app.substring(0, app.indexOf("$"));
					if (StringUtils.isNotBlank(placeholder)) {
						listaPlaceholder.add(placeholder);
					}
					app = app.substring(app.indexOf("$")+1);					
				}
			}
		}
		return listaPlaceholder;
	}
	
	public ImportaDestinatariFromXlsRegMultiplaUscitaBean importaDestinatariFromXls(ImportaDestinatariFromXlsRegMultiplaUscitaBean bean) throws Exception {

		List<DestinatariXFileXlsRegMultiplaUscitaBean> listaDestinatariXls = new ArrayList<DestinatariXFileXlsRegMultiplaUscitaBean>();		

		RegistrazioneMultiplaUscitaExcelUtility protMassivaExcelUtility = new RegistrazioneMultiplaUscitaExcelUtility();
		try {
			for(FileXlsDestinatariRegMultiplaUscitaBean lFileXlsBean : bean.getListaFileXls()) {
				
				String uriExcel = lFileXlsBean.getUriExcel();
				String mimeType = lFileXlsBean.getMimeType();
				boolean isXls = mimeType.equals("application/excel");
				boolean isXlsx = mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				DestinatariXFileXlsRegMultiplaUscitaBean datiDestinatari = new DestinatariXFileXlsRegMultiplaUscitaBean();
				/**
				 * Implementazione HSSF (Horrible SpreadSheet Format): indica un'API che funziona con Excel 2003 o versioni precedenti.
				 */
				if (isXls) {
					datiDestinatari = protMassivaExcelUtility.caricaDatiFromXls(uriExcel, getSession(), lFileXlsBean.getNomeFile());
					datiDestinatari.setNomeFile(lFileXlsBean.getNomeFile());
					listaDestinatariXls.add(datiDestinatari);
				}
				
				/**
				 * Implementazione XSSF (XML SpreadSheet Format): indica un'API che funziona con Excel 2007 o versioni successive.
				 */
				else if (isXlsx) { 
					datiDestinatari = protMassivaExcelUtility.caricaDatiFromXlsx(uriExcel, getSession(), lFileXlsBean.getNomeFile());
					datiDestinatari.setNomeFile(lFileXlsBean.getNomeFile());
					listaDestinatariXls.add(datiDestinatari);
				} else {
					String message = "Il formato del documento non è supportato, solo xls e xlsx sono ammessi come documenti validi";
					logger.error(message);
					
					throw new StoreException(message);
				}
			}
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause() != null ? e.getCause().getMessage() : null;

			String message = "Durante il caricamento delle righe del file, si è verificata la seguente eccezione: " + errorMessage;
			logger.error(message, e);

			throw new StoreException(message); 
		}

		bean.setListaDestinatariXFileXls(listaDestinatariXls);
		return bean;
	}
	
	public enum TipoDestinatario implements Serializable {

		PF("Persona fisica"),
		PG("Persona giuridica"),
		PA("PA o sua articolazione"),
		UOI("U.O. interna"),
		UP("Unità di personale");

		private final String value;

		TipoDestinatario(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	public enum MezzoTrasmissione implements Serializable {

		R("Raccomandata"),
		NM("Notifica"),
		PEC("PEC"),
		PEO("PEO"),
		EMAIL("E-mail");

		private final String value;

		MezzoTrasmissione(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	public enum EffettuaAssegnazioneCc implements Serializable {

		ASS("effettua assegnazione"),
		CC("c.c.");

		private final String value;

		EffettuaAssegnazioneCc(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	public enum InvioEmail implements Serializable {

		SI_CON_SEGNATURA("SI con Segnatura.xml"),
		SI_SENZA_SEGNATURA("SI senza Segnatura.xml"),
		NO("NO");

		private final String value;

		InvioEmail(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
}
