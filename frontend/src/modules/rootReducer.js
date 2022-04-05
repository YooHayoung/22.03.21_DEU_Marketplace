import { combineReducers } from "redux";
import token from "./token";
import oauth from "./oauth";

const rootReducer = combineReducers({
    token,
    oauth,
});

export default rootReducer;