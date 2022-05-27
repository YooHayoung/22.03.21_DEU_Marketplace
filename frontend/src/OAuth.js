
// https://nid.naver.com/oauth2.0/authorize
// 서비스 URL: http://localhost:3000
// Callback URL: http://localhost:8000/login/ouath2/code/naver
// 요청 URI: http://localhost:8000/oauth/authorization

import { AWS_CLIENT, AWS_SERVER, DOMAIN, LOCAL_CLIENT, LOCAL_SERVER, THIS_CLIENT } from "./api/client";

// export const NAVER_LOGIN_URI = "https://nid.naver.com/oauth2.0/authorize";

export const LOGIN_REQUEST_URI = DOMAIN + '/oauth/authorization/';
// export const LOGIN_REQUEST_URI = LOCAL_SERVER + '/oauth/authorization/';

export const CALLBACK_URI = DOMAIN + '/oauth/callback';
// export const CALLBACK_URI = LOCAL_SERVER + '/oauth/callback';

export const REDIRECT_URI = THIS_CLIENT+'/oauth/redirect';
// export const REDIRECT_URI = LOCAL_CLIENT+'/oauth/redirect';

export const REDIRECT_PARAM = '?redirect_uri=';

export const PROVIDER_NAME = { naver: 'naver' };

export const NAVER_CLIENT_ID = 'DzgrFrgrm21HtqmUUgIv';
export const NAVER_CLIENT_SECRET = 'bs7Z8Mw1Cy';


export const NAVER_LOGIN_REQUEST_URI = LOGIN_REQUEST_URI + PROVIDER_NAME.naver + '?redirect_uri=' + REDIRECT_URI;