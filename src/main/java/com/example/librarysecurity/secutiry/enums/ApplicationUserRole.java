package com.example.librarysecurity.secutiry.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.librarysecurity.secutiry.enums.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(CREATE_STUDENT, READ_STUDENT, UPDATE_STUDENT, DELETE_STUDENT,
            CREATE_BOOK, READ_BOOK, UPDATE_BOOK, DELETE_BOOK)),

    MODERATOR(Sets.newHashSet(READ_STUDENT, CREATE_STUDENT, READ_BOOK, CREATE_BOOK)),

    DEFAULT(Sets.newHashSet(READ_BOOK));
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = this.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
