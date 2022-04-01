import React from "react";
import { useNavigate } from "../../../../node_modules/react-router-dom/index";

import './TopNav.scss'

const BarWithBackOnTop = () => {
   let navigate = useNavigate();
   // console.log(navigate);

   return (
      <header className="div_barWithBackOnTop">
         <button className="btn_back" onClick={() => navigate(-1)}>Back</button>
      </header>
   );
};

export default BarWithBackOnTop;