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
import"./kendo.html.base.js";var __meta__={id:"html.button",name:"Html.Button",category:"web",description:"HTML rendering utility for Kendo UI for jQuery.",depends:["html.base"],features:[]};!function(e,t){var n=window.kendo,s=n.html.HTMLBase,i="k-button-icon",o=s.extend({init:function(e,t){var n=this;s.fn.init.call(n,e,t),n.wrapper=n.element.addClass("k-button"),n.element.attr("type")||n.element.attr("type",n.options.type),n._addClasses(),n.iconElement(),n._textElement()},options:{name:"HTMLButton",type:"button",icon:"",iconClass:"",spriteCssClass:"",imageUrl:"",size:"medium",rounded:"medium",fillMode:"solid",themeColor:"base",stylingOptions:["size","rounded","fillMode","themeColor"]},iconElement:function(){var t,s,o,a=this,l=a.element,r=a.options,d=r.icon,c=r.iconClass,p=r.spriteCssClass,u=r.imageUrl;(p||u||d||c)&&(o=!0,l.contents().filter((function(){return!e(this).hasClass("k-sprite")&&!e(this).hasClass("k-icon")&&!e(this).hasClass("k-image")})).each((function(e,t){(1==t.nodeType||3==t.nodeType&&n.trim(t.nodeValue).length>0)&&(o=!1)}))),o&&a.element.addClass("k-icon-button"),u?((s=l.children("img.k-image").first())[0]||(s=e('<img alt="icon" class="k-image" />').prependTo(l)),s.attr("src",u),s.addClass(i)):d||c?((t=l.children("span.k-icon").first())[0]||(t=e("<span></span>").prependTo(l)),t.attr("class",d?"k-icon k-i-"+d:c),t.addClass(i)):p&&((t=l.children("span.k-sprite").first())[0]||(t=e('<span class="k-sprite"></span>').prependTo(l)),t.addClass(p+" "+i))},_textElement:function(){this.element.contents().filter((function(){return!(e(this).hasClass(i)||e(this).hasClass("k-sprite")||e(this).hasClass("k-icon")||e(this).hasClass("k-image"))})).each((function(e,t){if(1==t.nodeType||3==t.nodeType&&n.trim(t.nodeValue).length>0){if(3===t.nodeType){var s=document.createElement("span");t.parentNode.insertBefore(s,t),s.appendChild(t),t=s}t.classList.add("k-button-text")}}))}});e.extend(n.html,{renderButton:function(n,s){return(arguments[0]===t||e.isPlainObject(arguments[0]))&&(s=n,n=e("<button></button>")),new o(n,s).html()},HTMLButton:o}),n.cssProperties.registerPrefix("HTMLButton","k-button-"),n.cssProperties.registerValues("HTMLButton",[{prop:"fillMode",values:n.cssProperties.fillModeValues.concat(["link"])},{prop:"rounded",values:n.cssProperties.roundedValues.concat([["full","full"]])}])}(window.kendo.jQuery);
//# sourceMappingURL=kendo.html.button.js.map
