import axios from "../../node_modules/axios/index";
import object from "../../node_modules/sockjs-client/lib/utils/object";
import token from "../modules/token";
import { NAVER_CLIENT_ID, NAVER_CLIENT_SECRET } from "../OAuth";
import client from "./client";

const CHATLOG_PAGE_SIZE = 20;
const ITEM_PAGE_SIZE = 20;

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

// get 채팅방 목록 가져오기 및 페이징
export const getChatRoomPage = (token, object) => {
   return client.get("/api/v1/chatRoom?page=" + object.page, { headers: { Authorization: `Bearer ${token}` } });
}

// get 채팅방 입장
export const getChatRoom = (token, object) => {
   return client.get("/api/v1/chatRoom/" + object.chatRoomId, { headers: { Authorization: `Bearer ${token}` } });
}

// get 채팅 로그 가져오기 및 페이징
export const getChatPage = (token, object) => {
   return client.get("/api/v1/chat/" + object.chatRoomId + "?size=" + CHATLOG_PAGE_SIZE + "&page=" + object.page + "&enterTime=" + object.enterTime, { headers: { Authorization: `Bearer ${token}` } });
}

// get 물품 목록 검색 및 페이징.
export const getItemPage = (token, object) => {
   return client.get("/api/v1/items?classification=" + object.classification + "&itemCategoryId=" + object.itemCategoryId
                        + "&lectureName=" + object.lectureName    + "&professorName=" + object.professorName + "&title=" + object.title
                        + "&priceGoe=" + object.priceGoe + "&priceLoe=" + object.priceLoe
                        + "&size=" + ITEM_PAGE_SIZE + "&page=" + object.page, {headers: {Authorization: `Bearer ${token}`}});
}

// get 물품 상세 내용
export const getItemDetail = (token, object) => {
   return client.get("/api/v1/items/" + object.itemId, {headers: {Authorization: `Bearer ${token}`}});
}

// get 물품 찜 변경
export const setWishItem = (token, object) => {
   return client.get("/api/v1/wishItem/"+object.itemId, { headers: { Authorization: `Bearer ${token}` } });
}

// get 물품 카테고리 목록
export const getItemCategory = (token, object) => {
   return client.get("/api/v1/itemCategory", {headers: {Authorization: `Bearer ${token}`}});
}

// get 게시판 카테고리 목록
export const getPostCategory = (token, object) => {
   return client.get("/api/v1/postCategory", {headers: {Authorization: `Bearer ${token}`}});
}

// get 강의 목록 검색 및 페이징
export const getLectures = (token, object) => {
   // ?lectureName=영어&professorName=&page=0
   return client.get("/api/v1/lecture/search?" + "lectureName=" + object.lectureName 
      + "&professorName=" + object.professorName + "&page=" + object.page
      , {headers: {Authorization: `Bearer ${token}`}});
}

// post 물품 내용 등록
export const saveItem = (token, object) => {
   return client.post("/api/v1/items/save", object, {headers: {Authorization: `Bearer ${token}`}});
}

// post 물품 이미지 등록
export const saveItemImgs = (token, object) => {
   return client.post("/api/v1/img/itemImg/upload", object, {headers: {Authorization: `Bearer ${token}`, "content-type": "multipart/form-data"}});
}

// patch 물품 내용 수정
export const updateItem = (token, object) => {
   return client.patch("/api/v1/items/" + object.itemId, object.dto, {headers: {Authorization: `Bearer ${token}`}});
}

// patch 물품 이미지 수정
export const updateItemImgs = (token, object) => {
   return client.patch("/api/v1/img/itemImg/upload", object, {headers: {Authorization: `Bearer ${token}`, "content-type": "multipart/form-data"}});
}

// post 게시물 내용 등록
export const savePost = (token, object) => {
   return client.post("/api/v1/posts/save", object, {headers: {Authorization: `Bearer ${token}`}});
}

// post 게시물 이미지 등록
export const savePostImgs = (token, object) => {
   return client.post("/api/v1/img/postImg/upload", object, {headers: {Authorization: `Bearer ${token}`, "content-type": "multipart/form-data"}});
}

// patch 게시물 내용 수정
export const updatePost = (token, object) => {
   return client.patch("/api/v1/posts/" + object.postId, object.dto, {headers: {Authorization: `Bearer ${token}`}});
}

// patch 게시물 이미지 수정
export const updatePostImgs = (token, object) => {
   return client.patch("/api/v1/img/postImg/upload", object, {headers: {Authorization: `Bearer ${token}`, "content-type": "multipart/form-data"}});
}

// get 게시물 상세
export const getPostDetail = (token, object) => {
   return client.get("/api/v1/posts/" + object.postId, {headers: {Authorization: `Bearer ${token}`}});
}

// get 댓글 목록
export const getPostCommentPage = (token, object) => {
   return client.get("/api/v1/postComments?page=" + object.page + "&postId=" + object.postId, {headers: {Authorization: `Bearer ${token}`}});
}

// get 게시물 목록 검색 및 페이징
export const getPostList = (token, object) => {
   return client.get("/api/v1/posts?page=" + object.page + "&title=" + object.title + "&postCategoryId=" + object.postCategoryId, {headers: {Authorization: `Bearer ${token}`}});
}

// post 댓글 작성
export const savePostComment = (token, object) => {
   return client.post("/api/v1/postComments/new", object, {headers: {Authorization: `Bearer ${token}`}});
}

// delete 댓글 삭제
export const deletePostComment = (token, object) => {
   return client.delete("/api/v1/postComments/" + object.postCommentId, {headers: {Authorization: `Bearer ${token}`}});
}

// delete 게시물 삭제
export const deletePost = (token, object) => {
   return client.delete("/api/v1/posts/" + object.postId, {headers: {Authorization: `Bearer ${token}`}});
}

// post 채팅방 생성
export const createChatRoom = (token, object) => {
   return client.post("/api/v1/chatRoom/new", object, {headers: {Authorization: `Bearer ${token}`}});
}