/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesDassociazioneudvsprocBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.dmpk_processes.store.Dassociazioneudvsproc;
import it.eng.auriga.database.store.dmpk_processes.store.Iuassociazioneudvsproc;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.AllegatoPraticaBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.GestioneAllegatiPraticaBean;
import it.eng.document.function.bean.GestioneAllegatiPraticaOutBean;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Service(name = "GestioneAllegatiPratica")
public class GestioneAllegatiPratica extends GestioneDocumenti {
	
	private static Logger mLogger = Logger.getLogger(GestioneAllegatiPratica.class);
	
	@Operation(name = "creaAllegati")
	public GestioneAllegatiPraticaOutBean creaAllegati(AurigaLoginBean pAurigaLoginBean, GestioneAllegatiPraticaBean pAllegatiPraticaBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		GestioneAllegatiPraticaOutBean lAllegatiPraticaOutBean = new GestioneAllegatiPraticaOutBean();			
		if(pAllegatiPraticaBean != null) {		
			if(pAllegatiPraticaBean.getFileAllegati() != null) {			
				for(int i = 0; i < pAllegatiPraticaBean.getFileAllegati().size(); i++) {					
					AllegatoPraticaBean lAllegatoPraticaBean = pAllegatiPraticaBean.getFileAllegati().get(i);					
					CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
					lCreaModDocumentoInBean.setTipoDocumento(pAllegatiPraticaBean.getIdTipoAllegato());					
					if(lAllegatoPraticaBean.getIdUd() == null) {
						CreaModDocumentoOutBean lCreaModDocumentoOutBean = creaDocumento(pAurigaLoginBean, lCreaModDocumentoInBean, lAllegatoPraticaBean, null);						
						if(StringUtils.isNotBlank(lCreaModDocumentoOutBean.getDefaultMessage())) {
							lAllegatiPraticaOutBean.setErrorCode(lCreaModDocumentoOutBean.getErrorCode());
							lAllegatiPraticaOutBean.setErrorContext(lCreaModDocumentoOutBean.getErrorContext());
							lAllegatiPraticaOutBean.setStoreName(lCreaModDocumentoOutBean.getStoreName());
							lAllegatiPraticaOutBean.setDefaultMessage(lCreaModDocumentoOutBean.getDefaultMessage());
							return lAllegatiPraticaOutBean;
						}						
						DmpkProcessesIuassociazioneudvsprocBean lIuassociazioneudvsprocInput = new DmpkProcessesIuassociazioneudvsprocBean();
						lIuassociazioneudvsprocInput.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lIuassociazioneudvsprocInput.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));					
						lIuassociazioneudvsprocInput.setIdprocessin(new BigDecimal(pAllegatiPraticaBean.getIdProcess()));
						lIuassociazioneudvsprocInput.setIdudin(lCreaModDocumentoOutBean.getIdUd());
						lIuassociazioneudvsprocInput.setNroordinevsprocin(null);
						lIuassociazioneudvsprocInput.setFlgacqprodin(pAllegatiPraticaBean.getFlgAcqProd());
						lIuassociazioneudvsprocInput.setCodruolodocinprocin(pAllegatiPraticaBean.getCodRuoloDocInProd());						
						lIuassociazioneudvsprocInput.setCodstatoudinprocin(pAllegatiPraticaBean.getCodStatoUdInProc()); 						
						Iuassociazioneudvsproc lIuassociazioneudvsproc = new Iuassociazioneudvsproc();
						try {
							lIuassociazioneudvsproc.execute(pAurigaLoginBean, lIuassociazioneudvsprocInput);
						} catch (Exception e) {
							if (e instanceof StoreException){
								BeanUtilsBean2.getInstance().copyProperties(lAllegatiPraticaOutBean, ((StoreException)e).getError());
								return lAllegatiPraticaOutBean;
							} else {
								lAllegatiPraticaOutBean.setDefaultMessage(e.getMessage());
								return lAllegatiPraticaOutBean;
							}						
						}						
					} else {
						CreaModDocumentoOutBean lCreaModDocumentoOutBean = modificaDocumento(pAurigaLoginBean, lAllegatoPraticaBean.getIdUd(), lAllegatoPraticaBean.getIdDocumento(), lCreaModDocumentoInBean, lAllegatoPraticaBean, null);						
						if(StringUtils.isNotBlank(lCreaModDocumentoOutBean.getDefaultMessage())) {
							lAllegatiPraticaOutBean.setErrorCode(lCreaModDocumentoOutBean.getErrorCode());
							lAllegatiPraticaOutBean.setErrorContext(lCreaModDocumentoOutBean.getErrorContext());
							lAllegatiPraticaOutBean.setStoreName(lCreaModDocumentoOutBean.getStoreName());
							lAllegatiPraticaOutBean.setDefaultMessage(lCreaModDocumentoOutBean.getDefaultMessage());
							return lAllegatiPraticaOutBean;
						}
					}				
				}
			}			
			if(pAllegatiPraticaBean.getIdUdToRemove() != null) {				
				for(BigDecimal idUdToRemove : pAllegatiPraticaBean.getIdUdToRemove()) {						
					DmpkProcessesDassociazioneudvsprocBean lDassociazioneudvsprocInput = new DmpkProcessesDassociazioneudvsprocBean();
					lDassociazioneudvsprocInput.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
					lDassociazioneudvsprocInput.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));					
					lDassociazioneudvsprocInput.setIdprocessin(new BigDecimal(pAllegatiPraticaBean.getIdProcess()));
					lDassociazioneudvsprocInput.setIdudin(idUdToRemove);					
					Dassociazioneudvsproc lDassociazioneudvsproc = new Dassociazioneudvsproc();
					try {
						lDassociazioneudvsproc.execute(pAurigaLoginBean, lDassociazioneudvsprocInput);
					} catch (Exception e) {
						if (e instanceof StoreException){
							BeanUtilsBean2.getInstance().copyProperties(lAllegatiPraticaOutBean, ((StoreException)e).getError());
							return lAllegatiPraticaOutBean;
						} else {
							lAllegatiPraticaOutBean.setDefaultMessage(e.getMessage());
							return lAllegatiPraticaOutBean;
						}						
					}								
				}
			}
		}		
		return lAllegatiPraticaOutBean;
	}
	
}
