/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.pt.awt.font.NFontType1;
import com.pt.io.FileList;
import com.pt.io.InputUni;
import com.pt.io.InputUniByteArray;
import com.pt.io.InputUniFile;
import com.pt.io.OutputUni;

import multivalent.ParseException;
import multivalent.std.adaptor.pdf.COS;
import multivalent.std.adaptor.pdf.Cmd;
import multivalent.std.adaptor.pdf.CryptFilter;
import multivalent.std.adaptor.pdf.Dict;
import multivalent.std.adaptor.pdf.Fonts;
import multivalent.std.adaptor.pdf.IRef;
import multivalent.std.adaptor.pdf.Images;
import multivalent.std.adaptor.pdf.InputStreamComposite;
import multivalent.std.adaptor.pdf.PDFReader;
import multivalent.std.adaptor.pdf.PDFWriter;
import phelps.io.PrintStreams;
import phelps.lang.Integers;
import phelps.util.Arrayss;
import phelps.util.Version;

public class CompressAuriga {

	public CompressAuriga(File file) throws IOException, ParseException {
		this(((InputUni) (new InputUniFile(file))));
	}

	public CompressAuriga(byte abyte0[]) throws IOException, ParseException {
		this(((InputUni) (new InputUniByteArray(abyte0))));
	}

	public CompressAuriga(InputUni inputuni) throws IOException, ParseException {
		this(new PDFReader(inputuni));
	}

	public CompressAuriga(PDFReader pdfreader) {
		password_ = null;
		fold_ = false;
		fcompact0_ = false;
		fforce_ = finplace_ = false;
		fstruct_ = fwebcap_ = fpagepiece_ = falt_ = fcore14_ = fembed_ = foutline_ = true;
		fjpeg_ = fjpeg2000_ = fsubset_ = false;
		ftestable_ = false;
		fpre_ = fcontent_ = true;
		out_ = PrintStreams.DEVNULL;
		fverbose_ = fquiet_ = fmonitor_ = false;
	}

	public void setOld(boolean flag) {
		fold_ = flag;
	}

	public void setCompact(boolean flag) {
		fcompact0_ = flag;
	}

	public void setStruct(boolean flag) {
		fstruct_ = flag;
	}

	public void setSubset(boolean flag) {
		fsubset_ = flag;
	}

	public void setMax() {
		fold_ = ftestable_ = false;
		fcompact0_ = fjpeg_ = fsubset_ = true;
		fstruct_ = fwebcap_ = fpagepiece_ = falt_ = fcore14_ = fembed_ = false;
	}

	public void setOut(PrintStream printstream) {
		out_ = printstream == null ? PrintStreams.DEVNULL : printstream;
	}

	public void setTestable(boolean flag) {
		ftestable_ = flag;
	}

	public void setQuiet(boolean flag) {
		fquiet_ = flag;
	}

	public void setVerbose(boolean flag) {
		fverbose_ = flag;
	}

	public boolean setPassword(String s) throws IOException {
		return pdfr_.setPassword(s);
	}

	public java.util.List getWacky() {
		return wacky_;
	}

	public void writeFile(File file) throws IOException, ParseException {
		writeUni(OutputUni.getInstance(file, null));
	}

	public byte[] writeBytes() throws IOException, ParseException {
		OutputUni outputuni = OutputUni.getInstance(new byte[pdfr_.getObjCnt() * 100], null);
		writeUni(outputuni);
		return outputuni.toByteArray();
	}

	public void writeUni(OutputUni outputuni) throws IOException, ParseException {
		compress(new PDFWriter(outputuni, pdfr_));
	}

	public PDFReader writePipe() throws IOException, ParseException {
		return null;
	}

	private Object compress(PDFWriter pdfwriter) throws IOException, ParseException {
		long l = System.currentTimeMillis();
		PDFReader pdfreader = pdfr_;
		if (!pdfreader.isAuthorized())
			throw new ParseException("invalid password");
		Dict dict = (Dict) pdfreader.getTrailer().get("Compress");
		if (!fforce_ && dict != null) {
			String s = (String) dict.get("Filter");
			if (s == null && !fcompact0_ || "Compact".equals(s) && fcompact0_) {
				out_.println("Already compressed.  (Force recompression with -force.)");
				return null;
			}
		}
		long l1 = pdfreader.getRA().length();
		if (!fquiet_) {
			out_.print(pdfreader.getURI() + ", " + l1 + " bytes");
			if (pdfreader.getEncrypt().getStmF() != CryptFilter.IDENTITY)
				out_.print(", encrypted");
			out_.println();
			Dict dict1 = pdfreader.getInfo();
			if (dict1 != null)
				out_.println("PDF " + pdfreader.getVersion() + ", producer=" + dict1.get("Producer") + ", creator=" + dict1.get("Creator"));
		}
		if (fmonitor_)
			out_.print(pdfreader.getObjCnt() + " objects / " + pdfreader.getPageCnt() + " pages");
		compress2(pdfwriter);
		if (fmonitor_)
			out_.print("write ");
		Object obj = pdfwriter.writePDF();
		long l2 = pdfwriter.getOutputStream().getCount();
		pdfr_.close();
		pdfr_ = null;
		pdfwriter.close();
		long l3 = System.currentTimeMillis();
		out_.println("=> new length = " + l2 + ", saved " + ((l1 - l2) * 100L) / l1 + "%, elapsed time = " + (l3 - l) / 1000L + " sec");
		return obj;
	}

	private void compress2(PDFWriter pdfwriter) throws IOException, ParseException {
		wacky_ = new ArrayList(5);
		PDFReader pdfreader = pdfr_;
		Version version = pdfreader.getVersion();
		long l = pdfreader.getRA().length();
		pdfreader.fault();
		Dict dict = (Dict) pdfwriter.getTrailer().get("Compress");
		if (dict == null) {
			dict = new Dict(5);
			pdfwriter.getTrailer().put("Compress", dict);
			dict.put("LengthO", new Integer((int) l));
			dict.put("SpecO", version.toString());
		}
		fcompact_ = fcompact0_ && l > l / 4L + 714L;
		if (fcompact_)
			dict.put("Filter", "Compact");
		else
			dict.remove("Filter");
		if (fold_)
			pdfreader.setExact(true);
		else if (!fcompact_)
			pdfwriter.getVersion().setMin(new Version(1L, 5L));
		int i = 0;
		int j = 0;
		int k = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 1;
		for (int i3 = pdfreader.getObjCnt(); l2 < i3; l2++) {
			Object obj = pdfreader.getObject(l2);
			if (COS.OBJECT_DELETED == obj)
				k++;
			else if (COS.CLASS_DICTIONARY == obj.getClass()) {
				Dict dict2 = (Dict) obj;
				Object obj1 = pdfreader.getObject(dict2.get("Filter"));
				Class class1 = obj1 == null ? null : obj1.getClass();
				if (COS.CLASS_NAME == class1) {
					if ("ASCII85Decode".equals(obj1) || "ASCIIHexDecode".equals(obj1))
						j++;
					else if ("LZWDecode".equals(obj1))
						i++;
				} else if (COS.CLASS_ARRAY == class1) {
					Object aobj[] = (Object[]) obj1;
					int i4 = aobj.length;
					for (int j4 = 0; j4 < i4; j4++) {
						Object obj8 = aobj[j4];
						if ("ASCII85Decode".equals(obj8) || "ASCIIHexDecode".equals(obj8)) {
							j++;
							continue;
						}
						if ("LZWDecode".equals(obj8))
							i++;
					}

				}
				if (dict2.get("DATA") != null && (dict2.get("Length") instanceof IRef))
					k1++;
				Object obj5 = pdfreader.getObject(dict2.get("Type"));
				Object obj7 = pdfreader.getObject(dict2.get("Subtype"));
				if (("XObject".equals(obj5) || obj5 == null) && "Image".equals(obj7) && dict2.get("Alternates") != null)
					j1++;
			}
			pdfwriter.getObject(l2, true);
			if (COS.CLASS_DICTIONARY != obj.getClass())
				continue;
			Dict dict3 = (Dict) obj;
			if (dict3.get("PieceInfo") != null)
				i1++;
			Object obj2 = pdfreader.getObject(dict3.get("Filter"));
			Object obj3 = pdfwriter.getObject(dict3.get("Type"));
			Object obj6 = pdfwriter.getObject(dict3.get("Subtype"));
			if (("XObject".equals(obj3) || obj3 == null) && "Image".equals(obj6)) {
				byte abyte0[] = (byte[]) dict3.get("DATA");
				if (obj2 == null) {
					l1++;
					i2 += abyte0.length;
				}
				recodeImage(dict3, pdfwriter, pdfreader);
				if (obj2 == null)
					pdfwriter.deflateStream(dict3, l2);
				continue;
			}
			if ("Font".equals(obj3) && !fembed_) {
				Dict dict5 = (Dict) pdfwriter.getObject(dict3.get("FontDescriptor"));
				if (dict5 != null) {
					dict5.remove("FontFile");
					dict5.remove("FontFile2");
					dict5.remove("FontFile3");
				}
				continue;
			}
			if (!"Font".equals(obj3) || !"Type1".equals(obj6))
				continue;
			Dict dict6 = (Dict) pdfwriter.getObject(dict3.get("FontDescriptor"));
			Dict dict7 = dict6 == null ? null : (Dict) pdfwriter.getObject(dict6.get("FontFile"));
			if (dict7 == null)
				continue;
			j2++;
			Object obj9 = pdfwriter.getObject(dict7.get("Length"));
			if (obj9 instanceof Number)
				k2 += ((Number) obj9).intValue();
		}

		if (fmonitor_) {
			if (i > 0)
				out_.print(", " + i + " LZW");
			if (j > 0)
				out_.print(", " + j + " ASCII");
			if (k > 0)
				out_.print(", " + k + " deleted");
			if (i1 > 0)
				out_.print(", " + i1 + " pagepiece");
			if (j1 > 0)
				out_.print(", " + j1 + " image /Alt");
			if (k1 > 0)
				out_.print(", " + k1 + " /Length IRef");
			if (l1 > 0)
				out_.print(", " + l1 + " raw samples = " + i2 / 1024 + "K");
			if (j2 > 0)
				out_.print(", " + j2 + " embedded Type 1 = " + k2 / 1024 + "K");
		}
		Dict dict1 = pdfwriter.getCatalog();
		strip(dict1, pdfwriter);
		int j3 = 0;
		int k3 = 0;
		for (int l3 = pdfreader.getPageCnt(); k3 < l3; k3++) {
			IRef iref = pdfreader.getPageRef(k3 + 1);
			Object obj4 = pdfwriter.getObject(iref);
			if (COS.OBJECT_NULL == obj4 || obj4 == null)
				continue;
			Dict dict4 = (Dict) obj4;
			obj4 = dict4.get("Thumb");
			if (obj4 != null) {
				dict4.remove("Thumb");
				if (COS.CLASS_IREF == obj4.getClass())
					pdfwriter.setObject(((IRef) obj4).id, COS.OBJECT_DELETED);
				j3++;
			}
			if (!ftestable_)
				stripLZW(dict4, dict4.get("Contents"), pdfreader, pdfwriter);
		}

		if (fmonitor_ && j3 > 0)
			out_.print(", " + j3 + " thumb");
		axeCore14(pdfwriter);
		recodeFonts(pdfwriter);
		subset(pdfreader, pdfwriter);
		if (fmonitor_)
			out_.print(", liftPageTree");
		pdfwriter.liftPageTree();
		inline(pdfwriter);
		unique(pdfwriter);
		k3 = pdfwriter.refcntRemove();
		if (k3 > 0 && fmonitor_)
			out_.print(", ref cnt " + k3);
		boolean flag = !fold_ && !fcompact_ ? pdfwriter.makeObjectStreams(0, pdfwriter.getObjCnt()) : false;
		if (fmonitor_)
			out_.println();
		if (fverbose_) {
			if (pdfreader.getLinearized() > 0)
				out_.println("lost Linearization (aka Fast Web View)");
			if (pdfreader.isRepaired())
				out_.println("repaired errors");
			if (i > 0)
				out_.println("converted LZW to Flate");
			if (j > 0)
				out_.println("stripped off verbose ASCII filters");
			if (k > 0)
				out_.println("nulled out deleted objects");
			if (j3 > 0)
				out_.println("removed thumbnails (Acrobat can generate on the fly)");
			if (i1 > 0 && !fpagepiece_)
				out_.println("removed /PieceInfo");
			if (j1 > 0 && !falt_)
				out_.println("remove alternate images");
			if (!fold_) {
				out_.println("cleaned and modernized");
				if (pdfwriter.getVersion().compareTo(version) > 0 && pdfwriter.getVersion().compareTo(1L, 5L) >= 0) {
					out_.println("\tcross references as stream");
					if (flag)
						out_.println("\tadditional compression via object streams");
					out_.println("\tnow REQUIRES Multivalent or Acrobat 6 to read (use -old for older PDF)");
				}
			}
		}
		if (!fquiet_) {
			if (fcompact_)
				out_.println("Compact PDF format -- requires Multivalent to read");
			StringBuffer stringbuffer = new StringBuffer();
			if (fold_ && pdfwriter.getVersion().compareTo(1L, 5L) < 0)
				stringbuffer.append(" [omit -old]");
			if (!fcompact0_)
				stringbuffer.append(" -compact");
			if (dict1.get("StructTreeRoot") != null)
				stringbuffer.append(" -nostruct");
			if (l1 > 0 && !fjpeg_)
				stringbuffer.append(" -jpeg");
			if (dict1.get("SpiderInfo") != null)
				stringbuffer.append(" -nowebcap");
			if (i1 > 0 && fpagepiece_)
				stringbuffer.append(" -nopagepiece");
			if (j1 > 0 && falt_)
				stringbuffer.append(" -noalt");
			if (stringbuffer.length() > 0) {
				out_.println("additional compression may be possible with:");
				out_.println("\t" + stringbuffer);
			}
		}
	}

	private void strip(Dict dict, PDFWriter pdfwriter) {
		if (!fstruct_) {
			dict.remove("StructTreeRoot");
			dict.remove("MarkInfo");
		}
		if (!fwebcap_)
			dict.remove("SpiderInfo");
		if (!fpagepiece_)
			dict.remove("PieceInfo");
		if (!foutline_)
			dict.remove("Outlines");
		if (!fpagepiece_) {
			int i = 1;
			for (int j = pdfwriter.getObjCnt(); i < j; i++) {
				Object obj = pdfwriter.getCache(i);
				if (COS.CLASS_DICTIONARY == obj.getClass()) {
					Dict dict1 = (Dict) obj;
					dict1.remove("PieceInfo");
				}
			}

		}
	}

	private void stripLZW(Dict dict, Object obj, PDFReader pdfreader, PDFWriter pdfwriter) throws IOException {
		if (ftestable_ || !fcontent_)
			return;
		Object obj1 = pdfwriter.getObject(obj);
		Object aobj[] = obj1 != null ? COS.CLASS_DICTIONARY != obj1.getClass() ? (Object[]) obj1 : (new Object[] { obj }) : new Object[0];
		int i = 0;
		for (int j = aobj.length; i < j; i++) {
			Dict dict3 = (Dict) pdfwriter.getObject(aobj[i]);
			try {
				Cmd acmd[] = pdfreader.readCommandArray(aobj[i]);
				dict3.put("DATA", pdfwriter.writeCommandArray(acmd, false));
			} catch (IOException ioexception) {
			}
		}

		Dict dict1 = (Dict) pdfreader.getObject(dict.get("Resources"));
		Dict dict2 = dict1 == null ? null : (Dict) pdfreader.getObject(dict1.get("XObject"));
		if (dict2 != null) {
			Iterator iterator = dict2.values().iterator();
			do {
				if (!iterator.hasNext())
					break;
				IRef iref = (IRef) iterator.next();
				Object obj2 = pdfreader.getObject(iref);
				if (COS.CLASS_DICTIONARY == obj2.getClass()) {
					Dict dict4 = (Dict) obj2;
					if ("Form".equals("Subtype"))
						stripLZW(dict4, iref, pdfreader, pdfwriter);
				}
			} while (true);
		}
	}

	private int unique(PDFWriter pdfwriter) {
		long l = System.currentTimeMillis();
		int i = pdfwriter.getObjCnt();
		Object aobj[] = pdfwriter.getObjects();
		Class aclass[] = new Class[i];
		for (int k = 1; k < i; k++)
			aclass[k] = aobj[k].getClass();

		long al[] = new long[i];
		int ai[] = new int[i];
		int i1 = 0;
		int j1 = 0;
		do {
			int k1 = 0;
			Object aobj1[] = pdfwriter.getObjects();
			int j = pdfwriter.getObjCnt();
			if (!$assertionsDisabled && al[0] != 0L)
				throw new AssertionError();
			for (int i2 = 1; i2 < j; i2++)
				al[i2] = ((long) sign(aobj1[i2]) << 32) + (long) i2;

			Arrays.sort(al, 1, j);
			for (int j2 = 1; j2 < j; j2++)
				ai[(int) (al[j2] & 2147483647L)] = j2;

			int ai1[] = new int[j];
			label0: for (int k2 = 1; k2 < j; k2++) {
				ai1[k2] = k2;
				Object obj = aobj1[k2];
				Class class1 = aclass[k2];
				if (COS.CLASS_DICTIONARY == class1 && "Page".equals(((Dict) obj).get("Type")))
					continue;
				int i3 = ai[k2];
				long l3 = al[i3] & -4294967296L;
				i3--;
				do {
					if (i3 <= 0 || (al[i3] & l3) != l3)
						continue label0;
					int j3 = (int) (al[i3] & 2147483647L);
					if (class1 == aclass[j3] && pdfwriter.objEquals(obj, aobj1[j3])) {
						int k3 = ai1[j3];
						ai1[k2] = k3;
						aobj1[k2] = aobj1[k3];
						k1++;
						continue label0;
					}
					i3--;
				} while (true);
			}

			if (k1 > 0) {
				int ai2[] = pdfwriter.renumberRemove(ai1);
				for (int l2 = 1; l2 < j; l2++)
					aclass[l2 - ai2[l2]] = aclass[l2];

				if (fmonitor_)
					out_.print(j1 != 0 ? " + " + k1 : ", " + k1 + " dups");
				i1 += k1;
				j1++;
			} else {
				long l1 = System.currentTimeMillis() - l;
				return i1;
			}
		} while (true);
	}

	private static int sign(Object obj) {
		int i = 0;
		Class class1 = obj.getClass();
		if (COS.OBJECT_NULL == obj)
			i = 0;
		else if (COS.CLASS_NAME == class1)
			i = ((String) obj).hashCode();
		else if (COS.CLASS_STRING == class1)
			i = ((StringBuffer) obj).length() * 11;
		else if (COS.CLASS_DATA == class1)
			i = ((byte[]) obj).length << 8;
		else if (COS.CLASS_BOOLEAN == class1)
			i = ((Boolean) obj).booleanValue() ? 11 : 7;
		else if (obj instanceof Number)
			i = ((Number) obj).intValue();
		else if (COS.CLASS_IREF == class1)
			i = ((IRef) obj).id * 131;
		else if (COS.CLASS_DICTIONARY == class1) {
			Dict dict = (Dict) obj;
			i += dict.size() * 13;
			for (Iterator iterator = dict.entrySet().iterator(); iterator.hasNext();) {
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				i += entry.getKey().hashCode();
				i += sign(entry.getValue());
			}

			i *= dict.size();
		} else if (COS.CLASS_ARRAY == class1) {
			Object aobj[] = (Object[]) obj;
			i += aobj.length * 7;
			int j = 0;
			for (int k = aobj.length; j < k; j++)
				i += sign(aobj[j]) * (j + 1);

		}
		return i;
	}

	private int inline(PDFWriter pdfwriter) {
		int i = 0;
		int j = pdfwriter.getObjCnt();
		Object aobj[] = pdfwriter.getObjects();
		int ai[] = pdfwriter.refcnt();
		if (fmonitor_)
			out_.print(", inline");
		label0: for (int k = 1; k < j; k++) {
			Class class1 = aobj[k].getClass();
			if (ai[k] == 0)
				continue;
			if (COS.CLASS_ARRAY == class1) {
				Object aobj1[] = (Object[]) aobj[k];
				int k1 = 0;
				int l1 = aobj1.length;
				do {
					if (k1 >= l1)
						continue label0;
					Object obj;
					if ((obj = inlineObj(aobj1[k1], ai, pdfwriter)) != null)
						aobj1[k1] = obj;
					k1++;
				} while (true);
			}
			if (COS.CLASS_DICTIONARY != class1)
				continue;
			Dict dict1 = (Dict) aobj[k];
			Iterator iterator = dict1.entrySet().iterator();
			do {
				Object obj1;
				java.util.Map.Entry entry;
				do {
					if (!iterator.hasNext())
						continue label0;
					entry = (java.util.Map.Entry) iterator.next();
				} while ((obj1 = inlineObj(entry.getValue(), ai, pdfwriter)) == null);
				entry.setValue(obj1);
			} while (true);
		}

		for (int l = 1; l < j; l++) {
			if (COS.CLASS_DICTIONARY != aobj[l].getClass() || ai[l] == 0)
				continue;
			Dict dict = (Dict) aobj[l];
			Object obj2;
			if (!"Page".equals(dict.get("Type")) || (obj2 = dict.get("Contents")) == null)
				continue;

			if (COS.CLASS_IREF == obj2.getClass()) {
				int j1 = ((IRef) obj2).id;
				obj2 = aobj[j1];
			}
			if (COS.CLASS_ARRAY != obj2.getClass())
				continue;
			Object aobj2[] = (Object[]) obj2;
			Object aobj3[] = new Object[aobj2.length];
			int i2 = 0;
			byte abyte0[] = null;
			int j2 = -1;
			int k2 = 0;
			int l2 = 0;
			for (int i3 = aobj2.length; l2 < i3; l2++) {
				IRef iref = (IRef) aobj2[l2];
				int j3 = iref.id;
				aobj3[i2++] = iref;
				byte abyte1[] = (byte[]) ((Dict) aobj[j3]).get("DATA");
				if (ai[j3] == 1 || j2 != -1 && abyte1.length < PDFWriter.PDFOBJREF_OVERHEAD * 2) {
					if (abyte0 == null) {
						abyte0 = abyte1;
						j2 = j3;
						k2 = 1;
					} else {
						int k3 = abyte0.length;
						abyte0 = Arrayss.resize(abyte0, k3 + abyte1.length + 1);
						abyte0[k3] = 32;
						System.arraycopy(abyte1, 0, abyte0, k3 + 1, abyte1.length);
						i2--;
						k2++;
					}
					continue;
				}
				if (k2 > 1)
					((Dict) aobj[j2]).put("DATA", abyte0);
				abyte0 = null;
				j2 = -1;
				k2 = 0;
			}

			if (k2 > 1)
				((Dict) aobj[j2]).put("DATA", abyte0);
			if (i2 < aobj2.length) {
				i += aobj2.length - i2;
				if (i2 == 0) {
					dict.remove("Contents");
					continue;
				}
				if (i2 == 1)
					dict.put("Contents", aobj3[0]);
				else
					dict.put("Contents", ((Object) (Arrayss.resize(aobj3, i2))));
			} else {
				dict.put("Contents", ((Object) (aobj2)));
			}
		}

		if (!$assertionsDisabled && pdfwriter.getObjGen(0) != 65535)
			throw new AssertionError(pdfwriter.getObjGen(0));
		int i1 = pdfwriter.refcntRemove();
		if (!$assertionsDisabled && pdfwriter.getObjGen(0) != 65535)
			throw new AssertionError();
		if (fmonitor_) {
			if (fmonitor_)
				out_.print(" " + -i1);
			if (i > 0)
				out_.print(", " + i + " concat");
		}
		return i1;
	}

	private Object inlineObj(Object obj, int ai[], PDFWriter pdfwriter) {
		if (!(obj instanceof IRef))
			return null;
		int i = ((IRef) obj).id;
		obj = pdfwriter.getCache(i);
		Class class1 = obj.getClass();
		if (COS.OBJECT_NULL == obj || COS.CLASS_INTEGER == class1 || COS.CLASS_BOOLEAN == class1
				|| COS.CLASS_NAME == class1 && ((String) obj).length() < PDFWriter.PDFOBJREF_OVERHEAD
				|| COS.CLASS_STRING == class1 && ((StringBuffer) obj).length() + 2 < PDFWriter.PDFOBJREF_OVERHEAD
				|| ai[i] == 1 && (COS.CLASS_NAME == class1 || COS.CLASS_STRING == class1 || COS.CLASS_REAL == class1)) {
			ai[i]--;
			return obj;
		} else {
			return null;
		}
	}

	private void axeCore14(PDFWriter pdfwriter) throws IOException {
		if (fcore14_ || ftestable_)
			return;
		if (fmonitor_)
			out_.print(", axeCore14");
		for (int i = 1; i < pdfwriter.getObjCnt(); i++) {
			Object obj = pdfwriter.getObject(i, false);
			if (COS.CLASS_DICTIONARY != obj.getClass())
				continue;
			Dict dict = (Dict) obj;
			Object obj1 = pdfwriter.getObject(dict.get("Type"));

			if (!"Font".equals(obj1) || !"WinAnsiEncoding".equals(pdfwriter.getObject(dict.get("Encoding"))))
				continue;
			String s = (String) pdfwriter.getObject(dict.get("BaseFont"));
			String s1 = Fonts.isSubset(s) ? s.substring("SIXCAP+".length()) : s;
			if (!fcore14_ && s1 != null && Arrays.binarySearch(CORE14, s1) >= 0 && dict.get("FontDescriptor") != null) {
				dict.remove("FontDescriptor");
				dict.put("BaseFont", s1);
			}
		}

	}

	private void recodeFonts(PDFWriter pdfwriter) throws IOException {
	}

	private void subset(PDFReader pdfreader, PDFWriter pdfwriter) throws IOException {
		if (!fsubset_ || ftestable_)
			return;
		if (fmonitor_)
			out_.print(", subset");
		int i = pdfwriter.getObjCnt();
		Dict adict[] = new Dict[i];
		int ai[] = new int[i];
		for (int j = 1; j < i; j++) {
			Object obj = pdfwriter.getObject(j, false);
			if (COS.CLASS_DICTIONARY != obj.getClass())
				continue;
			Dict dict = (Dict) obj;
			Object obj1 = pdfwriter.getObject(dict.get("Type"));
			Object obj2 = pdfwriter.getObject(dict.get("Subtype"));
			String s = (String) pdfwriter.getObject(dict.get("BaseFont"));
			if (!"Font".equals(obj1) || Fonts.isSubset(s))
				continue;
			Dict dict4 = (Dict) pdfwriter.getObject(dict.get("FontDescriptor"));
			if (dict4 == null)
				continue;
			Object obj4;
			if ((obj4 = dict4.get("FontFile")) != null && ("Type1".equals(obj2) || "MMType1".equals(obj2))) {
				adict[j] = dict;
				ai[j] = ((IRef) obj4).id;
				continue;
			}
			if ((obj4 = dict4.get("FontFile2")) != null) {
				ai[j] = ((IRef) obj4).id;
				continue;
			}
			if ((obj4 = dict4.get("FontFile3")) != null)
				ai[j] = ((IRef) obj4).id;
		}

		int k = 0;
		int ai1[][] = new int[i][];
		int l = 0;
		for (int i1 = adict.length; l < i1; l++)
			if (adict[l] != null) {
				ai1[ai[l]] = new int[256];
				k++;
			}

		l = 0;
		for (int j1 = pdfreader.getPageCnt(); l < j1; l++) {
			Dict dict1 = pdfreader.getPage(l + 1);
			Dict dict3 = (Dict) pdfwriter.getObject(dict1.get("Resources"));
			if (dict3 == null)
				continue;
			subsetCensus(dict3.get("Font"), dict1.get("Contents"), pdfreader, pdfwriter, ai1, ai);
			Object obj3 = dict3.get("XObject");
			if (obj3 == null)
				continue;
			Dict dict5 = (Dict) pdfwriter.getObject(obj3);
			Dict dict6 = (Dict) pdfwriter.getObject(dict5.get("Resources"));
			if (dict6 != null)
				subsetCensus(dict6.get("Font"), obj3, pdfreader, pdfwriter, ai1, ai);
		}

		l = 1;
		for (int k1 = adict.length; l < k1; l++) {
			Dict dict2 = adict[l];
			int ai2[] = ai1[ai[l]];
			if (ai2 == null)
				continue;
			ai1[ai[l]] = null;
			boolean aflag[] = new boolean[256];
			int l1 = 1;
			for (int i2 = 1; i2 < 256; i2++)
				if (ai2[i2] > 0) {
					aflag[i2] = true;
					l1++;
				}

			Dict dict7 = (Dict) pdfwriter.getObject(dict2.get("FontDescriptor"));
			IRef iref = (IRef) dict7.get("FontFile");
			Dict dict8 = (Dict) pdfwriter.getObject(iref);
			try {
				NFontType1 nfonttype1 = new NFontType1(null, pdfwriter.getStreamData(dict8));
				out_.print(", " + dict2.get("BaseFont") + " " + nfonttype1.getNumGlyphs() + " subset " + l1);
				if (fmonitor_) {
					out_.print(": ");
					for (int j2 = 32; j2 < 128; j2++)
						if (aflag[j2])
							out_.print((char) j2);

					out_.println();
				}
				String s1 = nfonttype1.getName();
				if (s1 != null && !Fonts.isSubset(s1)) {
					StringBuffer stringbuffer = new StringBuffer(7 + s1.length());
					Random random = new Random();
					for (int l2 = 0; l2 < 6; l2++)
						stringbuffer.append((char) (65 + random.nextInt(26)));

					stringbuffer.append('+');
					stringbuffer.append(s1);
					s1 = stringbuffer.toString();
				}
				nfonttype1 = nfonttype1.deriveFont(s1, aflag);
				byte abyte0[] = nfonttype1.toPFB();
				abyte0 = Arrayss.resize(abyte0, abyte0.length - NFontType1.PFB_00_LENGTH);
				dict8.put("DATA", abyte0);
				int k2 = NFontType1.getClen(abyte0);
				dict8.put("Length1", Integers.getInteger(k2));
				dict8.put("Length2", Integers.getInteger(abyte0.length - k2));
				dict8.put("Length3", Integers.ZERO);
				dict7.put("FontName", s1);
				dict2.put("BaseFont", s1);
			} catch (FontFormatException fontformatexception) {
			} catch (IOException ioexception) {
			}
		}

	}

	private void subsetCensus(Object obj, Object obj1, PDFReader pdfreader, PDFWriter pdfwriter, int ai[][], int ai1[]) throws IOException {
		if (obj == null || obj1 == null)
			return;
		Dict dict = (Dict) pdfwriter.getObject(obj);
		int ai2[] = null;
		int ai3[][] = new int[100][];
		int i = 0;
		Iterator iterator = dict.values().iterator();
		do {
			if (!iterator.hasNext())
				break;
			IRef iref = (IRef) iterator.next();
			int j = ai1[iref.id];
			if (j == 0)
				continue;
			multivalent.std.adaptor.pdf.InputStreamComposite inputstreamcomposite = pdfreader.getInputStream(obj1, true);
			label0: do {
				Cmd cmd;
				String s;
				do {
					do {
						if ((cmd = pdfreader.readCommand(inputstreamcomposite)) == null)
							break label0;
						s = cmd.op;
						if ("Tf" == s) {
							IRef iref1 = (IRef) dict.get(cmd.ops[0]);
							int k = ai1[iref1.id];
							if (ai[k] != null)
								ai2 = ai[k];
						} else if ("q" == s) {
							ai3[i++] = ai2;
						} else {
							if ("Q" != s)
								continue;
							if (--i >= 0)
								ai2 = ai3[i];
						}
						continue label0;
					} while (ai2 == null);
					if ("Tj" != s)
						continue;
					StringBuffer stringbuffer = (StringBuffer) cmd.ops[0];
					int l = 0;
					int j1 = stringbuffer.length();
					while (l < j1) {
						ai2[stringbuffer.charAt(l)]++;
						l++;
					}
					continue label0;
				} while ("TJ" != s);
				Object aobj[] = (Object[]) cmd.ops[0];
				int i1 = 0;
				int k1 = aobj.length;
				while (i1 < k1) {
					if (COS.CLASS_STRING == aobj[i1].getClass()) {
						StringBuffer stringbuffer1 = (StringBuffer) aobj[i1];
						int l1 = 0;
						for (int i2 = stringbuffer1.length(); l1 < i2; l1++)
							ai2[stringbuffer1.charAt(l1)]++;

					}
					i1++;
				}
			} while (true);
			break;
		} while (true);
	}

	private void recodeImage(Dict dict, PDFWriter pdfwriter, PDFReader pdfreader) throws IOException {
		byte abyte0[] = (byte[]) dict.get("DATA");
		String s = (String) pdfwriter.getObject(dict.get("Filter"));
		int i = ((Number) pdfwriter.getObject(dict.get("Width"))).intValue();
		int j = ((Number) pdfwriter.getObject(dict.get("Height"))).intValue();
		int k = pdfwriter.getObject(dict.get("ImageMask")) != Boolean.TRUE ? ((Number) pdfwriter.getObject(dict.get("BitsPerComponent"))).intValue() : 1;
		Object obj;
		if (!falt_ && (obj = pdfreader.getObject(dict.get("Alternates"))) != null) {
			Object aobj[] = (Object[]) obj;
			ArrayList arraylist = new ArrayList(aobj.length);
			Object aobj1[] = aobj;
			int l = aobj1.length;
			for (int i1 = 0; i1 < l; i1++) {
				Object obj1 = aobj1[i1];
				Dict dict1 = (Dict) pdfreader.getObject(obj1);
				if (dict1.get("OC") != null)
					arraylist.add(obj1);
			}

			if (arraylist.size() == 0)
				dict.remove("Alternates");
			else
				dict.put("Alternates", ((Object) (arraylist.toArray())));
		}
		if ("DCTDecode".equals(s))
			recodeDCTAsRaw(dict, i, j, abyte0);
		else if (!"CCITTFaxDecode".equals(s) && s == null) {
			boolean flag = false;
			if (fjpeg_)
				flag = recodeRawAsDCT(dict, i, j, k, abyte0, pdfreader);
			if (!flag && fpre_)
				flag = addPredictor(dict, i, j, k, abyte0, pdfreader);
		}
	}

	private boolean recodeDCTAsRaw(Dict dict, int i, int j, byte abyte0[]) throws IOException {
		if (ftestable_)
			return false;
		int k = abyte0.length;
		if (i * j > k)
			return false;
		try {
			ImageReader imagereader = (ImageReader) ImageIO.getImageReadersByFormatName("JPEG").next();
			ImageIO.setUseCache(false);
			ImageInputStream imageinputstream = ImageIO.createImageInputStream(new ByteArrayInputStream(abyte0));
			imagereader.setInput(imageinputstream, true);
			BufferedImage bufferedimage = imagereader.read(0);
			imagereader.dispose();
			imageinputstream.close();
			int l = bufferedimage.getColorModel().getNumComponents();
			int i1 = l * i * j;
			if (i1 / 2 < k && (l == 1 || l == 3)) {
				byte abyte1[] = new byte[i1];
				int j1 = 0;
				int k1 = 0;
				for (; j1 < j; j1++) {
					for (int l1 = 0; l1 < i; l1++) {
						int i2 = bufferedimage.getRGB(l1, j1);
						if (l == 3) {
							abyte1[k1++] = (byte) (i2 >> 16);
							abyte1[k1++] = (byte) (i2 >> 8);
						}
						abyte1[k1++] = (byte) i2;
					}

				}

				dict.put("DATA", abyte1);
				dict.remove("Filter");
			}
		} catch (Exception exception) {
		}
		return true;
	}

	private boolean recodeRawAsDCT(Dict paramDict, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte, PDFReader paramPDFReader)
			throws IOException {
		if (this.ftestable_)
			return false;
		if ((paramInt3 != 8) || (paramInt1 < 20) || (paramInt2 < 20) || (paramArrayOfByte.length < 5420))
			return false;
		byte[] arrayOfByte1 = PDFWriter.maybeDeflateData(paramArrayOfByte);
		int i = arrayOfByte1.length;
		arrayOfByte1 = null;
		if (i < 5420)
			return false;
		InputStreamComposite localInputStreamComposite = paramPDFReader.getInputStream(paramDict);
		BufferedImage localBufferedImage = Images.createImage(paramDict, localInputStreamComposite, new AffineTransform(), Color.WHITE, paramPDFReader);
		localInputStreamComposite.close();
		if (localBufferedImage == null)
			return false;
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramInt1 * paramInt2);
		int j = 0;
		String str1 = j != 0 ? "JPEG2000" : "JPEG";
		ImageWriter localImageWriter = (ImageWriter) ImageIO.getImageWritersByFormatName(str1).next();
		ImageIO.setUseCache(false);
		ImageOutputStream localImageOutputStream = ImageIO.createImageOutputStream(localByteArrayOutputStream);
		localImageWriter.setOutput(localImageOutputStream);
		IIOImage localIIOImage = new IIOImage(localBufferedImage, null, null);
		try {
			localImageWriter.write((IIOMetadata) null, localIIOImage, null);
		} catch (IllegalArgumentException localIllegalArgumentException) {
			boolean bool = false;
			return bool;
		} finally {
			localImageWriter.dispose();
			localImageOutputStream.close();
		}
		byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
		int k = i - arrayOfByte2.length;
		if (k > 5120) {
			if (this.fmonitor_) {
				this.out_.print(" jpeg" + k);
				if (k > 102400)
					this.out_.print("/" + paramInt1 + "x" + paramInt2);
			}
			paramDict.put("Filter", "JPEG2000".equals(str1) ? "JPXDecode" : "DCTDecode");
			paramDict.put("DATA", arrayOfByte2);
			String str2 = (10 == localBufferedImage.getType()) || (11 == localBufferedImage.getType()) ? "DeviceGray" : "DeviceRGB";
			paramDict.put("ColorSpace", str2);
		}
		return true;
	}

	private boolean addPredictor(Dict dict, int i, int j, int k, byte abyte0[], PDFReader pdfreader) throws IOException {
		if (!$assertionsDisabled && (dict == null || i < 1 || j < 1 || abyte0 == null || abyte0.length < 1))
			throw new AssertionError();
		if (k != 8 || abyte0.length < PREDICT_OVERHEAD + i)
			return false;
		Object obj = pdfreader.getObject(dict.get("ColorSpace"));
		ColorSpace colorspace = pdfreader.getColorSpace(obj, null, null);
		int l = colorspace.getNumComponents();
		if (COS.CLASS_ARRAY == obj.getClass() && "Indexed".equals(((Object[]) obj)[0]))
			l = 1;
		int i1 = (l * k + 7) / 8;
		int j1 = i1;
		int k1 = (i * j * l * k) / 8;
		if (!$assertionsDisabled && abyte0.length < k1)
			throw new AssertionError(abyte0.length + " != " + i + " * " + j + " * " + l + " * " + k + " = " + k1);
		if (abyte0.length - k1 > 1) {
			String s = "extra bytes " + i + "x" + j + "*" + l + "=" + k1 + " < " + abyte0.length;
			wacky_.add(s);
			if (fmonitor_)
				out_.print(" / " + s);
		}
		int l1 = i * i1;
		byte abyte1[] = new byte[(l1 + 1) * j];
		int i2 = l1 + j1;
		byte abyte2[] = new byte[i2];
		byte abyte3[] = new byte[i2];
		byte abyte4[] = new byte[i2];
		byte abyte5[] = new byte[i2];
		byte abyte6[] = new byte[i2];
		int j2 = 0;
		abyte1[j2++] = 0;
		System.arraycopy(abyte0, 0, abyte1, j2, l1);
		j2 += l1;
		int k2 = 1;
		int l2 = l1;
		for (int i3 = 0 - j1; k2 < j; i3 += l1) {
			int k3 = 0;
			int l3 = 0;
			int i4 = 0;
			int j4 = 0;
			int k4 = 0;
			System.arraycopy(abyte0, l2, abyte2, j1, l1);
			for (int l4 = i2 - 1; l4 >= j1; l4--)
				k3 += abyte2[l4] & 255;

			int i5 = k3;
			byte byte0 = 0;
			byte abyte8[] = abyte2;
			System.arraycopy(abyte0, l2, abyte3, j1, l1);
			int k5 = i2 - 1;
			for (int k6 = j1 + i1; k5 >= k6; k5--) {
				abyte3[k5] -= abyte3[k5 - i1] & 255;
				l3 += abyte3[k5] & 255;
			}

			if (l3 < i5) {
				i5 = l3;
				abyte8 = abyte3;
				byte0 = 1;
			}
			System.arraycopy(abyte0, l2, abyte4, j1, l1);
			for (int l5 = i2 - 1; l5 >= j1; l5--) {
				abyte4[l5] -= abyte0[i3 + l5] & 255;
				i4 += abyte4[l5] & 255;
			}

			if (i4 < i5) {
				i5 = i4;
				abyte8 = abyte4;
				byte0 = 2;
			}
			System.arraycopy(abyte0, l2, abyte5, j1, l1);
			for (int i6 = i2 - 1; i6 >= j1; i6--) {
				abyte5[i6] -= ((abyte5[i6 - i1] & 255) + (abyte0[i3 + i6] & 255)) / 2;
				j4 += abyte5[i6] & 255;
			}

			if (j4 < i5) {
				i5 = j4;
				abyte8 = abyte5;
				byte0 = 3;
			}
			System.arraycopy(abyte0, l2, abyte6, j1, l1);
			for (int j6 = i2 - 1; j6 >= j1; j6--) {
				int l6 = abyte6[j6 - i1] & 255;
				int i7 = abyte0[i3 + j6] & 255;
				int j7 = j6 < j1 + i1 ? 0 : abyte0[(i3 + j6) - i1] & 255;
				int k7 = (l6 + i7) - j7;
				int l7 = Math.abs(k7 - l6);
				int i8 = Math.abs(k7 - i7);
				int j8 = Math.abs(k7 - j7);
				int k8 = l7 > i8 || l7 > j8 ? i8 > j8 ? j7 : i7 : l6;
				abyte6[j6] -= (byte) k8;
				k4 += abyte6[j6] & 255;
			}

			if (k4 < i5) {

				abyte8 = abyte6;
				byte0 = 4;
			}
			if (!$assertionsDisabled && (k3 < 0 || l3 < 0 || i4 < 0 || j4 < 0 || k4 < 0))
				throw new AssertionError();
			abyte1[j2++] = byte0;
			System.arraycopy(abyte8, j1, abyte1, j2, l1);
			j2 += l1;
			k2++;
			l2 += l1;
		}

		if (!$assertionsDisabled && j2 != abyte1.length)
			throw new AssertionError(j2 + " != " + abyte1.length);
		byte abyte7[] = PDFWriter.maybeDeflateData(abyte1);
		l2 = abyte7.length;
		int j3 = PDFWriter.maybeDeflateData(abyte0).length;
		boolean flag = abyte1 != abyte7 && l2 + PREDICT_OVERHEAD + 10 + (fcompact_ ? 2048 : 0) < j3;
		if (flag) {
			if (fmonitor_)
				out_.print(" pre" + (j3 - l2));
			dict.put("Filter", "FlateDecode");
			dict.put("DATA", abyte7);
			Dict dict1 = new Dict(5);
			dict1.put("Predictor", Integers.getInteger(15));
			if (l != 1)
				dict1.put("Colors", Integers.getInteger(l));
			dict1.put("Columns", Integers.getInteger(i));
			if (k != 8)
				dict1.put("BitsPerComponent", Integers.getInteger(8));
			dict.put("DecodeParms", dict1);
		}
		return flag;
	}

	private int commandLine(String as[]) {
		setOut(System.out);
		int i = 0;
		int j;
		String s;
		for (j = as.length; i < j && (s = as[i]).startsWith("-"); i++) {
			s = s.toLowerCase();
			if (s.startsWith("--"))
				s = s.substring(1);
			if (System.getSecurityManager() != null)
				s = "-compact";
			if (s.startsWith("-force")) {
				fforce_ = true;
				continue;
			}
			if (s.startsWith("-old") || s.startsWith("-compat")) {
				fold_ = true;
				fcompact0_ = false;
				continue;
			}
			if (s.startsWith("-compact") || s.startsWith("-bulk")) {
				fcompact0_ = true;
				fold_ = false;
				continue;
			}
			if (s.startsWith("-nostruct")) {
				fstruct_ = false;
				continue;
			}
			if (s.startsWith("-nowebcap")) {
				fwebcap_ = false;
				continue;
			}
			if (s.startsWith("-nopagep")) {
				fpagepiece_ = false;
				continue;
			}
			if (s.equals("-jpeg") || s.equals("-jpg")) {
				fjpeg_ = true;
				continue;
			}
			if (s.startsWith("-jpeg2") || s.startsWith("-jpg2")) {
				fjpeg2000_ = true;
				continue;
			}
			if (s.startsWith("-noalt")) {
				falt_ = false;
				continue;
			}
			if (s.startsWith("-nocore")) {
				fcore14_ = false;
				continue;
			}
			if (s.startsWith("-subset")) {
				setSubset(true);
				continue;
			}
			if (s.startsWith("-noembed")) {
				fembed_ = false;
				continue;
			}
			if (s.startsWith("-noout") || s.startsWith("-nobook")) {
				foutline_ = false;
				continue;
			}
			if (s.equals("-max") || s.equals("-all")) {
				setMax();
				continue;
			}
			if (s.equals("-web")) {
				fpagepiece_ = falt_ = fcore14_ = false;
				fjpeg_ = true;
				continue;
			}
			if (s.equals("-password")) {
				password_ = as[++i];
				continue;
			}
			if (s.startsWith("-inplace")) {
				finplace_ = true;
				continue;
			}
			if (s.startsWith("-test")) {
				ftestable_ = true;
				continue;
			}
			if (s.equals("-nopre")) {
				fpre_ = false;
				continue;
			}
			if (s.equals("-nocs")) {
				fcontent_ = false;
				continue;
			}
			if (s.startsWith("-verb")) {
				fverbose_ = true;
				fquiet_ = false;
				continue;
			}
			if (s.startsWith("-mon")) {
				fmonitor_ = fverbose_ = true;
				fquiet_ = false;
				continue;
			}
			if (s.startsWith("-q")) {
				fquiet_ = true;
				fmonitor_ = fverbose_ = false;
				continue;
			}
			if (s.startsWith("-v")) {
				System.out.println("2.4 of $Date: 2005/07/26 21:13:18 $");
				// System.exit(0);
				continue;
			}
			if (s.startsWith("-h")) {
				System.out.println(
						"java [-Xmx128m] tool.pdf.Compress [<options>] <PDF-file-or-directory...>\n\t[-old] [-compact] [-force] [-inplace] [-max]\n\t(data:) [-nopagepiece] [-nostruct] [-nowebcap] [-nooutline]\n\t(fonts:) [-nocore] [-noembed] [-subset]\n\t(images:) [-jpeg]\n\t[-password <password>] [-verbose] [-quiet]");
				// System.exit(0);
			} else {
				System.err.println("Unknown option: " + s);
				System.err.println(
						"java [-Xmx128m] tool.pdf.Compress [<options>] <PDF-file-or-directory...>\n\t[-old] [-compact] [-force] [-inplace] [-max]\n\t(data:) [-nopagepiece] [-nostruct] [-nowebcap] [-nooutline]\n\t(fonts:) [-nocore] [-noembed] [-subset]\n\t(images:) [-jpeg]\n\t[-password <password>] [-verbose] [-quiet]");
				// System.exit(1);
			}
		}

		if (i == j) {
			System.err.println(
					"java [-Xmx128m] tool.pdf.Compress [<options>] <PDF-file-or-directory...>\n\t[-old] [-compact] [-force] [-inplace] [-max]\n\t(data:) [-nopagepiece] [-nostruct] [-nowebcap] [-nooutline]\n\t(fonts:) [-nocore] [-noembed] [-subset]\n\t(images:) [-jpeg]\n\t[-password <password>] [-verbose] [-quiet]");
			// System.exit(0);
		}
		return i;
	}

	public static boolean compress(String[] paramArrayOfString) throws IOException {
		try {
			CompressAuriga localCompress = new CompressAuriga((PDFReader) null);
			int i = localCompress.commandLine(paramArrayOfString);
			Iterator localIterator = new FileList(paramArrayOfString, i, COS.FILTER).iterator();
			while (localIterator.hasNext()) {
				File localFile = (File) localIterator.next();
				if (localFile.canRead())
					if (System.getSecurityManager() == null)
						try {
							if (System.getSecurityManager() == null)
								localCompress.pdfr_ = new PDFReader(localFile);

							String str = localFile.getPath();
							// if (localFile.getName().toLowerCase().endsWith(".pdf"))
							// str = str.substring(0, str.length() - 4);
							localCompress.writeFile(localCompress.finplace_ ? localFile : new File(str + "-compress"));
						} catch (Exception localException) {
							throw new Exception(localFile + ": " + localException, localException);
						}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;

	}

	static final boolean DEBUG = false;
	public static final String VERSION = "2.4 of $Date: 2005/07/26 21:13:18 $";
	public static final String COPYRIGHT = "";
	private static final String USAGE = "java [-Xmx128m] tool.pdf.Compress [<options>] <PDF-file-or-directory...>\n\t[-old] [-compact] [-force] [-inplace] [-max]\n\t(data:) [-nopagepiece] [-nostruct] [-nowebcap] [-nooutline]\n\t(fonts:) [-nocore] [-noembed] [-subset]\n\t(images:) [-jpeg]\n\t[-password <password>] [-verbose] [-quiet]";
	private static final int PREDICT_OVERHEAD = "/DecodeParms<</Predictor 15/Columns10/Colors 3>>>>".length();
	private static final int PREDICT_COMPACT_WORTHWHILE = 2048;
	private static final int JPEG_OVERHEAD = 300;
	private static final int LOSSY_WORTHWHILE = 5120;
	private static final int COMPACT_OVERHEAD = 714;
	private static final String CORE14[] = { "Times-Roman", "Times-Bold", "Times-Italic", "Times-BoldItalic", "Helvetica", "Helvetica-Bold",
			"Helvetica-Oblique", "Helvetica-BoldOblique", "Courier", "Courier-Bold", "Courier-Oblique", "Courier-BoldOblique", "Symbol", "ZapfDingbats",
			"NimbusRomNo9L-Regu", "NimbusRomNo9L-ReguItal", "NimbusRomNo9L-Medi", "NimbusRomNo9L-MediItal", "NimbusSanL-Regu", "NimbusSanL-Bold",
			"NimbusSanL-ReguItal", "NimbusSanL-BoldItal", "NimbusMonL-Regu", "NimbusMonL-Bold", "NimbusMonL-ReguObli", "NimbusMonL-BoldObli",
			"Standard Symbols L", "Dingbats" };
	private static final int TTF_KEEP[] = { 1735162214, 1819239265, 1668112752, 1751474532, 1751672161, 1752003704, 1835104368, 1668707360, 1718642541,
			1886545264, 1330851634, 1886352244, 1734439792, 1751412088, 1280594760 };
	private boolean fold_;
	private boolean fforce_;
	private boolean finplace_;
	private boolean fcompact0_;
	private boolean fcompact_;
	private boolean fstruct_;
	private boolean fwebcap_;
	private boolean fpagepiece_;
	private boolean fjpeg_;
	private boolean fjpeg2000_;
	private boolean falt_;
	private boolean fcore14_;
	private boolean fembed_;
	private boolean fsubset_;
	private boolean foutline_;
	private boolean fverbose_;
	private boolean fmonitor_;
	private boolean fquiet_;
	private PrintStream out_;
	private boolean ftestable_;
	private boolean fpre_;
	private boolean fcontent_;
	private String password_;
	private PDFReader pdfr_;
	private java.util.List wacky_;
	static final boolean $assertionsDisabled; /* synthetic field */

	static {
		$assertionsDisabled = !(it.eng.module.foutility.util.CompressAuriga.class).desiredAssertionStatus();
		Arrays.sort(CORE14);
		Arrays.sort(TTF_KEEP);
	}
}
