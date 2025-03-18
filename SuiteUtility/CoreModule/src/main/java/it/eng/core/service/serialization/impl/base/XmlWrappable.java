/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

/**
 * Wrapper per i tipi base (String byte etc.) che non sono serializzabili direttamnte da JAXB.
 * Chi implementa XmlWrapper deve farlo con un bean serializzabile da JAXB mentre il valore originale
 * è prelevato da getData.
 * 
 *
 */
public interface XmlWrappable<T extends Serializable> {

	public T getData();
	public void setData(T data);
}
