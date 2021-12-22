package ru.pepper.at.template;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/config.properties"
})
public interface Props extends Config {

    @Key("pepper.url")
    String pepperUrl();

    @Key("timeout")
    @DefaultValue("8000")
    int timeout();

    @Key("remote.url")
    String remoteUrl();
}
