package org.springframework.samples.petclinic.feeding;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feeding")
public class FeedingController {
    
	@Autowired
	private FeedingService feedingService;
	
	private static final String VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";
	
	@GetMapping(path = "/create")
	public String initCreationForm(ModelMap model) {
		model.addAttribute("feeding", new Feeding());
		model.addAttribute("feedingType", feedingService.getAllFeedingTypes());
		return VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(path = "/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model) {
		String view = "welcome";
		if (result.hasErrors()) {
			model.addAttribute("feeding", feeding);
			model.addAttribute("feedingType", feedingService.getAllFeedingTypes());
			return VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM;
		}else {
			try{
				this.feedingService.save(feeding);
            }catch(UnfeasibleFeedingException ex){
               result.rejectValue("pet", "La mascota seleccionada no se le puede asignar el plan de alimentación especificado.");
                return VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM;
            }
			model.addAttribute("message", "¡Se ha guardado correctamente!");
			
		}
		return view;	
			
		
	}
	
}
