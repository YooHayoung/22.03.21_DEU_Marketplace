import React, { useEffect, useState } from "react";

import ChatRoom from "../components/contents/chatRoomList/ChatRoom";

import axios from "../../node_modules/axios/index";
import { useCookies } from "../../node_modules/react-cookie/cjs/index";
import { Cookies } from "../../node_modules/react-cookie/cjs/index";
import { UseApi } from "../api/UseApi";
import { getChatRoomPage } from "../api/Api";
import HeaderContainer from "../containers/HeaderContainer";
import BottomNav from "../components/nav/bottom/BottomNav";
import { useLocation } from "../../node_modules/react-router/index";

const ChatRoomListPage = ({ token, setToken }) => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);
   const cookies = new Cookies('token');
   const location = useLocation();
   console.log(location);

   // const work = (res) => {
   //    setContents(res);
   // }

   const work = (res) => {
      setContents(res.data.body.result.content);
   }

   useEffect(() => {
      UseApi(getChatRoomPage, token, setToken, work);
   }, []);



   const renderChatRooms = contents.map((content) => (<ChatRoom content={content} key={content.chatRoomId} />));

   return (
      <>
      <div className="div_contents">
         <HeaderContainer pageName={"채팅방 목록"} />
         {/* <h1>채팅방</h1> */}
         {renderChatRooms}
         {/* {renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms} */}
      </div>
      <BottomNav />
      </>
   );
};

export default ChatRoomListPage;