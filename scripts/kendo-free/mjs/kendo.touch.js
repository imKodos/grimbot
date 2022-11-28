/**
 * Copyright 2022 Progress Software Corporation and/or one of its subsidiaries or affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import"./kendo.core.js";import"./kendo.userevents.js";var __meta__={id:"touch",name:"Touch",category:"mobile",description:"The kendo Touch widget provides a cross-platform compatible API for handling user-initiated touch events, multi-touch gestures and event sequences (drag, swipe, etc.). ",depends:["core","userevents"]};!function(e,t){var n=window.kendo,i=n.ui.Widget,a=Math.abs,r=i.extend({init:function(e,t){var a=this;function r(e){return function(t){a._triggerTouch(e,t)}}function o(e){return function(t){a.trigger(e,{touches:t.touches,distance:t.distance,center:t.center,event:t.event})}}i.fn.init.call(a,e,t),t=a.options,e=a.element,a.wrapper=e,a.events=new n.UserEvents(e,{filter:t.filter,surface:t.surface,minHold:t.minHold,multiTouch:t.multiTouch,allowSelection:!0,fastTap:t.fastTap,press:r("touchstart"),hold:r("hold"),tap:a._tap.bind(a),gesturestart:o("gesturestart"),gesturechange:o("gesturechange"),gestureend:o("gestureend")}),t.enableSwipe?(a.events.bind("start",a._swipestart.bind(a)),a.events.bind("move",a._swipemove.bind(a))):(a.events.bind("start",a._dragstart.bind(a)),a.events.bind("move",r("drag")),a.events.bind("end",r("dragend"))),n.notify(a)},events:["touchstart","dragstart","drag","dragend","tap","doubletap","hold","swipe","gesturestart","gesturechange","gestureend"],options:{name:"Touch",surface:null,global:!1,fastTap:!1,filter:null,multiTouch:!1,enableSwipe:!1,minXDelta:30,maxYDelta:20,maxDuration:1e3,minHold:800,doubleTapTimeout:800},cancel:function(){this.events.cancel()},destroy:function(){i.fn.destroy.call(this),this.events.destroy()},_triggerTouch:function(e,t){this.trigger(e,{touch:t.touch,event:t.event})&&t.preventDefault()},_tap:function(e){var t=this,i=t.lastTap,a=e.touch;i&&a.endTime-i.endTime<t.options.doubleTapTimeout&&n.touchDelta(a,i).distance<20?(t._triggerTouch("doubletap",e),t.lastTap=null):(t._triggerTouch("tap",e),t.lastTap=a)},_dragstart:function(e){this._triggerTouch("dragstart",e)},_swipestart:function(e){2*a(e.x.velocity)>=a(e.y.velocity)&&e.sender.capture()},_swipemove:function(e){var t=this.options,n=e.touch,i=e.event.timeStamp-n.startTime,r=n.x.initialDelta>0?"right":"left";a(n.x.initialDelta)>=t.minXDelta&&a(n.y.initialDelta)<t.maxYDelta&&i<t.maxDuration&&(this.trigger("swipe",{direction:r,touch:e.touch}),n.cancel())}});n.ui.plugin(r)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.touch.js.map
