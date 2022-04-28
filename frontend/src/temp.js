// //Header.js

// import React, { useState } from 'react';
// import './Header.css'
// import SearchIcon from '@mui/icons-material/Search';
// import Person from '@mui/icons-material/Person'
// import NotificationsIcon from '@mui/icons-material/Notifications';
// import MenuIcon from '@mui/icons-material/Menu';
// import { Link } from '../../../../node_modules/react-router-dom/index';
// import { useNavigate } from '../../../../node_modules/react-router/index';
// import ArrowBackIcon from '@mui/icons-material/ArrowBack';

// const Header = (props) => {
//     let navigate = useNavigate();

//     const [isOpen, setMenu] = useState(false);  // 메뉴의 초기값을 false로 설정

//     const number = 10;
//     const toggleMenu = () => {
//         setMenu(isOpen => !isOpen); // on,off 개념 boolean
//     }

//     const url = window.location.pathname;
//     /* 
//         type: main || back || backAndSave
//     */
//     const selectHeader = (type) => {
//         // if(type === 'main'){
//         if (url == '/' || url == '/chatRooms') {
//             return (
//                 <>
//                 <div>
//                 <ul className="header_Menu">
//                     <li>
//                         <MenuIcon onClick={() => toggleMenu()} className="header_MenuIcon" />
//                     </li>
//                     <ul className={isOpen ? "show-menu" : "hide-menu"}>
//                         <Link to={{
//                             pathname: `/`,
//                         }}>
//                             <ul className="sell">팝니다
//                                 {/* <li>1</li>
//                             <li>2</li> */}
//                             </ul></Link>
//                         <ul className="buy">삽니다
//                             {/* <li>1</li>
//                             <li>2</li> */}
//                         </ul>
//                         <Link to={{
//                             pathname: `/chatRooms`,
//                             state: {},
//                         }}>
//                             <ul className="chatting">채팅
//                                 {/* <li>1</li>
//                             <li>2</li> */}
//                             </ul></Link>
//                     </ul>
//                 </ul>
//             </div>
//             <SearchIcon className="header_searchIcon" />
//             <NotificationsIcon className="header_notificationIcon" />
//             <Person className="person" />
//             </>
//             );
//         }
//         else if (window.location.pathname.startsWith('/item/')) {
//             return (<ArrowBackIcon className="header_arrow" onClick={() => navigate(-1)} />);
//         }
//     }

//     return (
//         <div className="header">
//             {selectHeader(props.headerType)}
//         </div >
//     );
// }

// export default Header;