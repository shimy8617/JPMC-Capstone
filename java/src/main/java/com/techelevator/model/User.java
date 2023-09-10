package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User {

   private int id;
   private String username;
   @JsonIgnore
   private String password;
   @JsonIgnore // prevent from being sent to client
   private String confirmPassword;
   @JsonIgnore
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();
   private String fullName;
   private String email;
   private int zipCode;

   //private int preferenceZipCode; Por le momento no
   private String address;
   private String token;
   private List<TypeOfFood> typeOfFood;

   public List<TypeOfFood> getTypeOfFood() {
      return typeOfFood;
   }

   public void setTypeOfFood(List<TypeOfFood> typeOfFood) {
      this.typeOfFood = typeOfFood;
   }

   public User() { }

   public User(int id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      if(authorities != null) this.setAuthorities(authorities);
      this.activated = true;
   }

   public User(int id, String username, String password, String authorities, String fullName, String email, int zipCode, String address) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.activated = true;
      if(authorities != null) this.setAuthorities(authorities);
      this.fullName = fullName;
      this.email = email;
      this.zipCode = zipCode;
      this.address = address;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }
   public static User valueOf(Principal principal){
      UserDetail userDetail = (UserDetail) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
      StringBuilder builder = new StringBuilder();
      for (GrantedAuthority a: userDetail.getAuthorities()){
         builder.append(a.getAuthority()).append(",");
      }
      String auths = builder.toString();
      return new User(userDetail.getId(), userDetail.getUsername(), userDetail.getPassword(), auths);
   }
   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new Authority(authority));
      }
   }

   public String getConfirmPassword() {
      return confirmPassword;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }
   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public int getZipCode() {
      return zipCode;
   }

   public void setZipCode(int zipCode) {
      this.zipCode = zipCode;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", password='" + password + '\'' +
              ", confirmPassword='" + confirmPassword + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              ", fullName='" + fullName + '\'' +
              ", email='" + email + '\'' +
              ", zipCode=" + zipCode +
              ", address='" + address + '\'' +
              '}';
   }

   public boolean isMemberOfRole(String roleName){
      return this.authorities.contains(new Authority(roleName));
   }
   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }
}
