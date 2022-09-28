package chickenTest.ChickenTest.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import chickenTest.ChickenTest.exceptions.CustomizableException;
import chickenTest.ChickenTest.services.ChickenService;
import chickenTest.ChickenTest.services.EggService;
import chickenTest.ChickenTest.services.FarmService;

@Controller
public class FarmController {
	@Autowired
	private EggService eggService;

	@Autowired
	private ChickenService chickenService;

	@Autowired
	private FarmService farmService;

	@GetMapping("/")
	public String showIndex(Model model) {
		model.addAttribute("farm", farmService.findAll());
		model.addAttribute("eggs", eggService.findAll().size());
		model.addAttribute("chickens", chickenService.findAll().size());

		return "index.html";
	}

	@PostMapping("/buy/{amount}/{type}")
	public String buy(Model model, @PathVariable String type, @PathVariable int amount) {
		try {
			ResponseEntity<?> response = farmService.buy(type, amount);
			model.addAttribute("farm", farmService.findAll());
			model.addAttribute("eggs", eggService.findAll().size());
			model.addAttribute("chickens", chickenService.findAll().size());
			int respLength = response.getBody().toString().length();
			model.addAttribute("response", response.getBody().toString().substring(9, respLength - 13));

			model.addAttribute("respStatus", response.getBody().toString().substring(respLength - 11, respLength - 1));

			return "index.html";
		} catch (CustomizableException e) {
			model.addAttribute("response", e.getMessage());
			model.addAttribute("farm", farmService.findAll());
			model.addAttribute("eggs", eggService.findAll().size());
			model.addAttribute("chickens", chickenService.findAll().size());
			return "index.html";
		}
	}

	@PostMapping("/sell/{amount}/{type}")
	public String sell(Model model, @PathVariable String type, @PathVariable int amount) {
		try {
			ResponseEntity<?> response = farmService.sell(type, amount);
			model.addAttribute("farm", farmService.findAll());
			model.addAttribute("eggs", eggService.findAll().size());
			model.addAttribute("chickens", chickenService.findAll().size());
			model.addAttribute("response", response.getBody());
			int respLength = response.getBody().toString().length();
			model.addAttribute("response", response.getBody().toString().substring(9, respLength - 13));

			model.addAttribute("respStatus", response.getBody().toString().substring(respLength - 11, respLength - 1));

			return "index.html";
		} catch (CustomizableException e) {
			model.addAttribute("response", e.getMessage());
			model.addAttribute("farm", farmService.findAll());
			model.addAttribute("eggs", eggService.findAll().size());
			model.addAttribute("chickens", chickenService.findAll().size());
			return "index.html";
		}
	}

	@PostMapping("/pass/{amount}")
	public String passDays(Model model, @PathVariable int amount) throws CustomizableException {
		try {
			ResponseEntity<?> response = farmService.passDays(amount);
			model.addAttribute("response", response.getBody());
			model.addAttribute("farm", farmService.findAll());
			model.addAttribute("eggs", eggService.findAll().size());
			model.addAttribute("chickens", chickenService.findAll().size());
			int respLength = response.getBody().toString().length();
			model.addAttribute("response", response.getBody().toString().substring(9, respLength - 13));

			model.addAttribute("respStatus", response.getBody().toString().substring(respLength - 11, respLength - 1));

			return "index.html";
		} catch (CustomizableException e) {
			model.addAttribute("response", e.getMessage());
			model.addAttribute("farm", farmService.findAll());
			model.addAttribute("eggs", eggService.findAll().size());
			model.addAttribute("chickens", chickenService.findAll().size());
			return "index.html";
		}
	}

	@GetMapping("/chickens")
	public String retrieveAllChickens(Model model) {
		model.addAttribute("chickens", chickenService.findAll());
		model.addAttribute("amount", chickenService.findAll().size());
		return "chickens.html";
	}

	@GetMapping("/eggs")
	public String retrieveAllEggs(Model model) {
		model.addAttribute("eggs", eggService.findAll());
		model.addAttribute("amount", eggService.findAll().size());
		return "eggs.html";
	}

	@GetMapping("/farm")
	public String retrieveFarm(Model model) {
		model.addAttribute("farm", farmService.findAll());
		model.addAttribute("eggs", eggService.findAll());
		model.addAttribute("eggsAmount", eggService.findAll().size());
		model.addAttribute("chickens", chickenService.findAll());
		model.addAttribute("chickensAmount", chickenService.findAll().size());

		return "farm.html";
	}

	@GetMapping("/error")
	public String error(Model model) {
		return "error.html";
	}
}
