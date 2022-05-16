import React, { useState } from 'react';
import './Header.css'
import SearchIcon from '@mui/icons-material/Search';
import Person from '@mui/icons-material/Person'
import NotificationsIcon from '@mui/icons-material/Notifications';
import MenuIcon from '@mui/icons-material/Menu';
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

    const onBackBtnClick = (pageName) => {
        console.log(document.referrer);
        navigate(-1);

        // if (url.startsWith('/item/') && url.substring('/item/'.length)!='') {
        //     if (pageName=='SELL') {
        //         window.location.pathname = '/';
        //     } else if (pageName=='BUY') {
        //         window.location.pathname = '/buy';
        //     }
        // } else if (url.startsWith('/chatRooms/') && url.substring('/chatRooms/'.length)!='') {
        //     window.location.pathname = '/chatRooms';
        // } else if ((url === '/search') || (url==='/save') || (url==='/update')) {
        //     navigate(-1);
        // } else if (url.startsWith('/board/') && url.substring('/board/'.length)!='') {
        //     window.location.pathname = '/board';
        // } else if (url === '/myPage') {
        //     navigate(-1);
        // }
    }
    /* 
        type: main || back 
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
            {/* <MenuIcon onClick={() => toggleMenu()} className="header_MenuIcon" fontSize={"large"} /> */}
            <div className='header_MenuIcon'>{'DEU\nMarket\nPlace'}</div>
            <div className='header_pageName'><b>{pageTitle}</b></div>
            <SearchIcon className="header_searchIcon" fontSize={"large"} onClick={() => window.location.pathname="/search"}/>
            <NotificationsIcon className="header_notificationIcon" fontSize={"large"} />
            <Person className="person" fontSize={"large"} onClick={() => window.location.pathname="/myPage"} />
            </>
            );
        }
        else if ((url.startsWith('/item/') && url.substring('/item/'.length)!='') || 
                    (url.startsWith('/chatRooms/') && url.substring('/chatRooms/'.length)!='') ||
                    (url === '/search') || (url==='/save') || (url==='/update') ||
                    (url.startsWith('/board/') && url.substring('/board/'.length)!='') ||
                    (url === '/myPage')) {
                        console.log(url.substring('/chatRooms/'.length))
            return (
                <>
                <ArrowBackIcon className="header_arrow" onClick={() => onBackBtnClick(pageName)} fontSize={"large"} />
                <div className='header_pageName'><b>{pageTitle}</b></div>
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