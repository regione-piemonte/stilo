// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.anim;

import org.apache.batik.anim.timing.TimegraphListener;
import org.apache.batik.anim.timing.TimedElement;
import org.apache.batik.anim.values.AnimatableValue;
import java.util.Iterator;
import org.apache.batik.dom.anim.AnimationTargetListener;
import org.apache.batik.util.DoublyIndexedTable;
import org.apache.batik.dom.anim.AnimationTarget;
import java.util.Map;
import java.util.HashMap;
import org.apache.batik.anim.timing.TimedDocumentRoot;
import org.w3c.dom.Document;

public abstract class AnimationEngine
{
    public static final short ANIM_TYPE_XML = 0;
    public static final short ANIM_TYPE_CSS = 1;
    public static final short ANIM_TYPE_OTHER = 2;
    protected Document document;
    protected TimedDocumentRoot timedDocumentRoot;
    protected long pauseTime;
    protected HashMap targets;
    protected HashMap animations;
    protected Listener targetListener;
    protected static final Map.Entry[] MAP_ENTRY_ARRAY;
    
    public AnimationEngine(final Document document) {
        this.targets = new HashMap();
        this.animations = new HashMap();
        this.targetListener = new Listener();
        this.document = document;
        this.timedDocumentRoot = this.createDocumentRoot();
    }
    
    public void dispose() {
        for (final Map.Entry<AnimationTarget, V> entry : this.targets.entrySet()) {
            final AnimationTarget animationTarget = entry.getKey();
            final TargetInfo targetInfo = (TargetInfo)entry.getValue();
            for (final DoublyIndexedTable.Entry entry2 : targetInfo.xmlAnimations) {
                final String s = (String)entry2.getKey1();
                final String s2 = (String)entry2.getKey2();
                if (((Sandwich)entry2.getValue()).listenerRegistered) {
                    animationTarget.removeTargetListener(s, s2, false, this.targetListener);
                }
            }
            for (final Map.Entry<String, V> entry3 : targetInfo.cssAnimations.entrySet()) {
                final String s3 = entry3.getKey();
                if (entry3.getValue().listenerRegistered) {
                    animationTarget.removeTargetListener(null, s3, true, this.targetListener);
                }
            }
        }
    }
    
    public void pause() {
        if (this.pauseTime == 0L) {
            this.pauseTime = System.currentTimeMillis();
        }
    }
    
    public void unpause() {
        if (this.pauseTime != 0L) {
            this.timedDocumentRoot.getDocumentBeginTime().add(14, (int)(System.currentTimeMillis() - this.pauseTime));
            this.pauseTime = 0L;
        }
    }
    
    public boolean isPaused() {
        return this.pauseTime != 0L;
    }
    
    public float getCurrentTime() {
        return this.timedDocumentRoot.getCurrentTime();
    }
    
    public float setCurrentTime(final float n) {
        final boolean b = this.pauseTime != 0L;
        this.unpause();
        this.timedDocumentRoot.getDocumentBeginTime().add(14, (int)((this.timedDocumentRoot.convertEpochTime(System.currentTimeMillis()) - n) * 1000.0f));
        if (b) {
            this.pause();
        }
        return this.tick(n, true);
    }
    
    public void addAnimation(final AnimationTarget target, final short type, final String attributeNamespaceURI, final String attributeLocalName, final AbstractAnimation abstractAnimation) {
        this.timedDocumentRoot.addChild(abstractAnimation.getTimedElement());
        final AnimationInfo animationInfo = this.getAnimationInfo(abstractAnimation);
        animationInfo.type = type;
        animationInfo.attributeNamespaceURI = attributeNamespaceURI;
        animationInfo.attributeLocalName = attributeLocalName;
        animationInfo.target = target;
        this.animations.put(abstractAnimation, animationInfo);
        final Sandwich sandwich = this.getSandwich(target, type, attributeNamespaceURI, attributeLocalName);
        if (sandwich.animation == null) {
            abstractAnimation.lowerAnimation = null;
            abstractAnimation.higherAnimation = null;
        }
        else {
            sandwich.animation.higherAnimation = abstractAnimation;
            abstractAnimation.lowerAnimation = sandwich.animation;
            abstractAnimation.higherAnimation = null;
        }
        sandwich.animation = abstractAnimation;
        if (abstractAnimation.lowerAnimation == null) {
            sandwich.lowestAnimation = abstractAnimation;
        }
    }
    
    public void removeAnimation(final AbstractAnimation abstractAnimation) {
        this.timedDocumentRoot.removeChild(abstractAnimation.getTimedElement());
        final AbstractAnimation higherAnimation = abstractAnimation.higherAnimation;
        if (higherAnimation != null) {
            higherAnimation.markDirty();
        }
        this.moveToBottom(abstractAnimation);
        if (abstractAnimation.higherAnimation != null) {
            abstractAnimation.higherAnimation.lowerAnimation = null;
        }
        final AnimationInfo animationInfo = this.getAnimationInfo(abstractAnimation);
        final Sandwich sandwich = this.getSandwich(animationInfo.target, animationInfo.type, animationInfo.attributeNamespaceURI, animationInfo.attributeLocalName);
        if (sandwich.animation == abstractAnimation) {
            sandwich.animation = null;
            sandwich.lowestAnimation = null;
            sandwich.shouldUpdate = true;
        }
    }
    
    protected Sandwich getSandwich(final AnimationTarget animationTarget, final short n, final String s, final String s2) {
        final TargetInfo targetInfo = this.getTargetInfo(animationTarget);
        Sandwich sandwich;
        if (n == 0) {
            sandwich = (Sandwich)targetInfo.xmlAnimations.get(s, s2);
            if (sandwich == null) {
                sandwich = new Sandwich();
                targetInfo.xmlAnimations.put(s, s2, sandwich);
            }
        }
        else if (n == 1) {
            sandwich = targetInfo.cssAnimations.get(s2);
            if (sandwich == null) {
                sandwich = new Sandwich();
                targetInfo.cssAnimations.put(s2, sandwich);
            }
        }
        else {
            sandwich = targetInfo.otherAnimations.get(s2);
            if (sandwich == null) {
                sandwich = new Sandwich();
                targetInfo.otherAnimations.put(s2, sandwich);
            }
        }
        return sandwich;
    }
    
    protected TargetInfo getTargetInfo(final AnimationTarget animationTarget) {
        TargetInfo value = this.targets.get(animationTarget);
        if (value == null) {
            value = new TargetInfo();
            this.targets.put(animationTarget, value);
        }
        return value;
    }
    
    protected AnimationInfo getAnimationInfo(final AbstractAnimation abstractAnimation) {
        AnimationInfo value = this.animations.get(abstractAnimation);
        if (value == null) {
            value = new AnimationInfo();
            this.animations.put(abstractAnimation, value);
        }
        return value;
    }
    
    protected float tick(final float n, final boolean b) {
        final float seekTo = this.timedDocumentRoot.seekTo(n, b);
        final Map.Entry[] array = (Map.Entry[])this.targets.entrySet().toArray(AnimationEngine.MAP_ENTRY_ARRAY);
        for (int i = 0; i < array.length; ++i) {
            final Map.Entry entry = array[i];
            final AnimationTarget animationTarget = entry.getKey();
            final TargetInfo targetInfo = entry.getValue();
            for (final DoublyIndexedTable.Entry entry2 : targetInfo.xmlAnimations) {
                final String s = (String)entry2.getKey1();
                final String s2 = (String)entry2.getKey2();
                final Sandwich sandwich = (Sandwich)entry2.getValue();
                if (sandwich.shouldUpdate || (sandwich.animation != null && sandwich.animation.isDirty)) {
                    AnimatableValue composedValue = null;
                    boolean usesUnderlyingValue = false;
                    final AbstractAnimation animation = sandwich.animation;
                    if (animation != null) {
                        composedValue = animation.getComposedValue();
                        usesUnderlyingValue = sandwich.lowestAnimation.usesUnderlyingValue();
                        animation.isDirty = false;
                    }
                    if (usesUnderlyingValue && !sandwich.listenerRegistered) {
                        animationTarget.addTargetListener(s, s2, false, this.targetListener);
                        sandwich.listenerRegistered = true;
                    }
                    else if (!usesUnderlyingValue && sandwich.listenerRegistered) {
                        animationTarget.removeTargetListener(s, s2, false, this.targetListener);
                        sandwich.listenerRegistered = false;
                    }
                    animationTarget.updateAttributeValue(s, s2, composedValue);
                    sandwich.shouldUpdate = false;
                }
            }
            for (final Map.Entry<String, V> entry3 : targetInfo.cssAnimations.entrySet()) {
                final String s3 = entry3.getKey();
                final Sandwich sandwich2 = (Sandwich)entry3.getValue();
                if (sandwich2.shouldUpdate || (sandwich2.animation != null && sandwich2.animation.isDirty)) {
                    AnimatableValue composedValue2 = null;
                    boolean usesUnderlyingValue2 = false;
                    final AbstractAnimation animation2 = sandwich2.animation;
                    if (animation2 != null) {
                        composedValue2 = animation2.getComposedValue();
                        usesUnderlyingValue2 = sandwich2.lowestAnimation.usesUnderlyingValue();
                        animation2.isDirty = false;
                    }
                    if (usesUnderlyingValue2 && !sandwich2.listenerRegistered) {
                        animationTarget.addTargetListener(null, s3, true, this.targetListener);
                        sandwich2.listenerRegistered = true;
                    }
                    else if (!usesUnderlyingValue2 && sandwich2.listenerRegistered) {
                        animationTarget.removeTargetListener(null, s3, true, this.targetListener);
                        sandwich2.listenerRegistered = false;
                    }
                    if (usesUnderlyingValue2) {
                        animationTarget.updatePropertyValue(s3, null);
                    }
                    if (!usesUnderlyingValue2 || composedValue2 != null) {
                        animationTarget.updatePropertyValue(s3, composedValue2);
                    }
                    sandwich2.shouldUpdate = false;
                }
            }
            for (final Map.Entry<String, V> entry4 : targetInfo.otherAnimations.entrySet()) {
                final String s4 = entry4.getKey();
                final Sandwich sandwich3 = (Sandwich)entry4.getValue();
                if (sandwich3.shouldUpdate || sandwich3.animation.isDirty) {
                    AnimatableValue composedValue3 = null;
                    final AbstractAnimation animation3 = sandwich3.animation;
                    if (animation3 != null) {
                        composedValue3 = sandwich3.animation.getComposedValue();
                        animation3.isDirty = false;
                    }
                    animationTarget.updateOtherValue(s4, composedValue3);
                    sandwich3.shouldUpdate = false;
                }
            }
        }
        return seekTo;
    }
    
    public void toActive(final AbstractAnimation abstractAnimation, final float beginTime) {
        this.moveToTop(abstractAnimation);
        abstractAnimation.isActive = true;
        abstractAnimation.beginTime = beginTime;
        abstractAnimation.isFrozen = false;
        this.pushDown(abstractAnimation);
        abstractAnimation.markDirty();
    }
    
    protected void pushDown(final AbstractAnimation lowestAnimation) {
        final TimedElement timedElement = lowestAnimation.getTimedElement();
        AbstractAnimation animation = null;
        int n = 0;
        while (lowestAnimation.lowerAnimation != null && (lowestAnimation.lowerAnimation.isActive || lowestAnimation.lowerAnimation.isFrozen) && (lowestAnimation.lowerAnimation.beginTime > lowestAnimation.beginTime || (lowestAnimation.lowerAnimation.beginTime == lowestAnimation.beginTime && timedElement.isBefore(lowestAnimation.lowerAnimation.getTimedElement())))) {
            final AbstractAnimation higherAnimation = lowestAnimation.higherAnimation;
            final AbstractAnimation lowerAnimation = lowestAnimation.lowerAnimation;
            final AbstractAnimation lowerAnimation2 = lowerAnimation.lowerAnimation;
            if (higherAnimation != null) {
                higherAnimation.lowerAnimation = lowerAnimation;
            }
            if (lowerAnimation2 != null) {
                lowerAnimation2.higherAnimation = lowestAnimation;
            }
            lowerAnimation.lowerAnimation = lowestAnimation;
            lowerAnimation.higherAnimation = higherAnimation;
            lowestAnimation.lowerAnimation = lowerAnimation2;
            lowestAnimation.higherAnimation = lowerAnimation;
            if (n == 0) {
                animation = lowerAnimation;
                n = 1;
            }
        }
        if (n != 0) {
            final AnimationInfo animationInfo = this.getAnimationInfo(lowestAnimation);
            final Sandwich sandwich = this.getSandwich(animationInfo.target, animationInfo.type, animationInfo.attributeNamespaceURI, animationInfo.attributeLocalName);
            if (sandwich.animation == lowestAnimation) {
                sandwich.animation = animation;
            }
            if (lowestAnimation.lowerAnimation == null) {
                sandwich.lowestAnimation = lowestAnimation;
            }
        }
    }
    
    public void toInactive(final AbstractAnimation abstractAnimation, final boolean isFrozen) {
        abstractAnimation.isActive = false;
        abstractAnimation.isFrozen = isFrozen;
        abstractAnimation.beginTime = Float.NEGATIVE_INFINITY;
        abstractAnimation.markDirty();
        if (!isFrozen) {
            abstractAnimation.value = null;
            this.moveToBottom(abstractAnimation);
        }
        else {
            this.pushDown(abstractAnimation);
        }
    }
    
    public void removeFill(final AbstractAnimation abstractAnimation) {
        abstractAnimation.isActive = false;
        abstractAnimation.isFrozen = false;
        abstractAnimation.value = null;
        abstractAnimation.markDirty();
        this.moveToBottom(abstractAnimation);
    }
    
    protected void moveToTop(final AbstractAnimation abstractAnimation) {
        final AnimationInfo animationInfo = this.getAnimationInfo(abstractAnimation);
        final Sandwich sandwich = this.getSandwich(animationInfo.target, animationInfo.type, animationInfo.attributeNamespaceURI, animationInfo.attributeLocalName);
        sandwich.shouldUpdate = true;
        if (abstractAnimation.higherAnimation == null) {
            return;
        }
        if (abstractAnimation.lowerAnimation == null) {
            sandwich.lowestAnimation = abstractAnimation.higherAnimation;
        }
        else {
            abstractAnimation.lowerAnimation.higherAnimation = abstractAnimation.higherAnimation;
        }
        abstractAnimation.higherAnimation.lowerAnimation = abstractAnimation.lowerAnimation;
        if (sandwich.animation != null) {
            sandwich.animation.higherAnimation = abstractAnimation;
        }
        abstractAnimation.lowerAnimation = sandwich.animation;
        abstractAnimation.higherAnimation = null;
        sandwich.animation = abstractAnimation;
    }
    
    protected void moveToBottom(final AbstractAnimation abstractAnimation) {
        if (abstractAnimation.lowerAnimation == null) {
            return;
        }
        final AnimationInfo animationInfo = this.getAnimationInfo(abstractAnimation);
        final Sandwich sandwich = this.getSandwich(animationInfo.target, animationInfo.type, animationInfo.attributeNamespaceURI, animationInfo.attributeLocalName);
        final AbstractAnimation lowerAnimation = abstractAnimation.lowerAnimation;
        lowerAnimation.markDirty();
        abstractAnimation.lowerAnimation.higherAnimation = abstractAnimation.higherAnimation;
        if (abstractAnimation.higherAnimation != null) {
            abstractAnimation.higherAnimation.lowerAnimation = abstractAnimation.lowerAnimation;
        }
        else {
            sandwich.animation = lowerAnimation;
            sandwich.shouldUpdate = true;
        }
        sandwich.lowestAnimation.lowerAnimation = abstractAnimation;
        abstractAnimation.higherAnimation = sandwich.lowestAnimation;
        abstractAnimation.lowerAnimation = null;
        sandwich.lowestAnimation = abstractAnimation;
        if (sandwich.animation.isDirty) {
            sandwich.shouldUpdate = true;
        }
    }
    
    public void addTimegraphListener(final TimegraphListener timegraphListener) {
        this.timedDocumentRoot.addTimegraphListener(timegraphListener);
    }
    
    public void removeTimegraphListener(final TimegraphListener timegraphListener) {
        this.timedDocumentRoot.removeTimegraphListener(timegraphListener);
    }
    
    public void sampledAt(final AbstractAnimation abstractAnimation, final float n, final float n2, final int n3) {
        abstractAnimation.sampledAt(n, n2, n3);
    }
    
    public void sampledLastValue(final AbstractAnimation abstractAnimation, final int n) {
        abstractAnimation.sampledLastValue(n);
    }
    
    protected abstract TimedDocumentRoot createDocumentRoot();
    
    static {
        MAP_ENTRY_ARRAY = new Map.Entry[0];
    }
    
    protected static class AnimationInfo
    {
        public AnimationTarget target;
        public short type;
        public String attributeNamespaceURI;
        public String attributeLocalName;
    }
    
    protected static class Sandwich
    {
        public AbstractAnimation animation;
        public AbstractAnimation lowestAnimation;
        public boolean shouldUpdate;
        public boolean listenerRegistered;
    }
    
    protected static class TargetInfo
    {
        public DoublyIndexedTable xmlAnimations;
        public HashMap cssAnimations;
        public HashMap otherAnimations;
        
        protected TargetInfo() {
            this.xmlAnimations = new DoublyIndexedTable();
            this.cssAnimations = new HashMap();
            this.otherAnimations = new HashMap();
        }
    }
    
    protected class Listener implements AnimationTargetListener
    {
        public void baseValueChanged(final AnimationTarget animationTarget, final String s, final String s2, final boolean b) {
            final Sandwich sandwich = AnimationEngine.this.getSandwich(animationTarget, (short)(b ? 1 : 0), s, s2);
            sandwich.shouldUpdate = true;
            AbstractAnimation abstractAnimation;
            for (abstractAnimation = sandwich.animation; abstractAnimation.lowerAnimation != null; abstractAnimation = abstractAnimation.lowerAnimation) {}
            abstractAnimation.markDirty();
        }
    }
}
