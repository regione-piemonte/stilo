/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerIufoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.IufoglioximportImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.CreaFoglioXImportInBean;
import it.eng.document.function.bean.CreaFoglioXImportOutBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.storeutil.AnalyzeResult;

@Service(name = "GestioneFogliXImport")
public class GestioneFogliXImport {
	
	private static Logger mLogger = Logger.getLogger(GestioneFogliXImport.class);
	
	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}
	
	@Operation(name = "creaFoglioXImport")
	public CreaFoglioXImportOutBean creaFoglioXImport(AurigaLoginBean pAurigaLoginBean, CreaFoglioXImportInBean pCreaFoglioXImportInBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		
		CreaFoglioXImportOutBean outBean = new CreaFoglioXImportOutBean();
		String uriFile = null;
		
		try{
			
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				
				// salvo il file nello storage di archiviazione
				// non elimino il temporaneo, viene gestito dai job
				uriFile = DocumentStorage.store(pCreaFoglioXImportInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				if(StringUtils.isEmpty(uriFile)){
					throw new Exception("File non archiviato");
				}
				
							
				DmpkBmanagerIufoglioximportBean lDmpkBmanagerIufoglioximportBean = new DmpkBmanagerIufoglioximportBean();
				lDmpkBmanagerIufoglioximportBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lDmpkBmanagerIufoglioximportBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				
				lDmpkBmanagerIufoglioximportBean.setFlgautocommitin(0); // blocco l'autocommit
				
				// passo URI e tipo contenuto alla store
				lDmpkBmanagerIufoglioximportBean.setUriin(uriFile);
				lDmpkBmanagerIufoglioximportBean.setTipocontenutoin(pCreaFoglioXImportInBean.getTipoContenuto());
				
				final IufoglioximportImpl iufoglioximport = new IufoglioximportImpl();
				iufoglioximport.setBean(lDmpkBmanagerIufoglioximportBean);
				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				
				// effettuo chiamata alla store
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						iufoglioximport.execute(paramConnection);
					}
				});
				
				StoreResultBean<DmpkBmanagerIufoglioximportBean> resultStore = new StoreResultBean<DmpkBmanagerIufoglioximportBean>();
				AnalyzeResult.analyze(lDmpkBmanagerIufoglioximportBean, resultStore);
				resultStore.setResultBean(lDmpkBmanagerIufoglioximportBean);
				
				if (resultStore.isInError()) {
					throw new StoreException(resultStore);
				} else {
					// salvo id foglio restituito dalla store
					outBean.setIdFoglio(resultStore.getResultBean().getIdfoglioio());
				}
				
				session.flush();
				lTransaction.commit();
				
			} catch (StoreException e) {
				
				mLogger.error("Si è verificata la seguente eccezione nel metodo creaFoglioXImport", e);
				BeanUtilsBean2.getInstance().copyProperties(outBean, ((StoreException) e).getError());

				if(StringUtils.isNotEmpty(uriFile)){
					try{
						DocumentStorage.delete(uriFile, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
					}
					catch(Exception e1){						
						mLogger.error("Si è verificata un'eccezione nella cancellazione dal file dallo storage", e);
					}
				}
				
				return outBean;
			} catch (Exception e) {
				
				mLogger.error("Si è verificata la seguente eccezione nel metodo creaFoglioXImport", e);
				outBean.setDefaultMessage(e.getMessage());

				if(StringUtils.isNotEmpty(uriFile)){
					try{
						DocumentStorage.delete(uriFile, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
					}
					catch(Exception e1){						
						mLogger.error("Si è verificata un'eccezione nella cancellazione dal file dallo storage", e);
					}
				}
				
				return outBean;
			} finally {
				HibernateUtil.release(session);
			}
			
		}
		catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore creaFoglioXImport: " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(outBean, ((StoreException) e).getError());
				return outBean;
			} else {
				mLogger.error("Errore creaFoglioXImport: " + e.getMessage(), e);
				outBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return outBean;
			}
		}
		
		return outBean;					

	}

}
