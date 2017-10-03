/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.execution.map;

import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.exception.SiddhiAppRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * createFromJSON(String)
 * Returns the created map
 * Accept Type(s): (String)
 * Return Type(s): Map
 */
@Extension(
        name = "createFromJSON",
        namespace = "map",
        description = "Returns the map created with the key values pairs given in the JSONstring.",
        parameters = {
                @Parameter(name = "json.string",
                        description = "JSON as a string, which is used to create the map.",
                        type = DataType.STRING,
                        optional = false
                )

        },

        examples = @Example(
                description = "returns a map with the keys \"symbol\", \"price\", \"volume\", " +
                        "and with the values \"IBM\", 200 and 100 respectively.",
                syntax = "createFromJSON(“{‘symbol' : 'IBM' , 'price' : 200, 'volume' : 100}”)"),
        returnAttributes = @ReturnAttribute(description = "Will return a map", type = DataType.OBJECT)

)
public class CreateFromJSONFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if ((attributeExpressionExecutors.length) != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:create() function, " +
                    "required only 1, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        return null;
    }

    @Override
    protected Object execute(Object data) {
        if (data instanceof String) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data.toString());
            } catch (JSONException e) {
                throw new SiddhiAppRuntimeException(
                        "Cannot create JSON from '" + data.toString() + "' in create from json function", e);
            }
            return getMapFromJson(map, jsonObject);
        } else {
            throw new SiddhiAppRuntimeException("Data should be a string");
        }
    }

    private Map<Object, Object> getMapFromJson(Map<Object, Object> map, JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            Object value = null;
            try {
                value = jsonObject.get(key);
            } catch (JSONException e) {
                throw new SiddhiAppRuntimeException(
                        "JSON '" + jsonObject + "'does not contain key '" + key + "' in create from json function", e);
            }
            if (value instanceof JSONObject) {
                value = getMapFromJson(new HashMap<Object, Object>(), (JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

    @Override
    public Map<String, Object> currentState() {
        return null;    //No need to maintain a state.
    }

    @Override
    public void restoreState(Map<String, Object> map) {

    }

}


