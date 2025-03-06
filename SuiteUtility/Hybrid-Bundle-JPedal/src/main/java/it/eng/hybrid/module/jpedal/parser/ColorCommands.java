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

  * ColorCommands.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.parser;

import it.eng.hybrid.module.jpedal.color.ColorSpaces;
import it.eng.hybrid.module.jpedal.color.ColorspaceFactory;
import it.eng.hybrid.module.jpedal.color.DeviceCMYKColorSpace;
import it.eng.hybrid.module.jpedal.color.DeviceGrayColorSpace;
import it.eng.hybrid.module.jpedal.color.DeviceRGBColorSpace;
import it.eng.hybrid.module.jpedal.color.GenericColorSpace;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.GraphicsState;
import it.eng.hybrid.module.jpedal.objects.PdfPageData;
import it.eng.hybrid.module.jpedal.objects.raw.ColorSpaceObject;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.util.StringUtils;


public class ColorCommands {

    static void CS(boolean isLowerCase,String colorspaceObject, GraphicsState gs, PdfObjectCache cache, PdfObjectReader currentPdfFile, boolean isPrinting, int pageNum, PdfPageData pageData,boolean alreadyUsed) {

        //set flag for stroke
        boolean isStroke = !isLowerCase;

        /**
         * work out colorspace
         */
        PdfObject ColorSpace=(PdfObject)cache.get(PdfObjectCache.Colorspaces,colorspaceObject);

        if(ColorSpace==null)
            ColorSpace=new ColorSpaceObject(StringUtils.toBytes(colorspaceObject));

        String ref=ColorSpace.getObjectRefAsString(), ref2=ref+ '-'+isLowerCase;

        GenericColorSpace newColorSpace;

        //(ms) 20090430 new code does not work so commented out

        //int ID=ColorSpace.getParameterConstant(PdfDictionary.ColorSpace);

        //        if(isLowerCase)
        //            System.out.println(" cs="+colorspaceObject+" "+alreadyUsed+" ref="+ref);
        //        else
        //            System.out.println(" CS="+colorspaceObject+" "+alreadyUsed+" ref="+ref);

        if(!alreadyUsed && cache.colorspacesObjects.containsKey(ref)){

            newColorSpace=(GenericColorSpace) cache.colorspacesObjects.get(ref);

            //reinitialise
            newColorSpace.reset();
        }else if(alreadyUsed && cache.colorspacesObjects.containsKey(ref2)){

            newColorSpace=(GenericColorSpace) cache.colorspacesObjects.get(ref2);

            //reinitialise
            newColorSpace.reset();
        }else{

            newColorSpace=ColorspaceFactory.getColorSpaceInstance(currentPdfFile, ColorSpace);

            newColorSpace.setPrinting(isPrinting);

            //use alternate as preference if CMYK
            //if(newColorSpace.getID()==ColorSpaces.ICC && ColorSpace.getParameterConstant(PdfDictionary.Alternate)==ColorSpaces.DeviceCMYK)
            //  newColorSpace=new DeviceCMYKColorSpace();

            //broken on calRGB so ignore at present
            //if(newColorSpace.getID()!=ColorSpaces.CalRGB)

            if((newColorSpace.getID()==ColorSpaces.ICC || newColorSpace.getID()==ColorSpaces.Separation)){
                //if(newColorSpace.getID()==ColorSpaces.Separation)

                if(!alreadyUsed){
                    cache.colorspacesObjects.put(ref, newColorSpace);
                }else
                    cache.colorspacesObjects.put(ref2, newColorSpace);

                // System.out.println("cache "+ref +" "+isLowerCase+" "+colorspaceObject);
            }

        }

        //pass in pattern arrays containing all values
        if(newColorSpace.getID()==ColorSpaces.Pattern){

            //at this point we only know it is Pattern so need to pass in WHOLE array
            newColorSpace.setPattern(cache.patterns,pageData.getMediaBoxWidth(pageNum), pageData.getMediaBoxHeight(pageNum),gs.CTM);
            newColorSpace.setGS(gs);
        }

        //track colorspace use
        cache.put(PdfObjectCache.ColorspacesUsed, newColorSpace.getID(),"x");

        if(isStroke)
            gs.strokeColorSpace=newColorSpace;
        else
            gs.nonstrokeColorSpace=newColorSpace;
    }

    static void G(boolean isLowerCase, GraphicsState gs, CommandParser parser, PdfObjectCache cache) {

        boolean isStroke=!isLowerCase;
        float[] operand= parser.getValuesAsFloat();
        int operandCount=operand.length;

        //set colour and colorspace
        if(isStroke){
            if (gs.strokeColorSpace.getID() != ColorSpaces.DeviceGray)
                gs.strokeColorSpace=new DeviceGrayColorSpace();

            gs.strokeColorSpace.setColor(operand,operandCount);

            //track colrspace use
            cache.put(PdfObjectCache.ColorspacesUsed, gs.strokeColorSpace.getID(), "x");

        }else{
            if (gs.nonstrokeColorSpace.getID() != ColorSpaces.DeviceGray)
                gs.nonstrokeColorSpace=new DeviceGrayColorSpace();

            gs.nonstrokeColorSpace.setColor(operand,operandCount);

            //track colorspace use
            cache.put(PdfObjectCache.ColorspacesUsed, gs.nonstrokeColorSpace.getID(), "x");

        }
    }

    static void K(boolean isLowerCase, GraphicsState gs, CommandParser parser, PdfObjectCache cache) {

        //set flag to show which color (stroke/nonstroke)
        boolean isStroke=!isLowerCase;

        float[] operand=parser.getValuesAsFloat();

        int operandCount=operand.length;


        /**allow for less than 4 values
         * (ie second mapping for device colourspace
         */
        if (operandCount > 3) {


            float[] tempValues=new float[operandCount];
            for(int ii=0;ii<operandCount;ii++)
                tempValues[operandCount-ii-1]=operand[ii];
            operand=tempValues;

            //set colour and make sure in correct colorspace
            if(isStroke){
                if (gs.strokeColorSpace.getID() != ColorSpaces.DeviceCMYK)
                    gs.strokeColorSpace=new DeviceCMYKColorSpace();

                gs.strokeColorSpace.setColor(operand,operandCount);

                //track colorspace use
                cache.put(PdfObjectCache.ColorspacesUsed, gs.strokeColorSpace.getID(),"x");

            }else{
                if (gs.nonstrokeColorSpace.getID() != ColorSpaces.DeviceCMYK)
                    gs.nonstrokeColorSpace=new DeviceCMYKColorSpace();

                gs.nonstrokeColorSpace.setColor(operand,operandCount);

                //track colorspace use
                cache.put(PdfObjectCache.ColorspacesUsed, gs.nonstrokeColorSpace.getID(),"x");

            }
        }
    }

    static void RG(boolean isLowerCase, GraphicsState gs, CommandParser parser, PdfObjectCache cache) {

        //set flag to show which color (stroke/nonstroke)
        boolean isStroke=!isLowerCase;

        float[] operand=parser.getValuesAsFloat();

        int operandCount=operand.length;

        float[] tempValues=new float[operandCount];
        for(int ii=0;ii<operandCount;ii++)
            tempValues[operandCount-ii-1]=operand[ii];
        operand=tempValues;

        //set colour
        if(isStroke){
            if (gs.strokeColorSpace.getID() != ColorSpaces.DeviceRGB)
                gs.strokeColorSpace=new DeviceRGBColorSpace();

            gs.strokeColorSpace.setColor(operand,operandCount);

            //track colorspace use
            cache.put(PdfObjectCache.ColorspacesUsed, gs.strokeColorSpace.getID(),"x");

        }else{
            if (gs.nonstrokeColorSpace.getID() != ColorSpaces.DeviceRGB)
                gs.nonstrokeColorSpace=new DeviceRGBColorSpace();

            gs.nonstrokeColorSpace.setColor(operand,operandCount);

            //track colrspace use
            cache.put(PdfObjectCache.ColorspacesUsed, gs.nonstrokeColorSpace.getID(),"x");

        }
    }



    static void SCN(boolean isLowerCase, GraphicsState gs, CommandParser parser, PdfObjectCache cache) {

        float[] values;

        if(isLowerCase){

            if(gs.nonstrokeColorSpace.getID()==ColorSpaces.Pattern){
                String[] vals=parser.getValuesAsString();
                gs.nonstrokeColorSpace.setColor(vals,vals.length);
            }else{
                values=parser.getValuesAsFloat();

                int operandCount=values.length;
                float[] tempValues=new float[operandCount];
                for(int ii=0;ii<operandCount;ii++)
                    tempValues[operandCount-ii-1]=values[ii];
                values=tempValues;

                //System.out.println(nonstrokeColorSpace);
                gs.nonstrokeColorSpace.setColor(values,operandCount);
            }

            //track colrspace use
            cache.put(PdfObjectCache.ColorspacesUsed, gs.nonstrokeColorSpace.getID(),"x");

        }else{
            if(gs.strokeColorSpace.getID()==ColorSpaces.Pattern){
                String[] vals=parser.getValuesAsString();
                gs.strokeColorSpace.setColor(vals,vals.length);
            }else{
                values=parser.getValuesAsFloat();

                int operandCount=values.length;
                float[] tempValues=new float[operandCount];
                for(int ii=0;ii<operandCount;ii++)
                    tempValues[operandCount-ii-1]=values[ii];
                values=tempValues;

                gs.strokeColorSpace.setColor(values,operandCount);
            }

            //track colrspace use
            cache.put(PdfObjectCache.ColorspacesUsed, gs.strokeColorSpace.getID(),"x");

        }
    }
}
