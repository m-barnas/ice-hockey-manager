package cz.muni.fi.pa165.facades;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Lukáš Kotol
 */
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper dozer;

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u, mapToClass);
    }

    public Mapper getMapper() {
        return dozer;
    }
}
