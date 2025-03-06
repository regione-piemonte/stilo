package it.test.mime;

import java.util.ArrayList;

 

public class MimePdf {
	
	public static void main(String[] args)throws Exception {
		
		String entry="0	string		%PDF-		application/pdf";
		ArrayList list= new ArrayList();
		list.add(entry);
//		MyMagicMimeEntry m = new MyMagicMimeEntry(list);
//		System.out.println(m);
//		MyMagicMimeEntry result=m.getMatch(new RandomAccessFile("c:/filev1.pdf","r"));
//		System.out.println("result:"+result);
	}
}
