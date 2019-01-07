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

import org.apache.log4j.Logger;
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
                        optional = false
                        ),
                @Parameter(name = "key",
                        description = "The key of the element that needs to removed from the map.",
                        type = DataType.OBJECT,
                        optional = false
                )
        },
        returnAttributes = @ReturnAttribute(description = "return Object will be a HashMap", type = DataType.OBJECT),
        examples = @Example(
                syntax = "remove(students , 1234)",
                description = "This function returns the updated map, students after removing the element with the" +
                        " key 1234"
                )

)
public class RemoveFunctionExtension extends FunctionExecutor {
    private static final Logger log = Logger.getLogger(RemoveFunctionExtension.class);
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length < 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:remove() function, " +
                    "required one or more keys, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        Map<Object, Object> map = (Map<Object, Object>) data[0];
        for (int i = 1; i < data.length; i++) {
            map.remove(data[i]);
        }
        return map;
    }

    @Override
    protected Object execute(Object data) {
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


