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
import org.wso2.siddhi.core.exception.SiddhiAppRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

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
        description = "This returns the updated map after adding the given key-value pair.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map to which the value should be added.",
                        type = DataType.OBJECT,
                        optional = false
                        ),
                @Parameter(name = "key",
                        description = "The key of the value added.",
                        type = DataType.OBJECT,
                        optional = false
                ),
                @Parameter(name = "value",
                        description = "The new value.",
                        type = DataType.OBJECT,
                        optional = false
                )
        },
        returnAttributes = @ReturnAttribute(description = "A hashMap is returned.", type = DataType.OBJECT),
        examples = @Example(
                syntax = "put(students , 1234 , ”sam”)",
                description = "This function returns the updated map named students after adding the object \"sam\" " +
                        "with key 1234.")
)
public class PutFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 3) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:put() function, " +
                    "required 3 parameters, but found " + attributeExpressionExecutors.length);
        }
    }


    @Override
    protected Object execute(Object[] data) {
        if (data == null) {
            throw new SiddhiAppRuntimeException("Data can not be null.");
        }
        Map<Object, Object> hashMap = (Map<Object, Object>) data[0];
        hashMap.put(data[1], data[2]);
        return hashMap;
    }

    @Override
    protected Object execute(Object data) {
        //Since the map:put() function takes in 3 parameters, this method does not get called. Hence, not implemented.
        return null;
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


