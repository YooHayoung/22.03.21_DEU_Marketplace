import React from "react";
import { Card, CardMedia, Chip } from "../../../../node_modules/@material-ui/core/index";
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
      // <Link to={{
      //    pathname: url,
      // }}
      // style={{ textDecoration: 'none' }}>
         <Card className="div_chatRoom" sx={{ minWidth: 100 }} onClick={() => {window.location.pathname=url}}>
            <div className="div_itemImg">{props.content.itemInfo.itemImg === null ? <img src={noImg} /> : <img src={props.content.itemInfo.itemImg} /> }</div>
            <div className="div_targetMemberNick">{props.content.targetMemberInfo.nickname}</div>
            <div className="div_logContent">{props.content.lastLogInfo.content}</div>
            {(props.content.lastLogInfo.notReadNum&&props.content.lastLogInfo.notReadNum!=0)?<div className="div_notReadNum">{props.content.lastLogInfo.notReadNum>99?'99+':props.content.lastLogInfo.notReadNum}</div>:null}
            <div className="div_lastDate">{changeDate(props.content.lastLogInfo.lastModifiedDate)}</div>
            {props.content.itemInfo.dealState?<Chip className="div_dealState" label={renderDealState(props.content.itemInfo.dealState)} size="small" color={props.content.dealInfo.dealTargetMemberInfo.memberId==props.content.targetMemberInfo.memberId||props.content.dealInfo.dealTargetMemberInfo.memberId==props.myId?(props.content.itemInfo.dealState=="APPOINTMENT"?"info":"success"):"default"} />:null}
         </Card>
      // </Link>
   );
};

export default ChatRoom;