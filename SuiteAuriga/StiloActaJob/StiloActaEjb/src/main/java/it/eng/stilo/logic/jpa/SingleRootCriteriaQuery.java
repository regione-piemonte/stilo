/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * This class is just a container for the criteria query and the single root
 * specified to be used in the query itself. The criteria helper provides a
 * convenient method for creating a SingleRootCriteriaQuery.
 * 
 * @param <T>
 *            The type of the root.
 */
public class SingleRootCriteriaQuery<T> {

	/**
	 * Standard getter of the typed criteria query.
	 * 
	 * @return The typed criteria query.
	 */
	public final CriteriaQuery<T> getCriteria() {
		return criteria;
	}

	/**
	 * Standard setter for the criteria reference.
	 * 
	 * @param icriteria
	 *            The new criteria.
	 */
	public final void setCriteria(final CriteriaQuery<T> icriteria) {
		this.criteria = icriteria;
	}

	/**
	 * Standard getter of the root of type T.
	 * 
	 * @return The root of type T.
	 */
	public final Root<T> getRoot() {
		return root;
	}

	/**
	 * Standard setter for the root reference.
	 * 
	 * @param iroot
	 *            The new root.
	 */
	public final void setRoot(final Root<T> iroot) {
		this.root = iroot;
	}

	/**
	 * The reference to the type criteria query.
	 */
	private CriteriaQuery<T> criteria;

	/**
	 * The reference to the root of type T.
	 */
	private Root<T> root;

}
