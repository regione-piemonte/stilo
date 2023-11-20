/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SFTPPoolUTest {

	public static void test1(int size) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(1);
		config.setMaxTotal(10);
		config.setMinIdle(5);

		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		SFTPWrapFactory sftpWrap = new SFTPWrapFactory("root", "C0ns3rvaz10n3", "10.159.67.84", 22, 60000);
		SFTPWrapPool pool = new SFTPWrapPool(sftpWrap, config);

		List<SFTPWrap> lista = new ArrayList<SFTPWrap>();
		try {
			SFTPWrap obj = null;
			for (int index = 0; index < size; index++) {
				obj = pool.borrowObject();
				lista.add(obj);
			}

			for (int index = 0; index < lista.size(); index++) {
				System.out.println("Return");
				pool.returnObject(lista.get(index));
			}

			System.out.println("EXIT");

			for (int index = 0; index < lista.size(); index++) {
				System.out.println("Invalida");
				pool.invalidateObject(lista.get(index));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test2() {
		try {
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			config.setMaxIdle(1);
			config.setMaxTotal(10);
			config.setMinIdle(5);

			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			SFTPWrapFactory sftpWrap = new SFTPWrapFactory("ftptest", "password", "10.159.67.66", 22, 60000);
			SFTPWrapPool pool = new SFTPWrapPool(sftpWrap, config);

			SFTPWrap wrap = pool.borrowObject();
//			wrap.downloadDirectory("/home/ftptest/COMUNE_CERTALDO", "D:/Appy/");
//			
			String currentDir = "ENTE_TEST"; 
	        String saveDir = "E:/WorkTemp/transfered"; 
	        //wrap.deleteFile("ENTE_TEST/METADATI_a_b123.CNT.Contratto16112017_2.xml"); 
	        wrap.deleteDirectory(currentDir+"/a_b123.CNT.Contratto16112017_2/CNT/ALLEGATI"); 
	        //wrap.deleteFile("ENTE_TEST/METADATI_a_b123.CNT.Contratto16112017_2.xml");

			pool.invalidateObject(wrap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		test1(1);
		test2();
	}

}
