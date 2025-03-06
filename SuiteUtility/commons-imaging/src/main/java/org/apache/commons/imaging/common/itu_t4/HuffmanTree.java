// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.itu_t4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HuffmanTree<T>
{
    private final List<Node<T>> nodes;
    
    HuffmanTree() {
        this.nodes = new ArrayList<Node<T>>();
    }
    
    public final void insert(final String pattern, final T value) throws HuffmanTreeException {
        int position = 0;
        Node<T> node = this.growAndGetNode(position);
        if (node.value != null) {
            throw new HuffmanTreeException("Can't add child to a leaf");
        }
        for (int patternPosition = 0; patternPosition < pattern.length(); ++patternPosition) {
            final char nextChar = pattern.charAt(patternPosition);
            if (nextChar == '0') {
                position = (position << 1) + 1;
            }
            else {
                position = position + 1 << 1;
            }
            node = this.growAndGetNode(position);
            if (node.value != null) {
                throw new HuffmanTreeException("Can't add child to a leaf");
            }
        }
        node.value = value;
    }
    
    private Node<T> growAndGetNode(final int position) {
        while (position >= this.nodes.size()) {
            this.nodes.add(new Node<T>());
        }
        final Node<T> node = this.nodes.get(position);
        node.empty = false;
        return node;
    }
    
    public final T decode(final BitInputStreamFlexible bitStream) throws HuffmanTreeException {
        int position = 0;
        Node<T> node = this.nodes.get(0);
        while (node.value == null) {
            int nextBit;
            try {
                nextBit = bitStream.readBits(1);
            }
            catch (IOException ioEx) {
                throw new HuffmanTreeException("Error reading stream for huffman tree", ioEx);
            }
            if (nextBit == 0) {
                position = (position << 1) + 1;
            }
            else {
                position = position + 1 << 1;
            }
            if (position >= this.nodes.size()) {
                throw new HuffmanTreeException("Invalid bit pattern");
            }
            node = this.nodes.get(position);
            if (node.empty) {
                throw new HuffmanTreeException("Invalid bit pattern");
            }
        }
        return node.value;
    }
    
    private static final class Node<T>
    {
        boolean empty;
        T value;
        
        private Node() {
            this.empty = true;
        }
    }
}
