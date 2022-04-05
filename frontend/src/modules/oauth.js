// 액션 타입 정의
const UPDATETOKEN = "oauth/UPDATETOKEN";
const UPDATESTATE = "oauth/UPDATESTATE";
const REMOVE = "oauth/REMOVE";

// 액션 생성 함수 만들기
export const updateToken = (value) => ({
   type: UPDATETOKEN,
   accessToken: value.accessToken,
   refreshToken: value.refreshToken
});
export const updateState = (value) => ({
   type: UPDATESTATE,
   code: value.code,
   state: value.state
});
export const remove = () => ({
   type: REMOVE
});


// 초기 상태 및 리듀서 함수 만들기
const initialState = {
   code: '',
   state: '',
   accessToken: '',
   refreshToken: ''
};

function oauth(state = initialState, action) {
   switch (action.type) {
      case UPDATETOKEN:
         return {
            // ...state,
            accessToken: action.accessToken,
            refreshToken: action.refreshToken
         };
      case UPDATESTATE:
         return {
            // ...state,
            code: action.code,
            state: action.state
         };
      case REMOVE:
         return {
            code: '',
            state: '',
            accessToken: '',
            refreshToken: ''
         };
      default:
         return state;
   }
}

export default oauth;