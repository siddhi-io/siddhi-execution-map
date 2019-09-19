/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.event.ComplexEventChunk;
import io.siddhi.core.event.stream.MetaStreamEvent;
import io.siddhi.core.event.stream.StreamEvent;
import io.siddhi.core.event.stream.StreamEventCloner;
import io.siddhi.core.event.stream.holder.StreamEventClonerHolder;
import io.siddhi.core.event.stream.populater.ComplexEventPopulater;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.processor.Processor;
import io.siddhi.core.query.processor.stream.StreamProcessor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.AbstractDefinition;
import io.siddhi.query.api.definition.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation class for map:tokenize()
 */
@Extension(
        namespace = "map",
        name = "tokenize",
        description = "Tokenize the map and return each key, value as new attributes in events",
        parameters = {
                @Parameter(
                        name = "map",
                        description = "Hash map containing key value pairs",
                        type = DataType.OBJECT
                )
        },
        returnAttributes = {
                @ReturnAttribute(
                        name = "key",
                        description = "Key of an entry consisted in the map",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING}
                ),
                @ReturnAttribute(
                        name = "value",
                        description = "Value of an entry consisted in the map",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING}
                )
        },
        examples = {
                @Example(
                        syntax = "map:tokenize(customMap)",
                        description = "If custom map contains ('symbol': 'wso2'), ('volume' : 100) key-value pairs, " +
                                "then tokenize function will return 2 events with key: symbol, value: wso2 and " +
                                "key: volume, value: 100 respectively. "
                )
        }
)
public class TokenizeStreamProcessor extends StreamProcessor<State> {
   private List<Attribute> attributesList = new ArrayList<>();
   private ExpressionExecutor expressionExecutor;

    @Override
    protected StateFactory<State> init(MetaStreamEvent metaStreamEvent, AbstractDefinition abstractDefinition,
                                       ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                       StreamEventClonerHolder streamEventClonerHolder,
                                       boolean outputExpectsExpiredEvents, boolean findToBeExecuted,
                                       SiddhiQueryContext siddhiQueryContext) {

        int attributesLength = expressionExecutors.length;
        if ((attributesLength != 1)) {
            throw new SiddhiAppCreationException("map:tokenize() function  should have only one parameter , " +
                    "but found '" + attributesLength + "' parameters.");
        }

        if (expressionExecutors[0].getReturnType() == Attribute.Type.OBJECT) {
            this.expressionExecutor = expressionExecutors[0];
        } else {
            throw new SiddhiAppCreationException("The parameter 'map' in map:tokenize() function should be of " +
                    "type OBJECT, but found a parameter with type '" + expressionExecutors[0].getReturnType() + "'.");
        }

        this.attributesList.add(new Attribute("key", Attribute.Type.STRING));
        this.attributesList.add(new Attribute("value", Attribute.Type.STRING));
        return null;
    }

    @Override
    protected void process(ComplexEventChunk<StreamEvent> complexEventChunk, Processor processor,
                           StreamEventCloner streamEventCloner, ComplexEventPopulater complexEventPopulater,
                           State state) {
        while (complexEventChunk.hasNext()) {
            StreamEvent event = complexEventChunk.next();
            Object mapObject = this.expressionExecutor.execute(event);

            if (!(mapObject instanceof HashMap)) {
                throw new SiddhiAppRuntimeException("Dropping event since the object is not of type Map<>. " +
                        "Event: '" + event + "'.");
            }

            Map<Object, Object> dataMap = (HashMap<Object, Object>) mapObject;

            for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
                StreamEvent clonedEvent = streamEventCloner.copyStreamEvent(event);
                Object[] data = new Object[]{entry.getKey(), entry.getValue()};
                complexEventPopulater.populateComplexEvent(clonedEvent, data);
                complexEventChunk.insertBeforeCurrent(clonedEvent);
            }
            complexEventChunk.remove();
        }
        nextProcessor.process(complexEventChunk);
    }

    @Override
    public List<Attribute> getReturnAttributes() {
        return this.attributesList;
    }

    @Override
    public ProcessingMode getProcessingMode() {
        return ProcessingMode.SLIDE;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
