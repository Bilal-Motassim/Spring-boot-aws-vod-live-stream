package com.example.awsproject.Model;

import jakarta.persistence.*;
import lombok.*;


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

    @Column(unique = true)
    private String username;

    @Column
    private String streamKey;

    @Column
    private String streamUrl;

    @Column
    private String streamArn;

    @Column
    private String chatArn;

    @Column
    private String chatToken;

}
