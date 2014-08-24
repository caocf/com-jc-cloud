package com.jc.base.util;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: qiaolei
 * Date: 13-5-2 Time: 上午10:02
 */
public class ErrorsUtil {

    public final static void rejectErrors(Model model, String... errorMsgs) {
        List<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

        if (errorMsgs != null) {
            for (String message : errorMsgs) {
                errorObjects.add(new ErrorObject(message));
            }
        }
        List<ErrorObject> existed = (List<ErrorObject>) model.asMap().get("errors");
        if (existed != null) {
            errorObjects.addAll(0, existed);
        }

        model.addAttribute("errors", errorObjects);
    }
}