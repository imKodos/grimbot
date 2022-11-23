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
import"./kendo.html.base.js";var __meta__={id:"html.input",name:"Html.Input",category:"web",description:"HTML rendering utility for Kendo UI for jQuery.",depends:["html.base"],features:[]};!function(e,l){var t=window.kendo,n=t.html.HTMLBase,i=n.extend({init:function(e,l){var t=this;n.fn.init.call(t,e,l),t._wrapper(),t._addClasses()},options:{label:null,labelPosition:"after",labelId:null,encoded:!0},_wrapper:function(){var l=this,n=l.element[0],i=l.options,a=n.id;l.wrapper=l.element.addClass(i.inputClass).prop("type",i.type),!a&&i.label&&(n.id=a=t.guid()),i.label&&(l.labelEl=e("<label for='"+a+"' class='"+i.labelClass+"'>"),i.encoded?l.labelEl.text(i.label):l.labelEl.html(i.label),i.labelId&&l.labelEl.attr("id",i.labelId),i.optional&&l.labelEl.append("<span class='"+i.optionalClass+"'>"+i.optionalText+"</span>"),l.element[i.labelPosition](l.labelEl))},html:function(){var e=this,l="after"===e.options.labelPosition,t=n.fn.html.call(e);return e.labelEl?l?t+e.labelEl[0].outerHTML:e.labelEl[0].outerHTML+t:t}}),a=i.extend({init:function(e,l){i.fn.init.call(this,e,l),this._addClasses()},options:{name:"HTMLCheckBox",inputClass:"k-checkbox",labelClass:"k-checkbox-label",optionalClass:"k-label-optional",optionalText:"(Optional)",type:"checkbox",rounded:"medium",size:"medium",stylingOptions:["size","rounded"]}}),o=i.extend({init:function(e,l){i.fn.init.call(this,e,l),this._addClasses()},options:{name:"HTMLRadioButton",inputClass:"k-radio",labelClass:"k-radio-label",optionalClass:"k-label-optional",optionalText:"(Optional)",type:"radio",size:"medium",stylingOptions:["size"]}});e.extend(t.html,{renderCheckBox:function(t,n){return(arguments[0]===l||e.isPlainObject(arguments[0]))&&(n=t,t=e("<input />")),new a(t,n).html()},renderRadioButton:function(t,n){return(arguments[0]===l||e.isPlainObject(arguments[0]))&&(n=t,t=e("<input />")),new o(t,n).html()},HTMLInput:i,HTMLCheckBox:a,HTMLRadioButton:o}),t.cssProperties.registerPrefix("HTMLCheckBox","k-checkbox-"),t.cssProperties.registerValues("HTMLCheckBox",[{prop:"rounded",values:t.cssProperties.roundedValues.concat([["full","full"]])}]),t.cssProperties.registerPrefix("HTMLRadioButton","k-radio-")}(window.kendo.jQuery);
//# sourceMappingURL=kendo.html.input.js.map
