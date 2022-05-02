import { connect } from "react-redux";
import { set, clear } from "../modules/token";
import Story from "../pages/Story";

const SearchContainer = ({token, set, clear}) => {
    return (
        <Story token={token} setToken={set} onClear={clear} />
    );
};

export default connect(
    state => ({
        token: state.token.accessToken,
    }),
    {
        set,
        clear,
    },
)(SearchContainer);