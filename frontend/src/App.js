import React, { useEffect, useState } from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";


import './App.css';

import LoginPage from "./pages/LoginPage";
import ChatRoomListPage from "./pages/ChatRoomListPage";
import ChatRoomPage from "./pages/ChatRoomPage";
import CookiesProvider from "../node_modules/react-cookie/cjs/CookiesProvider";
import LoginAccess from "./pages/LoginAccess";



function App() {

  return (
    <div className="App">
      <CookiesProvider>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/oauth/redirect" element={<LoginPage />} />
          <Route path="/chatRooms" element={<ChatRoomListPage />} />
          <Route path="/chatRooms/:chatRoomId" element={<ChatRoomPage />} />
          {/* <Route path="/oauth/redirect/*" element={<LoginAccess />} /> */}
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
