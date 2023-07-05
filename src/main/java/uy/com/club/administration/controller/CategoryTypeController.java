package uy.com.club.administration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.club.administration.dto.request.CategoryTypeDTO;
import uy.com.club.administration.dto.request.CategoryTypeListDTO;
import uy.com.club.administration.services.CategoryTypeService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category-type")
@RequiredArgsConstructor
public class CategoryTypeController {
    private final CategoryTypeService categoryTypeService;

    @RequestMapping(value = "/get-roots", method = RequestMethod.GET)
    List<CategoryTypeListDTO> getRootsCategories() {
        return categoryTypeService.getRootsCategories();
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    Page<CategoryTypeListDTO> categoryTypePageable(Pageable pageable) {
        return categoryTypeService.findAll(pageable);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Page<CategoryTypeDTO>> findCategoryTypePageable(@RequestBody CategoryTypeDTO categoryTypeDTO, Pageable pageable) {
        return ResponseEntity.ok(categoryTypeService.findCategoryTypeByQuery(categoryTypeDTO, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryTypeDTO> createCategoryType(@RequestBody CategoryTypeDTO categoryTypeDTO) {
        return ResponseEntity.ok(categoryTypeService.createCategoryType(categoryTypeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryTypeDTO> updateCategoryType(@PathVariable String id, @RequestBody CategoryTypeDTO categoryTypeDTO) {
        return ResponseEntity.ok(categoryTypeService.updateCategoryType(id, categoryTypeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryTypeDTO> getCategoryTypeById(@PathVariable String id) {
        return ResponseEntity.ok(categoryTypeService.getCategoryType(id));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryTypeDTO>> getAllCategoryType() {
        return ResponseEntity.ok(categoryTypeService.getAllCategoryType());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategoryTypeById(@PathVariable String id) {
        categoryTypeService.deleteCategoryType(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{parentId}")
    public ResponseEntity<List<CategoryTypeListDTO>> getSubCategoryByParentId(@PathVariable String parentId) {
        return ResponseEntity.ok(categoryTypeService.getSubCategoryFromParent(parentId));
    }
}