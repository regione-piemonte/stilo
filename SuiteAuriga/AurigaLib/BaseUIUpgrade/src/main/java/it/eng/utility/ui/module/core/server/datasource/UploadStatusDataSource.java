/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.StatusBean;

@Datasource(id="UploadStatusDataSource")
public class UploadStatusDataSource extends AbstractServiceDataSource<StatusBean, StatusBean>{

	@Override
	public StatusBean call(StatusBean bean) throws Exception {
		String smartId = bean.getSmartId();
		if (bean.isFinish()){
			getSession().setAttribute("caricato", 0);
			getSession().setAttribute("finito", false);
			getSession().removeAttribute("UploadServlet_NumFileInElaborazione_" + smartId);
			getSession().removeAttribute("UploadServlet_NumFileTotali_" + smartId);
			if(bean.isUploadCancellato()) {
				getSession().setAttribute("uploadCancellato", true);
			}
			return bean;
		}
		Integer caricato = getSession().getAttribute("caricato") !=null ? (Integer) getSession().getAttribute("caricato") : 0;
		Integer numero = getSession().getAttribute("numero") != null ? (Integer) getSession().getAttribute("numero") : 0;
		boolean finito = getSession().getAttribute("finito") != null ? (Boolean) getSession().getAttribute("finito") : false;
		Integer numFileInElaborazione = getSession().getAttribute("UploadServlet_NumFileInElaborazione_" + smartId) != null ? (Integer) getSession().getAttribute("UploadServlet_NumFileInElaborazione_" + smartId) : 0;
		Integer numFileTotali = getSession().getAttribute("UploadServlet_NumFileTotali_" + smartId) != null ? (Integer) getSession().getAttribute("UploadServlet_NumFileTotali_" + smartId) : 0;
		StatusBean lStatusBean = new StatusBean();
		lStatusBean.setSmartId(bean.getSmartId());
		lStatusBean.setCaricato(caricato);
		lStatusBean.setNumero(numero);
		lStatusBean.setFinito(finito);
		lStatusBean.setNumFileInElaborazione(numFileInElaborazione);
		lStatusBean.setNumFileTotali(numFileTotali);
		return lStatusBean;
	}

}
