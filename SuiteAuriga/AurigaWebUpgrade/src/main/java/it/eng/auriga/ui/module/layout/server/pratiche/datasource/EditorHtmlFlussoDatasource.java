/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EditorHtmlFlussoBean;
import it.eng.client.DmpkProcessesIuassociazioneudvsproc;
import it.eng.client.GestioneDocumenti;
import it.eng.client.GestioneTask;
import it.eng.document.function.bean.EseguiTaskInBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


@Datasource(id = "EditorHtmlFlussoDatasource")
public class EditorHtmlFlussoDatasource extends AbstractServiceDataSource<EditorHtmlFlussoBean, EditorHtmlFlussoBean>{

	private static Logger mLogger = Logger.getLogger(EditorHtmlFlussoDatasource.class);
	
	@Override
	public EditorHtmlFlussoBean call(EditorHtmlFlussoBean pInBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		
		for(IdFileBean lIdFileBean : pInBean.getFiles()) {
			
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(new BigDecimal(lIdFileBean.getIdFile()));
			lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(lIdFileBean.getUri()));
			MimeTypeFirmaBean lMimeTypeFirmaBean;
			if (lIdFileBean.getInfoFile() != null) {
				lMimeTypeFirmaBean = lIdFileBean.getInfoFile();
			} else {
				lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
			}								
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();		
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(lIdFileBean.getNomeFile());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());				
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);				
			lRebuildedFile.setInfo(lFileInfoBean);
			
			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile); 
				
			VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);	
			if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
				mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutBean);
			}	
			
			DmpkProcessesIuassociazioneudvsprocBean dmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
			dmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
			dmpkProcessesIuassociazioneudvsprocBean.setIdudin(new BigDecimal(lIdFileBean.getIdUd()));
			dmpkProcessesIuassociazioneudvsprocBean.setNroordinevsprocin(null);
			dmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin(null);
			dmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin(null);
			dmpkProcessesIuassociazioneudvsprocBean.setCodstatoudinprocin(null);
	
			DmpkProcessesIuassociazioneudvsproc dmpkProcessesIuassociazioneudvsproc = new DmpkProcessesIuassociazioneudvsproc();
			StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = dmpkProcessesIuassociazioneudvsproc.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), dmpkProcessesIuassociazioneudvsprocBean);
	
			if (result.isInError()){
				throw new StoreException(result);
			}
		
		}
		
		EseguiTaskInBean lEseguiTaskInBean = new EseguiTaskInBean();	
		lEseguiTaskInBean.setInstanceId(pInBean.getInstanceId());
		lEseguiTaskInBean.setActivityName(pInBean.getActivityName());		
		lEseguiTaskInBean.setIdProcess(pInBean.getIdProcess());
		lEseguiTaskInBean.setIdEvento(pInBean.getIdEvento());									
		lEseguiTaskInBean.setIdEventType(pInBean.getIdTipoEvento());
		
		GestioneTask lGestioneTask = new GestioneTask();
		EseguiTaskOutBean lEseguiTaskOutBean = lGestioneTask.salvatask(getLocale(), loginBean, lEseguiTaskInBean);

		if(StringUtils.isNotBlank(lEseguiTaskOutBean.getWarningMessage())) {
			addMessage(lEseguiTaskOutBean.getWarningMessage(), "", MessageType.WARNING);
		}

		if(StringUtils.isNotBlank(lEseguiTaskOutBean.getDefaultMessage())) {
			throw new StoreException(lEseguiTaskOutBean);
		}

		return pInBean;
	}
	
}
