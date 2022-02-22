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
import io.siddhi.core.exception.SiddhiAppRuntimeException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.query.processor.ProcessingMode;
import io.siddhi.core.query.selector.attribute.aggregator.AttributeAggregatorExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation class for map:merge()
 */
@Extension(
        namespace = "map",
        name = "merge",
        description = "Collect multiple maps to merge as a single map. Only distinct keys are collected, if a " +
                "duplicate key arrives, it overrides the old value.",
        parameters = {
                @Parameter(
                        name = "map",
                        description = "Maps to be collected",
                        type = DataType.OBJECT,
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = "map")
        },
        returnAttributes = {
                @ReturnAttribute(
                        description = "Map containing all the collected key-value pairs.",
                        type = DataType.OBJECT
                )
        },
        examples = {
                @Example(
                        syntax = "from StockStream#window.lengthBatch(2)\n" +
                                "select map:merge(map) as stockDetails\n" +
                                "insert into OutputStream;",
                        description = "For the window expiry of 2 events, the merge() function will collect " +
                                "attributes of `map` and merge them to a single map, returned as stockDetails."
                )
        }
)
public class MergeAggregateFunction extends AttributeAggregatorExecutor<State> {
    private static final long serialVersionUID = 1L;
    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors, ProcessingMode processingMode,
                                       boolean outputExpectsExpiredEvents, ConfigReader configReader,
                                       SiddhiQueryContext siddhiQueryContext) {
        return MapState::new;
    }

    @Override
    public Object processAdd(Object data, State state) {
        if (data instanceof Map) {
            ((MapState) state).addAll((Map<Object, Object>) data);
            return ((MapState) state).getDataMapClone();
        }
        throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map, but found '" +
                data.getClass().getCanonicalName() + "'.");
    }

    @Override
    public Object processAdd(Object[] data, State state) {
        // not reached due to validation at the init()
        return null;
    }

    @Override
    public Object processRemove(Object data, State state) {
        if (data instanceof Map) {
            ((MapState) state).removeAll((Map<Object, Object>) data);
            return ((MapState) state).getDataMapClone();
        }
        throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map, but found '" +
                data.getClass().getCanonicalName() + "'.");
    }

    @Override
    public Object processRemove(Object[] data, State state) {
        // not reached due to validation at the init()
        return null;
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
