package com.example.librarysecurity.secutiry.enums;

public enum ApplicationUserPermission {
    CREATE_STUDENT("student:create"),
    READ_STUDENT("student:read"),
    UPDATE_STUDENT("student:update"),
    DELETE_STUDENT("student:delete"),
    CREATE_BOOK("book:create"),
    READ_BOOK("book:read"),
    UPDATE_BOOK("book:update"),
    DELETE_BOOK("book:delete");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
