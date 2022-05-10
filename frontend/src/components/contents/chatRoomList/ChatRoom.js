import React from "react";
import { Card, CardMedia } from "../../../../node_modules/@material-ui/core/index";
import { Link } from "../../../../node_modules/react-router-dom/index";
import noImg from '../../../noImg.png';

import './ChatRoom.scss'

const ChatRoom = (props) => {
   const url = `/chatRooms/${props.content.chatRoomId}`;

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

const renderDealState = (dealState) => {
   if (dealState === 'APPOINTMENT') return "예약중";
   else if (dealState === 'COMPLETE') return "거래완료";
   else return null;
}

   return (
      <Link to={{
         pathname: url,
      }}
      style={{ textDecoration: 'none' }}>
         <Card className="div_chatRoom" sx={{ minWidth: 100 }} >
            <div className="div_itemImg">{props.content.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.content.itemInfo.itemImg} /> }</div>
            <div className="div_targetMemberNick">{props.content.targetMemberInfo.nickname}</div>
            <div className="div_logContent">{props.content.lastLogInfo.content}</div>
            {props.content.lastLogInfo.notReadNum!=0?<div className="div_notReadNum">{props.content.lastLogInfo.notReadNum>99?'99+':props.content.lastLogInfo.notReadNum}</div>:null}
            <div className="div_lastDate">{changeDate(props.content.lastLogInfo.lastModifiedDate)}</div>
            {props.content.itemInfo.dealState?<div className="div_dealState">{renderDealState(props.content.itemInfo.dealState)}</div>:null}

            {/* <div className="div_itemImg">{props.content.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.content.itemInfo.itemImg} /> }</div>
            <div className="div_targetMemberNick">닉네임닉네ㅁㄴㅇㅁㄴㅇㅁㄴ임닉네임ㅁㄴㅇㅁㄴㅇ</div>
            <div className="div_logContent">내용내용내ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ용ㅁㅇㄴㅁㄴㅇ내용내용내용</div>
            <div className="div_notReadNum">{props.content.lastLogInfo.notReadNum>99?'99+':props.content.lastLogInfo.notReadNum}</div>
            <div className="div_lastDate">2022년 12월 22일</div>
            <div className="div_dealState">{renderDealState(props.content.itemInfo.dealState)}</div> */}
         </Card>
      </Link>
   );
};

export default ChatRoom;