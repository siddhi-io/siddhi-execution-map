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
import io.siddhi.annotation.ParameterOverload;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.event.ComplexEventChunk;
import io.siddhi.core.event.stream.MetaStreamEvent;
import io.siddhi.core.event.stream.StreamEvent;
import io.siddhi.core.event.stream.StreamEventCloner;
import io.siddhi.core.event.stream.holder.StreamEventClonerHolder;
import io.siddhi.core.event.stream.populater.ComplexEventPopulater;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                        type = DataType.OBJECT,
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map"}),
                @ParameterOverload(parameterNames = {"map", "..."})
        },
        returnAttributes = {
                @ReturnAttribute(
                        name = "key",
                        description = "Key of an entry consisted in the map",
                        type = {DataType.OBJECT}
                ),
                @ReturnAttribute(
                        name = "value",
                        description = "Value of an entry consisted in the map",
                        type = {DataType.OBJECT}
                )
        },
        examples = {
                @Example(
                        syntax =
                                "define stream StockStream(symbol string, price float);\n\n" +
                                "from StockStream#window.lengthBatch(2)\n" +
                                "select map:collect(symbol, price) as symbolPriceMap\n" +
                                "insert into TempStream;\n\n" +
                                "from TempStream#map:tokenize(customMap)\n" +
                                "select key, value \n" +
                                "insert into SymbolStream;",
                        description = "Based on the length batch window, `symbolPriceMap` will collect two events, " +
                                "and the map will then again tokenized to give 2 events with key and values being " +
                                "symbol name and price respectively."
                )
        }
)
public class TokenizeStreamProcessor extends StreamProcessor<State> {
   private List<Attribute> attributesList = new ArrayList<>();
   private ExpressionExecutor[] expressionExecutor;
   private int numOfMaps;

    @Override
    protected StateFactory<State> init(MetaStreamEvent metaStreamEvent, AbstractDefinition abstractDefinition,
                                       ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                       StreamEventClonerHolder streamEventClonerHolder,
                                       boolean outputExpectsExpiredEvents, boolean findToBeExecuted,
                                       SiddhiQueryContext siddhiQueryContext) {
        this.expressionExecutor = expressionExecutors;
        this.numOfMaps = expressionExecutors.length;

        this.attributesList.add(new Attribute("key", Attribute.Type.OBJECT));
        this.attributesList.add(new Attribute("value", Attribute.Type.OBJECT));
        return null;
    }

    @Override
    protected void process(ComplexEventChunk<StreamEvent> complexEventChunk, Processor processor,
                           StreamEventCloner streamEventCloner, ComplexEventPopulater complexEventPopulater,
                           State state) {
        while (complexEventChunk.hasNext()) {
            StreamEvent event = complexEventChunk.next();

            List<Map<Object, Object>> dataMaps = new ArrayList<>();
            for (int i = 0; i < this.expressionExecutor.length; i++) {
                Object mapObject = this.expressionExecutor[i].execute(event);
                if (!(mapObject instanceof HashMap)) {
                    throw new SiddhiAppRuntimeException("Attribute number, '" + (i + 1) + "' must be of type " +
                            "java.util.Map, but found " + mapObject.getClass().getCanonicalName());
                }
                dataMaps.add((Map) mapObject);
            }

            Set<Object> allKeySet = new HashSet<>();
            for (Map<Object, Object> dataMap : dataMaps) {
                Set<Object> keySet = dataMap.keySet();
                allKeySet.addAll(keySet);
            }

            for (Object key : allKeySet) {
                StreamEvent clonedEvent = streamEventCloner.copyStreamEvent(event);

                Object[] data;
                if (this.numOfMaps == 1) {
                    data = new Object[]{key, dataMaps.get(0).get(key)};
                } else {
                    List<Object> value = new ArrayList<>();
                    for (int i = 0; i < this.numOfMaps; i++) {
                        value.add(dataMaps.get(i).get(key));
                    }
                    data = new Object[]{key, value};
                }

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
