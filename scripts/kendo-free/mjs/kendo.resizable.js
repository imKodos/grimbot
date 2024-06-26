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
import"./kendo.core.js";import"./kendo.draganddrop.js";var __meta__={id:"resizable",name:"Resizable",category:"framework",depends:["core","draganddrop"],advanced:!0};!function(i,t){var n=window.kendo,o=n.ui,e=o.Widget,s=n.isFunction,r=i.extend,a="horizontal",d="vertical",l="start",c="resize",p="resizeend",_=e.extend({init:function(i,t){var n=this;e.fn.init.call(n,i,t),n.orientation=n.options.orientation.toLowerCase()!=d?a:d,n._positionMouse=n.orientation==a?"x":"y",n._position=n.orientation==a?"left":"top",n._sizingDom=n.orientation==a?"outerWidth":"outerHeight",n.draggable=new o.Draggable(t.draggableElement||i,{distance:1,filter:t.handle,drag:n._resize.bind(n),dragcancel:n._cancel.bind(n),dragstart:n._start.bind(n),dragend:n._stop.bind(n)}),n.userEvents=n.draggable.userEvents},events:[c,p,l],options:{name:"Resizable",orientation:a},resize:function(){},_max:function(i){var n=this,o=n.hint?n.hint[n._sizingDom]():0,e=n.options.max;return s(e)?e(i):e!==t?n._initialElementPosition+e-o:e},_min:function(i){var n=this.options.min;return s(n)?n(i):n!==t?this._initialElementPosition+n:n},_start:function(t){var n=this,o=n.options.hint,e=i(t.currentTarget);n._initialElementPosition=e.position()[n._position],n._initialMousePosition=t[n._positionMouse].startLocation,o&&(n.hint=s(o)?i(o(e)):o,n.hint.css({position:"absolute"}).css(n._position,n._initialElementPosition).appendTo(n.element)),n.trigger(l,t),n._maxPosition=n._max(t),n._minPosition=n._min(t),i(document.body).css("cursor",e.css("cursor"))},_resize:function(i){var n,o=this,e=o._maxPosition,s=o._minPosition,a=o._initialElementPosition+(i[o._positionMouse].location-o._initialMousePosition);n=s!==t?Math.max(s,a):a,o.position=n=e!==t?Math.min(e,n):n,o.hint&&o.hint.toggleClass(o.options.invalidClass||"",n==e||n==s).css(o._position,n),o.resizing=!0,o.trigger(c,r(i,{position:n}))},_stop:function(t){var n=this;n.hint&&n.hint.remove(),n.resizing=!1,n.trigger(p,r(t,{position:n.position})),i(document.body).css("cursor","")},_cancel:function(i){var n=this;n.hint&&(n.position=t,n.hint.css(n._position,n._initialElementPosition),n._stop(i))},destroy:function(){var i=this;e.fn.destroy.call(i),i.draggable&&i.draggable.destroy()},press:function(i){if(i){var t=i.position(),n=this;n.userEvents.press(t.left,t.top,i[0]),n.targetPosition=t,n.target=i}},move:function(i){var n=this,o=n._position,e=n.targetPosition,s=n.position;s===t&&(s=e[o]),e[o]=s+i,n.userEvents.move(e.left,e.top)},end:function(){this.userEvents.end(),this.target=this.position=t}});n.ui.plugin(_)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.resizable.js.map
