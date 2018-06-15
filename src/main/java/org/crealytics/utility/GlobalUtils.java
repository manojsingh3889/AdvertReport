package org.crealytics.utility;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * Global utility contains utility functions for regular use
 */
public class GlobalUtils {


    public static <T> T parseValue(Class<T> clazz, String value){
        PropertyEditor editor = PropertyEditorManager.findEditor(clazz);
        editor.setAsText(value);
        return (T)editor.getValue();
    }
}
