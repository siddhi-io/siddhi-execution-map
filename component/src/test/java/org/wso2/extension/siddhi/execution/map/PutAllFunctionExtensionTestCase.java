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

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.extension.siddhi.execution.map.test.util.SiddhiTestHelper;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.exception.SiddhiAppCreationException;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PutAllFunctionExtensionTestCase {
    private static final Logger log = Logger.getLogger(PutAllFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testPutAllFunctionExtension() throws InterruptedException {
        log.info("PutAllFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = ("@info(name = 'query1') from startTrigger "
                + "select map:create(1 , 'one' ,  2 , 'two' , 3 , 'three') as map1, "
                + "map:create(4 , 'four' ,  5 , 'five' , 6 , 'six') as map2 insert into tmpStream;"
                + "@info(name = 'query2') from tmpStream  select map:putAll(map1, map2)" +
                " as newMap"
                + " insert into outputStream;"

        );

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                EventPrinter.print(events);
                for (Event event : events) {
                    count.incrementAndGet();
                    HashMap map = (HashMap) event.getData(0);
                    AssertJUnit.assertEquals("one", map.get(1));
                    AssertJUnit.assertEquals("six", map.get(6));
                    eventArrived = true;
                }
            }
        });
        siddhiAppRuntime.start();
        SiddhiTestHelper.waitForEvents(100, 1, count, 60000);
        AssertJUnit.assertEquals(1, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testPutAllFunctionExtension2() throws InterruptedException {
        log.info("PutAllFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = ("@info(name = 'query1') from startTrigger "
                + "select map:create(1 , 'one' ,  2 , 'two' , 3 , 'three') as map1, "
                + "map:create(4 , 'four' ,  5 , 'five' , 6 , 'six') as map2, "
                + "map:create(7 , 'seven' ,  8 , 'eight' , 9 , 'nine') as map3 "
                + "insert into tmpStream;"
                + "@info(name = 'query2') from tmpStream  "
                + "select map:putAll(map:putAll(map1, map2), map3) as newMap "
                + "insert into outputStream;"
        );

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                for (Event event : events) {
                    EventPrinter.print(events);
                    count.incrementAndGet();
                    HashMap map = (HashMap) event.getData(0);
                    AssertJUnit.assertEquals("one", map.get(1));
                    AssertJUnit.assertEquals("six", map.get(6));
                    AssertJUnit.assertEquals("nine", map.get(9));
                    eventArrived = true;
                }
            }
        });
        siddhiAppRuntime.start();
        SiddhiTestHelper.waitForEvents(100, 1, count, 60000);
        AssertJUnit.assertEquals(1, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(expectedExceptions = SiddhiAppCreationException.class)
    public void testPutAllFunctionExtension1() throws InterruptedException {
        log.info("PutAllFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = ("@info(name = 'query1') from startTrigger "
                + "select map:create() as map1, map:create() as map2, map:create() as map3 "
                + "insert into tmpStream;"
                + "@info(name = 'query2') from tmpStream select map1, map:putAll(map1, map2, map3) as newMap"
                + " insert into outputStream;"
        );
        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }
}
