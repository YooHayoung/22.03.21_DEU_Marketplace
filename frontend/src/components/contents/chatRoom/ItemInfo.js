import React from "react";

import './css/ItemInfo.scss'

const ItemInfo = (props) => {
   return (
      <div className="div_itemInfo">
         <div className="itemImg">{props.itemInfo.itemImg === null ? '이미지 없음' : props.itemInfo.itemImg}</div>
         <div className="title">{props.itemInfo.title}</div>
         <div className="price">{props.itemInfo.price}</div>
         <div className="dealState">{props.itemInfo.dealState}</div>
         <div className="btn_stateChange">상태변경</div>
      </div>
   );
};

export default ItemInfo;