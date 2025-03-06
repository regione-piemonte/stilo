// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import java.util.Iterator;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.DOMException;
import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.apache.batik.anim.values.AnimatablePathDataValue;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathArrayProducer;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGPathSegList;
import org.w3c.dom.svg.SVGAnimatedPathData;

public class SVGOMAnimatedPathData extends AbstractSVGAnimatedValue implements SVGAnimatedPathData
{
    protected boolean changing;
    protected BaseSVGPathSegList pathSegs;
    protected NormalizedBaseSVGPathSegList normalizedPathSegs;
    protected AnimSVGPathSegList animPathSegs;
    protected String defaultValue;
    
    public SVGOMAnimatedPathData(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
    }
    
    public SVGPathSegList getAnimatedNormalizedPathSegList() {
        throw new UnsupportedOperationException("SVGAnimatedPathData.getAnimatedNormalizedPathSegList is not implemented");
    }
    
    public SVGPathSegList getAnimatedPathSegList() {
        if (this.animPathSegs == null) {
            this.animPathSegs = new AnimSVGPathSegList();
        }
        return (SVGPathSegList)this.animPathSegs;
    }
    
    public SVGPathSegList getNormalizedPathSegList() {
        if (this.normalizedPathSegs == null) {
            this.normalizedPathSegs = new NormalizedBaseSVGPathSegList();
        }
        return (SVGPathSegList)this.normalizedPathSegs;
    }
    
    public SVGPathSegList getPathSegList() {
        if (this.pathSegs == null) {
            this.pathSegs = new BaseSVGPathSegList();
        }
        return (SVGPathSegList)this.pathSegs;
    }
    
    public void check() {
        if (!this.hasAnimVal) {
            if (this.pathSegs == null) {
                this.pathSegs = new BaseSVGPathSegList();
            }
            this.pathSegs.revalidate();
            if (this.pathSegs.missing) {
                throw new LiveAttributeException(this.element, this.localName, (short)0, null);
            }
            if (this.pathSegs.malformed) {
                throw new LiveAttributeException(this.element, this.localName, (short)1, this.pathSegs.getValueAsString());
            }
        }
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        final SVGPathSegList pathSegList = this.getPathSegList();
        final PathArrayProducer pathArrayProducer = new PathArrayProducer();
        SVGAnimatedPathDataSupport.handlePathSegList(pathSegList, pathArrayProducer);
        return new AnimatablePathDataValue(animationTarget, pathArrayProducer.getPathCommands(), pathArrayProducer.getPathParameters());
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatablePathDataValue animatablePathDataValue = (AnimatablePathDataValue)animatableValue;
            if (this.animPathSegs == null) {
                this.animPathSegs = new AnimSVGPathSegList();
            }
            this.animPathSegs.setAnimatedValue(animatablePathDataValue.getCommands(), animatablePathDataValue.getParameters());
        }
        this.fireAnimatedAttributeListeners();
    }
    
    public void attrAdded(final Attr attr, final String s) {
        if (!this.changing) {
            if (this.pathSegs != null) {
                this.pathSegs.invalidate();
            }
            if (this.normalizedPathSegs != null) {
                this.normalizedPathSegs.invalidate();
            }
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrModified(final Attr attr, final String s, final String s2) {
        if (!this.changing) {
            if (this.pathSegs != null) {
                this.pathSegs.invalidate();
            }
            if (this.normalizedPathSegs != null) {
                this.normalizedPathSegs.invalidate();
            }
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrRemoved(final Attr attr, final String s) {
        if (!this.changing) {
            if (this.pathSegs != null) {
                this.pathSegs.invalidate();
            }
            if (this.normalizedPathSegs != null) {
                this.normalizedPathSegs.invalidate();
            }
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public class AnimSVGPathSegList extends AbstractSVGPathSegList
    {
        private int[] parameterIndex;
        
        public AnimSVGPathSegList() {
            this.parameterIndex = new int[1];
            this.itemList = new ArrayList(1);
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPathData.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedPathData.this.element).createSVGException(n, s, array);
        }
        
        public int getNumberOfItems() {
            if (SVGOMAnimatedPathData.this.hasAnimVal) {
                return super.getNumberOfItems();
            }
            return SVGOMAnimatedPathData.this.getPathSegList().getNumberOfItems();
        }
        
        public SVGPathSeg getItem(final int n) throws DOMException {
            if (SVGOMAnimatedPathData.this.hasAnimVal) {
                return super.getItem(n);
            }
            return SVGOMAnimatedPathData.this.getPathSegList().getItem(n);
        }
        
        protected String getValueAsString() {
            if (this.itemList.size() == 0) {
                return "";
            }
            final StringBuffer sb = new StringBuffer(this.itemList.size() * 8);
            final Iterator<SVGItem> iterator = (Iterator<SVGItem>)this.itemList.iterator();
            if (iterator.hasNext()) {
                sb.append(iterator.next().getValueAsString());
            }
            while (iterator.hasNext()) {
                sb.append(this.getItemSeparator());
                sb.append(iterator.next().getValueAsString());
            }
            return sb.toString();
        }
        
        protected void setAttributeValue(final String s) {
        }
        
        public void clear() throws DOMException {
            throw SVGOMAnimatedPathData.this.element.createDOMException((short)7, "readonly.pathseg.list", null);
        }
        
        public SVGPathSeg initialize(final SVGPathSeg svgPathSeg) throws DOMException, SVGException {
            throw SVGOMAnimatedPathData.this.element.createDOMException((short)7, "readonly.pathseg.list", null);
        }
        
        public SVGPathSeg insertItemBefore(final SVGPathSeg svgPathSeg, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedPathData.this.element.createDOMException((short)7, "readonly.pathseg.list", null);
        }
        
        public SVGPathSeg replaceItem(final SVGPathSeg svgPathSeg, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedPathData.this.element.createDOMException((short)7, "readonly.pathseg.list", null);
        }
        
        public SVGPathSeg removeItem(final int n) throws DOMException {
            throw SVGOMAnimatedPathData.this.element.createDOMException((short)7, "readonly.pathseg.list", null);
        }
        
        public SVGPathSeg appendItem(final SVGPathSeg svgPathSeg) throws DOMException {
            throw SVGOMAnimatedPathData.this.element.createDOMException((short)7, "readonly.pathseg.list", null);
        }
        
        protected SVGPathSegItem newItem(final short n, final float[] array, final int[] array2) {
            switch (n) {
                case 10:
                case 11: {
                    return new SVGPathSegArcItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++], array[array2[0]++], array[array2[0]++], array[array2[0]++] != 0.0f, array[array2[0]++] != 0.0f, array[array2[0]++], array[array2[0]++]);
                }
                case 1: {
                    return new SVGPathSegItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n]);
                }
                case 6:
                case 7: {
                    return new SVGPathSegCurvetoCubicItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++], array[array2[0]++], array[array2[0]++], array[array2[0]++], array[array2[0]++], array[array2[0]++]);
                }
                case 16:
                case 17: {
                    return new SVGPathSegCurvetoCubicSmoothItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++], array[array2[0]++], array[array2[0]++], array[array2[0]++]);
                }
                case 8:
                case 9: {
                    return new SVGPathSegCurvetoQuadraticItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++], array[array2[0]++], array[array2[0]++], array[array2[0]++]);
                }
                case 18:
                case 19: {
                    return new SVGPathSegCurvetoQuadraticSmoothItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++], array[array2[0]++]);
                }
                case 2:
                case 3:
                case 4:
                case 5: {
                    return new SVGPathSegMovetoLinetoItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++], array[array2[0]++]);
                }
                case 12:
                case 13: {
                    return new SVGPathSegLinetoHorizontalItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++]);
                }
                case 14:
                case 15: {
                    return new SVGPathSegLinetoVerticalItem(n, SVGPathSegConstants.PATHSEG_LETTERS[n], array[array2[0]++]);
                }
                default: {
                    return null;
                }
            }
        }
        
        protected void setAnimatedValue(final short[] array, final float[] array2) {
            int i = this.itemList.size();
            int j = 0;
            final int[] parameterIndex = this.parameterIndex;
            parameterIndex[0] = 0;
            while (j < i && j < array.length) {
                final SVGPathSeg svgPathSeg = this.itemList.get(j);
                if (svgPathSeg.getPathSegType() != array[j]) {
                    this.newItem(array[j], array2, parameterIndex);
                }
                else {
                    switch (array[j]) {
                        case 10:
                        case 11: {
                            final SVGPathSegArcItem svgPathSegArcItem = (SVGPathSegArcItem)svgPathSeg;
                            svgPathSegArcItem.r1 = array2[parameterIndex[0]++];
                            svgPathSegArcItem.r2 = array2[parameterIndex[0]++];
                            svgPathSegArcItem.angle = array2[parameterIndex[0]++];
                            svgPathSegArcItem.largeArcFlag = (array2[parameterIndex[0]++] != 0.0f);
                            svgPathSegArcItem.sweepFlag = (array2[parameterIndex[0]++] != 0.0f);
                            svgPathSegArcItem.x = array2[parameterIndex[0]++];
                            svgPathSegArcItem.y = array2[parameterIndex[0]++];
                        }
                        case 6:
                        case 7: {
                            final SVGPathSegCurvetoCubicItem svgPathSegCurvetoCubicItem = (SVGPathSegCurvetoCubicItem)svgPathSeg;
                            svgPathSegCurvetoCubicItem.x1 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicItem.y1 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicItem.x2 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicItem.y2 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicItem.x = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicItem.y = array2[parameterIndex[0]++];
                            break;
                        }
                        case 16:
                        case 17: {
                            final SVGPathSegCurvetoCubicSmoothItem svgPathSegCurvetoCubicSmoothItem = (SVGPathSegCurvetoCubicSmoothItem)svgPathSeg;
                            svgPathSegCurvetoCubicSmoothItem.x2 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicSmoothItem.y2 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicSmoothItem.x = array2[parameterIndex[0]++];
                            svgPathSegCurvetoCubicSmoothItem.y = array2[parameterIndex[0]++];
                            break;
                        }
                        case 8:
                        case 9: {
                            final SVGPathSegCurvetoQuadraticItem svgPathSegCurvetoQuadraticItem = (SVGPathSegCurvetoQuadraticItem)svgPathSeg;
                            svgPathSegCurvetoQuadraticItem.x1 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoQuadraticItem.y1 = array2[parameterIndex[0]++];
                            svgPathSegCurvetoQuadraticItem.x = array2[parameterIndex[0]++];
                            svgPathSegCurvetoQuadraticItem.y = array2[parameterIndex[0]++];
                            break;
                        }
                        case 18:
                        case 19: {
                            final SVGPathSegCurvetoQuadraticSmoothItem svgPathSegCurvetoQuadraticSmoothItem = (SVGPathSegCurvetoQuadraticSmoothItem)svgPathSeg;
                            svgPathSegCurvetoQuadraticSmoothItem.x = array2[parameterIndex[0]++];
                            svgPathSegCurvetoQuadraticSmoothItem.y = array2[parameterIndex[0]++];
                            break;
                        }
                        case 2:
                        case 3:
                        case 4:
                        case 5: {
                            final SVGPathSegMovetoLinetoItem svgPathSegMovetoLinetoItem = (SVGPathSegMovetoLinetoItem)svgPathSeg;
                            svgPathSegMovetoLinetoItem.x = array2[parameterIndex[0]++];
                            svgPathSegMovetoLinetoItem.y = array2[parameterIndex[0]++];
                            break;
                        }
                        case 12:
                        case 13: {
                            ((SVGPathSegLinetoHorizontalItem)svgPathSeg).x = array2[parameterIndex[0]++];
                            break;
                        }
                        case 14:
                        case 15: {
                            ((SVGPathSegLinetoVerticalItem)svgPathSeg).y = array2[parameterIndex[0]++];
                            break;
                        }
                    }
                }
                ++j;
            }
            while (j < array.length) {
                this.appendItemImpl(this.newItem(array[j], array2, parameterIndex));
                ++j;
            }
            while (i > array.length) {
                this.removeItemImpl(--i);
            }
        }
        
        protected void resetAttribute() {
        }
        
        protected void resetAttribute(final SVGItem svgItem) {
        }
        
        protected void revalidate() {
            this.valid = true;
        }
    }
    
    public class NormalizedBaseSVGPathSegList extends AbstractSVGNormPathSegList
    {
        protected boolean missing;
        protected boolean malformed;
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPathData.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedPathData.this.element).createSVGException(n, s, array);
        }
        
        protected String getValueAsString() throws SVGException {
            final Attr attributeNodeNS = SVGOMAnimatedPathData.this.element.getAttributeNodeNS(SVGOMAnimatedPathData.this.namespaceURI, SVGOMAnimatedPathData.this.localName);
            if (attributeNodeNS == null) {
                return SVGOMAnimatedPathData.this.defaultValue;
            }
            return attributeNodeNS.getValue();
        }
        
        protected void setAttributeValue(final String s) {
            try {
                SVGOMAnimatedPathData.this.changing = true;
                SVGOMAnimatedPathData.this.element.setAttributeNS(SVGOMAnimatedPathData.this.namespaceURI, SVGOMAnimatedPathData.this.localName, s);
            }
            finally {
                SVGOMAnimatedPathData.this.changing = false;
            }
        }
        
        protected void revalidate() {
            if (this.valid) {
                return;
            }
            this.valid = true;
            this.missing = false;
            this.malformed = false;
            final String valueAsString = this.getValueAsString();
            if (valueAsString == null) {
                this.missing = true;
                return;
            }
            try {
                final ListBuilder listBuilder = new ListBuilder();
                this.doParse(valueAsString, listBuilder);
                if (listBuilder.getList() != null) {
                    this.clear(this.itemList);
                }
                this.itemList = listBuilder.getList();
            }
            catch (ParseException ex) {
                this.itemList = new ArrayList(1);
                this.malformed = true;
            }
        }
    }
    
    public class BaseSVGPathSegList extends AbstractSVGPathSegList
    {
        protected boolean missing;
        protected boolean malformed;
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPathData.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedPathData.this.element).createSVGException(n, s, array);
        }
        
        protected String getValueAsString() {
            final Attr attributeNodeNS = SVGOMAnimatedPathData.this.element.getAttributeNodeNS(SVGOMAnimatedPathData.this.namespaceURI, SVGOMAnimatedPathData.this.localName);
            if (attributeNodeNS == null) {
                return SVGOMAnimatedPathData.this.defaultValue;
            }
            return attributeNodeNS.getValue();
        }
        
        protected void setAttributeValue(final String s) {
            try {
                SVGOMAnimatedPathData.this.changing = true;
                SVGOMAnimatedPathData.this.element.setAttributeNS(SVGOMAnimatedPathData.this.namespaceURI, SVGOMAnimatedPathData.this.localName, s);
            }
            finally {
                SVGOMAnimatedPathData.this.changing = false;
            }
        }
        
        protected void resetAttribute() {
            super.resetAttribute();
            this.missing = false;
            this.malformed = false;
        }
        
        protected void resetAttribute(final SVGItem svgItem) {
            super.resetAttribute(svgItem);
            this.missing = false;
            this.malformed = false;
        }
        
        protected void revalidate() {
            if (this.valid) {
                return;
            }
            this.valid = true;
            this.missing = false;
            this.malformed = false;
            final String valueAsString = this.getValueAsString();
            if (valueAsString == null) {
                this.missing = true;
                return;
            }
            try {
                final ListBuilder listBuilder = new ListBuilder();
                this.doParse(valueAsString, listBuilder);
                if (listBuilder.getList() != null) {
                    this.clear(this.itemList);
                }
                this.itemList = listBuilder.getList();
            }
            catch (ParseException ex) {
                this.itemList = new ArrayList(1);
                this.malformed = true;
            }
        }
    }
}
