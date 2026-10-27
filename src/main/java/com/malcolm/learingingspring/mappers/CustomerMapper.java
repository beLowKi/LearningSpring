package com.malcolm.learingingspring.mappers;

import com.malcolm.learingingspring.dtos.CustomerDto;
import com.malcolm.learingingspring.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
}
