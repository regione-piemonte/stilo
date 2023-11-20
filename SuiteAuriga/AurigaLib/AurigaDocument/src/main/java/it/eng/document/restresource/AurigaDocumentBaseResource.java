/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBContext;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.sun.jersey.api.core.ResourceContext;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCtrlutenzaabilitatainvioBean;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.gd.lucenemanager.storage.StorageCenter;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.xml.XmlUtilityDeserializer;

public abstract class AurigaDocumentBaseResource {

	private static final Logger log = Logger.getLogger(AurigaDocumentBaseResource.class);
	protected static final String KEY_SCHEMA = "rs.database.schema";
	// se non specificato l'autenticazione viene eseguita comunque
	protected static final String KEY_FLAG_SKIP_AUTH = "rs.authentication.flag.skip";
	protected final Configuration cfg;
	protected final IServiceSerialize serializeUtil = null;
	protected final String schema;// "OWNER_1"
	protected final Boolean flagSkipAuthentication;
	protected final AurigaDocumentFunctionCallHelper callHelper;
	protected final JAXBContext jaxbContextSezCache;
	protected final XmlUtilityDeserializer xmlUtilityDeserializer;
	protected final StorageCenter storageCenter;
	@Context
	protected ResourceContext resourceCtx;

	public AurigaDocumentBaseResource() throws Exception {
		cfg = Configuration.getInstance();
		// serializeUtil = SerializerRepository.getSerializationUtil(cfg.getSerializationtype());
		schema = ConfigUtil.getConfig().getString(KEY_SCHEMA);
		flagSkipAuthentication = ConfigUtil.getConfig().getBoolean(KEY_FLAG_SKIP_AUTH, Boolean.FALSE);
		callHelper = new AurigaDocumentFunctionCallHelper();
		jaxbContextSezCache = SingletonJAXBContext.getInstance();
		storageCenter = new StorageCenter();
		xmlUtilityDeserializer = new XmlUtilityDeserializer();
	}

	protected String getIdUtenteModPec(final DmpkIntMgoEmailCtrlutenzaabilitatainvioBean out) {
		if (out == null)
			return null;
		String idUtenteModPec = out.getIdutentepecout();
		if (out.getIduserout() != null && StringUtils.isBlank(idUtenteModPec)) {
			final BigInteger idUserInt = out.getIduserout().toBigInteger();
			idUtenteModPec = idUserInt.toString();
		}
		return idUtenteModPec;
	}

	protected Map<String, String> retrieveMessages(final String xml) throws Exception {
		Map<String, String> messages = new HashMap<String, String>(0);
		if (StringUtils.isBlank(xml)) {
			return messages;
		}
		final StringReader reader = new StringReader(xml);
		final Lista lista = (Lista) jaxbContextSezCache.createUnmarshaller().unmarshal(reader);
		final int rowNbr = lista.getRiga().size();
		if (rowNbr > 0) {
			messages = new HashMap<String, String>();
		}
		for (int i = 0; i < rowNbr; i++) {
			final Lista.Riga riga_i = lista.getRiga().get(i);
			final List<Colonna> cols = riga_i.getColonna();
			if (cols.size() <= 4)
				continue;
			final Colonna colonna3 = cols.get(3);
			if (colonna3 == null)
				continue;
			final String content3 = colonna3.getContent();
			if ("KO".equalsIgnoreCase(content3)) {
				messages.put(cols.get(0).getContent(), cols.get(4).getContent());
			}
		} // for

		return messages;
	}// retrieveMessages

}// BaseResource
