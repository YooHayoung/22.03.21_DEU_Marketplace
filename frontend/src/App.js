import React, { useEffect, useState } from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";

import './App.css';
import './components/nav/top/Header.css'

import CookiesProvider from "../node_modules/react-cookie/cjs/CookiesProvider";
import LoginContainer from "./containers/LoginContainer";
import SellContainer from "./containers/SellContainer";
import ChatRoomListContainer from "./containers/ChatRoomListContainer";
import ChatRoomContainer from "./containers/ChatRoomContainer";
import HeaderContainer from "./containers/HeaderContainer";



function App() {

  return (
    <div className="App">
      <CookiesProvider>
        <HeaderContainer />
        <Routes>
          <Route path="/" element={<SellContainer />} />
          <Route path="/oauth" element={<LoginContainer />} />
          <Route path="/oauth/redirect/*" element={<LoginContainer />} />
          <Route path="/chatRooms" element={<ChatRoomListContainer />} />
          <Route path="/chatRooms/:chatRoomId" element={<ChatRoomContainer />} />
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
