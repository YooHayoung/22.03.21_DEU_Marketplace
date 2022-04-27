import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "../../node_modules/react-router-dom/index";
import jwt_decode from "jwt-decode";
import axios from "../../node_modules/axios/index";
import { doLogoutFromNaver, doLogoutFromServer, getItemPage, getNewAccessToken, getTokensFromNaver } from "../api/Api";
import { UseApi } from "../api/UseApi";
import ItemListComponent from "../components/contents/item/ItemListComponent";
import HeaderContainer from "../containers/HeaderContainer";
import BottomNav from "../components/nav/bottom/BottomNav";


const SellPage = ({ token, setToken, onClear, oauth, code, state, accessToken, refreshToken, updateToken, remove }) => {
   let navigate = useNavigate();
   const [searchCond, setSearchCond] = useState(
      {
         classification: 'SELL',
         itemCategoryId: '',
         lectureName: '',
         professorName: '',
         title: '',
         priceGoe: '',
         priceLoe: '',
      });
   const [page, setPage] = useState(0);
   const [contents, setContents] = useState([]);

   useEffect(() => {
      console.log(token);
      if (token === '') {
         (async () => {
            axios.get('http://localhost:8000/oauth/refresh', { withCredentials: true })
               .catch((error) => {
                  if (error.response.status === 307) {
                     console.log(error.response.headers.authorization);
                     setToken(error.response.headers.authorization);
                     getPages();
                     // return Promise.reject(error);
                  } else if (error.response.status === 401) {
                     console.log(error.response.status);
                     navigate('/oauth');
                     // return Promise.reject(error);
                  }
               })
         })();
      } else {
         console.log(jwt_decode(token))
         console.log(jwt_decode(token).exp)
         console.log(Date.now() / 1000);
         getPages();
      }
   }, [])


   const afterGetPage = (res) => {
      if (res.data.body.result.totalPages !== page){
         console.log(res.data.body.result);
         setContents([...contents, ...res.data.body.result.content]);
         setPage(page+1);
      }
   };

   const getPages = () => {
      (async () => {
         UseApi(getItemPage, token, setToken, afterGetPage, {
            classification: searchCond.classification,
            itemCategoryId: searchCond.itemCategoryId,
            professorName: searchCond.professorName,
            lectureName: searchCond.lectureName,
            title: searchCond.title,
            priceGoe: searchCond.priceGoe,
            priceLoe: searchCond.priceLoe,
            page: page
         });
         console.log(contents);
         console.log(page);
      })();
   }

   // useEffect(() => {
   //    getPages();
   // }, []);

   const work = (res) => {
      if (res.status === 200) {
         onClear();
         remove();
         window.location.href = "/oauth";
      }
   }

   const doLogout = () => {
      console.log(code);
      console.log(state);
      console.log(token);
      (async () => {
         UseApi(doLogoutFromServer, token, setToken, work, { code: code, state: state })
         // doLogoutFromServer({ code, state, token })
         //    .then((response) => {
         //       if (response.status === 200) {
         //          onClear();
         //          remove();
         //          window.location.href = "/oauth";
         //       }
         //    })
      })();
   }

   const renderItemList = contents.map((content) => (<ItemListComponent content={content} token={token} setToken={setToken} key={content.itemId} />));

   return (
      <div className="contents">
         <HeaderContainer pageName={"팝니다"}/>
         {/* <Link to={{
            pathname: `/oauth`,
            state: {},  
         }}>
            <button>로그인하기</button>
         </Link>
         <button onClick={doLogout}>로그아웃</button> */}
         <div className="itemList">
         {renderItemList}
         </div>
         <button onClick={getPages}>물품목록불러오기</button>
         <BottomNav />
      </div>
   );
};

export default SellPage;