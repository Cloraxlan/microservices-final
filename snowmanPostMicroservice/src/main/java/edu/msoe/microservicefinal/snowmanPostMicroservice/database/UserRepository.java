package edu.msoe.microservicefinal.snowmanPostMicroservice.database;

import edu.msoe.microservicefinal.snowmanPostMicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
}