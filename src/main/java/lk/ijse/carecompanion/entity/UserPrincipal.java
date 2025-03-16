package lk.ijse.carecompanion.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private Provider provider;
    private Patient patient;

    public UserPrincipal(Provider provider) {
        this.provider = provider;
    }
    public UserPrincipal(Patient patient) {
        this.patient = patient;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER")) ;
    }

    @Override
    public String getPassword() {
        if (patient!=null){
            return patient.getPassword();
        }else {
            return provider.getPassword();
        }
    }

    @Override
    public String getUsername() {
        if (patient!=null){
            return patient.getUserName();
        }else {
            return provider.getUserName();
        }
    }

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
