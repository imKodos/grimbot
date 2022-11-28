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
import"./kendo.core.js";var __meta__={id:"mobile.tabstrip",name:"TabStrip",category:"mobile",description:"The mobile TabStrip widget is used inside a mobile view or layout footer element to display an application-wide group of navigation buttons.",depends:["core"]};!function(e,t){var i=window.kendo,n=i.mobile.ui,a=n.Widget,r="km-state-active",s="select";function o(t){return e('<span class="km-badge">'+t+"</span>")}var l=a.extend({init:function(e,t){var i=this;a.fn.init.call(i,e,t),i.container().bind("show",this.refresh.bind(this)),i.element.addClass("km-tabstrip").find("a").each(i._buildButton).eq(i.options.selectedIndex).addClass(r),i.element.on("down","a","_release")},events:[s],switchTo:function(t){var i,n,a=this.element.find("a"),r=0,s=a.length;if(!isNaN(t))return this._setActiveItem(a.eq(t)),!0;for(;r<s;r++)if(-1!==(n=(i=a[r]).href.replace(/(\#.+)(\?.+)$/,"$1")).indexOf(t,n.length-t.length))return this._setActiveItem(e(i)),!0;return!1},switchByFullUrl:function(e){var t;t=this.element.find("a[href$='"+e+"']"),this._setActiveItem(t)},clear:function(){this.currentItem().removeClass(r)},currentItem:function(){return this.element.children(".km-state-active")},badge:function(t,i){var n,a=this.element;return isNaN(t)||(t=a.children().get(t)),t=a.find(t),n=e(t.find(".km-badge")[0]||o(i).insertAfter(t.children(".km-icon"))),i||0===i?(n.html(i),this):!1===i?(n.empty().remove(),this):n.html()},_release:function(t){if(!(t.which>1)){var i=this,n=e(t.currentTarget);n[0]!==i.currentItem()[0]&&(i.trigger(s,{item:n})?t.preventDefault():i._setActiveItem(n))}},_setActiveItem:function(e){e[0]&&(this.clear(),e.addClass(r))},_buildButton:function(){var t=e(this),n=i.attrValue(t,"icon"),a=i.attrValue(t,"badge"),r=t.find("img"),s=e('<span class="km-icon"/>');t.addClass("km-button").attr(i.attr("role"),"tab").contents().not(r).wrapAll('<span class="km-text"/>'),r[0]?r.addClass("km-image").prependTo(t):(t.prepend(s),n&&(s.addClass("km-"+n),(a||0===a)&&o(a).insertAfter(s)))},refresh:function(e){var t=e.view.id;t&&!this.switchTo(e.view.id)&&this.switchTo(t)},options:{name:"TabStrip",selectedIndex:0,enable:!0}});n.plugin(l)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.tabstrip.js.map
