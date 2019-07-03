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
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * remove(HashMap , key)
 * Returns the updated hashmap
 * Accept Type(s): (HashMap , ValidAttributeType)
 * Return Type(s): HashMap
 */
@Extension(
        name = "remove",
        namespace = "map",
        description = "This returns the updated map after removing the element with the key specified.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map that needs to be updated by removing the element.",
                        type = DataType.OBJECT,
                        dynamic = true,
                        optional = false
                ),
                @Parameter(name = "key",
                        description = "The key of the element that needs to removed from the map.",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true,
                        optional = false
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "key"})
        },
        returnAttributes = @ReturnAttribute(description = "return Object will be a HashMap", type = DataType.OBJECT),
        examples = @Example(
                syntax = "remove(students , 1234)",
                description = "This function returns the updated map, students after removing the element with the " +
                        "key 1234."
        )
)
public class RemoveFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(RemoveFunctionExtension.class);
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length < 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:remove() function, " +
                    "required one or more keys, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        Map<Object, Object> map = (Map<Object, Object>) data[0];
        for (int i = 1; i < data.length; i++) {
            map.remove(data[i]);
        }
        return map;
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
