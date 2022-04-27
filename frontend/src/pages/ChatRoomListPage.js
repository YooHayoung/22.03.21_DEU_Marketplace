import React, { useEffect, useState } from "react";

import ChatRoom from "../components/contents/chatRoomList/ChatRoom";

import axios from "../../node_modules/axios/index";
import { useCookies } from "../../node_modules/react-cookie/cjs/index";
import { Cookies } from "../../node_modules/react-cookie/cjs/index";
import { UseApi } from "../api/UseApi";
import { getChatRoomPage } from "../api/Api";
import HeaderContainer from "../containers/HeaderContainer";
import BottomNav from "../components/nav/bottom/BottomNav";

const ChatRoomListPage = ({ token, setToken }) => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);
   const cookies = new Cookies('token');

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
      <div className="div_chatRoomPage">
         <HeaderContainer pageName={"채팅방 목록"} />
         {/* <h1>채팅방</h1> */}
         {renderChatRooms}
         <BottomNav />
      </div>
   );
};

export default ChatRoomListPage;