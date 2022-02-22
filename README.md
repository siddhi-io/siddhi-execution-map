Siddhi Execution Map
======================================

  [![Jenkins Build Status](https://wso2.org/jenkins/job/siddhi/job/siddhi-execution-map/badge/icon)](https://wso2.org/jenkins/job/siddhi/job/siddhi-execution-map/)
  [![GitHub Release](https://img.shields.io/github/release/siddhi-io/siddhi-execution-map.svg)](https://github.com/siddhi-io/siddhi-execution-map/releases)
  [![GitHub Release Date](https://img.shields.io/github/release-date/siddhi-io/siddhi-execution-map.svg)](https://github.com/siddhi-io/siddhi-execution-map/releases)
  [![GitHub Open Issues](https://img.shields.io/github/issues-raw/siddhi-io/siddhi-execution-map.svg)](https://github.com/siddhi-io/siddhi-execution-map/issues)
  [![GitHub Last Commit](https://img.shields.io/github/last-commit/siddhi-io/siddhi-execution-map.svg)](https://github.com/siddhi-io/siddhi-execution-map/commits/master)
  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

The **siddhi-execution-map extension** is a <a target="_blank" href="https://siddhi.io/">Siddhi</a> extension that provides capability to generate and manipulate map (key-value) data objects.

For information on <a target="_blank" href="https://siddhi.io/">Siddhi</a> and it's features refer <a target="_blank" href="https://siddhi.io/redirect/docs.html">Siddhi Documentation</a>. 

## Download

* Versions 5.x and above with group id `io.siddhi.extension.*` from <a target="_blank" href="https://mvnrepository.com/artifact/io.siddhi.extension.execution.map/siddhi-execution-map/">here</a>.
* Versions 4.x and lower with group id `org.wso2.extension.siddhi.*` from <a target="_blank" href="https://mvnrepository.com/artifact/org.wso2.extension.siddhi.execution.map/siddhi-execution-map">here</a>.

## Latest API Docs 

Latest API Docs is <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7">5.0.7</a>.

## Features

* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#collect-aggregate-function">collect</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#aggregate-function">Aggregate Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Collect multiple key-value pairs to construct a map. Only distinct keys are collected, if a duplicate key arrives, it overrides the old value</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#merge-aggregate-function">merge</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#aggregate-function">Aggregate Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Collect multiple maps to merge as a single map. Only distinct keys are collected, if a duplicate key arrives, it overrides the old value.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#clear-function">clear</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the cleared map. </p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#clone-function">clone</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the cloned map.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#combinebykey-function">combineByKey</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the map after combining all the maps given as parameters, such that the keys, of all the maps will be matched with an Array list of values from each map respectively.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#containskey-function">containsKey</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function checks if the map contains the key.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#containsvalue-function">containsValue</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function checks if the map contains the value.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#create-function">create</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function creates a map pairing the keys and their corresponding values.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#createfromjson-function">createFromJSON</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the map created by pairing the keys with their corresponding values given in the JSON string.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#createfromxml-function">createFromXML</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the map created by pairing the keys with their corresponding values,given as an XML string.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#get-function">get</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the value corresponding to the given key from the map.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#isempty-function">isEmpty</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function checks if the map is empty.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#ismap-function">isMap</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function checks if the object is type of a map.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#keys-function">keys</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function to return the keys of the map as a list.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#put-function">put</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the updated map after adding the given key-value pair. If the key already exist in the map the key is updated with the new value.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#putall-function">putAll</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the updated map after adding all the key-value pairs from another map. If there are duplicate keys, the key will be assigned new values from the map that's being copied.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#putifabsent-function">putIfAbsent</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the updated map after adding the given key-value pair if key is absent. </p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#remove-function">remove</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the updated map after removing the element with the specified key.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#replace-function">replace</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the updated map after replacing the given key-value pair only if key is present.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#replaceall-function">replaceAll</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the updated map after replacing all the key-value pairs from another map, if keys are present.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#size-function">size</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function to return the size of the map.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#tojson-function">toJSON</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function converts a map into a JSON object and returns the JSON as a string.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#toxml-function">toXML</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function returns the map as an XML string.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#values-function">values</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Function to return the values of the map.</p></p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.7/#tokenize-stream-processor">tokenize</a> *(<a target="_blank" href="http://siddhi.io/en/v5.1/docs/query-guide/#stream-processor">Stream Processor</a>)*<br> <div style="padding-left: 1em;"><p><p style="word-wrap: break-word;margin: 0;">Tokenize the map and return each key, value as new attributes in events</p></p></div>

## Dependencies 

There are no other dependencies needed for this extension. 

## Installation

For installing this extension on various siddhi execution environments refer Siddhi documentation section on <a target="_blank" href="https://siddhi.io/redirect/add-extensions.html">adding extensions</a>.

## Support and Contribution

* We encourage users to ask questions and get support via <a target="_blank" href="https://stackoverflow.com/questions/tagged/siddhi">StackOverflow</a>, make sure to add the `siddhi` tag to the issue for better response.

* If you find any issues related to the extension please report them on <a target="_blank" href="https://github.com/siddhi-io/siddhi-execution-map/issues">the issue tracker</a>.

* For production support and other contribution related information refer <a target="_blank" href="https://siddhi.io/community/">Siddhi Community</a> documentation.
