package com.rantomah.boilerplate.application.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rantomah.boilerplate.application.domain.constant.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(
        name = "app_user",
        indexes = {
            @Index(name = "idx_user_username", columnList = "username")})
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE app_user SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@SuppressWarnings("deprecation")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(
            updatable = false,
            nullable = false,
            columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder.Default
    private boolean enabled = false;

    @Builder.Default
    private boolean accountNonLocked = true;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private Instant updatedAt;

    @Column(updatable = false)
    private Instant deletedAt;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
}
