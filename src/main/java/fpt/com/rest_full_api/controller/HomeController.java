package fpt.com.rest_full_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController

@RequestMapping("/api/user")
public class HomeController {

   @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/home")
    public String getMethodName() {
        return "tien";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/home1")
    public String getMethodName1() {
        return "tien";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/home2")
    public String getMethodName2() {
        return "tien";
    }

}
