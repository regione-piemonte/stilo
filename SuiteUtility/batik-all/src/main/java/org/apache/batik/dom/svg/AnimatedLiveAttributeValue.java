/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;

public interface AnimatedLiveAttributeValue extends LiveAttributeValue
{
    String getNamespaceURI();
    
    String getLocalName();
    
    AnimatableValue getUnderlyingValue(final AnimationTarget p0);
    
    void addAnimatedAttributeListener(final AnimatedAttributeListener p0);
    
    void removeAnimatedAttributeListener(final AnimatedAttributeListener p0);
}
