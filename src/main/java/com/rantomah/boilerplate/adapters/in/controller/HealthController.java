package com.rantomah.boilerplate.adapters.in.controller;

import com.rantomah.boilerplate.core.BaseController;
import com.rantomah.boilerplate.core.helper.I18nHelper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthController extends BaseController {

    private final I18nHelper i18nHelper;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return success(Map.of("status", i18nHelper.get("message.info.health.status")));
    }

}
