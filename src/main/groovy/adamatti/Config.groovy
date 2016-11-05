package adamatti

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.stereotype.Component

@Component
class Config extends PropertyPlaceholderConfigurer {
    private static ConfigObject cfg

    static {
        def config = this.class.getResource("/config.groovy")
        if (!config){
            config = new File("src/main/resources/config.groovy")
        }
        cfg = new ConfigSlurper().parse(config.text)
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        props.putAll(cfg.toProperties())
    }
}
