package it.eng.utility.amco.utils;

import java.util.Comparator;

import it.finmatica.gsaws.BusinessPartners;
import it.finmatica.gsaws.ContiImputazione;
import it.finmatica.gsaws.ContoCreditoDebito;
import it.finmatica.gsaws.TipoDocumento;

public class SortingObjectList {
	
	public Comparator<ContoCreditoDebito> getContoCreditoDebitoComparatorByCod() {
		
		return new Comparator<ContoCreditoDebito>() {	
			@Override
			public int compare(ContoCreditoDebito o1, ContoCreditoDebito o2) {
				
				if (o1 != null && o2 != null && o1.getCodice() != null && o2.getCodice() != null) {
					return o1.getCodice().toLowerCase().compareTo(o2.getCodice().toLowerCase());
                } else {
                    return -1;
                }
			}
		};
		
	}
	
	public Comparator<ContoCreditoDebito> getContoCreditoDebitoComparatorByDesc() {
		
		return new Comparator<ContoCreditoDebito>() {
			@Override
			public int compare(ContoCreditoDebito o1, ContoCreditoDebito o2) {
				if (o1 != null && o2 != null && o1.getDescrizione() != null && o2.getDescrizione() != null) {
					return o1.getDescrizione().toLowerCase().compareTo(o2.getDescrizione().toLowerCase());
                } else {
                    return -1;
                }
			}
		};
		
	}
	
	public Comparator<ContiImputazione> getContoImputazioneComparatorByCod() {
		
		return new Comparator<ContiImputazione>() {	
			@Override
			public int compare(ContiImputazione o1, ContiImputazione o2) {
				
				if (o1 != null && o2 != null && o1.getCodice() != null && o2.getCodice() != null) {
					return o1.getCodice().toLowerCase().compareTo(o2.getCodice().toLowerCase());
                } else {
                    return -1;
                }
			}
		};
		
	}
	
	public Comparator<ContiImputazione> getContoImputazioneComparatorByDesc() {
		
		return new Comparator<ContiImputazione>() {
			@Override
			public int compare(ContiImputazione o1, ContiImputazione o2) {
				if (o1 != null && o2 != null && o1.getDescrizione() != null && o2.getDescrizione() != null) {
					return o1.getDescrizione().toLowerCase().compareTo(o2.getDescrizione().toLowerCase());
                } else {
                    return -1;
                }
			}
		};
		
	}
	
	public Comparator<TipoDocumento> getTipoDocumentoComparatorByNome() {
		
		return new Comparator<TipoDocumento>() {
			@Override
			public int compare(TipoDocumento o1, TipoDocumento o2) {
				if (o1 != null && o2 != null && o1.getNome() != null && o2.getNome() != null) {
					return o1.getNome().toLowerCase().compareTo(o2.getNome().toLowerCase());
                } else {
                    return -1;
                }
			}
		};
		
	}
	
	public Comparator<BusinessPartners> getBusinessPartnersComparatorByRagSoc() {
		
		return new Comparator<BusinessPartners>() {
			@Override
			public int compare(BusinessPartners o1, BusinessPartners o2) {
				if (o1 != null && o2 != null && o1.getRagioneSociale() != null && o2.getRagioneSociale() != null) {
					return o1.getRagioneSociale().toLowerCase().compareTo(o2.getRagioneSociale().toLowerCase());
                } else {
                    return -1;
                }
			}
		};
		
	}
	
}
