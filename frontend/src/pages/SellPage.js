import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "../../node_modules/react-router-dom/index";
import jwt_decode from "jwt-decode";
import axios from "../../node_modules/axios/index";
import { doLogoutFromNaver, doLogoutFromServer, getNewAccessToken, getTokensFromNaver } from "../api/Api";


/////
import SockJS from 'sockjs-client';
import * as StompJS from '@stomp/stompjs';
import SockJsClient from 'react-stomp';
import { TalkBox } from "react-talk";
/////

const SellPage = ({ token, setToken, onClear, oauth, code, state, accessToken, refreshToken, updateToken, remove }) => {
   let navigate = useNavigate();

   useEffect(() => {
      if (token === '') {
         (async () => {
            axios.get('http://localhost:8000/oauth/refresh', { withCredentials: true })
               .catch((error) => {
                  if (error.response.status === 307) {
                     console.log(error.response.headers.authorization);
                     setToken(error.response.headers.authorization);
                     // return Promise.reject(error);
                  } else if (error.response.status === 401) {
                     console.log(error.response.status);
                     navigate('/oauth');
                     return Promise.reject(error);
                  }
               })
         })();
      } else {
         console.log(jwt_decode(token))
         console.log(jwt_decode(token).exp)
         console.log(Date.now() / 1000);
      }
   }, [])


   const doLogout = () => {
      console.log(code);
      console.log(state);
      console.log(token);
      (async () => {
         doLogoutFromServer({ code, state, token })
            .then((response) => {
               if (response.status === 200) {
                  onClear();
                  remove();
                  window.location.href = "/oauth";
               }
            })
      })();
   }


   //////////////////////////////////////

   // var sock = new SockJS('http://localhost:8000/chat')

   // const [data, setData] = useState({
   //    clientConnected: false,
   //    messages: []
   // });

   // const onMessageReceive = (msg, topic) => {
   //    setData(prevState => ({
   //       messages: [...prevState.messages, msg]
   //    }));
   // }

   // const sendMessage = (msg, selfMsg) => {
   //    try {
   //       this.clientRef.sendMessage("/app/enter/1", JSON.stringify(selfMsg));
   //       return true;
   //    } catch (e) {
   //       return false;
   //    }
   // }

   // const componentWillMount = () => {
   //    Fetch("/history", {
   //       method: "GET"
   //    }).then((response) => {
   //       setData({ messages: response.body });
   //    });
   // }



   //////////////////////////////////////

   return (
      <div>
         <h1>Sell Page</h1>
         {/* 로그인하러가는버튼은 임시 */}
         <Link to={{
            pathname: `/oauth`,
            state: {},
         }}>
            <button>로그인하기</button>
         </Link>
         {/* 로그아웃 버튼은 나중에 헤더쪽으로 보냄 */}
         <button onClick={doLogout}>로그아웃</button>
         {/* <Link to={{
            pathname: `/chatRooms`,
            state: {},
         }}><button>채팅방목록</button></Link> */}
         {/* 토큰 확인용 */}
         <div>{token}</div>
      </div>
   );
};

export default SellPage;