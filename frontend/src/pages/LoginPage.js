import React, { useEffect, useState } from "react";
import { Link } from "../../node_modules/react-router-dom/index";
import { NAVER_AUTH_URL } from "../Urls";


const LoginPage = (props) => {

   return (
      <div className="div_loginPage">
         <h1>Login Page</h1>
         <Link to={{
            pathname: `/chatRooms`,
            state: {},
         }}><button>채팅방목록</button></Link>
         <a href={NAVER_AUTH_URL}><button>로그인</button></a>
         {/* <button onClick={doLogin}>로그인</button> */}
         <div>{props.accessToken}</div>
      </div>
   );
};

export default LoginPage;