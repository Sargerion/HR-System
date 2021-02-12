package edu.epam.project.entity;

public abstract class Entity {

    private Integer entityId;

    public Entity() {
    }

    public Entity(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        return entityId.equals(entity.entityId);
    }

    @Override
    public int hashCode() {
        return entityId != null ? entityId.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entity id=");
        sb.append(entityId);
        return sb.toString();
    }
}