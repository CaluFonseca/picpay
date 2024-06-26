package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.user.UserType;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password,
                      UserType userType) {
}
