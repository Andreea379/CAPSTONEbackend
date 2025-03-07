package com.example.capstone.Models;

import com.example.capstone.Enumerations.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Role {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    public Role(){}

    public Role(Roles role) {
        this.role = role;
    }
}
