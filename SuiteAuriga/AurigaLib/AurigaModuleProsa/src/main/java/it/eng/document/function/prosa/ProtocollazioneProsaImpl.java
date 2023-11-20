/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.bean.BytesResult;
import it.eng.bean.Result;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.prosa.ProsaAllegatiResult;
import it.eng.document.function.bean.prosa.ProsaAllegato;
import it.eng.document.function.bean.prosa.ProsaAssegnazione;
import it.eng.document.function.bean.prosa.ProsaDatiProtocollo;
import it.eng.document.function.bean.prosa.ProsaDocumentiProtocollatiResult;
import it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato;
import it.eng.document.function.bean.prosa.ProsaImmagineDocumentale;
import it.eng.document.function.bean.prosa.ProsaRicerca;
import it.eng.document.function.bean.prosa.ProsaVoceTitolario;
import it.eng.document.function.bean.prosa.ProsaVociTitolarioResult;
import it.eng.utility.client.prosa.Allegato;
import it.eng.utility.client.prosa.Assegnazione;
import it.eng.utility.client.prosa.DatiProtocollo;
import it.eng.utility.client.prosa.DocumentoProtocollato;
import it.eng.utility.client.prosa.ImmagineDocumentale;
import it.eng.utility.client.prosa.Ricerca;
import it.eng.utility.client.prosa.VoceTitolario;
import it.eng.utility.client.prosa.WSEsito;
import it.eng.utility.client.protocollo.ClientProsa;
import it.eng.utility.client.protocollo.data.Output;

@Service(name = "ProtocollazioneProsaImpl")
public class ProtocollazioneProsaImpl implements ProtocollazioneProsa {

	private static Logger logger = Logger.getLogger(ProtocollazioneProsaImpl.class);
	private static final ClientProsa client;
	private static final ProtocollazioneProsaMapperImpl mapper;

	static {
		client = new ClientProsa();
		try {
			client.init();
		} catch (Exception e) {
			logger.error("Fallita inizializzazione del client Prosa.");
			throw new ExceptionInInitializerError(e);
		}
		mapper = new ProtocollazioneProsaMapperImpl();
	}

	@Override
	@Operation(name = "getContenutoAllegato")
	public BytesResult getContenutoAllegato(long idAllegato) {
		final Output<byte[]> out = client.getContenutoAllegato(idAllegato);
		final byte[] payload = out.getResult();
		return createBytesResult(out, payload);
	}

	@Override
	@Operation(name = "rimuoviAllegato")
	public Result<ProsaAllegato> rimuoviAllegato(long idAllegato) {
		final Output<Allegato> out = client.rimuoviAllegato(idAllegato);
		final ProsaAllegato payload = mapper.allegatoToProsaAllegato(out.getResult());
		return createProsaResult(out, payload);
	}

	// @Operation(name = "estraiFlussiLavorazioneDocumento")
	// public Vector estraiFlussiLavorazioneDocumento(long in1) {
	// throw new NotImplementedException("TODO");
	// }

	@Override
	@Operation(name = "inserisciContenutoDaDocumentale")
	public Result<String> inserisciContenutoDaDocumentale(Long idProfiloDa, Long idProfiloA, boolean stampaTimbro) {
		final Output<WSEsito> out = client.inserisciContenutoDaDocumentale(idProfiloDa, idProfiloA, stampaTimbro);
		final String payload = out.getResult() != null ? out.getResult().getCodiceEsito() : null;
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "inserisciAllegatoDaFascicolo")
	public Result<String> inserisciAllegatoDaFascicolo(Long idProfilo, ProsaAllegato prosaAllegato) {
		final Allegato allegato = mapper.prosaAllegatoToAllegato(prosaAllegato);
		final Output<WSEsito> out = client.inserisciAllegatoDaFascicolo(idProfilo, allegato);
		final String payload = out.getResult() != null ? out.getResult().getCodiceEsito() : null;
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "inserisciAllegatoZip")
	public Result<String> inserisciAllegatoZip(ProsaAllegato prosaAllegato) {
		final Allegato allegato = mapper.prosaAllegatoToAllegato(prosaAllegato);
		final Output<WSEsito> out = client.inserisciAllegatoZip(allegato);
		final String payload = out.getResult() != null ? out.getResult().getCodiceEsito() : null;
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "protocollaProfilo")
	public Result<String> protocollaProfilo(ProsaDatiProtocollo prosaDatiProtocollo) {
		final DatiProtocollo datiProtocollo = mapper.prosaDatiProtocolloToDatiProtocollo(prosaDatiProtocollo);
		final Output<WSEsito> out = client.protocollaProfilo(datiProtocollo);
		final String payload = out.getResult() != null ? out.getResult().getCodiceEsito() : null;
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "protocolla")
	public Result<ProsaDocumentoProtocollato> protocolla(ProsaDocumentoProtocollato prosaDocProtocollato) {
		final DocumentoProtocollato docProt = mapper.prosaDocumentoProtocollatoToDocumentoProtocollato(prosaDocProtocollato);
		final Output<DocumentoProtocollato> out = client.protocolla(docProt);
		final ProsaDocumentoProtocollato payload = mapper.documentoProtocollatoToProsaDocumentoProtocollato(out.getResult());
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "assegna")
	public Result<ProsaAssegnazione> assegna(ProsaAssegnazione prosaAssegnazione) {
		final Assegnazione assegnazione = mapper.prosaAssegnazioneToAssegnazione(prosaAssegnazione);
		final Output<Assegnazione> out = client.assegna(assegnazione);
		final ProsaAssegnazione payload = mapper.assegnazioneToProsaAssegnazione(out.getResult());
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "recuperaContenuto")
	public Result<ProsaImmagineDocumentale> recuperaContenuto(long idDoc) {
		final Output<ImmagineDocumentale> out = client.recuperaContenuto(idDoc);
		final ProsaImmagineDocumentale payload = mapper.immagineDocumentaleToProsaImmagineDocumentale(out.getResult());
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "getContenutoDocumento")
	public BytesResult getContenutoDocumento(long idDoc) {
		final Output<byte[]> out = client.getContenutoDocumento(idDoc);
		final byte[] payload = out.getResult();
		return createBytesResult(out, payload);
	}

	@Override
	@Operation(name = "inserisciAllegato")
	public Result<ProsaAllegato> inserisciAllegato(ProsaAllegato prosaAllegato) {
		final Allegato allegato = mapper.prosaAllegatoToAllegato(prosaAllegato);
		final Output<Allegato> out = client.inserisciAllegato(allegato);
		final ProsaAllegato payload = mapper.allegatoToProsaAllegato(out.getResult());
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "getAllegati")
	public ProsaAllegatiResult getAllegati(long idProfilo) {
		final Output<Allegato[]> out = client.getAllegati(idProfilo);
		final ProsaAllegato[] payload = mapper.allegatiToProsaAllegati(out.getResult());
		final ProsaAllegatiResult result = new ProsaAllegatiResult();
		result.setMessage(out.getMessaggio());
		result.setOk(out.isOk());
		result.setTimeout(out.isTimeout());
		result.setRispostaNonRicevuta(out.isRispostaNonRicevuta());
		result.setPayload(payload);
		return result;
	}

	@Override
	@Operation(name = "getAllegato")
	public Result<ProsaAllegato> getAllegato(long idAllegato) {
		final Output<Allegato> out = client.getAllegato(idAllegato);
		final ProsaAllegato payload = mapper.allegatoToProsaAllegato(out.getResult());
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "ricercaTitolarioPerCodiceDescrizione")
	public ProsaVociTitolarioResult ricercaTitolarioPerCodiceDescrizione(ProsaVoceTitolario prosaVoceTitolario) {
		final VoceTitolario voceTitolario = mapper.prosaVoceTitolarioToVoceTitolario(prosaVoceTitolario);
		final Output<VoceTitolario[]> out = client.ricercaTitolarioPerCodiceDescrizione(voceTitolario);
		final ProsaVoceTitolario[] payload = mapper.vociTitolarioToProsaVociTitolario(out.getResult());
		final ProsaVociTitolarioResult result = new ProsaVociTitolarioResult();
		result.setMessage(out.getMessaggio());
		result.setOk(out.isOk());
		result.setTimeout(out.isTimeout());
		result.setRispostaNonRicevuta(out.isRispostaNonRicevuta());
		result.setPayload(payload);
		return result;
	}

	@Override
	@Operation(name = "inserisciContenuto")
	public Result<ProsaImmagineDocumentale> inserisciContenuto(ProsaImmagineDocumentale prosaImmagineDoc, boolean timbro) {
		final ImmagineDocumentale immagineDocumentale = mapper.prosaImmagineDocumentaleToImmagineDocumentale(prosaImmagineDoc);
		final Output<ImmagineDocumentale> out = client.inserisciContenuto(immagineDocumentale, timbro);
		final ProsaImmagineDocumentale payload = mapper.immagineDocumentaleToProsaImmagineDocumentale(out.getResult());
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "testLogin")
	public Result<String> testLogin() {
		final Output<WSEsito> out = client.testLogin();
		final String payload = out.getResult() != null ? out.getResult().getCodiceEsito() : null;
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "testStato")
	public Result<String> testStato() {
		final Output<WSEsito> out = client.testStato();
		final String payload = out.getResult() != null ? out.getResult().getCodiceEsito() : null;
		return createProsaResult(out, payload);
	}

	@Override
	@Operation(name = "ricercaProtocolli")
	public ProsaDocumentiProtocollatiResult ricercaProtocolli(ProsaRicerca prosaRicerca) {
		final Ricerca ricerca = mapper.prosaRicercaToRicerca(prosaRicerca);
		final Output<DocumentoProtocollato[]> out = client.ricercaProtocolli(ricerca);
		final ProsaDocumentoProtocollato[] payload = mapper.documentiProtocollatiToProsaDocumentiProtocollati(out.getResult());
		final ProsaDocumentiProtocollatiResult result = new ProsaDocumentiProtocollatiResult();
		result.setMessage(out.getMessaggio());
		result.setOk(out.isOk());
		result.setTimeout(out.isTimeout());
		result.setRispostaNonRicevuta(out.isRispostaNonRicevuta());
		result.setPayload(payload);
		return result;
	}

	private <S, T> Result<T> createProsaResult(final Output<S> out, final T payload) {
		final Result<T> result = new Result<>();
		result.setMessage(out.getMessaggio());
		result.setOk(out.isOk());
		result.setTimeout(out.isTimeout());
		result.setRispostaNonRicevuta(out.isRispostaNonRicevuta());
		result.setPayload(payload);
		return result;
	}

	private BytesResult createBytesResult(final Output<?> out, final byte[] payload) {
		final BytesResult result = new BytesResult();
		result.setMessage(out.getMessaggio());
		result.setOk(out.isOk());
		result.setTimeout(out.isTimeout());
		result.setRispostaNonRicevuta(out.isRispostaNonRicevuta());
		result.setPayload(payload);
		return result;
	}

}
