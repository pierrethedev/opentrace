package br.com.opentrace.opentrace.validation;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class DomainValidator {

    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
        "(?i)^(?=.{1,253}$)(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z]{2,63}$"
    );

    public boolean isValid(String domain) {
        if (domain == null || domain.isBlank()) {
            return false;
        }

        return DOMAIN_PATTERN.matcher(domain.trim()).matches();
    }
}
