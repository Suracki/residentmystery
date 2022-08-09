package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.Ending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EndingRepository extends JpaRepository<Ending, Integer>  {

    @Query("SELECT u FROM Ending u WHERE u.endingName = ?1")
    Ending findByName(String endingName);

}
