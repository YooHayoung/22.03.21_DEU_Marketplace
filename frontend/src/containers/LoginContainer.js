import { connect } from "react-redux";
import LoginPage from "../pages/LoginPage"
import { set, clear } from "../modules/token";
import { updateToken, updateState, remove } from "../modules/oauth";

const LoginContainer = ({ token, set, clear, updateState, updateToken, remove }) => {
   return (
      <LoginPage token={token} setToken={set} onClear={clear}
         updateState={updateState} updateToken={updateToken} remove={remove} />
   );
};

export default connect(
   ({ token, oauth }) => ({
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
)(LoginContainer);