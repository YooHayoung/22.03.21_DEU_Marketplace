import { Link } from "../../../../node_modules/react-router-dom/index";
import { Navigate, useNavigate, useParams } from "../../../../node_modules/react-router/index"

import './BottomNav.scss';
import MessageInputOnBottom from "./MessageInputOnBottom";

const BottomNav = (props) => {
    let navigate = useNavigate();

    const params = useParams('localhost:3000/chatRooms/:chatRoomId');
    const url = window.location.pathname;

    const setUrl = (url) => {
        navigate(url);
    }

    const selectBottomNav = () => {
        // console.log(props);
        if (url == '/' || url == '/chatRooms') {
            return (
                <div className="mainBottomNav">
                    <button className="btn_sell" onClick={() => setUrl('/')}>팝니다</button>
                    <button className="btn_buy">삽니다</button>
                    <button className="btn_regist">+</button>
                    <button className="btn_board">게시판</button>
                    <button className="btn_chat" onClick={() =>setUrl('/chatRooms')}>채팅</button>
                </div>
            );
        }
        else if (window.location.pathname == `/chatRooms/${params.chatRoomId}`) {
            return (
                <>
                <MessageInputOnBottom onClick={props.onClick} />
                </>
            );
        }
    };

    return (
        <div className="div_bottomNav">
            {selectBottomNav()}
        </div>
    );
};

export default BottomNav;