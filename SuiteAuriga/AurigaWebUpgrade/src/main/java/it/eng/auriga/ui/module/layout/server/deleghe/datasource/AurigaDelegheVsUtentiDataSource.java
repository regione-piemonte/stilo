/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGetdelegheuservsusersBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecuritySetdelegheuservsusersBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.deleghe.datasource.bean.DefinizioneDelegheBean;
import it.eng.auriga.ui.module.layout.server.deleghe.datasource.bean.DelegaVsUtenteBean;
import it.eng.client.DmpkDefSecurityGetdelegheuservsusers;
import it.eng.client.DmpkDefSecuritySetdelegheuservsusers;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AurigaDelegheVsUtentiDataSource")
public class AurigaDelegheVsUtentiDataSource extends AbstractServiceDataSource<DefinizioneDelegheBean, DefinizioneDelegheBean> {

	@Override
	public DefinizioneDelegheBean call(DefinizioneDelegheBean bean) throws Exception {
		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkDefSecurityGetdelegheuservsusersBean input = new DmpkDefSecurityGetdelegheuservsusersBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIduserin(null);
		input.setUsernamein(null);
		input.setDesuserin(null);
		
		DmpkDefSecurityGetdelegheuservsusers store = new DmpkDefSecurityGetdelegheuservsusers();
		
		StoreResultBean<DmpkDefSecurityGetdelegheuservsusersBean> result = store.execute(getLocale(), loginBean, input);
		if (result.isInError()){
			throw new StoreException(result);
		}
		
		DmpkDefSecurityGetdelegheuservsusersBean output = result.getResultBean();
		
		List<DelegaVsUtenteBean> listaDelegheVsUtenti = new ArrayList<DelegaVsUtenteBean>(); 		
		
		if(output.getXmldelegheout() != null) {
			StringReader sr = new StringReader(output.getXmldelegheout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       	DelegaVsUtenteBean delegaVsUtente = new DelegaVsUtenteBean();
			       	delegaVsUtente.setIdUtente(v.get(1)); //colonna 2 dell'xml	
			       	String descrizione = v.get(2); //colonna 3 dell'xml		       	
			       	if(descrizione != null && descrizione.contains("[") && descrizione.contains("]")) {
			       		String cognomeNome = descrizione.substring(0, descrizione.indexOf("[")).trim();
//				       	String username = descrizione.substring(descrizione.indexOf("[") + 1, descrizione.indexOf("]")).trim();
				       	descrizione = cognomeNome/* + " ** " + username*/;				       	
			       	}
			       	delegaVsUtente.setDescrizione(descrizione);	
			       	delegaVsUtente.setDataVldDal(v.get(3) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(3)) : null); //colonna 4 dell'xml	
			       	delegaVsUtente.setDataVldFinoAl(v.get(4) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(4)) : null); //colonna 5 dell'xml	 	
			       	delegaVsUtente.setMotivo(v.get(5)); //colonna 6 dell'xml
			       	listaDelegheVsUtenti.add(delegaVsUtente);		        		
			   	}							
			}	
		}
		
		bean.setListaDelegheVsUtenti(listaDelegheVsUtenti);
		
		return bean;		
	}
			
	public DefinizioneDelegheBean salva(DefinizioneDelegheBean bean) throws Exception{		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkDefSecuritySetdelegheuservsusersBean input = new DmpkDefSecuritySetdelegheuservsusersBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIduserin(null);
		input.setUsernamein(null);
		input.setDesuserin(null);
		input.setFlgmoddeleghein("C");
		input.setXmldeleghein(getXmlDeleghe(bean));
		
		DmpkDefSecuritySetdelegheuservsusers store = new DmpkDefSecuritySetdelegheuservsusers();
		
		StoreResultBean<DmpkDefSecuritySetdelegheuservsusersBean> result = store.execute(getLocale(), loginBean, input);
		
		if (result.isInError()){
			throw new StoreException(result);
		}
		
		return bean;
	}
	
	private String getXmlDeleghe(DefinizioneDelegheBean bean) throws Exception {	
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXmlList(bean.getListaDelegheVsUtenti());		
	}
	
}
