import React, { useEffect, useState } from "react";
import { Link } from "../../node_modules/react-router-dom/index";
import { NAVER_AUTH_URL } from "../Urls";

import Cookies from "../../node_modules/universal-cookie/cjs/Cookies";



const cookies = new Cookies();
export const setCookie = (name, value, option) => {
   return cookies.set(name, value, { ...option });
};
export const getCookie = (name) => {
   return cookies.get(name);
}

const IndexPage = (props) => {
   const [memberId, setMemberId] = useState('');

   useEffect(() => {
      console.log(document.cookie.match());
   }, []);

   const onChange = (e) => {
      setMemberId(e.target.value);
   };

   const onClick = () => {
      if (memberId !== '') {
         props.GetMemberId(memberId);
         setMemberId('');
      } else {
         alert('회원Id 입력하세요');
      }
   };

   const enterKeyEventHandler = (e) => {
      if (e.key === 'Enter') {
         onClick();
      }
   }

   return (
      <div className="div_indexPage">
         <h1>Index Page</h1>
         <input type={'text'} value={memberId} onChange={onChange} onKeyPress={enterKeyEventHandler} placeholder="회원Id 입력" />
         <button onClick={onClick}>
            전송
         </button>
         <div>{memberId}</div>
         <Link to={{
            pathname: `/chatRooms`,
            state: { memberId: memberId },
         }}><button>채팅방목록</button></Link>
         <a href={NAVER_AUTH_URL}><button>로그인</button></a>
      </div>
   );
};

export default IndexPage;