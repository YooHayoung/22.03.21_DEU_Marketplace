import { connect } from "react-redux";
import SellPage from "../pages/SellPage";
import { set, clear } from "../modules/token";
import oauth, { updateToken, updateState, remove } from "../modules/oauth";

const SellContainer = ({ token, set, clear, oauth, code, state, accessToken, refreshToken, updateState, updateToken, remove}) => {
   return (
      <SellPage token={token} setToken={set} onClear={clear} oauth={oauth} code={code} state={state} accessToken={accessToken} refreshToken={refreshToken}
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
)(SellContainer);