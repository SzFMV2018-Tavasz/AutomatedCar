package hu.oe.nik.szfmv.automatedcar.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigProviderTest {

    @Test
    public void provideTest() {
        assertEquals(false, ConfigProvider.provide().getBoolean("general.debug"));
    }
}
