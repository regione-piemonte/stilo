/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetlogudfascxguimodprotBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetlogsedutaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OperazioneEffettuataBean;
import it.eng.client.DmpkRepositoryGuiGetlogudfascxguimodprot;
import it.eng.client.DmpkSeduteOrgCollGetlogseduta;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;




import org.apache.commons.lang3.StringUtils;

@Datasource(id = "OperazioniEffettuateDataSource")
public class OperazioniEffettuateDataSource extends AbstractFetchDataSource<OperazioneEffettuataBean> {

	@Override
	public PaginatorBean<OperazioneEffettuataBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
				
		List<OperazioneEffettuataBean> listaOperazioni = new ArrayList<OperazioneEffettuataBean>();
		
		//se ho chiamato la cronologia delle operazioni da "Protocollazione", "Pratiche", "Archivio", "Atti" 
		if("ud_fascicoli".equals(getExtraparams().get("operazione"))) {
			String idUdFolder = getExtraparams().get("idUdFolder");
			String flgUdFolder = getExtraparams().get("flgUdFolder");
			
			listaOperazioni = getListaOperazioniUdFascicoli(idUdFolder, flgUdFolder);
		}
		//se ho chiamato la cronologia delle operazioni da "convocazione" o "sicussione seduta"
		else if("convocazione_discussioneSeduta".equals(getExtraparams().get("operazione"))) {
			String idSeduta = getExtraparams().get("idSeduta");
			
			listaOperazioni = getListaOperazioniConvocazioneDiscussione(idSeduta);
		}
		
		PaginatorBean<OperazioneEffettuataBean> lPaginatorBean = new PaginatorBean<OperazioneEffettuataBean>();
		lPaginatorBean.setData(listaOperazioni);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaOperazioni.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaOperazioni.size());
		
		return lPaginatorBean;		
	}	
	
	private List<OperazioneEffettuataBean> getListaOperazioniConvocazioneDiscussione(String idSeduta) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollGetlogsedutaBean input = new DmpkSeduteOrgCollGetlogsedutaBean();
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodidconnectiontokenin(token);
		input.setIdsedutain(idSeduta);
		input.setFlgincludiopfallitein(null);
		
		DmpkSeduteOrgCollGetlogseduta dmpkSeduteOrgCollGetlogseduta = new DmpkSeduteOrgCollGetlogseduta();
		StoreResultBean<DmpkSeduteOrgCollGetlogsedutaBean> output = dmpkSeduteOrgCollGetlogseduta.execute(getLocale(), loginBean, input);
			
		List<OperazioneEffettuataBean> listaOperazioni = new ArrayList<OperazioneEffettuataBean>();					
		if(output.getResultBean().getListalogout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListalogout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       		HashMap<String, String> comparators = new HashMap<String, String>();
			       		OperazioneEffettuataBean operazione = new OperazioneEffettuataBean();
			       		operazione.setIdUdFolder(idSeduta);
			       		operazione.setTsOperazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(0)) : null); //colonna 1 dell'xml
			       		String c= v.get(1); //colonna 2 dell'xml
						// Ordino per numeri rappresentati come stringhe, quindi devono essere tutte lunghe uguali
						if(c!=null)
						{
							int x = c.length();
							if(x==1)
								c= "000"+c;
							else if(x==2)
								c= "00"+c;
							else if(x==3)
								c= "0"+c;
							
						}
			       		operazione.setTsOperazioneXOrd(c != null ? c : null);
			       		operazione.setEffettuatoDa(v.get(3)); //colonna 4 dell'xml		        		
			       		operazione.setANomeDi(v.get(5)); //colonna 6 dell'xml		        		
			       		operazione.setTipo(v.get(7)); //colonna 8 dell'xml				       					       		 	        		
			       		operazione.setDettagli(v.get(8) != null && !v.get(8).equalsIgnoreCase("") && !v.get(8).equalsIgnoreCase("null") ? v.get(8) : null); //colonna 9 dell'xml
			       		listaOperazioni.add(operazione);		        		
			   		}							
			}								
		}
		
		return listaOperazioni;
	
	}

	private List<OperazioneEffettuataBean> getListaOperazioniUdFascicoli(String idUdFolder, String flgUdFolder) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkRepositoryGuiGetlogudfascxguimodprotBean input = new DmpkRepositoryGuiGetlogudfascxguimodprotBean();
	    input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgfascudin(flgUdFolder);
		input.setIdobjloggedin(new BigDecimal(idUdFolder));
		input.setFlgincludiopfallitein(null);
		
		DmpkRepositoryGuiGetlogudfascxguimodprot dmpkRepositoryGuiGetlogudfascxguimodprot = new DmpkRepositoryGuiGetlogudfascxguimodprot();
		StoreResultBean<DmpkRepositoryGuiGetlogudfascxguimodprotBean> output = dmpkRepositoryGuiGetlogudfascxguimodprot.execute(getLocale(), loginBean, input);
			
		List<OperazioneEffettuataBean> listaOperazioni = new ArrayList<OperazioneEffettuataBean>();					
		if(output.getResultBean().getListalogout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListalogout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		
			       		HashMap<String, String> comparators = new HashMap<String, String>();
			       		OperazioneEffettuataBean operazione = new OperazioneEffettuataBean();
			       		operazione.setIdUdFolder(idUdFolder);
			       		operazione.setFlgUdFolder(flgUdFolder);
			       		operazione.setTsOperazione(v.get(0) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(0)) : null); //colonna 1 dell'xml
			       		String c= v.get(1); //colonna 2 dell'xml
						// Ordino per numeri rappresentati come stringhe, quindi devono essere tutte lunghe uguali
						if(c!=null)
						{
							int x = c.length();
							if(x==1)
								c= "000"+c;
							else if(x==2)
								c= "00"+c;
							else if(x==3)
								c= "0"+c;
							
						}
			       		operazione.setTsOperazioneXOrd(c != null ? c : null);
			       		operazione.setEffettuatoDa(v.get(3)); //colonna 4 dell'xml		        		
			       		operazione.setANomeDi(v.get(5)); //colonna 6 dell'xml		        		
			       		operazione.setTipo(v.get(7)); //colonna 8 dell'xml
			       		operazione.setDettagli(v.get(8) != null && !v.get(8).equalsIgnoreCase("") && !v.get(8).equalsIgnoreCase("null") ? v.get(8) : null); //colonna 9 dell'xml				       			       					       	
			       		listaOperazioni.add(operazione);		        		
			   		}							
			}								
		}
		
		return listaOperazioni;
	}

	@Override
	public OperazioneEffettuataBean get(OperazioneEffettuataBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public OperazioneEffettuataBean remove(OperazioneEffettuataBean bean)
	throws Exception {
		
		return null;
	}
	
	@Override
	public OperazioneEffettuataBean add(OperazioneEffettuataBean bean) 
	throws Exception {
		
		return bean;
	}

	@Override
	public OperazioneEffettuataBean update(OperazioneEffettuataBean bean,
			OperazioneEffettuataBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(OperazioneEffettuataBean bean)
	throws Exception {
		
		return null;
	}

}
