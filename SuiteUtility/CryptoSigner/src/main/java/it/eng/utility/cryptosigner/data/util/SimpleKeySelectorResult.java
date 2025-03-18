/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

