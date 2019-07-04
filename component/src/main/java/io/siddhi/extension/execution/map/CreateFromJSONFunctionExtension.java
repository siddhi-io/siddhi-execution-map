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

package io.siddhi.extension.execution.map;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ParameterOverload;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;
import org.json.JSONException;
import org.json.JSONObject;

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
        description = "Function returns the map created by pairing the keys with their corresponding values " +
                "given in the JSON string.",
        parameters = {
                @Parameter(name = "json.string",
                        description = "JSON as a string, which is used to create the map.",
                        type = DataType.STRING,
                        dynamic = true,
                        optional = false
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"json.string"})
        },
        returnAttributes = @ReturnAttribute(description = "Will return a map.", type = DataType.OBJECT),

        examples = @Example(
                syntax = "map:createFromJSON(\"{‘symbol' : 'IBM', 'price' : 200, 'volume' : 100}\")",
                description = "This returns a map with the keys `symbol`, `price`, and `volume`, " +
                        "and their values, `IBM`, `200` and `100` respectively.")
)
public class CreateFromJSONFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if ((attributeExpressionExecutors.length) != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:create() function, " +
                    "required only 1, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] objects, State state) {
        return null;
    }

    @Override
    protected Object execute(Object data, State state) {
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
}
