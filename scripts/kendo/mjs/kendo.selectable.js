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
import"./kendo.core.js";import"./kendo.userevents.js";var __meta__={id:"selectable",name:"Selectable",category:"framework",depends:["core","userevents"],advanced:!0};!function(e,t){var n=window.kendo,i=n.ui.Widget,s=Math.abs,l="aria-selected",a="k-selected",o="k-selecting",r="k-selectable",c="change",u="unselect",d="k-unselecting",f=n.support.browser.msie,v=!1,h=e.extend;!function(e){e('<div class="parent"><span></span></div>').on("click",">*",(function(){v=!0})).find("span").trigger("click").end().off()}(e);var p=i.extend({init:function(t,s){var l,a,o=this;i.fn.init.call(o,t,s),o._marquee=e("<div class='k-marquee'><div class='k-marquee-color'></div></div>"),o._lastActive=null,o.element.addClass(r),o.relatedTarget=o.options.relatedTarget,l=o.options.multiple,a=o.options.dragToSelect,o.userEvents=new n.UserEvents(o.element,{global:!0,allowSelection:!0,filter:(v?"":".k-selectable ")+o.options.filter,tap:o._tap.bind(o),touchAction:l?"none":"pan-x pan-y"}),l&&(a&&o.userEvents.bind("start",o._start.bind(o)).bind("move",o._move.bind(o)).bind("end",o._end.bind(o)),o.userEvents.bind("select",o._select.bind(o)))},events:[c,u],options:{name:"Selectable",filter:">*",inputSelectors:"input,a,textarea,.k-multiselect-wrap,select,button,.k-button>span,.k-button>img,span.k-icon.k-i-arrow-60-down,span.k-icon.k-i-arrow-60-up,label.k-checkbox-label.k-no-text,.k-icon.k-i-collapse,.k-icon.k-i-expand,span.k-numeric-wrap,.k-focusable",multiple:!1,dragToSelect:!0,relatedTarget:e.noop,ignoreOverlapped:!1,addIdToRanges:!1},_isElement:function(e){var t,n=this.element,i=n.length,s=!1;for(e=e[0],t=0;t<i;t++)if(n[t]===e){s=!0;break}return s},_tap:function(t){var n,i=e(t.target),s=this,l=t.event.ctrlKey||t.event.metaKey,o=s.options.multiple,u=o&&t.event.shiftKey,d=s.options.selectedClass||a,f=t.event.which,v=t.event.button;!s._isElement(i.closest("."+r))||f&&3==f||v&&2==v||this._allowSelection(t.event.target)&&(n=i.hasClass(d),i=i.add(s.relatedTarget(i)),o?u?(s._lastRange&&g(s._lastRange,i)||(s.selectRange(s._firstSelectee(),i,t),s._notify(c,t)),s._lastRange=i):(s._lastRange=null,n&&l?(s._unselect(i),s._notify(c,t)):l?(s.value(i,t),s._notify(c,t)):(!n||s.value().length>1)&&(s.clear(),s.value(i,t),s._notify(c,t)),s._lastActive=s._downTarget=i):n&&l?(s._unselect(i),s._notify(c,t)):n||(s.clear(),s.value(i,t),s._notify(c,t)))},_start:function(t){var n,i=this,s=e(t.target),l=i.options.selectedClass||a,o=s.hasClass(l),c=t.event.ctrlKey||t.event.metaKey;this._allowSelection(t.event.target)&&(i._downTarget=s,i._isElement(s.closest("."+r))?(i.options.useAllItems?i._items=i.element.find(i.options.filter):(n=s.closest(i.element),i._items=n.find(i.options.filter)),t.sender.capture(),i._marquee.appendTo(document.body).css({left:t.x.client+1,top:t.y.client+1,width:0,height:0}),c||i.clear(),s=s.add(i.relatedTarget(s)),o&&(i._selectElement(s,!0),c&&s.addClass(d))):i.userEvents.cancel())},_move:function(e){var t={left:e.x.startLocation>e.x.location?e.x.location:e.x.startLocation,top:e.y.startLocation>e.y.location?e.y.location:e.y.startLocation,width:s(e.x.initialDelta),height:s(e.y.initialDelta)};this._marquee.css(t),this._invalidateSelectables(t,e.event.ctrlKey||e.event.metaKey),e.preventDefault()},_end:function(t){var i=this,s=n.attr("range-selected"),l=n.guid();i._marquee.remove(),i._unselect(i.element.find(i.options.filter+"."+d)).removeClass(d);var a=i.element.find(i.options.filter+"."+o);if(a=a.add(i.relatedTarget(a)),i.options.addIdToRanges)for(var r=0;r<i._currentlyActive.length;r++)e(i._currentlyActive[r]).attr(s,l);i._lastRange&&g(i._lastRange,a)||(i.value(a,t),i._notify(c,t)),i._lastRange=a,i._lastActive=i._downTarget,i._items=null},_invalidateSelectables:function(e,t){var n,i,s,l,r=this._downTarget[0],c=this._items,u=this.options.selectedClass||a;for(this._currentlyActive=[],n=0,i=c.length;n<i;n++)s=(l=c.eq(n)).add(this.relatedTarget(l)),m(l,e)?(l.hasClass(u)?t&&r!==l[0]&&s.removeClass(u).addClass(d):l.hasClass(o)||l.hasClass(d)||this._collidesWithActiveElement(s,e)||s.addClass(o),this._currentlyActive.push(s[0])):l.hasClass(o)?s.removeClass(o):t&&l.hasClass(d)&&s.removeClass(d).addClass(u)},_collidesWithActiveElement:function(e,t){if(!this.options.ignoreOverlapped)return!1;var i,s=this._currentlyActive,l=e[0].getBoundingClientRect(),a=!1,o=n.support.isRtl(e)?"right":"left",r={};t.right=t.left+t.width,t.bottom=t.top+t.height;for(var c=0;c<s.length;c++)if(_(l,i=s[c].getBoundingClientRect())){if(r[o]="left"===o?i.right:i.left,(l=h({},l,r)).left>l.right)return!0;a=!_(l,t)}return a},value:function(e){var t=this,n=t._selectElement.bind(t);if(!e)return t.element.find(t.options.filter+"."+(t.options.selectedClass||a));e.each((function(){n(this)}))},selectedRanges:function(){var t=n.attr("range-selected"),i={};return this.element.find("["+t+"]").each((function(n,s){var l=e(s).attr(t),a=i[l];a||(a=i[l]=[]),a.push(e(s))})),i},selectedSingleItems:function(){var t=this,i=n.attr("range-selected");return t.element.find(t.options.filter+"."+(t.options.selectedClass||a)+":not(["+i+"])").toArray().map((function(t){return e(t)}))},_firstSelectee:function(){var e,t=this;return null!==t._lastActive?t._lastActive:(e=t.value()).length>0?e[0]:t.element.find(t.options.filter)[0]},_selectElement:function(t,n){var i=e(t),s=this.options.selectedClass||a,r=!n&&this._notify("select",{element:t});i.removeClass(o),r||(i.addClass(s),this.options.aria&&i.attr(l,!0))},_notify:function(e,t){return t=t||{},this.trigger(e,t)},_unselect:function(e){if(!this.trigger(u,{element:e})){var t=n.attr("range-selected");return e.removeClass(this.options.selectedClass||a).removeAttr(t),this.options.aria&&e.attr(l,!1),e}},_select:function(t){this._allowSelection(t.event.target)&&(!f||f&&!e(n._activeElement()).is(this.options.inputSelectors))&&t.preventDefault()},_allowSelection:function(t){return!e(t).is(this.options.inputSelectors)||(this.userEvents.cancel(),this._downTarget=null,!1)},resetTouchEvents:function(){this.userEvents.cancel()},clear:function(){var e=this.element.find(this.options.filter+"."+(this.options.selectedClass||a));this._unselect(e)},selectRange:function(t,n){var i,s,l,a=this;for(a.clear(),a.element.length>1&&(l=a.options.continuousItems()),l&&l.length||(l=a.element.find(a.options.filter)),(t=e.inArray(e(t)[0],l))>(n=e.inArray(e(n)[0],l))&&(s=t,t=n,n=s),a.options.useAllItems||(n+=a.element.length-1),i=t;i<=n;i++)a._selectElement(l[i],!0)},destroy:function(){var e=this;i.fn.destroy.call(e),e.element.off(".kendoSelectable"),e.userEvents.destroy(),e._marquee=e._lastActive=e.element=e.userEvents=null}});function g(e,t){if(e.length!==t.length)return!1;for(var n=0;n<e.length;n++)if(e[n]!==t[n])return!1;return!0}function m(e,t){if(!e.is(":visible"))return!1;var i=n.getOffset(e),s=t.left+t.width,l=t.top+t.height;return i.right=i.left+n._outerWidth(e),i.bottom=i.top+n._outerHeight(e),!(i.left>s||i.right<t.left||i.top>l||i.bottom<t.top)}function _(e,t){return!(e.right<=t.left||e.left>=t.right||e.bottom<=t.top||e.top>=t.bottom)}p.parseOptions=function(e){var t=e.mode||e,n="string"==typeof t&&t.toLowerCase();return{multiple:n&&n.indexOf("multiple")>-1,cell:n&&n.indexOf("cell")>-1}},n.ui.plugin(p)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.selectable.js.map
