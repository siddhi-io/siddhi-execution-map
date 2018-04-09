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

package org.wso2.extension.siddhi.execution.map;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wso2.extension.siddhi.execution.map.test.util.SiddhiTestHelper;
import org.wso2.extension.siddhi.execution.map.test.util.UnitTestAppender;
import org.wso2.extension.siddhi.execution.string.ConcatFunctionExtension;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.exception.SiddhiAppCreationException;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;
import org.wso2.siddhi.core.util.EventPrinter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateFromXMLFunctionExtensionTestCase {
    private static Logger log = Logger.getLogger(CreateFromXMLFunctionExtensionTestCase.class);
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
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 19L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 11.45d);
                        AssertJUnit.assertEquals(map.get("commonAttr3"), true);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "ELEMENT_TEXT");
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
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 25L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 100.1d);
                        AssertJUnit.assertEquals(map.get("commonAttr3"), true);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "Event1");
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 35L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 100.11d);
                        AssertJUnit.assertEquals(map.get("commonAttr3"), false);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "Event2");
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        AssertJUnit.assertEquals(map.get("commonAttr1"), 45L);
                        AssertJUnit.assertEquals(map.get("commonAttr2"), 100.13456d);
                        AssertJUnit.assertEquals(map.get("commonAttr3"), true);
                        AssertJUnit.assertEquals(map.get("commonAttr4"), "Event3");
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
        log = Logger.getLogger(FunctionExecutor.class);
        UnitTestAppender appender = new UnitTestAppender();
        log.addAppender(appender);
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
        AssertJUnit.assertTrue(appender.getMessages().contains("Unexpected character"));
        siddhiAppRuntime.shutdown();
    }

    @Test
    public void testCreateFromXMLFunctionExtension5() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase with test data is to be a string");
        log = Logger.getLogger(FunctionExecutor.class);
        UnitTestAppender appender = new UnitTestAppender();
        log.addAppender(appender);
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
        AssertJUnit.assertTrue(appender.getMessages().contains("Data should be a string"));
        siddhiAppRuntime.shutdown();
    }
}
