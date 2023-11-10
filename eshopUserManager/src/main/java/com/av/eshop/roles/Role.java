package com.av.eshop.roles;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet());

    Role(Set<Object> objects) {
    }
}
