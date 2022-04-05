import React, { useEffect, useState } from "react";

import ChatRoom from "../components/contents/chatRoomList/ChatRoom";

import axios from "../../node_modules/axios/index";
import { useCookies } from "../../node_modules/react-cookie/cjs/index";
import { Cookies } from "../../node_modules/react-cookie/cjs/index";
import { UseApi } from "../api/UseApi";
import { getChatRoomPage } from "../api/Api";

const ChatRoomListPage = ({ token, setToken }) => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);
   const cookies = new Cookies('token');

   // const work = (res) => {
   //    setContents(res);
   // }

   const work = (res) => {
      setContents(res.data.content);
   }

   useEffect(() => {
      UseApi(getChatRoomPage, token, setToken, work);
   }, []);



   const renderChatRooms = contents.map((content) => (<ChatRoom content={content} key={content.chatRoomId} />));

   return (
      <div className="div_chatRoomPage">
         <h1>채팅방</h1>
         {renderChatRooms}
      </div>
   );
};

export default ChatRoomListPage;