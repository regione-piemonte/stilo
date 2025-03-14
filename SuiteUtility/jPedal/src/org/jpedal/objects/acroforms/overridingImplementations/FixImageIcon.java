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
* FixImageIcon.java
* ---------------
*/
package org.jpedal.objects.acroforms.overridingImplementations;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.SwingConstants;

import org.jpedal.objects.raw.FormObject;


public class FixImageIcon extends CustomImageIcon implements Icon, SwingConstants {
    
    private static final long serialVersionUID = 8946195842453749725L;
    
    
    /** -1 means only one image,<br>0 means unselected,<br>1 means selected
     * <br>if there is only one image it is stored as the selected image
     */
    private int selected = -1;
    private static final int UNSELECTEDICON = 0;
    private static final int SELECTEDICON = 1;

    /** stores the final image after any icon rotation */
    private BufferedImage imageSelected=null,imageUnselected=null;
    
    
	
	/** constructor to be used for one image */
	public FixImageIcon(BufferedImage img,int iconRot) {
		super(iconRot);
		
        if(img!=null)
        	imageSelected = img;
        else
        	//if null store opaque image
        	imageSelected = FormObject.getOpaqueImage();
        
        selected = -1;
    }
	
	/** constructor for 2 images to be used for multipul pressed images, 
	 * sel should be 1 if its currently selected, 0 if unselected */
    public FixImageIcon(BufferedImage selImg,BufferedImage unselImg,int iconRot,int sel) {
    	super(iconRot);
		
    	imageSelected = selImg;
        imageUnselected = unselImg;
        selected = sel;
    }
    
    
    /** returns the currently selected Image*/
    public Image getImage(){
		Image image;
		
		switch(selected){
		case UNSELECTEDICON: 
			image = imageUnselected; 
			break;
		default: //or SELECTEDICON
			image = imageSelected; 
			break;
		}
		
		
		
		return image;
	}
    
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
    	
    	BufferedImage image = (BufferedImage) getImage();
		
		if (image == null)
			return;

		if (c.isEnabled()) {
			g.setColor(c.getBackground());
		} else {
			g.setColor(Color.gray);
		}

		
		Graphics2D g2 = (Graphics2D) g;
		if (iconWidth > 0 && iconHeight > 0) {
			
			int drawWidth = iconWidth;
			int drawHeight = iconHeight;
			boolean rotateIcon = false;
			if((iconRotation==270 || iconRotation==90)){
				//mark to rotate the x and y positions later
				rotateIcon = true;
				
				//swap width and height so that the image is drawn in the corect orientation
				//without changing the raw width and height for the icon size
				drawWidth = iconHeight;
				drawHeight = iconWidth;
			}
			
			
			//now work out the x,y position to keep the icon in the centre of the icon
			int posX=0,posY=0;
			
			int finalRotation;
			if(displaySingle){
				finalRotation = validateRotationValue(pageRotate - iconRotation);
			}else {
				finalRotation = pageRotate;
			}
			
			/** with new decode at needed size code the resize (drawImage) may not be needed. */
			if (finalRotation ==270) {
				g2.rotate(-Math.PI / 2);
				g2.translate(-drawWidth, 0);
				g2.drawImage(image, -posX, posY, drawWidth, drawHeight, null);
			} else if (finalRotation == 90) {
				g2.rotate(Math.PI / 2);
				g2.translate(0, -drawHeight);
				g2.drawImage(image, posX, -posY, drawWidth, drawHeight, null);
			} else if (finalRotation == 180) {
				g2.rotate(Math.PI);
				g2.translate(-drawWidth, -drawHeight);
				g2.drawImage(image, -posX, -posY, drawWidth, drawHeight, null);
			}else {
				g2.drawImage(image, posX, posY, drawWidth, drawHeight, null);
			}
		} else
			g2.drawImage(image, 0, 0, null);

		g2.translate(-x, -y);
	}


    /** if this imageicon was constructed for use with one image this will do nothing,
     * <br>otherwise it will set the selected image to be the selected image if the flag is true
     * or the unseleced image if the flag is false.
     */
	public void swapImage(boolean selectedImage) {
		if(selected==-1)
			return;
		
		if(selectedImage)
			selected = SELECTEDICON;
		else
			selected = UNSELECTEDICON;
	}
	
	/**generates higher quality images */
	public void setPrinting(boolean print,int multiplier){
		
	}

	public void setImageIcon(BufferedImage imageSpace) {
		
		imageSelected = imageSpace;
		selected = -1;
		iconRotation=0;
        iconOpp = iconRotation - 180;
		if(iconOpp<0)
			iconOpp+=360;
		
	}
}
