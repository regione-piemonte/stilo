/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
* Main Developer: Simon Barnett
*
* 	This file is part of JPedal
*
* Copyright (c) 2008, IDRsolutions
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the IDRsolutions nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY IDRsolutions ``AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL IDRsolutions BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* Other JBIG2 image decoding implementations include
* jbig2dec (http://jbig2dec.sourceforge.net/)
* xpdf (http://www.foolabs.com/xpdf/)
* 
* The final draft JBIG2 specification can be found at http://www.jpeg.org/public/fcd14492.pdf
* 
* All three of the above resources were used in the writing of this software, with methodologies,
* processes and inspiration taken from all three.
*
* ---------------
* RegionSegment.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.io.jbig2;

import java.io.IOException;

public abstract class RegionSegment extends Segment {
	protected int regionBitmapWidth, regionBitmapHeight;
	protected int regionBitmapXLocation, regionBitmapYLocation;

	protected RegionFlags regionFlags = new RegionFlags();

	public RegionSegment(JBIG2StreamDecoder streamDecoder) {
		super(streamDecoder);
	}

	public void readSegment() throws IOException, JBIG2Exception {
		short[] buff = new short[4];
		decoder.readByte(buff);
		regionBitmapWidth = BinaryOperation.getInt32(buff);

		buff = new short[4];
		decoder.readByte(buff);
		regionBitmapHeight = BinaryOperation.getInt32(buff);

		if (JBIG2StreamDecoder.debug)
			System.out.println("Bitmap size = " + regionBitmapWidth + 'x' + regionBitmapHeight);

		buff = new short[4];
		decoder.readByte(buff);
		regionBitmapXLocation = BinaryOperation.getInt32(buff);

		buff = new short[4];
		decoder.readByte(buff);
		regionBitmapYLocation = BinaryOperation.getInt32(buff);

		if (JBIG2StreamDecoder.debug)
			System.out.println("Bitmap location = " + regionBitmapXLocation + ',' + regionBitmapYLocation);

		/** extract region Segment flags */
		short regionFlagsField = decoder.readByte();

		regionFlags.setFlags(regionFlagsField);

		if (JBIG2StreamDecoder.debug)
			System.out.println("region Segment flags = " + regionFlagsField);
	}
}
