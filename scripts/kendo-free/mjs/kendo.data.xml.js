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
import"./kendo.core.js";var __meta__={id:"data.xml",name:"XML",category:"framework",depends:["core"],hidden:!0};!function(e,t){var r=window.kendo,n=Array.isArray,i=e.isPlainObject,a=e.map,o=e.each,f=e.extend,d=r.getter,l=r.Class.extend({init:function(e){var t=this,d=e.total,l=e.model,s=e.parse,u=e.errors,p=e.serialize,c=e.data;if(l){if(i(l)){var m=e.modelBase||r.data.Model;l.fields&&o(l.fields,(function(e,n){i(n)&&n.field?r.isFunction(n.field)||(n=f(n,{field:t.getter(n.field)})):n={field:t.getter(n)},l.fields[e]=n}));var h=l.id;if(h){var x={};x[t.xpathToMember(h,!0)]={field:t.getter(h)},l.fields=f(x,l.fields),l.id=t.xpathToMember(h)}l=m.define(l)}t.model=l}if(d&&("string"==typeof d?(d=t.getter(d),t.total=function(e){return parseInt(d(e),10)}):"function"==typeof d&&(t.total=d)),u&&("string"==typeof u?(u=t.getter(u),t.errors=function(e){return u(e)||null}):"function"==typeof u&&(t.errors=u)),c&&("string"==typeof c?(c=t.xpathToMember(c),t.data=function(e){var r,i=t.evaluate(e,c);return i=n(i)?i:[i],t.model&&l.fields?(r=new t.model,a(i,(function(e){if(e){var t,n={};for(t in l.fields)n[t]=r._parse(t,l.fields[t].field(e));return n}}))):i}):"function"==typeof c&&(t.data=c)),"function"==typeof s){var g=t.parse;t.parse=function(e){var r=s.call(t,e);return g.call(t,r)}}"function"==typeof p&&(t.serialize=p)},total:function(e){return this.data(e).length},errors:function(e){return e?e.errors:null},serialize:function(e){return e},parseDOM:function(e){var t,r,i,a,o,f,d,l={},s=e.attributes,u=s.length;for(d=0;d<u;d++)l["@"+(f=s[d]).nodeName]=f.nodeValue;for(r=e.firstChild;r;r=r.nextSibling)3===(i=r.nodeType)||4===i?l["#text"]=r.nodeValue:1===i&&(t=this.parseDOM(r),o=l[a=r.nodeName],n(o)?o.push(t):o=undefined!==o?[o,t]:t,l[a]=o);return l},evaluate:function(e,t){for(var r,i,a,o,f,d=t.split(".");r=d.shift();)if(e=e[r],n(e)){for(i=[],t=d.join("."),f=0,a=e.length;f<a;f++)o=this.evaluate(e[f],t),o=n(o)?o:[o],i.push.apply(i,o);return i}return e},parse:function(t){var r,n,i={};return r=t.documentElement||e.parseXML(t).documentElement,n=this.parseDOM(r),i[r.nodeName]=n,i},xpathToMember:function(e,t){return e?(e=e.replace(/^\//,"").replace(/\//g,".")).indexOf("@")>=0?e.replace(/\.?(@.*)/,t?"$1":'["$1"]'):e.indexOf("text()")>=0?e.replace(/(\.?text\(\))/,t?"#text":'["#text"]'):e:""},getter:function(e){return d(this.xpathToMember(e),!0)}});e.extend(!0,r.data,{XmlDataReader:l,readers:{xml:l}})}(window.kendo.jQuery);
//# sourceMappingURL=kendo.data.xml.js.map
