package chickenTest.ChickenTest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chickenTest.ChickenTest.entities.Farm;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {

}
