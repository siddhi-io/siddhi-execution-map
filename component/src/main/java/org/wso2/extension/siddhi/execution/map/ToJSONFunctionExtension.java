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
        description = "This converts a map into a JSON object and returns the definition of that " +
                "JSON object as a string.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map that needs to be converted to JSON",
                        type = DataType.OBJECT,
                        optional = false
                ),
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns a string representation of the map in JSON format.",
                type = DataType.STRING),
        examples = @Example(
                syntax = "toJSON(company)",
                description = "If \"company\" is a map with key-value pairs, (\"symbol\" : wso2),(\"volume\" : 100), " +
                        "and (\"price\",200), it returns the string “{“symbol” : “wso2” ," +
                        " “volume” : 100 , “price” : 200}”.")


)
public class ToJSONFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.STRING;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if ((attributeExpressionExecutors.length) != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:toJSON() function, " +
                    "required only 1, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        return null;
    }

    @Override
    protected Object execute(Object data) {
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

    @Override
    public Map<String, Object> currentState() {
        return null;    //No need to maintain a state.
    }

    @Override
    public void restoreState(Map<String, Object> state) {
        //Since there's no need to maintain a state, nothing needs to be done here.
    }
}


