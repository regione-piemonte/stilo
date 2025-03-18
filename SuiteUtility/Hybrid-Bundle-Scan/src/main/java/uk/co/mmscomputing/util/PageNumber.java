/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class PageNumber {
	private static PageNumber pageNumber = null;
	private int pgNum = -1;
	public PageNumber() {
		pgNum = 0;
	}

	public synchronized void cleanNumeration() {
		pgNum = 0;
	}

	public synchronized int getPageNumber() {
		pgNum ++;
		return pgNum ;
	}

	public static synchronized PageNumber getInstance() {
		if(pageNumber == null)
			pageNumber = new PageNumber();
		return pageNumber;
	}	
}
