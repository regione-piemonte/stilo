// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.Calendar;
import org.apache.batik.parser.TimingSpecifierListHandler;
import org.apache.batik.parser.TimingSpecifierListParser;
import java.util.LinkedList;
import org.apache.batik.parser.DefaultTimingSpecifierListHandler;

public class TimingSpecifierListProducer extends DefaultTimingSpecifierListHandler
{
    protected LinkedList timingSpecifiers;
    protected TimedElement owner;
    protected boolean isBegin;
    
    public TimingSpecifierListProducer(final TimedElement owner, final boolean isBegin) {
        this.timingSpecifiers = new LinkedList();
        this.owner = owner;
        this.isBegin = isBegin;
    }
    
    public TimingSpecifier[] getTimingSpecifiers() {
        return this.timingSpecifiers.toArray(new TimingSpecifier[0]);
    }
    
    public static TimingSpecifier[] parseTimingSpecifierList(final TimedElement timedElement, final boolean b, final String s, final boolean b2, final boolean b3) {
        final TimingSpecifierListParser timingSpecifierListParser = new TimingSpecifierListParser(b2, b3);
        final TimingSpecifierListProducer timingSpecifierListHandler = new TimingSpecifierListProducer(timedElement, b);
        timingSpecifierListParser.setTimingSpecifierListHandler(timingSpecifierListHandler);
        timingSpecifierListParser.parse(s);
        return timingSpecifierListHandler.getTimingSpecifiers();
    }
    
    public void offset(final float n) {
        this.timingSpecifiers.add(new OffsetTimingSpecifier(this.owner, this.isBegin, n));
    }
    
    public void syncbase(final float n, final String s, final String s2) {
        this.timingSpecifiers.add(new SyncbaseTimingSpecifier(this.owner, this.isBegin, n, s, s2.charAt(0) == 'b'));
    }
    
    public void eventbase(final float n, final String s, final String s2) {
        this.timingSpecifiers.add(new EventbaseTimingSpecifier(this.owner, this.isBegin, n, s, s2));
    }
    
    public void repeat(final float n, final String s) {
        this.timingSpecifiers.add(new RepeatTimingSpecifier(this.owner, this.isBegin, n, s));
    }
    
    public void repeat(final float n, final String s, final int n2) {
        this.timingSpecifiers.add(new RepeatTimingSpecifier(this.owner, this.isBegin, n, s, n2));
    }
    
    public void accesskey(final float n, final char c) {
        this.timingSpecifiers.add(new AccesskeyTimingSpecifier(this.owner, this.isBegin, n, c));
    }
    
    public void accessKeySVG12(final float n, final String s) {
        this.timingSpecifiers.add(new AccesskeyTimingSpecifier(this.owner, this.isBegin, n, s));
    }
    
    public void mediaMarker(final String s, final String s2) {
        this.timingSpecifiers.add(new MediaMarkerTimingSpecifier(this.owner, this.isBegin, s, s2));
    }
    
    public void wallclock(final Calendar calendar) {
        this.timingSpecifiers.add(new WallclockTimingSpecifier(this.owner, this.isBegin, calendar));
    }
    
    public void indefinite() {
        this.timingSpecifiers.add(new IndefiniteTimingSpecifier(this.owner, this.isBegin));
    }
}
