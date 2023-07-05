package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.dto.request.CategoryTypeDTO;
import uy.com.club.administration.dto.request.CategoryTypeListDTO;
import uy.com.club.administration.exception.ErrorConstants;
import uy.com.club.administration.exception.MissingCategoryTypeException;
import uy.com.club.administration.mappers.CategoryTypeMapper;
import uy.com.club.administration.repository.user.CategoryTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryTypeService {
    private final CategoryTypeRepository categoryTypeRepository;

    public CategoryTypeDTO createCategoryType(CategoryTypeDTO categoryTypeDTO) {
        CategoryType type = CategoryType.builder()
                .name(categoryTypeDTO.getName())
                .root(categoryTypeDTO.isRoot())
                .build();
        if (categoryTypeDTO.getParentId() != null) {
            updateCategoryTypeByParentId(categoryTypeDTO.getParentId(), type);
        }
        this.categoryTypeRepository.save(type);
        return CategoryTypeMapper.INSTANCE.categoryTypeToDTO(categoryTypeRepository.save(type));
    }

    private void updateCategoryTypeByParentId(String parentId, CategoryType type) {
        Optional<CategoryType> catTypeOpt = categoryTypeRepository.findById(parentId);
        catTypeOpt.map(catType -> {
            type.setParent(catType);
            return type;
        }).orElseThrow(() -> new MissingCategoryTypeException(ErrorConstants.MISSING_CATEGORY_TYPE));
    }

    public CategoryTypeDTO updateCategoryType(String id, CategoryTypeDTO categoryTypeDTO) {
        Optional<CategoryType> catTypeOpt = categoryTypeRepository.findById(id);
        if (catTypeOpt.isPresent()) {
            CategoryType type = catTypeOpt.get();
            type.setName(categoryTypeDTO.getName());
            type.setRoot(categoryTypeDTO.isRoot());
            if (categoryTypeDTO.getParentId() != null) {
                updateCategoryTypeByParentId(categoryTypeDTO.getParentId(), type);
            } else {
                type.setParent(null);
            }
            this.categoryTypeRepository.save(type);
            return CategoryTypeMapper.INSTANCE.categoryTypeToDTO(categoryTypeRepository.save(type));
        } else {
            throw new MissingCategoryTypeException(ErrorConstants.MISSING_CATEGORY_TYPE);
        }
    }

    public CategoryTypeDTO getCategoryType(String id) {
        Optional<CategoryType> catTypeOpt = categoryTypeRepository.findById(id);
        if (catTypeOpt.isPresent()) {
            CategoryType type = catTypeOpt.get();
            CategoryTypeDTO categoryTypeDTO = CategoryTypeDTO.builder()
                    .name(type.getName())
                    .root(type.isRoot())
                    .build();
            if (type.getParent() != null) {
                categoryTypeDTO.setParentId(type.getParent().getId());
            }
            return categoryTypeDTO;
        } else {
            throw new MissingCategoryTypeException(ErrorConstants.MISSING_CATEGORY_TYPE);
        }
    }

    public List<CategoryTypeDTO> getAllCategoryType() {
        List<CategoryType> categoryTypes = categoryTypeRepository.findAll();
        return categoryTypes.stream()
                .map(CategoryTypeMapper.INSTANCE::categoryTypeToDTO)
                .collect(Collectors.toList());
    }

    public Page<CategoryTypeListDTO> findAll(Pageable pageable) {
        Page<CategoryType> categoryTypesPage = categoryTypeRepository.findAll(pageable);
        return categoryTypesPage.map(CategoryTypeMapper.INSTANCE::categoryTypeToListDTO);
    }

    public List<CategoryTypeListDTO> getRootsCategories() {
        List<CategoryType> categoryTypes = categoryTypeRepository.findByRoot(true);
        return categoryTypes.stream()
                .map(CategoryTypeMapper.INSTANCE::categoryTypeToListDTO)
                .collect(Collectors.toList());
    }

    public Page<CategoryTypeDTO> findCategoryTypeByQuery(CategoryTypeDTO categoryTypeDTO, Pageable pageable) {
        Page<CategoryType> categoryTypes = categoryTypeRepository.findByMethod(categoryTypeDTO.getName(), pageable);
        return categoryTypes.map(CategoryTypeMapper.INSTANCE::categoryTypeToDTO);
    }

    public void deleteCategoryType(String id) {
        Optional<CategoryType> categoryTypeOpt = categoryTypeRepository.findById(id);
        if (categoryTypeOpt.isPresent()) {
            if (categoryTypeOpt.get().isRoot()) {
                List<CategoryType> subCate = categoryTypeRepository.findByParent_Id(id);
                if (subCate.isEmpty()) {
                    categoryTypeRepository.deleteById(id);
                } else {
                    throw new MissingCategoryTypeException(ErrorConstants.CATEGORY_TYPE_IS_ROOT);
                }
            } else {
                categoryTypeRepository.deleteById(id);
            }
        } else {
            throw new MissingCategoryTypeException(ErrorConstants.MISSING_CATEGORY_TYPE);
        }
    }

    public List<CategoryTypeListDTO> getSubCategoryFromParent(String catId) {
        List<CategoryType> categoryTypes = categoryTypeRepository.findByParent_Id(catId);
        return categoryTypes.stream()
                .map(CategoryTypeMapper.INSTANCE::categoryTypeToListDTO)
                .collect(Collectors.toList());
    }
}
