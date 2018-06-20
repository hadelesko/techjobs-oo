package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();


    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view


        Job job=jobData.findById(id);


        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        String jobname=jobForm.getName();
        Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
        PositionType positionType= jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        Location location=jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency coreCompetency=jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        // Create and assign the variable to be pass to the new job added view
        if (errors.hasErrors()) {

            model.addAttribute("title", "Add Form");

            return "new-job";

        }

        Job newjob= new Job(jobname, employer, location, positionType, coreCompetency);
        jobData.add(newjob);
        int jobId=newjob.hashCode();


        //return "job-detail";
        return "redirect:?id=" + newjob.hashCode();

    }
}
