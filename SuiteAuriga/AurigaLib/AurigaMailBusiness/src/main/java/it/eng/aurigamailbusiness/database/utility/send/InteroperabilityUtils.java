/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGenerasegnaturaxmlBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.config.SegnaturaConfig;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.client.DmpkIntMgoEmailGenerasegnaturaxml;	
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FileUtil;

/**
 * classe d'utilità per la creazione delle notifiche di interoperabilità
 * 
 * @author jravagnan
 * @deprecated
 * 
 */
public class InteroperabilityUtils {

	private Locale locale = Locale.ITALIAN;

	private Logger log = LogManager.getLogger(InteroperabilityUtils.class);

	private static final String VERSIONE_SEGNATURA = "versionesegnatura";

	public File getSegnatura(RegistrazioneProtocollo registra, MailLoginBean login) throws AurigaMailBusinessException {
		File tmpFile = null;
		try {
			DmpkIntMgoEmailGenerasegnaturaxmlBean segnaturaBean = new DmpkIntMgoEmailGenerasegnaturaxmlBean();
			segnaturaBean.setCodidconnectiontokenin(login.getToken());
			segnaturaBean.setIduserlavoroin(new BigDecimal(login.getUserId()));
			segnaturaBean.setIdudin(new BigDecimal(registra.getIdProvReg()));
			segnaturaBean.setXmldatiinviomailin(registra.getXmlDatiIn());
			// VERIFICO QUALE VERSIONE DI SEGNATURA UTILIZZARE
			segnaturaBean.setVersionedtdin(getVersion());
			AurigaLoginBean aurLog = new AurigaLoginBean();
			aurLog.setToken(login.getToken());
			aurLog.setIdUserLavoro(login.getUserId());
			DmpkIntMgoEmailGenerasegnaturaxml segnaturaService = new DmpkIntMgoEmailGenerasegnaturaxml();
			StoreResultBean<DmpkIntMgoEmailGenerasegnaturaxmlBean> result = segnaturaService.execute(locale, aurLog, segnaturaBean);
			String segnRes = result.getResultBean().getXmlsegnaturaout();
			tmpFile = File.createTempFile("tmp_", "_xml");
			FileUtils.writeStringToFile(tmpFile, segnRes);
			return tmpFile;
		} catch (Exception e) {
			log.error("Impossibile ottenere il file di segnatura", e);
			throw new AurigaMailBusinessException("Impossibile ottenere il file di segnatura", e);
		} finally {
			if (tmpFile != null) {
				FileUtil.deleteFile(tmpFile);
			}
		}
	}

	private String getVersion() {
		SegnaturaConfig segnConf = (SegnaturaConfig) SpringAppContext.getContext().getBean(VERSIONE_SEGNATURA);
		String version = null;
		if (segnConf == null)
			version = "old";
		else
			version = segnConf.getVersione();
		return version;
	}
}
