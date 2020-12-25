package cn.godk.macaque.spring.v2;

import cn.godk.macaque.spring.beans.propertieseditors.CustomNumberEditor;
import org.junit.Assert;
import org.junit.Test;

public class CustomNumberEditorTest {

    @Test
    public void testConvertString() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) editor.getValue()).intValue());


        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);


        try {
            editor.setAsText("3.1");

        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();

    }

}
