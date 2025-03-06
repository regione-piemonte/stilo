package it.eng.dm.sira.dao;

import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.entity.VNormeLeggi;
import it.eng.dm.sira.entity.VRiferimentiNormativi;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.Normativa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DaoNormativa {

	private DaoNormeLeggi daoNormeLeggi = null;

	private DaoRiferimentiNormativi daoRiferimenti = null;

	private Logger log = Logger.getLogger(DaoNormativa.class);

	public List<Normativa> get() throws SiraBusinessException {
		List<Normativa> lista = new ArrayList<Normativa>();
		try {
			daoNormeLeggi = new DaoNormeLeggi();
			TFilterFetch<VNormeLeggi> filterFetchNorme = new TFilterFetch<VNormeLeggi>();
			VNormeLeggi filterNorme = new VNormeLeggi();
			filterNorme.setAttivo(new BigDecimal("1"));
			filterFetchNorme.setFilter(filterNorme);
			List<VNormeLeggi> listaNormeLeggi = daoNormeLeggi.search(filterFetchNorme).getData();
			for (VNormeLeggi normeLeggi : listaNormeLeggi) {
				Normativa norm = new Normativa();
				norm.setCodice(normeLeggi.getCodice());
				norm.setDescrizione(normeLeggi.getDescrizione());
				lista.add(norm);
			}
			daoRiferimenti = new DaoRiferimentiNormativi();
			TFilterFetch<VRiferimentiNormativi> filterFetchRif = new TFilterFetch<VRiferimentiNormativi>();
			VRiferimentiNormativi filterRif = new VRiferimentiNormativi();
			filterRif.setAttivo(new BigDecimal("1"));
			filterFetchRif.setFilter(filterRif);
			List<VRiferimentiNormativi> listaRif = daoRiferimenti.search(filterFetchRif).getData();
			for (VRiferimentiNormativi normeRif : listaRif) {
				Normativa norm = new Normativa();
				norm.setCodice(normeRif.getCodice());
				norm.setDescrizione(normeRif.getDescrizione());
				lista.add(norm);
			}
		} catch (Exception e) {
			log.debug("Impossibile ricavare le norme ", e);
			throw new SiraBusinessException("Impossibile ricavare le norme ", e);
		}
		return lista;
	}

}