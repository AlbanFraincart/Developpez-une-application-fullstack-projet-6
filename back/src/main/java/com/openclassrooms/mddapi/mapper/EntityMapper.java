package com.openclassrooms.mddapi.mapper;

import java.util.List;

/**
 * Interface générique pour les mappers d'entités et de DTOs.
 *
 * @param <D> Type du DTO.
 * @param <E> Type de l'entité.
 */
public interface EntityMapper<D, E> {

    /**
     * Convertit un DTO en entité.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante.
     */
    E toEntity(D dto);

    /**
     * Convertit une entité en DTO.
     *
     * @param entity L'entité à convertir.
     * @return Le DTO correspondant.
     */
    D toDto(E entity);

    /**
     * Convertit une liste d'entités en liste de DTOs.
     *
     * @param entities Liste d'entités à convertir.
     * @return Liste de DTOs correspondante.
     */
    List<D> toDto(List<E> entities);

    /**
     * Convertit une liste de DTOs en liste d'entités.
     *
     * @param dtos Liste de DTOs à convertir.
     * @return Liste d'entités correspondante.
     */
    List<E> toEntity(List<D> dtos);
}
