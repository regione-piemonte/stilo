/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.DocumentoXmlOutBean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentoRecuperatoBean extends DocumentoXmlOutBean {

	private static final long serialVersionUID = -635906002502515793L;

	private BigDecimal idUD;
	
	public BigDecimal getIdUD() {
		return idUD;
	}
	
	public void setIdUD(BigDecimal idUD) {
		this.idUD = idUD;
	}

	@Override
	public String toString() {
		return String
				.format("DocumentoRecuperatoBean [idUD=%s, getFlgTipoProv()=%s, getOggetto()=%s, getLivelloRiservatezza()=%s, getTermineRiservatezza()=%s, getPriorita()=%s, getDataArrivo()=%s, getMessoTrasmissione()=%s, getDtRaccomandata()=%s, getNroRaccomandata()=%s, getNote()=%s, getTipoDocumento()=%s, getDataStesura()=%s, getMittenti()=%s, getDestinatari()=%s, getAssegnatari()=%s, getClassifichefascicoli()=%s, getNomeTipoDocumento()=%s, getFilePrimario()=%s, getFlgNoPubblPrimario()=%s, getAllegati()=%s, getCollocazioneFisica()=%s, getRifDocRicevuto()=%s, getEstremiRegDocRicevuto()=%s, getDtDocRicevuto()=%s, getEstremiRegistrazione()=%s, getRegEmergenza()=%s, getDocCollegato()=%s, getAbilAssegnazioneSmistamento()=%s, getAbilCondivisione()=%s, getAbilClassificazioneFascicolazione()=%s, getAbilModificaDatiRegistrazione()=%s, getAbilModificaDati()=%s, getAbilAggiuntaFile()=%s, getEmailProv()=%s, getAbilProtocollazioneUscita()=%s, getAbilProtocollazioneInterna()=%s, getIdDocPrimario()=%s, getAbilInvioPEO()=%s, getAbilInvioPEC()=%s, getDestInvioCC()=%s, getIdEmailArrivo()=%s, getEmailInviataFlgPEC()=%s, getEmailInviataFlgPEO()=%s, getEmailArrivoInteroperabile()=%s, getFlgInteroperabilita()=%s, getIdUserCtrlAmmissib()=%s, getAnnoDocRicevuto()=%s, getAbilRichAnnullamentoReg()=%s, getAbilEliminaRichAnnullamentoReg()=%s, getConDatiAnnullati()=%s, getListaDatiAnnullati()=%s, getAbilPresaInCarico()=%s, getAbilRestituzione()=%s, getAbilModificaRichAnnullamentoReg()=%s, getAbilAnnullamentoReg()=%s, getAbilInvioConferma()=%s, getAbilInvioAggiornamento()=%s, getAbilInvioAnnullamento()=%s, getIdUserConfermaAssegnazione()=%s, getDesUserConfermaAssegnazione()=%s, getUsernameConfermaAssegnazione()=%s, getRicEccezioniInterop()=%s, getRicAggiornamentiInterop()=%s, getRicAnnullamentiInterop()=%s, getPresenzaDocCollegati()=%s, getEstremiTrasm()=%s, getCodStatoDett()=%s, getFolderCustom()=%s, getAbilArchiviazione()=%s, getIdProcess()=%s, getEstremiProcess()=%s, getTipoRegNumerazioneSecondaria()=%s, getSiglaRegNumerazioneSecondaria()=%s, getAnnoRegNumerazioneSecondaria()=%s, getNumeroRegNumerazioneSecondaria()=%s, getDataRegistrazioneNumerazioneSecondaria()=%s, getDataAvvioIterAtto()=%s, getFunzionarioIstruttoreAtto()=%s, getResponsabileAtto()=%s, getDirettoreFirmatarioAtto()=%s, getDataFirmaAtto()=%s, getFunzionarioFirmaAtto()=%s, getDataPubblicazione()=%s, getLogIterAtto()=%s, getAbilInvioInConservazione()=%s, getStatoConservazione()=%s, getDataInvioInConservazione()=%s, getErroreInInvio()=%s, getContraenti()=%s, getRowidDoc()=%s, getFlgRichParereRevisori()=%s, getSceltaIterPropostaDD()=%s, getInviataMailInteroperabile()=%s, getClass()=%s, hashCode()=%s, toString()=%s]",
						idUD, getFlgTipoProv(), getOggetto(), getLivelloRiservatezza(), getTermineRiservatezza(), getPriorita(),
						getDataArrivo(), getMessoTrasmissione(), getDtRaccomandata(), getNroRaccomandata(), getNote(), getTipoDocumento(),
						getDataStesura(), getMittenti(), getDestinatari(), getAssegnatari(), getClassifichefascicoli(),
						getNomeTipoDocumento(), getFilePrimario(), getFlgNoPubblPrimario(), getAllegati(), getCollocazioneFisica(),
						getRifDocRicevuto(), getEstremiRegDocRicevuto(), getDtDocRicevuto(), getEstremiRegistrazione(), getRegEmergenza(),
						getDocCollegato(), getAbilAssegnazioneSmistamento(), getAbilCondivisione(), getAbilClassificazioneFascicolazione(),
						getAbilModificaDatiRegistrazione(), getAbilModificaDati(), getAbilAggiuntaFile(), getEmailProv(),
						getAbilProtocollazioneUscita(), getAbilProtocollazioneInterna(), getIdDocPrimario(), getAbilInvioPEO(),
						getAbilInvioPEC(), getDestInvioCC(), getIdEmailArrivo(), getEmailInviataFlgPEC(), getEmailInviataFlgPEO(),
						getEmailArrivoInteroperabile(), getFlgInteroperabilita(), getIdUserCtrlAmmissib(), getAnnoDocRicevuto(),
						getAbilRichAnnullamentoReg(), getAbilEliminaRichAnnullamentoReg(), getConDatiAnnullati(), getListaDatiAnnullati(),
						getAbilPresaInCarico(), getAbilRestituzione(), getAbilModificaRichAnnullamentoReg(), getAbilAnnullamentoReg(),
						getAbilInvioConferma(), getAbilInvioAggiornamento(), getAbilInvioAnnullamento(), getIdUserConfermaAssegnazione(),
						getDesUserConfermaAssegnazione(), getUsernameConfermaAssegnazione(), getRicEccezioniInterop(),
						getRicAggiornamentiInterop(), getRicAnnullamentiInterop(), getPresenzaDocCollegati(), getEstremiTrasm(),
						getCodStatoDett(), getFolderCustom(), getAbilArchiviazione(), getIdProcess(), getEstremiProcess(),
						getTipoRegNumerazioneSecondaria(), getSiglaRegNumerazioneSecondaria(), getAnnoRegNumerazioneSecondaria(),
						getNumeroRegNumerazioneSecondaria(), getDataRegistrazioneNumerazioneSecondaria(), getDataAvvioIterAtto(),
						getFunzionarioIstruttoreAtto(), getResponsabileAtto(), getDirettoreFirmatarioAtto(), getDataFirmaAtto(),
						getFunzionarioFirmaAtto(), getDataPubblicazione(), getLogIterAtto(), getAbilInvioInConservazione(),
						getStatoConservazione(), getDataInvioInConservazione(), getErroreInInvio(), getContraenti(), getRowidDoc(),
						getFlgRichParereRevisori(), getSceltaIterPropostaDD(), getInviataMailInteroperabile(), getClass(), hashCode(),
						super.toString());
	}
	
	
}
