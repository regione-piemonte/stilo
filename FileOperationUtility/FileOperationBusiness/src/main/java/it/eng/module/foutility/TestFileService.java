/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.module.foutility.beans.BeanFile;
import it.eng.module.foutility.beans.BeanString;
import it.eng.module.foutility.beans.FOResponse;
import it.eng.module.foutility.beans.generated.Operations;
import it.eng.module.foutility.beans.generated.Response;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse.FileOperationResults;
import it.eng.module.foutility.exception.FOException;

@Service(name = "TestFileService")
public class TestFileService {

	private static final Logger log = LogManager.getLogger(TestFileService.class);

	@Operation(name = "execute")
	public FOResponse execute(Operations operations, File signedFile, File timestamp, HashSet<File> colls, BeanFile bean) throws FOException {
		log.debug("test attach started");
		// stamp i file ricevuti
		log.debug("signedFile:" + signedFile);
		log.debug("timestampFile:" + timestamp);
		log.debug("colls:" + colls);
		log.debug("beanFile:" + bean);
		log.debug("---------------------------------");
		log.debug("colls class" + colls.getClass());

		// bean della risposta
		FOResponse resp = new FOResponse();
		Response ret = new Response();
		File file = new File("c:/testAttach/response.ppt");
		// File file= new File("C:/VIDEO_E_FOTO/Film/Cado.Dalle.Nubi.2009.iTALiAN.LD.DVDRip.XViD-M3SN[S.o.M.]_MP3.avi");

		resp.setForesults(new FileOperationResults());
		resp.setResponsefile(file);
		log.debug("test attach terminated");
		return resp;
	}

	@Operation(name = "getFileTest")
	public File getFileTest(File file1, File file2) throws Exception {
		log.debug("file1:" + file1);
		log.debug("file2:" + file2);
		File returned = new File("c:/testhello.txt");
		// File returned= new File("C:/VIDEO_E_FOTO/Film/Cado.Dalle.Nubi.2009.iTALiAN.LD.DVDRip.XViD-M3SN[S.o.M.]_MP3.avi");
		return returned;
	}

	@Operation(name = "getFileListTest")
	public List<File> getListFileTest(File file1, File file2) {
		log.debug("file1:" + file1);
		log.debug("file2:" + file2);
		List<File> files = new ArrayList<File>();
		files.add(file1);
		files.add(file2);
		return files;
	}

	@Operation(name = "getMultiTest")
	public List<File> getMultiTest(File file1, BeanString prop2, HashSet<File> prop4) {
		log.debug("file1:" + file1);

		List<File> files = new ArrayList<File>();
		files.add(file1);
		for (Iterator iterator = prop4.iterator(); iterator.hasNext();) {
			File file = (File) iterator.next();
			files.add(file);
		}

		return files;
	}

	@Operation(name = "getStringRetNoInput")
	public String getStringRetNoInput() {

		return "hello worl.. no input";
	}

	@Operation(name = "getStringRet")
	public String getStringRet(String par1, Date pardate, int parint) {

		return "hello worl..   input" + par1 + pardate + parint;
	}

	@Operation(name = "getDateRet")
	public Date getDateRet(String par1, Date pardate, int parint) {
		log.debug("par1 " + par1);
		log.debug("pardate " + pardate);
		log.debug("parint " + parint);
		return new Date();
	}

	@Operation(name = "getBean")
	public BeanString getBean(BeanString prop2) {
		log.debug("prop2:" + prop2);

		return prop2;
	}

	@Operation(name = "getExceptionTest")
	public BeanString getExceptionTest(BeanString prop2) throws Exception {
		log.debug("prop2:" + prop2);
		if (true) {
			throw new Exception("di prova");
		}
		return prop2;
	}
}
