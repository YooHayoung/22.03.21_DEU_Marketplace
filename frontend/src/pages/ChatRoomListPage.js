import React, { useEffect, useState } from "react";

import ChatRoom from "../components/contents/chatRoomList/ChatRoom";

import axios from "../../node_modules/axios/index";
import { useCookies } from "../../node_modules/react-cookie/cjs/index";
import { Cookies } from "../../node_modules/react-cookie/cjs/index";
import jwt_decode from "jwt-decode";

const ChatRoomListPage = (props) => {
   const [contents, setContents] = useState([]);
   const [page, setPage] = useState(0);
   const cookies = new Cookies('token');

   useEffect(() => {
      console.log(props.accessToken);

      if (props.accessToken === '' || (jwt_decode(props.accessToken).exp <= Date.now() / 1000)) {
         (async () => {
            axios.get('http://localhost:8000/oauth/refresh', { withCredentials: true })
               .catch((error) => {
                  if (error.response.status === 307) {
                     console.log(error.response.headers.authorization);
                     props.getAccessToken(error.response.headers.authorization);
                     axios.get('http://localhost:8000/api/v1/chatRoom', {
                        headers: {
                           Authorization: `Bearer ${error.response.headers.authorization}`
                        }
                     })
                        .then((response) => {
                           console.log(response.data);
                           setContents(response.data.content);
                        })
                        .catch((error) => {
                           if (error.response.status === 401) {
                              console.log(error.response.status);
                              window.location.href = "/";
                              return Promise.reject(error);
                           }
                        })
                     return Promise.reject(error);
                  } else if (error.response.status === 401) {
                     console.log(error.response.status);
                     window.location.href = "/";
                     return Promise.reject(error);
                  }
               })
         })();
      } else {
         (async () => {
            axios.get('http://localhost:8000/api/v1/chatRoom', {
               headers: {
                  Authorization: `Bearer ${props.accessToken}`
               }
            })
               .then((response) => {
                  console.log(response.data);
                  setContents(response.data.content);
               })
               .catch((error) => {
                  if (error.response.status === 401) {
                     console.log(error.response.status);
                     window.location.href = "/";
                     return Promise.reject(error);
                  }
               })
         })();
      }
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