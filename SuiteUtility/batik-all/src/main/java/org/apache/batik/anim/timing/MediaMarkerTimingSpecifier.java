// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

public class MediaMarkerTimingSpecifier extends TimingSpecifier
{
    protected String syncbaseID;
    protected TimedElement mediaElement;
    protected String markerName;
    protected InstanceTime instance;
    
    public MediaMarkerTimingSpecifier(final TimedElement timedElement, final boolean b, final String syncbaseID, final String markerName) {
        super(timedElement, b);
        this.syncbaseID = syncbaseID;
        this.markerName = markerName;
        this.mediaElement = timedElement.getTimedElementById(syncbaseID);
    }
    
    public String toString() {
        return this.syncbaseID + ".marker(" + this.markerName + ")";
    }
    
    public boolean isEventCondition() {
        return false;
    }
}
