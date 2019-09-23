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

import io.siddhi.core.SiddhiAppRuntime;
import io.siddhi.core.SiddhiManager;
import io.siddhi.core.event.Event;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.stream.output.StreamCallback;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectAggregatorFunctionTestCase {
    private static final Logger log = Logger.getLogger(CollectAggregatorFunctionTestCase.class);
    private boolean eventArrived;
    private int inEventCount;
    private List<Object[]> actual;

    @BeforeMethod
    public void init() {
        inEventCount = 0;
        eventArrived = false;
        actual = new ArrayList<>();
    }

    @Test
    public void testCollectAggregator1() throws InterruptedException {

        log.info("testCollectAggregator1 TestCase 1");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream cscStream(key string, value string);";
        String query = ("@info(name = 'query1') " +
                "from cscStream#window.lengthBatch(3) " +
                "select map:collect(key, value) as studentDetails " +
                "insert into StudentSteam;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition +
                query);

        siddhiAppRuntime.addCallback("StudentSteam", new StreamCallback() {
            @Override
            public void receive(Event[] events) {

                EventPrinter.print(events);
                eventArrived = true;
                for (Event event : events) {
                    inEventCount++;
                    if (inEventCount == 1) {
                        HashMap<String, String> studentDetails = (HashMap) event.getData(0);
                        studentDetails.forEach((key, value) -> {
                            switch (key) {
                                case "name":
                                    AssertJUnit.assertEquals("Harry", value);
                                    break;
                                case "middleName":
                                    AssertJUnit.assertEquals("Henry", value);
                                    break;
                                case "surName":
                                    AssertJUnit.assertEquals("Potter", value);
                                    break;
                                default:
                                    AssertJUnit.fail();
                            }
                        });
                    } else {
                        AssertJUnit.fail();
                    }
                }
            }

        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cscStream");
        siddhiAppRuntime.start();

        inputHandler.send(new Object[]{"name", "Harry"});
        inputHandler.send(new Object[]{"middleName", "Henry"});
        inputHandler.send(new Object[]{"surName", "Potter"});
        inputHandler.send(new Object[]{"age", 18});
        SiddhiTestHelper.waitForEvents(2000, 1, inEventCount, 60000);
        AssertJUnit.assertTrue(eventArrived);
        AssertJUnit.assertEquals(1, inEventCount);
        siddhiAppRuntime.shutdown();

    }

    @Test
    public void testCollectAggregator2() throws InterruptedException {

        log.info("testCollectAggregator1 TestCase 1");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream cscStream(key string, value string);";
        String query = ("@info(name = 'query1') " +
                "from cscStream#window.length(2) " +
                "select map:collect(key, value) as studentDetails " +
                "insert into StudentSteam;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition +
                query);

        siddhiAppRuntime.addCallback("StudentSteam", new StreamCallback() {
            @Override
            public void receive(Event[] events) {

                EventPrinter.print(events);
                eventArrived = true;
                for (Event event : events) {
                    inEventCount++;
                    actual.add(event.getData());
                }
            }

        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("cscStream");
        siddhiAppRuntime.start();

        Map<String, String> entry1 = new HashMap<>();
        entry1.put("name", "Harry");

        Map<String, String> entry2 = new HashMap<>();
        entry2.put("name", "Harry");
        entry2.put("middleName", "Henry");


        Map<String, String> entry3 = new HashMap<>();
        entry3.put("middleName", "Henry");
        entry3.put("surName", "Potter");

        List<Object[]> expected = Arrays.asList(
                new Object[]{entry1},
                new Object[]{entry2},
                new Object[]{entry3}
        );

        inputHandler.send(new Object[]{"name", "Harry"});
        inputHandler.send(new Object[]{"middleName", "Henry"});
        inputHandler.send(new Object[]{"surName", "Potter"});
        SiddhiTestHelper.waitForEvents(2000, 3, inEventCount, 60000);
        AssertJUnit.assertTrue(eventArrived);
        AssertJUnit.assertEquals(3, inEventCount);
        AssertJUnit.assertTrue(SiddhiTestHelper.isEventsMatch(actual, expected));
        siddhiAppRuntime.shutdown();
    }

    @Test(expectedExceptions = SiddhiAppCreationException.class)
    public void testCollectAggregator3() {

        log.info("testCollectAggregator3 TestCase 1");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream cscStream(key string, value string);";
        String query = ("@info(name = 'query1') " +
                "from cscStream#window.length(2) " +
                "select map:collect(key) as studentDetails " +
                "insert into StudentSteam;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition +
                query);

        siddhiAppRuntime.start();
        siddhiAppRuntime.shutdown();
    }
}
