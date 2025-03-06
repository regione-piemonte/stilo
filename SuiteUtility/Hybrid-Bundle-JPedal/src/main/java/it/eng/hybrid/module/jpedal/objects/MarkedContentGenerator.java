/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2012, IDRsolutions and Contributors.
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
* MarkedContentGenerator.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.objects;

import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.raw.PageObject;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.parser.PdfStreamDecoder;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.viewer.ValueTypes;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * extract as marked content
 */
public class MarkedContentGenerator {
	
	public final static Logger logger = Logger.getLogger(MarkedContentGenerator.class);
	
	private PdfObjectReader currentPdfFile;

    private DocumentBuilder db=null;

    private Document doc;

    private Element root;

    private Map pageStreams=new HashMap();

	private PdfObject structTreeRootObj;

    private PdfResources res;

    private PdfLayerList layers;

    private PdfPageData pdfPageData;

    private boolean isDecoding=false;

	/**
	 * main entry paint
	 */
	public Document getMarkedContentTree(PdfResources res, PdfPageData pdfPageData, PdfObjectReader currentPdfFile) {

        this.structTreeRootObj=res.getPdfObject(PdfResources.StructTreeRootObj);
        //PdfObject markInfoObj=res.getPdfObject(PdfResources.MarkInfoObj);  //not used at present

        this.res=res;
        this.layers=res.getPdfLayerList();

        this.pdfPageData=pdfPageData;

        return null;
        /**/

	}


    /**
     * extract marked content - not yet live
     */
    final synchronized private void decodePageForMarkedContent(int pageNumber, PdfObject pdfObject, Object pageStream) throws Exception {

        if (isDecoding) {

            logger.info("[PDF]WARNING - this file is being decoded already");

        } else {

            //if no tree use page
            if(pdfObject==null){
                String currentPageOffset = currentPdfFile.getReferenceforPage(pageNumber);

                pdfObject=new PageObject(currentPageOffset);
                currentPdfFile.readObject(pdfObject);

            }else{
                pageNumber=currentPdfFile.convertObjectToPageNumber(new String(pdfObject.getUnresolvedData()));
                currentPdfFile.checkResolved(pdfObject);
            }

            try{
                isDecoding=true;

                /** read page or next pages */
                if (pdfObject != null) {

                    /** the ObjectStore for this file */
                    ObjectStore objectStoreRef = new ObjectStore();

                    PdfStreamDecoder current = new PdfStreamDecoder(currentPdfFile, false, layers);
                    current.setParameters(true, false, 0,PdfDecoder.TEXT + PdfDecoder.RAWIMAGES + PdfDecoder.FINALIMAGES);
                    current.setXMLExtraction(false);
                    current.setObjectValue(ValueTypes.Name, "markedContent");
                    current.setObjectValue(ValueTypes.ObjectStore,objectStoreRef);
                    current.setObjectValue(ValueTypes.StatusBar, null);
                    current.setObjectValue(ValueTypes.PDFPageData,pdfPageData);
                    current.setIntValue(ValueTypes.PageNum, pageNumber);

                    res.setupResources(current, false, pdfObject.getDictionary(PdfDictionary.Resources), pageNumber, currentPdfFile);

                    current.setObjectValue(ValueTypes.MarkedContent,pageStream);

                    current.decodePageContent(pdfObject);

                    objectStoreRef.flush();

                }
            }catch(Exception e){
                //tell user and log
               logger.info("Exception: "+e.getMessage());
            }finally {
                isDecoding=false;
            }
        }
    }
}
