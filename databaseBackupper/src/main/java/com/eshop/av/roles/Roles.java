package com.eshop.av.roles;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public enum Roles {

    USER(Collections.emptySet());

    Roles(Set<Object> objects) {
    }
}

