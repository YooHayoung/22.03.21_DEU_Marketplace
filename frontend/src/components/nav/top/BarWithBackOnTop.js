import React from "react";
import { useNavigate } from "../../../../node_modules/react-router-dom/index";

import './TopNav.scss'

const BarWithBackOnTop = () => {
   let navigate = useNavigate();
   // console.log(navigate);
   const url = window.location.pathname;

   // const setUrl = (url) => {
   //    if (url == "/chatRooms/new") {
   //       window.location.pathname = "/chatRooms"
   //    }
   // }

   const printBackUrl = () => {
      console.log(document.referrer);
      // if (document.referrer == "/chatRooms/new" || ) {
         // 두칸 뒤로
      // } else {
         // 한칸 뒤로
      // }
   }

   return (
      <header className="div_barWithBackOnTop">
         {/* <button className="btn_back" onClick={() => navigate(-1)}>Back</button> */}
         <button className="btn_back" onClick={() => printBackUrl()}>Back</button>
      </header>
   );
};

export default BarWithBackOnTop;