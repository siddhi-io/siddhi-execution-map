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
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.selector.attribute.aggregator.AttributeAggregatorExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;

import java.util.HashMap;

/**
 * Implementation class for map:collect()
 */
@Extension(
        namespace = "map",
        name = "collect",
        description = "Collect multiple key-value pairs to construct a map. Only distinct keys are collected, if a " +
                "duplicate key arrives, it overrides the old value",
        parameters = {
                @Parameter(
                        name = "key",
                        description = "Key of the map entry",
                        type = {DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.FLOAT, DataType.BOOL, DataType.STRING},
                        dynamic = true
                ),
                @Parameter(
                        name = "value",
                        description = "Value of the map entry",
                        type = {DataType.OBJECT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE,
                                DataType.BOOL, DataType.STRING},
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(
                        parameterNames = {"key", "value"}
                )
        },
        returnAttributes = {
                @ReturnAttribute(
                        description = "Map containing all the key-value pairs.",
                        type = DataType.OBJECT
                )
        },
        examples = {
                @Example(
                        syntax = "from StockStream#window.lengthBatch(10)\n" +
                                 "select map:collect(symbol, price) as stockDetails\n" +
                                 "insert into OutputStream;",
                        description = "For the window expiry of 10 events, the collect() function will collect " +
                                "attributes of `key` and `value` to a single map and return as stockDetails."
                )
        }
)
public class CollectAggregateFunction extends AttributeAggregatorExecutor<State> {
    private static final long serialVersionUID = 1L;

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] expressionExecutors, ProcessingMode processingMode,
                                       boolean outputExpectsExpiredEvents, ConfigReader configReader,
                                       SiddhiQueryContext siddhiQueryContext) {
        return MapState::new;
    }

    @Override
    public Object processAdd(Object object, State state) {
        // Cannot be reached due validation at the init()
        return null;
    }

    @Override
    public Object processAdd(Object[] objects, State state) {
        ((MapState) state).addEntry(objects[0], objects[1]);
        return ((MapState) state).getDataMapClone();
    }

    @Override
    public Object processRemove(Object object, State state) {
        // Cannot be reached due validation at the init()
        return null;
    }

    @Override
    public Object processRemove(Object[] objects, State state) {
        ((MapState) state).removeEntry(objects[0]);
        return ((MapState) state).getDataMapClone();
    }


    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.OBJECT;
    }


    @Override
    public Object reset(State state) {
        ((MapState) state).setDataMap(new HashMap<>());
        return ((MapState) state).getDataMapClone();
    }
}
