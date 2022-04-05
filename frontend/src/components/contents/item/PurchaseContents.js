import React from 'react';
import './PurchaseContents.css';

function PurchaseContents(props) {
    return (
        <div className="board_wrap">
        <div className="board_list">
            <div className="list_top">
                <div className="num">번호</div>
                <div className="title">제목</div>
                <div className="writer">글쓴이</div>
                <div className="date">날짜</div>
            </div>
            <div className>
                <div className="num">1</div>
                <div className="title">제목입니다</div>
                <div className="writer">감경률</div>
                <div className="date">2022.04.02</div>
            </div>
            <div className>
                <div className="num">1</div>
                <div className="title">제목입니다</div>
                <div className="writer">감경률</div>
                <div className="date">2022.04.02</div>
            </div>
            <div className>
                <div className="num">1</div>
                <div className="title">제목입니다</div>
                <div className="writer">감경률</div>
                <div className="date">2022.04.02</div>
            </div>
        </div>
        <div className="board_page">
            <a href="#" className="btn first">&lt;&lt;</a>
            <a href="#" className="btn prev">&lt;</a>
            <a href="#" className="num on">1</a>
            <a href="#" className="num">2</a>
            <a href="#" className="num">3</a>
            <a href="#" className="num">4</a>
            <a href="#" className="btn next">&gt;</a>
            <a href="#" className="btn last">&gt;&gt;</a>
        </div>
    </div>
    );
}

export default PurchaseContents;