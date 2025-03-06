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

  * LinearThread.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package org.jpedal.linear;

import org.jpedal.PdfDecoder;
import org.jpedal.io.LinearizedHintTable;
import org.jpedal.io.PdfReader;
import org.jpedal.objects.raw.PdfDictionary;
import org.jpedal.objects.raw.PdfObject;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * handles download of rest of file in Linearized mode
 */
public class LinearThread extends Thread {

    public int percentageDone=0;

    FileChannel fos;
    PdfObject linearObj;
    InputStream is;
    File tempURLFile;
    LinearizedHintTable linHintTable;

    final byte[] startObj=new byte[]{'o','b','j'},endObj=new byte[]{'e','n','d','o','b','j'};
    int startCharReached=0, endCharReached=0;
    int startObjPtr=0,endObjPtr=0;

    //use top line to slow down load speed
    int bufSize=8192, lastBytes=8192;

    int generation=0,ref=0,firstObjLength=0;

    PdfDecoder decode_pdf;

    public LinearThread(InputStream is, FileChannel fos, File tempURLFile, PdfObject linearObj, byte[] linearBytes, LinearizedHintTable linHintTable, PdfDecoder decode_pdf) {

        this.fos=fos;
        this.linearObj=linearObj;
        this.is=is;
        this.tempURLFile=tempURLFile;
        this.linHintTable=linHintTable;

        this.decode_pdf=decode_pdf;

        //scan start of file for objects
        firstObjLength=linearBytes.length;

        scanStreamForObjects(0, null, linearBytes);

    }

    public int getPercentageLoaded(){
        return percentageDone;
    }

    public void run() {

        final int linearfileLength=linearObj.getInt(PdfDictionary.L);

        try{

            int read,bytesRead=0;

            //we cache last few bytes incase ref rolls across boundary
            byte[] lastBuffer=new byte[lastBytes];

            byte[] buffer = new byte[bufSize];
            byte[] b;
            while ((read = is.read(buffer)) != -1 && !this.isInterrupted() && isAlive()) {

                if(read>0){
                    synchronized (fos){

                        b=new byte[read];
                        System.arraycopy(buffer,0,b,0,read);
                        buffer=b;
                        ByteBuffer f=ByteBuffer.wrap(b);
                        fos.write(f);
                    }
                }

                //track start endobj and flag so we know if object read
                if(read>0){
                    scanStreamForObjects(firstObjLength+bytesRead, lastBuffer, buffer);

                    bytesRead=bytesRead+read;

                    //save last few bytes incase of overlap
                    int aa=30;

                    int size1=buffer.length;

                    if(aa>size1-1)
                        aa=size1-1;

                    lastBuffer=new byte[aa];
                    System.arraycopy(buffer, size1-aa, lastBuffer,0,aa);
                }

                percentageDone= (int) (100*((float)bytesRead/(float)linearfileLength));

                //debug code to simulate slow file stream by slowing down read of data from file
               // System.out.println("percentage="+percentageDone);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            }

            linHintTable.setFinishedReading();


        } catch (IOException e) {
            //tell user and log
            if(LogWriter.isOutput())
                LogWriter.writeLog("Exception: "+e.getMessage());

        } finally{

            try{
                is.close();

                //possible that page still being decoded on slower machine so wait
                decode_pdf.waitForDecodingToFinish();

                decode_pdf.currentPdfFile = new PdfReader();

                /** get reader object to open the file if all downloaded*/
                if(isAlive() && !isInterrupted()){
                    decode_pdf.openPdfFile(tempURLFile.getAbsolutePath());

                    /** store fi name for use elsewhere as part of ref key without .pdf */
                    decode_pdf.objectStoreRef.storeFileName(tempURLFile.getName().substring(0, tempURLFile.getName().lastIndexOf('.')));
                }

            }catch(Exception e){
                //tell user and log
                if(LogWriter.isOutput())
                    LogWriter.writeLog("Exception: "+e.getMessage());
            }
        }
    }

    private void scanStreamForObjects(int bytesRead, byte[] lastBuffer, byte[] buffer) {

        int bufSize=buffer.length;

        for(int i=0;i<bufSize;i++){

            if(startCharReached==0){ //look for gap at start of obj
                if(buffer[i]==' ' || buffer[i]==0 || buffer[i]==10 || buffer[i]==32 )
                    startCharReached++;
            }else if(startCharReached<4){ //look for rest of obj

                if(buffer[i]==startObj[startCharReached-1]){

                    if(startCharReached==3){ //start found so read object ref and log start

                        startObjPtr=bytesRead+i-4;

                        //get the values
                        int ii=i-4;

                        byte[] data;

                        //add in last buffer to allow for crossing boundary
                        if(lastBuffer!=null && ii<30){

                            int size1=lastBuffer.length;
                            int size2=buffer.length;
                            data =new byte[size1+size2];
                            System.arraycopy(lastBuffer,0, data,0,size1);
                            System.arraycopy(buffer,0, data,size1,size2);

                            ii=ii+size1;

                        }else{
                            data =buffer;
                        }

                        int keyEnd =ii;

                        //generation value
                        while(data[ii]!=10 && data[ii]!=13 && data[ii]!=32 && data[ii]!=9){
                            ii--;
                            startObjPtr--;
                        }

                        generation= NumberUtils.parseInt(ii + 1, keyEnd, data);

                        //roll back to start of number
                        while(data[ii]==10 || data[ii]==13 || data[ii]==32 || data[ii]==47 || data[ii]==60){
                            ii--;
                            startObjPtr--;
                        }

                        keyEnd =ii+1;

                        while(data[ii]!=10 && data[ii]!=13 && data[ii]!=32 && data[ii]!=47 && data[ii]!=60 && data[ii]!=62){
                            ii--;
                            startObjPtr--;
                        }

                        ref= NumberUtils.parseInt(ii + 1, keyEnd, data);

                    }

                    startCharReached++;
                }else
                    startCharReached=0;

            }else{
                if(buffer[i]==endObj[endCharReached]){
                    endCharReached++;

                    if(endCharReached==6){
                        endObjPtr=bytesRead+i;

                        //currentPdfFile.storeOffset()
                        linHintTable.storeOffset(ref,startObjPtr,endObjPtr);

                        startCharReached=0;
                        endCharReached=0;
                    }
                }else
                    endCharReached=0;
            }
        }
    }
}
