/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityCtrldatiindirizzoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CtrlDatiIndirizzoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CtrlDatiIndirizzoXmlBean;
import it.eng.client.DmpkUtilityCtrldatiindirizzo;
import it.eng.document.NumeroColonna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

/**
 * 
 * @author cristiano
 *
 */

@Datasource(id = "CtrlDatiIndirizzoDataSource")
public class CtrlDatiIndirizzoDataSource extends AbstractServiceDataSource<CtrlDatiIndirizzoBean, CtrlDatiIndirizzoBean> {

	private static final Logger log = Logger.getLogger(CtrlDatiIndirizzoDataSource.class);

	public static final String _COD_ISTAT_ITALIA = "200";
	public static final String _NOME_STATO_ITALIA = "ITALIA";

	@Override
	public CtrlDatiIndirizzoBean call(CtrlDatiIndirizzoBean bean) throws Exception {
		
		CtrlDatiIndirizzoBean result = new CtrlDatiIndirizzoBean();
		try {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(loginBean.getSchema());

			DmpkUtilityCtrldatiindirizzoBean input = new DmpkUtilityCtrldatiindirizzoBean();
			input.setTsvldin(null);
			input.setFlgindobbligin(null);
			input.setFlgsoloindinviarioin(null);
			input.setIddominioin(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio());

			String xmlDatiIndirizziXml = buildXmlDatiIndirizzi(bean);
			input.setDatiindirizzoxmlio(xmlDatiIndirizziXml);

			DmpkUtilityCtrldatiindirizzo dmpkUtilityCtrldatiindirizzo = new DmpkUtilityCtrldatiindirizzo();
			StoreResultBean<DmpkUtilityCtrldatiindirizzoBean> output = dmpkUtilityCtrldatiindirizzo.execute(getLocale(), lSchemaBean, input);

			if (output.isInError()) {
				// if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				// addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				// }
				result.setEsito(false);
			} else {							
				if (StringUtils.isNotBlank(output.getResultBean().getDatiindirizzoxmlio())) {
					CtrlDatiIndirizzoXmlBean lCtrlDatiIndirizzoXmlBean = recuperaDatiIndirizziFromXml(output.getResultBean().getDatiindirizzoxmlio());
					if(StringUtils.isNotBlank(lCtrlDatiIndirizzoXmlBean.getCodiceVia())) {
						result.setEsito(true);
					} else {
						result.setEsito(false);
					}
					result.setCodToponimo(lCtrlDatiIndirizzoXmlBean.getCodiceVia());
					result.setIndirizzo(lCtrlDatiIndirizzoXmlBean.getDescrizioneVia());
					result.setCap(lCtrlDatiIndirizzoXmlBean.getCap());
					result.setZona(lCtrlDatiIndirizzoXmlBean.getZona());
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	protected String buildXmlDatiIndirizzi(CtrlDatiIndirizzoBean bean) throws Exception {
		CtrlDatiIndirizzoXmlBean lCtrlDatiIndirizzoXmlBean = new CtrlDatiIndirizzoXmlBean();
		lCtrlDatiIndirizzoXmlBean.setStato(_NOME_STATO_ITALIA);
		lCtrlDatiIndirizzoXmlBean.setCodIstat(_COD_ISTAT_ITALIA);
		if (bean.getCodToponimo() != null && !bean.getCodToponimo().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setCodiceVia(bean.getCodToponimo());
		} else {
			lCtrlDatiIndirizzoXmlBean.setCodiceVia("");
		}
		if (bean.getIndirizzo() != null && !bean.getIndirizzo().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setDescrizioneVia(bean.getIndirizzo());
		} else {
			lCtrlDatiIndirizzoXmlBean.setDescrizioneVia("");
		}
		if (bean.getCivico() != null && !bean.getCivico().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setNrCivico(bean.getCivico());
		} else {
			lCtrlDatiIndirizzoXmlBean.setNrCivico("");
		}
		if (bean.getCap() != null && !bean.getCap().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setCap(bean.getCap());
		} else {
			lCtrlDatiIndirizzoXmlBean.setCap("");
		}
		if (bean.getComune() != null && !bean.getComune().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setCodiceComune(bean.getComune());
		} else {
			lCtrlDatiIndirizzoXmlBean.setCodiceComune(ParametriDBUtil.getParametroDB(getSession(), "ISTAT_COMUNE_RIF"));
			// lCtrlDatiIndirizzoXmlBean.setCodiceComune("");
		}
		if (bean.getNomeComune() != null && !bean.getNomeComune().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setNomeComune(bean.getNomeComune());
		} else {
			lCtrlDatiIndirizzoXmlBean.setNomeComune(ParametriDBUtil.getParametroDB(getSession(), "NOME_COMUNE_RIF"));
			// lCtrlDatiIndirizzoXmlBean.setNomeComune("");
		}
		if (bean.getZona() != null && !bean.getZona().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setZona(bean.getZona());
		} else {
			lCtrlDatiIndirizzoXmlBean.setZona("");
		}
		if (bean.getAppendici() != null && !bean.getAppendici().equalsIgnoreCase("")) {
			lCtrlDatiIndirizzoXmlBean.setAppendici(bean.getAppendici());
		} else {
			lCtrlDatiIndirizzoXmlBean.setAppendici("");
		}
		BeanWrapperImpl wrapperCtrlDatiIndirizzoXmlBean = BeanPropertyUtility.getBeanWrapper(lCtrlDatiIndirizzoXmlBean);
		Riga lRiga = buildRigaFromBean(lCtrlDatiIndirizzoXmlBean, wrapperCtrlDatiIndirizzoXmlBean);
		return transformRigaToXml(lRiga);
	}

	protected CtrlDatiIndirizzoXmlBean recuperaDatiIndirizziFromXml(String xml) throws Exception {
		Riga lRiga = transformXmlToRiga(xml);
		return buildBeanFromRiga(lRiga);
	}

	private Riga buildRigaFromBean(CtrlDatiIndirizzoXmlBean lCtrlDatiIndirizzoXmlBean, BeanWrapperImpl wrapperCtrlDatiIndirizzoXmlBean) throws Exception {
		Riga lRiga = new Riga();
		Field[] lFields = lCtrlDatiIndirizzoXmlBean.getClass().getDeclaredFields();
		for (Field lField : lFields) {
			NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
			if (lNumeroColonna != null) {
				String value = BeanPropertyUtility.getPropertyValueAsString(lCtrlDatiIndirizzoXmlBean, wrapperCtrlDatiIndirizzoXmlBean, lField.getName());
				// String value = BeanUtilsBean2.getInstance().getProperty(lCtrlDatiIndirizzoXmlBean, lField.getName());
				Colonna lColonna = new Colonna();
				lColonna.setContent(value);
				lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
				lRiga.getColonna().add(lColonna);
			}
		}
		return lRiga;
	}

	private CtrlDatiIndirizzoXmlBean buildBeanFromRiga(Riga lRiga) throws Exception {
		CtrlDatiIndirizzoXmlBean lCtrlDatiIndirizzoXmlBean = new CtrlDatiIndirizzoXmlBean();
		BeanWrapperImpl wrappedCtrlDatiIndirizzoXmlBean = BeanPropertyUtility.getBeanWrapper(lCtrlDatiIndirizzoXmlBean);
		Field[] lFields = lCtrlDatiIndirizzoXmlBean.getClass().getDeclaredFields();
		if (lRiga != null) {
			for (Colonna lColonna : lRiga.getColonna()) {
				for (Field lField : lFields) {
					NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
					if (lNumeroColonna != null && lColonna.getNro().intValue() == Integer.parseInt(lNumeroColonna.numero())) {
						BeanPropertyUtility.setPropertyValue(lCtrlDatiIndirizzoXmlBean, wrappedCtrlDatiIndirizzoXmlBean, lField.getName(), lColonna.getContent());
						// BeanUtilsBean2.getInstance().setProperty(lCtrlDatiIndirizzoXmlBean, lField.getName(), lColonna.getContent());
					}
				}
			}
		}
		return lCtrlDatiIndirizzoXmlBean;
	}

	private Riga transformXmlToRiga(String xml) throws JAXBException {
		return (Riga) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(xml));
	}

	private String transformRigaToXml(Riga riga) throws JAXBException {
		StringWriter lStringWriter = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(riga, lStringWriter);
		return lStringWriter.toString();
	}

}
