package com.deu.marketplace.domain.item.service;

import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.repository.ItemRepository;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 등록
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    // 상품 삭제
    @Transactional
    public void delete(Member member, Long itemId) {
        // 회원 인증 -> 상품 식별자 & 로그인된 회원으로 상품 검색 -> 삭제 되었는지 확인 -> 삭제 되지 않았으면 삭제 진행
        itemRepository.findByItemIdAndMemberId(itemId, member.getId())
                .filter(item -> !item.getDeleted())
                .ifPresent(item -> item.setDeleted(true));
    }

    // 상품 수정
    @Transactional
    public Optional<Item> update(Member member, Long itemId, Item updateInfo) {
        // 회원 인증 -> 상품 식별자 & 로그인된 회원으로 상품 검색 -> 삭제 되었는지 확인
        // -> 삭제되지 않았으면 상품 정보 수정 진행
        // -> 해당되는 상품이 없으면 빈 Optional 반환, 컨트롤러에서 처리?
        Optional<Item> findItem = itemRepository.findByItemIdAndMemberId(itemId, member.getId())
                .filter(item -> !item.getDeleted());
        findItem.ifPresent(item -> item.updateItem(updateInfo));
        return findItem;
    }

    // 상품 식별자로 조회
    public Optional<Item> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    // 상품 목록 조회 - 검색, 페이징
}
