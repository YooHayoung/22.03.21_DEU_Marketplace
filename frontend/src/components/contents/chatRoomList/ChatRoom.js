import React from "react";
import { Link } from "../../../../node_modules/react-router-dom/index";

import './ChatRoom.scss'

const ChatRoom = (props) => {
   const url = `/chatRooms/${props.content.chatRoomId}`;
   return (
      <Link to={{
         pathname: url,
         state: {
            memberId: 1
         },
      }}>
         <div className="div_chatRoom" >
            <div className="div_itemImg">{props.content.itemInfo.itemImg === null ? '이미지 없음' : props.content.itemInfo.itemImg}</div>
            <div className="div_targetMemberNick">{props.content.targetMemberInfo.nickname}</div>
            <div className="div_logContent">{props.content.lastLogInfo.content}</div>
            <div className="div_notReadNum">{props.content.lastLogInfo.notReadNum}</div>
            <div className="div_dealState">{props.content.itemInfo.dealState}</div>
         </div>
      </Link>
   );
};

export default ChatRoom;