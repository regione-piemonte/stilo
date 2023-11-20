/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.prosa.ProsaAllegato;
import it.eng.document.function.bean.prosa.ProsaAssegnazione;
import it.eng.document.function.bean.prosa.ProsaDatiProtocollo;
import it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato;
import it.eng.document.function.bean.prosa.ProsaImmagineDocumentale;
import it.eng.document.function.bean.prosa.ProsaMittenteDestinatario;
import it.eng.document.function.bean.prosa.ProsaRicerca;
import it.eng.document.function.bean.prosa.ProsaVoceTitolario;
import it.eng.utility.client.prosa.Allegato;
import it.eng.utility.client.prosa.Assegnazione;
import it.eng.utility.client.prosa.DatiProtocollo;
import it.eng.utility.client.prosa.DocumentoProtocollato;
import it.eng.utility.client.prosa.ImmagineDocumentale;
import it.eng.utility.client.prosa.MittenteDestinatario;
import it.eng.utility.client.prosa.Ricerca;
import it.eng.utility.client.prosa.VoceTitolario;

public class ProtocollazioneProsaMapperImpl {

	/* ================ DA BEAN AURIGA A BEAN AXIS ====================================================================================== */

	public DocumentoProtocollato prosaDocumentoProtocollatoToDocumentoProtocollato(ProsaDocumentoProtocollato input) {
		final DocumentoProtocollato bean = new DocumentoProtocollato();
		bean.setContenuto(input.getContenuto());
		bean.setDataDocumento(input.getDataDocumento());
		bean.setDataProtocollo(input.getDataProtocollo());
		bean.setId(input.getId());
		bean.setIsContenuto(input.isContenuto());
		bean.setMittentiDestinatari(prosaMittentiDestinatariToMittentiDestinatari(input.getMittentiDestinatari()));
		bean.setNomeFileContenuto(input.getNomeFileContenuto());
		bean.setNote(input.getNote());
		bean.setNumeroProtocollo(input.getNumeroProtocollo());
		bean.setNumeroProtocolloEsterno(input.getNumeroProtocolloEsterno());
		bean.setOggetto(input.getOggetto());
		bean.setRegistro(input.getRegistro());
		bean.setTimbro(input.isTimbro());
		bean.setTipoProtocollo(input.getTipoProtocollo());
		bean.setUfficioCompetente(input.getUfficioCompetente());
		bean.setVociTitolario(input.getVociTitolario());
		return bean;
	}

	public Assegnazione prosaAssegnazioneToAssegnazione(ProsaAssegnazione input) {
		final Assegnazione bean = new Assegnazione();
		bean.setCodiceAssegnazione(input.getCodiceAssegnazione());
		bean.setDataScadenza(input.getDataScadenza());
		bean.setIdProtocollo(input.getIdProtocollo());
		bean.setNote(input.getNote());
		bean.setUfficioAssegnatario(input.getUfficioAssegnatario());
		bean.setUtenteAssegnatario(input.getUtenteAssegnatario());
		return bean;
	}

	public Allegato prosaAllegatoToAllegato(ProsaAllegato input) {
		final Allegato bean = new Allegato();
		bean.setCollocazione(input.getCollocazione());
		bean.setContenuto(input.getContenuto());
		bean.setDescrizione(input.getDescrizione());
		bean.setId(input.getId());
		bean.setIdProfilo(input.getIdProfilo());
		bean.setNomeFile(input.getNomeFile());
		return bean;
	}

	public VoceTitolario prosaVoceTitolarioToVoceTitolario(ProsaVoceTitolario input) {
		final VoceTitolario bean = new VoceTitolario();
		bean.setCodice(input.getCodice());
		bean.setDescrizione(input.getDescrizione());
		bean.setId(input.getId());
		return bean;
	}

	public ImmagineDocumentale prosaImmagineDocumentaleToImmagineDocumentale(ProsaImmagineDocumentale input) {
		final ImmagineDocumentale bean = new ImmagineDocumentale();
		bean.setContenuto(input.getContenuto());
		bean.setId(input.getId());
		bean.setNomeFile(input.getNomeFile());
		return bean;
	}

	public Ricerca prosaRicercaToRicerca(ProsaRicerca input) {
		final Ricerca bean = new Ricerca();
		bean.setCodiceTitolario(input.getCodiceTitolario());
		bean.setDataDocumentoA(input.getDataDocumentoA());
		bean.setDataDocumentoDa(input.getDataDocumentoDa());
		bean.setDataProtocolloA(input.getDataProtocolloA());
		bean.setDataProtocolloDa(input.getDataProtocolloDa());
		bean.setMittDest(input.getMittDest());
		bean.setModalita(input.getModalita());
		bean.setNumeroA(input.getNumeroA());
		bean.setNumeroDa(input.getNumeroDa());
		bean.setOggetto(input.getOggetto());
		bean.setRegistro(input.getRegistro());
		bean.setUfficioCompetente(input.getUfficioCompetente());
		return bean;
	}

	public DatiProtocollo prosaDatiProtocolloToDatiProtocollo(ProsaDatiProtocollo input) {
		final DatiProtocollo bean = new DatiProtocollo();
		bean.setAoo(input.getAoo());
		bean.setCasellaemail(input.getCasellaEmail());
		bean.setIdProfilo(input.getIdProfilo());
		bean.setModalita(input.getModalita());
		bean.setRegistro(input.getRegistro());
		bean.setStampaTimbro(input.isStampaTimbro());
		return bean;
	}

	public MittenteDestinatario prosaMittenteDestinatarioToMittenteDestinatario(ProsaMittenteDestinatario input) {
		final MittenteDestinatario bean = new MittenteDestinatario();
		bean.setCitta(input.getCitta());
		bean.setCodiceMezzoSpedizione(input.getCodiceMezzoSpedizione());
		bean.setCodiceMittenteDestinatario(input.getCodiceMittenteDestinatario());
		bean.setCognome(input.getCognome());
		bean.setDenominazione(input.getDenominazione());
		bean.setEmail(input.getEmail());
		bean.setIndirizzo(input.getIndirizzo());
		bean.setInvioPC(input.getInvioPC());
		bean.setNome(input.getNome());
		bean.setTipo(input.getTipo());
		return bean;
	}

	public MittenteDestinatario[] prosaMittentiDestinatariToMittentiDestinatari(ProsaMittenteDestinatario[] input) {
		if (input == null)
			return null;
		final MittenteDestinatario[] beans = new MittenteDestinatario[input.length];
		for (int i = 0; i < input.length; i++) {
			beans[i] = prosaMittenteDestinatarioToMittenteDestinatario(input[i]);
		}
		return beans;
	}

	/* ================ DA BEAN AXIS A BEAN AURIGA ====================================================================================== */

	public ProsaDocumentoProtocollato documentoProtocollatoToProsaDocumentoProtocollato(DocumentoProtocollato input) {
		final ProsaDocumentoProtocollato bean = new ProsaDocumentoProtocollato();
		bean.setContenuto(input.getContenuto());
		bean.setContenuto(input.isIsContenuto());
		bean.setDataDocumento(input.getDataDocumento());
		bean.setDataProtocollo(input.getDataProtocollo());
		bean.setId(input.getId());
		bean.setMittentiDestinatari(mittentiDestinatariToProsaMittentiDestinatari(input.getMittentiDestinatari()));
		bean.setNomeFileContenuto(input.getNomeFileContenuto());
		bean.setNote(input.getNote());
		bean.setNumeroProtocollo(input.getNumeroProtocollo());
		bean.setNumeroProtocolloEsterno(input.getNumeroProtocolloEsterno());
		bean.setOggetto(input.getOggetto());
		bean.setRegistro(input.getRegistro());
		bean.setTimbro(input.isTimbro());
		bean.setTipoProtocollo(input.getTipoProtocollo());
		bean.setUfficioCompetente(input.getUfficioCompetente());
		bean.setVociTitolario(input.getVociTitolario());
		return bean;
	}

	public ProsaAssegnazione assegnazioneToProsaAssegnazione(Assegnazione input) {
		final ProsaAssegnazione bean = new ProsaAssegnazione();
		bean.setCodiceAssegnazione(input.getCodiceAssegnazione());
		bean.setDataScadenza(input.getDataScadenza());
		bean.setIdProtocollo(input.getIdProtocollo());
		bean.setNote(input.getNote());
		bean.setUfficioAssegnatario(input.getUfficioAssegnatario());
		bean.setUtenteAssegnatario(input.getUtenteAssegnatario());
		return bean;
	}

	public ProsaImmagineDocumentale immagineDocumentaleToProsaImmagineDocumentale(ImmagineDocumentale input) {
		final ProsaImmagineDocumentale bean = new ProsaImmagineDocumentale();
		bean.setContenuto(input.getContenuto());
		bean.setId(input.getId());
		bean.setNomeFile(input.getNomeFile());
		return bean;
	}

	public ProsaAllegato allegatoToProsaAllegato(Allegato input) {
		final ProsaAllegato bean = new ProsaAllegato();
		bean.setCollocazione(input.getCollocazione());
		bean.setContenuto(input.getContenuto());
		bean.setDescrizione(input.getDescrizione());
		bean.setId(input.getId());
		bean.setIdProfilo(input.getIdProfilo());
		bean.setNomeFile(input.getNomeFile());
		return bean;
	}

	private ProsaVoceTitolario voceTitolarioToProsaVoceTitolario(VoceTitolario input) {
		final ProsaVoceTitolario bean = new ProsaVoceTitolario();
		bean.setCodice(input.getCodice());
		bean.setDescrizione(input.getDescrizione());
		bean.setId(input.getId());
		return bean;
	}

	public ProsaMittenteDestinatario mittenteDestinatarioToProsaMittenteDestinatario(MittenteDestinatario input) {
		final ProsaMittenteDestinatario bean = new ProsaMittenteDestinatario();
		bean.setCitta(input.getCitta());
		bean.setCodiceMezzoSpedizione(input.getCodiceMezzoSpedizione());
		bean.setCodiceMittenteDestinatario(input.getCodiceMittenteDestinatario());
		bean.setCognome(input.getCognome());
		bean.setDenominazione(input.getDenominazione());
		bean.setEmail(input.getEmail());
		bean.setIndirizzo(input.getIndirizzo());
		bean.setInvioPC(input.getInvioPC());
		bean.setNome(input.getNome());
		bean.setTipo(input.getTipo());
		return bean;
	}

	public ProsaAllegato[] allegatiToProsaAllegati(Allegato[] input) {
		if (input == null)
			return null;
		final ProsaAllegato[] beans = new ProsaAllegato[input.length];
		for (int i = 0; i < input.length; i++) {
			beans[i] = allegatoToProsaAllegato(input[i]);
		}
		return beans;
	}

	public ProsaVoceTitolario[] vociTitolarioToProsaVociTitolario(VoceTitolario[] input) {
		if (input == null)
			return null;
		final ProsaVoceTitolario[] beans = new ProsaVoceTitolario[input.length];
		for (int i = 0; i < input.length; i++) {
			beans[i] = voceTitolarioToProsaVoceTitolario(input[i]);
		}
		return beans;
	}

	public ProsaDocumentoProtocollato[] documentiProtocollatiToProsaDocumentiProtocollati(DocumentoProtocollato[] input) {
		if (input == null)
			return null;
		final ProsaDocumentoProtocollato[] beans = new ProsaDocumentoProtocollato[input.length];
		for (int i = 0; i < input.length; i++) {
			beans[i] = documentoProtocollatoToProsaDocumentoProtocollato(input[i]);
		}
		return beans;
	}

	public ProsaMittenteDestinatario[] mittentiDestinatariToProsaMittentiDestinatari(MittenteDestinatario[] input) {
		if (input == null)
			return null;
		final ProsaMittenteDestinatario[] beans = new ProsaMittenteDestinatario[input.length];
		for (int i = 0; i < input.length; i++) {
			beans[i] = mittenteDestinatarioToProsaMittenteDestinatario(input[i]);
		}
		return beans;
	}

}// ProtocollazioneProsaMapperImpl
