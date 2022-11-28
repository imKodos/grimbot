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
import"./kendo.popup.js";var __meta__={id:"mobile.shim",name:"Shim",category:"mobile",description:"Mobile Shim",depends:["popup"],hidden:!0};!function(e,i){var n=window.kendo,o=n.mobile.ui,t=n.ui.Popup,s="hide",p=o.Widget,a=p.extend({init:function(i,o){var a=this,d=n.mobile.application,l=n.support.mobileOS,r=d?d.os.name:l?l.name:"ios",c="ios"===r||"wp"===r||!!d&&d.os.skin,h="blackberry"===r,m=o.align||(c?"bottom center":h?"center right":"center center"),u=o.position||(c?"bottom center":h?"center right":"center center"),f=o.effect||(c?"slideIn:up":h?"slideIn:left":"fade:in"),b=e('<div class="km-shim"></div>').handler(a).hide();p.fn.init.call(a,i,o),a.shim=b,i=a.element,(o=a.options).className&&a.shim.addClass(o.className),o.modal||a.shim.on("down","_hide"),(d?d.element:e(document.body)).append(b),a.popup=new t(a.element,{anchor:b,modal:!0,appendTo:b,origin:m,position:u,animation:{open:{effects:f,duration:o.duration},close:{duration:o.duration}},close:function(e){var i=!1;a._apiCall||(i=a.trigger(s)),i&&e.preventDefault(),a._apiCall=!1},deactivate:function(){b.hide()},open:function(){b.show()}}),n.notify(a)},events:[s],options:{name:"Shim",modal:!1,align:i,position:i,effect:i,duration:200},show:function(){this.popup.open()},hide:function(){this._apiCall=!0,this.popup.close()},destroy:function(){p.fn.destroy.call(this),this.shim.kendoDestroy(),this.popup.destroy(),this.shim.remove()},_hide:function(i){i&&e.contains(this.shim.children().children(".k-popup")[0],i.target)||this.popup.close()}});o.plugin(a)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.shim.js.map
