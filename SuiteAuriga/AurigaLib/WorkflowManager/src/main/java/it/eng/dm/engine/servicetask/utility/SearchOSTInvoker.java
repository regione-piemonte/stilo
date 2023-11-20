/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
//
//import it.eng.dm.sira.service.bean.NaturaOST;
//import it.eng.dm.sira.service.bean.SiraRelOstCaratterizzazione;
//import it.eng.dm.sira.service.bean.SoggettoFisicoBeanIn;
//import it.eng.dm.sira.service.search.GenericSearchService;
//import it.eng.dm.sira.service.search.GenericSearchServiceBeanIn;
//
//import java.util.List;
//
//import org.activiti.engine.delegate.DelegateExecution;
//import org.activiti.engine.delegate.JavaDelegate;
//import org.activiti.engine.impl.el.Expression;
//import org.activiti.engine.impl.el.FixedValue;
//import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
//import org.apache.log4j.Logger;
//
//import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
//import com.hyperborea.sira.ws.ReferencedBean;
//import com.hyperborea.sira.ws.SoggettiFisici;
//
//public class SearchOSTInvoker implements JavaDelegate {
//
//	private static Logger log = Logger.getLogger(SearchOSTInvoker.class);
//
//	private Expression tipoCatasto;
//
//	private FixedValue proxy;
//
//	private Expression natura;
//
//	private Expression categoria;
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
//		GenericSearchService service = new GenericSearchService();
//		String catasto = null;
//		String categoriaValue = null;
//		String naturaValue = null;
//		SiraRelOstCaratterizzazione relazione = null;
//		String denominazione = (String) execution.getVariable("denominazione");
//		String cognome = (String) execution.getVariable("cognome");
//		if (proxy != null) {
//			log.debug("passo attraverso un proxy");
//			init();
//		}
//		if (natura == null && categoria == null) {
//			catasto = (String) tipoCatasto.getValue(execution);
//	//		relazione = RelOstWS.getValueForNomeOst(catasto, false);
//		} else {
//			naturaValue = (String) natura.getValue(execution);
//			categoriaValue = (String) categoria.getValue(execution);
//			relazione = SiraRelOstCaratterizzazione.getValueForNaturaCategoria(Integer.parseInt(naturaValue), Integer.parseInt(categoriaValue));
//		}
//		if (relazione != null) {
//			GenericSearchServiceBeanIn input = null;
//			Integer natura = relazione.getNatura();
//			if (natura != Integer.parseInt(NaturaOST.Soggetto_Fisico.getValue())) {
//				input = new GenericSearchServiceBeanIn();
//			} else {
//				input = new SoggettoFisicoBeanIn();
//				((SoggettoFisicoBeanIn) input).setCognome(cognome);
//			}
//			Integer categoria = relazione.getCategoria();
//			input.setCategoria(categoria);
//			input.setNatura(natura);
//			input.setDenominazione(denominazione);
//			List<ReferencedBean> result = service.search(input);
//			for (ReferencedBean bean : result) {
//				if (bean instanceof OggettiStruttureTerritoriali) {
//					OggettiStruttureTerritoriali ogg = (OggettiStruttureTerritoriali) bean;
//					execution.setVariable("risultato", ogg.getCaratterizzazioniOsts()[0].getDenominazione());
//				} else {
//					SoggettiFisici sogg = (SoggettiFisici) bean;
//					execution.setVariable("risultato", sogg.getNome() + " " + sogg.getCognome() + " " + sogg.getCodiceFiscale());
//				}
//			}
//		}
//
//	}
//
//	public Expression getTipoCatasto() {
//		return tipoCatasto;
//	}
//
//	public void setTipoCatasto(Expression tipoCatasto) {
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
//		SearchOSTInvoker invoker = new SearchOSTInvoker();
//		invoker.setProxy(new FixedValue("si"));
//		// invoker.setTipoCatasto(new FixedValue("Sede legale"));
//		// invoker.setTipoCatasto(new FixedValue("Impianto depurazione"));
//		ExecutionImpl execution = new ExecutionImpl();
//		execution.setVariable("denominazione", "biologico");
//		execution.setVariable("tipo_catasto", "Impianto depurazione");
//		execution.setVariable("natura","3");
//		execution.setVariable("categoria","15");
//		invoker.execute(execution);
//
//	}
//
//	public Expression getNatura() {
//		return natura;
//	}
//
//	public void setNatura(Expression natura) {
//		this.natura = natura;
//	}
//
//	public Expression getCategoria() {
//		return categoria;
//	}
//
//	public void setCategoria(Expression categoria) {
//		this.categoria = categoria;
//	}
//
//}
