/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class FTPPoolUTest {

	public static void test1(int size) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(1);
		config.setMaxTotal(10);
		config.setMinIdle(5);

		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		// FTPWrapFactory sftpWrap = new FTPWrapFactory("ros", "alfaromeo", "localhost", 990, 60000, FTPWrap.TLS, true, false, "/");
		FTPWrapFactory sftpWrap = new FTPWrapFactory("ros", "alfaromeo", "localhost", 21, 60000, FTPWrap.FTP, false, false, "/", true);
		FTPWrapPool pool = new FTPWrapPool(sftpWrap, config);

		List<FTPWrap> lista = new ArrayList<FTPWrap>();
		try {
			FTPWrap obj = null;
			for (int index = 0; index < size; index++) {
				obj = pool.borrowObject();
				lista.add(obj);

				InputStream is = new FileInputStream("D:/Appy/2016/10/14/1234567890.txt");
				String remotePath = "2016/10/14/";
				String fileName = "pippo";

				boolean ret = obj.store(is, remotePath, fileName);
				if (ret) {
					FileOutputStream fos = new FileOutputStream("D:/appy/Rosariooooooooooo.txt");
					// InputStream fis = obj.extract(remotePath, fileName);
					// System.out.println("Estrazione eseguita [" + fis.available() + "]");
					obj.extract(fos, remotePath, fileName);

				} else {
					System.out.println("File non scritto su FTP Server");
				}

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

	public static void main(String[] args) {
		test1(1);
	}

}
