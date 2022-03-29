import React, { useEffect, useState } from "react";

import ChatRoom from "../components/contents/chatRoomList/ChatRoom";

import axios from "../../node_modules/axios/index";

const ChatRoomListPage = () => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);

   useEffect(() => {
      (async () => {
         const headers = {
            'memberId': 1
         }
         axios.get('http://localhost:8080/api/v1/chatRoom', { headers: headers })
            .then((response) => {
               console.log(response.data);
               setContents(response.data.content);
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