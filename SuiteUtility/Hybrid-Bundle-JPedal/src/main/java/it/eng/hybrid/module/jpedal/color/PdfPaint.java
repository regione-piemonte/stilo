/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
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
 * PdfPaint.java
 * ---------------
*/
package it.eng.hybrid.module.jpedal.color;

import java.awt.*;

public interface PdfPaint extends Paint {

	public void setScaling(double cropX, double cropH, float scaling, float textX, float textY);
	
	public boolean isPattern();
	
	//constructor for pattern color
	public void setPattern(int dummy);
	
	public int getRGB();

    //added for HTML conversion
    public void setRenderingType(int createHtml);
}
