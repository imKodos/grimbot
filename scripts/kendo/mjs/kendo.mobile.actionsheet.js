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
import"./kendo.mobile.popover.js";import"./kendo.mobile.shim.js";var __meta__={id:"mobile.actionsheet",name:"ActionSheet",category:"mobile",description:"The mobile ActionSheet widget displays a set of choices related to a task the user initiates.",depends:["mobile.popover","mobile.shim"]};!function(e,t){var i=window.kendo,o=i.support,n=i.mobile.ui,s=n.Shim,a=n.Popup,c=n.Widget,r="open",l="close",h="command",p="li>a",d=i.template('<li class="km-actionsheet-cancel"><a href="\\#">#:cancel#</a></li>'),m=c.extend({init:function(t,r){var l,h,m,u=this,g=o.mobileOS;c.fn.init.call(u,t,r),m=(r=u.options).type,t=u.element,l=(h="auto"===m?g&&g.tablet:"tablet"===m)?a:s,r.cancelTemplate&&(d=i.template(r.cancelTemplate)),t.addClass("km-actionsheet").append(d({cancel:u.options.cancel})).wrap('<div class="km-actionsheet-wrapper"></div>').on("up",p,"_click").on("click",p,i.preventDefault),u.view().bind("destroy",(function(){u.destroy()})),u.wrapper=t.parent().addClass(m?" km-actionsheet-"+m:""),u.shim=new l(u.wrapper,e.extend({modal:g.ios&&g.majorVersion<7,className:"km-actionsheet-root"},u.options.popup)),u._closeProxy=u._close.bind(u),u._shimHideProxy=u._shimHide.bind(u),u.shim.bind("hide",u._shimHideProxy),h&&i.onResize(u._closeProxy),i.notify(u,n)},events:[r,l,h],options:{name:"ActionSheet",cancel:"Cancel",type:"auto",popup:{height:"auto"}},open:function(t,i){var o=this;o.target=e(t),o.context=i,o.shim.show(t)},close:function(){this.context=this.target=null,this.shim.hide()},openFor:function(e){var t=e.data("actionsheetContext");this.open(e,t),this.trigger(r,{target:e,context:t})},destroy:function(){c.fn.destroy.call(this),i.unbindResize(this._closeProxy),this.shim.destroy()},_click:function(t){if(!t.isDefaultPrevented()){var o=e(t.currentTarget),n=o.data("action");if(n){var s={target:this.target,context:this.context},a=this.options.$angular;a?this.element.injector().get("$parse")(n)(a[0])(s):i.getter(n)(window)(s)}this.trigger(h,{target:this.target,context:this.context,currentTarget:o}),t.preventDefault(),this._close()}},_shimHide:function(e){this.trigger(l)?e.preventDefault():this.context=this.target=null},_close:function(e){this.trigger(l)?e.preventDefault():this.close()}});n.plugin(m)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.mobile.actionsheet.js.map
