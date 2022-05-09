import PostListPage from "../pages/PostListPage";
import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import oauth, { updateToken, updateState, remove } from "../modules/oauth";

const PostListContainer = ({ token, set, clear, oauth, code, state, accessToken, refreshToken, updateState, updateToken, remove}) => {
    return (
        <PostListPage token={token} setToken={set} onClear={clear} oauth={oauth} code={code} state={state} accessToken={accessToken} refreshToken={refreshToken}
        updateState={updateState} updateToken={updateToken} remove={remove} />
    );
};

export default connect(
    ({ oauth, token }) => ({
       token: token.accessToken,
       code: oauth.code,
       state: oauth.state,
       accessToken: oauth.accessToken,
       refreshToken: oauth.refreshToken,
    }),
    {
       set,
       clear,
       updateState,
       updateToken,
       remove,
    },
 )(PostListContainer);