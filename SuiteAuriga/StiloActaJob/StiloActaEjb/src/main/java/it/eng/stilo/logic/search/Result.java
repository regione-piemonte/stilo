/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.criteria.CriteriaQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is a container for a complete result set with all the attributes
 * that can be used by a web app or an EJB such as the pager object, the query
 * attributes (sort order, pages), the original query and a map for results set
 * details.
 * 
 * @param <T>
 *            The type of the result set.
 */

/**
 * @author a530772
 * 
 * @param <T>
 */
@SuppressWarnings("serial")
public class Result<T> implements Serializable {

	/**
	 * Reference to the typed list of the result set entries/records.
	 */
	private List<T> entries;

	/**
	 * The original query attributes specified in the constructor. Can be null.
	 */
	private QueryAttributes queryAttributes;

	/**
	 * It's the size of the result set. If the result is paged, then size should
	 * be the total results that match the query while the entries are specified
	 * by the page size in the query attributes.
	 */
	private Integer size = 0;

	/**
	 * The elapsed time for the result set building. If it is a result of a
	 * query, this value should be the time of the query excution.
	 */
	private Long elapsed = 0L;

	/**
	 * This is a map used for enriching the result set with additional
	 * information such as contextual external parameters.
	 */
	private Map<String, Object> details;

	/**
	 * If it is a JPA query result set, this is the reference to the original
	 * criteria query used for retrieving the result set.
	 */
	private CriteriaQuery<T> criteriaQuery;

	/**
	 * Default constructor. Needs the query attribute parameter. The parameter
	 * can be null (means no sort/paging on the query).
	 * 
	 * @param attrs
	 *            The query attributes specification.
	 */
	public Result(final QueryAttributes attrs) {
		super();
		this.entries = new ArrayList<T>();
		if (attrs != null) {
			this.queryAttributes = attrs;
		}
	}

	/**
	 * Constructor from an existing list.
	 * 
	 * @param list
	 *            The list to be used.
	 * @param attrs
	 *            The query attributes specification.
	 */
	public Result(final List<T> list, final QueryAttributes attrs) {
		super();
		this.entries = list;
		this.size = list.size();
		if (attrs != null) {
			this.queryAttributes = attrs;
		}
	}

	/**
	 * Standard getter for the size of the result set. When paging is defined in
	 * the query attributes, this method should return the full size of the
	 * entire result set while the entries are only one page of the set.
	 * 
	 * @return The full size of the result set.
	 */
	public final Integer getSize() {
		return size;
	}

	/**
	 * Standard setter for the size property of the result set.
	 * 
	 * @param isize
	 *            The size of the result set.
	 */
	public final void setSize(final Integer isize) {
		this.size = isize;
	}

	/**
	 * Standard getter for the entries of the result set. When paged, it's
	 * composed only by a number of entries corresponding to the page size
	 * defined in the query attribute object.
	 * 
	 * @return The entries of the result set.
	 */
	public final List<T> getEntries() {
		return entries;
	}

	/**
	 * Standard setter for the list of entries of the result set.
	 * 
	 * @param ientries
	 *            The entries of the result set.
	 */
	public final void setEntries(final List<T> ientries) {
		this.entries = ientries;
	}

	/**
	 * This method give the expected number of pages on the basis of the query
	 * attributes specification. If no query attribute is specified then the
	 * current page is always set to 0.
	 * 
	 * @return The current page in the paged result set.
	 */
	public final Integer getCurrentPage() {
		if (queryAttributes == null) {
			return 0;
		}
		return queryAttributes.getOffset() / queryAttributes.getMax();
	}

	/**
	 * Standard getter for the elapsed time for creating the result set.
	 * 
	 * @return The elapsed time in millis.
	 */
	public final Long getElapsed() {
		return elapsed;
	}

	/**
	 * Standard setter for the elapsed time property.
	 * 
	 * @param ielapsed
	 *            the new value for the elapsed time.
	 */
	public final void setElapsed(final Long ielapsed) {
		this.elapsed = ielapsed;
	}

	/**
	 * Standard lazy getter for the details map of the result set.
	 * 
	 * @return The reference to the details map.
	 */
	public final Map<String, Object> getDetails() {
		if (details == null) {
			details = new TreeMap<String, Object>();
		}
		return details;
	}

	/**
	 * This method calculate the number of pages on the basis of the query
	 * attributes specification. If no query attribute is defined then the
	 * number of pages is set to 1.
	 * 
	 * @return The pages number.
	 */
	public final int getPages() {
		if (queryAttributes == null) {
			return 1;
		}
		return (int) Math.ceil((1.0 * size) / queryAttributes.getMax());
	}

	/**
	 * Standard getter for the criteria query that originated the result set.
	 * The criteria can be execute in another context.
	 * 
	 * @return The criteria
	 */
	public final CriteriaQuery<T> getCriteriaQuery() {
		return criteriaQuery;
	}

	/**
	 * Standard setter for the criteria query that originated the result set.
	 * 
	 * @param icriteriaQuery
	 *            The new value of the criteria query.
	 */
	public final void setCriteriaQuery(final CriteriaQuery<T> icriteriaQuery) {
		this.criteriaQuery = icriteriaQuery;
	}

	@Override
	public final String toString() {
		return "Result [size=" + size + ", elapsed=" + elapsed + " results=" + entries.size() + "]";
	}

}
