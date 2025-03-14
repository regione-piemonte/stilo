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

  * ExternalHandlers.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.external;

import it.eng.hybrid.module.jpedal.parser.PdfStreamDecoder;
import it.eng.hybrid.module.jpedal.render.DynamicVectorRenderer;
import it.eng.hybrid.module.jpedal.util.Options;
import it.eng.hybrid.module.jpedal.viewer.ValueTypes;

import java.util.Map;

public class ExternalHandlers {

    //Option to append the error thrown due to lack of CID jar to page decode report
    public static boolean throwMissingCIDError = false;

    private DynamicVectorRenderer customDVR=null;

    ImageHandler customImageHandler = null;

    //custom class for flagging painting
    RenderChangeListener customRenderChangeListener=null;


    private Map jpedalActionHandlers;

    CustomPrintHintingHandler customPrintHintingHandler=null;

    ColorHandler customColorHandler=null;//new ExampleColorHandler();

    private CustomFormPrint customFormPrint;

    private CustomMessageHandler customMessageHandler =null;

    //copy for callback
    Object swingGUI=null;


    public void addHandlers(PdfStreamDecoder streamDecoder) {

        streamDecoder.setObjectValue(ValueTypes.ImageHandler, customImageHandler);

    }

    /**
     * allows external helper classes to be added to JPedal to alter default functionality -
     * not part of the API and should be used in conjunction with IDRsolutions only
     * <br>if Options.FormsActionHandler is the type then the <b>newHandler</b> should be
     * of the form <b>org.jpedal.objects.acroforms.ActionHandler</b>
     *
     * @param newHandler
     * @param type
     */
    public void addExternalHandler(Object newHandler, int type) {

        switch (type) {

            case Options.SwingContainer:
                swingGUI = newHandler;
                break;

            case Options.ImageHandler:
                customImageHandler = (ImageHandler) newHandler;
                break;

            case Options.ColorHandler:
                customColorHandler = (ColorHandler) newHandler;
                break;


//            case Options.Renderer:
//                //cast and assign here
//                break;

//            case Options.FormFactory:
//                formRenderer.setFormFactory((FormFactory) newHandler);
//                break;
//
//            case Options.MultiPageUpdate:
//                customSwingHandle = newHandler;
//                break;
//
//
//            case Options.LinkHandler:
//
//                if (formRenderer != null)
//                    formRenderer.resetHandler(newHandler, this,Options.LinkHandler);
//
//                break;
//
//            case Options.FormsActionHandler:
//
//                if (formRenderer != null)
//                    formRenderer.resetHandler(newHandler, this,Options.FormsActionHandler);
//
//                break;
//
//            //<start-thin><start-adobe>
//            case Options.SwingMouseHandler:
//                if(formRenderer != null){
//                    formRenderer.getActionHandler().setMouseHandler((SwingMouseListener) newHandler);
//                }
//                break;
//
//            case Options.ThumbnailHandler:
//                pages.setThumbnailPanel((org.jpedal.examples.viewer.gui.generic.GUIThumbnailPanel) newHandler);
//                break;
//            //<end-adobe><end-thin>
//
            case Options.JPedalActionHandler:
                jpedalActionHandlers = (Map) newHandler;
                break;

            case Options.CustomMessageOutput:
                customMessageHandler = (CustomMessageHandler) newHandler;
                break;

            case Options.RenderChangeListener:
                customRenderChangeListener = (RenderChangeListener) newHandler;
                break;

            case Options.CustomPrintHintingHandler:
                customPrintHintingHandler = (CustomPrintHintingHandler) newHandler;
                break;

            case Options.CustomOutput:
                customDVR = (DynamicVectorRenderer) newHandler;
                break;


            default:
                throw new IllegalArgumentException("Unknown type="+type);

        }
    }

        /**
         * allows external helper classes to be accessed if needed - also allows user to access SwingGUI if running
         * full Viewer package - not all Options available to get - please contact IDRsolutions if you are looking to
         * use
         *
         * @param type
         */
        public Object getExternalHandler(int type) {

            switch (type) {
                case Options.ImageHandler:
                    return customImageHandler;

                case Options.ColorHandler:
                    return customColorHandler;


                case Options.SwingContainer:
                    return swingGUI;

//                case Options.Renderer:
//                    return null;
//
//                case Options.FormFactory:
//                    return formRenderer.getFormFactory();
//
//                case Options.MultiPageUpdate:
//                    return customSwingHandle;
//
//
                case Options.JPedalActionHandler:
                    return jpedalActionHandlers;

                case Options.CustomMessageOutput:
                    return customMessageHandler;

//                case Options.Display:
//                    return pages;
//
//                case Options.CurrentOffset:
//                    return currentOffset;
//
                case Options.CustomOutput:
                    return customDVR;

                case Options.RenderChangeListener:
                    return customRenderChangeListener;

                case Options.JPedalActionHandlers:
                    return jpedalActionHandlers;


                default:

                    if(type==Options.UniqueAnnotationHandler){ //LGPL version
                        return null;
                    }else
                        throw new IllegalArgumentException("Unknown type "+type);

            }
        }
}
