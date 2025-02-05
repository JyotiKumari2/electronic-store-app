package com.lcwd.electronic_store.repositories;

import com.lcwd.electronic_store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}
