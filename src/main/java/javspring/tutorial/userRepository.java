package javspring.tutorial;

import org.springframework.stereotype.Repository;

import javspring.tutorial.model.users;

@Repository
public interface userRepository extends crudRepository<users, String> {
    
}
