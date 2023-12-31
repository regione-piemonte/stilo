/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Smart GWT (GWT for SmartClient)
 * Copyright 2008 and beyond, Isomorphic Software, Inc.
 *
 * Smart GWT is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  Smart GWT is also
 * available under typical commercial license terms - see
 * http://smartclient.com/license
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
/* sgwtgen */
 
package com.smartgwt.client.widgets;


import com.smartgwt.client.event.*;
import com.smartgwt.client.core.*;
import com.smartgwt.client.types.*;
import com.smartgwt.client.data.*;
import com.smartgwt.client.data.events.*;
import com.smartgwt.client.rpc.*;
import com.smartgwt.client.callbacks.*;
import com.smartgwt.client.tools.*;
import com.smartgwt.client.bean.*;
import com.smartgwt.client.widgets.*;
import com.smartgwt.client.widgets.events.*;
import com.smartgwt.client.widgets.form.*;
import com.smartgwt.client.widgets.form.validator.*;
import com.smartgwt.client.widgets.form.fields.*;
import com.smartgwt.client.widgets.tile.*;
import com.smartgwt.client.widgets.tile.events.*;
import com.smartgwt.client.widgets.grid.*;
import com.smartgwt.client.widgets.grid.events.*;
import com.smartgwt.client.widgets.chart.*;
import com.smartgwt.client.widgets.layout.*;
import com.smartgwt.client.widgets.layout.events.*;
import com.smartgwt.client.widgets.menu.*;
import com.smartgwt.client.widgets.rte.*;
import com.smartgwt.client.widgets.rte.events.*;
import com.smartgwt.client.widgets.ace.*;
import com.smartgwt.client.widgets.ace.events.*;
import com.smartgwt.client.widgets.tab.*;
import com.smartgwt.client.widgets.toolbar.*;
import com.smartgwt.client.widgets.tree.*;
import com.smartgwt.client.widgets.tree.events.*;
import com.smartgwt.client.widgets.tableview.*;
import com.smartgwt.client.widgets.viewer.*;
import com.smartgwt.client.widgets.calendar.*;
import com.smartgwt.client.widgets.calendar.events.*;
import com.smartgwt.client.widgets.cube.*;
import com.smartgwt.client.widgets.drawing.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.smartgwt.client.util.*;
import com.smartgwt.client.util.events.*;
import com.smartgwt.client.util.workflow.*;
import com.google.gwt.event.shared.*;
import com.google.gwt.event.shared.HasHandlers;
import com.smartgwt.logicalstructure.core.*;
import com.smartgwt.logicalstructure.widgets.*;
import com.smartgwt.logicalstructure.widgets.drawing.*;
import com.smartgwt.logicalstructure.widgets.plugins.*;
import com.smartgwt.logicalstructure.widgets.form.*;
import com.smartgwt.logicalstructure.widgets.tile.*;
import com.smartgwt.logicalstructure.widgets.grid.*;
import com.smartgwt.logicalstructure.widgets.chart.*;
import com.smartgwt.logicalstructure.widgets.layout.*;
import com.smartgwt.logicalstructure.widgets.menu.*;
import com.smartgwt.logicalstructure.widgets.rte.*;
import com.smartgwt.logicalstructure.widgets.ace.*;
import com.smartgwt.logicalstructure.widgets.tab.*;
import com.smartgwt.logicalstructure.widgets.tableview.*;
import com.smartgwt.logicalstructure.widgets.toolbar.*;
import com.smartgwt.logicalstructure.widgets.tree.*;
import com.smartgwt.logicalstructure.widgets.viewer.*;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;

import com.smartgwt.logicalstructure.widgets.calendar.*;
import com.smartgwt.logicalstructure.widgets.cube.*;
import com.smartgwt.logicalstructure.widgets.tools.*;

/**
 * Base class for all Smart GWT visual components (except {@link com.smartgwt.client.widgets.form.fields.FormItem
 * FormItems}). <p> Canvas provides: <ul> <li> basic visual lifecycle support - creation and destruction of HTML via     
 * {@link com.smartgwt.client.widgets.Canvas#draw draw()} and {@link com.smartgwt.client.widgets.Canvas#clear clear()},
 * visibility via      {@link com.smartgwt.client.widgets.Canvas#show show()} and {@link
 * com.smartgwt.client.widgets.Canvas#hide hide()}, z-layering via      {@link
 * com.smartgwt.client.widgets.Canvas#bringToFront bringToFront()} and {@link com.smartgwt.client.widgets.Canvas#sendToBack
 * sendToBack()}. <li> consistent cross-browser {@link com.smartgwt.client.widgets.Canvas#moveTo positioning},      {@link
 * com.smartgwt.client.widgets.Canvas#resizeTo sizing} and {@link com.smartgwt.client.widgets.Canvas#getScrollHeight size
 * detection}, with      automatic compensation for {@link com.smartgwt.client.docs.CSSStyleName browser CSS behavior
 * differences}. <li> clipping, scrolling and overflow management of content via {@link
 * com.smartgwt.client.widgets.Canvas#getOverflow overflow} <li> consistent cross-browser {@link
 * com.smartgwt.client.widgets.Canvas#addKeyPressHandler key} and {@link
 * com.smartgwt.client.widgets.Canvas#addMouseDownHandler mouse}      events, including {@link
 * com.smartgwt.client.docs.MobileDevelopment mapping touch events} to mouse events <li> built-in drag and drop
 * capabilities including {@link com.smartgwt.client.widgets.Canvas#getCanDragReposition moving},       {@link
 * com.smartgwt.client.widgets.Canvas#getCanDragResize resizing}, {@link
 * com.smartgwt.client.widgets.Canvas#getCanDragScroll drag scrolling}      and {@link
 * com.smartgwt.client.widgets.Canvas#getSnapToGrid snap-to-grid} behavior. <li> the ability to either contain {@link
 * com.smartgwt.client.widgets.Canvas#getContents HTML content} or      {@link
 * com.smartgwt.client.widgets.Canvas#getChildren contain other Canvases}, including       {@link
 * com.smartgwt.client.widgets.Canvas#getSnapTo an edge-based positioning} and       {@link
 * com.smartgwt.client.widgets.Canvas#getPercentSource percent sizing system} for children.  For more advanced layout     
 * capabilities, see {@link com.smartgwt.client.widgets.layout.Layout}. <li> various other details like {@link
 * com.smartgwt.client.widgets.Canvas#getCursor cursors},      {@link com.smartgwt.client.widgets.Canvas#showClickMask
 * modal masking}, {@link com.smartgwt.client.widgets.Canvas#animateMove animation},      {@link
 * com.smartgwt.client.widgets.Canvas#getAriaRole accessibility properties}, and      {@link
 * com.smartgwt.client.widgets.Canvas#getLocateChildrenBy settings} for {@link com.smartgwt.client.docs.AutomatedTesting
 * automated testing}. </ul>
 */
@BeanFactory.FrameworkClass
@BeanFactory.ScClassName("Canvas")
public class Canvas extends BaseWidget implements com.smartgwt.client.widgets.events.HasClickHandlers, com.smartgwt.client.widgets.events.HasDoubleClickHandlers, com.smartgwt.client.widgets.events.HasDragMoveHandlers, com.smartgwt.client.widgets.events.HasDragRepositionMoveHandlers, com.smartgwt.client.widgets.events.HasDragRepositionStartHandlers, com.smartgwt.client.widgets.events.HasDragRepositionStopHandlers, com.smartgwt.client.widgets.events.HasDragResizeMoveHandlers, com.smartgwt.client.widgets.events.HasDragResizeStartHandlers, com.smartgwt.client.widgets.events.HasDragResizeStopHandlers, com.smartgwt.client.widgets.events.HasDragStartHandlers, com.smartgwt.client.widgets.events.HasDragStopHandlers, com.smartgwt.client.widgets.events.HasDropMoveHandlers, com.smartgwt.client.widgets.events.HasDropOutHandlers, com.smartgwt.client.widgets.events.HasDropOverHandlers, com.smartgwt.client.widgets.events.HasFocusChangedHandlers, com.smartgwt.client.widgets.events.HasHoverHandlers, com.smartgwt.client.widgets.events.HasHoverHiddenHandlers, com.smartgwt.client.widgets.events.HasKeyDownHandlers, com.smartgwt.client.widgets.events.HasKeyPressHandlers, com.smartgwt.client.widgets.events.HasMouseDownHandlers, com.smartgwt.client.widgets.events.HasMouseMoveHandlers, com.smartgwt.client.widgets.events.HasMouseOutHandlers, com.smartgwt.client.widgets.events.HasMouseOverHandlers, com.smartgwt.client.widgets.events.HasMouseStillDownHandlers, com.smartgwt.client.widgets.events.HasMouseUpHandlers, com.smartgwt.client.widgets.events.HasMouseWheelHandlers, com.smartgwt.client.widgets.events.HasMovedHandlers, com.smartgwt.client.widgets.events.HasClearHandlers, com.smartgwt.client.widgets.events.HasDropHandlers, com.smartgwt.client.widgets.events.HasParentMovedHandlers, com.smartgwt.client.widgets.events.HasResizedHandlers, com.smartgwt.client.widgets.events.HasRightMouseDownHandlers, com.smartgwt.client.widgets.events.HasRuleContextChangedHandlers, com.smartgwt.client.widgets.events.HasScrolledHandlers, com.smartgwt.client.widgets.events.HasShowContextMenuHandlers, com.smartgwt.client.widgets.events.HasVisibilityChangedHandlers {

    public static Canvas getOrCreateRef(JavaScriptObject jsObj) {
        if (jsObj == null) return null;
        final BaseWidget refInstance = BaseWidget.getRef(jsObj);
        if (refInstance == null) {
            return new Canvas(jsObj);
        } else {
            assert refInstance instanceof Canvas;
            return (Canvas)refInstance;
        }
    }
    /** 
     * This method returns a Map of config properties suitable for use as the "defaults" 
     * attribute of a {@link com.smartgwt.client.tools.PaletteNode}.  Use it when you need to
     * work with PaletteNodes indirectly, such when setting up 
     * {@link com.smartgwt.client.widgets.tile.TileRecord}s that will be used in a 
     * {@link com.smartgwt.client.tools.TilePalette}.  See 
     * {@link com.smartgwt.client.docs.DevTools the dev tools overview} for examples of how to
     * assemble and acquire a suitable defaults object when you are creating a PaletteNode 
     * indirectly
     */
    public Map getPaletteDefaults() {
        if (isCreated()) {
            ConfigUtil.warnOfPreConfigInstantiation(this.getClass(), "getPaletteDefaults", (String)null);
        }
        return JSOHelper.convertToMap(JSOHelper.cleanProperties(getConfig(), true));
    }
        


    /**
     * Changes the defaults for Canvas AutoChildren named <code>autoChildName</code>.
     *
     * @param autoChildName name of an AutoChild to customize the defaults for.
     * @param defaults Canvas defaults to apply. These defaults override any existing properties
     * without destroying or wiping out non-overridden properties.  For usage tips on this
     * param, see {@link com.smartgwt.client.docs.SGWTProperties}.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public static native void changeAutoChildDefaults(String autoChildName, Canvas defaults) /*-{
        if (defaults.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPreConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(Canvas.@java.lang.Object::getClass()(), "changeAutoChildDefaults", "Canvas");
        }
        defaults.@com.smartgwt.client.widgets.BaseWidget::setConfigOnly(Z)(true);
        var cleanDefaultsJS = @com.smartgwt.client.util.JSOHelper::cleanProperties(Lcom/google/gwt/core/client/JavaScriptObject;Z)(defaults.@com.smartgwt.client.widgets.BaseWidget::getConfig()(), true);
        $wnd.isc.Canvas.changeDefaults(autoChildName + "Defaults", cleanDefaultsJS);
    }-*/;

    /**
     * Changes the defaults for FormItem AutoChildren named <code>autoChildName</code>.
     *
     * @param autoChildName name of an AutoChild to customize the defaults for.
     * @param defaults FormItem defaults to apply. These defaults override any existing properties
     * without destroying or wiping out non-overridden properties.  For usage tips on this
     * param, see {@link com.smartgwt.client.docs.SGWTProperties}.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public static native void changeAutoChildDefaults(String autoChildName, FormItem defaults) /*-{
        if (defaults.@com.smartgwt.client.widgets.form.fields.FormItem::isCreated()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPreConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(Canvas.@java.lang.Object::getClass()(), "changeAutoChildDefaults", "FormItem");
        }
        defaults.@com.smartgwt.client.widgets.form.fields.FormItem::setConfigOnly(Z)(true);
    	var cleanDefaultsJS = defaults.@com.smartgwt.client.widgets.form.fields.FormItem::getEditorTypeConfig()();
        $wnd.isc.Canvas.changeDefaults(autoChildName + "Defaults", cleanDefaultsJS);
    }-*/;

    public Canvas(){
    	SC.setScreenReaderMode(UserInterfaceFactory.isAttivaAccessibilita());
        scClassName = "Canvas";
    }

    public Canvas(JavaScriptObject jsObj){
		SC.setScreenReaderMode(UserInterfaceFactory.isAttivaAccessibilita());
        scClassName = "Canvas";
        setJavaScriptObject(jsObj);
    }


    public Canvas(String id) {
        super(id);
        scClassName = "Canvas";
        SC.setScreenReaderMode(UserInterfaceFactory.isAttivaAccessibilita());
    }

    protected native JavaScriptObject create()/*-{
        var config = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
        var widget = $wnd.isc[scClassName].create(config);
        if ($wnd.isc.keepGlobals) this.@com.smartgwt.client.widgets.BaseWidget::internalSetID(Lcom/google/gwt/core/client/JavaScriptObject;)(widget);
        this.@com.smartgwt.client.widgets.BaseWidget::doInit()();
        return widget;
    }-*/;

    // ********************* Properties / Attributes ***********************

    /**
     * If specified this governs the HTML accessKey for the widget. <P> This should be set to a character - when a user hits
     * the html accessKey modifier for the browser, plus this character, focus will be given to the widget in question. The
     * accessKey modifier can vary by browser and platform.  <P> The following list of default behavior is for reference only,
     * developers should also consult browser documentation for additional information. <ul> <li><b>Internet Explorer (all
     * platforms)</b>: <code>Alt</code> + <i>accessKey</i></li> <li><b>Mozilla Firefox (Windows, Unix)</b>:
     * <code>Alt+Shift</code> + <i>accessKey</i></li> <li><b>Mozilla Firefox (Mac)</b>: <code>Ctrl+Opt</code> +
     * <i>accessKey</i></li> <li><b>Chrome and Safari (Windows, Unix)</b>:  <code>Alt</code> + <i>accessKey</i></li>
     * <li><b>Chrome and Safari (Mac)</b>:  <code>Ctrl+Opt</code> + <i>accessKey</i></li> </ul>
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the accessKey for this canvas. <P> The accessKey can be set to any alphanumeric character (symbols not supported) Having set an accessKey, the canvas will be given focus when the user hits Alt+[accessKey], or in Mozilla Firefox 2.0 and above, Shift+Alt+[accessKey].
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param accessKey Character to use as an accessKey for this widget.  Case Insensitive. Default value is null
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public void setAccessKey(String accessKey) {
        setAttribute("accessKey", accessKey, true);
    }

    /**
     * If specified this governs the HTML accessKey for the widget. <P> This should be set to a character - when a user hits
     * the html accessKey modifier for the browser, plus this character, focus will be given to the widget in question. The
     * accessKey modifier can vary by browser and platform.  <P> The following list of default behavior is for reference only,
     * developers should also consult browser documentation for additional information. <ul> <li><b>Internet Explorer (all
     * platforms)</b>: <code>Alt</code> + <i>accessKey</i></li> <li><b>Mozilla Firefox (Windows, Unix)</b>:
     * <code>Alt+Shift</code> + <i>accessKey</i></li> <li><b>Mozilla Firefox (Mac)</b>: <code>Ctrl+Opt</code> +
     * <i>accessKey</i></li> <li><b>Chrome and Safari (Windows, Unix)</b>:  <code>Alt</code> + <i>accessKey</i></li>
     * <li><b>Chrome and Safari (Mac)</b>:  <code>Ctrl+Opt</code> + <i>accessKey</i></li> </ul>
     *
     * @return Current accessKey value. Default value is null
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public String getAccessKey()  {
        return getAttributeAsString("accessKey");
    }
    

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getAdaptiveWidthPriority adaptiveWidthPriority}.
     *
     * @param adaptiveHeightPriority New adaptiveHeightPriority value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setAdaptiveHeightPriority(Integer adaptiveHeightPriority)  throws IllegalStateException {
        setAttribute("adaptiveHeightPriority", adaptiveHeightPriority, false);
    }

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getAdaptiveWidthPriority adaptiveWidthPriority}.
     *
     * @return Current adaptiveHeightPriority value. Default value is null
     */
    public Integer getAdaptiveHeightPriority()  {
        return getAttributeAsInt("adaptiveHeightPriority");
    }
    

    /**
     * If multiple widgets in the same Layout have {@link com.smartgwt.client.widgets.Canvas#getCanAdaptWidth adaptive width},
     * <code>adaptiveWidthPriority</code> can be set to indicate which of the components should be  given priority access to
     * space. <p> The widget with the highest priority setting will be offered surplus space first, and asked to give up space
     * last.  Lack of a priority setting is treated as zero.  Any adaptive widgets with the same priority setting will be asked
     * to give up or release space according to their order in {@link com.smartgwt.client.widgets.layout.Layout#getMembers
     * Layout.members}.
     *
     * @param adaptiveWidthPriority New adaptiveWidthPriority value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setCanAdaptWidth
     */
    public void setAdaptiveWidthPriority(Integer adaptiveWidthPriority)  throws IllegalStateException {
        setAttribute("adaptiveWidthPriority", adaptiveWidthPriority, false);
    }

    /**
     * If multiple widgets in the same Layout have {@link com.smartgwt.client.widgets.Canvas#getCanAdaptWidth adaptive width},
     * <code>adaptiveWidthPriority</code> can be set to indicate which of the components should be  given priority access to
     * space. <p> The widget with the highest priority setting will be offered surplus space first, and asked to give up space
     * last.  Lack of a priority setting is treated as zero.  Any adaptive widgets with the same priority setting will be asked
     * to give up or release space according to their order in {@link com.smartgwt.client.widgets.layout.Layout#getMembers
     * Layout.members}.
     *
     * @return Current adaptiveWidthPriority value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getCanAdaptWidth
     */
    public Integer getAdaptiveWidthPriority()  {
        return getAttributeAsInt("adaptiveWidthPriority");
    }
    

    /**
     * On {@link com.smartgwt.client.util.Browser#isTouch touch devices} that support native touch scrolling, if {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars showCustomScrollbars} is <code>true</code> and touch
     * scrolling has not been disabled by the {@link com.smartgwt.client.widgets.Canvas#getUseTouchScrolling useTouchScrolling}
     * and/or {@link com.smartgwt.client.widgets.Canvas#getDisableTouchScrollingForDrag disableTouchScrollingForDrag} settings,
     * should custom scrollbars <em>and</em> native touch scrolling be enabled for this component? If <code>false</code> or
     * unset, then only native touch scrolling will be enabled. If <code>true</code>, then both scrolling mechanisms will be
     * enabled. <p> <strong>NOTE:</strong> Because native touch scrolling (also called momentum scrolling) is computationally
     * intensive, some mobile browsers implement an optimization where the state of the DOM for the element being scrolled will
     * be frozen or partially frozen during the scroll animation. This results in a delay between when the scroll position
     * reaches a certain point in the animation and when the positions of the custom scrollbar thumbs are updated to reflect
     * that scroll position.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param alwaysShowScrollbars New alwaysShowScrollbars value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setAlwaysShowScrollbars(Boolean alwaysShowScrollbars)  throws IllegalStateException {
        setAttribute("alwaysShowScrollbars", alwaysShowScrollbars, false);
    }

    /**
     * On {@link com.smartgwt.client.util.Browser#isTouch touch devices} that support native touch scrolling, if {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars showCustomScrollbars} is <code>true</code> and touch
     * scrolling has not been disabled by the {@link com.smartgwt.client.widgets.Canvas#getUseTouchScrolling useTouchScrolling}
     * and/or {@link com.smartgwt.client.widgets.Canvas#getDisableTouchScrollingForDrag disableTouchScrollingForDrag} settings,
     * should custom scrollbars <em>and</em> native touch scrolling be enabled for this component? If <code>false</code> or
     * unset, then only native touch scrolling will be enabled. If <code>true</code>, then both scrolling mechanisms will be
     * enabled. <p> <strong>NOTE:</strong> Because native touch scrolling (also called momentum scrolling) is computationally
     * intensive, some mobile browsers implement an optimization where the state of the DOM for the element being scrolled will
     * be frozen or partially frozen during the scroll animation. This results in a delay between when the scroll position
     * reaches a certain point in the animation and when the positions of the custom scrollbar thumbs are updated to reflect
     * that scroll position.
     *
     * @return Current alwaysShowScrollbars value. Default value is null
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public Boolean getAlwaysShowScrollbars()  {
        return getAttributeAsBoolean("alwaysShowScrollbars");
    }
    

    /**
     * Default acceleration effect to apply to all animations on this Canvas. Can be overridden by setting
     * animationAcceleration for specific animations or by passing an acceleration function directly into the appropriate
     * method.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateAcceleration New animateAcceleration value. Default value is "smoothEnd"
     */
    public void setAnimateAcceleration(AnimationAcceleration animateAcceleration) {
        setAttribute("animateAcceleration", animateAcceleration == null ? null : animateAcceleration.getValue(), true);
    }

    /**
     * Default acceleration effect to apply to all animations on this Canvas. Can be overridden by setting
     * animationAcceleration for specific animations or by passing an acceleration function directly into the appropriate
     * method.
     *
     * @return Current animateAcceleration value. Default value is "smoothEnd"
     */
    public AnimationAcceleration getAnimateAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateAcceleration"));
    }
    

    /**
     * Default time for performing an animated fade.  If unset, <code>this.animateTime</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateFadeTime New animateFadeTime value. Default value is null
     */
    public void setAnimateFadeTime(Integer animateFadeTime) {
        setAttribute("animateFadeTime", animateFadeTime, true);
    }

    /**
     * Default time for performing an animated fade.  If unset, <code>this.animateTime</code> will be used by default instead
     *
     * @return Current animateFadeTime value. Default value is null
     */
    public Integer getAnimateFadeTime()  {
        return getAttributeAsInt("animateFadeTime");
    }
    

    /**
     * Default acceleration function for performing an animated hide.  If unset,  <code>this.animateAcceleration</code> will be
     * used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateHideAcceleration New animateHideAcceleration value. Default value is null
     */
    public void setAnimateHideAcceleration(AnimationAcceleration animateHideAcceleration) {
        setAttribute("animateHideAcceleration", animateHideAcceleration == null ? null : animateHideAcceleration.getValue(), true);
    }

    /**
     * Default acceleration function for performing an animated hide.  If unset,  <code>this.animateAcceleration</code> will be
     * used by default instead
     *
     * @return Current animateHideAcceleration value. Default value is null
     */
    public AnimationAcceleration getAnimateHideAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateHideAcceleration"));
    }
    
    

    /**
     * Default time for performing an animated hide.  If unset, <code>this.animateTime</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateHideTime New animateHideTime value. Default value is null
     */
    public void setAnimateHideTime(Integer animateHideTime) {
        setAttribute("animateHideTime", animateHideTime, true);
    }

    /**
     * Default time for performing an animated hide.  If unset, <code>this.animateTime</code> will be used by default instead
     *
     * @return Current animateHideTime value. Default value is null
     */
    public Integer getAnimateHideTime()  {
        return getAttributeAsInt("animateHideTime");
    }
    

    /**
     * Default acceleration effect for performing an animated move.  If unset,  <code>this.animateAcceleration</code> will be
     * used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateMoveAcceleration New animateMoveAcceleration value. Default value is null
     */
    public void setAnimateMoveAcceleration(AnimationAcceleration animateMoveAcceleration) {
        setAttribute("animateMoveAcceleration", animateMoveAcceleration == null ? null : animateMoveAcceleration.getValue(), true);
    }

    /**
     * Default acceleration effect for performing an animated move.  If unset,  <code>this.animateAcceleration</code> will be
     * used by default instead
     *
     * @return Current animateMoveAcceleration value. Default value is null
     */
    public AnimationAcceleration getAnimateMoveAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateMoveAcceleration"));
    }
    

    /**
     * Default time for performing an animated move.  If unset, <code>this.animateTime</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateMoveTime New animateMoveTime value. Default value is null
     */
    public void setAnimateMoveTime(Integer animateMoveTime) {
        setAttribute("animateMoveTime", animateMoveTime, true);
    }

    /**
     * Default time for performing an animated move.  If unset, <code>this.animateTime</code> will be used by default instead
     *
     * @return Current animateMoveTime value. Default value is null
     */
    public Integer getAnimateMoveTime()  {
        return getAttributeAsInt("animateMoveTime");
    }
    

    /**
     * Default acceleration function for performing an animated move and resize.  If unset, 
     * <code>this.animateAcceleration</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateRectAcceleration New animateRectAcceleration value. Default value is null
     */
    public void setAnimateRectAcceleration(AnimationAcceleration animateRectAcceleration) {
        setAttribute("animateRectAcceleration", animateRectAcceleration == null ? null : animateRectAcceleration.getValue(), true);
    }

    /**
     * Default acceleration function for performing an animated move and resize.  If unset, 
     * <code>this.animateAcceleration</code> will be used by default instead
     *
     * @return Current animateRectAcceleration value. Default value is null
     */
    public AnimationAcceleration getAnimateRectAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateRectAcceleration"));
    }
    

    /**
     * Default time for performing an animated setRect.  If unset, <code>this.animateTime</code> will be used by default
     * instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateRectTime New animateRectTime value. Default value is null
     */
    public void setAnimateRectTime(Integer animateRectTime) {
        setAttribute("animateRectTime", animateRectTime, true);
    }

    /**
     * Default time for performing an animated setRect.  If unset, <code>this.animateTime</code> will be used by default
     * instead
     *
     * @return Current animateRectTime value. Default value is null
     */
    public Integer getAnimateRectTime()  {
        return getAttributeAsInt("animateRectTime");
    }
    

    /**
     * Default acceleration function for performing an animated resize.  If unset,  <code>this.animateAcceleration</code> will
     * be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateResizeAcceleration New animateResizeAcceleration value. Default value is null
     */
    public void setAnimateResizeAcceleration(AnimationAcceleration animateResizeAcceleration) {
        setAttribute("animateResizeAcceleration", animateResizeAcceleration == null ? null : animateResizeAcceleration.getValue(), true);
    }

    /**
     * Default acceleration function for performing an animated resize.  If unset,  <code>this.animateAcceleration</code> will
     * be used by default instead
     *
     * @return Current animateResizeAcceleration value. Default value is null
     */
    public AnimationAcceleration getAnimateResizeAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateResizeAcceleration"));
    }
    

    /**
     * Default time for performing an animated resize.  If unset, <code>this.animateTime</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateResizeTime New animateResizeTime value. Default value is null
     */
    public void setAnimateResizeTime(Integer animateResizeTime) {
        setAttribute("animateResizeTime", animateResizeTime, true);
    }

    /**
     * Default time for performing an animated resize.  If unset, <code>this.animateTime</code> will be used by default instead
     *
     * @return Current animateResizeTime value. Default value is null
     */
    public Integer getAnimateResizeTime()  {
        return getAttributeAsInt("animateResizeTime");
    }
    

    /**
     * Default acceleration function for performing an animated scroll.  If unset,  <code>this.animateAcceleration</code> will
     * be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateScrollAcceleration New animateScrollAcceleration value. Default value is null
     */
    public void setAnimateScrollAcceleration(AnimationAcceleration animateScrollAcceleration) {
        setAttribute("animateScrollAcceleration", animateScrollAcceleration == null ? null : animateScrollAcceleration.getValue(), true);
    }

    /**
     * Default acceleration function for performing an animated scroll.  If unset,  <code>this.animateAcceleration</code> will
     * be used by default instead
     *
     * @return Current animateScrollAcceleration value. Default value is null
     */
    public AnimationAcceleration getAnimateScrollAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateScrollAcceleration"));
    }
    

    /**
     * Default time for performing an animated scroll.  If unset, <code>this.animateTime</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateScrollTime New animateScrollTime value. Default value is null
     */
    public void setAnimateScrollTime(Integer animateScrollTime) {
        setAttribute("animateScrollTime", animateScrollTime, true);
    }

    /**
     * Default time for performing an animated scroll.  If unset, <code>this.animateTime</code> will be used by default instead
     *
     * @return Current animateScrollTime value. Default value is null
     */
    public Integer getAnimateScrollTime()  {
        return getAttributeAsInt("animateScrollTime");
    }
    

    /**
     * Default acceleration function for performing an animated show.  If unset,  <code>this.animateAcceleration</code> will be
     * used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateShowAcceleration New animateShowAcceleration value. Default value is null
     */
    public void setAnimateShowAcceleration(AnimationAcceleration animateShowAcceleration) {
        setAttribute("animateShowAcceleration", animateShowAcceleration == null ? null : animateShowAcceleration.getValue(), true);
    }

    /**
     * Default acceleration function for performing an animated show.  If unset,  <code>this.animateAcceleration</code> will be
     * used by default instead
     *
     * @return Current animateShowAcceleration value. Default value is null
     */
    public AnimationAcceleration getAnimateShowAcceleration()  {
        return EnumUtil.getEnum(AnimationAcceleration.values(), getAttribute("animateShowAcceleration"));
    }
    
    

    /**
     * Default time for performing an animated show.  If unset, <code>this.animateTime</code> will be used by default instead
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateShowTime New animateShowTime value. Default value is null
     */
    public void setAnimateShowTime(Integer animateShowTime) {
        setAttribute("animateShowTime", animateShowTime, true);
    }

    /**
     * Default time for performing an animated show.  If unset, <code>this.animateTime</code> will be used by default instead
     *
     * @return Current animateShowTime value. Default value is null
     */
    public Integer getAnimateShowTime()  {
        return getAttributeAsInt("animateShowTime");
    }
    

    /**
     * Default total duration of animations. Can be overridden by setting animation times for specific animations, or by
     * passing a <code>duration</code> parameter into the appropriate animate...() method.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param animateTime New animateTime value. Default value is 300
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_move" target="examples">Fly Onscreen Example</a>
     */
    public void setAnimateTime(int animateTime) {
        setAttribute("animateTime", animateTime, true);
    }

    /**
     * Default total duration of animations. Can be overridden by setting animation times for specific animations, or by
     * passing a <code>duration</code> parameter into the appropriate animate...() method.
     *
     * @return Current animateTime value. Default value is 300
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_move" target="examples">Fly Onscreen Example</a>
     */
    public int getAnimateTime()  {
        return getAttributeAsInt("animateTime");
    }
    

    /**
     * Default directory for app-specific images, relative to the Page-wide {@link com.smartgwt.client.util.Page#getAppImgDir
     * appImgDir}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param appImgDir New appImgDir value. Default value is ""
     * @see com.smartgwt.client.docs.URL URL 
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public void setAppImgDir(String appImgDir) {
        setAttribute("appImgDir", appImgDir, true);
    }

    /**
     * Default directory for app-specific images, relative to the Page-wide {@link com.smartgwt.client.util.Page#getAppImgDir
     * appImgDir}.
     *
     * @return Current appImgDir value. Default value is ""
     * @see com.smartgwt.client.docs.URL URL 
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public String getAppImgDir()  {
        return getAttributeAsString("appImgDir");
    }
    

    /**
     * ARIA role of this component.  Usually does not need to be manually set - see {@link
     * com.smartgwt.client.docs.Accessibility}.
     *
     * @param ariaRole New ariaRole value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Accessibility Accessibility overview and related methods
     */
    public void setAriaRole(String ariaRole)  throws IllegalStateException {
        setAttribute("ariaRole", ariaRole, false);
    }

    /**
     * ARIA role of this component.  Usually does not need to be manually set - see {@link
     * com.smartgwt.client.docs.Accessibility}.
     *
     * @return Current ariaRole value. Default value is null
     * @see com.smartgwt.client.docs.Accessibility Accessibility overview and related methods
     */
    public String getAriaRole()  {
        return getAttributeAsString("ariaRole");
    }
    
    /**
     * If true, this canvas will draw itself immediately after it is created.
     *  <P>
     *  <b>Note</b> that you should turn this OFF for any canvases that are provided as children
     *  of other canvases, or they will draw initially, then be clear()ed and drawn again when
     *  added as children, causing a large performance penalty.  
     *  <P>
     *  For example, the following code is incorrect and will cause extra draw()s:
     *  <P>
     *  <pre>
     *      isc.Layout.create({
     *          members : [
     *              isc.ListGrid.create()
     *          ]
     *      });
     *  </pre>
     *  It should instead be:
     *  <pre>
     *      isc.Layout.create({
     *          members : [
     *              isc.ListGrid.create(<b>{ autoDraw: false }</b>)
     *          ]
     *      });
     *  </pre>
     *  In order to avoid unwanted autoDrawing systematically, it is recommend that you call
     *  {@link com.smartgwt.client.util.isc#setAutoDraw isc.setAutoDraw(false)} immediately after Smart GWT is loaded
     *  and before any components are created, then set <code>autoDraw:true</code> or call
     *  draw() explicitly to draw components.  
     *  <P>
     *  Otherwise, if the global setting for autoDraw remains <code>true</code>, you must set
     *  autoDraw:false, as shown above, on every component in your application that 
     *  should not immediately draw: all Canvas children, Layout members, Window items, Tab
     *  panes, etc, however deeply nested.  Forgetting to set autoDraw:false will result in one
     *  more clear()s - these are reported on the Results tab of the 
     *  {@link com.smartgwt.client.docs.Debugging Developer Console}, and can be tracked to individual components by
     *  using the "clears" log category in the Developer Console.
     *
     * @param autoDraw autoDraw Default value is true
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     * 
     */
    public void setAutoDraw(Boolean autoDraw)  throws IllegalStateException {
    	setAttribute("autoDraw", autoDraw, false); //TODO perchè è stata tolto?
    }

    /**
     * If true, this canvas will draw itself immediately after it is created.
     *  <P>
     *  <b>Note</b> that you should turn this OFF for any canvases that are provided as children
     *  of other canvases, or they will draw initially, then be clear()ed and drawn again when
     *  added as children, causing a large performance penalty.  
     *  <P>
     *  For example, the following code is incorrect and will cause extra draw()s:
     *  <P>
     *  <pre>
     *      isc.Layout.create({
     *          members : [
     *              isc.ListGrid.create()
     *          ]
     *      });
     *  </pre>
     *  It should instead be:
     *  <pre>
     *      isc.Layout.create({
     *          members : [
     *              isc.ListGrid.create(<b>{ autoDraw: false }</b>)
     *          ]
     *      });
     *  </pre>
     *  In order to avoid unwanted autoDrawing systematically, it is recommend that you call
     *  {@link com.smartgwt.client.util.isc#setAutoDraw isc.setAutoDraw(false)} immediately after Smart GWT is loaded
     *  and before any components are created, then set <code>autoDraw:true</code> or call
     *  draw() explicitly to draw components.  
     *  <P>
     *  Otherwise, if the global setting for autoDraw remains <code>true</code>, you must set
     *  autoDraw:false, as shown above, on every component in your application that 
     *  should not immediately draw: all Canvas children, Layout members, Window items, Tab
     *  panes, etc, however deeply nested.  Forgetting to set autoDraw:false will result in one
     *  more clear()s - these are reported on the Results tab of the 
     *  {@link com.smartgwt.client.docs.Debugging Developer Console}, and can be tracked to individual components by
     *  using the "clears" log category in the Developer Console.
     *
     *
     * @return Boolean
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     * 
     */
    public Boolean getAutoDraw()  {
        return getAttributeAsBoolean("autoDraw");
    }

    /**
     * When nodes are added to an EditContext, should they be masked by setting {@link
     * com.smartgwt.client.tools.EditProxy#getUseEditMask EditProxy.useEditMask} <code>true</code> if not explicitly set?
     *
     * @param autoMaskComponents New autoMaskComponents value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @deprecated As of Smart GWT version 10.0, deprecated in favor of {@link com.smartgwt.client.tools.EditProxy#getAutoMaskChildren
     * EditProxy.autoMaskChildren}
     */
    public void setAutoMaskComponents(Boolean autoMaskComponents)  throws IllegalStateException {
        setAttribute("autoMaskComponents", autoMaskComponents, false);
    }

    /**
     * When nodes are added to an EditContext, should they be masked by setting {@link
     * com.smartgwt.client.tools.EditProxy#getUseEditMask EditProxy.useEditMask} <code>true</code> if not explicitly set?
     *
     * @return Current autoMaskComponents value. Default value is null
     * @deprecated As of Smart GWT version 10.0, deprecated in favor of {@link com.smartgwt.client.tools.EditProxy#getAutoMaskChildren
     * EditProxy.autoMaskChildren}
     */
    public Boolean getAutoMaskComponents()  {
        return getAttributeAsBoolean("autoMaskComponents");
    }
    

    /**
     * This initialization property allows developers to create a canvas using the  Class.addAutoChild() method, and have it
     * added as a child to some other component.  This property may be set to the <code><i>childName</i></code> of another
     * already-created auto-child, or <code>"none"</code> to cause the component to be  created without being added as a child
     * to any other widget.<br> If unset, the canvas will be added as a child to the component on which
     * <code>addAutoChild(...)</code> was called. <P> See {@link autoChildren} for an overview of the autoChild subsystem.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param autoParent New autoParent value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setAutoParent(String autoParent)  throws IllegalStateException {
        setAttribute("autoParent", autoParent, false);
    }

    /**
     * This initialization property allows developers to create a canvas using the  Class.addAutoChild() method, and have it
     * added as a child to some other component.  This property may be set to the <code><i>childName</i></code> of another
     * already-created auto-child, or <code>"none"</code> to cause the component to be  created without being added as a child
     * to any other widget.<br> If unset, the canvas will be added as a child to the component on which
     * <code>addAutoChild(...)</code> was called. <P> See {@link autoChildren} for an overview of the autoChild subsystem.
     *
     * @return Current autoParent value. Default value is null
     */
    public String getAutoParent()  {
        return getAttributeAsString("autoParent");
    }
    

    /**
     * If set to true, the widget's parent (if any) will automatically be shown whenever the      widget is shown.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param autoShowParent New autoShowParent value. Default value is false
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setAutoShowParent(Boolean autoShowParent) {
        setAttribute("autoShowParent", autoShowParent, true);
    }

    /**
     * If set to true, the widget's parent (if any) will automatically be shown whenever the      widget is shown.
     *
     * @return Current autoShowParent value. Default value is false
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public Boolean getAutoShowParent()  {
        Boolean result = getAttributeAsBoolean("autoShowParent");
        return result == null ? false : result;
    }
    

    /**
     * The background color for this widget. It corresponds to the CSS background-color attribute. You can set this property to
     * an RGB value (e.g. #22AAFF) or a named color (e.g. red) from a list of browser supported color names.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Sets the background color of this component to <code>newColor</code>.
     *
     * @param backgroundColor new background color, or <code>null</code> to remove the current background color. Default value is null
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBackgroundColor(String backgroundColor) {
        setAttribute("backgroundColor", backgroundColor, true);
    }

    /**
     * The background color for this widget. It corresponds to the CSS background-color attribute. You can set this property to
     * an RGB value (e.g. #22AAFF) or a named color (e.g. red) from a list of browser supported color names.
     *
     * @return Current backgroundColor value. Default value is null
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getBackgroundColor()  {
        return getAttributeAsString("backgroundColor");
    }
    

    /**
     * URL for a background image for this widget (corresponding to the CSS "background-image" attribute).
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Sets the background to an image file given by newImage. This URL should be given as a          string relative to the image directory for the page (./images by default).
     *
     * @param backgroundImage new URL (local to Page image directory) for background image. Default value is null
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBackgroundImage(String backgroundImage) {
        setAttribute("backgroundImage", backgroundImage, true);
    }

    /**
     * URL for a background image for this widget (corresponding to the CSS "background-image" attribute).
     *
     * @return Current backgroundImage value. Default value is null
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getBackgroundImage()  {
        return getAttributeAsString("backgroundImage");
    }
    

    /**
     * Specifies how the background image should be positioned on the widget.      It corresponds to the CSS
     * background-position attribute. If unset,      no background-position attribute is specified if a background image is    
     *  specified.
     *
     * @param backgroundPosition New backgroundPosition value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBackgroundPosition(String backgroundPosition)  throws IllegalStateException {
        setAttribute("backgroundPosition", backgroundPosition, false);
    }

    /**
     * Specifies how the background image should be positioned on the widget.      It corresponds to the CSS
     * background-position attribute. If unset,      no background-position attribute is specified if a background image is    
     *  specified.
     *
     * @return Current backgroundPosition value. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getBackgroundPosition()  {
        return getAttributeAsString("backgroundPosition");
    }
    

    /**
     * Specifies how the background image should be tiled if this widget is larger than the image. It corresponds to the CSS
     * <code>background-repeat</code> attribute. <p> The default of null means no <code>background-repeat</code> CSS will be
     * written out.  See {@link com.smartgwt.client.types.BackgroundRepeat} type for details on other settings. <p> NOTE: this
     * setting directly sets the CSS property <code>background-repeat</code> but does not attempt to work around various known
     * bugs with this setting, or lack of support in IE6.  If you need to apply CSS-based workarounds for browser limitations
     * with this setting, it's best to do so via setting {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}.
     *
     * @param backgroundRepeat New backgroundRepeat value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBackgroundRepeat(BackgroundRepeat backgroundRepeat)  throws IllegalStateException {
        setAttribute("backgroundRepeat", backgroundRepeat == null ? null : backgroundRepeat.getValue(), false);
    }

    /**
     * Specifies how the background image should be tiled if this widget is larger than the image. It corresponds to the CSS
     * <code>background-repeat</code> attribute. <p> The default of null means no <code>background-repeat</code> CSS will be
     * written out.  See {@link com.smartgwt.client.types.BackgroundRepeat} type for details on other settings. <p> NOTE: this
     * setting directly sets the CSS property <code>background-repeat</code> but does not attempt to work around various known
     * bugs with this setting, or lack of support in IE6.  If you need to apply CSS-based workarounds for browser limitations
     * with this setting, it's best to do so via setting {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}.
     *
     * @return Current backgroundRepeat value. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public BackgroundRepeat getBackgroundRepeat()  {
        return EnumUtil.getEnum(BackgroundRepeat.values(), getAttribute("backgroundRepeat"));
    }
    

    /**
     * Set the CSS border of this component, as a CSS string including border-width, border-style, and/or color (eg "2px solid
     * blue"). <P> This property applies the same border to all four sides of this component.  Different per-side borders can
     * be set in a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <p> If {@link
     * com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is set to true then border is derived from the  {@link
     * com.smartgwt.client.widgets.Canvas#getGroupBorderCSS groupBorderCSS} attribute, not from the explicit border property.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the CSS border of this component, as a CSS string including border-width, border-style, and/or color (eg "2px solid blue"). <P> This property applies the same border to all four sides of this component.  Different per-side borders can be set in a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}.
     *
     * @param border new border to set to (eg: "2px solid black"). Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBorder(String border) {
        setAttribute("border", border, true);
    }

    /**
     * Set the CSS border of this component, as a CSS string including border-width, border-style, and/or color (eg "2px solid
     * blue"). <P> This property applies the same border to all four sides of this component.  Different per-side borders can
     * be set in a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <p> If {@link
     * com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is set to true then border is derived from the  {@link
     * com.smartgwt.client.widgets.Canvas#getGroupBorderCSS groupBorderCSS} attribute, not from the explicit border property.
     *
     * @return Current border value. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getBorder()  {
        return getAttributeAsString("border");
    }
    

    /**
     * Indicates that this object can receive dropped widgets (i.e. other widgets can be dropped on top of it).
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canAcceptDrop New canAcceptDrop value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public void setCanAcceptDrop(Boolean canAcceptDrop) {
        setAttribute("canAcceptDrop", canAcceptDrop, true);
    }

    /**
     * Indicates that this object can receive dropped widgets (i.e. other widgets can be dropped on top of it).
     *
     * @return Current canAcceptDrop value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public Boolean getCanAcceptDrop()  {
        Boolean result = getAttributeAsBoolean("canAcceptDrop");
        return result == null ? false : result;
    }
    

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getCanAdaptWidth canAdaptWidth}.
     *
     * @param canAdaptHeight New canAdaptHeight value. Default value is null
     */
    public void setCanAdaptHeight(Boolean canAdaptHeight) {
        setAttribute("canAdaptHeight", canAdaptHeight, true);
    }

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getCanAdaptWidth canAdaptWidth}.
     *
     * @return Current canAdaptHeight value. Default value is null
     */
    public Boolean getCanAdaptHeight()  {
        return getAttributeAsBoolean("canAdaptHeight");
    }
    

    /**
     * Indicates that this component is able to render itself at multiple possible sizes, in order to fit into less space when
     * very little space is available, or to display more information or provide more immediate access to functionality when
     * more space is available. <p> Unlike simply indicating a flexible size via width:"*", setting <code>canAdaptWidth</code>
     * indicates that a component has 2 (or more) different ways of rendering itself with different <i>discrete</i> sizes, but
     * does not have the ability to use every additional available pixel. <p> For example, a menu embedded in a toolbar might
     * show as only a fixed-size icon which reveals menu options when clicked, or if enough space is available, could show all
     * of the menu options directly in the toolbar.  In this example, the menu should either be allocated just enough space for
     * an icon, or enough space for <i>all</i> options to appear inline; any other amount of space being allocated is just a
     * waste. <p> When a component with <code>canAdaptWidth</code> is part of a Layout, {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()} will be called
     * every time component sizes are being calculated, passing a positive or negative pixel value:   <ul> <li> a
     * <i>positive</i> pixel value indicates a surplus - all other component's minimum sizes      can be accommodated,
     * including any {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth} declared on the     
     * <code>canAdaptWidth</code> component itself <li> a <i>negative</i> pixel value indicates that the containing layout is
     * going to be      forced to {@link com.smartgwt.client.types.Overflow} (introduce scrollbars, clip content, etc) unless
     * some      component or set of components gives up at least this many pixels </ul> <p> If a non-zero value is returned
     * from <code>adaptWidthBy</code>, this means that the component is willing to shrink or expand by that many pixels. 
     * Returning 0 means that the component is unable to reduce size further, or for a surplus, cannot make good use of the
     * surplus space. <p><ul> <li>A call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} may surrender as many pixels as desired (as long as the widget won't drop below
     * its minimum allowed width), no matter whether a positive (surplus) or negative (overflow) pixel value is supplied, but
     * <li>A call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} may not increase its size by more than the number of offered pixels - so if an
     * overflow is present, it may not increase its size at all. </ul> <p> Note that when the initial width is specified as a
     * stretch size (e.g. "*"), then after {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} is called, the Framework will stretch (but not shrink) the member like any other
     * stretch-size Layout member, but the <code>unadaptedWidth</code> argument will always reflect the unstretched width
     * requested by the previous call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()}.  This behavior may be disabled by specifying the initial width as a number, or
     * leaving it unspecified. <P> Behavior is slightly different for overflow: "visible" members - in this case the
     * <code>unadaptedWidth</code> passed in will reflect the current visible width of the member, rather than the last width
     * requested by the previous call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} or the specified width (on the first call). However, note that the visible length
     * will match your requested width unless the member is actually overflowed. Stretch sizing is not supported for
     * adaptive-width members with overflow: "visible". <p> Caution: you must either determine the current size of the canvas
     * by maintaining your own internal state, or use the <code>unadaptedWidth</code> parameter passed to  {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()}.  You must not
     * call {@link com.smartgwt.client.widgets.Canvas#getWidth getWidth()} or {@link
     * com.smartgwt.client.widgets.Canvas#getVisibleWidth getVisibleWidth()}  on the canvas itself inside {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()} as the size is in
     * the processing of being determined, but you may draw children or call {@link
     * com.smartgwt.client.widgets.Canvas#getVisibleWidth getVisibleWidth()} on them, as we guarantee that the adaptive-width
     * canvas is drawn before the first call to  {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()}.  An example of drawing children in {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()} to compute
     * overall width may be seen in the <a href="http://www.smartclient.com/smartgwt/showcase/#custom_adaptation_ms_new"
     * target="examples">Inlined Menu Mobile Sample</a>. <p> Note that reasonable settings for {@link
     * com.smartgwt.client.widgets.Canvas#getMinWidth minWidth} should be applied to all other flexible-sized members of a
     * layout where a <code>canAdaptWidth</code> component appears, because when too little space is available, a
     * <code>canAdaptWidth</code> component will absorb all available space until minimums for other components are hit (or the
     * <code>canAdaptWidth</code> component reaches its maximum size).  If more than one <code>canAdaptWidth</code> component
     * is present, {@link com.smartgwt.client.widgets.Canvas#getAdaptiveWidthPriority adaptiveWidthPriority} to give priority
     * to a particular component when allocating space. <p> All of the above behaviors are exactly the same for height, using
     * {@link com.smartgwt.client.widgets.Canvas#getCanAdaptHeight canAdaptHeight} and {@link
     * com.smartgwt.client.widgets.AdaptHeightByCustomizer#adaptHeightBy AdaptHeightByCustomizer.adaptHeightBy()}.
     *
     * @param canAdaptWidth New canAdaptWidth value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setCanAdaptHeight
     * @see com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * @see com.smartgwt.client.widgets.AdaptHeightByCustomizer#adaptHeightBy
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#custom_adaptation_ms_new" target="examples">Inlined Menu Example</a>
     */
    public void setCanAdaptWidth(Boolean canAdaptWidth) {
        setAttribute("canAdaptWidth", canAdaptWidth, true);
    }

    /**
     * Indicates that this component is able to render itself at multiple possible sizes, in order to fit into less space when
     * very little space is available, or to display more information or provide more immediate access to functionality when
     * more space is available. <p> Unlike simply indicating a flexible size via width:"*", setting <code>canAdaptWidth</code>
     * indicates that a component has 2 (or more) different ways of rendering itself with different <i>discrete</i> sizes, but
     * does not have the ability to use every additional available pixel. <p> For example, a menu embedded in a toolbar might
     * show as only a fixed-size icon which reveals menu options when clicked, or if enough space is available, could show all
     * of the menu options directly in the toolbar.  In this example, the menu should either be allocated just enough space for
     * an icon, or enough space for <i>all</i> options to appear inline; any other amount of space being allocated is just a
     * waste. <p> When a component with <code>canAdaptWidth</code> is part of a Layout, {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()} will be called
     * every time component sizes are being calculated, passing a positive or negative pixel value:   <ul> <li> a
     * <i>positive</i> pixel value indicates a surplus - all other component's minimum sizes      can be accommodated,
     * including any {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth} declared on the     
     * <code>canAdaptWidth</code> component itself <li> a <i>negative</i> pixel value indicates that the containing layout is
     * going to be      forced to {@link com.smartgwt.client.types.Overflow} (introduce scrollbars, clip content, etc) unless
     * some      component or set of components gives up at least this many pixels </ul> <p> If a non-zero value is returned
     * from <code>adaptWidthBy</code>, this means that the component is willing to shrink or expand by that many pixels. 
     * Returning 0 means that the component is unable to reduce size further, or for a surplus, cannot make good use of the
     * surplus space. <p><ul> <li>A call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} may surrender as many pixels as desired (as long as the widget won't drop below
     * its minimum allowed width), no matter whether a positive (surplus) or negative (overflow) pixel value is supplied, but
     * <li>A call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} may not increase its size by more than the number of offered pixels - so if an
     * overflow is present, it may not increase its size at all. </ul> <p> Note that when the initial width is specified as a
     * stretch size (e.g. "*"), then after {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} is called, the Framework will stretch (but not shrink) the member like any other
     * stretch-size Layout member, but the <code>unadaptedWidth</code> argument will always reflect the unstretched width
     * requested by the previous call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()}.  This behavior may be disabled by specifying the initial width as a number, or
     * leaving it unspecified. <P> Behavior is slightly different for overflow: "visible" members - in this case the
     * <code>unadaptedWidth</code> passed in will reflect the current visible width of the member, rather than the last width
     * requested by the previous call to {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()} or the specified width (on the first call). However, note that the visible length
     * will match your requested width unless the member is actually overflowed. Stretch sizing is not supported for
     * adaptive-width members with overflow: "visible". <p> Caution: you must either determine the current size of the canvas
     * by maintaining your own internal state, or use the <code>unadaptedWidth</code> parameter passed to  {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()}.  You must not
     * call {@link com.smartgwt.client.widgets.Canvas#getWidth getWidth()} or {@link
     * com.smartgwt.client.widgets.Canvas#getVisibleWidth getVisibleWidth()}  on the canvas itself inside {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()} as the size is in
     * the processing of being determined, but you may draw children or call {@link
     * com.smartgwt.client.widgets.Canvas#getVisibleWidth getVisibleWidth()} on them, as we guarantee that the adaptive-width
     * canvas is drawn before the first call to  {@link com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * AdaptWidthByCustomizer.adaptWidthBy()}.  An example of drawing children in {@link
     * com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy AdaptWidthByCustomizer.adaptWidthBy()} to compute
     * overall width may be seen in the <a href="http://www.smartclient.com/smartgwt/showcase/#custom_adaptation_ms_new"
     * target="examples">Inlined Menu Mobile Sample</a>. <p> Note that reasonable settings for {@link
     * com.smartgwt.client.widgets.Canvas#getMinWidth minWidth} should be applied to all other flexible-sized members of a
     * layout where a <code>canAdaptWidth</code> component appears, because when too little space is available, a
     * <code>canAdaptWidth</code> component will absorb all available space until minimums for other components are hit (or the
     * <code>canAdaptWidth</code> component reaches its maximum size).  If more than one <code>canAdaptWidth</code> component
     * is present, {@link com.smartgwt.client.widgets.Canvas#getAdaptiveWidthPriority adaptiveWidthPriority} to give priority
     * to a particular component when allocating space. <p> All of the above behaviors are exactly the same for height, using
     * {@link com.smartgwt.client.widgets.Canvas#getCanAdaptHeight canAdaptHeight} and {@link
     * com.smartgwt.client.widgets.AdaptHeightByCustomizer#adaptHeightBy AdaptHeightByCustomizer.adaptHeightBy()}.
     *
     * @return Current canAdaptWidth value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getCanAdaptHeight
     * @see com.smartgwt.client.widgets.AdaptWidthByCustomizer#adaptWidthBy
     * @see com.smartgwt.client.widgets.AdaptHeightByCustomizer#adaptHeightBy
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#custom_adaptation_ms_new" target="examples">Inlined Menu Example</a>
     */
    public Boolean getCanAdaptWidth()  {
        return getAttributeAsBoolean("canAdaptWidth");
    }
    

    /**
     * Indicates whether this widget can initiate custom drag-and-drop operations (other than reposition or resize). Normally
     * {@link com.smartgwt.client.widgets.Canvas#getCanDragReposition canDragReposition} or {@link
     * com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize} would be used instead of this property. <p> Note:
     * this property may be manipulated by higher-level dragging semantics. <p> If {@link
     * com.smartgwt.client.widgets.Canvas#getUseNativeDrag useNativeDrag} is true and this widget has been drawn, then this
     * widget must be {@link com.smartgwt.client.widgets.Canvas#redraw redrawn} in order for a change of the value of this
     * attribute to take effect.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canDrag New canDrag value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public void setCanDrag(Boolean canDrag) {
        setAttribute("canDrag", canDrag, true);
    }

    /**
     * Indicates whether this widget can initiate custom drag-and-drop operations (other than reposition or resize). Normally
     * {@link com.smartgwt.client.widgets.Canvas#getCanDragReposition canDragReposition} or {@link
     * com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize} would be used instead of this property. <p> Note:
     * this property may be manipulated by higher-level dragging semantics. <p> If {@link
     * com.smartgwt.client.widgets.Canvas#getUseNativeDrag useNativeDrag} is true and this widget has been drawn, then this
     * widget must be {@link com.smartgwt.client.widgets.Canvas#redraw redrawn} in order for a change of the value of this
     * attribute to take effect.
     *
     * @return Current canDrag value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public Boolean getCanDrag()  {
        Boolean result = getAttributeAsBoolean("canDrag");
        return result == null ? false : result;
    }
    

    /**
     * Indicates whether this widget can be moved by a user of your application by simply      dragging with the mouse.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canDragReposition New canDragReposition value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_effects" target="examples">Drag effects Example</a>
     */
    public void setCanDragReposition(Boolean canDragReposition) {
        setAttribute("canDragReposition", canDragReposition, true);
    }

    /**
     * Indicates whether this widget can be moved by a user of your application by simply      dragging with the mouse.
     *
     * @return Current canDragReposition value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_effects" target="examples">Drag effects Example</a>
     */
    public Boolean getCanDragReposition()  {
        Boolean result = getAttributeAsBoolean("canDragReposition");
        return result == null ? false : result;
    }
    

    /**
     * Indicates whether this widget can be resized by dragging on the edges and/or corners of      the widget with the mouse.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canDragResize New canDragResize value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public void setCanDragResize(Boolean canDragResize) {
        setAttribute("canDragResize", canDragResize, true);
    }

    /**
     * Indicates whether this widget can be resized by dragging on the edges and/or corners of      the widget with the mouse.
     *
     * @return Current canDragResize value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public Boolean getCanDragResize()  {
        Boolean result = getAttributeAsBoolean("canDragResize");
        return result == null ? false : result;
    }
    

    /**
     * If this Canvas is canAcceptDrop:true, when the user drags a droppable widget over      an edge of the widget, should we
     * scroll to show the rest of the widget's content?      Returned from canvas.shouldDragScroll() if there are scrollbars.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canDragScroll New canDragScroll value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#shouldDragScroll
     */
    public void setCanDragScroll(boolean canDragScroll) {
        setAttribute("canDragScroll", canDragScroll, true);
    }

    /**
     * If this Canvas is canAcceptDrop:true, when the user drags a droppable widget over      an edge of the widget, should we
     * scroll to show the rest of the widget's content?      Returned from canvas.shouldDragScroll() if there are scrollbars.
     *
     * @return Current canDragScroll value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#shouldDragScroll
     */
    public boolean getCanDragScroll()  {
        Boolean result = getAttributeAsBoolean("canDragScroll");
        return result == null ? true : result;
    }
    

    /**
     * Indicates that this object can be dropped on top of other widgets. Only valid if canDrag or canDragReposition is true.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canDrop New canDrop value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public void setCanDrop(Boolean canDrop) {
        setAttribute("canDrop", canDrop, true);
    }

    /**
     * Indicates that this object can be dropped on top of other widgets. Only valid if canDrag or canDragReposition is true.
     *
     * @return Current canDrop value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public Boolean getCanDrop()  {
        Boolean result = getAttributeAsBoolean("canDrop");
        return result == null ? false : result;
    }
    

    /**
     * When explicitly set to false, disallows drop before this member in the Layout.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canDropBefore New canDropBefore value. Default value is null
     * @see com.smartgwt.client.widgets.layout.Layout
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     */
    public void setCanDropBefore(Boolean canDropBefore) {
        setAttribute("canDropBefore", canDropBefore, true);
    }

    /**
     * When explicitly set to false, disallows drop before this member in the Layout.
     *
     * @return Current canDropBefore value. Default value is null
     * @see com.smartgwt.client.widgets.layout.Layout
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     */
    public Boolean getCanDropBefore()  {
        return getAttributeAsBoolean("canDropBefore");
    }
    

    /**
     * Can this widget be allowed to become the target of keyboard events? <P> If canFocus is unset (the default), only
     * scrollable widgets with visible scrollbars are focusable, to allow for keyboard scrolling. <P> A widget normally
     * receives focus by being clicked on or tabbed to.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Change whether a widget can accept keyboard focus.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canFocus whether the widget should now accept focus. Default value is null
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_focus_tabbing" target="examples">Focus & Tabbing Example</a>
     */
    public void setCanFocus(Boolean canFocus) {
        setAttribute("canFocus", canFocus, true);
    }

    /**
     * Can this widget be allowed to become the target of keyboard events? <P> If canFocus is unset (the default), only
     * scrollable widgets with visible scrollbars are focusable, to allow for keyboard scrolling. <P> A widget normally
     * receives focus by being clicked on or tabbed to.
     *
     * @return Current canFocus value. Default value is null
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_focus_tabbing" target="examples">Focus & Tabbing Example</a>
     */
    public Boolean getCanFocus()  {
        return getAttributeAsBoolean("canFocus");
    }
    

    /**
     * Will this Canvas fire hover events when the user hovers over it, or one of its children?
     *
     * @param canHover New canHover value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     * @see com.smartgwt.client.widgets.events.HoverEvent
     */
    public void setCanHover(Boolean canHover) {
        setAttribute("canHover", canHover, true);
    }

    /**
     * Will this Canvas fire hover events when the user hovers over it, or one of its children?
     *
     * @return Current canHover value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     * @see com.smartgwt.client.widgets.events.HoverEvent
     */
    public Boolean getCanHover()  {
        return getAttributeAsBoolean("canHover");
    }
    

    /**
     * Whether native drag selection of contained text is allowed within this Canvas. <P> Note that setting this property to
     * <code>false</code> will not avoid text selection which is initiated outside this Canvas from continuing into this
     * Canvas, even if text selection began in another Canvas.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param canSelectText New canSelectText value. Default value is false
     */
    public void setCanSelectText(Boolean canSelectText) {
        setAttribute("canSelectText", canSelectText, true);
    }

    /**
     * Whether native drag selection of contained text is allowed within this Canvas. <P> Note that setting this property to
     * <code>false</code> will not avoid text selection which is initiated outside this Canvas from continuing into this
     * Canvas, even if text selection began in another Canvas.
     *
     * @return Current canSelectText value. Default value is false
     */
    public Boolean getCanSelectText()  {
        Boolean result = getAttributeAsBoolean("canSelectText");
        return result == null ? false : result;
    }
    

    /**
     * If this canvas is being displayed in a {@link com.smartgwt.client.widgets.form.fields.CanvasItem}, this property will be
     * set to point at the item. Otherwise this property will be null.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current canvasItem value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     */
    public CanvasItem getCanvasItem() throws IllegalStateException {
        errorIfNotCreated("canvasItem");
        return CanvasItem.getOrCreateRef(getAttributeAsJavaScriptObject("canvasItem"));
    }
    

    /**
     * Array of all Canvii that are immediate children of this Canvas. <P> Use {@link
     * com.smartgwt.client.widgets.Canvas#addChild addChild()} and {@link com.smartgwt.client.widgets.Canvas#removeChild
     * removeChild()} to add and remove children after a Canvas has been created/drawn.
     *
     * @return Current children value. Default value is null
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public Canvas[] getChildren()  {
        return com.smartgwt.client.util.ConvertTo.arrayOfCanvas(getAttributeAsJavaScriptObject("children"));
    }
    

    /**
     * Flag to disable snapping to alignment when children of this Canvas are resized
     *
     * @param childrenResizeSnapAlign New childrenResizeSnapAlign value. Default value is null
     */
    public void setChildrenResizeSnapAlign(Boolean childrenResizeSnapAlign) {
        setAttribute("childrenResizeSnapAlign", childrenResizeSnapAlign, true);
    }

    /**
     * Flag to disable snapping to alignment when children of this Canvas are resized
     *
     * @return Current childrenResizeSnapAlign value. Default value is null
     */
    public Boolean getChildrenResizeSnapAlign()  {
        return getAttributeAsBoolean("childrenResizeSnapAlign");
    }
    

    /**
     * If enabled while {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid childrenSnapToGrid} is enabled,
     * children dragged within this Canvas will also snap to positions where their edges or center lines would be aligned with
     * the edges or centers of other components, and lines will be shown to point out the possible alignment (with appearance
     * controlled by {@link com.smartgwt.client.widgets.Canvas#getSnapAlignCenterLineStyle snapAlignCenterLineStyle} and {@link
     * com.smartgwt.client.widgets.Canvas#getSnapAlignEdgeLineStyle snapAlignEdgeLineStyle} respectively. <p> By default, edge-
     * or center-snapping is enabled for all components, but the set of eligible components can be explicitly set via {@link
     * com.smartgwt.client.widgets.Canvas#getSnapAlignCandidates snapAlignCandidates}. <p> See also {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapCenterAlign childrenSnapCenterAlign} and {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapEdgeAlign childrenSnapEdgeAlign} for enabling or disabling center
     * alignment or edge alignment individually.
     *
     * @param childrenSnapAlign New childrenSnapAlign value. Default value is null
     */
    public void setChildrenSnapAlign(Boolean childrenSnapAlign) {
        setAttribute("childrenSnapAlign", childrenSnapAlign, true);
    }

    /**
     * If enabled while {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid childrenSnapToGrid} is enabled,
     * children dragged within this Canvas will also snap to positions where their edges or center lines would be aligned with
     * the edges or centers of other components, and lines will be shown to point out the possible alignment (with appearance
     * controlled by {@link com.smartgwt.client.widgets.Canvas#getSnapAlignCenterLineStyle snapAlignCenterLineStyle} and {@link
     * com.smartgwt.client.widgets.Canvas#getSnapAlignEdgeLineStyle snapAlignEdgeLineStyle} respectively. <p> By default, edge-
     * or center-snapping is enabled for all components, but the set of eligible components can be explicitly set via {@link
     * com.smartgwt.client.widgets.Canvas#getSnapAlignCandidates snapAlignCandidates}. <p> See also {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapCenterAlign childrenSnapCenterAlign} and {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapEdgeAlign childrenSnapEdgeAlign} for enabling or disabling center
     * alignment or edge alignment individually.
     *
     * @return Current childrenSnapAlign value. Default value is null
     */
    public Boolean getChildrenSnapAlign()  {
        return getAttributeAsBoolean("childrenSnapAlign");
    }
    

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign}.  This setting enables or disables
     * snapping on center alignment only.
     *
     * @param childrenSnapCenterAlign New childrenSnapCenterAlign value. Default value is null
     */
    public void setChildrenSnapCenterAlign(Boolean childrenSnapCenterAlign) {
        setAttribute("childrenSnapCenterAlign", childrenSnapCenterAlign, true);
    }

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign}.  This setting enables or disables
     * snapping on center alignment only.
     *
     * @return Current childrenSnapCenterAlign value. Default value is null
     */
    public Boolean getChildrenSnapCenterAlign()  {
        return getAttributeAsBoolean("childrenSnapCenterAlign");
    }
    

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign}.  This setting enables or disables
     * snapping on edge alignment only.
     *
     * @param childrenSnapEdgeAlign New childrenSnapEdgeAlign value. Default value is null
     */
    public void setChildrenSnapEdgeAlign(Boolean childrenSnapEdgeAlign) {
        setAttribute("childrenSnapEdgeAlign", childrenSnapEdgeAlign, true);
    }

    /**
     * See {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign}.  This setting enables or disables
     * snapping on edge alignment only.
     *
     * @return Current childrenSnapEdgeAlign value. Default value is null
     */
    public Boolean getChildrenSnapEdgeAlign()  {
        return getAttributeAsBoolean("childrenSnapEdgeAlign");
    }
    

    /**
     * If true, causes this canvas's children to snap to its grid when resizing. This behavior can be overridden on a per-child
     * basis by setting the {@link com.smartgwt.client.widgets.Canvas#getSnapToGrid snapToGrid} or {@link
     * com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid snapResizeToGrid} value on the child.
     *
     * @param childrenSnapResizeToGrid New childrenSnapResizeToGrid value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setChildrenSnapResizeToGrid(Boolean childrenSnapResizeToGrid) {
        setAttribute("childrenSnapResizeToGrid", childrenSnapResizeToGrid, true);
    }

    /**
     * If true, causes this canvas's children to snap to its grid when resizing. This behavior can be overridden on a per-child
     * basis by setting the {@link com.smartgwt.client.widgets.Canvas#getSnapToGrid snapToGrid} or {@link
     * com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid snapResizeToGrid} value on the child.
     *
     * @return Current childrenSnapResizeToGrid value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public Boolean getChildrenSnapResizeToGrid()  {
        return getAttributeAsBoolean("childrenSnapResizeToGrid");
    }
    

    /**
     * If true, causes this canvas's children to snap to its grid when dragging. This behavior can be overridden on a per-child
     * basis by setting the {@link com.smartgwt.client.widgets.Canvas#getSnapToGrid snapToGrid} value on the child.
     *
     * @param childrenSnapToGrid New childrenSnapToGrid value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setChildrenSnapToGrid(Boolean childrenSnapToGrid) {
        setAttribute("childrenSnapToGrid", childrenSnapToGrid, true);
    }

    /**
     * If true, causes this canvas's children to snap to its grid when dragging. This behavior can be overridden on a per-child
     * basis by setting the {@link com.smartgwt.client.widgets.Canvas#getSnapToGrid snapToGrid} value on the child.
     *
     * @return Current childrenSnapToGrid value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public Boolean getChildrenSnapToGrid()  {
        return getAttributeAsBoolean("childrenSnapToGrid");
    }
    
    

    /**
     * Automatically generated mask canvas displayed when {@link com.smartgwt.client.widgets.Canvas#showComponentMask
     * showComponentMask()} is called.
     * <p>
     * This component is an AutoChild named "componentMask".  For an overview of how to use and
     * configure AutoChildren, see {@link com.smartgwt.client.docs.AutoChildUsage Using AutoChildren}.
     *
     * @return Current componentMask value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     * @see com.smartgwt.client.widgets.Canvas#getComponentMaskDefaults
     */
    public Canvas getComponentMask() throws IllegalStateException {
        errorIfNotCreated("componentMask");
        return (Canvas)Canvas.getByJSObject(getAttributeAsJavaScriptObject("componentMask"));
    }
    

    /**
     * Defaults for the {@link com.smartgwt.client.widgets.Canvas#getComponentMask componentMask} autoChild. Default properties
     * include {@link com.smartgwt.client.widgets.Canvas#getBackgroundColor backgroundColor} being set to <code>"black"</code>
     * and {@link com.smartgwt.client.widgets.Canvas#getOpacity opacity} being set to <code>20</code>.
     *
     * @param componentMaskDefaults New componentMaskDefaults value. Default value is {...}
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.SGWTProperties
     */
    public void setComponentMaskDefaults(Canvas componentMaskDefaults)  throws IllegalStateException {
        if (componentMaskDefaults != null) {
            if (componentMaskDefaults.isCreated()) {
                ConfigUtil.warnOfPreConfigInstantiation(Canvas.class, "setComponentMaskDefaults", "Canvas");
            }                                                                       
            componentMaskDefaults.setConfigOnly(true);
        }
        JavaScriptObject config = componentMaskDefaults == null ? null : componentMaskDefaults.getConfig();
        setAttribute("componentMaskDefaults", JSOHelper.cleanProperties(config, true), false);
    }

    /**
     * Defaults for the {@link com.smartgwt.client.widgets.Canvas#getComponentMask componentMask} autoChild. Default properties
     * include {@link com.smartgwt.client.widgets.Canvas#getBackgroundColor backgroundColor} being set to <code>"black"</code>
     * and {@link com.smartgwt.client.widgets.Canvas#getOpacity opacity} being set to <code>20</code>.
     *
     * @return Current componentMaskDefaults value. Default value is {...}
     */
    public Canvas getComponentMaskDefaults()  {
        Canvas properties = new Canvas();
        properties.setConfigOnly(true);
        properties.setConfig(getAttributeAsJavaScriptObject("componentMaskDefaults"));
        return properties;
    }
    

    /**
     * The contents of a canvas or label widget. Any HTML string is acceptable.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Changes the contents of a widget to newContents, an HTML string. <P> When {@link com.smartgwt.client.widgets.Canvas#getDynamicContents dynamicContents} is set, <code>setContents()</code> can also be called with no arguments to cause contents to be re-evaluated.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param contents an HTML string to be set as the contents of this widget. Default value is "&amp;nbsp;"
     * @see com.smartgwt.client.widgets.Canvas#setDynamicContents
     * @see com.smartgwt.client.docs.HTMLString HTMLString 
     */
    public void setContents(String contents) {
        setAttribute("contents", contents, true);
    }

    /**
     * The contents of a canvas or label widget. Any HTML string is acceptable.
     *
     * @return Returns the contents of a Canvas. The contents are an HTML string. Default value is "&amp;nbsp;"
     * @see com.smartgwt.client.widgets.Canvas#getDynamicContents
     * @see com.smartgwt.client.docs.HTMLString HTMLString 
     */
    public String getContents()  {
        return getAttributeAsString("contents");
    }
    

    /**
     * Context menu to show for this object, an instance of the Menu widget. <P> Note: if {@link
     * com.smartgwt.client.widgets.Canvas#destroy destroy()} is called on a canvas, any specified context menu is not
     * automatically destroyed as well. This is in contrast to {@link com.smartgwt.client.widgets.menu.MenuButton}s which
     * automatically destroy their specified {@link com.smartgwt.client.widgets.menu.MenuButton#getMenu MenuButton.menu} by
     * default. The behavior is intentional as context menus are commonly reused across components.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param contextMenu New contextMenu value. Default value is null
     * @see com.smartgwt.client.widgets.events.ShowContextMenuEvent
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_contextmenu" target="examples">Context menus Example</a>
     */
    public void setContextMenu(Menu contextMenu) {
        setAttribute("contextMenu", contextMenu == null ? null : contextMenu.getOrCreateJsObj(), true);
    }

    /**
     * Context menu to show for this object, an instance of the Menu widget. <P> Note: if {@link
     * com.smartgwt.client.widgets.Canvas#destroy destroy()} is called on a canvas, any specified context menu is not
     * automatically destroyed as well. This is in contrast to {@link com.smartgwt.client.widgets.menu.MenuButton}s which
     * automatically destroy their specified {@link com.smartgwt.client.widgets.menu.MenuButton#getMenu MenuButton.menu} by
     * default. The behavior is intentional as context menus are commonly reused across components.
     *
     * @return Current contextMenu value. Default value is null
     * @see com.smartgwt.client.widgets.events.ShowContextMenuEvent
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_contextmenu" target="examples">Context menus Example</a>
     */
    public Menu getContextMenu()  {
        return (Menu)Menu.getByJSObject(getAttributeAsJavaScriptObject("contextMenu"));
    }
    

    /**
     * Specifies the cursor image to display when the mouse pointer is      over this widget. It corresponds to the CSS cursor
     * attribute. See Cursor type for      different cursors.      <P>      See also {@link
     * com.smartgwt.client.widgets.Canvas#getDisabledCursor disabledCursor} and {@link
     * com.smartgwt.client.widgets.Canvas#getNoDropCursor noDropCursor}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Sets the cursor for this widget to cursor. See the cursor property          for possible values.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param cursor new cursor. Default value is Canvas.DEFAULT
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_cursors" target="examples">Cursors Example</a>
     */
    public void setCursor(Cursor cursor) {
        setAttribute("cursor", cursor == null ? null : cursor.getValue(), true);
    }

    /**
     * Specifies the cursor image to display when the mouse pointer is      over this widget. It corresponds to the CSS cursor
     * attribute. See Cursor type for      different cursors.      <P>      See also {@link
     * com.smartgwt.client.widgets.Canvas#getDisabledCursor disabledCursor} and {@link
     * com.smartgwt.client.widgets.Canvas#getNoDropCursor noDropCursor}.
     *
     * @return Current cursor value. Default value is Canvas.DEFAULT
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_cursors" target="examples">Cursors Example</a>
     */
    public Cursor getCursor()  {
        return EnumUtil.getEnum(Cursor.values(), getAttribute("cursor"));
    }
    
    

    /**
     * A dataPath may be specified on any canvas. This provides a straightforward way to display or
     *  edit complex nested data.
     *  <P>
     * For components which support displaying or editing data values, (such as {@link
     * com.smartgwt.client.widgets.form.DynamicForm} or
     * {@link com.smartgwt.client.widgets.grid.ListGrid} components), the dataPath may be set to specify how the components
     * data is
     *  accessed. In this case the dataPath essentially specifies a nested object to edit - typically
     *  a path to a field value within a dataSource record. Note that a ValuesManager will be required
     *  to handle connecting the dataBoundcomponent to the appropriate sub object. This may be explicitly
     *  specified on the component, or a parent of the component, or automatically generated
     *  if a DataSource is specified on either the component or a parent thereof.
     *  <P>
     *  To provide a simple example - if a complex object existed with the following format:
     *  
     *  
     *  <pre>
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     *  </pre>
     *  
     *  a developer could specify a DynamicForm instance with 'dataPath' set to "address" to edit
     *  the nested address object:
     *  
     *  
     *  <pre>
     *       ValuesManager vm = new ValuesManager();
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     *       vm.setValues(values);
     * 
     *       DynamicForm form = new DynamicForm();
     *       form.setValuesManager(vm);
     *       form.setDataPath("address");  
     *       TextItem streetField = new TextItem("street");
     *       TextItem cityField = new TextItem("city");
     *       TextItem stateField = new TextItem("state");
     *       form.setItems(streetField, cityField, stateField);
     *  </pre>
     *  
     *  If a component is specified with a <code>dataPath</code> attribute but does not have an
     *  explicitly specified valuesManager, it will check its parent element chain for a specified
     *  valuesManager and automatically bind to that. This simplifies binding multiple components used
     *  to view or edit a nested data structure as the valuesManager needs only be defined once at a
     *  reasonably high level component. Here's an example of this approach:
     *  
     *  
     *  <pre>
     *       ValuesManager vm = new ValuesManager();
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     *       vm.setValues(values);
     * 
     *       Layout layout = new Layout();
     *       layout.setValuesManager(vm);
     * 
     *       DynamicForm form = new DynamicForm();
     *       form.setDataPath("/");  
     *       TextItem companyNameField = new TextItem("companyName");
     *       form.setItems(companyNameField);
     * 
     *       DynamicForm form1 = new DynamicForm();
     *       form1.setDataPath("address");  
     *       TextItem streetField = new TextItem("street");
     *       TextItem cityField = new TextItem("city");
     *       TextItem stateField = new TextItem("state");
     *       form1.setItems(streetField, cityField, stateField);
     * 
     *       layout.addMember(form);
     *       layout.addMember(form1);
     *  </pre>
     *  
     *  Note that in this case the valuesManager is specified on a Layout, which has no 'values'
     *  management behavior of its own, but contains items with a specified dataPath which do. In this
     *  example you'd see 2 forms allowing editing of the nested data structure.
     *  <P>
     *  dataPaths from multiple nested components may also be combined. For example:
     *  
     *  
     *  <pre>
     *       ValuesManager vm = new ValuesManager();
     * 
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     * 
     *       Map values1 = new HashMap();
     *       values1.put("companyName", "Some Corporation");
     *       Map addressMap1 = new HashMap();
     *       addressMap1.put("street", "1 High Street");
     *       addressMap1.put("city", "New York");
     *       addressMap1.put("state", "NY");
     *       values1.put("address", addressMap1);
     *       values.put("parentCompany", values1);
     * 
     *       vm.setValues(values);
     *       Layout layout = new Layout();
     *       layout.setValuesManager(vm);
     * 
     *       DynamicForm form = new DynamicForm();
     *       form.setDataPath("/");  
     *       TextItem companyNameField = new TextItem("companyName");
     *       form.setItems(companyNameField);
     * 
     *       DynamicForm form1 = new DynamicForm();
     *       form1.setDataPath("address");  
     *       TextItem streetField = new TextItem("street");
     *       TextItem cityField = new TextItem("city");
     *       TextItem stateField = new TextItem("state");
     *       form1.setItems(streetField, cityField, stateField);
     * 
     *       Layout innerlayout = new Layout();
     *       innerlayout.setDataPath("parentCompany");
     * 
     *       DynamicForm form3 = new DynamicForm();
     *       form3.setDataPath("/");  
     *       StaticTextItem companyNameField1 = new StaticTextItem("companyName");
     *       form3.setItems(companyNameField1);
     * 
     *       DetailViewer detailViewer = new DetailViewer();
     *       detailViewer.setDataPath("address");  
     *       DetailViewerField streetDVField = new DetailViewerField("street");
     *       DetailViewerField cityDVField = new DetailViewerField("city");
     *       DetailViewerField stateDVField = new DetailViewerField("state");
     *       detailViewer.setFields(streetDVField, cityDVField, stateDVField);
     * 
     *       innerlayout.addMember(form3);
     *       innerlayout.addMember(detailViewer);
     * 
     *       layout.addMember(form);
     *       layout.addMember(form1);
     *       layout.addMember(innerlayout);
     *  </pre>
     *  
     *  In this example the detailViewer will display data from the <code>parentCompany.address</code>
     *  object within the base record.
     *  <P>
     *  Note that if a component has a specified  dataSource and shows child components with a
     *  specified dataPath, there is no need to explicitly declare a valuesManager at all. If a component
     *  with a dataPath has a dataSource, or an ancestor with a dataSource specified, it will, a
     *  valuesManager will automatically be generated on the higher level component (and be available as
     *  <code>component.valuesManager</code>).
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for the {@link com.smartgwt.client.widgets.Canvas#getDataPath dataPath} attribute. This method may be called directly at runtime to set the dataPath on a component, and will also be re-run automatically whenever a canvas' parent changes due to a call to addChild(). This method handles automatically binding the component to the appropriate valuesManager if necessary.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dataPath new dataPath. Default value is null
     * @see com.smartgwt.client.docs.DataPath DataPath 
     */
    public void setDataPath(String dataPath) {
        setAttribute("dataPath", dataPath, true);
    }

    /**
     * A dataPath may be specified on any canvas. This provides a straightforward way to display or
     *  edit complex nested data.
     *  <P>
     * For components which support displaying or editing data values, (such as {@link
     * com.smartgwt.client.widgets.form.DynamicForm} or
     * {@link com.smartgwt.client.widgets.grid.ListGrid} components), the dataPath may be set to specify how the components
     * data is
     *  accessed. In this case the dataPath essentially specifies a nested object to edit - typically
     *  a path to a field value within a dataSource record. Note that a ValuesManager will be required
     *  to handle connecting the dataBoundcomponent to the appropriate sub object. This may be explicitly
     *  specified on the component, or a parent of the component, or automatically generated
     *  if a DataSource is specified on either the component or a parent thereof.
     *  <P>
     *  To provide a simple example - if a complex object existed with the following format:
     *  
     *  
     *  <pre>
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     *  </pre>
     *  
     *  a developer could specify a DynamicForm instance with 'dataPath' set to "address" to edit
     *  the nested address object:
     *  
     *  
     *  <pre>
     *       ValuesManager vm = new ValuesManager();
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     *       vm.setValues(values);
     * 
     *       DynamicForm form = new DynamicForm();
     *       form.setValuesManager(vm);
     *       form.setDataPath("address");  
     *       TextItem streetField = new TextItem("street");
     *       TextItem cityField = new TextItem("city");
     *       TextItem stateField = new TextItem("state");
     *       form.setItems(streetField, cityField, stateField);
     *  </pre>
     *  
     *  If a component is specified with a <code>dataPath</code> attribute but does not have an
     *  explicitly specified valuesManager, it will check its parent element chain for a specified
     *  valuesManager and automatically bind to that. This simplifies binding multiple components used
     *  to view or edit a nested data structure as the valuesManager needs only be defined once at a
     *  reasonably high level component. Here's an example of this approach:
     *  
     *  
     *  <pre>
     *       ValuesManager vm = new ValuesManager();
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     *       vm.setValues(values);
     * 
     *       Layout layout = new Layout();
     *       layout.setValuesManager(vm);
     * 
     *       DynamicForm form = new DynamicForm();
     *       form.setDataPath("/");  
     *       TextItem companyNameField = new TextItem("companyName");
     *       form.setItems(companyNameField);
     * 
     *       DynamicForm form1 = new DynamicForm();
     *       form1.setDataPath("address");  
     *       TextItem streetField = new TextItem("street");
     *       TextItem cityField = new TextItem("city");
     *       TextItem stateField = new TextItem("state");
     *       form1.setItems(streetField, cityField, stateField);
     * 
     *       layout.addMember(form);
     *       layout.addMember(form1);
     *  </pre>
     *  
     *  Note that in this case the valuesManager is specified on a Layout, which has no 'values'
     *  management behavior of its own, but contains items with a specified dataPath which do. In this
     *  example you'd see 2 forms allowing editing of the nested data structure.
     *  <P>
     *  dataPaths from multiple nested components may also be combined. For example:
     *  
     *  
     *  <pre>
     *       ValuesManager vm = new ValuesManager();
     * 
     *       Map values = new HashMap();
     *       values.put("companyName", "Some company");
     *       Map addressMap = new HashMap();
     *       addressMap.put("street", "123 Main Street");
     *       addressMap.put("city", "New York");
     *       addressMap.put("state", "NY");
     *       values.put("address", addressMap);
     * 
     *       Map values1 = new HashMap();
     *       values1.put("companyName", "Some Corporation");
     *       Map addressMap1 = new HashMap();
     *       addressMap1.put("street", "1 High Street");
     *       addressMap1.put("city", "New York");
     *       addressMap1.put("state", "NY");
     *       values1.put("address", addressMap1);
     *       values.put("parentCompany", values1);
     * 
     *       vm.setValues(values);
     *       Layout layout = new Layout();
     *       layout.setValuesManager(vm);
     * 
     *       DynamicForm form = new DynamicForm();
     *       form.setDataPath("/");  
     *       TextItem companyNameField = new TextItem("companyName");
     *       form.setItems(companyNameField);
     * 
     *       DynamicForm form1 = new DynamicForm();
     *       form1.setDataPath("address");  
     *       TextItem streetField = new TextItem("street");
     *       TextItem cityField = new TextItem("city");
     *       TextItem stateField = new TextItem("state");
     *       form1.setItems(streetField, cityField, stateField);
     * 
     *       Layout innerlayout = new Layout();
     *       innerlayout.setDataPath("parentCompany");
     * 
     *       DynamicForm form3 = new DynamicForm();
     *       form3.setDataPath("/");  
     *       StaticTextItem companyNameField1 = new StaticTextItem("companyName");
     *       form3.setItems(companyNameField1);
     * 
     *       DetailViewer detailViewer = new DetailViewer();
     *       detailViewer.setDataPath("address");  
     *       DetailViewerField streetDVField = new DetailViewerField("street");
     *       DetailViewerField cityDVField = new DetailViewerField("city");
     *       DetailViewerField stateDVField = new DetailViewerField("state");
     *       detailViewer.setFields(streetDVField, cityDVField, stateDVField);
     * 
     *       innerlayout.addMember(form3);
     *       innerlayout.addMember(detailViewer);
     * 
     *       layout.addMember(form);
     *       layout.addMember(form1);
     *       layout.addMember(innerlayout);
     *  </pre>
     *  
     *  In this example the detailViewer will display data from the <code>parentCompany.address</code>
     *  object within the base record.
     *  <P>
     *  Note that if a component has a specified  dataSource and shows child components with a
     *  specified dataPath, there is no need to explicitly declare a valuesManager at all. If a component
     *  with a dataPath has a dataSource, or an ancestor with a dataSource specified, it will, a
     *  valuesManager will automatically be generated on the higher level component (and be available as
     *  <code>component.valuesManager</code>).
     *
     * @return Current dataPath value. Default value is null
     * @see com.smartgwt.client.docs.DataPath DataPath 
     */
    public String getDataPath()  {
        return getAttributeAsString("dataPath");
    }
    

    /**
     * For custom components, establishes a default height for the component. <P> For a component that should potentially be
     * sized automatically by a Layout, set this property rather than {@link com.smartgwt.client.widgets.Canvas#getHeight
     * height} directly, because Layouts regard a height setting as an explicit size that shouldn't be changed.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param defaultHeight New defaultHeight value. Default value is 100
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setDefaultHeight(int defaultHeight) {
        setAttribute("defaultHeight", defaultHeight, true);
    }

    /**
     * For custom components, establishes a default height for the component. <P> For a component that should potentially be
     * sized automatically by a Layout, set this property rather than {@link com.smartgwt.client.widgets.Canvas#getHeight
     * height} directly, because Layouts regard a height setting as an explicit size that shouldn't be changed.
     *
     * @return Current defaultHeight value. Default value is 100
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getDefaultHeight()  {
        return getAttributeAsInt("defaultHeight");
    }
    

    /**
     * For custom components, establishes a default width for the component. <P> For a component that should potentially be
     * sized automatically by a Layout, set this property rather than {@link com.smartgwt.client.widgets.Canvas#getWidth width}
     * directly, because Layouts regard a width setting as an explicit size that shouldn't be changed.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param defaultWidth New defaultWidth value. Default value is 100
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setDefaultWidth(int defaultWidth) {
        setAttribute("defaultWidth", defaultWidth, true);
    }

    /**
     * For custom components, establishes a default width for the component. <P> For a component that should potentially be
     * sized automatically by a Layout, set this property rather than {@link com.smartgwt.client.widgets.Canvas#getWidth width}
     * directly, because Layouts regard a width setting as an explicit size that shouldn't be changed.
     *
     * @return Current defaultWidth value. Default value is 100
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getDefaultWidth()  {
        return getAttributeAsInt("defaultWidth");
    }
    

    /**
     * If this property is set to <code>true</code>, the {@link com.smartgwt.client.widgets.Canvas#destroy destroy()} method
     * has been called on this canvas. This implies the canvas is no longer valid. Its ID has been removed from global scope,
     * and calling standard canvas APIs on it is likely to result in errors.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current destroyed value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     * @see com.smartgwt.client.widgets.Canvas#destroy
     */
    public Boolean getDestroyed() throws IllegalStateException {
        errorIfNotCreated("destroyed");
        return getAttributeAsBoolean("destroyed");
    }
    

    /**
     * This property is set to true when the {@link com.smartgwt.client.widgets.Canvas#destroy destroy()} method is called on a
     * widget. If this property is true, but {@link com.smartgwt.client.widgets.Canvas#getDestroyed destroyed} is not, this
     * indicates the canvas is in the process of being destroyed.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current destroying value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     * @see com.smartgwt.client.widgets.Canvas#destroy
     */
    public Boolean getDestroying() throws IllegalStateException {
        errorIfNotCreated("destroying");
        return getAttributeAsBoolean("destroying");
    }
    
    

    /**
     * Specifies the cursor image to display when the mouse pointer is      over this widget if this widget is disabled. It
     * corresponds to the CSS cursor      attribute. See Cursor type for different cursors.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param disabledCursor New disabledCursor value. Default value is Canvas.DEFAULT
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public void setDisabledCursor(Cursor disabledCursor) {
        setAttribute("disabledCursor", disabledCursor == null ? null : disabledCursor.getValue(), true);
    }

    /**
     * Specifies the cursor image to display when the mouse pointer is      over this widget if this widget is disabled. It
     * corresponds to the CSS cursor      attribute. See Cursor type for different cursors.
     *
     * @return Current disabledCursor value. Default value is Canvas.DEFAULT
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public Cursor getDisabledCursor()  {
        return EnumUtil.getEnum(Cursor.values(), getAttribute("disabledCursor"));
    }
    

    /**
     * Disables {@link com.smartgwt.client.widgets.Canvas#getUseTouchScrolling useTouchScrolling} whenever a built-in drag
     * operation has been enabled which is known to be non-functional if touch scrolling is enabled.  Default behavior is to
     * leave touch scrolling enabled even if it makes other enabled drag operations non-functional, since any {@link
     * com.smartgwt.client.docs.Accessibility accessible} application must provide an alternative way to perform drag and drop
     * operations anyway. <p> <code>disableTouchScrollingForDrag</code> exists so that applications can change the default
     * setting on a per-component basis (via   <code>setDefaultProperties</code>), in order to make a system-wide or
     * per-component-type decision about whether to favor touch scrolling vs retaining the ability to drag and drop via finger
     * drags, instead of having to set <code>useTouchScrolling</code> on each individual instance. <p> See the {@link
     * com.smartgwt.client.docs.MobileDevelopment Mobile Development overview} for more background information.
     *
     * @param disableTouchScrollingForDrag New disableTouchScrollingForDrag value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setDisableTouchScrollingForDrag(Boolean disableTouchScrollingForDrag)  throws IllegalStateException {
        setAttribute("disableTouchScrollingForDrag", disableTouchScrollingForDrag, false);
    }

    /**
     * Disables {@link com.smartgwt.client.widgets.Canvas#getUseTouchScrolling useTouchScrolling} whenever a built-in drag
     * operation has been enabled which is known to be non-functional if touch scrolling is enabled.  Default behavior is to
     * leave touch scrolling enabled even if it makes other enabled drag operations non-functional, since any {@link
     * com.smartgwt.client.docs.Accessibility accessible} application must provide an alternative way to perform drag and drop
     * operations anyway. <p> <code>disableTouchScrollingForDrag</code> exists so that applications can change the default
     * setting on a per-component basis (via   <code>setDefaultProperties</code>), in order to make a system-wide or
     * per-component-type decision about whether to favor touch scrolling vs retaining the ability to drag and drop via finger
     * drags, instead of having to set <code>useTouchScrolling</code> on each individual instance. <p> See the {@link
     * com.smartgwt.client.docs.MobileDevelopment Mobile Development overview} for more background information.
     *
     * @return Current disableTouchScrollingForDrag value. Default value is null
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public Boolean getDisableTouchScrollingForDrag()  {
        return getAttributeAsBoolean("disableTouchScrollingForDrag");
    }
    

    /**
     * Amount of time (in milliseconds) between which two clicks are considered a single click
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param doubleClickDelay New doubleClickDelay value. Default value is 250
     */
    public void setDoubleClickDelay(int doubleClickDelay) {
        setAttribute("doubleClickDelay", doubleClickDelay, true);
    }

    /**
     * Amount of time (in milliseconds) between which two clicks are considered a single click
     *
     * @return Current doubleClickDelay value. Default value is 250
     */
    public int getDoubleClickDelay()  {
        return getAttributeAsInt("doubleClickDelay");
    }
    

    /**
     * Visual appearance to show when the object is being dragged. May be overridden for dragResize or dragReposition events
     * via {@link com.smartgwt.client.widgets.Canvas#getDragResizeAppearance dragResizeAppearance} and {@link
     * com.smartgwt.client.widgets.Canvas#getDragRepositionAppearance dragRepositionAppearance}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragAppearance New dragAppearance value. Default value is EventHandler.OUTLINE
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragAppearance(DragAppearance dragAppearance) {
        setAttribute("dragAppearance", dragAppearance == null ? null : dragAppearance.getValue(), true);
    }

    /**
     * Visual appearance to show when the object is being dragged. May be overridden for dragResize or dragReposition events
     * via {@link com.smartgwt.client.widgets.Canvas#getDragResizeAppearance dragResizeAppearance} and {@link
     * com.smartgwt.client.widgets.Canvas#getDragRepositionAppearance dragRepositionAppearance}.
     *
     * @return Current dragAppearance value. Default value is EventHandler.OUTLINE
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public DragAppearance getDragAppearance()  {
        return EnumUtil.getEnum(DragAppearance.values(), getAttribute("dragAppearance"));
    }
    

    /**
     * This indicates how the system will test for droppable targets: either by intersection with the mouse or intersection
     * with the rectangle of the dragMoveTarget.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragIntersectStyle New dragIntersectStyle value. Default value is "mouse"
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragIntersectStyle(DragIntersectStyle dragIntersectStyle) {
        setAttribute("dragIntersectStyle", dragIntersectStyle == null ? null : dragIntersectStyle.getValue(), true);
    }

    /**
     * This indicates how the system will test for droppable targets: either by intersection with the mouse or intersection
     * with the rectangle of the dragMoveTarget.
     *
     * @return Current dragIntersectStyle value. Default value is "mouse"
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public DragIntersectStyle getDragIntersectStyle()  {
        return EnumUtil.getEnum(DragIntersectStyle.values(), getAttribute("dragIntersectStyle"));
    }
    

    /**
     * This property controls what kind of mask is used in case {@link com.smartgwt.client.widgets.Canvas#getUseDragMask
     * useDragMask} is enabled.
     *
     * @param dragMaskType New dragMaskType value. Default value is "div"
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragMaskType(DragMaskType dragMaskType) {
        setAttribute("dragMaskType", dragMaskType == null ? null : dragMaskType.getValue(), true);
    }

    /**
     * This property controls what kind of mask is used in case {@link com.smartgwt.client.widgets.Canvas#getUseDragMask
     * useDragMask} is enabled.
     *
     * @return Current dragMaskType value. Default value is "div"
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public DragMaskType getDragMaskType()  {
        return EnumUtil.getEnum(DragMaskType.values(), getAttribute("dragMaskType"));
    }
    

    /**
     * Sets maximum height that this Canvas can be resized to by a user.  Actual limit will be minimum of
     * <code>dragMaxHeight</code> and {@link com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragMaxHeight New dragMaxHeight value. Default value is 10000
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setDragMaxHeight(int dragMaxHeight) {
        setAttribute("dragMaxHeight", dragMaxHeight, true);
    }

    /**
     * Sets maximum height that this Canvas can be resized to by a user.  Actual limit will be minimum of
     * <code>dragMaxHeight</code> and {@link com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight}.
     *
     * @return Current dragMaxHeight value. Default value is 10000
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getDragMaxHeight()  {
        return getAttributeAsInt("dragMaxHeight");
    }
    

    /**
     * Maximum width that this Canvas can be resized to by a user.  Actual limit will be minimum of <code>dragMaxWidth</code>
     * and {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragMaxWidth New dragMaxWidth value. Default value is 10000
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setDragMaxWidth(int dragMaxWidth) {
        setAttribute("dragMaxWidth", dragMaxWidth, true);
    }

    /**
     * Maximum width that this Canvas can be resized to by a user.  Actual limit will be minimum of <code>dragMaxWidth</code>
     * and {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth}.
     *
     * @return Current dragMaxWidth value. Default value is 10000
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getDragMaxWidth()  {
        return getAttributeAsInt("dragMaxWidth");
    }
    

    /**
     * Minimum height that this Canvas can be resized to by a user.  See {@link
     * com.smartgwt.client.widgets.Canvas#getDragMinWidth dragMinWidth} for details of behavior.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragMinHeight New dragMinHeight value. Default value is 10
     * @see com.smartgwt.client.widgets.Canvas#setMinHeight
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setDragMinHeight(int dragMinHeight) {
        setAttribute("dragMinHeight", dragMinHeight, true);
    }

    /**
     * Minimum height that this Canvas can be resized to by a user.  See {@link
     * com.smartgwt.client.widgets.Canvas#getDragMinWidth dragMinWidth} for details of behavior.
     *
     * @return Current dragMinHeight value. Default value is 10
     * @see com.smartgwt.client.widgets.Canvas#getMinHeight
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getDragMinHeight()  {
        return getAttributeAsInt("dragMinHeight");
    }
    

    /**
     * Minimum width that this Canvas can be resized to by a user.  Actual limit will be maximum of <code>dragMinWidth</code>
     * and {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth}. <P> Note that a Canvas with overflow:"visible" has
     * an implicit minimize size based on it's contents. <p> Note that <code>dragMinWidth</code> affects only user-initiated
     * drag resizes.  To set the minimum width of a Canvas embedded in a Layout, you can set +{minWidth}, or {@link
     * com.smartgwt.client.widgets.layout.Layout#getMinMemberLength Layout.minMemberLength} to constrain the minimum size along
     * the length axis of all members of the {@link com.smartgwt.client.widgets.layout.Layout}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragMinWidth New dragMinWidth value. Default value is 10
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setDragMinWidth(int dragMinWidth) {
        setAttribute("dragMinWidth", dragMinWidth, true);
    }

    /**
     * Minimum width that this Canvas can be resized to by a user.  Actual limit will be maximum of <code>dragMinWidth</code>
     * and {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth}. <P> Note that a Canvas with overflow:"visible" has
     * an implicit minimize size based on it's contents. <p> Note that <code>dragMinWidth</code> affects only user-initiated
     * drag resizes.  To set the minimum width of a Canvas embedded in a Layout, you can set +{minWidth}, or {@link
     * com.smartgwt.client.widgets.layout.Layout#getMinMemberLength Layout.minMemberLength} to constrain the minimum size along
     * the length axis of all members of the {@link com.smartgwt.client.widgets.layout.Layout}.
     *
     * @return Current dragMinWidth value. Default value is 10
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getDragMinWidth()  {
        return getAttributeAsInt("dragMinWidth");
    }
    

    /**
     * If this widget has dragAppearance <code>"target"</code>, this value specifies the opacity to render the target while it
     * is being dragged. A null value implies we do not modify the opacity.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragOpacity New dragOpacity value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_effects" target="examples">Drag effects Example</a>
     */
    public void setDragOpacity(Integer dragOpacity) {
        setAttribute("dragOpacity", dragOpacity, true);
    }

    /**
     * If this widget has dragAppearance <code>"target"</code>, this value specifies the opacity to render the target while it
     * is being dragged. A null value implies we do not modify the opacity.
     *
     * @return Current dragOpacity value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_effects" target="examples">Drag effects Example</a>
     */
    public Integer getDragOpacity()  {
        return getAttributeAsInt("dragOpacity");
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getCanDragReposition canDragReposition} is true, this attributes specifies
     * the visual appearance to show during drag reposition. If unset {@link
     * com.smartgwt.client.widgets.Canvas#getDragAppearance dragAppearance} will be used.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragRepositionAppearance New dragRepositionAppearance value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragRepositionAppearance(DragAppearance dragRepositionAppearance) {
        setAttribute("dragRepositionAppearance", dragRepositionAppearance == null ? null : dragRepositionAppearance.getValue(), true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getCanDragReposition canDragReposition} is true, this attributes specifies
     * the visual appearance to show during drag reposition. If unset {@link
     * com.smartgwt.client.widgets.Canvas#getDragAppearance dragAppearance} will be used.
     *
     * @return Current dragRepositionAppearance value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public DragAppearance getDragRepositionAppearance()  {
        return EnumUtil.getEnum(DragAppearance.values(), getAttribute("dragRepositionAppearance"));
    }
    

    /**
     * Cursor to switch to if the mouse is over a widget that is drag repositionable.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragRepositionCursor New dragRepositionCursor value. Default value is Canvas.MOVE
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragRepositionCursor(Cursor dragRepositionCursor) {
        setAttribute("dragRepositionCursor", dragRepositionCursor == null ? null : dragRepositionCursor.getValue(), true);
    }

    /**
     * Cursor to switch to if the mouse is over a widget that is drag repositionable.
     *
     * @return Current dragRepositionCursor value. Default value is Canvas.MOVE
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public Cursor getDragRepositionCursor()  {
        return EnumUtil.getEnum(Cursor.values(), getAttribute("dragRepositionCursor"));
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize} is true, this attributes specifies the
     * visual appearance to show during drag resize. If unset {@link com.smartgwt.client.widgets.Canvas#getDragAppearance
     * dragAppearance} will be used.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragResizeAppearance New dragResizeAppearance value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragResizeAppearance(DragAppearance dragResizeAppearance) {
        setAttribute("dragResizeAppearance", dragResizeAppearance == null ? null : dragResizeAppearance.getValue(), true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize} is true, this attributes specifies the
     * visual appearance to show during drag resize. If unset {@link com.smartgwt.client.widgets.Canvas#getDragAppearance
     * dragAppearance} will be used.
     *
     * @return Current dragResizeAppearance value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public DragAppearance getDragResizeAppearance()  {
        return EnumUtil.getEnum(DragAppearance.values(), getAttribute("dragResizeAppearance"));
    }
    

    /**
     * If this widget supports drag-scrolling, This property specifies how many ms the      user must hover over the
     * drag-scroll threshold before scrolling begins.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragScrollDelay New dragScrollDelay value. Default value is 100
     */
    public void setDragScrollDelay(int dragScrollDelay) {
        setAttribute("dragScrollDelay", dragScrollDelay, true);
    }

    /**
     * If this widget supports drag-scrolling, This property specifies how many ms the      user must hover over the
     * drag-scroll threshold before scrolling begins.
     *
     * @return Current dragScrollDelay value. Default value is 100
     */
    public int getDragScrollDelay()  {
        return getAttributeAsInt("dragScrollDelay");
    }
    

    /**
     * Number of pixels the cursor needs to move before the EventHandler starts a drag operation.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragStartDistance New dragStartDistance value. Default value is 5
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragStartDistance(int dragStartDistance) {
        setAttribute("dragStartDistance", dragStartDistance, true);
    }

    /**
     * Number of pixels the cursor needs to move before the EventHandler starts a drag operation.
     *
     * @return Current dragStartDistance value. Default value is 5
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public int getDragStartDistance()  {
        return getAttributeAsInt("dragStartDistance");
    }
    
    

    /**
     * Sets a <code>dragType</code> for this widget used, to be compared to {@link
     * com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} on possible drop target widgets.  See {@link
     * com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} for a full explanation.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragType New dragType value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDragType(String dragType) {
        setAttribute("dragType", dragType, true);
    }

    /**
     * Sets a <code>dragType</code> for this widget used, to be compared to {@link
     * com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} on possible drop target widgets.  See {@link
     * com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} for a full explanation.
     *
     * @return Current dragType value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public String getDragType()  {
        return getAttributeAsString("dragType");
    }
    

    /**
     * When a drag and drop interaction occurs, if a {@link com.smartgwt.client.widgets.Canvas#getDragType dragType} is
     * configured on the source widget, it is compared to the <code>dropTypes</code> configured on the target widget, and a
     * drop is only allowed if the <code>dragType</code> is listed in the target widget's <code>dropTypes</code> array. <p> The
     * default setting means any <code>dragType</code> is eligible for dropping on this widget, including no
     * <code>dragType</code> setting. <p> See also {@link com.smartgwt.client.widgets.Canvas#willAcceptDrop willAcceptDrop()}
     * for dynamic determination of drop eligibility.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dropTypes New dropTypes value. Default value is Canvas.ANYTHING
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDropTypes(String... dropTypes) {
        setAttribute("dropTypes", dropTypes, true);
    }

    /**
     * When a drag and drop interaction occurs, if a {@link com.smartgwt.client.widgets.Canvas#getDragType dragType} is
     * configured on the source widget, it is compared to the <code>dropTypes</code> configured on the target widget, and a
     * drop is only allowed if the <code>dragType</code> is listed in the target widget's <code>dropTypes</code> array. <p> The
     * default setting means any <code>dragType</code> is eligible for dropping on this widget, including no
     * <code>dragType</code> setting. <p> See also {@link com.smartgwt.client.widgets.Canvas#willAcceptDrop willAcceptDrop()}
     * for dynamic determination of drop eligibility.
     *
     * @return Current dropTypes value. Default value is Canvas.ANYTHING
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public String[] getDropTypes()  {
        return com.smartgwt.client.util.ConvertTo.arrayOfString(getAttributeAsJavaScriptObject("dropTypes"));
    }

    /**
     * When a drag and drop interaction occurs, if a {@link com.smartgwt.client.widgets.Canvas#getDragType dragType} is
     * configured on the source widget, it is compared to the <code>dropTypes</code> configured on the target widget, and a
     * drop is only allowed if the <code>dragType</code> is listed in the target widget's <code>dropTypes</code> array. <p> The
     * default setting means any <code>dragType</code> is eligible for dropping on this widget, including no
     * <code>dragType</code> setting. <p> See also {@link com.smartgwt.client.widgets.Canvas#willAcceptDrop willAcceptDrop()}
     * for dynamic determination of drop eligibility.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dropTypes New dropTypes value. Default value is Canvas.ANYTHING
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setDropTypes(String dropTypes) {
        setAttribute("dropTypes", dropTypes, true);
    }

    /**
     * When a drag and drop interaction occurs, if a {@link com.smartgwt.client.widgets.Canvas#getDragType dragType} is
     * configured on the source widget, it is compared to the <code>dropTypes</code> configured on the target widget, and a
     * drop is only allowed if the <code>dragType</code> is listed in the target widget's <code>dropTypes</code> array. <p> The
     * default setting means any <code>dragType</code> is eligible for dropping on this widget, including no
     * <code>dragType</code> setting. <p> See also {@link com.smartgwt.client.widgets.Canvas#willAcceptDrop willAcceptDrop()}
     * for dynamic determination of drop eligibility.
     *
     * @return Current dropTypes value. Default value is Canvas.ANYTHING
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public String getDropTypesAsString()  {
        return getAttributeAsString("dropTypes");
    }
    

    /**
     * Dynamic contents allows the contents string to be treated as a simple, but powerful
     *  template.  When this attribute is set to true, expressions of the form &#36;{arbitrary JS
     *  here} are replaced by the result of the evaluation of the JS code inside the curly
     *  brackets.  This evaluation happens at draw time.  If you want to trigger a re-evaluation
     *  of the expressions in the contents string you can call markForRedraw() on the canvas.
     *  <p>
     *  You can use this feature to build some simple custom components. For example, let's say
     *  you want to show the value of a Slider in a Canvas somewhere on the screen.  You can do
     *  this by observing the valueChanged() method on the slider and calling setContents() on
     *  your canvas with the new string or you can set the contents of the canvas to something
     *  like:
     *  <p><code>
     *  "The slider value is &#36;{sliderInstance.getValue()}."
     *  </code><p>
     *  Next you set dynamicContents: true on the canvas, observe valueChanged() on the slider
     *  and call canvas.markForRedraw() in that observation.  This approach is cleaner than
     *  setContents() when the Canvas is aggregating several values or dynamic expressions.
     *  Like so:
     *  <p>
     *  <pre>
     *  Slider.create({
     *      ID: "mySlider"
     *  });
     * 
     *  Canvas.create({
     *      ID: "myCanvas",
     *      dynamicContents: true,
     *      contents: "The slider value is &#36;{mySlider.getValue()}."
     *  });
     * 
     *  myCanvas.observe(mySlider, "valueChanged",
     *                   "observer.markForRedraw()");
     *  </pre>
     *  You can embed an arbitrary number of dynamic expressions in the contents string.  The
     *  search and replace is optimized for speed.
     *  <p>
     *  If an error occurs during the evaluation of one of the expressions, a warning is logged
     *  to the ISC Developer Console and the error string is embedded in place of the expected
     *  value in the Canvas.
     *  <p>
     *  The value of a function is its return value.  The value of any variable is the same as
     *  that returned by its toString() representation.
     *  <p>
     *  Inside the evaluation contentext, <code>this</code> points to the canvas instance that
     *  has the dynamicContents string as its contents - in other words the canvas instance on
     *  which the template is declared.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dynamicContents New dynamicContents value. Default value is false
     * @see com.smartgwt.client.widgets.Canvas#setContents
     * @see com.smartgwt.client.widgets.Canvas#setDynamicContentsVars
     * 
     */
    public void setDynamicContents(Boolean dynamicContents) {
        setAttribute("dynamicContents", dynamicContents, true);
    }

    /**
     * Dynamic contents allows the contents string to be treated as a simple, but powerful
     *  template.  When this attribute is set to true, expressions of the form &#36;{arbitrary JS
     *  here} are replaced by the result of the evaluation of the JS code inside the curly
     *  brackets.  This evaluation happens at draw time.  If you want to trigger a re-evaluation
     *  of the expressions in the contents string you can call markForRedraw() on the canvas.
     *  <p>
     *  You can use this feature to build some simple custom components. For example, let's say
     *  you want to show the value of a Slider in a Canvas somewhere on the screen.  You can do
     *  this by observing the valueChanged() method on the slider and calling setContents() on
     *  your canvas with the new string or you can set the contents of the canvas to something
     *  like:
     *  <p><code>
     *  "The slider value is &#36;{sliderInstance.getValue()}."
     *  </code><p>
     *  Next you set dynamicContents: true on the canvas, observe valueChanged() on the slider
     *  and call canvas.markForRedraw() in that observation.  This approach is cleaner than
     *  setContents() when the Canvas is aggregating several values or dynamic expressions.
     *  Like so:
     *  <p>
     *  <pre>
     *  Slider.create({
     *      ID: "mySlider"
     *  });
     * 
     *  Canvas.create({
     *      ID: "myCanvas",
     *      dynamicContents: true,
     *      contents: "The slider value is &#36;{mySlider.getValue()}."
     *  });
     * 
     *  myCanvas.observe(mySlider, "valueChanged",
     *                   "observer.markForRedraw()");
     *  </pre>
     *  You can embed an arbitrary number of dynamic expressions in the contents string.  The
     *  search and replace is optimized for speed.
     *  <p>
     *  If an error occurs during the evaluation of one of the expressions, a warning is logged
     *  to the ISC Developer Console and the error string is embedded in place of the expected
     *  value in the Canvas.
     *  <p>
     *  The value of a function is its return value.  The value of any variable is the same as
     *  that returned by its toString() representation.
     *  <p>
     *  Inside the evaluation contentext, <code>this</code> points to the canvas instance that
     *  has the dynamicContents string as its contents - in other words the canvas instance on
     *  which the template is declared.
     *
     * @return Current dynamicContents value. Default value is false
     * @see com.smartgwt.client.widgets.Canvas#getContents
     * @see com.smartgwt.client.widgets.Canvas#getDynamicContentsVars
     * 
     */
    public Boolean getDynamicContents()  {
        Boolean result = getAttributeAsBoolean("dynamicContents");
        return result == null ? false : result;
    }
    
    

    /**
     * Background color for the EdgedCanvas created to decorate this component.  This can be used to provide an underlying
     * "tint" color for translucent edge media
     *
     * @param edgeBackgroundColor New edgeBackgroundColor value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     */
    public void setEdgeBackgroundColor(String edgeBackgroundColor)  throws IllegalStateException {
        setAttribute("edgeBackgroundColor", edgeBackgroundColor, false);
    }

    /**
     * Background color for the EdgedCanvas created to decorate this component.  This can be used to provide an underlying
     * "tint" color for translucent edge media
     *
     * @return Current edgeBackgroundColor value. Default value is null
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     */
    public String getEdgeBackgroundColor()  {
        return getAttributeAsString("edgeBackgroundColor");
    }
    

    /**
     * Background color for the center section only.  Can be used as a surrogate background color for the decorated Canvas, if
     * the Canvas is set to partially overlap the edges and hence can't show a background color itself without occluding media.
     *
     * @param edgeCenterBackgroundColor New edgeCenterBackgroundColor value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     */
    public void setEdgeCenterBackgroundColor(String edgeCenterBackgroundColor)  throws IllegalStateException {
        setAttribute("edgeCenterBackgroundColor", edgeCenterBackgroundColor, false);
    }

    /**
     * Background color for the center section only.  Can be used as a surrogate background color for the decorated Canvas, if
     * the Canvas is set to partially overlap the edges and hence can't show a background color itself without occluding media.
     *
     * @return Current edgeCenterBackgroundColor value. Default value is null
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     */
    public String getEdgeCenterBackgroundColor()  {
        return getAttributeAsString("edgeCenterBackgroundColor");
    }
    
    

    /**
     * Base name of images for edges.  Extensions for each corner or edge piece will be added to this image URL, before the
     * extension.  For example, with the default base name of "edge.gif", the top-left corner image will be "edge_TL.gif". <P>
     * The full list of extensions is: "_TL", "_TR", "_BL", "_BR", "_T", "_L", "_B", "_R", "_center".
     *
     * @param edgeImage New edgeImage value. Default value is "[SKIN]edge.gif"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public void setEdgeImage(String edgeImage)  throws IllegalStateException {
        setAttribute("edgeImage", edgeImage, false);
    }

    /**
     * Base name of images for edges.  Extensions for each corner or edge piece will be added to this image URL, before the
     * extension.  For example, with the default base name of "edge.gif", the top-left corner image will be "edge_TL.gif". <P>
     * The full list of extensions is: "_TL", "_TR", "_BL", "_BR", "_T", "_L", "_B", "_R", "_center".
     *
     * @return Current edgeImage value. Default value is "[SKIN]edge.gif"
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public String getEdgeImage()  {
        return getAttributeAsString("edgeImage");
    }
    

    /**
     * How far into the edge of an object do we consider the "edge" for drag resize purposes?
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param edgeMarginSize New edgeMarginSize value. Default value is 5
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public void setEdgeMarginSize(int edgeMarginSize) {
        setAttribute("edgeMarginSize", edgeMarginSize, true);
    }

    /**
     * How far into the edge of an object do we consider the "edge" for drag resize purposes?
     *
     * @return Current edgeMarginSize value. Default value is 5
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public int getEdgeMarginSize()  {
        return getAttributeAsInt("edgeMarginSize");
    }
    

    /**
     * Amount the contained Canvas should be offset.  Defaults to edgeSize; set to less than edgeSize to allow the contained
     * Canvas to overlap the edge and corner media.
     *
     * @param edgeOffset New edgeOffset value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_corners" target="examples">Corners Example</a>
     */
    public void setEdgeOffset(Integer edgeOffset)  throws IllegalStateException {
        setAttribute("edgeOffset", edgeOffset, false);
    }

    /**
     * Amount the contained Canvas should be offset.  Defaults to edgeSize; set to less than edgeSize to allow the contained
     * Canvas to overlap the edge and corner media.
     *
     * @return Current edgeOffset value. Default value is null
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_corners" target="examples">Corners Example</a>
     */
    public Integer getEdgeOffset()  {
        return getAttributeAsInt("edgeOffset");
    }
    

    /**
     * Opacity of the edges.  Defaults to matching this.opacity. if {@link com.smartgwt.client.widgets.Canvas#setOpacity
     * setOpacity()} is called on a Canvas where edgeOpacity is set, edgeOpacity will be considered a percentage of the
     * parent's opacity (so 50% opaque parent plus edgeOpacity 50 means 25% opaque edges)
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the {@link com.smartgwt.client.widgets.Canvas#getEdgeOpacity edgeOpacity} and mark the canvas for redraw
     *
     * @param edgeOpacity new edge-opacity level. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public void setEdgeOpacity(Integer edgeOpacity)  throws IllegalStateException {
        setAttribute("edgeOpacity", edgeOpacity, false);
    }

    /**
     * Opacity of the edges.  Defaults to matching this.opacity. if {@link com.smartgwt.client.widgets.Canvas#setOpacity
     * setOpacity()} is called on a Canvas where edgeOpacity is set, edgeOpacity will be considered a percentage of the
     * parent's opacity (so 50% opaque parent plus edgeOpacity 50 means 25% opaque edges)
     *
     * @return Current edgeOpacity value. Default value is null
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public Integer getEdgeOpacity()  {
        return getAttributeAsInt("edgeOpacity");
    }
    

    /**
     * Whether to show media in the center section, that is, behind the decorated Canvas.
     *
     * @param edgeShowCenter New edgeShowCenter value. Default value is false
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_corners" target="examples">Corners Example</a>
     */
    public void setEdgeShowCenter(Boolean edgeShowCenter)  throws IllegalStateException {
        setAttribute("edgeShowCenter", edgeShowCenter, false);
    }

    /**
     * Whether to show media in the center section, that is, behind the decorated Canvas.
     *
     * @return Current edgeShowCenter value. Default value is false
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_corners" target="examples">Corners Example</a>
     */
    public Boolean getEdgeShowCenter()  {
        Boolean result = getAttributeAsBoolean("edgeShowCenter");
        return result == null ? false : result;
    }
    

    /**
     * Size in pixels for corners and edges
     *
     * @param edgeSize New edgeSize value. Default value is 10
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public void setEdgeSize(int edgeSize)  throws IllegalStateException {
        setAttribute("edgeSize", edgeSize, false);
    }

    /**
     * Size in pixels for corners and edges
     *
     * @return Current edgeSize value. Default value is 10
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public int getEdgeSize()  {
        return getAttributeAsInt("edgeSize");
    }
    

    /**
     * The component's {@link com.smartgwt.client.tools.EditNode} for a component that has been created by a {@link
     * com.smartgwt.client.tools.Palette} from a {@link com.smartgwt.client.tools.PaletteNode}.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current editNode value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     */
    public EditNode getEditNode() throws IllegalStateException {
        errorIfNotCreated("editNode");
        return new EditNode(getAttributeAsJavaScriptObject("editNode"));
    }
    

    /**
     * An {@link com.smartgwt.client.tools.EditProxy} controls the behaviors of a component when it is placed into {@link
     * com.smartgwt.client.docs.DevTools editing mode}. <p> The <code>editProxy</code> AutoChild is created when a component is
     * first placed into edit mode via {@link com.smartgwt.client.widgets.Canvas#setEditMode setEditMode()}. <p>
     * <code>editProxy</code> properties can be supplied on a {@link com.smartgwt.client.tools.PaletteNode} or {@link
     * com.smartgwt.client.tools.EditNode} as {@link com.smartgwt.client.tools.PaletteNode#getEditProxyProperties
     * editProxyProperties}, but must be provided before the component is first placed into edit mode. <p> Most editable
     * components use a custom EditProxy. See the documentation for each class' {@link
     * com.smartgwt.client.widgets.Canvas#getEditProxyConstructor editProxyConstructor} to determine the class.
     * <p>
     * This component is an AutoChild named "editProxy".  For an overview of how to use and
     * configure AutoChildren, see {@link com.smartgwt.client.docs.AutoChildUsage Using AutoChildren}.
     *
     * @return Current editProxy value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     * @see com.smartgwt.client.widgets.Canvas#setEditMode
     */
    public EditProxy getEditProxy() throws IllegalStateException {
        errorIfNotCreated("editProxy");
        return EditProxy.getOrCreateRef(getAttributeAsJavaScriptObject("editProxy"));
    }
    

    /**
     * Default class used to construct the {@link com.smartgwt.client.tools.EditProxy} for this component when the component is
     * {@link com.smartgwt.client.widgets.Canvas#setEditMode first placed into edit mode}.
     *
     * @param editProxyConstructor New editProxyConstructor value. Default value is "CanvasEditProxy"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.SCClassName SCClassName 
     */
    public void setEditProxyConstructor(String editProxyConstructor)  throws IllegalStateException {
        setAttribute("editProxyConstructor", editProxyConstructor, false);
    }

    /**
     * Default class used to construct the {@link com.smartgwt.client.tools.EditProxy} for this component when the component is
     * {@link com.smartgwt.client.widgets.Canvas#setEditMode first placed into edit mode}.
     *
     * @return Current editProxyConstructor value. Default value is "CanvasEditProxy"
     * @see com.smartgwt.client.docs.SCClassName SCClassName 
     */
    public String getEditProxyConstructor()  {
        return getAttributeAsString("editProxyConstructor");
    }
    
    

    /**
     * Criteria to be evaluated to determine whether this Canvas should be enabled.  Re-evaluated whenever data in the {@link
     * com.smartgwt.client.widgets.Canvas#getRuleScope ruleScope} changes. <P> A basic criteria uses textMatchStyle:"exact".
     * When specified in {@link com.smartgwt.client.docs.ComponentXML Component XML} this property allows {@link
     * com.smartgwt.client.docs.XmlCriteriaShorthand shorthand formats} for defining criteria.
     *
     * @param enableWhen New enableWhen value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.RuleCriteria RuleCriteria overview and related methods
     */
    public void setEnableWhen(AdvancedCriteria enableWhen)  throws IllegalStateException {
        if (enableWhen instanceof Criterion) {
            enableWhen.setAttribute("_constructor", "AdvancedCriteria");
        }
        setAttribute("enableWhen", enableWhen == null ? null : enableWhen.getJsObj(), false);
    }

    /**
     * Criteria to be evaluated to determine whether this Canvas should be enabled.  Re-evaluated whenever data in the {@link
     * com.smartgwt.client.widgets.Canvas#getRuleScope ruleScope} changes. <P> A basic criteria uses textMatchStyle:"exact".
     * When specified in {@link com.smartgwt.client.docs.ComponentXML Component XML} this property allows {@link
     * com.smartgwt.client.docs.XmlCriteriaShorthand shorthand formats} for defining criteria.
     *
     * @return Current enableWhen value. Default value is null
     * @see com.smartgwt.client.docs.RuleCriteria RuleCriteria overview and related methods
     */
    public AdvancedCriteria getEnableWhen()  {
        return new AdvancedCriteria(getAttributeAsJavaScriptObject("enableWhen"));
    }
    

    /**
     * Whether this canvas should end the line it's in when used as a tile in a {@link
     * com.smartgwt.client.widgets.layout.FlowLayout}. This property is not supported in a {@link
     * com.smartgwt.client.widgets.tile.TileLayout} with {@link com.smartgwt.client.widgets.tile.TileLayout#getLayoutPolicy
     * TileLayout.layoutPolicy}: "fit" or if databound (i.e.{@link com.smartgwt.client.widgets.tile.TileGrid}).
     *
     * @param endLine New endLine value. Default value is false
     * @see com.smartgwt.client.widgets.tile.TileLayout#setAutoWrapLines
     */
    public void setEndLine(Boolean endLine) {
        setAttribute("endLine", endLine, true);
    }

    /**
     * Whether this canvas should end the line it's in when used as a tile in a {@link
     * com.smartgwt.client.widgets.layout.FlowLayout}. This property is not supported in a {@link
     * com.smartgwt.client.widgets.tile.TileLayout} with {@link com.smartgwt.client.widgets.tile.TileLayout#getLayoutPolicy
     * TileLayout.layoutPolicy}: "fit" or if databound (i.e.{@link com.smartgwt.client.widgets.tile.TileGrid}).
     *
     * @return Current endLine value. Default value is false
     * @see com.smartgwt.client.widgets.tile.TileLayout#getAutoWrapLines
     */
    public Boolean getEndLine()  {
        Boolean result = getAttributeAsBoolean("endLine");
        return result == null ? false : result;
    }
    

    /**
     * When this Canvas is included as a member in a Layout, extra blank space that should be left after this member in a
     * Layout.
     *
     * @param extraSpace New extraSpace value. Default value is 0
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.layout.LayoutSpacer
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     */
    public void setExtraSpace(int extraSpace)  throws IllegalStateException {
        setAttribute("extraSpace", extraSpace, false);
    }

    /**
     * When this Canvas is included as a member in a Layout, extra blank space that should be left after this member in a
     * Layout.
     *
     * @return Current extraSpace value. Default value is 0
     * @see com.smartgwt.client.widgets.layout.LayoutSpacer
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     */
    public int getExtraSpace()  {
        return getAttributeAsInt("extraSpace");
    }
    

    /**
     * Sets the style for the grouping frame around the canvas. Only necessary when {@link
     * com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is set to true.
     *
     * @param groupBorderCSS New groupBorderCSS value. Default value is "2px solid black"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setGroupBorderCSS(String groupBorderCSS)  throws IllegalStateException {
        setAttribute("groupBorderCSS", groupBorderCSS, false);
    }

    /**
     * Sets the style for the grouping frame around the canvas. Only necessary when {@link
     * com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is set to true.
     *
     * @return Current groupBorderCSS value. Default value is "2px solid black"
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getGroupBorderCSS()  {
        return getAttributeAsString("groupBorderCSS");
    }
    

    /**
     * If set, the background color of the grouping label. Only applicable when {@link
     * com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is set to true. <p> This corresponds to the CSS background-color
     * property on the grouping label. You can set this property to an RGB value (e.g. #22AAFF) or a named color (e.g. red)
     * from a list of browser supported color names. <p> The getter for this attribute, {@link
     * #getGroupLabelBackgroundColor()}, returns the color that will actually be used; i.e. if groupLabelBackgroundColor is
     * left unset or is set to null, then getGroupLabelBackgroundColor() returns the color string that will be used.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for {@link com.smartgwt.client.widgets.Canvas#getGroupLabelBackgroundColor groupLabelBackgroundColor}.
     *
     * @param groupLabelBackgroundColor the new grouping label background color. Default value is null
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setGroupLabelBackgroundColor(String groupLabelBackgroundColor) {
        setAttribute("groupLabelBackgroundColor", groupLabelBackgroundColor, true);
    }

    /**
     * If set, the background color of the grouping label. Only applicable when {@link
     * com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is set to true. <p> This corresponds to the CSS background-color
     * property on the grouping label. You can set this property to an RGB value (e.g. #22AAFF) or a named color (e.g. red)
     * from a list of browser supported color names. <p> The getter for this attribute, {@link
     * #getGroupLabelBackgroundColor()}, returns the color that will actually be used; i.e. if groupLabelBackgroundColor is
     * left unset or is set to null, then getGroupLabelBackgroundColor() returns the color string that will be used.
     *
     * @return Current groupLabelBackgroundColor value. Default value is null
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getGroupLabelBackgroundColor()  {
        return getAttributeAsString("groupLabelBackgroundColor");
    }
    

    /**
     * Sets the style for the grouping label. Only necessary when {@link com.smartgwt.client.widgets.Canvas#getIsGroup isGroup}
     * is set to true. <p> Note that {@link com.smartgwt.client.widgets.Canvas#getGroupLabelBackgroundColor
     * groupLabelBackgroundColor} overrides any background-color of this style.
     *
     * @param groupLabelStyleName New groupLabelStyleName value. Default value is "groupLabel"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.CSSStyleName CSSStyleName 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setGroupLabelStyleName(String groupLabelStyleName)  throws IllegalStateException {
        setAttribute("groupLabelStyleName", groupLabelStyleName, false);
    }

    /**
     * Sets the style for the grouping label. Only necessary when {@link com.smartgwt.client.widgets.Canvas#getIsGroup isGroup}
     * is set to true. <p> Note that {@link com.smartgwt.client.widgets.Canvas#getGroupLabelBackgroundColor
     * groupLabelBackgroundColor} overrides any background-color of this style.
     *
     * @return Current groupLabelStyleName value. Default value is "groupLabel"
     * @see com.smartgwt.client.docs.CSSStyleName CSSStyleName 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getGroupLabelStyleName()  {
        return getAttributeAsString("groupLabelStyleName");
    }
    

    /**
     * The title/label for the grouping. Only applicable when {@link com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is
     * set to true.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for {@link com.smartgwt.client.widgets.Canvas#getGroupTitle groupTitle}.
     *
     * @param groupTitle The new title for the grouping. Default value is null
     * @see com.smartgwt.client.docs.HTMLString HTMLString 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setGroupTitle(String groupTitle) {
        setAttribute("groupTitle", groupTitle, true);
    }

    /**
     * The title/label for the grouping. Only applicable when {@link com.smartgwt.client.widgets.Canvas#getIsGroup isGroup} is
     * set to true.
     *
     * @return Current groupTitle value. Default value is null
     * @see com.smartgwt.client.docs.HTMLString HTMLString 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getGroupTitle()  {
        return getAttributeAsString("groupTitle");
    }
    

    /**
     * Size for this component's vertical dimension. <P> Can be a number of pixels, or a percentage like "50%". See
     * documentation for {@link com.smartgwt.client.widgets.Canvas#getWidth width} for details on how percentage values are
     * resolved actual size. <P> Note that if {@link com.smartgwt.client.widgets.Canvas#getOverflow overflow} is set to
     * "visible", this size is a minimum, and the component may overflow to show all content and/or children. <P> If trying to
     * establish a default height for a custom component, set {@link com.smartgwt.client.widgets.Canvas#getDefaultHeight
     * defaultHeight} instead.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Resizes the widget vertically to the specified height (moves the bottom side of the widget). The height parameter can be expressed as a percentage of viewport size or as the number of pixels. <P> NOTE: if you're setting multiple coordinates, use resizeTo() or setRect() instead
     *
     * @param height new height. Default value is null
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setHeight(Integer height) {
        setAttribute("height", height, true);
    }

    /**
     * Size for this component's vertical dimension. <P> Can be a number of pixels, or a percentage like "50%". See
     * documentation for {@link com.smartgwt.client.widgets.Canvas#getWidth width} for details on how percentage values are
     * resolved actual size. <P> Note that if {@link com.smartgwt.client.widgets.Canvas#getOverflow overflow} is set to
     * "visible", this size is a minimum, and the component may overflow to show all content and/or children. <P> If trying to
     * establish a default height for a custom component, set {@link com.smartgwt.client.widgets.Canvas#getDefaultHeight
     * defaultHeight} instead.
     *
     * @return Return the height of this object, in pixels. Default value is null
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public Integer getHeight()  {
        return getAttributeAsInt("height");
    }

    /**
     * Size for this component's vertical dimension. <P> Can be a number of pixels, or a percentage like "50%". See
     * documentation for {@link com.smartgwt.client.widgets.Canvas#getWidth width} for details on how percentage values are
     * resolved actual size. <P> Note that if {@link com.smartgwt.client.widgets.Canvas#getOverflow overflow} is set to
     * "visible", this size is a minimum, and the component may overflow to show all content and/or children. <P> If trying to
     * establish a default height for a custom component, set {@link com.smartgwt.client.widgets.Canvas#getDefaultHeight
     * defaultHeight} instead.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Resizes the widget vertically to the specified height (moves the bottom side of the widget). The height parameter can be expressed as a percentage of viewport size or as the number of pixels. <P> NOTE: if you're setting multiple coordinates, use resizeTo() or setRect() instead
     *
     * @param height new height. Default value is null
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setHeight(String height) {
        setAttribute("height", height, true);
    }
    

    /**
     * When this widget is hidden (see {@link com.smartgwt.client.widgets.Canvas#getVisibility visibility} and {@link
     * com.smartgwt.client.widgets.Canvas#hide hide()}), should display:none be applied to the {@link
     * com.smartgwt.client.widgets.Canvas#getOuterElement outer element}? <p> This setting is not supported for general use,
     * but in certain cases, it has been shown that display:none is a work-around for browser bugs involving burn-through of
     * iframes or plugins, where the content of the iframe or plugin may still be visible despite the containing widget being
     * hidden.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param hideUsingDisplayNone New hideUsingDisplayNone value. Default value is false
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setHideUsingDisplayNone(boolean hideUsingDisplayNone)  throws IllegalStateException {
        setAttribute("hideUsingDisplayNone", hideUsingDisplayNone, false);
    }

    /**
     * When this widget is hidden (see {@link com.smartgwt.client.widgets.Canvas#getVisibility visibility} and {@link
     * com.smartgwt.client.widgets.Canvas#hide hide()}), should display:none be applied to the {@link
     * com.smartgwt.client.widgets.Canvas#getOuterElement outer element}? <p> This setting is not supported for general use,
     * but in certain cases, it has been shown that display:none is a work-around for browser bugs involving burn-through of
     * iframes or plugins, where the content of the iframe or plugin may still be visible despite the containing widget being
     * hidden.
     *
     * @return Current hideUsingDisplayNone value. Default value is false
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public boolean getHideUsingDisplayNone()  {
        Boolean result = getAttributeAsBoolean("hideUsingDisplayNone");
        return result == null ? false : result;
    }
    

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the alignment of content in the hover
     * canvas.
     *
     * @param hoverAlign New hoverAlign value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverAlign(Alignment hoverAlign) {
        setAttribute("hoverAlign", hoverAlign == null ? null : hoverAlign.getValue(), true);
    }

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the alignment of content in the hover
     * canvas.
     *
     * @return Current hoverAlign value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public Alignment getHoverAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("hoverAlign"));
    }
    

    /**
     * If <code>this.showHover</code> is true and {@link com.smartgwt.client.widgets.Canvas#getHoverComponent
     * getHoverComponent()} is implemented, should the hoverCanvas returned from it be automatically destroyed when it is
     * hidden? <P> The default of null indicates that the component <b>will</b> be automatically destroyed.  Set to false to
     * prevent this.
     *
     * @param hoverAutoDestroy New hoverAutoDestroy value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverAutoDestroy(Boolean hoverAutoDestroy) {
        setAttribute("hoverAutoDestroy", hoverAutoDestroy, true);
    }

    /**
     * If <code>this.showHover</code> is true and {@link com.smartgwt.client.widgets.Canvas#getHoverComponent
     * getHoverComponent()} is implemented, should the hoverCanvas returned from it be automatically destroyed when it is
     * hidden? <P> The default of null indicates that the component <b>will</b> be automatically destroyed.  Set to false to
     * prevent this.
     *
     * @return Current hoverAutoDestroy value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public Boolean getHoverAutoDestroy()  {
        return getAttributeAsBoolean("hoverAutoDestroy");
    }
    

    /**
     * Maximum auto-fit width for a hover if {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth hoverAutoFitWidth}
     * is  enabled. May be specified as a pixel value, or a percentage of page width.
     *
     * @param hoverAutoFitMaxWidth New hoverAutoFitMaxWidth value. Default value is 100
     */
    public void setHoverAutoFitMaxWidth(int hoverAutoFitMaxWidth) {
        setAttribute("hoverAutoFitMaxWidth", hoverAutoFitMaxWidth, true);
    }

    /**
     * Maximum auto-fit width for a hover if {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth hoverAutoFitWidth}
     * is  enabled. May be specified as a pixel value, or a percentage of page width.
     *
     * @return Current hoverAutoFitMaxWidth value. Default value is 100
     */
    public int getHoverAutoFitMaxWidth()  {
        return getAttributeAsInt("hoverAutoFitMaxWidth");
    }

    /**
     * Maximum auto-fit width for a hover if {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth hoverAutoFitWidth}
     * is  enabled. May be specified as a pixel value, or a percentage of page width.
     *
     * @param hoverAutoFitMaxWidth New hoverAutoFitMaxWidth value. Default value is 100
     */
    public void setHoverAutoFitMaxWidth(String hoverAutoFitMaxWidth) {
        setAttribute("hoverAutoFitMaxWidth", hoverAutoFitMaxWidth, true);
    }

    /**
     * Maximum auto-fit width for a hover if {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth hoverAutoFitWidth}
     * is  enabled. May be specified as a pixel value, or a percentage of page width.
     *
     * @return Current hoverAutoFitMaxWidth value. Default value is 100
     */
    public String getHoverAutoFitMaxWidthAsString()  {
        return getAttributeAsString("hoverAutoFitMaxWidth");
    }
    

    /**
     * if {@link com.smartgwt.client.widgets.Canvas#getShowHover showHover} is true, this property will cause the specified 
     * {@link com.smartgwt.client.widgets.Canvas#getHoverWidth hoverWidth} to be treated as a minimum width for the hover. If
     * the hover content string exceeds this, the hover will expand to accomodate it up to {@link
     * com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth hoverAutoFitMaxWidth} (without the text wrapping). <P> Using
     * this settings differs from simply disabling wrapping via  {@link com.smartgwt.client.widgets.Canvas#getHoverWrap
     * hoverWrap:false} as the content will wrap if the {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth
     * hoverAutoFitMaxWidth} is exceeded.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param hoverAutoFitWidth New hoverAutoFitWidth value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverAutoFitWidth(Boolean hoverAutoFitWidth) {
        setAttribute("hoverAutoFitWidth", hoverAutoFitWidth, true);
    }

    /**
     * if {@link com.smartgwt.client.widgets.Canvas#getShowHover showHover} is true, this property will cause the specified 
     * {@link com.smartgwt.client.widgets.Canvas#getHoverWidth hoverWidth} to be treated as a minimum width for the hover. If
     * the hover content string exceeds this, the hover will expand to accomodate it up to {@link
     * com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth hoverAutoFitMaxWidth} (without the text wrapping). <P> Using
     * this settings differs from simply disabling wrapping via  {@link com.smartgwt.client.widgets.Canvas#getHoverWrap
     * hoverWrap:false} as the content will wrap if the {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth
     * hoverAutoFitMaxWidth} is exceeded.
     *
     * @return Current hoverAutoFitWidth value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public Boolean getHoverAutoFitWidth()  {
        Boolean result = getAttributeAsBoolean("hoverAutoFitWidth");
        return result == null ? true : result;
    }
    

    /**
     * If <code>this.canHover</code> is true, how long should the mouse be kept over this widget before the hover event is
     * fired
     *
     * @param hoverDelay New hoverDelay value. Default value is 300
     * @see com.smartgwt.client.widgets.Canvas#setCanHover
     * @see com.smartgwt.client.widgets.events.HoverEvent
     */
    public void setHoverDelay(int hoverDelay) {
        setAttribute("hoverDelay", hoverDelay, true);
    }

    /**
     * If <code>this.canHover</code> is true, how long should the mouse be kept over this widget before the hover event is
     * fired
     *
     * @return Current hoverDelay value. Default value is 300
     * @see com.smartgwt.client.widgets.Canvas#getCanHover
     * @see com.smartgwt.client.widgets.events.HoverEvent
     */
    public int getHoverDelay()  {
        return getAttributeAsInt("hoverDelay");
    }
    

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the height of the hover canvas shown.
     *
     * @param hoverHeight New hoverHeight value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverHeight(Integer hoverHeight) {
        setAttribute("hoverHeight", hoverHeight, true);
    }

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the height of the hover canvas shown.
     *
     * @return Current hoverHeight value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public Integer getHoverHeight()  {
        return getAttributeAsInt("hoverHeight");
    }
    

    /**
     * If <code>this.showHover</code> is true, should this widget's hover canvas be moved with the mouse while visible?
     *
     * @param hoverMoveWithMouse New hoverMoveWithMouse value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverMoveWithMouse(Boolean hoverMoveWithMouse) {
        setAttribute("hoverMoveWithMouse", hoverMoveWithMouse, true);
    }

    /**
     * If <code>this.showHover</code> is true, should this widget's hover canvas be moved with the mouse while visible?
     *
     * @return Current hoverMoveWithMouse value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public Boolean getHoverMoveWithMouse()  {
        return getAttributeAsBoolean("hoverMoveWithMouse");
    }
    

    /**
     * If <code>this.showHover</code> is true, should the hover canvas be shown with opacity other than 100?
     *
     * @param hoverOpacity New hoverOpacity value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public void setHoverOpacity(Integer hoverOpacity) {
        setAttribute("hoverOpacity", hoverOpacity, true);
    }

    /**
     * If <code>this.showHover</code> is true, should the hover canvas be shown with opacity other than 100?
     *
     * @return Current hoverOpacity value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public Integer getHoverOpacity()  {
        return getAttributeAsInt("hoverOpacity");
    }
    

    /**
     * If <code>this.showHover</code> is true, this property can be used to specify the css style to apply to the hover canvas.
     *
     * @param hoverStyle New hoverStyle value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     * @see com.smartgwt.client.docs.CSSStyleName CSSStyleName 
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public void setHoverStyle(String hoverStyle) {
        setAttribute("hoverStyle", hoverStyle, true);
    }

    /**
     * If <code>this.showHover</code> is true, this property can be used to specify the css style to apply to the hover canvas.
     *
     * @return Current hoverStyle value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     * @see com.smartgwt.client.docs.CSSStyleName CSSStyleName 
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public String getHoverStyle()  {
        return getAttributeAsString("hoverStyle");
    }
    

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the vertical alignment of content in the
     * hover canvas.
     *
     * @param hoverVAlign New hoverVAlign value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverVAlign(VerticalAlignment hoverVAlign) {
        setAttribute("hoverVAlign", hoverVAlign == null ? null : hoverVAlign.getValue(), true);
    }

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the vertical alignment of content in the
     * hover canvas.
     *
     * @return Current hoverVAlign value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public VerticalAlignment getHoverVAlign()  {
        return EnumUtil.getEnum(VerticalAlignment.values(), getAttribute("hoverVAlign"));
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getShowHover this.showHover} is true, this property can be used to
     * customize the width of the hover canvas shown. See also {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth
     * hoverAutoFitWidth} and  {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth hoverAutoFitMaxWidth}.
     *
     * @param hoverWidth New hoverWidth value. Default value is 1
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public void setHoverWidth(int hoverWidth) {
        setAttribute("hoverWidth", hoverWidth, true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getShowHover this.showHover} is true, this property can be used to
     * customize the width of the hover canvas shown. See also {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth
     * hoverAutoFitWidth} and  {@link com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth hoverAutoFitMaxWidth}.
     *
     * @return Current hoverWidth value. Default value is 1
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public int getHoverWidth()  {
        return getAttributeAsInt("hoverWidth");
    }
    

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the whether content in the hover canvas
     * is displayed in a single line, or wraps. <P> Note that if developers wish to have hovers expand horizontally to fit
     * their text without wrapping *up to some maximum*, and then wrap rather than exceeding that maximum, the {@link
     * com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth hoverAutoFitWidth} and {@link
     * com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth hoverAutoFitMaxWidth} attributes may be used instead of
     * simply setting hoverWrap to false.
     *
     * @param hoverWrap New hoverWrap value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setShowHover
     */
    public void setHoverWrap(Boolean hoverWrap) {
        setAttribute("hoverWrap", hoverWrap, true);
    }

    /**
     * If <code>this.showHover</code> is true, this property can be used to customize the whether content in the hover canvas
     * is displayed in a single line, or wraps. <P> Note that if developers wish to have hovers expand horizontally to fit
     * their text without wrapping *up to some maximum*, and then wrap rather than exceeding that maximum, the {@link
     * com.smartgwt.client.widgets.Canvas#getHoverAutoFitWidth hoverAutoFitWidth} and {@link
     * com.smartgwt.client.widgets.Canvas#getHoverAutoFitMaxWidth hoverAutoFitMaxWidth} attributes may be used instead of
     * simply setting hoverWrap to false.
     *
     * @return Current hoverWrap value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public Boolean getHoverWrap()  {
        return getAttributeAsBoolean("hoverWrap");
    }
    

    /**
     * If specified as a pointer to an HTML element present in the DOM, this canvas will be rendered inside that element on
     * {@link com.smartgwt.client.widgets.Canvas#draw draw()}. <P> <i>NOTES:</i><br> This feature is intended for temporary
     * integration with legacy page architectures only; the native browser's reaction to DOM insertion is unspecified and
     * unsupported. For consistent cross-browser layout and positioning semantics, use Canvas parents (especially Layouts) and
     * use absolute positioning at top level. <P> Persistence of htmlElement: If {@link
     * com.smartgwt.client.widgets.Canvas#getHtmlPosition htmlPosition} is set to <code>"replace"</code> the htmlElement will
     * be removed from the DOM when the canvas is drawn - therefore the htmlElement attribute will be cleared at this time.
     * Otherwise if a Canvas is clear()d and then draw()n again it will be rendered inside the same htmlElement.<br> If a
     * Canvas is added as a child to Canvas parent, its htmlElement will be dropped. <P> {@link
     * com.smartgwt.client.widgets.Canvas#getPosition position} should typically be set to <code>"relative"</code> if the
     * widget is to be rendered inline within a standard page.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for the {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param htmlElement New htmlElement for this canvas, or Null to clear the existing                             htmlElement. Default value is null
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setHtmlElement(Element htmlElement) {
        setAttribute("htmlElement", htmlElement, true);
    }

    /**
     * If specified as a pointer to an HTML element present in the DOM, this canvas will be rendered inside that element on
     * {@link com.smartgwt.client.widgets.Canvas#draw draw()}. <P> <i>NOTES:</i><br> This feature is intended for temporary
     * integration with legacy page architectures only; the native browser's reaction to DOM insertion is unspecified and
     * unsupported. For consistent cross-browser layout and positioning semantics, use Canvas parents (especially Layouts) and
     * use absolute positioning at top level. <P> Persistence of htmlElement: If {@link
     * com.smartgwt.client.widgets.Canvas#getHtmlPosition htmlPosition} is set to <code>"replace"</code> the htmlElement will
     * be removed from the DOM when the canvas is drawn - therefore the htmlElement attribute will be cleared at this time.
     * Otherwise if a Canvas is clear()d and then draw()n again it will be rendered inside the same htmlElement.<br> If a
     * Canvas is added as a child to Canvas parent, its htmlElement will be dropped. <P> {@link
     * com.smartgwt.client.widgets.Canvas#getPosition position} should typically be set to <code>"relative"</code> if the
     * widget is to be rendered inline within a standard page.
     *
     * @return Current htmlElement value. Default value is null
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public Element getHtmlElement()  {
        return getAttributeAsElement("htmlElement");
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} is specified, this attribute specifies the
     * position where the canvas should be inserted relative to the <code>htmlElement</code> in the DOM.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for the {@link com.smartgwt.client.widgets.Canvas#getHtmlPosition htmlPosition}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param htmlPosition New htmlPosition for this canvas. Default value is "afterBegin"
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setHtmlPosition(DrawPosition htmlPosition) {
        setAttribute("htmlPosition", htmlPosition == null ? null : htmlPosition.getValue(), true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} is specified, this attribute specifies the
     * position where the canvas should be inserted relative to the <code>htmlElement</code> in the DOM.
     *
     * @return Current htmlPosition value. Default value is "afterBegin"
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public DrawPosition getHtmlPosition()  {
        return EnumUtil.getEnum(DrawPosition.values(), getAttribute("htmlPosition"));
    }
    
    

    /**
     * Should a grouping frame be shown around this canvas.
     *
     * @param isGroup New isGroup value. Default value is false
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setGroupBorderCSS
     * @see com.smartgwt.client.widgets.Canvas#setGroupLabelStyleName
     * @see com.smartgwt.client.widgets.Canvas#setGroupLabelBackgroundColor
     */
    public void setIsGroup(boolean isGroup)  throws IllegalStateException {
        setAttribute("isGroup", isGroup, false);
    }

    /**
     * Should a grouping frame be shown around this canvas.
     *
     * @return Current isGroup value. Default value is false
     * @see com.smartgwt.client.widgets.Canvas#getGroupBorderCSS
     * @see com.smartgwt.client.widgets.Canvas#getGroupLabelStyleName
     * @see com.smartgwt.client.widgets.Canvas#getGroupLabelBackgroundColor
     */
    public boolean getIsGroup()  {
        Boolean result = getAttributeAsBoolean("isGroup");
        return result == null ? false : result;
    }
    

    /**
     * This boolean flag will be set to true by framework logic while generating print HTML  for this widget as a result to a
     * call to {@link com.smartgwt.client.widgets.Canvas#showPrintPreview showPrintPreview()}  (or just {@link
     * com.smartgwt.client.widgets.Canvas#getPrintHTML getPrintHTML()}). Note that this flag is set recursively as parent
     * widgets generate print HTML for their children. <P> This is a read-only property and should not be modified by
     * application code.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current isPrinting value. Default value is false
     * @throws IllegalStateException if this widget has not yet been rendered.
     */
    public Boolean getIsPrinting() throws IllegalStateException {
        errorIfNotCreated("isPrinting");
        Boolean result = getAttributeAsBoolean("isPrinting");
        return result == null ? false : result;
    }
    

    /**
     * Marks this Canvas as the {@link com.smartgwt.client.widgets.Canvas#getRuleScope ruleScope} that will be discovered by
     * any contained {@link com.smartgwt.client.widgets.DataBoundComponent}s which do not specify an explicit
     * <code>ruleScope</code>.
     *
     * @param isRuleScope New isRuleScope value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setIsRuleScope(Boolean isRuleScope)  throws IllegalStateException {
        setAttribute("isRuleScope", isRuleScope, false);
    }

    /**
     * Marks this Canvas as the {@link com.smartgwt.client.widgets.Canvas#getRuleScope ruleScope} that will be discovered by
     * any contained {@link com.smartgwt.client.widgets.DataBoundComponent}s which do not specify an explicit
     * <code>ruleScope</code>.
     *
     * @return Current isRuleScope value. Default value is null
     */
    public Boolean getIsRuleScope()  {
        return getAttributeAsBoolean("isRuleScope");
    }
    

    /**
     * Flag to disable snapping to alignment against this Canvas when <i>other</i> Canvases dragged into the same parent when
     * {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent.
     *
     * @param isSnapAlignCandidate New isSnapAlignCandidate value. Default value is null
     */
    public void setIsSnapAlignCandidate(Boolean isSnapAlignCandidate) {
        setAttribute("isSnapAlignCandidate", isSnapAlignCandidate, true);
    }

    /**
     * Flag to disable snapping to alignment against this Canvas when <i>other</i> Canvases dragged into the same parent when
     * {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent.
     *
     * @return Current isSnapAlignCandidate value. Default value is null
     */
    public Boolean getIsSnapAlignCandidate()  {
        return getAttributeAsBoolean("isSnapAlignCandidate");
    }
    

    /**
     * Constrains drag-resizing and drag-repositioning of this canvas to either the rect of its parent (if set to true) or an
     * arbitrary rect based on its parent (if set to a [Left,Top,Width,Height] rect array).  In the latter mode you may use
     * negative offsets for left/top and a width/height greater than the visible or scroll width of the parent to allow
     * positioning beyond the confines of the parent. <p> If this canvas has no parent, constrains dragging to within the
     * browser window. <p> Affects target and outline dragAppearance, not tracker. <p> Note: keepInParentRect affects only user
     * drag interactions, not programmatic moves. <p> <u>Example use cases:</u><br> <code>keepInParentRect: true</code> -
     * confine to parent<br> <code>keepInParentRect: [0, 0, 500, 500]</code> - confine to top left 500x500 region within
     * parent<br> <code>keepInParentRect: [0, 0, 10000, 10000]</code> - in combination with overflow: "auto", confine to
     * parent, but allow moving off the right and bottom of the parent to force scrolling (and hence enlarge the scrollWidth of
     * the parent).
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param keepInParentRect New keepInParentRect value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public void setKeepInParentRect(Boolean keepInParentRect) {
        setAttribute("keepInParentRect", keepInParentRect, true);
    }

    /**
     * Constrains drag-resizing and drag-repositioning of this canvas to either the rect of its parent (if set to true) or an
     * arbitrary rect based on its parent (if set to a [Left,Top,Width,Height] rect array).  In the latter mode you may use
     * negative offsets for left/top and a width/height greater than the visible or scroll width of the parent to allow
     * positioning beyond the confines of the parent. <p> If this canvas has no parent, constrains dragging to within the
     * browser window. <p> Affects target and outline dragAppearance, not tracker. <p> Note: keepInParentRect affects only user
     * drag interactions, not programmatic moves. <p> <u>Example use cases:</u><br> <code>keepInParentRect: true</code> -
     * confine to parent<br> <code>keepInParentRect: [0, 0, 500, 500]</code> - confine to top left 500x500 region within
     * parent<br> <code>keepInParentRect: [0, 0, 10000, 10000]</code> - in combination with overflow: "auto", confine to
     * parent, but allow moving off the right and bottom of the parent to force scrolling (and hence enlarge the scrollWidth of
     * the parent).
     *
     * @return Current keepInParentRect value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public Boolean getKeepInParentRect()  {
        return getAttributeAsBoolean("keepInParentRect");
    }

    /**
     * Constrains drag-resizing and drag-repositioning of this canvas to either the rect of its parent (if set to true) or an
     * arbitrary rect based on its parent (if set to a [Left,Top,Width,Height] rect array).  In the latter mode you may use
     * negative offsets for left/top and a width/height greater than the visible or scroll width of the parent to allow
     * positioning beyond the confines of the parent. <p> If this canvas has no parent, constrains dragging to within the
     * browser window. <p> Affects target and outline dragAppearance, not tracker. <p> Note: keepInParentRect affects only user
     * drag interactions, not programmatic moves. <p> <u>Example use cases:</u><br> <code>keepInParentRect: true</code> -
     * confine to parent<br> <code>keepInParentRect: [0, 0, 500, 500]</code> - confine to top left 500x500 region within
     * parent<br> <code>keepInParentRect: [0, 0, 10000, 10000]</code> - in combination with overflow: "auto", confine to
     * parent, but allow moving off the right and bottom of the parent to force scrolling (and hence enlarge the scrollWidth of
     * the parent).
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param keepInParentRect New keepInParentRect value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_create" target="examples">Drag create Example</a>
     */
    public void setKeepInParentRect(Integer... keepInParentRect) {
        setAttribute("keepInParentRect", keepInParentRect, true);
    }
    

    /**
     * When this Canvas is included as a member in a Layout, layoutAlign controls alignment on the breadth axis of the layout. 
     * Default is "left" for a VLayout, "top" for an HLayout.
     *
     * @param layoutAlign New layoutAlign value. Default value is null
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     * 
     */
    public void setLayoutAlign(Alignment layoutAlign) {
        setAttribute("layoutAlign", layoutAlign == null ? null : layoutAlign.getValue(), true);
    }

    /**
     * When this Canvas is included as a member in a Layout, layoutAlign controls alignment on the breadth axis of the layout. 
     * Default is "left" for a VLayout, "top" for an HLayout.
     *
     * @return Current layoutAlign value. Default value is null
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     * 
     */
    public Alignment getLayoutAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("layoutAlign"));
    }

    /**
     * When this Canvas is included as a member in a Layout, layoutAlign controls alignment on the breadth axis of the layout. 
     * Default is "left" for a VLayout, "top" for an HLayout.
     *
     * @param layoutAlign New layoutAlign value. Default value is null
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     * 
     */
    public void setLayoutAlign(VerticalAlignment layoutAlign) {
        setAttribute("layoutAlign", layoutAlign == null ? null : layoutAlign.getValue(), true);
    }
    

    /**
     * If set, overrides the global {@link com.smartgwt.client.widgets.Canvas#defaultPageSpace defaultPageSpace}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for {@link com.smartgwt.client.widgets.Canvas#getLeavePageSpace leavePageSpace}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param leavePageSpace new value for <code>leavePageSpace</code>. Default value is null
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setLeavePageSpace(Integer leavePageSpace) {
        setAttribute("leavePageSpace", leavePageSpace, true);
    }

    /**
     * If set, overrides the global {@link com.smartgwt.client.widgets.Canvas#defaultPageSpace defaultPageSpace}.
     *
     * @return Current leavePageSpace value. Default value is null
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public Integer getLeavePageSpace()  {
        return getAttributeAsInt("leavePageSpace");
    }
    

    /**
     * Number of pixels the left side of the widget is offset to the right from its default drawing context (either its
     * parent's topleft corner, or the document flow, depending on the value of the {@link
     * com.smartgwt.client.widgets.Canvas#getPosition position} property). <P> Can also be set as a percentage, specified as a
     * String ending in '%', eg, "50%".  In this case the top coordinate is considered as a percentage of the specified width
     * of the {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the left coordinate of this object, relative to its enclosing context, in pixels.          NOTE: if you're setting multiple coordinates, use setRect(), moveTo() or resizeTo()          instead
     *
     * @param left new left coordinate. Default value is 0
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setLeft(int left) {
        setAttribute("left", left, true);
    }

    /**
     * Number of pixels the left side of the widget is offset to the right from its default drawing context (either its
     * parent's topleft corner, or the document flow, depending on the value of the {@link
     * com.smartgwt.client.widgets.Canvas#getPosition position} property). <P> Can also be set as a percentage, specified as a
     * String ending in '%', eg, "50%".  In this case the top coordinate is considered as a percentage of the specified width
     * of the {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     *
     * @return Return the left coordinate of this object, relative to its enclosing context, in pixels. Default value is 0
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public int getLeft()  {
        return getAttributeAsInt("left");
    }

    /**
     * Number of pixels the left side of the widget is offset to the right from its default drawing context (either its
     * parent's topleft corner, or the document flow, depending on the value of the {@link
     * com.smartgwt.client.widgets.Canvas#getPosition position} property). <P> Can also be set as a percentage, specified as a
     * String ending in '%', eg, "50%".  In this case the top coordinate is considered as a percentage of the specified width
     * of the {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the left coordinate of this object, relative to its enclosing context, in pixels.          NOTE: if you're setting multiple coordinates, use setRect(), moveTo() or resizeTo()          instead
     *
     * @param left new left coordinate. Default value is 0
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setLeft(String left) {
        setAttribute("left", left, true);
    }
    

    /**
     * Strategy to use when locating children in this canvas from an autoTest locator string.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param locateChildrenBy New locateChildrenBy value. Default value is null
     */
    public void setLocateChildrenBy(LocatorStrategy locateChildrenBy) {
        setAttribute("locateChildrenBy", locateChildrenBy == null ? null : locateChildrenBy.getValue(), true);
    }

    /**
     * Strategy to use when locating children in this canvas from an autoTest locator string.
     *
     * @return Current locateChildrenBy value. Default value is null
     */
    public LocatorStrategy getLocateChildrenBy()  {
        return EnumUtil.getEnum(LocatorStrategy.values(), getAttribute("locateChildrenBy"));
    }
    

    /**
     * {@link com.smartgwt.client.types.LocatorTypeStrategy} to use when finding children within this canvas.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param locateChildrenType New locateChildrenType value. Default value is null
     */
    public void setLocateChildrenType(LocatorTypeStrategy locateChildrenType) {
        setAttribute("locateChildrenType", locateChildrenType == null ? null : locateChildrenType.getValue(), true);
    }

    /**
     * {@link com.smartgwt.client.types.LocatorTypeStrategy} to use when finding children within this canvas.
     *
     * @return Current locateChildrenType value. Default value is null
     */
    public LocatorTypeStrategy getLocateChildrenType()  {
        return EnumUtil.getEnum(LocatorTypeStrategy.values(), getAttribute("locateChildrenType"));
    }
    

    /**
     * Strategy to use when locating peers of this canvas from an autoTest locator string.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param locatePeersBy New locatePeersBy value. Default value is null
     */
    public void setLocatePeersBy(LocatorStrategy locatePeersBy) {
        setAttribute("locatePeersBy", locatePeersBy == null ? null : locatePeersBy.getValue(), true);
    }

    /**
     * Strategy to use when locating peers of this canvas from an autoTest locator string.
     *
     * @return Current locatePeersBy value. Default value is null
     */
    public LocatorStrategy getLocatePeersBy()  {
        return EnumUtil.getEnum(LocatorStrategy.values(), getAttribute("locatePeersBy"));
    }
    

    /**
     * {@link com.smartgwt.client.types.LocatorTypeStrategy} to use when finding peers of this canvas.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param locatePeersType New locatePeersType value. Default value is null
     */
    public void setLocatePeersType(LocatorTypeStrategy locatePeersType) {
        setAttribute("locatePeersType", locatePeersType == null ? null : locatePeersType.getValue(), true);
    }

    /**
     * {@link com.smartgwt.client.types.LocatorTypeStrategy} to use when finding peers of this canvas.
     *
     * @return Current locatePeersType value. Default value is null
     */
    public LocatorTypeStrategy getLocatePeersType()  {
        return EnumUtil.getEnum(LocatorTypeStrategy.values(), getAttribute("locatePeersType"));
    }
    

    /**
     * Set the CSS Margin, in pixels, for this component.  Margin provides blank space outside of the border. <P> This property
     * sets the same thickness of margin on every side.  Differing per-side margins can be set in a CSS style and applied via
     * {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <P> Note that the specified size of the widget will
     * be the size <b>including</b> the margin thickness on each side.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the CSS Margin, in pixels, for this component.  Margin provides blank space outside of the border. <P> This property sets the same thickness of margin on every side.  Differing per-side margins can be set in a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <P> Note that the specified size of the widget will be the size <b>including</b> the margin thickness on each side.
     *
     * @param margin new margin in pixels. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setMargin(Integer margin) {
        setAttribute("margin", margin, true);
    }

    /**
     * Set the CSS Margin, in pixels, for this component.  Margin provides blank space outside of the border. <P> This property
     * sets the same thickness of margin on every side.  Differing per-side margins can be set in a CSS style and applied via
     * {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <P> Note that the specified size of the widget will
     * be the size <b>including</b> the margin thickness on each side.
     *
     * @return Current margin value. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public Integer getMargin()  {
        return getAttributeAsInt("margin");
    }
    

    /**
     * This Canvas's "master" (the Canvas to which it was added as a peer), if any.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current masterElement value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     * @deprecated In favor or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas getMasterCanvas()} as of Smart GWT release 9.1
     */
    public Canvas getMasterElement() throws IllegalStateException {
        errorIfNotCreated("masterElement");
        return (Canvas)Canvas.getByJSObject(getAttributeAsJavaScriptObject("masterElement"));
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} is specified, should this canvas initially be
     * drawn at the same dimensions as the htmlElement?<br> Note: setting this property will not force the canvas to resize if
     * the element subsequently resizes (for example due to page reflow).
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param matchElement New matchElement value. Default value is null
     */
    public void setMatchElement(Boolean matchElement) {
        setAttribute("matchElement", matchElement, true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} is specified, should this canvas initially be
     * drawn at the same dimensions as the htmlElement?<br> Note: setting this property will not force the canvas to resize if
     * the element subsequently resizes (for example due to page reflow).
     *
     * @return Current matchElement value. Default value is null
     */
    public Boolean getMatchElement()  {
        return getAttributeAsBoolean("matchElement");
    }
    

    /**
     * Maximum height available to this Canvas.  See {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} for
     * details of behavior.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param maxHeight New maxHeight value. Default value is 10000
     * @see com.smartgwt.client.widgets.Canvas#setDragMaxHeight
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setMaxHeight(int maxHeight) {
        setAttribute("maxHeight", maxHeight, true);
    }

    /**
     * Maximum height available to this Canvas.  See {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} for
     * details of behavior.
     *
     * @return Current maxHeight value. Default value is 10000
     * @see com.smartgwt.client.widgets.Canvas#getDragMaxHeight
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getMaxHeight()  {
        return getAttributeAsInt("maxHeight");
    }
    

    /**
     * Maximum width available to this Canvas.  <p> The <code>maxWidth</code> and {@link
     * com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight} settings apply to: <ul> <li>For a canvas being managed as a
     * member of a {@link com.smartgwt.client.widgets.layout.Layout}, the maximum size the     layout should apply to the
     * canvas. <li>For a canvas with a width or height specified as a percent value, a maximum     numeric pixel value to limit
     * how large the canvas is sized. <li> determining size for a Canvas in a {@link
     * com.smartgwt.client.widgets.form.fields.CanvasItem} (<code>maxHeight</code> only) <li> end user {@link
     * com.smartgwt.client.widgets.Canvas#getCanDragResize drag resizing} </ul> Maximum sizes do not apply in various other
     * circumstances where sizes are being determined, such as {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getShowRecordComponents ListGrid recordComponents}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param maxWidth New maxWidth value. Default value is 10000
     * @see com.smartgwt.client.widgets.Canvas#setDragMaxWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setMaxWidth(int maxWidth) {
        setAttribute("maxWidth", maxWidth, true);
    }

    /**
     * Maximum width available to this Canvas.  <p> The <code>maxWidth</code> and {@link
     * com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight} settings apply to: <ul> <li>For a canvas being managed as a
     * member of a {@link com.smartgwt.client.widgets.layout.Layout}, the maximum size the     layout should apply to the
     * canvas. <li>For a canvas with a width or height specified as a percent value, a maximum     numeric pixel value to limit
     * how large the canvas is sized. <li> determining size for a Canvas in a {@link
     * com.smartgwt.client.widgets.form.fields.CanvasItem} (<code>maxHeight</code> only) <li> end user {@link
     * com.smartgwt.client.widgets.Canvas#getCanDragResize drag resizing} </ul> Maximum sizes do not apply in various other
     * circumstances where sizes are being determined, such as {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getShowRecordComponents ListGrid recordComponents}.
     *
     * @return Current maxWidth value. Default value is 10000
     * @see com.smartgwt.client.widgets.Canvas#getDragMaxWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getMaxWidth()  {
        return getAttributeAsInt("maxWidth");
    }
    

    /**
     * Default class used to construct menus created by this component, including context menus.
     *
     * @param menuConstructor New menuConstructor value. Default value is "Menu"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.events.ShowContextMenuEvent
     * @see com.smartgwt.client.docs.SCClassName SCClassName 
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public void setMenuConstructor(String menuConstructor)  throws IllegalStateException {
        setAttribute("menuConstructor", menuConstructor, false);
    }

    /**
     * Default class used to construct menus created by this component, including context menus.
     *
     * @return Current menuConstructor value. Default value is "Menu"
     * @see com.smartgwt.client.widgets.events.ShowContextMenuEvent
     * @see com.smartgwt.client.docs.SCClassName SCClassName 
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public String getMenuConstructor()  {
        return getAttributeAsString("menuConstructor");
    }
    

    /**
     * Minimum height available to this Canvas.  See {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth} for
     * details of behavior.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Resizes the widget vertically if required to satisfy the specified {@link com.smartgwt.client.widgets.Canvas#getMinHeight minHeight}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param minHeight new minimum height. Default value is 0
     * @see com.smartgwt.client.widgets.Canvas#setDragMinHeight
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setMinHeight(int minHeight) {
        setAttribute("minHeight", minHeight, true);
    }

    /**
     * Minimum height available to this Canvas.  See {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth} for
     * details of behavior.
     *
     * @return Current minHeight value. Default value is 0
     * @see com.smartgwt.client.widgets.Canvas#getDragMinHeight
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getMinHeight()  {
        Integer result = getAttributeAsInt("minHeight");
        return result == null ? 0 : result;
    }
    

    /**
     * If the widget has drag resize configured on one or more of it's edges, and the edgeMarginSize is large enough that the
     * remaining space is less than <code>minNonEdgeSize</code>, the edgeMarginSize will be reduced such that the non-edge part
     * of the widget is at least 1/3 of the total space (with two draggable edges) or half of it (with one draggable edge).
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param minNonEdgeSize New minNonEdgeSize value. Default value is 0
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public void setMinNonEdgeSize(int minNonEdgeSize) {
        setAttribute("minNonEdgeSize", minNonEdgeSize, true);
    }

    /**
     * If the widget has drag resize configured on one or more of it's edges, and the edgeMarginSize is large enough that the
     * remaining space is less than <code>minNonEdgeSize</code>, the edgeMarginSize will be reduced such that the non-edge part
     * of the widget is at least 1/3 of the total space (with two draggable edges) or half of it (with one draggable edge).
     *
     * @return Current minNonEdgeSize value. Default value is 0
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public int getMinNonEdgeSize()  {
        return getAttributeAsInt("minNonEdgeSize");
    }
    

    /**
     * Minimum width available to this Canvas. <P> The <code>minWidth</code> and {@link
     * com.smartgwt.client.widgets.Canvas#getMinHeight minHeight} settings apply to: <ul> <li>For a canvas being managed as a
     * member of a {@link com.smartgwt.client.widgets.layout.Layout}, the minimum size the     layout should apply to the
     * canvas. <li>For a canvas with a width or height specified as a percent value, a minimum     numeric pixel value to limit
     * how large the canvas is sized. <li> determining size for a Canvas in a {@link
     * com.smartgwt.client.widgets.form.fields.CanvasItem} (<code>minHeight</code> only) <li> end user {@link
     * com.smartgwt.client.widgets.Canvas#getCanDragResize drag resizing} <li> minimum size when using {@link
     * com.smartgwt.client.types.Overflow} "visible" outside of a Layout - minimum      size will be the greater of this
     * setting or the minimum size needed to make all      content visible </ul> <p> Minimum sizes do not apply in various
     * other circumstances where sizes are being determined, such as {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getShowRecordComponents ListGrid recordComponents}. <p> See also {@link
     * com.smartgwt.client.widgets.layout.Layout#getMinMemberLength Layout.minMemberLength} as a way of establishing minimum
     * sizes along the length axis for all members of a {@link com.smartgwt.client.widgets.layout.Layout} with a single
     * setting.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Resizes the widget horizontally if required to satisfy the specified {@link com.smartgwt.client.widgets.Canvas#getMinWidth minWidth}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param minWidth new minimum width. Default value is 0
     * @see com.smartgwt.client.widgets.Canvas#setDragMinWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setMinWidth(int minWidth) {
        setAttribute("minWidth", minWidth, true);
    }

    /**
     * Minimum width available to this Canvas. <P> The <code>minWidth</code> and {@link
     * com.smartgwt.client.widgets.Canvas#getMinHeight minHeight} settings apply to: <ul> <li>For a canvas being managed as a
     * member of a {@link com.smartgwt.client.widgets.layout.Layout}, the minimum size the     layout should apply to the
     * canvas. <li>For a canvas with a width or height specified as a percent value, a minimum     numeric pixel value to limit
     * how large the canvas is sized. <li> determining size for a Canvas in a {@link
     * com.smartgwt.client.widgets.form.fields.CanvasItem} (<code>minHeight</code> only) <li> end user {@link
     * com.smartgwt.client.widgets.Canvas#getCanDragResize drag resizing} <li> minimum size when using {@link
     * com.smartgwt.client.types.Overflow} "visible" outside of a Layout - minimum      size will be the greater of this
     * setting or the minimum size needed to make all      content visible </ul> <p> Minimum sizes do not apply in various
     * other circumstances where sizes are being determined, such as {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getShowRecordComponents ListGrid recordComponents}. <p> See also {@link
     * com.smartgwt.client.widgets.layout.Layout#getMinMemberLength Layout.minMemberLength} as a way of establishing minimum
     * sizes along the length axis for all members of a {@link com.smartgwt.client.widgets.layout.Layout} with a single
     * setting.
     *
     * @return Current minWidth value. Default value is 0
     * @see com.smartgwt.client.widgets.Canvas#getDragMinWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public int getMinWidth()  {
        Integer result = getAttributeAsInt("minWidth");
        return result == null ? 0 : result;
    }
    

    /**
     * The minimum speed in pixels per second that must be reached for momentum scrolling to kick in. This setting only applies
     * to touch-enabled devices.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param momentumScrollMinSpeed New momentumScrollMinSpeed value. Default value is 200
     */
    public void setMomentumScrollMinSpeed(double momentumScrollMinSpeed) {
        setAttribute("momentumScrollMinSpeed", momentumScrollMinSpeed, true);
    }

    /**
     * The minimum speed in pixels per second that must be reached for momentum scrolling to kick in. This setting only applies
     * to touch-enabled devices.
     *
     * @return Current momentumScrollMinSpeed value. Default value is 200
     */
    public double getMomentumScrollMinSpeed()  {
        return getAttributeAsDouble("momentumScrollMinSpeed");
    }
    

    /**
     * Amount of time (in milliseconds) between repeated 'mouseStillDown' events for this canvas. See {@link
     * com.smartgwt.client.widgets.Canvas#addMouseStillDownHandler Canvas.mouseStillDown()} for details.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param mouseStillDownDelay New mouseStillDownDelay value. Default value is 100
     */
    public void setMouseStillDownDelay(int mouseStillDownDelay) {
        setAttribute("mouseStillDownDelay", mouseStillDownDelay, true);
    }

    /**
     * Amount of time (in milliseconds) between repeated 'mouseStillDown' events for this canvas. See {@link
     * com.smartgwt.client.widgets.Canvas#addMouseStillDownHandler Canvas.mouseStillDown()} for details.
     *
     * @return Current mouseStillDownDelay value. Default value is 100
     */
    public int getMouseStillDownDelay()  {
        return getAttributeAsInt("mouseStillDownDelay");
    }
    

    /**
     * Amount of time (in milliseconds) before mouseStillDown events start to be fired repeatedly for this canvas. See {@link
     * com.smartgwt.client.widgets.Canvas#addMouseStillDownHandler Canvas.mouseStillDown()} for details.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param mouseStillDownInitialDelay New mouseStillDownInitialDelay value. Default value is 400
     */
    public void setMouseStillDownInitialDelay(int mouseStillDownInitialDelay) {
        setAttribute("mouseStillDownInitialDelay", mouseStillDownInitialDelay, true);
    }

    /**
     * Amount of time (in milliseconds) before mouseStillDown events start to be fired repeatedly for this canvas. See {@link
     * com.smartgwt.client.widgets.Canvas#addMouseStillDownHandler Canvas.mouseStillDown()} for details.
     *
     * @return Current mouseStillDownInitialDelay value. Default value is 400
     */
    public int getMouseStillDownInitialDelay()  {
        return getAttributeAsInt("mouseStillDownInitialDelay");
    }
    

    /**
     * Optional name for the canvas, which can later be used to reference it.  Need not be globally unique, but should be
     * unique within the {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent} to get defined results for {@link
     * com.smartgwt.client.widgets.layout.Layout#getMember Layout.getMember()} and {@link
     * com.smartgwt.client.widgets.layout.Layout#getMemberNumber Layout.getMemberNumber()}.
     *
     * @param name New name value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setID
     * @see com.smartgwt.client.docs.Identifier Identifier 
     */
    public void setName(String name)  throws IllegalStateException {
        setAttribute("name", name, false);
    }

    /**
     * Optional name for the canvas, which can later be used to reference it.  Need not be globally unique, but should be
     * unique within the {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent} to get defined results for {@link
     * com.smartgwt.client.widgets.layout.Layout#getMember Layout.getMember()} and {@link
     * com.smartgwt.client.widgets.layout.Layout#getMemberNumber Layout.getMemberNumber()}.
     *
     * @return Current name value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getID
     * @see com.smartgwt.client.docs.Identifier Identifier 
     */
    public String getName()  {
        return getAttributeAsString("name");
    }
    

    /**
     * If true, this canvas will receive all mouse-clicks as single {@link com.smartgwt.client.widgets.Canvas#addClickHandler
     * click} events rather than as {@link com.smartgwt.client.widgets.Canvas#addDoubleClickHandler doubleClick} events.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param noDoubleClicks New noDoubleClicks value. Default value is null
     */
    public void setNoDoubleClicks(Boolean noDoubleClicks) {
        setAttribute("noDoubleClicks", noDoubleClicks, true);
    }

    /**
     * If true, this canvas will receive all mouse-clicks as single {@link com.smartgwt.client.widgets.Canvas#addClickHandler
     * click} events rather than as {@link com.smartgwt.client.widgets.Canvas#addDoubleClickHandler doubleClick} events.
     *
     * @return Current noDoubleClicks value. Default value is null
     */
    public Boolean getNoDoubleClicks()  {
        return getAttributeAsBoolean("noDoubleClicks");
    }
    

    /**
     * Specifies the cursor image to display when the user drags a droppable canvas      over this if it is not a valid drop
     * target for the event and      {@link com.smartgwt.client.util.EventHandler#showNoDropIndicator showNoDropIndicator} is
     * true.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param noDropCursor New noDropCursor value. Default value is Canvas.NOT_ALLOWED
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public void setNoDropCursor(Cursor noDropCursor) {
        setAttribute("noDropCursor", noDropCursor == null ? null : noDropCursor.getValue(), true);
    }

    /**
     * Specifies the cursor image to display when the user drags a droppable canvas      over this if it is not a valid drop
     * target for the event and      {@link com.smartgwt.client.util.EventHandler#showNoDropIndicator showNoDropIndicator} is
     * true.
     *
     * @return Current noDropCursor value. Default value is Canvas.NOT_ALLOWED
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public Cursor getNoDropCursor()  {
        return EnumUtil.getEnum(Cursor.values(), getAttribute("noDropCursor"));
    }
    

    /**
     * Renders the widget to be partly transparent. A widget's opacity property may      be set to any number between 0
     * (transparent) to 100 (opaque).      Null means don't specify opacity directly, 100 is fully opaque.      Note that heavy
     * use of opacity may have a performance impact on some older      browsers.      <P>      In older versions of Internet
     * Explorer (Pre IE9 / HTML5), opacity is achieved      through proprietary filters. If      {@link
     * com.smartgwt.client.widgets.Canvas#neverUseFilters filters have been disabled} within this application      developers
     * must set {@link com.smartgwt.client.widgets.Canvas#getUseOpacityFilter useOpacityFilter} to true for specific components
     * on which opacity support is required.      <P>      Also note that opacity is incompatible      with {@link
     * com.smartgwt.client.widgets.Canvas#getUseBackMask backMasks}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Sets the opacity for the widget to the newOpacity value. This newOpacity value must be within the range of 0 (transparent) to 100 (opaque). Null means don't specify opacity directly. Note that heavy use of opacity may have a performance impact on some older browsers. <P> In older versions of Internet Explorer (Pre IE9 / HTML5), opacity is achieved through proprietary filters. If {@link com.smartgwt.client.widgets.Canvas#neverUseFilters filters have been disabled} within this application developers must set {@link com.smartgwt.client.widgets.Canvas#getUseOpacityFilter useOpacityFilter} to true for specific components on which opacity support is required. <P> Also note that opacity is incompatible with {@link com.smartgwt.client.widgets.Canvas#getUseBackMask backMasks}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param opacity new opacity level. Default value is null
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public void setOpacity(Integer opacity) {
        setAttribute("opacity", opacity, true);
    }

    /**
     * Renders the widget to be partly transparent. A widget's opacity property may      be set to any number between 0
     * (transparent) to 100 (opaque).      Null means don't specify opacity directly, 100 is fully opaque.      Note that heavy
     * use of opacity may have a performance impact on some older      browsers.      <P>      In older versions of Internet
     * Explorer (Pre IE9 / HTML5), opacity is achieved      through proprietary filters. If      {@link
     * com.smartgwt.client.widgets.Canvas#neverUseFilters filters have been disabled} within this application      developers
     * must set {@link com.smartgwt.client.widgets.Canvas#getUseOpacityFilter useOpacityFilter} to true for specific components
     * on which opacity support is required.      <P>      Also note that opacity is incompatible      with {@link
     * com.smartgwt.client.widgets.Canvas#getUseBackMask backMasks}.
     *
     * @return Current opacity value. Default value is null
     * @see com.smartgwt.client.docs.Cues Cues overview and related methods
     */
    public Integer getOpacity()  {
        return getAttributeAsInt("opacity");
    }
    

    /**
     * Controls what happens when the drawn size of the content of a Canvas is either          greater or smaller than the
     * specified size of the Canvas.  Similar to the CSS          property overflow, but consistent across browsers.  See
     * Overflow type for          details.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Update the {@link com.smartgwt.client.widgets.Canvas#getOverflow overflow} of a Canvas after it has been created.
     *
     * @param overflow New overflow value. Default value is Canvas.VISIBLE
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setOverflow(Overflow overflow) {
        setAttribute("overflow", overflow == null ? null : overflow.getValue(), true);
    }

    /**
     * Controls what happens when the drawn size of the content of a Canvas is either          greater or smaller than the
     * specified size of the Canvas.  Similar to the CSS          property overflow, but consistent across browsers.  See
     * Overflow type for          details.
     *
     * @return Current overflow value. Default value is Canvas.VISIBLE
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public Overflow getOverflow()  {
        return EnumUtil.getEnum(Overflow.values(), getAttribute("overflow"));
    }
    

    /**
     * Set the CSS padding of this component, in pixels.  Padding provides space between the border and the component's
     * contents. <P> This property sets the same thickness of padding on every side.  Differing per-side padding can be set in
     * a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <P> Note that CSS padding
     * does not affect the placement of {@link com.smartgwt.client.widgets.Canvas#getChildren children}.  To provide a blank
     * area around children, either use {@link com.smartgwt.client.widgets.Canvas#getMargin CSS margins} or use a {@link
     * com.smartgwt.client.widgets.layout.Layout} as the parent instead, and use properties such as {@link
     * com.smartgwt.client.widgets.layout.Layout#getLayoutMargin Layout.layoutMargin} to create blank space.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the CSS padding of this component, in pixels.  Padding provides space between the border and the component's contents. <P> This property sets the same thickness of padding on every side.  Differing per-side padding can be set in a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <P>
     *
     * @param padding new padding in pixels. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setPadding(Integer padding) {
        setAttribute("padding", padding, true);
    }

    /**
     * Set the CSS padding of this component, in pixels.  Padding provides space between the border and the component's
     * contents. <P> This property sets the same thickness of padding on every side.  Differing per-side padding can be set in
     * a CSS style and applied via {@link com.smartgwt.client.widgets.Canvas#getStyleName styleName}. <P> Note that CSS padding
     * does not affect the placement of {@link com.smartgwt.client.widgets.Canvas#getChildren children}.  To provide a blank
     * area around children, either use {@link com.smartgwt.client.widgets.Canvas#getMargin CSS margins} or use a {@link
     * com.smartgwt.client.widgets.layout.Layout} as the parent instead, and use properties such as {@link
     * com.smartgwt.client.widgets.layout.Layout#getLayoutMargin Layout.layoutMargin} to create blank space.
     *
     * @return Current padding value. Default value is null
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public Integer getPadding()  {
        return getAttributeAsInt("padding");
    }
    

    /**
     * This Canvas's immediate parent, if any. <BR> Can be initialized, but any subsequent manipulation should be via {@link
     * com.smartgwt.client.widgets.Canvas#addChild addChild()} and {@link com.smartgwt.client.widgets.Canvas#removeChild
     * removeChild()} calls on the parent. The parent Canvas should be fetched using {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()}.
     *
     * @param parentCanvas New parentCanvas value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public void setParentCanvas(Canvas parentCanvas)  throws IllegalStateException {
        setAttribute("parentCanvas", parentCanvas == null ? null : parentCanvas.getOrCreateJsObj(), false);
    }

    /**
     * This Canvas's immediate parent, if any. <BR> Can be initialized, but any subsequent manipulation should be via {@link
     * com.smartgwt.client.widgets.Canvas#addChild addChild()} and {@link com.smartgwt.client.widgets.Canvas#removeChild
     * removeChild()} calls on the parent. The parent Canvas should be fetched using {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()}.
     *
     * @return Returns the parent of this canvas, if any. Default value is null
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public Canvas getParentCanvas()  {
        return (Canvas)Canvas.getByJSObject(getAttributeAsJavaScriptObject("parentCanvas"));
    }
    

    /**
     * This Canvas's immediate parent, if any. <BR> Can be initialized, but any subsequent manipulation should be via {@link
     * com.smartgwt.client.widgets.Canvas#addChild addChild()} and {@link com.smartgwt.client.widgets.Canvas#removeChild
     * removeChild()} calls on the parent.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param parentElement New parentElement value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     * @deprecated As of Smartclient version 9.1, deprecated in favor of {@link com.smartgwt.client.widgets.Canvas#getParentCanvas
     * parentCanvas}             and {@link com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()}
     */
    public void setParentElement(Canvas parentElement)  throws IllegalStateException {
        setAttribute("parentElement", parentElement == null ? null : parentElement.getOrCreateJsObj(), false);
    }

    /**
     * This Canvas's immediate parent, if any. <BR> Can be initialized, but any subsequent manipulation should be via {@link
     * com.smartgwt.client.widgets.Canvas#addChild addChild()} and {@link com.smartgwt.client.widgets.Canvas#removeChild
     * removeChild()} calls on the parent.
     *
     * @return Current parentElement value. Default value is null
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     * @deprecated As of Smartclient version 9.1, deprecated in favor of {@link com.smartgwt.client.widgets.Canvas#getParentCanvas
     * parentCanvas}             and {@link com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()}
     */
    public Canvas getParentElement()  {
        return (Canvas)Canvas.getByJSObject(getAttributeAsJavaScriptObject("parentElement"));
    }
    

    /**
     * Array of all Canvii that are peers of this Canvas. <P> Use {@link com.smartgwt.client.widgets.Canvas#addPeer addPeer()}
     * and {@link com.smartgwt.client.widgets.Canvas#removePeer removePeer()} to add and remove peers after a Canvas has been
     * created/drawn.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param peers New peers value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public void setPeers(Canvas... peers)  throws IllegalStateException {
        setAttribute("peers", peers, false);
    }

    /**
     * Array of all Canvii that are peers of this Canvas. <P> Use {@link com.smartgwt.client.widgets.Canvas#addPeer addPeer()}
     * and {@link com.smartgwt.client.widgets.Canvas#removePeer removePeer()} to add and remove peers after a Canvas has been
     * created/drawn.
     *
     * @return Current peers value. Default value is null
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public Canvas[] getPeers()  {
        return com.smartgwt.client.util.ConvertTo.arrayOfCanvas(getAttributeAsJavaScriptObject("peers"));
    }
    

    /**
     * Governs the model to be used when sizing canvases with percentage width or height, or positioning widgets with a
     * specified {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo}. <P> Only affects widgets with a a specified
     * {@link com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, or widgets that have {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} set and are peers of some {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas other canvas}. <P> Determines whether the coordinates used for sizing
     * (for percentage sized widgets) and positioning (if <code>snapTo</code> is set) should be relative to the visible size or
     * the viewport size of the percentSource or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}
     * widget.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param percentBox New percentBox value. Default value is "visible"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setPercentBox(PercentBoxModel percentBox)  throws IllegalStateException {
        setAttribute("percentBox", percentBox == null ? null : percentBox.getValue(), false);
    }

    /**
     * Governs the model to be used when sizing canvases with percentage width or height, or positioning widgets with a
     * specified {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo}. <P> Only affects widgets with a a specified
     * {@link com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, or widgets that have {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} set and are peers of some {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas other canvas}. <P> Determines whether the coordinates used for sizing
     * (for percentage sized widgets) and positioning (if <code>snapTo</code> is set) should be relative to the visible size or
     * the viewport size of the percentSource or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}
     * widget.
     *
     * @return Current percentBox value. Default value is "visible"
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public PercentBoxModel getPercentBox()  {
        return EnumUtil.getEnum(PercentBoxModel.values(), getAttribute("percentBox"));
    }
    

    /**
     * If this canvas has its size specified as a percentage, this property allows the user to explicitly designate another
     * canvas upon which sizing will be based. <P> If unset percentage sizing is based on<br> - the {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas} if there is one and   {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is set,<br> - otherwise on the amount of space available in this
     * widget's parent canvas, if this is   a child of some other widget<br> - otherwise the page size.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter method for the {@link com.smartgwt.client.widgets.Canvas#getPercentSource percentSource} attribute.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param percentSource eterNew percent source (if omitted existing                                      percentSource will just be cleared). Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setPercentBox
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setPercentSource(Canvas percentSource) {
        setAttribute("percentSource", percentSource == null ? null : percentSource.getOrCreateJsObj(), true);
    }

    /**
     * If this canvas has its size specified as a percentage, this property allows the user to explicitly designate another
     * canvas upon which sizing will be based. <P> If unset percentage sizing is based on<br> - the {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas} if there is one and   {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is set,<br> - otherwise on the amount of space available in this
     * widget's parent canvas, if this is   a child of some other widget<br> - otherwise the page size.
     *
     * @return Current percentSource value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getPercentBox
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public Canvas getPercentSource()  {
        return (Canvas)Canvas.getByJSObject(getAttributeAsJavaScriptObject("percentSource"));
    }
    

    /**
     * Absolute or relative, corresponding to the "absolute" (with respect to parent) or
     *  "relative" (with respect to document flow) values for the CSS position attribute.
     *  <P>
     *  Setting <code>position:"relative"</code> enables Smart GWT components to be embedded
     *  directly into the native HTML flow of a page, causing the component to be rendered
     *  within an existing DOM structure.
     *  This attribute should only be set to <code>"relative"</code> on a top level component
     *  (a component with no {@link com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()}).
     *  <P>
     *  There are 2 ways to embed relatively positioned canvases in the DOM - by default the
     *  component will be written out inline when it gets {@link com.smartgwt.client.widgets.Canvas#draw drawn()n}. For example
     *  to embed a canvas in an HTML table you could use this code:
     *  <pre>
     *  &lt;table&gt;
     *    &lt;tr&gt;
     *      &lt;td&gt;
     *        &lt;script&gt;
     *          isc.Canvas.create({autoDraw:true, backgroundColor:"red", position:"relative"});
     *        &lt;/script&gt;
     *      &lt;td&gt;
     *    &lt;/tr&gt;
     *  &lt;/table&gt;
     *  </pre>
     *  Alternatively you can make use of the {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} attribute.
     *  <P>
     *  Relative positioning is intended as a short-term integration scenario while incrementally
     *  upgrading existing applications.
     *  Note that relative positioning is not used to manage layout within Smart GWT components -
     *  instead the {@link com.smartgwt.client.widgets.layout.Layout} class would typically be used.
     *  For best consistency and flexibility across browsers, all Smart GWT layout managers
     *  use absolute positioning.
     *  <P>
     * For canvases with a specified {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement}, this attribute
     * defaults to
     *  <code>"relative"</code>. In all other cases the default value will be
     *  <code>"absolute"</code>.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param position New position value. Default value is null
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     * 
     */
    public void setPosition(Positioning position) {
        setAttribute("position", position == null ? null : position.getValue(), true);
    }

    /**
     * Absolute or relative, corresponding to the "absolute" (with respect to parent) or
     *  "relative" (with respect to document flow) values for the CSS position attribute.
     *  <P>
     *  Setting <code>position:"relative"</code> enables Smart GWT components to be embedded
     *  directly into the native HTML flow of a page, causing the component to be rendered
     *  within an existing DOM structure.
     *  This attribute should only be set to <code>"relative"</code> on a top level component
     *  (a component with no {@link com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()}).
     *  <P>
     *  There are 2 ways to embed relatively positioned canvases in the DOM - by default the
     *  component will be written out inline when it gets {@link com.smartgwt.client.widgets.Canvas#draw drawn()n}. For example
     *  to embed a canvas in an HTML table you could use this code:
     *  <pre>
     *  &lt;table&gt;
     *    &lt;tr&gt;
     *      &lt;td&gt;
     *        &lt;script&gt;
     *          isc.Canvas.create({autoDraw:true, backgroundColor:"red", position:"relative"});
     *        &lt;/script&gt;
     *      &lt;td&gt;
     *    &lt;/tr&gt;
     *  &lt;/table&gt;
     *  </pre>
     *  Alternatively you can make use of the {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} attribute.
     *  <P>
     *  Relative positioning is intended as a short-term integration scenario while incrementally
     *  upgrading existing applications.
     *  Note that relative positioning is not used to manage layout within Smart GWT components -
     *  instead the {@link com.smartgwt.client.widgets.layout.Layout} class would typically be used.
     *  For best consistency and flexibility across browsers, all Smart GWT layout managers
     *  use absolute positioning.
     *  <P>
     * For canvases with a specified {@link com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement}, this attribute
     * defaults to
     *  <code>"relative"</code>. In all other cases the default value will be
     *  <code>"absolute"</code>.
     *
     * @return Current position value. Default value is null
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     * 
     */
    public Positioning getPosition()  {
        return EnumUtil.getEnum(Positioning.values(), getAttribute("position"));
    }
    

    /**
     * Should this canvas print its children absolutely positioned when generating {@link
     * com.smartgwt.client.widgets.Canvas#getPrintHTML printable HTML}. <P> By default explicitly specified absolute
     * positioning and sizing is ignored when generating print HTML. This is done intentionally: there is no way for the
     * framework to predict how explicit sizes will translate to a the printed page and if HTML for printing includes the same
     * absolute positioning and sizing as is displayed within an application it is very common to encounter undesirable
     * effects, such as seeing tables get broken over several pages horizontally when there is enough room to print them on a
     * single page of paper. <P> In some cases, however, a developer may wish to have explicit sizing and positioning respected
     * within the print-view. Setting this attribute to <code>true</code> will cause this to occur.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param printChildrenAbsolutelyPositioned New printChildrenAbsolutelyPositioned value. Default value is false
     * @see com.smartgwt.client.docs.Printing Printing overview and related methods
     */
    public void setPrintChildrenAbsolutelyPositioned(Boolean printChildrenAbsolutelyPositioned) {
        setAttribute("printChildrenAbsolutelyPositioned", printChildrenAbsolutelyPositioned, true);
    }

    /**
     * Should this canvas print its children absolutely positioned when generating {@link
     * com.smartgwt.client.widgets.Canvas#getPrintHTML printable HTML}. <P> By default explicitly specified absolute
     * positioning and sizing is ignored when generating print HTML. This is done intentionally: there is no way for the
     * framework to predict how explicit sizes will translate to a the printed page and if HTML for printing includes the same
     * absolute positioning and sizing as is displayed within an application it is very common to encounter undesirable
     * effects, such as seeing tables get broken over several pages horizontally when there is enough room to print them on a
     * single page of paper. <P> In some cases, however, a developer may wish to have explicit sizing and positioning respected
     * within the print-view. Setting this attribute to <code>true</code> will cause this to occur.
     *
     * @return Current printChildrenAbsolutelyPositioned value. Default value is false
     * @see com.smartgwt.client.docs.Printing Printing overview and related methods
     */
    public Boolean getPrintChildrenAbsolutelyPositioned()  {
        Boolean result = getAttributeAsBoolean("printChildrenAbsolutelyPositioned");
        return result == null ? false : result;
    }
    

    /**
     * Prompt displayed in hover canvas if {@link com.smartgwt.client.widgets.Canvas#getShowHover showHover} is true.
     *
     * @param prompt New prompt value. Default value is null
     * @see com.smartgwt.client.docs.HTMLString HTMLString 
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public void setPrompt(String prompt) {
        setAttribute("prompt", prompt, true);
    }

    /**
     * Prompt displayed in hover canvas if {@link com.smartgwt.client.widgets.Canvas#getShowHover showHover} is true.
     *
     * @return Current prompt value. Default value is null
     * @see com.smartgwt.client.docs.HTMLString HTMLString 
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_interaction_hovers" target="examples">Hovers / Tooltips Example</a>
     */
    public String getPrompt()  {
        return getAttributeAsString("prompt");
    }
    
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize} is true, this property specifies the
     * conditions for when proportional resizing is used.  The default is  {@link
     * com.smartgwt.client.types.ProportionalResizeMode#MODIFIER_OFF} , which means that proportional resizing is disabled.
     *
     * @param proportionalResizing New proportionalResizing value. Default value is "none"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setProportionalResizeModifiers
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setProportionalResizing(ProportionalResizeMode proportionalResizing)  throws IllegalStateException {
        setAttribute("proportionalResizing", proportionalResizing == null ? null : proportionalResizing.getValue(), false);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize} is true, this property specifies the
     * conditions for when proportional resizing is used.  The default is  {@link
     * com.smartgwt.client.types.ProportionalResizeMode#MODIFIER_OFF} , which means that proportional resizing is disabled.
     *
     * @return Current proportionalResizing value. Default value is "none"
     * @see com.smartgwt.client.widgets.Canvas#getProportionalResizeModifiers
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public ProportionalResizeMode getProportionalResizing()  {
        return EnumUtil.getEnum(ProportionalResizeMode.values(), getAttribute("proportionalResizing"));
    }
    

    /**
     * Whether this canvas should receive {@link eventHandling events} from its scrollbars, which are {@link
     * com.smartgwt.client.widgets.Canvas#getPeers peers}.  Normally, a canvas only gets bubbled events from its {@link
     * com.smartgwt.client.widgets.Canvas#getChildren children}. <P> Note that this property only has an impact if {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars showCustomScrollbars} is true.
     *
     * @param receiveScrollbarEvents New receiveScrollbarEvents value. Default value is true
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setShowCustomScrollbars
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setReceiveScrollbarEvents(boolean receiveScrollbarEvents)  throws IllegalStateException {
        setAttribute("receiveScrollbarEvents", receiveScrollbarEvents, false);
    }

    /**
     * Whether this canvas should receive {@link eventHandling events} from its scrollbars, which are {@link
     * com.smartgwt.client.widgets.Canvas#getPeers peers}.  Normally, a canvas only gets bubbled events from its {@link
     * com.smartgwt.client.widgets.Canvas#getChildren children}. <P> Note that this property only has an impact if {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars showCustomScrollbars} is true.
     *
     * @return Current receiveScrollbarEvents value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public boolean getReceiveScrollbarEvents()  {
        Boolean result = getAttributeAsBoolean("receiveScrollbarEvents");
        return result == null ? true : result;
    }
    

    /**
     * Should this element be redrawn in response to a resize? <P> Should be set to true for components whose {@link
     * com.smartgwt.client.widgets.Canvas#getInnerHTML inner HTML} will not automatically reflow to fit the component's new
     * size.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param redrawOnResize New redrawOnResize value. Default value is null
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public void setRedrawOnResize(Boolean redrawOnResize) {
        setAttribute("redrawOnResize", redrawOnResize, true);
    }

    /**
     * Should this element be redrawn in response to a resize? <P> Should be set to true for components whose {@link
     * com.smartgwt.client.widgets.Canvas#getInnerHTML inner HTML} will not automatically reflow to fit the component's new
     * size.
     *
     * @return Current redrawOnResize value. Default value is null
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public Boolean getRedrawOnResize()  {
        return getAttributeAsBoolean("redrawOnResize");
    }
    

    /**
     * When this Canvas is included as a member in a Layout, and {@link com.smartgwt.client.widgets.Canvas#getShowResizeBar
     * showResizeBar} is set to <code>true</code> so that a resizeBar is created, <code>resizeBarTarget:"next"</code> can be
     * set to indicate that the resizeBar should resize the next member of the layout rather than this one.  For resizeBars
     * that support hiding their target member when clicked on,  <code>resizeBarTarget:"next"</code> also means that the next
     * member will be the one hidden. <P> This is typically used to create a 3-way split pane, where left and right-hand
     * sections can be resized or hidden to allow a center section to expand. <P> <b>NOTE:</b> as with any Layout, to ensure
     * all available space is used, one or more members must maintain a flexible size (eg 75%, or *).  In a two pane Layout
     * with a normal resize bar, to fill all space after a user resizes, the member on the <b>right</b> should have flexible
     * size.  With resizeBarTarget:"next", the member on the <b>left</b> should have flexible size.
     *
     * @param resizeBarTarget New resizeBarTarget value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setShowResizeBar
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     */
    public void setResizeBarTarget(String resizeBarTarget)  throws IllegalStateException {
        setAttribute("resizeBarTarget", resizeBarTarget, false);
    }

    /**
     * When this Canvas is included as a member in a Layout, and {@link com.smartgwt.client.widgets.Canvas#getShowResizeBar
     * showResizeBar} is set to <code>true</code> so that a resizeBar is created, <code>resizeBarTarget:"next"</code> can be
     * set to indicate that the resizeBar should resize the next member of the layout rather than this one.  For resizeBars
     * that support hiding their target member when clicked on,  <code>resizeBarTarget:"next"</code> also means that the next
     * member will be the one hidden. <P> This is typically used to create a 3-way split pane, where left and right-hand
     * sections can be resized or hidden to allow a center section to expand. <P> <b>NOTE:</b> as with any Layout, to ensure
     * all available space is used, one or more members must maintain a flexible size (eg 75%, or *).  In a two pane Layout
     * with a normal resize bar, to fill all space after a user resizes, the member on the <b>right</b> should have flexible
     * size.  With resizeBarTarget:"next", the member on the <b>left</b> should have flexible size.
     *
     * @return Current resizeBarTarget value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getShowResizeBar
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     */
    public String getResizeBarTarget()  {
        return getAttributeAsString("resizeBarTarget");
    }
    

    /**
     * When drag resizing is enabled via {@link com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize}, restricts
     * resizes to only certain edges or corners. <P> The default of null indicates the widget can be resized from any corner or
     * edge (if <code>canDragResize</code> is true). <P> To restrict resizing to only certain corners, set
     * <code>resizeFrom</code> to an Array of {@link com.smartgwt.client.types.EdgeName}s.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param resizeFrom New resizeFrom value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public void setResizeFrom(EdgeName... resizeFrom) {
        setAttribute("resizeFrom", resizeFrom, true);
    }

    /**
     * When drag resizing is enabled via {@link com.smartgwt.client.widgets.Canvas#getCanDragResize canDragResize}, restricts
     * resizes to only certain edges or corners. <P> The default of null indicates the widget can be resized from any corner or
     * edge (if <code>canDragResize</code> is true). <P> To restrict resizing to only certain corners, set
     * <code>resizeFrom</code> to an Array of {@link com.smartgwt.client.types.EdgeName}s.
     *
     * @return Current resizeFrom value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_resize" target="examples">Drag resize Example</a>
     */
    public EdgeName[] getResizeFrom()  {
        final String[] strings = getAttributeAsStringArray("resizeFrom");
        return EnumUtil.getEnums(EdgeName.values(), strings, strings == null ? null : new EdgeName[strings.length]);
    }
    

    /**
     * {@link com.smartgwt.client.widgets.Canvas#getID ID} of the component that gathers the context for evaluation of
     * criteria-based
     * rules specified by properties such as {@link com.smartgwt.client.widgets.form.fields.FormItem#getVisibleWhen
     * FormItem.visibleWhen}.
     *  <p>
     * If not specified, this component will search through it's {@link com.smartgwt.client.widgets.Canvas#getParentCanvas
     * parentCanvas} chain until it
     * either reaches the top or reaches a parent marked {@link com.smartgwt.client.widgets.Canvas#getIsRuleScope isRuleScope}.
     *  Determination of the
     *  <code>ruleScope</code> happens when the component is first drawn.
     *  <p>
     *  The component designated as the <code>ruleScope</code> manages a nested data structure called
     *  the "rule context" which contains information from all {@link com.smartgwt.client.widgets.DataBoundComponent}s that are
     * registered with the <code>ruleScope</code>.  By specifying {@link com.smartgwt.client.data.Criterion#getFieldName
     * Criterion.fieldName} as a
     *  {@link com.smartgwt.client.docs.DataPath}, AdvancedCriteria defined in properties such as
     * {@link com.smartgwt.client.widgets.form.fields.FormItem#getVisibleWhen FormItem.visibleWhen} can access any part of the
     * rule context.
     *  <p>
     *  By default, the rule context contains data as follows:
     *  <ul>
     *  <li> any <code>DataBoundComponent</code> that has a DataSource contributes the values of the
     *    selected record or record being edited under the ID of the DataSource. For any collision
     *    an editable display (such as a form or editable grid) wins over a static display (such as
     *    a non-editable grid with a selection.)
     *  <li> any ListGrid or other component that manages a selection and has been assigned an explicit
     *    {@link com.smartgwt.client.widgets.Canvas#getID ID} will contribute the values of the selected record under
     *    <code>&lt;componentId&gt;.selectedRecord</code>, and also contributes 3 flags for checking
     *    for selection: <code>anySelected</code>, <code>multiSelected</code>,
     *    <code>numSelected</code>.
     *  <li> any DynamicForm or other component that edits values and has been assigned an explicit
     *    {@link com.smartgwt.client.widgets.Canvas#getID ID} contributes its current values under
     *    <code>&lt;componentId&gt;.values</code>, and contributes a flag <code>hasChanges</code>.
     *  <li> any DynamicForm or ListGrid that has been assigned an explicit {@link com.smartgwt.client.widgets.Canvas#getID ID}
     *    contributes a value <code>&lt;componentId&gt;.focusField</code>. When present the value
     *    indicates the component has focus along with the name of the field that has focus. Its absense
     *    indicates the component does not have focus at all.
     * <li> any ListGrid that has been assigned an explicit {@link com.smartgwt.client.widgets.Canvas#getID ID} contributes a
     * flag
     *    <code>isGrouped</code> under <code>&lt;componentId&gt;</code>.
     *  </ul>
     *  For example, given a screen where:
     *  <ul>
     *  <li> a ListGrid with ID "itemGrid" and DynamicForm with ID "itemForm" are both bound to the
     *    <code>supplyItem</code> sample DataSource
     *  <li> the ListGrid has a single selection, and the record selected in the ListGrid is being
     *    edited in the form, and has been changed
     *  </ul>
     * The default rule context available from {@link com.smartgwt.client.widgets.Canvas#getRuleContext getRuleContext()},
     * expressed as JSON, would be:
     *  <pre>
     *  {
     *   supplyItem : {
     *      itemID : "654321",
     *      itemName : "Sewing Machine",
     *      price : 5.50, // note: user change
     *      <i>..other properties..</i>
     *   },
     *   itemForm.values : {
     *      itemID : "654321",
     *      itemName : "Sewing Machine",
     *      price : 5.50, // note: user change
     *      <i>..other properties..</i>
     *   },
     *   itemForm.focusField : "itemName",
     *   itemForm.hasChanges : true,
     *   itemGrid.selectedRecord : {
     *      itemID : "654321",
     *      itemName : "Sewing Machine",
     *      price : 3.50, // note: old price
     *      <i>..other properties..</i>
     *   },
     *   itemGrid.anySelected : true,
     *   itemGrid.multiSelected : false,
     *   itemGrid.numSelected : 1,
     *   itemGrid.isGrouped : false
     *  }
     *  </pre>
     * In addition, an application can put custom data into the ruleScope via {@link
     * com.smartgwt.client.widgets.Canvas#provideRuleContext provideRuleContext()}.
     *
     * @param ruleScope New ruleScope value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setRuleScope(String ruleScope)  throws IllegalStateException {
        setAttribute("ruleScope", ruleScope, false);
    }

    /**
     * {@link com.smartgwt.client.widgets.Canvas#getID ID} of the component that gathers the context for evaluation of
     * criteria-based
     * rules specified by properties such as {@link com.smartgwt.client.widgets.form.fields.FormItem#getVisibleWhen
     * FormItem.visibleWhen}.
     *  <p>
     * If not specified, this component will search through it's {@link com.smartgwt.client.widgets.Canvas#getParentCanvas
     * parentCanvas} chain until it
     * either reaches the top or reaches a parent marked {@link com.smartgwt.client.widgets.Canvas#getIsRuleScope isRuleScope}.
     *  Determination of the
     *  <code>ruleScope</code> happens when the component is first drawn.
     *  <p>
     *  The component designated as the <code>ruleScope</code> manages a nested data structure called
     *  the "rule context" which contains information from all {@link com.smartgwt.client.widgets.DataBoundComponent}s that are
     * registered with the <code>ruleScope</code>.  By specifying {@link com.smartgwt.client.data.Criterion#getFieldName
     * Criterion.fieldName} as a
     *  {@link com.smartgwt.client.docs.DataPath}, AdvancedCriteria defined in properties such as
     * {@link com.smartgwt.client.widgets.form.fields.FormItem#getVisibleWhen FormItem.visibleWhen} can access any part of the
     * rule context.
     *  <p>
     *  By default, the rule context contains data as follows:
     *  <ul>
     *  <li> any <code>DataBoundComponent</code> that has a DataSource contributes the values of the
     *    selected record or record being edited under the ID of the DataSource. For any collision
     *    an editable display (such as a form or editable grid) wins over a static display (such as
     *    a non-editable grid with a selection.)
     *  <li> any ListGrid or other component that manages a selection and has been assigned an explicit
     *    {@link com.smartgwt.client.widgets.Canvas#getID ID} will contribute the values of the selected record under
     *    <code>&lt;componentId&gt;.selectedRecord</code>, and also contributes 3 flags for checking
     *    for selection: <code>anySelected</code>, <code>multiSelected</code>,
     *    <code>numSelected</code>.
     *  <li> any DynamicForm or other component that edits values and has been assigned an explicit
     *    {@link com.smartgwt.client.widgets.Canvas#getID ID} contributes its current values under
     *    <code>&lt;componentId&gt;.values</code>, and contributes a flag <code>hasChanges</code>.
     *  <li> any DynamicForm or ListGrid that has been assigned an explicit {@link com.smartgwt.client.widgets.Canvas#getID ID}
     *    contributes a value <code>&lt;componentId&gt;.focusField</code>. When present the value
     *    indicates the component has focus along with the name of the field that has focus. Its absense
     *    indicates the component does not have focus at all.
     * <li> any ListGrid that has been assigned an explicit {@link com.smartgwt.client.widgets.Canvas#getID ID} contributes a
     * flag
     *    <code>isGrouped</code> under <code>&lt;componentId&gt;</code>.
     *  </ul>
     *  For example, given a screen where:
     *  <ul>
     *  <li> a ListGrid with ID "itemGrid" and DynamicForm with ID "itemForm" are both bound to the
     *    <code>supplyItem</code> sample DataSource
     *  <li> the ListGrid has a single selection, and the record selected in the ListGrid is being
     *    edited in the form, and has been changed
     *  </ul>
     * The default rule context available from {@link com.smartgwt.client.widgets.Canvas#getRuleContext getRuleContext()},
     * expressed as JSON, would be:
     *  <pre>
     *  {
     *   supplyItem : {
     *      itemID : "654321",
     *      itemName : "Sewing Machine",
     *      price : 5.50, // note: user change
     *      <i>..other properties..</i>
     *   },
     *   itemForm.values : {
     *      itemID : "654321",
     *      itemName : "Sewing Machine",
     *      price : 5.50, // note: user change
     *      <i>..other properties..</i>
     *   },
     *   itemForm.focusField : "itemName",
     *   itemForm.hasChanges : true,
     *   itemGrid.selectedRecord : {
     *      itemID : "654321",
     *      itemName : "Sewing Machine",
     *      price : 3.50, // note: old price
     *      <i>..other properties..</i>
     *   },
     *   itemGrid.anySelected : true,
     *   itemGrid.multiSelected : false,
     *   itemGrid.numSelected : 1,
     *   itemGrid.isGrouped : false
     *  }
     *  </pre>
     * In addition, an application can put custom data into the ruleScope via {@link
     * com.smartgwt.client.widgets.Canvas#provideRuleContext provideRuleContext()}.
     *
     * @return Current ruleScope value. Default value is null
     */
    public String getRuleScope()  {
        return getAttributeAsString("ruleScope");
    }
    

    /**
     * The class that will be used to create custom scrollbars for this component. Set this attribute to a Scrollbar subclass
     * with e.g. a different skinImgDir, to customize scrollbar appearance for this component only. <p> When {@link
     * com.smartgwt.client.docs.Skinning spriting is enabled} and supported by the skin, the default
     * <code>scrollbarConstructor</code> is changed to a different scrollbar class which handles scrollbar spriting. Spriting
     * of the scrollbars of an individual component can therefore be disabled by creating the component with
     * <code>scrollbarConstructor</code> set to the "Scrollbar" class. "Scrollbar" is a basic scrollbar class that does not
     * employ spriting.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param scrollbarConstructor New scrollbarConstructor value. Default value is "Scrollbar"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setScrollbarConstructor(String scrollbarConstructor)  throws IllegalStateException {
        setAttribute("scrollbarConstructor", scrollbarConstructor, false);
    }
    

    /**
     * How thick should we make the scrollbars for this canvas. This only applies if {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars showCustomScrollbars} is <code>true</code>. <p>
     * <strong>NOTE:</strong> When {@link com.smartgwt.client.docs.Skinning spriting is enabled}, changing the
     * <code>scrollbarSize</code> may cause tiling of certain images and backgrounds that make up the custom scrollbar. This
     * can be fixed for a component by creating it with {@link com.smartgwt.client.widgets.Canvas#getScrollbarConstructor
     * scrollbarConstructor} set to "Scrollbar"&mdash;a basic scrollbar class that does not employ spriting.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param scrollbarSize New scrollbarSize value. Default value is 16
     * @see com.smartgwt.client.widgets.Canvas#getScrollbarSize
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setScrollbarSize(int scrollbarSize) {
        setAttribute("scrollbarSize", scrollbarSize, true);
    }

    /**
     * How thick should we make the scrollbars for this canvas. This only applies if {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars showCustomScrollbars} is <code>true</code>. <p>
     * <strong>NOTE:</strong> When {@link com.smartgwt.client.docs.Skinning spriting is enabled}, changing the
     * <code>scrollbarSize</code> may cause tiling of certain images and backgrounds that make up the custom scrollbar. This
     * can be fixed for a component by creating it with {@link com.smartgwt.client.widgets.Canvas#getScrollbarConstructor
     * scrollbarConstructor} set to "Scrollbar"&mdash;a basic scrollbar class that does not employ spriting.
     *
     * @return Returns the thickness of this widget's scrollbars.<br>  For canvases showing custom scrollbars this is determined from
     * <code>this.scrollbarSize</code>. Default value is 16
     * @see com.smartgwt.client.widgets.Canvas#getScrollbarSize
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public int getScrollbarSize()  {
        return getAttributeAsInt("scrollbarSize");
    }
    

    /**
     * Color for the css-based drop shadow shown if {@link com.smartgwt.client.widgets.Canvas#getUseCSSShadow useCSSShadow} is
     * true  and {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow} is true. <P> Has no effect if we are not
     * using css-based shadows - in that case, use {@link com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage}
     * instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowColor New shadowColor value. Default value is "#AAAAAA"
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     */
    public void setShadowColor(String shadowColor) {
        setAttribute("shadowColor", shadowColor, true);
    }

    /**
     * Color for the css-based drop shadow shown if {@link com.smartgwt.client.widgets.Canvas#getUseCSSShadow useCSSShadow} is
     * true  and {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow} is true. <P> Has no effect if we are not
     * using css-based shadows - in that case, use {@link com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage}
     * instead.
     *
     * @return Current shadowColor value. Default value is "#AAAAAA"
     * @see com.smartgwt.client.docs.CSSColor CSSColor 
     */
    public String getShadowColor()  {
        return getAttributeAsString("shadowColor");
    }
    

    /**
     * Depth of the shadow, or the virtual height above the page of the widget throwing the shadow. <P> This is a single
     * parameter that can be used to control both <code>shadowSoftness</code> and <code>shadowOffset</code>.
     *
     * @param shadowDepth New shadowDepth value. Default value is 4
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setShadowDepth(int shadowDepth)  throws IllegalStateException {
        setAttribute("shadowDepth", shadowDepth, false);
    }

    /**
     * Depth of the shadow, or the virtual height above the page of the widget throwing the shadow. <P> This is a single
     * parameter that can be used to control both <code>shadowSoftness</code> and <code>shadowOffset</code>.
     *
     * @return Current shadowDepth value. Default value is 4
     */
    public int getShadowDepth()  {
        return getAttributeAsInt("shadowDepth");
    }
    

    /**
     * Horizontal offset for the {@link com.smartgwt.client.widgets.Canvas#getShowShadow shadow}. Takes precedence over  {@link
     * com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset} if set. Has no effect if {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css-shadows} are not being used for this canvas.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowHOffset New shadowHOffset value. Default value is null
     */
    public void setShadowHOffset(Integer shadowHOffset) {
        setAttribute("shadowHOffset", shadowHOffset, true);
    }

    /**
     * Horizontal offset for the {@link com.smartgwt.client.widgets.Canvas#getShowShadow shadow}. Takes precedence over  {@link
     * com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset} if set. Has no effect if {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css-shadows} are not being used for this canvas.
     *
     * @return Current shadowHOffset value. Default value is null
     */
    public Integer getShadowHOffset()  {
        return getAttributeAsInt("shadowHOffset");
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getUseCSSShadow useCSSShadow} is false, (or for browsers that do not
     * support css3), this property supplies the base name of the series of images for the sides,  corners, and center of the
     * shadow. <P> The actual image names fetched for the dropShadow combine the segment name and the <code>shadowDepth</code>
     * setting.  For example, given "ds.png" as the base name, a depth of 4, and the top-left segment of the shadow, we'd use
     * "ds4_TL.png". <P> The names for segments are the same as those given for controlling resizable edges; see {@link
     * com.smartgwt.client.widgets.Canvas#getResizeFrom resizeFrom}.  The center segment has the name "center".  The center
     * segment is the only segment that doesn't include the depth in the URL, so the final image name for the center given a
     * baseName of "ds.png" would be just "ds_center.png".
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowImage New shadowImage value. Default value is "[SKIN]ds.png"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     */
    public void setShadowImage(String shadowImage)  throws IllegalStateException {
        setAttribute("shadowImage", shadowImage, false);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getUseCSSShadow useCSSShadow} is false, (or for browsers that do not
     * support css3), this property supplies the base name of the series of images for the sides,  corners, and center of the
     * shadow. <P> The actual image names fetched for the dropShadow combine the segment name and the <code>shadowDepth</code>
     * setting.  For example, given "ds.png" as the base name, a depth of 4, and the top-left segment of the shadow, we'd use
     * "ds4_TL.png". <P> The names for segments are the same as those given for controlling resizable edges; see {@link
     * com.smartgwt.client.widgets.Canvas#getResizeFrom resizeFrom}.  The center segment has the name "center".  The center
     * segment is the only segment that doesn't include the depth in the URL, so the final image name for the center given a
     * baseName of "ds.png" would be just "ds_center.png".
     *
     * @return Current shadowImage value. Default value is "[SKIN]ds.png"
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     */
    public String getShadowImage()  {
        return getAttributeAsString("shadowImage");
    }
    

    /**
     * Offset of the shadow.  Defaults to half of <code>shadowDepth</code> if unset. <P> Because of the blurred edges, a shadow
     * is larger than the originating component by 2xsoftness.  An <code>shadowOffset</code> of 0 means that the shadow will
     * extend around the originating component equally in all directions. <P> If {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css shadows} are being used, separate vertical and horizontal offsets
     * may be specified via {@link com.smartgwt.client.widgets.Canvas#getShadowHOffset shadowHOffset} and {@link
     * com.smartgwt.client.widgets.Canvas#getShadowVOffset shadowVOffset}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowOffset New shadowOffset value. Default value is null
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_shadows" target="examples">Shadows Example</a>
     */
    public void setShadowOffset(Integer shadowOffset) {
        setAttribute("shadowOffset", shadowOffset, true);
    }

    /**
     * Offset of the shadow.  Defaults to half of <code>shadowDepth</code> if unset. <P> Because of the blurred edges, a shadow
     * is larger than the originating component by 2xsoftness.  An <code>shadowOffset</code> of 0 means that the shadow will
     * extend around the originating component equally in all directions. <P> If {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css shadows} are being used, separate vertical and horizontal offsets
     * may be specified via {@link com.smartgwt.client.widgets.Canvas#getShadowHOffset shadowHOffset} and {@link
     * com.smartgwt.client.widgets.Canvas#getShadowVOffset shadowVOffset}.
     *
     * @return Current shadowOffset value. Default value is null
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_shadows" target="examples">Shadows Example</a>
     */
    public Integer getShadowOffset()  {
        return getAttributeAsInt("shadowOffset");
    }
    

    /**
     * Softness, or degree of blurring, of the shadow. <P> A shadow with <code>softness:x</code> is 2x pixels larger in each
     * direction than the element throwing the shadow, and the media for each edge should be x pixels wide/tall. <P> Defaults
     * to <code>shadowDepth</code> if unset.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowSoftness New shadowSoftness value. Default value is null
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_shadows" target="examples">Shadows Example</a>
     */
    public void setShadowSoftness(Integer shadowSoftness) {
        setAttribute("shadowSoftness", shadowSoftness, true);
    }

    /**
     * Softness, or degree of blurring, of the shadow. <P> A shadow with <code>softness:x</code> is 2x pixels larger in each
     * direction than the element throwing the shadow, and the media for each edge should be x pixels wide/tall. <P> Defaults
     * to <code>shadowDepth</code> if unset.
     *
     * @return Current shadowSoftness value. Default value is null
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_shadows" target="examples">Shadows Example</a>
     */
    public Integer getShadowSoftness()  {
        return getAttributeAsInt("shadowSoftness");
    }
    

    /**
     * Explicit spread for the css-based drop shadow shown if {@link com.smartgwt.client.widgets.Canvas#getUseCSSShadow
     * useCSSShadow} is true  and {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow} is true. This property
     * governs how much larger than the widget the shadow will appear. A negative value (coupled with an explicit offset) will
     * result in a smaller shadow. <P> Has no effect if we are not using css-based shadows - in that case, use {@link
     * com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage} instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowSpread New shadowSpread value. Default value is null
     */
    public void setShadowSpread(Integer shadowSpread) {
        setAttribute("shadowSpread", shadowSpread, true);
    }

    /**
     * Explicit spread for the css-based drop shadow shown if {@link com.smartgwt.client.widgets.Canvas#getUseCSSShadow
     * useCSSShadow} is true  and {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow} is true. This property
     * governs how much larger than the widget the shadow will appear. A negative value (coupled with an explicit offset) will
     * result in a smaller shadow. <P> Has no effect if we are not using css-based shadows - in that case, use {@link
     * com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage} instead.
     *
     * @return Current shadowSpread value. Default value is null
     */
    public Integer getShadowSpread()  {
        return getAttributeAsInt("shadowSpread");
    }
    

    /**
     * Vertical offset for the {@link com.smartgwt.client.widgets.Canvas#getShowShadow shadow}. Takes precedence over  {@link
     * com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset} if set. Has no effect if {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css-shadows} are not being used for this canvas.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shadowVOffset New shadowVOffset value. Default value is null
     */
    public void setShadowVOffset(Integer shadowVOffset) {
        setAttribute("shadowVOffset", shadowVOffset, true);
    }

    /**
     * Vertical offset for the {@link com.smartgwt.client.widgets.Canvas#getShowShadow shadow}. Takes precedence over  {@link
     * com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset} if set. Has no effect if {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css-shadows} are not being used for this canvas.
     *
     * @return Current shadowVOffset value. Default value is null
     */
    public Integer getShadowVOffset()  {
        return getAttributeAsInt("shadowVOffset");
    }
    

    /**
     * Whether this canvas should be included in a printable view. <P> Default is to: <ul> <li> omit all peers (edges generated
     * by showEdges:true, etc) <li> omit anything considered a "control", such as a button or menu (see {@link
     * com.smartgwt.client.util.PrintProperties#getOmitControls PrintProperties.omitControls}) <li> include everything else not
     * marked shouldPrint:false </ul>
     *
     * @param shouldPrint New shouldPrint value. Default value is null
     * @see com.smartgwt.client.docs.Printing Printing overview and related methods
     */
    public void setShouldPrint(Boolean shouldPrint) {
        setAttribute("shouldPrint", shouldPrint, true);
    }

    /**
     * Whether this canvas should be included in a printable view. <P> Default is to: <ul> <li> omit all peers (edges generated
     * by showEdges:true, etc) <li> omit anything considered a "control", such as a button or menu (see {@link
     * com.smartgwt.client.util.PrintProperties#getOmitControls PrintProperties.omitControls}) <li> include everything else not
     * marked shouldPrint:false </ul>
     *
     * @return Current shouldPrint value. Default value is null
     * @see com.smartgwt.client.docs.Printing Printing overview and related methods
     */
    public Boolean getShouldPrint()  {
        return getAttributeAsBoolean("shouldPrint");
    }
    

    /**
     * Whether to use the browser's native scrollbars or Smart GWT-based scrollbars.
     *  <P>
     *  Smart GWT-based scrollbars are skinnable, giving you complete control over look and
     *  feel.  Smart GWT-based scrollbars also enable some interactions not possible with
     * native scrollbars, such as {@link com.smartgwt.client.widgets.grid.ListGrid#getFixedRecordHeights variable height
     * records}
     *  in grids in combination with {@link com.smartgwt.client.widgets.grid.ListGrid#getDataPageSize data paging}.
     *  <P>
     *  Native browser scrollbars are slightly faster simply because there are less Smart GWT
     *  components that need to be created, drawn and updated.  Each visible Smart GWT-based
     *  scrollbar on the screen has roughly the impact of two StretchImgButtons.
     *  <P>
     *  Smart GWT is always aware of the size of the scrollbar, regardless of whether native
     *  or custom scrollbars are used, and regardless of what operating system and/or operating
     *  system "theme" or "skin" is in use.  This means Smart GWT will correctly report the
     *  {@link com.smartgwt.client.widgets.Canvas#getViewportHeight viewport size}, that is, the interior area of the
     *  widget excluding space taken by scrollbars, which is key for exactly filling a component
     *  with content without creating unnecessary scrolling.
     *  <P>
     *  The <code>showCustomScrollbars</code> setting is typically overridden in load_skin.js in
     *  order to change the default for all Smart GWT components at once.
     *  This may be achieved via the static {@link com.smartgwt.client.widgets.Canvas#setDefaultShowCustomScrollbars}
     *   method or 
     *  calling setProperty() , like so:
     *  
     *  
     *  <pre>
     *       Canvas canvas = new Canvas();
     *       canvas.setProperty("showCustomScrollbars", false);
     *  </pre>
     *  
     *  <p>
     *  On {@link com.smartgwt.client.util.Browser#isTouch touch devices}, custom scrollbars are disabled in favor of enabling
     *  native touch scrolling if available. However, custom scrollbars <em>and</em> native touch
     * scrolling can be enabled for the component by setting {@link com.smartgwt.client.widgets.Canvas#getAlwaysShowScrollbars
     * alwaysShowScrollbars}
     *  to <code>true</code>.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param showCustomScrollbars New showCustomScrollbars value. Default value is true
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Canvas#setReceiveScrollbarEvents
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setShowCustomScrollbars(boolean showCustomScrollbars)  throws IllegalStateException {
        setAttribute("showCustomScrollbars", showCustomScrollbars, false);
    }

    /**
     * Whether to use the browser's native scrollbars or Smart GWT-based scrollbars.
     *  <P>
     *  Smart GWT-based scrollbars are skinnable, giving you complete control over look and
     *  feel.  Smart GWT-based scrollbars also enable some interactions not possible with
     * native scrollbars, such as {@link com.smartgwt.client.widgets.grid.ListGrid#getFixedRecordHeights variable height
     * records}
     *  in grids in combination with {@link com.smartgwt.client.widgets.grid.ListGrid#getDataPageSize data paging}.
     *  <P>
     *  Native browser scrollbars are slightly faster simply because there are less Smart GWT
     *  components that need to be created, drawn and updated.  Each visible Smart GWT-based
     *  scrollbar on the screen has roughly the impact of two StretchImgButtons.
     *  <P>
     *  Smart GWT is always aware of the size of the scrollbar, regardless of whether native
     *  or custom scrollbars are used, and regardless of what operating system and/or operating
     *  system "theme" or "skin" is in use.  This means Smart GWT will correctly report the
     *  {@link com.smartgwt.client.widgets.Canvas#getViewportHeight viewport size}, that is, the interior area of the
     *  widget excluding space taken by scrollbars, which is key for exactly filling a component
     *  with content without creating unnecessary scrolling.
     *  <P>
     *  The <code>showCustomScrollbars</code> setting is typically overridden in load_skin.js in
     *  order to change the default for all Smart GWT components at once.
     *  This may be achieved via the static {@link com.smartgwt.client.widgets.Canvas#setDefaultShowCustomScrollbars}
     *   method or 
     *  calling setProperty() , like so:
     *  
     *  
     *  <pre>
     *       Canvas canvas = new Canvas();
     *       canvas.setProperty("showCustomScrollbars", false);
     *  </pre>
     *  
     *  <p>
     *  On {@link com.smartgwt.client.util.Browser#isTouch touch devices}, custom scrollbars are disabled in favor of enabling
     *  native touch scrolling if available. However, custom scrollbars <em>and</em> native touch
     * scrolling can be enabled for the component by setting {@link com.smartgwt.client.widgets.Canvas#getAlwaysShowScrollbars
     * alwaysShowScrollbars}
     *  to <code>true</code>.
     *
     * @return Current showCustomScrollbars value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#getReceiveScrollbarEvents
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public boolean getShowCustomScrollbars()  {
        Boolean result = getAttributeAsBoolean("showCustomScrollbars");
        return result == null ? true : result;
    }
    

    /**
     * When this widget is dragged, if its dragAppearance is <code>"target"</code>, should we show a shadow behind the canvas
     * during the drag.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param showDragShadow New showDragShadow value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_effects" target="examples">Drag effects Example</a>
     */
    public void setShowDragShadow(Boolean showDragShadow) {
        setAttribute("showDragShadow", showDragShadow, true);
    }

    /**
     * When this widget is dragged, if its dragAppearance is <code>"target"</code>, should we show a shadow behind the canvas
     * during the drag.
     *
     * @return Current showDragShadow value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_effects" target="examples">Drag effects Example</a>
     */
    public Boolean getShowDragShadow()  {
        return getAttributeAsBoolean("showDragShadow");
    }
    

    /**
     * Whether an {@link com.smartgwt.client.widgets.EdgedCanvas} should be used to show image-based edges around this
     * component.
     *
     * @param showEdges New showEdges value. Default value is false
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public void setShowEdges(Boolean showEdges)  throws IllegalStateException {
        setAttribute("showEdges", showEdges, false);
    }

    /**
     * Whether an {@link com.smartgwt.client.widgets.EdgedCanvas} should be used to show image-based edges around this
     * component.
     *
     * @return Current showEdges value. Default value is false
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_edges" target="examples">Edges Example</a>
     */
    public Boolean getShowEdges()  {
        Boolean result = getAttributeAsBoolean("showEdges");
        return result == null ? false : result;
    }
    

    /**
     * If <code>this.canHover</code> is true, should we show the global hover canvas by default when the user hovers over this
     * canvas?
     *
     * @param showHover New showHover value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#getHoverHTML
     */
    public void setShowHover(Boolean showHover) {
        setAttribute("showHover", showHover, true);
    }

    /**
     * If <code>this.canHover</code> is true, should we show the global hover canvas by default when the user hovers over this
     * canvas?
     *
     * @return Current showHover value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#getHoverHTML
     */
    public Boolean getShowHover()  {
        Boolean result = getAttributeAsBoolean("showHover");
        return result == null ? true : result;
    }
    

    /**
     * When set to true, shows a widget hovering at the mouse point instead of the builtin hover label.  Override {@link
     * com.smartgwt.client.widgets.Canvas#getHoverComponent getHoverComponent} to provide the Canvas to show as the
     * hoverComponent.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param showHoverComponents New showHoverComponents value. Default value is false
     */
    public void setShowHoverComponents(Boolean showHoverComponents) {
        setAttribute("showHoverComponents", showHoverComponents, true);
    }

    /**
     * When set to true, shows a widget hovering at the mouse point instead of the builtin hover label.  Override {@link
     * com.smartgwt.client.widgets.Canvas#getHoverComponent getHoverComponent} to provide the Canvas to show as the
     * hoverComponent.
     *
     * @return Current showHoverComponents value. Default value is false
     */
    public Boolean getShowHoverComponents()  {
        Boolean result = getAttributeAsBoolean("showHoverComponents");
        return result == null ? false : result;
    }
    

    /**
     * When this Canvas is included as a member in a {@link com.smartgwt.client.widgets.layout.Layout}, whether a resizeBar
     * should be shown after this member in the layout, to allow it to be resized. <p> Whether a resizeBar is actually shown
     * also depends on the  {@link com.smartgwt.client.widgets.layout.Layout#getDefaultResizeBars defaultResizeBars} attribute
     * of the layout, and whether this Canvas is the last layout member. <p> By default the resize bar acts on the Canvas that
     * it is declared on.  If you want the resize bar to instead act on the next member of the Layout (e.g. to collapse down or
     * to the right), set {@link com.smartgwt.client.widgets.Canvas#getResizeBarTarget resizeBarTarget} as well.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * When this Canvas is included as a member in a {@link com.smartgwt.client.widgets.layout.Layout}, dynamically updates whether a  resizeBar should be shown after this member in the layout, to allow it to be resized. <p> Whether a resizeBar is actually shown also depends on the  {@link com.smartgwt.client.widgets.layout.Layout#getDefaultResizeBars defaultResizeBars} attribute of the layout, and whether this Canvas is the last layout member.
     *
     * @param showResizeBar setting for this.showResizeBar. Default value is false
     * @see com.smartgwt.client.widgets.Canvas#setResizeBarTarget
     * @see com.smartgwt.client.widgets.layout.Layout#setDefaultResizeBars
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#layout_nesting" target="examples">Nesting Example</a>
     */
    public void setShowResizeBar(Boolean showResizeBar) {
        setAttribute("showResizeBar", showResizeBar, true);
    }

    /**
     * When this Canvas is included as a member in a {@link com.smartgwt.client.widgets.layout.Layout}, whether a resizeBar
     * should be shown after this member in the layout, to allow it to be resized. <p> Whether a resizeBar is actually shown
     * also depends on the  {@link com.smartgwt.client.widgets.layout.Layout#getDefaultResizeBars defaultResizeBars} attribute
     * of the layout, and whether this Canvas is the last layout member. <p> By default the resize bar acts on the Canvas that
     * it is declared on.  If you want the resize bar to instead act on the next member of the Layout (e.g. to collapse down or
     * to the right), set {@link com.smartgwt.client.widgets.Canvas#getResizeBarTarget resizeBarTarget} as well.
     *
     * @return Current showResizeBar value. Default value is false
     * @see com.smartgwt.client.widgets.Canvas#getResizeBarTarget
     * @see com.smartgwt.client.widgets.layout.Layout#getDefaultResizeBars
     * @see com.smartgwt.client.docs.LayoutMember LayoutMember overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#layout_nesting" target="examples">Nesting Example</a>
     */
    public Boolean getShowResizeBar()  {
        Boolean result = getAttributeAsBoolean("showResizeBar");
        return result == null ? false : result;
    }
    

    /**
     * Whether to show a drop shadow for this Canvas.  <P> Shadows may be rendered using {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css} or via images.  The appearance of shadows can be customized via
     * {@link com.smartgwt.client.widgets.Canvas#getShadowColor shadowColor} (for css-based shadows) or {@link
     * com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage}  (for image based shadows), {@link
     * com.smartgwt.client.widgets.Canvas#getShadowDepth shadowDepth}, {@link
     * com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset} and {@link
     * com.smartgwt.client.widgets.Canvas#getShadowSoftness shadowSoftness}. <P> When {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow useCSSShadow} is false, developers should be aware that the drop
     * shadow is rendered as a {@link com.smartgwt.client.widgets.Canvas#getPeers peer} and is drawn outside the specified 
     * width and height of the widget meaning a widget with shadows  takes up a little more space than it otherwise would. A
     * full screen canvas with showShadow set  to true as this would be likely to cause browser scrollbars to appear -
     * developers can handle this by either setting this property to false on full-screen widgets, or by setting overflow to
     * "hidden" on the &lt;body&gt; element if browser-level scrolling is never intended to occur.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Method to update {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow}.
     *
     * @param showShadow true if the shadow should be visible false if not. Default value is false
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_shadows" target="examples">Shadows Example</a>
     */
    public void setShowShadow(Boolean showShadow) {
        setAttribute("showShadow", showShadow, true);
    }

    /**
     * Whether to show a drop shadow for this Canvas.  <P> Shadows may be rendered using {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow css} or via images.  The appearance of shadows can be customized via
     * {@link com.smartgwt.client.widgets.Canvas#getShadowColor shadowColor} (for css-based shadows) or {@link
     * com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage}  (for image based shadows), {@link
     * com.smartgwt.client.widgets.Canvas#getShadowDepth shadowDepth}, {@link
     * com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset} and {@link
     * com.smartgwt.client.widgets.Canvas#getShadowSoftness shadowSoftness}. <P> When {@link
     * com.smartgwt.client.widgets.Canvas#getUseCSSShadow useCSSShadow} is false, developers should be aware that the drop
     * shadow is rendered as a {@link com.smartgwt.client.widgets.Canvas#getPeers peer} and is drawn outside the specified 
     * width and height of the widget meaning a widget with shadows  takes up a little more space than it otherwise would. A
     * full screen canvas with showShadow set  to true as this would be likely to cause browser scrollbars to appear -
     * developers can handle this by either setting this property to false on full-screen widgets, or by setting overflow to
     * "hidden" on the &lt;body&gt; element if browser-level scrolling is never intended to occur.
     *
     * @return Current showShadow value. Default value is false
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_shadows" target="examples">Shadows Example</a>
     */
    public Boolean getShowShadow()  {
        Boolean result = getAttributeAsBoolean("showShadow");
        return result == null ? false : result;
    }
    

    /**
     * Whether to show a snap grid for this Canvas. Note that the grid is only shown when either {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid childrenSnapToGrid} or {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid childrenSnapResizeToGrid} is <code>true</code>. <p> Grid
     * is based on {@link com.smartgwt.client.widgets.Canvas#getSnapHGap snapHGap} and {@link
     * com.smartgwt.client.widgets.Canvas#getSnapVGap snapVGap} properties.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the showSnapGrid property.
     *
     * @param showSnapGrid New showSnapGrid value. Default value is null
     */
    public void setShowSnapGrid(Boolean showSnapGrid) {
        setAttribute("showSnapGrid", showSnapGrid, true);
    }

    /**
     * Whether to show a snap grid for this Canvas. Note that the grid is only shown when either {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid childrenSnapToGrid} or {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid childrenSnapResizeToGrid} is <code>true</code>. <p> Grid
     * is based on {@link com.smartgwt.client.widgets.Canvas#getSnapHGap snapHGap} and {@link
     * com.smartgwt.client.widgets.Canvas#getSnapVGap snapVGap} properties.
     *
     * @return Current showSnapGrid value. Default value is null
     */
    public Boolean getShowSnapGrid()  {
        return getAttributeAsBoolean("showSnapGrid");
    }
    

    /**
     * This is an advanced setting. If set to <code>true</code>, when a widget is {@link
     * com.smartgwt.client.widgets.Canvas#hide hidden}, the widget's handle will be resized such that it takes up no space, in
     * addition to having its css <code>visibility</code> property set to <code>"hidden"</code>. <P> In addition to preventing
     * the size of this widget from impacting the {@link com.smartgwt.client.widgets.Canvas#getScrollWidth scroll size} of any
     * parent widget while hidden, this setting works around a native bug observed in Internet Explorer 10, whereby an
     * <code>&lt;IFRAME&gt;</code> element with visibility set to hidden can cause rendering problems, if the HTML loaded by
     * the <code>&lt;IFRAME&gt;</code> contains a <code>&lt;frameset&gt;</code>. In this case the browser may refuse to draw
     * other elements at the same coordinates with a lower z-index than the hidden frame. Setting this property to
     * <code>true</code> works around this problem for cases where an <code>&lt;IFRAME&gt;</code> containing a
     * <code>&lt;frameset</code> will be rendered out, for example in an {@link com.smartgwt.client.widgets.HTMLFlow} with
     * <code>contentsType</code> set to <code>"page"</code>.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param shrinkElementOnHide New shrinkElementOnHide value. Default value is false
     * @see com.smartgwt.client.docs.Visibility Visibility overview and related methods
     */
    public void setShrinkElementOnHide(boolean shrinkElementOnHide) {
        setAttribute("shrinkElementOnHide", shrinkElementOnHide, true);
    }

    /**
     * This is an advanced setting. If set to <code>true</code>, when a widget is {@link
     * com.smartgwt.client.widgets.Canvas#hide hidden}, the widget's handle will be resized such that it takes up no space, in
     * addition to having its css <code>visibility</code> property set to <code>"hidden"</code>. <P> In addition to preventing
     * the size of this widget from impacting the {@link com.smartgwt.client.widgets.Canvas#getScrollWidth scroll size} of any
     * parent widget while hidden, this setting works around a native bug observed in Internet Explorer 10, whereby an
     * <code>&lt;IFRAME&gt;</code> element with visibility set to hidden can cause rendering problems, if the HTML loaded by
     * the <code>&lt;IFRAME&gt;</code> contains a <code>&lt;frameset&gt;</code>. In this case the browser may refuse to draw
     * other elements at the same coordinates with a lower z-index than the hidden frame. Setting this property to
     * <code>true</code> works around this problem for cases where an <code>&lt;IFRAME&gt;</code> containing a
     * <code>&lt;frameset</code> will be rendered out, for example in an {@link com.smartgwt.client.widgets.HTMLFlow} with
     * <code>contentsType</code> set to <code>"page"</code>.
     *
     * @return Current shrinkElementOnHide value. Default value is false
     * @see com.smartgwt.client.docs.Visibility Visibility overview and related methods
     */
    public boolean getShrinkElementOnHide()  {
        Boolean result = getAttributeAsBoolean("shrinkElementOnHide");
        return result == null ? false : result;
    }
    

    /**
     * Is it possible that a call to {@link com.smartgwt.client.widgets.Canvas#redraw redraw()} on this widget will change its 
     * size? <P> Used by framework layout code when determining whether a component which has been {@link
     * com.smartgwt.client.widgets.Canvas#markForRedraw marked as dirty} needs an immediate redraw to determine its drawn size.
     * <P> If unset, default behavior assumes any component with overflow set to "visible"  may change size on redraw, and any
     * component with overflow set to "hidden", "scroll", or "auto" will not. This property overrides that behavior, and may be
     * used to indicate that some component with non visible overflow can change size on redraw. An example use case would be a
     * custom component with an override to explicitly  resize the component as part of the redraw() flow.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param sizeMayChangeOnRedraw New sizeMayChangeOnRedraw value. Default value is null
     */
    public void setSizeMayChangeOnRedraw(Boolean sizeMayChangeOnRedraw) {
        setAttribute("sizeMayChangeOnRedraw", sizeMayChangeOnRedraw, true);
    }

    /**
     * Is it possible that a call to {@link com.smartgwt.client.widgets.Canvas#redraw redraw()} on this widget will change its 
     * size? <P> Used by framework layout code when determining whether a component which has been {@link
     * com.smartgwt.client.widgets.Canvas#markForRedraw marked as dirty} needs an immediate redraw to determine its drawn size.
     * <P> If unset, default behavior assumes any component with overflow set to "visible"  may change size on redraw, and any
     * component with overflow set to "hidden", "scroll", or "auto" will not. This property overrides that behavior, and may be
     * used to indicate that some component with non visible overflow can change size on redraw. An example use case would be a
     * custom component with an override to explicitly  resize the component as part of the redraw() flow.
     *
     * @return Current sizeMayChangeOnRedraw value. Default value is null
     */
    public Boolean getSizeMayChangeOnRedraw()  {
        return getAttributeAsBoolean("sizeMayChangeOnRedraw");
    }
    

    /**
     * Default directory for skin images (those defined by the class), relative to the Page-wide {@link
     * com.smartgwt.client.util.Page#getSkinDir skinDir}.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param skinImgDir New skinImgDir value. Default value is "images/"
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public void setSkinImgDir(String skinImgDir) {
        setAttribute("skinImgDir", skinImgDir, true);
    }

    /**
     * Default directory for skin images (those defined by the class), relative to the Page-wide {@link
     * com.smartgwt.client.util.Page#getSkinDir skinDir}.
     *
     * @return Current skinImgDir value. Default value is "images/"
     * @see com.smartgwt.client.docs.SCImgURL SCImgURL 
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public String getSkinImgDir()  {
        return getAttributeAsString("skinImgDir");
    }
    

    /**
     * When {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled, list of candidates to
     * check for alignment. <p> If a list of <code>snapAlignCandidates</code> is never provided, the default is to use all
     * children that are not explicitly excluded via {@link com.smartgwt.client.widgets.Canvas#getIsSnapAlignCandidate
     * isSnapAlignCandidate}, including automatically adding newly added children as candidates, and ignoring children that
     * have been removed.  Use {@link com.smartgwt.client.widgets.Canvas#addSnapAlignCandidate addSnapAlignCandidate()} and
     * {@link com.smartgwt.client.widgets.Canvas#removeSnapAlignCandidate removeSnapAlignCandidate()} to add and remove special
     * candidates while retaining all children as default candidates. <p> Possible candidates which are not drawn or are hidden
     * are automatically ignored.
     *
     * @param snapAlignCandidates New snapAlignCandidates value. Default value is null
     */
    public void setSnapAlignCandidates(Canvas... snapAlignCandidates) {
        setAttribute("snapAlignCandidates", snapAlignCandidates, true);
    }

    /**
     * When {@link com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled, list of candidates to
     * check for alignment. <p> If a list of <code>snapAlignCandidates</code> is never provided, the default is to use all
     * children that are not explicitly excluded via {@link com.smartgwt.client.widgets.Canvas#getIsSnapAlignCandidate
     * isSnapAlignCandidate}, including automatically adding newly added children as candidates, and ignoring children that
     * have been removed.  Use {@link com.smartgwt.client.widgets.Canvas#addSnapAlignCandidate addSnapAlignCandidate()} and
     * {@link com.smartgwt.client.widgets.Canvas#removeSnapAlignCandidate removeSnapAlignCandidate()} to add and remove special
     * candidates while retaining all children as default candidates. <p> Possible candidates which are not drawn or are hidden
     * are automatically ignored.
     *
     * @return Current snapAlignCandidates value. Default value is null
     */
    public Canvas[] getSnapAlignCandidates()  {
        return com.smartgwt.client.util.ConvertTo.arrayOfCanvas(getAttributeAsJavaScriptObject("snapAlignCandidates"));
    }
    

    /**
     * CSS border declaration used for the line shown to indicate snapping to a center line when {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is set.
     *
     * @param snapAlignCenterLineStyle New snapAlignCenterLineStyle value. Default value is "1px solid blue"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setSnapAlignCenterLineStyle(String snapAlignCenterLineStyle)  throws IllegalStateException {
        setAttribute("snapAlignCenterLineStyle", snapAlignCenterLineStyle, false);
    }

    /**
     * CSS border declaration used for the line shown to indicate snapping to a center line when {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is set.
     *
     * @return Current snapAlignCenterLineStyle value. Default value is "1px solid blue"
     */
    public String getSnapAlignCenterLineStyle()  {
        return getAttributeAsString("snapAlignCenterLineStyle");
    }
    

    /**
     * CSS border declaration used for the line shown to indicate snapping to a edge line when {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is set.
     *
     * @param snapAlignEdgeLineStyle New snapAlignEdgeLineStyle value. Default value is "1px solid #555555"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setSnapAlignEdgeLineStyle(String snapAlignEdgeLineStyle)  throws IllegalStateException {
        setAttribute("snapAlignEdgeLineStyle", snapAlignEdgeLineStyle, false);
    }

    /**
     * CSS border declaration used for the line shown to indicate snapping to a edge line when {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is set.
     *
     * @return Current snapAlignEdgeLineStyle value. Default value is "1px solid #555555"
     */
    public String getSnapAlignEdgeLineStyle()  {
        return getAttributeAsString("snapAlignEdgeLineStyle");
    }
    

    /**
     * Describes which axes to apply snap-to-grid to. Valid values are "horizontal", "vertical" or "both".
     *
     * @param snapAxis New snapAxis value. Default value is "both"
     * @see com.smartgwt.client.widgets.Canvas#setSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapAxis(String snapAxis) {
        setAttribute("snapAxis", snapAxis, true);
    }

    /**
     * Describes which axes to apply snap-to-grid to. Valid values are "horizontal", "vertical" or "both".
     *
     * @return Current snapAxis value. Default value is "both"
     * @see com.smartgwt.client.widgets.Canvas#getSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public String getSnapAxis()  {
        return getAttributeAsString("snapAxis");
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is defined to this widget, this property can be used to
     * define which edge of this widget should be snapped to an edge of the master or parent element. <P> If unspecified the,
     * default snapTo behavior is set up to align the "snapTo" edge of this widget with the snapTo edge of the master or
     * parent.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the snapEdge property of this canvas, and handle repositioning.
     *
     * @param snapEdge new snapEdge value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setSnapTo
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setSnapEdge(String snapEdge) {
        setAttribute("snapEdge", snapEdge, true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is defined to this widget, this property can be used to
     * define which edge of this widget should be snapped to an edge of the master or parent element. <P> If unspecified the,
     * default snapTo behavior is set up to align the "snapTo" edge of this widget with the snapTo edge of the master or
     * parent.
     *
     * @return Return the snapEdge value of this object. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getSnapTo
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public String getSnapEdge()  {
        return getAttributeAsString("snapEdge");
    }
    

    /**
     * The horizontal snap direction. Set this value to "before" to snap to the nearest gridpoint to the left; set it to
     * "after" to snap to the nearest gridpoint to the right; and set it to "nearest" to snap to the nearest gridpoint in
     * either direction.
     *
     * @param snapHDirection New snapHDirection value. Default value is "after"
     * @see com.smartgwt.client.widgets.Canvas#setSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapHDirection(String snapHDirection) {
        setAttribute("snapHDirection", snapHDirection, true);
    }

    /**
     * The horizontal snap direction. Set this value to "before" to snap to the nearest gridpoint to the left; set it to
     * "after" to snap to the nearest gridpoint to the right; and set it to "nearest" to snap to the nearest gridpoint in
     * either direction.
     *
     * @return Current snapHDirection value. Default value is "after"
     * @see com.smartgwt.client.widgets.Canvas#getSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public String getSnapHDirection()  {
        return getAttributeAsString("snapHDirection");
    }
    

    /**
     * The horizontal grid size to use, in pixels, when snap-to-grid is enabled.
     *
     * @param snapHGap New snapHGap value. Default value is 20
     * @see com.smartgwt.client.widgets.Canvas#setSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapHGap(int snapHGap) {
        setAttribute("snapHGap", snapHGap, true);
    }

    /**
     * The horizontal grid size to use, in pixels, when snap-to-grid is enabled.
     *
     * @return Current snapHGap value. Default value is 20
     * @see com.smartgwt.client.widgets.Canvas#getSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public int getSnapHGap()  {
        return getAttributeAsInt("snapHGap");
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is defined for this widget, this property can be used to
     * specify an offset in px or percentage for the left coordinate of this widget. <P> For example if <code>snapTo</code> is
     * specified as <code>"L"</code> and <code>snapOffsetLeft</code> is set to 6, this widget will be rendered 6px inside the
     * left edge of its parent or master element. Alternatively if <code>snapTo</code> was set to <code>"R"</code>, a
     * <code>snapOffsetLeft</code> value of -6 would cause the component to be rendered 6px inside the right edge of its parent
     * or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for {@link com.smartgwt.client.widgets.Canvas#getSnapOffsetLeft snapOffsetLeft}.
     *
     * @param snapOffsetLeft new snapOffsetLeft value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setSnapTo
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setSnapOffsetLeft(Integer snapOffsetLeft) {
        setAttribute("snapOffsetLeft", snapOffsetLeft, true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is defined for this widget, this property can be used to
     * specify an offset in px or percentage for the left coordinate of this widget. <P> For example if <code>snapTo</code> is
     * specified as <code>"L"</code> and <code>snapOffsetLeft</code> is set to 6, this widget will be rendered 6px inside the
     * left edge of its parent or master element. Alternatively if <code>snapTo</code> was set to <code>"R"</code>, a
     * <code>snapOffsetLeft</code> value of -6 would cause the component to be rendered 6px inside the right edge of its parent
     * or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}.
     *
     * @return Current snapOffsetLeft value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getSnapTo
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public Integer getSnapOffsetLeft()  {
        return getAttributeAsInt("snapOffsetLeft");
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is defined for this widget, this property can be used to
     * specify an offset in px or percentage for the top coordinate of this widget. <P> For example if <code>snapTo</code> is
     * specified as <code>"T"</code> and <code>snapOffsetTop</code> is set to 6, this widget will be rendered 6px below the top
     * edge of its parent or master element. . Alternatively if <code>snapTo</code> was set to <code>"B"</code>, a
     * <code>snapOffsetTop</code> value of -6 would cause the component to be rendered 6px inside the bottom edge of its parent
     * or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for {@link com.smartgwt.client.widgets.Canvas#getSnapOffsetTop snapOffsetTop}.
     *
     * @param snapOffsetTop new snapOffsetTop value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setSnapTo
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setSnapOffsetTop(Integer snapOffsetTop) {
        setAttribute("snapOffsetTop", snapOffsetTop, true);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is defined for this widget, this property can be used to
     * specify an offset in px or percentage for the top coordinate of this widget. <P> For example if <code>snapTo</code> is
     * specified as <code>"T"</code> and <code>snapOffsetTop</code> is set to 6, this widget will be rendered 6px below the top
     * edge of its parent or master element. . Alternatively if <code>snapTo</code> was set to <code>"B"</code>, a
     * <code>snapOffsetTop</code> value of -6 would cause the component to be rendered 6px inside the bottom edge of its parent
     * or {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}.
     *
     * @return Current snapOffsetTop value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getSnapTo
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public Integer getSnapOffsetTop()  {
        return getAttributeAsInt("snapOffsetTop");
    }
    

    /**
     * When this canvas is dropped onto an object supporting snap-to-grid, should it snap to the grid (true, the default) or
     * just drop wherever the mouse is (false).
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param snapOnDrop New snapOnDrop value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#setSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#shouldSnapOnDrop
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapOnDrop(Boolean snapOnDrop) {
        setAttribute("snapOnDrop", snapOnDrop, true);
    }

    /**
     * When this canvas is dropped onto an object supporting snap-to-grid, should it snap to the grid (true, the default) or
     * just drop wherever the mouse is (false).
     *
     * @return Current snapOnDrop value. Default value is true
     * @see com.smartgwt.client.widgets.Canvas#getSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#shouldSnapOnDrop
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public Boolean getSnapOnDrop()  {
        Boolean result = getAttributeAsBoolean("snapOnDrop");
        return result == null ? true : result;
    }
    

    /**
     * Flag to disable snapping to alignment when this Canvas is resized. <p> To control snapping to align for the children
     * resized <i>within</i> this Canvas, see {@link com.smartgwt.client.widgets.Canvas#getChildrenResizeSnapAlign
     * childrenResizeSnapAlign} instead.
     *
     * @param snapResizeToAlign New snapResizeToAlign value. Default value is null
     */
    public void setSnapResizeToAlign(Boolean snapResizeToAlign) {
        setAttribute("snapResizeToAlign", snapResizeToAlign, true);
    }

    /**
     * Flag to disable snapping to alignment when this Canvas is resized. <p> To control snapping to align for the children
     * resized <i>within</i> this Canvas, see {@link com.smartgwt.client.widgets.Canvas#getChildrenResizeSnapAlign
     * childrenResizeSnapAlign} instead.
     *
     * @return Current snapResizeToAlign value. Default value is null
     */
    public Boolean getSnapResizeToAlign()  {
        return getAttributeAsBoolean("snapResizeToAlign");
    }
    

    /**
     * Causes this canvas to snap to its parent's grid when resizing. Note that this value defaults to the Canvas's {@link
     * com.smartgwt.client.widgets.Canvas#getSnapToGrid snapToGrid} value if undefined.
     *
     * @param snapResizeToGrid New snapResizeToGrid value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapResizeToGrid(Boolean snapResizeToGrid) {
        setAttribute("snapResizeToGrid", snapResizeToGrid, true);
    }

    /**
     * Causes this canvas to snap to its parent's grid when resizing. Note that this value defaults to the Canvas's {@link
     * com.smartgwt.client.widgets.Canvas#getSnapToGrid snapToGrid} value if undefined.
     *
     * @return Current snapResizeToGrid value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public Boolean getSnapResizeToGrid()  {
        return getAttributeAsBoolean("snapResizeToGrid");
    }
    

    /**
     * Position this widget such that it is aligned with ("snapped to") an edge of its {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas master} (if specified), or its {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas}. <P> Note that this property also impacts the sizing
     * of this widget. If this widgets size is specified as a percent value, and has no explicit {@link
     * com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, sizing will be calculated based on the size of the
     * {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas} when snapTo is set. <P> Possible values: BR,
     * BL, TR, TL, R, L, B, T, C where B=Bottom, T=Top, L=Left, R=right and C=center <P> Standard snapTo behavior will attach
     * the outer edge of the widget to the parent or master element - for example setting <code>snapTo</code> to
     * <code>"B"</code> would align the bottom edge of this component with the bottom edge of the master or parent element (and
     * center this component horizontally over its master or parent element). {@link
     * com.smartgwt.client.widgets.Canvas#getSnapEdge snapEdge} can be specified to change this behavior allowing the developer
     * to, for example, align the top edge of this component with the bottom edge of its {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}. <P> {@link
     * com.smartgwt.client.widgets.Canvas#getSnapOffsetLeft snapOffsetLeft} and {@link
     * com.smartgwt.client.widgets.Canvas#getSnapOffsetTop snapOffsetTop} may also be specified to offset the element from
     * exact snapTo alignment.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the snapTo property of this canvas, and handle repositioning.
     *
     * @param snapTo new snapTo value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setSnapEdge
     * @see com.smartgwt.client.widgets.Canvas#setPercentBox
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setSnapTo(String snapTo) {
        setAttribute("snapTo", snapTo, true);
    }

    /**
     * Position this widget such that it is aligned with ("snapped to") an edge of its {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas master} (if specified), or its {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas}. <P> Note that this property also impacts the sizing
     * of this widget. If this widgets size is specified as a percent value, and has no explicit {@link
     * com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, sizing will be calculated based on the size of the
     * {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas} when snapTo is set. <P> Possible values: BR,
     * BL, TR, TL, R, L, B, T, C where B=Bottom, T=Top, L=Left, R=right and C=center <P> Standard snapTo behavior will attach
     * the outer edge of the widget to the parent or master element - for example setting <code>snapTo</code> to
     * <code>"B"</code> would align the bottom edge of this component with the bottom edge of the master or parent element (and
     * center this component horizontally over its master or parent element). {@link
     * com.smartgwt.client.widgets.Canvas#getSnapEdge snapEdge} can be specified to change this behavior allowing the developer
     * to, for example, align the top edge of this component with the bottom edge of its {@link
     * com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}. <P> {@link
     * com.smartgwt.client.widgets.Canvas#getSnapOffsetLeft snapOffsetLeft} and {@link
     * com.smartgwt.client.widgets.Canvas#getSnapOffsetTop snapOffsetTop} may also be specified to offset the element from
     * exact snapTo alignment.
     *
     * @return Return the snapTo value of this object. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getSnapEdge
     * @see com.smartgwt.client.widgets.Canvas#getPercentBox
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public String getSnapTo()  {
        return getAttributeAsString("snapTo");
    }
    

    /**
     * Flag to disable snapping to alignment when this Canvas is dragged when  {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent. <p> To
     * control snapping to align for the children dragged <i>within</i> this Canvas, see {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} instead.
     *
     * @param snapToAlign New snapToAlign value. Default value is null
     */
    public void setSnapToAlign(Boolean snapToAlign) {
        setAttribute("snapToAlign", snapToAlign, true);
    }

    /**
     * Flag to disable snapping to alignment when this Canvas is dragged when  {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent. <p> To
     * control snapping to align for the children dragged <i>within</i> this Canvas, see {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} instead.
     *
     * @return Current snapToAlign value. Default value is null
     */
    public Boolean getSnapToAlign()  {
        return getAttributeAsBoolean("snapToAlign");
    }
    

    /**
     * Flag to disable snapping to center alignment when this Canvas is dragged when  {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent.
     *
     * @param snapToCenterAlign New snapToCenterAlign value. Default value is null
     */
    public void setSnapToCenterAlign(Boolean snapToCenterAlign) {
        setAttribute("snapToCenterAlign", snapToCenterAlign, true);
    }

    /**
     * Flag to disable snapping to center alignment when this Canvas is dragged when  {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent.
     *
     * @return Current snapToCenterAlign value. Default value is null
     */
    public Boolean getSnapToCenterAlign()  {
        return getAttributeAsBoolean("snapToCenterAlign");
    }
    

    /**
     * Flag to disable snapping to edge alignment when this Canvas is dragged when  {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent.
     *
     * @param snapToEdgeAlign New snapToEdgeAlign value. Default value is null
     */
    public void setSnapToEdgeAlign(Boolean snapToEdgeAlign) {
        setAttribute("snapToEdgeAlign", snapToEdgeAlign, true);
    }

    /**
     * Flag to disable snapping to edge alignment when this Canvas is dragged when  {@link
     * com.smartgwt.client.widgets.Canvas#getChildrenSnapAlign childrenSnapAlign} is enabled on this Canvas' parent.
     *
     * @return Current snapToEdgeAlign value. Default value is null
     */
    public Boolean getSnapToEdgeAlign()  {
        return getAttributeAsBoolean("snapToEdgeAlign");
    }
    

    /**
     * Causes this canvas to snap to its parent's grid when dragging.
     *
     * @param snapToGrid New snapToGrid value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapToGrid(Boolean snapToGrid) {
        setAttribute("snapToGrid", snapToGrid, true);
    }

    /**
     * Causes this canvas to snap to its parent's grid when dragging.
     *
     * @return Current snapToGrid value. Default value is null
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public Boolean getSnapToGrid()  {
        return getAttributeAsBoolean("snapToGrid");
    }
    

    /**
     * The vertical snap direction. Set this value to "before" to snap to the nearest gridpoint above; set it to "after" to
     * snap to the nearest gridpoint below; and set it to "nearest" to snap to the nearest gridpoint in either direction.
     *
     * @param snapVDirection New snapVDirection value. Default value is "after"
     * @see com.smartgwt.client.widgets.Canvas#setSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapVDirection(String snapVDirection) {
        setAttribute("snapVDirection", snapVDirection, true);
    }

    /**
     * The vertical snap direction. Set this value to "before" to snap to the nearest gridpoint above; set it to "after" to
     * snap to the nearest gridpoint below; and set it to "nearest" to snap to the nearest gridpoint in either direction.
     *
     * @return Current snapVDirection value. Default value is "after"
     * @see com.smartgwt.client.widgets.Canvas#getSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public String getSnapVDirection()  {
        return getAttributeAsString("snapVDirection");
    }
    

    /**
     * The vertical grid size to use, in pixels, when snap-to-grid is enabled.
     *
     * @param snapVGap New snapVGap value. Default value is 20
     * @see com.smartgwt.client.widgets.Canvas#setSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#setChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setSnapVGap(int snapVGap) {
        setAttribute("snapVGap", snapVGap, true);
    }

    /**
     * The vertical grid size to use, in pixels, when snap-to-grid is enabled.
     *
     * @return Current snapVGap value. Default value is 20
     * @see com.smartgwt.client.widgets.Canvas#getSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getSnapResizeToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapToGrid
     * @see com.smartgwt.client.widgets.Canvas#getChildrenSnapResizeToGrid
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public int getSnapVGap()  {
        return getAttributeAsInt("snapVGap");
    }
    

    /**
     * Whether this canvas should always start a new line when used as a tile in a  {@link
     * com.smartgwt.client.widgets.layout.FlowLayout}.  This property is not supported in a {@link
     * com.smartgwt.client.widgets.tile.TileLayout} with {@link com.smartgwt.client.widgets.tile.TileLayout#getLayoutPolicy
     * TileLayout.layoutPolicy}: "fit" or if databound (i.e. for a {@link com.smartgwt.client.widgets.tile.TileGrid}).
     *
     * @param startLine New startLine value. Default value is false
     * @see com.smartgwt.client.widgets.tile.TileLayout#setAutoWrapLines
     */
    public void setStartLine(Boolean startLine) {
        setAttribute("startLine", startLine, true);
    }

    /**
     * Whether this canvas should always start a new line when used as a tile in a  {@link
     * com.smartgwt.client.widgets.layout.FlowLayout}.  This property is not supported in a {@link
     * com.smartgwt.client.widgets.tile.TileLayout} with {@link com.smartgwt.client.widgets.tile.TileLayout#getLayoutPolicy
     * TileLayout.layoutPolicy}: "fit" or if databound (i.e. for a {@link com.smartgwt.client.widgets.tile.TileGrid}).
     *
     * @return Current startLine value. Default value is false
     * @see com.smartgwt.client.widgets.tile.TileLayout#getAutoWrapLines
     */
    public Boolean getStartLine()  {
        Boolean result = getAttributeAsBoolean("startLine");
        return result == null ? false : result;
    }
    

    /**
     * The CSS class applied to this widget as a whole.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Sets the CSS class for this widget
     *
     * @param styleName new CSS style name. Default value is "normal"
     * @see com.smartgwt.client.docs.CSSStyleName CSSStyleName 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_css" target="examples">CSS styles Example</a>
     */
    public void setStyleName(String styleName) {
        setAttribute("styleName", styleName, true);
    }

    /**
     * The CSS class applied to this widget as a whole.
     *
     * @return Current styleName value. Default value is "normal"
     * @see com.smartgwt.client.docs.CSSStyleName CSSStyleName 
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_lf_css" target="examples">CSS styles Example</a>
     */
    public String getStyleName()  {
        return getAttributeAsString("styleName");
    }
    

    /**
     * If specified this governs the tabIndex of the widget in the page's tab order. Setting this value to <code>-1</code> will
     * ensure the canvas does not show up in the page's tab order, though if {@link
     * com.smartgwt.client.widgets.Canvas#getCanFocus canFocus} is true, the user may still give it keyboard focus by clicking
     * on the widget directly. <P> By default Smart GWT auto-assigns tab-indices, ensuring focusable widgets are reachable by
     * tabbing in an intuitive order based on widget hierarchy and  draw order. Specifying an explicit tab index means a widget
     * will not participate  in this automatic tab position allocation and is typically not recommended except for very simple
     * cases. <P> For more information on automatic tab index assignment, including recommended approaches for customizing tab
     * order assignation, see the {@link com.smartgwt.client.docs.TabOrderOverview}. Developers may call {@link
     * com.smartgwt.client.widgets.Canvas#clearExplicitTabIndex clearExplicitTabIndex()} to clear any explicitly assigned tab
     * index, and have the widget participate in automatic tab position allocation. <P> <code>canvas.tabIndex</code> cannot be
     * set to greater than {@link com.smartgwt.client.widgets.Canvas#TAB_INDEX_FLOOR TAB_INDEX_FLOOR} - as we reserve the
     * values above this range for auto-assigned tab-indices.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Assign an explicit {@link com.smartgwt.client.widgets.Canvas#getTabIndex tabIndex} to this widget at runtime. <P> Developers may also call {@link com.smartgwt.client.widgets.Canvas#clearExplicitTabIndex clearExplicitTabIndex()} to clear any explicitly assigned tab index, and have the widget participate in automatic tab position allocation. For more information see {@link com.smartgwt.client.widgets.Canvas#getTabIndex tabIndex}
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param tabIndex New tabIndex for this widget. Must be less than                          {@link
     * com.smartgwt.client.widgets.Canvas#TAB_INDEX_FLOOR TAB_INDEX_FLOOR} to avoid interfering with auto-assigned             
     *             tab indices on the page. Default value is null
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public void setTabIndex(Integer tabIndex) {
    	// setAttribute("tabIndex", tabIndex, true);
    	//TODO AURIGA START    	
    	// Se il tabIndex è diverso da -1 lascio che sia il browser a gestire l'indice dei tab
    	// Non settando il tab index evito che ci siano più item con lo stesso tabIndex, ciò causava rallentamenti nel caricamento dei dettagli
    	// Devo solo settare il setCanFocus a false se il tabIndex è -1
    	if (tabIndex != null && tabIndex == -1) {
    		setCanFocus(false);
    	} 
    	//TODO AURIGA END    	
    }

    /**
     * If specified this governs the tabIndex of the widget in the page's tab order. Setting this value to <code>-1</code> will
     * ensure the canvas does not show up in the page's tab order, though if {@link
     * com.smartgwt.client.widgets.Canvas#getCanFocus canFocus} is true, the user may still give it keyboard focus by clicking
     * on the widget directly. <P> By default Smart GWT auto-assigns tab-indices, ensuring focusable widgets are reachable by
     * tabbing in an intuitive order based on widget hierarchy and  draw order. Specifying an explicit tab index means a widget
     * will not participate  in this automatic tab position allocation and is typically not recommended except for very simple
     * cases. <P> For more information on automatic tab index assignment, including recommended approaches for customizing tab
     * order assignation, see the {@link com.smartgwt.client.docs.TabOrderOverview}. Developers may call {@link
     * com.smartgwt.client.widgets.Canvas#clearExplicitTabIndex clearExplicitTabIndex()} to clear any explicitly assigned tab
     * index, and have the widget participate in automatic tab position allocation. <P> <code>canvas.tabIndex</code> cannot be
     * set to greater than {@link com.smartgwt.client.widgets.Canvas#TAB_INDEX_FLOOR TAB_INDEX_FLOOR} - as we reserve the
     * values above this range for auto-assigned tab-indices.
     *
     * @return Current tabIndex value. Default value is null
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public Integer getTabIndex()  {
        return getAttributeAsInt("tabIndex");
    }
    

    /**
     * Number of pixels the top of the widget is offset down from its default drawing context (either its parent's top-left
     * corner, or the document flow, depending on the value of the {@link com.smartgwt.client.widgets.Canvas#getPosition
     * position} property). <P> Can also be set as a percentage, specified as a String ending in '%', eg, "50%".  In this case
     * the top coordinate is considered as a percentage of the specified height of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the top coordinate of this object, relative to its enclosing context, in pixels. <P> NOTE: if you're setting multiple coordinates, use setRect() or moveTo() instead
     *
     * @param top new top coordinate. Default value is 0
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setTop(int top) {
        setAttribute("top", top, true);
    }

    /**
     * Number of pixels the top of the widget is offset down from its default drawing context (either its parent's top-left
     * corner, or the document flow, depending on the value of the {@link com.smartgwt.client.widgets.Canvas#getPosition
     * position} property). <P> Can also be set as a percentage, specified as a String ending in '%', eg, "50%".  In this case
     * the top coordinate is considered as a percentage of the specified height of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     *
     * @return Return the top coordinate of this object, relative to its enclosing context, in pixels. Default value is 0
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public int getTop()  {
        return getAttributeAsInt("top");
    }

    /**
     * Number of pixels the top of the widget is offset down from its default drawing context (either its parent's top-left
     * corner, or the document flow, depending on the value of the {@link com.smartgwt.client.widgets.Canvas#getPosition
     * position} property). <P> Can also be set as a percentage, specified as a String ending in '%', eg, "50%".  In this case
     * the top coordinate is considered as a percentage of the specified height of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the top coordinate of this object, relative to its enclosing context, in pixels. <P> NOTE: if you're setting multiple coordinates, use setRect() or moveTo() instead
     *
     * @param top new top coordinate. Default value is 0
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public void setTop(String top) {
        setAttribute("top", top, true);
    }
    

    /**
     * The top-most Canvas (i.e., not a child of any other Canvas), if any, in this widget's containment hierarchy.
     * <p>
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Current topElement value. Default value is null
     * @throws IllegalStateException if this widget has not yet been rendered.
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public Canvas getTopElement() throws IllegalStateException {
        errorIfNotCreated("topElement");
        return (Canvas)Canvas.getByJSObject(getAttributeAsJavaScriptObject("topElement"));
    }
    

    /**
     * Should canvases with no {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas} be moved to the end of
     * the TabIndexManager tree on draw()? <P> If set to false, the tab-position will not be modified on draw. <P> This
     * property is useful for cases where the tab position of a widget will be managed by some explicit tabIndex management
     * code.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param updateTabPositionOnDraw New updateTabPositionOnDraw value. Default value is true
     */
    public void setUpdateTabPositionOnDraw(boolean updateTabPositionOnDraw) {
        setAttribute("updateTabPositionOnDraw", updateTabPositionOnDraw, true);
    }

    /**
     * Should canvases with no {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas} be moved to the end of
     * the TabIndexManager tree on draw()? <P> If set to false, the tab-position will not be modified on draw. <P> This
     * property is useful for cases where the tab position of a widget will be managed by some explicit tabIndex management
     * code.
     *
     * @return Current updateTabPositionOnDraw value. Default value is true
     */
    public boolean getUpdateTabPositionOnDraw()  {
        Boolean result = getAttributeAsBoolean("updateTabPositionOnDraw");
        return result == null ? true : result;
    }
    

    /**
     * Should canvases with a {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas} be added to the
     * TabIndexManager under the parent as described in {@link com.smartgwt.client.widgets.Canvas#updateChildTabPositions
     * updateChildTabPositions()} and {@link com.smartgwt.client.widgets.Canvas#updateChildTabPosition
     * updateChildTabPosition()}? <P> If set to false, the tab-position will not be modified on parent change. <P> This
     * property is useful for cases where the tab position of a widget will be managed by something other than the parent
     * canvas, for example for  {@link com.smartgwt.client.widgets.form.fields.CanvasItem#getCanvas canvasItem canvases}.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for the {@link com.smartgwt.client.widgets.Canvas#getUpdateTabPositionOnReparent updateTabPositionOnReparent} attribute.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param updateTabPositionOnReparent new value for canvas.updateTabPositionOnReparent. Default value is true
     */
    public void setUpdateTabPositionOnReparent(boolean updateTabPositionOnReparent) {
        setAttribute("updateTabPositionOnReparent", updateTabPositionOnReparent, true);
    }

    /**
     * Should canvases with a {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas} be added to the
     * TabIndexManager under the parent as described in {@link com.smartgwt.client.widgets.Canvas#updateChildTabPositions
     * updateChildTabPositions()} and {@link com.smartgwt.client.widgets.Canvas#updateChildTabPosition
     * updateChildTabPosition()}? <P> If set to false, the tab-position will not be modified on parent change. <P> This
     * property is useful for cases where the tab position of a widget will be managed by something other than the parent
     * canvas, for example for  {@link com.smartgwt.client.widgets.form.fields.CanvasItem#getCanvas canvasItem canvases}.
     *
     * @return Current updateTabPositionOnReparent value. Default value is true
     */
    public boolean getUpdateTabPositionOnReparent()  {
        Boolean result = getAttributeAsBoolean("updateTabPositionOnReparent");
        return result == null ? true : result;
    }
    

    /**
     * In earlier versions of Internet Explorer (pre IE9), a native limitation exists  whereby if HTML elements are overlapping
     * on the page, certain elements can appear to "burn through" elements in the same position with a higher z-index. Specific
     * cases in which this have been observed include Applets, &lt;IFRAME&gt; elements, and for older versions of IE, native
     * &lt;SELECT&gt; items. <P> The backMask is a workaround for this issue. If <code>useBackMask</code> is set to
     * <code>true</code>, the component will render an empty &lt;IFRAME&gt; element behind the canvas, which prevents this
     * effect in all known cases. <P> Has no effect in other browsers.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param useBackMask New useBackMask value. Default value is false
     */
    public void setUseBackMask(Boolean useBackMask) {
        setAttribute("useBackMask", useBackMask, true);
    }

    /**
     * In earlier versions of Internet Explorer (pre IE9), a native limitation exists  whereby if HTML elements are overlapping
     * on the page, certain elements can appear to "burn through" elements in the same position with a higher z-index. Specific
     * cases in which this have been observed include Applets, &lt;IFRAME&gt; elements, and for older versions of IE, native
     * &lt;SELECT&gt; items. <P> The backMask is a workaround for this issue. If <code>useBackMask</code> is set to
     * <code>true</code>, the component will render an empty &lt;IFRAME&gt; element behind the canvas, which prevents this
     * effect in all known cases. <P> Has no effect in other browsers.
     *
     * @return Current useBackMask value. Default value is false
     */
    public Boolean getUseBackMask()  {
        Boolean result = getAttributeAsBoolean("useBackMask");
        return result == null ? false : result;
    }
    

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow} is true, should we use the css
     * <code>box-shadow</code> property (where supported) to achieve the shadow? <P> Set this property to false to switch to a
     * media-based approach, achieved by rendering the {@link com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage} in
     * an automatically generated peer. This approach is also used regardless of this property value in older browsers  where
     * the css <code>box-shadow</code> property isn't supported. <P> See also {@link
     * com.smartgwt.client.widgets.Canvas#getShadowColor shadowColor}, {@link com.smartgwt.client.widgets.Canvas#getShadowDepth
     * shadowDepth}, {@link com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset}, {@link
     * com.smartgwt.client.widgets.Canvas#getShadowSoftness shadowSoftness} and {@link
     * com.smartgwt.client.widgets.Canvas#getShadowSpread shadowSpread}
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param useCSSShadow New useCSSShadow value. Default value is true
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setUseCSSShadow(boolean useCSSShadow)  throws IllegalStateException {
        setAttribute("useCSSShadow", useCSSShadow, false);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getShowShadow showShadow} is true, should we use the css
     * <code>box-shadow</code> property (where supported) to achieve the shadow? <P> Set this property to false to switch to a
     * media-based approach, achieved by rendering the {@link com.smartgwt.client.widgets.Canvas#getShadowImage shadowImage} in
     * an automatically generated peer. This approach is also used regardless of this property value in older browsers  where
     * the css <code>box-shadow</code> property isn't supported. <P> See also {@link
     * com.smartgwt.client.widgets.Canvas#getShadowColor shadowColor}, {@link com.smartgwt.client.widgets.Canvas#getShadowDepth
     * shadowDepth}, {@link com.smartgwt.client.widgets.Canvas#getShadowOffset shadowOffset}, {@link
     * com.smartgwt.client.widgets.Canvas#getShadowSoftness shadowSoftness} and {@link
     * com.smartgwt.client.widgets.Canvas#getShadowSpread shadowSpread}
     *
     * @return Current useCSSShadow value. Default value is true
     */
    public boolean getUseCSSShadow()  {
        Boolean result = getAttributeAsBoolean("useCSSShadow");
        return result == null ? true : result;
    }
    

    /**
     * This flag controls whether we register the component as a maskable item with the EventHandler. If enabled, a backmask
     * will be automatically created for the dragMoveTarget on the fly to avoid burnthrough e.g. by plugins or frames. <P> Note
     * that this property will be defaulted to false unless the canvas contains an IFrame, in which case it will be defaulted
     * to true. <p> The {@link com.smartgwt.client.widgets.Canvas#getDragMaskType dragMaskType} property controls what kind of
     * mask is used in case useDragMask is enabled.
     *
     * @param useDragMask New useDragMask value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public void setUseDragMask(boolean useDragMask) {
        setAttribute("useDragMask", useDragMask, true);
    }

    /**
     * This flag controls whether we register the component as a maskable item with the EventHandler. If enabled, a backmask
     * will be automatically created for the dragMoveTarget on the fly to avoid burnthrough e.g. by plugins or frames. <P> Note
     * that this property will be defaulted to false unless the canvas contains an IFrame, in which case it will be defaulted
     * to true. <p> The {@link com.smartgwt.client.widgets.Canvas#getDragMaskType dragMaskType} property controls what kind of
     * mask is used in case useDragMask is enabled.
     *
     * @return Current useDragMask value. Default value is false
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public boolean getUseDragMask()  {
        Boolean result = getAttributeAsBoolean("useDragMask");
        return result == null ? false : result;
    }
    

    /**
     * If set, forces the main SVG image or icon in the canvas to be rendered in an &lt;image&gt; tag rather than an
     * &lt;object&gt; tag, the default.  Typical use cases might be configuring the image of an {@link
     * com.smartgwt.client.widgets.Img} or {@link com.smartgwt.client.widgets.ImgButton}, or the icon of a {@link
     * com.smartgwt.client.widgets.Button}. <P> Rendering via &lt;object&gt; tag provides the maximum support for CSS in SVG,
     * but may result in a flicker at the browser level when changing images - either manually such as with {@link
     * com.smartgwt.client.widgets.Canvas#setImage setImage()} or via state change from rollover, mouseDown, etc.  Using
     * &lt;image&gt; tags to inline the images breaks CSS support but may avoid flickering. <P> Note that if multiple icons are
     * potentially present in a canvas (e.g. {@link com.smartgwt.client.widgets.grid.ListGrid#getRemoveIcon removeIcons} in the
     * cells of a grid body), then setting this property on the widget instance may have no effect.  In such case, the {@link
     * com.smartgwt.client.widgets.Canvas} prototype is consulted.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param useImageForSVG New useImageForSVG value. Default value is false
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.Img#setSrc
     * @see com.smartgwt.client.widgets.Button#setIcon
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public void setUseImageForSVG(boolean useImageForSVG)  throws IllegalStateException {
        setAttribute("useImageForSVG", useImageForSVG, false);
    }

    /**
     * If set, forces the main SVG image or icon in the canvas to be rendered in an &lt;image&gt; tag rather than an
     * &lt;object&gt; tag, the default.  Typical use cases might be configuring the image of an {@link
     * com.smartgwt.client.widgets.Img} or {@link com.smartgwt.client.widgets.ImgButton}, or the icon of a {@link
     * com.smartgwt.client.widgets.Button}. <P> Rendering via &lt;object&gt; tag provides the maximum support for CSS in SVG,
     * but may result in a flicker at the browser level when changing images - either manually such as with {@link
     * com.smartgwt.client.widgets.Canvas#setImage setImage()} or via state change from rollover, mouseDown, etc.  Using
     * &lt;image&gt; tags to inline the images breaks CSS support but may avoid flickering. <P> Note that if multiple icons are
     * potentially present in a canvas (e.g. {@link com.smartgwt.client.widgets.grid.ListGrid#getRemoveIcon removeIcons} in the
     * cells of a grid body), then setting this property on the widget instance may have no effect.  In such case, the {@link
     * com.smartgwt.client.widgets.Canvas} prototype is consulted.
     *
     * @return Current useImageForSVG value. Default value is false
     * @see com.smartgwt.client.widgets.Img#getSrc
     * @see com.smartgwt.client.widgets.Button#getIcon
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public boolean getUseImageForSVG()  {
        Boolean result = getAttributeAsBoolean("useImageForSVG");
        return result == null ? false : result;
    }
    

    /**
     * If set, native HTML5 drag and drop will be used for all drags initiated on this widget (on browsers where this is
     * supported). <p> When using native HTML5 drags, the same series of events fires as for a normal drag ({@link
     * com.smartgwt.client.widgets.Canvas#addDragStartHandler Canvas.dragStart()}, {@link
     * com.smartgwt.client.widgets.Canvas#addDropMoveHandler Canvas.dropMove()}, etc.), and the {@link
     * com.smartgwt.client.widgets.Canvas#getDragType dragType} / {@link com.smartgwt.client.widgets.Canvas#getDropTypes
     * dropTypes} system works. {@link com.smartgwt.client.widgets.Canvas#getDragAppearance dragAppearance} is not supported;
     * however, basic customization of the browser's tracker image is supported in certain browsers via the {@link
     * com.smartgwt.client.util.EventHandler#setDragTrackerImage EventHandler.setDragTrackerImage()} API. <p> The primary
     * difference with a native drag is that it can be cross-frame; that is, the user can drag out of the current browser
     * window and drop into a different window or tab. <p> To provide information that will be available to a foreign frame,
     * use {@link com.smartgwt.client.util.EventHandler#setNativeDragData EventHandler.setNativeDragData()}.  This API must be
     * called when the {@link com.smartgwt.client.widgets.Canvas#addDragStartHandler Canvas.dragStart()} event fires, and will
     * not work if called at any other time. <p> However, due to browser bugs and/or browser-imposed limitations, the
     * information provided to <code>setNativeDragData</code> cannot be accessed in the foreign frame until the actual drop
     * occurs (mouse button released).  This means drop eligibility cannot be determined dynamically based on the dragged data;
     * instead, eligibility can only be determined based on the {@link com.smartgwt.client.widgets.Canvas#getDragType dragType}
     * / {@link com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} system. For this reason, a {@link
     * com.smartgwt.client.widgets.Canvas#getDragType dragType} <b>must</b> be set on the source of a drag. <p> NOTE: Although
     * Internet Explorer 10+ and Edge support a subset of the  <a href='http://www.w3.org/TR/html5/editing.html#dnd'
     * target='_blank'>HTML5 drag and drop standard</a>, native drag and drop is disabled in IE and Edge because cross-window
     * drags&mdash;the  primary purpose of this API&mdash;are not possible.
     *
     * @param useNativeDrag New useNativeDrag value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_native_drag_create" target="examples">Native Drag Create Example</a>
     */
    public void setUseNativeDrag(Boolean useNativeDrag)  throws IllegalStateException {
        setAttribute("useNativeDrag", useNativeDrag, false);
    }

    /**
     * If set, native HTML5 drag and drop will be used for all drags initiated on this widget (on browsers where this is
     * supported). <p> When using native HTML5 drags, the same series of events fires as for a normal drag ({@link
     * com.smartgwt.client.widgets.Canvas#addDragStartHandler Canvas.dragStart()}, {@link
     * com.smartgwt.client.widgets.Canvas#addDropMoveHandler Canvas.dropMove()}, etc.), and the {@link
     * com.smartgwt.client.widgets.Canvas#getDragType dragType} / {@link com.smartgwt.client.widgets.Canvas#getDropTypes
     * dropTypes} system works. {@link com.smartgwt.client.widgets.Canvas#getDragAppearance dragAppearance} is not supported;
     * however, basic customization of the browser's tracker image is supported in certain browsers via the {@link
     * com.smartgwt.client.util.EventHandler#setDragTrackerImage EventHandler.setDragTrackerImage()} API. <p> The primary
     * difference with a native drag is that it can be cross-frame; that is, the user can drag out of the current browser
     * window and drop into a different window or tab. <p> To provide information that will be available to a foreign frame,
     * use {@link com.smartgwt.client.util.EventHandler#setNativeDragData EventHandler.setNativeDragData()}.  This API must be
     * called when the {@link com.smartgwt.client.widgets.Canvas#addDragStartHandler Canvas.dragStart()} event fires, and will
     * not work if called at any other time. <p> However, due to browser bugs and/or browser-imposed limitations, the
     * information provided to <code>setNativeDragData</code> cannot be accessed in the foreign frame until the actual drop
     * occurs (mouse button released).  This means drop eligibility cannot be determined dynamically based on the dragged data;
     * instead, eligibility can only be determined based on the {@link com.smartgwt.client.widgets.Canvas#getDragType dragType}
     * / {@link com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} system. For this reason, a {@link
     * com.smartgwt.client.widgets.Canvas#getDragType dragType} <b>must</b> be set on the source of a drag. <p> NOTE: Although
     * Internet Explorer 10+ and Edge support a subset of the  <a href='http://www.w3.org/TR/html5/editing.html#dnd'
     * target='_blank'>HTML5 drag and drop standard</a>, native drag and drop is disabled in IE and Edge because cross-window
     * drags&mdash;the  primary purpose of this API&mdash;are not possible.
     *
     * @return Current useNativeDrag value. Default value is null
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_dd_native_drag_create" target="examples">Native Drag Create Example</a>
     */
    public Boolean getUseNativeDrag()  {
        return getAttributeAsBoolean("useNativeDrag");
    }
    

    /**
     * Configures where the Opacity filter is used for IE6-8. <P> With the default of null, opacity filters are used unless
     * {@link com.smartgwt.client.widgets.Canvas#neverUseFilters neverUseFilters} has been set.  When set explicitly to true,
     * opacity filters are used even if <code>neverUseFilters</code> is true. <P> See {@link
     * com.smartgwt.client.docs.IEFilters} for background.
     *
     * @param useOpacityFilter New useOpacityFilter value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.IEFilters IEFilters overview and related methods
     */
    public void setUseOpacityFilter(Boolean useOpacityFilter)  throws IllegalStateException {
        setAttribute("useOpacityFilter", useOpacityFilter, false);
    }

    /**
     * Configures where the Opacity filter is used for IE6-8. <P> With the default of null, opacity filters are used unless
     * {@link com.smartgwt.client.widgets.Canvas#neverUseFilters neverUseFilters} has been set.  When set explicitly to true,
     * opacity filters are used even if <code>neverUseFilters</code> is true. <P> See {@link
     * com.smartgwt.client.docs.IEFilters} for background.
     *
     * @return Current useOpacityFilter value. Default value is null
     * @see com.smartgwt.client.docs.IEFilters IEFilters overview and related methods
     */
    public Boolean getUseOpacityFilter()  {
        return getAttributeAsBoolean("useOpacityFilter");
    }
    

    /**
     * On {@link com.smartgwt.client.util.Browser#isTouch touch devices}, if this <code>Canvas</code> can be scrolled, should
     * touch-dragging the content area result in scrolling? Set to <code>false</code> if touch-dragging should not cause
     * scrolling. Note that setting to <code>false</code> enables the use of {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars custom scrollbars} on touch devices. <p>
     * <code>useTouchScrolling</code> can default to <code>false</code> if {@link
     * com.smartgwt.client.widgets.Canvas#getDisableTouchScrollingForDrag disableTouchScrollingForDrag} is <code>true</code>
     * and various built-in drag operations are enabled that normally interfere with touch scrolling (e.g. {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getCanDragSelect ListGrid.canDragSelect} and {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getCanReorderRecords ListGrid.canReorderRecords}). <p> When touch scrolling is
     * disabled, it can be difficult to interact with parts of the custom scrollbars at their default size of 16 pixels. In
     * touch browsers, any touch 8px before the thumb of a {@link com.smartgwt.client.widgets.Scrollbar custom scrollbar} will
     * be mapped to the thumb, but the other parts of the scrollbar do not have a similar tolerance applied. The width of the
     * custom scrollbars can be increased by setting the {@link com.smartgwt.client.widgets.Canvas#getScrollbarSize
     * scrollbarSize} to a larger value, but note that when {@link com.smartgwt.client.docs.Skinning spriting is enabled},
     * changing the <code>scrollbarSize</code> may cause tiling of certain images and backgrounds that make up the custom
     * scrollbar. This can be fixed for a component by creating it with {@link
     * com.smartgwt.client.widgets.Canvas#getScrollbarConstructor scrollbarConstructor} set to "Scrollbar"&mdash;a basic
     * scrollbar class that does not employ spriting.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param useTouchScrolling New useTouchScrolling value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public void setUseTouchScrolling(Boolean useTouchScrolling)  throws IllegalStateException {
        setAttribute("useTouchScrolling", useTouchScrolling, false);
    }

    /**
     * On {@link com.smartgwt.client.util.Browser#isTouch touch devices}, if this <code>Canvas</code> can be scrolled, should
     * touch-dragging the content area result in scrolling? Set to <code>false</code> if touch-dragging should not cause
     * scrolling. Note that setting to <code>false</code> enables the use of {@link
     * com.smartgwt.client.widgets.Canvas#getShowCustomScrollbars custom scrollbars} on touch devices. <p>
     * <code>useTouchScrolling</code> can default to <code>false</code> if {@link
     * com.smartgwt.client.widgets.Canvas#getDisableTouchScrollingForDrag disableTouchScrollingForDrag} is <code>true</code>
     * and various built-in drag operations are enabled that normally interfere with touch scrolling (e.g. {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getCanDragSelect ListGrid.canDragSelect} and {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getCanReorderRecords ListGrid.canReorderRecords}). <p> When touch scrolling is
     * disabled, it can be difficult to interact with parts of the custom scrollbars at their default size of 16 pixels. In
     * touch browsers, any touch 8px before the thumb of a {@link com.smartgwt.client.widgets.Scrollbar custom scrollbar} will
     * be mapped to the thumb, but the other parts of the scrollbar do not have a similar tolerance applied. The width of the
     * custom scrollbars can be increased by setting the {@link com.smartgwt.client.widgets.Canvas#getScrollbarSize
     * scrollbarSize} to a larger value, but note that when {@link com.smartgwt.client.docs.Skinning spriting is enabled},
     * changing the <code>scrollbarSize</code> may cause tiling of certain images and backgrounds that make up the custom
     * scrollbar. This can be fixed for a component by creating it with {@link
     * com.smartgwt.client.widgets.Canvas#getScrollbarConstructor scrollbarConstructor} set to "Scrollbar"&mdash;a basic
     * scrollbar class that does not employ spriting.
     *
     * @return Current useTouchScrolling value. Default value is null
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public Boolean getUseTouchScrolling()  {
        return getAttributeAsBoolean("useTouchScrolling");
    }
    

    /**
     * {@link com.smartgwt.client.widgets.form.ValuesManager} for managing values displayed in this component. If specified at
     * initialization time, this component will be added to the valuesManager via {@link
     * com.smartgwt.client.widgets.form.ValuesManager#addMember ValuesManager.addMember()}. <P> ValuesManagers allow different
     * fields of a single object to be displayed or edited across multiple UI components. Given a single values object, a
     * valuesManager will handle determining the appropriate field values for its member components and displaying them / 
     * responding to edits if the components support this. <P> Data may be derived simply from the specified fieldNames within
     * the member components, or for complex nested data structures can be specified by both component and field-level {@link
     * com.smartgwt.client.docs.DataPath}. <P> Note that components may be automatically bound to an existing valuesManager
     * attached to a  parent component if dataPath is specified. See {@link com.smartgwt.client.widgets.Canvas#getDataPath
     * dataPath} for more information. Also note that if a databound component has a specified dataSource and dataPath but no
     * specified valuesManager object one will be automatically generated as part of the databinding process
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for the {@link com.smartgwt.client.widgets.Canvas#getValuesManager valuesManager} attribute. This method may be called directly at  runtime to set the ValuesManager for a component; it has the same effect as calling  {@link com.smartgwt.client.widgets.form.ValuesManager#addMember ValuesManager.addMember()}, passing in this DataBoundComponent.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param valuesManager new dataPath. Default value is null
     */
    public void setValuesManager(ValuesManager valuesManager) {
        setAttribute("valuesManager", valuesManager == null ? null : valuesManager.getOrCreateJsObj(), true);
    }

    /**
     * {@link com.smartgwt.client.widgets.form.ValuesManager} for managing values displayed in this component. If specified at
     * initialization time, this component will be added to the valuesManager via {@link
     * com.smartgwt.client.widgets.form.ValuesManager#addMember ValuesManager.addMember()}. <P> ValuesManagers allow different
     * fields of a single object to be displayed or edited across multiple UI components. Given a single values object, a
     * valuesManager will handle determining the appropriate field values for its member components and displaying them / 
     * responding to edits if the components support this. <P> Data may be derived simply from the specified fieldNames within
     * the member components, or for complex nested data structures can be specified by both component and field-level {@link
     * com.smartgwt.client.docs.DataPath}. <P> Note that components may be automatically bound to an existing valuesManager
     * attached to a  parent component if dataPath is specified. See {@link com.smartgwt.client.widgets.Canvas#getDataPath
     * dataPath} for more information. Also note that if a databound component has a specified dataSource and dataPath but no
     * specified valuesManager object one will be automatically generated as part of the databinding process
     *
     * @return Current valuesManager value. Default value is null
     */
    public ValuesManager getValuesManager()  {
        return ValuesManager.getOrCreateRef(getAttributeAsJavaScriptObject("valuesManager"));
    }

    /**
     * {@link com.smartgwt.client.widgets.form.ValuesManager} for managing values displayed in this component. If specified at
     * initialization time, this component will be added to the valuesManager via {@link
     * com.smartgwt.client.widgets.form.ValuesManager#addMember ValuesManager.addMember()}. <P> ValuesManagers allow different
     * fields of a single object to be displayed or edited across multiple UI components. Given a single values object, a
     * valuesManager will handle determining the appropriate field values for its member components and displaying them / 
     * responding to edits if the components support this. <P> Data may be derived simply from the specified fieldNames within
     * the member components, or for complex nested data structures can be specified by both component and field-level {@link
     * com.smartgwt.client.docs.DataPath}. <P> Note that components may be automatically bound to an existing valuesManager
     * attached to a  parent component if dataPath is specified. See {@link com.smartgwt.client.widgets.Canvas#getDataPath
     * dataPath} for more information. Also note that if a databound component has a specified dataSource and dataPath but no
     * specified valuesManager object one will be automatically generated as part of the databinding process
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Setter for the {@link com.smartgwt.client.widgets.Canvas#getValuesManager valuesManager} attribute. This method may be called directly at  runtime to set the ValuesManager for a component; it has the same effect as calling  {@link com.smartgwt.client.widgets.form.ValuesManager#addMember ValuesManager.addMember()}, passing in this DataBoundComponent.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param valuesManager new dataPath. Default value is null
     * @see com.smartgwt.client.docs.GlobalId GlobalId 
     */
    public void setValuesManager(String valuesManager) {
        setAttribute("valuesManager", valuesManager, true);
    }

    /**
     * {@link com.smartgwt.client.widgets.form.ValuesManager} for managing values displayed in this component. If specified at
     * initialization time, this component will be added to the valuesManager via {@link
     * com.smartgwt.client.widgets.form.ValuesManager#addMember ValuesManager.addMember()}. <P> ValuesManagers allow different
     * fields of a single object to be displayed or edited across multiple UI components. Given a single values object, a
     * valuesManager will handle determining the appropriate field values for its member components and displaying them / 
     * responding to edits if the components support this. <P> Data may be derived simply from the specified fieldNames within
     * the member components, or for complex nested data structures can be specified by both component and field-level {@link
     * com.smartgwt.client.docs.DataPath}. <P> Note that components may be automatically bound to an existing valuesManager
     * attached to a  parent component if dataPath is specified. See {@link com.smartgwt.client.widgets.Canvas#getDataPath
     * dataPath} for more information. Also note that if a databound component has a specified dataSource and dataPath but no
     * specified valuesManager object one will be automatically generated as part of the databinding process
     *
     * @return Current valuesManager value. Default value is null
     * @see com.smartgwt.client.docs.GlobalId GlobalId 
     */
    public String getValuesManagerAsString()  {
        return getAttributeAsString("valuesManager");
    }
    

    /**
     * Controls widget visibility when the widget is initialized. See Visibility type for      details.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Sets this widget's visibility to "inherit", so that it becomes visible if all of its parents are visible or it has no parents. <P> If the widget has not yet been drawn (and doesn't have a parent or master), this method calls the draw method as well.
     *
     * @param visibility New visibility value. Default value is Canvas.INHERIT
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setVisibility(Visibility visibility) {
        setAttribute("visibility", visibility == null ? null : visibility.getValue(), true);
    }

    /**
     * Controls widget visibility when the widget is initialized. See Visibility type for      details.
     *
     * @return Returns true if the widget is visible, taking all parents into account, so that a widget which is not hidden might still
     * report itself as not visible if it is within a hidden parent. <P> NOTE: Undrawn widgets will report themselves as
     * visible if they would be visible if drawn. Default value is Canvas.INHERIT
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public Visibility getVisibility()  {
        return EnumUtil.getEnum(Visibility.values(), getAttribute("visibility"));
    }
    

    /**
     * Criteria to be evaluated to determine whether this Canvas should be visible.  Re-evaluated whenever data in the {@link
     * com.smartgwt.client.widgets.Canvas#getRuleScope ruleScope} changes. <P> A basic criteria uses textMatchStyle:"exact".
     * When specified in {@link com.smartgwt.client.docs.ComponentXML Component XML} this property allows {@link
     * com.smartgwt.client.docs.XmlCriteriaShorthand shorthand formats} for defining criteria.
     *
     * @param visibleWhen New visibleWhen value. Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.RuleCriteria RuleCriteria overview and related methods
     */
    public void setVisibleWhen(AdvancedCriteria visibleWhen)  throws IllegalStateException {
        if (visibleWhen instanceof Criterion) {
            visibleWhen.setAttribute("_constructor", "AdvancedCriteria");
        }
        setAttribute("visibleWhen", visibleWhen == null ? null : visibleWhen.getJsObj(), false);
    }

    /**
     * Criteria to be evaluated to determine whether this Canvas should be visible.  Re-evaluated whenever data in the {@link
     * com.smartgwt.client.widgets.Canvas#getRuleScope ruleScope} changes. <P> A basic criteria uses textMatchStyle:"exact".
     * When specified in {@link com.smartgwt.client.docs.ComponentXML Component XML} this property allows {@link
     * com.smartgwt.client.docs.XmlCriteriaShorthand shorthand formats} for defining criteria.
     *
     * @return Current visibleWhen value. Default value is null
     * @see com.smartgwt.client.docs.RuleCriteria RuleCriteria overview and related methods
     */
    public AdvancedCriteria getVisibleWhen()  {
        return new AdvancedCriteria(getAttributeAsJavaScriptObject("visibleWhen"));
    }
    

    /**
     * Size for this component's horizontal dimension. <P> Can be a number of pixels, or a percentage like "50%". Percentage
     * sizes are resolved to pixel values as follows: <UL> <LI>If a canvas has a specified {@link
     * com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, sizing will be     a percentage of the size of that
     * widget (see also {@link com.smartgwt.client.widgets.Canvas#getPercentBox percentBox}).</LI> <LI>Otherwise, if a canvas
     * has a {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}, and     {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is set for the widget, sizing will be a percentage of     the size
     * of that widget (see also {@link com.smartgwt.client.widgets.Canvas#getPercentBox percentBox}).</LI> <LI>Otherwise if
     * this is a child of some other canvas, percentages will be based on the     inner size of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas}'s viewport.</LI> <LI>Otherwise, for top level widgets,
     * sizing is calculated as a percentage of page size.</LI> </UL> Note that if a {@link
     * com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} or {@link com.smartgwt.client.widgets.Canvas#getMinWidth
     * minWidth} are specified (or {@link com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight} / {@link
     * com.smartgwt.client.widgets.Canvas#getMinHeight minHeight} for heights), these properties act as explicit pixel limits
     * on the canvas' size. For example, a canvas with {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} set to
     * <code>500</code>, and width specified as  "100%" will not render larger than 500 pixels in width even if there is more
     * space available in the parent canvas or percentSource. <P> {@link com.smartgwt.client.widgets.layout.Layout Layouts} may
     * specially interpret percentage sizes on their children, and also allow "*" as a size. <P> Note that if {@link
     * com.smartgwt.client.widgets.Canvas#getOverflow overflow} is set to "visible", this size is a minimum, and the component
     * may overflow to show all content and/or children. <P> If trying to establish a default width for a custom component, set
     * {@link com.smartgwt.client.widgets.Canvas#getDefaultWidth defaultWidth} instead.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Resizes the widget horizontally to the specified width (moves the right side of the widget). The width parameter can be expressed as a percentage of viewport size or as the number of pixels. <P> NOTE: if you're setting multiple coordinates, use resizeTo() or setRect() instead
     *
     * @param width new width. Default value is null
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setWidth(Integer width) {
        setAttribute("width", width, true);
    }

    /**
     * Size for this component's horizontal dimension. <P> Can be a number of pixels, or a percentage like "50%". Percentage
     * sizes are resolved to pixel values as follows: <UL> <LI>If a canvas has a specified {@link
     * com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, sizing will be     a percentage of the size of that
     * widget (see also {@link com.smartgwt.client.widgets.Canvas#getPercentBox percentBox}).</LI> <LI>Otherwise, if a canvas
     * has a {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}, and     {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is set for the widget, sizing will be a percentage of     the size
     * of that widget (see also {@link com.smartgwt.client.widgets.Canvas#getPercentBox percentBox}).</LI> <LI>Otherwise if
     * this is a child of some other canvas, percentages will be based on the     inner size of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas}'s viewport.</LI> <LI>Otherwise, for top level widgets,
     * sizing is calculated as a percentage of page size.</LI> </UL> Note that if a {@link
     * com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} or {@link com.smartgwt.client.widgets.Canvas#getMinWidth
     * minWidth} are specified (or {@link com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight} / {@link
     * com.smartgwt.client.widgets.Canvas#getMinHeight minHeight} for heights), these properties act as explicit pixel limits
     * on the canvas' size. For example, a canvas with {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} set to
     * <code>500</code>, and width specified as  "100%" will not render larger than 500 pixels in width even if there is more
     * space available in the parent canvas or percentSource. <P> {@link com.smartgwt.client.widgets.layout.Layout Layouts} may
     * specially interpret percentage sizes on their children, and also allow "*" as a size. <P> Note that if {@link
     * com.smartgwt.client.widgets.Canvas#getOverflow overflow} is set to "visible", this size is a minimum, and the component
     * may overflow to show all content and/or children. <P> If trying to establish a default width for a custom component, set
     * {@link com.smartgwt.client.widgets.Canvas#getDefaultWidth defaultWidth} instead.
     *
     * @return Return the width of this object, in pixels. Default value is null
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public Integer getWidth()  {
        return getAttributeAsInt("width");
    }

    /**
     * Size for this component's horizontal dimension. <P> Can be a number of pixels, or a percentage like "50%". Percentage
     * sizes are resolved to pixel values as follows: <UL> <LI>If a canvas has a specified {@link
     * com.smartgwt.client.widgets.Canvas#getPercentSource percentSource}, sizing will be     a percentage of the size of that
     * widget (see also {@link com.smartgwt.client.widgets.Canvas#getPercentBox percentBox}).</LI> <LI>Otherwise, if a canvas
     * has a {@link com.smartgwt.client.widgets.Canvas#getMasterCanvas master canvas}, and     {@link
     * com.smartgwt.client.widgets.Canvas#getSnapTo snapTo} is set for the widget, sizing will be a percentage of     the size
     * of that widget (see also {@link com.smartgwt.client.widgets.Canvas#getPercentBox percentBox}).</LI> <LI>Otherwise if
     * this is a child of some other canvas, percentages will be based on the     inner size of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent canvas}'s viewport.</LI> <LI>Otherwise, for top level widgets,
     * sizing is calculated as a percentage of page size.</LI> </UL> Note that if a {@link
     * com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} or {@link com.smartgwt.client.widgets.Canvas#getMinWidth
     * minWidth} are specified (or {@link com.smartgwt.client.widgets.Canvas#getMaxHeight maxHeight} / {@link
     * com.smartgwt.client.widgets.Canvas#getMinHeight minHeight} for heights), these properties act as explicit pixel limits
     * on the canvas' size. For example, a canvas with {@link com.smartgwt.client.widgets.Canvas#getMaxWidth maxWidth} set to
     * <code>500</code>, and width specified as  "100%" will not render larger than 500 pixels in width even if there is more
     * space available in the parent canvas or percentSource. <P> {@link com.smartgwt.client.widgets.layout.Layout Layouts} may
     * specially interpret percentage sizes on their children, and also allow "*" as a size. <P> Note that if {@link
     * com.smartgwt.client.widgets.Canvas#getOverflow overflow} is set to "visible", this size is a minimum, and the component
     * may overflow to show all content and/or children. <P> If trying to establish a default width for a custom component, set
     * {@link com.smartgwt.client.widgets.Canvas#getDefaultWidth defaultWidth} instead.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Resizes the widget horizontally to the specified width (moves the right side of the widget). The width parameter can be expressed as a percentage of viewport size or as the number of pixels. <P> NOTE: if you're setting multiple coordinates, use resizeTo() or setRect() instead
     *
     * @param width new width. Default value is null
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public void setWidth(String width) {
        setAttribute("width", width, true);
    }
    

    // ********************* Methods ***********************
	/**
     * Adds newChild as a child of this widget, set up a named object reference (i.e., this[name]) to the new widget if name
     * argument is provided, and draw the child if this widget has been drawn already. <P> If newChild has a parent it will be
     * removed from it. If it has a master, it will be detached from it if the master is a child of a different parent. If
     * newChild has peers, they'll be added to this widget as children as well.
     * @param newChild new child canvas to add
     *
     * @return the new child, or null if it couldn't be added
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Canvas addChild(Canvas newChild) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "addChild", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.addChild(newChild == null ? null : newChild.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

    /**
     * @see Canvas#addChild
     */
    public Canvas addChild(Canvas newChild, String name){
        return addChild(newChild, name, null);
    }

	/**
     * Adds newChild as a child of this widget, set up a named object reference (i.e., this[name]) to the new widget if name
     * argument is provided, and draw the child if this widget has been drawn already. <P> If newChild has a parent it will be
     * removed from it. If it has a master, it will be detached from it if the master is a child of a different parent. If
     * newChild has peers, they'll be added to this widget as children as well.
     * @param newChild new child canvas to add
     * @param name name to assign to child (eg: this[name] == child)
     * @param autoDraw if false, child will not automatically be drawn (only                                          for advanced use)
     *
     * @return the new child, or null if it couldn't be added
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Canvas addChild(Canvas newChild, String name, Boolean autoDraw) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "addChild", "Canvas,String,Boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.addChild(newChild == null ? null : newChild.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), name, autoDraw == null ? null : autoDraw.@java.lang.Boolean::booleanValue()());
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;
	
	/**
     * Adds newPeer as a peer of this widget (also making it a child of this widget's parent, if any), set up a named object
     * reference (i.e., this[name]) to the new widget if name is provided, and draw the peer if this widget has been drawn
     * already.<br> <P> The widget to be added as a peer will be removed from its old master and/or parent, if any, and it will
     * be added as a child to the parent of this canvas (if any)
     * @param newPeer new peer widget to add
     *
     * @return the new peer, or null if it couldn't be added
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Canvas addPeer(Canvas newPeer) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "addPeer", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.addPeer(newPeer == null ? null : newPeer.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

    /**
     * @see Canvas#addPeer
     */
    public Canvas addPeer(Canvas newPeer, String name){
        return addPeer(newPeer, name, null, null);
    }

    /**
     * @see Canvas#addPeer
     */
    public Canvas addPeer(Canvas newPeer, String name, Boolean autoDraw){
        return addPeer(newPeer, name, autoDraw, null);
    }

	/**
     * Adds newPeer as a peer of this widget (also making it a child of this widget's parent, if any), set up a named object
     * reference (i.e., this[name]) to the new widget if name is provided, and draw the peer if this widget has been drawn
     * already.<br> <P> The widget to be added as a peer will be removed from its old master and/or parent, if any, and it will
     * be added as a child to the parent of this canvas (if any)
     * @param newPeer new peer widget to add
     * @param name name to assign to peer (eg: this[peer] == child)
     * @param autoDraw if true, peer will not automatically be drawn (only                             for advanced use)
     * @param preDraw if true, when draw is called on the master widget, the peer                            will be drawn before the master
     *
     * @return the new peer, or null if it couldn't be added
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Canvas addPeer(Canvas newPeer, String name, Boolean autoDraw, Boolean preDraw) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "addPeer", "Canvas,String,Boolean,Boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.addPeer(newPeer == null ? null : newPeer.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), name, autoDraw == null ? null : autoDraw.@java.lang.Boolean::booleanValue()(), preDraw == null ? null : preDraw.@java.lang.Boolean::booleanValue()());
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;
	
	/**
     * Add a candidate to {@link com.smartgwt.client.widgets.Canvas#getSnapAlignCandidates snapAlignCandidates}.  Duplicates
     * are automatically avoided.
     * @param newCandidate 
     */
    public native void addSnapAlignCandidate(Canvas newCandidate) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "addSnapAlignCandidate", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.addSnapAlignCandidate(newCandidate == null ? null : newCandidate.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * This method tells a component to adjust for changes in the size of its content that happen  outside of Smart GWT APIs. 
     * This may include: <ol> <li> size changes due to "replaced" HTML elements (elements that may change size after their     
     * content loads, such as &lt;img&gt; tags with no sizes).  To avoid the need to call      
     * <code>adjustForContent()</code>, specify sizes on replaced elements wherever possible. </li> <li> modification of HTML
     * contained in a Canvas by direct manipulation of the DOM - see the       {@link com.smartgwt.client.docs.DomIntegration
     * DOM-level Integration overview}.  Note that only contents       supplied to a widget via {@link
     * com.smartgwt.client.widgets.Canvas#getContents contents} or via an override of       {@link
     * com.smartgwt.client.widgets.Canvas#getInnerHTML getInnerHTML()} should be manipulated directly. Contents automatically  
     * generated by Smart GWT components (such as the basic structure of a Button) should       never be manipulated: these
     * structures are considered internal, differ by platform,       and will change without notice. </li> </ol>
     * @param immediate By default the adjustment will occur on a small delay for performance reasons.  Pass in this parameter to force
     * immediate adjustment.
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native void adjustForContent(boolean immediate) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "adjustForContent", "boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.adjustForContent(immediate);
    }-*/;

	/**
     * Animate a change in opacity from the widget's current opacity to the specified opacity.
     * @param opacity desired final opacity
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_fade" target="examples">Fade Show & Hide Example</a>
     */
    public native void animateFade(Integer opacity) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateFade", "Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateFade(opacity == null ? null : opacity.@java.lang.Integer::intValue()());
    }-*/;

    /**
     * @see Canvas#animateFade
     */
    public void animateFade(Integer opacity, AnimationCallback callback){
        animateFade(opacity, callback, (Integer) null, null);
    }

    /**
     * @see Canvas#animateFade
     */
    public void animateFade(Integer opacity, AnimationCallback callback, Integer duration){
        animateFade(opacity, callback, duration, null);
    }

	/**
     * Animate a change in opacity from the widget's current opacity to the specified opacity.
     * @param opacity desired final opacity
     * @param callback When the fade completes this callback will be                       fired.  Single 'earlyFinish' parameter will be
     * passed if the                       animation was cut short, for example by a call to the non-animated                  
     *     API {@link com.smartgwt.client.widgets.Canvas#setOpacity setOpacity()}.
     * @param duration Duration in ms of the animated fade
     * @param acceleration Optional animation acceleration to bias the ratios
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_fade" target="examples">Fade Show & Hide Example</a>
     */
    public native void animateFade(Integer opacity, AnimationCallback callback, Integer duration, AnimationAcceleration acceleration) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateFade", "Integer,AnimationCallback,Integer,AnimationAcceleration");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateFade(opacity == null ? null : opacity.@java.lang.Integer::intValue()(), 
			$entry( function(earlyFinish) { 
				if(callback!=null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(
					earlyFinish == null ? false : earlyFinish
				);
			}), duration == null ? null : duration.@java.lang.Integer::intValue()(), acceleration == null ? null : acceleration.@com.smartgwt.client.types.AnimationAcceleration::getValue()());
    }-*/;
	
	/**
     * Animate a reposition of this canvas from its current position to the specified position
     * @param left new left position (or null for unchanged)
     * @param top new top position (or null for unchanged)
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_move" target="examples">Fly Onscreen Example</a>
     */
    public native void animateMove(Integer left, Integer top) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateMove", "Integer,Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateMove(left == null ? null : left.@java.lang.Integer::intValue()(), top == null ? null : top.@java.lang.Integer::intValue()());
    }-*/;

    /**
     * @see Canvas#animateMove
     */
    public void animateMove(Integer left, Integer top, AnimationCallback callback){
        animateMove(left, top, callback, (Integer) null, null);
    }

    /**
     * @see Canvas#animateMove
     */
    public void animateMove(Integer left, Integer top, AnimationCallback callback, Integer duration){
        animateMove(left, top, callback, duration, null);
    }

	/**
     * Animate a reposition of this canvas from its current position to the specified position
     * @param left new left position (or null for unchanged)
     * @param top new top position (or null for unchanged)
     * @param callback When the move completes this callback will be                       fired. Single 'earlyFinish' parameter will be passed
     * if the                       animation was cut short, for example by a call to the non-animated                      
     * APIs {@link com.smartgwt.client.widgets.Canvas#moveTo moveTo()} or {@link com.smartgwt.client.widgets.Canvas#moveBy
     * moveBy()}.
     * @param duration Duration in ms of the animated move
     * @param acceleration Optional acceleration effect to bias the ratios
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_move" target="examples">Fly Onscreen Example</a>
     */
    public native void animateMove(Integer left, Integer top, AnimationCallback callback, Integer duration, AnimationAcceleration acceleration) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateMove", "Integer,Integer,AnimationCallback,Integer,AnimationAcceleration");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateMove(left == null ? null : left.@java.lang.Integer::intValue()(), top == null ? null : top.@java.lang.Integer::intValue()(), 
			$entry( function(earlyFinish) { 
				if(callback!=null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(
					earlyFinish == null ? false : earlyFinish
				);
			}), duration == null ? null : duration.@java.lang.Integer::intValue()(), acceleration == null ? null : acceleration.@com.smartgwt.client.types.AnimationAcceleration::getValue()());
    }-*/;
	
	/**
     * Animate a reposition / resize of this canvas from its current size and position.
     * @param left new left position (or null for unchanged)
     * @param top new top position (or null for unchanged)
     * @param width new width (or null for unchanged)
     * @param height new height (or null for unchanged)
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_zoom" target="examples">Zoom & Shrink Example</a>
     */
    public native void animateRect(Integer left, Integer top, Integer width, Integer height) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateRect", "Integer,Integer,Integer,Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateRect(left == null ? null : left.@java.lang.Integer::intValue()(), top == null ? null : top.@java.lang.Integer::intValue()(), width == null ? null : width.@java.lang.Integer::intValue()(), height == null ? null : height.@java.lang.Integer::intValue()());
    }-*/;

    /**
     * @see Canvas#animateRect
     */
    public void animateRect(Integer left, Integer top, Integer width, Integer height, AnimationCallback callback){
        animateRect(left, top, width, height, callback, (Integer) null, null);
    }

    /**
     * @see Canvas#animateRect
     */
    public void animateRect(Integer left, Integer top, Integer width, Integer height, AnimationCallback callback, Integer duration){
        animateRect(left, top, width, height, callback, duration, null);
    }

	/**
     * Animate a reposition / resize of this canvas from its current size and position.
     * @param left new left position (or null for unchanged)
     * @param top new top position (or null for unchanged)
     * @param width new width (or null for unchanged)
     * @param height new height (or null for unchanged)
     * @param callback When the setRect completes this callback will be                       fired. Single 'earlyFinish' parameter will be
     * passed if the                       animation was cut short, for example by a call to the non-animated                  
     *     API {@link com.smartgwt.client.widgets.Canvas#setRect setRect()}.
     * @param duration Duration in ms of the animated setRect
     * @param acceleration Optional acceleration effect to apply to the animation
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_zoom" target="examples">Zoom & Shrink Example</a>
     */
    public native void animateRect(Integer left, Integer top, Integer width, Integer height, AnimationCallback callback, Integer duration, AnimationAcceleration acceleration) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateRect", "Integer,Integer,Integer,Integer,AnimationCallback,Integer,AnimationAcceleration");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateRect(left == null ? null : left.@java.lang.Integer::intValue()(), top == null ? null : top.@java.lang.Integer::intValue()(), width == null ? null : width.@java.lang.Integer::intValue()(), height == null ? null : height.@java.lang.Integer::intValue()(), 
			$entry( function(earlyFinish) { 
				if(callback!=null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(
					earlyFinish == null ? false : earlyFinish
				);
			}), duration == null ? null : duration.@java.lang.Integer::intValue()(), acceleration == null ? null : acceleration.@com.smartgwt.client.types.AnimationAcceleration::getValue()());
    }-*/;
	
	/**
     * Animate a resize of this canvas from its current size to the specified size
     * @param width new width (or null for unchanged)
     * @param height new height (or null for unchanged)
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_resize" target="examples">Resize Example</a>
     */
    public native void animateResize(Integer width, Integer height) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateResize", "Integer,Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateResize(width == null ? null : width.@java.lang.Integer::intValue()(), height == null ? null : height.@java.lang.Integer::intValue()());
    }-*/;

    /**
     * @see Canvas#animateResize
     */
    public void animateResize(Integer width, Integer height, AnimationCallback callback){
        animateResize(width, height, callback, (Integer) null, null);
    }

    /**
     * @see Canvas#animateResize
     */
    public void animateResize(Integer width, Integer height, AnimationCallback callback, Integer duration){
        animateResize(width, height, callback, duration, null);
    }

	/**
     * Animate a resize of this canvas from its current size to the specified size
     * @param width new width (or null for unchanged)
     * @param height new height (or null for unchanged)
     * @param callback When the resize completes this callback will be                       fired. Single 'earlyFinish' parameter will be
     * passed if the                       animation was cut short, for example by a call to the non-animated                  
     * APIs {@link com.smartgwt.client.widgets.Canvas#resizeTo resizeTo()} or {@link
     * com.smartgwt.client.widgets.Canvas#resizeBy resizeBy()}.
     * @param duration Duration in ms of the animated resize
     * @param acceleration Optional acceleration effect to apply to the resize
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#effects_animation_resize" target="examples">Resize Example</a>
     */
    public native void animateResize(Integer width, Integer height, AnimationCallback callback, Integer duration, AnimationAcceleration acceleration) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateResize", "Integer,Integer,AnimationCallback,Integer,AnimationAcceleration");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateResize(width == null ? null : width.@java.lang.Integer::intValue()(), height == null ? null : height.@java.lang.Integer::intValue()(), 
			$entry( function(earlyFinish) { 
				if(callback!=null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(
					earlyFinish == null ? false : earlyFinish
				);
			}), duration == null ? null : duration.@java.lang.Integer::intValue()(), acceleration == null ? null : acceleration.@com.smartgwt.client.types.AnimationAcceleration::getValue()());
    }-*/;
	
	/**
     * Animate a scroll from the current scroll position to the specified position.
     * @param scrollLeft desired final left scroll position
     * @param scrollTop desired final top scroll position
     */
    public native void animateScroll(Integer scrollLeft, Integer scrollTop) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateScroll", "Integer,Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateScroll(scrollLeft == null ? null : scrollLeft.@java.lang.Integer::intValue()(), scrollTop == null ? null : scrollTop.@java.lang.Integer::intValue()());
    }-*/;

    /**
     * @see Canvas#animateScroll
     */
    public void animateScroll(Integer scrollLeft, Integer scrollTop, AnimationCallback callback){
        animateScroll(scrollLeft, scrollTop, callback, (Integer) null, null);
    }

    /**
     * @see Canvas#animateScroll
     */
    public void animateScroll(Integer scrollLeft, Integer scrollTop, AnimationCallback callback, Integer duration){
        animateScroll(scrollLeft, scrollTop, callback, duration, null);
    }

	/**
     * Animate a scroll from the current scroll position to the specified position.
     * @param scrollLeft desired final left scroll position
     * @param scrollTop desired final top scroll position
     * @param callback When the scroll completes this callback will be                       fired. Single 'earlyFinish' parameter will be
     * passed if the                       animation was cut short, for example by a call to the non-animated                  
     * APIs {@link com.smartgwt.client.widgets.Canvas#scrollTo scrollTo()} or {@link
     * com.smartgwt.client.widgets.Canvas#scrollBy scrollBy()}.
     * @param duration Duration in ms of the animated scroll
     * @param acceleration Optional acceleration to bias the animation ratios
     */
    public native void animateScroll(Integer scrollLeft, Integer scrollTop, AnimationCallback callback, Integer duration, AnimationAcceleration acceleration) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "animateScroll", "Integer,Integer,AnimationCallback,Integer,AnimationAcceleration");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateScroll(scrollLeft == null ? null : scrollLeft.@java.lang.Integer::intValue()(), scrollTop == null ? null : scrollTop.@java.lang.Integer::intValue()(), 
			$entry( function(earlyFinish) { 
				if(callback!=null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(
					earlyFinish == null ? false : earlyFinish
				);
			}), duration == null ? null : duration.@java.lang.Integer::intValue()(), acceleration == null ? null : acceleration.@com.smartgwt.client.types.AnimationAcceleration::getValue()());
    }-*/;
	
	/**
     * If this canvas has keyboard focus, blur it. After this method, the canvas will no longer appear focused and will stop
     * receiving keyboard events.
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public native void blur() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "blur", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.blur();
    }-*/;

	/**
     * Puts this widget at the top of the stacking order, so it appears in front of all other widgets in the same parent.
     * @see com.smartgwt.client.docs.ZIndex ZIndex overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_layer" target="examples">Layer Example</a>
     */
    public native void bringToFront() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "bringToFront", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.bringToFront();
    }-*/;

	/**
     * Remove all visual representation of a Canvas, including all child or member Canvases, or managed top-level components
     * such as the ListGrid drop location indicator. <P> This is more expensive than hide(), because in order to become visible
     * again, the Canvas must be draw()n again.  Generally, application code has no reason to call clear() unless it is
     * attempting to do advanced memory management.  If you want to temporarily hide a Canvas, use hide() and show(), and if
     * you want to permanently destroy a Canvas, use {@link com.smartgwt.client.widgets.Canvas#destroy destroy()}. <P> You
     * would only use clear() if you were managing a very large pool of components and you wanted to reclaim some of the memory
     * used by components that had not been used in a while, while still being able to just draw() them to make them active and
     * visible again. <P> Note: a clear() will happen as part of moving a Canvas to a different parent.  See {@link
     * com.smartgwt.client.widgets.Canvas#addChild addChild()}.
     */
    public native void clear() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "clear", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.clear();
    }-*/;

	/**
     * If an explicit {@link com.smartgwt.client.widgets.Canvas#getTabIndex tabIndex} was assigned to this widget, clear it.
     * This will enable automatic tab index managment behaviors via the {@link com.smartgwt.client.widgets.TabIndexManager}
     * class as described in the {@link com.smartgwt.client.docs.TabOrderOverview}
     */
    public native void clearExplicitTabIndex() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "clearExplicitTabIndex", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.clearExplicitTabIndex();
    }-*/;

    /**
     * Add a click handler.
     * <p>
     * Executed when the left mouse is clicked (pressed and then released) on this widget.  No default implementation.
     *
     * @param handler the click handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addClickHandler(com.smartgwt.client.widgets.events.ClickHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.ClickEvent.getType()) == 0) setupClickEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.ClickEvent.getType());
    }

    private native void setupClickEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var click = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.ClickEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("click"));
            obj.addProperties({click: 
                function () {
                    var param = {"_this": this};
                    var ret = click(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("click", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("click"));
            obj.click = 
                function () {
                    var param = {"_this": this};
                    var ret = click(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("click", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

	/**
     * Determines whether a clickmask is showing
     *
     * @return whether or not a clickmask is showing
     * @see com.smartgwt.client.widgets.Canvas#showClickMask
     */
    public native Boolean clickMaskUp() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "clickMaskUp", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.clickMaskUp();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Determines whether a clickmask is showing
     * @param ID optional ID of specific clickMask to check. If not passed,                      checks for the click mask associated
     * with this widget only.
     *
     * @return whether or not a clickmask is showing
     * @see com.smartgwt.client.widgets.Canvas#showClickMask
     */
    public native Boolean clickMaskUp(String ID) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "clickMaskUp", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.clickMaskUp(ID);
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;
	
	/**
     * Returns true if element is a descendant of this widget (i.e., exists below this widget in      the containment
     * hierarchy); and false otherwise.
     * @param canvas the canvas to be tested
     *
     * @return true if specified element is a descendant of this canvas; false otherwise
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Boolean contains(Canvas canvas) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "contains", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.contains(canvas == null ? null : canvas.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Returns true if element is a descendant of this widget (i.e., exists below this widget in      the containment
     * hierarchy); and false otherwise.
     * @param canvas the canvas to be tested
     * @param testSelf If passed this method will return true if the canvas                               parameter is a pointer to this
     * widget.
     *
     * @return true if specified element is a descendant of this canvas; false otherwise
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Boolean contains(Canvas canvas, Boolean testSelf) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "contains", "Canvas,Boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.contains(canvas == null ? null : canvas.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), testSelf == null ? null : testSelf.@java.lang.Boolean::booleanValue()());
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;
	
	/**
     * Return true if the last event's mouse coordinates are within the bounds of this component.      NOTE: Z-ordering is not
     * considered for the purposes of this test.  If the coordinate you're      testing is occluded by other component, but the
     * X,Y coordinates are still within the bounds      of that component, this method will return true.
     *
     * @return true if the event occurred within the bounds of this component
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native Boolean containsEvent() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "containsEvent", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.containsEvent();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Returns true if the keyboard focus is in this Canvas or any child of this Canvas.
     *
     * @return whether this Canvas contains the keyboard focus
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public native Boolean containsFocus() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "containsFocus", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.containsFocus();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Return whether or not this object contains the specified global (x,y) coordinates. <P> Will return false if any parent
     * canvas does not contain the specified point, (EG: you're hovering over an element's absolute location, but it is
     * scrolled out of view in a parent element)
     * @param x GLOBAL x-coordinate
     * @param y GLOBAL y-coordinate
     *
     * @return true if this object contains the specified point; false otherwise
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native Boolean containsPoint(int x, int y) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "containsPoint", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.containsPoint(x, y);
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Return whether or not this object contains the specified global (x,y) coordinates. <P> Will return false if any parent
     * canvas does not contain the specified point, (EG: you're hovering over an element's absolute location, but it is
     * scrolled out of view in a parent element)
     * @param x GLOBAL x-coordinate
     * @param y GLOBAL y-coordinate
     * @param withinViewport point lies specifically within our viewport                                              (drawn area excluding margins
     * and scrollbars if                                              present)
     *
     * @return true if this object contains the specified point; false otherwise
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native Boolean containsPoint(int x, int y, Boolean withinViewport) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "containsPoint", "int,int,Boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.containsPoint(x, y, withinViewport == null ? null : withinViewport.@java.lang.Boolean::booleanValue()());
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;
	
	/**
     * Remove this canvas from its parent if it has one.
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native void deparent() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "deparent", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.deparent();
    }-*/;

	/**
     * Make this Canvas no longer a peer of its master
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native void depeer() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "depeer", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.depeer();
    }-*/;

	/**
     * Disables this widget and any children and peers of this widget.
     * @see com.smartgwt.client.docs.Enable Enable overview and related methods
     */
    public native void disable() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "disable", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.disable();
    }-*/;

    /**
     * Add a doubleClick handler.
     * <p>
     * Executed when the left mouse button is clicked twice in rapid succession (within {@link
     * com.smartgwt.client.widgets.Canvas#getDoubleClickDelay Canvas.doubleClickDelay} by default) in this object.
     *
     * @param handler the doubleClick handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDoubleClickHandler(com.smartgwt.client.widgets.events.DoubleClickHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DoubleClickEvent.getType()) == 0) setupDoubleClickEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DoubleClickEvent.getType());
    }

    private native void setupDoubleClickEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var doubleClick = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DoubleClickEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("doubleClick"));
            obj.addProperties({doubleClick: 
                function () {
                    var param = {"_this": this};
                    var ret = doubleClick(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("doubleClick", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("doubleClick"));
            obj.doubleClick = 
                function () {
                    var param = {"_this": this};
                    var ret = doubleClick(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("doubleClick", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragMove handler.
     * <p>
     * Executed every time the mouse moves while dragging this canvas.
     *
     * @param handler the dragMove handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragMoveHandler(com.smartgwt.client.widgets.events.DragMoveHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragMoveEvent.getType()) == 0) setupDragMoveEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragMoveEvent.getType());
    }

    private native void setupDragMoveEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragMove = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragMoveEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragMove"));
            obj.addProperties({dragMove: 
                function () {
                    var param = {"_this": this};
                    var ret = dragMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragMove", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragMove"));
            obj.dragMove = 
                function () {
                    var param = {"_this": this};
                    var ret = dragMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragMove", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragRepositionMove handler.
     * <p>
     * Executed every time the mouse moves while drag-repositioning. If this method does not Call {@link
     * com.smartgwt.client.widgets.events.DragRepositionMoveEvent#cancel()} from within {@link
     * com.smartgwt.client.widgets.events.DragRepositionMoveHandler#onDragRepositionMove}, the {@link
     * com.smartgwt.client.widgets.Canvas#getDragTarget Canvas.dragTarget} (or outline if {@link
     * com.smartgwt.client.widgets.Canvas#getDragAppearance Canvas.dragAppearance} is set to "outline") will automatically be
     * moved as appropriate whenever the mouse moves.
     *
     * @param handler the dragRepositionMove handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragRepositionMoveHandler(com.smartgwt.client.widgets.events.DragRepositionMoveHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragRepositionMoveEvent.getType()) == 0) setupDragRepositionMoveEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragRepositionMoveEvent.getType());
    }

    private native void setupDragRepositionMoveEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragRepositionMove = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragRepositionMoveEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragRepositionMove"));
            obj.addProperties({dragRepositionMove: 
                function () {
                    var param = {"_this": this};
                    var ret = dragRepositionMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragRepositionMove", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragRepositionMove"));
            obj.dragRepositionMove = 
                function () {
                    var param = {"_this": this};
                    var ret = dragRepositionMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragRepositionMove", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragRepositionStart handler.
     * <p>
     * Executed when dragging first starts. No default implementation.  Create this handler to set things up for the drag
     * reposition.
     *
     * @param handler the dragRepositionStart handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragRepositionStartHandler(com.smartgwt.client.widgets.events.DragRepositionStartHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragRepositionStartEvent.getType()) == 0) setupDragRepositionStartEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragRepositionStartEvent.getType());
    }

    private native void setupDragRepositionStartEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragRepositionStart = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragRepositionStartEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragRepositionStart"));
            obj.addProperties({dragRepositionStart: 
                function () {
                    var param = {"_this": this};
                    var ret = dragRepositionStart(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragRepositionStart", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragRepositionStart"));
            obj.dragRepositionStart = 
                function () {
                    var param = {"_this": this};
                    var ret = dragRepositionStart(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragRepositionStart", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragRepositionStop handler.
     * <p>
     * Executed when the mouse button is released at the end of the drag. Your widget can use this opportunity to fire custom
     * code based upon where the mouse button was released, etc. <p> Returning true from this handler will cause the {@link
     * com.smartgwt.client.widgets.Canvas#getDragTarget Canvas.dragTarget} (or outline if {@link
     * com.smartgwt.client.widgets.Canvas#getDragAppearance Canvas.dragAppearance} is set to "outline") to be left in its
     * current location. Returning false from this handler will cause it to snap back to its original location.
     *
     * @param handler the dragRepositionStop handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragRepositionStopHandler(com.smartgwt.client.widgets.events.DragRepositionStopHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragRepositionStopEvent.getType()) == 0) setupDragRepositionStopEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragRepositionStopEvent.getType());
    }

    private native void setupDragRepositionStopEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragRepositionStop = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragRepositionStopEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragRepositionStop"));
            obj.addProperties({dragRepositionStop: 
                function () {
                    var param = {"_this": this};
                    var ret = dragRepositionStop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragRepositionStop", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragRepositionStop"));
            obj.dragRepositionStop = 
                function () {
                    var param = {"_this": this};
                    var ret = dragRepositionStop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragRepositionStop", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragResizeMove handler.
     * <p>
     * Executed every time the mouse moves while drag-resizing. If this method does not Call {@link
     * com.smartgwt.client.widgets.events.DragResizeMoveEvent#cancel()} from within {@link
     * com.smartgwt.client.widgets.events.DragResizeMoveHandler#onDragResizeMove}, the {@link
     * com.smartgwt.client.widgets.Canvas#getDragTarget Canvas.dragTarget} (or outline if {@link
     * com.smartgwt.client.widgets.Canvas#getDragAppearance Canvas.dragAppearance} is set to "outline") will automatically be
     * moved as appropriate whenever the mouse moves.
     *
     * @param handler the dragResizeMove handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragResizeMoveHandler(com.smartgwt.client.widgets.events.DragResizeMoveHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragResizeMoveEvent.getType()) == 0) setupDragResizeMoveEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragResizeMoveEvent.getType());
    }

    private native void setupDragResizeMoveEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragResizeMove = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragResizeMoveEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragResizeMove"));
            obj.addProperties({dragResizeMove: 
                function () {
                    var param = {"_this": this};
                    var ret = dragResizeMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragResizeMove", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragResizeMove"));
            obj.dragResizeMove = 
                function () {
                    var param = {"_this": this};
                    var ret = dragResizeMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragResizeMove", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragResizeStart handler.
     * <p>
     * Executed when resize dragging first starts. No default implementation. Create this handler to set things up for the drag
     * resize.
     *
     * @param handler the dragResizeStart handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragResizeStartHandler(com.smartgwt.client.widgets.events.DragResizeStartHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragResizeStartEvent.getType()) == 0) setupDragResizeStartEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragResizeStartEvent.getType());
    }

    private native void setupDragResizeStartEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragResizeStart = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragResizeStartEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragResizeStart"));
            obj.addProperties({dragResizeStart: 
                function () {
                    var param = {"_this": this};
                    var ret = dragResizeStart(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragResizeStart", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragResizeStart"));
            obj.dragResizeStart = 
                function () {
                    var param = {"_this": this};
                    var ret = dragResizeStart(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragResizeStart", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragResizeStop handler.
     * <p>
     * Executed when the mouse button is released at the end of the drag resize. Your widget can use this opportunity to fire
     * custom code based upon where the mouse button was released, etc. <p> Returning true from this handler will cause the
     * {@link com.smartgwt.client.widgets.Canvas#getDragTarget Canvas.dragTarget} (or outline if {@link
     * com.smartgwt.client.widgets.Canvas#getDragAppearance Canvas.dragAppearance} is set to "outline") to be left at its
     * current size. Returning false from this handler will cause it to snap back to its original location size
     *
     * @param handler the dragResizeStop handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragResizeStopHandler(com.smartgwt.client.widgets.events.DragResizeStopHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragResizeStopEvent.getType()) == 0) setupDragResizeStopEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragResizeStopEvent.getType());
    }

    private native void setupDragResizeStopEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragResizeStop = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragResizeStopEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragResizeStop"));
            obj.addProperties({dragResizeStop: 
                function () {
                    var param = {"_this": this};
                    var ret = dragResizeStop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragResizeStop", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragResizeStop"));
            obj.dragResizeStop = 
                function () {
                    var param = {"_this": this};
                    var ret = dragResizeStop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragResizeStop", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragStart handler.
     * <p>
     * Executed when dragging first starts. Your widget can use this opportunity to set things up for the drag, such as setting
     * the drag tracker. Returning false from this event handler will cancel the drag action entirely. <p> A drag action is
     * considered to be begin when the mouse has moved {@link com.smartgwt.client.widgets.Canvas#getDragStartDistance
     * Canvas.dragStartDistance} with the left mouse down.
     *
     * @param handler the dragStart handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragStartHandler(com.smartgwt.client.widgets.events.DragStartHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragStartEvent.getType()) == 0) setupDragStartEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragStartEvent.getType());
    }

    private native void setupDragStartEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragStart = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragStartEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragStart"));
            obj.addProperties({dragStart: 
                function () {
                    var param = {"_this": this};
                    var ret = dragStart(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragStart", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragStart"));
            obj.dragStart = 
                function () {
                    var param = {"_this": this};
                    var ret = dragStart(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragStart", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dragStop handler.
     * <p>
     * Executed when the mouse button is released at the end of the drag. Your widget can use this opportunity to fire code
     * based on the last location of the drag or reset any visual state that was sent.
     *
     * @param handler the dragStop handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDragStopHandler(com.smartgwt.client.widgets.events.DragStopHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DragStopEvent.getType()) == 0) setupDragStopEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DragStopEvent.getType());
    }

    private native void setupDragStopEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dragStop = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DragStopEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dragStop"));
            obj.addProperties({dragStop: 
                function () {
                    var param = {"_this": this};
                    var ret = dragStop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragStop", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dragStop"));
            obj.dragStop = 
                function () {
                    var param = {"_this": this};
                    var ret = dragStop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dragStop", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dropMove handler.
     * <p>
     * Executed whenever the compatible dragged object is moved over this drop target. You can use this to show a custom visual
     * indication of where the drop would occur within the widget, or to show the {@link
     * com.smartgwt.client.widgets.Canvas#getNoDropCursor no-drop cursor} to indicate that this is not a valid drop target,
     * typically if {@link com.smartgwt.client.widgets.Canvas#willAcceptDrop Canvas.willAcceptDrop()} returns false. <P> For
     * details on showing a 'no drop' cursor when the user drags over all invalid drop targets, see {@link
     * com.smartgwt.client.util.EventHandler#showNoDropIndicator showNoDropIndicator}.
     *
     * @param handler the dropMove handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDropMoveHandler(com.smartgwt.client.widgets.events.DropMoveHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DropMoveEvent.getType()) == 0) setupDropMoveEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DropMoveEvent.getType());
    }

    private native void setupDropMoveEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dropMove = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DropMoveEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dropMove"));
            obj.addProperties({dropMove: 
                function () {
                    var param = {"_this": this};
                    var ret = dropMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dropMove", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dropMove"));
            obj.dropMove = 
                function () {
                    var param = {"_this": this};
                    var ret = dropMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dropMove", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dropOut handler.
     * <p>
     * Executed when the dragged object is no longer over this drop target, including when the drag interaction is ending with
     * a drop on this drop target. If you have set a visual indication  in dropOver or dropMove, you should reset it to its
     * normal state in dropOut.
     *
     * @param handler the dropOut handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDropOutHandler(com.smartgwt.client.widgets.events.DropOutHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DropOutEvent.getType()) == 0) setupDropOutEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DropOutEvent.getType());
    }

    private native void setupDropOutEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dropOut = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DropOutEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dropOut"));
            obj.addProperties({dropOut: 
                function () {
                    var param = {"_this": this};
                    var ret = dropOut(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dropOut", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dropOut"));
            obj.dropOut = 
                function () {
                    var param = {"_this": this};
                    var ret = dropOut(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dropOut", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a dropOver handler.
     * <p>
     * Executed when the compatible dragged object is first moved over this drop target. Your implementation can use this to
     * show a custom visual indication that the object can be dropped here.
     *
     * @param handler the dropOver handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDropOverHandler(com.smartgwt.client.widgets.events.DropOverHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DropOverEvent.getType()) == 0) setupDropOverEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DropOverEvent.getType());
    }

    private native void setupDropOverEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var dropOver = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DropOverEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("dropOver"));
            obj.addProperties({dropOver: 
                function () {
                    var param = {"_this": this};
                    var ret = dropOver(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dropOver", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("dropOver"));
            obj.dropOver = 
                function () {
                    var param = {"_this": this};
                    var ret = dropOver(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("dropOver", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

	/**
     * Enables this widget and any children / peers of this widget.
     * @see com.smartgwt.client.docs.Enable Enable overview and related methods
     */
    public native void enable() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "enable", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.enable();
    }-*/;

	/**
     * Returns true if the rectangle of this widget encloses the rectangle of the specified widget.
     * @param other other canvas to test for enclosure
     *
     * @return true if this canvas encloses other; false otherwise
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native Boolean encloses(Canvas other) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "encloses", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.encloses(other == null ? null : other.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * If this canvas can accept focus, give it keyboard focus. After this method, the canvas will appear focused and will
     * receive keyboard events.
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public native void focus() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "focus", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.focus();
    }-*/;

	/**
     * Shifts focus to the next focusable element after this one, skipping any elements nested inside the tabbing group for
     * this canvas, such as focusable children. <P> This method makes use of the {@link
     * com.smartgwt.client.widgets.TabIndexManager#shiftFocusAfterGroup TabIndexManager.shiftFocusAfterGroup()} method to
     * request focus be changed to the next registered entry. By default standard focusable  Smart GWT UI elements, including
     * Canvases, FormItems, FormItemIcons, etc are registered with the TabIndexManager in the appropriate order, and will
     * accept focus if {@link com.smartgwt.client.widgets.Canvas#getCanFocus focusable}, and not {@link
     * com.smartgwt.client.widgets.form.fields.FormItem#getDisabled disabled} or  {@link
     * com.smartgwt.client.widgets.Canvas#showClickMask masked}.  <P> This method differs from {@link
     * com.smartgwt.client.widgets.Canvas#focusInNextTabElement focusInNextTabElement()} in that it will skip any descendants
     * of this widget in the TabIndexManager's hierarchy of potential focus target. By default this means focus will be moved
     * to the next target on the page which is not a descendant of this widget. <P> FormItems support a similar method: {@link
     * com.smartgwt.client.widgets.form.fields.FormItem#focusAfterItem FormItem.focusAfterItem()}. <P> <b>NOTE: </b>Focusable
     * elements created directly in the raw HTML bootstrap or  by application code will not be passed focus by this method
     * unless they have also been explicitly registered with the TabIndexManager. See the {@link
     * com.smartgwt.client.docs.TabOrderOverview} for more information.
     * @see com.smartgwt.client.widgets.Canvas#getTabIndex
     * @see com.smartgwt.client.widgets.Canvas#focusInNextTabElement
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public native void focusAfterGroup() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "focusAfterGroup", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.focusAfterGroup();
    }-*/;

	/**
     * Shifts focus to the start or end of this canvas and its descendants. <P> This method makes use of the {@link
     * com.smartgwt.client.widgets.TabIndexManager#shiftFocusWithinGroup TabIndexManager.shiftFocusWithinGroup()} API to
     * request focus be changed within the set of focusable targets registered under this canvas. <P> If the <code>start</code>
     * parameter is true, if the canvas itself is focusable it will be given focus, otherwise the first focusable descendant
     * will be given focus. If the <code>start</code> parameter is false, the last focusable descendant will be given focus (or
     * if the canvas itself is focusable but there are no focusable descendants,  it will receive focus). <P> A use case for
     * this might be to programmatically shift focus to the first or last button in a toolbar or similar.
     * @param start Should we focus at the start or the end of this widget and its   descendants.
     */
    public native void focusAtEnd(boolean start) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "focusAtEnd", "boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.focusAtEnd(start);
    }-*/;

    /**
     * Add a focusChanged handler.
     * <p>
     * Notification function fired when this widget receives or loses keyboard focus.
     *
     * @param handler the focusChanged handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addFocusChangedHandler(com.smartgwt.client.widgets.events.FocusChangedHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.FocusChangedEvent.getType()) == 0) setupFocusChangedEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.FocusChangedEvent.getType());
    }

    private native void setupFocusChangedEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var focusChanged = $entry(function(){
            var param = {"_this": this, "hasFocus" : arguments[0]};
            var event = @com.smartgwt.client.widgets.events.FocusChangedEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("focusChanged", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("focusChanged"));
            obj.addProperties({focusChanged:  focusChanged              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("focusChanged"));
            obj.focusChanged =  focusChanged             ;
        }
   }-*/;

	/**
     * Shifts focus to the next focusable element after this one.  This programatically  simulates the user experience of a Tab
     * keypress, and is used automatically when  a blocking {@link com.smartgwt.client.widgets.Canvas#showClickMask clickMask}
     * is up to ensure focus does not move to any masked elements in the UI. <P> This method makes use of the {@link
     * com.smartgwt.client.widgets.TabIndexManager#shiftFocus TabIndexManager.shiftFocus()} method to request focus be changed
     * to the adjacent registered entry. By default standard focusable  Smart GWT UI elements, including Canvases, FormItems,
     * FormItemIcons, etc are registered with the TabIndexManager in the appropriate order, and will accept focus if {@link
     * com.smartgwt.client.widgets.Canvas#getCanFocus focusable}, and not {@link
     * com.smartgwt.client.widgets.form.fields.FormItem#getDisabled disabled} or  {@link
     * com.smartgwt.client.widgets.Canvas#showClickMask masked}.  <P> The TabIndexManager maintains a hierarchy of focusable
     * targets - so if a parent canvas contains focusable children, they will typically be nested under the parent canvas in
     * this hierarchy. If you want to shift focus to the next target outside this hierarchy (IE: skip any children and put
     * focus into the next widget on the page outside this one), you can use use {@link
     * com.smartgwt.client.widgets.Canvas#focusAfterGroup focusAfterGroup()}. <P> <b>NOTE: </b>Focusable elements created
     * directly in the raw HTML bootstrap or  by application code will not be passed focus by this method unless they have also
     * been explicitly registered with the TabIndexManager. See the {@link com.smartgwt.client.docs.TabOrderOverview} for more
     * information.
     * @see com.smartgwt.client.widgets.Canvas#getTabIndex
     * @see com.smartgwt.client.widgets.Canvas#focusInPreviousTabElement
     * @see com.smartgwt.client.widgets.Canvas#focusAfterGroup
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public native void focusInNextTabElement() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "focusInNextTabElement", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.focusInNextTabElement();
    }-*/;

	/**
     * Shifts focus to the previous focusable element before this one.  This programatically  simulates the user experience of
     * a Shift+Tab keypress. <P> This method makes use of the {@link com.smartgwt.client.widgets.TabIndexManager#shiftFocus
     * TabIndexManager.shiftFocus()} method to request focus be changed to the adjacent registered entry. By default standard
     * focusable  Smart GWT UI elements, including Canvases, FormItems, FormItemIcons, etc are registered with the
     * TabIndexManager in the appropriate order, and will accept focus if {@link com.smartgwt.client.widgets.Canvas#getCanFocus
     * focusable}, and not {@link com.smartgwt.client.widgets.form.fields.FormItem#getDisabled disabled} or  {@link
     * com.smartgwt.client.widgets.Canvas#showClickMask masked}.  <P> <b>NOTE: </b>Focusable elements created directly in the
     * raw HTML bootstrap or  by application code will not be passed focus by this method unless they have also been explicitly
     * registered with the TabIndexManager. See the {@link com.smartgwt.client.docs.TabOrderOverview} for more information.
     * @see com.smartgwt.client.widgets.Canvas#getTabIndex
     * @see com.smartgwt.client.widgets.Canvas#focusInNextTabElement
     * @see com.smartgwt.client.docs.Focus Focus overview and related methods
     */
    public native void focusInPreviousTabElement() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "focusInPreviousTabElement", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.focusInPreviousTabElement();
    }-*/;

	/**
     * Return the bottom coordinate of this object as rendered, relative to its enclosing context, in pixels.
     *
     * @return bottom coordinate
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getBottom() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getBottom", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getBottom();
        return ret;
    }-*/;

	/**
     * For a given child widget where {@link com.smartgwt.client.widgets.Canvas#getUpdateTabPositionOnReparent
     * updateTabPositionOnReparent} is true,  return the expected tab position within this parent. Default implementation will
     * any explicit relative tab position specified by  {@link com.smartgwt.client.widgets.Canvas#setRelativeTabPosition
     * setRelativeTabPosition()}, and otherwise put children in the  same order as defined in the children array <P> Overridden
     * in the {@link com.smartgwt.client.widgets.layout.Layout} class to return the position in the members array.
     * @param child Child to get local tab position for
     *
     * @return tab position of child within this parent
     */
    public native Integer getChildTabPosition(Canvas child) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getChildTabPosition", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getChildTabPosition(child == null ? null : child.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toInteger(I)(ret);
    }-*/;

	/**
     * Returns the DOM element for this Canvas which contains the {@link com.smartgwt.client.widgets.Canvas#getContents
     * contents}, or for {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parent components}, the DOM elements for any
     * drawn children. This method is provided for the  very rare cases where a programmer needs to examine the DOM hierarchy
     * created by a drawn Smart GWT component. <P> Direct manipulation of the DOM elements created by Smart GWT components is
     * not supported. Smart GWT components should be rendered or cleared using standard methods such as {@link
     * com.smartgwt.client.widgets.Canvas#draw draw()}, {@link com.smartgwt.client.widgets.Canvas#clear clear()}. If direct
     * integration with existing DOM structures is required, this should be achieved via the {@link
     * com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} attribute, rather than by attempting to move the
     * component's outer element via native browser APIs.<br> The content of Smart GWT components' DOM elements should also not
     * be directly  manipulated using native browser APIs - standard methods such as  {@link
     * com.smartgwt.client.widgets.Canvas#setContents setContents()}, {@link com.smartgwt.client.widgets.Canvas#addChild
     * addChild()}, {@link com.smartgwt.client.widgets.Canvas#removeChild removeChild()}, {@link
     * com.smartgwt.client.widgets.Canvas#markForRedraw markForRedraw()} and {@link com.smartgwt.client.widgets.Canvas#redraw
     * redraw()} should be used instead. <P> In some cases, the element returned may match the element returned by {@link
     * com.smartgwt.client.widgets.Canvas#getOuterElement getOuterElement()}, but this will not always be the case. <P> If the
     * widget is undrawn, this method will return <code>null</code>.
     *
     * @return The outer DOM element for a drawn Canvas.
     */
    public native Element getContentElement() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getContentElement", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getContentElement();
        return ret;
    }-*/;

	/**
     * Returns a fully qualified {@link com.smartgwt.client.docs.DataPath} for this canvas. This is calculated by combining the
     * canvas' specified {@link com.smartgwt.client.docs.DataPath} with the <code>dataPath</code> of any parent  canvases up to
     * whichever canvas has a specified {@link com.smartgwt.client.widgets.Canvas#getValuesManager valuesManager} specified to
     * actually manage values from this component.
     *
     * @return fully qualified dataPath for this component.
     * See {@link com.smartgwt.client.docs.DataPath DataPath}
     */
    public native String getFullDataPath() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getFullDataPath", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getFullDataPath();
        return ret;
    }-*/;

	/**
     * If <code>this.showHover</code> is true, when the user holds the mouse over this Canvas for long enough to trigger a
     * hover event, a hover canvas is shown by default. This method returns the contents of that hover canvas. Default
     * implementation returns <code>this.prompt</code> - override for custom hover HTML. Note that returning <code>null</code>
     * or an empty string will suppress the hover canvas altogether.
     *
     * @return the string to show in the hover.
     * See {@link com.smartgwt.client.docs.HTMLString HTMLString}
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     */
    public native String getHoverHTML() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getHoverHTML", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getHoverHTML();
        return ret;
    }-*/;

	/**
     * Get an offset to be used when calculating snap positioning. Returns 0 by default.
     *
     * @return The offset to use when snapping
     * @see com.smartgwt.client.widgets.Canvas#getHSnapPosition
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getHSnapOrigin() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getHSnapOrigin", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getHSnapOrigin();
        return ret;
    }-*/;

	/**
     * Get an offset to be used when calculating snap positioning. Returns 0 by default.
     * @param snapChild the child that is being snapped
     *
     * @return The offset to use when snapping
     * @see com.smartgwt.client.widgets.Canvas#getHSnapPosition
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getHSnapOrigin(Canvas snapChild) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getHSnapOrigin", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getHSnapOrigin(snapChild == null ? null : snapChild.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        return ret;
    }-*/;
	
	/**
     * Override this method to provide a custom snap-to grid.  Note that you do not need to do this if your grid is regular
     * (ie, grid points are every x pixels); regular grids should be defined using {@link
     * com.smartgwt.client.widgets.Canvas#getSnapHGap snapHGap} and {@link com.smartgwt.client.widgets.Canvas#getSnapVGap
     * snapVGap}. You should only override this method if you want to provide support for a grid of irregularly-placed points
     * @param coordinate x-coordinate of the drag event relative to the inside of this widget
     *
     * @return The horizontal coordinate to snap to
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public native int getHSnapPosition(int coordinate) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getHSnapPosition", "int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getHSnapPosition(coordinate);
        return ret;
    }-*/;

	/**
     * Override this method to provide a custom snap-to grid.  Note that you do not need to do this if your grid is regular
     * (ie, grid points are every x pixels); regular grids should be defined using {@link
     * com.smartgwt.client.widgets.Canvas#getSnapHGap snapHGap} and {@link com.smartgwt.client.widgets.Canvas#getSnapVGap
     * snapVGap}. You should only override this method if you want to provide support for a grid of irregularly-placed points
     * @param coordinate x-coordinate of the drag event relative to the inside of this widget
     * @param direction "before" or "after" denoting whether the returned coordinate should   match the left or right edge of the current
     * square. If unset {@link com.smartgwt.client.widgets.Canvas#getSnapHDirection snapHDirection} will   be used by default
     *
     * @return The horizontal coordinate to snap to
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public native int getHSnapPosition(int coordinate, String direction) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getHSnapPosition", "int,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getHSnapPosition(coordinate, direction);
        return ret;
    }-*/;
	
	/**
     * Retrieve a native image or SVG object element by name. <P> The element must have been created from HTML generated by
     * calling {@link com.smartgwt.client.widgets.Canvas#imgHTML imgHTML()} on this particular Canvas.
     * @param identifier name of the image or SVG object to get, as originally passed to                              <code>imgHTML()</code>
     *
     * @return DOM image or SVG object element if found, else null
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public native Element getImage(String identifier) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getImage", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getImage(identifier);
        return ret;
    }-*/;

	/**
     * Returns the amount of space available for interior content (or relatively positioned child widget(s)) without
     * introducing clipping, scrolling or overflow.<br> This is the space within the viewport of the widget (not including
     * padding, and excluding margins, borders or scrollbars) rendered at its specified size.
     *
     * @return inner height of the widget in pixels
     * @see com.smartgwt.client.widgets.Canvas#getInnerContentWidth
     * @see com.smartgwt.client.widgets.Canvas#getInnerHeight
     * @see com.smartgwt.client.widgets.Canvas#getInnerWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getInnerContentHeight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getInnerContentHeight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getInnerContentHeight();
        return ret;
    }-*/;

	/**
     * Returns the amount of space available for interior content (or relatively positioned child  widget(s)) without
     * introducing clipping, scrolling or overflow.<br>  This is the space within the viewport of the widget (not including
     * padding, and excluding  margins, borders or scrollbars) rendered at its specified size.
     *
     * @return inner height of the widget in pixels
     * @see com.smartgwt.client.widgets.Canvas#getInnerContentHeight
     * @see com.smartgwt.client.widgets.Canvas#getInnerHeight
     * @see com.smartgwt.client.widgets.Canvas#getInnerWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getInnerContentWidth() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getInnerContentWidth", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getInnerContentWidth();
        return ret;
    }-*/;

	/**
     * Returns the amount of space available for (an) absolutely positioned child widget(s) or absolutely positioned HTML
     * content, without introducing clipping, scrolling or overflow. <P> This is the space within the viewport of the widget
     * (including padding, but excluding margins, borders or scrollbars) rendered at its specified size.
     *
     * @return inner height of the widget in pixels
     * @see com.smartgwt.client.widgets.Canvas#getInnerWidth
     * @see com.smartgwt.client.widgets.Canvas#getInnerContentHeight
     * @see com.smartgwt.client.widgets.Canvas#getInnerContentWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getInnerHeight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getInnerHeight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getInnerHeight();
        return ret;
    }-*/;

	/**
     * Returns the amount of space available for absolutely positioned child widget(s) or absolutely positioned HTML content,
     * without introducing clipping, scrolling or overflow. <P> This is the space within the viewport of the widget (including
     * padding, but excluding margins, borders or scrollbars) rendered at its specified size.
     *
     * @return inner width of the widget in pixels
     * @see com.smartgwt.client.widgets.Canvas#getInnerHeight
     * @see com.smartgwt.client.widgets.Canvas#getInnerContentHeight
     * @see com.smartgwt.client.widgets.Canvas#getInnerContentWidth
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getInnerWidth() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getInnerWidth", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getInnerWidth();
        return ret;
    }-*/;

	/**
     * Retrieve the local ID of this canvas. If no local ID is assigned the normal canvas ID is returned making this method a
     * safe replacement for {@link com.smartgwt.client.widgets.Canvas#getID getID()}. <p> A "local ID" is name for a child
     * widget which is unique only for this parent, and not globally unique as is required for {@link
     * com.smartgwt.client.widgets.Canvas#getID ID}.  Widgets receive local IDs when loaded via {@link
     * com.smartgwt.client.rpc.RPCManager#loadScreen RPCManager.loadScreen()} or {@link
     * com.smartgwt.client.rpc.RPCManager#cacheScreens RPCManager.cacheScreens()} and {@link
     * com.smartgwt.client.rpc.RPCManager#createScreen RPCManager.createScreen()}.
     *
     * @return the local ID or standard ID of the Canvas
     */
    public native String getLocalId() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getLocalId", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getLocalId();
        return ret;
    }-*/;

	/**
     * Returns this canvas's "master" (the canvas to which it was added as a peer), if any.
     *
     * @return the master canvas, null if none exists.
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Canvas getMasterCanvas() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getMasterCanvas", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getMasterCanvas();
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

	/**
     * Return the X-coordinate of the last event relative to the left edge of the content of this  Canvas.<br><br>  NOTE: To
     * get a coordinate relative to the <b>viewport</b> of this Canvas, subtract  this.getScrollLeft()
     *
     * @return 
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getOffsetX() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getOffsetX", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getOffsetX();
        return ret;
    }-*/;

	/**
     * Return the Y-coordinate of the last event, relative to the top edge of the content of this  Canvas.<br><br>  NOTE: To
     * get a coordinate relative to the <b>viewport</b> of this Canvas, subtract  this.getScrollTop()
     *
     * @return 
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getOffsetY() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getOffsetY", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getOffsetY();
        return ret;
    }-*/;

	/**
     * Returns the outer DOM element of this Canvas. This method is provided for the  very rare cases where a programmer needs
     * to examine the DOM hierarchy created by a drawn Smart GWT component. <P> Direct manipulation of the DOM elements created
     * by Smart GWT components is not supported. Smart GWT components should be rendered or cleared using standard methods such
     * as {@link com.smartgwt.client.widgets.Canvas#draw draw()}, {@link com.smartgwt.client.widgets.Canvas#clear clear()}. If
     * direct integration with existing DOM structures is required, this should be achieved via the {@link
     * com.smartgwt.client.widgets.Canvas#getHtmlElement htmlElement} attribute, rather than by attempting to move the
     * component's outer element via native browser APIs.<br> The content of Smart GWT components' DOM elements should also not
     * be directly  manipulated using native browser APIs - standard methods such as  {@link
     * com.smartgwt.client.widgets.Canvas#setContents setContents()}, {@link com.smartgwt.client.widgets.Canvas#addChild
     * addChild()}, {@link com.smartgwt.client.widgets.Canvas#removeChild removeChild()}, {@link
     * com.smartgwt.client.widgets.Canvas#markForRedraw markForRedraw()} and {@link com.smartgwt.client.widgets.Canvas#redraw
     * redraw()} should be used instead. <P> In some cases, the element returned may match the element returned by  {@link
     * com.smartgwt.client.widgets.Canvas#getContentElement getContentElement()}, but this will not always be the case. <P> If
     * the widget is undrawn, this method will return <code>null</code>.
     *
     * @return The outer DOM element for a drawn Canvas.
     */
    public native Element getOuterElement() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getOuterElement", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getOuterElement();
        return ret;
    }-*/;

	/**
     * Return the page-relative bottom coordinate of this object, in pixels.
     *
     * @return GLOBAL bottom coordinate
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getPageBottom() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getPageBottom", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getPageBottom();
        return ret;
    }-*/;

	/**
     * Returns the page-relative left coordinate of the widget on the page, in pixels.
     *
     * @return global left coordinate
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getPageLeft() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getPageLeft", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getPageLeft();
        return ret;
    }-*/;

	/**
     * Return the page-relative right coordinate of this object, in pixels.
     *
     * @return GLOBAL right coordinate
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getPageRight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getPageRight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getPageRight();
        return ret;
    }-*/;

	/**
     * Returns the page-relative top coordinate of the widget on the page, in pixels
     *
     * @return GLOBAL top coordinate
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getPageTop() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getPageTop", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getPageTop();
        return ret;
    }-*/;

	/**
     * Returns this Canvas's "panel container".  A panel container is a widget that manages a  collection of panels, like a
     * {@link com.smartgwt.client.widgets.tab.TabSet} or {@link com.smartgwt.client.widgets.layout.SectionStack}.  If this
     * Canvas is not a child of such a panel container, this method returns null.
     *
     * @return the Canvas's panel container, or null if the Canvas is not a chlld of                    a panel container
     */
    public native Canvas getPanelContainer() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getPanelContainer", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getPanelContainer();
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

	/**
     * Return the right coordinate of this object as rendered, relative to its enclosing context, in pixels.
     *
     * @return right coordinate
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getRight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getRight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getRight();
        return ret;
    }-*/;

	/**
     * Get the current value of the rule context collected by the {@link com.smartgwt.client.widgets.Canvas#getRuleScope
     * ruleScope} of this component (which may be this component itself or whatever component is managing the
     * <code>ruleScope</code> for this component). <p> If the <code>databoundOnly</code> parameter is passed as true, only data
     * from components that actually have a {@link com.smartgwt.client.data.DataSource} is included. <p> Use {@link
     * com.smartgwt.client.widgets.Canvas#addRuleContextChangedHandler Canvas.ruleContextChanged()} to get a notification of
     * changes to the rule context.
     *
     * @return the ruleContext object, or null if canvas is not part of a ruleScope
     */
    public native Map getRuleContext() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getRuleContext", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getRuleContext();
        return @com.smartgwt.client.util.JSOHelper::convertToMap(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

	/**
     * Get the current value of the rule context collected by the {@link com.smartgwt.client.widgets.Canvas#getRuleScope
     * ruleScope} of this component (which may be this component itself or whatever component is managing the
     * <code>ruleScope</code> for this component). <p> If the <code>databoundOnly</code> parameter is passed as true, only data
     * from components that actually have a {@link com.smartgwt.client.data.DataSource} is included. <p> Use {@link
     * com.smartgwt.client.widgets.Canvas#addRuleContextChangedHandler Canvas.ruleContextChanged()} to get a notification of
     * changes to the rule context.
     * @param databoundOnly whether to include only data from components that have a                                  {@link
     * com.smartgwt.client.data.DataSource}
     *
     * @return the ruleContext object, or null if canvas is not part of a ruleScope
     */
    public native Map getRuleContext(boolean databoundOnly) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getRuleContext", "boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getRuleContext(databoundOnly);
        return @com.smartgwt.client.util.JSOHelper::convertToMap(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;
	
	/**
     * Returns the scrollTop required to scroll vertically to the end of this widget's content.
     *
     * @return scroll bottom coordinate
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native int getScrollBottom() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getScrollBottom", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getScrollBottom();
        return ret;
    }-*/;

	/**
     * Returns the scrollable height of the widget's contents, including children, ignoring          clipping.
     *
     * @return height of the element that can scroll
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getScrollHeight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getScrollHeight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getScrollHeight();
        return ret;
    }-*/;

	/**
     * Get the number of pixels this Canvas is scrolled from its left edge.
     *
     * @return scrollLeft
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getScrollLeft() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getScrollLeft", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getScrollLeft();
        return ret;
    }-*/;

	/**
     * Returns the scrollLeft required to scroll horizontally to the end of this widget's content.
     *
     * @return scroll bottom coordinate
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native int getScrollRight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getScrollRight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getScrollRight();
        return ret;
    }-*/;

	/**
     * Get the number of pixels this Canvas is scrolled from its top edge.
     *
     * @return scrollTop
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getScrollTop() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getScrollTop", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getScrollTop();
        return ret;
    }-*/;

	/**
     * Returns the scrollable width of the widget's contents, including children, ignoring clipping.
     *
     * @return the scrollable width of the widget's contents
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getScrollWidth() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getScrollWidth", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getScrollWidth();
        return ret;
    }-*/;

	/**
     * Returns the height of the viewport onto the scrollable content.
     *
     * @return height of the viewport, in pixels
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getViewportHeight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getViewportHeight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getViewportHeight();
        return ret;
    }-*/;

	/**
     * Returns the width of the viewport onto the scrollable content.
     *
     * @return width of the viewport, in pixels
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getViewportWidth() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getViewportWidth", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getViewportWidth();
        return ret;
    }-*/;

	/**
     * Return the visible height of the Canvas.
     *
     * @return visible height in pixels
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getVisibleHeight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getVisibleHeight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getVisibleHeight();
        return ret;
    }-*/;

	/**
     * Return the visible width of the Canvas.
     *
     * @return visible width in pixels
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native int getVisibleWidth() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getVisibleWidth", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getVisibleWidth();
        return ret;
    }-*/;

	/**
     * Get an offset to be used when calculating snap positioning. Returns 0 by default.
     *
     * @return The offset to use when snapping
     * @see com.smartgwt.client.widgets.Canvas#getVSnapPosition
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getVSnapOrigin() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getVSnapOrigin", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getVSnapOrigin();
        return ret;
    }-*/;

	/**
     * Get an offset to be used when calculating snap positioning. Returns 0 by default.
     * @param snapChild the child that is being snapped
     *
     * @return The offset to use when snapping
     * @see com.smartgwt.client.widgets.Canvas#getVSnapPosition
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native int getVSnapOrigin(Canvas snapChild) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getVSnapOrigin", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getVSnapOrigin(snapChild == null ? null : snapChild.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        return ret;
    }-*/;
	
	/**
     * Override this method to provide a custom snap-to grid.  Note that you do not need to do this if your grid is regular
     * (ie, grid points are every x pixels) - regular grids should be defined using {@link
     * com.smartgwt.client.widgets.Canvas#getSnapHGap snapHGap} and {@link com.smartgwt.client.widgets.Canvas#getSnapVGap
     * snapVGap}. You should only override this method if you want to provide support for a grid of irregularly-placed points
     * @param coordinate y-coordinate of the drag event relative to the inside of this widget
     *
     * @return The vertical coordinate to snap to
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public native int getVSnapPosition(int coordinate) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getVSnapPosition", "int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getVSnapPosition(coordinate);
        return ret;
    }-*/;

	/**
     * Override this method to provide a custom snap-to grid.  Note that you do not need to do this if your grid is regular
     * (ie, grid points are every x pixels) - regular grids should be defined using {@link
     * com.smartgwt.client.widgets.Canvas#getSnapHGap snapHGap} and {@link com.smartgwt.client.widgets.Canvas#getSnapVGap
     * snapVGap}. You should only override this method if you want to provide support for a grid of irregularly-placed points
     * @param coordinate y-coordinate of the drag event relative to the inside of this widget
     * @param direction "before" or "after" denoting whether the returned coordinate should   match the top or bottom edge of the current
     * square. If unset {@link com.smartgwt.client.widgets.Canvas#getSnapHDirection snapHDirection} will   be used by default
     *
     * @return The vertical coordinate to snap to
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public native int getVSnapPosition(int coordinate, String direction) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getVSnapPosition", "int,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getVSnapPosition(coordinate, direction);
        return ret;
    }-*/;
	
	/**
     * Get the z-Index of this canvas.<br><br>
     * @param resolveToNumber If passed <code>true</code>, for undrawn widgets, resolve "auto" to the next available zIndex.
     *
     * @return 
     * @see com.smartgwt.client.docs.ZIndex ZIndex overview and related methods
     */
    public native int getZIndex(boolean resolveToNumber) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "getZIndex", "boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getZIndex(resolveToNumber);
        return ret;
    }-*/;

	/**
     * Handler fired on a delay when the user hovers the mouse over this hover-target. Default implementation will fire
     * <code>this.hover()</code> (if defined), and handle showing the hover canvas if <code>this.showHover</code> is true.
     * @see com.smartgwt.client.widgets.Canvas#getCanHover
     * @see com.smartgwt.client.widgets.Canvas#getShowHover
     * @see com.smartgwt.client.widgets.events.HoverEvent
     */
    public native void handleHover() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "handleHover", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.handleHover();
    }-*/;

	/**
     * Sets the widget's CSS visibility attribute to "hidden".
     * @see com.smartgwt.client.docs.Visibility Visibility overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_showhide" target="examples">Show & Hide Example</a>
     */
    public native void hide() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "hide", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.hide();
    }-*/;

	/**
     * Hides the click mask associated with this canvas.
     * @see com.smartgwt.client.widgets.Canvas#showClickMask
     */
    public native void hideClickMask() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "hideClickMask", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.hideClickMask();
    }-*/;

	/**
     * Hides the click mask associated with this canvas.
     * @param ID optional ID of specific clickMask to hide. If not passed,                      defaults to hiding the click mask
     * associated with this widget only.
     * @see com.smartgwt.client.widgets.Canvas#showClickMask
     */
    public native void hideClickMask(String ID) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "hideClickMask", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.hideClickMask(ID);
    }-*/;
	
	/**
     * Hide the {@link com.smartgwt.client.widgets.Canvas#showComponentMask component level clickMask} for this widget
     */
    public native void hideComponentMask() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "hideComponentMask", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.hideComponentMask();
    }-*/;

	/**
     * Hide the {@link com.smartgwt.client.widgets.Canvas#showComponentMask component level clickMask} for this widget
     * @param suppressFocusReset By default when the component-level mask is hidden      it will attempt to reset focus to whatever had focus before the
     * mask was shown.      Pass this parameter to suppress this behavior.
     */
    public native void hideComponentMask(boolean suppressFocusReset) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "hideComponentMask", "boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.hideComponentMask(suppressFocusReset);
    }-*/;
	
	/**
     * The default implementation of this method hides the contextMenu currently being shown for this component (which occurs
     * when the mouse button that toggles the context menu is released). Override if you want some other behavior.
     * @see com.smartgwt.client.widgets.events.ShowContextMenuEvent
     * @see com.smartgwt.client.widgets.menu.Menu#hideContextMenu
     * @see com.smartgwt.client.widgets.Canvas#getOffsetX
     * @see com.smartgwt.client.widgets.Canvas#getOffsetY
     */
    public native void hideContextMenu() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "hideContextMenu", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.hideContextMenu();
    }-*/;

    /**
     * Add a hover handler.
     * <p>
     * If <code>canHover</code> is true for this widget, the <code>hover</code> string method will be fired when the user
     * hovers over this canvas. If this method returns false, it will suppress the default behavior of showing a hover canvas
     * if <code>this.showHover</code> is true.
     *
     * @param handler the hover handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addHoverHandler(com.smartgwt.client.widgets.events.HoverHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.HoverEvent.getType()) == 0) setupHoverEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.HoverEvent.getType());
    }

    private native void setupHoverEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var hover = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.HoverEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("hover"));
            obj.addProperties({hover: 
                function () {
                    var param = {"_this": this};
                    var ret = hover(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("hover", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("hover"));
            obj.hover = 
                function () {
                    var param = {"_this": this};
                    var ret = hover(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("hover", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a hoverHidden handler.
     * <p>
     * If {@link com.smartgwt.client.widgets.Canvas#getShowHover showHover} is true for this canvas, this notification method
     * will be fired whenever the hover shown in response to {@link com.smartgwt.client.widgets.Canvas#handleHover
     * handleHover()} is hidden. This method may be observed or overridden.
     *
     * @param handler the hoverHidden handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addHoverHiddenHandler(com.smartgwt.client.widgets.events.HoverHiddenHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.HoverHiddenEvent.getType()) == 0) setupHoverHiddenEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.HoverHiddenEvent.getType());
    }

    private native void setupHoverHiddenEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var hoverHidden = $entry(function(){
            var param = {"_this": this};
            var event = @com.smartgwt.client.widgets.events.HoverHiddenEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("hoverHidden", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("hoverHidden"));
            obj.addProperties({hoverHidden:  hoverHidden              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("hoverHidden"));
            obj.hoverHidden =  hoverHidden             ;
        }
   }-*/;

	/**
     * Returns true if the rectangles of this widget and the specified widget overlap.
     * @param other other canvas to test for intersection
     *
     * @return true if this canvas intersects other; false otherwise
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native Boolean intersects(Canvas other) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "intersects", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.intersects(other == null ? null : other.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Returns whether a canvas is waiting to be redrawn. Will return true if {@link
     * com.smartgwt.client.widgets.Canvas#markForRedraw markForRedraw()} has been called, but this canvas has not yet been
     * redrawn.
     *
     * @return true is this canvas needs to be redrawn; false otherwise
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public native Boolean isDirty() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "isDirty", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.isDirty();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Is this canvas disabled? Note that the disabled state is inherited - this method will return true if this widget, or any
     * of its ancestors are marked disabled.
     *
     * @return true if the widget or any widget above it in the containment hierarchy                      are disabled.
     * @see com.smartgwt.client.docs.Enable Enable overview and related methods
     */
    public native Boolean isDisabled() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "isDisabled", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.isDisabled();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * Returns true if this Canvas has the keyboard focus.  Note that in Internet Explorer
     * focus notifications can be asynchronous (see {@link com.smartgwt.client.util.EventHandler#synchronousFocusNotifications
     * synchronousFocusNotifications}).
     *  In this case, <code>canvas.isFocused()</code> method can correctly 
     *  return false when, intuitively, you would expect it to return true:
     *  <pre>
     *      someCanvas.focus();
     *      if (someCanvas.isFocused()) {
     *          // In most browsers we would get here, but not in Internet Explorer with
     *          // EventHandler.synchronousFocusNotifications disabled.
     *      }</pre>
     * 
     *
     * @return whether this Canvas has the keyboard focus
     * @see com.smartgwt.client.widgets.Canvas#containsFocus
     */
    public native Boolean isFocused() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "isFocused", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.isFocused();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

    /**
     * Add a keyDown handler.
     * <p>
     * Executed when a key is pressed on a focusable widget ({@link com.smartgwt.client.widgets.Canvas#getCanFocus
     * Canvas.canFocus}: true). <P> Use {@link com.smartgwt.client.util.EventHandler#getKey EventHandler.getKey()} to find out
     * the String of the key that was pressed, and use {@link com.smartgwt.client.util.EventHandler#shiftKeyDown
     * EventHandler.shiftKeyDown()} and related functions to determine whether modifier keys were down.
     *
     * @param handler the keyDown handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addKeyDownHandler(com.smartgwt.client.widgets.events.KeyDownHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.KeyDownEvent.getType()) == 0) setupKeyDownEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.KeyDownEvent.getType());
    }

    private native void setupKeyDownEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var keyDown = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.KeyDownEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("keyDown"));
            obj.addProperties({keyDown: 
                function () {
                    var param = {"_this": this};
                    var ret = keyDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("keyDown", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("keyDown"));
            obj.keyDown = 
                function () {
                    var param = {"_this": this};
                    var ret = keyDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("keyDown", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a keyPress handler.
     * <p>
     * Executed when a key is pressed and released on a focusable widget ({@link com.smartgwt.client.widgets.Canvas#getCanFocus
     * Canvas.canFocus}: true). <P> Use {@link com.smartgwt.client.util.EventHandler#getKey EventHandler.getKey()} to find out
     * the String of the key that was pressed, and use {@link com.smartgwt.client.util.EventHandler#shiftKeyDown
     * EventHandler.shiftKeyDown()} and related functions to determine whether modifier keys were down.
     *
     * @param handler the keyPress handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addKeyPressHandler(com.smartgwt.client.widgets.events.KeyPressHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.KeyPressEvent.getType()) == 0) setupKeyPressEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.KeyPressEvent.getType());
    }

    private native void setupKeyPressEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var keyPress = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.KeyPressEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("keyPress"));
            obj.addProperties({keyPress: 
                function () {
                    var param = {"_this": this};
                    var ret = keyPress(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("keyPress", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("keyPress"));
            obj.keyPress = 
                function () {
                    var param = {"_this": this};
                    var ret = keyPress(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("keyPress", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

	/**
     * Executed when a key is released on a focusable widget ({@link com.smartgwt.client.widgets.Canvas#getCanFocus canFocus}:
     * true). <P> Use {@link com.smartgwt.client.util.EventHandler#getKey EventHandler.getKey()} to find out the String of the
     * key that was pressed, and use {@link com.smartgwt.client.util.EventHandler#shiftKeyDown EventHandler.shiftKeyDown()} and
     * related functions to determine whether modifier keys were down.
     *
     * @return false to prevent this event from bubbling to this widget's parent, true or undefined to bubble.
     * @see com.smartgwt.client.widgets.Canvas#getCanFocus
     */
    public native Boolean keyUp() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "keyUp", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.keyUp();
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

	/**
     * <code>layoutChildren()</code> is where a Canvas should implement a sizing policy for it's Canvas children.  Since
     * <code>layoutChildren</code> calls parentResized() on its children, {@link
     * com.smartgwt.client.widgets.Canvas#parentResized parentResized()} is a good place for a child to implement a layout
     * policy that can be used within any parent. <P> Recommended practice for a Canvas that manages Canvas children is to
     * create those children without any initial coordinate or size settings and do all sizing when layoutChildren() is called.
     * <P> layoutChildren() is always called at least once before children are drawn, and is called automatically whenever the
     * viewport size changes (which includes both resizing and introduction/removal of scrolling).  layoutChildren() can also
     * be manually invoked in any other component-specific situation which changes the layout. <P> NOTE: layoutChildren() may
     * be called before draw() if a widget is resized before draw(), so be sure to avoid errors such as assuming that any
     * children you automatically create have already been created. <P> NOTE: auto-sizing: layoutChildren() is also called once
     * during the initial draw(), before children are drawn, with a "reason" of "initial draw".  During this invocation of
     * layoutChildren() it is legal to directly draw children (call child.draw()), which is otherwise never allowed.  This
     * allows a Canvas to implement an auto-sizing layout policy by drawing some children before deciding on the sizes of
     * remaining children, which is far more efficient than drawing all children and resizing some of them after they are
     * drawn.
     * @param reason reason why layoutChildren() is being called, passed when framework                        code invokes layoutChildren()
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native void layoutChildren(String reason) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "layoutChildren", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.layoutChildren(reason);
    }-*/;

	/**
     * Generates the HTML for a standard link (anchor) element.
     * @param href URL for the link to point to
     *
     * @return HTML for the link.
     * See {@link com.smartgwt.client.docs.HTMLString HTMLString}
     */
    public native String linkHTML(String href) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "linkHTML", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.linkHTML(href);
        return ret;
    }-*/;

    /**
     * @see Canvas#linkHTML
     */
    public String linkHTML(String href, String text){
        return linkHTML(href, text, null, null, (Integer) null, null);
    }

    /**
     * @see Canvas#linkHTML
     */
    public String linkHTML(String href, String text, String target){
        return linkHTML(href, text, target, null, (Integer) null, null);
    }

    /**
     * @see Canvas#linkHTML
     */
    public String linkHTML(String href, String text, String target, String ID){
        return linkHTML(href, text, target, ID, (Integer) null, null);
    }

    /**
     * @see Canvas#linkHTML
     */
    public String linkHTML(String href, String text, String target, String ID, Integer tabIndex){
        return linkHTML(href, text, target, ID, tabIndex, null);
    }

	/**
     * Generates the HTML for a standard link (anchor) element.
     * @param href URL for the link to point to
     * @param text HTML to display in the link element (defaults to the href).
     * See {@link com.smartgwt.client.docs.HTMLString HTMLString}
     * @param target Target window for the link (defaults to opening in a new, unnamed window)
     * @param ID optional ID for the link element to be written out
     * @param tabIndex optional tabIndex for the link
     * @param accessKey optional accessKey for the link
     *
     * @return HTML for the link.
     * See {@link com.smartgwt.client.docs.HTMLString HTMLString}
     */
    public native String linkHTML(String href, String text, String target, String ID, Integer tabIndex, String accessKey) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "linkHTML", "String,String,String,String,Integer,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.linkHTML(href, text, target, ID, tabIndex == null ? null : tabIndex.@java.lang.Integer::intValue()(), accessKey);
        return ret;
    }-*/;
	
	/**
     * {@link com.smartgwt.client.widgets.Canvas#destroy destroy()} this canvas on a timeout. This method should be used
     * instead of calling <code>canvas.destroy()</code> directly unless there's a reason a the canvas needs to be destroyed
     * synchronously. By using a timeout, this method ensures the <code>destroy()</code> will occur after the current thread of
     * execution completes. This allows you to easily mark canvases for destruction while they're handling events, which must
     * complete before the canvas can be destroyed.
     * @see com.smartgwt.client.widgets.Canvas#destroy
     */
    public native void markForDestroy() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "markForDestroy", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.markForDestroy();
    }-*/;

	/**
     * Marks the widget as "dirty" so that it will be added to a queue for redraw. Redraw of dirty components is handled by a
     * looping timer and will after a very short delay (typically less than 100ms). In most cases it is recommended that
     * developers use <code>markForRedraw()</code> instead of calling {@link com.smartgwt.client.widgets.Canvas#redraw
     * redraw()} directly. Since this method queues the redraw, multiple calls to markForRedraw() within a single thread of
     * execution will only lead to a single DOM manipulation which greatly improves application performance.
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public native void markForRedraw() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "markForRedraw", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.markForRedraw();
    }-*/;

	/**
     * Marks the widget as "dirty" so that it will be added to a queue for redraw. Redraw of dirty components is handled by a
     * looping timer and will after a very short delay (typically less than 100ms). In most cases it is recommended that
     * developers use <code>markForRedraw()</code> instead of calling {@link com.smartgwt.client.widgets.Canvas#redraw
     * redraw()} directly. Since this method queues the redraw, multiple calls to markForRedraw() within a single thread of
     * execution will only lead to a single DOM manipulation which greatly improves application performance.
     * @param reason reason for performing the redraw
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public native void markForRedraw(String reason) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "markForRedraw", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.markForRedraw(reason);
    }-*/;
	
    /**
     * Add a mouseDown handler.
     * <p>
     * Executed when the left mouse button is pressed on this widget.  No default implementation.
     *
     * @param handler the mouseDown handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseDownHandler(com.smartgwt.client.widgets.events.MouseDownHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseDownEvent.getType()) == 0) setupMouseDownEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseDownEvent.getType());
    }

    private native void setupMouseDownEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseDown = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseDownEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseDown"));
            obj.addProperties({mouseDown: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseDown", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseDown"));
            obj.mouseDown = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseDown", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a mouseMove handler.
     * <p>
     * Executed when the mouse moves within this widget.  No default implementation.
     *
     * @param handler the mouseMove handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseMoveHandler(com.smartgwt.client.widgets.events.MouseMoveHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseMoveEvent.getType()) == 0) setupMouseMoveEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseMoveEvent.getType());
    }

    private native void setupMouseMoveEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseMove = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseMoveEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseMove"));
            obj.addProperties({mouseMove: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseMove", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseMove"));
            obj.mouseMove = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseMove(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseMove", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a mouseOut handler.
     * <p>
     * Executed when the mouse leaves this widget.  No default implementation.
     *
     * @param handler the mouseOut handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseOutHandler(com.smartgwt.client.widgets.events.MouseOutHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseOutEvent.getType()) == 0) setupMouseOutEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseOutEvent.getType());
    }

    private native void setupMouseOutEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseOut = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseOutEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseOut"));
            obj.addProperties({mouseOut: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseOut(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseOut", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseOut"));
            obj.mouseOut = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseOut(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseOut", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a mouseOver handler.
     * <p>
     * Executed when mouse enters this widget.  No default implementation.
     *
     * @param handler the mouseOver handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseOverHandler(com.smartgwt.client.widgets.events.MouseOverHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseOverEvent.getType()) == 0) setupMouseOverEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseOverEvent.getType());
    }

    private native void setupMouseOverEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseOver = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseOverEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseOver"));
            obj.addProperties({mouseOver: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseOver(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseOver", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseOver"));
            obj.mouseOver = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseOver(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseOver", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a mouseStillDown handler.
     * <p>
     * Repeating notification method for the user holding the left mouse button down over this canvas. <P> The
     * <code>mouseStillDown</code> event is fired immediately when the mouse goes down. If the user holds the mouse down, after
     * a pause of  {@link com.smartgwt.client.widgets.Canvas#getMouseStillDownInitialDelay Canvas.mouseStillDownInitialDelay},
     * it will begin to fire repeatedly every  {@link com.smartgwt.client.widgets.Canvas#getMouseStillDownDelay
     * Canvas.mouseStillDownDelay} milliseconds. <P> This provides developers with a simple way to handle the common "repeated
     * action"  use case where a user can click a UI element to perform an action once, or click  and hold to perform the
     * action repeatedly.<br> Examples of this include standard scrollbar button behavior and buttons  to increase or decrease
     * the value in a spinner type input element. <P> This event is not native to JavaScript, but is provided by the ISC
     * system.
     *
     * @param handler the mouseStillDown handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseStillDownHandler(com.smartgwt.client.widgets.events.MouseStillDownHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseStillDownEvent.getType()) == 0) setupMouseStillDownEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseStillDownEvent.getType());
    }

    private native void setupMouseStillDownEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseStillDown = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseStillDownEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseStillDown"));
            obj.addProperties({mouseStillDown: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseStillDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseStillDown", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseStillDown"));
            obj.mouseStillDown = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseStillDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseStillDown", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a mouseUp handler.
     * <p>
     * Executed when the left mouse is released on this widget.  No default implementation.
     *
     * @param handler the mouseUp handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseUpHandler(com.smartgwt.client.widgets.events.MouseUpHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseUpEvent.getType()) == 0) setupMouseUpEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseUpEvent.getType());
    }

    private native void setupMouseUpEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseUp = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseUpEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseUp"));
            obj.addProperties({mouseUp: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseUp(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseUp", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseUp"));
            obj.mouseUp = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseUp(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseUp", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a mouseWheel handler.
     * <p>
     * Executed when the mouse wheel is actuated.
     *
     * @param handler the mouseWheel handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMouseWheelHandler(com.smartgwt.client.widgets.events.MouseWheelHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MouseWheelEvent.getType()) == 0) setupMouseWheelEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MouseWheelEvent.getType());
    }

    private native void setupMouseWheelEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var mouseWheel = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.MouseWheelEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("mouseWheel"));
            obj.addProperties({mouseWheel: 
                function () {
                    var param = {"_this": this};
                    var ret = mouseWheel(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseWheel", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("mouseWheel"));
            obj.mouseWheel = 
                function () {
                    var param = {"_this": this};
                    var ret = mouseWheel(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("mouseWheel", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

	/**
     * Puts this widget just above the specified widget in the stacking order, so it appears in front of the specified widget
     * if both widgets have the same parent.
     * @param canvas canvas to move above
     * @see com.smartgwt.client.docs.ZIndex ZIndex overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_layer" target="examples">Layer Example</a>
     */
    public native void moveAbove(Canvas canvas) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "moveAbove", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.moveAbove(canvas == null ? null : canvas.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Puts this widget just below the specified widget in the stacking order, so it appears behind the specified widget if
     * both widgets have the same parent.
     * @param canvas canvas to move below
     * @see com.smartgwt.client.docs.ZIndex ZIndex overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_layer" target="examples">Layer Example</a>
     */
    public native void moveBelow(Canvas canvas) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "moveBelow", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.moveBelow(canvas == null ? null : canvas.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Moves the widget deltaX pixels to the right and deltaY pixels down. Pass negative          numbers to move up and/or to
     * the left.
     * @param deltaX amount to move horizontally (may be negative)
     * @param deltaY amount to move vertically (may be negative)
     *
     * @return whether the component actually moved
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_move" target="examples">Move Example</a>
     */
    public native Boolean moveBy(int deltaX, int deltaY) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "moveBy", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.moveBy(deltaX, deltaY);
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;

    /**
     * Add a moved handler.
     * <p>
     * Notification method fired when this component is explicitly moved. Note that a component's position on the screen may
     * also changed due to an ancestor being moved. The {@link com.smartgwt.client.widgets.Canvas#addParentMovedHandler
     * Canvas.parentMoved()} method provides a notification entry point to catch that case as well.
     *
     * @param handler the moved handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addMovedHandler(com.smartgwt.client.widgets.events.MovedHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.MovedEvent.getType()) == 0) setupMovedEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.MovedEvent.getType());
    }

    private native void setupMovedEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var moved = $entry(function(){
            var param = {"_this": this, "deltaX" : arguments[0], "deltaY" : arguments[1]};
            var event = @com.smartgwt.client.widgets.events.MovedEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("moved", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("moved"));
            obj.addProperties({moved:  moved              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("moved"));
            obj.moved =  moved             ;
        }
   }-*/;

	/**
     * Moves the widget so that its top-left corner is at the specified coordinates. <P> This method will also accept a single
     * parameter as an object array with left and top given as properties.
     * @param left x-coordinate to move to in LOCAL coordinates                                             or Object with left and top
     * properties.
     * @param top y-coordinate to move to in LOCAL coordinates
     *
     * @return whether the component actually moved
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_move" target="examples">Move Example</a>
     */
    public native boolean moveTo(int left, int top) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "moveTo", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.moveTo(left, top);
        return ret == null ? false : ret;
    }-*/;
	
    /**
     * Add a clear handler.
     * <p>
     * Notification method fired when the canvas is removed from the DOM via a  call to {@link
     * com.smartgwt.client.widgets.Canvas#clear Canvas.clear()}. Canvases are cleared recursively, so if  a parent is
     * <i>clear()</i>'d, all of its descendants will also be cleared. This notification is fired before the canvas has been
     * removed from the DOM, and {@link com.smartgwt.client.widgets.Canvas#isDrawn Canvas.isDrawn()} will return
     * <code>true</code> if called while the  notification is running.
     *
     * @param handler the clear handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addClearHandler(com.smartgwt.client.widgets.events.ClearHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.ClearEvent.getType()) == 0) setupClearEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.ClearEvent.getType());
    }

    private native void setupClearEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var onClear = $entry(function(){
            var param = {"_this": this};
            var event = @com.smartgwt.client.widgets.events.ClearEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("onClear", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("onClear"));
            obj.addProperties({onClear:  onClear              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("onClear"));
            obj.onClear =  onClear             ;
        }
   }-*/;

    /**
     * Add a drop handler.
     * <p>
     * Notification method fired when the user drops another canvas onto this one. Returning <code>false</code> from this
     * method will prevent any default drop behavior from occurring
     *
     * @param handler the drop handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addDropHandler(com.smartgwt.client.widgets.events.DropHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.DropEvent.getType()) == 0) setupDropEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.DropEvent.getType());
    }

    private native void setupDropEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var onDrop = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.DropEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("onDrop"));
            obj.addProperties({onDrop: 
                function () {
                    var param = {"_this": this};
                    var ret = onDrop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("onDrop", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("onDrop"));
            obj.onDrop = 
                function () {
                    var param = {"_this": this};
                    var ret = onDrop(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("onDrop", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

	/**
     * This method is the programmatic equivalent of the user pressing the "Page Down" key while  this widget has the focus. 
     * It scrolls the widget's content downwards by the viewport  height, if the content can be scrolled that far downwards
     */
    public native void pageScrollDown() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "pageScrollDown", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.pageScrollDown();
    }-*/;

	/**
     * This method is the programmatic equivalent of the user pressing the "Page Up" key while  this widget has the focus.  It
     * scrolls the widget's content upwards by the viewport  height, if the content can be scrolled that far upwards
     */
    public native void pageScrollUp() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "pageScrollUp", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.pageScrollUp();
    }-*/;

    /**
     * Add a parentMoved handler.
     * <p>
     * Notification method fire when an ancestor of this component's position changes.
     *
     * @param handler the parentMoved handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addParentMovedHandler(com.smartgwt.client.widgets.events.ParentMovedHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.ParentMovedEvent.getType()) == 0) setupParentMovedEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.ParentMovedEvent.getType());
    }

    private native void setupParentMovedEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var parentMoved = $entry(function(){
            var param = {"_this": this, "parent" : arguments[0], "deltaX" : arguments[1], "deltaY" : arguments[2]};
            var event = @com.smartgwt.client.widgets.events.ParentMovedEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("parentMoved", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("parentMoved"));
            obj.addProperties({parentMoved:  parentMoved              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("parentMoved"));
            obj.parentMoved =  parentMoved             ;
        }
   }-*/;

	/**
     * Move this canvas to the specified point, or as close to the specified point as possible without this widget extending
     * beyond the edge of the browser viewport on any side.
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native void placeNear() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "placeNear", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.placeNear();
    }-*/;

    /**
     * @see Canvas#placeNear
     */
    public void placeNear(int left){
        placeNear(left, (Integer) null);
    }

	/**
     * Move this canvas to the specified point, or as close to the specified point as possible without this widget extending
     * beyond the edge of the browser viewport on any side.
     * @param left Left coordinate (defaults to mouse position)
     * @param top Top coordinate (defaults to mouse position)
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native void placeNear(int left, int top) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "placeNear", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.placeNear(left, top);
    }-*/;
	
	/**
     * Redraws the widget immediately with its current property values. Generally, if you want a Canvas to redraw, call
     * markForRedraw() - this will cause the Canvas to be redrawn when current processing ends, so that a series of
     * modifications made to a Canvas will cause it to redraw only once. Only call redraw() directly if you need immediate
     * responsiveness, for example you are redrawing in response to continuous mouse motion.
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public native void redraw() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "redraw", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.redraw();
    }-*/;

	/**
     * Redraws the widget immediately with its current property values. Generally, if you want a Canvas to redraw, call
     * markForRedraw() - this will cause the Canvas to be redrawn when current processing ends, so that a series of
     * modifications made to a Canvas will cause it to redraw only once. Only call redraw() directly if you need immediate
     * responsiveness, for example you are redrawing in response to continuous mouse motion.
     * @param reason reason for performing the redraw
     * @see com.smartgwt.client.docs.Drawing Drawing overview and related methods
     */
    public native void redraw(String reason) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "redraw", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.redraw(reason);
    }-*/;
	
	/**
     * Remove a child from this parent.
     * @param child Child canvas to remove from this parent.
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native void removeChild(Canvas child) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "removeChild", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.removeChild(child == null ? null : child.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Remove a child from this parent.
     * @param child Child canvas to remove from this parent.
     * @param name If the child canvas was assigned a name when added via addChild(), it                        should be passed in here to
     * ensure no reference is kept to the child
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native void removeChild(Canvas child, String name) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "removeChild", "Canvas,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.removeChild(child == null ? null : child.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), name);
    }-*/;
	
	/**
     * Remove a peer from this Canvas
     * @param peer Peer to be removed from this canvas
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native void removePeer(Canvas peer) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "removePeer", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.removePeer(peer == null ? null : peer.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Remove a peer from this Canvas
     * @param peer Peer to be removed from this canvas
     * @param name If this peer was assigned a name when added via addPeer(), it should                        be passed in here to ensure
     * no reference is kept to the peer
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native void removePeer(Canvas peer, String name) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "removePeer", "Canvas,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.removePeer(peer == null ? null : peer.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), name);
    }-*/;
	
	/**
     * Remove a candidate from {@link com.smartgwt.client.widgets.Canvas#getSnapAlignCandidates snapAlignCandidates}.  If the
     * passed widget was not actually a candidate, nothing happens and no warning is logged.
     * @param candidate 
     */
    public native void removeSnapAlignCandidate(Canvas candidate) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "removeSnapAlignCandidate", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.removeSnapAlignCandidate(candidate == null ? null : candidate.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Resizes the widget, adding deltaX to its width and deltaY to its height (moves the right          and/or bottom sides of
     * the widget).
     * @param deltaX amount to resize horizontally (may be negative)
     * @param deltaY amount to resize vertically (may be negative)
     *
     * @return whether the component actually changed size
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_resize" target="examples">Resize Example</a>
     */
    public native Boolean resizeBy(int deltaX, int deltaY) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "resizeBy", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.resizeBy(deltaX, deltaY);
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;
	
    /**
     * Add a resized handler.
     * <p>
     * Observable method called whenever a Canvas changes size. Note that if this canvas is {@link
     * com.smartgwt.client.widgets.Canvas#getOverflow overflow:"visible"}, and is waiting for a queued redraw (see {@link
     * com.smartgwt.client.widgets.Canvas#isDirty Canvas.isDirty()}), the value for {@link
     * com.smartgwt.client.widgets.Canvas#getVisibleWidth Canvas.getVisibleWidth()} and {@link
     * com.smartgwt.client.widgets.Canvas#getVisibleHeight Canvas.getVisibleHeight()} will be unreliable until
     * <code>redraw()</code> fires.
     *
     * @param handler the resized handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addResizedHandler(com.smartgwt.client.widgets.events.ResizedHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.ResizedEvent.getType()) == 0) setupResizedEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.ResizedEvent.getType());
    }

    private native void setupResizedEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var resized = $entry(function(){
            var param = {"_this": this};
            var event = @com.smartgwt.client.widgets.events.ResizedEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("resized", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("resized"));
            obj.addProperties({resized:  resized              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("resized"));
            obj.resized =  resized             ;
        }
   }-*/;

	/**
     * Resizes the widget to the specified width and height (moves the right and/ or bottom          sides of the widget). The
     * width and height parameters can be expressed as a percentage          of viewport size or as the number of pixels.
     * @param width new width for canvas.
     * @param height new height for canvas
     *
     * @return whether the size actually changed
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_resize" target="examples">Resize Example</a>
     */
    public native Boolean resizeTo(int width, int height) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "resizeTo", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.resizeTo(width, height);
        if(ret == null) return null;
        return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(ret);
    }-*/;
	
    /**
     * Add a rightMouseDown handler.
     * <p>
     * Executed when the right mouse button is pressed on this widget.  No default implementation.
     *
     * @param handler the rightMouseDown handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addRightMouseDownHandler(com.smartgwt.client.widgets.events.RightMouseDownHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.RightMouseDownEvent.getType()) == 0) setupRightMouseDownEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.RightMouseDownEvent.getType());
    }

    private native void setupRightMouseDownEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var rightMouseDown = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.RightMouseDownEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("rightMouseDown"));
            obj.addProperties({rightMouseDown: 
                function () {
                    var param = {"_this": this};
                    var ret = rightMouseDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("rightMouseDown", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("rightMouseDown"));
            obj.rightMouseDown = 
                function () {
                    var param = {"_this": this};
                    var ret = rightMouseDown(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("rightMouseDown", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

    /**
     * Add a ruleContextChanged handler.
     * <p>
     * Notification that the rule context gathered by the {@link com.smartgwt.client.widgets.Canvas#getRuleScope
     * Canvas.ruleScope} has changed.   <p> This notification fires only on the component designated as the {@link
     * com.smartgwt.client.widgets.Canvas#getRuleScope Canvas.ruleScope}; components that are merely contributing data to the
     * rule context do not fire <code>ruleContextChanged</code>.
     *
     * @param handler the ruleContextChanged handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addRuleContextChangedHandler(com.smartgwt.client.widgets.events.RuleContextChangedHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.RuleContextChangedEvent.getType()) == 0) setupRuleContextChangedEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.RuleContextChangedEvent.getType());
    }

    private native void setupRuleContextChangedEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var ruleContextChanged = $entry(function(){
            var param = {"_this": this, "data" : arguments[0]};
            var event = @com.smartgwt.client.widgets.events.RuleContextChangedEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("ruleContextChanged", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("ruleContextChanged"));
            obj.addProperties({ruleContextChanged:  ruleContextChanged              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("ruleContextChanged"));
            obj.ruleContextChanged =  ruleContextChanged             ;
        }
   }-*/;

	/**
     * Scroll this widget by some pixel increment in either (or both) direction(s).
     * @param dX Number of pixels to scroll horizontally
     * @param dY Number of pixels to scroll vertically
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollBy(int dX, int dY) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollBy", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollBy(dX, dY);
    }-*/;

	/**
     * Scroll this widget by some percentage of scroll size in either (or both) direction(s).
     * @param dX Percentage to scroll horizontally. Will accept either                                      a numeric percent value, or a
     * string like "10%".
     * @param dY Percentage to scroll horizontally. Will accept either                                      a numeric percent value, or a
     * string like "10%".
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollByPercent(int dX, int dY) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollByPercent", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollByPercent(dX, dY);
    }-*/;

    /**
     * Add a scrolled handler.
     * <p>
     * Notification that this component has just scrolled.  Use with  observation. <P> Fires for both CSS and {@link
     * com.smartgwt.client.widgets.Scrollbar "synthetic" scrollbars}.
     *
     * @param handler the scrolled handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addScrolledHandler(com.smartgwt.client.widgets.events.ScrolledHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.ScrolledEvent.getType()) == 0) setupScrolledEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.ScrolledEvent.getType());
    }

    private native void setupScrolledEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var scrolled = $entry(function(){
            var param = {"_this": this};
            var event = @com.smartgwt.client.widgets.events.ScrolledEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("scrolled", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("scrolled"));
            obj.addProperties({scrolled:  scrolled              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("scrolled"));
            obj.scrolled =  scrolled             ;
        }
   }-*/;

	/**
     * Scrolls the content of the widget so that the origin (top-left corner) of the content is left pixels to the left and top
     * pixels above the widget's top-left corner (but still clipped by the widget's dimensions). <p> This is guaranteed to be
     * called whenever this Canvas is scrolled, whether scrolling is initiated programmatically, by custom scrollbars, or a by
     * a native scrollbar.
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollTo() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollTo", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollTo();
    }-*/;

    /**
     * @see Canvas#scrollTo
     */
    public void scrollTo(Integer left){
        scrollTo(left, (Integer) null);
    }

	/**
     * Scrolls the content of the widget so that the origin (top-left corner) of the content is left pixels to the left and top
     * pixels above the widget's top-left corner (but still clipped by the widget's dimensions). <p> This is guaranteed to be
     * called whenever this Canvas is scrolled, whether scrolling is initiated programmatically, by custom scrollbars, or a by
     * a native scrollbar.
     * @param left the left coordinate
     * @param top the top coordinate
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollTo(Integer left, Integer top) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollTo", "Integer,Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollTo(left == null ? null : left.@java.lang.Integer::intValue()(), top == null ? null : top.@java.lang.Integer::intValue()());
    }-*/;
	
	/**
     * Vertically scrolls the content of the widget to the end of its content
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollToBottom() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollToBottom", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollToBottom();
    }-*/;

	/**
     * Horizontally scrolls the content of the widget to 0
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollToLeft() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollToLeft", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollToLeft();
    }-*/;

	/**
     * Scroll this widget to some position specified as a percentage of scroll size in either (or both) direction(s).
     * @param left Left Percentage position to scroll to                                        Will accept either a numeric percent value,
     * or a                                        string like "10%".
     * @param top Top Percentage position to scroll to                                       Will accept either a numeric percent value,
     * or a                                       string like "10%".
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollToPercent(int left, int top) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollToPercent", "int,int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollToPercent(left, top);
    }-*/;

	/**
     * Horizontally scrolls the content of the widget to the end of its content
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollToRight() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollToRight", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollToRight();
    }-*/;

	/**
     * Vertically scrolls the content of the widget to 0
     * @see com.smartgwt.client.docs.Scrolling Scrolling overview and related methods
     */
    public native void scrollToTop() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "scrollToTop", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.scrollToTop();
    }-*/;

	/**
     * Puts this widget at the bottom of the stacking order, so it appears behind all other widgets in the same parent.
     * @see com.smartgwt.client.docs.ZIndex ZIndex overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_layer" target="examples">Layer Example</a>
     */
    public native void sendToBack() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "sendToBack", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.sendToBack();
    }-*/;

	/**
     * Resizes the widget vertically to position its bottom edge at the specified coordinate. <P> NOTE: if you're setting
     * multiple coordinates, use setRect(), moveTo() or resizeTo() instead
     * @param bottom new bottom coordinate
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native void setBottom(int bottom) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setBottom", "int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setBottom(bottom);
    }-*/;

	/**
     * Enable or disable edit mode for this component. Components in editMode must be associated with an {@link
     * com.smartgwt.client.tools.EditNode} within an {@link com.smartgwt.client.tools.EditContext}. <P> Components with
     * editMode enabled support certain editing interactions which vary depending on the componentType and settings on the 
     * {@link com.smartgwt.client.widgets.Canvas#getEditProxy editProxy}. <p> To disable edit mode just pass
     * <code>editingOn</code> as false. The other parameters are not needed. <p> To enable edit mode on this component all
     * three parameters are required.  The <code>editNode</code> is the edit node for this component as it exists within the
     * <code>editContext</code>. <p> An alternative method, {@link com.smartgwt.client.tools.EditContext#enableEditing
     * EditContext.enableEditing()}, can be used when only an editContext and editNode are available. <p> Placing a component
     * into <code>editMode</code> causes the component's {@link com.smartgwt.client.widgets.Canvas#getEditProxy editProxy} to
     * be created.
     * @param editingOn true to enable editMode; false to disable
     * @see com.smartgwt.client.tools.EditTree
     * @see com.smartgwt.client.tools.EditContext
     */
    public native void setEditMode(boolean editingOn) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setEditMode", "boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setEditMode(editingOn);
    }-*/;

    /**
     * @see Canvas#setEditMode
     */
    public void setEditMode(boolean editingOn, EditContext editContext){
        setEditMode(editingOn, editContext, null);
    }

	/**
     * Enable or disable edit mode for this component. Components in editMode must be associated with an {@link
     * com.smartgwt.client.tools.EditNode} within an {@link com.smartgwt.client.tools.EditContext}. <P> Components with
     * editMode enabled support certain editing interactions which vary depending on the componentType and settings on the 
     * {@link com.smartgwt.client.widgets.Canvas#getEditProxy editProxy}. <p> To disable edit mode just pass
     * <code>editingOn</code> as false. The other parameters are not needed. <p> To enable edit mode on this component all
     * three parameters are required.  The <code>editNode</code> is the edit node for this component as it exists within the
     * <code>editContext</code>. <p> An alternative method, {@link com.smartgwt.client.tools.EditContext#enableEditing
     * EditContext.enableEditing()}, can be used when only an editContext and editNode are available. <p> Placing a component
     * into <code>editMode</code> causes the component's {@link com.smartgwt.client.widgets.Canvas#getEditProxy editProxy} to
     * be created.
     * @param editingOn true to enable editMode; false to disable
     * @param editContext the EditContext
     * @param editNode the EditNode
     * @see com.smartgwt.client.tools.EditTree
     * @see com.smartgwt.client.tools.EditContext
     */
    public native void setEditMode(boolean editingOn, EditContext editContext, EditNode editNode) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setEditMode", "boolean,EditContext,EditNode");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setEditMode(editingOn, editContext == null ? null : editContext.@com.smartgwt.client.core.BaseClass::getOrCreateJsObj()(), editNode == null ? null : editNode.@com.smartgwt.client.core.DataClass::getJsObj()());
    }-*/;
	
	/**
     * Set the URL of an image or SVG object element by name. <p> The element must have been created from HTML generated by
     * calling <code>canvas.imgHTML()</code> on this particular Canvas.
     * @param identifier name of the image to change, as originally passed to                              <code>imgHTML</code>
     * @param URL URL for the image.
     * See {@link com.smartgwt.client.docs.SCImgURL SCImgURL}
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public native void setImage(String identifier, String URL) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setImage", "String,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setImage(identifier, URL);
    }-*/;

	/**
     * Set the URL of an image or SVG object element by name. <p> The element must have been created from HTML generated by
     * calling <code>canvas.imgHTML()</code> on this particular Canvas.
     * @param identifier name of the image to change, as originally passed to                              <code>imgHTML</code>
     * @param URL URL for the image.
     * See {@link com.smartgwt.client.docs.SCImgURL SCImgURL}
     * @param imgDir optional image directory, overrides the default for this Canvas
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public native void setImage(String identifier, String URL, String imgDir) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setImage", "String,String,String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setImage(identifier, URL, imgDir);
    }-*/;
	
	/**
     * Set the page-relative left coordinate of this widget.
     * @param left new left coordinate in pixels
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native void setPageLeft(int left) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setPageLeft", "int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setPageLeft(left);
    }-*/;

	/**
     * Set the page-relative top coordinate of this widget.
     * @param top new top coordinate in pixels
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native void setPageTop(int top) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setPageTop", "int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setPageTop(top);
    }-*/;

	/**
     * Sets this Canvas's "panel container".  A panel container is a widget that manages a  collection of panels, like a {@link
     * com.smartgwt.client.widgets.tab.TabSet} or {@link com.smartgwt.client.widgets.layout.SectionStack}.  Smart GWT uses this
     * method internally when child panels are added to panel container widgets; if you need to create a panel container widget
     * that does not extend one of the built-in ones (these are <code>TabSet</code>, <code>SectionStack</code> and {@link
     * com.smartgwt.client.widgets.Window}), your code  should do the same thing.
     * @param container The container widget for this canvas
     */
    public native void setPanelContainer(Canvas container) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setPanelContainer", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setPanelContainer(container == null ? null : container.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Assign a relative tab position for this canvas. The meaning of a "relative" tab position varies depending on where the
     * canvas is in the page. <P> For canvases with no specified {@link com.smartgwt.client.widgets.Canvas#getParentCanvas
     * parent canvas}, (or where {@link com.smartgwt.client.widgets.Canvas#getUpdateTabPositionOnReparent
     * updateTabPositionOnReparent} is false), this method will and move the canvas to the appropriate tab-position among other
     * top level canvases. It will also disable {@link com.smartgwt.client.widgets.Canvas#getUpdateTabPositionOnDraw
     * updateTabPositionOnDraw} so if this method is called before draw, drawing this canvas will not cause its tab position to
     * change. <P> For canvases embedded in a {@link com.smartgwt.client.widgets.Canvas#getParentCanvas getParentCanvas()},
     * this method will move the canvas to the appropriate tab position among the other children of the parent.<br>
     * <b>Implementation note:</b> This is achieved by setting an internal property to indicate the new tab position which will
     * be respected by the default  {@link com.smartgwt.client.widgets.Canvas#getChildTabPosition getChildTabPosition()}
     * implementation, and calling  {@link com.smartgwt.client.widgets.Canvas#updateChildTabPositions
     * updateChildTabPositions()} to implement a reflow. Therefore if {@link
     * com.smartgwt.client.widgets.Canvas#getChildTabPosition getChildTabPosition()} has been overridden, this method may have
     * no effect. <P> As with other APIs related to {@link com.smartgwt.client.widgets.TabIndexManager tab index management},
     * tab indices are treated as a hierarchy by default. By setting the relative tab position of a canvas which is not itself
     * focusable but has focusable descendents, these descendents' tab position will be updated. <P> Note that after this
     * method has been called, the tab position can be modified by subsequent code to shift another sibling in front of this
     * one, or reparent this canvas.
     * @param position new relative tab position
     */
    public native void setRelativeTabPosition(Integer position) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setRelativeTabPosition", "Integer");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setRelativeTabPosition(position == null ? null : position.@java.lang.Integer::intValue()());
    }-*/;

	/**
     * Resizes the widget horizontally to position its right side at the specified coordinate. <P> NOTE: if you're setting
     * multiple coordinates, use setRect(), moveTo() or resizeTo() instead
     * @param right new right coordinate
     * @see com.smartgwt.client.docs.Sizing Sizing overview and related methods
     */
    public native void setRight(int right) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "setRight", "int");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setRight(right);
    }-*/;

	/**
     * If this widget is showing scrollbars, and a user drags close to the edge of the viewport, should we scroll the viewport
     * in the appropriate direction? Returns this.canDragScroll if there are scrollbars, else false.
     * @see com.smartgwt.client.docs.Dragdrop Dragdrop overview and related methods
     */
    public native void shouldDragScroll() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "shouldDragScroll", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.shouldDragScroll();
    }-*/;

	/**
     * Sets this widget's visibility to "inherit", so that it becomes visible if all of its parents are visible or it has no
     * parents. <P> If the widget has not yet been drawn (and doesn't have a parent or master), this method calls the draw
     * method as well.
     * @see com.smartgwt.client.docs.Visibility Visibility overview and related methods
     * @see <a href="http://www.smartclient.com/smartgwt/showcase/#basics_components_showhide" target="examples">Show & Hide Example</a>
     */
    public native void show() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "show", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.show();
    }-*/;

	/**
     * Temporariy block all user interaction with children of this widget, with the exception of those passed in in the
     * <code>unmaskedChildren</code> parameter. Children will remain blocked until {@link
     * com.smartgwt.client.widgets.Canvas#hideComponentMask hideComponentMask()} is called. <P> This method will show the
     * {@link com.smartgwt.client.widgets.Canvas#getComponentMask componentMask} canvas to block mouse interaction with
     * children, and temporarily remove masked children from the page's tab-order. <P> This behavior differs from the standard
     * {@link com.smartgwt.client.widgets.Canvas#showClickMask click mask} in that the modal mask shown by {@link
     * com.smartgwt.client.widgets.Canvas#showClickMask showClickMask()} will cover the entire screen and typically only  allow
     * "unmasking" of top level components. <P> Use {@link com.smartgwt.client.widgets.Canvas#hideComponentMask
     * hideComponentMask()} to hide the component level mask.
     */
    public native void showComponentMask() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "showComponentMask", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.showComponentMask();
    }-*/;

	/**
     * Temporariy block all user interaction with children of this widget, with the exception of those passed in in the
     * <code>unmaskedChildren</code> parameter. Children will remain blocked until {@link
     * com.smartgwt.client.widgets.Canvas#hideComponentMask hideComponentMask()} is called. <P> This method will show the
     * {@link com.smartgwt.client.widgets.Canvas#getComponentMask componentMask} canvas to block mouse interaction with
     * children, and temporarily remove masked children from the page's tab-order. <P> This behavior differs from the standard
     * {@link com.smartgwt.client.widgets.Canvas#showClickMask click mask} in that the modal mask shown by {@link
     * com.smartgwt.client.widgets.Canvas#showClickMask showClickMask()} will cover the entire screen and typically only  allow
     * "unmasking" of top level components. <P> Use {@link com.smartgwt.client.widgets.Canvas#hideComponentMask
     * hideComponentMask()} to hide the component level mask.
     * @param unmaskedChildren Children passed into this parameter will continue to       be interactive while other children are blocked. They will be
     * moved above the componentMask in      the page's z-order and remain accessible via keyboard navigation.  Note that this
     * array should      contain direct children of this widget only.
     */
    public native void showComponentMask(Canvas... unmaskedChildren) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "showComponentMask", "Canvas...");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.showComponentMask(@com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(unmaskedChildren));
    }-*/;
	
    /**
     * Add a showContextMenu handler.
     * <p>
     * Executed when the right mouse button is clicked.  The default implementation of this method auto-creates a {@link
     * com.smartgwt.client.widgets.menu.Menu} from the {@link com.smartgwt.client.widgets.Canvas#getContextMenu
     * Canvas.contextMenu} property on this component and then calls {@link
     * com.smartgwt.client.widgets.menu.Menu#showContextMenu Menu.showContextMenu()} on it to show it. <p> If you want to show
     * a standard context menu, you can simply define your Menu and set it as the contextMenu property on your component - you
     * do not need to override this method. <p> If you want to do some other processing before showing a menu or do something
     * else entirely, then you should override this method.  Note that the return value from this method controls whether or
     * not the native browser context menu is shown.
     *
     * @param handler the showContextMenu handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addShowContextMenuHandler(com.smartgwt.client.widgets.events.ShowContextMenuHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.ShowContextMenuEvent.getType()) == 0) setupShowContextMenuEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.ShowContextMenuEvent.getType());
    }

    private native void setupShowContextMenuEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var showContextMenu = $debox($entry(function(param){
            var event = @com.smartgwt.client.widgets.events.ShowContextMenuEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            var ret = event.@com.smartgwt.client.event.Cancellable::isCancelled()();
            return !ret;
        }));
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("showContextMenu"));
            obj.addProperties({showContextMenu: 
                function () {
                    var param = {"_this": this};
                    var ret = showContextMenu(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("showContextMenu", arguments);
                    }
                    return ret;
                }
             });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("showContextMenu"));
            obj.showContextMenu = 
                function () {
                    var param = {"_this": this};
                    var ret = showContextMenu(param) == true;
                    if (ret && hasDefaultHandler) {
                        ret = this.Super("showContextMenu", arguments);
                    }
                    return ret;
                }
            ;
        }
   }-*/;

	/**
     * Show this widget next to another widget on the page, positioned such that it will  not extend beyond the browser
     * viewport. <P> Note that this method simply sets the coordinates of the widget and displays it (using a {@link
     * com.smartgwt.client.widgets.Canvas#animateShow animateShow()} by default). It will not change the {@link
     * com.smartgwt.client.widgets.Canvas#getParentElement parentElement} of either component. <P> An example use case might be
     * showing a menu next to a menu-button.
     * @param otherWidget Canvas to show next to
     */
    public native void showNextTo(Canvas otherWidget) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "showNextTo", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.showNextTo(otherWidget == null ? null : otherWidget.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

    /**
     * @see Canvas#showNextTo
     */
    public void showNextTo(Canvas otherWidget, String side){
        showNextTo(otherWidget, side, false, false);
    }

    /**
     * @see Canvas#showNextTo
     */
    public void showNextTo(Canvas otherWidget, String side, boolean canOcclude){
        showNextTo(otherWidget, side, canOcclude, false);
    }

	/**
     * Show this widget next to another widget on the page, positioned such that it will  not extend beyond the browser
     * viewport. <P> Note that this method simply sets the coordinates of the widget and displays it (using a {@link
     * com.smartgwt.client.widgets.Canvas#animateShow animateShow()} by default). It will not change the {@link
     * com.smartgwt.client.widgets.Canvas#getParentElement parentElement} of either component. <P> An example use case might be
     * showing a menu next to a menu-button.
     * @param otherWidget Canvas to show next to
     * @param side Which side of the other canvas should we put. Options are                                  "top", "bottom", "left",
     * "right". (Defaults to "right")
     * @param canOcclude This argument controls whether this canvas can be positioned on top of the other  widget if there isn't room to put it
     * next to the other widget extending out of the  browser viewport<br>  If 'canOcclude' is true, simply shift this widget
     * over the other widget, so that  it ends up onscreen.  If 'canOcclude' is false, avoid extending offscreen  by
     * positioning this widget on the other side of the other widget.
     * @param skipAnimation If <code>false</code> do not use an animation to  show the component.
     */
    public native void showNextTo(Canvas otherWidget, String side, boolean canOcclude, boolean skipAnimation) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "showNextTo", "Canvas,String,boolean,boolean");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.showNextTo(otherWidget == null ? null : otherWidget.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), side, canOcclude, skipAnimation);
    }-*/;
	
	/**
     * Recursively show the canvas and all its parents so the canvas will be visible. <P> If the widget has not yet been drawn,
     * this method calls the draw method as well.
     * @see com.smartgwt.client.docs.Visibility Visibility overview and related methods
     */
    public native void showRecursively() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "showRecursively", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.showRecursively();
    }-*/;

	/**
     * Ensure that a specific child is slotted correctly into the page's tab order. Default implementation will, if {@link
     * com.smartgwt.client.widgets.Canvas#getUpdateTabPositionOnReparent updateTabPositionOnReparent} is true, ensure  the
     * child canvas shows up in the {@link com.smartgwt.client.widgets.TabIndexManager} tree under the entry for this widget
     * (the parent), in the position returned  by {@link com.smartgwt.client.widgets.Canvas#getChildTabPosition
     * getChildTabPosition()}. <P> This method is called automatically in cases where a single child's tab position may need to
     * be updated - such as if a child is added to a drawn parent. <P> See also {@link
     * com.smartgwt.client.widgets.Canvas#updateChildTabPositions updateChildTabPositions()}
     * @param child child to have tab position updated.
     */
    public native void updateChildTabPosition(Canvas child) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "updateChildTabPosition", "Canvas");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.updateChildTabPosition(child == null ? null : child.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
    }-*/;

	/**
     * Update the childrens' tab positions, slotting them under this widget in the {@link
     * com.smartgwt.client.widgets.TabIndexManager}, in the order defined by {@link
     * com.smartgwt.client.widgets.Canvas#getChildTabPosition getChildTabPosition()}. This method will skip any children where
     * {@link com.smartgwt.client.widgets.Canvas#getUpdateTabPositionOnReparent updateTabPositionOnReparent} is false. <P> This
     * method is called automatically on canvas draw(). It may be overridden by subclasses for custom tab-order behavior.
     */
    public native void updateChildTabPositions() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "updateChildTabPositions", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.updateChildTabPositions();
    }-*/;

	/**
     * If this canvas is currently showing a hover (see {@link com.smartgwt.client.widgets.Canvas#handleHover handleHover()}),
     * this method can be called to update the contents of the hover. Has no effect if the hover canvas is not showing for this
     * widget.
     */
    public native void updateHover() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "updateHover", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.updateHover();
    }-*/;

	/**
     * If this canvas is currently showing a hover (see {@link com.smartgwt.client.widgets.Canvas#handleHover handleHover()}),
     * this method can be called to update the contents of the hover. Has no effect if the hover canvas is not showing for this
     * widget.
     * @param hoverHTML Option to specify new HTML for the hover. If not passed, the result   of {@link
     * com.smartgwt.client.widgets.Canvas#getHoverHTML this.getHoverHTML()} will be used instead. Note that if the   hover HTML
     * is empty the hover will be hidden.
     */
    public native void updateHover(String hoverHTML) /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "updateHover", "String");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.updateHover(hoverHTML);
    }-*/;
	
	/**
     * This method is executed on draw. Default implementation for top-level widgets ensures  this widget is at the end of the
     * tab-sequence. <P> Has no effect if this canvas is embedded in a {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas parent}.
     */
    public native void updateTabPositionForDraw() /*-{
        if (this.@com.smartgwt.client.widgets.BaseWidget::isConfigOnly()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPostConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)(this.@java.lang.Object::getClass()(), "updateTabPositionForDraw", "");
        }
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.updateTabPositionForDraw();
    }-*/;

    /**
     * Add a visibilityChanged handler.
     * <p>
     * Notification  fired when this canvas becomes visible or hidden to the user. Note - this method is fired when the {@link
     * com.smartgwt.client.widgets.Canvas#isVisible Canvas.isVisible()} state of this component changes. It may be fired in
     * response an explicit call to {@link com.smartgwt.client.widgets.Canvas#show Canvas.show()} or {@link
     * com.smartgwt.client.widgets.Canvas#hide Canvas.hide()}, or in response to a parent component being shown or hidden when
     * this widgets {@link com.smartgwt.client.widgets.Canvas#getVisibility Canvas.visibility} is set to "inherit". <P> Note
     * that a call to {@link com.smartgwt.client.widgets.Canvas#show Canvas.show()} or {@link
     * com.smartgwt.client.widgets.Canvas#hide Canvas.hide()} will not <b>always</b> fire this notification. If this widget has
     * a hidden parent, show or hide would change this components {@link com.smartgwt.client.widgets.Canvas#getVisibility
     * Canvas.visibility} property, and may update the CSS visibility attribute of the drawn handle in the DOM, but would not
     * actually hide or reveal the component to the user and as such the notification would not fire. <P> Note also that this
     * notification will only be fired for components which have been {@link com.smartgwt.client.widgets.Canvas#draw drawn}.
     *
     * @param handler the visibilityChanged handler
     * @return {@link HandlerRegistration} used to remove this handler
     */
    public HandlerRegistration addVisibilityChangedHandler(com.smartgwt.client.widgets.events.VisibilityChangedHandler handler) {
        if(getHandlerCount(com.smartgwt.client.widgets.events.VisibilityChangedEvent.getType()) == 0) setupVisibilityChangedEvent();
        return doAddHandler(handler, com.smartgwt.client.widgets.events.VisibilityChangedEvent.getType());
    }

    private native void setupVisibilityChangedEvent() /*-{
        var obj;
        var selfJ = this;
        var hasDefaultHandler;
        var visibilityChanged = $entry(function(){
            var param = {"_this": this, "isVisible" : arguments[0]};
            var event = @com.smartgwt.client.widgets.events.VisibilityChangedEvent::new(Lcom/google/gwt/core/client/JavaScriptObject;)(param);
            selfJ.@com.smartgwt.client.widgets.BaseWidget::fireEvent(Lcom/google/gwt/event/shared/GwtEvent;)(event);
            if (hasDefaultHandler) this.Super("visibilityChanged", arguments);
        });
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
            hasDefaultHandler = $wnd.isc.isA.Function(obj.getProperty("visibilityChanged"));
            obj.addProperties({visibilityChanged:  visibilityChanged              });
        } else {
            obj = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
            var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
            hasDefaultHandler = $wnd.isc.isA.Function($wnd.isc[scClassName].getInstanceProperty("visibilityChanged"));
            obj.visibilityChanged =  visibilityChanged             ;
        }
   }-*/;


    // ********************* Static Methods ***********************


	/**
     * Check if an event is within an "edge" of this canvas.
     *
     * @return edge where the mouse is positioned, or null if not within a legal edge                    (including being in the
     * center)
     * @see com.smartgwt.client.widgets.Canvas#getResizeFrom
     */
    public static native EdgeName getEventEdge() /*-{
        var ret = $wnd.isc.Canvas.getEventEdge();
        if(ret == null) return null;
        var enumValues = @com.smartgwt.client.types.EdgeName::values()();
        return @com.smartgwt.client.util.EnumUtil::getEnum([Lcom/smartgwt/client/types/ValueEnum;Ljava/lang/String;)(enumValues, ret);
    }-*/;

	/**
     * Check if an event is within an "edge" of this canvas.
     * @param edgeMask Array of legal edges.  Default is all the edges that                                       allow resizing (see {@link
     * com.smartgwt.client.widgets.Canvas#getResizeFrom resizeFrom})
     *
     * @return edge where the mouse is positioned, or null if not within a legal edge                    (including being in the
     * center)
     * @see com.smartgwt.client.widgets.Canvas#getResizeFrom
     */
    public static native EdgeName getEventEdge(EdgeName... edgeMask) /*-{
        var ret = $wnd.isc.Canvas.getEventEdge(@com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(edgeMask));
        if(ret == null) return null;
        var enumValues = @com.smartgwt.client.types.EdgeName::values()();
        return @com.smartgwt.client.util.EnumUtil::getEnum([Lcom/smartgwt/client/types/ValueEnum;Ljava/lang/String;)(enumValues, ret);
    }-*/;
	



	/**
     * Return the position for <code>snapper</code> to be placed in order to "snap to" an edge or  corner of
     * <code>target</code>, in the same sense as {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo}.  <p>  Default for
     * <code>snapEdge</code> is the <b>opposite</b> edge or corner from  <code>snapTo</code>.  For example, <code>snapTo</code>
     * of "T" (top) means <code>snapEdge</code>  will default to "B" (bottom), so the returned coordinates would place
     * <code>snapper</code>  centered along the top edge of <code>target</code>.  A <code>snapTo</code> of "TL" (top left) 
     * means <code>snapEdge</code> will default to "BR" (bottom right), so the returned coordinates  would place the bottom
     * right of <code>snapper</code> at the top left corner of  <code>target</code>.  <p>  <code>target</code> can be passed as
     * either a Canvas or 4-element Array giving the top, left,  width and height of the target.
     * @param target canvas to snap to
     * @param snapTo edge against which to snap
     * @param snapper canvas being snapped
     *
     * @return the position for <code>snapper</code> to be placed in order to "snap to" an edge or  corner of <code>target</code>
     */
    public static native Point getSnapPosition(Canvas target, String snapTo, Canvas snapper) /*-{
        var ret = $wnd.isc.Canvas.getSnapPosition(target == null ? null : target.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), snapTo, snapper == null ? null : snapper.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        if(ret == null) return null;
        return @com.smartgwt.client.widgets.drawing.Point::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

	/**
     * Return the position for <code>snapper</code> to be placed in order to "snap to" an edge or  corner of
     * <code>target</code>, in the same sense as {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo}.  <p>  Default for
     * <code>snapEdge</code> is the <b>opposite</b> edge or corner from  <code>snapTo</code>.  For example, <code>snapTo</code>
     * of "T" (top) means <code>snapEdge</code>  will default to "B" (bottom), so the returned coordinates would place
     * <code>snapper</code>  centered along the top edge of <code>target</code>.  A <code>snapTo</code> of "TL" (top left) 
     * means <code>snapEdge</code> will default to "BR" (bottom right), so the returned coordinates  would place the bottom
     * right of <code>snapper</code> at the top left corner of  <code>target</code>.  <p>  <code>target</code> can be passed as
     * either a Canvas or 4-element Array giving the top, left,  width and height of the target.
     * @param target canvas to snap to
     * @param snapTo edge against which to snap
     * @param snapper canvas being snapped
     * @param snapEdge optional edge to snapTo. Default is the <b>opposite</b> edge or corner from  <code>snapTo</code>
     *
     * @return the position for <code>snapper</code> to be placed in order to "snap to" an edge or  corner of <code>target</code>
     */
    public static native Point getSnapPosition(Canvas target, String snapTo, Canvas snapper, String snapEdge) /*-{
        var ret = $wnd.isc.Canvas.getSnapPosition(target == null ? null : target.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), snapTo, snapper == null ? null : snapper.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), snapEdge);
        if(ret == null) return null;
        return @com.smartgwt.client.widgets.drawing.Point::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;
	





	/**
     * Registers one or more CSS classes to have their padding adjusted (independently on all edges)
     * according to the {@link com.smartgwt.client.widgets.Canvas#resizePadding padding size change} applied to the page.  Each
     * class
     *  to be registered is provided along with a corresponding baseline class, and a single
     *  <code>targetSizeChange</code> is specified for all the classes.  The padding in each
     *  registered class is adjusted downward towards the baseline as the padding size change
     *  approaches 0 (no resizing), and upward as it increases, so that it exactly equals the
     *  declared style's padding at a padding size change of <code>targetSizeChange</code>.
     *  <P>
     *  Note that each call to this method replaces the registration of the previous call (if any),
     *  and will have no effect until {@link com.smartgwt.client.widgets.Canvas#resizePadding resizePadding()} is called.
     *  <P>
     *  For example:<pre>
     *     isc.Canvas.registerFontScaledPaddingStyles(
     *         [        "tabButtonTop",         "tabButtonBottom"], 
     *         ["iconOnlyTabButtonTop", "iconOnlyTabButtonBottom"],
     *         3
     *     );
     *  </pre>
     *  In this case, the CSS style "tabButtonTop" will have its padding adjusted downward to the
     *  padding from the baseline CSS style "iconOnlyTabButtonTop" style at a <code>sizeChange</code>
     *  of 0, and be left unchanged at a <code>sizeChange</code> of 3.
     * 
     * @param scaledStyles styles whose padding should be adjusted
     * @param baselineStyles corresponding baseline reference styles
     * @param targetSizeChange sizeChange at which scaledStyles are unchanged
     * @see com.smartgwt.client.widgets.Canvas#resizeFonts
     * @see com.smartgwt.client.widgets.Canvas#resizePadding
     * @see com.smartgwt.client.widgets.Canvas#resizeControls
     * @see com.smartgwt.client.widgets.Canvas#registerIconSizingAttributes
     */
    public static native void registerFontScaledPaddingStyles(String[] scaledStyles, String[] baselineStyles, int targetSizeChange) /*-{
        $wnd.isc.Canvas.registerFontScaledPaddingStyles(@com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(scaledStyles), @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(baselineStyles), targetSizeChange);
    }-*/;



	/**
     * Change the value of attributes registered via {@link
     * com.smartgwt.client.widgets.Canvas#registerAutoChildSizingAttributes registerAutoChildSizingAttributes()} by some number
     * of pixels. This method may be invoked automatically from {@link com.smartgwt.client.widgets.Canvas#resizeControls
     * resizeControls()} or {@link com.smartgwt.client.widgets.Canvas#resizeFonts resizeFonts()} <P> Must be called after the
     * skin has been loaded, but before any components are created.
     * @param policy Which set of attributes should be resized? This should correspond to the    sizing policy argument applied when    
     * {@link com.smartgwt.client.widgets.Canvas#registerAutoChildSizingAttributes registering the icon sizing attributes}.
     * @param delta number of pixels to increase or decrease from current size
     */
    public static native void resizeAutoChildAttributes(String policy, int delta) /*-{
        $wnd.isc.Canvas.resizeAutoChildAttributes(policy, delta);
    }-*/;


	/**
     * Change the basic size of UI components in the current skin by "delta" pixels.  Must be called after the skin has been
     * loaded, but before any components are created. <p> The size of a text input control implies the size of most other
     * controls: <ul> <li> all other FormItems (eg selects) need to be the same size or mixed controls will look      odd. 
     * This includes Buttons <li> anything that potentially contains a FormItem needs to be as tall or slightly taller:     
     * this includes grid row (inline editing), {@link com.smartgwt.client.widgets.Window#getHeaderControls Window headers},   
     * TabBar and SectionHeaders </ul> <p> Because of this necessary uniformity, just specifying a single pixel value is enough
     * for the framework to resize all core controls, with several caveats: <ul> <li> skins that make extensive use of images
     * (eg TreeFrog) will stretch those images, which      may result in ugly artifacts in some combinations of operating
     * system and browser, for      which no workaround is possible.  For this reason, <code>resizeControls()</code> is     
     * only officially supported for Tahoe, Enterprise, EnterpriseBlue and Graphite skins, and only in      {@link
     * com.smartgwt.client.docs.Skinning CSS3 Mode} <li> even in Enterprise-series skins, {@link
     * com.smartgwt.client.widgets.tree.TreeGrid#getShowConnectors tree connector lines}       vertically stretch, becoming
     * obviously blurry and misshapen with an increase of 4-5px.      To avoid this, replace the tree connector media (see
     * {@link com.smartgwt.client.widgets.tree.TreeGrid#getConnectorImage TreeGrid.connectorImage}). <li> {@link
     * com.smartgwt.client.widgets.form.fields.FormItemIcon}s are not resized by default, because stretched icons generally
     * look      worse than non-scaled icons that are a bit smaller than the input field <li> images that use {@link
     * com.smartgwt.client.docs.Skinning spriting} will not be stretched because the sizes      for these controls are embedded
     * in CSS.  In most cases, this is desirable; for example,      the downward chevron shape used for SelectItem controls
     * doesn't stretch, and looks      better that way. </ul>
     * @param delta number of pixels to increase or decrease from current size
     */
    public static native void resizeControls(int delta) /*-{
        $wnd.isc.Canvas.resizeControls(delta);
    }-*/;


	/**
     * Modify the size of fonts for some or all stylesheets defined in the page. <p> This method can be used to dynamically
     * increase or decrease font size for all of the fonts in your application, or just fonts defined in your chosen Smart GWT
     * skin (the latter can be achieved by passing <code>styleSheets</code> as "skin_styles.css" - the default name for the CSS
     * file used in each Smart GWT skin). <p> <code>resizeFonts()</code> must be called after the skin has been loaded, and
     * before any components have been created.  Calling <code>resizeFonts()</code> at a later time is not supported (you will
     * notice that font sizes still increase, however, this approach is not supported). <p> Some browsers will disallow access
     * or modification of styleSheets if they are loaded from a domain that is different from the loading page.  In this case
     * <code>resizeFonts()</code> cannot be used. <p> This method has a small performance penalty which depends on the browser,
     * number of stylesheets being modified, and age of your machine.  With modern browsers on modern machines resizing just
     * skin fonts, the impact is basically negligible (&lt;5ms). <p> Certain controls such as icons are resized when fonts are
     * resized (see  {@link com.smartgwt.client.widgets.Canvas#setAutoResizeIcons setAutoResizeIcons()} and {@link
     * com.smartgwt.client.widgets.Canvas#setAutoResizeAutoChildAttributes setAutoResizeAutoChildAttributes()}) so you might
     * want to set <code>resizeRelatedControls</code> to <code>false</code> where you are just trying to make fonts in a
     * dynamically loaded stylesheet match previously loaded fonts, but controls such as icons should not be resized upwards
     * again.
     * @param sizeChange size to change fonts by.  Can be negative to shrink fonts
     */
    public static native void resizeFonts(int sizeChange) /*-{
        $wnd.isc.Canvas.resizeFonts(sizeChange);
    }-*/;

    /**
     * @see Canvas#resizeFonts
     */
    public static void resizeFonts(int sizeChange, String styleSheets){
        resizeFonts(sizeChange, styleSheets, null);
    }

	/**
     * Modify the size of fonts for some or all stylesheets defined in the page. <p> This method can be used to dynamically
     * increase or decrease font size for all of the fonts in your application, or just fonts defined in your chosen Smart GWT
     * skin (the latter can be achieved by passing <code>styleSheets</code> as "skin_styles.css" - the default name for the CSS
     * file used in each Smart GWT skin). <p> <code>resizeFonts()</code> must be called after the skin has been loaded, and
     * before any components have been created.  Calling <code>resizeFonts()</code> at a later time is not supported (you will
     * notice that font sizes still increase, however, this approach is not supported). <p> Some browsers will disallow access
     * or modification of styleSheets if they are loaded from a domain that is different from the loading page.  In this case
     * <code>resizeFonts()</code> cannot be used. <p> This method has a small performance penalty which depends on the browser,
     * number of stylesheets being modified, and age of your machine.  With modern browsers on modern machines resizing just
     * skin fonts, the impact is basically negligible (&lt;5ms). <p> Certain controls such as icons are resized when fonts are
     * resized (see  {@link com.smartgwt.client.widgets.Canvas#setAutoResizeIcons setAutoResizeIcons()} and {@link
     * com.smartgwt.client.widgets.Canvas#setAutoResizeAutoChildAttributes setAutoResizeAutoChildAttributes()}) so you might
     * want to set <code>resizeRelatedControls</code> to <code>false</code> where you are just trying to make fonts in a
     * dynamically loaded stylesheet match previously loaded fonts, but controls such as icons should not be resized upwards
     * again.
     * @param sizeChange size to change fonts by.  Can be negative to shrink fonts
     * @param styleSheets optional regular expression pattern for matching stylesheets
     * @param resizeRelatedControls resize icons and autoChild attributes? Set to false        to suppress default behavior.
     */
    public static native void resizeFonts(int sizeChange, String styleSheets, Boolean resizeRelatedControls) /*-{
        $wnd.isc.Canvas.resizeFonts(sizeChange, styleSheets, resizeRelatedControls == null ? null : resizeRelatedControls.@java.lang.Boolean::booleanValue()());
    }-*/;
	

	/**
     * Change the basic size of icons in the current skin by "delta" pixels. This method may be invoked automatically from
     * {@link com.smartgwt.client.widgets.Canvas#resizeControls resizeControls()}. <P> Must be called after the skin has been
     * loaded, but before any components are created.
     * @param policy Which set of icons should be resized? This should correspond to the    iconSizingPolicy argument applied when     {@link
     * com.smartgwt.client.widgets.Canvas#registerIconSizingAttributes registering the icon sizing attributes}.
     * @param delta number of pixels to increase or decrease from current size
     */
    public static native void resizeIcons(String policy, int delta) /*-{
        $wnd.isc.Canvas.resizeIcons(policy, delta);
    }-*/;


	/**
     * Modify the amount of padding for some CSS styles defined for the page.  Only CSS styles registered by {@link
     * com.smartgwt.client.widgets.Canvas#registerFontScaledPaddingStyles registerFontScaledPaddingStyles()} are modified. <p>
     * <code>resizePadding()</code> must be called after the skin has been loaded, and before any components have been created.
     * Calling <code>resizePadding()</code> at a later time is not supported (you will notice that padding is modified,
     * however, this approach is not supported). <p> This method has similar browser security limitations as {@link
     * com.smartgwt.client.widgets.Canvas#resizeFonts resizeFonts()}. <P> The intent is that the same font size change be
     * passed to this method as is passed to {@link com.smartgwt.client.widgets.Canvas#resizeFonts resizeFonts()}, so that the
     * <code>targetSizeChange</code> in the call to {@link com.smartgwt.client.widgets.Canvas#registerFontScaledPaddingStyles
     * registerFontScaledPaddingStyles()} represents the right font size for the unadjusted styles being registered.
     * @param fontSizeChange size change to apply to the padding of registered styles, so that                             they aren't changed at all
     * at the size change passed to                              {@link
     * com.smartgwt.client.widgets.Canvas#registerFontScaledPaddingStyles registerFontScaledPaddingStyles()}, and the padding
     * is                             reduced to baseline style levels at a zero size change.
     * @see com.smartgwt.client.widgets.Canvas#resizeFonts
     * @see com.smartgwt.client.widgets.Canvas#resizeControls
     */
    public static native void resizePadding(int fontSizeChange) /*-{
        $wnd.isc.Canvas.resizePadding(fontSizeChange);
    }-*/;

	/**
     * Modify the amount of padding for some CSS styles defined for the page.  Only CSS styles registered by {@link
     * com.smartgwt.client.widgets.Canvas#registerFontScaledPaddingStyles registerFontScaledPaddingStyles()} are modified. <p>
     * <code>resizePadding()</code> must be called after the skin has been loaded, and before any components have been created.
     * Calling <code>resizePadding()</code> at a later time is not supported (you will notice that padding is modified,
     * however, this approach is not supported). <p> This method has similar browser security limitations as {@link
     * com.smartgwt.client.widgets.Canvas#resizeFonts resizeFonts()}. <P> The intent is that the same font size change be
     * passed to this method as is passed to {@link com.smartgwt.client.widgets.Canvas#resizeFonts resizeFonts()}, so that the
     * <code>targetSizeChange</code> in the call to {@link com.smartgwt.client.widgets.Canvas#registerFontScaledPaddingStyles
     * registerFontScaledPaddingStyles()} represents the right font size for the unadjusted styles being registered.
     * @param fontSizeChange size change to apply to the padding of registered styles, so that                             they aren't changed at all
     * at the size change passed to                              {@link
     * com.smartgwt.client.widgets.Canvas#registerFontScaledPaddingStyles registerFontScaledPaddingStyles()}, and the padding
     * is                             reduced to baseline style levels at a zero size change.
     * @param styleSheets optional regular expression pattern for matching stylesheets
     * @see com.smartgwt.client.widgets.Canvas#resizeFonts
     * @see com.smartgwt.client.widgets.Canvas#resizeControls
     */
    public static native void resizePadding(int fontSizeChange, String styleSheets) /*-{
        $wnd.isc.Canvas.resizePadding(fontSizeChange, styleSheets);
    }-*/;
	

	/**
     * Changes the system-wide {@link com.smartgwt.client.widgets.Canvas#allowExternalFilters allowExternalFilters} setting.
     * @param allExternalFilters new setting
     */
    public static native void setAllowExternalFilters(boolean allExternalFilters) /*-{
        $wnd.isc.Canvas.setAllowExternalFilters(allExternalFilters);
    }-*/;


	/**
     * Should registered autoChild attributes be automatically resized with  {@link
     * com.smartgwt.client.widgets.Canvas#resizeControls controls} and {@link com.smartgwt.client.widgets.Canvas#resizeFonts
     * text}? <P> If true, attributes registered for resize with policy <code>"controls"</code> will be resized when
     * <code>resizeControls()</code> runs, and icons registered with policy <code>"fonts"</code> will resize when
     * <code>resizeFonts()</code> runs. <P> To resize autoChild attributes with other policies, developers should call {@link
     * com.smartgwt.client.widgets.Canvas#resizeAutoChildAttributes resizeAutoChildAttributes()} directly
     * @param autoResize true if attributes should be auto-resized
     */
    public static native void setAutoResizeAutoChildAttributes(boolean autoResize) /*-{
        $wnd.isc.Canvas.setAutoResizeAutoChildAttributes(autoResize);
    }-*/;


	/**
     * Should icons be automatically resized with {@link com.smartgwt.client.widgets.Canvas#resizeControls controls} and {@link
     * com.smartgwt.client.widgets.Canvas#resizeFonts text}? <P> If true, icon attributes registered for resize with policy
     * <code>"controls"</code> will be resized when <code>resizeControls()</code> runs, and icons registered with policy
     * <code>"fonts"</code> will resize when <code>resizeFonts()</code> runs. <P> To resize icons with other policies,
     * developers should call {@link com.smartgwt.client.widgets.Canvas#resizeIcons resizeIcons()} directly
     * @param autoResizeIcons true if icons should be auto-resized
     */
    public static native void setAutoResizeIcons(boolean autoResizeIcons) /*-{
        $wnd.isc.Canvas.setAutoResizeIcons(autoResizeIcons);
    }-*/;


	/**
     * Changes the global {@link com.smartgwt.client.widgets.Canvas#defaultPageSpace defaultPageSpace}.
     * @param newDefaultPageSpace the new value for <code>defaultPageSpace</code>.
     */
    public static native void setDefaultPageSpace(int newDefaultPageSpace) /*-{
        $wnd.isc.Canvas.setDefaultPageSpace(newDefaultPageSpace);
    }-*/;


	/**
     * Changes the system-wide {@link com.smartgwt.client.widgets.Canvas#neverUseFilters neverUseFilters} setting.
     * @param neverUseFilters new setting
     */
    public static native void setNeverUseFilters(boolean neverUseFilters) /*-{
        $wnd.isc.Canvas.setNeverUseFilters(neverUseFilters);
    }-*/;




    /** 
     * Class level method to set the default properties of this class.  If set, then all
     * existing and subsequently created instances of this class will automatically have
     * default properties corresponding to
     * the properties set on the SmartGWT class instance passed to this function before its
     * underlying SmartClient JS object was created.
     * This is a powerful feature that eliminates the need for users to create a separate
     * hierarchy of subclasses that only alter the default properties of this class. Can also
     * be used for skinning / styling purposes.  <P> <b>Note:</b> This method is intended for
     * setting default attributes only and will affect all instances of the underlying class
     * (including those automatically generated in JavaScript).  This method should not be used
     * to apply standard EventHandlers or override methods for a class - use a custom subclass
     * instead.  Calling this method after instances have been created can result in undefined
     * behavior, since it bypasses any setters and a class instance may have already examined 
     * a particular property and not be expecting any changes through this route.
     *
     * @param canvasProperties properties that should be used as new defaults when instances of this class are created
     * @see com.smartgwt.client.docs.SGWTProperties
     */
    public static native void setDefaultProperties(Canvas canvasProperties) /*-{
        if (canvasProperties.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            @com.smartgwt.client.util.ConfigUtil::warnOfPreConfigInstantiation(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Class;)(Canvas.@java.lang.Object::getClass()(), "setDefaultProperties", canvasProperties.@java.lang.Object::getClass()());
        }
        canvasProperties.@com.smartgwt.client.widgets.BaseWidget::setConfigOnly(Z)(true);
    	var properties = canvasProperties.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        properties = @com.smartgwt.client.util.JSOHelper::cleanProperties(Lcom/google/gwt/core/client/JavaScriptObject;Z)(properties,true);
        $wnd.isc.Canvas.addProperties(properties);
    }-*/;

    // ***********************************************************



	private boolean isElementSet = false;

    protected Canvas getTestInstance() {
        return null;
    }

    /**
     * Retrieve a Canvas by its global {@link com.smartgwt.client.widgets.Canvas#getID ID}.
     * @param ID global ID of the Canvas
     *
     * @return the Canvas, or null if not found or not a Canvas
     */
    public static Canvas getById(String ID) {
        BaseWidget obj = BaseWidget.getById(ID);
        return obj instanceof Canvas ? (Canvas)obj : null;
    }

    /**
     * Retrieve a Canvas from its {@link com.google.gwt.core.client.JavaScriptObject}.
     * @param jsObj SmartClient object associated with Canvas
     *
     * @return the Canvas, or null if not found or not a Canvas
     */
    public static Canvas getByJSObject(JavaScriptObject jsObj) {
        BaseWidget obj = BaseWidget.getByJSObject(jsObj);
        return obj instanceof Canvas ? (Canvas)obj : null;
    }

    /**
     * Normal usage of SmartGWT is to call draw() on SmartGWT widgets, not to use use RootPanel.add().
     * Developers will not typically need to call getElement() or setElement() for SmartGWT canvas instances.
     * If developers do need to access the DOM elements created by a drawn SmartGWT component, 
     * they should use {@link com.smartgwt.client.widgets.Canvas#getOuterElement()} and 
     * {@link com.smartgwt.client.widgets.Canvas#getContentElement()}. See the javadoc for
     * those methods for more information. Note particularly that these elements should not be
     * directly manipulated in the DOM - instead standard SmartGWT APIs such as <i>draw()</i>,
     * <i>clear()</i>, <i>setContents()</i>, <i>addChild()</i>, <i>redraw()</i> should be used.
     * See also {@link com.smartgwt.client.widgets.Canvas#setHtmlElement(Element)} for how to embed
     * a SmartGWT component into an existing DOM element.
     * <P>
     * getElement() and setEleent() exist solely to support adding SmartGWT widgets to a core GWT layout manager.
     * This usage has limitations and caveats - see this list of
     * <a href=http://forums.smartclient.com/showthread.php?t=8159#aMix>Frequently Asked Questions</a>.
     * <P>
     * The mechanism that getElement() uses to integrate with core GWT is the same as that described for
     * {@link com.smartgwt.client.widgets.Canvas#setHTMLElement(Element)} and has the same caveats
     * 
     */
    protected void setElement(Element elem) {
        super.setElement(elem);
        isElementSet = true;
    }

    /**
     * Normal usage of SmartGWT is to call draw() on SmartGWT widgets, not to use use RootPanel.add().
     * Developers will not typically need to call getElement() or setElement() for SmartGWT canvas instances.
     * If developers do need to access the DOM elements created by a drawn SmartGWT component, 
     * they should use {@link com.smartgwt.client.widgets.Canvas#getOuterElement()} and 
     * {@link com.smartgwt.client.widgets.Canvas#getContentElement()}. See the javadoc for
     * those methods for more information. Note particularly that these elements should not be
     * directly manipulated in the DOM - instead standard SmartGWT APIs such as <i>draw()</i>,
     * <i>clear()</i>, <i>setContents()</i>, <i>addChild()</i>, <i>redraw()</i> should be used.
     * See also {@link com.smartgwt.client.widgets.Canvas#setHtmlElement(Element)} for how to embed
     * a SmartGWT component into an existing DOM element.
     * <P>
     * getElement() and setEleent() exist solely to support adding SmartGWT widgets to a core GWT layout manager.
     * This usage has limitations and caveats - see this list of
     * <a href=http://forums.smartclient.com/showthread.php?t=8159#aMix>Frequently Asked Questions</a>.
     * <P>
     * The mechanism that getElement() uses to integrate with core GWT is the same as that described for
     * {@link com.smartgwt.client.widgets.Canvas#setHTMLElement(Element)} and has the same caveats
     * 
     */
    public Element getElement() {
        return getElement(true);
    }

    /**
     * @see BaseWidget#getElement()
     * @param allowPreRender
     * @return
     */
    public Element getElement(boolean allowPreRender) {

        if (!isElementSet) {
            JavaScriptObject jsObj = getJsObj();
            if (!allowPreRender) {
                error("This method should only be called after the component has been rendered");
            }

            if (jsObj == null) {
                getOrCreateJsObj();
            }

            Element wrapperDiv = com.google.gwt.user.client.DOM.createDiv();
            DOMUtil.setID(wrapperDiv, getID() + "_wrapper");
            //the wrapper div must be attached to the dom, or else this widgets children don't get
            //a handle to this widgets dom element (via getHandle()). For example if this self is a
            // HLayout and containts a Canvas and IButton child member.
            com.google.gwt.user.client.ui.RootPanel.getBodyElement().appendChild(wrapperDiv);

            Canvas self = ((Canvas) this);
            //need to set properties before calling clear else the properties are not set on the jsObj (it ends up on the config)
            setProperty("position", Positioning.RELATIVE.getValue());
            setProperty("redrawOnResize", true);
            setProperty("htmlElement", wrapperDiv);
            self.clear();
            self.draw();
            setElement(wrapperDiv);
            return wrapperDiv;

        }
        return super.getElement();
    }
    
    /**
     * Returns the existing SGWT widget, or creates and returns one if none exist,
     * associated with the supplied {@link com.google.gwt.core.client.JavaScriptObject}.  If
     * the supplied object is not a SmartClient Canvas, a warning will be logged and null
     * returned; otherwise the widget will be returned as the appropriate subtype of SGWT
     * Canvas.
     *
     * @param jsObj SmartClient Canvas whose wrapper is wanted
     *
     * @return wrapping SGWT Canvas, appropriately subtyped, or null
     */
    public static native <T extends Canvas> T asSGWTComponent(JavaScriptObject jsObj) /*-{
       if ($wnd.isc.isA.Canvas(jsObj)) {
           return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
       } 
       var className = $wnd.isc.isAn.Instance(jsObj) ? jsObj.getScClassName() : "none";
       @com.smartgwt.client.util.SC::logWarn(Ljava/lang/String;)("Canvas.asSGWTComponent(): " +
           "The supplied JS object must be a Canvas - actual class is " + className + ".");
       return null;
    }-*/;

    private static native JavaScriptObject getSuperClass(JavaScriptObject jso) /*-{
        return jso.getSuperClass();
    }-*/;

    private static native String getClassName(JavaScriptObject jso) /*-{
        return jso.getClassName();
    }-*/;

    /**
     * Retrieve a child of this Canvas by it's local ID.
     * <p>
     * A "local ID" is name for a child widget which is unique only for this parent, and not globally
     * unique as is required for {@link com.smartgwt.client.widgets.Canvas#getID ID}. 
     * Widgets receive local IDs when loaded via
     * 
     * {@link com.smartgwt.client.client.rpc.RPCManager#loadScreen()}
     * or {@link com.smartgwt.client.rpc.RPCManager#cacheScreens()} and
     * {@link com.smartgwt.client.rpc.RPCManager#createScreen()}.
     * 
     * @param ID global ID of the Canvas
     *
     * @return the Canvas, or null if not found
     */
    public native Canvas getByLocalId(String ID) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.getByLocalId(ID);
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

    /**
     * Returns the JavaScript class name.
     * @return the JavaScript class name.
     */
    public native String getClassName()/*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        return self.getClassName();
    }-*/;

    /**
     * Specify a notification method to fire when this Canvas is initialized in JavaScript. This allows developers
     * to set up form item state dynamically when the item is created.
     * 
     * @param initHandler
     */
    public native void setInitHandler(CanvasInitHandler initHandler) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        self.__onInit = $debox($entry(function(canvas) {
            var canvasJ = @com.smartgwt.client.widgets.Canvas::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(canvas);
            return initHandler.@com.smartgwt.client.widgets.events.CanvasInitHandler::onInit(Lcom/smartgwt/client/widgets/Canvas;)(canvasJ);
        }));
    }-*/;
    
	protected native void onInit () /*-{

        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self._jsOverrideCall = {};
        self._fireDefaultMethod = {};

        self.__parentResized = self.parentResized;
        self.parentResized = $debox($entry(function() {
            var jObj = this.__ref;
            jObj.@com.smartgwt.client.widgets.Canvas::parentResized()();
        }));

        self.__willAcceptDrop = self.willAcceptDrop;
        self.willAcceptDrop = $debox($entry(function() {
            var jObj = this.__ref;
            var retVal = jObj.@com.smartgwt.client.widgets.Canvas::willAcceptDrop()();
            return retVal == null ? null : retVal.@java.lang.Boolean::booleanValue()();
        }));

        self.__revealChild = self.revealChild;
        self.revealChild = $debox($entry(function(child) {
            var jObj = this.__ref;
            var jChild = child == null ? null : child.__ref;
            jObj.@com.smartgwt.client.widgets.Canvas::revealChild(Lcom/smartgwt/client/widgets/Canvas;)(jChild);
        }));

        self.__updateEditNode = self.updateEditNode;
        self.updateEditNode = $entry(function(editContext, editNode) {
            var jObj = this.__ref;

            // We need to create the correct Java type, but we can't call
            // getOrCreateRef on the EditContext interface. The best simple
            // thing we can do is take advantage of ObjectFactory.createCanvas
            // and friends to pick the right type.  Note that this will work
            // for custom developer classes so long as they descend from a
            // SmartClient EditContext implementation. It won't work for
            // totally custom implementations of EditContext, but totally
            // custom implementations would be hard to create properly for
            // other reasons.
            var editContextJ = null;
            if ($wnd.isc.isAn.Instance(editContext)) {
                var klass = editContext.getClassName();
                editContextJ = @com.smartgwt.client.util.ObjectFactory::createCanvas(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(klass, editContext);

                // An EditContext will probably be a Canvas, but not
                // necessarily ... we'll try the others as well.
                if (editContextJ == null) {
                    editContextJ = @com.smartgwt.client.util.ObjectFactory::createInstance(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(klass, editContext);
                }
            }

            var editNodeJ = @com.smartgwt.client.tools.EditNode::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(editNode);
            jObj.@com.smartgwt.client.widgets.Canvas::updateEditNode(Lcom/smartgwt/client/tools/EditContext;Lcom/smartgwt/client/tools/EditNode;)(editContextJ, editNodeJ);
        });

        self.__getPrintHTML = self.getPrintHTML;
        self.getPrintHTML = function (printProperties, callback) {
            self._jsOverrideCall.getPrintHTML = true;
            var jObj = this.__ref;
             var jPP = printProperties == null ? null :
                      @com.smartgwt.client.util.PrintProperties::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(printProperties);
             var ret;
             ret = jObj.@com.smartgwt.client.widgets.Canvas::getPrintHTMLJSCB(Lcom/smartgwt/client/util/PrintProperties;Lcom/google/gwt/core/client/JavaScriptObject;)(jPP,callback);
             if (self._fireDefaultMethod.getPrintHTML) {
                 ret = self.__getPrintHTML(printProperties,callback);
             }
             self._jsOverrideCall.getPrintHTML = null;
             self._fireDefaultMethod.getPrintHTML = null;
             return ret;
        }

        self.__getHoverComponent = self.getHoverComponent;
        self.getHoverComponent = $entry(function() {
            var jObj = this.__ref;
            var componentJ = jObj.@com.smartgwt.client.widgets.Canvas::getHoverComponent()();
            return componentJ == null ? null : componentJ.@com.smartgwt.client.widgets.Canvas::getOrCreateJsObj()();
        });

		if(self.__onInit){
			self.__onInit();
		}
		
	}-*/;

    /**
     * Create a databound component corresponding to the passed jsObj.
     *
     * @param jsObj the javascript object
     * @return the databound component
     */
    private static native DataBoundComponent getOrCreateDBC(JavaScriptObject jsObj) /*-{
        if(jsObj == null) return null;
        var obj = jsObj.__ref;
        if(obj == null || obj === undefined) {
            if($wnd.isc.isA.ListGrid(jsObj)) return @com.smartgwt.client.widgets.grid.ListGrid::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
            if($wnd.isc.isA.DynamicForm(jsObj)) return @com.smartgwt.client.widgets.form.DynamicForm::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
            if($wnd.isc.isA.DetailViewer(jsObj)) return @com.smartgwt.client.widgets.viewer.DetailViewer::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
            if($wnd.isc.isA.Calendar(jsObj)) return @com.smartgwt.client.widgets.calendar.Calendar::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
            if($wnd.isc.isA.ColumnTree(jsObj)) return @com.smartgwt.client.widgets.grid.ColumnTree::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
            if($wnd.isc.isA.TileGrid(jsObj)) return @com.smartgwt.client.widgets.tile.TileGrid::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
            return @com.smartgwt.client.widgets.Canvas::new(Lcom/google/gwt/core/client/JavaScriptObject;)(jsObj);
        } else {
            return obj;
        }
    }-*/;

    protected void onDetach() {
        super.onDetach();
        //when a SmartGWT widget is removed from a parent GWT widget container, make sure we carry out proper cleanup
        clear();
    }


    protected void onAttach() {
        super.onAttach();
        //upon attach to a GWT container, draw the SmartGWT widget if it has not been previously drawn
        if(!isDrawn()) {
            draw();
        }
    }

    /**
     * Multiple styles are currently not supported. This method essentially calls {@link #setStyleName(String)}
     *
     * @param style the style name
     * @see #setStyleName(String)
     */
    public void addStyleName(String style) {
        setStyleName(style);
        com.google.gwt.core.client.GWT.log("addStyleName is not supported. Calling setStyleName instead", null);
    }

    public void setVisible(boolean visible) {
        setVisibility(visible ? Visibility.INHERIT : Visibility.HIDDEN);
    }

    /**
     * @see Canvas#setHeight(Integer)
     */
    public void setHeight(int height) {
        setAttribute("height", height, true);
    }

    /**
     * Convenience method that sets the height to 100%.
     */
    public void setHeight100() {
        setHeight("100%");
    }

    public String getHeightAsString() {
        return getAttribute("height");
    }

    /**
     * Synonym for {@link #setPrompt(String)}
     *
     * @param title the title
     */
    public void setTitle(String title) {
        setPrompt(title);
    }

    /**
     * Synonym for {@link #getPrompt()}
     *
     * @return the title
     */
    public String getTitle() {
        return getPrompt();
    }

    /**
     * Synonym for {@link #setPrompt(String)}
     *
     * @param tooltip the tooltip
     */
    public void setTooltip(String tooltip) {
        setPrompt(tooltip);
    }

    /**
     * Synonym for {@link #getPrompt()}
     *
     * @return  the tooltip
     */
    public String getTooltip() {
        return getPrompt();
    }

    /**
     * @see Canvas#setWidth(Integer)
     */
    public void setWidth(int width) {
        setAttribute("width", width, true);
    }

    /**
     * Convenience method that sets the width to 100%.
     */
    public void setWidth100() {
        setWidth("100%");
    }
    
    public String getWidthAsString() {
        return getAttribute("width");
    }

    /**
     * Number of pixels the left side of the widget is offset to the right from its default drawing context (either its
     * parent's topleft corner, or the document flow, depending on the value of the {@link
     * com.smartgwt.client.widgets.Canvas#getPosition position} property). <P> Can also be set as a percentage, specified as a
     * String ending in '%', eg, "50%".  In this case the top coordinate is considered as a percentage of the specified width
     * of the {@link com.smartgwt.client.widgets.Canvas#getParentCanvas() 'parent'}.
     *
     *
     * @return Return the left coordinate of this object, relative to its enclosing context, in pixels.
     */
    public String getLeftAsString()  {
        return getAttributeAsString("left");
    }

    /**
     * If a Canvas is dropped onto a {@link com.smartgwt.client.widgets.cube.CubeGrid}, and it has a {@link #setFacetId(String) facetId}
     * property specified then the Cube treats this as adding that facetId at the drop location.
     *
     * @return the facet id
     */
    public String getFacetId()  {
        return getAttribute("facetId");
    }

    /**
     * If a Canvas is dropped onto a {@link com.smartgwt.client.widgets.cube.CubeGrid}, and it has a facetId property specified then
     * the Cube treats this as adding that facetId at the drop location.
     *
     * @param facetId the facet id
     */
    public void setFacetId(String facetId) {
        setAttribute("facetId", facetId, true);
    }

    /**
     * Number of pixels the top of the widget is offset down from its default drawing context (either its parent's top-left
     * corner, or the document flow, depending on the value of the {@link com.smartgwt.client.widgets.Canvas#getPosition
     * position} property). <P> Can also be set as a percentage, specified as a String ending in '%', eg, "50%".  In this case
     * the top coordinate is considered as a percentage of the specified height of the {@link
     * com.smartgwt.client.widgets.Canvas#getParentCanvas() 'parent'}.
     *
     *
     * @return Return the top coordinate of this object, relative to its enclosing context, in pixels.
     */
    public String getTopAsString()  {
        return getAttributeAsString("top");
    }

    public void setAlign(Alignment align) {
        setAttribute("align", align.getValue(), true);
    }

    /**
     * This method is similar to {@link #getPageLeft()}.
     * @return
     */
    public int getAbsoluteLeft() {
        return getPageLeft();
    }

    /**
     * This method is similar to {@link #getPageTop()}.
     * @return
     */
    public int getAbsoluteTop() {
        return getPageTop();
    }

    /**
     * Default animation effect to use if {@link Canvas#animateShow()} is called without an explicit effect parameter.
     *
     * @param animateShowEffect the animate show effect. Default is "wipe"
     */
    public void setAnimateShowEffect(AnimationEffect animateShowEffect) {
        setAttribute("animateShowEffect", animateShowEffect, true);
    }

    /**
     * Default animation effect to use if {@link Canvas#animateShow()} is called without an explicit effect parameter.
     *
     * @return animation effect. Default value is "wipe"
     */
    public AnimationEffect getAnimateShowEffect() {
        return EnumUtil.getEnum(AnimationEffect.values(), getAttribute("animateShowEffect"));
    }

    /**
     * Default animation effect to use if {@link Canvas#animateHide()} is called without an explicit effect parameter.
     *
     * @return animation effect. Default value is "wipe"
     */
    public AnimationEffect getAnimateHideEffect() {
        return EnumUtil.getEnum(AnimationEffect.values(), getAttribute("animateHideEffect"));
    }

    /**
     * Default animation effect to use if {@link Canvas#animateHide()} is called without an explicit effect parameter.
     *
     * @param animateHideEffect the animate show effect. Default is "wipe"
     */
    public void setAnimateHideEffect(AnimationEffect animateHideEffect) {
        setAttribute("animateHideEffect", animateHideEffect, true);
    }

    /**
     * If set to true, the widget will be disabled. A widget is only considered enabled       if it is individually enabled and
     * all parents above it in the containment hierarchy       are enabled. This allows you to enable or disable all components
     * of a complex       nested widget by enabling or disabling the top-level parent only.
     * set the disabled state of this object
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param disabled new disabled state of this object - pass <code>true</code> to disable the widget. Default value is false
     */
    public void setDisabled(boolean disabled) {
        setAttribute("disabled", disabled, true);
    }

    /**
     * If set to true, the widget will be disabled. A widget is only considered enabled       if it is individually enabled and
     * all parents above it in the containment hierarchy       are enabled. This allows you to enable or disable all components
     * of a complex       nested widget by enabling or disabling the top-level parent only.
     *
     *
     * @return Is this canvas disabled? Note that the disabled state is inherited - this method will return true if this widget, or any
     * of its ancestors are marked disabled.
     */
    public boolean getDisabled()  {
        Boolean disabled =  getAttributeAsBoolean("disabled");
        return disabled == null ? false : disabled;
    }

    /**
     * A different widget that should be actually dragged when dragging initiates on this&#010      widget. One example of this is to have a child widget that drags its parent, as with a&#010      drag box. Because the parent automatically repositions its children, setting the drag&#010      target of the child to the parent and then dragging the child will result in both&#010      widgets being moved.<br>&#010      Valid dragTarget values are:<br>&#010      - <code>null</code> (default) [this widget is its own drag target]<br>&#010      - Pointer to another widget, or widget ID<br>&#010      - <code>"parent"</code> drag target is this widget's &#010         {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parentCanvas}<br>&#010      - <code>"top"</code> drag target is this widget's &#010         {@link com.smartgwt.client.widgets.Canvas#getTopElement topElement}<br>
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param dragTarget dragTarget Default value is null
     */
    public void setDragTarget(Canvas dragTarget) {
        setAttribute("dragTarget", dragTarget.getOrCreateJsObj(), true);
    }
    /**
     * A different widget that should be actually dragged when dragging initiates on this&#010      widget. One example of this is to have a child widget that drags its parent, as with a&#010      drag box. Because the parent automatically repositions its children, setting the drag&#010      target of the child to the parent and then dragging the child will result in both&#010      widgets being moved.<br>&#010      Valid dragTarget values are:<br>&#010      - <code>null</code> (default) [this widget is its own drag target]<br>&#010      - Pointer to another widget, or widget ID<br>&#010      - <code>"parent"</code> drag target is this widget's &#010         {@link com.smartgwt.client.widgets.Canvas#getParentCanvas parentCanvas}<br>&#010      - <code>"top"</code> drag target is this widget's &#010         {@link com.smartgwt.client.widgets.Canvas#getTopElement topElement}<br>
     *
     *
     * @return Canvas
     *
     */
    public Canvas getDragTarget()  {
        return Canvas.getOrCreateRef(getAttributeAsJavaScriptObject("dragTarget"));
    }

    /**
     * Returns true if the widget object being dragged can be dropped on this widget, and false or null otherwise.  The default
     * implementation of this method simply compares the {@link com.smartgwt.client.widgets.Canvas#getDragType dragType} of the
     * <code>dragTarget</code> (the component being dragged from) with the list of {@link
     * com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} on this Canvas.  If the {@link
     * com.smartgwt.client.widgets.Canvas#getDropTypes dropTypes} list contains the {@link
     * com.smartgwt.client.widgets.Canvas#getDragType dragType} value, then this method returns true.  Otherwise it returns
     * false.
     * <p>No matter what you return, the
     * {@link com.smartgwt.client.widgets.events.DropOverHandler#onDropOver onDropOver} and 
     * {@link com.smartgwt.client.widgets.events.DropMoveHandler#onDropMove onDropMove} handlers
     * will still be called, and their return value will control whether those events are bubbled to parent elements.
     * <p>However, what you return from <code>willAcceptDrop()</code> does determine whether the
     * {@link com.smartgwt.client.widgets.events.DropHandler#onDrop onDrop} handler will be called.
     * <ul>
     * <li>If you return true, then <code>onDrop()</code> will be called, and
     * its return value will determine whether the event is bubbled to parent
     * elements.
     * <li>If you return false, then <code>onDrop()</code> will not be called,
     * and the event will not be bubbled.
     * <li>If you return null, then <code>onDrop()</code> will not be called,
     * but the event will be bubbled to parent elements (giving them a chance
     * to handle the drop).
     * </ul>
     * <p>So, you should return false to definitively deny a drop, and return null if it could make sense to allow a parent
     * element (such as a {@link com.smartgwt.client.widgets.layout.Layout}) to handle the drop.
     * <p><b>Note: This is an override point</b>
     *
     * @return true if the widget object being dragged can be dropped on this widget,
     *         false if it cannot (and a drop event should not be bubbled), 
     *         and null to permit a drop event to bubble to parent elements.
     */
    public native Boolean willAcceptDrop() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var retVal = self.__willAcceptDrop();
        if(retVal == null) {
            return null;
        } else {
            return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(retVal);
        }
    }-*/;

    /**
     * Fires when the interior size of the parent changes, including parent resize and scrollbar introduction or removal. <p>
     * This method allows a child to implement a layout policy that can be used within any  parent, such as a Resizer component
     * that always snaps to the parent's bottom-right corner.  The default implementation of this method applies a child's
     * percent sizes, if any, or implements layout based on the {@link com.smartgwt.client.widgets.Canvas#getSnapTo snapTo}
     * property.
     * <br><p>Make sure you call super.parentResized() if you'd like the default behavior to apply.
     *
     * <br><b>Note: This is an override point</b>
     *
     */
    protected native void parentResized() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.__parentResized();
    }-*/;

    /**
     * When {@link com.smartgwt.client.widgets.Canvas#getShowHoverComponents showHoverComponents} is true, this method is
     * called to get the component to show as a hover for this Canvas.  There is no default implementation of this method, so
     * you need to override it with an implementation that returns a Canvas that suits your needs. <P> By default, components
     * returned by <code>getHoverComponent()</code> will not be automatically destroyed when the hover is hidden.  To enforce
     * this, set {@link com.smartgwt.client.widgets.Canvas#getHoverAutoDestroy hoverAutoDestroy} to true on the returned
     * component.
     *
     * <br><b>Note: this is an override point</b>
     *
     * @return the component to show as a hover
     */
    public native Canvas getHoverComponent() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var hoverComponent = (self.__getHoverComponent == null) ? null : self.__getHoverComponent();
        return hoverComponent == null ? null : hoverComponent.@com.smartgwt.client.widgets.Canvas::getOrCreateJsObj()();
    }-*/;

    public void setSmoothFade(Boolean smoothFade) throws IllegalStateException {
        setAttribute("smoothFade", smoothFade, false);
    }


    public void setKeepInParentRect(Rectangle rectangle) {
        setAttribute("keepInParentRect", rectangle.getAsJSArray(), true);
    }

    public void setPrefix(String prefix) {
        setAttribute("prefix", prefix, true);
    }

    public String getPrefix() {
        return getAttribute("prefix");
    }

    /**
     * Allows resizing in certain edges or corners. The default value of null indicates that the widget is resizable from any corner
     * or edge. To restrict resizing to only certain corners, set resizeFrom to an array of any of the values listed:<br>
     *
     * T top edge<br>
     * B bottom edge<br>
     * L left edge<br>
     * R right edge<br>
     * TL top-left corner<br>
     * TR top-right corner<br>
     * BL bottom-left corner<br>
     * BR bottom-right corner<br><br>
     * E.g. setting this property to a value of ["R","TR","BR"] would restrict resizing to the right edge, top-right corner and bottom-right corner only
     *
     * @param resizeFrom resizeFrom values
     * @deprecated use {@link #setResizeFrom(EdgeName)} instead.
     */
    public void setResizeFrom(String... resizeFrom) {
        setAttribute("resizeFrom", resizeFrom, true);
    }

    /**
     * Resizes the widget to the specified width and height (moves the right and/ or bottom          sides of the widget). The
     * width and height parameters can be expressed as a percentage          of viewport size or as the number of pixels.
     * @param width new width for canvas
     * @param height new height for canvas
     *
     * @return whether the size actually changed
     */
    public native Boolean resizeTo(String width, String height) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var retVal =self.resizeTo(width, height);
        if(retVal == null) {
            return null;
        } else {
            return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(retVal);
        }
    }-*/;


    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getProportionalResizing proportionalResizing} is set to
     * {@link com.smartgwt.client.types.ProportionalResizeMode#MODIFIER} or
     * {@link com.smartgwt.client.types.ProportionalResizeMode#MODIFIER_OFF} then proportional resizing of the DrawItem
     * is activated or deactivated, respectively, whenever at least one key in this set of modifier keys is pressed.
     * <p>
     * The keys allowed in this set are:  "Alt", "Ctrl", and "Shift".  If this set of keys is empty then proportional
     * resizing is always used if <code>proportionalResizing</code> is <code>MODIFIER</code> and is never used if
     * <code>proportionalResizing</code> is <code>MODIFIER_OFF</code>.
     *
     * @param proportionalResizeModifiers  Default value is ["Shift"]
     * @throws IllegalStateException this property cannot be changed after the underlying component has been created
     */
    public void setProportionalResizeModifiers(String... proportionalResizeModifiers) throws IllegalStateException {
        setAttribute("proportionalResizeModifiers", proportionalResizeModifiers, false);
    }

    /**
     * If {@link com.smartgwt.client.widgets.Canvas#getProportionalResizing proportionalResizing} is set to
     * {@link com.smartgwt.client.types.ProportionalResizeMode#MODIFIER} or
     * {@link com.smartgwt.client.types.ProportionalResizeMode#MODIFIER_OFF} then proportional resizing of the DrawItem
     * is activated or deactivated, respectively, whenever at least one key in this set of modifier keys is pressed.
     * <p>
     * The keys allowed in this set are:  "Alt", "Ctrl", and "Shift".  If this set of keys is empty then proportional
     * resizing is always used if <code>proportionalResizing</code> is <code>MODIFIER</code> and is never used if
     * <code>proportionalResizing</code> is <code>MODIFIER_OFF</code>.
     *
     * @return String...
     */
    public String[] getProportionalResizeModifiers()  {
        return getAttributeAsStringArray("proportionalResizeModifiers");
    }

    /**
     * Returns true if the widget is visible, taking all parents into account, so that a widget which is not hidden
     * might still report itself as not visible if it is within a hidden parent. <P> NOTE: Undrawn widgets will
     * report themselves as visible if they would be visible if drawn.
     *
     * @return true if the widget is visible, false otherwise
     */
    public native boolean isVisible() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        return self.isVisible();
    }-*/;

    /**
     * Animate a scroll from the current scroll position to the specified position.
     *
     * @param scrollLeft desired final left scroll postion
     * @param scrollTop  desired final top scroll postion
     * @param callback   When the scroll completes this callback will be fired. Single
     *                   'earlyFinish' parameter will be passed if the animation was                              cut
     *                   short by a call to finishAnimation
     */
    public native void animateScroll(int scrollLeft, int scrollTop, AnimationCallback callback) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateScroll(scrollLeft, scrollTop, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }));
    }-*/;

    /**
     * Animate a scroll from the current scroll position to the specified position.
     *
     * @param scrollLeft desired final left scroll postion
     * @param scrollTop  desired final top scroll postion
     * @param callback   When the scroll completes this callback will be fired. Single
     *                   'earlyFinish' parameter will be passed if the animation was                              cut
     *                   short by a call to finishAnimation
     * @param duration   Duration in ms of the animated scroll
     */
    public native void animateScroll(int scrollLeft, int scrollTop, AnimationCallback callback, int duration) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateScroll(scrollLeft, scrollTop, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }), duration);
    }-*/;

    /**
     * Animate a change in opacity from the widget's current opacity to the specified opacity.
     *
     * @param opacity desired final opacity
     */
    public native void animateFade(int opacity) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateFade(opacity);
    }-*/;

    /**
     * Animate a change in opacity from the widget's current opacity to the specified opacity.
     *
     * @param opacity  desired final opacity
     * @param callback When the fade completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation
     */
    public native void animateFade(int opacity, AnimationCallback callback) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateFade(opacity, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }));
    }-*/;

    /**
     * Animate a change in opacity from the widget's current opacity to the specified opacity.
     *
     * @param opacity  desired final opacity
     * @param callback When the fade completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation
     * @param duration Duration in ms of the animated fade
     */
    public native void animateFade(int opacity, AnimationCallback callback, int duration) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateFade(opacity, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }), duration);
    }-*/;

    /**
     * Animate a change in opacity from the widget's current opacity to the specified opacity.
     *
     * @param opacity  desired final opacity
     * @param callback When the fade completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was cut
     *                 short by a call to finishAnimation
     * @param duration Duration in ms of the animated fade
     * @param acceleration Animation acceleration to bias the ratios
     */
    public native void animateFade(int opacity, AnimationCallback callback, int duration, AnimationAcceleration acceleration) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateFade(opacity, $entry(function(earlyFinish) {
            earlyFinish = (earlyFinish === undefined ? false : earlyFinish);
            if (callback != null) {
                callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
            }
        }), duration, (acceleration != null ? acceleration.@com.smartgwt.client.types.AnimationAcceleration::getValue()() : null));
    }-*/;

    /**
     * Hide a canvas by shrinking it with default effect specified by {@link 
     * com.smartgwt.client.widgets.Canvas#setAnimateHideEffect animateHideEffect}. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     */
    public native void animateHide() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide();
    }-*/;

    /**
     * Hide a canvas by shrinking it vertically to zero height over a period of time. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     *
     * @param effect How should the content of the  window be hidden during the hide? If omitted, default behavior can
     *               be configured via  {@link com.smartgwt.client.widgets.Canvas#getAnimateHideEffect
     *               animateHideEffect}
     */
    public native void animateHide(AnimationEffect effect) /*-{
        var effectVal = effect.@com.smartgwt.client.types.AnimationEffect::getValue()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide(effectVal);
    }-*/;

    /**
     * Hide a canvas by shrinking it vertically to zero height over a period of time. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     *
     * @param effect How should the content of the  window be hidden during the hide? If omitted, default behavior can
     *               be configured via  {@link com.smartgwt.client.widgets.Canvas#getAnimateHideEffect
     *               animateHideEffect}
     */
    public native void animateHide(AnimateShowEffect effect) /*-{
        var effectVal = effect.@com.smartgwt.client.widgets.drawing.AnimateShowEffect::getJsObj()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide(effectVal);
    }-*/;

    /**
     * Hide a canvas by shrinking it vertically to zero height over a period of time. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     *
     * @param effect   How should the content of the  window be hidden during the hide? If omitted, default behavior
     *                 can be configured via  {@link com.smartgwt.client.widgets.Canvas#getAnimateHideEffect
     *                 animateHideEffect}
     * @param callback When the hide completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation.
     */
    public native void animateHide(AnimationEffect effect, AnimationCallback callback) /*-{
        var effectVal = effect.@com.smartgwt.client.types.AnimationEffect::getValue()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }));
    }-*/;

    /**
     * Hide a canvas by shrinking it vertically to zero height over a period of time. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     *
     * @param effect   How should the content of the  window be hidden during the hide? If omitted, default behavior
     *                 can be configured via  {@link com.smartgwt.client.widgets.Canvas#getAnimateHideEffect
     *                 animateHideEffect}
     * @param callback When the hide completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation.
     */
    public native void animateHide(AnimateShowEffect effect, AnimationCallback callback) /*-{
        var effectVal = effect.@com.smartgwt.client.widgets.drawing.AnimateShowEffect::getJsObj()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }));
    }-*/;

    /**
     * Hide a canvas by shrinking it vertically to zero height over a period of time. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     *
     * @param effect   How should the content of the  window be hidden during the hide? If omitted, default behavior
     *                 can be configured via  {@link com.smartgwt.client.widgets.Canvas#getAnimateHideEffect
     *                 animateHideEffect}
     * @param callback When the hide completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation.
     * @param duration Duration in ms of the animated hide.  If unset, duration will be   picked up from {@link
     *                 com.smartgwt.client.widgets.Canvas#getAnimateHideTime animateHideTime}
     */
    public native void animateHide(AnimationEffect effect, AnimationCallback callback, int duration) /*-{
        var effectVal = effect.@com.smartgwt.client.types.AnimationEffect::getValue()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }), duration);
    }-*/;

    /**
     * Hide a canvas by shrinking it vertically to zero height over a period of time. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     *
     * @param effect   How should the content of the  window be hidden during the hide? If omitted, default behavior
     *                 can be configured via  {@link com.smartgwt.client.widgets.Canvas#getAnimateHideEffect
     *                 animateHideEffect}
     * @param callback When the hide completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation.
     * @param duration Duration in ms of the animated hide.  If unset, duration will be   picked up from {@link
     *                 com.smartgwt.client.widgets.Canvas#getAnimateHideTime animateHideTime}
     */
    public native void animateHide(AnimateShowEffect effect, AnimationCallback callback, int duration) /*-{
        var effectVal = effect.@com.smartgwt.client.widgets.drawing.AnimateShowEffect::getJsObj()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateHide(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }), duration);
    }-*/;

    /**
     * Show a canvas by growing it with default effect specified by {@link 
     * com.smartgwt.client.widgets.Canvas#setAnimateShowEffect animateShowEffect}. This method will not fire if the
     * widget is already drawn and visible, or has overflow other than <code>"visible"</code> or <code>"hidden"</code>.
     */
    public native void animateShow() /*-{
        var self      = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow();
    }-*/;

    /**
     * Show a canvas by growing it vertically to its fully drawn height over a period of time. This method will not fire
     * if the widget is already drawn and visible, or has overflow other than <code>"visible"</code> or
     * <code>"hidden"</code>.
     *
     * @param effect Animation effect to use      when revealing the widget. If omitted, default behavior can be
     *               configured via      {@link com.smartgwt.client.widgets.Canvas#getAnimateShowEffect
     *               animateShowEffect}
     */
    public native void animateShow(AnimationEffect effect) /*-{
        var effectVal = effect.@com.smartgwt.client.types.AnimationEffect::getValue()();
        var self      = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow(effectVal);
    }-*/;

    /**
     * Show a canvas by growing it vertically to its fully drawn height over a period of time. This method will not fire
     * if the widget is already drawn and visible, or has overflow other than <code>"visible"</code> or
     * <code>"hidden"</code>.
     *
     * @param effect AnimateShowEffect effect to use when revealing the widget. If omitted, default behavior can be
     *               configured via {@link com.smartgwt.client.widgets.Canvas#getAnimateShowEffect animateShowEffect}
     */
    public native void animateShow(AnimateShowEffect effect) /*-{
        var effectVal = effect.@com.smartgwt.client.widgets.drawing.AnimateShowEffect::getJsObj()();
        var self      = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow(effectVal);
    }-*/;
    
    /**
     * Show a canvas by growing it vertically to its fully drawn height over a period of time. This method will not fire
     * if the widget is already drawn and visible, or has overflow other than <code>"visible"</code> or
     * <code>"hidden"</code>.
     *
     * @param effect   Animation effect to use when revealing the widget. If omitted, default behavior can be
     *                 configured via      {@link com.smartgwt.client.widgets.Canvas#getAnimateShowEffect
     *                 animateShowEffect}
     * @param callback When the show completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was cut
     *                 short by a call to finishAnimation.
     */
    public native void animateShow(AnimationEffect effect, AnimationCallback callback) /*-{
        var effectVal = effect.@com.smartgwt.client.types.AnimationEffect::getValue()();
        var self      = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }));
    }-*/;

    /**
     * Show a canvas by growing it vertically to its fully drawn height over a period of time. This method will not fire
     * if the widget is already drawn and visible, or has overflow other than <code>"visible"</code> or
     * <code>"hidden"</code>.
     *
     * @param effect AnimateShowEffect effect to use when revealing the widget. If omitted, default behavior can be
     *               configured via {@link com.smartgwt.client.widgets.Canvas#getAnimateShowEffect animateShowEffect}
     * @param callback When the show completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was cut short by a call to finishAnimation.
     */
    public native void animateShow(AnimateShowEffect effect, AnimationCallback callback) /*-{
        var effectVal = effect.@com.smartgwt.client.widgets.drawing.AnimateShowEffect::getJsObj()();
        var self      = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }));
    }-*/;

    /**
     * Show a canvas by growing it vertically to its fully drawn height over a period of time. This method will not fire
     * if the widget is already drawn and visible, or has overflow other than <code>"visible"</code> or
     * <code>"hidden"</code>.
     *
     * @param effect   Animation effect to use      when revealing the widget. If omitted, default behavior can be
     *                 configured via      {@link com.smartgwt.client.widgets.Canvas#getAnimateShowEffect
     *                 animateShowEffect}
     * @param callback When the show completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation.
     * @param duration Duration in ms of the animated show. If unset, duration will be   picked up from {@link
     *                 com.smartgwt.client.widgets.Canvas#getAnimateShowTime animateShowTime}
     */
    public native void animateShow(AnimationEffect effect, AnimationCallback callback, int duration) /*-{
        var effectVal = effect.@com.smartgwt.client.types.AnimationEffect::getValue()();
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }), duration);
    }-*/;

    /**
     * Show a canvas by growing it vertically to its fully drawn height over a period of time. This method will not fire
     * if the widget is already drawn and visible, or has overflow other than <code>"visible"</code> or
     * <code>"hidden"</code>.
     *
     * @param effect AnimateShowEffect effect to use when revealing the widget. If omitted, default behavior can be
     *               configured via {@link com.smartgwt.client.widgets.Canvas#getAnimateShowEffect animateShowEffect}
     * @param callback When the show completes this callback will be fired. Single
     *                 'earlyFinish' parameter will be passed if the animation was                              cut
     *                 short by a call to finishAnimation.
     * @param duration Duration in ms of the animated show. If unset, duration will be   picked up from {@link
     *                 com.smartgwt.client.widgets.Canvas#getAnimateShowTime animateShowTime}
     */
    public native void animateShow(AnimateShowEffect effect, AnimationCallback callback, int duration) /*-{
        var effectVal = effect.@com.smartgwt.client.widgets.drawing.AnimateShowEffect::getJsObj()();
        var self      = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.animateShow(effectVal, $entry(function(earlyFinish) {
            earlyFinish = earlyFinish === undefined ? false : earlyFinish;
            if(callback != null) callback.@com.smartgwt.client.widgets.AnimationCallback::execute(Z)(earlyFinish);
        }), duration);
    }-*/;

    /**
     * Update the canvas shadow.
     */
    public native void updateShadow() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.updateShadow();
    }-*/;

    /**
     * Get the z-Index of this canvas.
     *
     * @return the zIndex
     */
    public native int getZIndex() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        return self.getZIndex(true);
    }-*/;

    /**
     * get the next zIndex for the next item to be drawn. see setZIndex() for notes
     *
     * @return the next zIndex
     */
    public native int getNextZIndex() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        return self.getNextZIndex();
    }-*/;

    /**
     * Set the z-Index of the canvas.
     *
     * @param newIndex the new  zIndex to set to
     */
    public native void setZIndex(int newIndex) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setZIndex(newIndex);
    }-*/;

    public native Rectangle getPageRect() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var rect = self.getPageRect();
        return @com.smartgwt.client.core.Rectangle::new(IIII)(rect[0], rect[1],rect[2],rect[3]);
    }-*/;


    /**
     * Set all four coordinates, relative to the enclosing context, at once. <P> Moves the widget so that its top-left
     * corner is at the specified top-left coordinates, then resizes it to the specified width and height.
     *
     * @param rectangle new coordinates
     */
    public void setRect(Rectangle rectangle) {
        setRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Set all four coordinates, relative to the enclosing context, at once. <P> Moves the widget so that its top-left
     * corner is at the specified top-left coordinates, then resizes it to the specified width and height.
     *
     * @param left   new left coordinate
     * @param top    new top coordinate
     * @param width  new width
     * @param height new height
     */
    public native void setRect(int left, int top, int width, int height) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.setRect(left, top, width, height);
    }-*/;

    /**
     * Get all four coordinates, relative to the enclosing context, at one.
     *
     * @return the Rectangle
     */
    public native Rectangle getRect() /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var rect = self.getRect();
        return @com.smartgwt.client.core.Rectangle::new(IIII)(rect[0], rect[1],rect[2],rect[3]);
    }-*/;


    /**
     * Return the full URL for an image to be drawn in this canvas. <P> If the passed URL begins with the special
     * prefix "[SKIN]", it will have the widget.skinImgDir and Page.skinImgDir prepended.  Otherwise the image is
     * assumed to be application-specific, and will have the widget.appImgDir and Page.appImgDir automatically
     * prepended. <P> Note that if passed an absolute path (starting with "/" or "http://" for example), no extra
     * image directory information will be prepended to the generated URL.//
     *
     * @param URL URL local to skin or application image directory
     * @return URL to use
     */
    public static native String getImgURL(String URL) /*-{
            return $wnd.isc.Canvas.getImgURL(URL);
        }-*/;

    /**
     * Return the full URL for an image to be drawn in this canvas. <P> If the passed URL begins with the special
     * prefix "[SKIN]", it will have the widget.skinImgDir and Page.skinImgDir prepended.  Otherwise the image is
     * assumed to be application-specific, and will have the widget.appImgDir and Page.appImgDir automatically
     * prepended. <P> Note that if passed an absolute path (starting with "/" or "http://" for example), no extra
     * image directory information will be prepended to the generated URL.//
     *
     * @param URL    URL local to skin or application image directory
     * @param imgDir optional image directory to override the default for this Canvas
     * @return URL to use
     */
    public static native String getImgURL(String URL, String imgDir) /*-{
            return $wnd.isc.Canvas.getImgURL(URL, imgDir);
        }-*/;

    /**
     * Generates the HTML for an image unique to this Canvas. <P> The full URL for the image will be formed
     * according to the rules documented for <code>{@link com.smartgwt.client.widgets.Canvas#getImgURL}</code>. <P>
     * The created image will have an identifier unique to this Canvas, and subsequent calls to <code>{@link
     * com.smartgwt.client.widgets.Canvas#getImage}</code> and <code>{@link com.smartgwt.client.widgets.Canvas#setImage}</code>
     *  with the name passed to this function will act on the image object produced by the HTML returned from this
     * call.
     *
     * @param src URL local to the skin or application directory.<br>  NOTE: instead of passing several parameters,
     *            you can pass an object as the 'src'      parameter with properties for all the various function
     *            parameters with, eg:<br>      canvas.imgHTML( {src:"foo", width:10, height:10} );
     * @return HTML to draw the image.
     */
    public static native String imgHTML(String src) /*-{
            return $wnd.isc.Canvas.imgHTML(src);
        }-*/;

    /**
     * Generates the HTML for an image unique to this Canvas. <P> The full URL for the image will be formed
     * according to the rules documented for <code>{@link com.smartgwt.client.widgets.Canvas#getImgURL}</code>. <P>
     * The created image will have an identifier unique to this Canvas, and subsequent calls to <code>{@link
     * com.smartgwt.client.widgets.Canvas#getImage}</code> and <code>{@link com.smartgwt.client.widgets.Canvas#setImage}</code>
     *  with the name passed to this function will act on the image object produced by the HTML returned from this
     * call.
     *
     * @param src        URL local to the skin or application directory.<br>  NOTE: instead of passing several
     *                   parameters, you can pass an object as the 'src'      parameter with properties for all the
     *                   various function parameters with, eg:<br>      canvas.imgHTML( {src:"foo", width:10,
     *                   height:10} );
     * @param width      width of the image
     * @param height     height of the image
     * @param name       name for the image
     * @param extraStuff additional attributes to write in the tag
     * @param imgDir     image-specific image directory to override the default                                  for
     *                   this Canvas
     * @return HTML to draw the image.
     */
    public static native String imgHTML(String src, int width, int height, String name, String extraStuff, String imgDir) /*-{
            return $wnd.isc.Canvas.imgHTML(src, width, height, name, extraStuff, imgDir);
        }-*/;

    public static native String imgHTML(String src, int width, int height) /*-{
         return $wnd.isc.Canvas.imgHTML(src, width, height);
     }-*/;

    /**
     * Show a PrintWindow containing a printable view of the component passed in containing print HTML.
     *
     * @param component to get printable HTML for.
     */
    public static native void showPrintPreview(Canvas component) /*-{
         var componentJS = component.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
         return $wnd.isc.Canvas.showPrintPreview(componentJS);
     }-*/;

    /**
     * Show a Print containing a printable view of the components passed in.
     * @param components components to get the print HTML for. May also include  raw HTML strings which will be folded into the generated print
     * output
     */
    public static native void showPrintPreview(Object[] components) /*-{
     	var componentsJS = @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(components);
        $wnd.isc.Canvas.showPrintPreview(componentsJS);
    }-*/;


    /**
     * Show a Print containing a printable view of the components passed in.
     * @param components components to get the print HTML for. May also include  raw HTML strings which will be folded into the generated print
     * output
     * @param printProperties for customizing the print HTML output. If this parameter is passed as null, it will be ignored.
     * @param title for the print window
     * @param callback to fire when the print preview window has been created and shown
     */
    public static native void showPrintPreview(Object[] components, PrintProperties printProperties, String title, PrintPreviewCallback callback) /*-{
        var componentsJS = @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(components);
        var ppJS = printProperties == null ? null : printProperties.@com.smartgwt.client.util.PrintProperties::getJsObj()();
        var pvpJS = {};
        if(title != null) $wnd.isc.addProperties(pvpJS, {title:title});
        $wnd.isc.Canvas.showPrintPreview(componentsJS, ppJS, pvpJS,
        callback == null ? null :
	        $entry(function (printCanvas, printWindow) {
	        	var canvasJ = @com.smartgwt.client.widgets.PrintCanvas::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(printCanvas);
	        	var windowJ = @com.smartgwt.client.widgets.PrintWindow::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(printWindow);
	        	callback.@com.smartgwt.client.util.PrintPreviewCallback::execute(Lcom/smartgwt/client/widgets/PrintCanvas;Lcom/smartgwt/client/widgets/PrintWindow;)(canvasJ, windowJ);
	        })
         );
    }-*/;

    /**
     * Show a Print containing a printable view of the components passed in.
     * @param components components to get the print HTML for. May also include  raw HTML strings which will be folded into the generated print
     * output
     * @param printProperties for customizing the print HTML output. If this parameter is passed as null, it will be ignored.
     * @param title for the print window
     * @param callback to fire when the print preview window has been created and shown
     * @param printWindowProperties the print window properties
     * @param printButtonTitle the brint button title
     */
    public static native void showPrintPreview(Object[] components, PrintProperties printProperties, String title, PrintPreviewCallback callback, Window printWindowProperties, String printButtonTitle) /*-{
        var componentsJS = @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(components);
        var ppJS = printProperties == null ? null : printProperties.@com.smartgwt.client.util.PrintProperties::getJsObj()();
        var pvpJS = printWindowProperties == null ? {} : printWindowProperties.@com.smartgwt.client.widgets.Window::getConfig()();
        if(title != null) $wnd.isc.addProperties(pvpJS, {title:title});
        if(printButtonTitle != null) $wnd.isc.addProperties(pvpJS, {printButtonTitle:printButtonTitle});

        $wnd.isc.Canvas.showPrintPreview(componentsJS, ppJS, pvpJS,
        callback == null ? null :
	        $entry(function (printCanvas, printWindow) {
	        	var canvasJ = @com.smartgwt.client.widgets.PrintCanvas::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(printCanvas);
	        	var windowJ = @com.smartgwt.client.widgets.PrintWindow::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(printWindow);
	        	callback.@com.smartgwt.client.util.PrintPreviewCallback::execute(Lcom/smartgwt/client/widgets/PrintCanvas;Lcom/smartgwt/client/widgets/PrintWindow;)(canvasJ, windowJ);
	        })
         );
    }-*/;

    /**
     * Returns print-formatted HTML for the specified list of components.
     * @param components Components to get the print HTML for. Strings of raw HTML may  also be included in this array, and will be integrated
     * into the final HTML at the appropriate  point.
     * <code>HTML</code>.
     * @param printProperties for customizing the print HTML output. If this parameter is passed as null, it will be ignored.
     * @param callback to fire when the HTML has been generated
     * @see #getPrintHTML(PrintProperties, PrintHTMLCallback)
     */
    public static native void getPrintHTML(Object[] components, PrintProperties printProperties, PrintHTMLCallback callback) /*-{
    	var componentsJS = @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(components);
        var ppJS = printProperties == null ? null : printProperties.@com.smartgwt.client.util.PrintProperties::getJsObj()();
        $wnd.isc.Canvas.getPrintHTML(componentsJS, ppJS,
         callback == null ? null :
         $entry(function (HTML) {
        	callback.@com.smartgwt.client.util.PrintHTMLCallback::setHTML(Ljava/lang/String;)(HTML);
        }));
    }-*/;

	/**
     * Retrieves printable HTML for this component and all printable subcomponents. <P>
     * By default any Canvas with children will simply collect the printable HTML of its
     * children by calling getPrintHTML() on each child that is considered 
     * {@link com.smartgwt.client.widgets.Canvas#getShouldPrint printable}. If a callback is
     * provided, then null is always returned and the callback is fired asynchronously. <P>
     * If overriding this method for a custom component, you should <b>either</b> return a
     * String of printable HTML directly <b>or</b> return null and fire the provided callback
     * using Class.fireCallback(). <P> To return an empty print representation, return an empty
     * string ("") rather than null. <P> The <code>printProperties</code> argument, if passed,
     * must be passed to any subcomponents on which <code>getPrintHTML()</code> is called. <P>
     * Default implementation will set 
     * {@link* com.smartgwt.client.widgets.Canvas#getIsPrinting isPrinting} flag to
     * <code>true</code> to indicate printing is in* progress, and clear this flag when the
     * printing has completed (possibly via an asynchronous callback). <P> <B>NOTE:</B>
     * Expecting a direct return value from the default implementation is deprecated usage. This
     * is because small changes to an application (such as adding a few more data points to a
     * chart or adding another button) or using certain browsers can make it necessary to
     * generate the HTML asynchronously. Thus, application code should not rely on the return
     * value and always pass a callback. <P>
     * <B>NOTE:</B> CubeGrid does not support a WYSIWYG print view by default (also used when
     * {@link com.smartgwt.client.rpc.RPCManager#exportContent exporting to pdf}). Instead we recommend
     * {@link com.smartgwt.client.data.DataSource#exportClientData exporting to excel or csv format}.
     *
     * @param printProperties properties to configure printing behavior - may be null.
     * @param callback optional callback.  This is required to handle cases where HTML generation
     * is asynchronous - if a method generates HTML asynchronously, it should return null, and
     * fire the specified callback on completion of HTML generation. The first parameter
     * <code>HTML</code> should contain the generated print HTML.  The callback is only called
     * if null is returned.  Furthermore, the default getPrintHTML() implementation always
     * returns null and fires the callback when a callback is provided.  See 
     * {@link com.smartgwt.client.docs.Callback Callback}.
     *
     * @return null if the print HTML is being generated asynchronously and/or a callback is
     * provided; otherwise, the direct print HTML for this component (but note that returning
     * direct print HTML is a deprecated feature).
     * @see com.smartgwt.client.docs.Printing Printing overview and related methods
     */
    public native String getPrintHTML(PrintProperties printProperties, PrintHTMLCallback callback) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        if (self._jsOverrideCall.getPrintHTML) {
            self._fireDefaultMethod.getPrintHTML = true;
            return;
        }
        var ppJS = printProperties == null ? null : printProperties.@com.smartgwt.client.util.PrintProperties::getJsObj()();

        var ret = self.__getPrintHTML(
                ppJS,
                callback == null ? null :
                    $entry(function (HTML) {
                        callback.@com.smartgwt.client.util.PrintHTMLCallback::setHTML(Ljava/lang/String;)(HTML);
                    })
         );
        return ret === undefined ? null : ret;
    }-*/;

	private String getPrintHTMLJSCB(PrintProperties printProperties, final JavaScriptObject jscallback) {
		if (jscallback == null) {
			return getPrintHTML(printProperties, null);
		} else {
			PrintHTMLCallback jcb = new PrintHTMLCallback() {
				public void setHTML (String HTML) {
					executeJS(HTML, jscallback, getOrCreateJsObj());
				}
				private native void executeJS (String HTML, JavaScriptObject jscallback, JavaScriptObject canvas) /*-{
					canvas.fireCallback(jscallback, ["HTML", "callback"], [HTML,jscallback]);
				}-*/;
			};
			return getPrintHTML(printProperties, jcb);
		}
	}

    /**
     * Generate printable HTML for the designated components and trigger the native print dialog, without ever showing the
     * printable HTML to the user.
     * @param components components to get the print HTML for. May also include        raw HTML strings which will be folded into the generated
     * print output
     */
    public static native void printComponents(Object[] components) /*-{
    	var componentsJS = @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(components);
        $wnd.isc.Canvas.printComponents(componentsJS);
    }-*/;

    /**
     * Sets the SmartClient constructor for the AutoChild named <code>autoChildName</code>.
     *
     * @param autoChildName the name of the AutoChild
     * @param className the SmartClient class name to use when constructing the AutoChild.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public native void setAutoChildConstructor(String autoChildName, String className) /*-{
        var self;
        if (this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            self = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
        } else {
            self = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        }
        self[autoChildName + "Constructor"] = className;
    }-*/;

    /**
     * Sets the properties for creating a <code>Canvas</code> AutoChild named <code>autoChildName</code>.
     *
     * <p><code>properties</code> must not have already been created 
     * ({@link com.smartgwt.client.widgets.BaseWidget#isCreated() properties.isCreated()}
     * must be <code>false</code>).
     *
     * <p><b>NOTE:</b> Overrides at override points are not applied to AutoChildren created from
     * <code>properties</code>; that is, if the Java {@link java.lang.Class} of <code>properties</code>
     * overrides a Smart&nbsp;GWT override point, the custom method implementation will not be called.
     *
     * @throws IllegalStateException if <code>properties</code> has been created.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public void setAutoChildProperties(String autoChildName, Canvas properties) 
        throws IllegalStateException 
    {
        if (properties.isCreated()) {
            ConfigUtil.warnOfPreConfigInstantiation(this.getClass(), "setAutoChildProperties",
                                                    properties.getClass());
        }
        properties.setConfigOnly(true);

        final JavaScriptObject selfJS = isCreated() ? getJsObj() : getConfig();
        JSOHelper.setAttribute(selfJS, autoChildName + "Properties", 
                               JSOHelper.cleanProperties(properties.getConfig(), true));
    }

    /**
     * Sets the properties for creating a <code>FormItem</code> AutoChild named <code>autoChildName</code>.
     *
     * <p><code>properties</code> must not have already been created 
     * ({@link com.smartgwt.client.widgets.form.fields.FormItem#isCreated() properties.isCreated()}
     * must be <code>false</code>).
     *
     * <p><b>NOTE:</b> Overrides at override points are not applied to AutoChildren created from
     * <code>properties</code>; that is, if the Java {@link java.lang.Class} of <code>properties</code>
     * overrides a Smart&nbsp;GWT override point, the custom method implementation will not be called.
     *
     * @throws IllegalStateException if <code>properties</code> has been created.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public void setAutoChildProperties(String autoChildName, FormItem properties) 
        throws IllegalStateException 
    {
        if (properties.isCreated()) {
            ConfigUtil.warnOfPreConfigInstantiation(this.getClass(), "setAutoChildProperties",
                                                    properties.getClass());
        }
        properties.setConfigOnly(true);

        final JavaScriptObject selfJS = isCreated() ? getJsObj() : getConfig();
        JSOHelper.setAttribute(selfJS, autoChildName + "Properties", 
                               properties.getEditorTypeConfig());
    }

    /**
     * Sets the properties for creating an AutoChild named <code>autoChildName</code>.
     *
     * <p><code>properties</code> must not have already been created
     * ({@link com.smartgwt.client.tools.EditProxy#isCreated() properties.isCreated()}
     * must be <code>false</code>).
     *
     * @throws IllegalStateException if <code>properties</code> has been created.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public void setAutoChildProperties(String autoChildName, EditProxy properties)
        throws IllegalStateException 
    {
        if (properties.isCreated()) {
            ConfigUtil.warnOfPreConfigInstantiation(this.getClass(), "setAutoChildProperties",
                                                    properties.getClass());
        }
        properties.setConfigOnly(true);

        final JavaScriptObject selfJS = isCreated() ? getJsObj() : getConfig();
        JSOHelper.setAttribute(selfJS, autoChildName + "Properties",
                               JSOHelper.cleanProperties(properties.getConfig(), true));
    }

    /**
     * Sets the properties for creating an AutoChild named <code>autoChildName</code>.
     *
     * <p><code>properties</code> must not have already been created 
     * ({@link com.smartgwt.client.tools.EditContext#isCreated() properties.isCreated()}
     * must be <code>false</code>).
     *
     * @throws IllegalStateException if <code>properties</code> has been created.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public void setAutoChildProperties(String autoChildName, EditContext properties)
        throws IllegalStateException 
    {
        if (properties.isCreated()) {
            ConfigUtil.warnOfPreConfigInstantiation(this.getClass(), "setAutoChildProperties",
                                                    properties.getClass());
        }
        properties.setConfigOnly(true);        

        final JavaScriptObject selfJS = isCreated() ? getJsObj() : getConfig();
        JSOHelper.setAttribute(selfJS, autoChildName + "Properties",
                               JSOHelper.cleanProperties(properties.getConfig(), true));
    }

    /**
     * Sets whether to create and show the AutoChild named <code>autoChildName</code>.
     * <p>
     * <b>NOTE:</b> This API is not valid for all AutoChildren because some AutoChildren cannot
     * be hidden without destroying the main functions of the component.
     *
     * @param autoChildName name of an AutoChild
     * @param visible whether to show the AutoChild
     */
    public void setAutoChildVisibility(String autoChildName, boolean visible) {
        setAttribute("show" + Character.toUpperCase(autoChildName.charAt(0)) + autoChildName.substring(1), visible, true);
    }

    /**
     * Returns the <code>Canvas</code> AutoChild named <code>autoChildName</code> if already created.
     *
     * @param autoChildName name of an AutoChild to return.
     * @throws RuntimeException if the AutoChild is not a &#83;martClient <code>Canvas</code> object.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public native final Canvas getCanvasAutoChild(String autoChildName) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var autoChildJS = self[autoChildName];
        if (autoChildJS == null) return null;
        if (!$wnd.isc.isA.Canvas(autoChildJS)) throw @java.lang.RuntimeException::new(Ljava/lang/String;)("The '" + autoChildName + "' AutoChild is not a Canvas.");

        // getCanvasAutoChild() should return the same Object.
        if (autoChildJS.__ref !== undefined) return autoChildJS.__ref;
        return (autoChildJS.__ref = @com.smartgwt.client.util.ObjectFactory::createCanvas(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(autoChildJS.getClassName(), autoChildJS));
    }-*/;

    /**
     * Returns the <code>FormItem</code> AutoChild named <code>autoChildName</code> if already created.
     *
     * @param autoChildName name of an AutoChild to return.
     * @throws RuntimeException if the AutoChild is not a &#83;martClient <code>FormItem</code> object.
     * @see com.smartgwt.client.docs.AutoChildUsage
     */
    public native final FormItem getFormItemAutoChild(String autoChildName) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var autoChildJS = self[autoChildName];
        if (autoChildJS == null) return null;
        if (!$wnd.isc.isA.FormItem(autoChildJS)) throw @java.lang.RuntimeException::new(Ljava/lang/String;)("The '" + autoChildName + "' AutoChild is not a FormItem.");

        // getFormItemAutoChild() should return the same Object.
        if (autoChildJS.__ref !== undefined) return autoChildJS.__ref;
        return (autoChildJS.__ref = @com.smartgwt.client.util.ObjectFactory::createFormItem(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(autoChildJS.getClassName(), autoChildJS));
    }-*/;

    /**
     * Adds newChild as a child of this widget, set up a named object reference (i.e., this[name]) to the new widget if
     * name argument is provided, and draw the child if this widget has been drawn already. <P> If newChild has a parent
     * it will be removed from it. If it has a master, it will be detached from it if the master is a child of a
     * different parent. If newChild has peers, they'll be added to this widget as children as well.
     *
     * @param newChild new child canvas to add
     */
    public void addChild(Widget widget) {
        if (widget instanceof Canvas) {
            addChild((Canvas) widget);
        } else {
            addChild(new WidgetCanvas(widget));
        }
    }

    /**
     * Adds newChild as a child of this widget, set up a named object reference (i.e., this[name]) to the new widget if name
     * argument is provided, and draw the child if this widget has been drawn already. <P> If newChild has a parent it will be
     * removed from it. If it has a master, it will be detached from it if the master is a child of a different parent. If
     * newChild has peers, they'll be added to this widget as children as well.
     * @param newChild new child canvas to add
     * @param name name to assign to child (eg: this[name] == child)
     * @param autoDraw if false, child will not automatically be drawn (only for advanced use)
     *
     * @return the new child, or null if it couldn't be added
     * @see com.smartgwt.client.docs.Containment Containment overview and related methods
     */
    public native Canvas addChild(Canvas newChild, String name, boolean autoDraw) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var ret = self.addChild(newChild.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), name, autoDraw);
        if(ret == null) return null;
        return @com.smartgwt.client.widgets.Canvas::getByJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)(ret);
    }-*/;

    /**
     * Array of all Canvii that are immediate children of this Canvas. <p><b>Note : </b> This is an advanced
     * setting</p>
     *
     * @param children children Default value is null
     */
    public void setChildren(Canvas... children) {
        if(!isDrawn()) {
            setAttribute("children", children, false);
        } else {
            for (int i = 0; i < children.length; i++) {
                Canvas child = children[i];
                addChild(child);
            }
        }
    }

    public int getOffsetHeight() {
        return getVisibleHeight();
    }

    public int getOffsetWidth() {
        return getVisibleWidth();
    }

    /**
     * Set the minimum value for height.
     * <p>
     * For use with setOverflow(Overflow.VISIBLE), where widgets render only as large as their contents
     * and/or children, and height and width settings act as a minimum size.
     */
    public void setAutoHeight() {
        setHeight(1);
    }

    /**
     * Set the minimum value for width.
     * <p>
     * For use with setOverflow(Overflow.VISIBLE), where widgets render only as large as their contents
     * and/or children, and height and width settings act as a minimum size.
     */
    public void setAutoWidth() {
        setWidth(1);
    }

    /**
     * Show a clickMask over the entire screen that intercepts mouse clicks and fires some action. The mask created will be
     * associated with this canvas - calling this method multiple times will not show multiple (stacked) clickMasks if the mask
     * associated with this canvas is  already up.<br><br> The clickMask useful for modal dialogs, menus and similar uses,
     * where any click outside of some Canvas should either be suppressed (as in a modal dialog) or just cause something (like
     * dismissing a menu).
     * @param clickActionCallback action to fire when the user clicks on the mask
     * @param mode whether to automatically hide the clickMask on mouseDown                                      and suppress the mouseDown
     * event from reaching                                      the target under the mouse
     * @param unmaskedTargets initially unmasked targets for this clickMask. Note that if this is a  <code>"hard"</code> mask, unmasked children of
     * masked parents are not supported  so any non-top-level widgets passed in will have their parents unmasked.  Children of
     * masked parents can never be masked.
     *
     * @return clickMask ID
     */
    public native String showClickMask(Function clickActionCallback, ClickMaskMode mode, Canvas[] unmaskedTargets) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var unmaskedTargetsJS = @com.smartgwt.client.util.JSOHelper::convertToJavaScriptArray([Ljava/lang/Object;)(unmaskedTargets);
        return self.showClickMask($entry(function() {
           if(clickActionCallback != null) clickActionCallback.@com.smartgwt.client.core.Function::execute()();
        }), mode.@com.smartgwt.client.types.ClickMaskMode::getValue()(), unmaskedTargetsJS);
    }-*/;

    /**
     * Does this widget contain the specified global (x,y) coordinates, and have no other widgets also at the specified
     * position, obscuring this one?  This is commonly used for (for example) drag and drop interactions.
     * @param x GLOBAL x-coordinate
     * @param y GLOBAL y-coordinate
     * @param withinViewport point lies within our viewport rather than just our drawn area
     * @param ignoreWidgets If passed ignore widget(s), do not check whether those widgets occludes this one
     * @param upToParent If passed, only check for siblings occluding the component up as far as the specified parent widget
     *
     * @return true if this object contains the specified point; false otherwise
     * @see com.smartgwt.client.docs.Positioning Positioning overview and related methods
     */
    public native Boolean visibleAtPoint(int x, int y, boolean withinViewport, Canvas ignoreWidgets, Canvas upToParent) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var retVal =self.visibleAtPoint(x, y, withinViewport, ignoreWidgets == null ? null : ignoreWidgets.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(), upToParent == null ? null : upToParent.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()());
        if(retVal == null) {
            return null;
        } else {
                return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(retVal);
        }
    }-*/;

    /**
     * Set a specific ARIA state mapping for this component. Usually this does not need to be manually
     * set. See {@link com.smartgwt.docs.Accessibility}.
     * @param stateName
     * @param stateValue
     * @throws IllegalStateException ARIA state cannot be changed after the component has been created.
     */
    public void setAriaState(String stateName, Object stateValue) throws IllegalStateException {
        JavaScriptObject ariaState = JSOHelper.getAttributeAsJavaScriptObject(getConfig(), "ariaState");
        if (ariaState == null) ariaState = JSOHelper.createObject();
        // We currently don't allow changing this post-create. Make sure that this component
        // has not been created before setting a property on the ARIA state object because
        // the property would be set before the IllegalStateException is thrown, so the
        // ariaState could be effectively changed post-create as long as setAriaState() was
        // called at least once before create to set a JSO in the config (which is copied by
        // reference to the live object).
        if (!isCreated()) JSOHelper.setAttribute(ariaState, stateName, stateValue);
        setAttribute("ariaState", ariaState,  false);
    }

	/**
     * A callback invoked for each {@link com.smartgwt.client.tools.EditNode#getLiveObject liveObject} by {@link
     * com.smartgwt.client.tools.EditContext} when the EditContext is being serialized. The liveObject may make any updates
     * needed to the {@link com.smartgwt.client.tools.EditNode} (or the {@link com.smartgwt.client.tools.EditContext} as a
     * whole) in order to be able to later recreate the objects.
     * <br><b>Note: This is an override point</b>
     * @param editContext the EditContext
     * @param editNode the EditNode
     * @see com.smartgwt.client.tools.EditContext#serializeAllEditNodes
     * @see com.smartgwt.client.tools.EditContext#serializeEditNodes
     */
    public native void updateEditNode(EditContext editContext, EditNode editNode) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
		if (self.__updateEditNode == null) return;

        // The EditContext is probably a BaseWidget, but it doesn't have to be.
        // We could use instanceof or Class.isAssignableFrom to figure out how
        // to get the JS object. However, JSOHelper.convertToJavaScriptArray has
        // code that does exactly that already. So, we call it, via a wrapper in
        // BeanValueType (for convenience, to avoid having to create the Java
        // array argument here in JSNI).
        var editContextJS = @com.smartgwt.client.bean.BeanValueType::convertToJavaScriptObject(Ljava/lang/Object;)(editContext);

        self.__updateEditNode(editContextJS, editNode.@com.smartgwt.client.core.DataClass::getJsObj()());
    }-*/;

	/**
     * Set customizer that defines 
     * {@link com.smartgwt.client.widgets.AdaptWidthByeCustomizer#adaptWidthBy adaptWidthBy}.
     * @param AdaptWidthByCustomizer customizer
     * @see com.smartgwt.client.widgets.AdaptWidthByCustomizer
     * @see com.smartgwt.client.widgets.Canvas#setCanAdaptWidth
     */
    public native void setAdaptWidthByCustomizer(AdaptWidthByCustomizer customizer) /*-{
        var self;
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            self = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
        } else {
            self = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        }
        // stick a reference to the Canvas on the customizer
        customizer.@com.smartgwt.client.widgets.AdaptWidthByCustomizer::setCanvas(Lcom/smartgwt/client/widgets/Canvas;)(this);
        // there is no default implementation of isc.Canvas.adaptWidthBy()
        self.adaptWidthBy = $debox($entry(function(deltaX, width) {
            var deltaXJ = $wnd.isc.isA.Number(deltaX) ? deltaX.valueOf() : 0,
                width   = $wnd.isc.isA.Number(width)  ?  width.valueOf() : 0;
        	return customizer.@com.smartgwt.client.widgets.AdaptWidthByCustomizer::adaptWidthBy(II)(deltaXJ, width);
    	}));
    }-*/;

	/**
     * Set customizer that defines 
     * {@link com.smartgwt.client.widgets.AdaptHeightByeCustomizer#adaptHeightBy adaptHeightBy}.
     * @param AdaptHeightByCustomizer customizer
     * @see com.smartgwt.client.widgets.AdaptHeightByCustomizer
     * @see com.smartgwt.client.widgets.Canvas#setCanAdaptHeight
     */
    public native void setAdaptHeightByCustomizer(AdaptHeightByCustomizer customizer) /*-{
        var self;
        if(this.@com.smartgwt.client.widgets.BaseWidget::isCreated()()) {
            self = this.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
        } else {
            self = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        }
        // stick a reference to the Canvas on the customizer
        customizer.@com.smartgwt.client.widgets.AdaptHeightByCustomizer::setCanvas(Lcom/smartgwt/client/widgets/Canvas;)(this);
        // there is no default implementation of isc.Canvas.adaptHeightBy()
        self.adaptHeightBy = $debox($entry(function(deltaY, height) {
            var deltaYJ = $wnd.isc.isA.Number(deltaY) ? deltaY.valueOf() : 0,
                height  = $wnd.isc.isA.Number(height) ? height.valueOf() : 0;
        	return customizer.@com.smartgwt.client.widgets.AdaptHeightByCustomizer::adaptHeightBy(II)(deltaYJ, height);
    	}));
    }-*/;
    
    
	/**
     * Provide data to the {@link com.smartgwt.client.widgets.Canvas#ruleScope} component,
     * to be made available in the rule context at the specified <code>path</code>.
     * <p>
     * <code>path</code> must be one or more valid identifiers with either dot (.) or slash (/) used
     * as a separator.  
     * <p>
     * <code>data</code> can be any value, including both atomic values like a Boolean or String, or a
     * nested data structure.  Pass <code>data</code> as <code>null</code> to remove data from the
     * context at the specified <code>path</code>
     * <p>
     * <code>dbc</code> is the DataBoundComponent to be identified as the owner of the rule context
     * contribution. This component is used to handle any conflicts between multiple components
     * contributing to the same base path (i.e. first segment of path). For any collision an editable
     * display (such as a form or editable grid) wins over a static display (such as a non-editable
     * grid with a selection). Hidden components have lowest priority even if editable. For two
     * editable components the first becomes the owner.
     *
     * @param path path where data should be made available
     * @param data data to contribute to rule context
     */
    public void provideRuleContext(String dataPath, Object data) {
		JSOHelper.callMethod(getJsObj(), "provideRuleContext", new Object[] {dataPath, data});
    }

	/**
     * Provide data to the {@link com.smartgwt.client.widgets.Canvas#ruleScope} component,
     * to be made available in the rule context at the specified <code>path</code>.
     * <p>
     * <code>path</code> must be one or more valid identifiers with either dot (.) or slash (/) used
     * as a separator.  
     * <p>
     * <code>data</code> can be any value, including both atomic values like a Boolean or String, or a
     * nested data structure.  Pass <code>data</code> as <code>null</code> to remove data from the
     * context at the specified <code>path</code>
     * <p>
     * <code>dbc</code> is the DataBoundComponent to be identified as the owner of the rule context
     * contribution. This component is used to handle any conflicts between multiple components
     * contributing to the same base path (i.e. first segment of path). For any collision an editable
     * display (such as a form or editable grid) wins over a static display (such as a non-editable
     * grid with a selection). Hidden components have lowest priority even if editable. For two
     * editable components the first becomes the owner.
     *
     * @param path path where data should be made available
     * @param data data to contribute to rule context
     * @param dbc the DataBoundComponent contributing to ruleContext
     */
    public void provideRuleContext(String dataPath, Object data, DataBoundComponent  dbc) {
		JSOHelper.callMethod(getJsObj(), "provideRuleContext", new Object[] {dataPath, data, dbc});
    }
    
	/**
     * Reveals the child Canvas passed in by showing it if it is currently hidden (note, this is stub functionality,
     * certain Canvas subclasses such as {@link com.smartgwt.client.widgets.tab.TabSet} have more useful implementations)
     * <b>NOTE: This is an override point.</b>
     * @param childID the global ID of the child Canvas to reveal
     */
    public native void revealChild(String childID) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.__revealChild(childID);
    }-*/;
    
	/**
     * Reveals the child Canvas passed in by showing it if it is currently hidden (note, this is stub functionality,
     * certain Canvas subclasses such as {@link com.smartgwt.client.widgets.tab.TabSet} have more useful implementations)
     * <b>NOTE: This is an override point.</b>
     * @param child the child Canvas to reveal
     */
    public native void revealChild(Canvas child) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        var childJS = child == null ? null : child.@com.smartgwt.client.widgets.BaseWidget::getJsObj()();
        self.__revealChild(childJS);
    }-*/;

    /**
     * Whether to use the browser's native scrollbars or SmartClient-based scrollbars by default
     * for all canvases.
     * <P>
     * This method changes the default value of {@link com.smartgwt.client.widgets.Canvas#showCustomScrollbars}.
     * 
     * @param showCS whether to show custom (SmartGWT-based) scrollbars rather than css-scrollbars by default.
     * 
     */
    public static native void setDefaultShowCustomScrollbars(boolean showCS)  /*-{
        $wnd.isc.Canvas.setShowCustomScrollbars(showCS);
    }-*/;

    /**
     * Setter implementing the {@link com.smartgwt.client.core.LogicalStructure} interface,
     * which supports Eclipse's logical structure debugging facility.
     */
    public LogicalStructureObject setLogicalStructure(CanvasLogicalStructure s) {
        super.setLogicalStructure(s);
        try {
            s.accessKey = getAttributeAsString("accessKey");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.accessKey:" + t.getMessage() + "\n";
        }
        try {
            s.adaptiveHeightPriority = getAttributeAsString("adaptiveHeightPriority");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.adaptiveHeightPriority:" + t.getMessage() + "\n";
        }
        try {
            s.adaptiveWidthPriority = getAttributeAsString("adaptiveWidthPriority");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.adaptiveWidthPriority:" + t.getMessage() + "\n";
        }
        try {
            s.alwaysShowScrollbars = getAttributeAsString("alwaysShowScrollbars");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.alwaysShowScrollbars:" + t.getMessage() + "\n";
        }
        try {
            s.animateAcceleration = getAttributeAsString("animateAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateFadeTime = getAttributeAsString("animateFadeTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateFadeTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateHideAcceleration = getAttributeAsString("animateHideAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateHideAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateHideTime = getAttributeAsString("animateHideTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateHideTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateMoveAcceleration = getAttributeAsString("animateMoveAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateMoveAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateMoveTime = getAttributeAsString("animateMoveTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateMoveTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateRectAcceleration = getAttributeAsString("animateRectAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateRectAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateRectTime = getAttributeAsString("animateRectTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateRectTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateResizeAcceleration = getAttributeAsString("animateResizeAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateResizeAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateResizeTime = getAttributeAsString("animateResizeTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateResizeTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateScrollAcceleration = getAttributeAsString("animateScrollAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateScrollAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateScrollTime = getAttributeAsString("animateScrollTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateScrollTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateShowAcceleration = getAttributeAsString("animateShowAcceleration");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateShowAcceleration:" + t.getMessage() + "\n";
        }
        try {
            s.animateShowTime = getAttributeAsString("animateShowTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateShowTime:" + t.getMessage() + "\n";
        }
        try {
            s.animateTime = getAttributeAsString("animateTime");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.animateTime:" + t.getMessage() + "\n";
        }
        try {
            s.appImgDir = getAttributeAsString("appImgDir");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.appImgDir:" + t.getMessage() + "\n";
        }
        try {
            s.ariaRole = getAttributeAsString("ariaRole");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.ariaRole:" + t.getMessage() + "\n";
        }
        try {
            s.autoMaskComponents = getAttributeAsString("autoMaskComponents");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.autoMaskComponents:" + t.getMessage() + "\n";
        }
        try {
            s.autoParent = getAttributeAsString("autoParent");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.autoParent:" + t.getMessage() + "\n";
        }
        try {
            s.autoShowParent = getAttributeAsString("autoShowParent");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.autoShowParent:" + t.getMessage() + "\n";
        }
        try {
            s.backgroundColor = getAttributeAsString("backgroundColor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.backgroundColor:" + t.getMessage() + "\n";
        }
        try {
            s.backgroundImage = getAttributeAsString("backgroundImage");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.backgroundImage:" + t.getMessage() + "\n";
        }
        try {
            s.backgroundPosition = getAttributeAsString("backgroundPosition");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.backgroundPosition:" + t.getMessage() + "\n";
        }
        try {
            s.backgroundRepeat = getAttributeAsString("backgroundRepeat");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.backgroundRepeat:" + t.getMessage() + "\n";
        }
        try {
            s.border = getAttributeAsString("border");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.border:" + t.getMessage() + "\n";
        }
        try {
            s.canAcceptDrop = getAttributeAsString("canAcceptDrop");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canAcceptDrop:" + t.getMessage() + "\n";
        }
        try {
            s.canAdaptHeight = getAttributeAsString("canAdaptHeight");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canAdaptHeight:" + t.getMessage() + "\n";
        }
        try {
            s.canAdaptWidth = getAttributeAsString("canAdaptWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canAdaptWidth:" + t.getMessage() + "\n";
        }
        try {
            s.canDrag = getAttributeAsString("canDrag");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canDrag:" + t.getMessage() + "\n";
        }
        try {
            s.canDragReposition = getAttributeAsString("canDragReposition");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canDragReposition:" + t.getMessage() + "\n";
        }
        try {
            s.canDragResize = getAttributeAsString("canDragResize");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canDragResize:" + t.getMessage() + "\n";
        }
        try {
            s.canDragScroll = getAttributeAsString("canDragScroll");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canDragScroll:" + t.getMessage() + "\n";
        }
        try {
            s.canDrop = getAttributeAsString("canDrop");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canDrop:" + t.getMessage() + "\n";
        }
        try {
            s.canDropBefore = getAttributeAsString("canDropBefore");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canDropBefore:" + t.getMessage() + "\n";
        }
        try {
            s.canFocus = getAttributeAsString("canFocus");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canFocus:" + t.getMessage() + "\n";
        }
        try {
            s.canHover = getAttributeAsString("canHover");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canHover:" + t.getMessage() + "\n";
        }
        try {
            s.canSelectText = getAttributeAsString("canSelectText");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canSelectText:" + t.getMessage() + "\n";
        }
        try {
            s.canvasItem = getCanvasItem();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.canvasItem:" + t.getMessage() + "\n";
        }
        try {
            s.children = getChildren();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenArray:" + t.getMessage() + "\n";
        }
        try {
            s.childrenResizeSnapAlign = getAttributeAsString("childrenResizeSnapAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenResizeSnapAlign:" + t.getMessage() + "\n";
        }
        try {
            s.childrenSnapAlign = getAttributeAsString("childrenSnapAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenSnapAlign:" + t.getMessage() + "\n";
        }
        try {
            s.childrenSnapCenterAlign = getAttributeAsString("childrenSnapCenterAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenSnapCenterAlign:" + t.getMessage() + "\n";
        }
        try {
            s.childrenSnapEdgeAlign = getAttributeAsString("childrenSnapEdgeAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenSnapEdgeAlign:" + t.getMessage() + "\n";
        }
        try {
            s.childrenSnapResizeToGrid = getAttributeAsString("childrenSnapResizeToGrid");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenSnapResizeToGrid:" + t.getMessage() + "\n";
        }
        try {
            s.childrenSnapToGrid = getAttributeAsString("childrenSnapToGrid");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.childrenSnapToGrid:" + t.getMessage() + "\n";
        }
        try {
            s.contents = getAttributeAsString("contents");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.contents:" + t.getMessage() + "\n";
        }
        try {
            s.contextMenu = getContextMenu();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.contextMenu:" + t.getMessage() + "\n";
        }
        try {
            s.cursor = getAttributeAsString("cursor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.cursor:" + t.getMessage() + "\n";
        }
        try {
            s.dataPath = getAttributeAsString("dataPath");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dataPath:" + t.getMessage() + "\n";
        }
        try {
            s.defaultHeight = getAttributeAsString("defaultHeight");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.defaultHeight:" + t.getMessage() + "\n";
        }
        try {
            s.defaultWidth = getAttributeAsString("defaultWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.defaultWidth:" + t.getMessage() + "\n";
        }
        try {
            s.destroyed = getAttributeAsString("destroyed");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.destroyed:" + t.getMessage() + "\n";
        }
        try {
            s.destroying = getAttributeAsString("destroying");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.destroying:" + t.getMessage() + "\n";
        }
        try {
            s.disabled = getAttributeAsString("disabled");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.disabled:" + t.getMessage() + "\n";
        }
        try {
            s.disabledCursor = getAttributeAsString("disabledCursor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.disabledCursor:" + t.getMessage() + "\n";
        }
        try {
            s.disableTouchScrollingForDrag = getAttributeAsString("disableTouchScrollingForDrag");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.disableTouchScrollingForDrag:" + t.getMessage() + "\n";
        }
        try {
            s.doubleClickDelay = getAttributeAsString("doubleClickDelay");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.doubleClickDelay:" + t.getMessage() + "\n";
        }
        try {
            s.dragAppearance = getAttributeAsString("dragAppearance");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragAppearance:" + t.getMessage() + "\n";
        }
        try {
            s.dragIntersectStyle = getAttributeAsString("dragIntersectStyle");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragIntersectStyle:" + t.getMessage() + "\n";
        }
        try {
            s.dragMaskType = getAttributeAsString("dragMaskType");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragMaskType:" + t.getMessage() + "\n";
        }
        try {
            s.dragMaxHeight = getAttributeAsString("dragMaxHeight");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragMaxHeight:" + t.getMessage() + "\n";
        }
        try {
            s.dragMaxWidth = getAttributeAsString("dragMaxWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragMaxWidth:" + t.getMessage() + "\n";
        }
        try {
            s.dragMinHeight = getAttributeAsString("dragMinHeight");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragMinHeight:" + t.getMessage() + "\n";
        }
        try {
            s.dragMinWidth = getAttributeAsString("dragMinWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragMinWidth:" + t.getMessage() + "\n";
        }
        try {
            s.dragOpacity = getAttributeAsString("dragOpacity");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragOpacity:" + t.getMessage() + "\n";
        }
        try {
            s.dragRepositionAppearance = getAttributeAsString("dragRepositionAppearance");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragRepositionAppearance:" + t.getMessage() + "\n";
        }
        try {
            s.dragRepositionCursor = getAttributeAsString("dragRepositionCursor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragRepositionCursor:" + t.getMessage() + "\n";
        }
        try {
            s.dragResizeAppearance = getAttributeAsString("dragResizeAppearance");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragResizeAppearance:" + t.getMessage() + "\n";
        }
        try {
            s.dragScrollDelay = getAttributeAsString("dragScrollDelay");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragScrollDelay:" + t.getMessage() + "\n";
        }
        try {
            s.dragStartDistance = getAttributeAsString("dragStartDistance");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragStartDistance:" + t.getMessage() + "\n";
        }
        try {
            s.dragTargetAsCanvas = getDragTarget();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragTargetAsCanvas:" + t.getMessage() + "\n";
        }
        try {
            s.dragTargetAsString = getAttributeAsString("dragTarget");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragTargetAsString:" + t.getMessage() + "\n";
        }
        try {
            s.dragType = getAttributeAsString("dragType");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dragType:" + t.getMessage() + "\n";
        }
        try {
            s.dropTypesAsStringArrayArray = getAttributeAsStringArray("dropTypes");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dropTypesAsStringArrayArray:" + t.getMessage() + "\n";
        }
        try {
            s.dynamicContents = getAttributeAsString("dynamicContents");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.dynamicContents:" + t.getMessage() + "\n";
        }
        try {
            s.edgeBackgroundColor = getAttributeAsString("edgeBackgroundColor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeBackgroundColor:" + t.getMessage() + "\n";
        }
        try {
            s.edgeCenterBackgroundColor = getAttributeAsString("edgeCenterBackgroundColor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeCenterBackgroundColor:" + t.getMessage() + "\n";
        }
        try {
            s.edgeImage = getAttributeAsString("edgeImage");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeImage:" + t.getMessage() + "\n";
        }
        try {
            s.edgeMarginSize = getAttributeAsString("edgeMarginSize");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeMarginSize:" + t.getMessage() + "\n";
        }
        try {
            s.edgeOffset = getAttributeAsString("edgeOffset");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeOffset:" + t.getMessage() + "\n";
        }
        try {
            s.edgeOpacity = getAttributeAsString("edgeOpacity");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeOpacity:" + t.getMessage() + "\n";
        }
        try {
            s.edgeShowCenter = getAttributeAsString("edgeShowCenter");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeShowCenter:" + t.getMessage() + "\n";
        }
        try {
            s.edgeSize = getAttributeAsString("edgeSize");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.edgeSize:" + t.getMessage() + "\n";
        }
        try {
            s.editNode = getEditNode();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.editNode:" + t.getMessage() + "\n";
        }
        try {
            s.editProxyConstructor = getAttributeAsString("editProxyConstructor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.editProxyConstructor:" + t.getMessage() + "\n";
        }
        try {
            s.enableWhen = getEnableWhen();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.enableWhen:" + t.getMessage() + "\n";
        }
        try {
            s.endLine = getAttributeAsString("endLine");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.endLine:" + t.getMessage() + "\n";
        }
        try {
            s.extraSpace = getAttributeAsString("extraSpace");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.extraSpace:" + t.getMessage() + "\n";
        }
        try {
            s.groupBorderCSS = getAttributeAsString("groupBorderCSS");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.groupBorderCSS:" + t.getMessage() + "\n";
        }
        try {
            s.groupLabelBackgroundColor = getAttributeAsString("groupLabelBackgroundColor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.groupLabelBackgroundColor:" + t.getMessage() + "\n";
        }
        try {
            s.groupLabelStyleName = getAttributeAsString("groupLabelStyleName");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.groupLabelStyleName:" + t.getMessage() + "\n";
        }
        try {
            s.groupTitle = getAttributeAsString("groupTitle");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.groupTitle:" + t.getMessage() + "\n";
        }
        try {
            s.hideUsingDisplayNone = getAttributeAsString("hideUsingDisplayNone");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hideUsingDisplayNone:" + t.getMessage() + "\n";
        }
        try {
            s.hoverAlign = getAttributeAsString("hoverAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverAlign:" + t.getMessage() + "\n";
        }
        try {
            s.hoverAutoDestroy = getAttributeAsString("hoverAutoDestroy");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverAutoDestroy:" + t.getMessage() + "\n";
        }
        try {
            s.hoverAutoFitMaxWidthAsString = getAttributeAsString("hoverAutoFitMaxWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverAutoFitMaxWidthAsString:" + t.getMessage() + "\n";
        }
        try {
            s.hoverAutoFitWidth = getAttributeAsString("hoverAutoFitWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverAutoFitWidth:" + t.getMessage() + "\n";
        }
        try {
            s.hoverDelay = getAttributeAsString("hoverDelay");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverDelay:" + t.getMessage() + "\n";
        }
        try {
            s.hoverHeight = getAttributeAsString("hoverHeight");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverHeight:" + t.getMessage() + "\n";
        }
        try {
            s.hoverMoveWithMouse = getAttributeAsString("hoverMoveWithMouse");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverMoveWithMouse:" + t.getMessage() + "\n";
        }
        try {
            s.hoverOpacity = getAttributeAsString("hoverOpacity");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverOpacity:" + t.getMessage() + "\n";
        }
        try {
            s.hoverStyle = getAttributeAsString("hoverStyle");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverStyle:" + t.getMessage() + "\n";
        }
        try {
            s.hoverVAlign = getAttributeAsString("hoverVAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverVAlign:" + t.getMessage() + "\n";
        }
        try {
            s.hoverWidth = getAttributeAsString("hoverWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverWidth:" + t.getMessage() + "\n";
        }
        try {
            s.hoverWrap = getAttributeAsString("hoverWrap");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.hoverWrap:" + t.getMessage() + "\n";
        }
        try {
            s.htmlElement = getAttributeAsString("htmlElement");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.htmlElement:" + t.getMessage() + "\n";
        }
        try {
            s.htmlPosition = getAttributeAsString("htmlPosition");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.htmlPosition:" + t.getMessage() + "\n";
        }
        try {
            s.ID = getAttributeAsString("ID");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.ID:" + t.getMessage() + "\n";
        }
        try {
            s.isGroup = getAttributeAsString("isGroup");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.isGroup:" + t.getMessage() + "\n";
        }
        try {
            s.isPrinting = getAttributeAsString("isPrinting");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.isPrinting:" + t.getMessage() + "\n";
        }
        try {
            s.isRuleScope = getAttributeAsString("isRuleScope");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.isRuleScope:" + t.getMessage() + "\n";
        }
        try {
            s.isSnapAlignCandidate = getAttributeAsString("isSnapAlignCandidate");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.isSnapAlignCandidate:" + t.getMessage() + "\n";
        }
        try {
            s.leavePageSpace = getAttributeAsString("leavePageSpace");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.leavePageSpace:" + t.getMessage() + "\n";
        }
        try {
            s.locateChildrenBy = getAttributeAsString("locateChildrenBy");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.locateChildrenBy:" + t.getMessage() + "\n";
        }
        try {
            s.locateChildrenType = getAttributeAsString("locateChildrenType");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.locateChildrenType:" + t.getMessage() + "\n";
        }
        try {
            s.locatePeersBy = getAttributeAsString("locatePeersBy");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.locatePeersBy:" + t.getMessage() + "\n";
        }
        try {
            s.locatePeersType = getAttributeAsString("locatePeersType");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.locatePeersType:" + t.getMessage() + "\n";
        }
        try {
            s.margin = getAttributeAsString("margin");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.margin:" + t.getMessage() + "\n";
        }
        try {
            s.masterElement = getMasterElement();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.masterElement:" + t.getMessage() + "\n";
        }
        try {
            s.matchElement = getAttributeAsString("matchElement");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.matchElement:" + t.getMessage() + "\n";
        }
        try {
            s.maxHeight = getAttributeAsString("maxHeight");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.maxHeight:" + t.getMessage() + "\n";
        }
        try {
            s.maxWidth = getAttributeAsString("maxWidth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.maxWidth:" + t.getMessage() + "\n";
        }
        try {
            s.menuConstructor = getAttributeAsString("menuConstructor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.menuConstructor:" + t.getMessage() + "\n";
        }
        try {
            s.minNonEdgeSize = getAttributeAsString("minNonEdgeSize");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.minNonEdgeSize:" + t.getMessage() + "\n";
        }
        try {
            s.momentumScrollMinSpeed = getAttributeAsString("momentumScrollMinSpeed");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.momentumScrollMinSpeed:" + t.getMessage() + "\n";
        }
        try {
            s.mouseStillDownDelay = getAttributeAsString("mouseStillDownDelay");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.mouseStillDownDelay:" + t.getMessage() + "\n";
        }
        try {
            s.mouseStillDownInitialDelay = getAttributeAsString("mouseStillDownInitialDelay");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.mouseStillDownInitialDelay:" + t.getMessage() + "\n";
        }
        try {
            s.name = getAttributeAsString("name");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.name:" + t.getMessage() + "\n";
        }
        try {
            s.noDoubleClicks = getAttributeAsString("noDoubleClicks");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.noDoubleClicks:" + t.getMessage() + "\n";
        }
        try {
            s.noDropCursor = getAttributeAsString("noDropCursor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.noDropCursor:" + t.getMessage() + "\n";
        }
        try {
            s.opacity = getAttributeAsString("opacity");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.opacity:" + t.getMessage() + "\n";
        }
        try {
            s.overflow = getAttributeAsString("overflow");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.overflow:" + t.getMessage() + "\n";
        }
        try {
            s.padding = getAttributeAsString("padding");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.padding:" + t.getMessage() + "\n";
        }
        try {
            s.parentCanvas = getParentCanvas();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.parentCanvas:" + t.getMessage() + "\n";
        }
        try {
            s.parentElement = getParentElement();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.parentElement:" + t.getMessage() + "\n";
        }
        try {
            s.peers = getPeers();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.peersArray:" + t.getMessage() + "\n";
        }
        try {
            s.percentBox = getAttributeAsString("percentBox");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.percentBox:" + t.getMessage() + "\n";
        }
        try {
            s.percentSource = getPercentSource();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.percentSource:" + t.getMessage() + "\n";
        }
        try {
            s.position = getAttributeAsString("position");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.position:" + t.getMessage() + "\n";
        }
        try {
            s.printChildrenAbsolutelyPositioned = getAttributeAsString("printChildrenAbsolutelyPositioned");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.printChildrenAbsolutelyPositioned:" + t.getMessage() + "\n";
        }
        try {
            s.prompt = getAttributeAsString("prompt");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.prompt:" + t.getMessage() + "\n";
        }
        try {
            s.proportionalResizeModifiers = getAttributeAsStringArray("proportionalResizeModifiers");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.proportionalResizeModifiersArray:" + t.getMessage() + "\n";
        }
        try {
            s.proportionalResizing = getAttributeAsString("proportionalResizing");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.proportionalResizing:" + t.getMessage() + "\n";
        }
        try {
            s.receiveScrollbarEvents = getAttributeAsString("receiveScrollbarEvents");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.receiveScrollbarEvents:" + t.getMessage() + "\n";
        }
        try {
            s.redrawOnResize = getAttributeAsString("redrawOnResize");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.redrawOnResize:" + t.getMessage() + "\n";
        }
        try {
            s.resizeBarTarget = getAttributeAsString("resizeBarTarget");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.resizeBarTarget:" + t.getMessage() + "\n";
        }
        try {
            s.resizeFrom = getAttributeAsStringArray("resizeFrom");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.resizeFromArray:" + t.getMessage() + "\n";
        }
        try {
            s.ruleScope = getAttributeAsString("ruleScope");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.ruleScope:" + t.getMessage() + "\n";
        }
        try {
            s.scrollbarSize = getAttributeAsString("scrollbarSize");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.scrollbarSize:" + t.getMessage() + "\n";
        }
        try {
            s.shadowColor = getAttributeAsString("shadowColor");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowColor:" + t.getMessage() + "\n";
        }
        try {
            s.shadowDepth = getAttributeAsString("shadowDepth");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowDepth:" + t.getMessage() + "\n";
        }
        try {
            s.shadowHOffset = getAttributeAsString("shadowHOffset");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowHOffset:" + t.getMessage() + "\n";
        }
        try {
            s.shadowImage = getAttributeAsString("shadowImage");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowImage:" + t.getMessage() + "\n";
        }
        try {
            s.shadowOffset = getAttributeAsString("shadowOffset");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowOffset:" + t.getMessage() + "\n";
        }
        try {
            s.shadowSoftness = getAttributeAsString("shadowSoftness");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowSoftness:" + t.getMessage() + "\n";
        }
        try {
            s.shadowSpread = getAttributeAsString("shadowSpread");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowSpread:" + t.getMessage() + "\n";
        }
        try {
            s.shadowVOffset = getAttributeAsString("shadowVOffset");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shadowVOffset:" + t.getMessage() + "\n";
        }
        try {
            s.shouldPrint = getAttributeAsString("shouldPrint");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shouldPrint:" + t.getMessage() + "\n";
        }
        try {
            s.showCustomScrollbars = getAttributeAsString("showCustomScrollbars");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showCustomScrollbars:" + t.getMessage() + "\n";
        }
        try {
            s.showDragShadow = getAttributeAsString("showDragShadow");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showDragShadow:" + t.getMessage() + "\n";
        }
        try {
            s.showEdges = getAttributeAsString("showEdges");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showEdges:" + t.getMessage() + "\n";
        }
        try {
            s.showHover = getAttributeAsString("showHover");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showHover:" + t.getMessage() + "\n";
        }
        try {
            s.showHoverComponents = getAttributeAsString("showHoverComponents");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showHoverComponents:" + t.getMessage() + "\n";
        }
        try {
            s.showResizeBar = getAttributeAsString("showResizeBar");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showResizeBar:" + t.getMessage() + "\n";
        }
        try {
            s.showShadow = getAttributeAsString("showShadow");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showShadow:" + t.getMessage() + "\n";
        }
        try {
            s.showSnapGrid = getAttributeAsString("showSnapGrid");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.showSnapGrid:" + t.getMessage() + "\n";
        }
        try {
            s.shrinkElementOnHide = getAttributeAsString("shrinkElementOnHide");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.shrinkElementOnHide:" + t.getMessage() + "\n";
        }
        try {
            s.sizeMayChangeOnRedraw = getAttributeAsString("sizeMayChangeOnRedraw");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.sizeMayChangeOnRedraw:" + t.getMessage() + "\n";
        }
        try {
            s.skinImgDir = getAttributeAsString("skinImgDir");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.skinImgDir:" + t.getMessage() + "\n";
        }
        try {
            s.snapAlignCandidates = getSnapAlignCandidates();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapAlignCandidatesArray:" + t.getMessage() + "\n";
        }
        try {
            s.snapAlignCenterLineStyle = getAttributeAsString("snapAlignCenterLineStyle");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapAlignCenterLineStyle:" + t.getMessage() + "\n";
        }
        try {
            s.snapAlignEdgeLineStyle = getAttributeAsString("snapAlignEdgeLineStyle");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapAlignEdgeLineStyle:" + t.getMessage() + "\n";
        }
        try {
            s.snapAxis = getAttributeAsString("snapAxis");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapAxis:" + t.getMessage() + "\n";
        }
        try {
            s.snapEdge = getAttributeAsString("snapEdge");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapEdge:" + t.getMessage() + "\n";
        }
        try {
            s.snapHDirection = getAttributeAsString("snapHDirection");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapHDirection:" + t.getMessage() + "\n";
        }
        try {
            s.snapHGap = getAttributeAsString("snapHGap");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapHGap:" + t.getMessage() + "\n";
        }
        try {
            s.snapOffsetLeft = getAttributeAsString("snapOffsetLeft");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapOffsetLeft:" + t.getMessage() + "\n";
        }
        try {
            s.snapOffsetTop = getAttributeAsString("snapOffsetTop");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapOffsetTop:" + t.getMessage() + "\n";
        }
        try {
            s.snapOnDrop = getAttributeAsString("snapOnDrop");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapOnDrop:" + t.getMessage() + "\n";
        }
        try {
            s.snapResizeToAlign = getAttributeAsString("snapResizeToAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapResizeToAlign:" + t.getMessage() + "\n";
        }
        try {
            s.snapResizeToGrid = getAttributeAsString("snapResizeToGrid");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapResizeToGrid:" + t.getMessage() + "\n";
        }
        try {
            s.snapTo = getAttributeAsString("snapTo");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapTo:" + t.getMessage() + "\n";
        }
        try {
            s.snapToAlign = getAttributeAsString("snapToAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapToAlign:" + t.getMessage() + "\n";
        }
        try {
            s.snapToCenterAlign = getAttributeAsString("snapToCenterAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapToCenterAlign:" + t.getMessage() + "\n";
        }
        try {
            s.snapToEdgeAlign = getAttributeAsString("snapToEdgeAlign");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapToEdgeAlign:" + t.getMessage() + "\n";
        }
        try {
            s.snapToGrid = getAttributeAsString("snapToGrid");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapToGrid:" + t.getMessage() + "\n";
        }
        try {
            s.snapVDirection = getAttributeAsString("snapVDirection");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapVDirection:" + t.getMessage() + "\n";
        }
        try {
            s.snapVGap = getAttributeAsString("snapVGap");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.snapVGap:" + t.getMessage() + "\n";
        }
        try {
            s.startLine = getAttributeAsString("startLine");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.startLine:" + t.getMessage() + "\n";
        }
        try {
            s.styleName = getAttributeAsString("styleName");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.styleName:" + t.getMessage() + "\n";
        }
        try {
            s.tabIndex = getAttributeAsString("tabIndex");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.tabIndex:" + t.getMessage() + "\n";
        }
        try {
            s.topElement = getTopElement();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.topElement:" + t.getMessage() + "\n";
        }
        try {
            s.updateTabPositionOnDraw = getAttributeAsString("updateTabPositionOnDraw");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.updateTabPositionOnDraw:" + t.getMessage() + "\n";
        }
        try {
            s.updateTabPositionOnReparent = getAttributeAsString("updateTabPositionOnReparent");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.updateTabPositionOnReparent:" + t.getMessage() + "\n";
        }
        try {
            s.useBackMask = getAttributeAsString("useBackMask");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.useBackMask:" + t.getMessage() + "\n";
        }
        try {
            s.useCSSShadow = getAttributeAsString("useCSSShadow");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.useCSSShadow:" + t.getMessage() + "\n";
        }
        try {
            s.useImageForSVG = getAttributeAsString("useImageForSVG");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.useImageForSVG:" + t.getMessage() + "\n";
        }
        try {
            s.useNativeDrag = getAttributeAsString("useNativeDrag");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.useNativeDrag:" + t.getMessage() + "\n";
        }
        try {
            s.useOpacityFilter = getAttributeAsString("useOpacityFilter");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.useOpacityFilter:" + t.getMessage() + "\n";
        }
        try {
            s.useTouchScrolling = getAttributeAsString("useTouchScrolling");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.useTouchScrolling:" + t.getMessage() + "\n";
        }
        try {
            s.valuesManagerAsValuesManager = getValuesManager();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.valuesManagerAsValuesManager:" + t.getMessage() + "\n";
        }
        try {
            s.valuesManagerAsString = getAttributeAsString("valuesManager");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.valuesManagerAsString:" + t.getMessage() + "\n";
        }
        try {
            s.visibility = getAttributeAsString("visibility");
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.visibility:" + t.getMessage() + "\n";
        }
        try {
            s.visibleWhen = getVisibleWhen();
        } catch (Throwable t) {
            s.logicalStructureErrors += "Canvas.visibleWhen:" + t.getMessage() + "\n";
        }
        return s;
    }

    /**
     * Getter implementing the {@link com.smartgwt.client.core.LogicalStructure} interface,
     * which supports Eclipse's logical structure debugging facility.
     */
    public LogicalStructureObject getLogicalStructure() {
        CanvasLogicalStructure s = new CanvasLogicalStructure();
        setLogicalStructure(s);
        return s;
    }
    
    @Override
    protected void onDestroy() {
    	GWTRestDataSource.destroyAllCanvasDataSources(this); //TODO AURIGA
    	super.onDestroy();
    }    
}
