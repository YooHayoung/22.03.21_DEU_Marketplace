package com.deu.marketplace.domain.deal.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deal extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member targetMember;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(nullable = false)
    private String meetingPlace;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DealState dealState;

    @Builder
    public Deal(Item item, Member targetMember, LocalDateTime appointmentDateTime, String meetingPlace) {
        this.item = item;
        this.targetMember = targetMember;
        this.appointmentDateTime = appointmentDateTime;
        this.meetingPlace = meetingPlace;
        appointmentDeal();
    }

    public void completeDeal(){
        this.dealState = DealState.COMPLETE;
    }

    public void cancelDeal() {
        this.dealState = DealState.CANCEL;
    }

    public void appointmentDeal() {
        this.dealState = DealState.APPOINTMENT;
    }
}
