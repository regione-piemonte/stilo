/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class CountersListsBean {
	
	private int nOrderedListsModello;
	private int nUnorderedListsModello;
//	private int nItemListsModello;
	
	public CountersListsBean() {
		nOrderedListsModello = 0;
		nUnorderedListsModello = 0;
//		nItemListsModello = 0;
	}
	
	public int getnOrderedListsModello() {
		return nOrderedListsModello;
	}
	public int getnUnorderedListsModello() {
		return nUnorderedListsModello;
	}
	public void setnOrderedListsModello(int nOrderedListsModello) {
		this.nOrderedListsModello = nOrderedListsModello;
	}
	public void setnUnorderedListsModello(int nUnorderedListsModello) {
		this.nUnorderedListsModello = nUnorderedListsModello;
	}
//	public int getnItemListsModello() {
//		return nItemListsModello;
//	}
//	public void setnItemListsModello(int nItemListsModello) {
//		this.nItemListsModello = nItemListsModello;
//	}
	
	public void incrementNOrderedListsModello() {
		nOrderedListsModello++;
	}
	public void incrementNUnorderedListsModello() {
		nUnorderedListsModello++;
	}
//	public void incrementNItemListsModello() {
//		nItemListsModello++;
//	}
}
