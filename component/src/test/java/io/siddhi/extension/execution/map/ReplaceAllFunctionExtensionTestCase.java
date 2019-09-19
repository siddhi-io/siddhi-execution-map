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

package io.siddhi.extension.execution.map;

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.event.Event;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.core.stream.output.StreamCallback;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ReplaceAllFunctionExtensionTestCase {
    private static final Logger log = Logger.getLogger(ReplaceAllFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testReplaceAllFunctionExtension() throws InterruptedException {
        log.info("ReplaceAllFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = ("@info(name = 'query1') from startTrigger "
                + "select map:create(1 , 'one' ,  2 , 'two' , 3 , 'three') as map1, "
                + "map:create(1 , 'one1' ,  2 , 'two2' , 3 , 'three3') as map2 insert into tmpStream;"
                + "@info(name = 'query2') from tmpStream  select map:replaceAll(map1, map2)" +
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
                    AssertJUnit.assertEquals("one1", map.get(1));
                    AssertJUnit.assertEquals("three3", map.get(3));
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
    public void testReplaceAllFunctionExtension2() throws InterruptedException {
        log.info("ReplaceAllFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = ("@info(name = 'query1') from startTrigger "
                + "select map:create(1 , 'one' ,  2 , 'two' , 3 , 'three') as map1, "
                + "map:create(4 , 'four' ,  5 , 'five' , 6 , 'six') as map2, "
                + "map:create(7 , 'seven' ,  8 , 'eight' , 9 , 'nine') as map3 "
                + "insert into tmpStream;"
                + "@info(name = 'query2') from tmpStream  "
                + "select map:replaceAll(map:replaceAll(map1, map2), map3) as newMap "
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
                    AssertJUnit.assertNull(map.get(6));
                    AssertJUnit.assertNull(map.get(9));
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
    public void testReplaceAllFunctionExtension1() {
        log.info("ReplaceAllFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = ("@info(name = 'query1') from startTrigger "
                + "select map:create() as map1, map:create() as map2, map:create() as map3 "
                + "insert into tmpStream;"
                + "@info(name = 'query2') from tmpStream select map1, map:replaceAll(map1, map2, map3) as newMap"
                + " insert into outputStream;"
        );
        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }
}
