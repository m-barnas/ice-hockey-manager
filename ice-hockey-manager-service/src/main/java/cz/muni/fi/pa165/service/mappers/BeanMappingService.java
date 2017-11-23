package cz.muni.fi.pa165.service.mappers;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Bean mapping service.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public interface BeanMappingService {

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();
}
