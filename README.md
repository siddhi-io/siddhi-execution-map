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

Latest API Docs is <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3">5.0.3</a>.

## Features

* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#create-function">create</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function creates a map pairing the keys and their corresponding values.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#createfromjson-function">createFromJSON</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the map created by pairing the keys with their corresponding values given in the JSON string.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#createfromxml-function">createFromXML</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the map created by pairing the keys with their corresponding values,given as an XML string.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#get-function">get</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the value corresponding to the given key from the map.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#ismap-function">isMap</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function checks if the object is type of a map.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#put-function">put</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the updated map after adding the given key-value pair. If the key already exist in the map the key is updated with the new value.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#putall-function">putAll</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the updated map after adding all the key-value pairs from another map. If there are duplicate keys, the key will be assigned new values from the map that's being copied.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#remove-function">remove</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the updated map after removing the element with the specified key.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#tojson-function">toJSON</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function converts a map into a JSON object and returns the JSON as a string.</p></div>
* <a target="_blank" href="https://siddhi-io.github.io/siddhi-execution-map/api/5.0.3/#toxml-function">toXML</a> *(<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/#function">Function</a>)*<br> <div style="padding-left: 1em;"><p>Function returns the map as an XML string.</p></div>

## Dependencies 

There are no other dependencies needed for this extension. 

## Installation

For installing this extension on various siddhi execution environments refer Siddhi documentation section on <a target="_blank" href="https://siddhi.io/redirect/add-extensions.html">adding extensions</a>.

## Support and Contribution

* We encourage users to ask questions and get support via <a target="_blank" href="https://stackoverflow.com/questions/tagged/siddhi">StackOverflow</a>, make sure to add the `siddhi` tag to the issue for better response.

* If you find any issues related to the extension please report them on <a target="_blank" href="https://github.com/siddhi-io/siddhi-execution-map/issues">the issue tracker</a>.

* For production support and other contribution related information refer <a target="_blank" href="https://siddhi.io/community/">Siddhi Community</a> documentation.
