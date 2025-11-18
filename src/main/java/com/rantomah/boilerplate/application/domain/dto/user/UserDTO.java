package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.application.domain.constant.UserRole;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class UserDTO {

    private UUID id;
    private String username;
    private UserRole role;
    private boolean enabled;
    private boolean locked;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
