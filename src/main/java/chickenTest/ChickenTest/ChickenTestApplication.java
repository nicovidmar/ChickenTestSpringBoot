package chickenTest.ChickenTest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;

import chickenTest.ChickenTest.constantes.Constantes;
import chickenTest.ChickenTest.entities.Farm;
import chickenTest.ChickenTest.repositories.FarmRepository;

@SpringBootApplication
public class ChickenTestApplication implements CommandLineRunner {

	@Autowired
	FarmRepository farmRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChickenTestApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Farm farm = new Farm();
		farm.setCash(Constantes.INITIAL_CASH);
		farm.setDate(new Date());
		farm.setMaxEggs(Constantes.MAX_EGGS);
		farm.setMaxChickens(Constantes.MAX_CHICKENS);
		farmRepository.save(farm);
	}

}
