package com.r2dbc.movie.stream.id;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

public interface GenerationStrategy extends Serializable {
    UID generateID(SharedSessionContractImplementor var1);
}
