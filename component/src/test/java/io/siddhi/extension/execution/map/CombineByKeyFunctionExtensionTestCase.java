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
import io.siddhi.core.stream.input.InputHandler;
import io.siddhi.core.stream.output.StreamCallback;
import io.siddhi.core.util.EventPrinter;
import io.siddhi.core.util.SiddhiTestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CombineByKeyFunctionExtensionTestCase {
    private static final Logger log = LogManager.getLogger(CombineByKeyFunctionExtensionTestCase.class);
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean eventArrived;

    @BeforeMethod
    public void init() {
        count.set(0);
        eventArrived = false;
    }

    @Test
    public void testCombineByKeyFunctionExtension() throws InterruptedException {
        log.info("CombineByKeyFunctionExtension TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') " +
                "from inputStream select symbol,price, "
                + "map:create(symbol,price) as tmpMap insert into tmpStream;"
                + "@info(name = 'query2') "
                + "from tmpStream "
                + "select symbol,price,tmpMap, "
                + "map:combineByKey(map:create('IBM', 300), map:create('WSO2', 300), tmpMap) as newmap "
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
                    eventArrived = true;
                    HashMap map = (HashMap) event.getData(3);

                    ArrayList ibm = (ArrayList) map.get("IBM");
                    AssertJUnit.assertEquals(300, ibm.get(0));
                    AssertJUnit.assertNull(ibm.get(1));
                    AssertJUnit.assertEquals(100, ibm.get(2));


                    ArrayList wso2 = (ArrayList) map.get("WSO2");
                    AssertJUnit.assertNull(wso2.get(0));
                    AssertJUnit.assertEquals(300, wso2.get(1));
                    AssertJUnit.assertNull(wso2.get(2));
                }
            }
        });


        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{"IBM", 100, 100L});

        SiddhiTestHelper.waitForEvents(100, 1, count, 60000);
        AssertJUnit.assertTrue(eventArrived);
        AssertJUnit.assertEquals(1, count.get());
        siddhiAppRuntime.shutdown();
    }


    @Test(expectedExceptions = SiddhiAppCreationException.class)
    public void testCombineByKeyFunctionExtension1() {
        log.info("CombineByKeyFunctionExtension TestCase with test attributeExpressionExecutors length");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "\ndefine stream inputStream (symbol string, price long, volume long);";
        String query = ("@info(name = 'query1') from inputStream select symbol,price, "
                + "map:create() as tmpMap insert into tmpStream;"
                + "@info(name = 'query2') "
                + "from tmpStream "
                + " select symbol,price,tmpMap, map:combinedByKey(tmpMap,symbol) as newmap"
                + " insert into outputStream;"
        );

        siddhiManager.createSiddhiAppRuntime(inStreamDefinition + query);
    }
}
