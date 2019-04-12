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

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.OperationNotSupportedException;
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
 * toXML(Map) or toXML(Map, RootElementName)
 * Returns the string representation of the map in XML format
 * Accept Type(s): (Map) or (Map, String)
 * Return Type(s): String
 */
@Extension(
        name = "toXML",
        namespace = "map",
        description = "This returns the map as an XML string.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map that needs to be converted to XML.",
                        type = DataType.OBJECT,
                        optional = false
                ),
                @Parameter(name = "rootelementname",
                        description = "The root element of the map.",
                        type = DataType.OBJECT,
                        optional = true,
                        defaultValue = "null"
                )
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns the string representation of the map in XML format.",
                type = DataType.STRING),
        examples = @Example(
                syntax = "toXML(company, \"abcCompany\")",
                description = "If \"company\" is a map with key-value pairs, (“symbol” : wso2), " +
                        "(“volume” : 100), and (“price” : 200), this function returns the string, " +
                        "“<abcCompany><symbol>wso2</symbol><volume><100></volume><price>200</price></abcCompany>.")
)
public class ToXMLFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.STRING;
    private String rootElement = null;

    @Override
    protected StateFactory init(ExpressionExecutor[] expressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if ((attributeExpressionExecutors.length) > 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:toXML() function, "
                    + "required only 1 or 2, but found " + attributeExpressionExecutors.length);

        } else if ((attributeExpressionExecutors.length) == 2) {
            Object rootElementObject = attributeExpressionExecutors[1].execute(null);
            if (rootElementObject instanceof String) {
                rootElement = ((String) rootElementObject);
            } else {
                throw new OperationNotSupportedException("Root element name should be of type String. But found "
                        + attributeExpressionExecutors[1].getReturnType());
            }
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        if (data[0] instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) data[0];
            return getXmlFromMapWithRootElement(map);
        } else {
            throw new SiddhiAppRuntimeException("Data should be a string");
        }
    }

    @Override
    protected Object execute(Object data, State state) {
        if (data instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) data;
            return getXmlFromMap(map);
        } else {
            throw new SiddhiAppRuntimeException("Data should be a string");
        }
    }

    private Object getXmlFromMap(Map<Object, Object> map) {
        return addingElements(map);
    }

    private Object getXmlFromMapWithRootElement(Map<Object, Object> map) {
        StringBuilder xmlValue = new StringBuilder();
        xmlValue.append("<" + rootElement + ">");
        xmlValue.append(addingElements(map));
        xmlValue.append("</" + rootElement + ">");
        return xmlValue.toString();
    }

    private String addingElements(Map<Object, Object> map) {
        StringBuilder xmlValue = new StringBuilder();
        for (Map.Entry<Object, Object> mapEntry : map.entrySet()) {
            xmlValue.append("<" + mapEntry.getKey().toString() + ">");
            if (mapEntry.getValue() instanceof Map) {
                xmlValue.append(getXmlFromMap((Map<Object, Object>) mapEntry.getValue()));
            } else {
                xmlValue.append(mapEntry.getValue().toString());
            }
            xmlValue.append("</" + mapEntry.getKey().toString() + ">");
        }
        return xmlValue.toString();
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
