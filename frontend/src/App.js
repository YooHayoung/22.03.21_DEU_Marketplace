import React, { useEffect, useState } from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";


import './App.css';

import LoginPage from "./pages/LoginPage";
import ChatRoomListPage from "./pages/ChatRoomListPage";
import ChatRoomPage from "./pages/ChatRoomPage";
import CookiesProvider from "../node_modules/react-cookie/cjs/CookiesProvider";
import LoginAccess from "./pages/LoginAccess";



function App() {
  const [accessToken, setAccessToken] = useState('');

  const getAccessToken = () => {
    if (document.cookie.match('accessToken') !== null) {
      const test = document.cookie.match('accessToken').input;
      setAccessToken(test.replace('accessToken=', ''));
    }
  }

  return (
    <div className="App">
      <CookiesProvider>
        <Routes>
          <Route path="/" element={<LoginPage getAccessToken={getAccessToken} accessToken={accessToken} />} />
          <Route path="/oauth/redirect" element={<LoginPage getAccessToken={getAccessToken} accessToken={accessToken} />} />
          <Route path="/chatRooms" element={<ChatRoomListPage getAccessToken={getAccessToken} accessToken={accessToken} />} />
          <Route path="/chatRooms/:chatRoomId" element={<ChatRoomPage getAccessToken={getAccessToken} accessToken={accessToken} />} />
          {/* <Route path="/oauth/redirect/*" element={<LoginAccess />} /> */}
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
