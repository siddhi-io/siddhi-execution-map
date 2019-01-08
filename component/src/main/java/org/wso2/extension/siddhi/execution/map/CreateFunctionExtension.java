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

package org.wso2.extension.siddhi.execution.map;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

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
        description = "This creates a map between the keys and their corresponding values.",
        parameters = {
                @Parameter(name = "key1",
                        description = "key 1",
                        type = DataType.OBJECT
                ),
                @Parameter(name = "value1",
                        description = "Value 1",
                        type = DataType.OBJECT
                ),
                @Parameter(name = "key2",
                        description = "Key 2",
                        type = DataType.OBJECT
                ),
                @Parameter(name = "value2",
                        description = "Value 2",
                        type = DataType.OBJECT
                ),
        },
        returnAttributes = @ReturnAttribute(description = "Returns the created map object. ", type = DataType.OBJECT),
        examples = @Example(
                syntax = "create(1 , ”one” ,  2 , ”two” , 3 , ”three”)",
                description = "This returns a map with keys 1, 2, 3 mapped with their corresponding values, " +
                        "\"one\", \"two\", \"three\".")
)
public class CreateFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if ((attributeExpressionExecutors.length) % 2 == 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:create() function, " +
                    "required 0 or multiple of 2, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (int i = 0; i < data.length; i += 2) {
            map.put(data[i], data[i + 1]);
        }
        return map;
    }

    @Override
    protected Object execute(Object data) {
        return new HashMap<Object, Object>();
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

    @Override
    public Map<String, Object> currentState() {
        return null;    //No need to maintain a state.
    }

    @Override
    public void restoreState(Map<String, Object> state) {
        //Since there's no need to maintain a state, nothing needs to be done here.
    }
}


