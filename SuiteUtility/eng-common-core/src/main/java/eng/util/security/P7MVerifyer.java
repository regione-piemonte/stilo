// 
// Decompiled by Procyon v0.5.36
// 

package eng.util.security;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.apache.commons.io.FileUtils;
import iaik.x509.X509Certificate;
import iaik.pkcs.pkcs7.SignerInfo;
import it.eng.utility.FileUtil;
import java.io.FileOutputStream;
import iaik.pkcs.pkcs7.EnvelopedData;
import iaik.pkcs.pkcs7.SignedData;
import java.io.InputStream;
import iaik.pkcs.pkcs7.ContentInfo;
import java.io.FileInputStream;
import eng.util.Logger;
import java.security.Security;
import java.io.File;

public class P7MVerifyer
{
    private static final int _MAX_P7M_ENVELOPES = 50;
    
    public static void extractP7M(final File inFile, final File outFile) throws EngSecurityException {
        extractP7M(inFile, outFile, 0);
    }
    
    public static void extractP7M(final File inFile, File outFile, final int recursion_level) throws EngSecurityException {
        final boolean certificato_verificato = false;
        try {
            final Object[] p = Security.getProviders();
            Logger.getLogger().info((Object)("quanti provider ho? ->" + p.length));
            for (int i = 0; i < p.length; ++i) {
                Logger.getLogger().info((Object)("Provider" + i + " ->" + p[i]));
            }
            final FileInputStream fis = new FileInputStream(inFile);
            final ContentInfo ci = new ContentInfo((InputStream)fis);
            final SignedData signed_data = (SignedData)ci.getContent();
            if (ci.getContent() instanceof EnvelopedData) {
                final EnvelopedData enveloped_data = (EnvelopedData)ci.getContent();
                final FileOutputStream fos = new FileOutputStream(outFile);
                fos.write(enveloped_data.getContent());
                fos.close();
            }
            else {
                final SignerInfo[] signer_infos = signed_data.getSignerInfos();
                for (int j = 0; j < signer_infos.length && !certificato_verificato; ++j) {
                    final X509Certificate signer_cert = signed_data.verify(j);
                }
                Logger.getLogger().info((Object)"dopo for");
                final FileOutputStream fos = new FileOutputStream(outFile);
                fos.write(signed_data.getContent());
                fos.close();
                if (recursion_level > 0) {
                    FileUtil.deleteFile(inFile);
                    final File new_out_file = new File(outFile.getPath());
                    outFile.renameTo(inFile);
                    outFile = new_out_file;
                    extractP7M(inFile, outFile, recursion_level - 1);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new EngSecurityException(ex.getMessage());
        }
    }
    
    private static InputStream openP7M(final InputStream is, int counter) throws EngSecurityException {
        CMSSignedDataParser sd = null;
        InputStream isOut = null;
        File content = null;
        try {
            if (counter > 50) {
                throw new EngSecurityException("Superato il numero massimo di buste p7m (50)");
            }
            try {
                content = File.createTempFile("content", null);
                FileUtil.writeStreamToFile(is, content);
                sd = new CMSSignedDataParser((InputStream)FileUtils.openInputStream(content));
                isOut = sd.getSignedContent().getContentStream();
            }
            catch (CMSException e) {
                if (counter == 0) {
                    throw new EngSecurityException(e.getMessage());
                }
                return FileUtils.openInputStream(content);
            }
            isOut = openP7M(isOut, ++counter);
            return isOut;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new EngSecurityException(ex.getMessage());
        }
        finally {
            try {
                sd.close();
            }
            catch (Exception ex2) {}
            FileUtil.deleteFile(content);
        }
    }
    
    public static InputStream openP7M(final InputStream is) throws EngSecurityException {
        return openP7M(is, 0);
    }
    
    public static void main(final String[] argv) throws Exception {
        final File inFile = new File("c:\\tmp\\test-files\\signedfile.pdf.p7m.p7m");
        final File outFile = new File("c:\\tmp\\test-files\\unsignedfile.pdf");
        extractP7M(inFile, outFile);
        System.out.println("Fine!!!");
    }
}
