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

import java.util.ArrayList;
import java.util.Map;

/**
 * Implementation class for map:values()
 */
@Extension(
        namespace = "map",
        name = "values",
        description = "Function to return the values of the map.",
        parameters = {
                @Parameter(
                        name = "map",
                        description = "The map from which list if values to be returned.",
                        type = {DataType.OBJECT},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns values of the map as a list.",
                type = DataType.OBJECT),
        examples = @Example(
                syntax = "map:values(students)",
                description = "Returns values of the `students` map.")
)
public class ValuesFunctionExtension extends FunctionExecutor<State> {

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                       ConfigReader configReader, SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.OBJECT;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        // Not reachable due to validation at init
        return null;
    }

    @Override
    protected Object execute(Object data, State state) {
        if (data instanceof Map) {
            return new ArrayList<Object>(((Map) data).values());
        }
        throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map.");
    }
}
