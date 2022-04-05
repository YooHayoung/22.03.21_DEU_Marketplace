import { connect } from "react-redux";
import Header from "../components/nav/top/Header";
import { set, clear } from "../modules/token";

const HeaderContainer = ({ token, set, clear }) => {
   return (
      <Header token={token} setToken={set} onClear={clear} />
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
)(HeaderContainer);