import React, { useEffect } from "react";
import { Link } from "../../node_modules/react-router-dom/index";

const SellPage = (props) => {
   useEffect(() => {
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
         <div>{props.accessToken}</div>
      </div>
   );
};

export default SellPage;