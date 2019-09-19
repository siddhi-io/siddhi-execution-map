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

import java.util.Arrays;
import java.util.Map;

/**
 * putIfAbsent(HashMap , key , value)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , ValidAttributeType , ValidAttributeType)
 * Return Type(s): HashMap
 */
@Extension(
        name = "putIfAbsent",
        namespace = "map",
        description = "Function returns the updated map after adding the given key-value pair if key is absent. ",
        parameters = {
                @Parameter(name = "map",
                        description = "The map to which the value should be added.",
                        type = DataType.OBJECT,
                        dynamic = true
                ),
                @Parameter(name = "key",
                        description = "The key to be added.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                ),
                @Parameter(name = "value",
                        description = "The value to be added.",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "key", "value"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the updated map with key and value.", type = DataType.OBJECT),
        examples = @Example(
                syntax = "map:putIfAbsent(students , 1234 , 'sam')",
                description = "Function returns the updated map named students after adding the value " +
                        "`sam` with the key `1234` if key is absent from the original map.")
)
public class PutIfAbsentFunctionExecutor extends FunctionExecutor<State> {
    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                                       SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.OBJECT;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data[0] instanceof Map) {
            Map<Object, Object> hashMap = (Map<Object, Object>) data[0];
            hashMap.putIfAbsent(data[1], data[2]);
            return hashMap;
        }
        throw new SiddhiAppRuntimeException("Dropping event since the first attribute is not of type Map<>. " +
                "Data: '" + Arrays.toString(data) + "'.");
    }

    @Override
    protected Object execute(Object data, State state) {
        //Since the map:putIfAbsent() function takes in 3 parameters,
        // this method does not get called. Hence, not implemented.
        return null;
    }


}
