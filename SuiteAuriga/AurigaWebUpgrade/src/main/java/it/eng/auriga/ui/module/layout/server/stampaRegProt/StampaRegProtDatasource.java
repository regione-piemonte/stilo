/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocStamparegistroBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.DatiRegistrazioniOut;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.DatiUdStampaOut;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.ParametriRegistroIn;
import it.eng.auriga.ui.module.layout.server.stampaRegProt.bean.StampaRegProtBean;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.DmpkRegistrazionedocStamparegistro;
import it.eng.client.GestioneDocumenti;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

@Datasource(id = "StampaRegProtDatasource")
public class StampaRegProtDatasource extends AbstractDataSource<StampaRegProtBean, StampaRegProtBean>{	

	@Override
	public StampaRegProtBean add(StampaRegProtBean bean) throws Exception {		
			
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRegistrazionedocStamparegistroBean stampaRegistroInput = new DmpkRegistrazionedocStamparegistroBean();
		stampaRegistroInput.setCodidconnectiontokenin(token);
		stampaRegistroInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		if(StringUtils.isNotBlank(bean.getIdJob())) {
			stampaRegistroInput.setIdjobio(new BigDecimal(bean.getIdJob()));
		} else {		
			ParametriRegistroIn parametriRegistro = new ParametriRegistroIn();
			
			parametriRegistro.setCodCategoriaRegistro(bean.getCodCategoriaRegistro());
			parametriRegistro.setSiglaRegistro(bean.getSiglaRegistro());
			
//			if(bean.getRegistro().equals("PG")) {
//				parametriRegistro.setCodCategoriaRegistro("PG");			
//			} else if(bean.getRegistro().equals("PI")) {
//				parametriRegistro.setCodCategoriaRegistro("I");
//				parametriRegistro.setSiglaRegistro("P.I.");					
//			}		
			if(bean.getTipoIntervallo().equals("D")) {
				parametriRegistro.setDataRegistrazioneDa(bean.getDataRegDa());
				parametriRegistro.setDataRegistrazioneA(bean.getDataRegA());			
			} else if(bean.getTipoIntervallo().equals("N")) {
				parametriRegistro.setAnnoRegistro(bean.getAnnoReg());	
				parametriRegistro.setNroRegistrazioneDa(bean.getNroRegDa());
				parametriRegistro.setNroRegistrazioneA(bean.getNroRegA());
			}					
			parametriRegistro.setSchermaRiservati(bean.getSchermaRiservati() != null && bean.getSchermaRiservati() ? Flag.SETTED : Flag.NOT_SETTED);
			stampaRegistroInput.setParametriregistroin(new XmlUtilitySerializer().bindXml(parametriRegistro));
		}
		
		DmpkRegistrazionedocStamparegistro stamparegistro = new DmpkRegistrazionedocStamparegistro();
		StoreResultBean<DmpkRegistrazionedocStamparegistroBean> stampaRegistroOutput = stamparegistro.execute(getLocale(),loginBean, stampaRegistroInput);
					
		if(stampaRegistroOutput.getDefaultMessage() != null) {
			throw new StoreException(stampaRegistroOutput);
		}
						
		if(stampaRegistroOutput.getResultBean().getIdjobio() != null) {
			bean.setIdJob(String.valueOf(stampaRegistroOutput.getResultBean().getIdjobio()));	
		} else {					
			
			DatiUdStampaOut datiUdStampa = new XmlUtilityDeserializer().unbindXml(stampaRegistroOutput.getResultBean().getDatiudstampaout(), DatiUdStampaOut.class);
			
			try {
				
				if(StringUtils.isNotBlank(stampaRegistroOutput.getDefaultMessage())) {
					if(stampaRegistroOutput.isInError()) {
						throw new StoreException(stampaRegistroOutput);		
					} else {
						addMessage(stampaRegistroOutput.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
				
				List<DatiRegistrazioniOut> datiRegistrazioniList = XmlListaUtility.recuperaLista(stampaRegistroOutput.getResultBean().getDatiregistrazionixmlout(), DatiRegistrazioniOut.class);
				List<DatiRegistrazioniOut> datiVarRegistrazioniList = XmlListaUtility.recuperaLista(stampaRegistroOutput.getResultBean().getDativarregistrazionixmlout(), DatiRegistrazioniOut.class);
				
				if(datiRegistrazioniList.size() > 0 || datiVarRegistrazioniList.size() > 0){
					
					String tipoReport = stampaRegistroOutput.getResultBean().getTiporeportout();
					if (tipoReport==null ||tipoReport.equalsIgnoreCase("")) {
						tipoReport ="PROTOCOLLO";
					}
									
					ArrayList<File> fileToMerge = new ArrayList<File>();
					
					if(datiRegistrazioniList.size() > 0) {
						
						String headerRegistro = stampaRegistroOutput.getResultBean().getHeaderregistroout();
						String footerRegistro = stampaRegistroOutput.getResultBean().getFooterregistroout();
						
						File fileRegistro = File.createTempFile("stamparegprot", ".pdf");	
						
						StampaRegProt.getInstance().stampaRegProt(fileRegistro, datiRegistrazioniList, headerRegistro, footerRegistro, 0, tipoReport);
						
						fileToMerge.add(fileRegistro);
						
					}	
					
					if(datiVarRegistrazioniList.size() > 0) {
						
						String headerVarRegistro = stampaRegistroOutput.getResultBean().getHeadervarregistroout();
						String footerVarRegistro = stampaRegistroOutput.getResultBean().getFootervarregistroout();
												
						File fileVarRegistro = File.createTempFile("stampavarregprot", ".pdf");	
						
						StampaRegProt.getInstance().stampaRegProt(fileVarRegistro, datiVarRegistrazioniList, headerVarRegistro, footerVarRegistro, 0, tipoReport);
						
						fileToMerge.add(fileVarRegistro);
						
					}	
					
					File fileOut = null;
					if(fileToMerge.size() == 1) {
						fileOut = fileToMerge.get(0);						
					} else {
						fileOut = File.createTempFile("mergestamparegprot", ".pdf");
						StampaRegProt.concatFiles(fileToMerge, fileOut);
					}
					
					bean.setUri(StorageImplementation.getStorage().store(fileOut));
					
					DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setIdDocumento(new BigDecimal(datiUdStampa.getIdDoc()));
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
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename((datiUdStampa.getDisplayFilename()));
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());				
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);				
					lRebuildedFile.setInfo(lFileInfoBean);
					
					VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
					BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 
					
					GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
					VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);	
					if (lVersionaDocumentoOutBean.getDefaultMessage() != null){
						throw new StoreException(lVersionaDocumentoOutBean);
					}
				} else {				
					//nel caso non ci siano dati da stampare
					return bean;
				}
				
			} catch (Exception e) {
				
				DmpkCoreDel_ud_doc_verBean delUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
				delUdDocVerInput.setFlgtipotargetin("U");
				delUdDocVerInput.setIduddocin(new BigDecimal(datiUdStampa.getIdUd()));
				delUdDocVerInput.setFlgcancfisicain(new Integer(1));
				
				DmpkCoreDel_ud_doc_ver delUdDocVer = new DmpkCoreDel_ud_doc_ver();
				delUdDocVer.execute(getLocale(),loginBean, delUdDocVerInput);
				
				throw new Exception("Stampa registro fallita", e);
			}
			
		}
			
		return bean;
	}
		
	@Override
	public StampaRegProtBean get(StampaRegProtBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public StampaRegProtBean remove(StampaRegProtBean bean) throws Exception {
		
		return null;
	}

	@Override
	public StampaRegProtBean update(StampaRegProtBean bean, StampaRegProtBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<StampaRegProtBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(StampaRegProtBean bean) throws Exception {
		
		return null;
	}
		
}
