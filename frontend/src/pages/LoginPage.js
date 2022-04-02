import React, { useEffect, useState } from "react";
import axios from "../../node_modules/axios/index";
import { Link } from "../../node_modules/react-router-dom/index";
import { useNavigate } from "../../node_modules/react-router/index";
// import { NAVER_AUTH_URL } from "../Urls";
// im port { NAVER_LOGIN_REQUEST_URI, LOGIN_REQUEST_URI, PROVIDER_NAME } from "../OAuth";


const LoginPage = (props) => {
   let navigate = useNavigate();

   useEffect(() => {
      const url = new URL(window.location.href);
      if (url.pathname === '/oauth/redirect') {
         props.getAccessToken(url.searchParams.get('token'));
         navigate('/');
      }
   }, []);

   const doLogin = () => {
      axios.get('http://localhost:8000/oauth2/authorization/naver?redirect_uri=http://localhost:3000/oauth/redirect', { withCredentials: true })
         .then((response) => {
            // if (response.status === 200) {
            //    const url = new URL(window.location.href);
            //    const token = url.searchParams.get('token');
            //    if (token !== null) {
            //       props.getAccessToken(token);
            //       window.location.href = "/";
            //    }
            // }
         })
   };

   return (
      <div className="div_loginPage">
         <h1>Login Page</h1>
         <Link to={{
            pathname: `/chatRooms`,
            state: {},
         }}><button>채팅방목록</button></Link>
         <a href={"http://localhost:8000/oauth/authorization/naver?redirect_uri=http://localhost:3000/oauth/redirect"}><button>로그인</button></a>
         {/* <button onClick={doLogin}>로그인</button> */}
      </div>
   );
};

export default LoginPage;