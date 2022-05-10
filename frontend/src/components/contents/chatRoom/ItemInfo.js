import React from "react";

import './css/ItemInfo.scss'
import noImg from '../../../noImg.png'
import { Button, Card } from "../../../../node_modules/@material-ui/core/index";

const ItemInfo = (props) => {
   // console.log(props);
   const renderDealState = (dealState) => {
      if (dealState === 'APPOINTMENT') return "예약중";
      else if (dealState === 'COMPLETE') return "거래완료";
      else return null;
   }

   return (
      <Card className="div_itemInfo">
         {/* <div className="itemImg">{props.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.itemInfo.itemImg} />}</div>
         <div className="title">{props.itemInfo.title}</div>
         <div className="price">{props.itemInfo.price.toLocaleString()}원</div>
         <div className="dealState">{renderDealState(props.itemInfo.dealState)}</div>
         <Button className="btn_stateChange" variant="contained" size="small">{props.itemInfo.dealState?'상태변경':'예약하기'}</Button> */}

         <div className="itemImg">{props.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.itemInfo.itemImg} />}</div>
         <div className="title">제목제목 제목제ㄱㅁ고좀롭ㄷㄹㅂㄷㅍㅂㅈㄷㄹ</div>
         <div className="price">{(999999999).toLocaleString()}원</div>
         {props.itemInfo.dealState?<div className="dealState">{renderDealState(props.itemInfo.dealState)}</div>:null}
         {props.itemSavedMemberId==props.myId?<Button className="btn_stateChange" variant="contained" size="small">{props.itemInfo.dealState?'상태변경':'예약하기'}</Button>:null}
      </Card>
   );
};

export default ItemInfo;