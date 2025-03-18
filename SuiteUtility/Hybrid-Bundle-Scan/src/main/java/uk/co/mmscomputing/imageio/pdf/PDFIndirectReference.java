/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.*;

public class PDFIndirectReference extends PDFObject{

  private PDFIndirectObject obj;

  public PDFIndirectReference(PDFIndirectObject obj){
    this.obj=obj;
  }

//  public void setDirectObject(PDFObject v){obj.setDirectObject(v);}
//  public PDFObject  getDirectObject(){ return obj.getDirectObject();}

  public PDFIndirectObject  getIndirectObject(){ return obj;}

  public String toString(){
    return obj.getObjectNumber() +" "+obj.getGenerationNumber()+" R ";
  }
}

