package com.shabushabu.javashop.shop.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Workaround for CVE-2022-22965 (Spring4Shell).
 * Disallows class-level property access through data binding to prevent RCE.
 * This is the recommended mitigation for Spring Framework versions that cannot
 * be upgraded to 5.3.18+ or 5.2.20+.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class Spring4ShellMitigationAdvice {

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        String[] denylist = new String[]{
            "class.*", "Class.*", "*.class.*", "*.Class.*"
        };
        dataBinder.setDisallowedFields(denylist);
    }
}
