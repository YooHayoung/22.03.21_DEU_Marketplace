package com.deu.marketplace.domain.postImg.repository;

import com.deu.marketplace.domain.postImg.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {
}
