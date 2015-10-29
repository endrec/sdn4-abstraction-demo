package com.example.demo.data.repository;

import com.example.demo.domain.AbstractUuidNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@NoRepositoryBean
public interface AbstractUuidAwareRepository<T extends AbstractUuidNode> extends PagingAndSortingRepository<T, String> {
    @Override
    @Query("MATCH (m{uuid:{0}}) RETURN m")
    T findOne(String s);

    @Override
    @Query("MATCH (m{uuid:{0}}) OPTIONAL MATCH (n)-[r]-() DELETE r,m")
    void delete(String s);
}
