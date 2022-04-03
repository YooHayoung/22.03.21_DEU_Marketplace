import React, { useEffect } from "react";
import { Link, useNavigate } from "../../node_modules/react-router-dom/index";
import jwt_decode from "jwt-decode";
import axios from "../../node_modules/axios/index";

const SellPage = (props) => {
   let navigate = useNavigate();

   useEffect(() => {

      if (props.accessToken === '') {
         (async () => {
            axios.get('http://localhost:8000/oauth/refresh', { withCredentials: true })
               .catch((error) => {
                  if (error.response.status === 307) {
                     console.log(error.response.headers.authorization);
                     props.getAccessToken(error.response.headers.authorization);
                     // return Promise.reject(error);
                  } else if (error.response.status === 401) {
                     console.log(error.response.status);
                     navigate('/oauth');
                     return Promise.reject(error);
                  }
               })
         })();
      } else {
         console.log(jwt_decode(props.accessToken))
         console.log(jwt_decode(props.accessToken).exp)
         console.log(Date.now() / 1000);
      }
   }, [])

   return (
      <div>
         <h1>Sell Page</h1>
         <Link to={{
            pathname: `/oauth`,
            state: {},
         }}>
            <button>로그인하기</button>
         </Link>
         <Link to={{
            pathname: `/chatRooms`,
            state: {},
         }}><button>채팅방목록</button></Link>
         <div>{props.accessToken}</div>
         {/* <div>{}</div> */}
      </div>
   );
};

export default SellPage;