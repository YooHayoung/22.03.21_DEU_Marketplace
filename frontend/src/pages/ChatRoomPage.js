import React, { useEffect, useState } from "react";
import axios from "../../node_modules/axios/index";
import { useMatch } from "../../node_modules/react-router-dom/index";
import ChatLogs from "../components/contents/chatRoom/ChatLogs";

import ItemInfo from "../components/contents/chatRoom/ItemInfo";
import MyChatLog from "../components/contents/chatRoom/MyChatLog";
import TargetChatLog from "../components/contents/chatRoom/TargetChatLog";
import TargetNickname from "../components/contents/chatRoom/TargetNickname";
import MessageInputOnBottom from "../components/nav/bottom/MessageInputOnBottom";
import BarWithBackOnTop from "../components/nav/top/BarWithBackOnTop";

import './ChatRoomPage.scss'


const ChatRoomPage = (props) => {

   const { params } = useMatch('/chatRooms/:chatRoomId');
   const [roomInfo, setRoomInfo] = useState({
      itemInfo: { // 초기화
         itemId: 0,
         itemImg: "",
         title: "",
         price: 0,
         dealState: ""
      },
   });
   const [chats, setChats] = useState([]);
   const [chatPage, setChatPage] = useState(0);
   const [message, setMessage] = useState('');

   useEffect(() => {
      (async () => {
         const headers = {
            'memberId': 1
         }
         axios.get('http://localhost:8080/api/v1/chatRoom/' + params.chatRoomId, { headers: headers })
            .then((response) => {
               // console.log(response.data);
               setRoomInfo(response.data.chatRoomInfoDto);
               setChatPage(response.data.chatLogDtoPage.pageable.pageNumber);
               setChats(chats.concat(response.data.chatLogDtoPage.content));

               // console.log(chatPage);
            })
      })();
   }, []);

   const getChats = () => {

      const headers = {
         'memberId': 1
      }
      axios.get('http://localhost:8080/api/v1/chat/' + params.chatRoomId + '?size=2&page=' + (chatPage + 1), { headers: headers })
         .then((response) => {
            // console.log(response.data);
            setChatPage(response.data.pageable.pageNumber);
            setChats(chats.concat(response.data.content));
         })
      console.log(chats);
   };

   // const renderChatRooms = contents.map((content) => (<ChatRoom content={content} key={content.chatRoomId} />));
   let targetChatCount = 0;
   const renderChatLogs = chats.slice(0).reverse().map((chat, idx) => {
      if (roomInfo.myId === chat.senderId) {
         targetChatCount = 0;
         return (<MyChatLog chatInfo={chat} key={chat.chatLogId} />);
      } else {
         if (targetChatCount === 0) {
            targetChatCount++;
            return (
               <React.Fragment key={'f' + idx}>
                  <TargetNickname key={'tn' + idx} nickname={roomInfo.myId === roomInfo.itemSavedMemberInfo.memberId ? roomInfo.itemSavedMemberInfo.nickname : roomInfo.requestedMemberInfo.nickname} />
                  <TargetChatLog chatInfo={chat} key={chat.chatLogId} />
               </React.Fragment>);
         } else {
            return (<TargetChatLog chatInfo={chat} key={chat.chatLogId} />);
         }
      }
   });

   const GetMessage = (message) => {
      setMessage(message);
      console.log(message);
   };


   return (
      <div className="div_chatRoomPage">
         <BarWithBackOnTop />
         <div className="div_contents">
            <button onClick={getChats}>불러오기</button>
            <ItemInfo itemInfo={roomInfo.itemInfo} />
            {renderChatLogs}
            {/* <ChatLogs chats={chats} /> */}
         </div>
         <MessageInputOnBottom GetMessage={GetMessage} />
      </div>
   );
};

export default ChatRoomPage;