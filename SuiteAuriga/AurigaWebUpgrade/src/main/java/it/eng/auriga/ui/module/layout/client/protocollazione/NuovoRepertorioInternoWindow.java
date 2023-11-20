/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class NuovoRepertorioInternoWindow extends ModalWindow {

	private NuovoRepertorioInternoWindow _window;

	private RepertorioDetailInterno portletLayout;

	public NuovoRepertorioInternoWindow(Record record, String title) {
		this(record, null, title, null);
	}
	public NuovoRepertorioInternoWindow(Record record, String title, Map<String, Object> initialValues) {
		this(record, null, title, initialValues);
	}

	public NuovoRepertorioInternoWindow(Record record, String taskName, String title, Map<String, Object> initialValues) {

		super("repertorioInternoWindow", true);

		setTitle(title);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		portletLayout = ProtocollazioneUtil.buildRepertorioDetailInterno(null, null, new ServiceCallback<Record>() {

			@Override
			public void execute(Record record) {
				afterRegistra(record);
			}
		});
		
		/**
		 * Se si deve preimpostare il Mittente allora il Destinarario va preimpostato come il mittente e con check “effettua assegnazione” spuntato.
		 */
		if (hasDefaultValue()) {
			if(getSelezioneUoProtocollanteValueMap().size() == 1) {
				String idUoSoggetto = getSelezioneUoProtocollanteValueMap().keySet().toArray(new String[1])[0];
				String descrizione = getSelezioneUoProtocollanteValueMap().get(idUoSoggetto);
				Record recordDestinatario = new Record();
				recordDestinatario.setAttribute("tipoDestinatario", "UOI");
				if (idUoSoggetto.startsWith("UO")) {
					recordDestinatario.setAttribute("idUoSoggetto", idUoSoggetto.substring(2));
					recordDestinatario.setAttribute("organigrammaDestinatario", idUoSoggetto);
				} else {
					recordDestinatario.setAttribute("idUoSoggetto", idUoSoggetto);
					recordDestinatario.setAttribute("organigrammaDestinatario", "UO" + idUoSoggetto);
				}
				recordDestinatario.setAttribute("codRapidoDestinatario", descrizione.substring(0, descrizione.indexOf(" - ")));
				recordDestinatario.setAttribute("denominazioneDestinatario", descrizione.substring(descrizione.indexOf(" - ") + 3));
				recordDestinatario.setAttribute("flgAssegnaAlDestinatario", true);
				recordDestinatario.setAttribute("flgPC", false);
				
				RecordList listaDestinatari = new RecordList();
				listaDestinatari.add(recordDestinatario);
				
				initialValues.put("listaDestinatari", listaDestinatari);
			}
		}
		
		portletLayout.nuovoDettaglio(null, initialValues);
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		setBody(portletLayout);

		setIcon("blank.png");
	}

	public abstract void  afterRegistra(Record record);
	
	/**
	 * Metodo che ritorna la mappa di tutte le UO di registrazione se non è settata una UO su cui è attiva la funzione di protocollo oppure una UO di lavoro predefinita, 
	 * in tal caso ritornerà solo quella se ho un'unica UO di registrazione il metodo ovviamente restituirà solo quella
	 */
	public boolean hasDefaultValue() {
		// se ho una sola UO collegata e il mittente è obbligatorio la setto lo stesso, anche se non è attiva la preimpostazione del mittente
		if(getUoProtocollanteValueMap().size() == 1) {
			return true;
		}
		return AurigaLayout.getParametroDBAsBoolean("PREIMP_UO_COME_MITT_PROT_UI") && (getSelezioneUoProtocollanteValueMap().size() == 1)
				&& (AurigaLayout.getIdUOPuntoProtAttivato() == null || "".equals(AurigaLayout.getIdUOPuntoProtAttivato()) );
	}
 
	protected LinkedHashMap<String, String> getUoProtocollanteValueMap() {
		// Provengo da una protocollazione in uscita o interna
		return AurigaLayout.getUoSpecificitaRegistrazioneUIValueMap();	
	}
	
	public LinkedHashMap<String, String> getSelezioneUoProtocollanteValueMap() {		
		return AurigaLayout.getSelezioneUoRegistrazioneValueMap("I");
	}
}
	