// src/main/java/com/erpca/erpcaapp/controller/LoginController.java
package com.erpca.erpcaapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.erpca.erpcaapp.model.LoginUser;
import com.erpca.erpcaapp.repository.LoginUserRepository;

@Controller
public class LoginController {

    private final LoginUserRepository userRepo;

    public LoginController(LoginUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // SHOW THE LOGIN FORM
    @GetMapping("/login")
    public String showLogin(Model model,
                            @RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out.");
        }
        return "login";  // <-- return the logical view name "login"
    }

    // PROCESS THE LOGIN SUBMISSION
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        LoginUser loginUser = userRepo.findByUsername(username);
        if (loginUser == null || !loginUser.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }
        // (Optional) Check status == "ACTIVE", etc.
        if (!"ACTIVE".equalsIgnoreCase(loginUser.getStatus())) {
            model.addAttribute("errorMessage", "Your account is not active.");
            return "login";
        }

        // Authentication succeeded → store flag in session
        session.setAttribute("LOGGED_IN_USER", username);
        session.setAttribute("LOGGED_IN_USER_ID", loginUser.getUserID());
        return "redirect:/";   // redirect to home page
    }

    // HANDLE LOGOUT
    @GetMapping("/logout")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
