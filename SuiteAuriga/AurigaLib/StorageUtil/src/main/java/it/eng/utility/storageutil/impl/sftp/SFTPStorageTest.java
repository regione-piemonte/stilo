/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageConfig;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.impl.StorageServiceImpl;

public class SFTPStorageTest extends Thread implements Observer{
	
	public static final String ID_UTILIZZATORE = "IRIS@3";
	public static final String TAG_IRIS = "[SFTP@IRIS]";
	private static StorageService storageService = null;
	
	
	public SFTPStorageTest(String storageDir, SFTPThr pcent, String storageXMLConfig){
		super(pcent);
		try {
			if ( storageService == null ){
				StorageConfig.configure(storageDir, "storage",storageXMLConfig);
				System.out.println("Created storage"); 	
				
				GenericStorageInfo info = new GenericStorageInfo() {			
					public String getUtilizzatoreStorageId() {
						return SFTPStorageTest.ID_UTILIZZATORE;
					}
				};
				storageService = StorageServiceImpl.newInstance(info);
				if (storageService != null){
					System.out.println("Instanced Storage storageService ["+ storageService +"]");
					System.out.println("Instanced Storage getConfigurazioniStorage["+ storageService.getConfigurazioniStorage() +"]");
					System.out.println("Instanced Storage getTipoStorage["+ storageService.getTipoStorage() +"]");
				}else{
					throw new Exception("Storage is null");
				}	
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public SFTPStorageTest(String storageDir, String storageXMLConfig){
		try {
			if ( storageService == null ){
				StorageConfig.configure(storageDir, "storage",storageXMLConfig);
				System.out.println("Created storage"); 	
				
				GenericStorageInfo info = new GenericStorageInfo() {			
					public String getUtilizzatoreStorageId() {
						return SFTPStorageTest.ID_UTILIZZATORE;
					}
				};
				storageService = StorageServiceImpl.newInstance(info);
				if (storageService != null){
					System.out.println("Instanced Storage storageService ["+ storageService +"]");
					System.out.println("Instanced Storage getConfigurazioniStorage["+ storageService.getConfigurazioniStorage() +"]");
					System.out.println("Instanced Storage getTipoStorage["+ storageService.getTipoStorage() +"]");
				}else{
					throw new Exception("Storage is null");
				}	
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public String insert(File file)throws Throwable{		
		String clipId = "";
		try {
			clipId = storageService.store(file);
			System.out.println( "Inserimento su SFTP eseguito con id restituito ["+ clipId +"]" );
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		return clipId;
	}
	
	public void extract(String clipId)throws Throwable{		
		try {
			getFileStream(clipId);
			System.out.println( "Estrazione da SFTP eseguita ["+ clipId +"]" );
			
		} catch(Throwable e) {
			System.out.println("[FAIL] ["+ storageService +"]["+ clipId +"]");
			e.printStackTrace();
		}
	}
	
	public void remove(String clipId)throws Throwable{		
		try {
			storageService.delete(clipId);
			System.out.println( "Eliminazione file su SFTP eseguita ["+ clipId +"]" );
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}	
	
	
	private static String saveFile(StorageService serviceStorage, InputStream stream) throws StorageException{
		return serviceStorage.storeStream(stream);
	}
	
	private void getFileStream(String id) throws StorageException, IOException  {
		InputStream is = storageService.extract(TAG_IRIS+id);
		File tmp=new File("./file-estratto");
		IOUtils.copy(is, FileUtils.openOutputStream(tmp));
	}
	
	private void getFile(String id) throws StorageException, IOException  {
		File is = storageService.extractFile(TAG_IRIS+id);
		System.out.println("PATH>"+is.getAbsolutePath());
	}
	
	public static void testInsert(String[] args){
		String operId = args[0];
		System.out.println("operid>"+operId);
		String storageDir = args[1];
		System.out.println("storageDir>"+storageDir);
		String[] clips = args[2].split("@");
		System.out.println("clips>"+args[2]);
		
		int numThreads=clips.length;
		
		SFTPStorageTest[] testers =  new SFTPStorageTest[numThreads];
		
//		Thread[] threads = new Thread[numThreads];

		long startTime = System.currentTimeMillis();
		
		int iOperId = Integer.parseInt(operId);
		SFTPThr pcent= null;
		Vector<SFTPThr> pcents = new Vector<SFTPThr>();
		for (int i = 0; i < testers.length; i++) {
			File file = new File(clips[i]);
			pcent= new SFTPThr(iOperId,i,file);
			testers[i] = new SFTPStorageTest(storageDir,pcent, "/storage-h2.cfg.xml");
			pcent.setTester(testers[i]);
			pcents.add(pcent);
		}
		
		for (int i = 0; i < testers.length; i++) {
			pcents.get(i).setStartTime(startTime);
		}
		
		System.out.println("StartTime>"+startTime);
		for (int index = 0; index < testers.length; index++) {
			testers[index].start();
		}

	}
	
	public static void testRetrieve(String[] args){
		String id = args[0];
		System.out.println("id>"+id);
		
		String storageDir = args[1];
		System.out.println("storageDir>"+storageDir);
		
		SFTPStorageTest test = new SFTPStorageTest(storageDir,"/storage-h2.cfg.xml");
		
		try {
			test.getFile(id);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void testRetrieve(String id, String storageDir){

		System.out.println("id>"+id);
		System.out.println("storageDir>"+storageDir);
		
		SFTPStorageTest test = new SFTPStorageTest(storageDir,"/storage-h2.cfg.xml");
		
		try {
			test.getFileStream(id);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable{
		System.out.println("Starting ....  ");
		//[SFTP@IRIS]/
		//args = new String[]{"/2017/01/19/20170119114038163133470737444407779","D:/Projects/ECM/Clienti/REGIONE_TOSCANA/DR" }; //D:\Projects\ECM\Clienti\REGIONE_TOSCANA\DR
		//args = new String[]{"DCNespresso.tgz","D:/Workspaces/ECM_AURIGA/StorageUtil" };
		//testRetrieve(args);
		testRetrieve(args[0],args[1]);
		System.out.println("end ....  "); 
		
	}

	@Override
	public void update(Observable o, Object arg) {
		SFTPThr pcent = (SFTPThr) o;
		System.out.println("Stop me !!!!");
	}

}

class SFTPThr extends Observable implements Runnable{
	
	private SFTPStorageTest tester = null;
	private int index = 0;
	private int operId = 1;
	private long startTime;

	String clipId;
	File file = null;
	
	public SFTPThr(int operId, int index){
		this.operId = operId;
		//this.tester = tester;
		this.index = index;
		//this.addObserver(tester);
	}
	
	public SFTPThr(int operId, int index, String clipId){
		this(operId, index);
		this.clipId = clipId;
	}
	
	public SFTPThr(int operId, int index,  File file){
		this(operId, index);
		this.file = file;

	}
	
	public void setStartTime(long startTime){
		this.startTime = startTime;
	}

	@Override
	public void run() {
		try {
			System.out.println("Start ....  ["+ index +"]"); 
			
			switch (operId) {
			case 1:
				System.out.println("Operazione insert ["+ operId +"]");
				this.tester.insert(file);
				break;
			case 2:
				System.out.println("Operazione extract ["+ operId +"]");
				this.tester.extract(clipId);
				break;
			case 3:
				System.out.println("Operazione delete ["+ operId +"]");
				this.tester.remove(clipId);
				break;

			default:
				System.out.println("Nessuna operazione eseguita ["+ operId +"]"); 
				break;
			}
			long stopTime = System.currentTimeMillis();
			
			
			System.out.println("End ....  ["+ index +"] Elapsed ["+( stopTime - startTime )+"]");
			this.setChanged();
			this.notifyObservers();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	public SFTPStorageTest getTester() {
		return tester;
	}

	public void setTester(SFTPStorageTest tester) {
		this.tester = tester;
		this.addObserver(tester);
	}
	
}
