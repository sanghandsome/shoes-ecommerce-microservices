package com.shoes.ecommerce.indentityservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_has_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserHasRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;
}
