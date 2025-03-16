package lk.ijse.carecompanion.config;

import lk.ijse.carecompanion.filter.JwtFilter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class webAppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }
}
