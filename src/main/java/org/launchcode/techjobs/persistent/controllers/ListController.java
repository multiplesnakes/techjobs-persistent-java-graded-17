package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.*;
import org.launchcode.techjobs.persistent.models.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EmployerRepository employerRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {

        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");

        columnChoices.put("skill", "Skill");

    }

    @RequestMapping("")
    public String list(Model model) {

        model.addAttribute("employers", employerRepository.findAll());

        model.addAttribute("skills", skillRepository.findAll());
        //model.addAttribute("jobs", jobRepository.findAll());
        return "list";
    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<Job> jobs;
        Iterable<Employer> employers;
        Iterable<Skill> skills;

        if (column.toLowerCase().equals("all")){
            jobs = jobRepository.findAll();
            employers = employerRepository.findAll();

            skills = skillRepository.findAll();
            model.addAttribute("title", "All Jobs");
            model.addAttribute("jobs", jobs);

            model.addAttribute("employers", employers);
            model.addAttribute("skills", skills);


        } else {
            jobs = JobData.findByColumnAndValue(column, value, jobRepository.findAll());
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("jobs", jobs);


        return "list-jobs";
    }
}