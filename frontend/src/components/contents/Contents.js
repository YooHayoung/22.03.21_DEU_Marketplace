import React from "react";
import ItemInfo from "./chatRoom/ItemInfo";
import MyChatLog from "./chatRoom/MyChatLog";
import TargetChatLog from "./chatRoom/TargetChatLog";

const Contents = () => {
   return (
      <div className="contents">
         <ItemInfo />
         <MyChatLog />
         <TargetChatLog />
      </div>
   );
};

export default Contents;

