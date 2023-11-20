/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiDetail;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

/**
 * Visualizza la lista degli atti personali
 * 
 * @author massimo malvestio
 *
 */
public class AttiPersonaliLayout extends AttiLayout {
		
	public AttiPersonaliLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public AttiPersonaliLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public AttiPersonaliLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public AttiPersonaliLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, new GWTRestDataSource("AttiPersonaliDatasource", "idProcedimento", FieldType.TEXT), createFilter(nomeEntita), new AttiPersonaliList(nomeEntita), new AttiDetail(
				nomeEntita), finalita, flgSelezioneSingola, showOnlyDetail);
	}

	private static AttiPersonaliFilter createFilter(String nomeEntita) {

		Map<String, String> extraParam = new HashMap<String, String>();

		extraParam.put("showFilterSmistamentoAtti", Boolean.toString(isAttivoSmistamentoAtti()));

		return new AttiPersonaliFilter(nomeEntita, extraParam);
	}

	@Override
	public boolean isForcedToAutoSearch() {
		return false;
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		MultiToolStripButton[] multiselectButtons = super.getMultiselectButtons();
		List<MultiToolStripButton> listaMultiselectButtons = new ArrayList<MultiToolStripButton>();
		if(multiselectButtons != null && multiselectButtons.length > 0) {
			listaMultiselectButtons.addAll(Arrays.asList(multiselectButtons));
		}
		
		return listaMultiselectButtons.toArray(new MultiToolStripButton[listaMultiselectButtons.size()]);

	}
}