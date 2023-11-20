/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGrantprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityRevokeprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_folder_types.bean.DmpkFolderTypesDfoldertypeBean;
import it.eng.auriga.database.store.dmpk_folder_types.bean.DmpkFolderTypesIufoldertypeBean;
import it.eng.auriga.database.store.dmpk_folder_types.bean.DmpkFolderTypesLoaddettfoldertypeBean;
import it.eng.auriga.database.store.dmpk_folder_types.bean.DmpkFolderTypesTrovafoldertypesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.AttrAddXEvtDelTipoBean;
import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.XMLAttrAddXEvtDelTipoBean;
import it.eng.auriga.ui.module.layout.server.tipoFascicoliAggr.datasource.bean.ListaTipoFascicoliBean;
import it.eng.auriga.ui.module.layout.server.tipoFascicoliAggr.datasource.bean.TipoFascicoliAggregatiBean;
import it.eng.client.DmpkDefSecurityGrantprivsudefcontesto;
import it.eng.client.DmpkDefSecurityRevokeprivsudefcontesto;
import it.eng.client.DmpkFolderTypesDfoldertype;
import it.eng.client.DmpkFolderTypesIufoldertype;
import it.eng.client.DmpkFolderTypesLoaddettfoldertype;
import it.eng.client.DmpkFolderTypesTrovafoldertypes;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
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
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author cristiano
 *
 */

@Datasource(id = "TipoFascicoliAggregatiDataSource")
public class TipoFascicoliAggregatiDataSource extends AbstractFetchDataSource<TipoFascicoliAggregatiBean> {

	@Override
	public String getNomeEntita() {
		return "tipofascicoliaggr";
	}

	@Override
	public PaginatorBean<TipoFascicoliAggregatiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		List<TipoFascicoliAggregatiBean> data = new ArrayList<TipoFascicoliAggregatiBean>();

		DmpkFolderTypesTrovafoldertypesBean input = new DmpkFolderTypesTrovafoldertypesBean();
		input.setCodidconnectiontokenin(token);
		input.setFlgsenzapaginazionein(1);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		if (getExtraparams().get("viewTipologiaDaUO") != null) {
			String idUO = getExtraparams().get("idUo");
			String flgTipDestIn = getExtraparams().get("flgTipologiaDestAbilIn");
			String flgStatoIn = getExtraparams().get("flgStatoAbilitazioneIn");

			input.setIddestabilin(idUO != null && idUO.length() > 0 ? new BigDecimal(idUO) : null);
			input.setFlgtpdestabilin(flgTipDestIn);
			input.setFlgstatoabilin(flgStatoIn != null && flgStatoIn.length() > 0 ? Integer.decode(flgStatoIn) : null);

		}

		input.setFlginclannullatiio(new BigDecimal(1));
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nome".equals(crit.getFieldName())) {
					input.setNomeio(getTextFilterValue(crit));
				} else if ("flgInclAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						input.setFlginclannullatiio(new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(0) : new BigDecimal(1));
					}
				}
			}
		}

		DmpkFolderTypesTrovafoldertypes dmpkFolderTypesTrovafoldertypes = new DmpkFolderTypesTrovafoldertypes();
		StoreResultBean<DmpkFolderTypesTrovafoldertypesBean> output = dmpkFolderTypesTrovafoldertypes.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		if (output.getResultBean().getListaxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					TipoFascicoliAggregatiBean bean = new TipoFascicoliAggregatiBean();
					bean.setIdFolderType(v.get(0));
					bean.setNome(v.get(1));
					bean.setNomeFolderTypeGen(v.get(2));
					bean.setIdFolderTypeGen(v.get(3));
					bean.setPeriodoConserv(v.get(6));
					bean.setDescrizione(v.get(7));
					bean.setAnnotazioni(v.get(9));
					bean.setDescApplFolderTypes(v.get(14));
					bean.setTsUltimoAgg(v.get(17) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(17)) : null);
					bean.setUtenteUltAgg(v.get(18));
					bean.setFlgSistema(v.get(19).equalsIgnoreCase("1") ? true : false);
					bean.setFlgRichAbilXVisualizz(v.get(20).equalsIgnoreCase("1") ? true : false);
					bean.setFlgRichAbilXGest(v.get(21).equalsIgnoreCase("1") ? true : false);
					bean.setFlgRichAbilXAssegn(v.get(22).equalsIgnoreCase("1") ? true : false);
					data.add(bean);
				}
			}
		}
		
		PaginatorBean<TipoFascicoliAggregatiBean> lPaginatorBean = new PaginatorBean<TipoFascicoliAggregatiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public TipoFascicoliAggregatiBean add(TipoFascicoliAggregatiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkFolderTypesIufoldertypeBean input = new DmpkFolderTypesIufoldertypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomein(bean.getNome());
		input.setNomefoldertypegenin(bean.getNomeFolderTypeGen());
		input.setIdprocesstypein(bean.getIdProcessType() != null && !"".equalsIgnoreCase(bean.getIdProcessType()) ? new BigDecimal(bean.getIdProcessType()) : null);
		input.setNomeprocesstypein(bean.getNomeProcessType());
		if (bean.getFlgConservPerm() != null && bean.getFlgConservPerm()) {
			input.setFlgconservpermin(new Integer(1));
			input.setPeriodoconservin(null);
		} else {
			input.setFlgconservpermin(new Integer(0));
			input.setPeriodoconservin(bean.getPeriodoConservInAnni() != null ? new BigDecimal(bean.getPeriodoConservInAnni()) : null);
		}
		input.setAnnotazioniin(bean.getAnnotazioni());
		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilXVisualizz() != null && bean.getFlgRichAbilXVisualizz() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxgestin(bean.getFlgRichAbilXGest() != null && bean.getFlgRichAbilXGest() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxassegnin(bean.getFlgRichAbilXAssegn() != null && bean.getFlgRichAbilXAssegn() ? new Integer(1) : new Integer(0));

		String xmlAttrAdd = null;
		if (bean.getListaAttributiAddizionali() != null && bean.getListaAttributiAddizionali().size() > 0) {
			xmlAttrAdd = getXMLAttrAddXEvtDelTipoInOutBean(bean);
		}
		input.setXmlattraddxfolderdeltipoin(xmlAttrAdd);
		input.setFlgmodattraddxfolderdeltipoin("C");
		input.setTemplatecodein(bean.getTemplateCode());
		input.setTemplatenomein(bean.getTemplateNome());
		input.setTemplatetimbroin(bean.getTemplateTimbro());

		input.setLivelliclassificazionein(bean.getLivelliClassificazione());
		input.setIdclassificazionein(bean.getIdClassificazione() != null ? new BigDecimal(bean.getIdClassificazione()) : null);
		input.setDesclassificazionein(bean.getDesClassificazione());

		DmpkFolderTypesIufoldertype dmpkFolderTypesIufoldertype = new DmpkFolderTypesIufoldertype();
		StoreResultBean<DmpkFolderTypesIufoldertypeBean> result = dmpkFolderTypesIufoldertype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			bean.setIdFolderType(result.getResultBean().getIdfoldertypeio().toString());
		}
		
		return bean;
	}

	@Override
	public TipoFascicoliAggregatiBean update(TipoFascicoliAggregatiBean bean, TipoFascicoliAggregatiBean oldvalue) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkFolderTypesIufoldertypeBean input = new DmpkFolderTypesIufoldertypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdfoldertypeio(bean.getIdFolderType() != null ? new BigDecimal(bean.getIdFolderType()) : null);
		input.setNomein(bean.getNome());
		input.setNomefoldertypegenin(bean.getNomeFolderTypeGen());
		input.setIdprocesstypein(bean.getIdProcessType() != null && !"".equalsIgnoreCase(bean.getIdProcessType()) ? new BigDecimal(bean.getIdProcessType()) : null);
		input.setNomeprocesstypein(bean.getNomeProcessType());
		if (bean.getFlgConservPerm() != null && bean.getFlgConservPerm()) {
			input.setFlgconservpermin(new Integer(1));
			input.setPeriodoconservin(null);
		} else {
			input.setFlgconservpermin(new Integer(0));
			input.setPeriodoconservin(bean.getPeriodoConservInAnni() != null ? new BigDecimal(bean.getPeriodoConservInAnni()) : null);
		}
		input.setAnnotazioniin(bean.getAnnotazioni());
		input.setFlgrichabilxvisualizzin(bean.getFlgRichAbilXVisualizz() != null && bean.getFlgRichAbilXVisualizz() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxgestin(bean.getFlgRichAbilXGest() != null && bean.getFlgRichAbilXGest() ? new Integer(1) : new Integer(0));
		input.setFlgrichabilxassegnin(bean.getFlgRichAbilXAssegn() != null && bean.getFlgRichAbilXAssegn() ? new Integer(1) : new Integer(0));

		String xmlAttrAdd = null;
		if (bean.getListaAttributiAddizionali() != null && bean.getListaAttributiAddizionali().size() > 0) {
			xmlAttrAdd = getXMLAttrAddXEvtDelTipoInOutBean(bean);
		}
		input.setXmlattraddxfolderdeltipoin(xmlAttrAdd);
		input.setFlgmodattraddxfolderdeltipoin("C");
		input.setTemplatecodein(bean.getTemplateCode());
		input.setTemplatenomein(bean.getTemplateNome());
		input.setTemplatetimbroin(bean.getTemplateTimbro());

		input.setLivelliclassificazionein(bean.getLivelliClassificazione());
		input.setIdclassificazionein(bean.getIdClassificazione() != null ? new BigDecimal(bean.getIdClassificazione()) : null);
		input.setDesclassificazionein(bean.getDesClassificazione());

		// Attributi dinamici
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciXmlBean);
		input.setAttributiaddin(xmlAttributiDinamici);
				
				
		DmpkFolderTypesIufoldertype dmpkFolderTypesIufoldertype = new DmpkFolderTypesIufoldertype();
		StoreResultBean<DmpkFolderTypesIufoldertypeBean> result = dmpkFolderTypesIufoldertype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getResultBean().getWarningmsgout())) {
			addMessage(result.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}

	@Override
	public TipoFascicoliAggregatiBean get(TipoFascicoliAggregatiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkFolderTypesLoaddettfoldertypeBean input = new DmpkFolderTypesLoaddettfoldertypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdfoldertypeio(bean.getIdFolderType() != null && !"".equals(bean.getIdFolderType()) ? new BigDecimal(bean.getIdFolderType()) : null);

		DmpkFolderTypesLoaddettfoldertype dmpkFolderTypesLoaddettfoldertype = new DmpkFolderTypesLoaddettfoldertype();
		StoreResultBean<DmpkFolderTypesLoaddettfoldertypeBean> output = dmpkFolderTypesLoaddettfoldertype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		TipoFascicoliAggregatiBean result = new TipoFascicoliAggregatiBean();
		result.setIdFolderType(output.getResultBean().getIdfoldertypeio().toString());
		result.setNome(output.getResultBean().getNomeout());
		result.setIdFolderTypeGen(output.getResultBean().getIdfoldertypegenout() != null ? output.getResultBean().getIdfoldertypegenout().toString() : null);
		result.setNomeFolderTypeGen(output.getResultBean().getNomefoldertypegenout());
		result.setIdProcessType(output.getResultBean().getIdprocesstypeout() != null ? output.getResultBean().getIdprocesstypeout().toString() : null);
		result.setNomeProcessType(output.getResultBean().getNomeprocesstypeout());
		result.setFlgConservPerm(output.getResultBean().getFlgconservpermout() != null && output.getResultBean().getFlgconservpermout() == 1 ? true : false);
		result.setPeriodoConservInAnni(output.getResultBean().getPeriodoconservout() != null && output.getResultBean().getPeriodoconservout().intValue() != 0 ? output.getResultBean().getPeriodoconservout().intValue() : null);
		result.setDescrizione(output.getResultBean().getDesclassificazioneout());
		result.setAnnotazioni(output.getResultBean().getAnnotazioniout());
		result.setFlgRichAbilXVisualizz(output.getResultBean().getFlgrichabilxvisualizzout() != null && output.getResultBean().getFlgrichabilxvisualizzout() == 1 ? true : false);
		result.setFlgRichAbilXGest(output.getResultBean().getFlgrichabilxgestout() != null && output.getResultBean().getFlgrichabilxgestout() == 1 ? true : false);
		result.setFlgRichAbilXAssegn(output.getResultBean().getFlgrichabilxassegnout() != null && output.getResultBean().getFlgrichabilxassegnout() == 1 ? true : false);

		result.setRowid(output.getResultBean().getRowidout());
		
		List<AttrAddXEvtDelTipoBean> listaAttrAddXml = new ArrayList<AttrAddXEvtDelTipoBean>();
		if (output.getResultBean().getXmlattraddxfolderdeltipoout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getXmlattraddxfolderdeltipoout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					AttrAddXEvtDelTipoBean attrAdd = new AttrAddXEvtDelTipoBean();
					attrAdd.setNome(v.get(1)); // colonna 2 non editabile
					attrAdd.setEtichetta(v.get(2));// colonna 3 non editabile
					attrAdd.setCheckObbligatorio(v.get(3) != null && "1".equals(v.get(3)));// colonna 4 xml editabile
					attrAdd.setMaxNumValori(v.get(4) != null ? v.get(4) : null);// colonna 5 (visibile solo se “Ripetibile” è spuntato) editabile
					if ("1".equals(attrAdd.getMaxNumValori())) {
						attrAdd.setCheckRipetibile(false);
					} else {
						attrAdd.setCheckRipetibile(true);
					}
					if (v.get(9) != null && "1".equals(v.get(9))) {
						attrAdd.setFlgTabPrincipale(true);
					} else {
						attrAdd.setCodice(v.get(10));
						attrAdd.setLabel(v.get(11));
					}
					listaAttrAddXml.add(attrAdd);
				}
			}
		}
		result.setListaAttributiAddizionali(listaAttrAddXml);

		result.setTemplateCode(output.getResultBean().getTemplatecodeout());
		result.setTemplateNome(output.getResultBean().getTemplatenomeout());
		result.setTemplateTimbro(output.getResultBean().getTemplatetimbroout());

		result.setIdClassificazione(output.getResultBean().getIdclassificazioneout() != null ? "" + output.getResultBean().getIdclassificazioneout().longValue() : null);
		result.setLivelliClassificazione(output.getResultBean().getLivelliclassificazioneout());
		result.setDesClassificazione(output.getResultBean().getDesclassificazioneout());

		return result;
	}

	@Override
	public TipoFascicoliAggregatiBean remove(TipoFascicoliAggregatiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkFolderTypesDfoldertypeBean input = new DmpkFolderTypesDfoldertypeBean();
		input.setCodidconnectiontokenin(token);
		input.setIdfoldertypein(bean.getIdFolderType() != null && !"".equals(bean.getIdFolderType()) ? new BigDecimal(bean.getIdFolderType()) : null);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgignorewarningin(new Integer(1));

		DmpkFolderTypesDfoldertype dmpkFolderTypesDfoldertype = new DmpkFolderTypesDfoldertype();
		StoreResultBean<DmpkFolderTypesDfoldertypeBean> output = dmpkFolderTypesDfoldertype.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		
		return bean;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected String getXMLAttrAddXEvtDelTipoInOutBean(TipoFascicoliAggregatiBean bean) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		
		String xmlBean;
		List<XMLAttrAddXEvtDelTipoBean> lList = new ArrayList<XMLAttrAddXEvtDelTipoBean>();
		for (AttrAddXEvtDelTipoBean lBean : bean.getListaAttributiAddizionali()) {
			XMLAttrAddXEvtDelTipoBean lXmlBean = new XMLAttrAddXEvtDelTipoBean();
			lXmlBean.setNome(lBean.getNome());
			lXmlBean.setEtichetta(lBean.getEtichetta());
			lXmlBean.setCheckObbligatorio(lBean.getCheckObbligatorio() != null && lBean.getCheckObbligatorio() ? "1" : "0");
			if (lBean.getCheckRipetibile() != null && lBean.getCheckRipetibile() ? true : false) {
				lXmlBean.setMaxNumValori(lBean.getMaxNumValori());							
			} else {
				lXmlBean.setMaxNumValori("1");
			}
			if (lBean.getFlgTabPrincipale() != null && lBean.getFlgTabPrincipale()) {
				lXmlBean.setFlgTabPrincipale("1");
			} else {
				lXmlBean.setCodice(lBean.getCodice());
				lXmlBean.setLabel(lBean.getLabel());
			}
			lList.add(lXmlBean);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlBean = lXmlUtilitySerializer.bindXmlList(lList);
		
		return xmlBean;
	}

	public ListaTipoFascicoliBean aggiungiTipoFscicoliAUO(ListaTipoFascicoliBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityGrantprivsudefcontestoBean input = new DmpkDefSecurityGrantprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));

		input.setFlgtpobjtograntin("TF");
		input.setListaprivilegiin("A;M");

		/*
		 * In aggiunta Dmpk_def_security.GrantPrivSuDefContesto: o in FlgTpObjToGrantIn si passa TD per le tipologie doc. e TF per le tipologie di fascicolo;
		 * o in ListaPrivilegiIn si passa A;M;F per i tipi documenti e A;M;AF per i tipi fascicoli
		 */

		Lista lista = new Lista();
		for (TipoFascicoliAggregatiBean funzione : bean.getListaTipoFascicoli()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent(funzione.getIdFolderType());
			riga.getColonna().add(col1);
			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtograntxmlin(sw.toString());

		DmpkDefSecurityGrantprivsudefcontesto grantPrivSuDefContesto = new DmpkDefSecurityGrantprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityGrantprivsudefcontestoBean> output = grantPrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

	public ListaTipoFascicoliBean rimuoviTipoFscicoliDaUO(ListaTipoFascicoliBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityRevokeprivsudefcontestoBean input = new DmpkDefSecurityRevokeprivsudefcontestoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);

		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		input.setListaprivilegiin("");

		Lista lista = new Lista();
		for (TipoFascicoliAggregatiBean funzione : bean.getListaTipoFascicoli()) {
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent("TF");
			riga.getColonna().add(col1);
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getIdFolderType());
			riga.getColonna().add(col2);
			lista.getRiga().add(riga);
		}

		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);
		input.setListaobjtorevokexmlin(sw.toString());

		DmpkDefSecurityRevokeprivsudefcontesto revokePrivSuDefContesto = new DmpkDefSecurityRevokeprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityRevokeprivsudefcontestoBean> output = revokePrivSuDefContesto.execute(getLocale(), loginBean, input);

		if (output.getDefaultMessage() != null) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

}
