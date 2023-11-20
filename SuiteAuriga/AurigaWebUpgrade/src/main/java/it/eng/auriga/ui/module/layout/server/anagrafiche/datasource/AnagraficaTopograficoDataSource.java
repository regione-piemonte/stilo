/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityDdatopograficoBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityIuintopograficoBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityTrovaintopograficoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.AnagraficaTopograficoBean;
import it.eng.client.DmpkUtilityDdatopografico;
import it.eng.client.DmpkUtilityIuintopografico;
import it.eng.client.DmpkUtilityTrovaintopografico;
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

@Datasource(id = "AnagraficaTopograficoDataSource")
public class AnagraficaTopograficoDataSource extends AbstractFetchDataSource<AnagraficaTopograficoBean> {

	@Override
	public String getNomeEntita() {
		return "anagrafiche_topografico";
	}
	
	
	@Override
	public AnagraficaTopograficoBean get(AnagraficaTopograficoBean bean)
			throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		// Inizializzo l'INPUT
		DmpkUtilityTrovaintopograficoBean input = new DmpkUtilityTrovaintopograficoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdtoponimoin(new BigDecimal(bean.getIdTopografico()));
				
		// Inizializzo l'OUTPUT
		DmpkUtilityTrovaintopografico lDmpkUtilityTrovaintopografico = new DmpkUtilityTrovaintopografico();
		StoreResultBean<DmpkUtilityTrovaintopograficoBean> output = lDmpkUtilityTrovaintopografico.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}		
		
		AnagraficaTopograficoBean result = null;		
		if (output.getResultBean().getNrototrecout() != null){			
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());			
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				if(lista.getRiga().size() == 1)
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(0));															
	        		result = new AnagraficaTopograficoBean();		        		
	        		// Setto i valori dell'XML nel bean 
	        		result.setIdTopografico(v.get(0)); //colonna 1  : ID TOPOGRAFICO
	        		result.setNome(v.get(1)); //colonna 2  : NOME
	        		result.setCodiceRapido(v.get(2)); //colonna 3  : CODICE RAPIDO
	        		result.setDescrizione(v.get(3)); //colonna 4  : DESCRIZIONE
	        		result.setCodiceOrigine(v.get(4)); //colonna 5  : CODICE ORIGINE
	        		result.setFlgCreatodame(v.get(5)); //colonna 6  : FLAG CREATO DA ME
	        		result.setFlgValido(v.get(6)); //colonna 7  : FLAG VALIDO
	        		result.setRecProtetto(v.get(7)); //colonna 8  : REC PROTETTO
	        		result.setNote(v.get(8)); //colonna 9  : NOTE
	        		result.setIdUtenteIns(v.get(9)); //colonna 10 : ID UTENTE INS
	        		result.setDescUtenteIns(v.get(10)); //colonna 11 : DESC UTENTE INS
	        		result.setDataIns(v.get(11) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(11)) : null); //colonna 12 : DATA INS
	        		result.setIdUtenteUltMod(v.get(12)); //colonna 13 : ID UTENTE ULT MOD
	        		result.setDescUtenteUltMod(v.get(13)); //colonna 14 : DESC UTENTE ULT MOD
	        		result.setDataUltMod(v.get(14) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(14)) : null); //colonna 15 : DATA ULT MOD
	    		}
			}					
		}		
		return result;
	}
	
	
	@Override
	public PaginatorBean<AnagraficaTopograficoBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		String nome = null;		
        String codiceRapido = null;
		String descrizione  = null;
		Integer flgSoloCreatiDaMe = null;
		Integer flgIncludiAnnullati = null;
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){		
				
				if("nome".equals(crit.getFieldName())) {
					nome = getTextFilterValue(crit);					
				}	
				else if("codiceRapido".equals(crit.getFieldName())) {
					codiceRapido = getTextFilterValue(crit);		
				} 
				
				else if("descrizione".equals(crit.getFieldName())) {
					descrizione = getTextFilterValue(crit);													
				} 
				else if("flgSoloCreatiDaMe".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {	
						flgSoloCreatiDaMe = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}
				}
				else if("flgIncludiAnnullati".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						flgIncludiAnnullati = new Boolean(String.valueOf(crit.getValue())) ? 1 : 0;
					}
				} 
			}
		}
	
		// Inizializzo l'INPUT
		DmpkUtilityTrovaintopograficoBean input = new DmpkUtilityTrovaintopograficoBean();
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
		input.setNometoponimoin(nome);
		input.setCodrapidotoponimoin(codiceRapido);
		input.setDestoponimoin(descrizione);
		input.setFlgsolotoponutentein(flgSoloCreatiDaMe);
		input.setFlgincludiannullatiin(flgIncludiAnnullati);		
		
		// Inizializzo l'OUTPUT
		DmpkUtilityTrovaintopografico lDmpkUtilityTrovaintopografico = new DmpkUtilityTrovaintopografico();
		StoreResultBean<DmpkUtilityTrovaintopograficoBean> output = lDmpkUtilityTrovaintopografico.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		List<AnagraficaTopograficoBean> data = new ArrayList<AnagraficaTopograficoBean>();
		
		if (output.getResultBean().getNrototrecout() != null){				
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());				
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																
		       		AnagraficaTopograficoBean node = new AnagraficaTopograficoBean();	        				        		
		       		// Setto i valori dell'XML nel bean 
		       		node.setIdTopografico(v.get(0)); //colonna 1  : ID TOPOGRAFICO
		       		node.setNome(v.get(1)); //colonna 2  : NOME
		       		node.setCodiceRapido(v.get(2)); //colonna 3  : CODICE RAPIDO
		       		node.setDescrizione(v.get(3)); //colonna 4  : DESCRIZIONE
		       		node.setCodiceOrigine(v.get(4)); //colonna 5  : CODICE ORIGINE
		       		node.setFlgCreatodame(v.get(5)); //colonna 6  : FLAG CREATO DA ME
		       		node.setFlgValido(v.get(6)); //colonna 7  : FLAG VALIDO
		       		node.setRecProtetto(v.get(7)); //colonna 8  : REC PROTETTO
		       		node.setNote(v.get(8)); //colonna 9  : NOTE
		       		node.setIdUtenteIns(v.get(9)); //colonna 10 : ID UTENTE INS
		       		node.setDescUtenteIns(v.get(10)); //colonna 11 : DESC UTENTE INS
		       		node.setDataIns(v.get(11) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(11)) : null); //colonna 12 : DATA INS
		       		node.setIdUtenteUltMod(v.get(12)); //colonna 13 : ID UTENTE ULT MOD
		       		node.setDescUtenteUltMod(v.get(13)); //colonna 14 : DESC UTENTE ULT MOD
		       		node.setDataUltMod(v.get(14) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(14)) : null); //colonna 15 : DATA ULT MOD				 		       		
		       		data.add(node);
		   		}
			}					
		}
		
		PaginatorBean<AnagraficaTopograficoBean> lPaginatorBean = new PaginatorBean<AnagraficaTopograficoBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}
	
	@Override
	public AnagraficaTopograficoBean add(AnagraficaTopograficoBean bean)
			throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		DmpkUtilityIuintopograficoBean input = new DmpkUtilityIuintopograficoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodrapidotoponimoin(bean.getCodiceRapido());		
		input.setCiprovtoponimoin(bean.getCodiceOrigine());
		input.setDescrizionein(bean.getDescrizione());
		input.setNometoponimoin(bean.getNome());
		input.setNotetoponimoin(bean.getNote());

		DmpkUtilityIuintopografico iutopografico = new DmpkUtilityIuintopografico();
		StoreResultBean<DmpkUtilityIuintopograficoBean> output = iutopografico.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if (output.getResultBean().getCiprovtoponimoin()==null)
			output.getResultBean().setCiprovtoponimoin("");		
		if (output.getResultBean().getCodrapidotoponimoin()==null)
			output.getResultBean().setCodrapidotoponimoin("");		
		if (output.getResultBean().getDescrizionein()==null)
			output.getResultBean().setDescrizionein("");		
		if (output.getResultBean().getNotetoponimoin()==null)
			output.getResultBean().setNotetoponimoin("");
		if (output.getResultBean().getFlglockedin()==null)
			output.getResultBean().setFlglockedin(0);
				
	    bean.setCodiceOrigine((String.valueOf(output.getResultBean().getCiprovtoponimoin())));
		bean.setCodiceRapido((String.valueOf(output.getResultBean().getCodrapidotoponimoin())));
		bean.setDescrizione((String.valueOf(output.getResultBean().getDescrizionein())));		
		bean.setFlgValido("1");
		bean.setIdTopografico((String.valueOf(output.getResultBean().getIdtoponimoio())));	
		bean.setRecProtetto((String.valueOf(output.getResultBean().getFlglockedin())));
				
		return bean;
	}
	
	@Override
	public AnagraficaTopograficoBean update(AnagraficaTopograficoBean bean,
			AnagraficaTopograficoBean oldvalue) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkUtilityIuintopograficoBean input = new DmpkUtilityIuintopograficoBean();
		input.setIdtoponimoio(new BigDecimal(bean.getIdTopografico()));
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setCodrapidotoponimoin(bean.getCodiceRapido());		
		input.setCiprovtoponimoin(bean.getCodiceOrigine());
		input.setDescrizionein(bean.getDescrizione());
		input.setNometoponimoin(bean.getNome());
		input.setNotetoponimoin(bean.getNote());
				
		DmpkUtilityIuintopografico iutopografico = new DmpkUtilityIuintopografico();
		StoreResultBean<DmpkUtilityIuintopograficoBean> output = iutopografico.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
				
		if (output.getResultBean().getCiprovtoponimoin()==null)
			output.getResultBean().setCiprovtoponimoin("");		
		if (output.getResultBean().getCodrapidotoponimoin()==null)
			output.getResultBean().setCodrapidotoponimoin("");		
		if (output.getResultBean().getDescrizionein()==null)
			output.getResultBean().setDescrizionein("");		
		if (output.getResultBean().getNotetoponimoin()==null)
			output.getResultBean().setNotetoponimoin("");
		
	    bean.setCodiceOrigine((String.valueOf(output.getResultBean().getCiprovtoponimoin())));
		bean.setCodiceRapido((String.valueOf(output.getResultBean().getCodrapidotoponimoin())));
		bean.setDescrizione((String.valueOf(output.getResultBean().getDescrizionein())));		
		bean.setIdTopografico((String.valueOf(output.getResultBean().getIdtoponimoio())));	
		bean.setNote((String.valueOf(output.getResultBean().getNotetoponimoin())));
		
		if (output.getResultBean().getFlglockedin()==null)
			output.getResultBean().setFlglockedin(0);
				
		bean.setRecProtetto((String.valueOf(output.getResultBean().getFlglockedin())));

		return bean;
	}	
	
	@Override
	public AnagraficaTopograficoBean remove(AnagraficaTopograficoBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkUtilityDdatopograficoBean input = new DmpkUtilityDdatopograficoBean();			
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIdtoponimoin(new BigDecimal(bean.getIdTopografico()));
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
				
		DmpkUtilityDdatopografico dtopografico = new DmpkUtilityDdatopografico();
		StoreResultBean<DmpkUtilityDdatopograficoBean> output = dtopografico.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}	

}
