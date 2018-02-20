package hu.oe.nik.szfmv;

import org.junit.Test;

import static org.junit.Assert.*;

public class XmlObjectTest {

    @Test
    public void testXmlObjectConstructor() {
        XmlObject xmlObject = new XmlObject("road", 10, 20, Math.PI);
        assertEquals("road", xmlObject.type);
        assertEquals(10, xmlObject.position.x);
        assertEquals(20, xmlObject.position.y);
        assertEquals(Math.PI, xmlObject.rotation, 0.001);
    }
}