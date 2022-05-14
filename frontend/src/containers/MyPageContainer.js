import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import MyPage from "../pages/MyPage";

const MyPageContainer = ({token, set, clear}) => {
    return (
        <MyPage token={token} setToken={set} onClear={clear} />
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
 )(MyPageContainer);