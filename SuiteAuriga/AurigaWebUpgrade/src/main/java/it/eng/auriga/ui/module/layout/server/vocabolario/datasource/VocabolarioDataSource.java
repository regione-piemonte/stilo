/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioDdictionaryvalueBean;
import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioIudictionaryvalueBean;
import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioLoaddettdictionaryvalueBean;
import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioTrovadictvaluesfordictentryBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.vocabolario.datasource.bean.DictionaryEntryBean;
import it.eng.auriga.ui.module.layout.server.vocabolario.datasource.bean.VocabolarioBean;
import it.eng.client.DmpkDizionarioDdictionaryvalue;
import it.eng.client.DmpkDizionarioIudictionaryvalue;
import it.eng.client.DmpkDizionarioLoaddettdictionaryvalue;
import it.eng.client.DmpkDizionarioTrovadictvaluesfordictentry;
import it.eng.document.function.bean.Flag;
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
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

@Datasource(id="VocabolarioDataSource")
public class VocabolarioDataSource extends AbstractFetchDataSource<VocabolarioBean>{
	
	private static final Logger log = Logger.getLogger(VocabolarioDataSource.class);
	
	@Override
	public String getNomeEntita() {
		return "vocabolario";
	};

	@Override
	public PaginatorBean<VocabolarioBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		String valoreFiltro = null;
		List<DestInvioNotifica> porzioneVocabolario = null;
		
		List<VocabolarioBean> data = new ArrayList<VocabolarioBean>();
		
		String dictionaryEntry="";
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("dictionaryEntry".equals(crit.getFieldName())) {
					dictionaryEntry = getTextFilterValue(crit);								
				} else if ("porzioneVocabolario".equals(crit.getFieldName())) {
					porzioneVocabolario = getListaSceltaOrganigrammaFilterValue(crit);	
				} else if ("valoreFiltro".equals(crit.getFieldName())) {
					valoreFiltro = getTextFilterValue(crit);		
				}
			}
		}
		
		DmpkDizionarioTrovadictvaluesfordictentryBean input = new DmpkDizionarioTrovadictvaluesfordictentryBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setStrinvaluein(valoreFiltro);
		
		// UO alla/e cui porzioni di vocabolario restringere la ricerca. Lista xml conforme a schema ListaSdt.xsd dove ogni riga è una UO:
		// - colonna 2) ID_UO
		// - colonna 3) flag 1/0 che indica anche le sotto-UO		
		Lista listaRestringiADizDiUo = new Lista();
		if (porzioneVocabolario != null) {
			for (DestInvioNotifica voce : porzioneVocabolario) {
				Riga riga = new Riga();
				Colonna col1 = new Colonna();
				col1.setNro(new BigInteger("2"));
				col1.setContent(voce.getId());
				Colonna col2 = new Colonna();
				col2.setNro(new BigInteger("3"));
				String value = voce.getFlgIncludiSottoUO() != null ? voce.getFlgIncludiSottoUO().getDbValue() : "0"; 
				col2.setContent(value);
				riga.getColonna().add(col1);
				riga.getColonna().add(col2);
				listaRestringiADizDiUo.getRiga().add(riga);
			}
		}
		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(listaRestringiADizDiUo, sw);
		input.setRestringiadizdiuoin(sw.toString()); 
		
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setDictionaryentryin(dictionaryEntry);		
		
		DmpkDizionarioTrovadictvaluesfordictentry dmpkDizionarioTrovadictvaluesfordictentry = new DmpkDizionarioTrovadictvaluesfordictentry();
		StoreResultBean<DmpkDizionarioTrovadictvaluesfordictentryBean> output = dmpkDizionarioTrovadictvaluesfordictentry.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if(output.getResultBean().getListaxmlout() != null){
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if(lista != null){
				for (int i = 0; i < lista.getRiga().size(); i++){
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					VocabolarioBean bean = new VocabolarioBean();
					bean.setDictionaryEntry(dictionaryEntry);
					bean.setValore(v.get(0)); // Colonna 1 dell'xml Valore
					bean.setCodiceValore(v.get(1));  // Colonna 2 dell'xml Codice corrispondente al valore (non sempre valorizzato)
					bean.setDtInizioValidita(StringUtils.isNotBlank(v.get(2)) ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(2)) : null);  // Colonna 3 dell'xml Data di inizio validità del valore (nel formato dato dal parametro di config. FMT_STD_DATA)
					bean.setDtFineValidita(StringUtils.isNotBlank(v.get(3)) ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(3)) : null);  // Colonna 4 dell'xml Data di fine validità del valore (nel formato dato dal parametro di config. FMT_STD_DATA)
					bean.setSignificatoValore(v.get(4));  // Colonna 5 dell'xml Significato del valore
					bean.setFlgLocked(StringUtils.isNotBlank(v.get(5)) ? new Integer(v.get(5)) : null);  // Colonna 6 dell'xml (valori 1/0) Se 1 è un valore riservato di sistema e non modificabile/cancellabile da GUI
					bean.setValueGenVincolo(v.get(6));  // Colonna 7 dell'xml Valore a cui è vincolato (della dictionary entry di cui è dettaglio la DictionaryEntryIn) 
					bean.setFlgValiditaValore(StringUtils.isNotBlank(v.get(7)) ? new Integer(v.get(7)) : null);  // Colonna 8 dell'xml (valori 1/0) Se 1 è un valore attualmente valido					
					bean.setFlgAbilModifica(v.get(8)); // Colonna 9 dell'xml Abilita modifica
					bean.setFlgAbilEliminazione(v.get(9)); // Colonna 10 dell'xml Abilita cancellazione
					bean.setVocabolarioDi(v.get(10)); // Colonna 11 dell'xml Nel vocabolario di (UO di appartenenza)
					bean.setFlgVisibSottoUo(v.get(11)); // Colonna 12 dell'xml Visibile dalle sotto-UO
					bean.setFlgGestSottoUo(v.get(12)); // Colonna 13 dell'xml Gestibile dalle sotto-UO
					data.add(bean);
				}
			}
		}		
		
		PaginatorBean<VocabolarioBean> lPaginatorBean = new PaginatorBean<VocabolarioBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;	
	}
	
	@Override
	public VocabolarioBean add(VocabolarioBean bean) throws Exception {
		return addOrUpdate(bean, null);
	}

	@Override
	public VocabolarioBean update(VocabolarioBean bean,	VocabolarioBean oldvalue) throws Exception {
		return addOrUpdate(bean, oldvalue);
	}
	

	private VocabolarioBean addOrUpdate(VocabolarioBean bean, VocabolarioBean oldValue) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkDizionarioIudictionaryvalueBean input = new DmpkDizionarioIudictionaryvalueBean();		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		// Id. della UO nella cui porzione del dizionario è censito il valore 
		if (bean.getIdUoAssociata() != null) {
			input.setIduoin(new BigDecimal(bean.getIdUoAssociata()));
		}
		// Flag 1/0. Se 1 la visibilità del valore è estesa a tutte le sotto UO di IdUOIn 
		input.setFlgvisibsottouoin((bean.getFlgVisibileDaSottoUo() == null || !bean.getFlgVisibileDaSottoUo()) ? 0 : 1);
		// Flag 1/0. Se 1 la modifica/cancellazione del valore è estesa a tutte le sotto UO di IdUOIn 
		input.setFlggestsottouoin((bean.getFlgModificabileDaSottoUo() == null || !bean.getFlgModificabileDaSottoUo()) ? 0 : 1);
		// Valore di dizionario da modificare - Se non valorizzato si intende che il valore è da aggiungere		
		input.setDictionaryentryin(bean.getDictionaryEntry());
		input.setValuegenvincoloin(bean.getValueGenVincolo());
		if(bean.getValoreOld() != null && !"".equals(bean.getValoreOld())){
			input.setOldvaluein(bean.getValoreOld());
		}else{
			input.setOldvaluein(null);
		}
		input.setValuein(sanificaCKEditor(bean.getValore()));
		input.setCodvaluein(bean.getCodiceValore());
		input.setMeaningin(bean.getSignificatoValore());
		input.setDtiniziovldin(bean.getDtInizioValidita() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtInizioValidita()) : null);
		input.setDtfinevldin(bean.getDtFineValidita() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDtFineValidita()) : null);
		input.setFlglockedin(bean.getFlgLocked());
		input.setFlgignorewarningin(1);
		
		DmpkDizionarioIudictionaryvalue dmpkDizionarioIudictionaryvalue = new DmpkDizionarioIudictionaryvalue();
		StoreResultBean<DmpkDizionarioIudictionaryvalueBean> output = dmpkDizionarioIudictionaryvalue.execute(getLocale(), loginBean, input);
		
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
	public VocabolarioBean get(VocabolarioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Leggo il valore flgValoriCkeditor dalla combo "DICTIONARY_ENTRY"
		String flgValoriCkeditor = getFlgValoriCkeditor(bean.getDictionaryEntry());
		
		DmpkDizionarioLoaddettdictionaryvalueBean input = new DmpkDizionarioLoaddettdictionaryvalueBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setDictionaryentryio(bean.getDictionaryEntry());
		input.setCodvalueio(bean.getCodiceValore());
		
		if (flgValoriCkeditor==null || !"1".equalsIgnoreCase(flgValoriCkeditor)) {
			input.setValueio(bean.getValore());
		}
		
		DmpkDizionarioLoaddettdictionaryvalue dmpkDizionarioLoaddettdictionaryvalue = new DmpkDizionarioLoaddettdictionaryvalue();
		StoreResultBean<DmpkDizionarioLoaddettdictionaryvalueBean> output = dmpkDizionarioLoaddettdictionaryvalue.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				log.error("Errore nel recupero dell'output: "+output.getDefaultMessage());
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		VocabolarioBean result = new VocabolarioBean();		
		
		// Voce di dizionario cui è relativo il valore (ricerca esatta case-insensitive)
		result.setDictionaryEntry(output.getResultBean().getDictionaryentryio());		
		result.setDictionaryEntryVincolo(output.getResultBean().getDictentryvincoloout());		
		// Valore di un campo più generale al quale il valore è vincolato
		result.setValueGenVincolo(output.getResultBean().getValuegenvincoloout());				
		// Valore di dizionario di cui mostrare i dettagli (ricerca esatta case-sensitive)
		result.setValore(output.getResultBean().getValueio());
		result.setValoreCkeditor(output.getResultBean().getValueio());		
		// Codice corrispondente al valore di dizionario di cui mostrare i dettagli (ricerca esatta case-sensitive)
		result.setCodiceValore(output.getResultBean().getCodvalueio());		
		// Data di inizio validità del valore (nel formato dato dal parametro di conf. FMT_STD_DATA).
		result.setDtInizioValidita(output.getResultBean().getDtiniziovldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtiniziovldout()) : null);		
		// Data di fine validità del valore (nel formato dato dal parametro di conf. FMT_STD_DATA).
		result.setDtFineValidita(output.getResultBean().getDtfinevldout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean().getDtfinevldout()) : null);
		// Signifcato del valore
		result.setSignificatoValore(output.getResultBean().getMeaningout()); 	
		// (valori 1/0) Indicatore di valore riservato dal sistema e non modificabile/cancellabile da GUI
		result.setFlgLocked(output.getResultBean().getFlglockedout() != null ? output.getResultBean().getFlglockedout() : null);
		// (valori 1/0) Se 1 il valore è referenziato in qualche tabella, se 0 no
		result.setFlgValueReferenced(output.getResultBean().getFlgvaluereferencedout()); 
		// (valori 1/0) Se 1 il codice associato al valore è referenziato in qualche tabella, se 0 no
		result.setFlgCodValReferenced(output.getResultBean().getFlgcodvalreferencedout()); 		
		// (valori 1/0) Se 1 indica che il codice è obbligatorio
		result.setFlgCodObbligatorio(output.getResultBean().getFlgcodobbligatorioout()); 
		// Inserisco le UO di appartenza
		if (output.getResultBean().getIduoout() != null) {
			result.setIdUoAssociata(output.getResultBean().getIduoout().toString());
			result.setNumeroLivelli(output.getResultBean().getLivelliuoout());
			result.setDescrizioneUo(output.getResultBean().getLivelliuoout() + " - " + output.getResultBean().getDesuoout());
			Integer vis = output.getResultBean().getFlgvisibsottouoout();
			result.setFlgVisibileDaSottoUo(((vis == null) || (vis == 0)) ? false : true);
			Integer mod = output.getResultBean().getFlggestsottouoout();
			result.setFlgModificabileDaSottoUo(((mod == null) || (mod == 0)) ? false : true);
		}
		// Flag 1/0. Se 1 indica che la modifica del valore è abilitata
		if(output.getResultBean().getFlgabilmodificaout() != null) {
			result.setFlgAbilModifica("" + output.getResultBean().getFlgabilmodificaout().intValue());
		}
		// Flag 1/0. Se 1 indica che la cancellazione del valore è abilitata
		if(output.getResultBean().getFlgabileliminazioneout() != null) {
			result.setFlgAbilEliminazione("" + output.getResultBean().getFlgabileliminazioneout().intValue());
		}
		
		result.setFlgValoriCkeditor(flgValoriCkeditor);

				
		return result;
	} 
	
	@Override
	public VocabolarioBean remove(VocabolarioBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Leggo il valore flgValoriCkeditor dalla combo "DICTIONARY_ENTRY"
		String flgValoriCkeditor = getFlgValoriCkeditor(bean.getDictionaryEntry());

		DmpkDizionarioDdictionaryvalueBean input = new DmpkDizionarioDdictionaryvalueBean();		
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setDictionaryentryin(bean.getDictionaryEntry());
		if (flgValoriCkeditor==null || !"1".equalsIgnoreCase(flgValoriCkeditor)) {
			input.setValuein(bean.getValore());
		}
		input.setCodvaluein(bean.getCodiceValore());
		
		DmpkDizionarioDdictionaryvalue dmpkDizionarioDdictionaryvalue = new DmpkDizionarioDdictionaryvalue();
		StoreResultBean<DmpkDizionarioDdictionaryvalueBean> output = dmpkDizionarioDdictionaryvalue.execute(getLocale(), loginBean, input);
		
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
	
	protected List<DestInvioNotifica> getListaSceltaOrganigrammaFilterValue(Criterion crit) {
		
		if (crit != null && crit.getValue() != null) {
			ArrayList lista = new ArrayList<DestInvioNotifica>();
			if (crit.getValue() instanceof Map) {
				Map map = (Map) crit.getValue();
				
				//Controllo se map è vuota o meno (Nel caso in cui la lista sia vuota map genera un nullPointerException)
				if (!map.isEmpty()) {
					for (Map val : (ArrayList<Map>) map.get("listaOrganigramma")) {
						DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
						String chiave = (String) val.get("organigramma");
						destInvioNotifica.setTipo(chiave.substring(0, 2));
						destInvioNotifica.setId(chiave.substring(2));
						if (val.get("flgIncludiSottoUO") != null && ((Boolean) val.get("flgIncludiSottoUO"))) {
							destInvioNotifica.setFlgIncludiSottoUO(Flag.SETTED);
						}
						if (val.get("flgIncludiScrivanie") != null && ((Boolean) val.get("flgIncludiScrivanie"))) {
							destInvioNotifica.setFlgIncludiScrivanie(Flag.SETTED);
						}
						lista.add(destInvioNotifica);
					}
				}
			} else {
				String value = getTextFilterValue(crit);
				if (StringUtils.isNotBlank(value)) {
					StringSplitterServer st = new StringSplitterServer(value, ";");
					while (st.hasMoreTokens()) {
						DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
						String chiave = st.nextToken();
						destInvioNotifica.setTipo(chiave.substring(0, 2));
						destInvioNotifica.setId(chiave.substring(2));
						lista.add(destInvioNotifica);
					}
				}
			}
			return lista;
		}
		return null;
	}
	
	protected String getFlgValoriCkeditor(String dictionaryEntry) throws Exception  {
		String result = null;
		DictionaryEntryDataSource lDictionaryEntryDataSource = new DictionaryEntryDataSource();
		try {			
			lDictionaryEntryDataSource.setSession(getSession());			
			PaginatorBean<DictionaryEntryBean> lPaginatorBean = lDictionaryEntryDataSource.fetch(new AdvancedCriteria(), null, null, null);
			if(lPaginatorBean.getData() != null && lPaginatorBean.getData().size() > 0) {
				for(DictionaryEntryBean lDictionaryEntryBean : lPaginatorBean.getData()) {
					String key = lDictionaryEntryBean.getKey();
					if (key.equalsIgnoreCase(dictionaryEntry)){
						result = lDictionaryEntryBean.getFlgValoriCkeditor();
					}
				}
			}
		}
		catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		
		return result;
	}
	
	private String sanificaCKEditor (String str) {
		String tmp = str;
		if (StringUtils.isNotBlank(tmp)) {
			tmp = tmp.replaceAll("(\\n){2,}", "");
			if (StringUtils.isNotBlank(tmp) && tmp.endsWith("\n")) {
				tmp = tmp.substring(0, tmp.length() - 1);
			}
		}
		return tmp;
	}
}