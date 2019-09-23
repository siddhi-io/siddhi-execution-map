/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 * Implementation for map:replace()
 */
@Extension(
        name = "replace",
        namespace = "map",
        description = "Function returns the updated map after replacing the given key-value pair " +
                "only if key is present.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map to which the key-value should be replaced.",
                        type = DataType.OBJECT,
                        dynamic = true
                ),
                @Parameter(name = "key",
                        description = "The key to be replaced.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                ),
                @Parameter(name = "value",
                        description = "The value to be replaced.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.BOOL, DataType.STRING},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "key", "value"})
        },
        returnAttributes =
                @ReturnAttribute(
                        description = "Returns the updated map replaced key and value.",
                        type = DataType.OBJECT
                ),
        examples =
                @Example(
                        syntax = "map:replace(stockDetails , 1234 , 'IBM')",
                        description = "Function returns the updated map named stockDetails after replacing the value " +
                        "`IBM` with the key `1234` if present.")
)
public class ReplaceFunctionExtension extends FunctionExecutor<State> {

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                       ConfigReader configReader, SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data[0] instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) data[0];
            map.replace(data[1], data[2]);
            return map;
        }
        throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map, but found '" +
                data[0].getClass().getCanonicalName() + "'.");
    }

    @Override
    protected Object execute(Object data, State state) {
        // Not reachable due to validation at param overload
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.OBJECT;
    }
}
