import React, { useEffect, useState } from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";


import './App.css';

import LoginPage from "./pages/LoginPage";
import ChatRoomListPage from "./pages/ChatRoomListPage";
import ChatRoomPage from "./pages/ChatRoomPage";
import CookiesProvider from "../node_modules/react-cookie/cjs/CookiesProvider";
import LoginAccess from "./pages/LoginAccess";
import SellPage from "./pages/SellPage";



function App() {
  const [accessToken, setAccessToken] = useState('');

  const getAccessToken = (token) => {
    console.log(token);
    setAccessToken(token);
  }

  return (
    <div className="App">
      <CookiesProvider>
        <Routes>
          <Route path="/" element={<SellPage accessToken={accessToken} />} />
          <Route path="/oauth" element={<LoginPage />} />
          <Route path="/oauth/redirect" element={<LoginPage getAccessToken={getAccessToken} />} />
          <Route path="/chatRooms" element={<ChatRoomListPage />} />
          <Route path="/chatRooms/:chatRoomId" element={<ChatRoomPage />} />
          {/* <Route path="/oauth/redirect/*" element={<LoginAccess />} /> */}
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
