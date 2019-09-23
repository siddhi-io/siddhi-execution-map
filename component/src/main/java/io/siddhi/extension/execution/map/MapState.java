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

import io.siddhi.core.util.snapshot.state.State;
import java.util.HashMap;
import java.util.Map;

/**
 * State to be kept for map:collect() and map:merge()
 */
public class MapState extends State {

    private Map<Object, Object> dataMap = new HashMap<>();

    @Override
    public boolean canDestroy() {
        return dataMap.isEmpty();
    }

    @Override
    public Map<String, Object> snapshot() {
        Map<String, Object> state = new HashMap<>();
        state.put("mapOfValues", dataMap);
        return state;
    }

    @Override
    public void restore(Map<String, Object> state) {
        dataMap = (HashMap<Object, Object>) state.get("mapOfValues");
    }

    public void setDataMap(Map<Object, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public void addEntry(Object key, Object value) {
        dataMap.put(key, value);
    }

    public void removeEntry(Object key) {
        dataMap.remove(key);
    }

    public void addAll(Map<Object, Object> map) {
        dataMap.putAll(map);
    }

    public void removeAll(Map<Object, Object> map) {
        map.keySet().forEach((key) -> dataMap.remove(key));
    }

    public Map<Object, Object> getDataMapClone() {
        return new HashMap<>(dataMap);
    }
}
