package it.eng.utility.cryptosigner.data.util;

import java.security.Key;
import java.security.PublicKey;

import javax.xml.crypto.KeySelectorResult;

public class SimpleKeySelectorResult implements KeySelectorResult {
	
	private PublicKey pk;
	SimpleKeySelectorResult(PublicKey pk) {
	    this.pk = pk;
	}

	public Key getKey() { return pk; }
    }

