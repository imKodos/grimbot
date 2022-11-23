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
import"./kendo.core.js";import"./kendo.label.js";var __meta__={id:"dateinput",name:"DateInput",category:"web",description:"The DateInput widget allows to edit date by typing.",depends:["core","label"]};!function(e,t){var a=window.kendo,n=a.caret,s=a.ui,i=s.Widget,r=a.keys,o=".kendoDateInput",l={}.toString,u=e.isPlainObject,d=(a.support.propertyChangeEvent?"propertychange.kendoDateInput input":"input")+o,f="k-focus",h="k-disabled",c="k-invalid",m="disabled",p="readonly",g="change",b="dMyHhmftsz",v=i.extend({init:function(t,n){var s=this;i.fn.init.call(s,t,n),t=s.element,(n=s.options).format=a._extractFormat(n.format||a.getCulture(n.culture).calendars.standard.patterns.d),n.min=a.parseDate(t.attr("min"))||a.parseDate(n.min),n.max=a.parseDate(t.attr("max"))||a.parseDate(n.max);var r=t.parent().attr("class")||"",l=r.indexOf("picker")>=0&&r.indexOf("rangepicker")<0;l?s.wrapper=t.parent():(s.wrapper=t.wrap("<span class='k-dateinput k-input'></span>").parent(),s.wrapper.addClass(t[0].className).removeClass("input-validation-error"),s.wrapper[0].style.cssText=t[0].style.cssText,t.css({height:t[0].style.height})),s._validationIcon=e("<span class='k-input-validation-icon k-icon k-i-warning k-hidden'></span>").insertAfter(t),s._form(),s.element.addClass(l?" ":"k-input-inner").attr("autocomplete","off").on("focus"+o,(function(){s.wrapper.addClass(f)})).on("focusout"+o,(function(){s.wrapper.removeClass(f),s._change()}));try{t[0].setAttribute("type","text")}catch(e){t[0].type="text"}t.is("[disabled]")||e(s.element).parents("fieldset").is(":disabled")?s.enable(!1):s.readonly(t.is("[readonly]")),s.value(s.options.value||t.val()),s._applyCssClasses(),n.label&&s._label(),a.notify(s)},options:{name:"DateInput",culture:"",value:"",format:"",min:new Date(1900,0,1),max:new Date(2099,11,31),messages:{year:"year",month:"month",day:"day",weekday:"day of the week",hour:"hours",minute:"minutes",second:"seconds",dayperiod:"AM/PM"},size:"medium",fillMode:"solid",rounded:"medium",label:null},events:[g],min:function(e){if(e===t)return this.options.min;this.options.min=e},max:function(e){if(e===t)return this.options.max;this.options.max=e},setOptions:function(e){var t=this;i.fn.setOptions.call(t,e),this._unbindInput(),this._bindInput(),this._updateElementValue(),e.label&&t._inputLabel?t.label.setOptions(e.label):!1===e.label?(t.label._unwrapFloating(),t._inputLabel.remove(),delete t._inputLabel):e.label&&t._label()},destroy:function(){var e=this;e.element.off(o),e._formElement&&e._formElement.off("reset",e._resetHandler),e.label&&e.label.destroy(),i.fn.destroy.call(e)},value:function(e){if(e===t)return this._dateTime.getDateObject();null===e&&(e=""),"[object Date]"!==l.call(e)&&(e=a.parseDate(e,this.options.format,this.options.culture)),e&&!e.getTime()&&(e=null),this._dateTime=new _(e,this.options.format,this.options.culture,this.options.messages),this._updateElementValue(),this._oldValue=e,this.label&&this.label.floatingLabel&&this.label.floatingLabel.refresh()},_updateElementValue:function(){var e=this._dateTime.toPair(this.options.format,this.options.culture,this.options.messages);this.element.val(e[0]),this._oldText=e[0],this._format=e[1]},_toggleDateMask:function(e){e?this._updateElementValue():this.element.val("")},readonly:function(e){this._editable({readonly:e===t||e,disable:!1}),this.label&&this.label.floatingLabel&&this.label.floatingLabel.readonly(e===t||e)},enable:function(e){this._editable({readonly:!1,disable:!(e=e===t||e)}),this.label&&this.label.floatingLabel&&this.label.floatingLabel.enable(e=e===t||e)},_label:function(){var t=this,n=t.options,s=u(n.label)?n.label:{content:n.label};t.label=new a.ui.Label(null,e.extend({},s,{widget:t,floatCheck:()=>(t._toggleDateMask(!0),!t.value()&&document.activeElement!==t.element[0]&&(t._toggleDateMask(!1),!0))})),t._inputLabel=t.label.element},_bindInput:function(){var e=this;e.element.on("focus"+o,(function(){e.wrapper.addClass(f)})).on("focusout"+o,(function(){e.wrapper.removeClass(f),e._change()})).on("paste"+o,e._paste.bind(e)).on("keydown"+o,e._keydown.bind(e)).on(d,e._input.bind(e)).on("mouseup"+o,e._mouseUp.bind(e)).on("DOMMouseScroll"+o+" mousewheel"+o,e._scroll.bind(e))},_unbindInput:function(){this.element.off("keydown"+o).off("paste"+o).off("focus"+o).off("focusout"+o).off(d).off("mouseup"+o).off("DOMMouseScroll"+o+" mousewheel"+o)},_editable:function(e){var t=this,a=t.element,n=e.disable,s=e.readonly,i=t.wrapper;t._unbindInput(),s||n?(n&&(i.addClass(h),a.attr(m,n),a&&a.length&&a[0].removeAttribute(p)),s&&a.attr(p,s)):(i.removeClass(h),a&&a.length&&(a[0].removeAttribute(m),a[0].removeAttribute(p)),t._bindInput())},_change:function(){var e=this,t=e._oldValue,a=e.value();a&&e.min()&&a<e.min()&&(e.value(e.min()),a=e.value()),a&&e.max()&&a>e.max()&&(e.value(e.max()),a=e.value()),(t&&a&&a.getTime()!==t.getTime()||t&&!a||!t&&a)&&(e._oldValue=a,e.trigger(g),e.element.trigger(g))},_input:function(){var e=this,t=e.element[0],s=!1;if(a._activeElement()===t){var i=function(e,t,a,n){var s=e[n+e.length-a.length];e=e.substring(0,n+e.length-a.length),a=a.substring(0,n);var i,r=[];if(e===a&&n>0)return r.push([t[n-1],a[n-1]]),r;if(0===e.indexOf(a)&&(0===a.length||t[a.length-1]!==t[a.length])){var o="";for(i=a.length;i<e.length;i++)t[i]!==o&&b.indexOf(t[i])>=0&&(o=t[i],r.push([o,""]));return r}if(" "===a[a.length-1]||a[a.length-1]===s)return[[t[n-1]," "]];if(0===a.indexOf(e)||-1===b.indexOf(t[n-1])){var l=t[0];for(i=Math.max(0,e.length-1);i<t.length;i++)if(b.indexOf(t[i])>=0){l=t[i];break}return[[l,a[n-1]]]}return[[t[n-1],a[n-1]]]}(this._oldText,this._format,this.element[0].value,n(this.element[0])[0]),r=1===i.length&&" "===i[0][1];if(!r)for(var o=0;o<i.length;o++){var l=this._dateTime.parsePart(i[o][0],i[o][1]);s=s||!l}if(this._updateElementValue(),i.length&&" "!==i[0][0]&&(this._selectSegment(i[0][0]),!r)){var u=i[0][0];setTimeout((function(){e._selectSegment(u)}))}if(r){this._keydown({keyCode:39,preventDefault:function(){}})}s&&e._blinkInvalidState()}},_blinkInvalidState:function(){var e=this;e._addInvalidState(),clearTimeout(e._invalidStateTimeout),e._invalidStateTimeout=setTimeout(e._removeInvalidState.bind(e),100)},_addInvalidState:function(){this.wrapper.addClass(c),this._validationIcon.removeClass("k-hidden")},_removeInvalidState:function(){var e=this;e.wrapper.removeClass(c),e._validationIcon.addClass("k-hidden"),e._invalidStateTimeout=null},_mouseUp:function(){var e=n(this.element[0]);e[0]===e[1]&&this._selectNearestSegment()},_scroll:function(e){if(a._activeElement()===this.element[0]&&!this.element.is("[readonly]")){var t={keyCode:37,preventDefault:function(){}};(e=window.event||e).shiftKey?t.keyCode=(e.wheelDelta||-e.detail)>0?37:39:t.keyCode=(e.wheelDelta||-e.detail)>0?38:40,this._keydown(t),e.returnValue=!1,e.preventDefault&&e.preventDefault(),e.stopPropagation&&e.stopPropagation()}},_form:function(){var t=this,a=t.element,n=a.attr("form"),s=n?e("#"+n):a.closest("form"),i=a[0].value;!i&&t.options.value&&(i=t.options.value),s[0]&&(t._resetHandler=function(){setTimeout((function(){t.value(i)}))},t._formElement=s.on("reset",t._resetHandler))},_paste:function(e){e.preventDefault()},_keydown:function(e){var t,s=e.keyCode;if(37==s||39==s){e.preventDefault(),(t=n(this.element[0]))[0]!=t[1]&&this._selectNearestSegment();for(var i=37==s?-1:1,o=-1==i?n(this.element[0])[0]-1:n(this.element[0])[1]+1;o>=0&&o<this._format.length;){if(b.indexOf(this._format[o])>=0){this._selectSegment(this._format[o]);break}o+=i}}if(38==s||40==s){e.preventDefault(),t=n(this.element[0]);var l=this._format[t[0]];if(b.indexOf(l)>=0){var u=1;"m"==l&&(u=this.options.interval||1),this._dateTime.modifyPart(l,38==s?1*u:-1*u),this._updateElementValue(),this._selectSegment(l),this.element.trigger(g)}}if(a.support.browser.msie&&a.support.browser.version<10){var d=e.keyCode?e.keyCode:e.which;if(8===d||46===d){var f=this;setTimeout((function(){f._input()}),0)}}s===r.ENTER&&this._change()},_selectNearestSegment:function(){for(var e=n(this.element[0])[0],t=e,a=e-1;t<this._format.length||a>=0;t++,a--){if(t<this._format.length&&-1!==b.indexOf(this._format[t]))return void this._selectSegment(this._format[t]);if(a>=0&&-1!==b.indexOf(this._format[a]))return void this._selectSegment(this._format[a])}},_selectSegment:function(e){for(var t=-1,a=0,s=0;s<this._format.length;s++)this._format[s]===e&&(a=s+1,-1===t&&(t=s));t<0&&(t=0),n(this.element,t,a)}});a.cssProperties.registerPrefix("DateInput","k-input-"),a.cssProperties.registerValues("DateInput",[{prop:"rounded",values:a.cssProperties.roundedValues.concat([["full","full"]])}]),s.plugin(v);var _=function(e,n,s,i){var r=null,o=!0,l=!0,u=!0,d=!0,f=!0,h=!0,c="",m="",p={},g=["","0","00","000","0000"];function b(e,t,a){return(a=(t=t||2)-(e+="").length)?g[t].substring(0,a)+e:e}var v=/dddd|ddd|dd|d|MMMM|MMM|MM|M|yyyy|yy|HH|H|hh|h|mm|m|fff|ff|f|tt|ss|s|zzz|zz|z|"[^"]*"|'[^']*'/g,_=null,k=null,y=null,M=!1,w=function(e){var a,n,s;switch(e){case"d":s=u?r.getDate():p.day;break;case"dd":s=u?b(r.getDate()):p.day;break;case"ddd":s=u&&l&&o?y.namesAbbr[r.getDay()]:p.weekday;break;case"dddd":s=u&&l&&o?y.names[r.getDay()]:p.weekday;break;case"M":s=l?r.getMonth()+1:p.month;break;case"MM":s=l?b(r.getMonth()+1):p.month;break;case"MMM":s=l?_.namesAbbr[r.getMonth()]:p.month;break;case"MMMM":s=l?_.names[r.getMonth()]:p.month;break;case"yy":s=o?b(r.getFullYear()%100):p.year;break;case"yyyy":s=o?b(r.getFullYear(),4):p.year;break;case"h":s=d?r.getHours()%12||12:p.hour;break;case"hh":s=d?b(r.getHours()%12||12):p.hour;break;case"H":s=d?r.getHours():p.hour;break;case"HH":s=d?b(r.getHours()):p.hour;break;case"m":s=f?r.getMinutes():p.minute;break;case"mm":s=f?b(r.getMinutes()):p.minute;break;case"s":s=h?r.getSeconds():p.second;break;case"ss":s=h?b(r.getSeconds()):p.second;break;case"f":s=Math.floor(r.getMilliseconds()/100);break;case"ff":(s=r.getMilliseconds())>99&&(s=Math.floor(s/10)),s=b(s);break;case"fff":s=b(r.getMilliseconds(),3);break;case"tt":s=d?r.getHours()<12?k.AM[0]:k.PM[0]:p.dayperiod;break;case"zzz":n=(a=r.getTimezoneOffset())<0,s=Math.abs(a/60).toString().split(".")[0],a=Math.abs(a)-60*s,s=(n?"+":"-")+b(s),s+=":"+b(a);break;case"z":case"zz":n=(s=r.getTimezoneOffset()/60)<0,s=Math.abs(s).toString().split(".")[0],s=(n?"+":"-")+("zz"===e?b(s):s)}if(s=s!==t?s:e.slice(1,e.length-1),M){s=""+s;var i="";"ddd"==e&&(e="EEE"),"dddd"==e&&(e="EEEE");for(var c=0;c<s.length;c++)i+=e[0];return i}return s};function D(e){return M=e,w}function S(e,t){switch(e){case"y":o=t;break;case"M":l=t,t||(r.setMonth(0),c="");break;case"d":u=t;break;case"H":case"h":d=t,t||(m="");break;case"m":f=t;break;case"s":h=t;break;default:return}}if(this.setValue=function(e){u=e},this.getValue=function(){return u},this.modifyPart=function(e,t){var a=new Date(r&&r.getTime?r.getTime():r);switch(e){case"y":a.setFullYear(a.getFullYear()+t);break;case"M":var n=a.getMonth()+t;a.setMonth(n),a.getMonth()%12!=(n+12)%12&&(a.setDate(1),a.setMonth(n));break;case"d":case"E":a.setDate(a.getDate()+t);break;case"H":case"h":a.setHours(a.getHours()+t);break;case"m":a.setMinutes(a.getMinutes()+t);break;case"s":a.setSeconds(a.getSeconds()+t);break;case"t":a.setHours((a.getHours()+12)%24)}a.getFullYear()>0&&(S(e,!0),r=a)},this.parsePart=function(e,t){if(!t)return S(e,!1),!0;var a,n=new Date(r&&r.getTime?r.getTime():r),s=new Date(n.getFullYear(),n.getMonth()+1,0).getDate();switch(e){case"d":var i=(u?10*n.getDate():0)+parseInt(t,10);if(isNaN(i))return;for(;i>s;)i=parseInt(i.toString().slice(1),10);if(i<1)u=!1;else{if(n.setDate(i),n.getMonth()!==r.getMonth())return;u=!0}break;case"M":var p=(l?10*(n.getMonth()+1):0)+parseInt(t,10);if(isNaN(p)){var g=k.months.names;for(c+=t.toLowerCase();c.length>0;){for(var b=0;b<g.length;b++)if(0===g[b].toLowerCase().indexOf(c))return n.setMonth(b),l=!0,r=n,!0;c=c.substring(1,c.length)}return!1}for(;p>12;)p=parseInt(p.toString().slice(1),10);p<1?l=!1:(n.setMonth(p-1),n.getMonth()!==p-1&&(n.setDate(1),n.setMonth(p-1)),l=!0);break;case"y":var v=(o?10*n.getFullYear():0)+parseInt(t,10);if(isNaN(v))return;for(;v>9999;)v=parseInt(v.toString().slice(1),10);v<1?o=!1:(n.setFullYear(v),o=!0);break;case"h":if(a=(d?10*(n.getHours()%12||12):0)+parseInt(t,10),isNaN(a))return;for(;a>12;)a=parseInt(a.toString().slice(1),10);n.setHours(12*Math.floor(n.getHours()/12)+a%12),d=!0;break;case"H":if(a=(d?10*n.getHours():0)+parseInt(t,10),isNaN(a))return;for(;a>23;)a=parseInt(a.toString().slice(1),10);n.setHours(a),d=!0;break;case"m":var _=(f?10*n.getMinutes():0)+parseInt(t,10);if(isNaN(_))return;for(;_>59;)_=parseInt(_.toString().slice(1),10);n.setMinutes(_),f=!0;break;case"s":var y=(h?10*n.getSeconds():0)+parseInt(t,10);if(isNaN(y))return;for(;y>59;)y=parseInt(y.toString().slice(1),10);n.setSeconds(y),h=!0;break;case"t":if(d){for(m+=t.toLowerCase();m.length>0;){if(0===k.AM[0].toLowerCase().indexOf(m)&&n.getHours()>=12||0===k.PM[0].toLowerCase().indexOf(m)&&n.getHours()<12)return n.setHours((n.getHours()+12)%24),r=n,!0;m=m.substring(1,m.length)}return!1}}return r=n,!0},this.toPair=function(e,t,n){return e?(t=a.getCulture(t),e=(k=t.calendars.standard).patterns[e]||e,y=k.days,_=k.months,p=n,[e.replace(v,D(!1)),e.replace(v,D(!0))]):["",""]},this.getDateObject=function(){return o&&l&&u&&d&&f&&h?new Date(r.getTime()):null},e)r=new Date(e.getTime());else{r=new Date;for(var I=this.toPair(n,s,i)[1],x=0;x<I.length;x++)S(I[x],!1)}}}(window.kendo.jQuery);
//# sourceMappingURL=kendo.dateinput.js.map
