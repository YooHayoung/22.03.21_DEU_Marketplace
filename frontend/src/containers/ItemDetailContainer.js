import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import ItemDetailPage from "../pages/ItemDetailPage";

const ItemDetailContainer = ({ token, set, clear }) => {
   return (
      <ItemDetailPage token={token} setToken={set} onClear={clear} />
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
)(ItemDetailContainer);