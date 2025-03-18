package lk.ijse.carecompanion.controller;

import lk.ijse.carecompanion.dto.AuthTokenDTO;
import lk.ijse.carecompanion.dto.UserLoginDTO;
import lk.ijse.carecompanion.enums.Role;
import lk.ijse.carecompanion.service.PatientService;
import lk.ijse.carecompanion.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(){
        return "login";
    }



}
