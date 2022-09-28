package chickenTest.ChickenTest.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import chickenTest.ChickenTest.entities.Chicken;
import chickenTest.ChickenTest.entities.Egg;

@Repository
public interface EggRepository extends JpaRepository<Egg, Integer> {
	@Query("select e from Egg e where e.date <= :date")
	List<Egg> findEggsBefore(@Param("date") Date date);

}