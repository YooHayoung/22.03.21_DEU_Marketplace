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
import { Button } from "../../node_modules/@material-ui/core/index";


const ChatRoomListPage = ({ token, setToken }) => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);
   const [isLastPage, setIsLastPage] = useState(false); 

   const cookies = new Cookies('token');
   const location = useLocation();

   console.log(location);

   // const work = (res) => {
   //    setContents(res);
   // }

   const work = (res) => {
      console.log(res.data.body.result);
      setIsLastPage(res.data.body.result.last);
      setContents(res.data.body.result.content);
      setPage(page+1);
   }

   useEffect(() => {
      UseApi(getChatRoomPage, token, setToken, work, {page: page});
   }, []);

   const afterGetPage = (res) => {
      console.log(res.data.body);
      setIsLastPage(res.data.body.result.last);
      if (res.data.body.result.totalPages !== page){
         setContents([...contents, ...res.data.body.result.content]);
         setPage(page+1);
      }
   }

   const getPages = () => {
      (async () => {
         UseApi(getChatRoomPage, token, setToken, afterGetPage, { page: page});
      })();
   }

   const renderChatRooms = contents.map((content) => (<ChatRoom content={content} key={content.chatRoomId} />));

   return (
      <>
      <div className="div_contents">
         <HeaderContainer pageName={"채팅방 목록"} />
         {/* <h1>채팅방</h1> */}
         {renderChatRooms}
         {/* {renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms}{renderChatRooms} */}
         {isLastPage?null:<Button className="btn_getChatRooms" onClick={getPages}>채팅방 목록 더보기</Button>}
      </div>
      <BottomNav />
      </>
   );
};

export default ChatRoomListPage;