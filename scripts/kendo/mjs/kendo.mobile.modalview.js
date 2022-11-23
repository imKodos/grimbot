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
import"./kendo.mobile.shim.js";import"./kendo.mobile.view.js";var __meta__={id:"mobile.modalview",name:"ModalView",category:"mobile",description:"The Kendo ModalView is used to present self-contained functionality in the context of the current task.",depends:["mobile.shim","mobile.view"]};!function(e,i){var t=window.kendo.mobile.ui,o=t.Shim,n=t.Widget,s="beforeOpen",r="open",a="close",l="init",h=t.View.extend({init:function(e,i){var t=this;n.fn.init.call(t,e,i),t._id(),t._wrap(),t._shim(),this.options.$angular||(t._layout(),t._scroller(),t._model()),t.element.css("display",""),t.trigger(l)},events:[l,s,r,a],options:{name:"ModalView",modal:!0,width:null,height:null},destroy:function(){n.fn.destroy.call(this),this.shim.destroy()},open:function(i){var t=this;t.target=e(i),t.shim.show(),t._invokeNgController(),t.trigger("show",{view:t})},openFor:function(e){this.trigger(s,{target:e})||(this.open(e),this.trigger(r,{target:e}))},close:function(){this.element.is(":visible")&&!this.trigger(a)&&this.shim.hide()},_wrap:function(){var e,i,t=this,o=t.element,n=t.options;e=o[0].style.width||"auto",i=o[0].style.height||"auto",o.addClass("km-modalview").wrap('<div class="km-modalview-wrapper"></div>'),t.wrapper=o.parent().css({width:n.width||e||300,height:n.height||i||300}).addClass("auto"==i?" km-auto-height":""),o.css({width:"",height:""})},_shim:function(){var e=this;e.shim=new o(e.wrapper,{modal:e.options.modal,position:"center center",align:"center center",effect:"fade:in",className:"km-modalview-root",hide:function(i){e.trigger(a)&&i.preventDefault()}})}});t.plugin(h)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.modalview.js.map
