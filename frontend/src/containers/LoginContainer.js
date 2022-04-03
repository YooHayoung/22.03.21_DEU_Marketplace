import LoginPage from "../pages/LoginPage"

const LoginContainer = ({ token, set, clear }) => {
   return <LoginPage token={token} onSet={set} onClear={clear} />;
};

export default LoginContainer;