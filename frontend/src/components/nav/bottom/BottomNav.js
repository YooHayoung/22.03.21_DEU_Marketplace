import React from "react";
import { BottomNavigation, BottomNavigationAction, Box, CssBaseline, Paper } from "../../../../node_modules/@material-ui/core/index";
import { Link } from "../../../../node_modules/react-router-dom/index";
import { Navigate, useLocation, useNavigate, useParams } from "../../../../node_modules/react-router/index"

import './BottomNav.scss';
import MessageInputOnBottom from "./MessageInputOnBottom";

import ChatIcon from '@mui/icons-material/Chat';
import SellIcon from '@mui/icons-material/Sell';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import AddIcon from '@mui/icons-material/Add';
import ArticleIcon from '@mui/icons-material/Article';

const BottomNav = (props) => {
    const urlList = ['/', '/chatRooms', '/sell', '/buy', '/board'];
    const [value, setValue] = React.useState(0);
    let navigate = useNavigate();
    const location = useLocation();

    const params = useParams('localhost:3000/chatRooms/:chatRoomId');
    const url = window.location.pathname;

    const setUrl = (url) => {
        if (url === '/save') {
            window.location.pathname=url;
            // navigate(url);
        } else {
            navigate(url);
        }
    }

    const selectBottomNav = () => {
        // console.log(props);
        if (urlList.includes(url)) {
            return (
                // <div className="mainBottomNav">
                //     <button className="btn_sell" onClick={() => {setUrl('/'); props.setSearchCond({...props.searchCond, classification: "SELL"}); props.work();}}>팝니다</button>
                //     <button className="btn_buy" onClick={() => {setUrl('/buy'); props.setSearchCond({...props.searchCond, classification: "BUY"}); props.work();}}>삽니다</button>
                //     <button className="btn_regist">+</button>
                //     <button className="btn_board">게시판</button>
                //     <button className="btn_chat" onClick={() =>setUrl('/chatRooms')}>채팅</button>
                // </div>
                <Paper className="mainBottomNav" sx={{ position: 'fixed', bottom: 0, left: 0, right: 0 }} elevation={3}>
                <BottomNavigation
                    showLabels
                    value={url}
                    onChange={(event, newValue) => {
                        setValue(newValue);
                        console.log(newValue);
                        setUrl(newValue);
                        if (newValue == '/') {
                            props.setSearchCond({classification: "SELL", itemCategoryId:'', lectureName:'', professorName:'', title:'', priceGoe:'', priceLoe:''}); 
                            props.work();
                        } else if (newValue == '/buy') {
                            props.setSearchCond({classification: "BUY", itemCategoryId:'', lectureName:'', professorName:'', title:'', priceGoe:'', priceLoe:''}); 
                            props.work();
                        }
                    }}
                >
                    <BottomNavigationAction label="팝니다" icon={<SellIcon />} value={('/')} />
                    <BottomNavigationAction label="삽니다" icon={<ShoppingBagIcon />} value={('/buy')} />
                    <BottomNavigationAction label="등록" icon={<AddIcon />} value={('/save')}/>
                    <BottomNavigationAction label="게시판" icon={<ArticleIcon />} value={('/board')} />
                    <BottomNavigationAction label="채팅" icon={<ChatIcon />} value={('/chatRooms')}/>
                </BottomNavigation>
                </Paper>
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