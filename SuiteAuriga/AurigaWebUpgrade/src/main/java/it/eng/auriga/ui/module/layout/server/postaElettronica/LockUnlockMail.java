/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.AurigaGetDettaglioPostaElettronicaDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.LockEmailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.UnlockEmailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ControlloLockEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.LockEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.UnlockEmailBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.utility.ui.user.AurigaUserUtil;

public class LockUnlockMail {
	
	private static Logger mLogger = Logger.getLogger(LockUnlockMail.class);
	private HttpSession session;
	
	public LockUnlockMail(HttpSession session) {
		this.session = session;
	}
	
	public TEmailMgoBean lockMail(String idEmail) throws Exception {

		PostaElettronicaBean bean = new PostaElettronicaBean();
		bean.setIdEmail(idEmail);
		
		TEmailMgoBean lTEmailMgoBean = new TEmailMgoBean();
		
		DettaglioPostaElettronicaBean lDettaglioBeanIn = new DettaglioPostaElettronicaBean();
		lDettaglioBeanIn.setIdEmail(bean.getIdEmail());
		AurigaGetDettaglioPostaElettronicaDataSource lAurigaGetDettaglioDS = new AurigaGetDettaglioPostaElettronicaDataSource();
		lAurigaGetDettaglioDS.setSession(getSession());
		
		DettaglioPostaElettronicaBean lDettaglioBeanOut = lAurigaGetDettaglioDS.get(lDettaglioBeanIn);

		lTEmailMgoBean.setIdEmail(lDettaglioBeanOut.getIdEmail());
		lTEmailMgoBean.setCorpo(lDettaglioBeanOut.getBody());
		lTEmailMgoBean.setCategoria(lDettaglioBeanOut.getCategoria());
		lTEmailMgoBean.setTsInvio(lDettaglioBeanOut.getTsInvio());

		List<PostaElettronicaBean> lListaRecord = new ArrayList<>();
		lListaRecord.add(bean);

		LockEmailBean lLockEmailBeanIn = new LockEmailBean();
		lLockEmailBeanIn.setIduserlockfor(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdUtenteModPec());
		lLockEmailBeanIn.setListaRecord(lListaRecord);

		LockEmailDataSource lLockEmailDataSource = new LockEmailDataSource();
		lLockEmailDataSource.setSession(getSession());
		lLockEmailDataSource.add(lLockEmailBeanIn);			
		
		return lTEmailMgoBean;
	}
	
	public TEmailMgoBean lockMailFromProtEmail(DettaglioPostaElettronicaBean pInBean) throws Exception {

		PostaElettronicaBean postaElettronicaBean = new PostaElettronicaBean();
		postaElettronicaBean.setIdEmail(pInBean.getIdEmail());
		
		TEmailMgoBean lTEmailMgoBean = new TEmailMgoBean();

		lTEmailMgoBean.setIdEmail(pInBean.getIdEmail());
		lTEmailMgoBean.setCorpo(pInBean.getBody());
		lTEmailMgoBean.setCategoria(pInBean.getCategoria());
		lTEmailMgoBean.setTsInvio(pInBean.getTsInvio());

		List<PostaElettronicaBean> lListaRecord = new ArrayList<>();
		lListaRecord.add(postaElettronicaBean);

		LockEmailBean lLockEmailBeanIn = new LockEmailBean();
		lLockEmailBeanIn.setIduserlockfor(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdUtenteModPec());
		lLockEmailBeanIn.setListaRecord(lListaRecord);

		LockEmailDataSource lLockEmailDataSource = new LockEmailDataSource();
		lLockEmailDataSource.setSession(getSession());
		lLockEmailDataSource.add(lLockEmailBeanIn);			
		
		return lTEmailMgoBean;
	}
	
	/**
	 * NEW recuperaIsLocked
	 * 
	 * @param bean
	 * @return
	 * @throws StoreException 
	 * @throws Exception
	 */
	public boolean recuperaIsLocked(String idEmail) throws Exception  {
		PostaElettronicaBean bean = new PostaElettronicaBean();
		bean.setIdEmail(idEmail);
		
		LockEmailDataSource lLockEmailDataSource = new LockEmailDataSource();
		lLockEmailDataSource.setSession(getSession());
		
		ControlloLockEmailBean lControlloLockEmailBean = lLockEmailDataSource.checkLockEmail(bean);		
		return lControlloLockEmailBean.isFlagPresenzaLock();
	}
	
	/**
 	 * NEW unlockMail
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public TEmailMgoBean unlockMail(String idEmail) throws Exception {
		PostaElettronicaBean bean = new PostaElettronicaBean();
		bean.setIdEmail(idEmail);
		
		TEmailMgoBean lTEmailMgoBean = null;
		
		List<PostaElettronicaBean> lListaRecord = new ArrayList<>();
		lListaRecord.add(bean);
		
		UnlockEmailBean lUnlockEmailBeanIn = new UnlockEmailBean();
		lUnlockEmailBeanIn.setListaRecord(lListaRecord);
		
		UnlockEmailDataSource lUnlockEmailDS = new UnlockEmailDataSource();
		lUnlockEmailDS.setSession(getSession());
		lUnlockEmailDS.add(lUnlockEmailBeanIn);
		
		// al momento ignoriamo gli errori
//		if (lUnlockEmailBeanOut.getErrorMessages() != null && !lUnlockEmailBeanOut.getErrorMessages().isEmpty()) {
//			throw new StoreException("Errore durante l'unlock dell'email con id " + bean.getIdEmail() + ": " + lUnlockEmailBeanOut.getErrorMessages().values().iterator().next());
//		} else {			
			lTEmailMgoBean = new TEmailMgoBean();
			lTEmailMgoBean.setIdEmail(bean.getIdEmail());
			lTEmailMgoBean.setCorpo(bean.getCorpo());
			lTEmailMgoBean.setCategoria(bean.getCategoria());
			lTEmailMgoBean.setTsInvio(bean.getTsInvio());
//		}
		
		return lTEmailMgoBean;
	}

	private HttpSession getSession() {
		return session;
	}

}