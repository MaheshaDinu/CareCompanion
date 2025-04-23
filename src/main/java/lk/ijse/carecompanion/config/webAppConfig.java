package lk.ijse.carecompanion.config;

import lk.ijse.carecompanion.dto.PatientRegistrationDTO;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.filter.JwtFilter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class webAppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(PatientRegistrationDTO.class, Patient.class).addMappings(mapper -> mapper.skip(Patient::setId));

        return modelMapper;
    }
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

}
