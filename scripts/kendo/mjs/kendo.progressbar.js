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
import"./kendo.core.js";var __meta__={id:"progressbar",name:"ProgressBar",category:"web",description:"The ProgressBar offers rich functionality for displaying and tracking progress",depends:["core"]};!function(e,r){var a=window.kendo,s=a.ui.Widget,t="horizontal",n="vertical",o="k-progressbar-indeterminate",i="k-complete",p="k-progress-status",l="k-selected",u="k-disabled",d={VALUE:"value",PERCENT:"percent",CHUNK:"chunk"},c="change",g="complete",v=Math,_=e.extend,h={progressStatus:"<span class='k-progress-status-wrap k-progress-end'><span class='k-progress-status'></span></span>",announceElement:'<span aria-live="polite" class="k-sr-only k-progress-announce"></span>'},f=s.extend({init:function(e,r){var a=this;s.fn.init.call(this,e,r),r=a.options,a._progressProperty=r.orientation===t?"width":"height",a._fields(),r.value=a._validateValue(r.value),a._validateType(r.type),a._wrapper(),r.ariaRole&&a._aria(),a._progressAnimation(),r.value!==r.min&&!1!==r.value&&a._updateProgress()},setOptions:function(e){var r=this,a=r.wrapper;s.fn.setOptions.call(r,e),e.hasOwnProperty("reverse")&&a.toggleClass("k-progressbar-reverse",e.reverse),e.hasOwnProperty("enable")&&r.enable(e.enable),e.ariaRole&&r._aria(),r._progressAnimation(),r._validateValue(),r._updateProgress()},events:[c,g],options:{name:"ProgressBar",orientation:t,reverse:!1,min:0,max:100,value:0,enable:!0,type:d.VALUE,chunkCount:5,showStatus:!0,animation:{},label:null,labelId:null,ariaRole:!1},_aria:function(){var r=this,a=r.options,s=r.wrapper;s.attr({role:"progressbar","aria-valuemin":a.min,"aria-valuemax":a.max}),a.labelId?s.attr("aria-labelledby",a.labelId):a.label&&s.attr("aria-label",a.label),r.announce=e(h.announceElement),r.announce.appendTo(e("body")),!1!==a.value&&(s.attr("aria-valuenow",a.value),r.announce.text(r._calculatePercentage().toFixed()+"%"))},_fields:function(){var r=this;r._isStarted=!1,r.progressWrapper=r.progressStatus=e()},_validateType:function(r){var s=!1;if(e.each(d,(function(e,a){if(a===r)return s=!0,!1})),!s)throw new Error(a.format("Invalid ProgressBar type '{0}'",r))},_wrapper:function(){var e=this,r=e.wrapper=e.element,a=e.options,s=a.orientation,i=!1!==a.value?a.value:a.min;r.addClass("k-widget k-progressbar"),r.addClass("k-progressbar-"+(s===t?t:n)),!1===a.enable&&r.addClass(u),a.reverse&&r.addClass("k-progressbar-reverse"),!1===a.value&&r.addClass(o),a.type===d.CHUNK?e._addChunkProgressWrapper():a.showStatus&&(e.progressStatus=e.wrapper.prepend(h.progressStatus).find("."+p),a.type===d.VALUE?e.progressStatus.text(i):e.progressStatus.text(e._calculatePercentage(i).toFixed()+"%"))},value:function(e){return this._value(e)},_value:function(e){var a,s=this,t=s.options;if(e===r)return t.value;"boolean"!=typeof e?(e=s._roundValue(e),isNaN(e)||(a=s._validateValue(e))!==t.value&&(s.wrapper.removeClass(o),t.value=a,s._isStarted=!0,s._updateProgress())):e||(s.wrapper.addClass(o),s.wrapper.removeAttr("aria-valuenow"),t.value=!1,s.announce&&s.announce.text(""))},_roundValue:function(e){e=parseFloat(e);var r=v.pow(10,3);return a._round(e*r)/r},_validateValue:function(e){var r=this.options;if(!1!==e){if(e<=r.min||!0===e)return r.min;if(e>=r.max)return r.max}else if(!1===e)return!1;return isNaN(this._roundValue(e))?r.min:e},_updateProgress:function(){var e=this,r=e.options,a=e._calculatePercentage();r.type===d.CHUNK?(e._updateChunks(a),e._onProgressUpdateAlways(r.value)):e._updateProgressWrapper(a),r.ariaRole&&(e.wrapper.attr("aria-valuenow",e.options.value),e.announce&&e.announce.text(a.toFixed()+"%"))},_updateChunks:function(e){var r,s=this,o=s.options,i=o.chunkCount,p=parseInt(100/i*100,10)/100,u=parseInt(100*e,10)/100,d=v.floor(u/p);r=o.orientation===t&&!o.reverse||o.orientation===n&&o.reverse?s.wrapper.find("li.k-item").slice(0,d):0===d?a.jQuery():s.wrapper.find("li.k-item").slice(-1*d),s.wrapper.find(".k-selected").removeClass(l),r.addClass(l)},_updateProgressWrapper:function(e){var r=this,a=r.options,s=r.wrapper.find(".k-selected"),t=r._isStarted?r._animation.duration:0,n={};0===s.length&&r._addRegularProgressWrapper(),n[r._progressProperty]=e+"%",r.progressWrapper.animate(n,{duration:t,start:r._onProgressAnimateStart.bind(r),progress:r._onProgressAnimate.bind(r),complete:r._onProgressAnimateComplete.bind(r,a.value),always:r._onProgressUpdateAlways.bind(r,a.value)})},_onProgressAnimateStart:function(){this.progressWrapper.show()},_onProgressAnimate:function(e){var r,a=this,s=a.options,t=parseFloat(e.elem.style[a._progressProperty],10);s.showStatus&&(r=1e4/parseFloat(a.progressWrapper[0].style[a._progressProperty]),a.progressWrapper.find(".k-progress-status-wrap").css(a._progressProperty,r+"%")),s.type!==d.CHUNK&&t<=98&&a.progressWrapper.removeClass(i)},_onProgressAnimateComplete:function(e){var r,a=this,s=a.options,t=parseFloat(a.progressWrapper[0].style[a._progressProperty]);s.type!==d.CHUNK&&t>98&&a.progressWrapper.addClass(i),s.showStatus&&(r=s.type===d.VALUE?e:s.type==d.PERCENT?a._calculatePercentage(e).toFixed()+"%":v.floor(a._calculatePercentage(e))+"%",a.progressStatus.text(r)),e===s.min&&a.progressWrapper.hide()},_onProgressUpdateAlways:function(e){var r=this,a=r.options;r._isStarted&&r.trigger(c,{value:e}),e===a.max&&r._isStarted&&r.trigger(g,{value:a.max})},enable:function(e){var r=this.options;r.enable=void 0===e||e,this.wrapper.toggleClass(u,!r.enable)},destroy:function(){var e=this;e.announce&&e.announce.remove(),s.fn.destroy.call(e)},_addChunkProgressWrapper:function(){var e=this,r=e.options,a=e.wrapper,s=100/r.chunkCount,t="";r.chunkCount<=1&&(r.chunkCount=1),t+="<ul class='k-reset'>";for(var n=r.chunkCount-1;n>=0;n--)t+="<li class='k-item'></li>";t+="</ul>",a.append(t).find(".k-item").css(e._progressProperty,s+"%").first().addClass("k-first").end().last().addClass("k-last"),e._normalizeChunkSize()},_normalizeChunkSize:function(){var e=this,r=e.options,a=e.wrapper.find(".k-item").last(),s=parseFloat(a[0].style[e._progressProperty]),t=100-r.chunkCount*s;t>0&&a.css(e._progressProperty,s+t+"%")},_addRegularProgressWrapper:function(){var r=this;r.progressWrapper=e("<div class='k-selected'></div>").appendTo(r.wrapper),r.options.showStatus&&(r.progressWrapper.append(h.progressStatus),r.progressStatus=r.wrapper.find("."+p))},_calculateChunkSize:function(){var e=this,r=e.options.chunkCount,a=e.wrapper.find("ul.k-reset");return(parseInt(a.css(e._progressProperty),10)-(r-1))/r},_calculatePercentage:function(e){var a=this,s=a.options,t=e!==r?e:s.value,n=s.min,o=s.max;return a._onePercent=v.abs((o-n)/100),v.abs((t-n)/a._onePercent)},_progressAnimation:function(){var e=this,r=e.options,a=r.animation;e._animation=!1===a?{duration:0}:_({duration:400},r.animation)}});a.ui.plugin(f)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.progressbar.js.map
