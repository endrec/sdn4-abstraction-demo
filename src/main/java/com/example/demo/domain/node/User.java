package com.example.demo.domain.node;

import com.example.demo.domain.AbstractUuidNode;
import com.example.demo.domain.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@Getter
@Setter
@ToString
@NodeEntity
@Accessors(chain = true)
public class User extends AbstractUuidNode<User> {
    @GraphId
    private Long id;

    @NotNull
    @Index(unique = true)
    private String email;

    @NotNull
    private String password;

    private Set<UserRole> roles = new HashSet<>();

}
