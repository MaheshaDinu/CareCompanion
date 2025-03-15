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

    @GetMapping("")
    public String getProvider() {


        return "CareCompanion";
    }
}
