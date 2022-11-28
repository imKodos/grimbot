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
import"./kendo.core.js";var __meta__={id:"floatinglabel",name:"FloatingLabel",category:"framework",depends:["core"],hidden:!0};!function(e,t){var n=window.kendo,i=n.ui.Widget,o=n.ui,l=".kendoFloatingLabel",a="k-empty",s="k-focus",d="k-disabled",r="k-no-click",p="k-readonly",g=i.extend({init:function(o,l){var a=this;i.fn.init.call(a,o,l),l=e.extend(!0,{},l),a.widget=a.options.widget,a.widgetWrapper=a.widget.wrapper[0],a.refresh(),a._editable({readonly:a.options.widget.options.readonly!==t&&a.options.widget.options.readonly,disable:a.options.widget.options.enable!==t&&!a.options.widget.options.enable}),a.widgetWrapper.style.width&&(a.element.css("width",a.widgetWrapper.style.width),a.widgetWrapper.style.width="100%"),a.element.addClass("k-floating-label-container"),n.notify(a)},options:{name:"FloatingLabel",widget:null,useReadOnlyClass:!1,floatCheck:({element:e})=>!e.val()},readonly:function(e){this._editable({readonly:e===t||e,disable:!1})},enable:function(e){this._editable({readonly:!1,disable:!(e=e===t||e)})},refresh:function(){var e=this,t=e.element;t.removeClass(a).removeClass(s),e.options.floatCheck({element:e.options.widget.element,floating:e.element})&&t.addClass(a),(document.activeElement===e.options.widget.element[0]||e.options.widget.input&&document.activeElement===e.options.widget.input[0])&&t.addClass(s)},destroy:function(){this.element.off(l),i.fn.destroy.call(this)},_editable:function(e){var t=this,n=t.element,i=e.disable,o=e.readonly;n.off(l),o||i?n.toggleClass(d,i).toggleClass(t.options.useReadOnlyClass?p:r,o):(n.removeClass(d).removeClass(t.options.useReadOnlyClass?p:r),n.on("focusin"+l,t.refresh.bind(t)),n.on("focusout"+l,t.refresh.bind(t)))}});o.plugin(g)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.floatinglabel.js.map
