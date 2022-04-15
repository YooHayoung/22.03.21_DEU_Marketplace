package com.deu.marketplace.domain.postImg.repository;

import com.deu.marketplace.domain.postImg.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {

    List<PostImg> getAllByPostId(Long postId);
}
