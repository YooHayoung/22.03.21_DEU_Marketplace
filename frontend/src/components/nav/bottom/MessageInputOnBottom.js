import React, { useState } from "react";

import './BottomNav.scss'



const MessageInputOnBottom = (props) => {
   const [message, setMessage] = useState('');

   const onChange = (e) => {
      console.log(e.target.value);
      setMessage(e.target.value);
   }

   const onClick = () => {
      props.GetMessage(message);
      setMessage('');
   };

   const enterKeyEventHandler = (e) => {
      if (e.key === 'Enter') {
         onClick();
      }
   }

   return (
      <div className="div_messageInputOnBottom">
         <input type={'text'} value={message} onChange={onChange} onKeyPress={enterKeyEventHandler} placeholder="채팅을 입력하세요" />
         <button onClick={onClick}>
            전송
         </button>
      </div>
   );
};

export default MessageInputOnBottom;