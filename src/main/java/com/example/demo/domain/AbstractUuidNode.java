package com.example.demo.domain;

import org.neo4j.ogm.annotation.Index;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
public abstract class AbstractUuidNode<T extends AbstractUuidNode> extends AbstractNode<T> implements UuidAware {

    @Index(unique = true)
    protected String uuid;

    public String getUuid() {
        return this.uuid;
    }

    public String getPrimaryId() {
        return getUuid();
    }

}
