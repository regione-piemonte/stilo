/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;

/**
 * This class contains the parameters to be used when creating a paged query or
 * result set, no matter of the content of the result set.This class is used
 * generally in the EJB that access the database using the JPA API but can
 * easily adopted in other contexts.
 * 
 */
@SuppressWarnings("serial")
public class QueryAttributes implements Serializable {

	/**
	 * The name of the field in the metamodel/fields list to be used for
	 * sorting.
	 */
	private String orderField = "";

	/**
	 * The specified order direction for the JPA queries.
	 */
	private SortMode sortMode = SortMode.ASCENDING;

	/**
	 * The default order direction for the JPA queries.
	 */
	private SortMode defaultSortMode = SortMode.ASCENDING;

	/**
	 * Default limit mode to be applied to the JPA queries.
	 */
	private LimitMode limitMode = LimitMode.LIMITED;

	/**
	 * The initial offset to be used when specifying the limits of the queries.
	 */
	private Integer offset = 0;

	/**
	 * The max number of results to be read from the data source with the query.
	 * Deafult valu is unlimited.
	 */
	private Integer max = Integer.MAX_VALUE;

	/**
	 * The default attribute of the JPA metamodel to be used in the query for
	 * sorting.
	 */
	private SingularAttribute<? extends Object, ? extends Object> defaultOrderField = null;

	/**
	 * Standard getter for the order field in the metamodel.
	 * 
	 * @return the order field name
	 */
	public final String getOrderField() {
		return orderField;
	}

	/**
	 * Standard setter for the order field to be used when sorting.
	 * 
	 * @param iorderField
	 *            The name of the metamodel attribute.
	 */
	public final void setOrderField(final String iorderField) {
		this.orderField = iorderField;
	}

	/**
	 * Standard getter for the order mode/direction to be used.
	 * 
	 * @return the order direction.
	 */
	public final SortMode getSortMode() {
		return sortMode;
	}

	/**
	 * Standard setter for the order mode to be used when sorting.
	 * 
	 * @param iorderMode
	 *            The sort mode to be used.
	 */
	public final void setSortMode(final SortMode iorderMode) {
		this.sortMode = iorderMode;
	}

	/**
	 * Standard getter for the limit mode to be used.
	 * 
	 * @return the limit mode value.
	 */
	public final LimitMode getLimitMode() {
		return limitMode;
	}

	/**
	 * Standard setter for the limit mode to be used.
	 * 
	 * @param ilimitMode
	 *            the new limit mode value.
	 */
	public final void setLimitMode(final LimitMode ilimitMode) {
		this.limitMode = ilimitMode;
	}

	/**
	 * Standard getter for the offset to be used.
	 * 
	 * @return the query offset.
	 */
	public final Integer getOffset() {
		return offset;
	}

	/**
	 * Standard setter for the offset to be used.
	 * 
	 * @param ioffset
	 *            the query offset.
	 */
	public final void setOffset(final Integer ioffset) {
		this.offset = ioffset;
	}

	/**
	 * Standard getter for the max number of results to be read.
	 * 
	 * @return the max number of records.
	 */
	public final Integer getMax() {
		return max;
	}

	/**
	 * Standard setter for the max number of results to be read.
	 * 
	 * @param imax
	 *            the max number of records.
	 */
	public final void setMax(final Integer imax) {
		this.max = imax;
	}

	/**
	 * Standard getter for the default order field of the metamodel to be used
	 * in the query.
	 * 
	 * @return the default order field.
	 */
	public final SingularAttribute<? extends Object, ? extends Object> getDefaultOrderField() {
		return defaultOrderField;
	}

	/**
	 * Standard setter for the default order field of the metamodel to be used
	 * in the query.
	 * 
	 * @param idefaultOrderField
	 *            the default order field.
	 */
	public final void setDefaultOrderField(
			final SingularAttribute<? extends Object, ? extends Object> idefaultOrderField) {
		this.defaultOrderField = idefaultOrderField;
	}

	/**
	 * Standard getter for the default sort direction to be used in the query.
	 * 
	 * @return the default sort direction.
	 */
	public final SortMode getDefaultSortMode() {
		return defaultSortMode;
	}

	/**
	 * Standard setter for the default sort direction to be used in the query.
	 * 
	 * @param idefaultOrderMode the default sort direction.
	 */
	public final void setDefaultSortMode(final SortMode idefaultOrderMode) {
		this.defaultSortMode = idefaultOrderMode;
	}

}
