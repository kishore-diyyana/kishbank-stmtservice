package com.kishbank.stmtservice.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class hold the config attributes of this application.
 *
 * @author Kishore Diyyana
 */
@Component
@ConfigurationProperties(prefix = "com.kishbank.stmtservice")
public class StmtProperties {

    private Long inputFeedSize;

    public Long getInputFeedSize() {
        return inputFeedSize;
    }

    public void setInputFeedSize(Long inputFeedSize) {
        this.inputFeedSize = inputFeedSize;
    }
}
