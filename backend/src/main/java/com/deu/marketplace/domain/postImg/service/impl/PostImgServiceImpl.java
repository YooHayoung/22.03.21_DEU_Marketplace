package com.deu.marketplace.domain.postImg.service.impl;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.domain.postImg.repository.PostImgRepository;
import com.deu.marketplace.domain.postImg.service.PostImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    @Transactional
    public void deleteByImgIdList(List<PostImg> imgList) {
        postImgRepository.deleteInBatch(imgList);
    }

    @Override
    @Transactional
    public List<PostImg> updateImgSeq(List<PostImg> postImgs) {
        AtomicInteger index = new AtomicInteger();
        List<PostImg> updatedPostImgs = postImgs.stream().map(img -> {
            img.updateImgSeq(index.getAndIncrement() + 1);
            return img;
        }).collect(Collectors.toList());
        return updatedPostImgs;
    }

    @Override
    public List<PostImg> getAllByPostId(Long postId) {
        return postImgRepository.getAllByPostId(postId);
    }
}
