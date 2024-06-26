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
import"./kendo.calendar.js";import"./kendo.popup.js";import"./kendo.dateinput.js";import"./kendo.html.button.js";import"./kendo.label.js";var __meta__={id:"timepicker",name:"TimePicker",category:"web",description:"The TimePicker widget allows the end user to select a value from a list of predefined values or to type a new value.",depends:["calendar","popup","html.button","label"]};!function(t,e){var i=window.kendo,n=i.keys,a=i.html,s=i.parseDate,l=i._activeElement,o=i._extractFormat,r=i.support,u=r.browser,c=i.ui,d=c.Widget,h="open",p="close",m="change",f=".kendoTimePicker",_="click"+f,g="disabled",v="readonly",b="li",k="k-focus",w="k-hover",y="mousedown"+f,x=6e4,T=864e5,S="k-selected",M="k-disabled",L="aria-selected",V="aria-expanded",C="aria-hidden",D="aria-disabled",H="aria-readonly",P="aria-activedescendant",I="id",R=Array.isArray,A=t.extend,F=Date,z=/d{1,2}|E{1,6}|e{1,6}|c{3,6}|c{1}|M{1,5}|L{1,5}|y{1,4}|H{1,2}|h{1,2}|k{1,2}|K{1,2}|m{1,2}|a{1,5}|s{1,2}|S{1,3}|z{1,4}|Z{1,5}|x{1,5}|X{1,5}|G{1,5}|q{1,5}|Q{1,5}|"[^"]*"|'[^']*'/g,O="month",E="hour",q="zone",B="weekday",N="quarter",Y={G:"era",y:"year",q:N,Q:N,M:O,L:O,d:"day",E:B,c:B,e:B,h:E,H:E,k:E,K:E,m:"minute",s:"second",a:"dayperiod",x:q,X:q,z:q,Z:q},W={month:{type:"months",minLength:3,standAlone:"L"},quarter:{type:"quarters",minLength:3,standAlone:"q"},weekday:{type:"days",minLength:{E:0,c:3,e:3},standAlone:"c"},dayperiod:{type:"dayPeriods",minLength:0},era:{type:"eras",minLength:0}},j=new F;j=new Date(j.getFullYear(),j.getMonth(),j.getDate(),0,0,0);var K=function(t){var e=this,i=t.id;e.options=t,e._dates=[],e._createList(t.timeView&&"scroll"===t.timeView.list),i&&(e._timeViewID=i+"_timeview",e._optionID=i+"_option_selected",e.ul.attr(I,e._timeViewID)),e._heightHandler=e._height.bind(e),e._popup()};function G(t){return 60*t.getHours()*x+t.getMinutes()*x+1e3*t.getSeconds()+t.getMilliseconds()}function Q(t,e,i){var n,a=G(e),s=G(i);return!t||a==s||(a>(n=G(t))&&(n+=T),s<a&&(s+=T),n>=a&&n<=s)}K.prototype={_createList:function(t){t?this._createScrollList():this._createClassicRenderingList()},_createScrollList:function(){var e=t.extend({},this.options,{mainSize:i.getValidCssClass("k-timeselector-","size",this.options.size||"medium"),buttonSize:i.getValidCssClass("k-button-","size",this.options.size||"medium")});this.list=t(i.template('<div tabindex="0" class="k-timeselector #=mainSize#"><div class="k-time-header"><span class="k-title"></span><button class="k-button #=buttonSize# k-rounded-md k-button-flat k-button-flat-base k-time-now" title="Select now" aria-label="Select now"><span class="k-button-text">#=messages.now#</span></button></div><div class="k-time-list-container"><span class="k-time-highlight"></span></div></div>')(e)).on(y,Z),this.options.omitPopup||this.list.append(i.template('<div class="k-time-footer k-action-buttons"><button class="k-button #=buttonSize# k-rounded-md k-button-solid k-button-solid-base k-time-cancel" title="Cancel changes" aria-label="Cancel changes"><span class="k-button-text">#=messages.cancel#</span></button><button class="k-time-accept k-button #=buttonSize# k-rounded-md k-button-solid k-button-solid-primary" title="Set time" aria-label="Set time"><span class="k-button-text">#=messages.set#</span></button></div>')(e)),this.ul=this.list.find(".k-time-list-container"),this.list.on("click"+f,".k-time-header button.k-time-now",this._nowClickHandler.bind(this)),this.list.on("click"+f,".k-time-footer button.k-time-cancel",this._cancelClickHandler.bind(this)),this.list.on("click"+f,".k-time-footer button.k-time-accept",this._setClickHandler.bind(this)),this.list.on("mouseover"+f,".k-time-list-wrapper",this._mouseOverHandler.bind(this)),this.list.on("keydown"+f,this._scrollerKeyDownHandler.bind(this))},_scrollerKeyDownHandler:function(e){var a=this,s=e.keyCode,l=t(e.currentTarget).find(".k-time-list-wrapper.k-focus"),o=a.list.find(".k-time-list-wrapper"),r=o.length,u=o.index(l),c=i.support.isRtl(a.wrapper),d=st(l.find(".k-item:visible").eq(0)),h=l.find(".k-time-container.k-content.k-scrollable");l.length&&(s==n.RIGHT&&!c||s==n.LEFT&&c?u+1<r&&a._focusList(o.eq(u+1)):s==n.LEFT&&!c||s==n.RIGHT&&c?u-1>=0&&a._focusList(o.eq(u-1)):s==n.UP?(h.scrollTop(h.scrollTop()-d),e.preventDefault()):s==n.DOWN?(h.scrollTop(h.scrollTop()+d),e.preventDefault()):s===n.ENTER?a._setClickHandler(e):s===n.ESC&&a._cancelClickHandler(e))},_mouseOverHandler:function(e){this._focusList(t(e.currentTarget))},_focusList:function(t){this.list.find(".k-time-list-wrapper").removeClass(k),t.addClass(k),this.list.trigger("focus"),this._scrollTop=t.find(".k-scrollable").scrollTop()},_createClassicRenderingList:function(){var e=this,n=t('<div class="k-list '+i.getValidCssClass("k-list-","size",e.options.size)+'"><ul tabindex="-1" role="listbox" aria-hidden="true" unselectable="on" class="k-list-ul"/></div>');e.ul=n.find("ul").css({overflow:r.kineticScrollNeeded?"":"auto"}).on(_,b,e._click.bind(e)).on("mouseenter"+f,b,(function(){t(this).addClass(w)})).on("mouseleave"+f,b,(function(){t(this).removeClass(w)})),e.list=t("<div class='k-list-container k-list-scroller' unselectable='on'/>").append(n).on(y,Z),e.template=i.template('<li tabindex="-1" role="option" class="k-list-item" unselectable="on"><span class="k-list-item-text">#=data#</span></li>',{useWithBlock:!1})},current:function(i){var n=this,a=n.options.active;if(i===e)return n._current;n._current&&(n._current.removeClass(S),n._current&&n._current.length&&(n._current[0].removeAttribute(I),n._current[0].removeAttribute(L))),i&&(i=t(i).addClass(S).attr(I,n._optionID).attr(L,!0),n.scroll(i[0])),n._current=i,a&&a(i)},_updateTitle:function(){this.list.find(".k-time-header > .k-title").html(i.toString(this._value,this.options.format,this.options.culture))},applyValue:function(t){if(t){var e,n=nt(this.options.format.toLowerCase(),"t"),a=t.getHours(),s=t.getMinutes(),l=t.getSeconds(),o=i.attr("index"),r=this.ul.find("["+o+'="1"]'),u=this.ul.find("["+o+'="2"]'),c=this.ul.find("["+o+'="3"]'),d=this.ul.find("["+o+'="4"]');n&&(a>=12?(e="PM",a>12&&(a-=12)):(e="AM",0===a&&(a=12))),this._internalScroll=!0,r.length&&this._scrollListToPosition(r,a),u.length&&this._scrollListToPosition(u,s),c.length&&this._scrollListToPosition(c,l),d.length&&this._scrollListToPosition(d,e),this._internalScroll=!1}},_scrollListToPosition:function(t,e){var i=t.find('.k-item[data-value="'+at(e)+'"]'),n=st(i);t.scrollTop(t.find(".k-item:visible").index(i)*n)},close:function(){this.popup.close()},destroy:function(){var t=this;t.ul.off(f),t.list.off(f),this.popup&&t.popup.destroy()},open:function(){var t,e=this;(!e.ul[0].firstChild||e.ul.find("li").length<1)&&e.bind(),t=e.popup._hovered,e.popup._hovered=!0,e.popup.open(),setTimeout((function(){e.popup._hovered=t}),1),e._current&&e.scroll(e._current[0])},dataBind:function(t){for(var e,n=this,a=n.options,s=a.format,l=i.toString,o=n.template,r=t.length,u=0,c="";u<r;u++)Q(e=t[u],a.min,a.max)&&(c+=o(l(e,s,a.culture)));n._html(c)},refresh:function(){var t,e,n,a,s,l=this,o=l.options,r=o.format,u=(n=new F,a=new F(n.getFullYear(),n.getMonth(),n.getDate(),0,0,0),s=new F(n.getFullYear(),n.getMonth(),n.getDate(),12,0,0),-1*(a.getTimezoneOffset()-s.getTimezoneOffset())),c=u<0,d=i.parseDate(l._value),h=J(d||new Date,o.min),p=o.min,m=o.max,f=G(p),_=G(m),g=G((t=o.interval,(e=new Date(2100,0,1)).setMinutes(-t),e)),v=o.interval*x,b=i.toString,k=l.template,w=o.useValueToRender?h:new Date(+o.min),y=new F(w),S="";f==_&&g!==_||f>_&&(_+=T),o.timeView&&"scroll"===o.timeView.list?S=l._createListContent(function(t){var e,n,a,s,l,o,r=i.culture(),u=function(t,e){var i,n=e.calendar;"string"==typeof t&&(i=n.patterns[t]?n.patterns[t]:t);i||(i=n.patterns.d);return i}(t,r),c=[],d=z.lastIndex=0,h=z.exec(u);for(;h;){var p=h[0];d<h.index&&$(c,u.substring(d,h.index)),it(p,'"')||it(p,"'")?$(c,p):(e=p[0],a={type:n=Y[e],pattern:p},"hour"===n&&(a.hour12=tt(p)),(s=W[n])&&(l="number"==typeof s.minLength?s.minLength:s.minLength[e],(o=p.length)>=l&&(a.names={type:s.type,nameType:et(o),standAlone:s.standAlone===e})),c.push(a)),d=z.lastIndex,h=z.exec(u)}d<u.length&&$(c,u.substring(d));return c}(r)):l.getDatesInRange(undefined,_,y,m,v,w).forEach((function(t){S+=k(b(t,r,o.culture))})),l._html(S)},_showAllHiddenItems:function(){for(var e,i=this.list.find(".k-time-container"),n=i.length,a=0;a<n;a++)(e=t(i[a])).find(".k-item:hidden").show(),this._updateListBottomOffset(e)},_updateListBottomOffset:function(t){var e=st(t.find(".k-item:visible").eq(0)),i=t.outerHeight()-e;t.find(".k-scrollable-placeholder").css({height:t.find("ul").height()+i})},_updateHoursRange:function(){var e,n,a=this,s=i.attr("index"),l=this.ul.find("["+s+'="1"]'),o=this._minHours,r=this._maxHours,u=nt(this.options.format.toLowerCase(),"t"),c=this._findSelectedValue(this.ul.find("["+s+'="4"]'));l.length&&(u&&c?("AM"===c?(o<12&&(n=!0),r<12&&(e=!0)):"PM"===c&&(o>12&&(n=!0,o-=12),r>12&&(e=!0,r-=12)),l.find(".k-item").each((function(i,s){var l=+(s=t(s)).attr("data-value");a._validateMin&&n&&l<o||a._validateMax&&e&&l>r?s.hide():s.show()}))):l.find(".k-item").each((function(e,i){var n=+(i=t(i)).attr("data-value");a._validateMin&&n<o||a._validateMax&&n>r?i.hide():i.show()})),this._updateListBottomOffset(l))},_updateMinutesRange:function(){var e=this,n=i.attr("index"),a=this.ul.find("["+n+'="2"]'),s=this._minHours,l=this._maxHours,o=this._minMinutes,r=this._maxMinutes,u=+this._findSelectedValue(this.ul.find("["+n+'="1"]')),c=nt(this.options.format.toLowerCase(),"t"),d=this._findSelectedValue(this.ul.find("["+n+'="4"]'));c&&"PM"===d&&(u+=12),a.length&&(a.find(".k-item").each((function(i,n){var a=+(n=t(n)).attr("data-value");e._validateMin&&a<o&&s&&u===s||e._validateMax&&a>r&&l&&u===l?n.hide():n.show()})),this._updateListBottomOffset(a))},_updateSecondsRange:function(){var e=this,n=i.attr("index"),a=this.ul.find("["+n+'="3"]'),s=this._minSeconds,l=this._minSeconds,o=this._minMinutes,r=this._maxMinutes,u=+this._findSelectedValue(this.ul.find("["+n+'="2"]'));a.length&&(a.find(".k-item").each((function(i,n){var a=+(n=t(n)).attr("data-value");e._validateMin&&a<s&&o&&u===o||e._validateMax&&a>l&&r&&u===r?n.hide():n.show()})),this._updateListBottomOffset(a))},_updateDesignatorRange:function(){var t=this._minHours,e=this._maxHours,n=i.attr("index"),a=this.ul.find("["+n+'="4"]');a.length&&(this._validateMin&&t>=12?a.find('.k-item[data-value="AM"]').hide():a.find('.k-item[data-value="AM"]').show(),this._validateMax&&e<12?a.find('.k-item[data-value="PM"]').hide():a.find('.k-item[data-value="PM"]').show())},_updateRanges:function(){if(this.options.specifiedRange){this._currentlySelected||(this._currentlySelected=new Date);var t=this.options.max,e=this.options.min;if(this.options.validateDate){if(t.getFullYear()===this._currentlySelected.getFullYear()&&t.getMonth()===this._currentlySelected.getMonth()&&t.getDate()===this._currentlySelected.getDate()?this._validateMax=!0:this._validateMax=!1,e.getFullYear()===this._currentlySelected.getFullYear()&&e.getMonth()===this._currentlySelected.getMonth()&&e.getDate()===this._currentlySelected.getDate()?this._validateMin=!0:this._validateMin=!1,!this._validateMax&&!this._validateMin)return void this._showAllHiddenItems()}else this._validateMax=!0,this._validateMin=!0;this._minMinutes=e.getMinutes(),this._maxMinutes=t.getMinutes(),this._minHours=e.getHours(),this._maxHours=t.getHours(),this._minSeconds=e.getSeconds(),this._maxSeconds=t.getSeconds(),this._updateDesignatorRange(),this._updateHoursRange(),this._updateMinutesRange(),this._updateSecondsRange()}},addTranslate:function(){for(var t,e,i,n,a,s=this.ul.find(".k-time-container.k-content.k-scrollable"),l=s.length,o=0;o<l;o++)e=st((t=s.eq(o)).find(".k-item:visible").eq(0)),n="translateY("+((i=t.outerHeight())-e)/2+"px)",a=i-e,t.find("ul").css({transform:n,"-ms-transform":n}),t.find(".k-scrollable-placeholder").css({height:t.find("ul").height()+a}),t.off(f).on("click"+f,".k-item",this._itemClickHandler.bind(this)).on("scroll"+f,this._listScrollHandler.bind(this))},_nowClickHandler:function(t){t.preventDefault();var e=new Date;this.value(e),this.options.change(i.toString(e,this.options.format,this.options.culture),!0)},_cancelClickHandler:function(t){t.preventDefault(),this.value(this._value),this.popup.close()},_setClickHandler:function(t){t.preventDefault(),this._value=new Date(this._currentlySelected),this.options.change(i.toString(this._currentlySelected,this.options.format,this.options.culture),!0),this.popup.close()},_listScrollHandler:function(e){var i=this,n=st(t(e.currentTarget).find(".k-item:visible").eq(0));i._internalScroll||(i._scrollingTimeout&&clearTimeout(i._scrollingTimeout),i._scrollingTimeout=setTimeout((function(){e.currentTarget.scrollTop%n>1&&(e.currentTarget.scrollTop+=n-e.currentTarget.scrollTop%n),i._scrollTop=e.currentTarget.scrollTop,i._updateCurrentlySelected(),i._updateRanges()}),100))},_updateCurrentlySelected:function(){var t,n,a,s,l=nt(this.options.format.toLowerCase(),"t"),o=i.attr("index"),r=this.ul.find("["+o+'="1"]'),u=this.ul.find("["+o+'="2"]'),c=this.ul.find("["+o+'="3"]'),d=this.ul.find("["+o+'="4"]');this.ul.is(":visible")&&(this._currentlySelected||(this._currentlySelected=this._value?new Date(this._value):new Date),r.length&&(t=+this._findSelectedValue(r)),u.length&&(n=+this._findSelectedValue(u)),c.length&&(a=+this._findSelectedValue(c)),d.length&&(s=this._findSelectedValue(d)),l&&("PM"==s&&24==(t+=12)&&(t=12),"AM"===s&&12===t&&(t=0)),t!==e&&this._currentlySelected.setHours(t),n!==e&&this._currentlySelected.setMinutes(n),a!==e&&this._currentlySelected.setSeconds(a))},_findSelectedValue:function(t){var e,i,n=(e=t.scrollTop(),i=st(t.find(".k-item:visible").eq(0)),Math.max(Math.round(e/i),0));return t.find(".k-item:visible").eq(n).attr("data-value")},_itemClickHandler:function(e){var i=t(e.originalEvent.currentTarget),n=i.find(".k-item:visible").index(t(e.currentTarget)),a=st(i.find(".k-item:visible").eq(0));i.scrollTop(n*a)},getDatesInRange:function(t,e,i,n,a,s){for(var l=[];;){if(e&&(G(s)>=e||i.getDate()!=s.getDate())){t=G(s),i<s&&(t+=T),t>e&&(s=new F(+n)),G(s)>0&&l.push(new Date(s));break}if(i.getDate()!=s.getDate())break;if(l.push(new Date(s)),s.setTime(s.getTime()+a),!e&&this.options.maxSet)break}return l},_createListContent:function(t){for(var e,i,n=t.length,a="",s=0;s<n;s++)"literal"===(e=t[s]).type?a+=this._literalTemplate(e):(i=this._getValues(e,!0),a+=this._itemTemplate(i.values,e,this.options.messages[e.type],i.index));return a},_itemTemplate:function(t,e,n,a){var s="",l=t.length,o=i.attr("index");s+='<div class="k-time-list-wrapper" role="presentation"><span class="k-title">'+(n||e.type)+'</span><div class="k-time-list"><div class="k-time-container k-content k-scrollable" role="presentation" '+o+'="'+a+'"><ul class="k-reset">';for(var r=0;r<l;r++)s+='<li class="k-item" data-value="'+t[r]+'"><span>'+t[r]+"</span></li>";return s+='</ul><div class="k-scrollable-placeholder"></div></div></div></div>'},_getValues:function(t,e){var i,n,a=[],s=0;for("hour"===t.type?(s=t.hour12?1:0,i=1,n=t.hour12?12:23):"minute"===t.type?(i=2,n=59):"second"===t.type&&(i=3,n=59);s<=n;s++)a.push(e?at(s):s);return{values:a,index:i}},_literalTemplate:function(t){var e='<div class="k-time-separator">'+(" tt"===t.pattern?":":t.pattern)+"</div>";return" tt"===t.pattern&&(e+=this._itemTemplate(["AM","PM"],t,"AM/PM",4)),e},bind:function(){var t=this,e=t.options.dates;e&&e[0]?t.dataBind(e):t.refresh()},_html:function(t){var e=this;e.options.timeView&&"scroll"===e.options.timeView.list?(t='<span class="k-time-highlight"></span>'+t,e.ul.html(t)):(e.ul[0].innerHTML=t,e.popup.unbind(h,e._heightHandler),e.popup.one(h,e._heightHandler),e.current(null),e.select(e._value))},scroll:function(t){t&&(t.scrollIntoViewIfNeeded?t.scrollIntoViewIfNeeded():function(t,e){function i(t,e){return{start:t,length:e,end:t+e}}function n(t,i){return!1===e||i.start<t.end&&t.start<i.end?Math.min(t.start,Math.max(i.start,t.end-i.length)):(t.start+t.end-i.length)/2}function a(t,e){return{x:t,y:e,translate:function(i,n){return a(t+i,e+n)}}}function s(t,e){for(;t;)e=e.translate(t.offsetLeft,t.offsetTop),t=t.offsetParent;return e}var l,o=s(t,a(0,0)),r=a(t.offsetWidth,t.offsetHeight),u=t.parentNode;for(;u instanceof HTMLElement;)l=s(u,a(u.clientLeft,u.clientTop)),u.scrollLeft=n(i(o.x-l.x,r.x),i(u.scrollLeft,u.clientWidth)),u.scrollTop=n(i(o.y-l.y,r.y),i(u.scrollTop,u.clientHeight)),o=o.translate(-u.scrollLeft,-u.scrollTop),u=u.parentNode}(t))},select:function(e){var n,a=this,s=a.options,l=a._current;e instanceof Date&&(e=i.toString(e,s.format,s.culture)),"string"==typeof e&&(e=l&&l.text()===e?l:(e=t.grep(a.ul[0].childNodes,(function(t){return(t.textContent||t.innerText)==e})))[0]?e:null),n=a._distinctSelection(e),a.current(n)},_distinctSelection:function(e){var i,n,a=this;return e&&e.length>1&&(i=G(a._value),n=t.inArray(i,a._dates),e=a.ul.children()[n]),e},setOptions:function(t){var e=this.options;t.min=s(t.min),t.max=s(t.max),this.options=A(e,t,{active:e.active,change:e.change,close:e.close,open:e.open}),this.bind()},toggle:function(){var t=this;t.popup.visible()?t.close():t.open()},value:function(t){var e=this;e._value=t,e.ul[0].firstChild&&(e.options.timeView&&"scroll"===e.options.timeView.list?e.applyValue(t):e.select(t))},_click:function(e){var i=this,n=t(e.currentTarget),a=n.text(),s=i.options.dates;s&&s.length>0&&(a=s[n.index()]),e.isDefaultPrevented()||(i.select(n),i.options.change(a,!0),i.close())},_height:function(){var t=this,e=t.list,i=e.parent(".k-animation-container"),n=t.options.height;t.ul[0].children.length&&e.add(i).show().height(t.ul[0].scrollHeight>n?n:"auto").hide()},_parse:function(t){var e=this.options,i=G(e.min)!=G(j)?e.min:null,n=G(e.max)!=G(j)?e.max:null,a=this._value||i||n||j;return t instanceof F||(t=s(t,e.parseFormats,e.culture))&&(t=new F(a.getFullYear(),a.getMonth(),a.getDate(),t.getHours(),t.getMinutes(),t.getSeconds(),t.getMilliseconds())),t},_adjustListWidth:function(){var t,e,n=this.list,a=n[0].style.width,s=this.options.anchor,l=i._outerWidth;!n.data("width")&&a||(e=(t=window.getComputedStyle?window.getComputedStyle(s[0],null):0)?parseFloat(t.width):l(s),t&&(u.mozilla||u.msie)&&(e+=parseFloat(t.paddingLeft)+parseFloat(t.paddingRight)+parseFloat(t.borderLeftWidth)+parseFloat(t.borderRightWidth)),a=e-(l(n)-n.width()),n.css({fontFamily:s.css("font-family"),width:a}).data("width",a))},_popup:function(){var t=this,e=t.list,i=t.options,n=i.anchor;this.options.omitPopup?e.appendTo(i.timeDiv):t.popup=new c.Popup(e,A(i.popup,{anchor:n,open:i.open,close:i.close,animation:i.animation,isRtl:r.isRtl(i.anchor),activate:function(){t.options.timeView&&"scroll"===t.options.timeView.list&&(t.addTranslate(),t._value?t.applyValue(t._value):t._updateCurrentlySelected(),t._updateRanges(),t._focusList(t.list.find(".k-time-list-wrapper").eq(0)))}}))},move:function(t){var e=this,i=t.keyCode,a=e.ul[0],s=e._current,l=i===n.DOWN;if(i===n.UP||l){if(t.altKey)return void e.toggle(l);(s=l?s?s[0].nextSibling:a.firstChild:s?s[0].previousSibling:a.lastChild)&&e.select(s),e.options.change(e._current.text()),t.preventDefault()}else i!==n.ENTER&&i!==n.TAB&&i!==n.ESC||(t.preventDefault(),s&&e.options.change(s.text(),!0),e.close())}},K.getMilliseconds=G,i.TimeView=K;var U=d.extend({init:function(e,n){var a,l,o=this;(n=n||{}).componentType=n.componentType||"classic",d.fn.init.call(o,e,n),e=o.element,(n=o.options).min=s(e.attr("min"))||s(n.min),n.max=s(e.attr("max"))||s(n.max),+n.max==+j&&+n.min==+j||(this._specifiedRange=!0),X(n),o._initialOptions=A({},n),o._wrapper(),o.options.timeView&&"scroll"===o.options.timeView.list&&(o.options.height=null),o.timeView=l=new K(A({},n,{id:e.attr(I),anchor:o.wrapper,format:n.format,change:function(t,i){i?o._change(t):e.val(t)},open:function(t){o.options.timeView&&"scroll"!==o.options.timeView.list?o.timeView._adjustListWidth():o.timeView._updateTitle(),o.trigger(h)?t.preventDefault():(e.attr(V,!0),a.attr(C,!1),l.current()&&e.attr(P,l._optionID))},close:function(t){o.trigger(p)?t.preventDefault():(e.attr(V,!1),a.attr(C,!0),e[0].removeAttribute(P))},active:function(t){e&&e.length&&e[0].removeAttribute(P),t&&e.attr(P,l._optionID)},specifiedRange:o._specifiedRange,maxSet:+n.max!=+j})),a=l.ul,o._ariaLabel(a),o._icon(),o._reset();try{e[0].setAttribute("type","text")}catch(t){e[0].type="text"}if(e.addClass("k-input-inner").attr({role:"combobox","aria-expanded":!1,"aria-controls":l._timeViewID,autocomplete:"off"}),e.is("[disabled]")||t(o.element).parents("fieldset").is(":disabled")?o.enable(!1):o.readonly(e.is("[readonly]")),n.dateInput){var r=n.min,u=n.max,m=new F;G(r)==G(u)&&(r=new F(m.getFullYear(),m.getMonth(),m.getDate(),0,0,0),u=new F(m.getFullYear(),m.getMonth(),m.getDate(),24,0,0)),o._dateInput=new c.DateInput(e,{culture:n.culture,format:n.format,min:r,max:u,value:n.value,interval:n.interval,size:n.size,fillMode:n.fillMode,rounded:n.rounded})}o._old=o._update(n.value||o.element.val()),o._oldText=e.val(),o._applyCssClasses(),n.label&&o._label(),i.notify(o)},options:{name:"TimePicker",min:j,max:j,format:"",dates:[],parseFormats:[],value:null,interval:30,height:200,animation:{},dateInput:!1,messages:{set:"Set",cancel:"Cancel",hour:"hour",minute:"minute",second:"second",millisecond:"millisecond",now:"Now"},componentType:"classic",size:"medium",fillMode:"solid",rounded:"medium",label:null},events:[h,p,m],componentTypes:{classic:{timeView:{list:"list"}},modern:{timeView:{list:"scroll"}}},setOptions:function(t){var e=this,n=e._value;d.fn.setOptions.call(e,t),+(t=e.options).max==+j&&+t.min==+j||(this._specifiedRange=!0),e._arrow.off(f),e._arrow.remove(),X(t),e.timeView.setOptions(t),e._icon(),e._editable(t),n&&e.element.val(i.toString(n,t.format,t.culture)),t.label&&e._inputLabel?e.label.setOptions(t.label):!1===t.label?(e.label._unwrapFloating(),e._inputLabel.remove(),delete e._inputLabel):t.label&&e._label()},dataBind:function(t){R(t)&&this.timeView.dataBind(t)},_editable:function(t){var e=this,i=t.disable,n=t.readonly,a=e._arrow.off(f),s=e.element.off(f),l=e.wrapper.off(f);e._dateInput&&e._dateInput._unbindInput(),n||i?(l.addClass(i?M:"").removeClass(i?"":M),s.attr(g,i).attr(v,n).attr(D,i).attr(H,n)):(l.removeClass(M).on("mouseenter.kendoTimePicker mouseleave.kendoTimePicker",e._toggleHover),s&&s.length&&(s[0].removeAttribute(g),s[0].removeAttribute(v)),s.attr(D,!1).attr(H,!1).on("keydown"+f,e._keydown.bind(e)).on("focusout"+f,e._blur.bind(e)).on("focus"+f,(function(){e.wrapper.addClass(k)})),e._dateInput&&e._dateInput._bindInput(),a.on(_,e._click.bind(e)).on(y,Z))},_label:function(){var e=this,n=e.options,a=t.isPlainObject(n.label)?n.label:{content:n.label};e.label=new i.ui.Label(null,t.extend({},a,{widget:e})),e._inputLabel=e.label.element},readonly:function(t){this._editable({readonly:t===e||t,disable:!1}),this.label&&this.label.floatingLabel&&this.label.floatingLabel.readonly(t===e||t)},enable:function(t){this._editable({readonly:!1,disable:!(t=t===e||t)}),this.label&&this.label.floatingLabel&&this.label.floatingLabel.enable(t=t===e||t)},destroy:function(){var t=this;d.fn.destroy.call(t),t.timeView.destroy(),t.element.off(f),t._arrow.off(f),t.wrapper.off(f),t._form&&t._form.off("reset",t._resetHandler),t.label&&t.label.destroy()},close:function(){this.timeView.close()},open:function(){this.timeView.open()},min:function(t){return t&&(this._specifiedRange=!0),this._option("min",t)},max:function(t){return t&&this.timeView?(this._specifiedRange=!0,this.timeView.options.maxSet=!0):this.timeView&&(this.timeView.options.maxSet=!1),this._option("max",t)},value:function(t){var i=this;if(t===e)return i._value;i._old=i._update(t),null===i._old&&i.element.val(""),i._oldText=i.element.val(),i.label&&i.label.floatingLabel&&i.label.floatingLabel.refresh()},_blur:function(){var t=this,e=t.element.val();t.options.timeView&&"scroll"===t.options.timeView.list||t.close(),e!==t._oldText&&t._change(e),t.wrapper.removeClass(k)},_click:function(){var t=this.element;this.timeView.toggle(),r.touch||t[0]===l()||t.trigger("focus")},_change:function(t){var e,n=this,a=n.element.val();t=n._update(t);var s=(e=!i.calendar.isEqualDate(n._old,t))&&!n._typing,l=a!==n.element.val();(s||l)&&n.element.trigger(m),e&&(n._old=t,n._oldText=n.element.val(),n.trigger(m)),n._typing=!1},_icon:function(){var e,i=this,n=i.element,s=i.options;(e=n.next("button.k-input-button"))[0]||(e=t(a.renderButton('<button unselectable="on" tabindex="-1" class="k-input-button" aria-label="select"></button>',{icon:"clock",size:s.size,fillMode:s.fillMode,shape:"none",rounded:"none"})).insertAfter(n)),i._arrow=e.attr({role:"button"})},_keydown:function(t){var e=this,i=t.keyCode,a=e.timeView,s=e.element.val();a.popup.visible()||t.altKey?(a.move(t),e._dateInput&&t.stopImmediatePropagation&&t.stopImmediatePropagation()):i===n.ENTER&&s!==e._oldText?e._change(s):e._typing=!0},_option:function(t,i){var n=this,a=n.options;if(i===e)return a[t];(i=n.timeView._parse(i))&&(i=new F(+i),a[t]=i,n.timeView.options[t]=i,n.timeView.bind())},_toggleHover:function(e){t(e.currentTarget).toggleClass(w,"mouseenter"===e.type)},_update:function(t){var e=this,n=e.options,a=e.timeView,s=a._parse(t);return Q(s,n.min,n.max)||(s=null),e._value=s,e._currentlySelected=s,e._dateInput&&s?e._dateInput.value(s||t):e.element.val(i.toString(s||t,n.format,n.culture)),a.value(s),s},_wrapper:function(){var t,e=this.element;(t=e.parents(".k-timepicker"))[0]||(t=e.wrap("<span></span>").parent()),t[0].style.cssText=e[0].style.cssText,this.wrapper=t.addClass("k-timepicker k-input").addClass(e[0].className),e.css({height:e[0].style.height})},_reset:function(){var e=this,i=e.element,n=i.attr("form"),a=n?t("#"+n):i.closest("form");a[0]&&(e._resetHandler=function(){e.value(i[0].defaultValue),e.max(e._initialOptions.max),e.min(e._initialOptions.min)},e._form=a.on("reset",e._resetHandler))}});function X(t){var e=t.parseFormats;t.format=o(t.format||i.getCulture(t.culture).calendars.standard.patterns.t),(e=R(e)?e:[e]).splice(0,0,t.format),t.parseFormats=e}function Z(t){t.preventDefault()}function J(t,e){return new Date(t.getFullYear(),t.getMonth(),t.getDate(),e.getHours(),e.getMinutes(),e.getSeconds(),e.getMilliseconds())}function $(t,e){var i=t[t.length-1];i&&"LITERAL"===i.type?i.pattern+=e:t.push({type:"literal",pattern:e})}function tt(t){return"h"===t||"K"===t}function et(t){var e;return t<=3?e="abbreviated":4===t?e="wide":5===t?e="narrow":6===t&&(e="short"),e}function it(t,e,i){return i=i||0,t.indexOf(e,i)===i}function nt(t,e){var i=!1;return-1!==t.indexOf(e)&&(i=!0),i}function at(t,e){for(var i=String(t);i.length<(e||2);)i="0"+i;return i}function st(t){return t.length&&t[0].getBoundingClientRect().height}i.cssProperties.registerPrefix("TimePicker","k-input-"),i.cssProperties.registerValues("TimePicker",[{prop:"rounded",values:i.cssProperties.roundedValues.concat([["full","full"]])}]),c.plugin(U)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.timepicker.js.map
