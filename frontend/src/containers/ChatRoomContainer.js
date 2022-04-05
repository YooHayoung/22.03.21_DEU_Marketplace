import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import ChatRoomPage from "../pages/ChatRoomPage";

const ChatRoomContainer = ({ token, set, clear }) => {
   return (
      <ChatRoomPage token={token} setToken={set} onClear={clear} />
   );
};

export default connect(
   ({ token }) => ({
      token: token.accessToken,
   }),
   {
      set,
      clear,
   },
)(ChatRoomContainer);