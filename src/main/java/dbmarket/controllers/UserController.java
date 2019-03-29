package dbmarket.controllers;

import dbmarket.entities.User;
import dbmarket.repositories.RoleRepository;
import dbmarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String showMain(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "index";
    }


    @GetMapping("/signup")
    public String showSignUpForm(User user, Model model){
        model.addAttribute("roles", roleRepository.findAll());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model){
        /*if(result.hasErrors()){
            return "add-user";
        }*/

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "index";

    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid user Id: " + id));

        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("user", user);

        return "add-user";


    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @Valid User user, BindingResult result, Model model){
        if (result.hasErrors()){
            user.setId(id);
            return "update-user";
        }

        System.err.println(user);

        userRepository.saveAndFlush(user);
        model.addAttribute("users",userRepository.findAll());

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model){
        User user = userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid user ID: " + id));

        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "index";
    }

}







