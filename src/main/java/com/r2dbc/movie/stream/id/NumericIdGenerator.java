package com.r2dbc.movie.stream.id;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

public class NumericIdGenerator implements IdentifierGenerator {
    private static final SecureRandom random = new SecureRandom();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return 1_000_000L + random.nextInt(9_000_000);
    }
}
