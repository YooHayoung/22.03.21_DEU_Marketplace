import React, { useCallback, useEffect, useRef, useState } from "react";
import axios from "../../node_modules/axios/index";
import { useMatch, useParams } from "../../node_modules/react-router-dom/index";
import ChatLogs from "../components/contents/chatRoom/ChatLogs";



import ItemInfo from "../components/contents/chatRoom/ItemInfo";
import MyChatLog from "../components/contents/chatRoom/MyChatLog";
import TargetChatLog from "../components/contents/chatRoom/TargetChatLog";
import TargetNickname from "../components/contents/chatRoom/TargetNickname";
import MessageInputOnBottom from "../components/nav/bottom/MessageInputOnBottom";
import BarWithBackOnTop from "../components/nav/top/BarWithBackOnTop";
// import jwt_decode from "jwt-decode";

import './ChatRoomPage.scss'
import { UseApi } from "../api/UseApi";
import BottomNav from "../components/nav/bottom/BottomNav";
import HeaderContainer from "../containers/HeaderContainer";
import { useSelector } from "react-redux";
import { Button } from "../../node_modules/@material-ui/core/index";
import { useLocation, useNavigate } from "../../node_modules/react-router/index";
import jwt_decode from "jwt-decode";
import { createChatRoom } from "../api/Api";

// var sock = SockJS("/stomp/chat");
// var client = null;

const NewChatRoomPage = ({ token, setToken }) => {
    const location = useLocation();
    const navigate = useNavigate();
    const [itemInfo, setItemInfo] = React.useState(location.state.itemInfo);
    console.log(itemInfo);

    const afterCreateChatRoom = (res) => {
        navigate(`/chatRooms/${res.data.body.result}`);
    };
    const onChatSubmitBtnClick = (message) => {
        (async () => {
            UseApi(createChatRoom, token, setToken, afterCreateChatRoom, {itemId: itemInfo.itemId, message: message});
        })();
    };

    return (
        <div className="div_chatRoomPage">
            <HeaderContainer pageName={"새 채팅"}/>
            <div className="div_contents">
                <ItemInfo itemInfo={itemInfo} itemSavedMemberId={location.state.itemSavedMemberId} myId={jwt_decode(token).sub} />
            </div> 
            <BottomNav onClick={onChatSubmitBtnClick} />
        </div>
    );
};

export default NewChatRoomPage;