package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Provider;
import lk.ijse.carecompanion.entity.UserPrincipal;
import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.repository.ProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    ProviderRepo providerRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Patient> optPatient = patientRepo.findByUserName(username);
        if (optPatient.isPresent()) {
            Patient patient = optPatient.get();
            return new UserPrincipal(patient);
        }
        Optional<Provider> optProvider = providerRepo.findByUserName(username);
        if (optProvider.isPresent()) {
            Provider provider = optProvider.get();
            return new UserPrincipal(provider);
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
