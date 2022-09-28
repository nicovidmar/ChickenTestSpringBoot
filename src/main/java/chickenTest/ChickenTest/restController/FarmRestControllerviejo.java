//package chickenTest.ChickenTest.restController;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//
//import chickenTest.ChickenTest.serviceLayer.ChickenService;
//import chickenTest.ChickenTest.serviceLayer.EggService;
//import chickenTest.ChickenTest.serviceLayer.FarmService;
//
//@RestController
//public class FarmRestControllerviejo {
//
//	@Autowired
//	private EggService eggService;
//
//	@Autowired
//	private ChickenService chickenService;
//
//	@Autowired
//	private FarmService farmService;

//	@GetMapping("/eggs")
//	public ResponseEntity<List<Egg>> retrieveAllEggs() {
//		return new ResponseEntity<>(eggService.findAll(), HttpStatus.OK);
//	}

//	@GetMapping("/chickens")
//	public ResponseEntity<List<Chicken>> retrieveAllChickens() {
//		return new ResponseEntity<>(chickenService.findAll(), HttpStatus.OK);
//	}
//
//	@GetMapping("/farm")
//	public ResponseEntity<List<Farm>> retrieveFarm() {
//		return new ResponseEntity<>(farmService.findAll(), HttpStatus.OK);
//	}

//	@GetMapping("/farm/1")
//	public ResponseEntity<?> retrieveFarmOne() {
//		return new ResponseEntity<>(farmService.findAll().get(0), HttpStatus.OK);
//	}

//	@PostMapping("/sell/{amount}/{type}")
//	public ResponseEntity<?> sell(@PathVariable String type, @PathVariable int amount) {
//		try {
//			farmService.sell(type, amount);
//			return myCustomResponse.generateResponse(amount + " " + type + " vendido(s) satisfactoriamente!",
//					HttpStatus.OK, null);
//		} catch (CustomizableException e) {
//			return myCustomResponse.generateResponse(null, HttpStatus.BAD_REQUEST, e.getMessage());
//		}
//	}

//	@PostMapping("/buy/{amount}/{type}")
//	public String buy(Model model, @PathVariable String type, @PathVariable int amount) {
//		try {
//			ResponseEntity<?> response = farmService.buy(type, amount);
////			return myCustomResponse.generateResponse(amount + " " + type + " comprado(s) satisfactoriamente!",
////					HttpStatus.OK, null);
//			model.addAttribute("response", response);
//
//			return "redirect:/";
//		} catch (CustomizableException e) {
//			model.addAttribute("response", e);
//			return "index.html";
////			return myCustomResponse.generateResponse(null, HttpStatus.OK, e.getMessage());
//		}
//	}

//	@PostMapping("/pass/{amount}")
//	public void passDays(@PathVariable int amount) throws CustomizableException {
//		farmService.passDays(amount);
//	}
//
//}
