package com.deu.marketplace.domain.postImg.service;

import com.deu.marketplace.domain.postImg.entity.PostImg;

import java.util.List;

public interface PostImgService {

    List<PostImg> saveAll(List<PostImg> postImgs);

    void deleteByImgIdList(List<PostImg> imgList);

    void deleteAllByPostId(Long postId);

    List<PostImg> updateImgSeq(List<PostImg> postImgs);

    List<PostImg> getAllByPostId(Long postId);
}
