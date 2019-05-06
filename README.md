Siddhi Execution Extension - Map
======================================

The **siddhi-execution-map extension** is an extension to <a target="_blank" href="https://wso2.github.io/siddhi">Siddhi</a> that provides the capability to send a map object inside Siddhi stream definitions and use it inside queries. The following are the functions of the map extension..

Find some useful links below:

* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map">Source code</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/releases">Releases</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/issues">Issue tracker</a>

## Latest API Docs 

Latest API Docs is <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0">5.0.0</a>.

## How to use 

**Using the extension in <a target="_blank" href="https://github.com/wso2/product-sp">WSO2 Stream Processor</a>**

* You can use this extension in the latest <a target="_blank" href="https://github.com/wso2/product-sp/releases">WSO2 Stream Processor</a> that is a part of <a target="_blank" href="http://wso2.com/analytics?utm_source=gitanalytics&utm_campaign=gitanalytics_Jul17">WSO2 Analytics</a> offering, with editor, debugger and simulation support. 

* This extension is shipped by default with WSO2 Stream Processor, if you wish to use an alternative version of this extension you can replace the component <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/releases">jar</a> that can be found in the `<STREAM_PROCESSOR_HOME>/lib` 
directory.

**Using the extension as a <a target="_blank" href="https://wso2.github.io/siddhi/documentation/running-as-a-java-library">java library</a>**

* This extension can be added as a maven dependency along with other Siddhi dependencies to your project.

```
     <dependency>
        <groupId>io.siddhi.extension.execution.map</groupId>
        <artifactId>siddhi-execution-map</artifactId>
        <version>x.x.x</version>
     </dependency>
```

## Jenkins Build Status

---

|  Branch | Build Status |
| :------ |:------------ | 
| master  | [![Build Status](https://wso2.org/jenkins/view/All%20Builds/job/siddhi/job/siddhi-execution-map/badge/icon)](https://wso2.org/jenkins/view/All%20Builds/job/siddhi/job/siddhi-execution-map/) |

---

## Features

* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#create-function">create</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This creates a map between the keys and their corresponding values.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#createfromjson-function">createFromJSON</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the map created by pairing the keys with its corresponding values given in the JSON string.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#createfromxml-function">createFromXML</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the map created by pairing the keys with their corresponding values,given as an XML string.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#get-function">get</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the value object, that corresponds to the given key, from the map. </p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#ismap-function">isMap</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns 'true' if the object is a map and 'false' if otherwise.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#put-function">put</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the updated map after adding the given key-value pair.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#putall-function">putAll</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the updated 'to.map' map after copying all of the mappings from the specified 'from.map.' map. If there are duplicate keys, 'from.map' overwrites the values into the 'to.map.' map.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#remove-function">remove</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the updated map after removing the element with the key specified.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#tojson-function">toJSON</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This converts a map into a JSON object and returns the definition of that JSON object as a string.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/5.0.0/#toxml-function">toXML</a> *<a target="_blank" href="http://siddhi.io/documentation/siddhi-5.x/query-guide-5.x/#function">(Function)</a>*<br><div style="padding-left: 1em;"><p>This returns the map as an XML string.</p></div>

## How to Contribute
 
  * Please report issues at <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/issues">GitHub Issue Tracker</a>.
  
  * Send your contributions as pull requests to <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/tree/master">master branch</a>. 
 
## Contact us 

 * Post your questions with the <a target="_blank" href="http://stackoverflow.com/search?q=siddhi">"Siddhi"</a> tag in <a target="_blank" href="http://stackoverflow.com/search?q=siddhi">Stackoverflow</a>. 
 
 * Siddhi developers can be contacted via the mailing lists:
 
    Developers List   : [dev@wso2.org](mailto:dev@wso2.org)
    
    Architecture List : [architecture@wso2.org](mailto:architecture@wso2.org)
 
## Support 

* We are committed to ensuring support for this extension in production. Our unique approach ensures that all support leverages our open development methodology and is provided by the very same engineers who build the technology. 

* For more details and to take advantage of this unique opportunity contact us via <a target="_blank" href="http://wso2.com/support?utm_source=gitanalytics&utm_campaign=gitanalytics_Jul17">http://wso2.com/support/</a>. 
