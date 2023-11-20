/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIureluouserBean;
import it.eng.auriga.database.store.dmpk_def_security.store.impl.IureluouserImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.OrganigrammaInBean;
import it.eng.document.function.bean.OrganigrammaOutBean;
import it.eng.storeutil.AnalyzeResult;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

@Service(name = "GestioneOrganigramma")
public class GestioneOrganigramma {

	private static Logger mLogger = Logger.getLogger(GestioneOrganigramma.class);

	@Operation(name = "spostaUtenti")
	public OrganigrammaOutBean spostaUtenti(AurigaLoginBean pAurigaLoginBean, OrganigrammaInBean organigrammaCopy, OrganigrammaInBean organigrammaPaste) throws Exception{
		
		DmpkDefSecurityIureluouserBean bean = new DmpkDefSecurityIureluouserBean();
		OrganigrammaOutBean organigrammaBeanOutput = new OrganigrammaOutBean();
		
		bean.setFlgautocommitin(0);
		
		bean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		bean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		
		//CESSAZIONE
		
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		
		if (yesterday.before(new SimpleDateFormat("dd/MM/yyyy").parse(organigrammaCopy.getDataInizioValidita())))
			bean.setDtfinevldin(organigrammaCopy.getDataInizioValidita());
		else
			bean.setDtfinevldin(new SimpleDateFormat("dd/MM/yyyy").format(yesterday));

		bean.setIduoin(new BigDecimal(organigrammaCopy.getIdUO()));
		
		bean.setCireluseruoio(organigrammaCopy.getIdUO()+"."+organigrammaCopy.getIdUser()+"."+organigrammaCopy.getTipoRelazioneUtente()+"."+organigrammaCopy.getDataInizioValidita()+".");
		bean.setIduserin(new BigDecimal(organigrammaCopy.getIdUser()));
		bean.setDtiniziovldin(organigrammaCopy.getDataInizioValidita());
		bean.setFlgtiporelin(organigrammaCopy.getTipoRelazioneUtente());
		bean.setFlgxscrivaniain("V");
		
		try{
			SubjectBean subject =  SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject); 
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try{
				// FACCIO LA CESSAZIONE
				saveNode(pAurigaLoginBean, bean, session);		
				
				//FACCIO LO SPOSTAMENTO
				bean.setIduoin(new BigDecimal(organigrammaPaste.getIdUO()));
				bean.setDtiniziovldin( new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				bean.setDtfinevldin(organigrammaPaste.getDataFineValidita() == null ? null : organigrammaPaste.getDataFineValidita().toString());
				bean.setFlgxscrivaniain("N");
				bean.setCireluseruoio(null);

				saveNode(pAurigaLoginBean, bean, session);	
				
				lTransaction.commit();	
				
			}
			
			catch(Exception e){
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException){
					BeanUtilsBean2.getInstance().copyProperties(organigrammaBeanOutput, ((StoreException)e).getError());
					return organigrammaBeanOutput;
				} else {
					organigrammaBeanOutput.setDefaultMessage(e.getMessage());
					return organigrammaBeanOutput;
				}
			}
			
			finally{
				HibernateUtil.release(session);
			}
			
		}
		catch (Exception e) {
			if (e instanceof StoreException){
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(organigrammaBeanOutput, ((StoreException)e).getError());
				return organigrammaBeanOutput;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				organigrammaBeanOutput.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");				
				return organigrammaBeanOutput;
			}
		}
		
		return organigrammaBeanOutput;
	}
	
	private void saveNode(final AurigaLoginBean pAurigaLoginBean, final DmpkDefSecurityIureluouserBean bean, Session session) throws Exception{
		
		final IureluouserImpl store = new IureluouserImpl();
		store.setBean(bean);
		
		LoginService lLoginService = new LoginService();
		lLoginService.login(pAurigaLoginBean);
		
		session.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException{
				paramConnection.setAutoCommit(false);
				store.execute(paramConnection);
			}
		});
		
		StoreResultBean<DmpkDefSecurityIureluouserBean> result = new StoreResultBean<DmpkDefSecurityIureluouserBean>();
		AnalyzeResult.analyze(bean, result);
		result.setResultBean(bean);
		if (result.isInError()){
			throw new StoreException(result);
		}
	}
	
	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {		
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}
}
