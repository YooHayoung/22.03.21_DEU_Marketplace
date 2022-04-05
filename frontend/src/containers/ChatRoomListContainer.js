import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import ChatRoomListPage from "../pages/ChatRoomListPage";

const ChatRoomListContainer = ({ token, set, clear }) => {
   return (
      <ChatRoomListPage token={token} setToken={set} onClear={clear} />
   );
};

export default connect(
   state => ({
      token: state.token.accessToken,
   }),
   {
      set,
      clear,
   },
)(ChatRoomListContainer);