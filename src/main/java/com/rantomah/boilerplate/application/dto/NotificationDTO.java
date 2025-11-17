package com.rantomah.boilerplate.application.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private String type;
    private String template;
    private String to;
    private Map<String, Object> data;
}
