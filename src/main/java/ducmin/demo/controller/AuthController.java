package ducmin.demo.controller;

import ducmin.demo.dto.request.*;
import ducmin.demo.dto.response.JwtResponse;
import ducmin.demo.dto.response.ResponMessage;
import ducmin.demo.model.Role;
import ducmin.demo.model.RoleName;
import ducmin.demo.model.User;
import ducmin.demo.security.jwt.JwtProvider;
import ducmin.demo.security.jwt.JwtTokenFilter;
import ducmin.demo.security.userprincal.UserPrinciple;
import ducmin.demo.service.impl.RoleServiceImpl;
import ducmin.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RequestMapping
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
        if(userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponMessage("noemail"), HttpStatus.OK);
        }
        if(signUpForm.getAvatar() == null || signUpForm.getAvatar().trim().isEmpty()){
            signUpForm.setAvatar("https://firebasestorage.googleapis.com/v0/b/ducmin-530bc.appspot.com/o/images%2Fanh1.jpg?alt=media&token=bc90735e-a68f-485c-99a1-6b7a2c039b14");
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(),passwordEncoder.encode(signUpForm.getPassword()),signUpForm.getAvatar());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role ->{
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            ()-> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(),userPrinciple.getAvatar(), userPrinciple.getAuthorities()));
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordForm changePasswordForm){
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUerNameFromToken(jwt);
        User user;
        try {
           user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found wiht -> username"+username));
           boolean matches = passwordEncoder.matches(changePasswordForm.getCurrentPassword(), user.getPassword());
           if(changePasswordForm.getNewPassword() != null){
               if(matches){
                   user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
                   userService.save(user);
               } else {
                   return new ResponseEntity<>(new ResponMessage("no"), HttpStatus.OK);
               }
           }
           return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception){
            return new ResponseEntity<>(new ResponMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/change-avatar")
    public ResponseEntity<?> changeAvatar(HttpServletRequest httpServletRequest, @Valid @RequestBody ChangeAvatar changeAvatar){
        String jwt = jwtTokenFilter.getJwt(httpServletRequest);
        String username = jwtProvider.getUerNameFromToken(jwt);
        User user;
        try {
            if(changeAvatar.getAvatar()==null){
                return new ResponseEntity(new ResponMessage("no"), HttpStatus.OK);
            } else{
                user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Fount with -> username:"+username));
                user.setAvatar(changeAvatar.getAvatar());
                userService.save(user);
            }
            return new ResponseEntity(new ResponMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception){
            return new ResponseEntity<>(new ResponMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/change-profile")
    public ResponseEntity<?> changeProfile(HttpServletRequest request, @Valid @RequestBody ChangeProfileForm changeProfileForm){
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUerNameFromToken(jwt);
        User user;
        try {
            if(userService.existsByUsername(changeProfileForm.getUsername())){
                return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
            }
            if(userService.existsByEmail(changeProfileForm.getEmail())){
                return new ResponseEntity<>(new ResponMessage("noemail"), HttpStatus.OK);
            }
            user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with -> useranme"+username));
            user.setName(changeProfileForm.getName());
            user.setUsername(changeProfileForm.getUsername());
            user.setEmail(changeProfileForm.getEmail());
            userService.save(user);
            return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception){
            return new ResponseEntity<>(new ResponMessage(exception.getMessage()),HttpStatus.NOT_FOUND );
        }
    }
}
