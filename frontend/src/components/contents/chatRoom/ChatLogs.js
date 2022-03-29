// import React, { useEffect, useState } from "react";

// import MyChatLog from "./MyChatLog";
// import TargetChatLog from "./TargetChatLog";
// import TargetNickname from "./TargetNickname";

// const ChatLogs = (props) => {
//    const [targetChatCount, setTargetChatCount] = useState(0);
//    const [chatLogs, setChatLogs] = useState();

//    useEffect(() => {
//       setChatLogs(props.chats);
//    }, [props])


//    // const renderChatLogs = chats.slice(0).reverse().map((chat, idx) => {
//    //    if (roomInfo.myId === chat.senderId) {
//    //       targetChatCount = 0;
//    //       return (<MyChatLog chatInfo={chat} key={chat.chatLogId} />);
//    //    } else {
//    //       if (targetChatCount === 0) {
//    //          targetChatCount++;
//    //          return (
//    //             <React.Fragment key={'f' + idx}>
//    //                <TargetNickname key={'tn' + idx} nickname={roomInfo.myId === roomInfo.itemSavedMemberInfo.memberId ? roomInfo.itemSavedMemberInfo.nickname : roomInfo.requestedMemberInfo.nickname} />
//    //                <TargetChatLog chatInfo={chat} key={chat.chatLogId} />
//    //             </React.Fragment>);
//    //       } else {
//    //          return (<TargetChatLog chatInfo={chat} key={chat.chatLogId} />);
//    //       }
//    //    }
//    // });

//    const renderChatLogs = () => {
//       const chatLogList = chatLogs && chatLogs.map((chatLog) => <div>{chatLog.chatLogId}</div>);
//       console.log(chatLogs);
//       return chatLogList;
//    };

//    return (
//       <React.Fragment>
//          <div>
//             {renderChatLogs()}
//          </div>
//       </React.Fragment>
//    );
// };

// export default ChatLogs;