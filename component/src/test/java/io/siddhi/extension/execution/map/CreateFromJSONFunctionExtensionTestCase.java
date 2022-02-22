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
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.stream.output.StreamCallback;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import io.siddhi.extension.execution.map.test.util.UnitTestAppender;
import io.siddhi.extension.execution.string.ConcatFunctionExtension;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateFromJSONFunctionExtensionTestCase {
    private static final Logger log = (Logger) LogManager.getLogger(CreateFromJSONFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testCreateFromJSONFunctionExtension() throws InterruptedException {
        log.info("CreateFromJSONFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromJSON(\"{'symbol':'IBM','price':100,'volume':100 }\") " +
                "as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        HashMap map = (HashMap) event.getData(0);
                        AssertJUnit.assertEquals("IBM", map.get("symbol"));
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
    public void testCreateFromJSONFunctionExtension2() throws InterruptedException {
        log.info("CreateFromJSONFunctionExtension TestCase 2");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromJSON(str:concat('{symbol :',symbol,', price :',price,', volume :',volume,'}'))" +
                " as hashMap insert into outputStream;");
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    HashMap map = (HashMap) event.getData(0);
                    if (count.get() == 1) {
                        AssertJUnit.assertEquals("IBM", map.get("symbol"));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        AssertJUnit.assertEquals("WSO2", map.get("symbol"));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        AssertJUnit.assertEquals("XYZ", map.get("symbol"));
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
    public void testCreateFromJSONFunctionExtension3() throws InterruptedException {
        log.info("CreateFromJSONFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromJSON(\"{'symbol':'IBM','price':100,'volume':100 }\" , "
                + "\"{'symbol':'IBM','price':100,'volume':100 }\") "
                + "as hashMap insert into outputStream;");

        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }

    @Test
    public void testCreateFromJSONFunctionExtension4() throws InterruptedException {
        log.info("CreateFromJSONFunctionExtension TestCase with test string data format");
        UnitTestAppender appender = new UnitTestAppender("UnitTestAppender", null);
        final Logger logger = (Logger) LogManager.getRootLogger();
        logger.setLevel(Level.ALL);
        logger.addAppender(appender);
        appender.start();
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select " + "map:createFromJSON(\"'symbol':'IBM',"
                + "'price':100,'volume':100 \") as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"IBM", 100, 100L});
        AssertJUnit.assertTrue(((UnitTestAppender) logger.getAppenders().
                get("UnitTestAppender")).getMessages().contains("Cannot create JSON from"));
        siddhiAppRuntime.shutdown();
        logger.removeAppender(appender);
    }

    @Test (expectedExceptions = {SiddhiAppCreationException.class})
    public void testCreateFromJSONFunctionExtension5() throws InterruptedException {
        log.info("CreateFromJSONFunctionExtension TestCase with test data should be a string");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select " + "map:createFromJSON(12) as hashMap insert"
                + " into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

    }
}
