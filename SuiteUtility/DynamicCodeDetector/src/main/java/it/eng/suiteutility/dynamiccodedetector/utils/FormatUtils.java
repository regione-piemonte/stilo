package it.eng.suiteutility.dynamiccodedetector.utils;

import java.util.Properties;

public class FormatUtils {
	
	public static final Properties officeMimeTypes2OOMimeTypes = new Properties(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			this.put("application/msword", 				"application/vnd.oasis.opendocument.text");
			this.put("application/vnd.msword", 			"application/vnd.oasis.opendocument.text");
			this.put("application/vnd.ms-word", 		"application/vnd.oasis.opendocument.text");
			this.put("application/winword", 			"application/vnd.oasis.opendocument.text");
			this.put("application/word", 				"application/vnd.oasis.opendocument.text");
			this.put("application/x-msw6", 				"application/vnd.oasis.opendocument.text");
			this.put("application/x-msword", 			"application/vnd.oasis.opendocument.text");
			this.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.oasis.opendocument.text");
			this.put("application/vnd.ms-powerpoint", 	"application/vnd.oasis.opendocument.presentation");
			this.put("application/mspowerpoint", 		"application/vnd.oasis.opendocument.presentation");
			this.put("application/ms-powerpoint", 		"application/vnd.oasis.opendocument.presentation");
			this.put("application/vnd-mspowerpoint", 	"application/vnd.oasis.opendocument.presentation");
			this.put("application/powerpoint", 			"application/vnd.oasis.opendocument.presentation");
			this.put("application/x-powerpoint", 		"application/vnd.oasis.opendocument.presentation");
			this.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.oasis.opendocument.presentation");
			this.put("application/vnd.ms-excel",		"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/msexcel", 			"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/x-msexcel", 			"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/x-ms-excel", 			"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/vnd.ms-excel", 		"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/x-excel", 			"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/x-dos_ms_excel", 		"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/xls", 				"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/excel", 				"application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.oasis.opendocument.spreadsheet");
			this.put("application/dot", 				"application/vnd.oasis.opendocument.text-template");
			this.put("application/x-dot", 				"application/vnd.oasis.opendocument.text-template");
			this.put("application/doc", 				"application/vnd.oasis.opendocument.text-template");
		}
	};
	
	public static final Properties officeMimeTypes2OfficeFormat = new Properties(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			this.put("application/msword", 				"doc");
			this.put("application/vnd.msword", 			"doc");
			this.put("application/vnd.ms-word", 		"doc");
			this.put("application/winword", 			"doc");
			this.put("application/word", 				"doc");
			this.put("application/x-msw6", 				"doc");
			this.put("application/x-msword", 			"doc");
			this.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
			this.put("application/vnd.ms-powerpoint", 	"ppt");
			this.put("application/mspowerpoint", 		"ppt");
			this.put("application/ms-powerpoint", 		"ppt");
			this.put("application/vnd-mspowerpoint", 	"ppt");
			this.put("application/powerpoint", 			"ppt");
			this.put("application/x-powerpoint", 		"ppt");
			this.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
			this.put("application/vnd.ms-excel",		"xls");
			this.put("application/msexcel", 			"xls");
			this.put("application/x-msexcel", 			"xls");
			this.put("application/x-ms-excel", 			"xls");
			this.put("application/vnd.ms-excel", 		"xls");
			this.put("application/x-excel", 			"xls");
			this.put("application/x-dos_ms_excel", 		"xls");
			this.put("application/xls", 				"xls");
			this.put("application/excel", 				"xls");
			this.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
			this.put("application/dot", 				"dot");
			this.put("application/x-dot", 				"dot");
			this.put("application/doc", 				"dot");
		}
	};
	
	public static final Properties ooMimeTypes2OfficeMimeTypes = new Properties(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			this.put("application/vnd.oasis.opendocument.presentation", 		"application/vnd.ms-powerpoint");
			this.put("application/x-vnd.oasis.opendocument.presentation", 		"application/vnd.ms-powerpoint");
			this.put("application/vnd.oasis.opendocument.spreadsheet", 			"application/vnd.ms-excel");
			this.put("application/x-vnd.oasis.opendocument.spreadsheet", 		"application/vnd.ms-excel");
			this.put("application/vnd.oasis.opendocument.text", 				"application/vnd.msword");
			this.put("application/x-vnd.oasis.opendocument.text", 				"application/vnd.msword");
		}
	};
	
	public static final Properties ooMimeTypes2OoFormat = new Properties(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			this.put("application/vnd.oasis.opendocument.presentation", 		"odp");
			this.put("application/x-vnd.oasis.opendocument.presentation", 		"odp");
			this.put("application/vnd.oasis.opendocument.spreadsheet", 			"ods");
			this.put("application/x-vnd.oasis.opendocument.spreadsheet", 		"ods");
			this.put("application/vnd.oasis.opendocument.text", 				"odt");
			this.put("application/x-vnd.oasis.opendocument.text", 				"odt");
		}
	};
		
}