import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import NewChatRoomPage from "../pages/NewChatRoomPage"


const NewChatRoomContainer = ({token, set, clear}) => {
    return (
        <NewChatRoomPage token={token} setToken={set} onClear={clear} />
    );
};

export default connect(
    ({token}) => ({
        token: token.accessToken,
    }),
    {
        set,
        clear,
    },
)(NewChatRoomContainer);