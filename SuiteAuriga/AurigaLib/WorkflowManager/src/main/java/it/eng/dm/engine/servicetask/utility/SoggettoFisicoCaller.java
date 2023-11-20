/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
//
//import it.eng.dm.sira.service.SoggettiFisiciService;
//import it.eng.dm.sira.service.bean.SoggettiFisiciServiceInputBean;
//import it.eng.dm.sira.service.bean.SoggettiFisiciServiceOutputBean;
//
//import java.util.List;
//
//import org.activiti.engine.delegate.DelegateExecution;
//import org.activiti.engine.delegate.JavaDelegate;
//import org.activiti.engine.impl.pvm.runtime.ExecutionImpl;
//
//public class SoggettoFisicoCaller implements JavaDelegate {
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
//		String cognome = (String) execution.getVariable("cognome");
//		System.out.println("valore cognome: " + cognome);
//		String nome = (String) execution.getVariable("nome");
//		System.out.println("valore nome: " + nome);
//		SoggettiFisiciService service = new SoggettiFisiciService();
//		service.setEndpoint("http://siranet.sardegnaambiente.it/sira/services/intertematici/search?wsdl");
//		SoggettiFisiciServiceInputBean input = new SoggettiFisiciServiceInputBean();
//		input.setNome(nome);
//		input.setCognome(cognome);
//		List<SoggettiFisiciServiceOutputBean> out = service.search(input);
//		if (out != null && out.size() > 0) {
//			SoggettiFisiciServiceOutputBean soggetto = out.get(0);
//			String codiceFiscale = soggetto.getCodiceFiscale();
//			execution.setVariable("soggetto", codiceFiscale);
//			System.out.println(execution.getVariable("soggetto"));
//		} else {
//			execution.setVariable("soggetto", null);
//			System.out.println("soggetto non trovato");
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		ExecutionImpl ex = new ExecutionImpl();
//		ex.setVariable("cognome", "perra");
//		ex.setVariable("nome", "davide");
//		SoggettoFisicoCaller caller = new SoggettoFisicoCaller();
//		caller.execute(ex);
//	}
//}
