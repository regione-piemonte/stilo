/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.ui.module.layout.shared.bean.SchemaSelector;
import it.eng.auriga.ui.module.layout.shared.util.SharedConstants;
import it.eng.client.AurigaService;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.utility.ui.user.UserUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class EtichetteService implements PingCallback {

	private static Logger mLogger = Logger.getLogger(EtichetteService.class);

	public static String PREF_KEY_IMPOSTAZIONI_STAMPA_ETICHETTA = "impostazioniStampaEtichetta";
	public static String PREF_KEY_PRN_PROPERTIES = "impostazioniPrn";
	public static String PREF_KEY_PDF_PROPERTIES = "impostazioniPdf";
	public static String PREF_NAME_PROPERTIES = "properties";
	public static String PREF_NAME = "impostazioni";
	public static String PDF_PROPERTIES_FILE = "pdf.properties";
	public static String PRN_PROPERTIES_FILE = "stampaetichettaapplet.properties";
	public static String DEFAULT = "DEFAULT";
	//public Locale locale = UserUtil.getLocale(getSession());

	@Autowired
	private ImpostaEtichettaBean defaultValue;
	@Autowired
	private SchemaSelector schema;

	@Autowired
	public void init() throws Exception {
		EtichetteThread lEtichetteThread = new EtichetteThread(this);
		lEtichetteThread.start();
//		initAfterBusinessLoad();
	}

	public void initAfterBusinessLoad() throws Exception {
		AurigaLoginBean lAurigaLoginBean = new AurigaLoginBean();
		// Inserisco la lingua di default
		lAurigaLoginBean.setLinguaApplicazione(SharedConstants.DEFAUL_LANGUAGE);
		lAurigaLoginBean.setSchema(schema.getSchemi().get(0).getName());
		mLogger.debug("Verifico l'esistenza di un default generico per ImpostaEtichettaBean");
		
		boolean present = existPreference(PREF_KEY_IMPOSTAZIONI_STAMPA_ETICHETTA, PREF_NAME, DEFAULT, lAurigaLoginBean);
		mLogger.debug("Presente " + present);
		if (!present){
			insertDefaultEtichetta(lAurigaLoginBean);	
		}
		boolean propertiesPresent = existPreference(PREF_KEY_PRN_PROPERTIES, PREF_NAME_PROPERTIES, DEFAULT, lAurigaLoginBean);
		if (!propertiesPresent){
			insertDefaultPrn(lAurigaLoginBean);	
		}
		propertiesPresent = existPreference(PREF_KEY_PDF_PROPERTIES, PREF_NAME_PROPERTIES, DEFAULT, lAurigaLoginBean);
		if (!propertiesPresent){
			insertDefaultPdf(lAurigaLoginBean);	
		}
	}

	private void insertDefaultPdf(AurigaLoginBean lAurigaLoginBean) throws Exception {
		String lString = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource(PDF_PROPERTIES_FILE).toURI()));
		PreferenceBean lPreferenceBean = new PreferenceBean();
		lPreferenceBean.setPrefKey(PREF_KEY_PDF_PROPERTIES);
		lPreferenceBean.setPrefName(PREF_NAME_PROPERTIES);
		lPreferenceBean.setUserid(DEFAULT);
		lPreferenceBean.setValue(lString);
		lPreferenceBean.setSettingTime(new Date());
		Locale locale = new Locale(lAurigaLoginBean.getLinguaApplicazione());
		AurigaService.getDaoTUserPreferences().save(locale,lAurigaLoginBean , lPreferenceBean);
	}

	private void insertDefaultPrn(AurigaLoginBean lAurigaLoginBean) throws Exception {
		String lString = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource(PRN_PROPERTIES_FILE).toURI()));
		PreferenceBean lPreferenceBean = new PreferenceBean();
		lPreferenceBean.setPrefKey(PREF_KEY_PRN_PROPERTIES);
		lPreferenceBean.setPrefName(PREF_NAME_PROPERTIES);
		lPreferenceBean.setUserid(DEFAULT);
		lPreferenceBean.setValue(lString);
		lPreferenceBean.setSettingTime(new Date());
		Locale locale = new Locale(lAurigaLoginBean.getLinguaApplicazione());
		AurigaService.getDaoTUserPreferences().save(locale,lAurigaLoginBean , lPreferenceBean);
	}

	protected void insertDefaultEtichetta(AurigaLoginBean lAurigaLoginBean)
			throws Exception {
		PreferenceBean lPreferenceBean = new PreferenceBean();
		lPreferenceBean.setPrefKey(PREF_KEY_IMPOSTAZIONI_STAMPA_ETICHETTA);
		lPreferenceBean.setPrefName(PREF_NAME);
		lPreferenceBean.setUserid(DEFAULT);
		Gson gson = new Gson();
		lPreferenceBean.setValue(gson.toJson(defaultValue));
		lPreferenceBean.setSettingTime(new Date());
		Locale locale = new Locale(lAurigaLoginBean.getLinguaApplicazione());
		AurigaService.getDaoTUserPreferences().save(locale,lAurigaLoginBean , lPreferenceBean);
	}
	
	public boolean existPreference(String prefKey, String prefName, String utente, AurigaLoginBean lAurigaLoginBean) throws Exception{
		return (getPreference(prefKey, prefName, utente, lAurigaLoginBean) != null);
	}

	public PreferenceBean getPreference(String prefKey, String prefName, String utente, AurigaLoginBean lAurigaLoginBean) throws Exception{
		PreferenceBean bean = new PreferenceBean();
		bean.setUserid(utente);
		bean.setPrefName(prefName);
		bean.setPrefKey(prefKey);
//		AurigaService.getDaoTUserPreferences().delete(locale, lAurigaLoginBean, bean);

		TFilterFetch<PreferenceBean> filter = new TFilterFetch<PreferenceBean>();
		filter.setStartRow(0);
		filter.setEndRow(1);
		filter.setFilter(bean);

		Locale locale = new Locale(lAurigaLoginBean.getLinguaApplicazione());
		TPagingList<PreferenceBean> pagingList = AurigaService.getDaoTUserPreferences().search(locale, lAurigaLoginBean, filter);

		if (pagingList.getData()==null || pagingList.getData().size() == 0){
			return null;
		} else return pagingList.getData().get(0);
	}

	public void setDefaultValue(ImpostaEtichettaBean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public ImpostaEtichettaBean getDefaultValue() {
		return defaultValue;
	}

	public SchemaSelector getSchema() {
		return schema;
	}

	public void setSchema(SchemaSelector schema) {
		this.schema = schema;
	}
}
