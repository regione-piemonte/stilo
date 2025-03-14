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
* SwingFormFactory.java
* ---------------
*/
package org.jpedal.objects.acroforms.creation;


import org.jpedal.color.DeviceCMYKColorSpace;

import javax.imageio.ImageIO;
import org.jpedal.PdfDecoder;
import org.jpedal.gui.ShowGUIMessage;
import org.jpedal.io.PdfObjectReader;
import org.jpedal.objects.acroforms.actions.ActionFactory;
import org.jpedal.objects.acroforms.actions.ActionHandler;
import org.jpedal.objects.acroforms.actions.SwingActionFactory;
import org.jpedal.objects.acroforms.actions.SwingListener;
import org.jpedal.objects.raw.*;
import org.jpedal.objects.acroforms.formData.ComponentData;
import org.jpedal.objects.acroforms.formData.GUIData;
import org.jpedal.objects.acroforms.formData.SwingData;
import org.jpedal.objects.acroforms.overridingImplementations.FixImageIcon;
import org.jpedal.objects.acroforms.overridingImplementations.ReadOnlyTextIcon;
import org.jpedal.objects.acroforms.rendering.AcroRenderer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import org.jpedal.objects.acroforms.utils.ConvertToString;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.Strip;

import java.util.Iterator;
import java.util.Map;


public class SwingFormFactory extends GenericFormFactory implements FormFactory{
	
    
	private static final boolean showAppearanceImages = false;//show the images that are being added to the fields as they are added.

    /**
     * handle on AcroRenderer needed for adding mouse listener
     */
    private AcroRenderer acrorend;

    //prints all debugging information
    private boolean printAllouts = false;
    //used when only one method needs debugging,
    //see create(Button or choice or text) first line /**/
    private boolean printouts = printAllouts;

    /**
     * just stops user over-riding
     */
    //private SwingFormFactory() {}

    /**
     * allows access to renderer variables
     *
     */
    public SwingFormFactory(AcroRenderer acroRenderer, ActionHandler actionHandler/*,PdfObjectReader pdfFile*/) {
        acrorend = acroRenderer;
        formsActionHandler = actionHandler;
//        currentPdfFile = pdfFile;
    }

    public void reset(AcroRenderer acroRenderer, ActionHandler actionHandler) {
        
    	acrorend = acroRenderer;
        formsActionHandler = actionHandler;
        
    }

    


    /**
     * setup annotations display with popups, etc
     */
    public Object annotationButton(final FormObject form) {
        
        //point where testActions breaks - ignore this halting error as it is within the testActions flag.
        JButton but = new JButton();
        
        setupButton(but, form);
        setupUniversalFeatures(but, form);
        
        int subtype=form.getParameterConstant(PdfDictionary.Subtype);
        
        boolean newAnnots = true;
        
        /**
         * @kieran - there are several types of annotation (Underline, highlight, Ink).
         *
         * We implemented them by adding a button and putting the content on the button's image
         * We had not added others as no example. Can you use the text example to add for missing values?
         */
        switch(subtype){
            case PdfDictionary.Text:/* a sticky note which displays a popup when open. */
                String name = form.getTextStreamValue(PdfDictionary.Name);
                if(name!=null){
                    /* Name of the icon image to use for the icon of this annotation
                      * - predefined icons are needed for names:-
                      * Comment, Key, Note, Help, NewParagraph, Paragraph, Insert
                      */
                    if(name.equals("Comment")){

                        try {
                            BufferedImage commentIcon = ImageIO.read(getClass().getResource("/org/jpedal/objects/acroforms/res/comment.png"));
                            but.setIcon(new FixImageIcon(commentIcon,0));
                        } catch (Exception e){
                            //tell user and log
                            if(LogWriter.isOutput())
                                LogWriter.writeLog("Exception: "+e.getMessage());
                        }

                    }else {
                    }

                    //show as popup
                    break;
                    //PdfDictionary.Open;
                    //PdfDictionary.Popup;
                }

            break;
            case PdfDictionary.Highlight :
            	if(newAnnots){
            		float[] f = form.getFloatArray(PdfDictionary.C);
            		Color c = new Color(0);
            		if(f!=null){
            			switch(f.length){
            			case 0:
            				//Should not happen. Do nothing. Annotation is transparent
            				break;
            			case 1:
            				//DeviceGrey colorspace
            				c = new Color(f[0],f[0],f[0],0.5f);
            				break;
            			case 3:
            				//DeviceRGB colorspace
            				c = new Color(f[0],f[1],f[2],0.5f);
            				break;
            			case 4:
            				//DeviceCMYK colorspace
            				DeviceCMYKColorSpace cmyk = new DeviceCMYKColorSpace();
            				cmyk.setColor(f, 4);
            				c = new Color(cmyk.getColor().getRGB());
            				c = new Color(c.getRed(), c.getGreen(), c.getBlue(),0.5f);

            				break;
            			default:
            				break;
            			}            			
            		}
            		
            		float[] quad = form.getFloatArray(PdfDictionary.QuadPoints);
            		if(quad==null){
            			quad = form.getFloatArray(PdfDictionary.Rect);
            		}
            		
            		BufferedImage icon = new BufferedImage(form.getBoundingRectangle().width, form.getBoundingRectangle().height, BufferedImage.TYPE_4BYTE_ABGR);
        			Graphics g = icon.getGraphics();
//        			g.setColor(Color.blue);
//        			g.fillRect(0,0, icon.getWidth(), icon.getHeight());
        			if(quad.length>=8)
            		for(int hi=0; hi!=quad.length; hi+=8){
            			int x = (int)quad[hi]-form.getBoundingRectangle().x;
            			int y = (int)quad[hi+5]-form.getBoundingRectangle().y;
            			//Adjust y for display
            			y = (form.getBoundingRectangle().height-y)-(int)(quad[hi+1]-quad[hi+5]);
            			int width = (int)(quad[hi+2]-quad[hi]);
            			int height = (int)(quad[hi+1]-quad[hi+5]);
            			Rectangle rh = new Rectangle(x, y, width, height);
            			
            			try {
            				g.setColor(c);
            				g.fillRect(rh.x,rh.y, rh.width, rh.height);
            				but.setBackground(new Color(0,0,0,0));
            				but.setIcon(new FixImageIcon(icon,0));
            			} catch (Exception e){
                            //tell user and log
                            if(LogWriter.isOutput())
                                LogWriter.writeLog("Exception: "+e.getMessage());
            			}
            		}
            	}
            	break;
            case PdfDictionary.Underline :
            	if(newAnnots){
            		but.setBounds(form.getBoundingRectangle());
            		
            		float[] underlineColor = form.getFloatArray(PdfDictionary.C);
            		Color c1 = new Color(0);
            		if(underlineColor!=null){
            			switch(underlineColor.length){
            			case 0:
            				//Should not happen. Do nothing. Annotation is transparent
            				break;
            			case 1:
            				//DeviceGrey colorspace
            				c1 = new Color(underlineColor[0],underlineColor[0],underlineColor[0],1.0f);
            				break;
            			case 3:
            				//DeviceRGB colorspace
            				c1 = new Color(underlineColor[0],underlineColor[1],underlineColor[2],1.0f);
            				break;
            			case 4:
            				//DeviceCMYK colorspace
            				DeviceCMYKColorSpace cmyk = new DeviceCMYKColorSpace();
            				cmyk.setColor(underlineColor, 4);
            				c1 = new Color(cmyk.getColor().getRGB());

            				break;
            			default:
            				break;
            			}            			
            		}

            		float[] quad = form.getFloatArray(PdfDictionary.QuadPoints);
            		if(quad==null){
            			quad = form.getFloatArray(PdfDictionary.Rect);
            		}
            		
            		BufferedImage icon = new BufferedImage(form.getBoundingRectangle().width, form.getBoundingRectangle().height, BufferedImage.TYPE_4BYTE_ABGR);
        			Graphics g = icon.getGraphics();
//        			g.setColor(Color.blue);
//        			g.fillRect(0,0, icon.getWidth(), icon.getHeight());
        			if(quad.length>=8)
            		for(int hi=0; hi!=quad.length; hi+=8){
            			int x = (int)quad[hi]-form.getBoundingRectangle().x;
            			int y = (int)quad[hi+5]-form.getBoundingRectangle().y;
            			//Adjust y for display
            			y = (form.getBoundingRectangle().height-y)-(int)(quad[hi+1]-quad[hi+5]);
            			int width = (int)(quad[hi+2]-quad[hi]);
            			int height = (int)(quad[hi+1]-quad[hi+5]);
            			Rectangle rh = new Rectangle(x, y, width, height);
            			
            			try {
                			g.setColor(new Color(0.0f,0.0f,0.0f,0.0f));
                			g.fillRect(rh.x,rh.y, rh.width, rh.height);
                			g.setColor(c1);
                			g.fillRect(rh.x,rh.y+rh.height-1, rh.width, 1);
                			but.setBackground(new Color(0,0,0,0));
                			but.setIcon(new FixImageIcon(icon,0));
                		} catch (Exception e){
                            //tell user and log
                            if(LogWriter.isOutput())
                                LogWriter.writeLog("Exception: "+e.getMessage());
                		}
            		}
            	}
            	break;
            case PdfDictionary.StrickOut :
            	if(newAnnots){
            		
            		float[] strikeColor = form.getFloatArray(PdfDictionary.C);
            		Color c2 = new Color(0);
            		if(strikeColor!=null){
            			switch(strikeColor.length){
            			case 0:
            				//Should not happen. Do nothing. Annotation is transparent
            				break;
            			case 1:
            				//DeviceGrey colorspace
            				c2 = new Color(strikeColor[0],strikeColor[0],strikeColor[0],1.0f);
            				break;
            			case 3:
            				//DeviceRGB colorspace
            				c2 = new Color(strikeColor[0],strikeColor[1],strikeColor[2],1.0f);
            				break;
            			case 4:
            				//DeviceCMYK colorspace
            				DeviceCMYKColorSpace cmyk = new DeviceCMYKColorSpace();
            				cmyk.setColor(strikeColor, 4);
            				c2 = new Color(cmyk.getColor().getRGB());

            				break;
            			default:
            				break;
            			}            			
            		}

            		float[] quad = form.getFloatArray(PdfDictionary.QuadPoints);
            		if(quad==null){
            			quad = form.getFloatArray(PdfDictionary.Rect);
            		}
            		
            		BufferedImage icon = new BufferedImage(form.getBoundingRectangle().width, form.getBoundingRectangle().height, BufferedImage.TYPE_4BYTE_ABGR);
        			Graphics g = icon.getGraphics();
//        			g.setColor(Color.blue);
//        			g.fillRect(0,0, icon.getWidth(), icon.getHeight());
        			if(quad.length>=8)
            		for(int hi=0; hi!=quad.length; hi+=8){
            			int x = (int)quad[hi]-form.getBoundingRectangle().x;
            			int y = (int)quad[hi+5]-form.getBoundingRectangle().y;
            			//Adjust y for display
            			y = (form.getBoundingRectangle().height-y)-(int)(quad[hi+1]-quad[hi+5]);
            			int width = (int)(quad[hi+2]-quad[hi]);
            			int height = (int)(quad[hi+1]-quad[hi+5]);
            			Rectangle rh = new Rectangle(x, y, width, height);
            			
            			try {
            				g.setColor(new Color(0.0f,0.0f,0.0f,0.0f));
                			g.fillRect(0,0, rh.width, rh.height);
                			g.setColor(c2);
                			g.fillRect(rh.x,rh.y+(rh.height/2), rh.width, 1);
                			but.setBackground(new Color(0,0,0,0));
                			but.setIcon(new FixImageIcon(icon,0));
                		} catch (Exception e){
                            //tell user and log
                            if(LogWriter.isOutput())
                                LogWriter.writeLog("Exception: "+e.getMessage());
                		}
            		}
            	}
            	break;
            default:
                break;
        }

        return but;
    }

    /**
     * setup and return the ComboBox field specified in the FormObject
     */
    public Object comboBox(final FormObject form) {

        //populate items array with list from Opt
        String[] items = form.getItemsList();
        JComboBox comboBox;
        if (items == null)
            comboBox = new JComboBox();
        else{
        	
            comboBox = new JComboBox(items);

            /**
             * allow background colour in cells
             */
            Color backgroundColor = FormObject.generateColor(form.getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BG));
            if(backgroundColor!=null){
                ListCellRenderer renderer = new ComboColorRenderer(backgroundColor);
                comboBox.setRenderer(renderer); 
            }
        }
        
        //get and set currently selected value
        String textValue = form.getSelectedItem();
        if (form.getValuesMap(true) != null) {
            comboBox.setSelectedItem(form.getValuesMap(true).get(textValue));
        } else {
            comboBox.setSelectedItem(textValue);
        }

        if (printouts)
            System.out.println("currently selected value=" + textValue);

        boolean[] flags = form.getFieldFlags();
        if (flags[FormObject.EDIT_ID]) {//FormObject.EDIT_ID
            if (printouts)
                System.out.println("drop list and an editable text box");
            comboBox.setEditable(true);

        } else {//is not editable
            if (printouts)
                System.out.println("only a drop list");
            comboBox.setEditable(false);
        }

//    	    if(form instanceof XFAFormObject && ((XFAFormObject)form).choiceShown==XFAFormObject.CHOICE_ENTRY){
//    	    	comboBox.addMouseListener(formsActionHandler.setComboClickOnEntry());
//    	    }


        setupUniversalFeatures(comboBox, form);
        
        if(flags[FormObject.READONLY_ID]) {
            comboBox.setEditable(false);//combo box
            comboBox.setEnabled(false);

            if (printouts)
                System.out.println("READONLY_ID=" + comboBox);
        }


        return comboBox;
    }

    /**
     * setup and return the CheckBox button specified in the FormObject
     */
    public Object checkBoxBut(final FormObject form) {

        //			the text value
        JCheckBox checkBut = new JCheckBox();

        setupButton(checkBut, form);

        setupUniversalFeatures(checkBut, form);
        
        if(checkBut.getBorder()!=null){
        	checkBut.setBorderPainted(true);
        }

        boolean[] flags = form.getFieldFlags();
        if ((flags != null) && (flags[FormObject.READONLY_ID])) {
            checkBut.setEnabled(false);
            checkBut.setDisabledIcon(checkBut.getIcon());
            checkBut.setDisabledSelectedIcon(checkBut.getSelectedIcon());
//              retComponent.setForeground(Color.magenta);
//              retComponent.setBackground(Color.magenta);
        }

        return checkBut;
    }

    /**
     * setup and return the List field specified in the FormObject
     */
    public Object listField(final FormObject form) {

        //populate the items array with list from Opt
        String[] items = form.getItemsList();

        //create list (note we catch null value)
        JList list;
        if (items != null)
            list = new JList(items);
        else
            list = new JList();
        
        if (!form.getFieldFlags()[FormObject.MULTISELECT_ID])//mulitselect
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //if there is a top index or selected value select it
        if (form.getTopIndex() != null) {
            list.setSelectedIndices(form.getTopIndex());

            if (printouts) {
                System.out.println("topIndex should be=" + ConvertToString.convertArrayToString(form.getTopIndex()));
            }

        } else {
            String textValue = form.getSelectedItem();
            if (form.getValuesMap(true) != null) {
                list.setSelectedValue(form.getValuesMap(true).get(textValue), true);
            } else {
                list.setSelectedValue(textValue, true);
            }
            if (printouts)
                System.out.println("currently selected value=" + textValue);
        }

        setupUniversalFeatures(list, form);

        return list;
    }

    /**
     * setup and return the multi line password field specified in the FormObject
     */
    public Object multiLinePassword(final FormObject form) {
    	

        JPasswordField multipass;
        String textValue = form.getTextString();
        int maxLength = form.getInt(PdfDictionary.MaxLen);

        if (maxLength != -1)
            multipass = new JPasswordField(textValue, maxLength);
        else
            multipass = new JPasswordField(textValue);
        multipass.setEchoChar('*');


        setupUniversalFeatures(multipass, form);

        setupTextFeatures(multipass, form);

        boolean[] flags = form.getFieldFlags();
        if ((flags != null) && (flags[FormObject.READONLY_ID])) {
            multipass.setEditable(false);
            if (printouts)
                System.out.println("READONLY_ID=" + multipass);
        }
        
        setToolTip(form, multipass);


        return multipass;
    }

    /**
     * setup and return the multi line text area specified in the FormObject
     */
    public Object multiLineText(final FormObject form) {

    	boolean[] flags = form.getFieldFlags();
    	boolean[] characteristics = form.getCharacteristics();

    	JComponent comp;
			JTextArea newTextarea = new JTextArea(form.getTextString());
			newTextarea.setLineWrap(true);
			newTextarea.setWrapStyleWord(true);
			
			comp = newTextarea;

        setToolTip(form, comp);
        setupUniversalFeatures(comp, form);

        return comp;
    }

    /**
     * setup and return a signature field component,
     * <b>Note:</b> SKELETON METHOD FOR FUTURE UPGRADES.
     */
    public Object signature(FormObject form) {


        JButton sigBut = new JButton();
        
        setupButton(sigBut, form);

        setupUniversalFeatures(sigBut, form);

        boolean[] flags = form.getFieldFlags();
        if (flags != null && flags[FormObject.READONLY_ID]) {
            sigBut.setEnabled(false);
            sigBut.setDisabledIcon(sigBut.getIcon());
            sigBut.setDisabledSelectedIcon(sigBut.getSelectedIcon());
        }


        //old version results in bizarre images in Abacus code
        //show as box similar to Acrobat if no values
//        if(!form.isAppearanceUsed()){
//            sigBut.setOpaque(true);
//            sigBut.setBackground(new Color(221,228,255,175)); //r,g,b,a
//        }

        //new version
        //show as box similar to Acrobat if no values
        if(!form.isAppearanceUsed()){
            sigBut.setOpaque(false);
            BufferedImage img=new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D imgG2=img.createGraphics();
            imgG2.setPaint(new Color(221,228,255,175));
            imgG2.fillRect(0,0,1,1);
            sigBut.setIcon(new FixImageIcon(img,0));

           // sigBut.setBackground(new Color(221,228,255,175)); //r,g,b,a
        }

        return sigBut;
    }

    /**
     * setup and return the Push button specified in the FormObject
     */
    public Object pushBut(final FormObject form) {

        //the text value
        JButton pushBut = new JButton();
        
        setupButton(pushBut, form);

        setupUniversalFeatures(pushBut, form);

        boolean[] flags = form.getFieldFlags();
        if ((flags != null) && (flags[FormObject.READONLY_ID])) {
            pushBut.setEnabled(false);
            pushBut.setDisabledIcon(pushBut.getIcon());
            pushBut.setDisabledSelectedIcon(pushBut.getSelectedIcon());
        }

        return pushBut;
    }

    /**
     * setup and return the Radio button specified in the FormObject
     */
    public Object radioBut(final FormObject form) {
    	

        //the text value
        JRadioButton radioBut = new JRadioButton();
        //radioBut.setContentAreaFilled(false);//false for transparency
        
        
        setupButton(radioBut, form);

        setupUniversalFeatures(radioBut, form);
        
      //poss needed for xfa but have to make the border round first.
//        if(radioBut.getBorder()!=null){
//        	radioBut.setBorderPainted(true);
//        }
        
        boolean[] flags = form.getFieldFlags();
        if ((flags != null) && (flags[FormObject.READONLY_ID])) {
            radioBut.setEnabled(false);
            radioBut.setDisabledIcon(radioBut.getIcon());
            radioBut.setDisabledSelectedIcon(radioBut.getSelectedIcon());
//              retComponent.setForeground(Color.magenta);
//              retComponent.setBackground(Color.magenta);
        }

        return radioBut;
    }


    /**
     * setup and return the single line password field specified in the FormObject
     */
    public Object singleLinePassword(final FormObject form) {

        JPasswordField newPassword = new JPasswordField(form.getTextString());
        newPassword.setEchoChar('*');

        //set length
        int maxLength = form.getInt(PdfDictionary.MaxLen);
        if (maxLength != -1) {
            newPassword.setColumns(maxLength);
            if (printouts)
                System.out.println("textlength added=" + maxLength);
        }

        setupUniversalFeatures(newPassword, form);

        setupTextFeatures(newPassword, form);

        boolean[] flags = form.getFieldFlags();
        if ((flags != null) && (flags[FormObject.READONLY_ID])) {
            newPassword.setEditable(false);
            if (printouts)
                System.out.println("READONLY_ID=" + newPassword);
        }
        
        setToolTip(form, newPassword);


        return newPassword;
    }

    /**
     * setup and return the single line text field specified in the FormObject
     */
    public Object singleLineText(final FormObject form) {

    	boolean[] flags = form.getFieldFlags();
    	boolean[] characteristics = form.getCharacteristics();
    	

    	JComponent retComp=null;
    	if (((flags != null) && (flags[FormObject.READONLY_ID]))
    			|| (characteristics!=null && characteristics[9])//characteristics[9] = LockedContents
    			//p609 PDF ref ver 1-7, characteristics 'locked' flag does allow contents to be edited,
    			//but the 'LockedContents' flag stops content being changed. 
    			){
    		
    			JTextField newTextfield = new JTextField(form.getTextString());

    			setupTextFeatures(newTextfield, form);
    			setToolTip(form, newTextfield);

    			newTextfield.setEditable(false);

    			retComp = newTextfield;
    			

    	}else {//NOT read only
    		JTextField newTextfield = new JTextField(form.getTextString());

    		setupTextFeatures(newTextfield, form);
    		setToolTip(form, newTextfield);

    		retComp = newTextfield;
    	}

    	setupUniversalFeatures(retComp, form);

    	return retComp;
    }

	//############ below is all text setup ################ TAG
    /**
     * sets up all the required attributes for all text fields
     */
    private static void setupTextFeatures(JTextField textcomp, FormObject form) {
        //set text field alignment
        if (form.getAlignment() != -1)
            textcomp.setHorizontalAlignment(form.getAlignment());
        
    }

    //################# below is buttons setup ################## TAG
    /**
     * sets up the buttons captions, images, etc
     * for normal, rollover, down and off or on if radio or check buttons
     */
    private void setupButton(AbstractButton comp, FormObject form) {
        //transparancy
//    	((AbstractButton) comp).setContentAreaFilled(false);//false for transparency
    	String normalCaption = form.getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.CA);
        comp.setText(normalCaption);
        
        comp.setContentAreaFilled(false);
        
        String downCaption = form.getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.AC);
        String rolloverCaption = form.getDictionary(PdfDictionary.MK).getTextStreamValue(PdfDictionary.RC);
        if((downCaption != null && downCaption.length()>0) || (rolloverCaption != null && rolloverCaption.length()>0))
        	comp.addMouseListener((MouseListener)formsActionHandler.setupChangingCaption(normalCaption, rolloverCaption, downCaption));

        if (form.isAppearanceUsed())
            setAPImages(form, comp, showAppearanceImages); // pass in true to debug images by showing
        
        int textPosition = form.getTextPosition();
        if (textPosition != -1) {
            /*
             * if there are any appearance images, then the text is set back to null,
             * if the textPosition needs to be setup you need to either set the text back here or not 
             * set it to null in appearanceImages. 
             * 
             * If you need to set this up check file acodabb.pdf page 4 as it has an icon with text being
             * set to overlay the icon, which doesn't work.
             */
            switch (textPosition) {
                case 0:

                    comp.setIcon(null);
                    comp.setText(normalCaption); //seems to need reset
                    break;//0=caption only
                case 1:
                    comp.setText(null);
                    break;//1=icon only
                case 2:
                    comp.setVerticalTextPosition(SwingConstants.BOTTOM);
                    break;//2=caption below icon
                case 3:
                    comp.setVerticalTextPosition(SwingConstants.TOP);
                    break;//3=caption above icon
                case 4:
                    comp.setHorizontalTextPosition(SwingConstants.RIGHT);
                    break;//4=caption on right of icon
                case 5:
                    comp.setHorizontalTextPosition(SwingConstants.LEFT);
                    break;//5=caption on left of icon
                case 6:
                    comp.setText(null);
                    break;/*checkBut.setVerticalTextPosition(SwingConstants.CENTER);
            	comp.setHorizontalTextPosition(SwingConstants.CENTER);//6=caption overlaid ontop of icon */
            }
        }

        //TODO get margin data from formobject
        Insets insetZero = new Insets(0, 0, 0, 0);
        comp.setMargin(insetZero);
        
        comp.addMouseListener((MouseListener)formsActionHandler.setHoverCursor());
    }

    /**
     * gets each appearance image from the map <b>appearance</b> and
     * and adds it to the relevent icon for the AbstractButton <b>comp</b>
     * showImages is to display the appearance images for that FormObject
     */
    public void setAPImages(final FormObject form, Object rawComp, boolean showImages) {

    	AbstractButton comp=(AbstractButton)rawComp;

        { // OLD display code
    		
    		BufferedImage normalOff=null,normalOn=null,downOff=null,downOn=null; 

    		if (form.hasNormalOff()) {
    			comp.setText(null);
    			//store normal off as ma be used in generating the down image
    			normalOff = form.getNormalOffImage();
    			comp.setIcon(new FixImageIcon(normalOff,form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
    			
    			if (showImages)
    				ShowGUIMessage.showGUIMessage("normalAppOffImage", form.getNormalOffImage(), "normalAppOff");
    		}
    		if (form.hasNormalOn()) {
    			comp.setText(null);
    			//store normal on as ma be used in generating the down image
    			normalOn = form.getNormalOnImage();
    			comp.setSelectedIcon(new FixImageIcon(normalOn,form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
        		
    			if (showImages)
    				ShowGUIMessage.showGUIMessage("normalAppOnImage", form.getNormalOnImage(), "normalAppOn");
    		}

    		if (form.hasNoDownIcon()) {
    			comp.setPressedIcon(comp.getIcon());
    		} else {
    			if(!form.hasDownImage()){
    				if (form.hasNormalOff()) {
    					if (form.hasNormalOn()) {
    						if(form.hasOffsetDownIcon()){
    							downOn = createPressedLook(normalOn);
    							downOff = createPressedLook(normalOff);
    						}else {
    							downOff = invertImage(normalOff);
    							downOn = invertImage(normalOn);
    						}
    					} else {
    						if(form.hasOffsetDownIcon()){
    							downOff = createPressedLook(normalOff);
    						}else {
    							downOff = invertImage(normalOff);
    						}
    					}
    				} else if (form.hasNormalOn()) {
    					if(form.hasOffsetDownIcon()){
    						downOff = createPressedLook(normalOn);
    					}else {
    						downOff = invertImage(normalOn);
    					}
    				}
    			} else {
    				downOff = form.getDownOffImage();
    				downOn = form.getDownOnImage();
    			}
    		}
    		
    		if (downOff == null || downOn == null) {
    			if (downOff != null) {
    				comp.setText(null);
    				comp.setPressedIcon(new FixImageIcon(downOff,form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
    			} else if (downOn != null) {
    				comp.setText(null);
    				comp.setPressedIcon(new FixImageIcon(downOn,form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
    			}

    		} else {
    			comp.setPressedIcon(new FixImageIcon(downOn,downOff,form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R),comp.isSelected()?1:0));
    			comp.addActionListener((ActionListener)formsActionHandler.setupChangingDownIcon(downOff, downOn,form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
    		}

    		if (showImages) {
    			ShowGUIMessage.showGUIMessage("downAppOffImage", downOff, "downAppOff");
    			ShowGUIMessage.showGUIMessage("downAppOnImage", downOn, "downAppOn");
    		}

    		if (form.hasRolloverOff()) {
    			comp.setRolloverEnabled(true);
    			comp.setText(null);
    			comp.setRolloverIcon(new FixImageIcon(form.getRolloverOffImage(),form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
    			
    			if (showImages)
    				ShowGUIMessage.showGUIMessage("rolloverAppOffImage", form.getRolloverOffImage(), "rolloverAppOff");
    		}
    		if (form.hasRolloverOn()) {
    			comp.setRolloverEnabled(true);
    			comp.setText(null);
    			comp.setRolloverSelectedIcon(new FixImageIcon(form.getRolloverOnImage(),form.getDictionary(PdfDictionary.MK).getInt(PdfDictionary.R)));
    			
    			if (showImages)
    				ShowGUIMessage.showGUIMessage("rolloverAppOnImage", form.getRolloverOnImage(), "rolloverAppOn");
    		}
    	}
    	
    	//moved to end as flagLastUsed can call the imageicon
    	String defaultState = form.getName(PdfDictionary.AS);
    	if (printouts)
    		System.out.println("default state=" + defaultState);
    	if (defaultState != null && defaultState.equals(form.getNormalOnState())) {
    		comp.setSelected(true);
    		if(comp instanceof JToggleButton){
    			Icon icn = comp.getPressedIcon();
    			if(icn!=null && icn instanceof FixImageIcon)
    				((FixImageIcon)icn).swapImage(true);
    		}
    		acrorend.getCompData().flagLastUsedValue(comp, form,false);
    	}
    }


    //############  below is universal setup ################## TAG
    /**
     * sets up the features for all fields, transparancy, font, color, border, actions,
     * background color,
     */
    private void setupUniversalFeatures(JComponent comp, FormObject form) {

        comp.setOpaque(false);

        Font textFont = form.getTextFont();
        if (textFont != null)
            comp.setFont(textFont);
        comp.setForeground(form.getTextColor());

        Border newBorder = (Border)acrorend.getCompData().generateBorderfromForm(form,1);

        comp.setBorder(newBorder);
        if (printouts) {
            System.out.println("borderStyle=" + newBorder);
        }

        Color backgroundColor = FormObject.generateColor(form.getDictionary(PdfDictionary.MK).getFloatArray(PdfDictionary.BG));
        if (backgroundColor != null) {
            comp.setBackground(backgroundColor);
            comp.setOpaque(true);
        }else if(PdfDecoder.isRunningOnMac && (comp instanceof JButton)){
            //hack because OS X does not f***king work properly
            ((JButton)comp).setBorderPainted(false);
            comp.setBorder(null);

        }

        setupMouseListener(comp, form);
        if (printouts) {
            System.out.println("flagNum=" + form.getCharacteristics());
        }
        
    }

    /**
     * setup the events for currentComp, from the specified parameters
     *
     * @Action - mouse events added here
     */
    private void setupMouseListener(final Component currentComp, FormObject form) {
        /* bit 1 is index 0 in []
           * 1 = invisible
           * 2 = hidden - dont display or print
           * 3 = print - print if set, dont if not
           * 4 = nozoom
           * 5= norotate
           * 6= noview
           * 7 = read only (ignored by wiget)
           * 8 = locked
           * 9 = togglenoview
           * 10 = LockedContent
           */

    	boolean[] characteristic = form.getCharacteristics();//F
        if (characteristic[0] || characteristic[1] || characteristic[5]) {
            currentComp.setVisible(false);
        }

        SwingListener jpedalListener = new SwingListener(form, acrorend, formsActionHandler);
        //if combobox wee need to add the listener to the component at position 0 as well as the normal one, so it works properly.
        if (currentComp instanceof JComboBox) {
            ((JComboBox) currentComp).getComponent(0).addMouseListener(jpedalListener);
            ((JComboBox) currentComp).getComponent(0).addKeyListener(jpedalListener);
            ((JComboBox) currentComp).getComponent(0).addFocusListener(jpedalListener);
//            ((JComboBox) currentComp).getComponent(0).addComponentListener(jpedalListener);
//            ((JComboBox) currentComp).getComponent(0).addInputMethodListener(jpedalListener);
//            ((JComboBox) currentComp).getComponent(0).addHierarchyListener(jpedalListener);
            ((JComboBox)currentComp).addActionListener(jpedalListener);
        }
        if(currentComp instanceof JList){
        	((JList)currentComp).addListSelectionListener(jpedalListener);
        }
        
        currentComp.addMouseListener(jpedalListener);
        currentComp.addMouseMotionListener(jpedalListener);
        currentComp.addKeyListener(jpedalListener);
        currentComp.addFocusListener(jpedalListener);
        
//        currentComp.addComponentListener(jpedalListener);
//        currentComp.addInputMethodListener(jpedalListener);
//        currentComp.addHierarchyListener(jpedalListener);
//        currentComp.addPropertyChangeListener(jpedalListener);
        
        //old version 
        /**
        Object adata = form.getAobj();        
        if (adata != null && adata instanceof Map) {
            Map aDataMap = (Map) adata;
            if (aDataMap.containsKey("S")) {
                if (((String) aDataMap.get("S")).indexOf("URI") != -1) {
                    String oldText = (String) aDataMap.get("URI");
                    ((JComponent) currentComp).setToolTipText(oldText);
                }
            }
        }/**/
        
        //new version
        //actually fixes bug as old gave you 
        //old=(http://www.lbl.gov)
        //new=http://www.lbl.gov without spurious ()
        PdfObject aData=form.getDictionary(PdfDictionary.A);
        if(aData!=null && aData.getNameAsConstant(PdfDictionary.S)==PdfDictionary.URI){

            String noLinkToolTips=System.getProperty("org.jpedal.noURLaccess");

            if(noLinkToolTips==null || !noLinkToolTips.equals("true")){
        	    String text=aData.getTextStreamValue(PdfDictionary.URI); //+"ZZ"; deliberately broken first to test checking
        	    ((JComponent) currentComp).setToolTipText(text);
            }
        }
    }

    private static void setToolTip(final FormObject formObject, JComponent retComponent) {
		//TU seems to be used as a tooltip in text fields so added
		String userName = formObject.getTextStreamValue(PdfDictionary.TU);
		if(userName!=null)
			retComponent.setToolTipText(userName);
	}

    /**
     * new data object to hold all widget implementations
     */
	public GUIData getCustomCompData() {
			return new SwingData();
	}

    public ActionFactory getActionFactory() {
    	SwingActionFactory fact = new SwingActionFactory();
    	fact.setPDF(formsActionHandler.getPDFDecoder(), acrorend);
        return fact;
    }

    public int getType() {
        return FormFactory.SWING;
    }



    /**
     * pass in Map contains annot field list in order to set tabindex
     */
    public void setAnnotOrder(Map annotOrder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**just so I can set the f**king colour on the Cells*/
    static class ComboColorRenderer extends JLabel implements ListCellRenderer {

        Color color=Color.RED;

        public ComboColorRenderer(Color col) {

            color=col;

            setBorder(null);
            setOpaque(true);
        }

        public ComboColorRenderer() {
            setBorder(null);
            setOpaque(true);
        }


        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setBackground(color);

            if(value==null || ((String)value).length()==0)
                setText(" ");
            else
                setText((String)value);
            
            return this;
        }
    }
}
