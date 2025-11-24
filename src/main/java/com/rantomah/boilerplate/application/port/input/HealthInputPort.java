package com.rantomah.boilerplate.application.port.input;

import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface HealthInputPort {

    ResponseEntity<Map<String, Object>> health();
}
