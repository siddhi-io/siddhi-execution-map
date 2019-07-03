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
        description = "This returns the value object, that corresponds to the given key, from the map. ",
        parameters = {
                @Parameter(name = "map",
                        description = "The map from where the value should be obtained",
                        type = DataType.OBJECT,
                        dynamic = true,
                        optional = false
                ),
                @Parameter(
                        name = "key",
                        description = "The key of the value which needs to be returned",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true,
                        optional = false
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "key"})
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns the value object from the map that corresponds to the given key.",
                type = DataType.OBJECT),
        examples = @Example(
                syntax = "get(company,1)",
                description = "This function returns the value that is associated with the key, i.e., 1, from a " +
                        "map named company.")
)
public class GetFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:get() function, " +
                    "required 2, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        Map map;
        if (data[0] instanceof Map) {
            map = (Map) data[0];
        } else {
            throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map");
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
