import React from "react";

import './css/TargetChatLog.scss'

const TargetChatLog = (props) => {
   const changeDate = (date) => {
      const ddd = new Date(Date.parse(date.replace(' ', 'T')));
      let hour = ddd.getHours();
      let min = ddd.getMinutes();
      if (hour.toString().length==1) hour = "0" + hour;
	   if (min.toString().length==1) min = "0" + min;
      return (hour+":"+min);
   }

   return (
      <div className="div_targetChatLog">
         <div className="contents">{props.chatInfo.message}</div>
         <div className="sendTime">{changeDate(props.chatInfo.lastModifiedDate)}</div>
      </div>
   );
};

export default TargetChatLog;