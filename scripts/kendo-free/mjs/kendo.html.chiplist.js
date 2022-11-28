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
import"./kendo.html.base.js";var __meta__={id:"html.chiplist",name:"Html.ChipList",category:"web",description:"HTML rendering utility for Kendo UI for jQuery.",depends:["html.base"],features:[]};!function(i,e){var t=window.kendo,n=t.html.HTMLBase,s=n.extend({init:function(i,e){var t=this;n.fn.init.call(t,i,e),t.wrapper=t.element.addClass("k-chip-list"),t._addClasses()},options:{name:"HTMLChipList",size:"medium",stylingOptions:["size"]}});i.extend(t.html,{renderChipList:function(t,n){return(arguments[0]===e||i.isPlainObject(arguments[0]))&&(n=t,t=i("<div></div>")),new s(t,n).html()},HTMLChipList:s}),t.cssProperties.registerPrefix("HTMLChipList","k-chip-list-")}(window.kendo.jQuery);
//# sourceMappingURL=kendo.html.chiplist.js.map
