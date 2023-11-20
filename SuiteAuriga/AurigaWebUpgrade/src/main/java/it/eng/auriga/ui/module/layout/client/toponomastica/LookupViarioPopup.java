/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSOHelper;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * @author cristiano
 *
 */
public abstract class LookupViarioPopup extends ModalWindow {

	private LookupViarioPopup _window;

	private TrovaVieLayout portletLayout;

	public LookupViarioPopup(final String indirizzoFilter, String finalita, boolean flgSelezioneSingola) {
		this(indirizzoFilter, null, finalita, flgSelezioneSingola);
	}
	
	public LookupViarioPopup(final String indirizzoFilter, final String comuneFilter, String finalita, boolean flgSelezioneSingola) {
		
		super("trova_vie", true);

		setTitle("Viario");

		_window = this;

		portletLayout = new TrovaVieLayout(comuneFilter, finalita, flgSelezioneSingola) {
			
			@Override
			public void lookupBack(Record selectedRecord) {
				
				manageLookupBack(selectedRecord);
				_window.markForDestroy();
			}

			@Override
			public void multiLookupBack(Record record) {
				
				manageMultiLookupBack(record);
			}

			@Override
			public void multiLookupUndo(Record record) {
				
				manageMultiLookupUndo(record);
			}

			@Override
			public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, boolean autoSearch) {
				LinkedHashMap<String, Criterion> mapCriterion = new LinkedHashMap<String, Criterion>();
				if (criteria != null && criteria.getCriteria() != null) {
					for (int i = 0; i < criteria.getCriteria().length; i++) {
						Criterion criterion = criteria.getCriteria()[i];
						mapCriterion.put(criterion.getFieldName(), criterion);
					}
				}

				if (indirizzoFilter != null && !"".equals(indirizzoFilter)) {
					HashMap value = new HashMap();
					value.put("parole", indirizzoFilter);
					Criterion crit = new Criterion("descrNome", OperatorId.WORDS_START_WITH);
					JSOHelper.setAttribute(crit.getJsObj(), "value", value);
					mapCriterion.put("descrNome", crit);
				}

				super.setCriteriaAndFirstSearch(new AdvancedCriteria(OperatorId.AND, mapCriterion.values().toArray(new Criterion[0])),
						(indirizzoFilter != null || autoSearch));

			}

			@Override
			public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
				if (indirizzoFilter != null || !"".equals(indirizzoFilter)) {
					setCriteriaAndFirstSearch(new AdvancedCriteria(), autosearch);
				} else {
					super.setDefaultCriteriaAndFirstSearch(autosearch);
				}
			}
		};

		portletLayout.setLookup(true);

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("lookup/indirizzo.png");
	}

	public abstract void manageLookupBack(Record record);

	public abstract void manageMultiLookupBack(Record record);

	public abstract void manageMultiLookupUndo(Record record);

}
