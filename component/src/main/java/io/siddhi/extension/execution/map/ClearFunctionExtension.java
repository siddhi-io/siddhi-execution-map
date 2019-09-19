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

import java.util.Map;

/**
 * clear(HashMap , key , value)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , ValidAttributeType , ValidAttributeType)
 * Return Type(s): HashMap
 */
@Extension(
        name = "clear",
        namespace = "map",
        description = "Function returns the cleared map. ",
        parameters = {
                @Parameter(name = "map",
                        description = "The map which needs to be cleared",
                        type = DataType.OBJECT,
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns the cleared map.", type = DataType.OBJECT),
        examples = @Example(
                syntax = "map:clear(students)",
                description = "Returns an empty map.")
)
public class ClearFunctionExtension extends FunctionExecutor<State> {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        //Since the map:clear() function takes in 3 parameters, this method does not get called. Hence, not implemented.
        return null;
    }

    @Override
    protected Object execute(Object data, State state) {
        if (data instanceof Map) {
            Map<Object, Object> hashMap = (Map<Object, Object>) data;
            hashMap.clear();
            return hashMap;
        }
        throw new SiddhiAppRuntimeException("Dropping event since the object is not of type Map<>. " +
                "Data: '" + data.toString() + "'.");
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
