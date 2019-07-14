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

import java.util.HashMap;
import java.util.Map;

/**
 * create(key1,value1,key2,value2,.....,keyN,valueN) or create()
 * Returns the created hashmap
 * Accept Type(s): () , (Object,Object,....,Object,Object)
 * Return Type(s): HashMap
 */
@Extension(
        name = "create",
        namespace = "map",
        description = "Function creates a map pairing the keys and their corresponding values.",
        parameters = {
                @Parameter(name = "key1",
                        description = "Key 1",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true,
                        optional = true,
                        defaultValue = "-"
                ),
                @Parameter(name = "value1",
                        description = "Value 1",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true,
                        optional = true,
                        defaultValue = "-"
                ),
        },
        parameterOverloads = {
                @ParameterOverload(),
                @ParameterOverload(parameterNames = {"key1", "value1"}),
                @ParameterOverload(parameterNames = {"key1", "value1", "..."}),
        },
        returnAttributes = @ReturnAttribute(description = "Returns the created map object," +
                " by pairing adjacent key value pairs.", type = DataType.OBJECT),
        examples = {
                @Example(
                        syntax = "map:create(1, 'one', 2, 'two', 3, 'three')",
                        description = "This returns a map with keys `1`, `2`, `3` mapped with " +
                                "their corresponding values, `one`, `two`, `three`."),
                @Example(
                        syntax = "map:create()",
                        description = "This returns an empty map.")
        }
)
public class CreateFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if ((attributeExpressionExecutors.length) % 2 == 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:create() function, " +
                    "required 0 or multiple of 2, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (int i = 0; i < data.length; i += 2) {
            map.put(data[i], data[i + 1]);
        }
        return map;
    }

    @Override
    protected Object execute(Object data, State state) {
        return new HashMap<Object, Object>();
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
