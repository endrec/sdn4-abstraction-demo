package com.example.demo.data.repository;

import com.example.demo.domain.node.User;
import org.springframework.data.neo4j.annotation.Query;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
public interface UserRepository extends AbstractUuidAwareRepository<User> {

    @Query("MATCH (u:User) WHERE u.email = {0} return u")
    User findByEmail(String email);
}
