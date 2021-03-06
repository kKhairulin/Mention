package com.mention.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
@Data
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "profile_id")
  private Long id;

  @Column(name = "profile_firstname")
  private String firstName;

  @Column(name = "profile_secondname")
  private String secondName;

  @Column(name = "profile_address")
  private String address;

  @Column(name = "profile_birthdate")
  private Date birthDate;

  @Column(name = "profile_avatar_url")
  private String avatarUrl;

  @Column(name = "profile_background_url")
  private String backgroundUrl;

  @Column(name = "profile_avatar_key")
  private String avatarKey;

  @Column(name = "profile_background_key")
  private String backgroundKey;

  @OneToOne
  @JoinColumn(name = "user_id", updatable = false, unique = true, nullable = false)
  @JsonBackReference
  private User user;

  protected Profile() { }

  public Profile(User user) {
    this.user = user;
  }

  public Profile(String firstName, String secondName, String address,
                 Date birthDate, String avatarUrl, String backgroundUrl, User user) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.address = address;
    this.birthDate = birthDate;
    this.avatarUrl = avatarUrl;
    this.backgroundUrl = backgroundUrl;
    this.user = user;
  }
}
