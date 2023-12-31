/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU General Public License Version 2 only ("GPL") or the Common Development and Distribution
 * License("CDDL") (collectively, the "License"). You may not use this file except in compliance with the License. You can obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html or packager/legal/LICENSE.txt. See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception: Oracle designates this particular file as subject to the "Classpath" exception as provided by Oracle in the GPL Version 2 section of
 * the License file that accompanied this code.
 *
 * Modifications: If applicable, add the following below the License Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s): If you wish your version of this file to be governed by only the CDDL or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution under the [CDDL or GPL Version 2] license." If you don't indicate a single choice of
 * license, a recipient has the option to distribute your version of this file under either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above. However, if you add GPL Version 2 code and therefore, elected the GPL Version 2 license, then the option applies only if the
 * new code is made subject to such option by the copyright holder.
 */

package org.jvnet.mimepull;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * InternetHeaders is a utility class that manages RFC822 style headers. Given an RFC822 format message stream, it reads lines until the blank line that
 * indicates end of header. The input stream is positioned at the start of the body. The lines are stored within the object and can be extracted as either
 * Strings or {@link Header} objects.
 * <p>
 * <p/>
 * This class is mostly intended for service providers. MimeMessage and MimeBody use this class for holding their headers.
 * <p>
 * <p/>
 * <hr>
 * <strong>A note on RFC822 and MIME headers</strong>
 * <p>
 * <p/>
 * RFC822 and MIME header fields <strong>must</strong> contain only US-ASCII characters. If a header contains non US-ASCII characters, it must be encoded as per
 * the rules in RFC 2047. The MimeUtility class provided in this package can be used to to achieve this. Callers of the <code>setHeader</code>,
 * <code>addHeader</code>, and <code>addHeaderLine</code> methods are responsible for enforcing the MIME requirements for the specified headers. In addition,
 * these header fields must be folded (wrapped) before being sent if they exceed the line length limitation for the transport (1000 bytes for SMTP). Received
 * headers may have been folded. The application is responsible for folding and unfolding headers as appropriate.
 * <p>
 *
 * @author John Mani
 * @author Bill Shannon
 */
final class InternetHeaders {

	private final FinalArrayList<hdr> headers = new FinalArrayList<hdr>();

	/**
	 * Read and parse the given RFC822 message stream till the blank line separating the header from the body. Store the header lines inside this
	 * InternetHeaders object.
	 * <p>
	 * <p/>
	 * Note that the header lines are added into this InternetHeaders object, so any existing headers in this object will not be affected.
	 *
	 * @param lis
	 *            RFC822 input stream
	 */
	InternetHeaders(MIMEParser.LineInputStream lis) {
		// Read header lines until a blank line. It is valid
		// to have BodyParts with no header lines.
		String line;
		String prevline = null; // the previous header line, as a string
		// a buffer to accumulate the header in, when we know it's needed
		StringBuffer lineBuffer = new StringBuffer();

		try {
			// while ((line = lis.readLine()) != null) {
			do {
				line = lis.readLine();
				if (line != null && (line.startsWith(" ") || line.startsWith("\t"))) {
					// continuation of header
					if (prevline != null) {
						lineBuffer.append(prevline);
						prevline = null;
					}
					lineBuffer.append("\r\n");
					lineBuffer.append(line);
				} else {
					// new header
					if (prevline != null)
						addHeaderLine(prevline);
					else if (lineBuffer.length() > 0) {
						// store previous header first
						addHeaderLine(lineBuffer.toString());
						lineBuffer.setLength(0);
					}
					prevline = line;
				}
			} while (line != null && line.length() > 0);
		} catch (IOException ioex) {
			throw new MIMEParsingException("Error in input stream", ioex);
		}
	}

	/**
	 * Return all the values for the specified header. The values are String objects. Returns <code>null</code> if no headers with the specified name exist.
	 *
	 * @param name
	 *            header name
	 * @return array of header values, or null if none
	 */
	List<String> getHeader(String name) {
		// XXX - should we just step through in index order?
		FinalArrayList<String> v = new FinalArrayList<String>(); // accumulate return values

		int len = headers.size();
		for (int i = 0; i < len; i++) {
			hdr h = (hdr) headers.get(i);
			if (name.equalsIgnoreCase(h.name)) {
				v.add(h.getValue());
			}
		}
		return (v.size() == 0) ? null : v;
	}

	/**
	 * Return all the headers as an Enumeration of {@link Header} objects.
	 *
	 * @return Header objects
	 */
	FinalArrayList<? extends Header> getAllHeaders() {
		return headers; // conceptually it should be read-only, but for performance reason I'm not wrapping it here
	}

	/**
	 * Add an RFC822 header line to the header store. If the line starts with a space or tab (a continuation line), add it to the last header line in the list.
	 * <p>
	 * <p/>
	 * Note that RFC822 headers can only contain US-ASCII characters
	 *
	 * @param line
	 *            raw RFC822 header line
	 */
	void addHeaderLine(String line) {
		try {
			char c = line.charAt(0);
			if (c == ' ' || c == '\t') {
				hdr h = (hdr) headers.get(headers.size() - 1);
				h.line += "\r\n" + line;
			} else
				headers.add(new hdr(line));
		} catch (StringIndexOutOfBoundsException e) {
			// line is empty, ignore it
			return;
		} catch (NoSuchElementException e) {
			// XXX - vector is empty?
		}
	}

	void changeHeaderLine(String headerName, int partIdx) {
		try {
			int len = headers.size();
			for (int i = 0; i < len; i++) {
				hdr h = (hdr) headers.get(i);
				if (headerName.equalsIgnoreCase(h.name)) {
					String value = h.getValue();
					if (value != null) {
						boolean hasParentesiAng = false;
						if (value.startsWith("<")) {
							value = value.substring(1, value.length() - 1);
							hasParentesiAng = true;
						}
						if (!value.startsWith("#")) {
							String newValue = String.format("#%03d#%s", partIdx, value);
							if (hasParentesiAng)
								newValue = String.format("<%s>", newValue);
							headers.set(i, new hdr(String.format("%s: %s", h.name, newValue)));
						}
					}
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			// line is empty, ignore it
			return;
		} catch (NoSuchElementException e) {
			// XXX - vector is empty?
		}
	}

}

/*
 * A private utility class to represent an individual header.
 */

class hdr implements Header {

	String name; // the canonicalized (trimmed) name of this header
	// XXX - should name be stored in lower case?
	String line; // the entire RFC822 header "line"

	/*
	 * Constructor that takes a line and splits out the header name.
	 */
	hdr(String l) {
		int i = l.indexOf(':');
		if (i < 0) {
			// should never happen
			name = l.trim();
		} else {
			name = l.substring(0, i).trim();
		}
		line = l;
	}

	/*
	 * Constructor that takes a header name and value.
	 */
	hdr(String n, String v) {
		name = n;
		line = n + ": " + v;
	}

	/*
	 * Return the "name" part of the header line.
	 */
	public String getName() {
		return name;
	}

	/*
	 * Return the "value" part of the header line.
	 */
	public String getValue() {
		int i = line.indexOf(':');
		if (i < 0)
			return line;

		int j;
		if (name.equalsIgnoreCase("Content-Description")) {
			// Content-Description should retain the folded whitespace after header unfolding -
			// rf. RFC2822 section 2.2.3, rf. RFC2822 section 3.2.3
			for (j = i + 1; j < line.length(); j++) {
				char c = line.charAt(j);
				if (!(/* c == ' ' || */c == '\t' || c == '\r' || c == '\n'))
					break;
			}
		} else {
			// skip whitespace after ':'
			for (j = i + 1; j < line.length(); j++) {
				char c = line.charAt(j);
				if (!(c == ' ' || c == '\t' || c == '\r' || c == '\n'))
					break;
			}
		}
		return line.substring(j);
	}
}
