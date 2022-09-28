package chickenTest.ChickenTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import chickenTest.ChickenTest.entities.Egg;
import chickenTest.ChickenTest.entities.Farm;
import chickenTest.ChickenTest.exceptions.CustomizableException;
import chickenTest.ChickenTest.repositories.ChickenRepository;
import chickenTest.ChickenTest.repositories.EggRepository;
import chickenTest.ChickenTest.repositories.FarmRepository;
import chickenTest.ChickenTest.services.ChickenService;
import chickenTest.ChickenTest.services.EggService;
import chickenTest.ChickenTest.services.FarmService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FarmServiceTest {

	@InjectMocks
	private FarmService farmService;

	@InjectMocks
	private EggService eggService;

	@InjectMocks
	private ChickenService chickenService;

	@Mock
	FarmRepository farmRepository;

	@Mock
	EggRepository eggRepository;

	@Mock
	ChickenRepository chickenRepository;

	@Test
	public void buyTest() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		Farm farm1 = new Farm();

		farm1.setCash(10);
		farm1.setMaxEggs(10);
		farm.add(farm1);
		Mockito.when(farmRepository.findAll()).thenReturn(farm);
//		Mockito.when(eggRepository.save(new Egg())).thenReturn(new Egg(new Date()));

		ResponseEntity<?> response = farmService.buy("eggs", 1);
		assertEquals(response.getStatusCodeValue(), 200);
		assertEquals(farm1.getCash(), 9.0, 0);
	}

	@Test
	public void buyMoreThanAllowedTest() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		Farm farm1 = new Farm();

		farm1.setCash(10);
		farm1.setMaxEggs(0);
		farm.add(farm1);
		Mockito.when(farmRepository.findAll()).thenReturn(farm);
//		Mockito.when(eggRepository.save(new Egg())).thenReturn(new Egg(new Date()));

		assertThrows("No es posible tener más de " + farm1.getMaxEggs() + " eggs.", CustomizableException.class,
				() -> farmService.buy("eggs", 1));
	}

	@Test
	public void buyWithoutMoneyTest() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		Farm farm1 = new Farm();
		farm1.setCash(0);
		farm.add(farm1);
		Mockito.when(farmRepository.findAll()).thenReturn(farm);
//		Mockito.when(eggRepository.save(new Egg())).thenReturn(new Egg(new Date()));

		assertThrows("No hay dinero suficiente. El dinero disponible es de $0.0 y el necesario para esta compra: $1",
				CustomizableException.class, () -> farmService.buy("eggs", 1));

	}

	@Test
	public void buySomethingElse() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		Farm farm1 = new Farm();
		farm1.setCash(0);
		farm.add(farm1);
		Mockito.when(farmRepository.findAll()).thenReturn(farm);
//		Mockito.when(eggRepository.save(new Egg())).thenReturn(new Egg(new Date()));

		assertThrows("No es posible comprar pigs, ingrese eggs o chickens.", CustomizableException.class,
				() -> farmService.buy("pigs", 1));

	}

	@Test
	public void sellTest() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		List<Egg> eggs = new ArrayList();
		Farm farm1 = new Farm();
		Egg egg = new Egg();
		farm1.setCash(10);
		farm.add(farm1);

		eggs.add(egg);

		Mockito.when(farmRepository.findAll()).thenReturn(farm);
		Mockito.when(eggRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))).thenReturn(eggs);

		ResponseEntity<?> response = farmService.sell("eggs", 1);
		assertEquals(response.getStatusCodeValue(), 200);
		assertEquals(farm1.getCash(), 12.0, 0);
		System.out.println(response.getStatusCodeValue());
	}

	@Test
	public void sellMoreThanExisting() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		List<Egg> eggs = new ArrayList();
		Farm farm1 = new Farm();
		farm.add(farm1);

		Mockito.when(farmRepository.findAll()).thenReturn(farm);
		Mockito.when(eggRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))).thenReturn(eggs);

		assertThrows("Cantidad a vender debe ser menor o igual a la cantidad existente", CustomizableException.class,
				() -> farmService.sell("eggs", 1));
	}

	@Test
	public void sellSomethingElse() throws CustomizableException {
		List<Farm> farm = new ArrayList();
		List<Egg> eggs = new ArrayList();
		Farm farm1 = new Farm();
		farm.add(farm1);

		Mockito.when(farmRepository.findAll()).thenReturn(farm);

		assertThrows("No es posible comprar pigs, ingrese eggs o chickens.", CustomizableException.class,
				() -> farmService.sell("pigs", 1));
	}

	@Test
	public void passDaysTest() throws CustomizableException {
		List<Farm> farm = new ArrayList();

		Farm farm1 = new Farm();
		farm1.setDate(new Date());

		farm.add(farm1);

		Mockito.when(farmRepository.findAll()).thenReturn(farm);

		String date = farm1.getDate().toString();

		farmService.passDays(2);

		String newDate = farm1.getDate().toString();
		assertNotEquals(date, newDate);

	}
	
	@Test
	public void chickenTest() {
		
	}
}
