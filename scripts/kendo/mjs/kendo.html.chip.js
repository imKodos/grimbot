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
import"./kendo.html.base.js";var __meta__={id:"html.chip",name:"Html.Chip",category:"web",description:"HTML rendering utility for Kendo UI for jQuery.",depends:["html.base"],features:[]};!function(e,t){var n=window.kendo,i=n.html.HTMLBase,r=i.extend({init:function(e,t){i.fn.init.call(this,e,t),this._wrapper()},options:{name:"HTMLChip",size:"medium",rounded:"medium",fillMode:"solid",themeColor:"base",attr:{},icon:"",iconAttr:{},removable:!1,removableAttr:{},removeIcon:"x-circle",content:"",text:"",stylingOptions:["size","rounded","fillMode","themeColor"]},_wrapper:function(){var t=this,n=t.options;t.wrapper=t.element.wrap("<span class='k-chip'></span>").parent().attr(n.attr),t._addClasses(),n.icon&&t.wrapper.prepend(e("<span class='k-chip-icon k-icon k-i-"+n.icon+"'></span>").attr(n.iconAttr)),t.element.addClass("k-chip-content"),n.text&&t.element.html('<span class="k-chip-label">'+n.text+"</span>"),n.removable&&t.wrapper.append(e("<span class='k-chip-action k-chip-remove-action'><span class='k-icon k-i-"+n.removeIcon+"'></span></span>").attr(n.removableAttr))}});e.extend(n.html,{renderChip:function(t,n){return t&&!e.isPlainObject(t)||(n=t,t=e("<span></span>")),new r(t,n).html()},HTMLChip:r}),n.cssProperties.registerPrefix("HTMLChip","k-chip-")}(window.kendo.jQuery);
//# sourceMappingURL=kendo.html.chip.js.map
