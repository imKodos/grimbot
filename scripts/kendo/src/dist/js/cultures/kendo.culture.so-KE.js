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
        kendo.cultures["so-KE"] = {
            name: "so-KE",
            numberFormat: {
                pattern: ["-n"],
                decimals: 2,
                ",": ",",
                ".": ".",
                groupSize: [3],
                percent: {
                    pattern: ["-n%","n%"],
                    decimals: 2,
                    ",": ",",
                    ".": ".",
                    groupSize: [3],
                    symbol: "%"
                },
                currency: {
                    name: "Kenyan Shilling",
                    abbr: "KES",
                    pattern: ["-$n","$n"],
                    decimals: 2,
                    ",": ",",
                    ".": ".",
                    groupSize: [3],
                    symbol: "Ksh"
                }
            },
            calendars: {
                standard: {
                    days: {
                        names: ["Axad","Isniin","Talaado","Arbaco","Khamiis","Jimco","Sabti"],
                        namesAbbr: ["Axd","Isn","Tal","Arb","Kha","Jim","Sab"],
                        namesShort: ["Axd","Isn","Tal","Arb","Kha","Jim","Sab"]
                    },
                    months: {
                        names: ["Jannaayo","Febraayo","Maarso","Abriil","May","Juun","Luuliyo","Ogost","Sebtembar","Oktoobar","Nofembar","Desembar"],
                        namesAbbr: ["Jan","Feb","Mar","Abr","May","Juun","Luuliyo","Og","Seb","Okt","Nof","Des"]
                    },
                    AM: ["sn.","sn.","SN."],
                    PM: ["gn.","gn.","GN."],
                    patterns: {
                        d: "dd/MM/yyyy",
                        D: "dddd, MMMM dd, yyyy",
                        F: "dddd, MMMM dd, yyyy HH:mm:ss",
                        g: "dd/MM/yyyy HH:mm",
                        G: "dd/MM/yyyy HH:mm:ss",
                        m: "MMMM d",
                        M: "MMMM d",
                        s: "yyyy'-'MM'-'dd'T'HH':'mm':'ss",
                        t: "HH:mm",
                        T: "HH:mm:ss",
                        u: "yyyy'-'MM'-'dd HH':'mm':'ss'Z'",
                        y: "MMMM yyyy",
                        Y: "MMMM yyyy"
                    },
                    "/": "/",
                    ":": ":",
                    firstDay: 0
                }
            }
        };
    })();

}));
