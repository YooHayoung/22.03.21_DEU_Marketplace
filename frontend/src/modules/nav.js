const SET = "nav/SET";
const CLEAR = "nav/CLEAR";

export const set = (value) => ({
    type: SET,
    topNav: value.topNav,
    bottomNav: value.bottomNav
});
export const clear = () => ({type: CLEAR});

const initialState = {
    topNav: 'main',
    bottomNav: 'main'
};

function nav(state = initialState, action) {
    switch (action.type) {
        case SET:
            return {
                topNav: action.topNav,
                bottomNav: action.bottomNav
            };
        case CLEAR:
            return {
                topNav: '',
                bottomNav: ''
            };
        default:
            return state;
    }
}

export default nav;

/* 
topNav : 일반 1개, 뒤로가기 1개, 뒤로가기 & 저장 1개
bottomNav : 일반 1개, 채팅 1개
*/