# API Docs - v5.0.2

!!! Info "Tested Siddhi Core version: *<a target="_blank" href="http://siddhi.io/en/v5.0/docs/query-guide/">5.0.0</a>*"
    It could also support other Siddhi Core minor versions.

## Map

### create *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This creates a map between the keys and their corresponding values.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:create(<OBJECT> key1, <OBJECT> value1, <OBJECT> key2, <OBJECT> value2)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">key1</td>
        <td style="vertical-align: top; word-wrap: break-word">key 1</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">value1</td>
        <td style="vertical-align: top; word-wrap: break-word">Value 1</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">key2</td>
        <td style="vertical-align: top; word-wrap: break-word">Key 2</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">value2</td>
        <td style="vertical-align: top; word-wrap: break-word">Value 2</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
create(1 , ?one? ,  2 , ?two? , 3 , ?three?)
```
<p style="word-wrap: break-word">This returns a map with keys 1, 2, 3 mapped with their corresponding values, "one", "two", "three".</p>

### createFromJSON *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the map created by pairing the keys with its corresponding values given in the JSON string.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:createFromJSON(<STRING> json.string)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">json.string</td>
        <td style="vertical-align: top; word-wrap: break-word">JSON as a string, which is used to create the map.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
createFromJSON(?{?symbol' : 'IBM' , 'price' : 200, 'volume' : 100}?)
```
<p style="word-wrap: break-word">This returns a map with the keys "symbol", "price", "volume", and its values, "IBM", 200 and 100 respectively.</p>

### createFromXML *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the map created by pairing the keys with their corresponding values,given as an XML string.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:createFromXML(<STRING> xml.string)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">xml.string</td>
        <td style="vertical-align: top; word-wrap: break-word">The XML string, which is used to create the map.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
createFromJSON(?{?symbol' : 'IBM' , 'price' : 200, 'volume' : 100}?)
```
<p style="word-wrap: break-word"> returns a map with the keys "symbol", "price", "volume", and with the values "IBM", 200 and 100 respectively.</p>

### get *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the value object, that corresponds to the given key, from the map. </p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:get(<OBJECT> map, <OBJECT> key)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map from where the value should be obtained</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">key</td>
        <td style="vertical-align: top; word-wrap: break-word">The key of the value which needs to be returned</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
get(company,1)
```
<p style="word-wrap: break-word">This function returns the value that is associated with the key, i.e., 1, from a map named company.</p>

### isMap *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns 'true' if the object is a map and 'false' if otherwise.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<BOOL> map:isMap(<OBJECT> object)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">object</td>
        <td style="vertical-align: top; word-wrap: break-word">The object that the function checks to determine whether it's a map or not.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
isMap(students)
```
<p style="word-wrap: break-word">This function returns 'true' if the object, students is a map. It returns 'false' if it is not a map.</p>

### put *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the updated map after adding the given key-value pair.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:put(<OBJECT> map, <OBJECT> key, <OBJECT> value)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map to which the value should be added.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">key</td>
        <td style="vertical-align: top; word-wrap: break-word">The key of the value added.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">value</td>
        <td style="vertical-align: top; word-wrap: break-word">The new value.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
put(students , 1234 , ?sam?)
```
<p style="word-wrap: break-word">This function returns the updated map named students after adding the object "sam" with key 1234.</p>

### putAll *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the updated 'to.map' map after copying all of the mappings from the specified 'from.map.' map. If there are duplicate keys, 'from.map' overwrites the values into the 'to.map.' map.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:putAll(<OBJECT> to.map, <OBJECT> from.map)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">to.map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map into which the mappings need to copied.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">from.map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map from which the mappings are copied.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
putAll(toMap , fromMap)
```
<p style="word-wrap: break-word">This returns the updated map named 'toMap' after adding each mapping from 'fromMap.'</p>

### remove *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the updated map after removing the element with the key specified.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<OBJECT> map:remove(<OBJECT> map, <OBJECT> key)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map that needs to be updated by removing the element.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">key</td>
        <td style="vertical-align: top; word-wrap: break-word">The key of the element that needs to removed from the map.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
remove(students , 1234)
```
<p style="word-wrap: break-word">This function returns the updated map, students after removing the element with the key 1234.</p>

### toJSON *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This converts a map into a JSON object and returns the definition of that JSON object as a string.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<STRING> map:toJSON(<OBJECT> map)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map that needs to be converted to JSON</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
toJSON(company)
```
<p style="word-wrap: break-word">If "company" is a map with key-value pairs, ("symbol" : wso2),("volume" : 100), and ("price",200), it returns the string ?{?symbol? : ?wso2?, ?volume? : 100 , ?price? : 200}?.</p>

### toXML *<a target="_blank" href="https://siddhi.io/en/v5.0/docs/query-guide/#function">(Function)</a>*

<p style="word-wrap: break-word">This returns the map as an XML string.</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
<STRING> map:toXML(<OBJECT> map, <OBJECT> rootelementname)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">map</td>
        <td style="vertical-align: top; word-wrap: break-word">The map that needs to be converted to XML.</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">rootelementname</td>
        <td style="vertical-align: top; word-wrap: break-word">The root element of the map.</td>
        <td style="vertical-align: top">null</td>
        <td style="vertical-align: top">OBJECT</td>
        <td style="vertical-align: top">Yes</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
toXML(company, "abcCompany")
```
<p style="word-wrap: break-word">If "company" is a map with key-value pairs, (?symbol? : wso2), (?volume? : 100), and (?price? : 200), this function returns the string, ?&lt;abcCompany&gt;&lt;symbol&gt;wso2&lt;/symbol&gt;&lt;volume&gt;&lt;100&gt;&lt;/volume&gt;&lt;price&gt;200&lt;/price&gt;&lt;/abcCompany&gt;.</p>

