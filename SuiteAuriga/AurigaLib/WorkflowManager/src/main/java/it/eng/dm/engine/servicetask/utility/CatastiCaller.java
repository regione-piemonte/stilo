/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
//
//import it.eng.dm.sira.service.SoggettiGiuridiciService;
//import it.eng.dm.sira.service.bean.SoggettiGiuridiciServiceInputBean;
//import it.eng.dm.sira.service.bean.SoggettiGiuridiciServiceOutputBean;
//
//import java.util.List;
//
//import org.activiti.engine.delegate.DelegateExecution;
//import org.activiti.engine.delegate.Expression;
//import org.activiti.engine.delegate.JavaDelegate;
//import org.activiti.engine.impl.el.FixedValue;
//import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
//
//public class CatastiCaller implements JavaDelegate {
//
//	private Expression documentazione;
//
//	private FixedValue tipo_catasto;
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
//		init();
//		if (documentazione != null) {
//			Boolean valoreDocumentazione = (Boolean) documentazione.getValue(execution);
//			System.out.println("valore del field documentazione: " + valoreDocumentazione);
//		}
//		if (tipo_catasto != null) {
//			String valoreTipo = tipo_catasto.getExpressionText();
//			System.out.println("valore del field tipo testo: " + valoreTipo);
//		}
//
//		String catasti = (String) execution.getVariable("catasti");
//		String denominazione = (String) execution.getVariable("denominazione");
//		System.out.println("valore variabile catasti: " + catasti);
//		if (catasti.equals("no")) {
//			execution.setVariable("soggetto", "non trovato");
//			System.out.println("WARNING: NON E' PREVISTA LA CHIAMATA AI CATASTI");
//		} else {
//			SoggettiGiuridiciService service = new SoggettiGiuridiciService();
//			service.setEndpoint("http://sira2.hyperborea.com/sira/services/intertematici/search?wsdl");
//			SoggettiGiuridiciServiceInputBean input = new SoggettiGiuridiciServiceInputBean();
//			input.setDenominazione(denominazione);
//			List<SoggettiGiuridiciServiceOutputBean> out = service.search(input);
//			if (out != null && out.size() > 0) {
//				SoggettiGiuridiciServiceOutputBean soggetto = out.get(0);
//				String ragioneSociale = soggetto.getRagioneSociale();
//				String comune = soggetto.getComune();
//				execution.setVariable("soggetto", ragioneSociale + " " + comune);
//				System.out.println(execution.getVariable("soggetto"));
//			} else {
//				execution.setVariable("soggetto", "non trovato");
//			}
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		ExecutionImpl ex = new ExecutionImpl();
//		ex.setVariable("denominazione", "depura");
//		ex.setVariable("catasti", "si");
//		CatastiCaller caller = new CatastiCaller();
//		caller.execute(ex);
//	}
//
//	public Expression getDocumentazione() {
//		return documentazione;
//	}
//
//	public void setDocumentazione(Expression documentazione) {
//		this.documentazione = documentazione;
//	}
//
//	public FixedValue getTipo_catasto() {
//		return tipo_catasto;
//	}
//
//	public void setTipo_catasto(FixedValue tipo_catasto) {
//		this.tipo_catasto = tipo_catasto;
//	}
//
//}
