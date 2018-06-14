package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

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
        model.addAttribute("name", job.getName());
        model.addAttribute("positionType", job.getPositionType().getValue());
        model.addAttribute("employerName", job.getEmployer().getValue());
        model.addAttribute("coreCompetency", job.getCoreCompetency().getValue());
        model.addAttribute("location", job.getLocation().getValue());
        //model.addAttribute("name", job.getName().getValue());
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        ArrayList<Location>locations=new ArrayList<>();

        for (Location location : jobForm.getLocations()){
            if(!locations.contains(location)){
                locations.add(location);
            }
        }

        ArrayList<Employer>employers=new ArrayList<>();
        for (Employer employer : jobForm.getEmployers()){
            if(!employers.contains(employer)){
                employers.add(employer);
            }
        }


        ArrayList<PositionType>positionTypes=new ArrayList<>();

        for (PositionType positionType : jobForm.getPositionTypes()) {
            if(!positionTypes.contains(positionType)){
                positionTypes.add(positionType);
            }
        }

        ArrayList<CoreCompetency>coreCompetencies=new ArrayList<>();
        for (CoreCompetency coreCompetency : jobForm.getCoreCompetencies()){
            if(!coreCompetencies.contains(coreCompetency)){
                coreCompetencies.add(coreCompetency);
            }
        }

        // Add a list of unduplicated elements of the jobs to the model for their use in the view
        model.addAttribute("locations", locations);
        model.addAttribute("employers", employers );
        model.addAttribute("positionstypes", positionTypes);
        model.addAttribute("coreCompetencies", coreCompetencies);
        model.addAttribute("name","name");
        int id=jobForm.getEmployerId();
        model.addAttribute("id",id);

        // Add default value for the dropdown in view
        // By no specification of these defaut values in the views,
        // html will take the last value ofevery list as the default option
        model.addAttribute("defaultLocation", locations.get(locations.size()-1));
        // [locations.size()-1] to index the last element oft the arraylyst// 0 means the first element of the Arraylist
        model.addAttribute("defaultEmployer", employers.get(0));
        model.addAttribute("defaultPositionstype", positionTypes.get(0));
        model.addAttribute("deaultCoreCompetency", coreCompetencies.get(0));


        return "redirect:?id="+ id;

    }
}
