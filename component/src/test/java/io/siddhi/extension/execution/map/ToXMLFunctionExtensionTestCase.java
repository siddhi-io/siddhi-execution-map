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
import io.siddhi.extension.execution.map.test.util.SiddhiTestHelper;
import io.siddhi.extension.execution.map.test.util.UnitTestAppender;
import io.siddhi.extension.execution.string.ConcatFunctionExtension;
import org.apache.axiom.om.impl.exception.XMLComparisonException;
import org.apache.axiom.om.impl.llom.util.XMLComparator;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.stream.XMLStreamException;

public class ToXMLFunctionExtensionTestCase {
    private static Logger log = Logger.getLogger(ToXMLFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testToXMLFunctionExtension() throws InterruptedException {
        log.info("ToXMLFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

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
                "</sensor>\") as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap) as xmlString " +
                "insert into outputStream2;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream2", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(
                                    AXIOMUtil.stringToOM("<commonAttr1>19</commonAttr1>"
                                            + "<commonAttr2>11.45</commonAttr2>" + "<commonAttr3>true</commonAttr3>"
                                            + "<commonAttr4>ELEMENT_TEXT</commonAttr4>"
                                            + "<specAttributesObj><specAttr1>111</specAttr1>" +
                                            "<specAttr2>222</specAttr2></specAttributesObj>"),
                                    AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
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
    public void testCreateFromXMLFunctionExtension2() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase 2");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\n" +
                "define stream inputStream (longAttr long, doubleAttr double, booleanAttr bool, strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "map:createFromXML(str:concat('<sensor><commonAttr1>',longAttr,'</commonAttr1><commonAttr2>'," +
                "doubleAttr,'</commonAttr2><commonAttr3>',booleanAttr,'</commonAttr3><commonAttr4>',strAttr," +
                "'</commonAttr4></sensor>')) " +
                "as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap) as xmlString " +
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
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(
                                    AXIOMUtil.stringToOM("<commonAttr1>25</commonAttr1>"
                                            + "<commonAttr2>100.1</commonAttr2>" + "<commonAttr3>true</commonAttr3>"
                                            + "<commonAttr4>Event1</commonAttr4>"),
                                    AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        }
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(
                                    AXIOMUtil.stringToOM("<commonAttr1>35</commonAttr1>"
                                            + "<commonAttr2>100.11</commonAttr2>" + "<commonAttr3>false</commonAttr3>"
                                            + "<commonAttr4>Event2</commonAttr4>"),
                                    AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        }
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(
                                    AXIOMUtil.stringToOM("<commonAttr1>45</commonAttr1>"
                                            + "<commonAttr2>100.13456</commonAttr2>" + "<commonAttr3>true</commonAttr3>"
                                            + "<commonAttr4>Event3</commonAttr4>"),
                                    AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        }
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

    @Test
    public void testToXMLFunctionExtension3() throws InterruptedException {
        log.info("ToXMLFunctionExtension TestCase 3");
        SiddhiManager siddhiManager = new SiddhiManager();

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
                "</sensor>\") as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap, 'sensor') as xmlString " +
                "insert into outputStream2;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);

        siddhiAppRuntime.addCallback("outputStream2", new StreamCallback() {
            @Override
            public void receive(Event[] inEvents) {
                EventPrinter.print(inEvents);
                for (Event event : inEvents) {
                    count.incrementAndGet();
                    if (count.get() == 1) {
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(
                                    AXIOMUtil.stringToOM("<sensor>" + "<commonAttr1>19</commonAttr1>"
                                            + "<commonAttr2>11.45</commonAttr2>" + "<commonAttr3>true</commonAttr3>"
                                            + "<commonAttr4>ELEMENT_TEXT</commonAttr4>"
                                            + "<specAttributesObj><specAttr1>111</specAttr1>" +
                                            "<specAttr2>222</specAttr2></specAttributesObj>"
                                            + "</sensor>"),
                                    AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
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
    public void testCreateFromXMLFunctionExtension4() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase 4");
        SiddhiManager siddhiManager = new SiddhiManager();
        siddhiManager.setExtension("str:concat", ConcatFunctionExtension.class);

        String inStreamDefinition = "\n" +
                "define stream inputStream (longAttr long, doubleAttr double, booleanAttr bool, strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "map:createFromXML(str:concat('<sensor><commonAttr1>',longAttr,'</commonAttr1><commonAttr2>'," +
                "doubleAttr,'</commonAttr2><commonAttr3>',booleanAttr,'</commonAttr3><commonAttr4>',strAttr," +
                "'</commonAttr4></sensor>')) " +
                "as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap, 'sensor') as xmlString " +
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
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(AXIOMUtil.stringToOM(
                                    "<sensor>" +
                                            "<commonAttr1>25</commonAttr1>" +
                                            "<commonAttr2>100.1</commonAttr2>" +
                                            "<commonAttr3>true</commonAttr3>" +
                                            "<commonAttr4>Event1</commonAttr4>" +
                                            "</sensor>"), AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        }
                        eventArrived = true;
                    }
                    if (count.get() == 2) {
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(AXIOMUtil.stringToOM(
                                    "<sensor>" +
                                            "<commonAttr1>35</commonAttr1>" +
                                            "<commonAttr2>100.11</commonAttr2>" +
                                            "<commonAttr3>false</commonAttr3>" +
                                            "<commonAttr4>Event2</commonAttr4>" +
                                            "</sensor>"), AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        }
                        eventArrived = true;
                    }
                    if (count.get() == 3) {
                        try {
                            AssertJUnit.assertEquals(true, new XMLComparator().compare(AXIOMUtil.stringToOM(
                                    "<sensor>" +
                                            "<commonAttr1>45</commonAttr1>" +
                                            "<commonAttr2>100.13456</commonAttr2>" +
                                            "<commonAttr3>true</commonAttr3>" +
                                            "<commonAttr4>Event3</commonAttr4>" +
                                            "</sensor>"), AXIOMUtil.stringToOM((String) event.getData(0))));
                        } catch (XMLComparisonException e) {
                            log.error("Error comparing two XML elements:" + e.getMessage(), e);
                        } catch (XMLStreamException e) {
                            log.error("Error parsing XML:" + e.getMessage(), e);
                        }
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
    public void testToXMLFunctionExtension5() throws InterruptedException {
        log.info("ToXMLFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();

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
                "</sensor>\") as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap, 'wso2' ,'ibm') as xmlString " +
                "insert into outputStream2;");

        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }

    @Test (expectedExceptions = {SiddhiAppCreationException.class})
    public void testToXMLFunctionExtension6() throws InterruptedException {
        log.info("ToXMLFunctionExtension TestCase with test data should be Map string format  ");
        log = Logger.getLogger(StreamJunction.class);
        UnitTestAppender appender = new UnitTestAppender();
        log.addAppender(appender);
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "symbol,price,volume as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap) as xmlString " +
                "insert into outputStream2;");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
    }

    @Test (expectedExceptions = {SiddhiAppCreationException.class})
    public void testCreateFromXMLFunctionExtension5() throws InterruptedException {
        log.info("CreateFromXMLFunctionExtension TestCase with test Object[] data should be string format");
        log = Logger.getLogger(StreamJunction.class);
        UnitTestAppender appender = new UnitTestAppender();
        log.addAppender(appender);
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine stream inputStream (longAttr long, doubleAttr double, booleanAttr bool,"
                + " strAttr string);";
        String query = ("@info(name = 'query1') from inputStream select " +
                "longAttr, doubleAttr, booleanAttr, strAttr as hashMap insert into outputStream;" +
                "from outputStream " +
                "select map:toXML(hashMap, 'sensor') as xmlString " +
                "insert into outputStream2");

        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.createSiddhiAppRuntime(
                inStreamDefinition + query);
    }
}
