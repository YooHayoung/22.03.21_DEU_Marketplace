package com.deu.marketplace.domain.postImg.service.impl;

import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.domain.postImg.repository.PostImgRepository;
import com.deu.marketplace.domain.postImg.service.PostImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostImgServiceImpl implements PostImgService {

    private final PostImgRepository postImgRepository;

    @Override
    @Transactional
    public List<PostImg> saveAll(List<PostImg> postImgs) {
        return postImgRepository.saveAll(postImgs);
    }

    @Override
    @Transactional
    public void deleteAllByPostId(Long postId) {
        List<PostImg> findPostImg = postImgRepository.getAllByPostId(postId);
        postImgRepository.deleteInBatch(findPostImg);
    }

    @Override
    public List<PostImg> getAllByPostId(Long postId) {
        return postImgRepository.getAllByPostId(postId);
    }
}
