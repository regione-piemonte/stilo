/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class StringSplitterServer {

	private String[] tokens;
	private int currentPosition;
	private int maxPosition;

	public StringSplitterServer(String str, String regEx) {

		this.tokens = str.split(Pattern.quote(regEx));
		this.currentPosition = 0; 
		this.maxPosition = tokens.length-1;

	}

	public String[] getTokens() {
		return tokens;
	}

	public boolean hasMoreTokens() {
		return currentPosition <= maxPosition ? true : false;
	}

	public boolean hasMoreElements() {
		return hasMoreTokens();
	}
	
	public String nextToken() {
		if (currentPosition > maxPosition) {
			throw new NoSuchElementException();
		}
		return tokens[currentPosition++];
	}
	
	public int countTokens() {
		return tokens.length;
	}

}