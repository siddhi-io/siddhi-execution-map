/*
 * Copyright (c)  2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 * putAll(HashMap , HashMap)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , HashMap)
 * Return Type(s): HashMap
 */
@Extension(
        name = "putAll",
        namespace = "map",
        description = "Returns the updated to.map after copying all of the mappings from the specified from.map. "
                + "If you have duplicate keys, from.map will overwrite the values in to.map.",
        parameters = {
                @Parameter(name = "to.map",
                        description = "Map to which mappings needs to be copied in to.",
                        type = DataType.OBJECT,
                        optional = false
                ),
                @Parameter(name = "from.map",
                        description = "Map from which mappings are copied from.",
                        type = DataType.OBJECT,
                        optional = false
                )
        },
        examples = @Example(
                description = "Returns the updated map named to.map after adding each mapping from from.map.",
                syntax = "putAll(toMap , fromMap)"),
        returnAttributes = @ReturnAttribute(description = "A hashMap will be returned", type = DataType.OBJECT)
)
public class PutAllFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:putAll() function, " +
                    "required 2 parameters, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        if (data == null) {
            throw new SiddhiAppRuntimeException("Data can not be null.");
        }
        Map<Object, Object> map1 = (Map<Object, Object>) data[0];
        Map<Object, Object> map2 = (Map<Object, Object>) data[1];
        map1.putAll(map2);
        return map1;
    }

    @Override
    protected Object execute(Object data) {
        //Since the map:putAll() function takes in 2 parameters, this method does not get called.
        // Hence, not implemented.
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

    @Override
    public Map<String, Object> currentState() {
        //No need to maintain a state.
        return null;
    }

    @Override
    public void restoreState(Map<String, Object> state) {
        //Since there's no need to maintain a state, nothing needs to be done here.
    }
}
