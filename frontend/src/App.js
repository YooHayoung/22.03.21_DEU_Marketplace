import React, { useEffect, useState } from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";


import './App.css';

import IndexPage from "./pages/IndexPage";
import ChatRoomListPage from "./pages/ChatRoomListPage";
import ChatRoomPage from "./pages/ChatRoomPage";
import CookiesProvider from "../node_modules/react-cookie/cjs/CookiesProvider";



function App() {
  const [memberId, setMemberId] = useState('');


  const GetMemberId = (memberId) => {
    setMemberId(memberId);
    console.log(memberId);
  };


  return (
    <div className="App">
      <CookiesProvider>
        <Routes>
          <Route exact path="/" element={<IndexPage GetMemberId={GetMemberId} />} />
          <Route exact path="/chatRooms" element={<ChatRoomListPage memberId={memberId} />} />
          <Route exact path="/chatRooms/:chatRoomId" element={<ChatRoomPage memberId={memberId} />} />
          <Route path="/oauth/redirect" element={<IndexPage />} />
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
