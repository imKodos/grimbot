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
import"./kendo.core.js";var __meta__={id:"mobile.navbar",name:"NavBar",category:"mobile",description:"The Kendo mobile NavBar widget is used inside a mobile View or Layout Header element to display an application navigation bar.",depends:["core"]};!function(e,i){var t=window.kendo,n=t.mobile.ui,l=n.Widget;function a(i,n){var l=n.find("["+t.attr("align")+"="+i+"]");if(l[0])return e('<div class="km-'+i+'item" />').append(l).prependTo(n)}var o=l.extend({init:function(i,t){var n=this;l.fn.init.call(n,i,t),i=n.element,n.container().bind("show",this.refresh.bind(this)),i.addClass("km-navbar").wrapInner(e('<div class="km-view-title km-show-title" />')),n.leftElement=a("left",i),n.rightElement=a("right",i),n.centerElement=i.find(".km-view-title")},options:{name:"NavBar"},title:function(e){var i,n,l,a,o;this.element.find(t.roleSelector("view-title")).text(e),i=this.centerElement,n=i.siblings(),l=!!i.children("ul")[0],a=!!n[0]&&""===t.trim(i.text()),o=!(!t.mobile.application||!t.mobile.application.element.is(".km-android")),i.prevAll().toggleClass("km-absolute",l),i.toggleClass("km-show-title",a),i.toggleClass("km-fill-title",a&&!t.trim(i.html())),i.toggleClass("km-no-title",l),i.toggleClass("km-hide-title",o&&!n.children().is(":visible"))},refresh:function(e){var i=e.view;this.title(i.options.title)},destroy:function(){l.fn.destroy.call(this),t.destroy(this.element)}});n.plugin(o)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.navbar.js.map
