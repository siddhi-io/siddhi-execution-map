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
 * Implementation for map:replaceAll()
 */
@Extension(
        name = "replaceAll",
        namespace = "map",
        description = "Function returns the updated map after replacing all the key-value pairs from another map, " +
                "if keys are present.",
        parameters = {
                @Parameter(name = "to.map",
                        description = "The map into which the key-values need to copied.",
                        type = DataType.OBJECT,
                        dynamic = true
                ),
                @Parameter(name = "from.map",
                        description = "The map from which the key-values are copied.",
                        type = DataType.OBJECT,
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"to.map", "from.map"})
        },
        returnAttributes =
                @ReturnAttribute(
                        description = "Returns the updated map after replacing all the key-value pairs if present.",
                        type = DataType.OBJECT
                ),
        examples =
                @Example(
                        syntax = "map:replaceAll(toMap, fromMap)",
                        description = "If `toMap` contains key-value pairs ('symbol': 'wso2'), ('volume' : 100), " +
                        "and if `fromMap` contains key-value pairs ('symbol': 'IBM'), ('price' : 12), " +
                        "then the function returns updated `toMap` with key-value pairs ('symbol': 'IBM'), " +
                        "('volume' : 100)."
                )
)
public class ReplaceAllFunctionExtension extends FunctionExecutor<State> {

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                       ConfigReader configReader, SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data[0] instanceof Map) {
            if (data[1] instanceof Map) {
                Map<Object, Object> toMap = (Map<Object, Object>) data[0];
                Map<Object, Object> fromMap = (Map<Object, Object>) data[1];
                fromMap.forEach(toMap::replace);
                return toMap;
            }
            throw new SiddhiAppRuntimeException("Second attribute value must be of type java.util.Map, but found '" +
                    data[1].getClass().getCanonicalName() + "'.");
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
