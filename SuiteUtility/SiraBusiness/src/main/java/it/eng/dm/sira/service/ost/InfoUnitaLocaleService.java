package it.eng.dm.sira.service.ost;

import java.util.ArrayList;
import java.util.List;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.AttivitaEconomicheUL;
import it.eng.dm.sira.service.bean.ConsumoMateriePrimeUL;
import it.eng.dm.sira.service.bean.InfoAnagraficheULBean;
import it.eng.dm.sira.service.bean.InfoTecnicheULBean;
import it.eng.dm.sira.service.ost.util.OstUtils;

import org.apache.log4j.Logger;

import com.hyperborea.sira.ws.AttEcoSvolta;
import com.hyperborea.sira.ws.CaratterizzazioniOst;
import com.hyperborea.sira.ws.CcostSoggettiGiuridici;
import com.hyperborea.sira.ws.CcostUnitaLocali;
import com.hyperborea.sira.ws.ConsumoMatPrime;
import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.ReferencedBean;

/**
 * servizio che restituisce informazioni sulle unit locali
 * 
 * @author jravagnan
 * 
 */
public class InfoUnitaLocaleService {

	private Logger log = Logger.getLogger(InfoUnitaLocaleService.class);

	private static final String NATURA_UL = "1";

	private static final String CATEGORIA_UL = "1";

	public InfoAlberoOst getOstDirettamenteCollegati(String idOst) throws SiraBusinessException {
		InfoOSTService infoService = new InfoOSTService();
		InfoAlberoOst albero = null;
		try {
			InfoBeanIn in = new InfoBeanIn();
			in.setCategoria(NATURA_UL);
			in.setNatura(CATEGORIA_UL);
			in.setIdOggetto(idOst);
			albero = infoService.getInfoOstCollegatiCardinalita(in);
		} catch (Exception e) {
			log.error("impossibile ricavare le unit locali ", e);
			throw new SiraBusinessException("impossibile ricavare le unit locali ", e);
		}
		return albero;
	}

	private OggettiStruttureTerritoriali getOggetto(String idOst) throws SiraBusinessException {
		ReferencedBean rf = null;
		OggettiStruttureTerritoriali ret = null;
		try {
			InfoOSTService infoService = new InfoOSTService();
			InfoBeanIn in = new InfoBeanIn();
			in.setNatura(NATURA_UL);
			in.setCategoria(CATEGORIA_UL);
			in.setIdOggetto(idOst);
			rf = infoService.getInfo(in);
		} catch (Exception e) {
			log.error("impossibile ricavare le info della unit locale", e);
			throw new SiraBusinessException("impossibile ricavare le info della unit locale", e);
		}
		if (rf != null) {
			ret = (OggettiStruttureTerritoriali) rf;
		}
		return ret;
	}

	public InfoAnagraficheULBean getInfoAnagrafiche(String idOst) throws SiraBusinessException {
		InfoAnagraficheULBean bean = new InfoAnagraficheULBean();
		OggettiStruttureTerritoriali ul = getOggetto(idOst);
		CaratterizzazioniOst caratterizzazione = OstUtils.chooseCaratterizzazione(ul.getCaratterizzazioniOsts());
		CcostUnitaLocali costUL = caratterizzazione.getCcostSoggettiGiuridici().getCcostUnitaLocali();
		CcostSoggettiGiuridici costSG = caratterizzazione.getCcostSoggettiGiuridici();
		bean.setCap(caratterizzazione.getUbicazioniOst().getCap());
		List<String> codAteco = new ArrayList<String>();
//		if (costUL.getAtecoByFkAteco1() != null) {
//			codAteco.add(costUL.getAtecoByFkAteco1().getAtecoOr());
//		}
//		if (costUL.getAtecoByFkAteco2() != null) {
//			codAteco.add(costUL.getAtecoByFkAteco2().getAtecoOr());
//		}
//		if (costUL.getAtecoByFkAteco3() != null) {
//			codAteco.add(costUL.getAtecoByFkAteco3().getAtecoOr());
//		}
//		if (costUL.getAtecoByFkAteco4() != null) {
//			codAteco.add(costUL.getAtecoByFkAteco4().getAtecoOr());
//		}
		bean.setCodAteco(codAteco);
		bean.setComune(caratterizzazione.getUbicazioniOst().getComune());
		bean.setNumeroRegistroImprese(costSG.getNumRegistroImprese());
		bean.setDataRegistroImprese(costSG.getDataIscrizioneRegImp());
		bean.setProvRegistroImprese(costSG.getProvRegistroImprese());
		bean.setDenominazione(caratterizzazione.getDenominazione());
		bean.setFax(costSG.getFax());
		bean.setIndrizzo(caratterizzazione.getUbicazioniOst().getNomeStrada());
		bean.setNumeroCivico(caratterizzazione.getUbicazioniOst().getNumeroCivico());
		bean.setLocalita(caratterizzazione.getUbicazioniOst().getLocalita());
		bean.setNumeroAddetti(String.valueOf(costUL.getNumeroAddetti()));
		bean.setTelefono(costSG.getTelefono());
		return bean;
	}

	public InfoTecnicheULBean getInfoTecniche(String idOst) throws SiraBusinessException {
		OggettiStruttureTerritoriali ul = getOggetto(idOst);
		InfoTecnicheULBean bean = new InfoTecnicheULBean();
		CaratterizzazioniOst caratterizzazione = OstUtils.chooseCaratterizzazione(ul.getCaratterizzazioniOsts());
		CcostUnitaLocali costUL = caratterizzazione.getCcostSoggettiGiuridici().getCcostUnitaLocali();
		bean.setSuperficieCopertaM2(costUL.getSuperficieCopertaM2());
		bean.setSuperficieScopertaNonpavM2(costUL.getSuperficieScopertaNonpavM2());
		bean.setSuperficieScopertaPavM2(costUL.getSuperficieScopertaPavM2());
		List<AttivitaEconomicheUL> attivita = new ArrayList<AttivitaEconomicheUL>();
//		if (costUL.getAttEcoSvoltas() != null) {
//			for (AttEcoSvolta attSvolta : costUL.getAttEcoSvoltas()) {
//				AttivitaEconomicheUL aeUL = new AttivitaEconomicheUL();
//				aeUL.setCodiceIppc(attSvolta.getCategoriaIppc().getCodice());
//				aeUL.setCodiceNace(attSvolta.getCategoriaNace().getCodice());
//				aeUL.setCodiceNose(attSvolta.getCategoriaNose().getCodice());
//				aeUL.setDataCessazione(attSvolta.getDataCessazione());
//				aeUL.setDataInizio(attSvolta.getDataInizio());
//				aeUL.setDescrizione(attSvolta.getDescrizione());
//				aeUL.setNote(attSvolta.getNote());
//				aeUL.setNumeroAddetti(attSvolta.getNumeroAddetti());
//				aeUL.setPeriodicitaAttiva(attSvolta.getPeriodicitaAttiva());
//				String mesi = "";
//				mesi.concat(attSvolta.getGen() != null ? attSvolta.getGen() + "|" : "");
//				mesi.concat(attSvolta.getFeb() != null ? attSvolta.getFeb() + "|" : "");
//				mesi.concat(attSvolta.getMar() != null ? attSvolta.getMar() + "|" : "");
//				mesi.concat(attSvolta.getApr() != null ? attSvolta.getApr() + "|" : "");
//				mesi.concat(attSvolta.getMag() != null ? attSvolta.getMag() + "|" : "");
//				mesi.concat(attSvolta.getGiu() != null ? attSvolta.getGiu() + "|" : "");
//				mesi.concat(attSvolta.getLug() != null ? attSvolta.getLug() + "|" : "");
//				mesi.concat(attSvolta.getAgo() != null ? attSvolta.getAgo() + "|" : "");
//				mesi.concat(attSvolta.getSett() != null ? attSvolta.getSett() + "|" : "");
//				mesi.concat(attSvolta.getOtt() != null ? attSvolta.getOtt() + "|" : "");
//				mesi.concat(attSvolta.getNov() != null ? attSvolta.getNov() + "|" : "");
//				mesi.concat(attSvolta.getDic() != null ? attSvolta.getDic() + "|" : "");
//				aeUL.setMesiInCuiSvolgeAttivita(mesi);
//				attivita.add(aeUL);
//			}
//		}
		List<ConsumoMateriePrimeUL> listaConsumi = new ArrayList<ConsumoMateriePrimeUL>();
//		if (costUL.getConsumoMatPrimes() != null) {
//			for (ConsumoMatPrime consumo : costUL.getConsumoMatPrimes()) {
//				ConsumoMateriePrimeUL materiePrime = new ConsumoMateriePrimeUL();
//				materiePrime.setConsumoAnnuo(consumo.getConsumoAnnuo());
//				materiePrime.setDescrizione(consumo.getDescrizione());
//				materiePrime.setProduttore(consumo.getProduttore());
//				materiePrime.setSchedaTecnica(consumo.getSchedaTecnica());
//				materiePrime.setTipo(consumo.getTipo() != null ? consumo.getTipo().getDescrizione() : null);
//				materiePrime.setUnitaMisura(consumo.getUmConsumoAnnuo() != null ? consumo.getUmConsumoAnnuo().getSigla() : null);
//				listaConsumi.add(materiePrime);
//			}
//		}
		bean.setAttivitaEconomiche(attivita);
		bean.setConsumoMateriePrime(listaConsumi);
		return bean;

	}
}
