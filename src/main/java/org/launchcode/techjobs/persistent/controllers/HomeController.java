package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.*;
import org.launchcode.techjobs.persistent.models.*;
import org.launchcode.techjobs.persistent.models.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("jobs", jobRepository.findAll());

        model.addAttribute("skills", skillRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll());

        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);

        newJob.setSkills(skillObjs);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("employer", employerRepository.findById(employerId));
        model.addAttribute("skills", skillRepository.findAll());


        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        return "view";
    }


}