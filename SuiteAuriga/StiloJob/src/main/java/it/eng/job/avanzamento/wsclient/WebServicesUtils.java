/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.job.exception.StoreException;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.xml.XmlUtilitySerializer;

public class WebServicesUtils {

	Logger logger = Logger.getLogger(getClass());

	public void aggiornaDatiSIB(Session sessionJob, String schema, String token, String idDocPrimario, String idPropostAMC, String eventoSIB, String esitoEventoSIB, Date dataEventoSIB, String errMsgEventoSIB, AurigaLoginBean loginBean)
			throws Exception {

		{
			try {
				SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();

				if (StringUtils.isNotBlank(idPropostAMC)) {
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc",
							idPropostAMC != null ? idPropostAMC : "");
				}

				 if(StringUtils.isNotBlank(eventoSIB) && !"aggiornamento".equals(eventoSIB)) {
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_SIB_Doc", eventoSIB != null ? eventoSIB : "");
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_SIB_Doc", esitoEventoSIB != null ? esitoEventoSIB : "");
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_SIB_Doc", dataEventoSIB != null ? new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataEventoSIB) : "");
					 putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_SIB_Doc", errMsgEventoSIB != null ? errMsgEventoSIB : "");
				 }

				if (sezioneCacheAttributiDinamici.getVariabile().size() > 0) {

//					AurigaLoginBean loginBean = new AurigaLoginBean();
//					loginBean.setToken(token);
//					
//					logger.info("schema " + schema);
//					loginBean.setSchema(schema);
//					SpecializzazioneBean spec = new SpecializzazioneBean();
//					spec.setIdDominio(idDominio);
//					loginBean.setSpecializzazioneBean(spec);
					final UpddocudImpl lDmpkCoreUpddocud = new UpddocudImpl();
					DmpkCoreUpddocudBean bean = new DmpkCoreUpddocudBean();
					bean.setCodidconnectiontokenin(token);
					bean.setIduddocin(StringUtils.isNotBlank(idDocPrimario) ? new BigDecimal(idDocPrimario) : null);
					bean.setFlgtipotargetin("D");
					
					CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
					lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					bean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
					lDmpkCoreUpddocud.setBean(bean);
					sessionJob.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							lDmpkCoreUpddocud.execute(paramConnection);
						}
					});
					StoreResultBean<DmpkCoreUpddocudBean> out = new StoreResultBean<DmpkCoreUpddocudBean>();
					AnalyzeResult.analyze(bean, out);
					out.setResultBean(bean);
					
					

					if (out.isInError()) {
						throw new StoreException(out);
					}
				}
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
	}

	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile,
			String valoreSemplice) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if (pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);
		} else {
			sezioneCache.getVariabile().add(createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}

	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {
		if (sezioneCache != null && sezioneCache.getVariabile() != null) {
			for (int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if (var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}

	private Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}


}
