/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AllegaDocumentazioneBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AllegatoProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EventoCustomXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.util.EventoCustomCreator;
import it.eng.client.DmpkProcessesIueventocustom;
import it.eng.client.GestioneAllegatiPratica;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.AllegatoPraticaBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.GestioneAllegatiPraticaBean;
import it.eng.document.function.bean.GestioneAllegatiPraticaOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


@Datasource(id="AllegatiPraticaDataSource")
public class AllegatiPraticaDataSource extends AbstractServiceDataSource<AllegaDocumentazioneBean,AllegaDocumentazioneBean> {
	
private static Logger mLogger = Logger.getLogger(AllegatiPraticaDataSource.class);
	
	@Override
	public AllegaDocumentazioneBean call(AllegaDocumentazioneBean pInBean)
			throws Exception {
		mLogger.debug("Start call");
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();
		
		GestioneAllegatiPraticaBean lGestioneAllegatiPraticaBean = new GestioneAllegatiPraticaBean();
		lGestioneAllegatiPraticaBean.setIdProcess(pInBean.getIdProcess());
		
		List<BigDecimal> idUdListToRemove = new ArrayList<BigDecimal>();
		if(pInBean.getListaAllegatiProcSaved() != null) {
			for(int i = 0; i < pInBean.getListaAllegatiProcSaved().size(); i++) {
				AllegatoProcBean lAllegatoProcBean = pInBean.getListaAllegatiProcSaved().get(i);
				idUdListToRemove.add(lAllegatoProcBean.getIdUd() != null && !"".equals(lAllegatoProcBean.getIdUd()) ? new BigDecimal(lAllegatoProcBean.getIdUd()) : null);
			}
		}
			
		List<AllegatoPraticaBean> fileAllegati = new ArrayList<AllegatoPraticaBean>();
		if(pInBean.getListaAllegatiProcToSave() != null) {
			for(int i = 0; i < pInBean.getListaAllegatiProcToSave().size(); i++) {
				AllegatoProcBean lAllegatoProcBean = pInBean.getListaAllegatiProcToSave().get(i);
				if(lAllegatoProcBean.getIdUd() != null && !"".equals(lAllegatoProcBean.getIdUd()) && idUdListToRemove.contains(new BigDecimal(lAllegatoProcBean.getIdUd()))) {
					idUdListToRemove.remove(lAllegatoProcBean.getIdUd());
				}
				if (StringUtils.isNotBlank(lAllegatoProcBean.getUri()) && StringUtils.isNotBlank(lAllegatoProcBean.getDisplayName())){
					AllegatoPraticaBean lAllegatoPraticaBean = new AllegatoPraticaBean();
					File lFile = null;
					//Il file è esterno					
					if (lAllegatoProcBean.getRemoteUri() != null && lAllegatoProcBean.getRemoteUri()){
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(lAllegatoProcBean.getUri());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						//File locale
						lFile = StorageImplementation.getStorage().extractFile(lAllegatoProcBean.getUri());
					}
					lAllegatoPraticaBean.setFile(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = lAllegatoProcBean.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.PRIMARIO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(lAllegatoProcBean.getDisplayName());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lAllegatoProcBean.getAlgoritmoImpronta());
					lGenericFile.setEncoding(lAllegatoProcBean.getEncoding());
					if (lMimeTypeFirmaBean.isDaScansione()){
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser()+"");
					}  

					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					lAllegatoPraticaBean.setInfo(lFileInfoBean);	
					lAllegatoPraticaBean.setIdDocumento(lAllegatoProcBean.getIdDoc() != null && !"".equals(lAllegatoProcBean.getIdDoc()) ? new BigDecimal(lAllegatoProcBean.getIdDoc()) : null);
					lAllegatoPraticaBean.setIsNewOrChanged(lAllegatoProcBean.getIdDoc()== null || lAllegatoProcBean.getIsChanged());
					fileAllegati.add(lAllegatoPraticaBean);
				}						
			}
		}
		
		lGestioneAllegatiPraticaBean.setIdTipoAllegato(pInBean.getIdTipoDocumento());
		lGestioneAllegatiPraticaBean.setIdUdToRemove(idUdListToRemove);
		lGestioneAllegatiPraticaBean.setFileAllegati(fileAllegati);
		lGestioneAllegatiPraticaBean.setFlgAcqProd(getExtraparams().get("flgAcqProd"));
		lGestioneAllegatiPraticaBean.setCodRuoloDocInProd(getExtraparams().get("codRuoloDocInProd"));	
		lGestioneAllegatiPraticaBean.setCodStatoUdInProc(getExtraparams().get("codStatoUdInProc"));
		
		
		GestioneAllegatiPratica lGestioneAllegatiPratica = new GestioneAllegatiPratica();
		GestioneAllegatiPraticaOutBean lGestioneAllegatiPraticaOutBean = lGestioneAllegatiPratica.creaallegati(getLocale(), lAurigaLoginBean, lGestioneAllegatiPraticaBean);

		if(lGestioneAllegatiPraticaOutBean.getDefaultMessage() != null) {
			throw new StoreException(lGestioneAllegatiPraticaOutBean);
		}
		
		DmpkProcessesIueventocustomBean lBean = EventoCustomCreator.buildCustomForSave(convertBeanToXmlBean(pInBean), lAurigaLoginBean);
		
		DmpkProcessesIueventocustom store = new DmpkProcessesIueventocustom();
		
		StoreResultBean<DmpkProcessesIueventocustomBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()){
			throw new StoreException(result);
		}
		addMessage("Salvataggio effettuato con successo", "", MessageType.INFO);
		
		return pInBean;
	}	
	
	public EventoCustomXmlBean convertBeanToXmlBean(AllegaDocumentazioneBean pInBean) {
		EventoCustomXmlBean pInBeanXml = new EventoCustomXmlBean();
		pInBeanXml.setIdProcess(pInBean.getIdProcess());
		pInBeanXml.setIdEvento(pInBean.getIdEvento());
		pInBeanXml.setIdTipoEvento(pInBean.getIdTipoEvento());		
		return pInBeanXml;
	}

}


