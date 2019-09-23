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
 * isEmpty(Object)
 * Returns boolean true if the object is empty map, boolean false if it is not .
 * Accept Type(s): (Object)
 * Return Type(s): boolean
 */
@Extension(
        name = "isEmpty",
        namespace = "map",
        description = "Function checks if the map is empty.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map the need to be checked whether it's empty or not.",
                        type = {DataType.OBJECT},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map"})
        },
        returnAttributes =
                @ReturnAttribute(
                        description = "Returns `true` if the map is empty and `false` if otherwise.",
                        type = DataType.BOOL
                ),
        examples =
                @Example(
                        syntax = "map:isEmpty(stockDetails)",
                        description = "Returns 'true' if the stockDetails map is empty else it returns `false`."
                )
)
public class IsEmptyFunctionExtension extends FunctionExecutor<State> {
    private Attribute.Type returnType = Attribute.Type.BOOL;

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        return null;
    }

    @Override
    protected Boolean execute(Object data, State state) {
        if (data instanceof Map) {
            return ((Map) data).isEmpty();
        }
        throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map, but found '" +
                data.getClass().getCanonicalName() + "'.");
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
