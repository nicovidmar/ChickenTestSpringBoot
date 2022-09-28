package chickenTest.ChickenTest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chickenTest.ChickenTest.entities.Chicken;
import chickenTest.ChickenTest.repositories.ChickenRepository;

@Service
public class ChickenService {

	@Autowired
	private ChickenRepository chickenRepository;

	public List<Chicken> findAll() {
		return chickenRepository.findAll();
	}

//	public void deleteById(int id) throws CustomizableException {
//		if (chickenRepository.findById(id) == null) {
//			throw new CustomizableException("No existe chicken con id: " + id);
//		} else {
//			chickenRepository.deleteById(id);
//		}
//	}
}
