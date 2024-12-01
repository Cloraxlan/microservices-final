package edu.msoe.microservicefinal.snowmanPostMicroservice.database;

import edu.msoe.microservicefinal.snowmanPostMicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT usrs FROM User usrs JOIN usrs.notifyCities c WHERE c = ?1")
    List<User> getAllByCity(String city);
}