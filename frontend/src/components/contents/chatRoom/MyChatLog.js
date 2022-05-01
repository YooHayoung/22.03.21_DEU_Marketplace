import React from "react";

import './css/MyChatLog.scss'

const MyChatLog = (props) => {
   const changeDate = (date) => {
      const ddd = new Date(Date.parse(date.replace(' ', 'T')));
      let hour = ddd.getHours();
      let min = ddd.getMinutes();
      if (hour.toString().length==1) hour = "0" + hour;
	   if (min.toString().length==1) min = "0" + min;
      return (hour+":"+min);
   }

   return (
      <div className="div_myChatLog">
         <div className="contents">{props.chatInfo.message}</div>
         <div className="sendTime">{changeDate(props.chatInfo.lastModifiedDate)}</div>
         {/* <div className="isRead">{props.chatInfo.read ? '읽음' : '안읽음'}</div> */}
      </div>
   );
};

export default MyChatLog;