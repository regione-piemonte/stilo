/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.cryptosigner.utils.MessageConstants;

public interface FileOpMessage extends MessageConstants {
	public static final String PDF_OP_ERROR="PDF_OP.ERROR";
	public static final String PDF_OP_NO_CONVERTIBLE_FORMAT="PDF_OP.NO_CONVERTIBLE_FORMAT";
	public static final String PDF_OP_NO_CONVERTIBLE_SIGN_FORMAT="PDF_OP.NO_CONVERTIBLE_SIGN_FORMAT";
	public static final String PDF_OP_NO_CONVERTIBLE_XFORM="PDF_OP.NO_CONVERTIBLE_XFORM";
	
	//format recognition message
	public static final String FR_MIME_UNKNOWN="FR.MIME_UNKNOWN";
	public static final String FR_FORMAT_NOT_FOUND="FR.FORMAT_NOT_FOUND";
	public static final String FR_DB_ERROR="FR.DB_ERROR";
	
	public static final String SIGN_OP_ERROR="SIGN_OP.ERROR";
	public static final String SIGN_OP_WARNING="SIGN_OP.WARNING";
	public static final String SIGN_OP_GENERIC_ERROR="SIGN_OP.GENERIC_ERROR";
	public static final String SIGN_OP_FILENOTSIGNED="SIGN_OP.FILENOTSIGNED";
	
	public static final String CD_OP_ERROR="CD_OP.ERROR";
	public static final String CD_OP_WARNING="CD_OP.WARNING";
	public static final String CD_OP_MIMETIPE_NOTFOUND="CD_OP.MIMETIPE_NOTFOUND";
	public static final String CD_OP_MIMETIPE_NOTVERIFIABLE="CD_OP.MIMETIPE_NOTVERIFIABLE";
	
	public static final String TIMBRO_OP_ERROR="TIMBRO_OP.ERROR";
	public static final String TIMBRO_OP_FORMAT_NOTPDF="TIMBRO_OP.FORMAT_NOTPDF";
	public static final String TIMBRO_OP_FORMAT_PDFXFORM="TIMBRO_OP_FORMAT_PDFXFORM";
	public static final String TIMBRO_OP_MISSING_TEXT="TIMBRO_OP.MISSING_TEXT";
	public static final String TIMBRO_OP_PDF_PASSWORD="TIMBRO_OP_PDF_PASSWORD";
	public static final String TIMBRO_OP_POSITION_UNSUITABLE="TIMBRO_OP.POSITION_UNSUITABLE";
	public static final String TIMBRO_OP_SAME_POSITION_TEXT="TIMBRO_OP.SAME_POSITION_TEXT";
	
	public static final String DIGEST_OP_ERROR="DIGEST_OP.ERROR";
	
	public static final String UNPACK_OP_ERROR="UNPACK_OP.ERROR";
	
	public static final String UNPACKMULTIPART_OP_ERROR_FILE_NOTVALID="UNPACKMULTIPART_OP.ERROR_FILE_NOTVALID";
	public static final String UNPACKMULTIPART_OP_ERROR="UNPACKMULTIPART_OP.ERROR";
	public static final String UNPACKMULTIPART_OP_ERROR_FILE="UNPACKMULTIPART_OP.ERROR_FILE";
	
	public static final String COMPRESS_OP_ERROR="COMPRESS_OP.ERROR";
	
	public static final String UNPACKDIGEST_OP_ERROR="UNPACKDIGEST_OP.ERROR";
	
	public static final String VISIBLE_SIGN_ROW_ERROR="VISIBLE_SIGN_ROW.ERROR";
	public static final String VISIBLE_SIGN_COLUMN_ERROR="VISIBLE_SIGN_COLUMN.ERROR";
	public static final String VISIBLE_SIGN_LIMIT_ERROR="VISIBLE_SIGN_LIMIT.ERROR";
}
