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
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.Map;

/**
 * isMap(Object)
 * Returns boolean true if the object is a hashmap, boolean false if it is not .
 * Accept Type(s): (Object)
 * Return Type(s): boolean
 */
@Extension(
        name = "isMap",
        namespace = "map",
        description = "Function checks if the object is type of a map.",
        parameters = {
                @Parameter(name = "arg",
                        description = "The argument the need to be determined whether it's a map or not.",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"arg"})
        },
        returnAttributes = @ReturnAttribute(
                description = "Returns `true` if the arg is a map (`java.util.Map`) and `false` if otherwise.",
                type = DataType.BOOL),
        examples = @Example(
                syntax = "map:isMap(stockDetails)",
                description = "Returns 'true' if the stockDetails is and an instance of `java.util.Map` " +
                        "else it returns `false`.")
)
public class IsMapFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.BOOL;
    private static final long serialVersionUID = 1L;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:isMap() function, " +
                    "required only one, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        return null;
    }

    @Override
    protected Boolean execute(Object data, State state) {
        if (data instanceof Map) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
