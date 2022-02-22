/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import io.siddhi.core.stream.StreamJunction;
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.stream.output.StreamCallback;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import io.siddhi.extension.execution.map.test.util.UnitTestAppender;
import io.siddhi.extension.execution.string.ConcatFunctionExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ToJSONFunctionExtensionTestCase {
    private static final Logger log = (Logger) LogManager.getLogger(ToJSONFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testToJSONFunctionExtension() throws InterruptedException {
        log.info("ToJSONFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromJSON(\"{'symbol':'WSO2','price':100,'volume':100,'last5val':" +
                "{'price':150,'volume':200}}\") as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toJSON(hashMap) as jsonString " +
                "insert into outputStream2");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream2", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        AssertJUnit.assertEquals(event.getData(0) instanceof String, true);
                        try {
                            JSONAssert.assertEquals(new JSONObject("{\"volume\":100,\"symbol\":\"WSO2\"," +
                                            "\"price\":100,\"last5val\":{\"volume\":200,\"price\":150}}"),
                                    new JSONObject((String) event.getData(0)), false);
                        } catch (JSONException e) {
                            log.error(e);
                            AssertJUnit.fail(e.getMessage());
                        }
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"IBM", 100, 100L});
        inputHandler.send(new Object[]{"WSO2", 200, 200L});
        inputHandler.send(new Object[]{"XYZ", 300, 200L});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testToJSONFunctionExtension2() throws InterruptedException {
        log.info("ToJSONFunctionExtension TestCase 2");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromJSON(str:concat('{symbol :',symbol,', price :',price,', volume :',volume,'}')) " +
                "as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toJSON(hashMap) as jsonString " +
                "insert into outputStream2");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream2", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        AssertJUnit.assertEquals(event.getData(0) instanceof String, true);
                        try {
                            JSONAssert.assertEquals(new
                                            JSONObject("{\"volume\":100,\"symbol\":\"IBM\",\"price\":100}"),
                                    new JSONObject((String) event.getData(0)), false);
                        } catch (JSONException e) {
                            log.error(e);
                            AssertJUnit.fail(e.getMessage());
                        }
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        AssertJUnit.assertEquals(event.getData(0) instanceof String, true);
                        try {
                            JSONAssert.assertEquals(new
                                            JSONObject("{\"volume\":200,\"symbol\":\"WSO2\",\"price\":200}"),
                                    new JSONObject((String) event.getData(0)), false);
                        } catch (JSONException e) {
                            log.error(e);
                            AssertJUnit.fail(e.getMessage());
                        }
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        AssertJUnit.assertEquals(event.getData(0) instanceof String, true);
                        try {
                            JSONAssert.assertEquals(new
                                            JSONObject("{\"volume\":200,\"symbol\":\"XYZ\",\"price\":300}"),
                                    new JSONObject((String) event.getData(0)), false);
                        } catch (JSONException e) {
                            log.error(e);
                            AssertJUnit.fail(e.getMessage());
                        }
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"IBM", 100, 100L});
        inputHandler.send(new Object[]{"WSO2", 200, 200L});
        inputHandler.send(new Object[]{"XYZ", 300, 200L});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(expectedExceptions = SiddhiAppCreationException.class)
    public void testToJSONFunctionExtension3() throws InterruptedException {
        log.info("ToJSONFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromJSON(\"{'symbol':'WSO2','price':100,'volume':100,'last5val':{'price':150,'volume'"
                + ":200}}\") as hashMap insert into outputStream;"
                + "from outputStream "
                + "select map:toJSON() as jsonString "
                + "insert into outputStream2");
        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }

    @Test (expectedExceptions = {SiddhiAppCreationException.class})
    public void testToJSONFunctionExtension4() throws InterruptedException {
        log.info("ToJSONFunctionExtension TestCase with data should be a Map string format");
        Logger logger = (Logger) LogManager.getLogger(StreamJunction.class);
        UnitTestAppender appender = new UnitTestAppender("UnitTestAppender", null);
        logger.addAppender(appender);
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "symbol, price, volume as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toJSON(hashMap) as jsonString " +
                "insert into outputStream2");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"IBM", 100, 100L});
        AssertJUnit.assertTrue(appender.getMessages().contains("Data should be a string"));
        siddhiAppRuntime.shutdown();
    }
}
