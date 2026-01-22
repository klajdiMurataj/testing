package org.example.carrental.model.common;

import java.io.Serializable;

/**
 * Interface for entities that have a unique identifier
 */
public interface Identifiable extends Serializable {
    String getId();
    void setId(String id);
}