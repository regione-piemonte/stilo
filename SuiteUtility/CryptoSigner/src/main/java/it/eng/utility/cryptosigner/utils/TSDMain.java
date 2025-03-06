package it.eng.utility.cryptosigner.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.tsp.cms.CMSTimeStampedData;

public class TSDMain {
	
	//private static final Logger logger = LoggerFactory.getLogger(TSDDetector.class);
		
	public static void main(String[] args) throws Exception {
		
		/*file di input con il tsd*/
		File f = new File("C:/FileControl/1/PARTE I n. 47 del 12.10.2011.pdf.p7m.tsd");
		File f1 = new File("C:/FileControl/1/test.pdf.p7m");
		File f2 = new File("C:/FileControl/1/test.tsr");
		
		CMSTimeStampedData data = new CMSTimeStampedData(FileUtils.openInputStream(f));
		FileUtils.writeByteArrayToFile(f1, data.getContent());
		FileUtils.writeByteArrayToFile(f2, data.getTimeStampTokens()[0].getEncoded());
		
		
		
		
		
		
//		TimeStampResponse resp = new TimeStampResponse(FileUtils.readFileToByteArray(f));
//		
//		/*file di output per il salvataggio del p7m*/
//		File fo = new File("C:/FileControl/1/file.pdf.p7m");
//		
//		/*file di output per il salvataggio del contenuto del p7m*/
//		File fo2 = new File("C:/FileControl/1/file.pdf");
//		
//		try {
//			
//			TSDUtil tsd = new TSDUtil(f);
//			tsd.saveP7M(fo);
//			tsd.saveContent(fo2);
//			
//			//logger.info("letto");
//		} catch (FileNotFoundException e) {			
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (CMSException e) {
//			e.printStackTrace();
//		}
	}
}
