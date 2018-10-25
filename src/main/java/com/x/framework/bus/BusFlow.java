package com.x.framework.bus;

import java.lang.reflect.Method;
import java.util.List;

import com.x.framework.model.BaseModel;
import com.x.framework.service.BaseService;

public class BusFlow {
    private final static String VOID = "void";
    private final static String BUS_FLOW_EXCEPTION = "BusService required input is BaseModel";
    private List<BusService> busServiceFlow;

    public List<BusService> getBusServiceFlow() {
        return busServiceFlow;
    }

    public void setBusServiceFlow(List<BusService> busServiceFlow) {
        this.busServiceFlow = busServiceFlow;
    }

    public Object execute(Object... params) throws Exception {
        Object returnValue = null;
        if (busServiceFlow != null) {
            int size = busServiceFlow.size();
            if (size > 0) {
                BusService busService0 = busServiceFlow.get(0);
                Object service0 = busService0.getService();
                String methodName0 = busService0.getMethodName();
                Class[] paramTypes = null;
                if (params != null) {
                    int length = params.length;
                    if (length > 0) {
                        paramTypes = new Class[length];
                        for (int i = 0; i < length; i++) {
                            paramTypes[i] = params[i].getClass();
                        }
                    }
                }
                Method method0 = service0.getClass().getMethod(methodName0, paramTypes);
                returnValue = method0.invoke(service0, params);

                if (size > 1) {
                    List<String> rules;
                    for (int i = 1; i < busServiceFlow.size(); i++) {
                        boolean validateFlag = true;
                        BusService busService = busServiceFlow.get(i);
                        rules = busService.getRules();
                        if (rules != null) {
                            if (BaseModel.class.isInstance(returnValue)) {
                                if (!RuleEngine.validateRule((BaseModel) returnValue, rules)) {
                                    validateFlag = false;
                                }
                            } else {
                                throw new Exception(BUS_FLOW_EXCEPTION);
                            }
                        }
                        if (validateFlag) {
                            Object service = busService.getService();
                            String methodName = busService.getMethodName();
                            Method method = service.getClass().getMethod(methodName, returnValue.getClass());
                            Object value = method.invoke(service, returnValue);
                            Class returnType = method.getReturnType();
                            if (!returnType.toString().equals(VOID)) {
                                returnValue = value;
                            }
                        }
                    }
                }
            }
        }
        return returnValue;
    }

}
