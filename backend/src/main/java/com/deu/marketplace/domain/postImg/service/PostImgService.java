package com.deu.marketplace.domain.postImg.service;

import com.deu.marketplace.domain.postImg.entity.PostImg;

import java.util.List;

public interface PostImgService {

    List<PostImg> saveAll(List<PostImg> postImgs);

    void deleteAllByPostId(Long postId);

    List<PostImg> getAllByPostId(Long postId);
}
