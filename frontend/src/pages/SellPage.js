import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "../../node_modules/react-router-dom/index";
import jwt_decode from "jwt-decode";
import axios from "../../node_modules/axios/index";
import { doLogoutFromNaver, doLogoutFromServer, getNewAccessToken, getTokensFromNaver } from "../api/Api";
import { UseApi } from "../api/UseApi";


const SellPage = ({ token, setToken, onClear, oauth, code, state, accessToken, refreshToken, updateToken, remove }) => {
   let navigate = useNavigate();

   useEffect(() => {
      console.log(token);
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
                     // return Promise.reject(error);
                  }
               })
         })();
      } else {
         console.log(jwt_decode(token))
         console.log(jwt_decode(token).exp)
         console.log(Date.now() / 1000);
      }
   }, [])

   const work = (res) => {
      if (res.status === 200) {
         onClear();
         remove();
         window.location.href = "/oauth";
      }
   }

   const doLogout = () => {
      console.log(code);
      console.log(state);
      console.log(token);
      (async () => {
         UseApi(doLogoutFromServer, token, setToken, work, { code: code, state: state })
         // doLogoutFromServer({ code, state, token })
         //    .then((response) => {
         //       if (response.status === 200) {
         //          onClear();
         //          remove();
         //          window.location.href = "/oauth";
         //       }
         //    })
      })();
   }

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
      </div>
   );
};

export default SellPage;