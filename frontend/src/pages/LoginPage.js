import React, { useEffect, useState } from "react";
import { useNavigate } from "../../node_modules/react-router/index";
import { NAVER_LOGIN_REQUEST_URI } from "../OAuth"
import { Typography } from "../../node_modules/@material-ui/core/index";
import NaverLoginBtnImg from "../image/2021_Login_with_naver_guidelines_Kr/btnG_완성형.png";
import "./LoginPage.scss";


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

   const onLoginBtnClick = () => {
      window.location.href = NAVER_LOGIN_REQUEST_URI;
   };

   return (
      <div className="div_loginPage" >
         <div>
         <h1 style={{margin: 0}}>Deu Marketplace</h1>
         <button className="loginBtn"  >
            <img className="img hover" src={NaverLoginBtnImg} onClick={onLoginBtnClick}/>
         </button>
         <Typography variant="body2" gutterBottom sx={{mt:15}} >
            네이버 로그인을 통해서 서비스를 이용하실 수 있습니다.
         </Typography>
         </div>
      </div>
   );
};

export default LoginPage;