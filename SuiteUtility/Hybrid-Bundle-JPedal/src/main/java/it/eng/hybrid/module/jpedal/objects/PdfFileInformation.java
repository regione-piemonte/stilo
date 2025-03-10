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
* PdfFileInformation.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.objects;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.jpedal.constants.PDFflags;
import it.eng.hybrid.module.jpedal.io.DecryptionFactory;
import it.eng.hybrid.module.jpedal.io.ObjectDecoder;
import it.eng.hybrid.module.jpedal.io.PdfFileReader;
import it.eng.hybrid.module.jpedal.io.PdfObjectReader;
import it.eng.hybrid.module.jpedal.objects.raw.MetadataObject;
import it.eng.hybrid.module.jpedal.objects.raw.PdfDictionary;
import it.eng.hybrid.module.jpedal.objects.raw.PdfObject;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;
import it.eng.hybrid.module.jpedal.util.StringUtils;

/**
 * <p>Added as a repository to store PDF file metadata (both legacy fields and XML metadata) in so that it can be accesed. 
 * <p>Please see org.jpedal.examples (especially Viewer) for example code.
 */
public class PdfFileInformation
{
	
	public final static Logger logger = Logger.getLogger(PdfFileInformation.class);
	
	/**list of pdf information fields data might contain*/
	private final static String[] information_fields =
		{
			"Title",
			"Author",
			"Subject",
			"Keywords",
			"Creator",
			"Producer",
			"CreationDate",
			"ModDate",
			"Trapped" };

    /**list of pdf information fields data might contain*/
	public final static int[] information_field_IDs =
		{
			PdfDictionary.Title,
			PdfDictionary.Author,
			PdfDictionary.Subject,
			PdfDictionary.Keywords,
			PdfDictionary.Creator,
			PdfDictionary.Producer,
			PdfDictionary.CreationDate,
			PdfDictionary.ModDate,
			PdfDictionary.Trapped};

    /**assigned values found in pdf information object*/
	private String[] information_values = {"","","","","","","","",""};	
	
	/**Any XML metadata as a string*/
	private String XMLmetadata=null;

	private byte[] rawData=null;
			
	/**return list of field names*/
	public static String[] getFieldNames(){
		return information_fields;
	}
	
	/**return XML data embedded inside PDF*/
	public String getFileXMLMetaData(){

        if(rawData==null)
            return "";
        else{

            if(XMLmetadata==null){

                //make deep copy
                int length=rawData.length;
                byte[] stream=new byte[length];
                System.arraycopy(rawData,0,stream,0,length);
                
                //strip empty lines
                int count=stream.length;
                int reached=0;
                byte lastValue=0;
                for(int ii=0;ii<count;ii++){
                    if(lastValue==13 && stream[ii]==10){
                        //convert return
                        stream[reached-1]=10;
                    }else if((lastValue==10 || lastValue==32) && (stream[ii]==32 || stream[ii]==10)){
                    }else{
                        stream[reached]=stream[ii];
                        lastValue=stream[ii];
                        reached++;
                    }
                }

                if(reached!=count){
                    byte[] cleanedStream=new byte[reached];
                    System.arraycopy(stream, 0,cleanedStream,0,reached);
                    stream=cleanedStream;
                }

                XMLmetadata=new String(stream);
            }

            return XMLmetadata;
        }
    }

	/**return XML data embedded inside PDF in its raw format
     * please use getFileXMLMetaData()
     * *
	public byte[] getFileXMLMetaDataArray(){
		return rawData;
	} /**/
	
	/**set list of field names as file opened by JPedal (should not be used externally)*/
	public void setFileXMLMetaData(byte[] rawData){

		this.rawData=rawData;
	}
	
	/**return list of field values to match field names (legacy non-XML information fields)*/
	public String[] getFieldValues(){
		return information_values;
	}
	
	/**set the information values as file opened by JPedal (should not be used externally)*/
	public void setFieldValue(int i,String convertedValue){
		information_values[i] =convertedValue;
	}

/**
     * read information object and return pointer to correct
     * place
     */
public void readInformationObject(PdfObject infoObj, ObjectDecoder objectDecoder) {


        //get info
        try{
            objectDecoder.checkResolved(infoObj);
        }catch(Exception e){

            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }

        /**
         * set the information values
         **/
        String newValue;
        int id;
        byte[] data;

        int count=PdfFileInformation.information_field_IDs.length;

        //put into fields so we can display
        for (int i = 0; i < count; i++){

            id=PdfFileInformation.information_field_IDs[i];
            if(id==PdfDictionary.Trapped){
                newValue=infoObj.getName(id);

                if(newValue==null)
                    newValue="";

            }else{

                data=infoObj.getTextStreamValueAsByte(id);
                if(data==null)
                    newValue="";
                else
                    newValue= StringUtils.getTextString(data, false);
            }

            setFieldValue(i, newValue);
        }
    }

    /**
        * read the form data from the file
        */
       final public PdfFileInformation readPdfFileMetadata(PdfObject metadataObj,PdfObjectReader currentPdfFile) {

           PdfFileReader objectReader=currentPdfFile.getObjectReader();
           ObjectDecoder objectDecoder=new ObjectDecoder(currentPdfFile.getObjectReader());

           //read info object (may be defined and object set in different trailers so must be done at end)
           DecryptionFactory decryption=objectReader.getDecryptionObject();
           PdfObject infoObject=objectReader.getInfoObject();
           if(infoObject!=null &&(!(decryption!=null && (decryption.getBooleanValue(PDFflags.IS_FILE_ENCRYPTED) || decryption.getBooleanValue(PDFflags.IS_PASSWORD_SUPPLIED)))))
               readInformationObject(infoObject,objectDecoder);

           //read and set XML value
           if(metadataObj!=null){

               String objectRef=new String(metadataObj.getUnresolvedData());

               //byte[] stream= metadataObj.DecodedStream;

               //start old
               //get data
               MetadataObject oldMetaDataObj =new MetadataObject(objectRef);
               objectReader.readObject(oldMetaDataObj);
               byte[] oldstream= oldMetaDataObj.getDecodedStream();
               /** breaks on encrypted (ie preptool)
                boolean failed=PdfReader.checkStreamsIdentical(stream,oldstream);

                if(failed)
                throw new RuntimeException("Mismatch on info streams");
                /////////////////////////////////////
                /**/
               rawData = oldstream;

               /**
                * remove any rubbish at end  (last char should be > so find last >
                * and remove the rest)
                */
               if(rawData!=null){
            	   int count=rawData.length;
            	   while(count>1){
            		   if(rawData[count-1]=='>')
            			   break;

            		   count--;
            	   }

            	   //copy and resize
            	   if(count>0){
            		   byte[] trimmedVersion=new byte[count];
            		   System.arraycopy(rawData,0,trimmedVersion,0,count);
            		   rawData=trimmedVersion;
            	   }
               }

           }

           return this;
       }



}
