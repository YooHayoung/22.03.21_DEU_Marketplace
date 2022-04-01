import React, { useEffect, useState } from "react";

import ChatRoom from "../components/contents/chatRoomList/ChatRoom";

import axios from "../../node_modules/axios/index";
import { useCookies } from "../../node_modules/react-cookie/cjs/index";
import { Cookies } from "../../node_modules/react-cookie/cjs/index";

const ChatRoomListPage = (props) => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);
   const cookies = new Cookies('token');

   useEffect(() => {
      console.log(props.accessToken);
      (async () => {
         axios.get('http://localhost:8080/api/v1/chatRoom', { headers: { 'Authorization': props.accessToken } })
            .then((response) => {
               console.log(response.data);
               setContents(response.data.content);
               if (response.headers.Authorization !== null) {
                  props.getAccessToken();
               }
            })
            .catch((error) => {
               console.log(error.status);
               window.location.href = "/";
               return Promise.reject(error);
            })
      })();
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