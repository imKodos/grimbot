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
        kendo.cultures["se"] = {
            name: "se",
            numberFormat: {
                pattern: ["-n"],
                decimals: 2,
                ",": " ",
                ".": ",",
                groupSize: [3],
                percent: {
                    pattern: ["-n %","n %"],
                    decimals: 2,
                    ",": " ",
                    ".": ",",
                    groupSize: [3],
                    symbol: "%"
                },
                currency: {
                    name: "",
                    abbr: "",
                    pattern: ["-n $","n $"],
                    decimals: 2,
                    ",": " ",
                    ".": ",",
                    groupSize: [3],
                    symbol: "kr"
                }
            },
            calendars: {
                standard: {
                    days: {
                        names: ["sotnabeaivi","vuossárga","maŋŋebárga","gaskavahkku","duorasdat","bearjadat","lávvardat"],
                        namesAbbr: ["sotn","vuos","maŋ","gask","duor","bear","láv"],
                        namesShort: ["sotn","vuos","maŋ","gask","duor","bear","láv"]
                    },
                    months: {
                        names: ["ođđajagemánnu","guovvamánnu","njukčamánnu","cuoŋománnu","miessemánnu","geassemánnu","suoidnemánnu","borgemánnu","čakčamánnu","golggotmánnu","skábmamánnu","juovlamánnu"],
                        namesAbbr: ["ođđj","guov","njuk","cuo","mies","geas","suoi","borg","čakč","golg","skáb","juov"]
                    },
                    AM: ["i.b.","i.b.","I.B."],
                    PM: ["e.b.","e.b.","E.B."],
                    patterns: {
                        d: "yyyy-MM-dd",
                        D: "yyyy MMMM d, dddd",
                        F: "yyyy MMMM d, dddd HH:mm:ss",
                        g: "yyyy-MM-dd HH:mm",
                        G: "yyyy-MM-dd HH:mm:ss",
                        m: "MMMM d",
                        M: "MMMM d",
                        s: "yyyy'-'MM'-'dd'T'HH':'mm':'ss",
                        t: "HH:mm",
                        T: "HH:mm:ss",
                        u: "yyyy'-'MM'-'dd HH':'mm':'ss'Z'",
                        y: "yyyy MMMM",
                        Y: "yyyy MMMM"
                    },
                    "/": "-",
                    ":": ":",
                    firstDay: 1
                }
            }
        };
    })();

}));
