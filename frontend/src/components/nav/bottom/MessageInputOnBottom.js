import React, { useState } from "react";
import SendIcon from '@mui/icons-material/Send';

import './BottomNav.scss'
import { Button, TextField } from "../../../../node_modules/@material-ui/core/index";

const MessageInputOnBottom = (props) => {
   const [message, setMessage] = useState('');

   const onChange = (e) => {
      setMessage(e.target.value);
   }

   const onClick = () => {
      if (message !== '') {
         // props.GetMessage(message);
         console.log(message);
         props.onClick(message);
         setMessage('');
      } else {
         alert('메세지를 입력하세요');
      }
   };

   const enterKeyEventHandler = (e) => {
      if (e.key === 'Enter') {
         onClick();
      }
   }

   return (
      <div className="div_bottomNav_message">
         {/* <input type={'text'} value={message} onChange={onChange} onKeyPress={enterKeyEventHandler} placeholder="채팅을 입력하세요" /> */}
         <TextField multiline maxRows={3} value={message} onChange={onChange} placeholder="채팅을 입력하세요." sx={{flex: 1}}/>
         {/* <button onClick={onClick}>
            전송
         </button> */}
         <Button id="btn_send" variant="outlined" sx={{backgroundColor: "white"}} onClick={onClick} >
         <SendIcon />
         </Button>
      </div>
   );
};

export default MessageInputOnBottom;