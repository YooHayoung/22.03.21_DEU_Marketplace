import axios from "../../node_modules/axios/index";
import object from "../../node_modules/sockjs-client/lib/utils/object";
import { NAVER_CLIENT_ID, NAVER_CLIENT_SECRET } from "../OAuth";
import client from "./client";

const CHATLOG_PAGE_SIZE = 5;
const ITEM_PAGE_SIZE = 5;

export const getTokensFromNaver = ({ code, state }) => {
   return axios.get(`https://nid.naver.com/oauth2.0/token?client_id=${NAVER_CLIENT_ID}&client_secret=${NAVER_CLIENT_SECRET}&grant_type=authorization_code&state=${state}&code=${code}`, { headers: { "Access-Control-Allow-Origin": "*" } });
   // return client.get(`https://nid.naver.com/oauth2.0/token?client_id=${NAVER_CLIENT_ID}&client_secret=${NAVER_CLIENT_SECRET}&grant_type=authorization_code&state=${state}&code=${code}`, { headers: { "Access-Control-Allow-Origin": "*" } })
}

export const doLogoutFromNaver = (accessToken) => {
   return axios.get(`https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=${NAVER_CLIENT_ID}&client_secret=${NAVER_CLIENT_SECRET}&access_token=${accessToken}&service_provider=NAVER`);
}
export const doLogoutFromServer = (token, { code, state }) => {
   return client.get(`/oauth/logout?code=${code}&state=${state}`, { headers: { Authorization: `Bearer ${token}` } });
   // return client.get("/oauth/logout", { headers: { Authorization: `Bearer ${token}` } })
}

export const getNewAccessToken = () => {
   return client.get("/oauth/token", { withCredentials: true });
   // return client.get("/oauth/refresh", { withCredentials: true })
}

// 채팅방 목록 가져오기 - 페이징
export const getChatRoomPage = (token) => {
   return client.get("/api/v1/chatRoom", { headers: { Authorization: `Bearer ${token}` } });
}

// 채팅방 입장
export const getChatRoom = (token, object) => {
   return client.get("/api/v1/chatRoom/" + object.chatRoomId, { headers: { Authorization: `Bearer ${token}` } });
}

// 채팅 로그 가져오기 - 페이징
export const getChatPage = (token, object) => {
   return client.get("/api/v1/chat/" + object.chatRoomId + "?size=" + CHATLOG_PAGE_SIZE + "&page=" + object.page + "&enterTime=" + object.enterTime, { headers: { Authorization: `Bearer ${token}` } });
}

// 물품 목록 가져오기 - 페이징.
export const getItemPage = (token, object) => {
   return client.get("/api/v1/items?classification=" + object.classification + "&itemCategoryId=" + object.itemCategoryId
                        + "&lectureName=" + object.lectureName    + "&professorName=" + object.professorName + "&title=" + object.title
                        + "&priceGoe=" + object.priceGoe + "&priceLoe=" + object.priceLoe
                        + "&size=" + ITEM_PAGE_SIZE + "&page=" + object.page, {headers: {Authorization: `Bearer ${token}`}});
}

export const getItemDetail = (token, object) => {
   return client.get("/api/v1/items/" + object.itemId, {headers: {Authorization: `Bearer ${token}`}});
}

export const setWishItem = (token, object) => {
   return client.get("/api/v1/wishItem/"+object.itemId, { headers: { Authorization: `Bearer ${token}` } });
}

export const getItemCategory = (token, object) => {
   return client.get("/api/v1/itemCategory", {headers: {Authorization: `Bearer ${token}`}});
}

export const getPostCategory = (token, object) => {
   return client.get("/api/v1/postCategory", {headers: {Authorization: `Bearer ${token}`}});
}

export const getLectures = (token, object) => {
   // ?lectureName=영어&professorName=&page=0
   return client.get("/api/v1/lecture/search?" + "lectureName=" + object.lectureName 
      + "&professorName=" + object.professorName + "&page=" + object.page
      , {headers: {Authorization: `Bearer ${token}`}});
}

export const saveItem = (token, object) => {
   return client.post("/api/v1/items/save", object, {headers: {Authorization: `Bearer ${token}`}});
}

export const saveItemImgs = (token, object) => {
   return client.post("/api/v1/img/itemImg/upload", object, {headers: {Authorization: `Bearer ${token}`, "content-type": "multipart/form-data"}});
}