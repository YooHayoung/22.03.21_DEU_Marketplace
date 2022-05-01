import React, { useCallback, useEffect, useRef, useState } from "react";
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
import BottomNav from "../components/nav/bottom/BottomNav";
import HeaderContainer from "../containers/HeaderContainer";
import { useSelector } from "react-redux";
import { Button } from "../../node_modules/@material-ui/core/index";

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
   const [isChatsLast, setIsChatsLast] = useState(false);
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
   const [isLastPage, setIsLastPage] = useState(true);

   const getRoomInfoAndChatLogs = (res) => {
      setRoomInfo(res.data.body.result.chatRoomInfo);
      setChatPage(res.data.body.result.chatLogInfos.pageable.pageNumber);
      setChats(chats.concat(res.data.body.result.chatLogInfos.content));
      // console.log(res.data.body.result.chatLogInfos.last);
      setIsChatsLast(res.data.body.result.chatLogInfos.last);
   }

   useEffect(() => {
      UseApi(getChatRoom, token, setToken, getRoomInfoAndChatLogs, params)
   }, []);

   //////////////////////////////////////

   const getChatLogs = (res) => {
      setChatPage(res.data.body.result.pageable.pageNumber);
      setChats(chats.concat(res.data.body.result.content));
      console.log(res.data.body.result.last);
      setIsChatsLast(res.data.body.result.last);
   }

   const getChatPageApiObject = {
      chatRoomId: params.chatRoomId,
      page: chatPage + 1,
      enterTime: date
   }

   const getChats = () => {
      console.log(isChatsLast);
      UseApi(getChatPage, token, setToken, getChatLogs, getChatPageApiObject)
   }

   // const renderChatRooms = contents.map((content) => (<ChatRoom content={content} key={content.chatRoomId} />));
   useEffect(() => {

   }, [chats])

   let targetChatCount = 0;
   let chatDate = new Date();
   chatDate.setDate(chatDate.getDate()+10);
   const renderChatLogs = (chats) => {
      const result = (JSON.parse(JSON.stringify(chats)).reverse().map((chat, idx) => {
         const thisChatDate = new Date(Date.parse((chat.lastModifiedDate).replace(' ', 'T')));
         if (chatDate.getFullYear() == thisChatDate.getFullYear() 
               && chatDate.getMonth() == thisChatDate.getMonth()
               && chatDate.getDate() == thisChatDate.getDate()) {
                  chatDate = thisChatDate;
                  console.log("nn")
                  if (roomInfo.myId === chat.senderId) {
                     targetChatCount = 0;
                     return (<><MyChatLog chatInfo={chat} key={chat.chatLogId} /></>);
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
         } else {
            chatDate = thisChatDate;
            console.log("else")
            if (roomInfo.myId === chat.senderId) {
               targetChatCount = 0;
               return (<React.Fragment key={'f' + idx}>{chatDate.getFullYear() == thisChatDate.getFullYear() 
                  && chatDate.getMonth() == thisChatDate.getMonth()
                  && chatDate.getDate() == thisChatDate.getDate()?<div className="chatDate" key={'d' + idx}>{thisChatDate.getFullYear()+'년 ' + (thisChatDate.getMonth()+1) + '월 ' + thisChatDate.getDate() + '일'}</div>:null}<MyChatLog chatInfo={chat} key={chat.chatLogId} /></React.Fragment>);
            } else {
               if (targetChatCount === 0) {
                  targetChatCount++;
                  return (
                     <React.Fragment key={'f' + idx}>
                        {chatDate.getFullYear() == thisChatDate.getFullYear() 
                  && chatDate.getMonth() == thisChatDate.getMonth()
                  && chatDate.getDate() == thisChatDate.getDate()?<div className="chatDate" key={'d' + idx}>{thisChatDate.getFullYear()+'년 ' + (thisChatDate.getMonth()+1) + '월 ' + thisChatDate.getDate() + '일'}</div>:null}
                        <TargetNickname key={'tn' + idx} nickname={roomInfo.myId === roomInfo.itemSavedMemberInfo.memberId ? roomInfo.itemSavedMemberInfo.nickname : roomInfo.requestedMemberInfo.nickname} />
                        <TargetChatLog chatInfo={chat} key={chat.chatLogId} />
                     </React.Fragment>);
               } else {
                  return (<TargetChatLog chatInfo={chat} key={chat.chatLogId} />);
               }
            }
         }
      }));
      return result;
      // return (JSON.parse(JSON.stringify(chats)).reverse().map((chat, idx) => {
      //    if (roomInfo.myId === chat.senderId) {
      //       targetChatCount = 0;
      //       return (<MyChatLog chatInfo={chat} key={chat.chatLogId} />);
      //    } else {
      //       if (targetChatCount === 0) {
      //          targetChatCount++;
      //          return (
      //             <React.Fragment key={'f' + idx}>
      //                <TargetNickname key={'tn' + idx} nickname={roomInfo.myId === roomInfo.itemSavedMemberInfo.memberId ? roomInfo.itemSavedMemberInfo.nickname : roomInfo.requestedMemberInfo.nickname} />
      //                <TargetChatLog chatInfo={chat} key={chat.chatLogId} />
      //             </React.Fragment>);
      //       } else {
      //          return (<TargetChatLog chatInfo={chat} key={chat.chatLogId} />);
      //       }
      //    }
      // }))
   }
   /*
      1. chat의 젤 위에거랑 비교. 같다면 그냥 출력.
      2. 날짜가 다르다면 newChat과 비교. 같다면 그냥 출력.
      3. newChat이 없거나 날짜가 다르다면 찍는다.
   */

   let newTargetChatCount = 0;
   let newChatDate;
   const renderNewChatLogs = (nchats) => {
      // return null;
      let origChatDate;
      if (chats.length!=0) {
         origChatDate = new Date(Date.parse((chats[0].lastModifiedDate).replace(' ', 'T')));
         newChatDate = origChatDate;
         //////////////////
      return (nchats.map((chat, idx) => {
         const thisChatDate = new Date(Date.parse((chat.lastModifiedDate).replace(' ', 'T')));
         if (newChatDate.getFullYear() == thisChatDate.getFullYear()
            && newChatDate.getMonth() == thisChatDate.getMonth()
            && newChatDate.getDate() == thisChatDate.getDate()) {
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
         } else {
            newChatDate = thisChatDate;
            if (roomInfo.myId === chat.senderId) {
               newTargetChatCount = 0;
               return (<React.Fragment key={'f' + idx}><div className="chatDate" key={'d' + idx}>{thisChatDate.getFullYear()+'년 ' + (thisChatDate.getMonth()+1) + '월 ' + thisChatDate.getDate() + '일'}</div><MyChatLog chatInfo={chat} key={chat.chatLogId} /></React.Fragment>);
            } else {
               if (newTargetChatCount === 0) {
                  newTargetChatCount++;
                  return (
                     <React.Fragment key={'f' + idx}>
                        <div className="chatDate" key={'d' + idx}>{thisChatDate.getFullYear()+'년 ' + (thisChatDate.getMonth()+1) + '월 ' + thisChatDate.getDate() + '일'}</div>
                        <TargetNickname key={'tn' + idx} nickname={roomInfo.myId === roomInfo.itemSavedMemberInfo.memberId ? roomInfo.itemSavedMemberInfo.nickname : roomInfo.requestedMemberInfo.nickname} />
                        <TargetChatLog chatInfo={chat} key={chat.chatLogId} />
                     </React.Fragment>);
               } else {
                  return (<TargetChatLog chatInfo={chat} key={chat.chatLogId} />);
               }
            }
            
         }




         // if (roomInfo.myId === chat.senderId) {
         //    targetChatCount = 0;
         //    return (<MyChatLog chatInfo={chat} key={chat.chatLogId} />);
         // } else {
         //    if (targetChatCount === 0) {
         //       targetChatCount++;
         //       return (
         //          <React.Fragment key={'f' + idx}>
         //             <TargetNickname key={'tn' + idx} nickname={roomInfo.myId === roomInfo.itemSavedMemberInfo.memberId ? roomInfo.itemSavedMemberInfo.nickname : roomInfo.requestedMemberInfo.nickname} />
         //             <TargetChatLog chatInfo={chat} key={chat.chatLogId} />
         //          </React.Fragment>);
         //    } else {
         //       return (<TargetChatLog chatInfo={chat} key={chat.chatLogId} />);
         //    }
         // }
      }))
   } else {
      return null;
   }
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

   const scrollRef = useRef();
   const scrollToBottom = () => {
      console.log(scrollRef.current);
      if (scrollRef.current) {
         // scrollRef.current.scrollTop = scrollRef.current.scrollHeight + 68;
         // console.log(scrollRef.current.scrollHeight);
         // console.log(document.body.scrollHeight);
         // scrollRef.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'end' });
         document.body.scrollIntoView(false);
      }
   };

   const scrollToTop = () => {
      console.log(scrollRef.current);
      if (scrollRef.current) {
         scrollRef.current.scrollTop = scrollRef.current.scrollHeight + 68;
         console.log(scrollRef.current.scrollHeight);
         console.log(document.body.scrollHeight);
         scrollRef.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'end' });
         // document.body.scrollIntoView(true);
      }
   };

   // useEffect(() => {
   //    scrollToBottom();
   // }, [chats]);
   useEffect(() => {
      scrollToBottom();
   }, [newChats]);

   const onScrollTop = () => {
      if (!isChatsLast) {
         // getChats();
         // 여기 부터 코드 작성
         // 스크롤 올리면 데이터 가져오기 실행
      }
   }

   return (
      <div className="div_chatRoomPage">
         <HeaderContainer pageName={"채팅내역"}/>
         <div className="div_contents" ref={scrollRef}>
            <ItemInfo itemInfo={roomInfo.itemInfo} />
            {onScrollTop()}
            <div className="div_chatLogs">
               {/* <button onClick={getChats}>불러오기</button> */}
               {isChatsLast?null:(<Button className="btn_getChats" onClick={getChats}>불러오기</Button>)}
               {/* {chatDate.getTime()} */}
               {renderChatLogs(chats)}
               {renderNewChatLogs(newChats)}
               {/* <div className="div_myChatLog">
                <div className="contents">내용내용내용내용내용내용내용</div>
                <div className="sendTime">2022-04-30 19:28:20</div>
            </div>
            <div className="div_myChatLog">
                <div className="contents">내용내용내용내용내용내용내용</div>
                <div className="sendTime">2022-04-30 19:28:20</div>
            </div>
            <div className="div_targetNickname">닉네임</div>
            <div className="div_targetChatLog">
                <div className="contents">채팅내용채팅내용채팅내용</div>
                <div className="sendTime">2022-04-30 19:30:11</div>
            </div>
            <div className="div_targetChatLog">
                <div className="contents">채팅내용채팅내용채팅내용</div>
                <div className="sendTime">2022-04-30 19:30:11</div>
            </div>
            <div className="div_targetChatLog">
                <div className="contents">채팅내용채팅내용채팅내용</div>
                <div className="sendTime">2022-04-30 19:30:11</div>
            </div>
            <div className="div_myChatLog">
                <div className="contents">내용내용내용내용내용내용내용</div>
                <div className="sendTime">2022-04-30 19:28:20</div>
            </div>
            <div className="div_targetNickname">닉네임</div>
            <div className="div_targetChatLog">
                <div className="contents">채팅내용채팅내용채팅내용</div>
                <div className="sendTime">2022-04-30 19:30:11</div>
            </div>
            <div className="div_myChatLog">
                <div className="contents">내용내용내용내용내용내용내용</div>
                <div className="sendTime">2022-04-30 19:28:20</div>
            </div>
            <div className="div_targetNickname">닉네임</div>
            <div className="div_targetChatLog">
                <div className="contents">채팅내용채팅내용채팅내용</div>
                <div className="sendTime">2022-04-30 19:30:11</div>
            </div> */}
            </div> 
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
         <BottomNav onClick={publish} />
      </div>
   );
};

export default ChatRoomPage;