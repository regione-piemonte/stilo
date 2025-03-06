// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.segments;

import org.apache.commons.imaging.common.BinaryFunctions;
import java.util.logging.Level;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

public class SofnSegment extends Segment
{
    private static final Logger LOGGER;
    public final int width;
    public final int height;
    public final int numberOfComponents;
    public final int precision;
    private final Component[] components;
    
    public SofnSegment(final int marker, final byte[] segmentData) throws IOException {
        this(marker, segmentData.length, new ByteArrayInputStream(segmentData));
    }
    
    public SofnSegment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength);
        if (SofnSegment.LOGGER.isLoggable(Level.FINEST)) {
            SofnSegment.LOGGER.finest("SOF0Segment marker_length: " + markerLength);
        }
        this.precision = BinaryFunctions.readByte("Data_precision", is, "Not a Valid JPEG File");
        this.height = BinaryFunctions.read2Bytes("Image_height", is, "Not a Valid JPEG File", this.getByteOrder());
        this.width = BinaryFunctions.read2Bytes("Image_Width", is, "Not a Valid JPEG File", this.getByteOrder());
        this.numberOfComponents = BinaryFunctions.readByte("Number_of_components", is, "Not a Valid JPEG File");
        this.components = new Component[this.numberOfComponents];
        for (int i = 0; i < this.numberOfComponents; ++i) {
            final int componentIdentifier = BinaryFunctions.readByte("ComponentIdentifier", is, "Not a Valid JPEG File");
            final int hvSamplingFactors = BinaryFunctions.readByte("SamplingFactors", is, "Not a Valid JPEG File");
            final int horizontalSamplingFactor = hvSamplingFactors >> 4 & 0xF;
            final int verticalSamplingFactor = hvSamplingFactors & 0xF;
            final int quantTabDestSelector = BinaryFunctions.readByte("QuantTabDestSel", is, "Not a Valid JPEG File");
            this.components[i] = new Component(componentIdentifier, horizontalSamplingFactor, verticalSamplingFactor, quantTabDestSelector);
        }
    }
    
    public Component[] getComponents() {
        return this.components.clone();
    }
    
    public Component getComponents(final int index) {
        return this.components[index];
    }
    
    @Override
    public String getDescription() {
        return "SOFN (SOF" + (this.marker - 65472) + ") (" + this.getSegmentType() + ")";
    }
    
    static {
        LOGGER = Logger.getLogger(SofnSegment.class.getName());
    }
    
    public static class Component
    {
        public final int componentIdentifier;
        public final int horizontalSamplingFactor;
        public final int verticalSamplingFactor;
        public final int quantTabDestSelector;
        
        public Component(final int componentIdentifier, final int horizontalSamplingFactor, final int veritcalSamplingFactor, final int quantTabDestSelector) {
            this.componentIdentifier = componentIdentifier;
            this.horizontalSamplingFactor = horizontalSamplingFactor;
            this.verticalSamplingFactor = veritcalSamplingFactor;
            this.quantTabDestSelector = quantTabDestSelector;
        }
    }
}
