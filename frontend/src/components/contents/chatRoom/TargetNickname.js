import React from "react";

import './css/TargetNickname.scss';

const TargetNickname = (props) => {
   return (
      <div className="div_targetNickname">{props.nickname}</div>
   );
};

export default TargetNickname;