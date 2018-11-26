package org.wso2.extension.siddhi.execution.map;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.exception.SiddhiAppRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.Map;

/**
 * putAll(HashMap , HashMap)
 * Returns the updated hashmap.
 * Accept Type(s): (HashMap , HashMap)
 * Return Type(s): HashMap
 */
@Extension(
        name = "putAll",
        namespace = "map",
        description = "Returns the updated map1 after copying all of the mappings from the specified map2. "
                + "If you have duplicate keys, map2 will overwrite the values in map1.",
        parameters = {
                @Parameter(name = "map1",
                        description = "Map to be merged",
                        type = DataType.OBJECT,
                        optional = false
                ),
                @Parameter(name = "map2",
                        description = "Map to be merged",
                        type = DataType.OBJECT,
                        optional = false
                )
        },
        examples = @Example(
                description = "Returns the updated map named map1 after adding each mapping from map2.",
                syntax = "putAll(map1 , map2)"),
        returnAttributes = @ReturnAttribute(description = "A hashMap will be returned", type = DataType.OBJECT)
)
public class PutAllFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:putAll() function, " +
                    "required 2 parameters, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        if (data == null) {
            throw new SiddhiAppRuntimeException("Data can not be null.");
        }
        Map<Object, Object> map1 = (Map<Object, Object>) data[0];
        Map<Object, Object> map2 = (Map<Object, Object>) data[1];
        map1.putAll(map2);
        return map1;
    }

    @Override
    protected Object execute(Object data) {
        //Since the map:putAll() function takes in 2 parameters, this method does not get called.
        // Hence, not implemented.
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

    @Override
    public Map<String, Object> currentState() {
        //No need to maintain a state.
        return null;
    }

    @Override
    public void restoreState(Map<String, Object> state) {
        //Since there's no need to maintain a state, nothing needs to be done here.
    }
}
