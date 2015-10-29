package com.example.demo.domain;


import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
public abstract class AbstractNode<T extends AbstractNode> implements Identifiable, Serializable {
    private static final long serialVersionUID = 1L;

    @GraphId
    protected Long id;

    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("unchecked")
    public T setId(final Long id) {
        this.id = id;
        return (T) this;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AbstractNode)) return false;
        final AbstractNode other = (AbstractNode) o;
        if (!other.canEqual((Object) this)) return false;

        final Object this$primaryId = this.getPrimaryId();
        final Object other$primaryId = other.getPrimaryId();
        if (this$primaryId != null && this$primaryId.equals(other$primaryId)) return true;

        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 0 : $id.hashCode());
        final Object $primaryId = this.getPrimaryId();
        result = result * PRIME + ($primaryId == null ? 0 : $primaryId.hashCode());
        return result;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AbstractNode;
    }


}
