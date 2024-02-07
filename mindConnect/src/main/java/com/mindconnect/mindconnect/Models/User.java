package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String Password;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "gender")
    private String gender;

    @Column(name = "mental_condition")
    private String mentalCondition;

    @Column(name = "username")
    private String username;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;


    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_fkey")
    )
    private User user;

    @OneToMany(mappedBy = "user")
    private List<User> blockedUsers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
    @Override
    public String getUsername(){return emailAddress;}
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
