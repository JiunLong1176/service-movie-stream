package com.r2dbc.movie.stream.id;

import com.r2dbc.movie.stream.util.RandomStringGenerator;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.internal.CoreLogging;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class UIDGenerator implements IdentifierGenerator {

    public static final String UID_GEN_STRATEGY = "uid_gen_strategy";
    public static final String UID_RANDOM_VALUE_TYPES = "uid_random_value_types";
    public static final String UID_RANDOM_VALUE_LENGTH = "uid_random_value_length";
    public static final String UID_GEN_STRATEGY_CLASS = "uid_gen_strategy_class";
    private static final CoreMessageLogger LOG = CoreLogging.messageLogger(UUIDGenerator.class);
    private GenerationStrategy strategy;
    private Class returnedClass;

    public UIDGenerator() {
    }

    public static UIDGenerator getInstance() {
        UIDGenerator generator = new UIDGenerator();
        generator.strategy = StandardRandomStrategy.INSTANCE;
        return generator;
    }

    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.strategy = (GenerationStrategy)params.get(UID_GEN_STRATEGY);

        if (this.strategy == null) {
            String strategyClassName = params.getProperty(UID_GEN_STRATEGY_CLASS);
            if (strategyClassName != null) {
                try {
                    ClassLoaderService cls = (ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class);
                    Class strategyClass = cls.classForName(strategyClassName);

                    try {
                        this.strategy = (GenerationStrategy)strategyClass.getDeclaredConstructor().newInstance();
                    } catch (Exception exception) {
//                        LOG.unableToInstantiateUuidGenerationStrategy(exception);
                    }
                } catch (ClassLoadingException exception) {
//                    LOG.unableToLocateUuidGenerationStrategy(strategyClassName);
                }
            }
        }

        if (this.strategy == null) {
            String randomTypes = params.getProperty(UID_RANDOM_VALUE_TYPES);
            String randomLengthStr = params.getProperty(UID_RANDOM_VALUE_LENGTH);

            RandomStringGenerator.Type[] generatorTypes = getRandomTypes(randomTypes).toArray(size -> new RandomStringGenerator.Type[size]);
            int randomLength = randomLengthStr != null? Integer.valueOf(randomLengthStr) : 5;
            this.strategy = new StandardRandomStrategy(generatorTypes, randomLength);
        }

        returnedClass = type.getReturnedClass();
    }

    public void configure(Method method, Properties params) {
        if (this.strategy == null) {
            String randomTypes = params.getProperty(UID_RANDOM_VALUE_TYPES);
            String randomLengthStr = params.getProperty(UID_RANDOM_VALUE_LENGTH);

            RandomStringGenerator.Type[] generatorTypes = getRandomTypes(randomTypes).toArray(size -> new RandomStringGenerator.Type[size]);
            int randomLength = randomLengthStr != null? Integer.valueOf(randomLengthStr) : 5;
            this.strategy = new StandardRandomStrategy(generatorTypes, randomLength);
        }
        returnedClass = method.getReturnType();
    }

    public Serializable generate() {
        return generate(null,  null);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (UID.class.isAssignableFrom(returnedClass)) {
            return this.strategy.generateID(session);
        } else if (String.class.isAssignableFrom(returnedClass)) {
            return this.strategy.generateID(session).toString();
        } else {
            throw new HibernateException("Unanticipated return type [" + returnedClass.getName() + "] for UID conversion");
        }
    }

    private List<RandomStringGenerator.Type> getRandomTypes(String randomTypes) {
        if (StringUtils.isNotBlank(randomTypes)) {
            return List.of(randomTypes.split(",")).stream().map(RandomStringGenerator.Type::valueOfName)
                    .filter(Objects::nonNull).collect(Collectors.toList());
        }
        return List.of(RandomStringGenerator.Type.NUMERIC); // return default value
    }
}
