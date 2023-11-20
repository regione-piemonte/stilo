/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.gd.lucenemanager.LuceneIndexWriter;
import it.eng.gd.lucenemanager.bean.SearchCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * scrivo sugli indici di Lucene
 * 
 * @author jravagnan
 * 
 */
@Service(name = "LuceneWriter")
public class LuceneWriter {

	private LuceneIndexWriter luceneWriter = null;

	private Logger log = LogManager.getLogger(LuceneWriter.class);

	/**
	 * inserisco negli indici di Lucene gli attributi da indicizzare e quelli invece da non indicizzare riguardanti la mail
	 * 
	 * @param message
	 * @param idCasella
	 * @throws AurigaMailBusinessException
	 */
	@Operation(name = "indicizza")
	public void indicizza(EmailBean message, String idCasella) throws AurigaMailBusinessException {
		try {
			luceneWriter = new LuceneIndexWriter();
			String id = message.getMail().getIdEmail();
			String subject = message.getMail().getOggetto();
			String mittente = message.getMail().getAccountMittente();
			List<TDestinatariEmailMgoBean> destinatari = message.getDestinatari();
			String destini = "";
			if (destinatari != null) {
				for (TDestinatariEmailMgoBean destina : destinatari) {
					destini = destina.getAccountDestinatario() + LuceneConstants.SPACE;
				}
			}
			String body = message.getMail().getCorpo();
			List<TAttachEmailMgoBean> attachments = message.getAttachments();
			String nomeAllegati = "";
			if (attachments != null) {
				for (TAttachEmailMgoBean att : attachments) {
					nomeAllegati = nomeAllegati + att.getNomeOriginale() + LuceneConstants.SPACE;
				}
			}
			Map<String, Object> indexedMetadata = new HashMap<String, Object>();
			indexedMetadata.put(LuceneConstants.KEY_MITTENTE, mittente);
			indexedMetadata.put(LuceneConstants.KEY_DESTINATARI, destini);
			indexedMetadata.put(LuceneConstants.KEY_BODY, body);
			indexedMetadata.put(LuceneConstants.KEY_SUBJECT, subject);
			indexedMetadata.put(LuceneConstants.KEY_ALLEGATI, nomeAllegati);
			Map<String, Object> unindexedMetadata = new HashMap<String, Object>();
			unindexedMetadata.put(LuceneConstants.ID_CASELLA, idCasella);
			luceneWriter.setCategory(SearchCategory.EMAIL);
			luceneWriter.addDocument(subject, LuceneConstants.MIMETYPE_EMAIL, id, indexedMetadata, unindexedMetadata, null);
		} catch (Exception e) {
			log.error("Impossibile indicizzare la email: " + message.getMail().getIdEmail(), e);
			throw new AurigaMailBusinessException("Impossibile indicizzare la email", e);
		}
	}

	public LuceneIndexWriter getLuceneWriter() {
		return luceneWriter;
	}

	public void setLuceneWriter(LuceneIndexWriter luceneWriter) {
		this.luceneWriter = luceneWriter;
	}

}
