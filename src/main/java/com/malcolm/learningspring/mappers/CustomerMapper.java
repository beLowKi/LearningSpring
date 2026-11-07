package com.malcolm.learningspring.mappers;

import com.malcolm.learningspring.dtos.CustomerDto;
import com.malcolm.learningspring.entities.Customer;
import org.hibernate.Hibernate;
import org.mapstruct.*;

import java.util.Collection;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class, OrderMapper.class})
public interface CustomerMapper {
    CustomerDto toDto(Customer customer, @Context Set<String> includes);
    Customer fromDto(CustomerDto customerDto);

    @Condition
    default boolean isIncluded(
            @TargetPropertyName String property,
            @Context Set<String> includes,
            Collection<?> source
    ) {
        return includes.contains(property) && Hibernate.isInitialized(source);
    }
}
