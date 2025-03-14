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
* SwingFormButtonListener.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.acroforms;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;

/**
 * class implements MouseListener to create all required actions for the associated button
 *
 * @author chris
 */
public class SwingFormButtonListener implements MouseListener {

    private static final boolean showMethods = false;

    private Map captionChanger = null;

    /**
     * sets up the captions to change when needed
     */
    public SwingFormButtonListener(String normalCaption, String rolloverCaption, String downCaption) {
    	if(showMethods)
    		System.out.println("SwingFormButtonListener.SwingFormButtonListener(string string string)");
    	
    	int captions = 0;
        //set up the captions to work for rollover and down presses of the mouse
        captionChanger = new HashMap();
        if (rolloverCaption != null && rolloverCaption.length()>0){
            captionChanger.put("rollover", rolloverCaption);
            captions++;
        }
        if (downCaption != null && downCaption.length()>0){
            captionChanger.put("down", downCaption);
            captions++;
        }
        if(normalCaption!=null  && normalCaption.length()>0){
        	captionChanger.put("normal", normalCaption);
        	captions++;
        }
        
        if(captions==0)
        	captionChanger = null;
    }

    public void mouseEntered(MouseEvent e) {
        if (PDFListener.debugMouseActions || showMethods)
        	System.out.println("SwingFormButtonListener.mouseEntered()");

        if (captionChanger != null && e.getSource() instanceof AbstractButton) {
            if (captionChanger.containsKey("rollover")) {
                ((AbstractButton) e.getSource()).setText((String) captionChanger.get("rollover"));
            }
        }
    }

    public void mouseExited(MouseEvent e) {
        if (PDFListener.debugMouseActions || showMethods)
            System.out.println("customMouseListener.mouseExited()");

        if (captionChanger != null && e.getSource() instanceof AbstractButton) {
            if (captionChanger.containsKey("normal")) {
                ((AbstractButton) e.getSource()).setText((String) captionChanger.get("normal"));
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    	if(PDFListener.debugMouseActions || showMethods)
    		System.out.println("SwingFormButtonListener.mouseClicked()");
    }

    public void mousePressed(MouseEvent e) {
        if (PDFListener.debugMouseActions || showMethods)
            System.out.println("customMouseListener.mousePressed()");

        if (captionChanger != null && e.getSource() instanceof AbstractButton) {
            if (captionChanger.containsKey("down")) {
                ((AbstractButton) e.getSource()).setText((String) captionChanger.get("down"));
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (PDFListener.debugMouseActions || showMethods)
            System.out.println("customMouseListener.mouseReleased()");

        if (captionChanger != null && e.getSource() instanceof AbstractButton) {
            if (captionChanger.containsKey("rollover")) {
                ((AbstractButton) e.getSource()).setText((String) captionChanger.get("rollover"));
            } else if (captionChanger.containsKey("normal")){
                ((AbstractButton) e.getSource()).setText((String) captionChanger.get("normal"));
            }
        }
    }
}
