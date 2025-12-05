package tn.enis.controller;

import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import tn.enis.entities.Admin;
import tn.enis.service.AdminService;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/log")
public class AdminController {

    private AdminService ad;

    @PostMapping("/inscription")
    public Admin inscriAdmin(@RequestBody Admin admin) {
        return ad.createNewAdmin(admin.getUsername(), admin.getPassword());
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Admin admin) {
        String token = ad.verifLogin(admin.getUsername(), admin.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);  
        return response;
    }
}
