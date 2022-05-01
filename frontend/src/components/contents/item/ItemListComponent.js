import React, { useState } from "react";
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';
import "./ItemListComponent.scss";
import { UseApi } from "../../../api/UseApi";
import { setWishItem } from "../../../api/Api";
import { Link } from "../../../../node_modules/react-router-dom/index";
import noImg from "/Users/hayoungyoo/yoo_dev/22.03.21_DEU_Marketplace/frontend/src/noImg.png";
import { Card } from "../../../../node_modules/@material-ui/core/index";

const ItemListComponent = (props) => {
    const [content, setContent] = useState(props.content);

    const renderDealState = (dealState) => {
        if (dealState === 'APPOINTMENT') return <div className="itemDealState">예약중</div>;
        else if (dealState === 'COMPLETE') return <div className="itemDealState">거래완료</div>;
        else return null;
    }

    const renderItemCategory = (itemCategoryName) => {
        if (itemCategoryName=='강의 관련 물품' || itemCategoryName=='대학 교재') {
            return <div className="itemCategory">대학</div>;
        }
    }

    const renderLecture = (lectureName) => {
        if (lectureName) return <div className="itemLecture">{lectureName}</div>
    }

    const renderProfessor = (professorName) => {
        if (professorName) return <div className="itemProfessor">{professorName}</div>
    }

    const work = (res) => {
        console.log(res);
        setContent((prevState) => ({
            ...prevState,
            wishedMemberId: res.data.body.result
        }));
    }

    const handleChange = (event) => {
        event.stopPropagation();
        UseApi(setWishItem, props.token, props.setToken, work, {itemId: content.itemId})
        console.log(content.wishedMemberId);
    };

    const changeDate = (date) => {
        const now = new Date();
        const ddd = new Date(Date.parse(date.replace(' ', 'T')));
        const passedTime = now-ddd;

        let result = passedTime/1000;
        if (result >= 60) { 
            result = result/60;
            if (result >= 60) {
                 result = result/60;
                 if (result >= 24) {
                    result = result/24;
                    if(result > 30) {
                       if (now.getFullYear() == ddd.getFullYear()) {
                          return ((ddd.getMonth()+1)+"월 "+ddd.getDate()+"일");
                       } else {
                          return (ddd.getFullYear()+"년 "+(ddd.getMonth()+1)+"월 "+ddd.getDate()+"일");
                       }
                    } else {
                       return (Math.floor(result) + "일 전");
                    }
                 } else {
                    return (Math.floor(result) + "시간 전");
                }
            } else {
                return (Math.floor(result) + "분 전");
            }
        } else {
            return (Math.floor(result) + "초 전");
        }
     }

    return(
        <Link to={{
            pathname: `/item/${content.itemId}`
        }}
        style={{ textDecoration: 'none', color: 'black' }}>
        <Card className="itemListCompo">
            <div className="itemImg">{content.itemImgFile===null? <img src={noImg} />: <img src={content.itemImgFile} />}</div>
            <div className="itemTitle"><b>{content.title}</b></div>
            {renderItemCategory(content.itemCategoryName)}
            {renderLecture(content.lectureName)}
            {renderProfessor(content.professorName)}
            <div className="itemPrice">{content.price.toLocaleString()}원</div>
            <div className="itemLastModifiedDate">{changeDate(content.lastModifiedDate)}</div>
            {renderDealState(content.dealState)}
            <Checkbox className="itemWishedMember" icon={<FavoriteBorder />} checkedIcon={<Favorite />} 
                checked={content.wishedMemberId===null?false:true} onClick={handleChange}/>
            {/* <div className="itemImg">{content.itemImgFile===null? <img src={noImg} />: <img src={content.itemImgFile} />}</div>
            <div className="itemTitle"><b>제목제목제목제목제목제목제목제목제목제목</b></div>
            {renderItemCategory(content.itemCategoryName)}
            {renderLecture('강의명강의명강의명강의명')}
            {renderProfessor('교수명교수명교수명교수명')}
            <div className="itemPrice">{(999999999).toLocaleString()}원</div>
            <div className="itemLastModifiedDate">2022년 12월 22일</div>
            {renderDealState(content.dealState)}
            <Checkbox className="itemWishedMember" icon={<FavoriteBorder />} checkedIcon={<Favorite />} 
                checked={content.wishedMemberId===null?false:true} onClick={handleChange}/> */}
        </Card>
        </Link>
    );
};

export default ItemListComponent;