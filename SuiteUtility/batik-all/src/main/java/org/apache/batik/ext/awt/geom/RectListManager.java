// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.geom;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.Rectangle;
import java.util.Collection;

public class RectListManager implements Collection
{
    Rectangle[] rects;
    int size;
    Rectangle bounds;
    public static Comparator comparator;
    
    public void dump() {
        System.err.println("RLM: " + this + " Sz: " + this.size);
        System.err.println("Bounds: " + this.getBounds());
        for (int i = 0; i < this.size; ++i) {
            final Rectangle rectangle = this.rects[i];
            System.err.println("  [" + rectangle.x + ", " + rectangle.y + ", " + rectangle.width + ", " + rectangle.height + ']');
        }
    }
    
    public RectListManager(final Collection collection) {
        this.rects = null;
        this.size = 0;
        this.bounds = null;
        this.rects = new Rectangle[collection.size()];
        final Iterator<Rectangle> iterator = collection.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            this.rects[n++] = iterator.next();
        }
        this.size = this.rects.length;
        Arrays.sort(this.rects, RectListManager.comparator);
    }
    
    public RectListManager(final Rectangle[] array) {
        this(array, 0, array.length);
    }
    
    public RectListManager(final Rectangle[] array, final int n, final int size) {
        this.rects = null;
        this.size = 0;
        this.bounds = null;
        this.size = size;
        System.arraycopy(array, n, this.rects = new Rectangle[size], 0, size);
        Arrays.sort(this.rects, RectListManager.comparator);
    }
    
    public RectListManager(final RectListManager rectListManager) {
        this(rectListManager.rects);
    }
    
    public RectListManager(final Rectangle rectangle) {
        this();
        this.add(rectangle);
    }
    
    public RectListManager() {
        this.rects = null;
        this.size = 0;
        this.bounds = null;
        this.rects = new Rectangle[10];
        this.size = 0;
    }
    
    public RectListManager(final int n) {
        this.rects = null;
        this.size = 0;
        this.bounds = null;
        this.rects = new Rectangle[n];
    }
    
    public Rectangle getBounds() {
        if (this.bounds != null) {
            return this.bounds;
        }
        if (this.size == 0) {
            return null;
        }
        this.bounds = new Rectangle(this.rects[0]);
        for (int i = 1; i < this.size; ++i) {
            final Rectangle rectangle = this.rects[i];
            if (rectangle.x < this.bounds.x) {
                this.bounds.width = this.bounds.x + this.bounds.width - rectangle.x;
                this.bounds.x = rectangle.x;
            }
            if (rectangle.y < this.bounds.y) {
                this.bounds.height = this.bounds.y + this.bounds.height - rectangle.y;
                this.bounds.y = rectangle.y;
            }
            if (rectangle.x + rectangle.width > this.bounds.x + this.bounds.width) {
                this.bounds.width = rectangle.x + rectangle.width - this.bounds.x;
            }
            if (rectangle.y + rectangle.height > this.bounds.y + this.bounds.height) {
                this.bounds.height = rectangle.y + rectangle.height - this.bounds.y;
            }
        }
        return this.bounds;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.copy();
    }
    
    public RectListManager copy() {
        return new RectListManager(this.rects);
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void clear() {
        Arrays.fill(this.rects, null);
        this.size = 0;
        this.bounds = null;
    }
    
    public Iterator iterator() {
        return new RLMIterator();
    }
    
    public ListIterator listIterator() {
        return new RLMIterator();
    }
    
    public Object[] toArray() {
        final Rectangle[] array = new Rectangle[this.size];
        System.arraycopy(this.rects, 0, array, 0, this.size);
        return array;
    }
    
    public Object[] toArray(Object[] array) {
        final Class<?> componentType = array.getClass().getComponentType();
        if (componentType != Object.class && componentType != Rectangle.class) {
            Arrays.fill(array, null);
            return array;
        }
        if (array.length < this.size) {
            array = new Rectangle[this.size];
        }
        System.arraycopy(this.rects, 0, array, 0, this.size);
        Arrays.fill(array, this.size, array.length, null);
        return array;
    }
    
    public boolean add(final Object o) {
        this.add((Rectangle)o);
        return true;
    }
    
    public void add(final Rectangle rectangle) {
        this.add(rectangle, 0, this.size - 1);
    }
    
    protected void add(final Rectangle rectangle, int i, int n) {
        this.ensureCapacity(this.size + 1);
        int n2 = i;
        while (i <= n) {
            for (n2 = (i + n) / 2; this.rects[n2] == null && n2 < n; ++n2) {}
            if (this.rects[n2] == null) {
                n = (i + n) / 2;
                n2 = (i + n) / 2;
                if (i > n) {
                    n2 = i;
                }
                while (this.rects[n2] == null && n2 > i) {
                    --n2;
                }
                if (this.rects[n2] == null) {
                    this.rects[n2] = rectangle;
                    return;
                }
            }
            if (rectangle.x == this.rects[n2].x) {
                break;
            }
            if (rectangle.x < this.rects[n2].x) {
                if (n2 == 0) {
                    break;
                }
                if (this.rects[n2 - 1] != null && rectangle.x >= this.rects[n2 - 1].x) {
                    break;
                }
                n = n2 - 1;
            }
            else {
                if (n2 == this.size - 1) {
                    ++n2;
                    break;
                }
                if (this.rects[n2 + 1] != null && rectangle.x <= this.rects[n2 + 1].x) {
                    ++n2;
                    break;
                }
                i = n2 + 1;
            }
        }
        if (n2 < this.size) {
            System.arraycopy(this.rects, n2, this.rects, n2 + 1, this.size - n2);
        }
        this.rects[n2] = rectangle;
        ++this.size;
        this.bounds = null;
    }
    
    public boolean addAll(final Collection collection) {
        if (collection instanceof RectListManager) {
            this.add((RectListManager)collection);
        }
        else {
            this.add(new RectListManager(collection));
        }
        return collection.size() != 0;
    }
    
    public boolean contains(final Object o) {
        final Rectangle rectangle = (Rectangle)o;
        int i = 0;
        int n = this.size - 1;
        int n2 = 0;
        while (i <= n) {
            n2 = i + n >>> 1;
            if (rectangle.x == this.rects[n2].x) {
                break;
            }
            if (rectangle.x < this.rects[n2].x) {
                if (n2 == 0) {
                    break;
                }
                if (rectangle.x >= this.rects[n2 - 1].x) {
                    break;
                }
                n = n2 - 1;
            }
            else {
                if (n2 == this.size - 1) {
                    ++n2;
                    break;
                }
                if (rectangle.x <= this.rects[n2 + 1].x) {
                    ++n2;
                    break;
                }
                i = n2 + 1;
            }
        }
        if (this.rects[n2].x != rectangle.x) {
            return false;
        }
        for (int j = n2; j >= 0; --j) {
            if (this.rects[n2].equals(rectangle)) {
                return true;
            }
            if (this.rects[n2].x != rectangle.x) {
                break;
            }
        }
        for (int k = n2 + 1; k < this.size; ++k) {
            if (this.rects[n2].equals(rectangle)) {
                return true;
            }
            if (this.rects[n2].x != rectangle.x) {
                break;
            }
        }
        return false;
    }
    
    public boolean containsAll(final Collection collection) {
        if (collection instanceof RectListManager) {
            return this.containsAll((RectListManager)collection);
        }
        return this.containsAll(new RectListManager(collection));
    }
    
    public boolean containsAll(final RectListManager rectListManager) {
        int n = 0;
        for (int i = 0; i < rectListManager.size; ++i) {
            int n2 = n;
            while (this.rects[n2].x < rectListManager.rects[i].x) {
                if (++n2 == this.size) {
                    return false;
                }
            }
            n = n2;
            final int x = this.rects[n2].x;
            while (!rectListManager.rects[i].equals(this.rects[n2])) {
                if (++n2 == this.size) {
                    return false;
                }
                if (x != this.rects[n2].x) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean remove(final Object o) {
        return this.remove((Rectangle)o);
    }
    
    public boolean remove(final Rectangle rectangle) {
        int i = 0;
        int n = this.size - 1;
        int n2 = 0;
        while (i <= n) {
            n2 = i + n >>> 1;
            if (rectangle.x == this.rects[n2].x) {
                break;
            }
            if (rectangle.x < this.rects[n2].x) {
                if (n2 == 0) {
                    break;
                }
                if (rectangle.x >= this.rects[n2 - 1].x) {
                    break;
                }
                n = n2 - 1;
            }
            else {
                if (n2 == this.size - 1) {
                    ++n2;
                    break;
                }
                if (rectangle.x <= this.rects[n2 + 1].x) {
                    ++n2;
                    break;
                }
                i = n2 + 1;
            }
        }
        if (this.rects[n2].x != rectangle.x) {
            return false;
        }
        for (int j = n2; j >= 0; --j) {
            if (this.rects[n2].equals(rectangle)) {
                System.arraycopy(this.rects, n2 + 1, this.rects, n2, this.size - n2);
                --this.size;
                this.bounds = null;
                return true;
            }
            if (this.rects[n2].x != rectangle.x) {
                break;
            }
        }
        for (int k = n2 + 1; k < this.size; ++k) {
            if (this.rects[n2].equals(rectangle)) {
                System.arraycopy(this.rects, n2 + 1, this.rects, n2, this.size - n2);
                --this.size;
                this.bounds = null;
                return true;
            }
            if (this.rects[n2].x != rectangle.x) {
                break;
            }
        }
        return false;
    }
    
    public boolean removeAll(final Collection collection) {
        if (collection instanceof RectListManager) {
            return this.removeAll((RectListManager)collection);
        }
        return this.removeAll(new RectListManager(collection));
    }
    
    public boolean removeAll(final RectListManager rectListManager) {
        int n = 0;
        boolean b = false;
        for (int i = 0; i < rectListManager.size; ++i) {
            int n2 = n;
            while ((this.rects[n2] == null || this.rects[n2].x < rectListManager.rects[i].x) && ++n2 != this.size) {}
            if (n2 == this.size) {
                break;
            }
            n = n2;
            final int x = this.rects[n2].x;
            while (true) {
                if (this.rects[n2] == null) {
                    if (++n2 == this.size) {
                        break;
                    }
                    continue;
                }
                else {
                    if (rectListManager.rects[i].equals(this.rects[n2])) {
                        this.rects[n2] = null;
                        b = true;
                    }
                    if (++n2 == this.size) {
                        break;
                    }
                    if (x != this.rects[n2].x) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (b) {
            int size = 0;
            for (int j = 0; j < this.size; ++j) {
                if (this.rects[j] != null) {
                    this.rects[size++] = this.rects[j];
                }
            }
            this.size = size;
            this.bounds = null;
        }
        return b;
    }
    
    public boolean retainAll(final Collection collection) {
        if (collection instanceof RectListManager) {
            return this.retainAll((RectListManager)collection);
        }
        return this.retainAll(new RectListManager(collection));
    }
    
    public boolean retainAll(final RectListManager rectListManager) {
        int n = 0;
        boolean b = false;
        int i = 0;
    Label_0011:
        while (i < this.size) {
            int n2 = n;
            while (rectListManager.rects[n2].x < this.rects[i].x && ++n2 != rectListManager.size) {}
            if (n2 == rectListManager.size) {
                b = true;
                for (int j = i; j < this.size; ++j) {
                    this.rects[j] = null;
                }
                this.size = i;
                break;
            }
            n = n2;
            final int x = rectListManager.rects[n2].x;
            while (true) {
                while (!this.rects[i].equals(rectListManager.rects[n2])) {
                    if (++n2 == rectListManager.size || x != rectListManager.rects[n2].x) {
                        this.rects[i] = null;
                        b = true;
                        ++i;
                        continue Label_0011;
                    }
                }
                continue;
            }
        }
        if (b) {
            int size = 0;
            for (int k = 0; k < this.size; ++k) {
                if (this.rects[k] != null) {
                    this.rects[size++] = this.rects[k];
                }
            }
            this.size = size;
            this.bounds = null;
        }
        return b;
    }
    
    public void add(final RectListManager rectListManager) {
        if (rectListManager.size == 0) {
            return;
        }
        Rectangle[] rects = this.rects;
        if (this.rects.length < this.size + rectListManager.size) {
            rects = new Rectangle[this.size + rectListManager.size];
        }
        if (this.size == 0) {
            System.arraycopy(rectListManager.rects, 0, rects, this.size, rectListManager.size);
            this.size = rectListManager.size;
            this.bounds = null;
            return;
        }
        final Rectangle[] rects2 = rectListManager.rects;
        int n = rectListManager.size - 1;
        final Rectangle[] rects3 = this.rects;
        int n2 = this.size - 1;
        int i = this.size + rectListManager.size - 1;
        int n3 = rects2[n].x;
        int n4 = rects3[n2].x;
        while (i >= 0) {
            if (n3 <= n4) {
                rects[i] = rects3[n2];
                if (n2 == 0) {
                    System.arraycopy(rects2, 0, rects, 0, n + 1);
                    break;
                }
                --n2;
                n4 = rects3[n2].x;
            }
            else {
                rects[i] = rects2[n];
                if (n == 0) {
                    System.arraycopy(rects3, 0, rects, 0, n2 + 1);
                    break;
                }
                --n;
                n3 = rects2[n].x;
            }
            --i;
        }
        this.rects = rects;
        this.size += rectListManager.size;
        this.bounds = null;
    }
    
    public void mergeRects(final int n, final int n2) {
        if (this.size == 0) {
            return;
        }
        final Rectangle rectangle = new Rectangle();
        final Rectangle[] array = new Rectangle[4];
        for (int i = 0; i < this.size; ++i) {
            Rectangle rectangle2 = this.rects[i];
            if (rectangle2 != null) {
                int n3 = n + rectangle2.height * n2 + rectangle2.height * rectangle2.width;
                int j;
                do {
                    final int n4 = rectangle2.x + rectangle2.width + n / rectangle2.height;
                    for (j = i + 1; j < this.size; ++j) {
                        final Rectangle rectangle3 = this.rects[j];
                        if (rectangle3 != null) {
                            if (rectangle3 != rectangle2) {
                                if (rectangle3.x >= n4) {
                                    j = this.size;
                                    break;
                                }
                                final int n5 = n + rectangle3.height * n2 + rectangle3.height * rectangle3.width;
                                final Rectangle union = rectangle2.union(rectangle3);
                                final int n6 = n + union.height * n2 + union.height * union.width;
                                if (n6 <= n3 + n5) {
                                    final Rectangle[] rects = this.rects;
                                    final int n7 = i;
                                    final Rectangle rectangle4 = union;
                                    rects[n7] = rectangle4;
                                    rectangle2 = rectangle4;
                                    this.rects[j] = null;
                                    n3 = n6;
                                    j = -1;
                                    break;
                                }
                                if (rectangle2.intersects(rectangle3)) {
                                    this.splitRect(rectangle3, rectangle2, array);
                                    int n8 = 0;
                                    int n9 = 0;
                                    for (int k = 0; k < 4; ++k) {
                                        if (array[k] != null) {
                                            final Rectangle rectangle5 = array[k];
                                            if (k < 3) {
                                                array[n9++] = rectangle5;
                                            }
                                            n8 += n + rectangle5.height * n2 + rectangle5.height * rectangle5.width;
                                        }
                                    }
                                    if (n8 < n5) {
                                        if (n9 == 0) {
                                            this.rects[j] = null;
                                            if (array[3] != null) {
                                                this.add(array[3], j, this.size - 1);
                                            }
                                        }
                                        else {
                                            this.rects[j] = array[0];
                                            if (n9 > 1) {
                                                this.insertRects(array, 1, j + 1, n9 - 1);
                                            }
                                            if (array[3] != null) {
                                                this.add(array[3], j, this.size - 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } while (j != this.size);
            }
        }
        int size = 0;
        int l = 0;
        float n10 = 0.0f;
        while (l < this.size) {
            if (this.rects[l] != null) {
                final Rectangle rectangle6 = this.rects[l];
                this.rects[size++] = rectangle6;
                n10 += n + rectangle6.height * n2 + rectangle6.height * rectangle6.width;
            }
            ++l;
        }
        this.size = size;
        this.bounds = null;
        final Rectangle bounds = this.getBounds();
        if (bounds == null) {
            return;
        }
        if (n + bounds.height * n2 + bounds.height * bounds.width < n10) {
            this.rects[0] = bounds;
            this.size = 1;
        }
    }
    
    public void subtract(final RectListManager rectListManager, final int n, final int n2) {
        int n3 = 0;
        final Rectangle[] array = new Rectangle[4];
        for (int i = 0; i < this.size; ++i) {
            Rectangle rectangle = this.rects[i];
            int n4 = n + rectangle.height * n2 + rectangle.height * rectangle.width;
            for (int j = n3; j < rectListManager.size; ++j) {
                final Rectangle r = rectListManager.rects[j];
                if (r.x + r.width < rectangle.x) {
                    if (j == n3) {
                        ++n3;
                    }
                }
                else {
                    if (r.x > rectangle.x + rectangle.width) {
                        break;
                    }
                    if (rectangle.intersects(r)) {
                        this.splitRect(rectangle, r, array);
                        int n5 = 0;
                        for (int k = 0; k < 4; ++k) {
                            final Rectangle rectangle2 = array[k];
                            if (rectangle2 != null) {
                                n5 += n + rectangle2.height * n2 + rectangle2.height * rectangle2.width;
                            }
                        }
                        if (n5 < n4) {
                            int n6 = 0;
                            for (int l = 0; l < 3; ++l) {
                                if (array[l] != null) {
                                    array[n6++] = array[l];
                                }
                            }
                            if (n6 == 0) {
                                this.rects[i].width = 0;
                                if (array[3] != null) {
                                    this.add(array[3], i, this.size - 1);
                                    break;
                                }
                                break;
                            }
                            else {
                                rectangle = array[0];
                                this.rects[i] = rectangle;
                                n4 = n + rectangle.height * n2 + rectangle.height * rectangle.width;
                                if (n6 > 1) {
                                    this.insertRects(array, 1, i + 1, n6 - 1);
                                }
                                if (array[3] != null) {
                                    this.add(array[3], i + n6, this.size - 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        int size = 0;
        for (int n7 = 0; n7 < this.size; ++n7) {
            if (this.rects[n7].width == 0) {
                this.rects[n7] = null;
            }
            else {
                this.rects[size++] = this.rects[n7];
            }
        }
        this.size = size;
        this.bounds = null;
    }
    
    protected void splitRect(final Rectangle rectangle, final Rectangle rectangle2, final Rectangle[] array) {
        final int x = rectangle.x;
        final int n = x + rectangle.width - 1;
        int y = rectangle.y;
        int n2 = y + rectangle.height - 1;
        final int x2 = rectangle2.x;
        final int n3 = x2 + rectangle2.width - 1;
        final int y2 = rectangle2.y;
        final int n4 = y2 + rectangle2.height - 1;
        if (y < y2 && n2 >= y2) {
            array[0] = new Rectangle(x, y, rectangle.width, y2 - y);
            y = y2;
        }
        else {
            array[0] = null;
        }
        if (y <= n4 && n2 > n4) {
            array[1] = new Rectangle(x, n4 + 1, rectangle.width, n2 - n4);
            n2 = n4;
        }
        else {
            array[1] = null;
        }
        if (x < x2 && n >= x2) {
            array[2] = new Rectangle(x, y, x2 - x, n2 - y + 1);
        }
        else {
            array[2] = null;
        }
        if (x <= n3 && n > n3) {
            array[3] = new Rectangle(n3 + 1, y, n - n3, n2 - y + 1);
        }
        else {
            array[3] = null;
        }
    }
    
    protected void insertRects(final Rectangle[] array, final int n, final int n2, final int n3) {
        if (n3 == 0) {
            return;
        }
        this.ensureCapacity(this.size + n3);
        for (int i = this.size - 1; i >= n2; --i) {
            this.rects[i + n3] = this.rects[i];
        }
        System.arraycopy(array, n, this.rects, n2, n3);
        this.size += n3;
    }
    
    public void ensureCapacity(final int n) {
        if (n <= this.rects.length) {
            return;
        }
        int i;
        for (i = this.rects.length + (this.rects.length >> 1) + 1; i < n; i += (i >> 1) + 1) {}
        final Rectangle[] rects = new Rectangle[i];
        System.arraycopy(this.rects, 0, rects, 0, this.size);
        this.rects = rects;
    }
    
    static {
        RectListManager.comparator = new RectXComparator();
    }
    
    private class RLMIterator implements ListIterator
    {
        int idx;
        boolean removeOk;
        boolean forward;
        
        RLMIterator() {
            this.idx = 0;
            this.removeOk = false;
            this.forward = true;
        }
        
        public boolean hasNext() {
            return this.idx < RectListManager.this.size;
        }
        
        public int nextIndex() {
            return this.idx;
        }
        
        public Object next() {
            if (this.idx >= RectListManager.this.size) {
                throw new NoSuchElementException("No Next Element");
            }
            this.forward = true;
            this.removeOk = true;
            return RectListManager.this.rects[this.idx++];
        }
        
        public boolean hasPrevious() {
            return this.idx > 0;
        }
        
        public int previousIndex() {
            return this.idx - 1;
        }
        
        public Object previous() {
            if (this.idx <= 0) {
                throw new NoSuchElementException("No Previous Element");
            }
            this.forward = false;
            this.removeOk = true;
            final Rectangle[] rects = RectListManager.this.rects;
            final int idx = this.idx - 1;
            this.idx = idx;
            return rects[idx];
        }
        
        public void remove() {
            if (!this.removeOk) {
                throw new IllegalStateException("remove can only be called directly after next/previous");
            }
            if (this.forward) {
                --this.idx;
            }
            if (this.idx != RectListManager.this.size - 1) {
                System.arraycopy(RectListManager.this.rects, this.idx + 1, RectListManager.this.rects, this.idx, RectListManager.this.size - (this.idx + 1));
            }
            final RectListManager this$0 = RectListManager.this;
            --this$0.size;
            RectListManager.this.rects[RectListManager.this.size] = null;
            this.removeOk = false;
        }
        
        public void set(final Object o) {
            final Rectangle rectangle = (Rectangle)o;
            if (!this.removeOk) {
                throw new IllegalStateException("set can only be called directly after next/previous");
            }
            if (this.forward) {
                --this.idx;
            }
            if (this.idx + 1 < RectListManager.this.size && RectListManager.this.rects[this.idx + 1].x < rectangle.x) {
                throw new UnsupportedOperationException("RectListManager entries must be sorted");
            }
            if (this.idx >= 0 && RectListManager.this.rects[this.idx - 1].x > rectangle.x) {
                throw new UnsupportedOperationException("RectListManager entries must be sorted");
            }
            RectListManager.this.rects[this.idx] = rectangle;
            this.removeOk = false;
        }
        
        public void add(final Object o) {
            final Rectangle rectangle = (Rectangle)o;
            if (this.idx < RectListManager.this.size && RectListManager.this.rects[this.idx].x < rectangle.x) {
                throw new UnsupportedOperationException("RectListManager entries must be sorted");
            }
            if (this.idx != 0 && RectListManager.this.rects[this.idx - 1].x > rectangle.x) {
                throw new UnsupportedOperationException("RectListManager entries must be sorted");
            }
            RectListManager.this.ensureCapacity(RectListManager.this.size + 1);
            if (this.idx != RectListManager.this.size) {
                System.arraycopy(RectListManager.this.rects, this.idx, RectListManager.this.rects, this.idx + 1, RectListManager.this.size - this.idx);
            }
            RectListManager.this.rects[this.idx] = rectangle;
            ++this.idx;
            this.removeOk = false;
        }
    }
    
    private static class RectXComparator implements Comparator, Serializable
    {
        RectXComparator() {
        }
        
        public final int compare(final Object o, final Object o2) {
            return ((Rectangle)o).x - ((Rectangle)o2).x;
        }
    }
}
