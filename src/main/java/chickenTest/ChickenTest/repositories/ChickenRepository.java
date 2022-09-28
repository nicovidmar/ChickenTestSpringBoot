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
public interface ChickenRepository extends JpaRepository<Chicken, Integer> {
	@Query("select c from Chicken c where c.date <= :date")
	List<Chicken> findChickensBefore(@Param("date") Date date);
}
