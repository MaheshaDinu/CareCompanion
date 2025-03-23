package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.*;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Provider;
import lk.ijse.carecompanion.repository.ProviderRepo;
import lk.ijse.carecompanion.service.JWTService;
import lk.ijse.carecompanion.service.ProviderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    ProviderRepo providerRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;
    AuthTokenDTO authTokenDTO = new AuthTokenDTO();
    @Transactional
    @Override
    public void register(ProviderRegistrationDTO providerRegistrationDTO){
        providerRegistrationDTO.setPassword(bCryptPasswordEncoder.encode(providerRegistrationDTO.getPassword()));
        Provider provider = modelMapper.map(providerRegistrationDTO,Provider.class);
        providerRepo.save(provider);
    }
    @Transactional
    @Override
    public void update(ProviderRegistrationDTO providerDTO){
        Provider provider = modelMapper.map(providerDTO,Provider.class);
        providerRepo.save(provider);
    }
    @Transactional
    @Override
    public void delete(int id){
        providerRepo.deleteById(id);
    }
    @Transactional
    @Override
    public List<ProviderDTO> getAll(){
        return modelMapper.map(providerRepo.findAll(),new TypeToken<List<ProviderDTO>>(){}.getType());
    }
    public ProviderDTO getByUserName(String userName){
        return modelMapper.map(providerRepo.findByUserName(userName),ProviderDTO.class);
    }
    @Transactional
    @Override
    public AuthTokenDTO verifyProvider(UserLoginDTO userDTO) {
        Optional<Provider> optProvider = providerRepo.findByUserName(userDTO.getUsername());
        if (optProvider.isPresent()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                authTokenDTO.setAuthenticated(true);
                authTokenDTO.setToken(jwtService.generateToken(userDTO.getUsername(),userDTO.getRole()));
                authTokenDTO.setMessage("success");
                return authTokenDTO;
            }
            authTokenDTO.setAuthenticated(false);
            authTokenDTO.setMessage("fail");
            return authTokenDTO;
        }
        authTokenDTO.setAuthenticated(false);
        authTokenDTO.setMessage("Provider not found");
        return authTokenDTO;
    }

    @Override
    public List<PatientDTO> getPatientsByProviderId(int providerId) {
        List<Patient> patients = providerRepo.findById(providerId).get().getPatients();
        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .toList();
    }
    @Override
    public ProviderDTO getById(int id) {
        return modelMapper.map(providerRepo.findById(id), ProviderDTO.class);
    }
}
