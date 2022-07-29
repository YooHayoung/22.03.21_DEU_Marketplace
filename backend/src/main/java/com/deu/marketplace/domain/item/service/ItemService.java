package com.deu.marketplace.domain.item.service;

import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.repository.ItemRepository;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.exception.MemberAndWriterNotSameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    public boolean delete(Member member, Long itemId) {
        Item item = itemRepository.findById(itemId).filter(i -> !i.getDeleted()).orElseThrow();
        if (!item.getWriter().equals(member)) {
            throw new MemberAndWriterNotSameException("권한이 없습니다.");
        }
        item.setDeleted(true);
        return item.getDeleted();
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

    @Transactional
    public Item update(Item item, Item updateInfo, List<String> storeFileNamesForDelete) {
        item.updateItem(updateInfo);

        AtomicInteger i = new AtomicInteger();
        List<ItemImg> collect = item.getItemImgs().stream()
                .filter(itemImg -> storeFileNamesForDelete.contains(itemImg.getStoreFileName()))
                .collect(Collectors.toList());

        item.getItemImgs().removeAll(collect);
        item.getItemImgs()
                .forEach(itemImg -> itemImg.updateImgSeq(i.incrementAndGet()));

        return item;
    }

    // 상품 식별자로 조회
    public Optional<Item> findById(Long itemId) {
        return itemRepository
                .findById(itemId)
                .filter(item -> !item.getDeleted());
    }

    public Optional<Item> findByIdAndMember(Long itemId, Member member) {
        return itemRepository
                .findByItemIdAndMemberId(itemId, member.getId())
                .filter(item -> !item.getDeleted());
    }

    // 상품 목록 조회 - 검색, 페이징
}
