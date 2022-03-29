import React from "react";

import './css/MyChatLog.scss'

const MyChatLog = (props) => {
   return (
      <div className="div_myChatLog">
         <div className="contents">{props.chatInfo.message}</div>
         <div className="sendTime">{props.chatInfo.lastModifiedDate}</div>
         <div className="isRead">{props.chatInfo.read ? '읽음' : '안읽음'}</div>
      </div>
   );
};

export default MyChatLog;