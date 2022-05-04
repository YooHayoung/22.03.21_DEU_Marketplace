import jwt_decode from "jwt-decode";
import { getNewAccessToken } from "./Api";

// api = method, accessToken = string, work = method
export const UseApi = (api, accessToken, setToken, work, object) => {
   if (accessToken === '' || (jwt_decode(accessToken).exp <= Date.now() / 1000)) {
      (async () => {
         getNewAccessToken()
            .catch((error) => {
               if (error.response.status === 307) {
                  let newToken = error.response.headers.authorization;
                  setToken(newToken);
                  api(newToken, object)
                     .then((response) => {
                        // 응답 이후 할 작업
                        work(response);
                     })
                     .catch((error) => {
                        if (error.response.status === 401) { // 로그인 안됨. 로그인페이지로 이동
                           window.location.href = "/";
                           // return Promise.reject(error);
                        }
                     });
               } else if (error.response.status === 401) {
                  window.location.href = "/";
                  // return Promise.reject(error);
               }
            })
      })();
   } else {
      (async () => {
         api(accessToken, object)
            .then((response) => {
               work(response);
            })
            .catch((error) => {
               console.log(error);
               if (error.response.status === 401) {
                  window.location.href = "/";
                  // return Promise.reject(error);
               }
            });
      })();
   }
};

// export default useApi;