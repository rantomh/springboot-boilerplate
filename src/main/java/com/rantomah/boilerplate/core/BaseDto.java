package com.rantomah.boilerplate.core;

import java.time.Instant;
import lombok.Data;

@Data
public abstract class BaseDto {

    private Long id;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;
}
