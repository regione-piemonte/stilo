/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetinfoallegistrichintpraticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.wizard.datasource.bean.AllegatoStoreBean;
import it.eng.auriga.ui.module.layout.server.wizard.datasource.bean.WizardDocumentoTreeBean;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkProcessesGetinfoallegistrichintpratica;
import it.eng.client.GestioneDocumenti;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="WizardDocumentiTreeDatasource")
public class WizardDocumentiTreeDatasource extends AbstractFetchDataSource<WizardDocumentoTreeBean>{

	private static final Logger log = Logger.getLogger(WizardDocumentiTreeDatasource.class);
	
	@Override
	public PaginatorBean<WizardDocumentoTreeBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		List<WizardDocumentoTreeBean> lListResult = new ArrayList<WizardDocumentoTreeBean>();
		
		DmpkProcessesGetinfoallegistrichintpraticaBean input = new DmpkProcessesGetinfoallegistrichintpraticaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setAllegatidiin("ISTANZA");
		input.setIdprocessin(new BigDecimal(getExtraparams().get("idProcess")));
//		input.setParametro_1(value);
//		input.setQuadropraticain(value);
		
		DmpkProcessesGetinfoallegistrichintpratica dmpkProcessesGetinfoallegistrichintpratica = new DmpkProcessesGetinfoallegistrichintpratica();
		StoreResultBean<DmpkProcessesGetinfoallegistrichintpraticaBean> output = dmpkProcessesGetinfoallegistrichintpratica.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if(output.getResultBean().getDatiallegatixmlout() != null)
		{
			StringReader sr = new StringReader(output.getResultBean().getDatiallegatixmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null)
			{
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					WizardDocumentoTreeBean bean = new WizardDocumentoTreeBean();
					MimeTypeFirmaBean firmaBean = new MimeTypeFirmaBean();
					
					bean.setIdUd(getExtraparams().get("idUd")!=null ? new Integer(getExtraparams().get("idUd")):null);
					bean.setId(v.get(0));//colonna 1
					bean.setNome(v.get(1));//colonna 2
					bean.setFlagObbligatorio(v.get(2)!=null && new Integer(v.get(2)).intValue()==1 ? true : false);//colonna 3
					bean.setMaxNroDoc(v.get(3)!=null ? new BigDecimal(v.get(3)) : null);//colonna 4
					bean.setIdDocumento(v.get(4));//colonna 5
					
					
					bean.setDimensioneAllegato(v.get(7));//colonna 8
					bean.setQuadro(v.get(10));//colonna 11
					
					firmaBean.setFirmato(v.get(6)!=null && new Integer(v.get(6)).intValue()==1 ? true : false);//colonna 7
					firmaBean.setMimetype(v.get(8));//colonna 9
					firmaBean.setConvertibile(v.get(9)!=null && new Integer(v.get(9)).intValue()==1 ? true : false);//colonna 10
					firmaBean.setImpronta(v.get(11)); //colonna 12
					firmaBean.setCorrectFileName(v.get(5));//colonna 6
					
					bean.setAlgoritmoImpronta(v.get(12));//colonna 13
					bean.setEncodingImpronta(v.get(13));//colonna 14
					bean.setDataOraInserimentoFile(StringUtils.isNotBlank(v.get(14)) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(14)) : null); //colonna 15
					bean.setUri(v.get(15));//colonna 16
					lListResult.add(bean);
				}
			}
		}
		PaginatorBean<WizardDocumentoTreeBean> lPaginatorBean = new PaginatorBean<WizardDocumentoTreeBean>();
		lPaginatorBean.setData(lListResult);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? lListResult.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(lListResult.size());
		return lPaginatorBean;	
	}

	@Override
	public WizardDocumentoTreeBean update(WizardDocumentoTreeBean bean,WizardDocumentoTreeBean oldvalue) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession()); 
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
		if(StringUtils.isBlank(bean.getIdDocumento())) {
		   DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
		   lAdddocInput.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		   lAdddocInput.setIduserlavoroin(null);     
		       
		   AllegatoStoreBean lAllegatoStoreBean = new AllegatoStoreBean();     
		   lAllegatoStoreBean.setIdUd(bean.getIdUd());
		//   lAllegatoStoreBean.setDescrizione(bean.getNome());
		   lAllegatoStoreBean.setIdDocType(bean.getId()!=null ? new Integer(bean.getId()):null);
		       
		   XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();	    
		   lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lAllegatoStoreBean));      
		       
		   DmpkCoreAdddoc lAdddoc = new DmpkCoreAdddoc();
		   StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lAdddoc.execute(getLocale(), lAurigaLoginBean, lAdddocInput);                    
		       
		   if(StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
		    String message = "DMPK_CORE.AddDoc: " + lAdddocOutput.getDefaultMessage();
		    if(lAdddocOutput.isInError()) {
		     throw new Exception(message); 
		    } else {
		    	log.warn(message);        
		    }      
		   }
		   bean.setId(lAdddocOutput.getResultBean().getIddocout().toString());
		  }
		     
		  RebuildedFile lRebuildedFile = new RebuildedFile();
		  lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdDocumento()));
		  lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(bean.getUri()));    
		  MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		  InfoFileUtility lFileUtility = new InfoFileUtility();
		  lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);  
		  FileInfoBean lFileInfoBean = new FileInfoBean();
		  lFileInfoBean.setTipo(TipoFile.ALLEGATO);
		  GenericFile lGenericFile = new GenericFile();
		  setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		  lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		  lGenericFile.setDisplayFilename(bean.getNome());
		  lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		  lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		  lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		  lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());  
		  lFileInfoBean.setAllegatoRiferimento(lGenericFile);  
		  lRebuildedFile.setInfo(lFileInfoBean);
		  
		  VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		  BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 
		  
		  GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		  VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean); 
		  if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
				log.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutBean);
			}	
			
		  return null;
	}
}