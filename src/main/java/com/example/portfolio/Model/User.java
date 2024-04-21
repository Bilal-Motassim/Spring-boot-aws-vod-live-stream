package com.example.portfolio.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "firstname" ,nullable = false )
    private String firstName;

    @Column( name = "lastname", nullable = false )
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String username;

    @Column
    private String streamKey;

    @Column
    private String streamUrl;

}
