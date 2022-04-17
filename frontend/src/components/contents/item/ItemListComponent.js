import React, { useState } from "react";
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';
import "./ItemListComponent.scss";
import { UseApi } from "../../../api/UseApi";
import { setWishItem } from "../../../api/Api";
import { Link } from "../../../../node_modules/react-router-dom/index";

const ItemListComponent = (props) => {
    const [content, setContent] = useState(props.content);

    const renderDealState = (dealState) => {
        if (dealState === 'APPOINTMENT') return <li className="itemDealState">예약됨</li>;
        else if (dealState === 'COMPLETE') return <li className="itemDealState">거래완료</li>;
        else return null;
    }

    const renderItemCategory = (itemCategoryName) => {
        if (itemCategoryName=='강의 관련 물품' || itemCategoryName=='대학 교재') {
            return <li className="itemCategory">대학</li>;
        }
    }

    const renderLecture = (lectureName) => {
        if (lectureName) return <li className="itemLecture">{lectureName}</li>
    }

    const renderProfessor = (professorName) => {
        if (professorName) return <li className="itemProfessor">{professorName}</li>
    }

    const work = (res) => {
        console.log(res);
        setContent((prevState) => ({
            ...prevState,
            wishedMemberId: res.data.body.result
        }));
    }

    const handleChange = (event) => {
        UseApi(setWishItem, props.token, props.setToken, work, {itemId: content.itemId})
        console.log(content.wishedMemberId);
      };

    return(
        <Link to={{
            pathname: `/item/${content.itemId}`
        }}>
        <ul className="itemListCompo" >
            {/* <li className="">{props.content.classification}</li> */}
            <li className="itemImg">{content.itemImgFile===null? '이미지없음' : <img src={content.itemImgFile} />}</li>
            <li className="itemTitle">{content.title}</li>
            {renderItemCategory(content.itemCategoryName)}
            {renderLecture(content.lectureName)}
            {renderProfessor(content.professorName)}
            <li className="itemPrice">{content.price}원</li>
            <li className="itemLastModifiedDate">{content.lastModifiedDate}</li>
            {renderDealState(content.dealState)}
            <Checkbox className="itemWishedMember" icon={<FavoriteBorder />} checkedIcon={<Favorite />} 
                checked={content.wishedMemberId===null?false:true} onChange={handleChange} />
        </ul>
        </Link>
    );
};

export default ItemListComponent;