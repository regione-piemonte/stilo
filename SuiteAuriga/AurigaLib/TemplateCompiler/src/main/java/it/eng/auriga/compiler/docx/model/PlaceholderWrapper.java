/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper che permette omogeneità di interfaccia all'accesso della variabile
 * che contiene il placeholder e le direttive
 * 
 * @author massimo malvestio
 *
 */
public abstract class PlaceholderWrapper {

	/**
	 * Restituisce il valore della variabile presente nel wrapper, ovvero
	 * l'identificativo e le eventuali direttive, seprati da |*|
	 * 
	 * @return
	 */
	public abstract String getPlaceholderValue();

	/**
	 * Permette di impostare il valore della variabile presente nel wrapper
	 * 
	 * @param newValue
	 */
	public abstract void setPlaceholderValue(String newValue);

	/**
	 * 
	 * @return true se si tratta di un checkbox, false altrimenti
	 */
	public abstract boolean isCheckBox();

	public abstract boolean isText();

	public abstract boolean isRtfText();

	public abstract void injectValue(String value);

	/**
	 * Restituisce i valori della variabile presenti nel wrapper
	 * 
	 * @return
	 */
	public String[] getPlaceHolderValueContents() {

		String placeholderValue = getPlaceholderValue();

		if (placeholderValue == null) {
			return null;
		} else {
			return placeholderValue.split("\\|\\*\\|");
		}

	}

	public String getPlaceholderIdentifier() {

		String[] contents = getPlaceHolderValueContents();

		if (contents == null) {
			return null;
		} else {
			return contents[0];
		}

	}

	/**
	 * Estrae tutte le direttive definite nella variabile
	 * 
	 * @return
	 */
	public Set<DirectiveEnum> getDirectives() {

		Set<DirectiveEnum> retValue = new HashSet<DirectiveEnum>();

		String[] contents = getPlaceHolderValueContents();

		// posizione 0 c'è l'identificativo
		for (int i = 1; i < contents.length; i++) {

			DirectiveEnum directive = DirectiveEnum.get(contents[i]);

			if (directive != null) {
				retValue.add(directive);
			}

		}

		return retValue;
	}

	/**
	 * Espone la possibilità di rimuovere il controllo di tipo form dal
	 * documento. A seconda del tipo di controllo, le implementazioni, quindi le
	 * informazioni a applicare tale azione, potrebbero essere differenti
	 */
	public abstract void removeFromDocument();
}
