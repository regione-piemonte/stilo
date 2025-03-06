// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.icc;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.Writer;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class IccProfileInfo
{
    private static final Logger LOGGER;
    private final byte[] data;
    public final int profileSize;
    public final int cmmTypeSignature;
    public final int profileVersion;
    public final int profileDeviceClassSignature;
    public final int colorSpace;
    public final int profileConnectionSpace;
    public final int profileFileSignature;
    public final int primaryPlatformSignature;
    public final int variousFlags;
    public final int deviceManufacturer;
    public final int deviceModel;
    public final int renderingIntent;
    public final int profileCreatorSignature;
    private final byte[] profileId;
    private final IccTag[] tags;
    
    public IccProfileInfo(final byte[] data, final int profileSize, final int cmmTypeSignature, final int profileVersion, final int profileDeviceClassSignature, final int colorSpace, final int profileConnectionSpace, final int profileFileSignature, final int primaryPlatformSignature, final int variousFlags, final int deviceManufacturer, final int deviceModel, final int renderingIntent, final int profileCreatorSignature, final byte[] profileId, final IccTag[] tags) {
        this.data = data;
        this.profileSize = profileSize;
        this.cmmTypeSignature = cmmTypeSignature;
        this.profileVersion = profileVersion;
        this.profileDeviceClassSignature = profileDeviceClassSignature;
        this.colorSpace = colorSpace;
        this.profileConnectionSpace = profileConnectionSpace;
        this.profileFileSignature = profileFileSignature;
        this.primaryPlatformSignature = primaryPlatformSignature;
        this.variousFlags = variousFlags;
        this.deviceManufacturer = deviceManufacturer;
        this.deviceModel = deviceModel;
        this.renderingIntent = renderingIntent;
        this.profileCreatorSignature = profileCreatorSignature;
        this.profileId = profileId;
        this.tags = tags;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public byte[] getProfileId() {
        return this.profileId;
    }
    
    public IccTag[] getTags() {
        return this.tags;
    }
    
    public boolean issRGB() {
        return this.deviceManufacturer == 1229275936 && this.deviceModel == 1934772034;
    }
    
    private void printCharQuad(final PrintWriter pw, final String msg, final int i) {
        pw.println(msg + ": '" + (char)(0xFF & i >> 24) + (char)(0xFF & i >> 16) + (char)(0xFF & i >> 8) + (char)(0xFF & i >> 0) + "'");
    }
    
    public void dump(final String prefix) {
        IccProfileInfo.LOGGER.fine(this.toString());
    }
    
    @Override
    public String toString() {
        try {
            return this.toString("");
        }
        catch (Exception e) {
            return "IccProfileInfo: Error";
        }
    }
    
    public String toString(final String prefix) throws ImageReadException, IOException {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        pw.println(prefix + ": data length: " + this.data.length);
        this.printCharQuad(pw, prefix + ": ProfileDeviceClassSignature", this.profileDeviceClassSignature);
        this.printCharQuad(pw, prefix + ": CMMTypeSignature", this.cmmTypeSignature);
        this.printCharQuad(pw, prefix + ": ProfileDeviceClassSignature", this.profileDeviceClassSignature);
        this.printCharQuad(pw, prefix + ": ColorSpace", this.colorSpace);
        this.printCharQuad(pw, prefix + ": ProfileConnectionSpace", this.profileConnectionSpace);
        this.printCharQuad(pw, prefix + ": ProfileFileSignature", this.profileFileSignature);
        this.printCharQuad(pw, prefix + ": PrimaryPlatformSignature", this.primaryPlatformSignature);
        this.printCharQuad(pw, prefix + ": ProfileFileSignature", this.profileFileSignature);
        this.printCharQuad(pw, prefix + ": DeviceManufacturer", this.deviceManufacturer);
        this.printCharQuad(pw, prefix + ": DeviceModel", this.deviceModel);
        this.printCharQuad(pw, prefix + ": RenderingIntent", this.renderingIntent);
        this.printCharQuad(pw, prefix + ": ProfileCreatorSignature", this.profileCreatorSignature);
        for (int i = 0; i < this.tags.length; ++i) {
            final IccTag tag = this.tags[i];
            tag.dump(pw, "\t" + i + ": ");
        }
        pw.println(prefix + ": issRGB: " + this.issRGB());
        pw.flush();
        return sw.getBuffer().toString();
    }
    
    static {
        LOGGER = Logger.getLogger(IccProfileInfo.class.getName());
    }
}
