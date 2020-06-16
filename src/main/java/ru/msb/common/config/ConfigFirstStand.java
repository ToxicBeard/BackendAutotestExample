package ru.msb.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import ru.msb.common.setting.YamlProppertySourceFactory;

@Profile("first")
@EnableConfigurationProperties
@ComponentScan(basePackages = "ru.msb")
@PropertySource(value = "file:config\\firstStandConfig.yml", factory = YamlProppertySourceFactory.class)
public class ConfigFirstStand {
}