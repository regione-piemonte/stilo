/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciDattributoBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciIuattributoBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciLoaddettattributoBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciTrovaattributidefinitiBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiCustom.datasource.bean.AttributiCustomBean;
import it.eng.client.DmpkAttributiDinamiciDattributo;
import it.eng.client.DmpkAttributiDinamiciIuattributo;
import it.eng.client.DmpkAttributiDinamiciLoaddettattributo;
import it.eng.client.DmpkAttributiDinamiciTrovaattributidefiniti;
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
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id = "AttributiCustomDataSource")
public class AttributiCustomDataSource extends AbstractFetchDataSource<AttributiCustomBean> {

	private static final Logger log = Logger.getLogger(AttributiCustomDataSource.class);

	@Override
	public String getNomeEntita() {
		return "attributi_custom";
	};

	@Override
	public PaginatorBean<AttributiCustomBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1; // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;

		List<AttributiCustomBean> data = new ArrayList<AttributiCustomBean>();

		String nomeF = null;
		String etichettaF = null;
		String tipoF = null;
		String descrizioneF = null;
		String appartenenzaF = null;

		BigDecimal flgEscludiSottoAttrF = null; // argomento FlgEscludiSottoAttrIO
		BigDecimal flgInclAnnullatiF = null; // argomento FlgInclAnnullatiIO

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nome".equals(crit.getFieldName())) {
					nomeF = getTextFilterValue(crit);
				} else if ("etichetta".equals(crit.getFieldName())) {
					etichettaF = getTextFilterValue(crit);
				} else if ("descrizione".equals(crit.getFieldName())) {
					descrizioneF = getTextFilterValue(crit);
				} else if ("appartenenza".equals(crit.getFieldName())) {
					appartenenzaF = getTextFilterValue(crit);
				} else if ("flgEscludiSottoAttr".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgEscludiSottoAttrF = new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0);
					}
				} else if ("flgInclAnnullati".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flgInclAnnullatiF = new Boolean(String.valueOf(crit.getValue())) ? new BigDecimal(1) : new BigDecimal(0);
					}
				} else if ("tipo".equals(crit.getFieldName())) {
					tipoF = getTextFilterValue(crit);
				}
			}
		}

		DmpkAttributiDinamiciTrovaattributidefinitiBean input = new DmpkAttributiDinamiciTrovaattributidefinitiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);
		input.setFlgdescorderbyio(flgDescOrderBy);
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);

		input.setNomeattrio(nomeF);
		input.setDesattrio(descrizioneF);
		input.setLabelattrio(etichettaF);
		input.setTipoattrio(tipoF);
		input.setNomeattrsupin(appartenenzaF);

		input.setFlgescludisottoattrio(flgEscludiSottoAttrF);
		input.setFlginclannullatiio(flgInclAnnullatiF);

		DmpkAttributiDinamiciTrovaattributidefiniti dmpkAttributiDinamiciTrovaattributidefiniti = new DmpkAttributiDinamiciTrovaattributidefiniti();
		StoreResultBean<DmpkAttributiDinamiciTrovaattributidefinitiBean> output = dmpkAttributiDinamiciTrovaattributidefiniti.execute(getLocale(), loginBean,
				input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
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
					AttributiCustomBean bean = new AttributiCustomBean();

					bean.setNome(v.get(1)); // Argomento - NomeAttrIO colonna 2
					bean.setDescrizione(v.get(2)); // DescrIO - colonna 3
					bean.setTipo(v.get(4));// TipoAttrIO - colonna 5
					bean.setEtichetta(v.get(5));// LabelAttrIO - colonna 6
					bean.setAppartenenza(v.get(6));// NomeAttrSupIn - colonna 7
					bean.setValido(new Integer(v.get(8)));// valore dell'xml icona di spunta se colonna 9=0, colonna 9 è flag di annullamento 1/0)

					data.add(bean);
				}
			}
		}

		PaginatorBean<AttributiCustomBean> lPaginatorBean = new PaginatorBean<AttributiCustomBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}

	@Override
	public AttributiCustomBean add(AttributiCustomBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAttributiDinamiciIuattributoBean input = new DmpkAttributiDinamiciIuattributoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomein(bean.getNome());
		input.setDescrizionein(bean.getDescrizione());
		input.setLabelin(bean.getEtichetta());
		input.setTipoin(bean.getTipo());
		input.setFlgsolovaldalookupin(bean.getValido());
		input.setNomeattrappin(bean.getAppartenenza());

		input.setRowidio(bean.getRowid());

		input.setAltezzaguiin(bean.getAltezzaVideoCaratteriItem() != null ? new Integer(bean.getAltezzaVideoCaratteriItem()) : new Integer(0));// altezzaVideoCaratteriItem
		input.setFormatonumericoin(bean.getFormatNumberItem());// formatNumberItem
		input.setLarghguiin(bean.getLarghezzaVideoCaratteriItem() != null ? new Integer(bean.getLarghezzaVideoCaratteriItem()) : new Integer(0));// larghezzaVideoCaratteriItem
		input.setRegularexprin(bean.getEspressioneRegolareItem());// espressioneRegolareItem

		if (bean.getTipo().equalsIgnoreCase("DATE")) {
			input.setValoredefaultin(bean.getDefaultDateValueItem() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDefaultDateValueItem()) : null); // defaultValue
			input.setValoremaxin(bean.getMaxDateValueItem() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getMaxDateValueItem()) : null);// maxNumeroCifreItem
			input.setValoreminin(bean.getMinDateValueItem() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getMinDateValueItem()) : null);// numMinValue
		} else if (bean.getTipo().equalsIgnoreCase("DATETIME")) {
			input.setValoredefaultin(bean.getDefaultDateTimeValueItem() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean
					.getDefaultDateTimeValueItem()) : null); // defaultValue
			input.setValoremaxin(bean.getMaxDateTimeValueItem() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getMaxDateTimeValueItem()) : null);// maxNumeroCifreItem
			input.setValoreminin(bean.getMinDateTimeValueItem() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getMinDateTimeValueItem()) : null);// numMinValue
		} else if (bean.getTipo().equalsIgnoreCase("TEXT-AREA")) {
			input.setValoredefaultin(bean.getDefaultTextAreaValue() != null ? bean.getDefaultTextAreaValue() : null);
			input.setValoremaxin(bean.getMaxNumeroCifreItem() != null ? bean.getMaxNumeroCifreItem() : null);
			input.setValoreminin(bean.getNumMinValue() != null ? bean.getNumMinValue() : null);
		} else if (bean.getTipo().equalsIgnoreCase("CKEDITOR")) {
			input.setValoredefaultin(bean.getDefaultCKeditorValue() != null ? bean.getDefaultCKeditorValue() : null);
			input.setValoremaxin(bean.getMaxNumeroCifreItem() != null ? bean.getMaxNumeroCifreItem() : null);
			input.setValoreminin(bean.getNumMinValue() != null ? bean.getNumMinValue() : null);
		} else {
			input.setValoredefaultin(bean.getDefaultValue() != null ? bean.getDefaultValue() : null); // defaultValue
			input.setValoremaxin(bean.getMaxNumeroCifreItem() != null ? bean.getMaxNumeroCifreItem() : null);// maxNumeroCifreItem
			input.setValoreminin(bean.getNumMinValue() != null ? bean.getNumMinValue() : null);// numMinValue
		}

		input.setRestrizionisulcasein(bean.getCaseItem());// caseItem
		input.setPrecisionedecimalein(bean.getNrDecimaleItem() != null ? new Integer(bean.getNrDecimaleItem()) : new Integer(0));// nrDecimaleItem
		input.setFlgdaindicizzarein(bean.getFlgDaIndicizzare() != null && bean.getFlgDaIndicizzare() ? new Integer(1) : new Integer(0));// flgDaIndicizzare
		input.setNrocifrecaratteriin(bean.getMaxNumeroCaratteriItem() != null ? new Integer(bean.getMaxNumeroCaratteriItem()) : new Integer(0));// maxNumeroCaratteriItem

		if (bean.getTipoLista().equals("Query") && StringUtils.isNotBlank(bean.getAttrQueryXValues())) {
			input.setQuerypervaloripossibiliin(bean.getAttrQueryXValues());// attrQueryXValues
			input.setXmlvaloripossibiliin(null);
		} else if (bean.getTipoLista().equals("Opzione") && bean.getXmlValoriPossibiliIn() != null && bean.getXmlValoriPossibiliIn().size() > 0) {
			String xmlValoriPox = null;
			xmlValoriPox = getXmlValoriPossibili(bean);
			input.setFlgmodvaloripossibiliin("I");
			input.setXmlvaloripossibiliin(xmlValoriPox);
			input.setQuerypervaloripossibiliin(null);
		} else if (bean.getTipoLista().equals("WebService") && bean.getuRLWSValoriPossibiliIn() != null) {
			input.setQuerypervaloripossibiliin(null);
			input.setXmlvaloripossibiliin(null);
			input.setUrlwsvaloripossibiliin(bean.getuRLWSValoriPossibiliIn());
			input.setXmlinwsvaloripossibiliin(bean.getxMLInWSValoriPossibiliIn());
		}

		input.setNrorigainattrappin(bean.getNroRigaInAttrAppIn());
		input.setNroordinattrappin(bean.getNroOrdine());
		input.setFlgvaloriunivociin(bean.getFlgValoriUnivociIn() != null && bean.getFlgValoriUnivociIn() ? new Integer(1) : new Integer(0));
		input.setFlgprotectedin(bean.getFlgProtectedIn() != null && bean.getFlgProtectedIn() ? new Integer(1) : new Integer(0));
		input.setFlgobbliginattrappin(bean.getFlgValoreObbligatorio() != null && bean.getFlgValoreObbligatorio() ? new Integer(1) : new Integer(0));
		input.setSubtipoin("CKEDITOR".equals(bean.getTipo()) ? bean.getTipoEditorHtml() : null);
		
		DmpkAttributiDinamiciIuattributo dmpkAttributiDinamiciIuattributo = new DmpkAttributiDinamiciIuattributo();
		StoreResultBean<DmpkAttributiDinamiciIuattributoBean> output = dmpkAttributiDinamiciIuattributo.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} else {
			bean.setRowid(output.getResultBean().getRowidio());
		}
		return bean;
	}

	@Override
	public AttributiCustomBean update(AttributiCustomBean bean, AttributiCustomBean oldBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAttributiDinamiciIuattributoBean input = new DmpkAttributiDinamiciIuattributoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomein(bean.getNome());
		input.setDescrizionein(bean.getDescrizione());
		input.setLabelin(bean.getEtichetta());
		input.setTipoin(bean.getTipo());
		input.setFlgsolovaldalookupin(bean.getValido());
		input.setNomeattrappin(bean.getAppartenenza());

		input.setRowidio(bean.getRowid());

		input.setAltezzaguiin(bean.getAltezzaVideoCaratteriItem() != null ? new Integer(bean.getAltezzaVideoCaratteriItem()) : new Integer(0));// altezzaVideoCaratteriItem
		input.setFormatonumericoin(bean.getFormatNumberItem());// formatNumberItem
		input.setLarghguiin(bean.getLarghezzaVideoCaratteriItem() != null ? new Integer(bean.getLarghezzaVideoCaratteriItem()) : new Integer(0));// larghezzaVideoCaratteriItem
		input.setRegularexprin(bean.getEspressioneRegolareItem());// espressioneRegolareItem

		if (bean.getTipo().equalsIgnoreCase("DATE")) {
			input.setValoredefaultin(bean.getDefaultDateValueItem() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDefaultDateValueItem()) : null); // defaultValue
			input.setValoremaxin(bean.getMaxDateValueItem() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getMaxDateValueItem()) : null);// maxNumeroCifreItem
			input.setValoreminin(bean.getMinDateValueItem() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getMinDateValueItem()) : null);// numMinValue
		} else if (bean.getTipo().equalsIgnoreCase("DATETIME")) {
			input.setValoredefaultin(bean.getDefaultDateTimeValueItem() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean
					.getDefaultDateTimeValueItem()) : null); // defaultValue
			input.setValoremaxin(bean.getMaxDateTimeValueItem() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getMaxDateTimeValueItem()) : null);// maxNumeroCifreItem
			input.setValoreminin(bean.getMinDateTimeValueItem() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getMinDateTimeValueItem()) : null);// numMinValue
		} else if (bean.getTipo().equalsIgnoreCase("TEXT-AREA")) {
			input.setValoredefaultin(bean.getDefaultTextAreaValue() != null ? bean.getDefaultTextAreaValue() : null);
			input.setValoremaxin(bean.getMaxNumeroCifreItem() != null ? bean.getMaxNumeroCifreItem() : null);
			input.setValoreminin(bean.getNumMinValue() != null ? bean.getNumMinValue() : null);
		} else if (bean.getTipo().equalsIgnoreCase("CKEDITOR")) {
			input.setValoredefaultin(bean.getDefaultCKeditorValue() != null ? bean.getDefaultCKeditorValue() : null);
			input.setValoremaxin(bean.getMaxNumeroCifreItem() != null ? bean.getMaxNumeroCifreItem() : null);
			input.setValoreminin(bean.getNumMinValue() != null ? bean.getNumMinValue() : null);
		} else {
			input.setValoredefaultin(bean.getDefaultValue() != null ? bean.getDefaultValue() : null); // defaultValue
			input.setValoremaxin(bean.getMaxNumeroCifreItem() != null ? bean.getMaxNumeroCifreItem() : null);// maxNumeroCifreItem
			input.setValoreminin(bean.getNumMinValue() != null ? bean.getNumMinValue() : null);// numMinValue
		}

		input.setRestrizionisulcasein(bean.getCaseItem());// caseItem
		input.setPrecisionedecimalein(bean.getNrDecimaleItem() != null ? new Integer(bean.getNrDecimaleItem()) : new Integer(0));// nrDecimaleItem
		input.setFlgdaindicizzarein(bean.getFlgDaIndicizzare() != null && bean.getFlgDaIndicizzare() ? new Integer(1) : new Integer(0));// flgDaIndicizzare
		input.setNrocifrecaratteriin(bean.getMaxNumeroCaratteriItem() != null ? new Integer(bean.getMaxNumeroCaratteriItem()) : new Integer(0));// maxNumeroCaratteriItem

		if (bean.getTipoLista().equals("Query") && StringUtils.isNotBlank(bean.getAttrQueryXValues())) {
			input.setQuerypervaloripossibiliin(bean.getAttrQueryXValues());// attrQueryXValues
			input.setXmlvaloripossibiliin(null);
		} else if (bean.getTipoLista().equals("Opzione") && bean.getXmlValoriPossibiliIn() != null && bean.getXmlValoriPossibiliIn().size() > 0) {
			String xmlValoriPox = null;
			xmlValoriPox = getXmlValoriPossibili(bean);
			input.setFlgmodvaloripossibiliin("C");
			input.setXmlvaloripossibiliin(xmlValoriPox);
			input.setQuerypervaloripossibiliin(null);
		} else if (bean.getTipoLista().equals("WebService") && bean.getuRLWSValoriPossibiliIn() != null) {
			input.setQuerypervaloripossibiliin(null);
			input.setXmlvaloripossibiliin(null);
			input.setUrlwsvaloripossibiliin(bean.getuRLWSValoriPossibiliIn());
			input.setXmlinwsvaloripossibiliin(bean.getxMLInWSValoriPossibiliIn());
		}

		input.setNrorigainattrappin(bean.getNroRigaInAttrAppIn());
		input.setNroordinattrappin(bean.getNroOrdine());
		input.setFlgvaloriunivociin(bean.getFlgValoriUnivociIn() != null && bean.getFlgValoriUnivociIn() ? new Integer(1) : new Integer(0));
		input.setFlgprotectedin(bean.getFlgProtectedIn() != null && bean.getFlgProtectedIn() ? new Integer(1) : new Integer(0));
		input.setFlgobbliginattrappin(bean.getFlgValoreObbligatorio() != null && bean.getFlgValoreObbligatorio() ? new Integer(1) : new Integer(0));
		input.setSubtipoin("CKEDITOR".equals(bean.getTipo()) ? bean.getTipoEditorHtml() : null);
		
		DmpkAttributiDinamiciIuattributo dmpkAttributiDinamiciIuattributo = new DmpkAttributiDinamiciIuattributo();
		StoreResultBean<DmpkAttributiDinamiciIuattributoBean> output = dmpkAttributiDinamiciIuattributo.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	@Override
	public AttributiCustomBean remove(AttributiCustomBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAttributiDinamiciDattributoBean input = new DmpkAttributiDinamiciDattributoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomein(StringUtils.isNotBlank(bean.getNome()) ? bean.getNome() : null);
		input.setMotiviin(null);
		input.setFlgignorewarningin(new Integer(1));
		input.setFlgcancfisicain(null);

		DmpkAttributiDinamiciDattributo dmpkAttributiDinamiciDattributo = new DmpkAttributiDinamiciDattributo();
		StoreResultBean<DmpkAttributiDinamiciDattributoBean> output = dmpkAttributiDinamiciDattributo.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}

	@Override
	public AttributiCustomBean get(AttributiCustomBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkAttributiDinamiciLoaddettattributoBean input = new DmpkAttributiDinamiciLoaddettattributoBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setNomeio(bean.getNome());

		DmpkAttributiDinamiciLoaddettattributo loadDettAttr = new DmpkAttributiDinamiciLoaddettattributo();
		StoreResultBean<DmpkAttributiDinamiciLoaddettattributoBean> output = loadDettAttr.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		AttributiCustomBean result = new AttributiCustomBean();
		result.setRowid(output.getResultBean().getRowidout());
		result.setNome(output.getResultBean().getNomeio());
		result.setDescrizione(output.getResultBean().getDescrizioneout());
		result.setEtichetta(output.getResultBean().getLabelout());
		result.setTipo(output.getResultBean().getTipoout());
		result.setAppartenenza(output.getResultBean().getNomeattrappout());

		result.setValido(output.getResultBean().getFlgsolovaldalookupout());
		result.setAltezzaVideoCaratteriItem(output.getResultBean().getAltezzaguiout() != null ? output.getResultBean().getAltezzaguiout().toString() : null);
		result.setFormatNumberItem(output.getResultBean().getFormatonumericoout());
		result.setLarghezzaVideoCaratteriItem(output.getResultBean().getLarghguiout() != null ? output.getResultBean().getLarghguiout().toString() : null);
		result.setCaseItem(output.getResultBean().getRestrizionisulcaseout());

		if (output.getResultBean().getTipoout().equalsIgnoreCase("DATE")) {
			result.setDefaultDateValueItem(output.getResultBean().getValoredefaultout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output
					.getResultBean().getValoredefaultout()) : null);
			result.setMaxDateValueItem(output.getResultBean().getValoremaxout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean()
					.getValoremaxout()) : null);
			result.setMinDateValueItem(output.getResultBean().getValoreminout() != null ? new SimpleDateFormat(FMT_STD_DATA).parse(output.getResultBean()
					.getValoreminout()) : null);
		} else if (output.getResultBean().getTipoout().equalsIgnoreCase("DATETIME")) {
			result.setDefaultDateTimeValueItem(output.getResultBean().getValoredefaultout() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output
					.getResultBean().getValoredefaultout()) : null);
			result.setMaxDateTimeValueItem(output.getResultBean().getValoremaxout() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output
					.getResultBean().getValoremaxout()) : null);
			result.setMinDateTimeValueItem(output.getResultBean().getValoreminout() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output
					.getResultBean().getValoreminout()) : null);
		} else if (output.getResultBean().getTipoout().equalsIgnoreCase("TEXT-AREA")) {
			result.setDefaultTextAreaValue(output.getResultBean().getValoredefaultout());
			result.setNumMinValue(output.getResultBean().getValoreminout());
			result.setMaxNumeroCifreItem(output.getResultBean().getValoremaxout());
		} else if (output.getResultBean().getTipoout().equalsIgnoreCase("CKEDITOR")) {
			result.setDefaultCKeditorValue(output.getResultBean().getValoredefaultout());
			result.setNumMinValue(output.getResultBean().getValoreminout());
			result.setMaxNumeroCifreItem(output.getResultBean().getValoremaxout());
		} else {
			result.setDefaultValue(output.getResultBean().getValoredefaultout());
			result.setNumMinValue(output.getResultBean().getValoreminout());
			result.setMaxNumeroCifreItem(output.getResultBean().getValoremaxout());
		}

		if (output.getResultBean().getQuerypervaloripossibiliout() != null) {
			result.setAttrQueryXValues(output.getResultBean().getQuerypervaloripossibiliout());
		}

		List<OpzioniListaSceltaBean> listaXmlValoriPossibili = new ArrayList<OpzioniListaSceltaBean>();
		if (output.getResultBean().getXmlvaloripossibiliout() != null && output.getResultBean().getXmlvaloripossibiliout().length() > 0) {
			StringReader sr = new StringReader(output.getResultBean().getXmlvaloripossibiliout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			if (lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					OpzioniListaSceltaBean opzioniListaSceltaBeanItem = new OpzioniListaSceltaBean();
					opzioniListaSceltaBeanItem.setOpzione(v.get(0));
					listaXmlValoriPossibili.add(opzioniListaSceltaBeanItem);
				}
				result.setXmlValoriPossibiliIn(listaXmlValoriPossibili);
			}
		}

		if (output.getResultBean().getUrlwsvaloripossibiliout() != null) {
			if (StringUtils.isNotBlank(output.getResultBean().getUrlwsvaloripossibiliout())) {
				result.setuRLWSValoriPossibiliIn(output.getResultBean().getUrlwsvaloripossibiliout());

				if (output.getResultBean().getXmlinwsvaloripossibiliout() != null) {
					result.setxMLInWSValoriPossibiliIn(output.getResultBean().getXmlinwsvaloripossibiliout());
				}
			}
		}
		
		if(output.getResultBean().getSubtipoout() != null) {
			result.setTipoEditorHtml(output.getResultBean().getSubtipoout());
		}

		if (StringUtils.isNotBlank(result.getAttrQueryXValues())) {
			result.setTipoLista("Query");
		} else if (result.getXmlValoriPossibiliIn() != null && result.getXmlValoriPossibiliIn().size() > 0) {
			result.setTipoLista("Opzione");
		} else if (result.getuRLWSValoriPossibiliIn() != null) {
			result.setTipoLista("WebService");
		}

		result.setNrDecimaleItem(output.getResultBean().getPrecisionedecimaleout() != null ? output.getResultBean().getPrecisionedecimaleout().toString()
				: null);
		result.setMaxNumeroCaratteriItem(output.getResultBean().getNrocifrecaratteriout() != null ? output.getResultBean().getNrocifrecaratteriout().toString()
				: null);

		result.setEspressioneRegolareItem(output.getResultBean().getRegularexprout());
		result.setFlgDaIndicizzare(output.getResultBean().getFlgdaindicizzareout() != null && output.getResultBean().getFlgdaindicizzareout().intValue() == 1 ? true
				: false);

		result.setNroRigaInAttrAppIn(output.getResultBean().getNrorigainattrappout() != null ? new Integer(output.getResultBean().getNrorigainattrappout()
				.intValue()) : new Integer(0));
		result.setNroOrdine(output.getResultBean().getNroordinattrappout() != null ? new Integer(output.getResultBean().getNroordinattrappout().intValue())
				: new Integer(0));
		result.setFlgValoriUnivociIn(output.getResultBean().getFlgvaloriunivociout() != null && output.getResultBean().getFlgvaloriunivociout().intValue() == 1 ? true
				: false);
		result.setFlgProtectedIn(output.getResultBean().getFlgprotectedout() != null && output.getResultBean().getFlgprotectedout().intValue() == 1 ? true
				: false);
		result.setFlgValoreObbligatorio(output.getResultBean().getFlgobbliginattrappout() != null
				&& output.getResultBean().getFlgobbliginattrappout().intValue() == 1 ? true : false);

		return result;
	}

	protected String getXmlValoriPossibili(AttributiCustomBean bean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String xmlContatti = null;
		List<OpzioniListaSceltaBean> lList = new ArrayList<OpzioniListaSceltaBean>();
		for (OpzioniListaSceltaBean lOpzioniListaSceltaBeanItem : bean.getXmlValoriPossibiliIn()) {
			OpzioniListaSceltaBean lOpzioniListaSceltaBean = new OpzioniListaSceltaBean();
			lOpzioniListaSceltaBean.setOpzione(lOpzioniListaSceltaBeanItem.getOpzione());
			lList.add(lOpzioniListaSceltaBean);
		}
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlContatti = lXmlUtilitySerializer.bindXmlList(lList);
		return xmlContatti;
	}
}
