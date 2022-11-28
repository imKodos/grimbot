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
import"./kendo.toggleinputbase.js";import"./kendo.html.input.js";var __meta__={id:"checkbox",name:"CheckBox",category:"web",description:"The CheckBox widget is used to display boolean value input.",depends:["toggleinputbase","html.input"]};!function(e,o){var n=window.kendo,t=n.ui,i=t.ToggleInputBase.extend({options:{name:"CheckBox",checked:null,enabled:!0,encoded:!0,label:null,rounded:"medium",size:"medium"},RENDER_INPUT:n.html.renderCheckBox,NS:".kendoCheckBox",value:function(e){return"string"==typeof e&&(e="true"===e),this.check.apply(this,[e])}});n.cssProperties.registerPrefix("CheckBox","k-checkbox-"),n.cssProperties.registerValues("CheckBox",[{prop:"rounded",values:n.cssProperties.roundedValues.concat([["full","full"]])}]),t.plugin(i)}(window.kendo.jQuery);
//# sourceMappingURL=kendo.checkbox.js.map
