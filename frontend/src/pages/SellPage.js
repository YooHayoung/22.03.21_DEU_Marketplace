import React, { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "../../node_modules/react-router-dom/index";
import jwt_decode from "jwt-decode";
import axios from "../../node_modules/axios/index";
import { doLogoutFromNaver, doLogoutFromServer, getItemPage, getNewAccessToken, getTokensFromNaver } from "../api/Api";
import { UseApi } from "../api/UseApi";
import ItemListComponent from "../components/contents/item/ItemListComponent";
import HeaderContainer from "../containers/HeaderContainer";
import BottomNav from "../components/nav/bottom/BottomNav";
import { useLocation } from "../../node_modules/react-router/index";
import { Button } from "../../node_modules/@material-ui/core/index";
import './SellPage.scss';
import { AWS_SERVER, DOMAIN, LOCAL_SERVER } from "../api/client";


const SellPage = ({ token, setToken, onClear, oauth, code, state, accessToken, refreshToken, updateToken, remove }) => {
   let navigate = useNavigate();
   const location = useLocation();
   const params = useParams('localhost:3000/:classification');
   const classification = params.classification?params.classification.toUpperCase():'SELL';
   const [searchCond, setSearchCond] = useState({
      classification: classification,
      itemCategoryId: (location.state?(location.state.category.categoryId?location.state.category.categoryId:''):''),
      lectureName: '',
      professorName: '',
      title: (location.state?location.state.title:''),
      priceGoe: (location.state?location.state.priceGoe:''),
      priceLoe: (location.state?location.state.priceLoe:''),
   });
   
   console.log(searchCond.classification);
   const [page, setPage] = useState(0);
   const [contents, setContents] = useState([]);
   const [isLastPage, setIsLastPage] = useState(false); 

   useEffect(() => {
      // console.log(location);
      setPage(0);
      console.log(token);
      if (token === '') {
         (async () => {
            axios.get(DOMAIN+'/oauth/refresh', { withCredentials: true })
            // axios.get(AWS_SERVER+'/oauth/refresh', { withCredentials: true })
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
                  } else if (error.response.status === 403) {
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
   }, [searchCond]);


   const afterGetPage = (res) => {
      console.log(res);
      setIsLastPage(res.data.body.result.last);
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
         console.log(searchCond.itemCategoryId);
         console.log(contents);
         console.log(page);
      })();
   };

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

   const renderItemList = useCallback(() => {
      return contents.map((content) => (<ItemListComponent content={content} token={token} setToken={setToken} key={content.itemId} />));
   });

   const afteBottomButtonClick = () => {
      setPage(0);
      setContents([]);
   }

   return (
      <>
      <div className="div_contents">
         <HeaderContainer pageName={searchCond.classification}/>
         {/* <Link to={{
            pathname: `/oauth`,
            state: {},  
         }}>
            <button>로그인하기</button>
         </Link>
         <button onClick={doLogout}>로그아웃</button> */}
         <div className="itemList">
         {renderItemList()}
         </div>
         {isLastPage?null:(<Button className="btn_getItems" onClick={getPages}>물품 목록 더보기</Button>)}
         {/* {isChatsLast?null:(<Button className="btn_getChats" onClick={getChats}>불러오기</Button>)} */}
      </div>
      {<BottomNav thisPage={searchCond.classification} searchCond={searchCond} setSearchCond={setSearchCond} work={afteBottomButtonClick}/>}
      </>
   );
};

export default SellPage;