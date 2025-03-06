// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim.timing;

import java.util.MissingResourceException;
import java.util.Locale;
import org.apache.batik.anim.AnimationException;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventTarget;
import java.util.HashSet;
import java.util.Calendar;
import org.apache.batik.parser.ClockHandler;
import org.apache.batik.parser.ClockParser;
import org.apache.batik.parser.ParseException;
import java.util.Set;
import org.w3c.dom.events.Event;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.batik.i18n.LocalizableSupport;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import org.apache.batik.util.SMILConstants;

public abstract class TimedElement implements SMILConstants
{
    public static final int FILL_REMOVE = 0;
    public static final int FILL_FREEZE = 1;
    public static final int RESTART_ALWAYS = 0;
    public static final int RESTART_WHEN_NOT_ACTIVE = 1;
    public static final int RESTART_NEVER = 2;
    public static final float INDEFINITE = Float.POSITIVE_INFINITY;
    public static final float UNRESOLVED = Float.NaN;
    protected TimedDocumentRoot root;
    protected TimeContainer parent;
    protected TimingSpecifier[] beginTimes;
    protected TimingSpecifier[] endTimes;
    protected float simpleDur;
    protected boolean durMedia;
    protected float repeatCount;
    protected float repeatDur;
    protected int currentRepeatIteration;
    protected float lastRepeatTime;
    protected int fillMode;
    protected int restartMode;
    protected float min;
    protected boolean minMedia;
    protected float max;
    protected boolean maxMedia;
    protected boolean isActive;
    protected boolean isFrozen;
    protected float lastSampleTime;
    protected float repeatDuration;
    protected List beginInstanceTimes;
    protected List endInstanceTimes;
    protected Interval currentInterval;
    protected float lastIntervalEnd;
    protected Interval previousInterval;
    protected LinkedList beginDependents;
    protected LinkedList endDependents;
    protected boolean shouldUpdateCurrentInterval;
    protected boolean hasParsed;
    protected Map handledEvents;
    protected boolean isSampling;
    protected boolean hasPropagated;
    protected static final String RESOURCES = "org.apache.batik.anim.resources.Messages";
    protected static LocalizableSupport localizableSupport;
    
    public TimedElement() {
        this.beginInstanceTimes = new ArrayList();
        this.endInstanceTimes = new ArrayList();
        this.beginDependents = new LinkedList();
        this.endDependents = new LinkedList();
        this.shouldUpdateCurrentInterval = true;
        this.handledEvents = new HashMap();
        this.beginTimes = new TimingSpecifier[0];
        this.endTimes = this.beginTimes;
        this.simpleDur = Float.NaN;
        this.repeatCount = Float.NaN;
        this.repeatDur = Float.NaN;
        this.lastRepeatTime = Float.NaN;
        this.max = Float.POSITIVE_INFINITY;
        this.lastSampleTime = Float.NaN;
        this.lastIntervalEnd = Float.NEGATIVE_INFINITY;
    }
    
    public TimedDocumentRoot getRoot() {
        return this.root;
    }
    
    public float getActiveTime() {
        return this.lastSampleTime;
    }
    
    public float getSimpleTime() {
        return this.lastSampleTime - this.lastRepeatTime;
    }
    
    protected float addInstanceTime(final InstanceTime key, final boolean b) {
        this.hasPropagated = true;
        final List list = b ? this.beginInstanceTimes : this.endInstanceTimes;
        int binarySearch = Collections.binarySearch(list, key);
        if (binarySearch < 0) {
            binarySearch = -(binarySearch + 1);
        }
        list.add(binarySearch, key);
        this.shouldUpdateCurrentInterval = true;
        float sample;
        if (this.root.isSampling() && !this.isSampling) {
            sample = this.sampleAt(this.root.getCurrentTime(), this.root.isHyperlinking());
        }
        else {
            sample = Float.POSITIVE_INFINITY;
        }
        this.hasPropagated = false;
        this.root.currentIntervalWillUpdate();
        return sample;
    }
    
    protected float removeInstanceTime(final InstanceTime key, final boolean b) {
        this.hasPropagated = true;
        final List list = b ? this.beginInstanceTimes : this.endInstanceTimes;
        int i;
        int n;
        for (n = (i = Collections.binarySearch(list, key)); i >= 0; --i) {
            final InstanceTime instanceTime = (InstanceTime)list.get(i);
            if (instanceTime == key) {
                list.remove(i);
                break;
            }
            if (instanceTime.compareTo(key) != 0) {
                break;
            }
        }
        for (int size = list.size(), j = n + 1; j < size; ++j) {
            final InstanceTime instanceTime2 = (InstanceTime)list.get(j);
            if (instanceTime2 == key) {
                list.remove(j);
                break;
            }
            if (instanceTime2.compareTo(key) != 0) {
                break;
            }
        }
        this.shouldUpdateCurrentInterval = true;
        float sample;
        if (this.root.isSampling() && !this.isSampling) {
            sample = this.sampleAt(this.root.getCurrentTime(), this.root.isHyperlinking());
        }
        else {
            sample = Float.POSITIVE_INFINITY;
        }
        this.hasPropagated = false;
        this.root.currentIntervalWillUpdate();
        return sample;
    }
    
    protected float instanceTimeChanged(final InstanceTime instanceTime, final boolean b) {
        this.hasPropagated = true;
        this.shouldUpdateCurrentInterval = true;
        float sample;
        if (this.root.isSampling() && !this.isSampling) {
            sample = this.sampleAt(this.root.getCurrentTime(), this.root.isHyperlinking());
        }
        else {
            sample = Float.POSITIVE_INFINITY;
        }
        this.hasPropagated = false;
        return sample;
    }
    
    protected void addDependent(final TimingSpecifier timingSpecifier, final boolean b) {
        if (b) {
            this.beginDependents.add(timingSpecifier);
        }
        else {
            this.endDependents.add(timingSpecifier);
        }
    }
    
    protected void removeDependent(final TimingSpecifier timingSpecifier, final boolean b) {
        if (b) {
            this.beginDependents.remove(timingSpecifier);
        }
        else {
            this.endDependents.remove(timingSpecifier);
        }
    }
    
    public float getSimpleDur() {
        if (this.durMedia) {
            return this.getImplicitDur();
        }
        if (!isUnresolved(this.simpleDur)) {
            return this.simpleDur;
        }
        if (isUnresolved(this.repeatCount) && isUnresolved(this.repeatDur) && this.endTimes.length > 0) {
            return Float.POSITIVE_INFINITY;
        }
        return this.getImplicitDur();
    }
    
    public static boolean isUnresolved(final float v) {
        return Float.isNaN(v);
    }
    
    public float getActiveDur(final float n, final float n2) {
        final float simpleDur = this.getSimpleDur();
        if (!isUnresolved(n2) && simpleDur == Float.POSITIVE_INFINITY) {
            return this.repeatDuration = this.minTime(this.max, this.maxTime(this.min, this.minusTime(n2, n)));
        }
        float minTime;
        if (simpleDur == 0.0f) {
            minTime = 0.0f;
        }
        else if (isUnresolved(this.repeatDur) && isUnresolved(this.repeatCount)) {
            minTime = simpleDur;
        }
        else {
            minTime = this.minTime(this.minTime(isUnresolved(this.repeatCount) ? Float.POSITIVE_INFINITY : this.multiplyTime(simpleDur, this.repeatCount), isUnresolved(this.repeatDur) ? Float.POSITIVE_INFINITY : this.repeatDur), Float.POSITIVE_INFINITY);
        }
        float minTime2;
        if (isUnresolved(n2) || n2 == Float.POSITIVE_INFINITY) {
            minTime2 = minTime;
        }
        else {
            minTime2 = this.minTime(minTime, this.minusTime(n2, n));
        }
        this.repeatDuration = minTime;
        return this.minTime(this.max, this.maxTime(this.min, minTime2));
    }
    
    protected float minusTime(final float n, final float n2) {
        if (isUnresolved(n) || isUnresolved(n2)) {
            return Float.NaN;
        }
        if (n == Float.POSITIVE_INFINITY || n2 == Float.POSITIVE_INFINITY) {
            return Float.POSITIVE_INFINITY;
        }
        return n - n2;
    }
    
    protected float multiplyTime(final float n, final float n2) {
        if (isUnresolved(n) || n == Float.POSITIVE_INFINITY) {
            return n;
        }
        return n * n2;
    }
    
    protected float minTime(final float n, final float n2) {
        if (n == 0.0f || n2 == 0.0f) {
            return 0.0f;
        }
        if ((n == Float.POSITIVE_INFINITY || isUnresolved(n)) && n2 != Float.POSITIVE_INFINITY && !isUnresolved(n2)) {
            return n2;
        }
        if ((n2 == Float.POSITIVE_INFINITY || isUnresolved(n2)) && n != Float.POSITIVE_INFINITY && !isUnresolved(n)) {
            return n;
        }
        if ((n == Float.POSITIVE_INFINITY && isUnresolved(n2)) || (isUnresolved(n) && n2 == Float.POSITIVE_INFINITY)) {
            return Float.POSITIVE_INFINITY;
        }
        if (n < n2) {
            return n;
        }
        return n2;
    }
    
    protected float maxTime(final float n, final float n2) {
        if ((n == Float.POSITIVE_INFINITY || isUnresolved(n)) && n2 != Float.POSITIVE_INFINITY && !isUnresolved(n2)) {
            return n;
        }
        if ((n2 == Float.POSITIVE_INFINITY || isUnresolved(n2)) && n != Float.POSITIVE_INFINITY && !isUnresolved(n)) {
            return n2;
        }
        if ((n == Float.POSITIVE_INFINITY && isUnresolved(n2)) || (isUnresolved(n) && n2 == Float.POSITIVE_INFINITY)) {
            return Float.NaN;
        }
        if (n > n2) {
            return n;
        }
        return n2;
    }
    
    protected float getImplicitDur() {
        return Float.NaN;
    }
    
    protected float notifyNewInterval(final Interval interval) {
        float n = Float.POSITIVE_INFINITY;
        final Iterator iterator = this.beginDependents.iterator();
        while (iterator.hasNext()) {
            final float interval2 = iterator.next().newInterval(interval);
            if (interval2 < n) {
                n = interval2;
            }
        }
        final Iterator iterator2 = this.endDependents.iterator();
        while (iterator2.hasNext()) {
            final float interval3 = iterator2.next().newInterval(interval);
            if (interval3 < n) {
                n = interval3;
            }
        }
        return n;
    }
    
    protected float notifyRemoveInterval(final Interval interval) {
        float n = Float.POSITIVE_INFINITY;
        final Iterator iterator = this.beginDependents.iterator();
        while (iterator.hasNext()) {
            final float removeInterval = iterator.next().removeInterval(interval);
            if (removeInterval < n) {
                n = removeInterval;
            }
        }
        final Iterator iterator2 = this.endDependents.iterator();
        while (iterator2.hasNext()) {
            final float removeInterval2 = iterator2.next().removeInterval(interval);
            if (removeInterval2 < n) {
                n = removeInterval2;
            }
        }
        return n;
    }
    
    protected float sampleAt(final float lastSampleTime, boolean b) {
        this.isSampling = true;
        for (final Map.Entry<Event, V> entry : this.handledEvents.entrySet()) {
            final Event event = entry.getKey();
            final Set<EventLikeTimingSpecifier> set = entry.getValue();
            final Iterator<EventLikeTimingSpecifier> iterator2 = set.iterator();
            int n = 0;
            int n2 = 0;
            while (iterator2.hasNext() && (n == 0 || n2 == 0)) {
                if (iterator2.next().isBegin()) {
                    n = 1;
                }
                else {
                    n2 = 1;
                }
            }
            int n3;
            boolean b2;
            if (n != 0 && n2 != 0) {
                n3 = ((!this.isActive || this.restartMode == 0) ? 1 : 0);
                b2 = (n3 == 0);
            }
            else if (n != 0 && (!this.isActive || this.restartMode == 0)) {
                n3 = 1;
                b2 = false;
            }
            else {
                if (n2 == 0 || !this.isActive) {
                    continue;
                }
                n3 = 0;
                b2 = true;
            }
            for (final EventLikeTimingSpecifier eventLikeTimingSpecifier : set) {
                final boolean begin = eventLikeTimingSpecifier.isBegin();
                if ((begin && n3 != 0) || (!begin && b2)) {
                    eventLikeTimingSpecifier.resolve(event);
                    this.shouldUpdateCurrentInterval = true;
                }
            }
        }
        this.handledEvents.clear();
        if (this.currentInterval != null) {
            final float begin2 = this.currentInterval.getBegin();
            if (this.lastSampleTime < begin2 && lastSampleTime >= begin2) {
                if (!this.isActive) {
                    this.toActive(begin2);
                }
                this.isActive = true;
                this.isFrozen = false;
                this.lastRepeatTime = begin2;
                this.fireTimeEvent("beginEvent", this.currentInterval.getBegin(), 0);
            }
        }
        boolean b3 = this.currentInterval != null && lastSampleTime >= this.currentInterval.getEnd();
        if (this.currentInterval != null) {
            final float begin3 = this.currentInterval.getBegin();
            if (lastSampleTime >= begin3) {
                final float simpleDur = this.getSimpleDur();
                while (lastSampleTime - this.lastRepeatTime >= simpleDur && this.lastRepeatTime + simpleDur < begin3 + this.repeatDuration) {
                    this.lastRepeatTime += simpleDur;
                    ++this.currentRepeatIteration;
                    this.fireTimeEvent(this.root.getRepeatEventName(), this.lastRepeatTime, this.currentRepeatIteration);
                }
            }
        }
        float n4 = Float.POSITIVE_INFINITY;
        if (b) {
            this.shouldUpdateCurrentInterval = true;
        }
        while (this.shouldUpdateCurrentInterval || b3) {
            if (b3) {
                this.previousInterval = this.currentInterval;
                this.toInactive(this.isActive = false, this.isFrozen = (this.fillMode == 1));
                this.fireTimeEvent("endEvent", this.currentInterval.getEnd(), 0);
            }
            final boolean b4 = this.currentInterval == null && this.previousInterval == null;
            if (this.currentInterval != null && b) {
                this.isActive = false;
                this.toInactive(this.isFrozen = false, false);
                this.currentInterval = null;
            }
            if (this.currentInterval == null || b3) {
                if (b4 || b || this.restartMode != 2) {
                    boolean b5 = true;
                    float end;
                    if (b4 || b) {
                        end = Float.NEGATIVE_INFINITY;
                    }
                    else {
                        end = this.previousInterval.getEnd();
                        b5 = (end != this.previousInterval.getBegin());
                    }
                    final Interval computeInterval = this.computeInterval(b4, false, end, b5);
                    if (computeInterval == null) {
                        this.currentInterval = null;
                    }
                    else {
                        final float selectNewInterval = this.selectNewInterval(lastSampleTime, computeInterval);
                        if (selectNewInterval < n4) {
                            n4 = selectNewInterval;
                        }
                    }
                }
                else {
                    this.currentInterval = null;
                }
            }
            else {
                final float begin4 = this.currentInterval.getBegin();
                if (begin4 > lastSampleTime) {
                    boolean b6 = true;
                    float end2;
                    if (this.previousInterval == null) {
                        end2 = Float.NEGATIVE_INFINITY;
                    }
                    else {
                        end2 = this.previousInterval.getEnd();
                        b6 = (end2 != this.previousInterval.getBegin());
                    }
                    final Interval computeInterval2 = this.computeInterval(false, false, end2, b6);
                    final float notifyRemoveInterval = this.notifyRemoveInterval(this.currentInterval);
                    if (notifyRemoveInterval < n4) {
                        n4 = notifyRemoveInterval;
                    }
                    if (computeInterval2 == null) {
                        this.currentInterval = null;
                    }
                    else {
                        final float selectNewInterval2 = this.selectNewInterval(lastSampleTime, computeInterval2);
                        if (selectNewInterval2 < n4) {
                            n4 = selectNewInterval2;
                        }
                    }
                }
                else {
                    final Interval computeInterval3 = this.computeInterval(false, true, begin4, true);
                    final float end3 = computeInterval3.getEnd();
                    if (this.currentInterval.getEnd() != end3) {
                        final float setEnd = this.currentInterval.setEnd(end3, computeInterval3.getEndInstanceTime());
                        if (setEnd < n4) {
                            n4 = setEnd;
                        }
                    }
                }
            }
            this.shouldUpdateCurrentInterval = false;
            b = false;
            b3 = (this.currentInterval != null && lastSampleTime >= this.currentInterval.getEnd());
        }
        final float simpleDur2 = this.getSimpleDur();
        if (this.isActive && !this.isFrozen) {
            if (lastSampleTime - this.currentInterval.getBegin() >= this.repeatDuration) {
                this.toInactive(true, this.isFrozen = (this.fillMode == 1));
            }
            else {
                this.sampledAt(lastSampleTime - this.lastRepeatTime, simpleDur2, this.currentRepeatIteration);
            }
        }
        if (this.isFrozen) {
            float n5;
            boolean b7;
            if (this.isActive) {
                n5 = this.currentInterval.getBegin() + this.repeatDuration - this.lastRepeatTime;
                b7 = (this.lastRepeatTime + simpleDur2 == this.currentInterval.getBegin() + this.repeatDuration);
            }
            else {
                n5 = this.previousInterval.getEnd() - this.lastRepeatTime;
                b7 = (this.lastRepeatTime + simpleDur2 == this.previousInterval.getEnd());
            }
            if (b7) {
                this.sampledLastValue(this.currentRepeatIteration);
            }
            else {
                this.sampledAt(n5 % simpleDur2, simpleDur2, this.currentRepeatIteration);
            }
        }
        else if (!this.isActive) {}
        this.isSampling = false;
        this.lastSampleTime = lastSampleTime;
        if (this.currentInterval == null) {
            return n4;
        }
        float n6 = this.currentInterval.getBegin() - lastSampleTime;
        if (n6 <= 0.0f) {
            n6 = ((this.isConstantAnimation() || this.isFrozen) ? (this.currentInterval.getEnd() - lastSampleTime) : 0.0f);
        }
        if (n4 < n6) {
            return n4;
        }
        return n6;
    }
    
    protected boolean endHasEventConditions() {
        for (int i = 0; i < this.endTimes.length; ++i) {
            if (this.endTimes[i].isEventCondition()) {
                return true;
            }
        }
        return false;
    }
    
    protected float selectNewInterval(final float n, final Interval currentInterval) {
        this.currentInterval = currentInterval;
        final float notifyNewInterval = this.notifyNewInterval(this.currentInterval);
        float begin = this.currentInterval.getBegin();
        if (n >= begin) {
            this.lastRepeatTime = begin;
            if (begin < 0.0f) {
                begin = 0.0f;
            }
            this.toActive(begin);
            this.isActive = true;
            this.isFrozen = false;
            this.fireTimeEvent("beginEvent", begin, 0);
            final float simpleDur = this.getSimpleDur();
            final float end = this.currentInterval.getEnd();
            while (n - this.lastRepeatTime >= simpleDur && this.lastRepeatTime + simpleDur < end) {
                this.lastRepeatTime += simpleDur;
                ++this.currentRepeatIteration;
                this.fireTimeEvent(this.root.getRepeatEventName(), this.lastRepeatTime, this.currentRepeatIteration);
            }
        }
        return notifyNewInterval;
    }
    
    protected Interval computeInterval(final boolean b, final boolean b2, float n, final boolean b3) {
        final Iterator iterator = this.beginInstanceTimes.iterator();
        final Iterator iterator2 = this.endInstanceTimes.iterator();
        final float simpleDur = this.parent.getSimpleDur();
        InstanceTime instanceTime = iterator2.hasNext() ? iterator2.next() : null;
        int n2 = 1;
        InstanceTime instanceTime2 = null;
        InstanceTime instanceTime3 = null;
        while (true) {
            float time = 0.0f;
            Label_0206: {
                if (!b2) {
                    while (iterator.hasNext()) {
                        instanceTime2 = iterator.next();
                        time = instanceTime2.getTime();
                        if ((b3 && time >= n) || (!b3 && time > n)) {
                            if (!iterator.hasNext()) {
                                break Label_0206;
                            }
                            instanceTime3 = iterator.next();
                            if (instanceTime2.getTime() != instanceTime3.getTime()) {
                                break Label_0206;
                            }
                            instanceTime3 = null;
                        }
                    }
                    return null;
                }
                time = n;
                while (iterator.hasNext()) {
                    instanceTime3 = iterator.next();
                    if (instanceTime3.getTime() > time) {
                        break;
                    }
                }
            }
            if (time >= simpleDur) {
                return null;
            }
            float n3;
            if (this.endTimes.length == 0) {
                n3 = time + this.getActiveDur(time, Float.POSITIVE_INFINITY);
            }
            else {
                float n4;
                if (this.endInstanceTimes.isEmpty()) {
                    n4 = Float.NaN;
                }
                else {
                    n4 = instanceTime.getTime();
                    Label_0387: {
                        if ((b && n2 == 0 && n4 == time) || (!b && this.currentInterval != null && n4 == this.currentInterval.getEnd() && ((b3 && n >= n4) || (!b3 && n > n4)))) {
                            while (iterator2.hasNext()) {
                                instanceTime = iterator2.next();
                                n4 = instanceTime.getTime();
                                if (n4 > time) {
                                    break Label_0387;
                                }
                            }
                            if (!this.endHasEventConditions()) {
                                return null;
                            }
                            n4 = Float.NaN;
                        }
                    }
                    n2 = 0;
                    while (n4 < time) {
                        if (!iterator2.hasNext()) {
                            if (this.endHasEventConditions()) {
                                n4 = Float.NaN;
                                break;
                            }
                            return null;
                        }
                        else {
                            instanceTime = iterator2.next();
                            n4 = instanceTime.getTime();
                        }
                    }
                }
                n3 = time + this.getActiveDur(time, n4);
            }
            if (!b || n3 > 0.0f || (time == 0.0f && n3 == 0.0f) || isUnresolved(n3)) {
                if (this.restartMode == 0 && instanceTime3 != null) {
                    final float time2 = instanceTime3.getTime();
                    if (time2 < n3 || isUnresolved(n3)) {
                        n3 = time2;
                        instanceTime = instanceTime3;
                    }
                }
                return new Interval(time, n3, instanceTime2, instanceTime);
            }
            if (b2) {
                return null;
            }
            n = n3;
        }
    }
    
    protected void reset(final boolean b) {
        final Iterator<InstanceTime> iterator = this.beginInstanceTimes.iterator();
        while (iterator.hasNext()) {
            final InstanceTime instanceTime = iterator.next();
            if (instanceTime.getClearOnReset() && (b || this.currentInterval == null || this.currentInterval.getBeginInstanceTime() != instanceTime)) {
                iterator.remove();
            }
        }
        final Iterator<InstanceTime> iterator2 = this.endInstanceTimes.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().getClearOnReset()) {
                iterator2.remove();
            }
        }
        if (this.isFrozen) {
            this.removeFill();
        }
        this.currentRepeatIteration = 0;
        this.lastRepeatTime = Float.NaN;
        this.isActive = false;
        this.isFrozen = false;
        this.lastSampleTime = Float.NaN;
    }
    
    public void parseAttributes(final String s, final String s2, final String s3, final String s4, final String s5, final String s6, final String s7, final String s8, final String s9) {
        if (!this.hasParsed) {
            this.parseBegin(s);
            this.parseDur(s2);
            this.parseEnd(s3);
            this.parseMin(s4);
            this.parseMax(s5);
            if (this.min > this.max) {
                this.min = 0.0f;
                this.max = Float.POSITIVE_INFINITY;
            }
            this.parseRepeatCount(s6);
            this.parseRepeatDur(s7);
            this.parseFill(s8);
            this.parseRestart(s9);
            this.hasParsed = true;
        }
    }
    
    protected void parseBegin(String s) {
        try {
            if (s.length() == 0) {
                s = "0";
            }
            this.beginTimes = TimingSpecifierListProducer.parseTimingSpecifierList(this, true, s, this.root.useSVG11AccessKeys, this.root.useSVG12AccessKeys);
        }
        catch (ParseException ex) {
            throw this.createException("attribute.malformed", new Object[] { null, "begin" });
        }
    }
    
    protected void parseDur(final String s) {
        if (s.equals("media")) {
            this.durMedia = true;
            this.simpleDur = Float.NaN;
        }
        else {
            this.durMedia = false;
            if (s.length() == 0 || s.equals("indefinite")) {
                this.simpleDur = Float.POSITIVE_INFINITY;
            }
            else {
                try {
                    this.simpleDur = this.parseClockValue(s, false);
                }
                catch (ParseException ex) {
                    throw this.createException("attribute.malformed", new Object[] { null, "dur" });
                }
                if (this.simpleDur < 0.0f) {
                    this.simpleDur = Float.POSITIVE_INFINITY;
                }
            }
        }
    }
    
    protected float parseClockValue(final String s, final boolean b) throws ParseException {
        final ClockParser clockParser = new ClockParser(b);
        final Handler clockHandler = new Handler();
        clockParser.setClockHandler(clockHandler);
        clockParser.parse(s);
        return clockHandler.v;
    }
    
    protected void parseEnd(final String s) {
        try {
            this.endTimes = TimingSpecifierListProducer.parseTimingSpecifierList(this, false, s, this.root.useSVG11AccessKeys, this.root.useSVG12AccessKeys);
        }
        catch (ParseException ex) {
            throw this.createException("attribute.malformed", new Object[] { null, "end" });
        }
    }
    
    protected void parseMin(final String s) {
        if (s.equals("media")) {
            this.min = 0.0f;
            this.minMedia = true;
        }
        else {
            this.minMedia = false;
            if (s.length() == 0) {
                this.min = 0.0f;
            }
            else {
                try {
                    this.min = this.parseClockValue(s, false);
                }
                catch (ParseException ex) {
                    this.min = 0.0f;
                }
                if (this.min < 0.0f) {
                    this.min = 0.0f;
                }
            }
        }
    }
    
    protected void parseMax(final String s) {
        if (s.equals("media")) {
            this.max = Float.POSITIVE_INFINITY;
            this.maxMedia = true;
        }
        else {
            this.maxMedia = false;
            if (s.length() == 0 || s.equals("indefinite")) {
                this.max = Float.POSITIVE_INFINITY;
            }
            else {
                try {
                    this.max = this.parseClockValue(s, false);
                }
                catch (ParseException ex) {
                    this.max = Float.POSITIVE_INFINITY;
                }
                if (this.max < 0.0f) {
                    this.max = 0.0f;
                }
            }
        }
    }
    
    protected void parseRepeatCount(final String s) {
        if (s.length() == 0) {
            this.repeatCount = Float.NaN;
        }
        else if (s.equals("indefinite")) {
            this.repeatCount = Float.POSITIVE_INFINITY;
        }
        else {
            try {
                this.repeatCount = Float.parseFloat(s);
                if (this.repeatCount > 0.0f) {
                    return;
                }
            }
            catch (NumberFormatException ex) {
                throw this.createException("attribute.malformed", new Object[] { null, "repeatCount" });
            }
        }
    }
    
    protected void parseRepeatDur(final String s) {
        try {
            if (s.length() == 0) {
                this.repeatDur = Float.NaN;
            }
            else if (s.equals("indefinite")) {
                this.repeatDur = Float.POSITIVE_INFINITY;
            }
            else {
                this.repeatDur = this.parseClockValue(s, false);
            }
        }
        catch (ParseException ex) {
            throw this.createException("attribute.malformed", new Object[] { null, "repeatDur" });
        }
    }
    
    protected void parseFill(final String s) {
        if (s.length() == 0 || s.equals("remove")) {
            this.fillMode = 0;
        }
        else {
            if (!s.equals("freeze")) {
                throw this.createException("attribute.malformed", new Object[] { null, "fill" });
            }
            this.fillMode = 1;
        }
    }
    
    protected void parseRestart(final String s) {
        if (s.length() == 0 || s.equals("always")) {
            this.restartMode = 0;
        }
        else if (s.equals("whenNotActive")) {
            this.restartMode = 1;
        }
        else {
            if (!s.equals("never")) {
                throw this.createException("attribute.malformed", new Object[] { null, "restart" });
            }
            this.restartMode = 2;
        }
    }
    
    public void initialize() {
        for (int i = 0; i < this.beginTimes.length; ++i) {
            this.beginTimes[i].initialize();
        }
        for (int j = 0; j < this.endTimes.length; ++j) {
            this.endTimes[j].initialize();
        }
    }
    
    public void deinitialize() {
        for (int i = 0; i < this.beginTimes.length; ++i) {
            this.beginTimes[i].deinitialize();
        }
        for (int j = 0; j < this.endTimes.length; ++j) {
            this.endTimes[j].deinitialize();
        }
    }
    
    public void beginElement() {
        this.beginElement(0.0f);
    }
    
    public void beginElement(final float n) {
        this.addInstanceTime(new InstanceTime(null, this.root.convertWallclockTime(Calendar.getInstance()) + n, true), true);
    }
    
    public void endElement() {
        this.endElement(0.0f);
    }
    
    public void endElement(final float n) {
        this.addInstanceTime(new InstanceTime(null, this.root.convertWallclockTime(Calendar.getInstance()) + n, true), false);
    }
    
    public float getLastSampleTime() {
        return this.lastSampleTime;
    }
    
    public float getCurrentBeginTime() {
        final float begin;
        if (this.currentInterval == null || (begin = this.currentInterval.getBegin()) < this.lastSampleTime) {
            return Float.NaN;
        }
        return begin;
    }
    
    public boolean canBegin() {
        return this.currentInterval == null || (this.isActive && this.restartMode != 2);
    }
    
    public boolean canEnd() {
        return this.isActive;
    }
    
    public float getHyperlinkBeginTime() {
        if (this.isActive) {
            return this.currentInterval.getBegin();
        }
        if (!this.beginInstanceTimes.isEmpty()) {
            return this.beginInstanceTimes.get(0).getTime();
        }
        return Float.NaN;
    }
    
    protected void fireTimeEvent(final String s, final float n, final int n2) {
        final Calendar calendar = (Calendar)this.root.getDocumentBeginTime().clone();
        calendar.add(14, (int)Math.round(n * 1000.0));
        this.fireTimeEvent(s, calendar, n2);
    }
    
    void eventOccurred(final TimingSpecifier timingSpecifier, final Event event) {
        HashSet<TimingSpecifier> set = this.handledEvents.get(event);
        if (set == null) {
            set = new HashSet<TimingSpecifier>();
            this.handledEvents.put(event, set);
        }
        set.add(timingSpecifier);
        this.root.currentIntervalWillUpdate();
    }
    
    protected abstract void fireTimeEvent(final String p0, final Calendar p1, final int p2);
    
    protected abstract void toActive(final float p0);
    
    protected abstract void toInactive(final boolean p0, final boolean p1);
    
    protected abstract void removeFill();
    
    protected abstract void sampledAt(final float p0, final float p1, final int p2);
    
    protected abstract void sampledLastValue(final int p0);
    
    protected abstract TimedElement getTimedElementById(final String p0);
    
    protected abstract EventTarget getEventTargetById(final String p0);
    
    protected abstract EventTarget getRootEventTarget();
    
    public abstract Element getElement();
    
    protected abstract EventTarget getAnimationEventTarget();
    
    public abstract boolean isBefore(final TimedElement p0);
    
    protected abstract boolean isConstantAnimation();
    
    public AnimationException createException(final String s, final Object[] array) {
        final Element element = this.getElement();
        if (element != null) {
            array[0] = element.getNodeName();
        }
        return new AnimationException(this, s, array);
    }
    
    public static void setLocale(final Locale locale) {
        TimedElement.localizableSupport.setLocale(locale);
    }
    
    public static Locale getLocale() {
        return TimedElement.localizableSupport.getLocale();
    }
    
    public static String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return TimedElement.localizableSupport.formatMessage(s, array);
    }
    
    public static String toString(final float n) {
        if (Float.isNaN(n)) {
            return "UNRESOLVED";
        }
        if (n == Float.POSITIVE_INFINITY) {
            return "INDEFINITE";
        }
        return Float.toString(n);
    }
    
    static {
        TimedElement.localizableSupport = new LocalizableSupport("org.apache.batik.anim.resources.Messages", TimedElement.class.getClassLoader());
    }
    
    class Handler implements ClockHandler
    {
        protected float v;
        
        Handler() {
            this.v = 0.0f;
        }
        
        public void clockValue(final float v) {
            this.v = v;
        }
    }
}
