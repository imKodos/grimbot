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
(function (factory) {
    typeof define === 'function' && define.amd ? define(['kendo.core'], factory) :
    factory();
})((function () {
    (function( window, undefined$1 ) {
        kendo.cultures["pt-ST"] = {
            name: "pt-ST",
            numberFormat: {
                pattern: ["-n"],
                decimals: 2,
                ",": " ",
                ".": ",",
                groupSize: [3],
                percent: {
                    pattern: ["-n%","n%"],
                    decimals: 2,
                    ",": " ",
                    ".": ",",
                    groupSize: [3],
                    symbol: "%"
                },
                currency: {
                    name: "São Tomé and Príncipe Dobra",
                    abbr: "STN",
                    pattern: ["-n $","n $"],
                    decimals: 2,
                    ",": " ",
                    ".": ",",
                    groupSize: [3],
                    symbol: "Db"
                }
            },
            calendars: {
                standard: {
                    days: {
                        names: ["domingo","segunda-feira","terça-feira","quarta-feira","quinta-feira","sexta-feira","sábado"],
                        namesAbbr: ["domingo","segunda","terça","quarta","quinta","sexta","sábado"],
                        namesShort: ["dom","seg","ter","qua","qui","sex","sáb"]
                    },
                    months: {
                        names: ["janeiro","fevereiro","março","abril","maio","junho","julho","agosto","setembro","outubro","novembro","dezembro"],
                        namesAbbr: ["jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"]
                    },
                    AM: ["a.m.","a.m.","A.M."],
                    PM: ["p.m.","p.m.","P.M."],
                    patterns: {
                        d: "dd/MM/yyyy",
                        D: "dddd, d 'de' MMMM 'de' yyyy",
                        F: "dddd, d 'de' MMMM 'de' yyyy HH:mm:ss",
                        g: "dd/MM/yyyy HH:mm",
                        G: "dd/MM/yyyy HH:mm:ss",
                        m: "d 'de' MMMM",
                        M: "d 'de' MMMM",
                        s: "yyyy'-'MM'-'dd'T'HH':'mm':'ss",
                        t: "HH:mm",
                        T: "HH:mm:ss",
                        u: "yyyy'-'MM'-'dd HH':'mm':'ss'Z'",
                        y: "MMMM 'de' yyyy",
                        Y: "MMMM 'de' yyyy"
                    },
                    "/": "/",
                    ":": ":",
                    firstDay: 1
                }
            }
        };
    })();

}));
