/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.List;

/**
 * Bean che rappresenta una constraint di una specifica entry.
 * <br>
 * Al proprio interno contiene un field che é la lista di stringhe che costituiscono la data constraint
 * @author upescato
 *
 */
public class EntityConstraint {
	
	private List<PropValue> propertyNames;
	
	public EntityConstraint() {
	}

	public List<PropValue> getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(List<PropValue> propertyNames) {
		this.propertyNames = propertyNames;
	}
}