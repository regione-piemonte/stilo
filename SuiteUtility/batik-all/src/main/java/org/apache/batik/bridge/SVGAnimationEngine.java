// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.apache.batik.anim.timing.TimedElement;
import org.w3c.dom.events.EventTarget;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.lang.ref.WeakReference;
import org.apache.batik.anim.values.AnimatableBooleanValue;
import org.apache.batik.anim.values.AnimatableNumberValue;
import org.apache.batik.anim.values.AnimatableNumberOrPercentageValue;
import org.apache.batik.anim.values.AnimatablePreserveAspectRatioValue;
import org.apache.batik.parser.PreserveAspectRatioHandler;
import org.apache.batik.parser.DefaultPreserveAspectRatioHandler;
import org.apache.batik.parser.PreserveAspectRatioParser;
import org.apache.batik.anim.values.AnimatableIntegerValue;
import org.apache.batik.anim.values.AnimatableLengthValue;
import org.apache.batik.parser.DefaultLengthHandler;
import org.apache.batik.parser.LengthHandler;
import org.apache.batik.parser.LengthParser;
import org.apache.batik.anim.values.AnimatableLengthListValue;
import org.apache.batik.parser.LengthListHandler;
import org.apache.batik.parser.LengthArrayProducer;
import org.apache.batik.parser.LengthListParser;
import org.apache.batik.anim.values.AnimatableNumberListValue;
import org.apache.batik.anim.values.AnimatableRectValue;
import org.apache.batik.parser.NumberListHandler;
import org.apache.batik.parser.NumberListParser;
import org.apache.batik.anim.values.AnimatablePointListValue;
import org.apache.batik.parser.PointsHandler;
import org.apache.batik.parser.FloatArrayProducer;
import org.apache.batik.parser.PointsParser;
import org.apache.batik.parser.ParseException;
import org.apache.batik.anim.values.AnimatablePathDataValue;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathArrayProducer;
import org.apache.batik.parser.PathParser;
import org.apache.batik.anim.values.AnimatableLengthOrIdentValue;
import org.apache.batik.anim.values.AnimatableNumberOrIdentValue;
import org.apache.batik.anim.values.AnimatableAngleValue;
import org.apache.batik.css.engine.value.FloatValue;
import org.apache.batik.anim.values.AnimatableAngleOrIdentValue;
import org.apache.batik.css.engine.value.StringValue;
import java.awt.Paint;
import org.apache.batik.anim.values.AnimatableColorValue;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.anim.values.AnimatablePaintValue;
import java.awt.Color;
import org.apache.batik.anim.values.AnimatableStringValue;
import java.util.HashSet;
import org.apache.batik.anim.AnimationException;
import java.util.Date;
import java.util.Calendar;
import org.apache.batik.anim.timing.TimedDocumentRoot;
import org.apache.batik.util.RunnableQueue;
import org.apache.batik.css.engine.value.Value;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.dom.svg.SVGStylableElement;
import org.apache.batik.dom.svg.SVGOMElement;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.w3c.dom.Document;
import java.util.Set;
import org.apache.batik.css.engine.StyleMap;
import java.util.LinkedList;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.anim.AnimationEngine;

public class SVGAnimationEngine extends AnimationEngine
{
    protected BridgeContext ctx;
    protected CSSEngine cssEngine;
    protected boolean started;
    protected AnimationTickRunnable animationTickRunnable;
    protected UncomputedAnimatableStringValueFactory uncomputedAnimatableStringValueFactory;
    protected AnimatableLengthOrIdentFactory animatableLengthOrIdentFactory;
    protected AnimatableNumberOrIdentFactory animatableNumberOrIdentFactory;
    protected Factory[] factories;
    protected boolean isSVG12;
    protected LinkedList initialBridges;
    protected StyleMap dummyStyleMap;
    protected AnimationThread animationThread;
    protected int animationLimitingMode;
    protected float animationLimitingAmount;
    protected static final Set animationEventNames11;
    protected static final Set animationEventNames12;
    
    public SVGAnimationEngine(final Document document, final BridgeContext ctx) {
        super(document);
        this.uncomputedAnimatableStringValueFactory = new UncomputedAnimatableStringValueFactory();
        this.animatableLengthOrIdentFactory = new AnimatableLengthOrIdentFactory();
        this.animatableNumberOrIdentFactory = new AnimatableNumberOrIdentFactory(false);
        this.factories = new Factory[] { null, new AnimatableIntegerValueFactory(), new AnimatableNumberValueFactory(), new AnimatableLengthValueFactory(), null, new AnimatableAngleValueFactory(), new AnimatableColorValueFactory(), new AnimatablePaintValueFactory(), null, null, this.uncomputedAnimatableStringValueFactory, null, null, new AnimatableNumberListValueFactory(), new AnimatableLengthListValueFactory(), this.uncomputedAnimatableStringValueFactory, this.uncomputedAnimatableStringValueFactory, this.animatableLengthOrIdentFactory, this.uncomputedAnimatableStringValueFactory, this.uncomputedAnimatableStringValueFactory, this.uncomputedAnimatableStringValueFactory, this.uncomputedAnimatableStringValueFactory, new AnimatablePathDataFactory(), this.uncomputedAnimatableStringValueFactory, null, this.animatableNumberOrIdentFactory, this.uncomputedAnimatableStringValueFactory, null, new AnimatableNumberOrIdentFactory(true), new AnimatableAngleOrIdentFactory(), null, new AnimatablePointListValueFactory(), new AnimatablePreserveAspectRatioValueFactory(), null, this.uncomputedAnimatableStringValueFactory, null, null, null, null, this.animatableLengthOrIdentFactory, this.animatableLengthOrIdentFactory, this.animatableLengthOrIdentFactory, this.animatableLengthOrIdentFactory, this.animatableLengthOrIdentFactory, this.animatableNumberOrIdentFactory, null, null, new AnimatableNumberOrPercentageValueFactory(), null, new AnimatableBooleanValueFactory(), new AnimatableRectValueFactory() };
        this.initialBridges = new LinkedList();
        this.ctx = ctx;
        final SVGOMDocument svgomDocument = (SVGOMDocument)document;
        this.cssEngine = svgomDocument.getCSSEngine();
        this.dummyStyleMap = new StyleMap(this.cssEngine.getNumberOfProperties());
        this.isSVG12 = svgomDocument.isSVG12();
    }
    
    public void dispose() {
        synchronized (this) {
            this.pause();
            super.dispose();
        }
    }
    
    public void addInitialBridge(final SVGAnimationElementBridge e) {
        if (this.initialBridges != null) {
            this.initialBridges.add(e);
        }
    }
    
    public boolean hasStarted() {
        return this.started;
    }
    
    public AnimatableValue parseAnimatableValue(final Element element, final AnimationTarget animationTarget, final String str, final String str2, final boolean b, final String s) {
        final SVGOMElement svgomElement = (SVGOMElement)animationTarget.getElement();
        int n;
        if (b) {
            n = svgomElement.getPropertyType(str2);
        }
        else {
            n = svgomElement.getAttributeType(str, str2);
        }
        if (this.factories[n] == null) {
            throw new BridgeException(this.ctx, element, "attribute.not.animatable", new Object[] { animationTarget.getElement().getNodeName(), (str == null) ? str2 : ('{' + str + '}' + str2) });
        }
        return this.factories[n].createValue(animationTarget, str, str2, b, s);
    }
    
    public AnimatableValue getUnderlyingCSSValue(final Element element, final AnimationTarget animationTarget, final String s) {
        final ValueManager[] valueManagers = this.cssEngine.getValueManagers();
        final int propertyIndex = this.cssEngine.getPropertyIndex(s);
        if (propertyIndex == -1) {
            return null;
        }
        final int propertyType = valueManagers[propertyIndex].getPropertyType();
        if (this.factories[propertyType] == null) {
            throw new BridgeException(this.ctx, element, "attribute.not.animatable", new Object[] { animationTarget.getElement().getNodeName(), s });
        }
        final SVGStylableElement svgStylableElement = (SVGStylableElement)animationTarget.getElement();
        final CSSStyleDeclaration overrideStyle = svgStylableElement.getOverrideStyle();
        final String propertyValue = overrideStyle.getPropertyValue(s);
        if (propertyValue != null) {
            overrideStyle.removeProperty(s);
        }
        final Value computedStyle = this.cssEngine.getComputedStyle(svgStylableElement, null, propertyIndex);
        if (propertyValue != null && !propertyValue.equals("")) {
            overrideStyle.setProperty(s, propertyValue, null);
        }
        return this.factories[propertyType].createValue(animationTarget, s, computedStyle);
    }
    
    public void pause() {
        super.pause();
        final UpdateManager updateManager = this.ctx.getUpdateManager();
        if (updateManager != null) {
            updateManager.getUpdateRunnableQueue().setIdleRunnable(null);
        }
    }
    
    public void unpause() {
        super.unpause();
        final UpdateManager updateManager = this.ctx.getUpdateManager();
        if (updateManager != null) {
            updateManager.getUpdateRunnableQueue().setIdleRunnable(this.animationTickRunnable);
        }
    }
    
    public float getCurrentTime() {
        final boolean b = this.pauseTime != 0L;
        this.unpause();
        final float currentTime = this.timedDocumentRoot.getCurrentTime();
        if (b) {
            this.pause();
        }
        return currentTime;
    }
    
    public float setCurrentTime(final float currentTime) {
        final float setCurrentTime = super.setCurrentTime(currentTime);
        if (this.animationTickRunnable != null) {
            this.animationTickRunnable.resume();
        }
        return setCurrentTime;
    }
    
    protected TimedDocumentRoot createDocumentRoot() {
        return new AnimationRoot();
    }
    
    public void start(final long date) {
        if (this.started) {
            return;
        }
        this.started = true;
        try {
            try {
                final Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(date));
                this.timedDocumentRoot.resetDocument(instance);
                final Object[] array = this.initialBridges.toArray();
                this.initialBridges = null;
                for (int i = 0; i < array.length; ++i) {
                    ((SVGAnimationElementBridge)array[i]).initializeAnimation();
                }
                for (int j = 0; j < array.length; ++j) {
                    ((SVGAnimationElementBridge)array[j]).initializeTimedElement();
                }
                final UpdateManager updateManager = this.ctx.getUpdateManager();
                if (updateManager != null) {
                    final RunnableQueue updateRunnableQueue = updateManager.getUpdateRunnableQueue();
                    updateRunnableQueue.setIdleRunnable(this.animationTickRunnable = new AnimationTickRunnable(updateRunnableQueue, this));
                }
            }
            catch (AnimationException ex) {
                throw new BridgeException(this.ctx, ex.getElement().getElement(), ex.getMessage());
            }
        }
        catch (Exception ex2) {
            if (this.ctx.getUserAgent() == null) {
                ex2.printStackTrace();
            }
            else {
                this.ctx.getUserAgent().displayError(ex2);
            }
        }
    }
    
    public void setAnimationLimitingNone() {
        this.animationLimitingMode = 0;
    }
    
    public void setAnimationLimitingCPU(final float animationLimitingAmount) {
        this.animationLimitingMode = 1;
        this.animationLimitingAmount = animationLimitingAmount;
    }
    
    public void setAnimationLimitingFPS(final float animationLimitingAmount) {
        this.animationLimitingMode = 2;
        this.animationLimitingAmount = animationLimitingAmount;
    }
    
    static {
        animationEventNames11 = new HashSet();
        animationEventNames12 = new HashSet();
        final String[] array = { "click", "mousedown", "mouseup", "mouseover", "mousemove", "mouseout", "beginEvent", "endEvent" };
        final String[] array2 = { "DOMSubtreeModified", "DOMNodeInserted", "DOMNodeRemoved", "DOMNodeRemovedFromDocument", "DOMNodeInsertedIntoDocument", "DOMAttrModified", "DOMCharacterDataModified", "SVGLoad", "SVGUnload", "SVGAbort", "SVGError", "SVGResize", "SVGScroll", "repeatEvent" };
        final String[] array3 = { "load", "resize", "scroll", "zoom" };
        for (int i = 0; i < array.length; ++i) {
            SVGAnimationEngine.animationEventNames11.add(array[i]);
            SVGAnimationEngine.animationEventNames12.add(array[i]);
        }
        for (int j = 0; j < array2.length; ++j) {
            SVGAnimationEngine.animationEventNames11.add(array2[j]);
        }
        for (int k = 0; k < array3.length; ++k) {
            SVGAnimationEngine.animationEventNames12.add(array3[k]);
        }
    }
    
    protected class AnimatableStringValueFactory extends CSSValueFactory
    {
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return new AnimatableStringValue(animationTarget, value.getCssText());
        }
    }
    
    protected abstract class CSSValueFactory implements Factory
    {
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            return this.createValue(animationTarget, s2, this.createCSSValue(animationTarget, s2, s3));
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, Value computeValue) {
            computeValue = this.computeValue((CSSStylableElement)animationTarget.getElement(), s, computeValue);
            return this.createAnimatableValue(animationTarget, s, computeValue);
        }
        
        protected abstract AnimatableValue createAnimatableValue(final AnimationTarget p0, final String p1, final Value p2);
        
        protected Value createCSSValue(final AnimationTarget animationTarget, final String s, final String s2) {
            final CSSStylableElement cssStylableElement = (CSSStylableElement)animationTarget.getElement();
            return this.computeValue(cssStylableElement, s, SVGAnimationEngine.this.cssEngine.parsePropertyValue(cssStylableElement, s, s2));
        }
        
        protected Value computeValue(CSSStylableElement parentCSSStylableElement, final String s, Value computeValue) {
            final ValueManager[] valueManagers = SVGAnimationEngine.this.cssEngine.getValueManagers();
            final int propertyIndex = SVGAnimationEngine.this.cssEngine.getPropertyIndex(s);
            if (propertyIndex != -1) {
                if (computeValue.getCssValueType() == 0) {
                    parentCSSStylableElement = CSSEngine.getParentCSSStylableElement(parentCSSStylableElement);
                    if (parentCSSStylableElement != null) {
                        return SVGAnimationEngine.this.cssEngine.getComputedStyle(parentCSSStylableElement, null, propertyIndex);
                    }
                    return valueManagers[propertyIndex].getDefaultValue();
                }
                else {
                    computeValue = valueManagers[propertyIndex].computeValue(parentCSSStylableElement, null, SVGAnimationEngine.this.cssEngine, propertyIndex, SVGAnimationEngine.this.dummyStyleMap, computeValue);
                }
            }
            return computeValue;
        }
    }
    
    protected interface Factory
    {
        AnimatableValue createValue(final AnimationTarget p0, final String p1, final String p2, final boolean p3, final String p4);
        
        AnimatableValue createValue(final AnimationTarget p0, final String p1, final Value p2);
    }
    
    protected class AnimatablePaintValueFactory extends CSSValueFactory
    {
        protected AnimatablePaintValue createColorPaintValue(final AnimationTarget animationTarget, final Color color) {
            return AnimatablePaintValue.createColorPaintValue(animationTarget, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        }
        
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            Label_0270: {
                if (value.getCssValueType() == 1) {
                    switch (value.getPrimitiveType()) {
                        case 21: {
                            return AnimatablePaintValue.createNonePaintValue(animationTarget);
                        }
                        case 25: {
                            return this.createColorPaintValue(animationTarget, (Color)PaintServer.convertPaint(animationTarget.getElement(), null, value, 1.0f, SVGAnimationEngine.this.ctx));
                        }
                        case 20: {
                            return AnimatablePaintValue.createURIPaintValue(animationTarget, value.getStringValue());
                        }
                    }
                }
                else {
                    final Value item = value.item(0);
                    switch (item.getPrimitiveType()) {
                        case 25: {
                            return this.createColorPaintValue(animationTarget, (Color)PaintServer.convertPaint(animationTarget.getElement(), null, value, 1.0f, SVGAnimationEngine.this.ctx));
                        }
                        case 20: {
                            switch (value.item(1).getPrimitiveType()) {
                                case 21: {
                                    return AnimatablePaintValue.createURINonePaintValue(animationTarget, item.getStringValue());
                                }
                                case 25: {
                                    return this.createColorPaintValue(animationTarget, (Color)PaintServer.convertPaint(animationTarget.getElement(), null, value.item(1), 1.0f, SVGAnimationEngine.this.ctx));
                                }
                                default: {
                                    break Label_0270;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            return null;
        }
    }
    
    protected class AnimatableColorValueFactory extends CSSValueFactory
    {
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            final Paint convertPaint = PaintServer.convertPaint(animationTarget.getElement(), null, value, 1.0f, SVGAnimationEngine.this.ctx);
            if (convertPaint instanceof Color) {
                final Color color = (Color)convertPaint;
                return new AnimatableColorValue(animationTarget, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
            }
            return null;
        }
    }
    
    protected class AnimatableAngleOrIdentFactory extends CSSValueFactory
    {
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            if (value instanceof StringValue) {
                return new AnimatableAngleOrIdentValue(animationTarget, value.getStringValue());
            }
            final FloatValue floatValue = (FloatValue)value;
            short n = 0;
            switch (floatValue.getPrimitiveType()) {
                case 1:
                case 11: {
                    n = 2;
                    break;
                }
                case 12: {
                    n = 3;
                    break;
                }
                case 13: {
                    n = 4;
                    break;
                }
                default: {
                    return null;
                }
            }
            return new AnimatableAngleOrIdentValue(animationTarget, floatValue.getFloatValue(), n);
        }
    }
    
    protected class AnimatableAngleValueFactory extends CSSValueFactory
    {
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            final FloatValue floatValue = (FloatValue)value;
            short n = 0;
            switch (floatValue.getPrimitiveType()) {
                case 1:
                case 11: {
                    n = 2;
                    break;
                }
                case 12: {
                    n = 3;
                    break;
                }
                case 13: {
                    n = 4;
                    break;
                }
                default: {
                    return null;
                }
            }
            return new AnimatableAngleValue(animationTarget, floatValue.getFloatValue(), n);
        }
    }
    
    protected class AnimatableNumberOrIdentFactory extends CSSValueFactory
    {
        protected boolean numericIdents;
        
        public AnimatableNumberOrIdentFactory(final boolean numericIdents) {
            this.numericIdents = numericIdents;
        }
        
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            if (value instanceof StringValue) {
                return new AnimatableNumberOrIdentValue(animationTarget, value.getStringValue());
            }
            return new AnimatableNumberOrIdentValue(animationTarget, ((FloatValue)value).getFloatValue(), this.numericIdents);
        }
    }
    
    protected class AnimatableLengthOrIdentFactory extends CSSValueFactory
    {
        protected AnimatableValue createAnimatableValue(final AnimationTarget animationTarget, final String s, final Value value) {
            if (value instanceof StringValue) {
                return new AnimatableLengthOrIdentValue(animationTarget, value.getStringValue());
            }
            final short percentageInterpretation = animationTarget.getPercentageInterpretation(null, s, true);
            final FloatValue floatValue = (FloatValue)value;
            return new AnimatableLengthOrIdentValue(animationTarget, floatValue.getPrimitiveType(), floatValue.getFloatValue(), percentageInterpretation);
        }
    }
    
    protected class UncomputedAnimatableStringValueFactory implements Factory
    {
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            return new AnimatableStringValue(animationTarget, s3);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return new AnimatableStringValue(animationTarget, value.getCssText());
        }
    }
    
    protected class AnimatablePathDataFactory implements Factory
    {
        protected PathParser parser;
        protected PathArrayProducer producer;
        
        public AnimatablePathDataFactory() {
            this.parser = new PathParser();
            this.producer = new PathArrayProducer();
            this.parser.setPathHandler(this.producer);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            try {
                this.parser.parse(s3);
                return new AnimatablePathDataValue(animationTarget, this.producer.getPathCommands(), this.producer.getPathParameters());
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return null;
        }
    }
    
    protected class AnimatablePointListValueFactory implements Factory
    {
        protected PointsParser parser;
        protected FloatArrayProducer producer;
        
        public AnimatablePointListValueFactory() {
            this.parser = new PointsParser();
            this.producer = new FloatArrayProducer();
            this.parser.setPointsHandler(this.producer);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            try {
                this.parser.parse(s3);
                return new AnimatablePointListValue(animationTarget, this.producer.getFloatArray());
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return null;
        }
    }
    
    protected class AnimatableRectValueFactory implements Factory
    {
        protected NumberListParser parser;
        protected FloatArrayProducer producer;
        
        public AnimatableRectValueFactory() {
            this.parser = new NumberListParser();
            this.producer = new FloatArrayProducer();
            this.parser.setNumberListHandler(this.producer);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            try {
                this.parser.parse(s3);
                final float[] floatArray = this.producer.getFloatArray();
                if (floatArray.length != 4) {
                    return null;
                }
                return new AnimatableRectValue(animationTarget, floatArray[0], floatArray[1], floatArray[2], floatArray[3]);
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return null;
        }
    }
    
    protected class AnimatableNumberListValueFactory implements Factory
    {
        protected NumberListParser parser;
        protected FloatArrayProducer producer;
        
        public AnimatableNumberListValueFactory() {
            this.parser = new NumberListParser();
            this.producer = new FloatArrayProducer();
            this.parser.setNumberListHandler(this.producer);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            try {
                this.parser.parse(s3);
                return new AnimatableNumberListValue(animationTarget, this.producer.getFloatArray());
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return null;
        }
    }
    
    protected class AnimatableLengthListValueFactory implements Factory
    {
        protected LengthListParser parser;
        protected LengthArrayProducer producer;
        
        public AnimatableLengthListValueFactory() {
            this.parser = new LengthListParser();
            this.producer = new LengthArrayProducer();
            this.parser.setLengthListHandler(this.producer);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            try {
                final short percentageInterpretation = animationTarget.getPercentageInterpretation(s, s2, b);
                this.parser.parse(s3);
                return new AnimatableLengthListValue(animationTarget, this.producer.getLengthTypeArray(), this.producer.getLengthValueArray(), percentageInterpretation);
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return null;
        }
    }
    
    protected class AnimatableLengthValueFactory implements Factory
    {
        protected short type;
        protected float value;
        protected LengthParser parser;
        protected LengthHandler handler;
        
        public AnimatableLengthValueFactory() {
            this.parser = new LengthParser();
            this.handler = new DefaultLengthHandler() {
                private final /* synthetic */ AnimatableLengthValueFactory this$1 = this$1;
                
                public void startLength() throws ParseException {
                    this.this$1.type = 1;
                }
                
                public void lengthValue(final float value) throws ParseException {
                    this.this$1.value = value;
                }
                
                public void em() throws ParseException {
                    this.this$1.type = 3;
                }
                
                public void ex() throws ParseException {
                    this.this$1.type = 4;
                }
                
                public void in() throws ParseException {
                    this.this$1.type = 8;
                }
                
                public void cm() throws ParseException {
                    this.this$1.type = 6;
                }
                
                public void mm() throws ParseException {
                    this.this$1.type = 7;
                }
                
                public void pc() throws ParseException {
                    this.this$1.type = 10;
                }
                
                public void pt() throws ParseException {
                    this.this$1.type = 9;
                }
                
                public void px() throws ParseException {
                    this.this$1.type = 5;
                }
                
                public void percentage() throws ParseException {
                    this.this$1.type = 2;
                }
                
                public void endLength() throws ParseException {
                }
            };
            this.parser.setLengthHandler(this.handler);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            final short percentageInterpretation = animationTarget.getPercentageInterpretation(s, s2, b);
            try {
                this.parser.parse(s3);
                return new AnimatableLengthValue(animationTarget, this.type, this.value, percentageInterpretation);
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return new AnimatableIntegerValue(animationTarget, Math.round(value.getFloatValue()));
        }
    }
    
    protected class AnimatablePreserveAspectRatioValueFactory implements Factory
    {
        protected short align;
        protected short meetOrSlice;
        protected PreserveAspectRatioParser parser;
        protected DefaultPreserveAspectRatioHandler handler;
        
        public AnimatablePreserveAspectRatioValueFactory() {
            this.parser = new PreserveAspectRatioParser();
            this.handler = new DefaultPreserveAspectRatioHandler() {
                private final /* synthetic */ AnimatablePreserveAspectRatioValueFactory this$1 = this$1;
                
                public void startPreserveAspectRatio() throws ParseException {
                    this.this$1.align = 0;
                    this.this$1.meetOrSlice = 0;
                }
                
                public void none() throws ParseException {
                    this.this$1.align = 1;
                }
                
                public void xMaxYMax() throws ParseException {
                    this.this$1.align = 10;
                }
                
                public void xMaxYMid() throws ParseException {
                    this.this$1.align = 7;
                }
                
                public void xMaxYMin() throws ParseException {
                    this.this$1.align = 4;
                }
                
                public void xMidYMax() throws ParseException {
                    this.this$1.align = 9;
                }
                
                public void xMidYMid() throws ParseException {
                    this.this$1.align = 6;
                }
                
                public void xMidYMin() throws ParseException {
                    this.this$1.align = 3;
                }
                
                public void xMinYMax() throws ParseException {
                    this.this$1.align = 8;
                }
                
                public void xMinYMid() throws ParseException {
                    this.this$1.align = 5;
                }
                
                public void xMinYMin() throws ParseException {
                    this.this$1.align = 2;
                }
                
                public void meet() throws ParseException {
                    this.this$1.meetOrSlice = 1;
                }
                
                public void slice() throws ParseException {
                    this.this$1.meetOrSlice = 2;
                }
            };
            this.parser.setPreserveAspectRatioHandler(this.handler);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            try {
                this.parser.parse(s3);
                return new AnimatablePreserveAspectRatioValue(animationTarget, this.align, this.meetOrSlice);
            }
            catch (ParseException ex) {
                return null;
            }
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return null;
        }
    }
    
    protected class AnimatableNumberOrPercentageValueFactory implements Factory
    {
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            float n;
            boolean b2;
            if (s3.charAt(s3.length() - 1) == '%') {
                n = Float.parseFloat(s3.substring(0, s3.length() - 1));
                b2 = true;
            }
            else {
                n = Float.parseFloat(s3);
                b2 = false;
            }
            return new AnimatableNumberOrPercentageValue(animationTarget, n, b2);
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            switch (value.getPrimitiveType()) {
                case 2: {
                    return new AnimatableNumberOrPercentageValue(animationTarget, value.getFloatValue(), true);
                }
                case 1: {
                    return new AnimatableNumberOrPercentageValue(animationTarget, value.getFloatValue());
                }
                default: {
                    return null;
                }
            }
        }
    }
    
    protected class AnimatableNumberValueFactory implements Factory
    {
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            return new AnimatableNumberValue(animationTarget, Float.parseFloat(s3));
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return new AnimatableNumberValue(animationTarget, value.getFloatValue());
        }
    }
    
    protected class AnimatableIntegerValueFactory implements Factory
    {
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String s3) {
            return new AnimatableIntegerValue(animationTarget, Integer.parseInt(s3));
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return new AnimatableIntegerValue(animationTarget, Math.round(value.getFloatValue()));
        }
    }
    
    protected class AnimatableBooleanValueFactory implements Factory
    {
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final String s2, final boolean b, final String anObject) {
            return new AnimatableBooleanValue(animationTarget, "true".equals(anObject));
        }
        
        public AnimatableValue createValue(final AnimationTarget animationTarget, final String s, final Value value) {
            return new AnimatableBooleanValue(animationTarget, "true".equals(value.getCssText()));
        }
    }
    
    protected class AnimationThread extends Thread
    {
        protected Calendar time;
        protected RunnableQueue runnableQueue;
        protected Ticker ticker;
        
        protected AnimationThread() {
            this.time = Calendar.getInstance();
            this.runnableQueue = SVGAnimationEngine.this.ctx.getUpdateManager().getUpdateRunnableQueue();
            this.ticker = new Ticker();
        }
        
        public void run() {
            while (true) {
                this.time.setTime(new Date());
                this.ticker.t = SVGAnimationEngine.this.timedDocumentRoot.convertWallclockTime(this.time);
                try {
                    this.runnableQueue.invokeAndWait(this.ticker);
                }
                catch (InterruptedException ex) {}
            }
        }
        
        protected class Ticker implements Runnable
        {
            protected float t;
            
            public void run() {
                AnimationEngine.this.tick(this.t, false);
            }
        }
    }
    
    protected static class AnimationTickRunnable implements RunnableQueue.IdleRunnable
    {
        protected Calendar time;
        protected long waitTime;
        protected RunnableQueue q;
        private static final int NUM_TIMES = 8;
        protected long[] times;
        protected long sumTime;
        protected int timeIndex;
        protected WeakReference engRef;
        protected static final int MAX_EXCEPTION_COUNT = 10;
        protected int exceptionCount;
        
        public AnimationTickRunnable(final RunnableQueue q, final SVGAnimationEngine referent) {
            this.time = Calendar.getInstance();
            this.times = new long[8];
            this.q = q;
            this.engRef = new WeakReference((T)referent);
            Arrays.fill(this.times, 100L);
            this.sumTime = 800L;
        }
        
        public void resume() {
            this.waitTime = 0L;
            final Object iteratorLock = this.q.getIteratorLock();
            synchronized (iteratorLock) {
                iteratorLock.notify();
            }
        }
        
        public long getWaitTime() {
            return this.waitTime;
        }
        
        public void run() {
            final SVGAnimationEngine animationEngine = this.getAnimationEngine();
            synchronized (animationEngine) {
                final int animationLimitingMode = animationEngine.animationLimitingMode;
                final float animationLimitingAmount = animationEngine.animationLimitingAmount;
                try {
                    try {
                        final long currentTimeMillis = System.currentTimeMillis();
                        this.time.setTime(new Date(currentTimeMillis));
                        final float access$600 = animationEngine.tick(animationEngine.timedDocumentRoot.convertWallclockTime(this.time), false);
                        final long currentTimeMillis2 = System.currentTimeMillis();
                        long n = currentTimeMillis2 - currentTimeMillis;
                        if (n == 0L) {
                            n = 1L;
                        }
                        this.sumTime -= this.times[this.timeIndex];
                        this.sumTime += n;
                        this.times[this.timeIndex] = n;
                        this.timeIndex = (this.timeIndex + 1) % 8;
                        if (access$600 == Float.POSITIVE_INFINITY) {
                            this.waitTime = Long.MAX_VALUE;
                        }
                        else {
                            this.waitTime = currentTimeMillis + (long)(access$600 * 1000.0f) - 1000L;
                            if (this.waitTime < currentTimeMillis2) {
                                this.waitTime = currentTimeMillis2;
                            }
                            if (animationLimitingMode != 0) {
                                final float n2 = this.sumTime / 8.0f;
                                float n3;
                                if (animationLimitingMode == 1) {
                                    n3 = n2 / animationLimitingAmount - n2;
                                }
                                else {
                                    n3 = 1000.0f / animationLimitingAmount - n2;
                                }
                                final long waitTime = currentTimeMillis2 + (long)n3;
                                if (waitTime > this.waitTime) {
                                    this.waitTime = waitTime;
                                }
                            }
                        }
                    }
                    catch (AnimationException ex) {
                        throw new BridgeException(animationEngine.ctx, ex.getElement().getElement(), ex.getMessage());
                    }
                    this.exceptionCount = 0;
                }
                catch (Exception ex2) {
                    if (++this.exceptionCount < 10) {
                        if (animationEngine.ctx.getUserAgent() == null) {
                            ex2.printStackTrace();
                        }
                        else {
                            animationEngine.ctx.getUserAgent().displayError(ex2);
                        }
                    }
                }
                if (animationLimitingMode == 0) {
                    try {
                        Thread.sleep(1L);
                    }
                    catch (InterruptedException ex3) {}
                }
            }
        }
        
        protected SVGAnimationEngine getAnimationEngine() {
            return (SVGAnimationEngine)this.engRef.get();
        }
    }
    
    protected static class DebugAnimationTickRunnable extends AnimationTickRunnable
    {
        float t;
        
        public DebugAnimationTickRunnable(final RunnableQueue runnableQueue, final SVGAnimationEngine svgAnimationEngine) {
            super(runnableQueue, svgAnimationEngine);
            this.t = 0.0f;
            this.waitTime = Long.MAX_VALUE;
            new Thread() {
                private final /* synthetic */ DebugAnimationTickRunnable this$0 = this$0;
                
                public void run() {
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Enter times.");
                    while (true) {
                        String line;
                        try {
                            line = bufferedReader.readLine();
                        }
                        catch (IOException ex) {
                            line = null;
                        }
                        if (line == null) {
                            System.exit(0);
                        }
                        this.this$0.t = Float.parseFloat(line);
                        this.this$0.resume();
                    }
                }
            }.start();
        }
        
        public void resume() {
            this.waitTime = 0L;
            final Object iteratorLock = this.q.getIteratorLock();
            synchronized (iteratorLock) {
                iteratorLock.notify();
            }
        }
        
        public long getWaitTime() {
            final long waitTime = this.waitTime;
            this.waitTime = Long.MAX_VALUE;
            return waitTime;
        }
        
        public void run() {
            final SVGAnimationEngine animationEngine = this.getAnimationEngine();
            synchronized (animationEngine) {
                try {
                    try {
                        animationEngine.tick(this.t, false);
                    }
                    catch (AnimationException ex) {
                        throw new BridgeException(animationEngine.ctx, ex.getElement().getElement(), ex.getMessage());
                    }
                }
                catch (Exception ex2) {
                    if (animationEngine.ctx.getUserAgent() == null) {
                        ex2.printStackTrace();
                    }
                    else {
                        animationEngine.ctx.getUserAgent().displayError(ex2);
                    }
                }
            }
        }
    }
    
    protected class AnimationRoot extends TimedDocumentRoot
    {
        public AnimationRoot() {
            super(!SVGAnimationEngine.this.isSVG12, SVGAnimationEngine.this.isSVG12);
        }
        
        protected String getEventNamespaceURI(final String s) {
            if (!SVGAnimationEngine.this.isSVG12) {
                return null;
            }
            if (s.equals("focusin") || s.equals("focusout") || s.equals("activate") || SVGAnimationEngine.animationEventNames12.contains(s)) {
                return "http://www.w3.org/2001/xml-events";
            }
            return null;
        }
        
        protected String getEventType(final String s) {
            if (s.equals("focusin")) {
                return "DOMFocusIn";
            }
            if (s.equals("focusout")) {
                return "DOMFocusOut";
            }
            if (s.equals("activate")) {
                return "DOMActivate";
            }
            if (SVGAnimationEngine.this.isSVG12) {
                if (SVGAnimationEngine.animationEventNames12.contains(s)) {
                    return s;
                }
            }
            else if (SVGAnimationEngine.animationEventNames11.contains(s)) {
                return s;
            }
            return null;
        }
        
        protected String getRepeatEventName() {
            return "repeatEvent";
        }
        
        protected void fireTimeEvent(final String s, final Calendar calendar, final int n) {
            AnimationSupport.fireTimeEvent((EventTarget)SVGAnimationEngine.this.document, s, calendar, n);
        }
        
        protected void toActive(final float n) {
        }
        
        protected void toInactive(final boolean b, final boolean b2) {
        }
        
        protected void removeFill() {
        }
        
        protected void sampledAt(final float n, final float n2, final int n3) {
        }
        
        protected void sampledLastValue(final int n) {
        }
        
        protected TimedElement getTimedElementById(final String s) {
            return AnimationSupport.getTimedElementById(s, SVGAnimationEngine.this.document);
        }
        
        protected EventTarget getEventTargetById(final String s) {
            return AnimationSupport.getEventTargetById(s, SVGAnimationEngine.this.document);
        }
        
        protected EventTarget getAnimationEventTarget() {
            return null;
        }
        
        protected EventTarget getRootEventTarget() {
            return (EventTarget)SVGAnimationEngine.this.document;
        }
        
        public Element getElement() {
            return null;
        }
        
        public boolean isBefore(final TimedElement timedElement) {
            return false;
        }
        
        protected void currentIntervalWillUpdate() {
            if (SVGAnimationEngine.this.animationTickRunnable != null) {
                SVGAnimationEngine.this.animationTickRunnable.resume();
            }
        }
    }
}
