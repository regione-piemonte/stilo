/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetsoggconprofilivscasellaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailSetsoggconprofilivscasellaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.CasellaEmailBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.FruitoreCasellaBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.UtenteCasellaBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.XmlFruitoriProfBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.XmlUtentiProfBean;
import it.eng.client.DmpkIntMgoEmailGetsoggconprofilivscasella;
import it.eng.client.DmpkIntMgoEmailSetsoggconprofilivscasella;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="SoggettiAbilitatiCasellaDatasource")
public class SoggettiAbilitatiCasellaDatasource extends AbstractFetchDataSource<CasellaEmailBean>{
	
	@Override
	public CasellaEmailBean get(CasellaEmailBean bean) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailGetsoggconprofilivscasellaBean input = new DmpkIntMgoEmailGetsoggconprofilivscasellaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setIdcasellain(bean.getIdCasella());
			
		DmpkIntMgoEmailGetsoggconprofilivscasella store = new DmpkIntMgoEmailGetsoggconprofilivscasella();
        StoreResultBean<DmpkIntMgoEmailGetsoggconprofilivscasellaBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
        bean.setIdSpAoo(output.getResultBean().getIdenteaooout() != null ? String.valueOf(output.getResultBean().getIdenteaooout()) : null);
         
        List<FruitoreCasellaBean> listaFruitoriCasella = new ArrayList<FruitoreCasellaBean>();
    	if(StringUtils.isNotBlank(output.getResultBean().getXmlfruitoriprofout())) {
        	List<XmlFruitoriProfBean> listaXmlFruitoriProf = XmlListaUtility.recuperaLista(output.getResultBean().getXmlfruitoriprofout(), XmlFruitoriProfBean.class); 
        	for(int i = 0; i < listaXmlFruitoriProf.size(); i++) {
        		XmlFruitoriProfBean lXmlFruitoriProfBean = listaXmlFruitoriProf.get(i);
        		FruitoreCasellaBean lFruitoreCasellaBean = new FruitoreCasellaBean();
        		lFruitoreCasellaBean.setTipo(lXmlFruitoriProfBean.getTipo());
        		if(lFruitoreCasellaBean.getTipo() != null && "AOO".equalsIgnoreCase(lFruitoreCasellaBean.getTipo())) {
        			lFruitoreCasellaBean.setTipo("ENTE");
        		}
        		lFruitoreCasellaBean.setCodRapido(lXmlFruitoriProfBean.getCodice());
        		lFruitoreCasellaBean.setIdFruitoreCasella(lXmlFruitoriProfBean.getIdFruitoreCasella());
        		lFruitoreCasellaBean.setDenominazione(lXmlFruitoriProfBean.getDenominazione());        		
        		lFruitoreCasellaBean.setFlgSmistatore(lXmlFruitoriProfBean.getFlgSmistatore() != null && "1".equals(lXmlFruitoriProfBean.getFlgSmistatore()));
        		lFruitoreCasellaBean.setFlgMittente(lXmlFruitoriProfBean.getFlgMittente() != null && "1".equals(lXmlFruitoriProfBean.getFlgMittente()));
        		lFruitoreCasellaBean.setFlgAmministratore(lXmlFruitoriProfBean.getFlgAmministratore() != null && "1".equals(lXmlFruitoriProfBean.getFlgAmministratore()));
        		lFruitoreCasellaBean.setFlgIncludiSubordinati(lXmlFruitoriProfBean.getFlgIncludiSubordinati() != null && "1".equals(lXmlFruitoriProfBean.getFlgIncludiSubordinati()));
        		listaFruitoriCasella.add(lFruitoreCasellaBean);
        	}        	
        }
        bean.setListaFruitoriCasella(listaFruitoriCasella);
        
        List<UtenteCasellaBean> listaUtentiCasella = new ArrayList<UtenteCasellaBean>();
    	if(StringUtils.isNotBlank(output.getResultBean().getXmlutentiprofout())) {
        	List<XmlUtentiProfBean> listaXmlUtentiProf = XmlListaUtility.recuperaLista(output.getResultBean().getXmlutentiprofout(), XmlUtentiProfBean.class); 
        	for(int i = 0; i < listaXmlUtentiProf.size(); i++) {
        		XmlUtentiProfBean lXmlUtentiProfBean = listaXmlUtentiProf.get(i);
        		UtenteCasellaBean lUtenteCasellaBean = new UtenteCasellaBean();
        		lUtenteCasellaBean.setIdUser(lXmlUtentiProfBean.getIdUser());      
        		lUtenteCasellaBean.setUsername(lXmlUtentiProfBean.getUsername());
        		lUtenteCasellaBean.setCognomeNome(lXmlUtentiProfBean.getCognomeNome());
        		lUtenteCasellaBean.setFlgSmistatore(lXmlUtentiProfBean.getFlgSmistatore() != null && "1".equals(lXmlUtentiProfBean.getFlgSmistatore()));
        		lUtenteCasellaBean.setFlgMittente(lXmlUtentiProfBean.getFlgMittente() != null && "1".equals(lXmlUtentiProfBean.getFlgMittente()));
        		lUtenteCasellaBean.setFlgAmministratore(lXmlUtentiProfBean.getFlgAmministratore() != null && "1".equals(lXmlUtentiProfBean.getFlgAmministratore()));        		
        		listaUtentiCasella.add(lUtenteCasellaBean);
        	}        	
        }
    	bean.setListaUtentiCasella(listaUtentiCasella);
         
		return bean;		
	}
		
	@Override
	public CasellaEmailBean update(CasellaEmailBean bean, CasellaEmailBean oldvalue) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailSetsoggconprofilivscasellaBean input = new DmpkIntMgoEmailSetsoggconprofilivscasellaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	

		input.setIdcasellain(bean.getIdCasella());
		
		input.setIdenteaooin(StringUtils.isNotBlank(bean.getIdSpAoo()) ? new Integer(bean.getIdSpAoo()) : null);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		List<XmlFruitoriProfBean> listaXmlFruitoriProf = new ArrayList<XmlFruitoriProfBean>(); 
    	if(bean.getListaFruitoriCasella() != null) {
			for(int i = 0; i < bean.getListaFruitoriCasella().size(); i++) {
				FruitoreCasellaBean lFruitoreCasellaBean = bean.getListaFruitoriCasella().get(i);
        		XmlFruitoriProfBean lXmlFruitoriProfBean = new XmlFruitoriProfBean();        	
        		lXmlFruitoriProfBean.setTipo(lFruitoreCasellaBean.getTipo());
        		if(lXmlFruitoriProfBean.getTipo() != null) {
        			if("ENTE".equals(lXmlFruitoriProfBean.getTipo())) {
        				lXmlFruitoriProfBean.setIdSpAooUo(bean.getIdSpAoo());
            		} else if("UO".equals(lXmlFruitoriProfBean.getTipo())) {
            			lXmlFruitoriProfBean.setCodice(lFruitoreCasellaBean.getCodRapido());  
            			lXmlFruitoriProfBean.setIdFruitoreCasella(lFruitoreCasellaBean.getIdFruitoreCasella());                			
            		}
        		}
        		lXmlFruitoriProfBean.setFlgSmistatore(lFruitoreCasellaBean.getFlgSmistatore() != null && lFruitoreCasellaBean.getFlgSmistatore() ? "1" : null);
        		lXmlFruitoriProfBean.setFlgMittente(lFruitoreCasellaBean.getFlgMittente() != null && lFruitoreCasellaBean.getFlgMittente() ? "1" : null);
        		lXmlFruitoriProfBean.setFlgAmministratore(lFruitoreCasellaBean.getFlgAmministratore() != null && lFruitoreCasellaBean.getFlgAmministratore() ? "1" : null);
        		lXmlFruitoriProfBean.setFlgIncludiSubordinati(lFruitoreCasellaBean.getFlgIncludiSubordinati() != null && lFruitoreCasellaBean.getFlgIncludiSubordinati() ? "1" : null);
    			listaXmlFruitoriProf.add(lXmlFruitoriProfBean);
			}						
		}
    	input.setXmlfruitoriprofin(lXmlUtilitySerializer.bindXmlList(listaXmlFruitoriProf));
		
		List<XmlUtentiProfBean> listaXmlUtentiProf = new ArrayList<XmlUtentiProfBean>(); 
    	if(bean.getListaUtentiCasella() != null) {
			for(int i = 0; i < bean.getListaUtentiCasella().size(); i++) {
				UtenteCasellaBean lUtenteCasellaBean = bean.getListaUtentiCasella().get(i);
				XmlUtentiProfBean lXmlUtentiProfBean = new XmlUtentiProfBean();        			
				lXmlUtentiProfBean.setIdUser(lUtenteCasellaBean.getIdUser());
        		lXmlUtentiProfBean.setFlgSmistatore(lUtenteCasellaBean.getFlgSmistatore() != null && lUtenteCasellaBean.getFlgSmistatore() ? "1" : null);
				lXmlUtentiProfBean.setFlgMittente(lUtenteCasellaBean.getFlgMittente() != null && lUtenteCasellaBean.getFlgMittente() ? "1" : null);
				lXmlUtentiProfBean.setFlgAmministratore(lUtenteCasellaBean.getFlgAmministratore() != null && lUtenteCasellaBean.getFlgAmministratore() ? "1" : null);
				listaXmlUtentiProf.add(lXmlUtentiProfBean);
			}						
		}
    	input.setXmlutentiprofin(lXmlUtilitySerializer.bindXmlList(listaXmlUtentiProf));
	    
		DmpkIntMgoEmailSetsoggconprofilivscasella store = new DmpkIntMgoEmailSetsoggconprofilivscasella();
        StoreResultBean<DmpkIntMgoEmailSetsoggconprofilivscasellaBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
        return bean;		
	}
	
	@Override
	public PaginatorBean<CasellaEmailBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public CasellaEmailBean add(CasellaEmailBean bean) throws Exception {
        		
		return null;	
	}
		
	@Override
	public CasellaEmailBean remove(CasellaEmailBean bean) throws Exception {
				
		return null;
	}	
	
	public boolean isAttivaCifraturaPwd() {
		return true;//ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CIFRATURA_PWD");
	}
	
	public String getChiaveCifraturaPwd() {
		return ParametriDBUtil.getParametroDB(getSession(), "STRING#1");
	}
	
}
