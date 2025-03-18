/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.*;

public class JPEGDCInputStream extends InputStream{

  private JPEGHuffmanInputStream in;
  private int PRED;

  public JPEGDCInputStream(JPEGHuffmanInputStream in){
    this.in=in;
    PRED=0;
  }

  public void restart()throws IOException{      // Call at beginning of restart interval
    in.restart();
    PRED=0;
  }

  public int read()throws IOException{          // [1] p.104 decode
    int T=in.read();                            
    if(T==-1){return -1;}                       
    PRED+=in.readBits(T);
    return PRED;
  }
}