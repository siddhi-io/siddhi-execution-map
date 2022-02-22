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
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.XMLStreamException;

/**
 * createFromXML(String)
 * Returns the created hashmap
 * Accept Type(s): (String)
 * Return Type(s): Map
 */
@Extension(
        name = "createFromXML",
        namespace = "map",
        description = "Function returns the map created by pairing the keys with their corresponding values," +
                "given as an XML string.",
        parameters = {
                @Parameter(name = "xml.string",
                        description = "The XML string, which is used to create the map.",
                        type = DataType.STRING,
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"xml.string"})
        },
        returnAttributes = @ReturnAttribute(description = "This returns a map.", type = DataType.OBJECT),
        examples = @Example(
                syntax = "map:createFromXML(\"<stock>\n" +
                         "                      <symbol>IBM</symbol>\n" +
                         "                      <price>200</price>\n" +
                         "                      <volume>100</volume>\n" +
                         "                   </stock>\")\n",
                description = "This returns a map with the keys `symbol`, `price`, `volume`, " +
                        "and with their values `IBM`, `200` and `100` respectively.")
)
public class CreateFromXMLFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private static final long serialVersionUID = 1L;

    @Override
    protected StateFactory init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if ((attributeExpressionExecutors.length) != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:createFromXML() function, " +
                    "required only 1, but found " + attributeExpressionExecutors.length);
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        return null;
    }

    @Override
    protected Object execute(Object data, State state) {
        if (data instanceof String) {
            try {
                OMElement parentElement = AXIOMUtil.stringToOM(data.toString());
                return getMapFromXML(parentElement);
            } catch (XMLStreamException e) {
                throw new SiddhiAppRuntimeException("Input data cannot be parsed to xml: " + e.getMessage(), e);
            }
        } else {
            throw new SiddhiAppRuntimeException("Data should be a string");
        }
    }

    private Object getMapFromXML(OMElement parentElement) throws XMLStreamException {
        Map<Object, Object> topLevelMap = new HashMap<Object, Object>();
        Iterator iterator = parentElement.getChildElements();
        while (iterator.hasNext()) {
            OMElement streamAttributeElement = (OMElement) iterator.next();
            String key = streamAttributeElement.getQName().toString();
            Object value;
            if (streamAttributeElement.getFirstElement() != null) {
                value = getMapFromXML(streamAttributeElement);
            } else {
                String elementText = streamAttributeElement.getText();
                if (elementText.equals("true") || elementText.equals("false")) {
                    value = Boolean.parseBoolean(elementText);
                } else {
                    if (NumberUtils.isNumber(elementText)) {
                        try {
                            value = numberFormat.parse(elementText);
                        } catch (ParseException e) {
                            value = elementText;
                        }
                    } else {
                        value = elementText;
                    }
                }
            }
            topLevelMap.put(key, value);
        }
        return topLevelMap;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
