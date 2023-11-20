/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */



public class LuceneWordObject implements Comparable<LuceneWordObject>{
	
	private String term;

	private Integer count;
	
	private Boolean overflow;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int compareTo(LuceneWordObject o) {
		return count > o.count ? 1 : (count < o.count ? -1 : 0);
	}

	public Boolean getOverflow() {
		return overflow;
	}

	public void setOverflow(Boolean overflow) {
		this.overflow = overflow;
	}

}
