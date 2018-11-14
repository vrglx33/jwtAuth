package com.auth0.samples.authapi.springbootauthupdated.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


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
    @CrossOrigin
    @PostMapping("/sign-up")
    public ApplicationUser signUp(@RequestBody FullUser user) {
        childUser = new ChildUser();
        childUser.setName(user.getKidName());
        childUser.setCellphone(user.getKidCellphone());
        childUser.setEmail(user.getKidEmail());
        parentUser = new ParentUser();
        parentUser.setCellphone(user.getParentCellphone());
        parentUser.setName(user.getParentName());
        parentUser.setEmail(user.getParentEmail());
        loginUser = new ApplicationUser();
        loginUser.setUsername(parentUser.getEmail());
        loginUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(loginUser);
        parentUser = parentUserRepository.save(parentUser);
        childUser.setParent(parentUser.getId());
        childUserRepository.save(childUser);
        return applicationUserRepository.save(loginUser);
    }
    @CrossOrigin
    @GetMapping("/verify/mail")
    public ResponseEntity<ObjectNode> verifyMail (@RequestParam String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode1 = mapper.createObjectNode();
        if(parentUserRepository.existsByemail(email)){
            objectNode1.put("status", "parent");
            return new ResponseEntity<>(objectNode1, responseHeaders, HttpStatus.FOUND);
        }
        if(childUserRepository.existsByemail(email)){
            objectNode1.put("status", "child");
            return new ResponseEntity<>(objectNode1, responseHeaders, HttpStatus.FOUND);
        }
        objectNode1.put("status", "not found");
        return new ResponseEntity<>(objectNode1, responseHeaders, HttpStatus.NOT_FOUND);

    }
}