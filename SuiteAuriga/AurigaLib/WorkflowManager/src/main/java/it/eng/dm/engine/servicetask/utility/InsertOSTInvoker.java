/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
//
//import it.eng.auriga.database.store.bean.SchemaBean;
//import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
//import it.eng.auriga.database.store.dmpk_login.store.Login;
//import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetprocobjxmlBean;
//import it.eng.auriga.database.store.dmpk_processes.store.Getprocobjxml;
//import it.eng.auriga.database.store.result.bean.StoreResultBean;
//import it.eng.auriga.module.business.beans.AurigaLoginBean;
//import it.eng.auriga.module.business.beans.SpecializzazioneBean;
//import it.eng.dm.sira.service.bean.GenericOSTInsertInputBean;
//import it.eng.dm.sira.service.bean.GenericOSTInsertOutputBean;
//import it.eng.dm.sira.service.bean.NaturaOST;
//import it.eng.dm.sira.service.bean.SiraRelOstCaratterizzazione;
//import it.eng.dm.sira.service.bean.SoggettoFisicoBeanIn;
//import it.eng.dm.sira.service.insert.GenericInsertService;
//import it.eng.dm.sira.service.search.GenericSearchService;
//import it.eng.dm.sira.service.search.GenericSearchServiceBeanIn;
//import it.eng.document.function.GestioneTask;
//import it.eng.document.function.StoreException;
//import it.eng.document.function.bean.EseguiTaskOutBean;
//import it.eng.document.function.bean.SalvaOstInBean;
//import it.eng.document.function.bean.SalvaOstOutBean;
//import it.eng.jaxb.variabili.catasti.ProcObjects;
//import it.eng.jaxb.variabili.catasti.ProcObjects.SezioneCache;
//import it.eng.jaxb.variabili.catasti.ProcObjects.SezioneCache.Variabile;
//import it.eng.jaxb.variabili.catasti.ProcObjects.SezioneCache.Variabile.Lista.Riga;
//import it.eng.jaxb.variabili.catasti.ProcObjects.SezioneCache.Variabile.Lista.Riga.Colonna;
//
//import java.beans.PropertyDescriptor;
//import java.io.StringReader;
//import java.lang.reflect.Array;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
//import javax.xml.bind.JAXBContext;
//
//import org.activiti.engine.delegate.DelegateExecution;
//import org.activiti.engine.delegate.JavaDelegate;
//import org.activiti.engine.impl.el.Expression;
//import org.activiti.engine.impl.el.FixedValue;
//import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//
//import com.hyperborea.sira.ws.CaratterizzazioniOst;
//import com.hyperborea.sira.ws.CostNostId;
//import com.hyperborea.sira.ws.FontiDati;
//import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
//import com.hyperborea.sira.ws.ReferencedBean;
//import com.hyperborea.sira.ws.SoggettiFisici;
//import com.hyperborea.sira.ws.TipologieFontiDati;
//
//public class InsertOSTInvoker implements JavaDelegate {
//
//	private static Logger log = Logger.getLogger(InsertOSTInvoker.class);
//	
//	private FixedValue proxy;
//
//	public void init() {
//		System.setProperty("http.proxyHost", "proxy.eng.it");
//		System.setProperty("http.proxyPort", "3128");
//		System.setProperty("http.proxyUser", "mravagna");
//		System.setProperty("http.proxyPassword", "silh7519");
//	}
//
//	@Override
//	public void execute(DelegateExecution execution) throws Exception {
//		
//		if (proxy != null) {
//			log.debug("--> Passing through Proxy ENG");
//			init();
//		}
//		
//		/* Caricamento variabili necessarie (Locale, AurigaLoginBean e SalvaOstInBean) */
//		Locale locale = new Locale("it");
//		log.info("---> creato Locale");
//		
//		String idProcess = (String) execution.getVariable("idprocess");
//		String idEventType = (String) execution.getVariable("ideventtype");
//		SalvaOstInBean inBean = new SalvaOstInBean();
//		inBean.setIdEventType(idEventType);
//		inBean.setIdProcess(idProcess);
//		log.info("---> creato SalvaOstInBean");
//
//		String schema = (String) execution.getVariable("schema");
//		String idUtente = (String) execution.getVariable("idutente");
//		
//		AurigaLoginBean loginBean = new AurigaLoginBean();
//		loginBean.setIdUtente(idUtente);
//		loginBean.setSchema(schema);
//		log.info("---> creato AurigaLoginBean");
//		
//		DmpkLoginLoginBean lLBean = new DmpkLoginLoginBean();
//		lLBean.setUsernamein(idUtente);
//		
//		Login lDmpkLoginLogin = new Login();
//		StoreResultBean<DmpkLoginLoginBean> result1 = lDmpkLoginLogin.execute(loginBean, lLBean);
//		
//		String token = result1.getResultBean().getCodidconnectiontokenout();
//		
//		loginBean.setToken(token);
//		log.info("----> token AurigaLoginBean");
//		
//		SpecializzazioneBean spec = new SpecializzazioneBean();
//		spec.setCodIdConnectionToken( result1.getResultBean().getCodidconnectiontokenout());
//		spec.setDesDominioOut( result1.getResultBean().getDesdominioout());
//		spec.setDesUserOut( result1.getResultBean().getDesuserout());
//		spec.setIdDominio( result1.getResultBean().getIddominioautio());
//		spec.setParametriConfigOut( result1.getResultBean().getParametriconfigout());
//		spec.setTipoDominio( result1.getResultBean().getFlgtpdominioautio());
//		loginBean.setSpecializzazioneBean(spec);
//		log.info("----> specializzazione AurigaLoginBean");
//		
//		/* Salvataggio */
//		GestioneTask lGestioneTask = new GestioneTask();
//		SalvaOstOutBean outBean = lGestioneTask.salvaOst(loginBean, inBean);
//		log.info("-----> Invocazione servizio salvaost");
//		
//		/* Output */
//		if(outBean != null) {
//			execution.setVariable("numeroostsalvati", outBean.getMappaOstSalvati().size());
//			log.info("------> numeroostsalvati: " + outBean.getMappaOstSalvati().size());
//			
//			String ostSalvati = "";
//			for (String ost : outBean.getMappaOstSalvati().keySet()) {
//				ostSalvati += ost + " ";
//			}
//			execution.setVariable("listaostsalvati", ostSalvati);
//			log.info("------> listaostsalvati: " + ostSalvati);
//			
//		} else {
//			execution.setVariable("numeroostsalvati", 0);
//			log.info("------> Nessun OST salvato");
//		}
//	}
//
//	public FixedValue getProxy() {
//		return proxy;
//	}
//
//	public void setProxy(FixedValue proxy) {
//		this.proxy = proxy;
//	}
//
//	public static void main(String[] args) throws Exception {
//		InsertOSTInvoker invoker = new InsertOSTInvoker();
//		invoker.setProxy(new FixedValue("si"));
//
//		ExecutionImpl execution = new ExecutionImpl();
//		
//		String idProcess = "";
//		String idEventType = "";
//		
//		String idUtente = "VALE";
//		String schema = "OWNER_1";
//		
//		execution.setVariable("idprocess", idProcess);
//		execution.setVariable("ideventtype", idEventType);
//		
//		execution.setVariable("idutente", idUtente);
//		execution.setVariable("schema", schema);
//		
//		invoker.execute(execution);
//	}
//
//}
