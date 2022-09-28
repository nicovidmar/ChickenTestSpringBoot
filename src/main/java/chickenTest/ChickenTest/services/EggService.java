package chickenTest.ChickenTest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chickenTest.ChickenTest.entities.Egg;
import chickenTest.ChickenTest.repositories.EggRepository;

@Service
public class EggService {
	@Autowired
	private EggRepository eggRepository;

	public List<Egg> findAll() {
		return eggRepository.findAll();
	}

}
