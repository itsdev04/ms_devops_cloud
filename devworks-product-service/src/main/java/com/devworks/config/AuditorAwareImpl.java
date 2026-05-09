package com.devworks.config;

import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    // For a simple start, return a fixed value.
    // Later, this can be linked to your Login system.
    return Optional.of("itsdev04");
  }
}
