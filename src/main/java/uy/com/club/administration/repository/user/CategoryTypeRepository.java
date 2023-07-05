package uy.com.club.administration.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uy.com.club.administration.domain.CategoryType;

import java.util.List;

public interface CategoryTypeRepository extends MongoRepository<CategoryType, String> {
    List<CategoryType> findByRoot(boolean root);

    List<CategoryType> findByParent_Id(String id);

    @Query(value = "{'name': ?0}")
    Page<CategoryType> findByMethod(String name, Pageable pageable);
}
