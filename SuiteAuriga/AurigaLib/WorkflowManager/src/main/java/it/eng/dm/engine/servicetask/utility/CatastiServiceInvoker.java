/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
//
//import it.eng.core.business.beans.AbstractBean;
//import it.eng.dm.engine.manage.bean.RelazioneOSTWebService;
//import it.eng.dm.engine.manage.bean.TipoOperazione;
//import it.eng.dm.sira.service.bean.SoggettiGiuridiciServiceInputBean;
//import it.eng.dm.sira.service.bean.SoggettiGiuridiciServiceOutputBean;
//import it.eng.dm.sira.service.general.ISiraService;
//
//import java.util.List;
//
//import org.activiti.engine.delegate.DelegateExecution;
//import org.activiti.engine.delegate.JavaDelegate;
//import org.activiti.engine.impl.el.FixedValue;
//import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
//import org.apache.log4j.Logger;
//
//public class CatastiServiceInvoker implements JavaDelegate {
//
//	private static Logger log = Logger.getLogger(CatastiServiceInvoker.class);
//
//	private FixedValue tipoOperazione;
//
//	private FixedValue tipoCatasto;
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
//		ISiraService service = null;
//		String denominazione = (String) execution.getVariable("denominazione");
//		if (proxy != null) {
//			log.debug("passo attraverso un proxy");
//			init();
//		}
//		if (tipoOperazione == null || tipoCatasto == null) {
//			log.debug("Impossibile continuare: valori obbligatori mancanti");
//			throw new Exception("Impossibile continuare: valori obbligatori mancanti");
//		}
//		String operazione = tipoOperazione.getExpressionText();
//		if (operazione.equals(TipoOperazione.RICERCA.getValue())) {
////			String catasto = tipoCatasto.getExpressionText();
//			if (catasto.equalsIgnoreCase(RelazioneOSTWebService.SOGGETTO_GIURIDICO.getNomeOst())) {
//				service = (ISiraService<AbstractBean, AbstractBean>) Class.forName(RelazioneOSTWebService.SOGGETTO_GIURIDICO.getServizio())
//						.newInstance();
//				((SoggettiGiuridiciService)service).setEndpoint("http://siranet.sardegnaambiente.it/sira/services/intertematici/search?wsdl");
//				SoggettiGiuridiciServiceInputBean input = new SoggettiGiuridiciServiceInputBean();
//				input.setDenominazione(denominazione);
//				List<AbstractBean> listaOut = service.search(input);
//				for (AbstractBean bean : listaOut) {
//					SoggettiGiuridiciServiceOutputBean out = (SoggettiGiuridiciServiceOutputBean) bean;
//					log.debug(out.getRagioneSociale());
//				}
//			} else {
//				log.warn("catasto non previsto");
//			}
//
//		} else {
//			log.warn("operazione ancora non supportata");
//		}
//
//	}
//
//	public FixedValue getTipoOperazione() {
//		return tipoOperazione;
//	}
//
//	public void setTipoOperazione(FixedValue tipoOperazione) {
//		this.tipoOperazione = tipoOperazione;
//	}
//
//	public FixedValue getTipoCatasto() {
//		return tipoCatasto;
//	}
//
//	public void setTipoCatasto(FixedValue tipoCatasto) {
//		this.tipoCatasto = tipoCatasto;
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
//		CatastiServiceInvoker invoker = new CatastiServiceInvoker();
//		invoker.setProxy(new FixedValue("si"));
//		invoker.setTipoCatasto(new FixedValue("Soggetto Giuridico"));
//		invoker.setTipoOperazione(new FixedValue("ricerca"));
//		ExecutionImpl execution = new ExecutionImpl();
//		execution.setVariable("denominazione", "depura");
//		invoker.execute(execution);
//	}
//
//}
