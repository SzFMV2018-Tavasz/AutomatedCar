package hu.oe.nik.szfmv.automatedcar.common;

import com.moandjiezana.toml.Toml;

public class ConfigProvider {

    public static Toml provide(){
        return new Toml().read(ClassLoader.getSystemResourceAsStream("config.toml"));
    }

}
