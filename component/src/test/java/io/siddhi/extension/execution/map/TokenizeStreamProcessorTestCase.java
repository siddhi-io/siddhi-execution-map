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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenizeStreamProcessorTestCase {
    private static final Logger log = Logger.getLogger(TokenizeStreamProcessorTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;
    private List<Object[]> inEventsList;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
        inEventsList = new ArrayList<>();
    }

    @Test
    public void testTokenizeStreamProcessor1() throws InterruptedException {
        log.info("Tokenize Stream Processor TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = (
                " @info(name = 'query1') "
                + "from startTrigger "
                + "select map:create(1 , 'one' , 2 , 'two' , 3 , 'three', 4 , 'four' ,  5 , 'five' , 6 , 'six') as map "
                + "insert into tmpStream;"
                + "@info(name = 'query2') "
                + "from tmpStream#map:tokenize(map) "
                + "select key, value "
                + "insert into TokenizedStream; "
        );

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.addCallback("TokenizedStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                EventPrinter.print(events);
                for (Event event : events) {
                    eventArrived = true;
                    count.incrementAndGet();
                    inEventsList.add(event.getData());
                }
            }
        });
        siddhiAppRuntime.start();
        SiddhiTestHelper.waitForEvents(100, 6, count, 60000);

        List<Object[]> expected = Arrays.asList(
                new Object[]{1, "one"},
                new Object[]{2, "two"},
                new Object[]{3, "three"},
                new Object[]{4, "four"},
                new Object[]{5, "five"},
                new Object[]{6, "six"}
                );
        AssertJUnit.assertTrue(eventArrived);
        AssertJUnit.assertEquals(6, count.get());
        AssertJUnit.assertTrue("Events do not match", SiddhiTestHelper.isEventsMatch(expected, inEventsList));

        siddhiAppRuntime.shutdown();
    }

    @Test(expectedExceptions = SiddhiAppCreationException.class)
    public void testTokenizeStreamProcessor2() {
        log.info("Tokenize Stream Processor TestCase - More than one attribute");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = (
                "@info(name = 'query1') "
                        + "from startTrigger "
                        + "select map:create(1 , 'one' , 2 , 'two' , 3 , 'three') as map1, " +
                        " map:create(4 , 'four' ,  5 , 'five' , 6 , 'six') as map2 "
                        + "insert into tmpStream;"
                        + "@info(name = 'query2') "
                        + "from tmpStream#map:tokenize(map1, '2') "
                        + "select key, value "
                        + "insert into TokenizedStream; "

        );

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testTokenizeStreamProcessor3() throws InterruptedException {
        log.info("Tokenize Stream Processor TestCase - Use string object");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine trigger startTrigger at 'start';";
        String query = (
                "@info(name = 'query1') "
                + "from startTrigger "
                + "select map:create(1 , 'one' , 2 , 'two' , 3 , 'three') as map1, "
                + " map:create(4 , 'four' ,  5 , 'five' , 6 , 'six') as map2 "
                + "insert into tmpStream;"
                + "@info(name = 'query2') "
                + "from tmpStream#map:tokenize(map1, map2) "
                + "select key, value "
                + "insert into TokenizedStream; "

        );
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.addCallback("TokenizedStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                EventPrinter.print(events);
                for (Event event : events) {
                    eventArrived = true;
                    count.incrementAndGet();
                    inEventsList.add(event.getData());
                }
            }
        });
        siddhiAppRuntime.start();
        SiddhiTestHelper.waitForEvents(100, 6, count, 60000);

        List<Object[]> expected = Arrays.asList(
                new Object[]{1, new ArrayList<>(Arrays.asList("one", null))},
                new Object[]{2, new ArrayList<>(Arrays.asList("two", null))},
                new Object[]{3, new ArrayList<>(Arrays.asList("three", null))},
                new Object[]{4, new ArrayList<>(Arrays.asList(null, "four"))},
                new Object[]{5, new ArrayList<>(Arrays.asList(null, "five"))},
                new Object[]{6, new ArrayList<>(Arrays.asList(null, "six"))}
        );
        AssertJUnit.assertTrue(eventArrived);
        AssertJUnit.assertEquals(6, count.get());
        AssertJUnit.assertTrue("Events do not match", SiddhiTestHelper.isUnsortedEventsMatch(expected, inEventsList));

        siddhiAppRuntime.shutdown();
    }
}
