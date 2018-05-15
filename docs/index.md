Siddhi Execution Extension - Map
======================================

The **siddhi-execution-map extension** is an extension to <a target="_blank" href="https://wso2.github.io/siddhi">Siddhi</a> that provides the capability to send a map object inside Siddhi stream definitions and use it inside queries. The following are the functions of the map extension..

Find some useful links below:

* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map">Source code</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/releases">Releases</a>
* <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/issues">Issue tracker</a>

## Latest API Docs 

Latest API Docs is <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11">4.0.11</a>.

## How to use 

**Using the extension in <a target="_blank" href="https://github.com/wso2/product-sp">WSO2 Stream Processor</a>**

* You can use this extension in the latest <a target="_blank" href="https://github.com/wso2/product-sp/releases">WSO2 Stream Processor</a> that is a part of <a target="_blank" href="http://wso2.com/analytics?utm_source=gitanalytics&utm_campaign=gitanalytics_Jul17">WSO2 Analytics</a> offering, with editor, debugger and simulation support. 

* This extension is shipped by default with WSO2 Stream Processor, if you wish to use an alternative version of this extension you can replace the component <a target="_blank" href="https://github.com/wso2-extensions/siddhi-execution-map/releases">jar</a> that can be found in the `<STREAM_PROCESSOR_HOME>/lib` 
directory.

**Using the extension as a <a target="_blank" href="https://wso2.github.io/siddhi/documentation/running-as-a-java-library">java library</a>**

* This extension can be added as a maven dependency along with other Siddhi dependencies to your project.

```
     <dependency>
        <groupId>org.wso2.extension.siddhi.execution.map</groupId>
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

* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#create-function">create</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the created map object.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#createfromjson-function">createFromJSON</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the map created with the key values pairs given in the JSONstring.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#createfromxml-function">createFromXML</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the map created with the key values pairs given in the XMLstring.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#get-function">get</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the value object from the map that is related to the given key.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#ismap-function">isMap</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns true if the object is a map or false otherwise.t</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#put-function">put</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the updated map after adding the given key-value pair</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#remove-function">remove</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the updated map after removing the element with key.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#tojson-function">toJSON</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Converts a map into a JSON object and returns the definition of that JSON object as a string.</p></div>
* <a target="_blank" href="https://wso2-extensions.github.io/siddhi-execution-map/api/4.0.11/#toxml-function">toXML</a> *(<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#function">(Function)</a>)*<br><div style="padding-left: 1em;"><p>Returns the map as an XML string.</p></div>

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
