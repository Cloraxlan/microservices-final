package edu.msoe.microservicefinal.snowmanPostMicroservice.database;

import edu.msoe.microservicefinal.snowmanPostMicroservice.entity.Snowman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnowmanRepository extends JpaRepository<Snowman, Long> {
    @Query("SELECT sm FROM Snowman sm WHERE city = ?1")
    List<Snowman> findByCity(String city);
}
