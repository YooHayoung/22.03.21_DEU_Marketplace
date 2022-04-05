import React, { useEffect, useState } from "react";
import { Link } from "../../node_modules/react-router-dom/index";
import { useNavigate } from "../../node_modules/react-router/index";
import { NAVER_LOGIN_REQUEST_URI } from "../OAuth"


const LoginPage = ({ token, setToken, updateState }) => {
   let navigate = useNavigate();

   useEffect(() => {

      const url = new URL(window.location.href);
      if (url.pathname === '/oauth/redirect') {
         setToken(url.searchParams.get('token'));
         updateState({
            code: url.searchParams.get("code"),
            state: url.searchParams.get("state")
         })
         navigate('/');
      }
   }, []);

   return (
      <div className="div_loginPage">
         <h1>Login Page</h1>
         <a href={NAVER_LOGIN_REQUEST_URI}><button>네이버 아이디로 로그인</button></a>
      </div>
   );
};

export default LoginPage;