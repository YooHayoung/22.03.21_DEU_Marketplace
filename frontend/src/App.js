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
import ItemDetailPage from "./pages/ItemDetailPage";
import ItemDetailContainer from "./containers/ItemDetailContainer";
import NotFound from "./pages/NotFound";
import BottomNav from "./components/nav/bottom/BottomNav";
import Story from "./pages/Story";
import SearchContainer from "./containers/SearchContainer";
import InputItemInfoContainer from "./containers/InputItemInfoContainer";



function App() {

  return (
    <div className="App">
      <CookiesProvider>
        <Routes>
          <Route path="/" element={<SellContainer />} />
          <Route path="/:classification" element={<SellContainer />} />
          <Route path="/item/:itemId" element={<ItemDetailContainer/>} />
          <Route path="/oauth" element={<LoginContainer />} />
          <Route path="/oauth/redirect/*" element={<LoginContainer />} />
          <Route path="/chatRooms" element={<ChatRoomListContainer />} />
          <Route path="/chatRooms/:chatRoomId" element={<ChatRoomContainer />} />
          <Route path="/notFound" element={<NotFound />} />
          <Route path="/search" element={<SearchContainer />} />
          <Route path="/save" element={<InputItemInfoContainer />}/>
          <Route path="/update" element={<InputItemInfoContainer />}/>
          <Route path="/post" />
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
