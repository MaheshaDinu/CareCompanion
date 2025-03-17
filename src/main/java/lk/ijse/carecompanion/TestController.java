package lk.ijse.carecompanion;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.dto.UserDTO;
import lk.ijse.carecompanion.dto.UserLoginDTO;
import lk.ijse.carecompanion.dto.UserRegistrationDTO;
import lk.ijse.carecompanion.entity.Patient;
import lk.ijse.carecompanion.entity.Users;
import lk.ijse.carecompanion.enums.Role;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    PatientService patientService;
    @Autowired
    ProviderService providerService;


    @GetMapping("")
    public String getProvider(HttpServletRequest request) {


        return "CareCompanion Session ID:"+request.getSession().getId();
    }
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO userDTO) {
        return "";
    }
}
