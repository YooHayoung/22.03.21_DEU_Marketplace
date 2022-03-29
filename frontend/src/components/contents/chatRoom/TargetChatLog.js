import React from "react";

import './css/TargetChatLog.scss'

const TargetChatLog = (props) => {
   return (
      <div className="div_targetChatLog">
         <div className="contents">{props.chatInfo.message}</div>
         <div className="sendTime">{props.chatInfo.lastModifiedDate}</div>
      </div>
   );
};

export default TargetChatLog;