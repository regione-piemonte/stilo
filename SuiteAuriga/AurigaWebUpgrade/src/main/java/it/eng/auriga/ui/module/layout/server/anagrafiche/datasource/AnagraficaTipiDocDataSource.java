/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesLoaddettdoctypeBean;
import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesTrovadoctypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaTipiDocBean;
import it.eng.client.DmpkDocTypesLoaddettdoctype;
import it.eng.client.DmpkDocTypesTrovadoctypes;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AnagraficaTipiDocDataSource")
public class AnagraficaTipiDocDataSource extends AbstractFetchDataSource<AnagraficaTipiDocBean> {

	@Override
	public String getNomeEntita() {
		return "anagrafiche_societa";
	}

	@Override
	public AnagraficaTipiDocBean get(AnagraficaTipiDocBean bean) throws Exception {
		

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		
		
		DmpkDocTypesLoaddettdoctypeBean  input = new DmpkDocTypesLoaddettdoctypeBean ();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	

		input.setIddoctypeio(new BigDecimal(bean.getIdTipoDoc()));
		

		DmpkDocTypesLoaddettdoctype loaddett = new DmpkDocTypesLoaddettdoctype();
		StoreResultBean<DmpkDocTypesLoaddettdoctypeBean> output = loaddett.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		AnagraficaTipiDocBean res = new AnagraficaTipiDocBean();
		res.setIdTipoDoc(output.getResultBean().getIddoctypeio().toString());
		res.setDescrizioneTipoDoc(output.getResultBean().getDescrizioneout());
		res.setNomeTipoDoc(output.getResultBean().getNomeout());
		res.setCodTipoDoc(output.getResultBean().getCiprovdoctypeout());
		
		return res;
	}

	@Override
	public PaginatorBean<AnagraficaTipiDocBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1); // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;

		Integer flgDiSistema = new Integer(1); // 1 : di sistema
		String codTipoDoc    = null;
		String nomeTipoDoc   = null;
		
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				
				if("codTipoDoc".equals(crit.getFieldName())) {
					codTipoDoc = getTextFilterValue(crit);
				} 
				else if("nomeTipoDoc".equals(crit.getFieldName())) {
					nomeTipoDoc = getTextFilterValue(crit);
				} 
			}			
		}

		// Inizializzo l'INPUT
		DmpkDocTypesTrovadoctypesBean input = new DmpkDocTypesTrovadoctypesBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);              
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		
		
		// Setto i filtri all'input del servizio
		
		if(codTipoDoc != null) {
			input.setCiprovdoctypeio(codTipoDoc);
		}
		
		if(nomeTipoDoc != null) {
			input.setNomeio(nomeTipoDoc);
		}

		
		// Inizializzo l'OUTPUT
		DmpkDocTypesTrovadoctypes lDmpkDocTypesTrovadoctypes = new DmpkDocTypesTrovadoctypes();
		StoreResultBean<DmpkDocTypesTrovadoctypesBean> output = lDmpkDocTypesTrovadoctypes.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		
		List<AnagraficaTipiDocBean> data = new ArrayList<AnagraficaTipiDocBean>();
		
		if (output.getResultBean().getNrototrecout() != null){				
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());				
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
					AnagraficaTipiDocBean bean = new AnagraficaTipiDocBean();	   
					
					// Setto i valori dell'XML nel bean 
					bean.setIdTipoDoc(v.get(0)); 																			//col. 1 : Identificativo del tipo					
					bean.setNomeTipoDoc(v.get(1)); 																		    //col. 2 : nome del tipo
					bean.setDescrizioneTipoDoc(v.get(2)); 																	//col. 3 : descrizione del tipo
					
					
					bean.setCodTipoDoc(v.get(22));																	        //col. 23: Cod. identificativo del tipo di documento nel sistema di provenienza
					bean.setFlgAnn(v.get(20) != null ? new Integer(v.get(20)) : null); 							            //col. 21: Flag di annullamento logico (valori 1/0)
					bean.setTsIns(v.get(23) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(23)) : null); 	//col. 24: Timestamp di creazione del soggetto (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)					
					bean.setUteIns(v.get(24)); 																				//col. 25: Descrizione dell'utente di creazione del soggetto					
					bean.setTsLastUpd(v.get(25) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(25)) : null); //col. 26: Timestamp di ultima modifica dei dati identificativi del soggetto (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP) 								
					bean.setUteLastUpd(v.get(26)); 																			//col. 27: Descrizione dell'utente di ultima modifica dei dati identificativi del soggetto					
					
					if( (output.getResultBean().getFlgabilinsout()!=null && output.getResultBean().getFlgabilinsout().equals("1") ) ||
						(output.getResultBean().getFlgabilupdout()!=null && output.getResultBean().getFlgabilupdout().equals("1") ) ||
						(output.getResultBean().getFlgabildelout()!=null && output.getResultBean().getFlgabildelout().equals("1") )
						){
						flgDiSistema = new Integer("0");
					}
					else{
						flgDiSistema = new Integer("1");
					}
					bean.setFlgDiSistema(flgDiSistema);																		//(valori 1/0) Indicatore di non modificabile da GUI
					data.add(bean);
		   		}
			}					
		}
		PaginatorBean<AnagraficaTipiDocBean> lPaginatorBean = new PaginatorBean<AnagraficaTipiDocBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;		

	}

	
	
	@Override
	public AnagraficaTipiDocBean add(AnagraficaTipiDocBean bean) throws Exception {
				
		return bean;
	}

	
	@Override
	public AnagraficaTipiDocBean update(AnagraficaTipiDocBean bean, AnagraficaTipiDocBean oldvalue) throws Exception {
		
		return bean;
	}
	
	@Override
	public AnagraficaTipiDocBean remove(AnagraficaTipiDocBean bean) throws Exception {
		
		return bean;
	}
}
