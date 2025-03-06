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

public class SosSegment extends Segment
{
    private static final Logger LOGGER;
    public final int numberOfComponents;
    private final Component[] components;
    public final int startOfSpectralSelection;
    public final int endOfSpectralSelection;
    public final int successiveApproximationBitHigh;
    public final int successiveApproximationBitLow;
    
    public SosSegment(final int marker, final byte[] segmentData) throws IOException {
        this(marker, segmentData.length, new ByteArrayInputStream(segmentData));
    }
    
    public SosSegment(final int marker, final int markerLength, final InputStream is) throws IOException {
        super(marker, markerLength);
        if (SosSegment.LOGGER.isLoggable(Level.FINEST)) {
            SosSegment.LOGGER.finest("SosSegment marker_length: " + markerLength);
        }
        this.numberOfComponents = BinaryFunctions.readByte("number_of_components_in_scan", is, "Not a Valid JPEG File");
        this.components = new Component[this.numberOfComponents];
        for (int i = 0; i < this.numberOfComponents; ++i) {
            final int scanComponentSelector = BinaryFunctions.readByte("scanComponentSelector", is, "Not a Valid JPEG File");
            final int acDcEntropoyCodingTableSelector = BinaryFunctions.readByte("acDcEntropoyCodingTableSelector", is, "Not a Valid JPEG File");
            final int dcCodingTableSelector = acDcEntropoyCodingTableSelector >> 4 & 0xF;
            final int acCodingTableSelector = acDcEntropoyCodingTableSelector & 0xF;
            this.components[i] = new Component(scanComponentSelector, dcCodingTableSelector, acCodingTableSelector);
        }
        this.startOfSpectralSelection = BinaryFunctions.readByte("start_of_spectral_selection", is, "Not a Valid JPEG File");
        this.endOfSpectralSelection = BinaryFunctions.readByte("end_of_spectral_selection", is, "Not a Valid JPEG File");
        final int successiveApproximationBitPosition = BinaryFunctions.readByte("successive_approximation_bit_position", is, "Not a Valid JPEG File");
        this.successiveApproximationBitHigh = (successiveApproximationBitPosition >> 4 & 0xF);
        this.successiveApproximationBitLow = (successiveApproximationBitPosition & 0xF);
    }
    
    public Component[] getComponents() {
        return this.components.clone();
    }
    
    public Component getComponents(final int index) {
        return this.components[index];
    }
    
    @Override
    public String getDescription() {
        return "SOS (" + this.getSegmentType() + ")";
    }
    
    static {
        LOGGER = Logger.getLogger(SosSegment.class.getName());
    }
    
    public static class Component
    {
        public final int scanComponentSelector;
        public final int dcCodingTableSelector;
        public final int acCodingTableSelector;
        
        public Component(final int scanComponentSelector, final int dcCodingTableSelector, final int acCodingTableSelector) {
            this.scanComponentSelector = scanComponentSelector;
            this.dcCodingTableSelector = dcCodingTableSelector;
            this.acCodingTableSelector = acCodingTableSelector;
        }
    }
}
