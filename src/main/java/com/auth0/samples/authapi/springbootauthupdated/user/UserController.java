package com.auth0.samples.authapi.springbootauthupdated.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private ChildUser childUser;
    private ParentUser parentUser;
    private ApplicationUser loginUser;
    private ApplicationUserRepository applicationUserRepository;
    private ParentUserRepository parentUserRepository;
    private ChildUserRepository childUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ParentUserRepository parentUserRepository,
                          ChildUserRepository childUserRepository,
                          ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.parentUserRepository = parentUserRepository;
        this.childUserRepository = childUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody FullUser user) {
        childUser = new ChildUser();
        childUser.setName(user.getKidName());
        childUser.setCellphone(user.getKidCellphone());
        childUser.setEmail(user.getKidEmail());
        parentUser = new ParentUser();
        parentUser.setCellphone(user.getParentCellphone());
        parentUser.setName(user.getParentName());
        parentUser.setEmail(user.getParentEmail());
        loginUser = new ApplicationUser();
        loginUser.setUsername(parentUser.getName());
        loginUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(loginUser);
        parentUser = parentUserRepository.save(parentUser);
        childUser.setParent(parentUser.getId());
        childUserRepository.save(childUser);
    }

    @GetMapping("/verify/mail")
    public String verifyMail (@RequestParam String email) {
        if(parentUserRepository.existsByemail(email)){
            return "parent";
        }
        if(childUserRepository.existsByemail(email)){
            return "child";
        }else{
            return "not found";
        }
    }
}