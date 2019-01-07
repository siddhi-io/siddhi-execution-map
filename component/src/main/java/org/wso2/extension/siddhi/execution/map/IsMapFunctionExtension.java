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
        description = "This returns 'true' if the object is a map and 'false' if otherwise.",
        parameters = {
                @Parameter(name = "object",
                        description = "The object that the function checks to determine whether it's a map or not.",
                        type = {DataType.OBJECT},
                        optional = false
                )
        },
        returnAttributes = @ReturnAttribute(description = "This returns a boolean value based on whether " +
                "the object specified is a map or not.", type = DataType.BOOL),
        examples = @Example(
                syntax = "isMap(students)",
                description = "This function returns 'true' if the object, students is a map. It returns 'false' if " +
                        "it is not a map.")


)
public class IsMapFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.BOOL;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:isMap() function, " +
                    "required only one, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        return null;
    }

    @Override
    protected Boolean execute(Object data) {
        if (data instanceof Map) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
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


