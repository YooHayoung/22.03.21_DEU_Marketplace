import React, { useEffect, useRef, useState } from "react";
import axios from "../../node_modules/axios/index";
import { useMatch, useParams } from "../../node_modules/react-router-dom/index";
import ChatLogs from "../components/contents/chatRoom/ChatLogs";


/////
import SockJS from 'sockjs-client';
import * as StompJS from '@stomp/stompjs';
import jwt_decode from "jwt-decode";
import SockJsClient from 'react-stomp';
import { TalkBox } from "react-talk";
import { Stomp } from "../../node_modules/stompjs/lib/stomp";
/////


import ItemInfo from "../components/contents/chatRoom/ItemInfo";
import MyChatLog from "../components/contents/chatRoom/MyChatLog";
import TargetChatLog from "../components/contents/chatRoom/TargetChatLog";
import TargetNickname from "../components/contents/chatRoom/TargetNickname";
import MessageInputOnBottom from "../components/nav/bottom/MessageInputOnBottom";
import BarWithBackOnTop from "../components/nav/top/BarWithBackOnTop";
// import jwt_decode from "jwt-decode";

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
   const [newChats, setNewChats] = useState([]);
   const [chatPage, setChatPage] = useState(0);
   const [message, setMessage] = useState('');
   const client = useRef({});
   const [chatMessages, setChatMessages] = useState([]);
   const [date, setDate] = useState('');
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

   //////////////////////////////////////

   const getChatLogs = (res) => {
      setChatPage(res.data.pageable.pageNumber);
      setChats(chats.concat(res.data.content));
   }

   const getChatPageApiObject = {
      chatRoomId: params.chatRoomId,
      page: chatPage + 1,
      enterTime: date
   }

   const getChats = () => {
      UseApi(getChatPage, token, setToken, getChatLogs, getChatPageApiObject)
   }

   // const renderChatRooms = contents.map((content) => (<ChatRoom content={content} key={content.chatRoomId} />));
   useEffect(() => {

   }, [chats])
   let targetChatCount = 0;
   const renderChatLogs = (chats) => {
      return (JSON.parse(JSON.stringify(chats)).reverse().map((chat, idx) => {
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
      }))
   }

   const renderNewChatLogs = (chats) => {
      return (chats.map((chat, idx) => {
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
      }))
   }


   useEffect(() => {
      connect();

      // return () => disconnect();
   }, []);

   const connect = () => {
      setDate(new Date(+new Date() + 3240 * 10000).toISOString().replace("T", "_").replace(/\..*/, ''));
      client.current = new StompJS.Client({
         webSocketFactory: () => new SockJS("http://localhost:8000/ws"),

         connectHeaders: {
            Authorization: `Bearer ${token}`
         },
         debug: function (strr) {
            console.log(strr);
         },
         reconnectDelay: 5000,
         heartbeatIncoming: 4000,
         heartbeatOutgoing: 4000,
         onConnect: () => {
            subscribe();
         },
         onStompError: (frame) => {
            console.error(frame);
         },
      });

      client.current.activate();
   };

   const disconnect = () => {
      client.current.deactivate();
   };

   const subscribe = () => {
      client.current.subscribe(`/sub/chat/${params.chatRoomId}`, ({ body }) => {
         console.log(body);
         setChatMessages((_chatMessages) => [..._chatMessages, JSON.parse(body)]);
         setNewChats((_chatMessages) => [..._chatMessages, JSON.parse(body)]);
      });
   };

   const publish = (message) => {
      if (!client.current.connected) {
         return;
      }

      client.current.publish({
         destination: `/pub/chat/${params.chatRoomId}`,
         body: JSON.stringify({ message: message, senderId: jwt_decode(token).sub }),
      });

      console.log(date);
      setMessage("");
   };



   return (
      <div className="div_chatRoomPage">
         <BarWithBackOnTop />
         <div className="div_contents">
            <button onClick={getChats}>불러오기</button>
            <ItemInfo itemInfo={roomInfo.itemInfo} />
            {renderChatLogs(chats)}
            {renderNewChatLogs(newChats)}
         </div>
         {/* <div>
            {chatMessages && chatMessages.length > 0 && (
               <ul>
                  {chatMessages.map((_chatMessage, index) => (
                     <li key={index}>{_chatMessage.message}</li>
                  ))}
               </ul>
            )}
            <div>
               <input
                  type={"text"}
                  placeholder={"message"}
                  value={message}
                  onChange={(e) => setMessage(e.target.value)}
                  onKeyPress={(e) => e.which === 13 && publish(message)}
               />
               <button onClick={() => publish(message)}>send</button>
            </div>
         </div> */}
         <MessageInputOnBottom onClick={publish} />
      </div>
   );
};

export default ChatRoomPage;