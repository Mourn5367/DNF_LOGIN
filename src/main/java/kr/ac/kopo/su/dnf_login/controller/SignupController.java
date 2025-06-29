package kr.ac.kopo.su.dnf_login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Handles GET request for the signup page
    @GetMapping("/signup.html")
    public String signupPage() {
        System.out.println("DEBUG: 회원가입 페이지 요청됨."); // Log when signup page is requested
        return "signup";
    }

    // Handles POST request for user registration
    @PostMapping("/signup")
    public String registerUser(
            @RequestParam("inputid") String inputid,
            @RequestParam("inputpw") String inputpw,
            @RequestParam("confirm_inputpw") String confirmInputpw,
            Model model) {

        System.out.println("DEBUG: 회원가입 요청 수신 - 아이디: " + inputid); // Log received signup request
        System.out.println("DEBUG: 입력 비밀번호: " + inputpw.length() + "자, 확인 비밀번호: " + confirmInputpw.length() + "자"); // Log password lengths for quick check

        // 1. Check if passwords match
        if (!inputpw.equals(confirmInputpw)) {
            System.out.println("DEBUG: 비밀번호 불일치. 비밀번호 확인 오류."); // Log password mismatch
            model.addAttribute("error", "passwordMismatch");
            model.addAttribute("inputid", inputid);
            return "signup";
        }
        System.out.println("DEBUG: 비밀번호 일치 확인 완료."); // Log password match

        // 2. Check if user already exists
        if (inMemoryUserDetailsManager.userExists(inputid)) {
            System.out.println("DEBUG: 아이디 '" + inputid + "'가 이미 존재합니다."); // Log existing user
            model.addAttribute("error", "exists");
            model.addAttribute("inputid", inputid);
            return "signup";
        }
        System.out.println("DEBUG: 아이디 '" + inputid + "'는 사용 가능합니다."); // Log user availability

        // 3. Create a new UserDetails object
        UserDetails newUser = User.withUsername(inputid)
                .password(passwordEncoder.encode(inputpw))
                .roles("USER")
                .build();

        // 4. Add the new user to the in-memory user store
        inMemoryUserDetailsManager.createUser(newUser);
        System.out.println("DEBUG: 새로운 사용자 '" + inputid + "'가 성공적으로 등록되었습니다."); // Log successful user creation

        // Redirect to login page after successful registration
        return "redirect:/login.html?registered=true";
    }
}
