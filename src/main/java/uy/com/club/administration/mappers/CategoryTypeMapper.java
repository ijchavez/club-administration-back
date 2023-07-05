package uy.com.club.administration.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.dto.request.CategoryTypeDTO;
import uy.com.club.administration.dto.request.CategoryTypeListDTO;

@Mapper
public interface CategoryTypeMapper {
    CategoryTypeMapper INSTANCE = Mappers.getMapper(CategoryTypeMapper.class);

    @Mapping(source = "parent.id", target = "parentId")
    CategoryTypeDTO categoryTypeToDTO(CategoryType categoryType);

    @Mappings({
            @Mapping(source = "parent.id", target = "parentId"),
            @Mapping(source = "parent.name", target = "parentName")
    })
    CategoryTypeListDTO categoryTypeToListDTO(CategoryType categoryType);

    CategoryType categoryType(CategoryTypeDTO categoryTypeDTO);
}
