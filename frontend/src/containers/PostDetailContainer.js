import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import PostDetailPage from "../pages/PostDetailPage";

const PostDetailContainer = ({ token, set, clear }) => {
   return (
      <PostDetailPage token={token} setToken={set} onClear={clear} />
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
)(PostDetailContainer);