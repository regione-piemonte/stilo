/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindfasctitolarioBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiFascTitolarioXMLIOBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FindFascTitolarioBean;
import it.eng.client.DmpkCoreFindfasctitolario;
import it.eng.document.NumeroColonna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "FindFascTitolarioDatasource")
public class FindFascTitolarioDatasource extends AbstractServiceDataSource<FindFascTitolarioBean, FindFascTitolarioBean> {

	@Override
	public FindFascTitolarioBean call(FindFascTitolarioBean bean) throws Exception {
		FindFascTitolarioBean result = new FindFascTitolarioBean();
		try {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();

			DmpkCoreFindfasctitolarioBean input = new DmpkCoreFindfasctitolarioBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgsoloassegnabiliin(1);
			input.setFlgcompletadatiin(1);

			String xmlDatiFascTitolarioIn = buildXmlDatiFascicolo(bean);
			input.setDatifascicoloxmlio(xmlDatiFascTitolarioIn);

			DmpkCoreFindfasctitolario lDmpkCoreFindfasctitolario = new DmpkCoreFindfasctitolario();
			StoreResultBean<DmpkCoreFindfasctitolarioBean> output = lDmpkCoreFindfasctitolario.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()),
					input);

			if (output.isInError()) {
				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
				return new FindFascTitolarioBean();
			} else {
				result.setIdFolder(output.getResultBean().getIdfolderout());
				if (StringUtils.isNotBlank(output.getResultBean().getDatifascicoloxmlio())) {
					DatiFascTitolarioXMLIOBean lDatiFascTitolarioXMLIOBean = recuperaDatiFascicoloFromXml(output.getResultBean().getDatifascicoloxmlio());
					result = populateFascicolo(lDatiFascTitolarioXMLIOBean);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	private FindFascTitolarioBean populateFascicolo(DatiFascTitolarioXMLIOBean lDatiFascTitolarioXMLIOBean) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		FindFascTitolarioBean fascTitolarioInfo = new FindFascTitolarioBean();
		fascTitolarioInfo.setNomeFascicolo(lDatiFascTitolarioXMLIOBean.getNomeFascicolo());
		fascTitolarioInfo.setAnnoFascicolo(lDatiFascTitolarioXMLIOBean.getAnnoFascicolo());
		fascTitolarioInfo.setIdClassifica(lDatiFascTitolarioXMLIOBean.getIdClassifica());
		fascTitolarioInfo.setIndiceClassifica(lDatiFascTitolarioXMLIOBean.getIndiceClassifica());
		fascTitolarioInfo.setNroFascicolo(lDatiFascTitolarioXMLIOBean.getNroFascicolo());
		fascTitolarioInfo.setNroSottofascicolo(lDatiFascTitolarioXMLIOBean.getNroSottofascicolo());
		fascTitolarioInfo.setNroInserto(lDatiFascTitolarioXMLIOBean.getNroInserto());		
		fascTitolarioInfo.setIdFolderFascicolo(lDatiFascTitolarioXMLIOBean.getIdFolderFascicolo());
		fascTitolarioInfo.setNroSecondario(lDatiFascTitolarioXMLIOBean.getNroSecondario());
		fascTitolarioInfo.setLivelloRiservatezza(lDatiFascTitolarioXMLIOBean.getLivelloRiservatezza());
		fascTitolarioInfo.setEstremiDocCapofila(lDatiFascTitolarioXMLIOBean.getEstremiDocCapofila());
		return fascTitolarioInfo;
	}

	protected String buildXmlDatiFascicolo(FindFascTitolarioBean bean) throws Exception {
		DatiFascTitolarioXMLIOBean lDatiFascTitolarioXMLIOBean = new DatiFascTitolarioXMLIOBean();
		if (bean.getNomeFascicolo() != null && !bean.getNomeFascicolo().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setNomeFascicolo(bean.getNomeFascicolo());
		} else {
			lDatiFascTitolarioXMLIOBean.setNomeFascicolo("");
		}
		if (bean.getNroSecondario() != null && !bean.getNroSecondario().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setNroSecondario(bean.getNroSecondario());
		} else {
			lDatiFascTitolarioXMLIOBean.setNroSecondario("");
		}
		if (bean.getEstremiDocCapofila() != null && !bean.getEstremiDocCapofila().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setEstremiDocCapofila(bean.getEstremiDocCapofila());
		} else {
			lDatiFascTitolarioXMLIOBean.setEstremiDocCapofila("");
		}
		if (bean.getAnnoFascicolo() != null && !bean.getAnnoFascicolo().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setAnnoFascicolo(bean.getAnnoFascicolo());
		} else {
			lDatiFascTitolarioXMLIOBean.setAnnoFascicolo("");
		}
		if (bean.getNroFascicolo() != null && !bean.getNroFascicolo().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setNroFascicolo(bean.getNroFascicolo());
		} else {
			lDatiFascTitolarioXMLIOBean.setNroFascicolo("");
		}
		if (bean.getNroSottofascicolo() != null && !bean.getNroSottofascicolo().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setNroSottofascicolo(bean.getNroSottofascicolo());
		} else {
			lDatiFascTitolarioXMLIOBean.setNroSottofascicolo("");
		}
		if (bean.getNroInserto() != null && !bean.getNroInserto().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setNroInserto(bean.getNroInserto());
		} else {
			lDatiFascTitolarioXMLIOBean.setNroInserto("");
		}
		if (bean.getIdFolderFascicolo() != null && !bean.getIdFolderFascicolo().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setIdFolderFascicolo(bean.getIdFolderFascicolo());
		} else {
			lDatiFascTitolarioXMLIOBean.setIdFolderFascicolo("");
		}
		if (bean.getIdClassifica() != null && !bean.getIdClassifica().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setIdClassifica(bean.getIdClassifica());
		} else {
			lDatiFascTitolarioXMLIOBean.setIdClassifica("");
		}
		if (bean.getIndiceClassifica() != null && !bean.getIndiceClassifica().equalsIgnoreCase("")) {
			lDatiFascTitolarioXMLIOBean.setIndiceClassifica(bean.getIndiceClassifica());
		} else {
			lDatiFascTitolarioXMLIOBean.setIndiceClassifica("");
		}
		BeanWrapperImpl wrapperDatiFascTitolarioXMLIOBean = BeanPropertyUtility.getBeanWrapper(lDatiFascTitolarioXMLIOBean);
		Riga lRiga = buildRigaFromBean(lDatiFascTitolarioXMLIOBean, wrapperDatiFascTitolarioXMLIOBean);
		return transformRigaToXml(lRiga);
	}

	protected DatiFascTitolarioXMLIOBean recuperaDatiFascicoloFromXml(String xml) throws Exception {
		Riga lRiga = transformXmlToRiga(xml);
		return buildBeanFromRiga(lRiga);
	}

	private Riga buildRigaFromBean(DatiFascTitolarioXMLIOBean lDatiFascTitolarioXMLIOBean, BeanWrapperImpl wrapperDatiFascTitolarioXMLIOBean) throws Exception {
		Riga lRiga = new Riga();
		Field[] lFields = lDatiFascTitolarioXMLIOBean.getClass().getDeclaredFields();
		for (Field lField : lFields) {
			NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
			if (lNumeroColonna != null) {
				String value = BeanPropertyUtility.getPropertyValueAsString(lDatiFascTitolarioXMLIOBean, wrapperDatiFascTitolarioXMLIOBean, lField.getName());
				// String value = BeanUtilsBean2.getInstance().getProperty(lDatiFascTitolarioXMLIOBean, lField.getName());
				Colonna lColonna = new Colonna();
				lColonna.setContent(value);
				lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
				lRiga.getColonna().add(lColonna);
			}
		}
		return lRiga;
	}

	private DatiFascTitolarioXMLIOBean buildBeanFromRiga(Riga lRiga) throws Exception {
		DatiFascTitolarioXMLIOBean lDatiFascTitolarioXMLIOBean = new DatiFascTitolarioXMLIOBean();
		BeanWrapperImpl wrapperDatiFascTitolarioXMLIOBean = BeanPropertyUtility.getBeanWrapper(lDatiFascTitolarioXMLIOBean);
		Field[] lFields = lDatiFascTitolarioXMLIOBean.getClass().getDeclaredFields();
		if (lRiga != null) {
			for (Colonna lColonna : lRiga.getColonna()) {
				for (Field lField : lFields) {
					NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
					if (lNumeroColonna != null && lColonna.getNro().intValue() == Integer.parseInt(lNumeroColonna.numero())) {
						BeanPropertyUtility.setPropertyValue(lDatiFascTitolarioXMLIOBean, wrapperDatiFascTitolarioXMLIOBean, lField.getName(), lColonna.getContent());
						// BeanUtilsBean2.getInstance().setProperty(lDatiFascTitolarioXMLIOBean, lField.getName(), lColonna.getContent());
					}
				}
			}
		}
		return lDatiFascTitolarioXMLIOBean;
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
