package chickenTest.ChickenTest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		farm.setCash(100);
		farm.setDate(new Date());
		farm.setMaxEggs(2000);
		farm.setMaxChickens(1000);
		farmRepository.save(farm);
	}

}
