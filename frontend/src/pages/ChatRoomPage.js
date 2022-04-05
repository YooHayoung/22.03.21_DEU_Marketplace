import React, { useEffect, useState } from "react";
import axios from "../../node_modules/axios/index";
import { useMatch, useParams } from "../../node_modules/react-router-dom/index";
import ChatLogs from "../components/contents/chatRoom/ChatLogs";


import SockJS from 'sockjs-client';
import * as StompJS from '@stomp/stompjs';


import ItemInfo from "../components/contents/chatRoom/ItemInfo";
import MyChatLog from "../components/contents/chatRoom/MyChatLog";
import TargetChatLog from "../components/contents/chatRoom/TargetChatLog";
import TargetNickname from "../components/contents/chatRoom/TargetNickname";
import MessageInputOnBottom from "../components/nav/bottom/MessageInputOnBottom";
import BarWithBackOnTop from "../components/nav/top/BarWithBackOnTop";
import jwt_decode from "jwt-decode";

import './ChatRoomPage.scss'
import { getChatPage, getChatRoom } from "../api/Api";
import { UseApi } from "../api/UseApi";

// var sock = SockJS("/stomp/chat");
// var client = null;

const ChatRoomPage = ({ token, setToken }) => {
   const params = useParams('localhost:3000/chatRooms/:chatRoomId');

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
   const [data, setData] = useState({
      roomId: 0,
      senderId: 0,
      message: ''
   });

   const getRoomInfoAndChatLogs = (res) => {
      setRoomInfo(res.data.chatRoomInfoDto);
      setChatPage(res.data.chatLogDtoPage.pageable.pageNumber);
      setChats(chats.concat(res.data.chatLogDtoPage.content));
   }

   useEffect(() => {
      UseApi(getChatRoom, token, setToken, getRoomInfoAndChatLogs, params)
   }, []);

   /////////////////////
   // const subscribe = () => {
   //    if (client != null) {
   //       client.subscribe('/queue/chat/' + roomInfo.chatRoomId, function (chatDto) {
   //          const messagedto = JSON.parse(chatDto.body);
   //          console.log(messagedto);
   //       });
   //    }
   // };

   // useEffect(() => {
   //    connect();
   //    return () => disConnect();
   // }, []);

   // const connect = () => {
   //    client = new StompJS.Client({
   //       brokerURL: "http://localhost:8080/stomp/chat",
   //       debug: function (str) {
   //          console.log(str);
   //       },
   //       onConnect: () => {
   //          subscribe();
   //       },
   //    });

   //    client.activate();
   // };

   // const disConnect = () => {
   //    if (client != null) {
   //       if (client.connected) client.deactivate();
   //    }
   // };

   // const GetMessage = (message) => {
   //    if (client != null) {
   //       if (!client.connected) return;

   //       setMessage(message);
   //       console.log(message);
   //       setData({
   //          'roomId': roomInfo.chatRoomId,
   //          'senderId': roomInfo.myId,
   //          'message': message
   //       });

   //       client.publish({
   //          destination: "/app/chat/message/",
   //          body: JSON.stringify(data),
   //       });
   //    }
   // };

   ///////////////////////////

   // useEffect(() => {
   //    client.connect({}, () => {
   //       console.log('Connected : ' + roomInfo.myId)
   //       client.send("/app/chat/enter", {}, JSON.stringify(roomInfo.chatRoomId, roomInfo.myId)) // 접속

   //       // Create Message

   //       client.send(`/app/chat/message`, {}, JSON.stringify(data))

   //       client.subscribe('/queue/chat/' + roomInfo.chatRoomId, function (chatDto) {
   //          const messagedto = JSON.parse(chatDto.body);
   //          console.log(messagedto);
   //       })

   //    })
   //    return () => wsDisconnect();

   // }, [client, data])

   const getChatLogs = (res) => {
      setChatPage(res.data.pageable.pageNumber);
      setChats(chats.concat(res.data.content));
   }

   const getChatPageApiObject = {
      chatRoomId: params.chatRoomId,
      page: chatPage + 1
   }

   const getChats = () => {
      UseApi(getChatPage, token, setToken, getChatLogs, getChatPageApiObject)
   }

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

   // const GetMessage = (message) => {
   //    setMessage(message);
   //    console.log(message);
   //    setData({
   //       'roomId': roomInfo.chatRoomId,
   //       'senderId': roomInfo.myId,
   //       'message': message
   //    });
   // };



   return (
      <div className="div_chatRoomPage">
         <BarWithBackOnTop />
         <div className="div_contents">
            <button onClick={getChats}>불러오기</button>
            <ItemInfo itemInfo={roomInfo.itemInfo} />
            {renderChatLogs}
            {/* <ChatLogs chats={chats} /> */}
         </div>
         <MessageInputOnBottom />
         {/* GetMessage={GetMessage} */}
      </div>
   );
};

export default ChatRoomPage;