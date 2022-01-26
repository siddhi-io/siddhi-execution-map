/*
 * Copyright (c)  2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * combineByKey(HashMap , HashMap)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , HashMap)
 * Return Type(s): HashMap
 */
@Extension(
        name = "combineByKey",
        namespace = "map",
        description = "Function returns the map after combining all the maps given as parameters, such that " +
                "the keys, of all the maps will be matched with an Array list of values from each map respectively.",
        parameters = {
                @Parameter(name = "map",
                        description = "The map into which the key-values need to copied.",
                        type = DataType.OBJECT,
                        dynamic = true
                )
        },
        parameterOverloads = {
                @ParameterOverload(parameterNames = {"map", "map"}),
                @ParameterOverload(parameterNames = {"map", "map", "..."})
        },
        returnAttributes =
                @ReturnAttribute(
                        description = "Returns the map after combining key-value pairs by keys.",
                        type = DataType.OBJECT
                ),
        examples = @Example(
                syntax = "map:combineByKey(map1, map2)",
                description = "If `map2` contains key-value pairs ('symbol': 'wso2'), ('volume' : 100), " +
                        "and if `map2` contains key-value pairs ('symbol': 'IBM'), ('price' : 12), " +
                        "then the function returns the map with key value pairs as follows, " +
                        "(symbol: ArrayList('wso2, 'IBM')), (volume: ArrayList(100, null)) " +
                        "and (price: ArrayList(null, 12))")
)
public class CombineByKeyFunctionExtension extends FunctionExecutor<State> {
    private Attribute.Type returnType = Attribute.Type.OBJECT;
    private int numOfMaps;
    private static final long serialVersionUID = 1L;

    @Override
    protected StateFactory<State> init(ExpressionExecutor[] attributeExpressionExecutors,
                                ConfigReader configReader, SiddhiQueryContext siddhiQueryContext) {
        this.numOfMaps = attributeExpressionExecutors.length;
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        List<Map<Object, Object>> dataMaps = new ArrayList<>();
        for (int i = 0; i < numOfMaps; i++) {
            Object mapObject = data[i];
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

        HashMap<Object, Object> combinedMap = new HashMap<>();
        for (Object key : allKeySet) {
            List<Object> value = new ArrayList<>();
            for (int i = 0; i < this.numOfMaps; i++) {
                value.add(dataMaps.get(i).get(key));
            }
            combinedMap.put(key, value);
        }

        return combinedMap;
    }

    @Override
    protected Object execute(Object data, State state) {
        //Since the map:combineByKey() function takes in 2 parameters, this method does not get called.
        // Hence, not implemented.
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }
}
