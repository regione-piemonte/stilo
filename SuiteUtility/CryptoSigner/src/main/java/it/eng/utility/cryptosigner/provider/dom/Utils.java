/*
 * Copyright 2005 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
/*
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 */
/*
 * $Id: Utils.java 375655 2006-02-07 18:35:54Z mullan $
 */
package it.eng.utility.cryptosigner.provider.dom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Miscellaneous static utility methods for use in JSR 105 RI.
 *
 * @author Sean Mullan
 */
public final class Utils {

    private Utils() {}

    public static byte[] readBytesFromStream(InputStream is) 
	throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            int read = is.read(buf);
	    if (read == -1) { // EOF
		break;
	    }
            baos.write(buf, 0, read);
            if (read < 1024) {
                break;
            }
        }
        return baos.toByteArray();
    }

    /**
     * Converts an Iterator to a Set of Nodes, according to the XPath
     * Data Model.
     *
     * @param i the Iterator
     * @return the Set of Nodes
     */
    static Set toNodeSet(Iterator i) {
	Set nodeSet = new HashSet();
	while (i.hasNext()) {
	    Node n = (Node) i.next();
	    nodeSet.add(n);
	    // insert attributes nodes to comply with XPath
	    if (n.getNodeType() == Node.ELEMENT_NODE) {
	        NamedNodeMap nnm = n.getAttributes();
		for (int j = 0, length = nnm.getLength(); j < length; j++) {
		    nodeSet.add(nnm.item(j));
		}
	    }
	}
	return nodeSet;
    }

    /**
     * Returns the ID from a same-document URI (ex: "#id")
     */
    public static String parseIdFromSameDocumentURI(String uri) {
	if (uri.length() == 0) {
	    return null;
	}
	String id = uri.substring(1);
	if (id != null && id.startsWith("xpointer(id(")) {
            int i1 = id.indexOf('\'');
            int i2 = id.indexOf('\'', i1+1);
            id = id.substring(i1+1, i2);
	}
	return id;
    }

    /**
     * Returns true if uri is a same-document URI, false otherwise.
     */
    public static boolean sameDocumentURI(String uri) {
	return (uri != null && (uri.length() == 0 || uri.charAt(0) == '#'));
    }
}
