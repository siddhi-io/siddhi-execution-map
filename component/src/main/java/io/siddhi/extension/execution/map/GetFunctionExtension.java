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

import java.util.Map;

/**
 * get(HashMap , Key , Type)
 * Returns required attribute value'.
 * Accept Type(s): (HashMap , ValidKey)
 * Return Type(s): Object
 */
@Extension(
        name = "get",
        namespace = "map",
        description = "Function returns the value corresponding to the given key from the map.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map from where the value should be obtained.",
                        type = DataType.OBJECT,
                        dynamic = true
                ),
                @Parameter(
                        name = "key",
                        description = "The key to fetch the value.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                ),
                @Parameter(
                        name = "default.value",
                        description = "The value to be returned if the map does not have the key.",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        optional = true,
                        defaultValue = "<EMPTY_STRING>",
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "key"}),
                @ParameterOverload(parameterNames = {"map", "key", "default.value"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the value from the map that corresponds to the given key.",
                type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                        DataType.FLOAT, DataType.BOOL, DataType.STRING}
                        ),
        examples = {
                @Example(
                        syntax = "map:get(companyMap, 1)",
                        description = "If the companyMap has key `1` and value `ABC` in it's set of key " +
                                "value pairs. The function returns `ABC`."),
                @Example(
                        syntax = "map:get(companyMap, 2)",
                        description = "If the companyMap does not have any value for key `2` then the function " +
                                "returns `null`."),
                @Example(
                        syntax = "map:get(companyMap, 2, 'two')",
                        description = "If the companyMap does not have any value for key `2` then the function " +
                                "returns `two`."
                )
        }
)
public class GetFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;
    private boolean isDefaultValueGiven = false;
    private static final long serialVersionUID = 1L;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length == 3) {
            this.isDefaultValueGiven = true;
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        Map map;
        if (data[0] instanceof Map) {
            map = (Map) data[0];
        } else {
            throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map, but found " +
                    data[0].getClass().getCanonicalName());
        }
        if (this.isDefaultValueGiven) {
            return map.getOrDefault(data[1], data[2]);
        }
        return map.get(data[1]);
    }

    @Override
    protected Object execute(Object data, State state) {
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
