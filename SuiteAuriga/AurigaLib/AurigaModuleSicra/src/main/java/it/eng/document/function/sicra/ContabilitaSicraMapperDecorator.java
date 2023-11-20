/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import it.eng.document.function.bean.sicra.EsitoChiamata;
import it.eng.document.function.bean.sicra.SicraAnagrafica;
import it.eng.document.function.bean.sicra.SicraAttoLiqContabile;
import it.eng.document.function.bean.sicra.SicraAttoLiqTecnica;
import it.eng.document.function.bean.sicra.SicraBeneficiario;
import it.eng.document.function.bean.sicra.SicraBudget;
import it.eng.document.function.bean.sicra.SicraCodici;
import it.eng.document.function.bean.sicra.SicraFormaPagamento;
import it.eng.document.function.bean.sicra.SicraInputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraLiquidazioneAtto;
import it.eng.document.function.bean.sicra.SicraLiquidazioneOrdinativo;
import it.eng.document.function.bean.sicra.SicraOrdinativo;
import it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto;
import it.eng.document.function.bean.sicra.SicraQuietanza;
import it.eng.document.function.bean.sicra.SicraResidenza;
import it.eng.document.function.bean.sicra.SicraRifImport;
import it.eng.utility.data.Output;
import it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.FormePagamento;
import it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.FormePagamento.FormaPagamento;
import it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Liquidazione.AttoLiqContabile;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario.Quietanze;
import it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario.Quietanze.Quietanza;

public abstract class ContabilitaSicraMapperDecorator implements ContabilitaSicraMapper {

	private final ContabilitaSicraMapper delegate;

	public ContabilitaSicraMapperDecorator(ContabilitaSicraMapper delegate) {
		this.delegate = delegate;
	}

	// default
	// private List<SicraBudget> risultatoToSicraBudgets(it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato source) {
	// if (source == null)
	// return null;
	// if (source.getListaBudget() == null)
	// return null;
	// return delegate.budgetsToSicraBudgets(source.getListaBudget().getBudget());
	// }

	@Override
	public SicraOutputRicercaVociBilancio outputRisultatoToSicraOutputRicercaVociBilancio(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputRicercaVociBilancio target = delegate.outputRisultatoToSicraOutputRicercaVociBilancio(source);
		it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato data = source.getData();
		if (data == null || data.getListaBudget() == null) {
			return target;
		}
		List<SicraBudget> budget = delegate.budgetsToSicraBudgets(data.getListaBudget().getBudget());
		target.setBudget(budget);
		return target;
	}

	/* SET MOVIMENTI ATTO ********************************************************************************************/
	@Override
	public SicraOutputSetMovimentiAtto outputRisultatoToSicraOutputSetMovimentiAtto(
			Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputSetMovimentiAtto target = delegate.risultatoToSicraOutputSetMovimentiAtto(source.getData());
		target.setEsitoChiamata(delegate.esitoToEsitoChiamata(source.getOutcome()));
		return target;
	}

	/* SET ESECUTIVITA ATTO ********************************************************************************************/
	// default
	@Override
	public it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta sicraInputSetEsecutivitaAttoToRichiesta(SicraInputSetEsecutivitaAtto source) {
		if (source == null)
			return null;
		it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta target = delegate.sicraInputSetEsecutivitaAttoToRichiesta(source);
		it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta.Impegno impegno = target.getImpegno();
		impegno.setTestata(delegate.sicraImpegnoDaRendereEsecutivoToTestata(source.getImpegno()));
		return target;
	}

	// default
	@Override
	public SicraOutputSetEsecutivitaAtto outputRisultatoToSicraOutputSetEsecutivitaAtto(
			Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputSetEsecutivitaAtto target = delegate.risultatoToSicraOutputSetEsecutivitaAtto(source.getData());
		target.setEsitoChiamata(delegate.esitoToEsitoChiamata(source.getOutcome()));
		return target;
	}

	/* ARCHIVIA ATTO ********************************************************************************************/
	// default
	@Override
	public SicraOutputArchiviaAtto outputRisultatoToSicraOutputArchiviaAtto(Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputArchiviaAtto target = delegate.risultatoToSicraOutputArchiviaAtto(source.getData());
		target.setEsitoChiamata(delegate.esitoToEsitoChiamata(source.getOutcome()));
		return target;
	}
	
	/* RICERCA ANAGRAFICA ********************************************************************************************/
	@Override
	public SicraOutputRicercaAnagrafica outputRisultatoToSicraOutputRicercaAnagrafica(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputRicercaAnagrafica target = delegate.outputRisultatoToSicraOutputRicercaAnagrafica(source);
		it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato data = source.getData();
		if (data == null || data.getListaAnagrafiche() == null) {
			return target;
		}
		List<SicraAnagrafica> anagrafica = getListAnagrafica(data.getListaAnagrafiche().getAnagrafica());
		target.setListaAnagrafiche(anagrafica);
		
		// mapping esito
		//EsitoChiamata esito = new EsitoChiamata();
		//if (data.getEsito() != null) {
			//boolean isOk = false;
			//if (data.getEsito().getTipo().value().equals("OK")) {
				//isOk = true;
			//}
			
			//esito.setMessaggio(data.getEsito().getDescrizione());
			//esito.setOk(isOk);
		//}
		
		//target.setEsitoChiamata(esito);
		
		
		return target;
	}
	
	private List<SicraAnagrafica> getListAnagrafica(List<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<SicraAnagrafica> result = new ArrayList<SicraAnagrafica>();
		if (listSize > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica item : list) {
				result.add(getAnagrafica(item));
			}
		}
		
		return result;
	}
	
	private SicraAnagrafica getAnagrafica(it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica source) {
		SicraAnagrafica result = new SicraAnagrafica();
		
		if (source != null) {
			result.setPkid(source.getPkid());
			result.setRagSociale(source.getRagSociale());
			result.setCf(source.getCf());
			result.setPiva(source.getPiva());
			result.setResidenza(getResidenza(source.getResidenza()));
			result.setRifImport(getRifImport(source.getRifImport()));
			result.setDaBonificare(source.getDaBonificare());
			result.setNominativoFalso(source.getNominativoFalso());
			result.setIrriconoscibile(source.getIrriconoscibile());
			result.setDataFineAttivita(source.getDataFineAttivita());
			result.setNome(source.getNome());
			result.setCognome(source.getCognome());
			result.setFormePagamento(getListFormaPagamento(source.getFormePagamento()));
		}
		
		return result;
	}
	
	private SicraResidenza getResidenza(it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.Residenza source) {
		SicraResidenza result = new SicraResidenza();
		
		if (source != null) {
			result.setComune(source.getComune());
			result.setCap(source.getCap());
			result.setSiglaProvincia(source.getSiglaProvincia());
			result.setSpecie(source.getSpecie());
			result.setArea(source.getArea());
			result.setCivico(source.getCivico());
			result.setEmail(source.getEmail());
			result.setTelefono(source.getTelefono());
			result.setCellulare(source.getCellulare());
			result.setLettera(source.getLettera());
			result.setCorte(source.getCorte());
			result.setScala(source.getScala());
			result.setInterno(source.getInterno());
			result.setPiano(source.getPiano());
		}
		
		return result;
	}
	
	private SicraRifImport getRifImport(it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.RifImport source) {
		SicraRifImport result = new SicraRifImport();
		
		if (source != null) {
			result.setCodici(getListCodici(source.getCodici()));
		}
		
		return result;
	}
	
	private List<SicraCodici> getListCodici(List<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.RifImport.Codici> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<SicraCodici> result = new ArrayList<SicraCodici>();
		if (listSize > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.RifImport.Codici item : list) {
				result.add(getCodice(item));
			}
		}
		
		return result;
	}
	
	private SicraCodici getCodice(it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.RifImport.Codici source) {
		SicraCodici result = new SicraCodici();
		
		if (source != null) {
			result.setApplicazione(source.getApplicazione());
			result.setCodiceTrasco(source.getCodiceTrasco());
		}
		
		return result;
	}
	
	private List<SicraFormaPagamento> getListFormaPagamento(FormePagamento formePagamento) {
		if (formePagamento == null) {
			return null;
		}
		
		List<FormaPagamento> listFormePagamento = formePagamento.getFormaPagamento();
		List<SicraFormaPagamento> result = new ArrayList<SicraFormaPagamento>();
		if (listFormePagamento.size() > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.FormePagamento.FormaPagamento item : listFormePagamento) {
				result.add(getFormaPagamento(item));
			}
		}
		
		return result;
	}
	
	private SicraFormaPagamento getFormaPagamento(it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.ListaAnagrafiche.Anagrafica.FormePagamento.FormaPagamento source) {
		SicraFormaPagamento result = new SicraFormaPagamento();
		
		if (source != null) {
			result.setBicSwift(source.getBicSwift());
			result.setCodice(source.getCodice());
			result.setCodiceTipoPagamento(source.getCodiceTipoPagamento());
			result.setDescrizione(source.getDescrizione());
			result.setIban(source.getIban());
		}
		
		return result;
	}
	
	/* RICERCA ATTO LIQUIDAZIONE ********************************************************************************************/
	@Override
	public SicraOutputRicercaAttoLiquidazione outputRisultatoToSicraOutputRicercaAttoLiquidazione(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputRicercaAttoLiquidazione target = delegate.outputRisultatoToSicraOutputRicercaAttoLiquidazione(source);
		it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato data = source.getData();
		if (data == null || data.getLiquidazione() == null) {
			return target;
		}
		List<SicraLiquidazioneAtto> liquidazioneAtto = getListLiquidazione(data.getLiquidazione());
		target.setLiquidazione(liquidazioneAtto);
		
		// mapping esito
		EsitoChiamata esito = new EsitoChiamata();
		if (data.getEsito() != null) {
			boolean isOk = false;
			if (data.getEsito().getTipo().value().equals("OK")) {
				isOk = true;
			}
			
			esito.setMessaggio(data.getEsito().getDescrizione());
			esito.setOk(isOk);
		}
		target.setEsitoChiamata(esito);
		
		return target;
	}
	
	private List<SicraLiquidazioneAtto> getListLiquidazione(List<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Liquidazione> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<SicraLiquidazioneAtto> result = new ArrayList<SicraLiquidazioneAtto>();
		if (listSize > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Liquidazione item : list) {
				result.add(getLiquidazioneAtto(item));
			}
		}
		
		return result;
	}
	
	private SicraLiquidazioneAtto getLiquidazioneAtto(it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Liquidazione item) {
		SicraLiquidazioneAtto result = new SicraLiquidazioneAtto();
		
		if (item != null) {
			result.setAnno(item.getAnno());
			result.setCodice(item.getCodice());
			result.setCodiceAnnuale(item.getCodiceAnnuale());
			result.setData(item.getData());
			result.setOggetto(item.getOggetto());
			result.setTipoLiquidazione(item.getTipoLiquidazione());
			result.setAttoLiqContabile(getLiquidazioneContabile(item.getAttoLiqContabile()));
			result.setAttoLiqTecnica(getLiquidazioneTecnica(item.getAttoLiqTecnica()));
		}
		
		return result;
	}
	
	private SicraAttoLiqTecnica getLiquidazioneTecnica(it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Liquidazione.AttoLiqTecnica source) {
		SicraAttoLiqTecnica result = new SicraAttoLiqTecnica();
		
		if (source != null) {
			result.setContenutoFileAttoLiq(source.getContenutoFileAttoLiq());
			result.setNomeFileAttoLiq(source.getNomeFileAttoLiq());
		}
		
		return result;
	}
	
	private SicraAttoLiqContabile getLiquidazioneContabile(AttoLiqContabile attoLiqContabile) {
		SicraAttoLiqContabile result = new SicraAttoLiqContabile();
		
		if (attoLiqContabile != null) {
			result.setContenutoFileAttoLiq(attoLiqContabile.getContenutoFileAttoLiq());
			result.setNomeFileAttoLiq(attoLiqContabile.getNomeFileAttoLiq());
		}
		
		return result;
	}
	
	/* RICERCA ORDINATIVI ATTO LIQUIDAZIONE ********************************************************************************************/
	public SicraOutputRicercaOrdinativoAttoLiquidazione outputRisultatoToSicraOutputRicercaOrdinativoAttoLiquidazione(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputRicercaOrdinativoAttoLiquidazione target = delegate.outputRisultatoToSicraOutputRicercaOrdinativoAttoLiquidazione(source);
		it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato data = source.getData();
		if (data == null || data.getLiquidazione() == null) {
			return target;
		}
		List<SicraLiquidazioneOrdinativo> liquidazioneOrdinativo = getListLiquidazioneOrd(data.getLiquidazione());
		target.setLiquidazione(liquidazioneOrdinativo);
		
		// mapping esito
		EsitoChiamata esito = new EsitoChiamata();
		if (data.getEsito() != null) {
			boolean isOk = false;
			if (data.getEsito().getTipo().value().equals("OK")) {
				isOk = true;
			}
			
			esito.setMessaggio(data.getEsito().getDescrizione());
			esito.setOk(isOk);
		}
		target.setEsitoChiamata(esito);
		
		return target;
	}
	
	private List<SicraLiquidazioneOrdinativo> getListLiquidazioneOrd(List<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione> list) {
		if (list == null) {
			return null;
		}
		
		Integer listSize = list.size();
		List<SicraLiquidazioneOrdinativo> result = new ArrayList<SicraLiquidazioneOrdinativo>();
		if (listSize > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione item : list) {
				result.add(getLiquidazioneOrdinativo(item));
			}
		}
		
		return result;
	}
	
	private SicraLiquidazioneOrdinativo getLiquidazioneOrdinativo(it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione source) {
		SicraLiquidazioneOrdinativo result = new SicraLiquidazioneOrdinativo();
		
		if (source != null) {
			result.setAnno(source.getAnno());
			result.setCodice(source.getCodice());
			result.setCodiceAnnuale(source.getCodiceAnnuale());
			result.setData(source.getData());
			result.setOggetto(source.getOggetto());
			result.setTipoLiquidazione(source.getTipoLiquidazione());
			result.setOrdinativi(getListOrdinativo(source.getOrdinativi()));
		}
		
		return result;
	}
	
	private List<SicraOrdinativo> getListOrdinativo(Ordinativi ordinativi) {
		if (ordinativi == null) {
			return null;
		}
		
		List<Ordinativo> listOrdinativi = ordinativi.getOrdinativo();
		List<SicraOrdinativo> result = new ArrayList<SicraOrdinativo>();
		if (listOrdinativi.size() > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo item : listOrdinativi) {
				result.add(getOrdinativo(item));
			}
		}
		
		return result;
	}
	
	private SicraOrdinativo getOrdinativo(it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo source) {
		SicraOrdinativo result = new SicraOrdinativo();
		
		if (source != null) {
			result.setAnno(source.getAnno());
			result.setAnnullato(source.getAnnullato());
			result.setCausale(source.getCausale());
			result.setData(source.getData());
			result.setNumero(source.getNumero());
			result.setTipo(source.getTipo().value());
			result.setBeneficiari(getListBenificiario(source.getBeneficiari()));
		}
		
		return result;
	}
	
	private List<SicraBeneficiario> getListBenificiario(Beneficiari beneficiari) {
		if (beneficiari == null) {
			return null;
		}
		
		List<Beneficiario> listBeneficiari = beneficiari.getBeneficiario();
		List<SicraBeneficiario> result = new ArrayList<SicraBeneficiario>();
		if (listBeneficiari.size() > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario item : listBeneficiari) {
				result.add(getBenificiario(item));
			}
		}
		
		return result;
	}
	
	private SicraBeneficiario getBenificiario(it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario source) {
		SicraBeneficiario result = new SicraBeneficiario();
		
		if (source != null) {
			result.setCodiceFiscale(source.getCodiceFiscale());
			result.setImportoLordo(source.getImportoLordo());
			result.setImportoNetto(source.getImportoNetto());
			result.setImportoRitenute(source.getImportoRitenute());
			result.setNominativo(source.getNominativo());
			result.setPartitaIva(source.getPartitaIva());
			result.setQuietanze(getListQuietanza(source.getQuietanze()));
		}
		
		return result;
	}
	
	private List<SicraQuietanza> getListQuietanza(Quietanze quietanze) {
		if (quietanze == null) {
			return null;
		}
		
		List<Quietanza> listQuietanza = quietanze.getQuietanza();
		List<SicraQuietanza> result = new ArrayList<SicraQuietanza>();
		if (listQuietanza.size() > 0) {
			for (it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario.Quietanze.Quietanza item : listQuietanza) {
				result.add(getQueitanza(item));
			}
		}
		
		return result;
	}
	
	private SicraQuietanza getQueitanza(it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Liquidazione.Ordinativi.Ordinativo.Beneficiari.Beneficiario.Quietanze.Quietanza source) {
		SicraQuietanza result = new SicraQuietanza();
		
		if (source != null) {
			result.setAnno(source.getAnno());
			result.setData(source.getData());
			result.setImporto(source.getImporto());
			result.setNumero(source.getNumero());
		}
		
		return result;
	}
	
	/* AGGIORNA RIF ATTO ********************************************************************************************/
	@Override
	public it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta sicraInputAggiornaRifAttoLiquidazioneToRichiesta(SicraInputAggiornaRifAttoLiquidazione source) {
		if (source == null)
			return null;
		it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta target = delegate.sicraInputAggiornaRifAttoLiquidazioneToRichiesta(source);
		it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta.Proposta proposta = target.getProposta();
		it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta.AttoDefinitivo attoDefinitivo = target.getAttoDefinitivo();
		proposta = delegate.sicraPropostaAttoLiquidazioneToProposta(source.getProposta());
		attoDefinitivo = delegate.sicraAttoDefinitivoLiquidazioneToAttoDefinitivo(source.getAttoDefinitivo());
		
		target.setAttoDefinitivo(attoDefinitivo);
		target.setProposta(proposta);
		
		return target;
	}
	
	@Override
	public SicraOutputAggiornaRifAttoLiquidazione outputRisultatoToSicraOutputAggiornaRifAttoLiquidazione(
			Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> source) {
		if (source == null)
			return null;
		SicraOutputAggiornaRifAttoLiquidazione target = delegate.outputRisultatoToSicraOutputAggiornaRifAttoLiquidazione(source);
		it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato data = source.getData();
		if (data == null) {
			return target;
		}
		
		BigInteger totLiqAggiornate = new BigInteger("0");
		if (data.getTotLiquidazioniAggiornate() != null) {
			totLiqAggiornate = data.getTotLiquidazioniAggiornate();
		}
		target.setTotLiquidazioniAggiornate(totLiqAggiornate);
		
		// mapping esito
		EsitoChiamata esito = new EsitoChiamata();
		if (data.getEsito() != null) {
			boolean isOk = false;
			if (data.getEsito().getTipo().value().equals("OK")) {
				isOk = true;
			}
			
			esito.setMessaggio(data.getEsito().getDescrizione());
			esito.setOk(isOk);
		}
		target.setEsitoChiamata(esito);
		
		return target;
	}
	
}
