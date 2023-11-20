/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.document.function.bean.AmcoBusinessPartners;
import it.eng.document.function.bean.AmcoBusinessPartnersRequest;
import it.eng.document.function.bean.AmcoBusinessPartnersResponse;
import it.eng.document.function.bean.AmcoContiCreditoDebitoRequest;
import it.eng.document.function.bean.AmcoContiCreditoDebitoResponse;
import it.eng.document.function.bean.AmcoContiImputazioneRequest;
import it.eng.document.function.bean.AmcoContiImputazioneResponse;
import it.eng.document.function.bean.AmcoContoCreditoDebito;
import it.eng.document.function.bean.AmcoContoImputazione;
import it.eng.document.function.bean.AmcoTipiDocumentoRequest;
import it.eng.document.function.bean.AmcoTipiDocumentoResponse;
import it.eng.document.function.bean.AmcoTipoDocumento;
import it.finmatica.gsaws.BusinessPartners;
import it.finmatica.gsaws.BusinessPartnersRequest;
import it.finmatica.gsaws.BusinessPartnersResponse;
import it.finmatica.gsaws.ContiCreditoDebitoRequest;
import it.finmatica.gsaws.ContiCreditoDebitoResponse;
import it.finmatica.gsaws.ContiImputazione;
import it.finmatica.gsaws.ContiImputazioneRequest;
import it.finmatica.gsaws.ContiImputazioneResponse;
import it.finmatica.gsaws.ContoCreditoDebito;
//import it.finmatica.gsaws.EntrataUscitaType;
//import it.finmatica.gsaws.FinanziariaType;
import it.finmatica.gsaws.TipiDocumentoRequest;
import it.finmatica.gsaws.TipiDocumentoResponse;
import it.finmatica.gsaws.TipoDocumento;

public class ConsultazioneAmcoMapperImpl implements ConsultazioneAmcoMapper {

	@Override
	public ContiCreditoDebitoRequest inputRicercaContiCreditoDebito(AmcoContiCreditoDebitoRequest source) {
		if (source == null) {
			return null;
		}
		
		ContiCreditoDebitoRequest result = new ContiCreditoDebitoRequest();
		result.setCodiceBP(source.getCodiceBP());
		result.setCodiceCapitolo(source.getCodiceCapitolo());
		result.setEntrataUscita(source.getEntrataUscita());
		result.setNomeTipoDoc(source.getNomeTipoDoc());
		
		return result;
	}

	@Override
	public ContiImputazioneRequest inputRicercaContiImputazione(AmcoContiImputazioneRequest source) {
		if (source == null) {
			return null;
		}
		
		ContiImputazioneRequest result = new ContiImputazioneRequest();
		result.setCodiceCapitolo(source.getCodiceCapitolo());
		result.setCodiceConto(source.getCodiceConto());
		result.setDescConto(source.getDescConto());
		result.setEntrataUscita(source.getEntrataUscita());
		result.setNomeTipoDoc(source.getNomeTipoDoc());
		
		return result;
	}

	@Override
	public TipiDocumentoRequest inputRicercaTipiDocumento(AmcoTipiDocumentoRequest source) {
		if (source == null) {
			return null;
		}
		
		TipiDocumentoRequest result = new TipiDocumentoRequest();
		result.setDescrizione(source.getDescrizione());
		result.setFinanziaria(source.getFinanziaria());
		result.setNome(source.getNome());
		
		return result;
	}

	@Override
	public BusinessPartnersRequest inputRicercaBusinessPartners(AmcoBusinessPartnersRequest source) {
		if (source == null) {
			return null;
		}
		
		BusinessPartnersRequest result = new BusinessPartnersRequest();
		result.setCodiceFinanziaria(source.getCodiceFinanziaria());
		result.setCodiceFiscale(source.getCodiceFiscale());
		result.setPartitaIva(source.getPartitaIva());
		result.setRagioneSociale(source.getRagioneSociale());
		
		return result;
	}

	@Override
	public AmcoContiCreditoDebitoResponse outputRicercaContiCreditoDebito(ContiCreditoDebitoResponse source) {
		if (source == null) {
			return null;
		}
		
		AmcoContiCreditoDebitoResponse result = new AmcoContiCreditoDebitoResponse();
		result.setContoCreditoDebito(getContoCreditoDebitoList(source.getContoCreditoDebito()));
		result.setDescrizioneErrore(source.getDescrizioneErrore());
		
		return result;
	}

	@Override
	public AmcoContiImputazioneResponse outputRicercaContiImputazione(ContiImputazioneResponse source) {
		if (source == null) {
			return null;
		}
		
		AmcoContiImputazioneResponse result = new AmcoContiImputazioneResponse();
		result.setContiImputazione(getContoImputazioneList(source.getContiImputazione()));
		result.setDescrizioneErrore(source.getDescrizioneErrore());
		
		return result;
	}
	
	@Override
	public AmcoTipiDocumentoResponse outputRicercaTipiDocumento(TipiDocumentoResponse source) {
		if (source == null) {
			return null;
		}
		
		AmcoTipiDocumentoResponse result = new AmcoTipiDocumentoResponse();
		result.setTipiDocumento(getTipoDocumentoList(source.getTipiDocumento()));
		result.setDescrizioneErrore(source.getDescrizioneErrore());
		
		return result;
	}

	@Override
	public AmcoBusinessPartnersResponse outputRicercaBusinessPartners(BusinessPartnersResponse source) {
		if (source == null) {
			return null;
		}
		
		AmcoBusinessPartnersResponse result = new AmcoBusinessPartnersResponse();
		result.setBusinessPartners(getBusinessPartnersList(source.getBusinessPartners()));
		result.setDescrizioneErrore(source.getDescrizioneErrore());
		
		return result;
	}
	
	
	private List<AmcoContoCreditoDebito> getContoCreditoDebitoList(List<ContoCreditoDebito> source) {
		if (source == null) {
			return null;
		}
		
		Integer listSize = source.size();
		List<AmcoContoCreditoDebito> result = new ArrayList<AmcoContoCreditoDebito>();
		if (listSize > 0) {
			for (ContoCreditoDebito item : source) {
				result.add(getContoCreditoDebito(item));
			}
		}
		
		return result;
	}
	
	private AmcoContoCreditoDebito getContoCreditoDebito(ContoCreditoDebito source) {
		if (source == null) {
			return null;
		}
		
		AmcoContoCreditoDebito result = new AmcoContoCreditoDebito();
		result.setCodice(source.getCodice());
		result.setDescrizione(source.getDescrizione());
		result.setNatura(source.getNatura());
		result.setTipologia(source.getTipologia());
		
		return result;
	}
	
	private List<AmcoContoImputazione> getContoImputazioneList(List<ContiImputazione> source) {
		if (source == null) {
			return null;
		}
		
		Integer listSize = source.size();
		List<AmcoContoImputazione> result = new ArrayList<AmcoContoImputazione>();
		if (listSize > 0) {
			for (ContiImputazione item : source) {
				result.add(getContoImputazione(item));
			}
		}
		
		return result;
	}
	
	private AmcoContoImputazione getContoImputazione(ContiImputazione source) {
		if (source == null) {
			return null;
		}
		
		AmcoContoImputazione result = new AmcoContoImputazione();
		result.setCodice(source.getCodice());
		result.setDescrizione(source.getDescrizione());
		result.setTipologia(source.getTipologia());
		
		return result;
	}
	
	private List<AmcoTipoDocumento> getTipoDocumentoList(List<TipoDocumento> source) {
		if (source == null) {
			return null;
		}
		
		Integer listSize = source.size();
		List<AmcoTipoDocumento> result = new ArrayList<AmcoTipoDocumento>();
		if (listSize > 0) {
			for (TipoDocumento item : source) {
				result.add(getTipoDocumento(item));
			}
		}
		
		return result;
	}
	
	private AmcoTipoDocumento getTipoDocumento(TipoDocumento source) {
		if (source == null) {
			return null;
		}
		
		AmcoTipoDocumento result = new AmcoTipoDocumento();
		result.setDescrizione(source.getDescrizione());
		result.setFinanziaria(source.getFinanziaria());
		result.setNome(source.getNome());
		
		return result;
	}
	
	private List<AmcoBusinessPartners> getBusinessPartnersList(List<BusinessPartners> source) {
		if (source == null) {
			return null;
		}
		
		Integer listSize = source.size();
		List<AmcoBusinessPartners> result = new ArrayList<AmcoBusinessPartners>();
		if (listSize > 0) {
			for (BusinessPartners item : source) {
				result.add(getBusinessPartners(item));
			}
		}
		
		return result;
	}
	
	private AmcoBusinessPartners getBusinessPartners(BusinessPartners source) {
		if (source == null) {
			return null;
		}
		
		AmcoBusinessPartners result = new AmcoBusinessPartners();
		result.setCodiceAmco(source.getCodiceAmco());
		result.setCodiceFinanziaria(source.getCodiceFinanziaria());
		result.setCodiceFiscale(source.getCodiceFiscale());
		result.setPartitaIva(source.getPartitaIva());
		result.setRagioneSociale(source.getRagioneSociale());
		
		return result;
	}

}
