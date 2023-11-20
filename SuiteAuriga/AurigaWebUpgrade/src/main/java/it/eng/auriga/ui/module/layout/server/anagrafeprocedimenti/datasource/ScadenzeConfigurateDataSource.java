/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesIuscadenzaproctypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesLoaddettscadenzaproctypeBean;
import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovascadenzeproctypeBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafeprocedimenti.datasource.bean.ScadenzeConfigurateBean;
import it.eng.client.DmpkProcessTypesIuscadenzaproctype;
import it.eng.client.DmpkProcessTypesLoaddettscadenzaproctype;
import it.eng.client.DmpkProcessTypesTrovascadenzeproctype;
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
import org.apache.log4j.Logger;

/**
 * 
 * @author Alberto Calvelli
 *
 */

@Datasource(id="ScadenzeConfigurateDataSource")
public class ScadenzeConfigurateDataSource extends AbstractFetchDataSource<ScadenzeConfigurateBean> {

	private static final Logger log = Logger.getLogger(AnagrafeProcedimentiDataSource.class);

	@Override
	public String getNomeEntita() {
		return "scadenze_configurate";
	};
	
	@Override
	public PaginatorBean<ScadenzeConfigurateBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,List<OrderByBean> orderby) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		BigDecimal idProcessType = null;	
		List<ScadenzeConfigurateBean> data = new ArrayList<ScadenzeConfigurateBean>();

		DmpkProcessTypesTrovascadenzeproctypeBean input = new DmpkProcessTypesTrovascadenzeproctypeBean();
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){				
				if("descrizione".equals(crit.getFieldName())) {
					input.setDesscadenzain(getTextFilterValue(crit));
				} 
				if("idProcessTypeIO".equals(crit.getFieldName())) {
					idProcessType = new BigDecimal(String.valueOf(crit.getValue()));
					input.setIdprocesstypeio(idProcessType);
				} 
			}
		}		

		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
				
		DmpkProcessTypesTrovascadenzeproctype dmpkProcessTypesTrovascadenzeproctype = new DmpkProcessTypesTrovascadenzeproctype();
		StoreResultBean<DmpkProcessTypesTrovascadenzeproctypeBean> output = dmpkProcessTypesTrovascadenzeproctype.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
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
					ScadenzeConfigurateBean bean = new ScadenzeConfigurateBean();
					
					bean.setRowId(v.get(0));//rowID
					bean.setTipo(v.get(1));// tipo: colonna 2 dell'xml ListaXMLOut restituito dalla stored 
					bean.setDescrizioneScadenze(v.get(2)); // Descrizione: colonna 3 dell’xml ListaXMLOut restituito dalla stored
					bean.setDurataPeriodo(v.get(3) != null ? new BigDecimal(v.get(3)) : null);//Durata periodo: colonna 4 dell’xml ListaXMLOut restituito dalla stored
					bean.setValidoDal(StringUtils.isNotBlank(v.get(4)) ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(4)) : null);//Valido Dal: colonna 5 dell’xml ListaXMLOut restituito dalla stored
					bean.setValidoFinoAl(StringUtils.isNotBlank(v.get(5)) ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(5)) : null);//Valido Dal: colonna 6 dell’xml ListaXMLOut restituito dalla stored
					data.add(bean);
				}
			}
		}

		PaginatorBean<ScadenzeConfigurateBean> lPaginatorBean = new PaginatorBean<ScadenzeConfigurateBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;	
	
	}
	@Override
	public ScadenzeConfigurateBean add(ScadenzeConfigurateBean bean) throws Exception 
	{
		return addOrUpdate(bean, null,false);
	}

	@Override
	public ScadenzeConfigurateBean update(ScadenzeConfigurateBean bean,	ScadenzeConfigurateBean oldvalue) throws Exception 
	{
		return addOrUpdate(bean, oldvalue,true);
	}
	
	/**
	 * 
	 * @param bean
	 * @param oldvalue
	 * @return
	 * @throws Exception
	 */
	private ScadenzeConfigurateBean addOrUpdate(ScadenzeConfigurateBean bean,	ScadenzeConfigurateBean oldvalue, boolean update) throws Exception
	{
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkProcessTypesIuscadenzaproctypeBean input = new DmpkProcessTypesIuscadenzaproctypeBean();
		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		if(update)
			input.setCiscadenzaio(bean.getRowId());
		input.setIdprocesstypeio(bean.getIdProcessType());//id della maschera prima
		input.setDescrizionein(bean.getDescrizioneScadenze());
		input.setFlgscadpuntualeperiodoin(bean.getTipo());
		input.setDtiniziovldin(bean.getValidoDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getValidoDal()) : null);
		input.setDtfinevldin(bean.getValidoFinoAl()!=null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getValidoFinoAl()) : null);
		input.setDuratain(bean.getDurataPeriodo());
		
		DmpkProcessTypesIuscadenzaproctype dmpkProcessTypesIuscadenzaproctype = new DmpkProcessTypesIuscadenzaproctype();
		StoreResultBean<DmpkProcessTypesIuscadenzaproctypeBean> output = dmpkProcessTypesIuscadenzaproctype.execute(getLocale(), loginBean, input);
		
		if(!update)
		{ 
			bean.setRowId(output.getResultBean().getCiscadenzaio());
		}
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}	

	@Override
	public ScadenzeConfigurateBean get(ScadenzeConfigurateBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkProcessTypesLoaddettscadenzaproctypeBean input = new DmpkProcessTypesLoaddettscadenzaproctypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdprocesstypeio(bean.getIdProcessType());
		input.setRowidio(bean.getRowId());
		DmpkProcessTypesLoaddettscadenzaproctype loadDettAttr = new DmpkProcessTypesLoaddettscadenzaproctype();
		StoreResultBean<DmpkProcessTypesLoaddettscadenzaproctypeBean> output = loadDettAttr.execute(getLocale(), loginBean, input);

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		ScadenzeConfigurateBean result = new ScadenzeConfigurateBean();
		result.setIdProcessType(output.getResultBean().getIdprocesstypeio());
		result.setRowId(output.getResultBean().getRowidio()); 
		result.setDescrizioneScadenze(output.getResultBean().getDescrizioneio());
		result.setTipo(output.getResultBean().getFlgscadpuntualeperiodoout());
		result.setValidoFinoAl(output.getResultBean().getDtfinevldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtfinevldout()) : null);
		result.setValidoDal(output.getResultBean().getDtiniziovldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtiniziovldout()) : null);
		result.setDurataPeriodo(output.getResultBean().getDurataout());
		return result;
	}
}