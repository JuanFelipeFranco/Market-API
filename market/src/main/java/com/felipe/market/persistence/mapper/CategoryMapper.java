package com.felipe.market.persistence.mapper;

import com.felipe.market.domain.Category;
import com.felipe.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //como queremos traducir los objetos: usamos Mappings por que son varias listas de mapping
    @Mappings({
            @Mapping(source = "idCategoria", target ="categoryId"), //fuente objeto de categoria y donde quiero llevarlo a objeto de category
            @Mapping(source = "descripcion", target ="category"),
            @Mapping(source = "estado", target ="active"),
    })
    //Convertimos categoria en category
    Category toCategory(Categoria categoria);

    @InheritInverseConfiguration //map struct sabe que hace el mapeo inverso.
    @Mapping(target = "productos", ignore = true) //como categoria tiene productos pero category no entonces lo ignoramos.
    Categoria toCategoria(Category category);
}
