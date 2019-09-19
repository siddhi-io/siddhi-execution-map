/*
 * Copyright (c)  2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.util.Arrays;
import java.util.Map;

/**
 * containsKey(Object)
 * Returns boolean true if the object is empty map, boolean false if it is not .
 * Accept Type(s): (Object)
 * Return Type(s): boolean
 */
@Extension(
        name = "containsKey",
        namespace = "map",
        description = "Function checks if the map contains the key.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map the needs to be checked on containing the key or not.",
                        type = {DataType.OBJECT},
                        dynamic = true
                ),
                @Parameter(name = "key",
                        description = "The key to be checked.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.BOOL, DataType.STRING},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "key"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns `true` if the map is contains the key and `false` if otherwise.",
                type = DataType.BOOL),
        examples = @Example(
                syntax = "map:containsKey(students, '1234')",
                description = "Returns 'true' if the students map contains key `1234` else it returns `false`.")
)
public class ContainsKeyFunctionExtension extends FunctionExecutor<State> {
    private Attribute.Type returnType = Attribute.Type.BOOL;

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data[0] instanceof Map) {
            return ((Map) data[0]).containsKey(data[1]);
        }
        throw new SiddhiAppRuntimeException("Dropping event since the object is not of type Map<>. " +
                "Data: '" + Arrays.toString(data) + "'.");
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
