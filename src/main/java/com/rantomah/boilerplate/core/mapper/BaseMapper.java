package com.rantomah.boilerplate.core.mapper;

public interface BaseMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);
}
