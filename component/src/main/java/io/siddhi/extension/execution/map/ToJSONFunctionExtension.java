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
import org.json.JSONObject;

import java.util.Map;


/**
 * toJSON(Map)
 * Returns a string representation of the map in JSON format
 * Accept Type(s): (Map)
 * Return Type(s): String
 */
@Extension(
        name = "toJSON",
        namespace = "map",
        description = "Function converts a map into a JSON object and returns the JSON as a string.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map that needs to be converted to JSON",
                        type = DataType.OBJECT,
                        dynamic = true
                ),
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the map in JSON format.",
                type = DataType.STRING),
        examples = @Example(
                syntax = "map:toJSON(company)",
                description = "If `company` is a map with key-value pairs, ('symbol': 'wso2'),('volume' : 100), " +
                        "and ('price', 200), it returns the JSON string `{\"symbol\" : \"wso2\"," +
                        " \"volume\" : 100 , \"price\" : 200}`.")
)
public class ToJSONFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.STRING;
    private static final long serialVersionUID = 1L;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if ((attributeExpressionExecutors.length) != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:toJSON() function, " +
                    "required only 1, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        return null;
    }

    @Override
    protected Object execute(Object data, State state) {
        if (data instanceof Map) {
            Map<Object, Object> map = (Map) data;
            JSONObject jsonObject = new JSONObject(map);
            return jsonObject.toString();
        } else {
            throw new SiddhiAppRuntimeException("Data should be a string");
        }
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
