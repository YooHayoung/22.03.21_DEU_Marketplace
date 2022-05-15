import React, { useEffect, useState } from "react";
import { Route, Routes } from "../node_modules/react-router-dom/index";

import './App.css';
import './components/nav/top/Header.css'

import CookiesProvider from "../node_modules/react-cookie/cjs/CookiesProvider";
import LoginContainer from "./containers/LoginContainer";
import SellContainer from "./containers/SellContainer";
import ChatRoomListContainer from "./containers/ChatRoomListContainer";
import ChatRoomContainer from "./containers/ChatRoomContainer";
import ItemDetailContainer from "./containers/ItemDetailContainer";
import NotFound from "./pages/NotFound";
import SearchContainer from "./containers/SearchContainer";
import InputItemInfoContainer from "./containers/InputItemInfoContainer";
import PostListContainer from "./containers/PostListContainer";
import PostDetailContainer from "./containers/PostDetailContainer";
import NewChatRoomContainer from "./containers/NewChatRoomContainer";
import MyPageContainer from "./containers/MyPageContainer";



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
          <Route path="/myPage" element={<MyPageContainer />} />
          <Route path="/save" element={<InputItemInfoContainer />}/>
          <Route path="/update" element={<InputItemInfoContainer />}/>
          <Route path="/board" element={<PostListContainer />} />
          <Route path="/board/:postId" element={<PostDetailContainer />} />
          <Route path="/chatRooms/new" element={<NewChatRoomContainer />} />
        </Routes>
      </CookiesProvider>
    </div>
  );
}

export default App;
