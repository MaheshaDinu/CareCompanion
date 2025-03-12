package lk.ijse.carecompanion;

import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/provider")
    public String getProvider() {
        ProviderDTO providerDTO = new ProviderDTO();
        providerDTO.setUserName("provider");
        providerDTO.setId(1);
        providerDTO.setEmail("pr@gmail.com");
        providerDTO.setFirstName("Mahesha");
        providerDTO.setLastName("Prabath");
        providerDTO.setClinicName("DOCcare");

        return providerDTO.getUserName();
    }
}
