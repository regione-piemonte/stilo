/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.bean.BytesResult;
import it.eng.bean.Result;
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

public interface ProtocollazioneProsa {

	BytesResult getContenutoAllegato(long idAllegato);

	Result<ProsaAllegato> rimuoviAllegato(long idAllegato);

	Result<String> inserisciContenutoDaDocumentale(Long idProfiloDa, Long idProfiloA, boolean stampaTimbro);

	Result<String> inserisciAllegatoDaFascicolo(Long idProfilo, ProsaAllegato prosaAllegato);

	Result<String> inserisciAllegatoZip(ProsaAllegato prosaAllegato);

	Result<String> protocollaProfilo(ProsaDatiProtocollo prosaDatiProtocollo);

	Result<ProsaDocumentoProtocollato> protocolla(ProsaDocumentoProtocollato prosaDocProtocollato);

	Result<ProsaAssegnazione> assegna(ProsaAssegnazione prosaAssegnazione);

	Result<ProsaImmagineDocumentale> recuperaContenuto(long idDoc);

	BytesResult getContenutoDocumento(long idDoc);

	Result<ProsaAllegato> inserisciAllegato(ProsaAllegato prosaAllegato);

	ProsaAllegatiResult getAllegati(long idProfilo);

	Result<ProsaAllegato> getAllegato(long idAllegato);

	ProsaVociTitolarioResult ricercaTitolarioPerCodiceDescrizione(ProsaVoceTitolario prosaVoceTitolario);

	Result<ProsaImmagineDocumentale> inserisciContenuto(ProsaImmagineDocumentale prosaImmagineDoc, boolean timbro);

	Result<String> testLogin();

	Result<String> testStato();

	ProsaDocumentiProtocollatiResult ricercaProtocolli(ProsaRicerca prosaRicerca);

}