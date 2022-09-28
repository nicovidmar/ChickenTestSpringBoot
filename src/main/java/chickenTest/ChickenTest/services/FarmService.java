package chickenTest.ChickenTest.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import chickenTest.ChickenTest.constantes.Constantes;
import chickenTest.ChickenTest.entities.Chicken;
import chickenTest.ChickenTest.entities.Egg;
import chickenTest.ChickenTest.entities.Farm;
import chickenTest.ChickenTest.exceptions.CustomizableException;
import chickenTest.ChickenTest.repositories.ChickenRepository;
import chickenTest.ChickenTest.repositories.EggRepository;
import chickenTest.ChickenTest.repositories.FarmRepository;
import chickenTest.ChickenTest.response.myCustomResponse;

@Service
public class FarmService {

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private ChickenRepository chickenRepository;

	@Autowired
	private EggRepository eggRepository;

	public List<Farm> findAll() {
		return farmRepository.findAll();
	}

	public ResponseEntity<?> sell(String type, int amount) throws CustomizableException {
		List<Farm> listFarm = farmRepository.findAll();
		Farm farm = listFarm.stream().findFirst().get();

		double cashAvailable = farm.getCash();
		if (type.equals(Constantes.EGGS)) {
			// lista de Eggs ordenada por fecha para ir vendiendo los más recientes
			List<Egg> eggs = eggRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
			if (eggs.size() >= amount) {
				for (int i = 0; i < amount; i++) {
					eggRepository.deleteById(eggs.get(i).getId());
				}
				farm.setCash(cashAvailable + amount * Constantes.SELL_PRICE_EGGS);
				farmRepository.save(farm);
			} else {
				throw new CustomizableException("Cantidad a vender (" + amount
						+ ") debe ser menor o igual a la cantidad existente (" + eggs.size() + ").");
			}

		} else if (type.equals(Constantes.CHICKENS)) {
			// lista de Chickens ordenada por fecha para ir vendiendo las más viejas
			List<Chicken> chickens = chickenRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));

			if (chickens.size() >= amount) {
				for (int i = 0; i < amount; i++) {
					chickenRepository.deleteById(chickens.get(i).getId());
				}
				farm.setCash(cashAvailable + amount * Constantes.SELL_PRICE_CHICKENS);
				farmRepository.save(farm);
			} else {
				throw new CustomizableException("Cantidad a vender (" + amount
						+ ") debe ser menor o igual a la cantidad existente (" + chickens.size() + ").");
			}
		} else {
			throw new CustomizableException("No es posible vender " + type + ", ingrese eggs o chickens.");
		}
		return myCustomResponse.generateResponse(amount + " " + type + " vendido(s) satisfactoriamente!",
				HttpStatus.OK);
	}

	public ResponseEntity<?> buy(String type, int amount) throws CustomizableException {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		double cashAvailable = farm.getCash();

		Date today = farm.getDate();
		if (type.equals(Constantes.EGGS)) {
			long eggsAmount = eggRepository.count();
			long maxEggs = farm.getMaxEggs();

			if (eggsAmount + amount <= maxEggs) {
				if (cashAvailable >= amount * Constantes.BUY_PRICE_EGGS) {
					for (int i = 0; i < amount; i++) {
						eggRepository.save(new Egg(today));
					}

					farm.setCash(cashAvailable - amount * Constantes.BUY_PRICE_EGGS);
					farmRepository.save(farm);
				} else {
					throw new CustomizableException(
							"No hay dinero suficiente. El dinero disponible es de $" + cashAvailable
									+ " y el necesario para esta compra: $" + amount * Constantes.BUY_PRICE_EGGS);
				}
			} else {
				throw new CustomizableException(
						"No es posible tener más de " + maxEggs + " eggs. Cantidad existente ahora: " + eggsAmount);
			}
		} else if (type.equals(Constantes.CHICKENS)) {
			long chickenAmount = chickenRepository.count();
			long maxChickens = farm.getMaxChickens();
			if (chickenAmount + amount <= maxChickens) {
				if (cashAvailable >= amount * Constantes.BUY_PRICE_CHICKENS) {
					for (int i = 0; i < amount; i++) {
						chickenRepository.save(new Chicken(today));
					}
					farm.setCash(cashAvailable - amount * Constantes.BUY_PRICE_CHICKENS);
					farmRepository.save(farm);
				} else {
					throw new CustomizableException(
							"No hay dinero suficiente. El dinero disponible es de $" + cashAvailable
									+ " y el necesario para esta compra: $" + amount * Constantes.BUY_PRICE_CHICKENS);
				}

			} else {
				throw new CustomizableException("No es posible tener más de " + maxChickens
						+ " chickens. Cantidad existente ahora: " + chickenAmount);
			}
		} else {
			throw new CustomizableException("No es posible comprar " + type + ", ingrese eggs o chickens.");
		}

		return myCustomResponse.generateResponse(amount + " " + type + " comprado(s) satisfactoriamente!",
				HttpStatus.OK);

	}

	public ResponseEntity<?> passDays(int amount) throws CustomizableException {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		Date today = farm.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		int chickensSize = chickenRepository.findAll().size();

		if (amount > 0) {
			// por cada día que pasa...
			for (int i = 0; i < amount; i++) {

				// agregar un día a cal
				cal.add(Calendar.DATE, 1);
				// para actualizar fecha de granja
				today = cal.getTime();
				
				Calendar cal2 = Calendar.getInstance();
				Calendar cal3 = Calendar.getInstance();
				cal2.setTime(today);
				cal3.setTime(today);
				// se le resta a la fecha de la granja
				cal2.add(Calendar.DATE, -(Constantes.DAYS_TO_BREAK_EGG));
				cal3.add(Calendar.DATE, -(Constantes.DAYS_TO_DIE_CHICKEN));
				Date twentyDays = cal2.getTime();
				Date fortyDays = cal3.getTime();
				
				// para sacar la lista de Eggs con + de 20 días
				List<Egg> listEggs = eggRepository.findEggsBefore(twentyDays);
				// y de Chickens con + 40
				List<Chicken> listChicken = chickenRepository.findChickensBefore(fortyDays);
				
				eggRepository.deleteAll(listEggs);
				chickenRepository.deleteAll(listChicken);

					
				// y romperlos.
				List<Chicken> brokenEggs = breakEggs(listEggs, today);
				chickenRepository.saveAll(brokenEggs);



				// cada día, cada Chicken pone un huevo
				for (int j = 0; j < chickensSize; j++) {
					eggRepository.save(new Egg(today));
				}

				farm.setDate(today);
			}

			sellExcedent();
			farmRepository.save(farm);

		} else {
			throw new CustomizableException("Por favor ingrese un número positivo.");
		}
		return myCustomResponse.generateResponse(amount + " día(s) pasado(s).", HttpStatus.OK);
	}

	public List<Chicken> breakEggs(List<Egg> listEggs, Date date) {
		ArrayList<Chicken> chickens = new ArrayList<>();
		for (int i = 0; i < listEggs.size(); i++) {
			chickens.add(new Chicken(date));
		}
		return chickens;
	}

	// en caso de que se llegue al máximo permitido pero con el paso de los días
	// se rompa/ponga un huevo, se vende el excedente
	private void sellExcedent() throws CustomizableException {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		long maxEggs = farm.getMaxEggs();
		long eggsAmount = eggRepository.count();

		long maxChickens = farm.getMaxChickens();
		long chickenAmount = chickenRepository.count();

		if (chickenAmount > maxChickens) {
			int diff = Long.valueOf(chickenAmount - maxChickens).intValue();
			sell("chickens", diff);
		}
		if (eggsAmount > maxEggs) {
			int diff = Long.valueOf(eggsAmount - maxEggs).intValue();
			sell("eggs", diff);
		}
	}

}
