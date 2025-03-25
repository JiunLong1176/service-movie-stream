package com.r2dbc.movie.stream.id;

import com.r2dbc.movie.stream.util.RandomStringGenerator.Type;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class StandardRandomStrategy implements GenerationStrategy{
    public static final StandardRandomStrategy INSTANCE = new StandardRandomStrategy();
    private final Type[] types;
    private final int length;

    public StandardRandomStrategy() {
        this(new Type[] {Type.NUMERIC}, 5);
    }

    public StandardRandomStrategy(Type[] types, int length) {
        this.types = types;
        this.length = length;
    }

    @Override
    public UID generateID(SharedSessionContractImplementor var1) {
        return UID.generate(types, length);
    }
}
