/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.UUID;
import java.nio.ByteBuffer;
import java.io.OutputStream;

import com.filepool.fplibrary.FPClip;
import com.filepool.fplibrary.FPLibraryConstants;
import com.filepool.fplibrary.FPLibraryException;
import com.filepool.fplibrary.FPPool;
import com.filepool.fplibrary.FPTag;

/*****************************************************************************
 * 
 * Copyright (c) 2001-2005 EMC Corporation All Rights Reserved
 * 
 * BlobWritePartialExample.java
 * 
 * Example code for using FPTag_BlobWritePartial combined with multiple threads 
 * to accelerate storage of large objects onto Centera.
 * 
 * This sourcefile contains the intellectual property of EMC Corporation or
 * is licensed to EMC Corporation from third parties. Use of this sourcefile
 * and the intellectual property contained therein is expressly limited to
 * the terms and conditions of the License Agreement.
 *  
 ****************************************************************************/

/**
 * <p>
 * Title: BlobWritePartialExample
 * </p>
 * <p>
 * Description: Example code for using FPTag_BlobWritePartial combined with multiple threads to accelerate storage of large objects onto Centera.
 *  
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: EMC Corp.
 * </p>
 */
public class BlobReadPartial {
 
	// Returns an output stream for a ByteBuffer.
    // The write() methods use the relative ByteBuffer put() methods.
    public static OutputStream ByteBufferOutputStream(final ByteBuffer buf) {
        return new OutputStream() {
            public synchronized void write(int b) throws IOException {
                buf.put((byte)b);
            }
    
            public synchronized void write(byte[] bytes, int off, int len) throws IOException {
                buf.put(bytes, off, len);
            }
        };
    }
 
	private static FPPool thePool = null;

	/**
	   * <p>Title: SliceWriter </p>
	   * <p>Description:  Runnable class used for transmitting blob slices to Centera</p>
	   * <p>Copyright: Copyright (c) 2005</p>
	   * <p>Company: EMC Corp.</p>
	   */
	public static class SliceReader implements Runnable {
		
		private FPTag	mTag;
		private OutputStream mStream;
		private long	mOffset;
		private long	mLength;
		private long	mOptions;
		private Exception mException;
		private int     mStatus;
		
		SliceReader(FPTag tag, OutputStream inStream, long offset, long length, long options) {
			mTag = tag;
			mOffset = offset;
			mLength = length;
			mOptions = options;
			mStream = inStream;
			
			mException = null;
			mStatus = 0;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			
			try {
				//log("Beginning transfer of slice #" + mSequenceId);
				//mTag.BlobWritePartial(mStream, mOptions, mSequenceId);
				mTag.BlobReadPartial(mStream, mOffset, mLength, mOptions);
				//log("Completed transfer of slice #" + mSequenceId);
			}
			catch (FPLibraryException e) {
				mException = e;
				mStatus = e.getErrorCode();
			}
			catch (Exception e) {
				mException = e;
				mStatus = -1;
			}
		}
	}
	
	private static void readFile(String clipID, int sliceCount)
		throws FPLibraryException {

		String TAG_NAME = "BlobWritePartialExampleObject";
		String saveFilename = "";

		try {
			FPClip theClip = new FPClip(thePool, clipID, FPLibraryConstants.FP_OPEN_FLAT);

			log("Ok.");

			FPTag blobTag = theClip.FetchNext();
			
			// check clip metadata to see if this is 'our' data format			
			if (!blobTag.getTagName().equals(TAG_NAME)) {
				throw new IllegalArgumentException("This clip was not written by the 'Store Content' sample.");
			}
			
			// Get filename from tag attributes
			String origFilename = blobTag.getStringAttribute("filename");
			saveFilename = origFilename + ".out";

			// write the input file to centera using multiple streams
			readSlicedBlob(blobTag, saveFilename, sliceCount);
			
			blobTag.Close();
			theClip.Close();

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(
				"Could not open file \"" + saveFilename + "\" for reading");
		} catch (IOException e) {
			System.err.println("Error reading from file \"" + saveFilename);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Exception while writing \"" + saveFilename);
			e.printStackTrace();
		}

		return;
	}
	
	
	//ROSARIO
	public static InputStream retrieveIS( FPTag blobTag, int sliceCount ) throws Exception{
		FileOutputStream  out = null;
		FileChannel fc = null;
		try {
			out = retrieveOS(blobTag,sliceCount);
			fc = out.getChannel();
			return Channels.newInputStream(fc);
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				if ( fc != null ){
					fc.close();
				}
				if ( out != null ){
					out.close();
				}				
			} catch (Throwable e2) {
			}
				
		}
	}
	
	public static FileOutputStream retrieveOS( FPTag blobTag, int sliceCount ) throws Exception{
		String outFile = "./"+UUID.randomUUID();
		try {
			
			if (sliceCount < 2) {
				throw new IllegalArgumentException("Slice count must be greater than 1.");
			}
			
			long sliceBytes = blobTag.getBlobSize() / sliceCount;
			long lastSliceBytes = blobTag.getBlobSize() - ((sliceCount -1) * sliceBytes);
			
			if (sliceBytes <= 0 || lastSliceBytes <= 0) {
				throw new IllegalArgumentException("Input file too short to support requested slice count.");
			}

			// We are going to use MemoryMapped I/O for the slices of the output file
			OutputStream[] streams = new OutputStream[sliceCount];
			
			for (int s = 0; s < sliceCount; ++s) {
				long sliceLen = (s < (sliceCount -1)) ? sliceBytes : lastSliceBytes;
				long sliceOffset = s * sliceBytes;
				
				FileChannel channel = (new RandomAccessFile(outFile, "rw")).getChannel();
				ByteBuffer mappedFile = channel.map(FileChannel.MapMode.READ_WRITE, sliceOffset, sliceLen);
				streams[s] = ByteBufferOutputStream(mappedFile);

				blobTag.BlobReadPartial(streams[s], sliceOffset, sliceLen, FPLibraryConstants.FP_OPTION_DEFAULT_OPTIONS);
			}
			
			return new FileOutputStream("outFile");
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	/**
	 *  readSlicedBlob
	 * 
	 *  @param newTag
	 *  @param file
	 *  @param sliceCount
	 */
	private static boolean readSlicedBlob(FPTag blobTag, String outFile, int sliceCount) throws Exception  {
		boolean success = true;
		Exception lastException = null;
		
		if (sliceCount < 2) {
			throw new IllegalArgumentException("Slice count must be greater than 1.");
		}
		
		long sliceBytes = blobTag.getBlobSize() / sliceCount;
		long lastSliceBytes = blobTag.getBlobSize() - ((sliceCount -1) * sliceBytes);
		
		if (sliceBytes <= 0 || lastSliceBytes <= 0) {
			throw new IllegalArgumentException("Input file too short to support requested slice count.");
		}

		// We are going to use MemoryMapped I/O for the slices of the output file
		OutputStream[] streams = new OutputStream[sliceCount];
		SliceReader[] readers = new SliceReader[sliceCount];
		Thread[] workers = new Thread[sliceCount];
		
		for (int s = 0; s < sliceCount; ++s) {
			long sliceLen = (s < (sliceCount -1)) ? sliceBytes : lastSliceBytes;
			long sliceOffset = s * sliceBytes;
			
			FileChannel channel = (new RandomAccessFile(outFile, "rw")).getChannel();
			ByteBuffer mappedFile = channel.map(FileChannel.MapMode.READ_WRITE, sliceOffset, sliceLen);
			streams[s] = ByteBufferOutputStream(mappedFile);
			readers[s] = new SliceReader(blobTag, 
										streams[s], 
										sliceOffset,
										sliceLen,
										FPLibraryConstants.FP_OPTION_DEFAULT_OPTIONS);

			workers[s] = new Thread(readers[s]);
			
			// fire it up
			workers[s].start();
		}
		
		
		//
		// Now we wait for all of the worker threads to complete, order is unimportant
		//
		System.out.print("Worker Thread completions: ");
		for (int s = 0; s < sliceCount; ++s) {
			try {
				workers[s].join();
				
				System.out.print(" " + s + ":");
				if (0 == readers[s].mStatus) {
					System.out.print("success");
				} else {
					System.out.print("errcode=" + readers[s].mStatus);
					success = false;
					lastException = readers[s].mException;
				}
			} catch (InterruptedException e) {
				log("Thread number " + s + " was unexpectedly interrupted.");
			} finally {
				streams[s].close();
			}
			
			log("");
		}		
		
		if (null != lastException) {
			throw lastException;
		}
		
		return success;
	}
	
	static void log(String message){
		//System.out.println(message);
	}

	/**
	 * main
	 * 
	 */
	public static void main(String[] args) {

		String poolAddress = "us1cas1.centera.org?c:\\pea\\us1.pea";
		int exitCode = 0;
		// Threshold for when to embed blobs in CDF, in bytes
		int sliceCount = 5;
		String clipId = "8EG5JQPAAKLP2eD1QGCEGVNV5PR";
		InputStreamReader inputReader = new InputStreamReader(System.in);
		BufferedReader stdin = new BufferedReader(inputReader);

		try {

			// New feature for 2.3 lazy pool open
			FPPool.setGlobalOption(
				FPLibraryConstants.FP_OPTION_OPENSTRATEGY,
				FPLibraryConstants.FP_LAZY_OPEN);

			// Prompt user for cluster to connect to
			System.out.print("Address of cluster[" + poolAddress + "]: ");
			String answer = stdin.readLine();

			if (!answer.equals(""))
				poolAddress = answer;

			log(
				"Connecting to Centera cluster(" + poolAddress + ")");

			// open cluster connection
			thePool = new FPPool(poolAddress);

			// Prompt user for embedded blob size
			log(
				"Content Address of C-Clip [" +
				clipId +
				"]: ");
			answer = stdin.readLine();
			if (!answer.equals(""))
				clipId = answer;
			
			// Prompt user for number of slices
			log(
				"Number of slices for BlobPartialRead ["
					+ sliceCount
					+ "]: ");
			answer = stdin.readLine();

			if (!answer.equals(""))
				sliceCount = Integer.parseInt(answer);
			
			if (sliceCount < 1)
				throw new IllegalArgumentException("Invalid slice count selection.");
			
			// Read the blob from centera to our file
			readFile(clipId, sliceCount);

			// Always close the Pool connection when finished.
			thePool.Close();
			log(
				"\nClosed connection to Centera cluster (" + poolAddress + ")");

			inputReader.close();
			stdin.close();

		} catch (FPLibraryException e) {
			exitCode = e.getErrorCode();
			System.err.println(
				"Centera SDK Error: " + e.getMessage() + "(" + exitCode + ")");
		} catch (IOException e) {
			log("I/O Error occured: " + e.getMessage());
			e.printStackTrace();
			exitCode = -1;
		} catch (IllegalArgumentException e) {
			log(e.getMessage());
			e.printStackTrace();
			exitCode = -1;
		}

		System.exit(exitCode);

	}

}
