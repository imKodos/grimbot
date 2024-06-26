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
import"./kendo.core.js";var __meta__={id:"mobile.buttongroup",name:"ButtonGroup",category:"mobile",description:"The Kendo mobile ButtonGroup widget is a linear set of grouped buttons.",depends:["core"]};!function(e,n){var t=window.kendo,i=t.mobile.ui,a=i.Widget,s="state-active",o="state-disabled",d="select";function l(e){return"k-"+e+" km-"+e}function r(n){return e('<span class="'+l("badge")+'">'+n+"</span>")}var u=a.extend({init:function(e,n){var t=this;a.fn.init.call(t,e,n),t.element.addClass("km-buttongroup k-widget k-button-group").find("li").each(t._button),t.element.on(t.options.selectOn,"li:not(.km-state-active)","_select"),t._enable=!0,t.select(t.options.index),t.options.enable||(t._enable=!1,t.wrapper.addClass(l(o)))},events:[d],options:{name:"ButtonGroup",selectOn:"down",index:-1,enable:!0},current:function(){return this.element.find(".km-"+s)},select:function(n){var t=this,i=-1;undefined!==n&&-1!==n&&t._enable&&!e(n).is(".km-"+o)&&(t.current().removeClass(l(s)),"number"==typeof n?(i=n,n=e(t.element[0].children[n])):n.nodeType&&(i=(n=e(n)).index()),n.addClass(l(s)),t.selectedIndex=i)},badge:function(n,t){var i,a=this.element;return isNaN(n)||(n=a.children().get(n)),n=a.find(n),i=e(n.children(".km-badge")[0]||r(t).appendTo(n)),t||0===t?(i.html(t),this):!1===t?(i.empty().remove(),this):i.html()},enable:function(e){void 0===e&&(e=!0),this.wrapper.toggleClass(l(o),!e),this._enable=this.options.enable=e},_button:function(){var n=e(this).addClass(l("button")),i=t.attrValue(n,"icon"),a=t.attrValue(n,"badge"),s=n.children("span"),o=n.find("img").addClass(l("image"));s[0]||(s=n.wrapInner("<span/>").children("span")),s.addClass(l("text")),!o[0]&&i&&n.prepend(e('<span class="'+l("icon")+" "+l(i)+'"/>')),(a||0===a)&&r(a).appendTo(n)},_select:function(e){e.which>1||e.isDefaultPrevented()||!this._enable||(this.select(e.currentTarget),this.trigger(d,{index:this.selectedIndex}))}});i.plugin(u)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.buttongroup.js.map
