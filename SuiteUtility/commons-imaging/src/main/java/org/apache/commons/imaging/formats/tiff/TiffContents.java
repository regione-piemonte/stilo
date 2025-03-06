// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.internal.Debug;
import java.util.Comparator;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.ImageReadException;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TiffContents
{
    public final TiffHeader header;
    public final List<TiffDirectory> directories;
    
    public TiffContents(final TiffHeader tiffHeader, final List<TiffDirectory> directories) {
        this.header = tiffHeader;
        this.directories = Collections.unmodifiableList((List<? extends TiffDirectory>)directories);
    }
    
    public List<TiffElement> getElements() throws ImageReadException {
        final List<TiffElement> result = new ArrayList<TiffElement>();
        result.add(this.header);
        for (final TiffDirectory directory : this.directories) {
            result.add(directory);
            final List<TiffField> fields = directory.entries;
            for (final TiffField field : fields) {
                final TiffElement oversizeValue = field.getOversizeValueElement();
                if (null != oversizeValue) {
                    result.add(oversizeValue);
                }
            }
            if (directory.hasTiffImageData()) {
                result.addAll(directory.getTiffRawImageDataElements());
            }
            if (directory.hasJpegImageData()) {
                result.add(directory.getJpegRawImageDataElement());
            }
        }
        return result;
    }
    
    public TiffField findField(final TagInfo tag) throws ImageReadException {
        for (final TiffDirectory directory : this.directories) {
            final TiffField field = directory.findField(tag);
            if (null != field) {
                return field;
            }
        }
        return null;
    }
    
    public void dissect() throws ImageReadException {
        final List<TiffElement> elements = this.getElements();
        Collections.sort(elements, TiffElement.COMPARATOR);
        long lastEnd = 0L;
        for (final TiffElement element : elements) {
            if (element.offset > lastEnd) {
                Debug.debug("\tgap: " + (element.offset - lastEnd));
            }
            if (element.offset < lastEnd) {
                Debug.debug("\toverlap");
            }
            Debug.debug("element, start: " + element.offset + ", length: " + element.length + ", end: " + (element.offset + element.length) + ": " + element.getElementDescription());
            final String verbosity = element.getElementDescription();
            if (null != verbosity) {
                Debug.debug(verbosity);
            }
            lastEnd = element.offset + element.length;
        }
        Debug.debug("end: " + lastEnd);
        Debug.debug();
    }
}
