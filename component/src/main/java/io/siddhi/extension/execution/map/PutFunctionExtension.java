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
 * put(HashMap , key , value)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , ValidAttributeType , ValidAttributeType)
 * Return Type(s): HashMap
 */
@Extension(
        name = "put",
        namespace = "map",
        description = "Function returns the updated map after adding the given key-value pair. " +
                "If the key already exist in the map the key is updated with the new value.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map to which the value should be added.",
                        type = DataType.OBJECT,
                        dynamic = true
                ),
                @Parameter(name = "key",
                        description = "The key to be added.",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                ),
                @Parameter(name = "value",
                        description = "The value to be added.",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
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
                syntax = "map:put(stockDetails , 'IBM' , '200')",
                description = "Function returns the updated map named stockDetails after adding the value " +
                        "`200` with the key `IBM`.")
)
public class PutFunctionExtension extends FunctionExecutor {
    private static final long serialVersionUID = 1L;
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 3) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:put() function, " +
                    "required 3 parameters, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data == null) {
            throw new SiddhiAppRuntimeException("Data can not be null.");
        }
        Map<Object, Object> hashMap = (Map<Object, Object>) data[0];
        hashMap.put(data[1], data[2]);
        return hashMap;
    }

    @Override
    protected Object execute(Object data, State state) {
        //Since the map:put() function takes in 3 parameters, this method does not get called. Hence, not implemented.
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
