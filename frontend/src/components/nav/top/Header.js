import React, { useState } from 'react';
import './Header.css'
import SearchIcon from '@mui/icons-material/Search';
import Person from '@mui/icons-material/Person'
import NotificationsIcon from '@mui/icons-material/Notifications';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from '../../../../node_modules/react-router-dom/index';
import { useNavigate } from '../../../../node_modules/react-router/index';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const Header = (props) => {
    let navigate = useNavigate();

    const [isOpen, setMenu] = useState(false);  // 메뉴의 초기값을 false로 설정

    const number = 10;
    const toggleMenu = () => {
        setMenu(isOpen => !isOpen); // on,off 개념 boolean
    }

    const url = window.location.pathname;

    const onBackBtnClick = () => {
        navigate(-1);
    }
    /* 
        type: main || back || backAndSave
    */
    const selectHeader = (pageName) => {
        // if(type === 'main'){
        let pageTitle;
        if (pageName == 'SELL') {
            pageTitle = '팝니다';
        } else if (pageName == 'BUY') {
            pageTitle = '삽니다';
        } else {
            pageTitle = pageName;
        }
        if (url == '/' || url == '/chatRooms' || url == '/buy' || url == '/sell' || url == '/board') {
            return (
            <>
            <MenuIcon onClick={() => toggleMenu()} className="header_MenuIcon" fontSize={"large"} />
            <div className='header_pageName'><b>{pageTitle}</b></div>
            <SearchIcon className="header_searchIcon" fontSize={"large"} onClick={() => window.location.pathname="/search"}/>
            <NotificationsIcon className="header_notificationIcon" fontSize={"large"} />
            <Person className="person" fontSize={"large"}/>
            </>
            );
        }
        else if ((url.startsWith('/item/') && url.substring('/item/'.length)!='') || 
                    (url.startsWith('/chatRooms/') && url.substring('/chatRooms/'.length)!='') ||
                    (url === '/search') || (url==='/save') || (url==='/update') ||
                    (url.startsWith('/board/') && url.substring('/board/'.length)!='')) {
                        console.log(url.substring('/chatRooms/'.length))
            return (
                <>
                <ArrowBackIcon className="header_arrow" onClick={() => onBackBtnClick()} fontSize={"large"} />
                <div className='header_pageName'>{pageTitle}</div>
                </>
            );
        }
        else {
            navigate("/notFound");
        }
    }

    return (
        <div className="header">
            {selectHeader(props.pageName)}
        </div >
    );
}

export default Header;