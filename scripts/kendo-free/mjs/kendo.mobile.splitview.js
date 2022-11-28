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
import"./kendo.mobile.pane.js";var __meta__={id:"mobile.splitview",name:"SplitView",category:"mobile",description:"The mobile SplitView is a tablet-specific view that consists of two or more mobile Pane widgets.",depends:["mobile.pane"]};!function(e,i){var t=window.kendo,n=t.mobile.ui,a=n.Widget,o=n.View.extend({init:function(i,o){var l,s,r=this;a.fn.init.call(r,i,o),i=r.element,e.extend(r,o),r._id(),r.options.$angular||r._layout(),r._overlay(),r._style(),s=i.children(r._locate("modalview")),r.options.$angular?s.each((function(i,n){t.compileMobileDirective(e(n),o.$angular[0])})):t.mobile.init(s),r.panes=[],r._paramsHistory=[],r.options.$angular?(r.element.children(t.directiveSelector("pane")).each((function(){l=t.compileMobileDirective(e(this),o.$angular[0]),r.panes.push(l)})),r.element.children(t.directiveSelector("header footer")).each((function(){t.compileMobileDirective(e(this),o.$angular[0])}))):r.content.children(t.roleSelector("pane")).each((function(){l=t.initWidget(this,{},n.roles),r.panes.push(l)})),r.expandedPaneShim=e("<div class='km-expanded-pane-shim'></div>").appendTo(r.element),r._shimUserEvents=new t.UserEvents(r.expandedPaneShim,{fastTap:!0,tap:function(){r.collapsePanes()}})},_locate:function(e){return this.options.$angular?t.directiveSelector(e):t.roleSelector(e)},options:{name:"SplitView",style:"horizontal"},expandPanes:function(){this.element.addClass("km-expanded-splitview")},collapsePanes:function(){this.element.removeClass("km-expanded-splitview")},_layout:function(){var e=this,i=e.element;e.transition=t.attrValue(i,"transition"),t.mobile.ui.View.prototype._layout.call(this),t.mobile.init(this.header.add(this.footer)),e.element.addClass("km-splitview"),e.content.addClass("km-split-content")},_style:function(){var i,t=this.options.style,n=this.element;t&&(i=t.split(" "),e.each(i,(function(){n.addClass("km-split-"+this)})))},showStart:function(){var i=this;i.element.css("display",""),i.inited?this._invokeNgController():(i.inited=!0,e.each(i.panes,(function(){this.options.initial?this.navigateToInitial():this.navigate("")})),i.trigger("init",{view:i})),i.trigger("show",{view:i})}});n.plugin(o)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.splitview.js.map
