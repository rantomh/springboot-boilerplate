package com.rantomah.boilerplate.application.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * BaseMapper serves as a generic configuration interface for all MapStruct mappers within the
 * application. It defines shared mapping configurations to be inherited by specific mapper
 * interfaces. Configuration applied: - `componentModel = "spring"`: Configures the generated mapper
 * implementation to be a Spring bean. - `unmappedTargetPolicy = ReportingPolicy.IGNORE`: Ignores
 * unmapped target properties during mapping without throwing warnings. - `collectionMappingStrategy
 * = CollectionMappingStrategy.ADDER_PREFERRED`: Uses adder methods (e.g., addX) when updating
 * collections during mapping.
 *
 * <p>All specific mapper interfaces should extend this interface to inherit the common
 * configuration.
 */
@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ConfigMapper {}
