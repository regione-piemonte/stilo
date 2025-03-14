/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2007, IDRsolutions and Contributors.
 *
 * 	This file is part of JPedal
 *
     This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


  *
  * ---------------

  * JBIG2.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.io.jbig2;

public class JBIG2 {

/**
	 * JBIG decode using our own class
	 *
     */
	static public byte[] JBIGDecode(byte[] data, byte[] globalData) throws Exception {

		JBIG2Decoder decoder = new JBIG2Decoder();

		if (globalData != null && globalData.length > 0) {
			decoder.setGlobalData(globalData);
		}

		decoder.decodeJBIG2(data);

		data = decoder.getPageAsJBIG2Bitmap(0).getData(true);
		/**/


        return data;

    }

}
