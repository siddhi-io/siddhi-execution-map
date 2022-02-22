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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateFromXMLFunctionExtensionTestCase {
    private static final Logger log = (Logger) LogManager.getLogger(CreateFromJSONFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testCreateFromXMLFunctionExtension() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromXML(\"<sensor>" +
                "<commonAttr1>19</commonAttr1>" +
                "<commonAttr2>11.45</commonAttr2>" +
                "<commonAttr3>true</commonAttr3>" +
                "<commonAttr4>ELEMENT_TEXT</commonAttr4>" +
                "<specAttributesObj>" +
                "<specAttr1>111</specAttr1>" +
                "<specAttr2>222</specAttr2>" +
                "</specAttributesObj>" +
                "</sensor>\") as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        Map map = (Map) event.getData(0);
                        AssertJUnit.assertEquals(19L, map.get("commonAttr1"));
                        AssertJUnit.assertEquals(11.45d, map.get("commonAttr2"));
                        AssertJUnit.assertEquals(true, map.get("commonAttr3"));
                        AssertJUnit.assertEquals("ELEMENT_TEXT", map.get("commonAttr4"));
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
    public void testCreateFromXMLFunctionExtension2() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase 2");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream " +
                "(longAttr long, doubleAttr double, booleanAttr bool, strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "map:createFromXML(str:concat('<sensor><commonAttr1>',longAttr,'</commonAttr1><commonAttr2>'," +
                "doubleAttr,'</commonAttr2><commonAttr3>',booleanAttr,'</commonAttr3><commonAttr4>',strAttr," +
                "'</commonAttr4></sensor>')) " +
                "as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    Map map = (Map) event.getData(0);
                    if (count.get() == 1) {
                        AssertJUnit.assertEquals(25L, map.get("commonAttr1"));
                        AssertJUnit.assertEquals(100.1d, map.get("commonAttr2"));
                        AssertJUnit.assertEquals(true, map.get("commonAttr3"));
                        AssertJUnit.assertEquals("Event1", map.get("commonAttr4"));
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        AssertJUnit.assertEquals(35L, map.get("commonAttr1"));
                        AssertJUnit.assertEquals(100.11d, map.get("commonAttr2"));
                        AssertJUnit.assertEquals(false, map.get("commonAttr3"));
                        AssertJUnit.assertEquals("Event2", map.get("commonAttr4"));
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        AssertJUnit.assertEquals(45L, map.get("commonAttr1"));
                        AssertJUnit.assertEquals(100.13456d, map.get("commonAttr2"));
                        AssertJUnit.assertEquals(true, map.get("commonAttr3"));
                        AssertJUnit.assertEquals("Event3", map.get("commonAttr4"));
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{25, 100.1, true, "Event1"});
        inputHandler.send(new Object[]{35, 100.11, false, "Event2"});
        inputHandler.send(new Object[]{45, 100.13456, true, "Event3"});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }

    @Test(expectedExceptions = SiddhiAppCreationException.class)
    public void testCreateFromXMLFunctionExtension3() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (longAttr long, doubleAttr double, booleanAttr bool,"
                + " strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "map:createFromXML(str:concat('<sensor><commonAttr1>',longAttr,'</commonAttr1><commonAttr2>'," +
                "doubleAttr,'</commonAttr2><commonAttr3>',booleanAttr,'</commonAttr3><commonAttr4>',strAttr," +
                "'</commonAttr4></sensor>')," +
                "str:concat('<sensor><commonAttr1>',longAttr,'</commonAttr1><commonAttr2>'," +
                "doubleAttr,'</commonAttr2><commonAttr3>',booleanAttr,'</commonAttr3><commonAttr4>',strAttr," +
                "'</commonAttr4></sensor>')) " +
                "as hashMap insert into outputStream;");

        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }

    @Test
    public void testCreateFromXMLFunctionExtension4() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase with test string data has xml format");
        UnitTestAppender appender = new UnitTestAppender("UnitTestAppender", null);
        final Logger logger = (Logger) LogManager.getRootLogger();
        logger.setLevel(Level.ALL);
        logger.addAppender(appender);
        appender.start();
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (longAttr long, doubleAttr double, booleanAttr bool,"
                + " strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "map:createFromXML(str:concat('{<sensor><commonAttr1>',longAttr,'</commonAttr1><commonAttr2>'," +
                "doubleAttr,'</commonAttr2><commonAttr3>',booleanAttr,'</commonAttr3><commonAttr4>',strAttr," +
                "'</commonAttr4></sensor>}')) " +
                "as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{25, 100.1, true, "Event1"});
        AssertJUnit.assertTrue(((UnitTestAppender) logger.getAppenders().
                get("UnitTestAppender")).getMessages().contains("Unexpected character"));
        siddhiAppRuntime.shutdown();
        logger.removeAppender(appender);
    }

    @Test (expectedExceptions = {SiddhiAppCreationException.class})
    public void testCreateFromXMLFunctionExtension5() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase with test data is to be a string");
        UnitTestAppender appender = new UnitTestAppender("UnitTestAppender", null);
        final Logger logger = (Logger) LogManager.getRootLogger();
        logger.setLevel(Level.ALL);
        logger.addAppender(appender);
        appender.start();
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select "
                + "map:createFromXML(12) as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"IBM", 100, 100L});
        AssertJUnit.assertTrue(((UnitTestAppender) logger.getAppenders().
                get("UnitTestAppender")).getMessages().contains("Data should be a string"));
        siddhiAppRuntime.shutdown();
        logger.removeAppender(appender);
    }

    @Test
    public void testCreateFromXMLFunctionExtension6() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase 6");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\ndefine stream inputStream " +
                "(longAttr long, doubleAttr double, booleanAttr bool, strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "map:createFromXML(str:concat('<parent><commonAttr1>',longAttr,'</commonAttr1>" +
                "<commonAttr2>'," +
                "doubleAttr,'</commonAttr2><sensor><commonAttr3>',booleanAttr,'</commonAttr3></sensor><commonAttr4>'," +
                "strAttr,'</commonAttr4></parent>')) as hashMap insert into outputStream;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
        siddhiAppRuntime.addCallback("outputStream", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    Map map = (Map) event.getData(0);
                    if (count.get() == 1) {
                        Map<String, Boolean> innerMap = new HashMap<String, Boolean>();
                        innerMap.put("commonAttr3", true);
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 25L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 100.1d);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "Event1");
                        AssertJUnit.assertEquals(map.get("sensor"), innerMap);
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        Map<String, Boolean> innerMap = new HashMap<String, Boolean>();
                        innerMap.put("commonAttr3", false);
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 35L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 100.11d);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "Event2");
                        AssertJUnit.assertEquals(map.get("sensor"), innerMap);
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        Map<String, Boolean> innerMap = new HashMap<String, Boolean>();
                        innerMap.put("commonAttr3", true);
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 45L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 100.13456d);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "Event3");
                        AssertJUnit.assertEquals(map.get("sensor"), innerMap);
                        eventArrived = true;
                    }
                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{25, 100.1, true, "Event1"});
        inputHandler.send(new Object[]{35, 100.11, false, "Event2"});
        inputHandler.send(new Object[]{45, 100.13456, true, "Event3"});
        SiddhiTestHelper.waitForEvents(100, 3, count, 60000);
        AssertJUnit.assertEquals(3, count.get());
        AssertJUnit.assertTrue(eventArrived);
        siddhiAppRuntime.shutdown();
    }
}
