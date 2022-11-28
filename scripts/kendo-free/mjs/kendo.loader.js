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
import"./kendo.core.js";var __meta__={id:"loader",name:"Loader",category:"web",description:"The Loader is a visual indicator of loading data across different parts of the page.",depends:["core"]};!function(e,s){var i=window.kendo,a=i.ui.Widget,n=i.ui,t="k-widget k-loader",o="k-loader-canvas",r="k-loader-segment",l={pulsing:{className:"pulsing-2",segments:2},"infinite-spinner":{className:"spinner-3",segments:3},"converging-spinner":{className:"spinner-4",segments:4}},d=a.extend({init:function(e,s){var n=this;a.fn.init.call(n,e,s),n._render(),n._appearance(),i.notify(n)},destroy:function(){a.fn.destroy.call(this)},options:{name:"Loader",themeColor:"primary",sizes:{small:"sm",medium:"md",large:"lg"},size:"medium",type:"pulsing",visible:!0,messages:{loading:"Loading"},_classNames:[]},_render:function(){var i=this,a=i.element,n=i.options.type,t=l[n]===s?n:l[n],d=[];if(a.empty().attr("aria-label",i.options.messages.loading).attr("role","alert").attr("aria-live","polite"),t.segments)for(var p=0;p<t.segments;p+=1)d.push(e("<span/>").addClass(r));e("<div>").addClass(o).append(d).appendTo(a)},_appearance:function(){var e=this;e._themeColor=e.options.themeColor,e._sizes=e.options.sizes,e._size=e.options.size,e._type=e.options.type,e._visible=e.options.visible,e._updateClassNames()},_updateClassNames:function(){var i=this,a=[t],n=i.options._classNames,o=i._themeColor,r=i._sizes,d=i._size,p=i._type,m=l[p]===s?p:l[p],c=r[d]===s?d:r[d],u=i._visible;i.element.removeClass((function(e,s){0===s.indexOf("k-")&&-1===n.indexOf(s)&&i.element.removeClass(s)})),"string"==typeof o&&""!==o&&"inherit"!==o&&a.push("k-loader-"+o),"string"==typeof d&&""!==d&&""!==c&&a.push("k-loader-"+c),"string"==typeof p&&""!==p&&a.push("k-loader-"+(e.isPlainObject(m)?m.className:p)),!1===u&&a.push("k-hidden"),i.element.attr("aria-hidden",!u),i.element.addClass(a.join(" "))},setOptions:function(e){var s=this;a.fn.setOptions.call(s,e),s._render(),s._appearance()},themeColor:function(e){var i=this;if(0===arguments.length||e===s)return i._themeColor;i._themeColor=e,i._updateClassNames()},hide:function(){this._visible=!1,this._updateClassNames()},show:function(){this._visible=!0,this._updateClassNames()}});n.plugin(d)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.loader.js.map
