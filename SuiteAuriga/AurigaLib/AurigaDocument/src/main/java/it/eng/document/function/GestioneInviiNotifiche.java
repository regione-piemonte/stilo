/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationAnnullamentoinvioBean;
import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationAnnullamentonotificaBean;
import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationInvioBean;
import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationNotificaBean;
import it.eng.auriga.database.store.dmpk_collaboration.store.impl.AnnullamentoinvioImpl;
import it.eng.auriga.database.store.dmpk_collaboration.store.impl.AnnullamentonotificaImpl;
import it.eng.auriga.database.store.dmpk_collaboration.store.impl.InvioImpl;
import it.eng.auriga.database.store.dmpk_collaboration.store.impl.NotificaImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.ModificaInvioNotificaBean;
import it.eng.document.function.bean.ModificaInvioNotificaOutBean;
import it.eng.storeutil.AnalyzeResult;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

@Service(name = "GestioneInviiNotifiche")
public class GestioneInviiNotifiche {

	private static Logger mLogger = Logger.getLogger(GestioneInviiNotifiche.class);

	@Operation(name = "modificaInvioNotifica")
	public ModificaInvioNotificaOutBean loadFascicolo(AurigaLoginBean pAurigaLoginBean, ModificaInvioNotificaBean pModificaInvioNotificaBean) throws Exception{
		
		ModificaInvioNotificaOutBean lModificaInvioNotificaOutBean = new ModificaInvioNotificaOutBean();
		
		Session session = null;
		
		try {
			SubjectBean subject =  SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject); 
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				
				if("I".equals(pModificaInvioNotificaBean.getFlgInvioNotifica())) {
					DmpkCollaborationAnnullamentoinvioBean lAnnullamentoinvioBean = new DmpkCollaborationAnnullamentoinvioBean();
					lAnnullamentoinvioBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
					lAnnullamentoinvioBean.setIduserlavoroin(getUserLavoro(pAurigaLoginBean));
					lAnnullamentoinvioBean.setIdinviomovimentoin(StringUtils.isNotBlank(pModificaInvioNotificaBean.getIdInvioNotifica()) ? new BigDecimal(pModificaInvioNotificaBean.getIdInvioNotifica()) : null);
					lAnnullamentoinvioBean.setFlgtypeobjsentin(pModificaInvioNotificaBean.getFlgUdFolder());
					lAnnullamentoinvioBean.setIdobjsentin(new BigDecimal(pModificaInvioNotificaBean.getIdUdFolder()));	
					lAnnullamentoinvioBean.setMotivoannin(pModificaInvioNotificaBean.getMotivoAnnullamento());
					lAnnullamentoinvioBean.setFlgautocommitin(new Integer(0));
					
					final AnnullamentoinvioImpl store = new AnnullamentoinvioImpl();
					store.setBean(lAnnullamentoinvioBean);
					session.doWork(new Work() {
						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							store.execute(paramConnection);
						}
					});
					
					StoreResultBean<DmpkCollaborationAnnullamentoinvioBean> result = new StoreResultBean<DmpkCollaborationAnnullamentoinvioBean>();
					AnalyzeResult.analyze(lAnnullamentoinvioBean, result);
					result.setResultBean(lAnnullamentoinvioBean);							

					if (result.isInError()){
						throw new StoreException(result);
					} else {
						DmpkCollaborationInvioBean lInvioBean = new DmpkCollaborationInvioBean();
						lInvioBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lInvioBean.setIduserlavoroin(getUserLavoro(pAurigaLoginBean));
						
						lInvioBean.setFlgtypeobjtosendin(pModificaInvioNotificaBean.getFlgUdFolder());
						lInvioBean.setIdobjtosendin(new BigDecimal(pModificaInvioNotificaBean.getIdUdFolder()));
									
						lInvioBean.setFlgcallbyguiin(new Integer(0));
						lInvioBean.setRecipientsxmlin(pModificaInvioNotificaBean.getXmlDestinatari());
						lInvioBean.setCodmotivoinvioin(pModificaInvioNotificaBean.getMotivoInvio());
						lInvioBean.setMessaggioinvioin(pModificaInvioNotificaBean.getMessaggioInvio());
						lInvioBean.setLivelloprioritain(pModificaInvioNotificaBean.getLivelloPriorita());
						lInvioBean.setTsdecorrenzaassegnazin(pModificaInvioNotificaBean.getTsDecorrenzaAssegnaz() != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pModificaInvioNotificaBean.getTsDecorrenzaAssegnaz()) : null); //datetime
									
						if("U".equals(pModificaInvioNotificaBean.getFlgUdFolder())) {
							if(pModificaInvioNotificaBean.getFlgInviaFascicolo() != null) {
								lInvioBean.setFlginviofascicoloin(pModificaInvioNotificaBean.getFlgInviaFascicolo() ? new Integer(1) : new Integer(0));
							}
							if(pModificaInvioNotificaBean.getFlgInviaDocCollegati() != null) {
								lInvioBean.setFlginvioudcollegatein(pModificaInvioNotificaBean.getFlgInviaDocCollegati() ? new Integer(1) : new Integer(0));
							}
							if(pModificaInvioNotificaBean.getFlgMantieniCopiaUd() != null) {
								lInvioBean.setFlgmantienicopiaudin(pModificaInvioNotificaBean.getFlgMantieniCopiaUd() ? new Integer(1) : new Integer(0));
							}
							lInvioBean.setSenderidin(null);
							lInvioBean.setSendertypein(null);							
						}
						
						final InvioImpl storeInvio = new InvioImpl();
						storeInvio.setBean(lInvioBean);
						session.doWork(new Work() {
							@Override
							public void execute(Connection paramConnection) throws SQLException {
								storeInvio.execute(paramConnection);
							}
						});
						StoreResultBean<DmpkCollaborationInvioBean> resultInvio = new StoreResultBean<DmpkCollaborationInvioBean>();
						AnalyzeResult.analyze(lInvioBean, resultInvio);
						resultInvio.setResultBean(lInvioBean);
																
						if (resultInvio.isInError()){
							throw new StoreException(resultInvio);
						} 
						
					}
					
				} else if("N".equals(pModificaInvioNotificaBean.getFlgInvioNotifica())) {
					DmpkCollaborationAnnullamentonotificaBean lAnnullamentonotificaBean = new DmpkCollaborationAnnullamentonotificaBean();
					lAnnullamentonotificaBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
					lAnnullamentonotificaBean.setIduserlavoroin(getUserLavoro(pAurigaLoginBean));
					lAnnullamentonotificaBean.setIdnotificain(StringUtils.isNotBlank(pModificaInvioNotificaBean.getIdInvioNotifica()) ? new BigDecimal(pModificaInvioNotificaBean.getIdInvioNotifica()) : null);
					lAnnullamentonotificaBean.setFlgtypeobjnotifiedin(pModificaInvioNotificaBean.getFlgUdFolder());
					lAnnullamentonotificaBean.setIdobjnotifiedin(new BigDecimal(pModificaInvioNotificaBean.getIdUdFolder()));				
					lAnnullamentonotificaBean.setMotivoannin(pModificaInvioNotificaBean.getMotivoAnnullamento());
					lAnnullamentonotificaBean.setFlgautocommitin(new Integer(0));
					
					final AnnullamentonotificaImpl store = new AnnullamentonotificaImpl();
					store.setBean(lAnnullamentonotificaBean);
					session.doWork(new Work() {
						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							store.execute(paramConnection);
						}
					});
					
					StoreResultBean<DmpkCollaborationAnnullamentonotificaBean> result = new StoreResultBean<DmpkCollaborationAnnullamentonotificaBean>();
					AnalyzeResult.analyze(lAnnullamentonotificaBean, result);
					result.setResultBean(lAnnullamentonotificaBean);
									
					if (result.isInError()){
						throw new StoreException(result);
					} else {
						DmpkCollaborationNotificaBean lNotificaBean = new DmpkCollaborationNotificaBean();
						lNotificaBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lNotificaBean.setIduserlavoroin(getUserLavoro(pAurigaLoginBean));

						lNotificaBean.setFlgtypeobjtonotifin(pModificaInvioNotificaBean.getFlgUdFolder());
						lNotificaBean.setIdobjtonotifin(new BigDecimal(pModificaInvioNotificaBean.getIdUdFolder()));
								
						lNotificaBean.setFlgcallbyguiin(new Integer(0));
						lNotificaBean.setRecipientsxmlin(pModificaInvioNotificaBean.getXmlDestinatari());
						lNotificaBean.setCodmotivonotifin(pModificaInvioNotificaBean.getMotivoInvio());
						lNotificaBean.setMessaggionotifin(pModificaInvioNotificaBean.getMessaggioInvio());
						lNotificaBean.setLivelloprioritain(pModificaInvioNotificaBean.getLivelloPriorita());
						lNotificaBean.setTsdecorrenzanotifin(pModificaInvioNotificaBean.getTsDecorrenzaAssegnaz() != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(pModificaInvioNotificaBean.getTsDecorrenzaAssegnaz()) : null); //datetime
									
						if("U".equals(pModificaInvioNotificaBean.getFlgUdFolder())) {
							lNotificaBean.setSenderidin(null);
							lNotificaBean.setSendertypein(null);							
						}
						
						final NotificaImpl storeNotifica = new NotificaImpl();
						storeNotifica.setBean(lNotificaBean);
						session.doWork(new Work() {
							@Override
							public void execute(Connection paramConnection) throws SQLException {
								storeNotifica.execute(paramConnection);
							}
						});
						StoreResultBean<DmpkCollaborationNotificaBean> resultNotifica = new StoreResultBean<DmpkCollaborationNotificaBean>();
						AnalyzeResult.analyze(lNotificaBean, resultNotifica);
						resultNotifica.setResultBean(lNotificaBean);
																	
						if (resultNotifica.isInError()){
							throw new StoreException(resultNotifica);
						} 
						
					}
					
				}	
				
				lTransaction.commit();	
			}catch(Exception e){
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException){
					BeanUtilsBean2.getInstance().copyProperties(lModificaInvioNotificaOutBean, ((StoreException)e).getError());
					return lModificaInvioNotificaOutBean;
				} else {
					lModificaInvioNotificaOutBean.setDefaultMessage(e.getMessage());
					return lModificaInvioNotificaOutBean;
				}
			}			
		} catch (Exception e) {
			if (e instanceof StoreException){
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaInvioNotificaOutBean, ((StoreException)e).getError());
				return lModificaInvioNotificaOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaInvioNotificaOutBean.setDefaultMessage(e.getMessage());
				return lModificaInvioNotificaOutBean;
			}
		} finally{
			HibernateUtil.release(session);
		}
		
		return lModificaInvioNotificaOutBean;
		
	}
	
	protected BigDecimal getUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		BigDecimal idUserLavoroIn = StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
		return idUserLavoroIn;
	}
	
}
