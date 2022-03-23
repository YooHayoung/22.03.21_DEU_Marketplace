package com.deu.marketplace.domain.item.repository;

import com.deu.marketplace.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    

}
