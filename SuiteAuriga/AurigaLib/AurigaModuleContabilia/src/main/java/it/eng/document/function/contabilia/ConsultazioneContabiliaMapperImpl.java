/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.csi.siac.integ.data._1.Accertamento;
import it.csi.siac.integ.data._1.Capitolo;
import it.csi.siac.integ.data._1.Categoria;
import it.csi.siac.integ.data._1.ClasseSoggettoStilo;
import it.csi.siac.integ.data._1.ClassificatoreGenerico;
import it.csi.siac.integ.data._1.Ente;
import it.csi.siac.integ.data._1.Errore;
import it.csi.siac.integ.data._1.Esito;
import it.csi.siac.integ.data._1.Impegno;
import it.csi.siac.integ.data._1.Macroaggregato;
import it.csi.siac.integ.data._1.Messaggio;
import it.csi.siac.integ.data._1.Missione;
import it.csi.siac.integ.data._1.MovimentoGestioneStilo;
import it.csi.siac.integ.data._1.PianoDeiContiFinanziario;
import it.csi.siac.integ.data._1.ProgettoStilo;
import it.csi.siac.integ.data._1.Programma;
import it.csi.siac.integ.data._1.Provvedimento;
import it.csi.siac.integ.data._1.SoggettoStilo;
import it.csi.siac.integ.data._1.Stato;
import it.csi.siac.integ.data._1.StrutturaAmministrativa;
import it.csi.siac.integ.data._1.SubAccertamento;
import it.csi.siac.integ.data._1.SubImpegno;
import it.csi.siac.integ.data._1.TipoDebitoSiopeStilo;
import it.csi.siac.integ.data._1.TipoFinanziamento;
import it.csi.siac.integ.data._1.TipoFondo;
import it.csi.siac.integ.data._1.Tipologia;
import it.csi.siac.integ.data._1.Titolo;
import it.csi.siac.integ.data._1.VincoliStilo;
import it.csi.siac.integ.data._1.VincoloImpegnoStilo;
import it.csi.siac.ricerche.svc._1.RicercaAccertamentoResponse;
import it.csi.siac.ricerche.svc._1.RicercaImpegnoResponse;
import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStiloResponse;
import it.eng.document.function.bean.ContabiliaAccertamento;
import it.eng.document.function.bean.ContabiliaCapitolo;
import it.eng.document.function.bean.ContabiliaCategoria;
import it.eng.document.function.bean.ContabiliaClasseSoggettoStilo;
import it.eng.document.function.bean.ContabiliaClassificatoreGenerico;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiResponse;
import it.eng.document.function.bean.ContabiliaEnte;
import it.eng.document.function.bean.ContabiliaErrore;
import it.eng.document.function.bean.ContabiliaEsito;
import it.eng.document.function.bean.ContabiliaImpegno;
import it.eng.document.function.bean.ContabiliaMacroaggregato;
import it.eng.document.function.bean.ContabiliaMessaggio;
import it.eng.document.function.bean.ContabiliaMissione;
import it.eng.document.function.bean.ContabiliaMovimentoGestioneStilo;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento;
import it.eng.document.function.bean.ContabiliaOutputRicercaImpegno;
import it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione;
import it.eng.document.function.bean.ContabiliaPianoDeiContiFinanziario;
import it.eng.document.function.bean.ContabiliaProgettoStilo;
import it.eng.document.function.bean.ContabiliaProgramma;
import it.eng.document.function.bean.ContabiliaProvvedimento;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoRequest;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoResponse;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoRequest;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoResponse;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloRequest;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloResponse;
import it.eng.document.function.bean.ContabiliaSoggettoStilo;
import it.eng.document.function.bean.ContabiliaStato;
import it.eng.document.function.bean.ContabiliaStrutturaAmministrativa;
import it.eng.document.function.bean.ContabiliaSubAccertamento;
import it.eng.document.function.bean.ContabiliaSubImpegno;
import it.eng.document.function.bean.ContabiliaTipoDebitoSiopeStilo;
import it.eng.document.function.bean.ContabiliaTipoFinanziamento;
import it.eng.document.function.bean.ContabiliaTipoFondo;
import it.eng.document.function.bean.ContabiliaTipologia;
import it.eng.document.function.bean.ContabiliaTitolo;
import it.eng.document.function.bean.ContabiliaVincoliStilo;
import it.eng.document.function.bean.ContabiliaVincoloImpegnoStilo;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;
import it.eng.utility.client.contabilia.movimenti.data.OutputRicercaMovimentoGestione;
import it.eng.utility.client.contabilia.movimenti.data.RicercaMovimentoGestioneStiloRequest;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaAccertamento;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaImpegno;
import it.eng.utility.client.contabilia.ricerche.data.RicercaAccertamentoRequest;
import it.eng.utility.client.contabilia.ricerche.data.RicercaImpegnoRequest;

public class ConsultazioneContabiliaMapperImpl implements ConsultazioneContabiliaMapper {
	
	@Override
	public RicercaAccertamentoRequest fromContabiliaRicercaAccertamento(ContabiliaRicercaAccertamentoRequest source) {
		if (source == null) {
			return null;
		}
		
		RicercaAccertamentoRequest result = new RicercaAccertamentoRequest();
		result.setAnnoAccertamento(source.getAnnoAccertamento());
		result.setNumeroAccertamento(source.getNumeroAccertamento());
		result.setAnnoProvvedimento(source.getAnnoProvvedimento());
		result.setCodiceStruttura(source.getCodiceStruttura());
		result.setCodiceTipoProvvedimento(source.getCodiceTipoProvvedimento());
		result.setCodiceTipoStruttura(source.getCodiceTipoStruttura());
		result.setNumeroProvvedimento(source.getNumeroProvvedimento());
		result.setNumeroElementiPerPagina(source.getNumeroElementiPerPagina());
		result.setNumeroPagina(source.getNumeroPagina());
		result.setAnnoBilancio(source.getAnnoBilancio());
		result.setCodiceEnte(source.getCodiceEnte());
		result.setCodiceFruitore(source.getCodiceFruitore());
		result.setIdSpAoo(source.getIdSpAoo());
		
		return result;
	}
	
	@Override
	public RicercaImpegnoRequest fromContabiliaRicercaImpegno(ContabiliaRicercaImpegnoRequest source) {
		if (source == null) {
			return null;
		}
		
		RicercaImpegnoRequest result = new RicercaImpegnoRequest();
		result.setAnnoImpegno(source.getAnnoImpegno());
		result.setNumeroImpegno(source.getNumeroImpegno());
		result.setAnnoProvvedimento(source.getAnnoProvvedimento());
		result.setCodiceStruttura(source.getCodiceStruttura());
		result.setCodiceTipoProvvedimento(source.getCodiceTipoProvvedimento());
		result.setCodiceTipoStruttura(source.getCodiceTipoStruttura());
		result.setNumeroProvvedimento(source.getNumeroProvvedimento());
		result.setNumeroElementiPerPagina(source.getNumeroElementiPerPagina());
		result.setNumeroPagina(source.getNumeroPagina());
		result.setAnnoBilancio(source.getAnnoBilancio());
		result.setCodiceEnte(source.getCodiceEnte());
		result.setCodiceFruitore(source.getCodiceFruitore());
		result.setIdSpAoo(source.getIdSpAoo());
		
		return result;
	}
	
	@Override
	public RicercaMovimentoGestioneStiloRequest fromContabiliaRicercaMovimentoGestione(ContabiliaRicercaMovimentoGestioneStiloRequest source) {
		if (source == null) {
			return null;
		}
		
		RicercaMovimentoGestioneStiloRequest result = new RicercaMovimentoGestioneStiloRequest();
		result.setAnnoProvvedimento(source.getAnnoProvvedimento());
		result.setCodiceStruttura(source.getCodiceStruttura());
		result.setCodiceTipoProvvedimento(source.getCodiceTipoProvvedimento());
		result.setCodiceTipoStruttura(source.getCodiceTipoStruttura());
		result.setNumeroProvvedimento(source.getNumeroProvvedimento());
		result.setAnnoBilancio(source.getAnnoBilancio());
		result.setCodiceEnte(source.getCodiceEnte());
		result.setCodiceFruitore(source.getCodiceFruitore());
		result.setIdSpAoo(source.getIdSpAoo());
		
		return result;
	}
	
	@Override
	public ElaboraAttiAmministrativiRequest fromContabiliaElaboraAttiAmministrativi(ContabiliaElaboraAttiAmministrativiRequest source) {
		if (source == null) {
			return null;
		}
		
		ElaboraAttiAmministrativiRequest result = new ElaboraAttiAmministrativiRequest();
		result.setAnnoAttoProposta(source.getAnnoAttoProposta());
		result.setNumeroAttoProposta(source.getNumeroAttoProposta());
		result.setTipoAttoProposta(source.getTipoAttoProposta());
		result.setAnnoAttoDefinitivo(source.getAnnoAttoDefinitivo());
		result.setNumeroAttoDefinitivo(source.getNumeroAttoDefinitivo());
		result.setTipoAttoDefinitivo(source.getTipoAttoDefinitivo());
		result.setCentroResponsabilita(source.getCentroResponsabilita());
		result.setCentroCosto(source.getCentroCosto());
		result.setDataCreazione(source.getDataCreazione());
		result.setDataProposta(source.getDataProposta());
		result.setDataApprovazione(source.getDataApprovazione());
		result.setDataEsecutivita(source.getDataEsecutivita());
		result.setOggetto(source.getOggetto());
		result.setNote(source.getNote());
		result.setIdentificativo(source.getIdentificativo());
		result.setDirigenteResponsabile(source.getDirigenteResponsabile());
		result.setTrasparenza(source.getTrasparenza());
		result.setIdSpAoo(source.getIdSpAoo());
		
		return result;
	}
	
	@Override
	public ContabiliaOutputRicercaAccertamento outputRicercaAccertamentoToContabiliaOutputRicercaAccertamento(OutputRicercaAccertamento source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaOutputRicercaAccertamento result = new ContabiliaOutputRicercaAccertamento();
		result.setEntry(entryToContabiliaRicercaAccertamentoResponse(source.getEntry()));
		result.setOk(source.isOk());
		result.setMessaggio(source.getMessaggio());
		result.setTimeout(source.isTimeout());
		result.setRispostaNonRicevuta(source.isRispostaNonRicevuta());
		
		return result;
	}
	
	@Override
	public ContabiliaRicercaAccertamentoResponse entryToContabiliaRicercaAccertamentoResponse(RicercaAccertamentoResponse source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaRicercaAccertamentoResponse result = new ContabiliaRicercaAccertamentoResponse();
		result.setTotaleRisultati((source.getTotaleRisultati() == null) ? 0 : source.getTotaleRisultati());
		result.setEnte(enteToContabiliaEnte(source.getEnte()));
		result.setErrori(getListContabiliaErrore(source.getErrori()));
		result.setEsito(esitoToContabiliaEsito(source.getEsito()));
		result.setMessaggi(getListContabiliaMessaggio(source.getMessaggi()));
		result.setAccertamenti(getListContabiliaAccertamento(source.getAccertamenti()));
		
		return result;
	}
	
	@Override
	public ContabiliaOutputRicercaImpegno outputRicercaImpegnoToContabiliaOutputRicercaImpegno(OutputRicercaImpegno source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaOutputRicercaImpegno result = new ContabiliaOutputRicercaImpegno();
		result.setEntry(entryToContabiliaRicercaImpegnoResponse(source.getEntry()));
		result.setOk(source.isOk());
		result.setMessaggio(source.getMessaggio());
		result.setTimeout(source.isTimeout());
		result.setRispostaNonRicevuta(source.isRispostaNonRicevuta());
		
		return result;
	}
	
	@Override
	public ContabiliaRicercaImpegnoResponse entryToContabiliaRicercaImpegnoResponse(RicercaImpegnoResponse source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaRicercaImpegnoResponse result = new ContabiliaRicercaImpegnoResponse();
		result.setTotaleRisultati((source.getTotaleRisultati() == null) ? 0 : source.getTotaleRisultati());
		result.setEnte(enteToContabiliaEnte(source.getEnte()));
		result.setErrori(getListContabiliaErrore(source.getErrori()));
		result.setEsito(esitoToContabiliaEsito(source.getEsito()));
		result.setMessaggi(getListContabiliaMessaggio(source.getMessaggi()));
		result.setImpegni(getListContabiliaImpegno(source.getImpegni()));
		
		return result;
	}
	
	@Override
	public ContabiliaOutputRicercaMovimentoGestione outputRicercaMovimentoGestioneToContabiliaOutputRicercaMovimentoGestione(OutputRicercaMovimentoGestione source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaOutputRicercaMovimentoGestione result = new ContabiliaOutputRicercaMovimentoGestione();
		result.setEntry(entryToContabiliaRicercaMovimentoGestioneStiloResponse(source.getEntry()));
		result.setTotaleRisultati(source.getTotaleRisultati());
		result.setOk(source.isOk());
		result.setMessaggio(source.getMessaggio());
		result.setTimeout(source.isTimeout());
		result.setRispostaNonRicevuta(source.isRispostaNonRicevuta());
		
		return result;
	}
	
	@Override
	public ContabiliaRicercaMovimentoGestioneStiloResponse entryToContabiliaRicercaMovimentoGestioneStiloResponse(RicercaMovimentoGestioneStiloResponse source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaRicercaMovimentoGestioneStiloResponse result = new ContabiliaRicercaMovimentoGestioneStiloResponse();
		result.setEnte(enteToContabiliaEnte(source.getEnte()));
		result.setErrori(getListContabiliaErrore(source.getErrori()));
		result.setEsito(esitoToContabiliaEsito(source.getEsito()));
		result.setMessaggi(getListContabiliaMessaggio(source.getMessaggi()));
		result.setMovimentiGestione(getListContabiliaMovimentoGestioneStilo(source.getMovimentiGestione()));
		
		return result;
	}
	
	@Override
	public ContabiliaOutputElaboraAttiAmministrativi outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(OutputElaboraAttiAmministrativi source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaOutputElaboraAttiAmministrativi result = new ContabiliaOutputElaboraAttiAmministrativi();
		result.setEntry(entryToContabiliaElaboraAttiAmministrativiResponse(source.getEntry()));
		
		return result;
	}
	
	@Override
	public ContabiliaElaboraAttiAmministrativiResponse entryToContabiliaElaboraAttiAmministrativiResponse(ElaboraAttiAmministrativiResponse source) {
		if (source == null) {
			return null;
		}
		
		ContabiliaElaboraAttiAmministrativiResponse result = new ContabiliaElaboraAttiAmministrativiResponse();
		result.setResponseElaborazione(source.isResponseElaborazione());
		result.setEsitoElaborazione(source.getEsitoElaborazione());
		result.setCodiceElaborazione(source.getCodiceElaborazione());
		result.setMessaggioElaborazione(source.getMessaggioElaborazione());
		
		return result;
	}
	
	private ContabiliaEnte enteToContabiliaEnte(Ente source) {
		ContabiliaEnte result = new ContabiliaEnte();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private List<ContabiliaErrore> getListContabiliaErrore(List<Errore> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaErrore> result = new ArrayList<ContabiliaErrore>(listSize);
		if (listSize > 0) {
			for (Errore item : list) {
				result.add(erroreToContabiliaErrore(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaErrore erroreToContabiliaErrore(Errore source) {
		ContabiliaErrore result = new ContabiliaErrore();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaStato statoToContabiliaStato(Stato source) {
		ContabiliaStato result = new ContabiliaStato();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
		}
		
		return result;
	}
	
	private ContabiliaEsito esitoToContabiliaEsito(Esito source) {
		if (source == null) {
			return null;
		}
		
		if ("FALLIMENTO".equals(source.FALLIMENTO)) {
			return ContabiliaEsito.FALLIMENTO;
		}
		else {
			return ContabiliaEsito.SUCCESSO;
		}
	}
	
	private List<ContabiliaMessaggio> getListContabiliaMessaggio(List<Messaggio> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaMessaggio> result = new ArrayList<ContabiliaMessaggio>(listSize);
		if (listSize > 0) {
			for (Messaggio item : list) {
				result.add(messaggioToContabiliaMessaggio(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaMessaggio messaggioToContabiliaMessaggio(Messaggio source) {
		ContabiliaMessaggio result = new ContabiliaMessaggio();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private List<ContabiliaAccertamento> getListContabiliaAccertamento(List<Accertamento> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaAccertamento> result = new ArrayList<ContabiliaAccertamento>(listSize);
		if (listSize > 0) {
			for (Accertamento item : list) {
				result.add(accertamentoToContabiliaAccertamento(item));
			}
		}
		
		return result;
	}
	
	private List<ContabiliaImpegno> getListContabiliaImpegno(List<Impegno> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaImpegno> result = new ArrayList<ContabiliaImpegno>(listSize);
		if (listSize > 0) {
			for (Impegno item : list) {
				result.add(accertamentoToContabiliaImpegno(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaAccertamento accertamentoToContabiliaAccertamento(Accertamento source) {
		ContabiliaAccertamento result = new ContabiliaAccertamento();
		
		if (source != null) {
			result.setAnnoAccertamento(source.getAnnoAccertamento());
			result.setAnnoAccertamentoOrigine(source.getAnnoAccertamentoOrigine());
			result.setAnnoAccertamentoRiaccertato(source.getAnnoAccertamentoRiaccertato());
			result.setDisponibilitaIncassare(source.getDisponibilitaIncassare());
			result.setNumAccertamentoOrigine(source.getNumAccertamentoOrigine());
			result.setNumAccertamentoRiaccertato(source.getNumAccertamentoRiaccertato());
			result.setNumeroAccertamento(source.getNumeroAccertamento());
			result.setSubAccertamenti(getListContabiliaSubAccertamento(source.getSubAccertamenti()));
			result.setCig(source.getCig());
			result.setCodiceSoggetto(source.getCodiceSoggetto());
			result.setCup(source.getCup());
			result.setImporto(source.getImporto());
			result.setNumeroArticolo(source.getNumeroArticolo());
			result.setNumeroCapitolo(source.getNumeroCapitolo());
			result.setNumeroUEB(source.getNumeroUEB());
			result.setParereFinanziario(source.isParereFinanziario());
			result.setPdc(pdcfToContabiliaPdcf(source.getPdc()));
			result.setProvvedimento(provvedimentoToContabiliaProvvedimento(source.getProvvedimento()));
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaImpegno accertamentoToContabiliaImpegno(Impegno source) {
		ContabiliaImpegno result = new ContabiliaImpegno();
		
		if (source != null) {
			result.setAnnoImpegno(source.getAnnoImpegno());
			result.setAnnoImpegnoOrigine(source.getAnnoImpegnoOrigine());
			result.setAnnoImpegnoRiaccertato(source.getAnnoImpegnoRiaccertato());
			result.setDisponibilitaLiquidare(source.getDisponibilitaLiquidare());
			result.setNumImpegnoOrigine(source.getNumImpegnoOrigine());
			result.setNumImpegnoRiaccertato(source.getNumImpegnoRiaccertato());
			result.setNumeroImpegno(source.getNumeroImpegno());
			result.setSubImpegni(getListContabiliaSubImpegno(source.getSubImpegni()));
			result.setTipoImpegno(source.getTipoImpegno());
			result.setCig(source.getCig());
			result.setCodiceSoggetto(source.getCodiceSoggetto());
			result.setCup(source.getCup());
			result.setImporto(source.getImporto());
			result.setNumeroArticolo(source.getNumeroArticolo());
			result.setNumeroCapitolo(source.getNumeroCapitolo());
			result.setNumeroUEB(source.getNumeroUEB());
			result.setParereFinanziario(source.isParereFinanziario());
			result.setPdc(pdcfToContabiliaPdcf(source.getPdc()));
			result.setProvvedimento(provvedimentoToContabiliaProvvedimento(source.getProvvedimento()));
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private List<ContabiliaSubAccertamento> getListContabiliaSubAccertamento(List<SubAccertamento> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaSubAccertamento> result = new ArrayList<ContabiliaSubAccertamento>(listSize);
		if (listSize > 0) {
			for (SubAccertamento item : list) {
				result.add(subAccertamentoToContabiliaSubAccertamento(item));
			}
		}
		
		return result;
	}
	
	private List<ContabiliaSubImpegno> getListContabiliaSubImpegno(List<SubImpegno> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaSubImpegno> result = new ArrayList<ContabiliaSubImpegno>(listSize);
		if (listSize > 0) {
			for (SubImpegno item : list) {
				result.add(subImpegnoToContabiliaSubImpegno(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaSubAccertamento subAccertamentoToContabiliaSubAccertamento(SubAccertamento source) {
		ContabiliaSubAccertamento result = new ContabiliaSubAccertamento();
		
		if (source != null) {
			result.setAnnoSubAccertamento(source.getAnnoSubAccertamento());
			result.setDisponibilitaIncassare(source.getDisponibilitaIncassare());
			result.setNumeroSubAccertamento(source.getNumeroSubAccertamento());
			result.setCig(source.getCig());
			result.setCodiceSoggetto(source.getCodiceSoggetto());
			result.setCup(source.getCup());
			result.setImporto(source.getImporto());
			result.setNumeroArticolo(source.getNumeroArticolo());
			result.setNumeroCapitolo(source.getNumeroCapitolo());
			result.setNumeroUEB(source.getNumeroUEB());
			result.setParereFinanziario(source.isParereFinanziario());
			result.setPdc(pdcfToContabiliaPdcf(source.getPdc()));
			result.setProvvedimento(provvedimentoToContabiliaProvvedimento(source.getProvvedimento()));
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaSubImpegno subImpegnoToContabiliaSubImpegno(SubImpegno source) {
		ContabiliaSubImpegno result = new ContabiliaSubImpegno();
		
		if (source != null) {
			result.setAnnoSubImpegno(source.getAnnoSubImpegno());
			result.setDisponibilitaLiquidare(source.getDisponibilitaLiquidare());
			result.setNumeroSubImpegno(source.getNumeroSubImpegno());
			result.setCig(source.getCig());
			result.setCodiceSoggetto(source.getCodiceSoggetto());
			result.setCup(source.getCup());
			result.setImporto(source.getImporto());
			result.setNumeroArticolo(source.getNumeroArticolo());
			result.setNumeroCapitolo(source.getNumeroCapitolo());
			result.setNumeroUEB(source.getNumeroUEB());
			result.setParereFinanziario(source.isParereFinanziario());
			result.setPdc(pdcfToContabiliaPdcf(source.getPdc()));
			result.setProvvedimento(provvedimentoToContabiliaProvvedimento(source.getProvvedimento()));
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaPianoDeiContiFinanziario pdcfToContabiliaPdcf(PianoDeiContiFinanziario source) {
		ContabiliaPianoDeiContiFinanziario result = new ContabiliaPianoDeiContiFinanziario();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaProgettoStilo progettoStiloToContabiliaProgettoStilo(ProgettoStilo source) {
		ContabiliaProgettoStilo result = new ContabiliaProgettoStilo();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaProvvedimento provvedimentoToContabiliaProvvedimento(Provvedimento source) {
		ContabiliaProvvedimento result = new ContabiliaProvvedimento();
		
		if (source != null) {
			result.setAnnoProvvedimento(source.getAnnoProvvedimento());
			result.setCodiceTipoProvvedimento(source.getCodiceTipoProvvedimento());
			result.setNumeroProvvedimento(source.getNumeroProvvedimento());
			result.setSac(strutturaAmministrativaToContabiliaStrutturaAmministrativa(source.getSac()));
			result.setStatoProvvedimento(source.getStatoProvvedimento());
			result.setCodice(source.getCodice());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaStrutturaAmministrativa strutturaAmministrativaToContabiliaStrutturaAmministrativa(StrutturaAmministrativa source) {
		ContabiliaStrutturaAmministrativa result = new ContabiliaStrutturaAmministrativa();
		
		if (source != null) {
			result.setCodiceTipoStruttura(source.getCodiceTipoStruttura());
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private List<ContabiliaMovimentoGestioneStilo> getListContabiliaMovimentoGestioneStilo(List<MovimentoGestioneStilo> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaMovimentoGestioneStilo> result = new ArrayList<ContabiliaMovimentoGestioneStilo>(listSize);
		if (listSize > 0) {
			for (MovimentoGestioneStilo item : list) {
				result.add(movimentoGestioneStiloToContabiliaMovimentoGestioneStilo(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaMovimentoGestioneStilo movimentoGestioneStiloToContabiliaMovimentoGestioneStilo(MovimentoGestioneStilo source) {
		ContabiliaMovimentoGestioneStilo result = new ContabiliaMovimentoGestioneStilo();
		
		if (source != null) {
			result.setAnnoCompetenza(source.getAnnoCompetenza());
			result.setAnnoCompetenzaModifica(source.getAnnoCompetenzaModifica());
			result.setAnnoCompetenzaSubAccertamento(source.getAnnoCompetenzaSubAccertamento());
			result.setAnnoCompetenzaSubImpegno(source.getAnnoCompetenzaSubImpegno());
			result.setCapitolo(capitoloToContabiliaCapitolo(source.getCapitolo()));
			result.setCategoria(categoriaToContabiliaCategoria(source.getCategoria()));
			result.setCig(source.getCig());
			result.setClasseSoggetto(classeSoggettoStiloToContabiliaClasseSoggettoStilo(source.getClasseSoggetto()));
			result.setClasseSoggettoIniziale(classeSoggettoStiloToContabiliaClasseSoggettoStilo(source.getClasseSoggettoIniziale()));
			result.setClassseSoggettoFinale(classeSoggettoStiloToContabiliaClasseSoggettoStilo(source.getClassseSoggettoFinale()));
			result.setCodUE(classificatoreGenericoToContabiliaClassificatoreGenerico(source.getCodUE()));
			result.setCofog(classificatoreGenericoToContabiliaClassificatoreGenerico(source.getCofog()));
			result.setCup(source.getCup());
			result.setDescrizioneAccertamento(source.getDescrizioneAccertamento());
			result.setDescrizioneImpegno(source.getDescrizioneImpegno());
			result.setDescrizioneModifica(source.getDescrizioneModifica());
			result.setDescrizioneSubAccertamento(source.getDescrizioneSubAccertamento());
			result.setDescrizioneSubImpegno(source.getDescrizioneSubImpegno());
			result.setGsa(classificatoreGenericoToContabiliaClassificatoreGenerico(source.getGsa()));
			result.setImportoAttualeAccertamento(source.getImportoAttualeAccertamento());
			result.setImportoAttualeImpegno(source.getImportoAttualeImpegno());
			result.setImportoAttualeSubAccertamento(source.getImportoAttualeSubAccertamento());
			result.setImportoAttualeSubImpegno(source.getImportoAttualeSubImpegno());
			result.setImportoInizialeAccertamento(source.getImportoInizialeAccertamento());
			result.setImportoInizialeImpegno(source.getImportoInizialeImpegno());
			result.setImportoInizialeSubAccertamento(source.getImportoInizialeSubAccertamento());
			result.setImportoInizialeSubImpegno(source.getImportoInizialeSubImpegno());
			result.setImportoModifica(source.getImportoModifica());
			result.setMacroaggregato(macroaggregatoToContabiliaMacroaggregato(source.getMacroaggregato()));
			result.setMissione(missioneToContabiliaMissione(source.getMissione()));
			result.setMotivoAssenzaCig(source.getMotivoAssenzaCig());
			result.setNaturaRicorrente(classificatoreGenericoToContabiliaClassificatoreGenerico(source.getNaturaRicorrente()));
			result.setNumeroAccertamento(source.getNumeroAccertamento());
			result.setNumeroImpegno(source.getNumeroImpegno());
			result.setNumeroModifica(source.getNumeroModifica());
			result.setNumeroSubAccertamento(source.getNumeroSubAccertamento());
			result.setNumeroSubImpegno(source.getNumeroSubImpegno());
			result.setPianoDeiContiFinanziario(pdcfToContabiliaPdcf(source.getPianoDeiContiFinanziario()));
			result.setPrenotazione(source.getPrenotazione());
			result.setPrenotazioneLiquidabile(source.getPrenotazioneLiquidabile());
			result.setProgetto(progettoStiloToContabiliaProgettoStilo(source.getProgetto()));
			result.setProgramma(programmaToContabiliaProgramma(source.getProgramma()));
			result.setSoggetto(soggettoStiloToContabiliaSoggettoStilo(source.getSoggetto()));
			result.setSoggettoFinale(soggettoStiloToContabiliaSoggettoStilo(source.getSoggettoFinale()));
			result.setSoggettoIniziale(soggettoStiloToContabiliaSoggettoStilo(source.getSoggettoIniziale()));
			result.setTipoDebitoSiope(tipoDebitoSiopeToContabiliaTipoDebitoSiope(source.getTipoDebitoSiope()));
			result.setTipoFinanziamento(tipoFinanziamentoToContabiliaTipoFinanziamento(source.getTipoFinanziamento()));
			result.setTipoMovimentoGestione(source.getTipoMovimentoGestione());
			result.setTipologia(tipologiaToContabiliaTipologia(source.getTipologia()));
			result.setTitolo(titoloToContabiliaTitolo(source.getTitolo()));
			result.setVincoli(vincoliStiloToContabiliaVincoliStilo(source.getVincoli()));
		}
		
		return result;
	}
	
	private ContabiliaCapitolo capitoloToContabiliaCapitolo(Capitolo source) {
		ContabiliaCapitolo result = new ContabiliaCapitolo();
		
		if (source != null) {
			result.setAnnoEsercizio(source.getAnnoEsercizio());
			result.setClassificatoriGenerici(getListContabiliaClassificatoreGenerico(source.getClassificatoriGenerici()));
			result.setDescrizione(source.getDescrizione());
			result.setDescrizioneArticolo(source.getDescrizioneArticolo());
			result.setNumeroArticolo(source.getNumeroArticolo());
			result.setNumeroCapitolo(source.getNumeroCapitolo());
			result.setNumeroUEB(source.getNumeroUEB());
			result.setPianoDeiContiFinanziario(pdcfToContabiliaPdcf(source.getPianoDeiContiFinanziario()));
			result.setRilevanteIva(source.isRilevanteIva());
			result.setSac(strutturaAmministrativaToContabiliaStrutturaAmministrativa(source.getSac()));
			result.setTipoFinanziamento(tipoFinanziamentoToContabiliaTipoFinanziamento(source.getTipoFinanziamento()));
			result.setTipoFondo(tipoFondoToContabiliaTipoFondo(source.getTipoFondo()));
			result.setTitolo(titoloToContabiliaTitolo(source.getTitolo()));
		}
		
		return result;
	}
	
	private ContabiliaClasseSoggettoStilo classeSoggettoStiloToContabiliaClasseSoggettoStilo(ClasseSoggettoStilo source) {
		ContabiliaClasseSoggettoStilo result = new ContabiliaClasseSoggettoStilo();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private List<ContabiliaClassificatoreGenerico> getListContabiliaClassificatoreGenerico(List<ClassificatoreGenerico> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaClassificatoreGenerico> result = new ArrayList<ContabiliaClassificatoreGenerico>(listSize);
		if (listSize > 0) {
			for (ClassificatoreGenerico item : list) {
				result.add(classificatoreGenericoToContabiliaClassificatoreGenerico(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaClassificatoreGenerico classificatoreGenericoToContabiliaClassificatoreGenerico(ClassificatoreGenerico source) {
		ContabiliaClassificatoreGenerico result = new ContabiliaClassificatoreGenerico();
		
		if (source != null) {
			result.setTipoCodifica(source.getTipoCodifica());
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaTipoFinanziamento tipoFinanziamentoToContabiliaTipoFinanziamento(TipoFinanziamento source) {
		ContabiliaTipoFinanziamento result = new ContabiliaTipoFinanziamento();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaTipoFondo tipoFondoToContabiliaTipoFondo(TipoFondo source) {
		ContabiliaTipoFondo result = new ContabiliaTipoFondo();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaTitolo titoloToContabiliaTitolo(Titolo source) {
		ContabiliaTitolo result = new ContabiliaTitolo();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaCategoria categoriaToContabiliaCategoria(Categoria source) {
		ContabiliaCategoria result = new ContabiliaCategoria();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaMacroaggregato macroaggregatoToContabiliaMacroaggregato(Macroaggregato source) {
		ContabiliaMacroaggregato result = new ContabiliaMacroaggregato();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaMissione missioneToContabiliaMissione(Missione source) {
		ContabiliaMissione result = new ContabiliaMissione();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaProgramma programmaToContabiliaProgramma(Programma source) {
		ContabiliaProgramma result = new ContabiliaProgramma();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaSoggettoStilo soggettoStiloToContabiliaSoggettoStilo(SoggettoStilo source) {
		ContabiliaSoggettoStilo result = new ContabiliaSoggettoStilo();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaTipoDebitoSiopeStilo tipoDebitoSiopeToContabiliaTipoDebitoSiope(TipoDebitoSiopeStilo source) {
		ContabiliaTipoDebitoSiopeStilo result = new ContabiliaTipoDebitoSiopeStilo();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaTipologia tipologiaToContabiliaTipologia(Tipologia source) {
		ContabiliaTipologia result = new ContabiliaTipologia();
		
		if (source != null) {
			result.setCodice(source.getCodice());
			result.setDescrizione(source.getDescrizione());
			result.setStato(statoToContabiliaStato(source.getStato()));
		}
		
		return result;
	}
	
	private ContabiliaVincoliStilo vincoliStiloToContabiliaVincoliStilo(VincoliStilo source) {
		ContabiliaVincoliStilo result = new ContabiliaVincoliStilo();
		
		if (source != null) {
			result.setVincoli(getListContabiliaVincoloImpegnoStilo(source.getVincolo()));
		}
		
		return result;
	}
	
	private List<ContabiliaVincoloImpegnoStilo> getListContabiliaVincoloImpegnoStilo(List<VincoloImpegnoStilo> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<ContabiliaVincoloImpegnoStilo> result = new ArrayList<ContabiliaVincoloImpegnoStilo>(listSize);
		if (listSize > 0) {
			for (VincoloImpegnoStilo item : list) {
				result.add(vincoloImpegnoStiloToContabiliaVincoloImpegnoStilo(item));
			}
		}
		
		return result;
	}
	
	private ContabiliaVincoloImpegnoStilo vincoloImpegnoStiloToContabiliaVincoloImpegnoStilo(VincoloImpegnoStilo source) {
		ContabiliaVincoloImpegnoStilo result = new ContabiliaVincoloImpegnoStilo();
		
		if (source != null) {
			result.setAnnoAccertamento(source.getAnnoAccertamento());
			result.setImporto(source.getImporto());
			result.setNumeroAccertamento(source.getNumeroAccertamento());
			result.setTipo(source.getTipo());
		}
		
		return result;
	}
	
}
