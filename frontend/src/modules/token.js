// 액션 타입 정의
const SET = "token/SET";
const CLEAR = "token/CLEAR";

// 액션 생성 함수 만들기
export const set = (value) => ({
    type: SET,
    value
});
export const clear = () => ({ type: CLEAR })

// 초기 상태 및 리듀서 함수 만들기
const initialState = {
    accessToken: ''
};

function token(state = initialState, action) {
    switch (action.type) {
        case SET:
            return {
                accessToken: action.value
            };
        case CLEAR:
            return {
                accessToken: ''
            };
        default:
            return state;
    }
}

export default token;