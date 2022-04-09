import React, { useState } from 'react';
import './Header.css'
import SearchIcon from '@mui/icons-material/Search';
import Person from '@mui/icons-material/Person'
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from '../../../../node_modules/react-router-dom/index';

const Header = (props) => {

    const [isOpen, setMenu] = useState(false);  // 메뉴의 초기값을 false로 설정

    const number = 10;
    const toggleMenu = () => {
        setMenu(isOpen => !isOpen); // on,off 개념 boolean
    }

    return (
        <div className="header">
            <div>
                <ul className="header_Menu">
                    <li>
                        <MenuIcon onClick={() => toggleMenu()} className="header_MenuIcon" />
                    </li>
                    <ul className={isOpen ? "show-menu" : "hide-menu"}>
                        <Link to={{
                            pathname: `/`,
                        }}>
                            <ul className="sell">팝니다
                                {/* <li>1</li>
                            <li>2</li> */}
                            </ul></Link>
                        <ul className="buy">삽니다
                            {/* <li>1</li>
                            <li>2</li> */}
                        </ul>
                        <Link to={{
                            pathname: `/chatRooms`,
                            state: {},
                        }}>
                            <ul className="chatting">채팅
                                {/* <li>1</li>
                            <li>2</li> */}
                            </ul></Link>
                    </ul>
                </ul>
            </div>

            <div className="header_search">
                <input className="header_searchInput" type="text" />
                <SearchIcon className="header_searchIcon" />
            </div>

            <Person className="person" />

        </div >
    );
}

export default Header;