/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGrantprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityRevokeprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovafunzioniBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioTreeNodeBean;
import it.eng.auriga.ui.module.layout.server.listaFunzionalita.bean.ListaAbilFunzioniProfiloBean;
import it.eng.auriga.ui.module.layout.server.listaFunzionalita.bean.ListaFunzionalitaBean;
import it.eng.client.DmpkDefSecurityGrantprivsudefcontesto;
import it.eng.client.DmpkDefSecurityRevokeprivsudefcontesto;
import it.eng.client.DmpkDefSecurityTrovafunzioni;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.Navigator;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;



@Datasource(id="AurigaListaFunzionalitaDataSource")
public class AurigaListaFunzionalitaDataSource extends AbstractFetchDataSource<ListaFunzionalitaBean>{

	@Override
	public String getNomeEntita() {
		return "lista_funzionalita";
	};

	@Override
	public PaginatorBean<ListaFunzionalitaBean> fetch(
			AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;

		String strFlgStatoAbil = getExtraparams().get("flgStatoAbil");
		Integer flgStatoAbil = StringUtils.isNotBlank(strFlgStatoAbil) ? Integer.parseInt(strFlgStatoAbil) : null;
		
		String flgTpDestAbil = getExtraparams().get("flgTpDestAbil");

		String strIdDestAbil = getExtraparams().get("idDestAbil");
		BigDecimal idDestAbil = StringUtils.isNotBlank(strIdDestAbil) ? new BigDecimal(strIdDestAbil) : null;

		String codFunzP1 = null;
		String codFunzP2 = null;
		String codFunzP3 = null;
		String descrizione = null;
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("codFunzP1".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						codFunzP1 = getTextFilterValue(crit);
					}
				} else if("codFunzP2".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						codFunzP2 = getTextFilterValue(crit);
					}
				} else if("codFunzP3".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						codFunzP3 = getTextFilterValue(crit);
					}
				} else if("descrizione".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						descrizione = getTextFilterValue(crit);
					}
				} 
			}
		}		
		
		List<ListaFunzionalitaBean> data = new ArrayList<ListaFunzionalitaBean>();

		DmpkDefSecurityTrovafunzioniBean input = new DmpkDefSecurityTrovafunzioniBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);

		input.setFlgstatoabilin(flgStatoAbil != null ? flgStatoAbil : null);
		input.setFlgtpdestabilin(flgTpDestAbil != null ? flgTpDestAbil : null);
		input.setIddestabilin(idDestAbil);
		
		input.setDesfunzio(descrizione);
		input.setCodfunzp1io(codFunzP1);
		input.setCodfunzp2io(codFunzP2);
		input.setCodfunzp3io(codFunzP3);

		DmpkDefSecurityTrovafunzioni dmpkDefSecurityTrovafunzioni = new DmpkDefSecurityTrovafunzioni();
		StoreResultBean<DmpkDefSecurityTrovafunzioniBean> output = dmpkDefSecurityTrovafunzioni.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if(output.getResultBean().getListaxmlout() != null)
		{
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null)
			{
				for (int i = 0; i < lista.getRiga().size(); i++) 
				{
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
					ListaFunzionalitaBean bean = new ListaFunzionalitaBean();	  		
					bean.setCodice(v.get(0));
					bean.setCodFunzP1(v.get(1));
					bean.setCodFunzP2(v.get(2));
					bean.setCodFunzP3(v.get(3));
					bean.setDescrizione(v.get(4) );
					bean.setFlgSoloDisp(StringUtils.isNotBlank(v.get(6)) ? Integer.parseInt(v.get(6)) : null);
					data.add(bean);
				}
			}
		}

		PaginatorBean<ListaFunzionalitaBean> lPaginatorBean = new PaginatorBean<ListaFunzionalitaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;	

	}
	
	public ListaAbilFunzioniProfiloBean aggiungiAbilFunzioniProfilo(ListaAbilFunzioniProfiloBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityGrantprivsudefcontestoBean input = new DmpkDefSecurityGrantprivsudefcontestoBean();	
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		//input.setFlgtpobjprivtoin("PR");
		input.setFlgtpobjprivtoin(bean.getFlgTpObjPrivTo());
		input.setIdobjprivtoin(new BigDecimal(bean.getIdProfilo()));
		input.setFlgtpobjtograntin("F");
		input.setListaprivilegiin("FC");
		
		Lista lista = new Lista();
		for(ListaFunzionalitaBean funzione : bean.getListaFunzioni()) {			
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));			
			col1.setContent(funzione.getCodice());			
			riga.getColonna().add(col1);
			lista.getRiga().add(riga);
		}			
		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);				
		input.setListaobjtograntxmlin(sw.toString());
		
		DmpkDefSecurityGrantprivsudefcontesto grantPrivSuDefContesto = new DmpkDefSecurityGrantprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityGrantprivsudefcontestoBean> output = grantPrivSuDefContesto.execute(getLocale(), loginBean, input);

		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	public ListaAbilFunzioniProfiloBean rimuoviAbilFunzioniProfilo(ListaAbilFunzioniProfiloBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityRevokeprivsudefcontestoBean input = new DmpkDefSecurityRevokeprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		//input.setFlgtpobjprivtoin("PR");
		input.setFlgtpobjprivtoin(bean.getFlgTpObjPrivTo());
		input.setIdobjprivtoin(new BigDecimal(bean.getIdProfilo()));
		input.setListaprivilegiin(null);
		
		Lista lista = new Lista();
		for(ListaFunzionalitaBean funzione : bean.getListaFunzioni()) {			
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent("F");		
			riga.getColonna().add(col1);
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getCodice());			
			riga.getColonna().add(col2);
			lista.getRiga().add(riga);
		}		
		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);		
		input.setListaobjtorevokexmlin(sw.toString());
		
		DmpkDefSecurityRevokeprivsudefcontesto revokePrivSuDefContesto = new DmpkDefSecurityRevokeprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityRevokeprivsudefcontestoBean> output = revokePrivSuDefContesto.execute(getLocale(), loginBean, input);
		
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}		
		
		return bean;		
	}
		
}
