import { connect } from "react-redux";
import Header from "../components/nav/top/Header";
import { set, clear } from "../modules/token";

const HeaderContainer = ({ token, set, clear, pageName }) => {
   return (
      <Header token={token} setToken={set} onClear={clear} pageName={pageName} />
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