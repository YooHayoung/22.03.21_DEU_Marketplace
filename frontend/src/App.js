import React from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";

import './App.css';

import IndexPage from "./pages/IndexPage";
import ChatRoomListPage from "./pages/ChatRoomListPage";
import ChatRoomPage from "./pages/ChatRoomPage";



function App() {
  return (
    <div className="App">
      <Routes>
        <Route exact path="/" element={<IndexPage />} />
        <Route exact path="/chatRooms" element={<ChatRoomListPage />} />
        <Route exact path="/chatRooms/:chatRoomId" element={<ChatRoomPage />} />
      </Routes>
    </div>
  );
}

export default App;
