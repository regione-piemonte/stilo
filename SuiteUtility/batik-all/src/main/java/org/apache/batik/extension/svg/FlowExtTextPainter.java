// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import java.util.Iterator;
import org.apache.batik.gvt.text.TextSpanLayout;
import java.util.ArrayList;
import java.util.List;
import java.text.AttributedCharacterIterator;
import org.apache.batik.gvt.TextNode;
import org.apache.batik.gvt.TextPainter;
import org.apache.batik.gvt.renderer.StrokingTextPainter;

public class FlowExtTextPainter extends StrokingTextPainter
{
    protected static TextPainter singleton;
    
    public static TextPainter getInstance() {
        return FlowExtTextPainter.singleton;
    }
    
    public List getTextRuns(final TextNode textNode, final AttributedCharacterIterator attributedCharacterIterator) {
        final List textRuns = textNode.getTextRuns();
        if (textRuns != null) {
            return textRuns;
        }
        final AttributedCharacterIterator[] textChunkACIs = this.getTextChunkACIs(attributedCharacterIterator);
        final List computeTextRuns = this.computeTextRuns(textNode, attributedCharacterIterator, textChunkACIs);
        attributedCharacterIterator.first();
        final List list = (List)attributedCharacterIterator.getAttribute(FlowExtTextPainter.FLOW_REGIONS);
        if (list != null) {
            final Iterator<TextRun> iterator = computeTextRuns.iterator();
            final ArrayList<ArrayList<TextSpanLayout>> list2 = new ArrayList<ArrayList<TextSpanLayout>>();
            final TextRun textRun = iterator.next();
            ArrayList<TextSpanLayout> list3 = new ArrayList<TextSpanLayout>();
            list2.add(list3);
            list3.add(textRun.getLayout());
            while (iterator.hasNext()) {
                final TextRun textRun2 = iterator.next();
                if (textRun2.isFirstRunInChunk()) {
                    list3 = new ArrayList<TextSpanLayout>();
                    list2.add(list3);
                }
                list3.add(textRun2.getLayout());
            }
            FlowExtGlyphLayout.textWrapTextChunk(textChunkACIs, list2, list);
        }
        textNode.setTextRuns(computeTextRuns);
        return computeTextRuns;
    }
    
    static {
        FlowExtTextPainter.singleton = new FlowExtTextPainter();
    }
}
